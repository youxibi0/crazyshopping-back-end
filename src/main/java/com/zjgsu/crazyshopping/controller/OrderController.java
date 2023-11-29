package com.zjgsu.crazyshopping.controller;

import com.zjgsu.crazyshopping.entity.Order;
import com.zjgsu.crazyshopping.entity.RespBean;
import com.zjgsu.crazyshopping.entity.RespOrderBean;
import com.zjgsu.crazyshopping.service.OrderService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping(value = "/add")
    public RespBean addOrder(Order order){
        if(orderService.addOrder(order)==1){
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @DeleteMapping(value = "/delete/{id}")
    public  RespBean delOrder(@PathVariable Integer id){
        if(orderService.deleteOrder(id)==1){
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
    @PutMapping(value = "/accept/{id}")
    public  RespBean acceptOrder(@PathVariable Integer id){
        if(orderService.acceptOrder(id)==1){
            return RespBean.ok("接受成功!");
        }
        return RespBean.error("接受失败!");
    }

    @PutMapping(value = "/refuse/{id}")
    public RespBean refuseOrder(@PathVariable Integer id){
        if(orderService.refuseOrder(id)==1)
            return  RespBean.ok("拒绝成功!");
        return  RespBean.error("拒绝失败!");
    }

    @GetMapping(value = "")
    public List<Order> selectAllOrders(){
        return orderService.getAllOrder();
    }
    @GetMapping(value = "/{username}")
    public List<Order> selectOrderByName(@PathVariable String username){
        return orderService.getOrderByName(username);
    }

    @PutMapping(value = "/finish/{id}")
    public RespBean finishOrder(@PathVariable Integer id){
        if(orderService.finishOrder(id)>0)
            return  RespBean.ok("完成订单成功!");
        return  RespBean.error("完成订单失败!");
    }

    @PutMapping(value = "/fail/{id}")
    public RespBean failOrder(@PathVariable Integer id){
        if(orderService.failOrder(id)>0)
            return  RespBean.ok("交易失败!");
        return  RespBean.error("处理出现错误!");
    }

}
