package com.zjgsu.crazyshopping.utils;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


@Component
public class PropManager {
    public static Properties getProp(){
        Properties prop = new Properties();
        try{
            prop.load(new FileInputStream("crazyshopping.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return prop;
    }
}
