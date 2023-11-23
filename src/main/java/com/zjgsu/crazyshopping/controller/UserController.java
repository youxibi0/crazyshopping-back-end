package com.zjgsu.crazyshopping.controller;

import com.zjgsu.crazyshopping.entity.Account;
import com.zjgsu.crazyshopping.entity.RespBean;
import com.zjgsu.crazyshopping.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping(value = "/login")
    public RespBean login(Account user, HttpServletRequest request, HttpServletResponse response) {
        if(request.getSession().getAttribute("user")!=null)
            return RespBean.error("已经登录");
        Account u = userService.login(user);
        if (u.getPassword() != null) {
           request.getSession().setAttribute("user", u);
            return RespBean.ok("登录成功");
        } else {
            return RespBean.error("登录失败");
        }


    }

    @GetMapping(value = "/logout")
    public RespBean logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return RespBean.ok("退出登录成功");
    }

    @GetMapping(value = "/level")
    public RespBean isLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");
        if (user != null) {
            if (user.getLevel() == 0)
                return RespBean.ok("0",user);
            if (user.getLevel() == 1)
                return RespBean.ok("1",user);
        }
        else return RespBean.ok("2");
        return RespBean.error("error");
    }

    @PostMapping(value = "/register")
    public RespBean register(Account user){
        int resp = userService.register(user);
        if(resp == 1)return RespBean.ok("注册成功");
        else if(resp==2)return RespBean.error("账号已存在");
        else return  RespBean.error("注册失败");
    }

    @PutMapping(value = "/update")
    public RespBean modifyPassword(String username, String oldPassword, String newPassword) {
        if (userService.modifyPassword(username,oldPassword, newPassword) == 1) {
            return RespBean.ok("修改密码成功");
        }
        else if(userService.modifyPassword(username,oldPassword,newPassword)==2){

            return RespBean.error("用户名或老密码错误");
        }
        else {
            return RespBean.error("修改密码失败");
        }
    }
    @PutMapping(value = "/modifyInfo")
    public RespBean modifyInfo(String username , String phone , String location){
        if(userService.modifyInfo(username,phone,location)==1){
            return RespBean.ok("修改信息成功");
        }
        else {
            return RespBean.error("修改信息失败");
        }
    }
    @GetMapping(value = "/all")
    public List<Account> getAllUser(){
        return userService.getAllUser();
    }


}
