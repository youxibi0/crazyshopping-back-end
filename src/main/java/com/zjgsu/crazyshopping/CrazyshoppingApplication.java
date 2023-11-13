package com.zjgsu.crazyshopping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zjgsu.crazyshopping.mapper")
public class CrazyshoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrazyshoppingApplication.class, args);
    }

}
