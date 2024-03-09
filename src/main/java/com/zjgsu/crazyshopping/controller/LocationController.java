package com.zjgsu.crazyshopping.controller;

import com.zjgsu.crazyshopping.entity.Account;
import com.zjgsu.crazyshopping.entity.Location;
import com.zjgsu.crazyshopping.entity.RespBean;
import com.zjgsu.crazyshopping.service.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {

    @Autowired
    private LocationService locationService;
    @PostMapping("/location")
    public RespBean addLocation(HttpServletRequest request, HttpServletResponse response, Location location){
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("user");
        location.setUsername(account.getUsername());
        if(locationService.addLocation(location)==1){
            return RespBean.ok("添加地址成功");
        }
        else {
            return RespBean.error("添加地址失败");
        }
    }
    @PutMapping("/location/update")
    public RespBean modifyLocation(Location newLocation){
        if(locationService.modifyLocation(newLocation)==1){
            return RespBean.ok("修改地址成功");
        }
        else {
            return RespBean.error("修改地址失败");
        }
    }


}
