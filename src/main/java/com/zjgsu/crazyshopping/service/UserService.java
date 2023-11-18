package com.zjgsu.crazyshopping.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.crazyshopping.entity.Account;
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

    public Account login(String password) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("password",password);
        List<Account> accountList = userMapper.selectByMap(map);
        Account u = new Account();
        for(Account account : accountList){
            u = account;
        }
        return u;

    }


    public int modifyPassword(String oldPassword, String newPassword) {
            UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("password",oldPassword);
            Account u = new Account();
            u.setPassword(newPassword);
            return userMapper.update(u,updateWrapper);


    }
}
