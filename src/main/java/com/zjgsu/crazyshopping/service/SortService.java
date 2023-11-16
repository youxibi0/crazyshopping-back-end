package com.zjgsu.crazyshopping.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.crazyshopping.entity.RespOrderBean;
import com.zjgsu.crazyshopping.entity.SortOne;
import com.zjgsu.crazyshopping.entity.SortTwo;
import com.zjgsu.crazyshopping.mapper.SortOneMapper;
import com.zjgsu.crazyshopping.mapper.SortTwoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SortService {
    @Autowired
    private SortOneMapper sortOneMapper;
    @Autowired
    private SortTwoMapper sortTwoMapper;
    public List<SortOne> getAllSortOne(){

        return sortOneMapper.selectList(null);
    }
    public List<SortTwo> getAllSortTwo(String one){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("one",one);
        return sortTwoMapper.selectByMap(map);
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
