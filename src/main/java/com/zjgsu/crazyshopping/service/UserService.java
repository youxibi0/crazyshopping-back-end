package com.zjgsu.crazyshopping.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.crazyshopping.entity.Account;
import com.zjgsu.crazyshopping.entity.UserInfo;
import com.zjgsu.crazyshopping.mapper.UserInfoMapper;
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
    @Autowired
    private UserInfoMapper userInfoMapper;

    public Account login(Account user) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("username",user.getUsername());
        map.put("password",user.getPassword());
        List<Account> accountList = userMapper.selectByMap(map);
        Account u = new Account();
        for(Account account : accountList){
            u = account;
        }
        if(u.getLevel()==1){
            UserInfo userInfo = new UserInfo();
            this.setUserInfo(u,userInfo);
        }
        return u;
    }

    public void setUserInfo(Account user,UserInfo userInfo){
        Map<String,Object> userInfoMap = new HashMap<>();
        userInfoMap.put("username",user.getUsername());
        List<UserInfo> userInfoList = userInfoMapper.selectByMap(userInfoMap);
        for(UserInfo userInfoTemp : userInfoList){
            userInfo = userInfoTemp;
        }
        user.setUserInfo(userInfo);
    }

    public int register(Account user){
        user.setLevel(1);
        System.out.println(user.getUsername());
        System.out.println(user.getPhone());
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("username",user.getUsername());
        List<Account> accountList = userMapper.selectByMap(map);
        if(!accountList.isEmpty())return 2;//账号已存在
        if(userInfoMapper.insert(UserInfo.getUserInfo(user))==0)
            return 0;
        return userMapper.insert(user);
    }


    public int modifyPassword(String oldPassword, String newPassword) {
            UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("password",oldPassword);
            Account u = new Account();
            u.setPassword(newPassword);
            return userMapper.update(u,updateWrapper);


    }
}
