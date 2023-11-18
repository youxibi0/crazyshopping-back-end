package com.zjgsu.crazyshopping.entity;

public class UserInfo {
    private String username;
    private String phone;
    private String location;

    public UserInfo() {
    }

    public UserInfo(String username, String phone, String location) {
        this.username = username;
        this.phone = phone;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
