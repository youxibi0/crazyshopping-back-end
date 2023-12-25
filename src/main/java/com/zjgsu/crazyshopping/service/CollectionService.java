package com.zjgsu.crazyshopping.service;

import com.zjgsu.crazyshopping.entity.Collection;
import com.zjgsu.crazyshopping.mapper.CollectionMapper;
import com.zjgsu.crazyshopping.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CollectionService {
    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    public int addCollection(Collection collection){
        Map<String,Object> map = new HashMap<>();
        map.put("username",collection.getUsername());
        map.put("goodsId",collection.getGoodsId());
        if(!collectionMapper.selectByMap(map).isEmpty()){
            return 2;
        }
        return collectionMapper.insert(collection);
    }



}
