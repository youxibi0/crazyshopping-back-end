package com.zjgsu.crazyshopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("cart")
public class Cart {
    @TableId(type = IdType.INPUT)
    private Integer goodsId;
    private String username;

    public Cart(Integer goodsId, String username) {
        this.goodsId = goodsId;
        this.username = username;
    }
    public Cart() {
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "goodsId=" + goodsId +
                ", username='" + username + '\'' +
                '}';
    }
}
