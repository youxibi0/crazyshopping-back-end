package com.zjgsu.crazyshopping.service;

import com.zjgsu.crazyshopping.entity.AfterService;
import com.zjgsu.crazyshopping.mapper.AfterServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AfterServiceService {
    @Autowired
    private AfterServiceMapper afterServiceMapper;
    public int addAfterService(AfterService afterService){
        return afterServiceMapper.insert(afterService);
    }
    public List<AfterService> getAllAfterService(){
        return afterServiceMapper.selectList(null);
    }
}
