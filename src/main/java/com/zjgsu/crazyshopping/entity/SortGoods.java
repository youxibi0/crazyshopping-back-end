package com.zjgsu.crazyshopping.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("sortGoods")
public class SortGoods {
    private String one;
    private String two;
    private Integer goodsId;

    public SortGoods() {
    }

    public SortGoods(String one, String two, Integer goodsId) {
        this.one = one;
        this.two = two;
        this.goodsId = goodsId;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return "SortGoods{" +
                "one='" + one + '\'' +
                ", two='" + two + '\'' +
                ", goodsId=" + goodsId +
                '}';
    }
}
