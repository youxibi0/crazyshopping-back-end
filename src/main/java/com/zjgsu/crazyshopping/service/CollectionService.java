package com.zjgsu.crazyshopping.service;

import com.zjgsu.crazyshopping.entity.*;
import com.zjgsu.crazyshopping.mapper.CollectionMapper;
import com.zjgsu.crazyshopping.mapper.GoodsImagesMapper;
import com.zjgsu.crazyshopping.mapper.GoodsMapper;
import com.zjgsu.crazyshopping.mapper.SortGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CollectionService {
    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private SortGoodsMapper sortGoodsMapper;
    @Autowired
    private GoodsImagesMapper goodsImagesMapper;
    public int addCollection(Collection collection){
        Map<String,Object> map = new HashMap<>();
        map.put("username",collection.getUsername());
        map.put("goodsId",collection.getGoodsId());
        if(!collectionMapper.selectByMap(map).isEmpty()){
            return 2;
        }
        return collectionMapper.insert(collection);
    }
    public List<Goods> getCollectionByUsername(String username){
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        List<Collection> CollectionList;
        CollectionList = collectionMapper.selectByMap(map);
        List<Goods> goodsList = new ArrayList<>();
        for(Collection Collection:CollectionList){
            Map<String,Object> map2 = new HashMap<>();
            map2.put("goodsId",Collection.getGoodsId());
            List<SortGoods> sortGoodsList  = sortGoodsMapper.selectByMap(map2);
            List<GoodsImages> goodsImagesList = goodsImagesMapper.selectByMap(map2);
            SortGoods sortGoods = sortGoodsList.get(0);
            GoodsImages goodsImages = goodsImagesList.get(0);
            Goods goods1 = goodsMapper.selectById(Collection.getGoodsId());
            Goods goods2 = new Goods(goods1.getId(),goods1.getName(),goods1.getInfo(),goods1.getPrice(),goods1.getOnEnable(),goods1.getNum(),goods1.getImgFiles());
            goods2.setOne(sortGoods.getOne());
            goods2.setTwo(sortGoods.getTwo());
            goods2.addImgName(goodsImages.getImgName());
            goodsList.add(goods2);
        }

        return goodsList;
    }
    public int deleteCollection(Collection collection){
        Map<String,Object> map = new HashMap<>();
        map.put("goodsId",collection.getGoodsId());
        map.put("username",collection.getUsername());
        return collectionMapper.deleteByMap(map);
    }
    public int isCollection(Collection collection){
        Map<String,Object> map = new HashMap<>();
        map.put("goodsId",collection.getGoodsId());
        map.put("username",collection.getUsername());
        List<Collection> collectionList = collectionMapper.selectByMap(map);
        if(collectionList.isEmpty()){
            return 1;
        }
        else {
            return 2;
        }
    }



}
