package com.zjgsu.crazyshopping.controller;


import com.zjgsu.crazyshopping.entity.RespBean;
import com.zjgsu.crazyshopping.service.LogisticsService;
import com.zjgsu.crazyshopping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kd")
public class LogisticsController {
    @Autowired
    LogisticsService logisticsService;
    @Autowired
    OrderService orderService;

    @PostMapping("/query")
    public RespBean query(Integer id){
        String logisticsCode = logisticsService.getLogisticsCode(id);
        if(logisticsCode==null)return RespBean.error("此订单未发货");
        String cpCode =  logisticsService.codeQuery(logisticsCode);
        if(cpCode==null)return RespBean.error("未查到此订单");
        String phone = orderService.getOrdersMainById(id).getPhone();
        return logisticsService.logisticsQuery(logisticsCode,cpCode,phone);
    }

}
