package com.zjgsu.crazyshopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "orders")
public class Order {
    @TableId(type = IdType.INPUT)
    private Integer id;
    private Integer goodsId;
    private String goodsInfo;
    private Double goodsPrice;
    private String goodsName;
    private String imgName;
    private Integer ordersId;
    @TableField(exist = false)
    private int amount;

    public Order() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }
    public void setGoods(Goods goods){
        this.goodsId=goods.getId();
        this.goodsInfo=goods.getInfo();
        this.goodsName=goods.getName();
        this.goodsPrice=goods.getPrice();
        if(!goods.getImgNameList().isEmpty())
            this.imgName=goods.getImgNameList().get(0);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", goodsInfo='" + goodsInfo + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsName='" + goodsName + '\'' +
                ", imgName='" + imgName + '\'' +
                ", ordersId=" + ordersId +
                ", amount=" + amount +
                '}';
    }
}
