package com.zjgsu.crazyshopping.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.crazyshopping.entity.Goods;
import com.zjgsu.crazyshopping.entity.GoodsImages;
import com.zjgsu.crazyshopping.entity.RespGoodsBean;
import com.zjgsu.crazyshopping.entity.SortGoods;
import com.zjgsu.crazyshopping.mapper.GoodsImagesMapper;
import com.zjgsu.crazyshopping.mapper.GoodsMapper;
import com.zjgsu.crazyshopping.mapper.SortGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    @Autowired
    SortService sortService;
    @Autowired
    SortGoodsMapper sortGoodsMapper;

    public int addGoods(Goods goods, String one, String two) {
        goods.setOnEnable(1);
        int temp = goodsMapper.insert(goods);
        imageService.saveImg(goods);
        saveImgName(goods);
        //TODO:分类检测
        SortGoods sortGoods = new SortGoods(one, two, goods.getId());
        sortGoodsMapper.insert(sortGoods);
        return temp;
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

    public int modifyGoods(Goods newGoods) {
        Goods goods = getGoodsById(newGoods.getId());
        UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", newGoods.getId());
        goods.setNewGoods(newGoods);
        return goodsMapper.update(goods, updateWrapper);
    }


    public int disOnenable(int id) {
        UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        Goods goods = new Goods();
        goods.setOnEnable(0);
        return goodsMapper.update(goods, updateWrapper);


    }

    public void setGoodsImgNameList(Goods goods) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodsId", goods.getId());
        List<GoodsImages> goodsImages = goodsImagesMapper.selectByMap(map);
        List<String> imgList = new ArrayList<>();
        for (GoodsImages image : goodsImages) {
            imgList.add(image.getImgName());
        }
        goods.setImgNameList(imgList);
    }

    public void addNum(Integer id) {
        Goods goods = getGoodsById(id);
        UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        goods.setNum(goods.getNum() + 1);
        goodsMapper.update(goods, updateWrapper);
        checkOnenable(id);
    }

    public void subNum(Integer id) {
        Goods goods = getGoodsById(id);
        UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        goods.setNum(goods.getNum() - 1);
        goodsMapper.update(goods, updateWrapper);
        checkOnenable(id);
    }

    public void checkOnenable(Integer id) {
        Goods goods = getGoodsById(id);
        if (goods == null) return;
        UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        if (goods.getNum() == 0 && goods.getOnEnable() != 0) {
            goods.setOnEnable(0);
            goodsMapper.update(goods, updateWrapper);
            return;
        }
        if (goods.getNum() != 0 && goods.getOnEnable() == 0) {
            goods.setOnEnable(1);
            goodsMapper.update(goods, updateWrapper);
            return;
        }
    }

    public Goods getGoodsById(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        System.out.println(id);
        Goods goods = goodsMapper.selectByMap(map).get(0);
        return goods;
    }

    public void saveImgName(Goods goods) {
        if(goods.getImgNameList()==null)return;
        for (String imgName : goods.getImgNameList()
        ) {
            GoodsImages goodsImages=new GoodsImages(goods.getId(),imgName);
            goodsImagesMapper.insert(goodsImages);
        }
    }

    public RespGoodsBean searchGoods(String text,String one,String two){
        if(text==null)text="";
        text = text.trim();
        text="%"+text+"%";
        List<Goods> goodsList = goodsMapper.searchGoods(text,one,two);
        RespGoodsBean respGoodsBean = new RespGoodsBean();
        respGoodsBean.setGoodsList(goodsList);
        return respGoodsBean;
    }


}
