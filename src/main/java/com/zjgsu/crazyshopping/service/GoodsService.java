package com.zjgsu.crazyshopping.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.crazyshopping.entity.Goods;
import com.zjgsu.crazyshopping.entity.GoodsImages;
import com.zjgsu.crazyshopping.entity.RespGoodsBean;
import com.zjgsu.crazyshopping.mapper.GoodsImagesMapper;
import com.zjgsu.crazyshopping.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
public class GoodsService {

    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    ImageService imageService;
    @Autowired
    GoodsImagesMapper goodsImagesMapper;

    public int addGoods(Goods goods) {
        Goods goodsTemp = this.getGoods(1);
        if (goodsTemp.getId() != null) return 0;
        imageService.saveImg(goods);
        return goodsMapper.add(goods);
    }


    public Goods getGoods(int onEnable) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("onEnable", onEnable);
        List<Goods> goodsList = goodsMapper.selectByMap(map);
        Goods good = new Goods();
        for (Goods goods : goodsList) {
            good = goods;
        }
        return good;

    }


    public RespGoodsBean getAllGoods() {
        List<Goods> goodsList = goodsMapper.selectList(null);
        Integer total = Math.toIntExact(goodsMapper.selectCount(null));

        for (Goods goods : goodsList
        ) {
            this.setGoodsImgNameList(goods);
//            Map<String,Object> map = new HashMap<String,Object>();
//            map.put("goodsId",goods.getId());
//            List<GoodsImages> goodsImages = goodsImagesMapper.selectByMap(map);
//            List<String> imgList = new ArrayList<>();
//            for (GoodsImages image : goodsImages) {
//                imgList.add(image.getImgName());
//            }
//            goods.setImgNameList(imgList);
        }
        RespGoodsBean respGoodsBean = new RespGoodsBean();
        respGoodsBean.setGoodsList(goodsList);
        respGoodsBean.setTotal(total);
        return respGoodsBean;
    }

    public int modifyGoods(int id, Goods newGoods) {
        UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        return goodsMapper.update(newGoods, updateWrapper);
    }


    public int deleteGoods(int id) {
        UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        Goods goods = new Goods();
        goods.setOnEnable(0);
        return goodsMapper.update(goods, updateWrapper);


    }

    public void setGoodsImgNameList(Goods goods){
        Map<String,Object> map =new HashMap<String,Object>();
        map.put("goodsId",goods.getId());
        List<GoodsImages> goodsImages = goodsImagesMapper.selectByMap(map);
        List<String> imgList = new ArrayList<>();
        for (GoodsImages image : goodsImages) {
            imgList.add(image.getImgName());
        }
        goods.setImgNameList(imgList);
    }


}
