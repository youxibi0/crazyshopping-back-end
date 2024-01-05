package com.zjgsu.crazyshopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjgsu.crazyshopping.entity.Order;
import com.zjgsu.crazyshopping.entity.OrdersMain;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Insert("INSERT INTO ORDERS (ordersId,goodsId,  goodsInfo, goodsPrice, goodsName, imgName)\n" +
            "VALUES(#{ordersId},#{goodsId}, #{goodsInfo}, #{goodsPrice}, #{goodsName},#{imgName});")
    int add(Order order);

    @Delete("Delete from orders where ordersId = #{id};")
    int deleteByordersId(Integer id);

//    @Update("UPDATE orders SET info = CASE WHEN id = #{id} THEN '已接受' ELSE '已拒绝' END ," +
//            " isComplete = CASE WHEN id <> #{id} THEN 1 ELSE isComplete END "+
//            "WHERE goodsId = (SELECT goodsId FROM orders WHERE id = #{id});")
    @Update("UPDATE orders SET info = '已接受',isComplete = 1 WHERE id = #{id}")
    int acceptOrder(Integer id);

    @Update("UPDATE goods SET isFreeze = 1 WHERE id IN ( SELECT goodsId FROM orders WHERE id = #{id} );")
    int acceptOrder_goods(Integer id);

    @Update("Update orders set state = 2  where id = #{id};")
    int refuseOrder(Integer id);

    @Select("select orders.* ,goods.img as img ,goods.name as goodsName from orders,goods where orders.goodsId = goods.id and orders.id = #{id}")
    OrdersMain selectOrderById(Integer id);

    @Select("select orders.* ,goods.img as img ,goods.name as goodsName from orders,goods where orders.goodsId = goods.id")
    List<OrdersMain> selectAllOrders();

    @Select("SELECT COUNT(*) FROM orders")
    Integer getOrdersTotal();


}
