package com.zjgsu.crazyshopping.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.crazyshopping.entity.RespOrderBean;
import com.zjgsu.crazyshopping.entity.SortOne;
import com.zjgsu.crazyshopping.entity.SortTwo;
import com.zjgsu.crazyshopping.mapper.SortOneMapper;
import com.zjgsu.crazyshopping.mapper.SortTwoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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
    public int addSortTwo(SortTwo sortTwo){//先查询有没有一级分类，如果有一级分类，再检查是否有二级分类重名，如果不重名，添加二级分类
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("one",sortTwo.getOne());
        List<SortOne> sortOneList = sortOneMapper.selectByMap(map);
        HashMap<String,Object>  map2 = new HashMap<String,Object>();
        map2.put("two",sortTwo.getTwo());
        List<SortTwo> sortTwoList = sortTwoMapper.selectByMap(map2);
        System.out.println(sortOneList);
        if(sortOneList.isEmpty()){
            return 2;
        }
        else if(!sortTwoList.isEmpty()){
            return 3;
        }
        else {
            return sortTwoMapper.insert(sortTwo);
        }

    }
    public int modifySortOne(String oldOne , String newOne){
        UpdateWrapper<SortOne> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("one",oldOne);
        SortOne sortOne = new SortOne(newOne);
        return sortOneMapper.update(sortOne,updateWrapper);

    }
    public int modifySortTwo(String one , String oldTwo , String newTwo){
        UpdateWrapper<SortTwo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("one" , one);
        updateWrapper.eq("two",oldTwo);
        SortTwo sortTwo =new SortTwo();
        sortTwo.setOne(one);
        sortTwo.setTwo(newTwo);
        System.out.println(sortTwo);
        return sortTwoMapper.update(sortTwo,updateWrapper);

    }
    public int deleteSortTwo(SortTwo sortTwo){
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("one",sortTwo.getOne());
        map.put("two",sortTwo.getTwo());
        return sortTwoMapper.deleteByMap(map);
    }

}
