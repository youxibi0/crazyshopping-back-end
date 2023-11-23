package com.zjgsu.crazyshopping.controller;


import com.zjgsu.crazyshopping.entity.Goods;
import com.zjgsu.crazyshopping.entity.RespBean;
import com.zjgsu.crazyshopping.entity.RespGoodsBean;
import com.zjgsu.crazyshopping.service.ImageService;
import com.zjgsu.crazyshopping.service.GoodsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ImageService imageService;
    @GetMapping("/all")
    public RespGoodsBean getAllGoods(){
        RespGoodsBean respGoodsBean = goodsService.getAllGoods();
        return respGoodsBean;
    }
    @PostMapping(value = "/add")
    public RespBean addGoods(Goods goods,String one,String two){
        String temp = imageService.doImg(goods.getImgFiles());
        if(!"true".equals(temp)){
            return RespBean.error(temp);
        }

        if(goodsService.addGoods(goods,one,two)==1){
            return RespBean.ok("添加商品成功");
        }
            return RespBean.error("出现错误");
    }
    @PutMapping(value = "/disenable/{id}")
    public RespBean deleteGoods(@PathVariable Integer id){
        if(goodsService.disOnenable(id)==1){
            return RespBean.ok("下架商品成功");

        }
        return RespBean.error("下架商品失败");

    }
    @PutMapping(value = "/update")
    public RespBean modifyGoods(Goods newGoods){
        if(goodsService.modifyGoods(newGoods)==1){
            return RespBean.ok("修改商品信息成功");
        }
        return RespBean.error("密码错误");
    }

    @GetMapping(value = "/search")
    public RespGoodsBean searchGoods(@Param("text") String text, @Param("one") String one, @Param("two") String two){
        return  goodsService.searchGoods(text,one,two);
    }

}
