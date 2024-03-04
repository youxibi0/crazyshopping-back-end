package com.zjgsu.crazyshopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("cart")
public class Cart {
    @TableId(type = IdType.INPUT)
    private Integer goodsId;
    private String username;
    private int amount;
    public Cart() {

    }
    public Cart(Integer goodsId, String username, int amount) {
        this.goodsId = goodsId;
        this.username = username;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "goodsId=" + goodsId +
                ", username='" + username + '\'' +
                ", amount=" + amount +
                '}';
    }
}
