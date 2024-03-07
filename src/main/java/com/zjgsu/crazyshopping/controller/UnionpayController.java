package com.zjgsu.crazyshopping.controller;

import com.unionpay.acp.demo.DemoBase;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.SDKConstants;
import com.zjgsu.crazyshopping.service.UnionpayService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UnionpayController {

    @Autowired
    private UnionpayService unionpayService;

    @PostMapping("/pay")
    public String pay(Integer ordersId,String time,HttpServletResponse response) throws Exception{
        String pay = unionpayService.pay(ordersId,time);
        return pay;
    }

    @RequestMapping("/test")
    public String paytest(){
        String pay = unionpayService.pay(11111111,"20240306203000");
        return pay;
    }

}
