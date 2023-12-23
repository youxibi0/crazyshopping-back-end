package com.zjgsu.crazyshopping.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.crazyshopping.entity.Goods;
import com.zjgsu.crazyshopping.entity.OrdersMain;
import com.zjgsu.crazyshopping.entity.RespOrderBean;
import com.zjgsu.crazyshopping.mapper.GoodsImagesMapper;
import com.zjgsu.crazyshopping.mapper.GoodsMapper;
import com.zjgsu.crazyshopping.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<OrdersMain> getAllOrder(){
        return orderMapper.selectList(null);
    }
    public List<OrdersMain> getOrderByName(String username){
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        return orderMapper.selectByMap(map);


    }
    public int addOrder(OrdersMain order){
        Map<String,Object> map =new HashMap<String,Object>();
        map.put("id",order.getGoodsId());
        Goods goods = goodsMapper.selectByMap(map).get(0);

        if(goods.getNum()<=0)return 0;

        goodsService.setGoodsImgNameList(goods);

        order.setUser(userService.getUserAndSetUserInfo(order.getUsername()));

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(now);
        order.setTime(dateString);

        order.setState(1);
        order.setGoods(goods);

        goodsService.subNum(goods.getId());
        goodsService.checkOnenable(order.getGoodsId());
        return orderMapper.insert(order);
    }
    public int deleteOrder(Integer id){
        return  orderMapper.delete(id);
    }
    public int acceptOrder(Integer id){
        OrdersMain order = orderMapper.selectById(id);
        order.setState(3);
        UpdateWrapper<OrdersMain> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        return orderMapper.update(order,updateWrapper);
    }

    public int refuseOrder(Integer id){
        OrdersMain order = getOrderById(id);
        order.setState(2);
        int temp = orderMapper.updateById(order);
        goodsService.addNum(order.getGoodsId());
        goodsService.checkOnenable(order.getGoodsId());
        return temp;
    }
    public RespOrderBean selectAllOrders(){
        List<OrdersMain> data = orderMapper.selectAllOrders();
        Integer total = orderMapper.getOrdersTotal();
        RespOrderBean respOrderBean = new RespOrderBean();
        respOrderBean.setTotal(total);
        respOrderBean.setOrdersList(data);
        return respOrderBean;
    }
    public int finishOrder(Integer id){
        UpdateWrapper<OrdersMain> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        OrdersMain order = new OrdersMain();
        order.setState(4);
        return orderMapper.update(order,updateWrapper);
    }

    public int failOrder(Integer id){
        UpdateWrapper<OrdersMain> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        OrdersMain order = new OrdersMain();
        Map<String,Object> map =new HashMap<String,Object>();
        map.put("id",id);
        List<OrdersMain> orderList=orderMapper.selectByMap(map);
        if(!orderList.isEmpty())order = orderList.get(0);
        order.setState(5);
        goodsService.addNum(order.getGoodsId());
        goodsService.checkOnenable(order.getGoodsId());
        return orderMapper.update(order,updateWrapper);
    }

    public OrdersMain getOrderById(Integer id){
        Map<String,Object> map =new HashMap<String,Object>();
        map.put("id",id);
        OrdersMain order=new OrdersMain();
        List<OrdersMain> orderList = orderMapper.selectByMap(map);
        if(!orderList.isEmpty())order=orderList.get(0);
        return order;
    }
}
