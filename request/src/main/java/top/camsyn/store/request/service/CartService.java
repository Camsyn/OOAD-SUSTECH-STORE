package top.camsyn.store.request.service;

import org.springframework.stereotype.Service;
import top.camsyn.store.commons.entity.request.CartItem;
import top.camsyn.store.commons.entity.request.CartRequest;
import top.camsyn.store.commons.entity.request.Label;
import top.camsyn.store.commons.service.impl.SuperServiceImpl;
import top.camsyn.store.request.mapper.CartMapper;
import top.camsyn.store.request.mapper.LabelMapper;

import java.util.List;

@Service
public class CartService extends SuperServiceImpl<CartMapper, CartItem> {


    public List<CartRequest> getCartList(Integer sid){
        return this.baseMapper.getCart(sid, 0);
    }

    public List<CartRequest> getCartList(Integer sid, int state){
        return this.baseMapper.getCart(sid, state);
    }

    public boolean emptyCart(Integer sid){
        return this.baseMapper.emptyCart(sid);
    }
}
