package com.zjgsu.crazyshopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "ordersMain")
public class OrdersMain {


    @TableId(type = IdType.INPUT)
    private Integer id;
    private String username;
    private String phone;
    private String time;
    private String location;
    private Integer state;



    public OrdersMain() {
    }

    public OrdersMain(Integer id, String username, String phone, String time, String location, Integer state) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.time = time;
        this.location = location;
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


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }



    public void setUser(Account account){
        this.phone=account.getPhone();
        this.location=account.getLocation();
    }
}
