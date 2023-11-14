package com.zjgsu.crazyshopping.service;

import com.zjgsu.crazyshopping.entity.Order;
import com.zjgsu.crazyshopping.entity.RespOrderBean;
import com.zjgsu.crazyshopping.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;
    public int addOrder(Order order){
        return orderMapper.add(order);
    }
    public int deleteOrder(Integer id){
        return  orderMapper.delete(id);
    }
    public int acceptOrder(Integer id){
        if(orderMapper.acceptOrder_goods(id)>0 && orderMapper.acceptOrder(id)>0)
            return 1;
        return 0;
    }

    public int refuseOrder(Integer id){
        return orderMapper.refuseOrder(id);
    }
    public RespOrderBean selectAllOrders(){
        List<Order> data = orderMapper.selectAllOrders();
        Integer total = orderMapper.getOrdersTotal();
        RespOrderBean respOrderBean = new RespOrderBean();
        respOrderBean.setTotal(total);
        respOrderBean.setOrdersList(data);
        return respOrderBean;
    }
    public int finishOrder(Integer id){
        if(orderMapper.finishOrder_goods(id)>0 && orderMapper.finishOrder(id)>0)
            return 1;
        return 0;
    }

    public int failOrder(Integer id){
        if(orderMapper.failOrder_goods(id)>0 && orderMapper.failOrder(id)>0)
            return 1;
        return 0;
    }
}
