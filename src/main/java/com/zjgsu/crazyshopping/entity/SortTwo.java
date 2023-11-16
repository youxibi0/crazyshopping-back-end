package com.zjgsu.crazyshopping.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("sortTwo")
public class SortTwo {
    private String one;
    private String two;

    public SortTwo(String one, String two) {
        this.one = one;
        this.two = two;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    @Override
    public String toString() {
        return "SortTwo{" +
                "one='" + one + '\'' +
                ", two='" + two + '\'' +
                '}';
    }
}
