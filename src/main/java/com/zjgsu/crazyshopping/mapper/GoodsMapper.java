package com.zjgsu.crazyshopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjgsu.crazyshopping.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
    @Select("<script>" +
            "SELECT g.* FROM goods g " +
            "INNER JOIN sortGoods s ON g.id = s.goodsId " +
            "WHERE g.name LIKE #{text}" +
            "<if test='one != null and one != \"\"'> AND s.one = #{one}</if>" +
            "<if test='two != null and two != \"\"'> AND s.two = #{two}</if>" +
            "</script>")
    List<Goods> searchGoods(String text,String one,String two);
}
