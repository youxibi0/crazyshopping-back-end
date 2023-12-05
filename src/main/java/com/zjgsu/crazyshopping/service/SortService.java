package com.zjgsu.crazyshopping.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.crazyshopping.entity.*;
import com.zjgsu.crazyshopping.mapper.SortGoodsMapper;
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
    @Autowired
    private SortGoodsMapper sortGoodsMapper;
    public List<SortOne> getAllSortOne(){

        return sortOneMapper.selectList(null);
    }
    public List<SortTwo> getAllSortTwo(String one){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("one",one);
        return sortTwoMapper.selectByMap(map);
    }
    public int addSortOne(SortOne sortOne){
        Map<String,Object> map = new HashMap<>();
        map.put("one",sortOne.getOne());
        List<SortOne> sortOneList = sortOneMapper.selectByMap(map);
        if(!sortOneList.isEmpty()){
            return 2;
        }

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
        UpdateWrapper<SortTwo> sortTwoUpdateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("one",oldOne);
        sortTwoUpdateWrapper.eq("one",oldOne);
        SortOne sortOne = new SortOne(newOne);
        SortTwo sortTwo = new SortTwo();
        sortTwo.setOne(newOne);
        sortTwoMapper.update(sortTwo,sortTwoUpdateWrapper);
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
    public int deleteSortOne(SortOne sortOne){
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("one",sortOne.getOne());
        int result = sortOneMapper.deleteByMap(map);
        sortTwoMapper.deleteByMap(map);
        return result;



    }
    public int deleteSortTwo(SortTwo sortTwo){
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("one",sortTwo.getOne());
        map.put("two",sortTwo.getTwo());
        return sortTwoMapper.deleteByMap(map);
    }

    /**
     * two可为null,查询内容不存在则返回true
     * @param one
     * @param two
     * @return
     */
    public boolean checkSort(String one,String two){
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("one",one);
        map.put("two",two);
        return sortTwoMapper.selectByMap(map).isEmpty();
    }

    public RespBean UpdateSortGoods(Integer goodsId,String one,String two){
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("goodsId",goodsId);
        List<SortGoods> sortGoodsList = sortGoodsMapper.selectByMap(map);
        if(null!=sortGoodsList && !sortGoodsList.isEmpty()){
            sortGoodsMapper.deleteByMap(map);
        }
        SortGoods  sortGoods=new SortGoods();
        sortGoods.setGoodsId(goodsId);sortGoods.setOne(one);sortGoods.setTwo(two);
        if(sortGoodsMapper.insert(sortGoods)>0){
            return RespBean.ok("成功");
        }
        return RespBean.error("失败");
    }

}
