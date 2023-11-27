package com.zjgsu.crazyshopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "orders")
public class Order {


    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;
    private String username;
    private String phone;
    private String time;
    private String location;
    private Integer goodsId;
    private String goodsName;
    private Double goodsPrice;
    private String goodsInfo;
    private String imgName;
    /**
     * 1代表未处理,2代表拒绝,3代表同意,4代表交易成功,5代表交易失败.
     */
    private Integer state;



    public Order() {
    }

    public Order(Integer id, String username, String phone, String time, String location, Integer goodsId, String goodsName, Double goodsPrice, String goodsInfo, String imgName, Integer state) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.time = time;
        this.location = location;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsInfo = goodsInfo;
        this.imgName = imgName;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setGoods(Goods goods){
        this.goodsInfo=goods.getInfo();
        this.goodsName=goods.getName();
        this.goodsPrice=goods.getPrice();
        if(!goods.getImgNameList().isEmpty())
            this.imgName=goods.getImgNameList().get(0);
    }

    public void setUser(Account account){
        this.phone=account.getPhone();
        this.location=account.getLocation();
    }
}
