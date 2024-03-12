package com.zjgsu.crazyshopping.controller;

import com.zjgsu.crazyshopping.entity.Account;
import com.zjgsu.crazyshopping.entity.Location;
import com.zjgsu.crazyshopping.entity.RespBean;
import com.zjgsu.crazyshopping.service.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LocationController {

    @Autowired
    private LocationService locationService;
    @PostMapping("/location/add")
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
    @GetMapping("/location")
    public List<Location> getAllLocation(){
        return locationService.getlocationList();
    }
    @DeleteMapping("/location/delete/{id}")
    public RespBean deleteLocationById(@PathVariable Integer id){
        if(locationService.deleteLocationById(id)==1){
            return RespBean.ok("删除地址成功");

        }
        else {
            return RespBean.error("删除地址失败");
        }
    }
    @PutMapping("/location/set/{id}")
    public RespBean setDefault(@PathVariable Integer id){
        if(locationService.setDefault(id)==1){
            return RespBean.ok("设为默认地址成功");
        }
        else {
            return RespBean.error("设为默认地址失败");
        }
    }


}
