package com.zjgsu.crazyshopping.entity;

import java.util.List;

public class RespOrderBean {
private Integer total;
private List<?> ordersList;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<?> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<?> ordersList) {
        this.ordersList = ordersList;
    }


}
