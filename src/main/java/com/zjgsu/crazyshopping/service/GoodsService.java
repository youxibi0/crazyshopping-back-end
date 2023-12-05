package com.zjgsu.crazyshopping.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.crazyshopping.entity.*;
import com.zjgsu.crazyshopping.mapper.GoodsImagesMapper;
import com.zjgsu.crazyshopping.mapper.GoodsMapper;
import com.zjgsu.crazyshopping.mapper.SortGoodsMapper;
import com.zjgsu.crazyshopping.mapper.Tools;
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
    @Autowired
    Tools tools;

    public RespBean addGoods(Goods goods) {
        goods.setOnEnable(1);
        int temp = goodsMapper.insert(goods);
        goods.setId(tools.getId());
        imageService.saveImg(goods);
        saveImgName(goods);

        Goods newGoods = new Goods();
        newGoods.setId(goods.getId());

        if(temp == 1){
            return RespBean.ok("添加商品成功",newGoods);
        }
        return RespBean.error("出现错误");
    }




    public RespGoodsBean getAllGoods() {
        List<Goods> goodsList = goodsMapper.selectList(null);
        Integer total = Math.toIntExact(goodsMapper.selectCount(null));
        for (Goods goods : goodsList
        ) {
            this.setGoodsImgNameList(goods);
            this.setSort(goods);
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

    public void setSort(Goods goods){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodsId", goods.getId());
        List<SortGoods> sortGoodsList = sortGoodsMapper.selectByMap(map);
        if(null!=sortGoodsList && !sortGoodsList.isEmpty()){
            goods.setOne(sortGoodsList.get(0).getOne());
            goods.setTwo(sortGoodsList.get(0).getTwo());
        }
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
        if (goods.getNum() <=0 && goods.getOnEnable() != 0) {
            goods.setOnEnable(0);
            goodsMapper.update(goods, updateWrapper);
            return;
        }
        if (goods.getNum() > 0 && goods.getOnEnable() == 0) {
            goods.setOnEnable(1);
            goodsMapper.update(goods, updateWrapper);
            return;
        }
    }

    public Goods getGoodsById(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        Goods goods = goodsMapper.selectByMap(map).get(0);
        setGoodsImgNameList(goods);
        this.setSort(goods);
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
        for (Goods goods : goodsList
        ) {
            this.setGoodsImgNameList(goods);
            this.setSort(goods);
        }
        RespGoodsBean respGoodsBean = new RespGoodsBean();
        respGoodsBean.setGoodsList(goodsList);
        return respGoodsBean;
    }


}
