package com.zjgsu.crazyshopping.service;

import com.zjgsu.crazyshopping.entity.Cart;
import com.zjgsu.crazyshopping.entity.Goods;
import com.zjgsu.crazyshopping.mapper.CartMapper;
import com.zjgsu.crazyshopping.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private GoodsMapper goodsMapper;
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
    public List<Goods> getCartByUsername(String username){
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        List<Cart> cartList;
        cartList = cartMapper.selectByMap(map);
        List<Goods> goodsList = new ArrayList<>();
        for(Cart cart:cartList){
            goodsList.add(goodsMapper.selectById(cart.getGoodsId()));
        }

        return goodsList;
    }



}
