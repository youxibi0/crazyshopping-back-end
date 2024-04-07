package com.zjgsu.crazyshopping.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.crazyshopping.entity.*;
import com.zjgsu.crazyshopping.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AfterServiceService {
    @Autowired
    private AfterServiceMapper afterServiceMapper;
    @Autowired
    private OrdersMainMapper ordersMainMapper;
    @Autowired
    private Tools tools;
    @Autowired
    ImageService imageService;
    @Autowired
    AfterServiceImagesMapper afterServiceImagesMapper;
    public int addAfterService(AfterService afterService){
        OrdersMain ordersMain = ordersMainMapper.selectById(afterService.getOrdersId());
        afterService.setState("售后中");
        ordersMain.setState(10);//10 代表售后中
        UpdateWrapper<OrdersMain> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",afterService.getOrdersId());
        ordersMainMapper.update(ordersMain,updateWrapper);
        int temp = afterServiceMapper.insert(afterService);
        afterService.setId(tools.getId());
        imageService.saveImg(afterService);
        saveImgName(afterService);
        return temp;
    }
    public List<AfterService> getAllAfterService(){
        List<AfterService> afterServiceList = afterServiceMapper.selectList(null);
        for(AfterService afterService : afterServiceList){
            this.setAfterServiceImgNameList(afterService);
        }
        return afterServiceList;
    }
    public void setAfterServiceImgNameList(AfterService afterService) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("asId", afterService.getId());
        List<AfterServiceImages> afterServiceImages = afterServiceImagesMapper.selectByMap(map);
        List<String> imgList = new ArrayList<>();
        for (AfterServiceImages image : afterServiceImages) {
            imgList.add(image.getImgName());
        }
        afterService.setImgNameList(imgList);
    }
    public int doAfterService(Integer id, Integer way){
        AfterService afterService = afterServiceMapper.selectById(id);
        OrdersMain ordersMain = ordersMainMapper.selectById(afterService.getOrdersId());
        if(way==1){
            afterService.setState("退货退款");
            ordersMain.setState(8);//8代表状态为售后完成

        }
        else if(way==2){
            afterService.setState("仅退款");
            ordersMain.setState(8);
        }
        else if(way==3){
            afterService.setState("拒绝售后");
            ordersMain.setState(9);//9为拒绝售后
        }
        UpdateWrapper<AfterService> updateWrapper = new UpdateWrapper<>();
        UpdateWrapper<OrdersMain> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper1.eq("id",afterService.getOrdersId());
        ordersMainMapper.update(ordersMain,updateWrapper1);
        return afterServiceMapper.update(afterService,updateWrapper);
    }
    public void saveImgName(AfterService afterService) {
        if(afterService.getImgNameList()==null)return;
        for (String imgName : afterService.getImgNameList()
        ) {
            AfterServiceImages afterServiceImages =new AfterServiceImages(afterService.getId(),imgName);
            afterServiceImagesMapper.insert(afterServiceImages);
        }
    }
}
