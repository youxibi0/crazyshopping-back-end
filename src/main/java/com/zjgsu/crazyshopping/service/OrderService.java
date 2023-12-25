package com.zjgsu.crazyshopping.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.crazyshopping.entity.Goods;
import com.zjgsu.crazyshopping.entity.Order;
import com.zjgsu.crazyshopping.entity.OrdersMain;
import com.zjgsu.crazyshopping.entity.RespOrderBean;
import com.zjgsu.crazyshopping.mapper.GoodsImagesMapper;
import com.zjgsu.crazyshopping.mapper.GoodsMapper;
import com.zjgsu.crazyshopping.mapper.OrderMapper;
import com.zjgsu.crazyshopping.mapper.OrdersMainMapper;
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
    @Autowired
    OrdersMainMapper ordersMainMapper;

    public List<OrdersMain> getAllOrder() {
        List<OrdersMain> ordersMainList = ordersMainMapper.selectList(null);
        for (OrdersMain temp:ordersMainList
             ) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ordersId", temp.getId());
            List<Order> orderList = orderMapper.selectByMap(map);
            temp.setOrderList(orderList);
        }
        return ordersMainList;
    }

    public List<OrdersMain> getOrderByName(String username) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", username);
        List<OrdersMain> ordersMainList = ordersMainMapper.selectByMap(map);
        for (OrdersMain temp:ordersMainList
        ) {
            Map<String, Object> map2 = new HashMap<String, Object>();
            map.put("ordersId", temp.getId());
            List<Order> orderList = orderMapper.selectByMap(map2);
            temp.setOrderList(orderList);
        }
        return ordersMainList;
    }

    public int addOrder(String username, List<Integer> goodsIdList) {
        //TODO:测试
        OrdersMain ordersMain = new OrdersMain();
        for (Integer goodsId : goodsIdList
        ) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", goodsId);
            Goods goods = goodsMapper.selectByMap(map).get(0);
            if (goods.getNum() <= 0) return 0;
        }

        ordersMain.setUser(userService.getUserAndSetUserInfo(username));
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(now);
        ordersMain.setTime(dateString);
        ordersMain.setState(1);
        if(ordersMainMapper.insert(ordersMain)<=0)return 0;

        for (Integer goodsId : goodsIdList
        ) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", goodsId);
            Goods goods = goodsMapper.selectByMap(map).get(0);
            goodsService.setGoodsImgNameList(goods);
            Order order = new Order();
            order.setGoods(goods);
            goodsService.subNum(goods.getId());
            goodsService.checkOnenable(order.getGoodsId());
            if( orderMapper.insert(order)<=0)return 0;
        }

        return 1;
    }

    public int deleteOrder(Integer id) {
        return orderMapper.delete(id);
    }

    public int acceptOrder(Integer id) {
        OrdersMain ordersMain = ordersMainMapper.selectById(id);
        ordersMain.setState(3);
        UpdateWrapper<OrdersMain> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        return ordersMainMapper.update(ordersMain, updateWrapper);
    }

    public int refuseOrder(Integer id) {
        OrdersMain order = getOrderById(id);
        order.setState(2);
        int temp = orderMapper.updateById(order);
        goodsService.addNum(order.getGoodsId());
        goodsService.checkOnenable(order.getGoodsId());
        return temp;
    }

    public RespOrderBean selectAllOrders() {
        List<OrdersMain> data = orderMapper.selectAllOrders();
        Integer total = orderMapper.getOrdersTotal();
        RespOrderBean respOrderBean = new RespOrderBean();
        respOrderBean.setTotal(total);
        respOrderBean.setOrdersList(data);
        return respOrderBean;
    }

    public int finishOrder(Integer id) {
        UpdateWrapper<OrdersMain> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        OrdersMain ordersMain = new OrdersMain();
        ordersMain.setState(6);
        return ordersMainMapper.update(ordersMain, updateWrapper);
    }

    public int failOrder(Integer id) {
        UpdateWrapper<OrdersMain> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        OrdersMain order = new OrdersMain();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        List<OrdersMain> orderList = orderMapper.selectByMap(map);
        if (!orderList.isEmpty()) order = orderList.get(0);
        order.setState(5);
        goodsService.addNum(order.getGoodsId());
        goodsService.checkOnenable(order.getGoodsId());
        return orderMapper.update(order, updateWrapper);
    }

    public OrdersMain getOrderById(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        OrdersMain order = new OrdersMain();
        List<OrdersMain> orderList = orderMapper.selectByMap(map);
        if (!orderList.isEmpty()) order = orderList.get(0);
        return order;
    }
}
