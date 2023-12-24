package com.zjgsu.crazyshopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("collection")
public class Collection {
    @TableId(type = IdType.INPUT)
    private Integer goodsId;
    private String username;

    public Collection(Integer goodsId, String username) {
        this.goodsId = goodsId;
        this.username = username;
    }
    public Collection() {

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
        return "Collection{" +
                "goodsId=" + goodsId +
                ", username='" + username + '\'' +
                '}';
    }
}
