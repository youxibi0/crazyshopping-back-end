package com.zjgsu.crazyshopping.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
    public int doAfterService(Integer id, Integer way){
        AfterService afterService = afterServiceMapper.selectById(id);
        if(way==1){
            afterService.setState("退货");
        }
        else if(way==2){
            afterService.setState("退款");
        }
        else if(way==3){
            afterService.setState("不予处理");
        }
        UpdateWrapper<AfterService> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        return afterServiceMapper.update(afterService,updateWrapper);
    }
}
