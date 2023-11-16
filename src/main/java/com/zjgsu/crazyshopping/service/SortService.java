package com.zjgsu.crazyshopping.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.crazyshopping.entity.RespOrderBean;
import com.zjgsu.crazyshopping.entity.SortOne;
import com.zjgsu.crazyshopping.mapper.SortOneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SortService {
    @Autowired
    private SortOneMapper sortOneMapper;
    public List<SortOne> getAllSortOne(){

        return sortOneMapper.selectList(null);
    }
    public int addSortOne(SortOne sortOne){

        return sortOneMapper.insert(sortOne);

    }
    public int modifySortOne(String oldOne , String newOne){
        UpdateWrapper<SortOne> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("one",oldOne);
        SortOne sortOne = new SortOne(newOne);
        return sortOneMapper.update(sortOne,updateWrapper);

    }

}
