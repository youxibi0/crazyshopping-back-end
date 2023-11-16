package com.zjgsu.crazyshopping.service;

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

}
