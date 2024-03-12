package com.zjgsu.crazyshopping.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.crazyshopping.entity.Location;
import com.zjgsu.crazyshopping.entity.RespBean;
import com.zjgsu.crazyshopping.mapper.LocationMapper;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    @Autowired
    private LocationMapper locationMapper;

    public int addLocation(Location location){
        return locationMapper.insert(location);
    }
    public int modifyLocation(Location newLocation){
        UpdateWrapper<Location> updateWrapper= new UpdateWrapper<>();
        updateWrapper.eq("id",newLocation.getId());
        return locationMapper.update(newLocation,updateWrapper);
    }
    public List<Location> getlocationList(){
        return locationMapper.selectList(null);
    }
    public int deleteLocationById(Integer id){
        return locationMapper.deleteById(id);
    }
    public int setDefault(Integer id){//将传入地址的isDefault设为1 将其余地址的isDefault设为0
        Location location = locationMapper.selectById(id);
        UpdateWrapper<Location> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("isDefault", 0); // 设置 isDefault 属性为 0

        locationMapper.update(null, updateWrapper);

        UpdateWrapper<Location> updateWrapper2 = new UpdateWrapper<>();
        location.setIsDefault(1);
        updateWrapper2.eq("id",location.getId());
        return locationMapper.update(location,updateWrapper2);
    }



}
