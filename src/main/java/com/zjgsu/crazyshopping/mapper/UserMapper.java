package com.zjgsu.crazyshopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjgsu.crazyshopping.entity.Account;
import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface UserMapper extends BaseMapper<Account> {
    
    }

