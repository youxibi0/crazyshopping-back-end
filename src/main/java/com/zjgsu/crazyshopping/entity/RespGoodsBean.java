package com.zjgsu.crazyshopping.entity;

import java.util.List;

public class RespGoodsBean {
    private Integer total;
    private List<Goods> goodsList;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }
}
