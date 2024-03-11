package com.zjgsu.crazyshopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

@TableName(value = "ordersMain")
public class OrdersMain {


    @TableId(type = IdType.INPUT)
    private Integer id;
    private String username;
    private String phone;
    private String time;
    private String location;
    private Integer state;
    private String name;
    private int amount;



    @TableField(exist = false)
    private List<Order> orderList;



    public OrdersMain() {
    }

    public OrdersMain(Integer id, String username, String phone, String time, String location, Integer state, String name , int amount) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.time = time;
        this.location = location;
        this.state = state;
        this.name = name;
        this.amount = amount;
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

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public void setUser(Account account){
        this.username=account.getUsername();
        this.phone=account.getPhone();
        this.location=account.getLocation();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrdersMain{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", time='" + time + '\'' +
                ", location='" + location + '\'' +
                ", state=" + state +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", orderList=" + orderList +
                '}';
    }
}
