package com.zjgsu.crazyshopping.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("user")
public class User {
    private String password;
    public User(){

    }
    public User(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                '}';
    }
}
