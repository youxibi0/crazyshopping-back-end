package com.zjgsu.crazyshopping.controller.conf;

import com.zjgsu.crazyshopping.entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        if (request.getRequestURI().equals("/orders") && request.getMethod().equals("POST")) {
            return  true;
        }


        try {
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("user");
            if (account != null) {
                return true;
            }
            System.out.println("执行了拦截器的preHandle方法");
            response.sendRedirect("http://localhost:5173/#/login");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
