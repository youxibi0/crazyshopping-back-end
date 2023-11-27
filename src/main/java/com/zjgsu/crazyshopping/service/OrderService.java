package com.zjgsu.crazyshopping.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.crazyshopping.entity.Goods;
import com.zjgsu.crazyshopping.entity.Order;
import com.zjgsu.crazyshopping.entity.RespOrderBean;
import com.zjgsu.crazyshopping.mapper.GoodsImagesMapper;
import com.zjgsu.crazyshopping.mapper.GoodsMapper;
import com.zjgsu.crazyshopping.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    GoodsImagesMapper goodsImagesMapper;
    @Autowired
    GoodsService goodsService;
    @Autowired
    UserService userService;
    public List<Order> getAllOrder(){
        return orderMapper.selectList(null);
    }
    public int addOrder(Order order){
        Map<String,Object> map =new HashMap<String,Object>();
        map.put("id",order.getGoodsId());
        Goods goods = goodsMapper.selectByMap(map).get(0);
        goodsService.setGoodsImgNameList(goods);

        order.setUser(userService.getUserAndSetUserInfo(order.getUsername()));

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(now);
        order.setTime(dateString);

        order.setState(1);
        order.setGoods(goods);

        goodsService.addNum(goods.getId());
        return orderMapper.insert(order);
    }
    public int deleteOrder(Integer id){
        return  orderMapper.delete(id);
    }
    public int acceptOrder(@PathVariable  Integer id){
        if(orderMapper.acceptOrder_goods(id)>0 && orderMapper.acceptOrder(id)>0)
            return 1;
        return 0;
    }

    public int refuseOrder(Integer id){
        Order order = getOrderById(id);
        order.setState(2);
        int temp = orderMapper.updateById(order);
        goodsService.addNum(order.getGoodsId());
        goodsService.checkOnenable(order.getGoodsId());
        return temp;
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

    public Order getOrderById(Integer id){
        Map<String,Object> map =new HashMap<String,Object>();
        map.put("id",id);
        Order order=new Order();
        List<Order> orderList = orderMapper.selectByMap(map);
        if(!orderList.isEmpty())order=orderList.get(0);
        return order;
    }
}
