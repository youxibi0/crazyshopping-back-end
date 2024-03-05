package com.zjgsu.crazyshopping.entity;

public class GoodsIdList {
    private int goodsId;
    private int amount;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public GoodsIdList(int goodsId, int amount) {
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
