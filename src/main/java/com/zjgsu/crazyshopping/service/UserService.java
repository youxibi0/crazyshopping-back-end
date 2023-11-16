package com.zjgsu.crazyshopping.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.crazyshopping.entity.User;
import com.zjgsu.crazyshopping.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User login(String password) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("password",password);
        List<User> userList = userMapper.selectByMap(map);
        User u = new User();
        for(User user : userList){
            u = user;
        }
        return u;

    }


    public int modifyPassword(String oldPassword, String newPassword) {
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("password",oldPassword);
            User u = new User();
            u.setPassword(newPassword);
            return userMapper.update(u,updateWrapper);


    }
}
