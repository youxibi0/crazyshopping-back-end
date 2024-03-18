package com.zjgsu.crazyshopping.controller;

import com.zjgsu.crazyshopping.entity.AfterService;
import com.zjgsu.crazyshopping.entity.RespBean;
import com.zjgsu.crazyshopping.service.AfterServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AftersServiceController {
    @Autowired
    private AfterServiceService afterServiceService;
    @PostMapping("/afterservice/add")
    public RespBean addAfterService(AfterService afterService){
        afterService.setState("处理中");
        if(afterServiceService.addAfterService(afterService)==1){
            return RespBean.ok("发起售后成功");
        }
        else {
            return RespBean.error("发起售后失败");
        }
    }
    @GetMapping("/afterservice/all")
    public List<AfterService> getAllAfterService(){
        return afterServiceService.getAllAfterService();
    }

}
