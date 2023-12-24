package com.zjgsu.crazyshopping.service;

import com.zjgsu.crazyshopping.entity.Cart;
import com.zjgsu.crazyshopping.entity.Goods;
import com.zjgsu.crazyshopping.entity.SortGoods;
import com.zjgsu.crazyshopping.mapper.CartMapper;
import com.zjgsu.crazyshopping.mapper.GoodsMapper;
import com.zjgsu.crazyshopping.mapper.SortGoodsMapper;
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
    @Autowired
    private SortGoodsMapper sortGoodsMapper;
    public int addCart(Cart cart){
        Map<String,Object> map = new HashMap<>();
        map.put("username",cart.getUsername());
        map.put("goodsId",cart.getGoodsId());
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
            Map<String,Object> map2 = new HashMap<>();
            map2.put("goodsId",cart.getGoodsId());
            List<SortGoods> sortGoodsList  = sortGoodsMapper.selectByMap(map2);
            SortGoods sortGoods = sortGoodsList.get(0);
            Goods goods1 = goodsMapper.selectById(cart.getGoodsId());
            Goods goods2 = new Goods(goods1.getId(),goods1.getName(),goods1.getInfo(),goods1.getPrice(),goods1.getOnEnable(),goods1.getNum(),goods1.getImgFiles());
            goods2.setOne(sortGoods.getOne());
            goods2.setTwo(sortGoods.getTwo());
            goodsList.add(goods2);
        }

        return goodsList;
    }
    public int deleteCart(Cart cart){
        Map<String,Object> map = new HashMap<>();
        map.put("username",cart.getUsername());
        map.put("goodsId",cart.getGoodsId());
        return cartMapper.deleteByMap(map);
    }




}
