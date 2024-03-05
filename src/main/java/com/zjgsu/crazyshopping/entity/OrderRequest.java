package com.zjgsu.crazyshopping.entity;

import java.util.List;

public class OrderRequest {
    private String username;
    private List<GoodsIdList> goodsIdList;
    private String name;
    private String location;
    private String phone;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<GoodsIdList> getGoodsIdList() {
        return goodsIdList;
    }

    public void setGoodsIdList(List<GoodsIdList> goodsIdList) {
        this.goodsIdList = goodsIdList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "username='" + username + '\'' +
                ", goodsIdList=" + goodsIdList +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
