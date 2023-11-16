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
    @PostMapping("/add2")
    public RespBean addSortTwo(SortTwo sortTwo){
        if(sortService.addSortTwo(sortTwo)==1){
            return RespBean.ok("添加二级分类成功");
        }
        else if(sortService.addSortTwo(sortTwo)==2){
            return RespBean.ok("没有这个一级分类");
        }
        else if(sortService.addSortTwo(sortTwo)==3){
            return RespBean.ok("二级分类重名");
        }
        else {
            return RespBean.error("添加二级分类失败");
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
    @PutMapping("update2")
    public RespBean modifySortTwo(String one ,String oldTwo ,String newTwo){
        if(sortService.modifySortTwo(one,oldTwo,newTwo)==1){
            return RespBean.ok("修改二级分类成功");

        }
        else {
            return RespBean.error("修改二级分类失败");
        }
    }
    @DeleteMapping("/delete")
    public RespBean deleteSortOne(SortOne sortOne){
        if(sortService.deleteSortOne(sortOne)==1){
            return RespBean.ok("删除一级分类成功");
        }
        else if(sortService.deleteSortOne(sortOne)==2){
            return RespBean.error("此一级分类不存在");
        }
        else if(sortService.deleteSortOne(sortOne)==3){
            return RespBean.error("此二级分类不存在");
        }
        else {
            return RespBean.error("删除一级分类失败");
        }

    }
    @DeleteMapping("/delete2")
    public RespBean deleteSortTwo(SortTwo sortTwo){
        if(sortService.deleteSortTwo(sortTwo)==1){
            return RespBean.ok("删除二级分类成功");
        }
        else {
            return RespBean.error("删除二级分类失败");
        }
    }
}
