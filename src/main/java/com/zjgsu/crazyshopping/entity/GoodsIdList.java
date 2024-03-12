package com.zjgsu.crazyshopping.entity;

public class GoodsIdList {
    private Integer goodsId;
    private Integer amount;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public GoodsIdList(Integer goodsId, Integer amount) {
        this.goodsId = goodsId;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "GoodsIdList{" +
                "goodsId=" + goodsId +
                ", amount=" + amount +
                '}';
    }
}
