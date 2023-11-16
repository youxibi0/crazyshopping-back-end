package com.zjgsu.crazyshopping.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.crazyshopping.entity.Goods;
import com.zjgsu.crazyshopping.entity.RespGoodsBean;
import com.zjgsu.crazyshopping.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class GoodsService {

    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;
    @Autowired
    private GoodsMapper goodsMapper;

    public int addGoods(Goods goods) {
        Goods goodsTemp = this.getGoods(1);
        if(goodsTemp.getId()!=null)return 0;
        this.saveImg(goods);
        return goodsMapper.add(goods);
    }


    public Goods getGoods(int onEnable) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("onEnable",onEnable);
        List<Goods> goodsList = goodsMapper.selectByMap(map);
        Goods good = new Goods();
        for(Goods goods:goodsList){
            good = goods;
        }
        return good;

    }


    public RespGoodsBean getAllGoods() {
        List<Goods> goodsList = goodsMapper.selectList(null);
        Integer total = Math.toIntExact(goodsMapper.selectCount(null));
        RespGoodsBean respGoodsBean = new RespGoodsBean();
        respGoodsBean.setGoodsList(goodsList);
        respGoodsBean.setTotal(total);
        return respGoodsBean;
    }

    public int modifyGoods(int id , Goods newGoods) {
        UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        return goodsMapper.update(newGoods , updateWrapper);
    }


    public int deleteGoods(int id) {
        UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        Goods goods = new Goods();
        goods.setIsFreeze(0);
        goods.setOnEnable(0);
        return goodsMapper.update(goods,updateWrapper);


    }

    public String doImg(MultipartFile imgFile){
        if(imgFile!=null){
            if (imgFile.getSize() > 1024 * 1024 * 10) {
                return "文件大小不能大于10M";
            }
            //获取文件后缀
            String suffix = imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf(".") + 1, imgFile.getOriginalFilename().length());
            if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
                return "请选择jpg,jpeg,gif,png格式的图片";
            }
        }
        return "true";
    }

    public void saveImg(Goods goods){
        if(goods.getImgFile()==null)return;
        MultipartFile file = goods.getImgFile();
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
        String savePath = UPLOAD_FOLDER;
        String filename = null;
        String absolutePath = null;
        try {
            File savePathFile = new File(savePath);
             absolutePath = savePathFile.getCanonicalPath();
            File absolutePathFile = new File(absolutePath);
            if (!absolutePathFile.exists()) {
                absolutePathFile.mkdir();
            }
            //通过UUID生成唯一文件名
            filename = UUID.randomUUID().toString().replaceAll("-","") + "." + suffix;
            //将文件保存指定目录
            file.transferTo(new File(absolutePath + "/" +filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        goods.setImg(filename);
    }
}
