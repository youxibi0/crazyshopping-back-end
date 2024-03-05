package com.zjgsu.crazyshopping.controller;

import com.zjgsu.crazyshopping.entity.*;
import com.zjgsu.crazyshopping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping(value = "/add")
    public RespBean addOrder(@RequestBody OrderRequest orderRequest){
        System.out.println(orderRequest);
        if(orderService.addOrder(orderRequest)==1){
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @PostMapping(value = "/addCart")
    public RespBean addCart(@RequestBody OrderRequest orderRequest){
        if(orderService.addCart(orderRequest)==1){
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
    @PutMapping(value = "/stockup")
    public  RespBean stockup(Integer id){
        if(orderService.stockup(id)==1){
            return RespBean.ok("备货完成!");
        }
        return RespBean.error("失败!");
    }
    @PutMapping(value = "/sendGoods")
    public  RespBean sendGoods(Integer id){
        if(orderService.sendGoods(id)==1){
            return RespBean.ok("开始发货!");
        }
        return RespBean.error("失败!");
    }

    @PutMapping(value = "/refuse/{id}")
    public RespBean refuseOrder(@PathVariable Integer id){
        if(orderService.refuseOrder(id)==1)
            return  RespBean.ok("拒绝成功!");
        return  RespBean.error("拒绝失败!");
    }

    @GetMapping(value = "")
    public List<OrdersMain> selectAllOrders(){
        return orderService.getAllOrder();
    }
    @GetMapping(value = "/{username}")
    public List<OrdersMain> selectOrderByName(@PathVariable String username){
        return orderService.getOrderByName(username);
    }

    /**
     * 完成订单
     * @param id
     * @return
     */
    @PutMapping(value = "/finish/{id}")
    public RespBean finishOrder(@PathVariable Integer id){
        if(orderService.finishOrder(id)==1)
            return  RespBean.ok("完成订单成功!");
        return  RespBean.error("完成订单失败!");
    }


}
