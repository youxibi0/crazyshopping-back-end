package com.zjgsu.crazyshopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjgsu.crazyshopping.entity.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Insert("INSERT INTO ORDERS (name, phone, time, goodsId,info)\n" +
            "VALUES(#{name}, #{phone}, #{time}, #{goodsId},'等待处理');")
    int add(Order order);

    @Delete("Delete from orders where id = #{id};")
    int delete(Integer id);

    @Update("UPDATE orders SET info = CASE WHEN id = #{id} THEN '已接受' ELSE '已拒绝' END ," +
            " isComplete = CASE WHEN id <> #{id} THEN 1 ELSE isComplete END "+
            "WHERE goodsId = (SELECT goodsId FROM orders WHERE id = #{id});")
    int acceptOrder(Integer id);

    @Update("UPDATE goods SET isFreeze = 1 WHERE id IN ( SELECT goodsId FROM orders WHERE id = #{id} );")
    int acceptOrder_goods(Integer id);

    @Update("Update orders set info = '已拒绝' , isComplete = 1 where id = #{id};")
    int refuseOrder(Integer id);


    @Select("select orders.* ,goods.img as img ,goods.name as goodsName from orders,goods where orders.goodsId = goods.id and orders.id = #{id}")
    Order selectOrderById(Integer id);

    @Select("select orders.* ,goods.img as img ,goods.name as goodsName from orders,goods where orders.goodsId = goods.id")
    List<Order> selectAllOrders();

    @Select("SELECT COUNT(*) FROM orders")
    Integer getOrdersTotal();

    @Update("UPDATE orders SET info = '已完成', isComplete = 1 WHERE id = #{id}; " )
    int finishOrder(Integer id);

    @Update("UPDATE goods SET onenable = 0,isFreeze = 0 WHERE id IN ( SELECT goodsId FROM orders WHERE id = #{id} );")
    int finishOrder_goods(Integer id);

    @Update("UPDATE orders SET  info = '交易失败',isComplete = 1 WHERE id = #{id}")
    int failOrder(Integer id);

    @Update("UPDATE goods Set isFreeze = 0,onEnable = 1 WHERE id IN ( SELECT goodsId FROM orders WHERE id = #{id} );")
    int failOrder_goods(Integer id);

}
