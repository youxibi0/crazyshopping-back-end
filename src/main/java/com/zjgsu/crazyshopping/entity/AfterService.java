package com.zjgsu.crazyshopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("afterservice")
public class AfterService {
    @TableId(type = IdType.INPUT)
    private Integer id;
    private String title;
    private String context;
    private Integer ordersId;
    private String state;

    public AfterService(Integer id, String title, String context, Integer ordersId, String state) {
        this.id = id;
        this.title = title;
        this.context = context;
        this.ordersId = ordersId;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "AfterService{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", context='" + context + '\'' +
                ", ordersId=" + ordersId +
                ", state='" + state + '\'' +
                '}';
    }
}
