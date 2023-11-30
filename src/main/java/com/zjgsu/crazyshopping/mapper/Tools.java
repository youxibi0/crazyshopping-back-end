package com.zjgsu.crazyshopping.mapper;

import org.apache.ibatis.annotations.Select;

public interface Tools {

    @Select("SELECT last_insert_rowid();")
    public Integer getId();
}
