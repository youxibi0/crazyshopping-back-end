package com.zjgsu.crazyshopping.controller.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new UserLoginInterceptor());
        registration.addPathPatterns("/**"); //所有路径都被拦截
        registration.excludePathPatterns("/goods/addCart");
        registration.excludePathPatterns("/goods/addLike");
        registration.excludePathPatterns("/goods/cart");
        registration.excludePathPatterns("/goods/like");
        registration.excludePathPatterns("/goods/cart/{username}}");
        registration.excludePathPatterns("/goods/like/{username}}");
    }
}
