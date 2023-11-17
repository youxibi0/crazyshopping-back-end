package com.zjgsu.crazyshopping.controller;


import com.zjgsu.crazyshopping.entity.Goods;
import com.zjgsu.crazyshopping.entity.RespBean;
import com.zjgsu.crazyshopping.entity.RespGoodsBean;
import com.zjgsu.crazyshopping.service.ImageService;
import com.zjgsu.crazyshopping.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ImageService imageService;
//    @GetMapping("/images/{img}")
//    public String getGoodsImage(@PathVariable String img){
//        Resource imageResource = imageService.loadImage(img);
//        try {
//            Path imagePath = imageResource.getFile().toPath();
//            byte[] imageBytes = Files.readAllBytes(imagePath);
//            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
//            return "data:image/jpeg;base64," + base64Image;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
    @RequestMapping("/onenable")
    public Goods getGoods(){
        Goods goods = goodsService.getGoods(1);
        return goods;

    }
    @GetMapping("/all")
    public RespGoodsBean getAllGoods(){
        RespGoodsBean respGoodsBean = goodsService.getAllGoods();
        return respGoodsBean;
    }
    @PostMapping(value = "/add")
    public RespBean addGoods(Goods goods){
        String temp = imageService.doImg(goods.getImgFiles());
        if(!"true".equals(temp)){
            return RespBean.error(temp);
        }
        if(goodsService.addGoods(goods)==1){
            return RespBean.ok("添加商品成功");
        }
            return RespBean.error("已有商品");
    }
    @PutMapping(value = "/delete/{id}")
    public RespBean deleteGoods(@PathVariable Integer id){
        if(goodsService.deleteGoods(id)==1){
            return RespBean.ok("下架商品成功");

        }
        return RespBean.error("下架商品失败");

    }
    @PutMapping(value = "/{id}")
    public RespBean modifyGoods(@PathVariable Integer id , Goods newGoods){
        if(goodsService.modifyGoods(id,newGoods)==1){
            return RespBean.ok("修改商品信息成功");
        }
        return RespBean.error("密码错误");

    }

}
