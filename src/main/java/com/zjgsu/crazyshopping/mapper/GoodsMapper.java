package com.zjgsu.crazyshopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjgsu.crazyshopping.entity.Goods;
import com.zjgsu.crazyshopping.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
    @Insert("INSERT INTO GOODS (name, info, img, price)\n" +
            "VALUES(#{name}, #{info}, #{img}, #{price});")
    int add(Goods goods);
}
