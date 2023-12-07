package com.zjgsu.crazyshopping.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.crazyshopping.entity.Account;
import com.zjgsu.crazyshopping.entity.UserInfo;
import com.zjgsu.crazyshopping.mapper.UserInfoMapper;
import com.zjgsu.crazyshopping.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if(null!=u.getLevel() && u.getLevel()==1){
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

    public void updateUserSession(String username, HttpServletRequest request){
        Account account = getUserAndSetUserInfo(username);
        request.getSession().removeAttribute("user");
        request.getSession().setAttribute("user", account);
    }

    public Account getUserAndSetUserInfo(String username){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("username",username);
        List<Account> accountList = userMapper.selectByMap(map);
        Account user = new Account();
        for(Account account : accountList){
            user = account;
        }
        if(null!=user.getLevel() && user.getLevel()==1){
            UserInfo userInfo = new UserInfo();
            this.setUserInfo(user,userInfo);
        }

        return user;
    }


    public int modifyPassword(String username , String oldPassword, String newPassword) {
            Map<String,Object> map = new HashMap<>();
            map.put("username",username);
            map.put("password",oldPassword);
            List<Account> accountList = userMapper.selectByMap(map);
            if(accountList.isEmpty()){
                return 2;//用户名和对应的老密码不正确
            }
            UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("password",oldPassword);
            updateWrapper.eq("username",username);
            Account u = new Account();
            u.setPassword(newPassword);
            return userMapper.update(u,updateWrapper);


    }
    public int modifyInfo(String username, String phone , String location){
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username",username);
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(phone);
        userInfo.setLocation(location);
        return userInfoMapper.update(userInfo,updateWrapper);
    }
    public List<Account> getAllUser(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("level",1);
        List<Account> accountList = userMapper.selectByMap(map);
        List<Account> result = new ArrayList<>();
        for(Account account : accountList){
            account = this.getUserAndSetUserInfo(account.getUsername());
            result.add(account);
        }
       return result;


    }

}
