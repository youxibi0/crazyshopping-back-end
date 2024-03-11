package com.zjgsu.crazyshopping.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.crazyshopping.entity.*;
import com.zjgsu.crazyshopping.mapper.*;
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
    @Autowired
    Tools tools;
    @Autowired
    CartMapper cartMapper;

    public List<OrdersMain> getAllOrder() {
        List<OrdersMain> ordersMainList = ordersMainMapper.selectList(null);
        for (OrdersMain temp : ordersMainList
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
        for (OrdersMain temp : ordersMainList
        ) {
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("ordersId", temp.getId());
            List<Order> orderList = orderMapper.selectByMap(map2);
            for(Order order : orderList){
                System.out.println(temp.getAmount());
                order.setAmount(temp.getAmount());
            }
            temp.setOrderList(orderList);
        }
        return ordersMainList;
    }

    public int addOrder(OrderRequest orderRequest) {

        for (GoodsIdList goodsId : orderRequest.getGoodsIdList()
        ) {
            OrdersMain ordersMain = new OrdersMain();
            ordersMain.setName(orderRequest.getName());
            ordersMain.setLocation(orderRequest.getLocation());
            ordersMain.setPhone(orderRequest.getPhone());
            ordersMain.setUsername(orderRequest.getUsername());
            ordersMain.setAmount(goodsId.getAmount());
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = sdf.format(now);
            ordersMain.setTime(dateString);
            ordersMain.setState(1);
            if (ordersMainMapper.insert(ordersMain) <= 0) return 0;
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", goodsId.getGoodsId());
            Goods goods = goodsMapper.selectByMap(map).get(0);
            if (goods.getNum() <= 0) return 0;


        }


        Integer ordersId = tools.getId();
        for (GoodsIdList goodsId : orderRequest.getGoodsIdList()
        ) {
            System.out.println(goodsId);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", goodsId.getGoodsId());
            Goods goods = goodsMapper.selectByMap(map).get(0);
            goodsService.setGoodsImgNameList(goods);
            Order order = new Order();
            order.setGoods(goods);
            order.setOrdersId(ordersId);
            order.setAmount(goodsId.getAmount());
            System.out.println(order);
            goodsService.subNum(goods.getId(),goodsId.getAmount());
            goodsService.checkOnenable(order.getGoodsId());
            if (orderMapper.add(order) <= 0) return 0;//数据库没有amount 所以查询的时候是0
        }

        return 1;
    }

    public int addCart(OrderRequest orderRequest) {
        if (addOrder(orderRequest) != 1) return 0;
        for (GoodsIdList goodsId : orderRequest.getGoodsIdList()
        ) {
            Map<String, Object> map = new HashMap<>();
            map.put("username", orderRequest.getUsername());
            map.put("goodsId", goodsId.getGoodsId());
            cartMapper.deleteByMap(map);
        }
        return 1;
    }

    public int deleteOrder(Integer id) {
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("id", id);
        orderMapper.deleteByordersId(id);
        if (ordersMainMapper.deleteByMap(map1) <= 0) return 0;

        return 1;
    }

    public int acceptOrder(Integer id) {
        OrdersMain ordersMain = ordersMainMapper.selectById(id);
        ordersMain.setState(3);
        UpdateWrapper<OrdersMain> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        return ordersMainMapper.update(ordersMain, updateWrapper);
    }

    public int stockup(Integer id) {
        OrdersMain ordersMain = ordersMainMapper.selectById(id);
        ordersMain.setState(4);
        UpdateWrapper<OrdersMain> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        return ordersMainMapper.update(ordersMain, updateWrapper);
    }

    public int sendGoods(Integer id) {
        OrdersMain ordersMain = ordersMainMapper.selectById(id);
        ordersMain.setState(5);
        UpdateWrapper<OrdersMain> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        return ordersMainMapper.update(ordersMain, updateWrapper);
    }

    public int refuseOrder(Integer id) {
        OrdersMain ordersMain = getOrdersMainById(id);
        ordersMain.setState(2);
        System.out.println(ordersMain);
        int temp = ordersMainMapper.updateById(ordersMain);
        for (Order order : ordersMain.getOrderList()
        ) {
            goodsService.addNum(order.getGoodsId() , order.getAmount());
            goodsService.checkOnenable(order.getGoodsId());
        }
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


    public OrdersMain getOrdersMainById(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        OrdersMain ordersMain = new OrdersMain();
        List<OrdersMain> ordersMainList = ordersMainMapper.selectByMap(map);
        if (!ordersMainList.isEmpty()) {
            ordersMain = ordersMainList.get(0);
            Map<String, Object> map2 = new HashMap<String, Object>();
            map.put("ordersId", ordersMain.getId());
            List<Order> orderList = orderMapper.selectByMap(map2);
            ordersMain.setOrderList(orderList);
        }
        return ordersMain;
    }
}
