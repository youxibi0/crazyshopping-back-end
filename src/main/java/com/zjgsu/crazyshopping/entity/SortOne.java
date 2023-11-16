package com.zjgsu.crazyshopping.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("sortOne")
public class SortOne {
    private String one;

    public SortOne(String one) {
        this.one = one;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    @Override
    public String toString() {
        return "SortOne{" +
                "one='" + one + '\'' +
                '}';
    }
}
