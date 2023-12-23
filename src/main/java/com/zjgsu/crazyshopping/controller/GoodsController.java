package com.zjgsu.crazyshopping.controller;


import com.zjgsu.crazyshopping.entity.Cart;
import com.zjgsu.crazyshopping.entity.Goods;
import com.zjgsu.crazyshopping.entity.RespBean;
import com.zjgsu.crazyshopping.entity.RespGoodsBean;
import com.zjgsu.crazyshopping.service.CartService;
import com.zjgsu.crazyshopping.service.ImageService;
import com.zjgsu.crazyshopping.service.GoodsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private CartService cartService;
    @GetMapping("/all")
    public RespGoodsBean getAllGoods(){
        RespGoodsBean respGoodsBean = goodsService.getAllGoods();
        return respGoodsBean;
    }
    @GetMapping("/{id}")
    public Goods getGoodsById(@PathVariable Integer id){
        return goodsService.getGoodsById(id);
    }
    @PostMapping(value = "/add")
    public RespBean addGoods(Goods goods){
        String temp = imageService.doImg(goods.getImgFiles());
        if(!"true".equals(temp)){
            return RespBean.error(temp);
        }
        return goodsService.addGoods(goods);

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
        String ans =goodsService.modifyGoods(newGoods);
        if(ans.equals("true")){
            return RespBean.ok("修改商品信息成功");
        }
        return RespBean.error(ans);
    }

    @GetMapping(value = "/search")
    public RespGoodsBean searchGoods(@Param("text") String text, @Param("one") String one, @Param("two") String two){
        return  goodsService.searchGoods(text,one,two);
    }
    @PostMapping("/addCart")
    public RespBean addCart(Cart cart){
        if(cartService.addCart(cart)==1){
            return RespBean.ok("商品成功添加到购物车");
        }
        else if(cartService.addCart(cart)==2){
            return RespBean.error(("此商品已在购物车中"));
        }
        else {
            return RespBean.error("添加商品至购物车失败");
        }
    }
    @GetMapping("/cart/{username}")
    public List<Goods> getCartByUsername(@PathVariable String username){

        return cartService.getCartByUsername(username);
    }
    @DeleteMapping("/cart")
    public RespBean deleteCart(Cart cart){
        if(cartService.deleteCart(cart)==1){
            return RespBean.ok("商品移出购物车成功");
        }
        else {
            return RespBean.error("商品移出购物车失败");
        }
    }

}
