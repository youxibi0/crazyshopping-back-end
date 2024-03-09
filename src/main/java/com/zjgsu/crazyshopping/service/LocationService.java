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



}
