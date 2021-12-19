package top.camsyn.store.request.controller;

import lombok.SneakyThrows;
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

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    RequestService requestService;

    @Autowired
    RequestController requestController;

    @PostMapping("/add")
    public Result<CartItem> addRequestToCart(@RequestParam("requestId") Integer requestId, @RequestParam("count") Integer count){
        final UserDto currentUser = UaaHelper.getCurrentUser();
        final Integer sid = currentUser.getSid();
        final CartItem item = CartItem.builder().owner(sid).requestId(requestId).count(count).build();
        cartService.save(item);
        return Result.succeed(item,"成功加入购物车");
    }

    @SneakyThrows
    @PutMapping("/modify/count")
    public Result<CartItem> modifyCartItem(@RequestParam("cartItemId") Integer cartItemId, @RequestParam("count") Integer count){
        if (count <= 0) throw new BusinessException("修改后的count不得小于0");
        final CartItem item = cartService.getById(cartItemId);
        UaaHelper.assertAdmin(item.getOwner());
        if (item.getState() != 0 ) throw new BusinessException("已完成的购物车条目不得修改");

        item.setCount(count);
        cartService.updateById(item);
        return Result.succeed(item,"成功修改");
    }
    @SneakyThrows
    @Delete("/delete")
    public Result<CartItem> deleteCartItem(@RequestParam("cartItemId") Integer cartItemId){
        final CartItem item = cartService.getById(cartItemId);
        UaaHelper.assertAdmin(item.getOwner());
        if (item.getState() != 0 ) throw new BusinessException("已完成的购物车条目不得修改");
        cartService.removeById(item.getId());
        return Result.succeed(item,"成功删除");
    }

    @SneakyThrows
    @PutMapping("/empty")
    public Result<Boolean> emptyCart(){
        final int owner = UaaHelper.getLoginSid();
        return Result.succeed(cartService.emptyCart(owner),"成功清空");
    }

    @SneakyThrows
    @GetMapping("/get")
    public Result<List<CartRequest>> getCart(){
        final int owner = UaaHelper.getLoginSid();
        final List<CartRequest> cartList = cartService.getCartList(owner);
        return Result.succeed(cartList,"成功清空");
    }


    @SneakyThrows
    @GetMapping("/get/state")
    public Result<List<CartRequest>> getCart(@RequestParam("state") Integer state){
        final int owner = UaaHelper.getLoginSid();
        final List<CartRequest> cartList = cartService.getCartList(owner, state);
        return Result.succeed(cartList,"成功清空");
    }


    @PutMapping("/finish")
    public Result<List<CartItem>> finishCart(){
        final int owner = UaaHelper.getLoginSid();
        final List<CartRequest> cartList = cartService.getCartList(owner, 0);
        List<CartItem> cartItems = new ArrayList<>();
        for (CartRequest cartRequest : cartList) {
            requestController.pullRequest(cartRequest.getId(), cartRequest.getCartItemCount());
            final CartItem cartItem = cartRequest.toCartItem();
            cartItem.setState(1);
            cartItems.add(cartItem);
        }
        cartService.updateBatchById(cartItems);
        return Result.succeed(cartItems,"已批量拉取请求");
    }



}