package com.zjgsu.crazyshopping.entity;

public class GoodsImages {
    private Integer goodsId;
    private String imgName;

    public GoodsImages() {
    }

    public GoodsImages(Integer goodsId, String imgName) {
        this.goodsId = goodsId;
        this.imgName = imgName;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}
