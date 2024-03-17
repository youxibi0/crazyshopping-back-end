package com.zjgsu.crazyshopping.service;

import com.zjgsu.crazyshopping.entity.AfterService;
import com.zjgsu.crazyshopping.mapper.AfterServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfterServiceService {
    @Autowired
    private AfterServiceMapper afterServiceMapper;
    public int addAfterService(AfterService afterService){
        return afterServiceMapper.insert(afterService);
    }
}
