package com.zjgsu.crazyshopping.controller;

import com.zjgsu.crazyshopping.entity.Account;
import com.zjgsu.crazyshopping.entity.RespBean;
import com.zjgsu.crazyshopping.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping(value = "/login")
    public RespBean login(Account user, HttpServletRequest request, HttpServletResponse response){
        Account u = userService.login(user);
        if(u.getPassword()!=null){
            request.getSession().setAttribute("user", u);
            return RespBean.ok("登录成功");
        }
        else {
            return RespBean.error("登录失败");
        }


    }

    @GetMapping(value = "/logout")
    public  RespBean logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return RespBean.ok("清除成功");
    }
    @GetMapping(value = "/isLogin")
    public RespBean isLogin(HttpServletRequest request){
        HttpSession session =request.getSession();
        if(session.getAttribute("user")!=null){
            return RespBean.ok("已登录");
        }
        return RespBean.error("未登录");
    }
    @PutMapping(value = "/update")
    public RespBean modifyPassword(String oldPassword,String newPassword){
        if(userService.modifyPassword(oldPassword,newPassword)==1){
            return RespBean.ok("修改密码成功");
        }
        else {
            return RespBean.error("修改密码失败");
        }
    }




}
