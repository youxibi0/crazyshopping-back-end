package com.zjgsu.crazyshopping.service;

import com.zjgsu.crazyshopping.entity.Location;
import com.zjgsu.crazyshopping.entity.RespBean;
import com.zjgsu.crazyshopping.mapper.LocationMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    @Autowired
    private LocationMapper locationMapper;

    public int addLocation(Location location){
        return locationMapper.insert(location);
    }

}
