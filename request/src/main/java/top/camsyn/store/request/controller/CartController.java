package top.camsyn.store.request.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.commons.entity.request.CartItem;
import top.camsyn.store.commons.entity.request.CartRequest;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.exception.BusinessException;
import top.camsyn.store.commons.helper.UaaHelper;
import top.camsyn.store.commons.model.Result;
import top.camsyn.store.commons.model.UserDto;
import top.camsyn.store.request.service.CartService;
import top.camsyn.store.request.service.RequestService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    RequestService requestService;


    @PostMapping("/add")
    public Result<CartItem> addRequestToCart(@RequestParam("requestId") Integer requestId, @RequestParam("count") Integer count) {
        log.info("addRequestToCart");
        final UserDto currentUser = UaaHelper.getCurrentUser();
        final Integer sid = currentUser.getSid();
        final Request req = requestService.getById(requestId);
        if (req.getPusher().equals(sid)){
            log.info("无法将自己发布的请求纳入请求清单, sid：{}",sid);
            return Result.failed("无法将自己发布的请求纳入请求清单");
        }
        final CartItem item = CartItem.builder().owner(sid).requestId(requestId).count(count).build();
        cartService.save(item);
        log.info("成功加入购物车");
        return Result.succeed(item, "成功加入购物车");
    }

    @SneakyThrows
    @PutMapping("/modify/count")
    public Result<CartItem> modifyCartItem(@RequestParam("cartItemId") Integer cartItemId, @RequestParam("count") Integer count) {
        log.info("modifyCartItem");
        if (count <= 0) throw new BusinessException("修改后的count不得小于0");
        final CartItem item = cartService.getById(cartItemId);
        UaaHelper.assertAdmin(item.getOwner());
        if (item.getState() != 0) throw new BusinessException("已完成的购物车条目不得修改");

        item.setCount(count);
        cartService.updateById(item);
        log.info("成功修改");
        return Result.succeed(item, "成功修改");
    }

    @SneakyThrows
    @DeleteMapping("/delete")
    public Result<CartItem> deleteCartItem(@RequestParam("cartItemId") Integer cartItemId) {
        log.info("deleteCartItem： {}", cartItemId);
        final CartItem item = cartService.getById(cartItemId);
        UaaHelper.assertAdmin(item.getOwner());
        if (item.getState() != 0) throw new BusinessException("已完成的购物车条目不得修改");
        cartService.removeById(item.getId());
        log.info("成功删除");
        return Result.succeed(item, "成功删除");
    }

    @SneakyThrows
    @PutMapping("/empty")
    public Result<Boolean> emptyCart() {
        log.info("清空购物车");
        final int owner = UaaHelper.getLoginSid();
        return Result.succeed(cartService.emptyCart(owner), "成功清空");
    }

    @SneakyThrows
    @GetMapping("/get")
    public Result<List<CartRequest>> getCart() {
        log.info("getCart");
        final int owner = UaaHelper.getLoginSid();
        final List<CartRequest> cartList = cartService.getCartList(owner);
        log.info("getCart success");
        return Result.succeed(cartList, "成功清空");
    }


    @SneakyThrows
    @GetMapping("/get/state")
    public Result<List<CartRequest>> getCart(@RequestParam("state") Integer state) {
        log.info("getCart");
        final int owner = UaaHelper.getLoginSid();
        final List<CartRequest> cartList = cartService.getCartList(owner, state);
        log.info("getCart success");
        return Result.succeed(cartList, "成功清空");
    }


    @PutMapping("/finish")
    public Result<List<CartItem>> finishCart() {
        log.info("finishCart");
        final int owner = UaaHelper.getLoginSid();
        final List<CartRequest> cartList = cartService.getCartList(owner, 0);
        List<CartItem> cartItems = new ArrayList<>();
        for (CartRequest cartRequest : cartList) {
            final Result<Object> error = requestService.pullRequest(cartRequest.getId(), cartRequest.getCartItemCount(), owner);
            if (error != null) {
                log.info("消费失败");
                return Result.failed(error.getResp_msg());
            }
            final CartItem cartItem = cartRequest.toCartItem();
            cartItem.setState(1);
            cartItems.add(cartItem);
        }
        cartService.updateBatchById(cartItems);
        log.info("已批量拉取请求");
        return Result.succeed(cartItems, "已批量拉取请求");
    }

    @PutMapping("/satisfy")
    public Result<List<CartItem>> satisfyCart(@RequestParam("cartItemId") List<Integer> cartItemIds) {
        log.info("satisfy");
        final int owner = UaaHelper.getLoginSid();
        final List<CartRequest> cartList = cartService.getCartList(owner, 0);
        List<CartItem> cartItems = new ArrayList<>();
        for (CartRequest cartRequest : cartList) {
            if (!cartItemIds.contains(cartRequest.getCartItemId())) continue;
            final Result<Object> error = requestService.pullRequest(cartRequest.getId(), cartRequest.getCartItemCount(), owner);
            if (error != null) {
                log.info("消费失败");
                cartService.updateBatchById(cartItems);
                return Result.failed(error.getResp_msg());
            }
            final CartItem cartItem = cartRequest.toCartItem();
            cartItem.setState(1);
            cartItems.add(cartItem);
        }
        cartService.updateBatchById(cartItems);
        log.info("已批量拉取请求");
        return Result.succeed(cartItems, "已批量拉取请求");
    }


}
