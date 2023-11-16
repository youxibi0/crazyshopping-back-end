package com.zjgsu.crazyshopping.controller;

import com.zjgsu.crazyshopping.entity.SortOne;
import com.zjgsu.crazyshopping.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
