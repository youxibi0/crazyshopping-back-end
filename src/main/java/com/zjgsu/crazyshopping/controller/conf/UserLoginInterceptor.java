package com.zjgsu.crazyshopping.controller.conf;

import com.zjgsu.crazyshopping.entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.regex.Pattern;

public class UserLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ServerConfig serverConfig = new ServerConfig();
        int level = 2;
        try {
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("user");
            if (account != null) {
                level = account.getLevel();
            }else {
                level = 2;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String url =request.getRequestURI();
//        if(Arrays.asList("").contains(url) && Arrays.asList().contains(level)){return  true;}
        switch (request.getMethod()) {
            case "GET":
                if(isUrlAllowed(url,Arrays.asList("/user/level","/goods/all","/goods/search","/goods/**","/sort","/sort/**","/images/**"))){return  true;}
                //0,1
                if(isUrlAllowed(url,Arrays.asList("/user/logout","/orders/**")) && Arrays.asList(0,1).contains(level)){return  true;}
                //0
                if(isUrlAllowed(url,Arrays.asList("/user/all","/orders")) && Arrays.asList(0).contains(level)){return  true;}
                break;
            case "POST":
                if(isUrlAllowed(url,Arrays.asList("/user/login","/user/register"))){return  true;}
                //0
                if(isUrlAllowed(url,Arrays.asList("/goods/add","/sort/add","/sort/add2","/images/add","/images/delete"
                )) && Arrays.asList(0).contains(level)){return  true;}
                //1
                if(isUrlAllowed(url,Arrays.asList("/orders/add")) && Arrays.asList(1).contains(level)){return  true;}
                break;
            case "PUT":
                //0,1
                if(isUrlAllowed(url,Arrays.asList("/user/update","/user/modifyInfo")) && Arrays.asList(0,1).contains(level)){return  true;}
                //0
                if(isUrlAllowed(url,Arrays.asList("/goods/update","/goods/disenable/**","/sort/update","/sort/update2","/sort/goods",
                        "/orders/refuse/**","/orders/accept/**","/orders/finish/**","/orders/fail/**"))&& Arrays.asList(0).contains(level)){return  true;}
                //1
                if(isUrlAllowed(url,Arrays.asList("/user/modifyInfo")) && Arrays.asList(1).contains(level)){return  true;}
                break;
            case "DELETE":
                //0,1
                if(isUrlAllowed(url,Arrays.asList("/orders/delete/**")) && Arrays.asList(0,1).contains(level)){return  true;}
                //0
                if(isUrlAllowed(url,Arrays.asList("/sort/delete","/sort/delete2")) && Arrays.asList(0).contains(level)){return  true;}
                break;
        }
        response.sendRedirect(serverConfig.getUrlandPort()+"/#/login");
        return false;
    }
    private static boolean isUrlAllowed(String url, Iterable<String> allowedUrls) {
        for (String allowedUrl : allowedUrls) {
            // 将**替换为.*
            String regex = allowedUrl.replace("**", ".*");

            if (Pattern.matches(regex, url)) {
                // URL匹配允许的路径正则表达式
                return true;
            }
        }

        // URL不在允许的列表中
        return false;
    }

}
