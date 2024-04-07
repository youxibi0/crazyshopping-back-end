package com.zjgsu.crazyshopping.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.crazyshopping.entity.*;
import com.zjgsu.crazyshopping.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    LogisticsMapper logisticsMapper;

    public List<OrdersMain> getAllOrder() {
        List<OrdersMain> ordersMainList = ordersMainMapper.selectList(null);
        fetchOrderListForOrdersMain(ordersMainList);
        return ordersMainList;
    }

    public List<OrdersMain> getOrderByName(String username) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        List<OrdersMain> ordersMainList = ordersMainMapper.selectByMap(map);
        fetchOrderListForOrdersMain(ordersMainList);
        return ordersMainList;
    }

    private void fetchOrderListForOrdersMain(List<OrdersMain> ordersMainList) {
        for (OrdersMain temp : ordersMainList) {
            Map<String, Object> map = new HashMap<>();
            map.put("ordersId", temp.getId());
            List<Order> orderList = orderMapper.selectByMap(map);
            temp.setOrderList(orderList);
        }
    }

    private OrdersMain createOrdersMain(OrderRequest orderRequest) {
        OrdersMain ordersMain = new OrdersMain();
        ordersMain.setName(orderRequest.getName());
        ordersMain.setLocation(orderRequest.getLocation());
        ordersMain.setPhone(orderRequest.getPhone());
        ordersMain.setUsername(orderRequest.getUsername());
        ordersMain.setTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        ordersMain.setState(7);
        return ordersMain;
    }
    private Order createOrder(Integer ordersId, Goods goods, int amount) {
        Order order = new Order();
        order.setGoods(goods);
        order.setOrdersId(ordersId);
        order.setAmount(amount);
        return order;
    }
    private Goods getGoodsById(Integer goodsId) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", goodsId);
        return goodsMapper.selectByMap(map).get(0);
    }

    @Transactional(rollbackFor = Exception.class)
    public int addOrder(OrderRequest orderRequest) {

        Integer ordersId = tools.getId();

        for (GoodsIdList goodsId : orderRequest.getGoodsIdList()) {
            Goods goods = getGoodsById(goodsId.getGoodsId());
            if (goods.getNum() <= 0) return 0;
            goodsService.setGoodsImgNameList(goods);
            Order order = createOrder(ordersId, goods, goodsId.getAmount());
            goodsService.subNum(goods.getId(), goodsId.getAmount());
            goodsService.checkOnenable(goods.getId());
            if (orderMapper.add(order) <= 0) return 0;
        }
        OrdersMain ordersMain = createOrdersMain(orderRequest);
        if (ordersMainMapper.insert(ordersMain) <= 0) return 0;
        return ordersId;
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
    public int updateOrderState(Integer id, int state) {
        OrdersMain ordersMain = ordersMainMapper.selectById(id);
        ordersMain.setState(state);
        UpdateWrapper<OrdersMain> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        return ordersMainMapper.update(ordersMain, updateWrapper);
    }


    public int acceptOrder(Integer id) {
        return updateOrderState(id,3);
    }

    public int stockup(Integer id) {
        return updateOrderState(id,4);
    }

    @Transactional(rollbackFor = Exception.class)
    public int sendGoods(Integer id,String logisticsId) {
        Logistics logistics = new Logistics();
        logistics.setId(logisticsId);
        logistics.setOrdersId(id);
        logisticsMapper.insert(logistics);
        return updateOrderState(id,5);
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
            map2.put("ordersId", ordersMain.getId());
            List<Order> orderList = orderMapper.selectByMap(map2);
            ordersMain.setOrderList(orderList);
        }
        return ordersMain;
    }
}
