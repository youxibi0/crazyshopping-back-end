package com.zjgsu.crazyshopping.controller;

import com.zjgsu.crazyshopping.entity.RespBean;
import com.zjgsu.crazyshopping.entity.SortOne;
import com.zjgsu.crazyshopping.entity.SortTwo;
import com.zjgsu.crazyshopping.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sort")
public class SortController {
    @Autowired
    private SortService sortService;
    @RequestMapping("")
    public List<SortOne> getAllOneSort(){
        return sortService.getAllSortOne();
    }
    @RequestMapping("/{one}")//根据一级分类名字查询二级分类
    public List<SortTwo> getSortTwo(@PathVariable String one){

        return sortService.getAllSortTwo(one);
    }
    @PostMapping("/add")
    public RespBean addSortOne(SortOne sortOne){
        if(sortService.addSortOne(sortOne)==1){
            return RespBean.ok("添加一级分类成功");
        }
        else {
            return RespBean.error("添加一级分类失败");
        }
    }
    @PutMapping("update")
    public RespBean modifySortOne(String oldOne , String newOne){
        if(sortService.modifySortOne(oldOne,newOne)==1){
            return RespBean.ok("修改一级分类成功");
        }
        else {
            return RespBean.error("修改一级分类失败");
        }

    }
}
