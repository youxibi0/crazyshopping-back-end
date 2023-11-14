package com.zjgsu.crazyshopping.service;

import com.zjgsu.crazyshopping.entity.User;

public interface UserService {
  public User login(String password);
  public int modifyPassword(String oldPassword , String newPassword);




}
