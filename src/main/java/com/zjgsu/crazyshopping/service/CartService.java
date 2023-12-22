package com.zjgsu.crazyshopping.service;

import com.zjgsu.crazyshopping.entity.Cart;
import com.zjgsu.crazyshopping.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {
    @Autowired
    private CartMapper cartMapper;
    public int addCart(Cart cart){
        Map<String,Object> map = new HashMap<>();
        map.put("username",cart.getUsername());
        map.put("goodsId",cart.getGoodsId());
        System.out.println(cartMapper.selectByMap(map));
        if(!cartMapper.selectByMap(map).isEmpty()){
            return 2;
        }
        return cartMapper.insert(cart);
    }



}
