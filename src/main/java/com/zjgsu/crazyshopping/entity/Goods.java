package com.zjgsu.crazyshopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@TableName("goods")
public class Goods {
    @TableId(type = IdType.INPUT)
    private Integer id;
    private String name;
    private String info;

    private Double price;
    private Integer onEnable;
    private Integer num;
    @TableField(exist = false)
    private List<MultipartFile> imgFiles;

    @TableField(exist = false)
    private List<String> imgNameList;

    @TableField(exist = false)
    private String one;

    @TableField(exist = false)
    private String two;

    public Goods() {
    }

    public Goods(Integer id, String name, String info, Double price, Integer onEnable, Integer num, List<MultipartFile> imgFiles) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.price = price;
        this.onEnable = onEnable;
        this.num = num;
        this.imgFiles = imgFiles;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getOnEnable() {
        return onEnable;
    }

    public void setOnEnable(Integer onEnable) {
        this.onEnable = onEnable;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List<MultipartFile> getImgFiles() {
        return imgFiles;
    }

    public void setImgFiles(List<MultipartFile> imgFiles) {
        this.imgFiles = imgFiles;
    }

    public List<String> getImgNameList() {
        return imgNameList;
    }

    public void setImgNameList(List<String> imgNameList) {
        this.imgNameList = imgNameList;
    }

    public void addImgName(String name){
        if(this.imgNameList==null)this.imgNameList = new ArrayList<String>();
        this.imgNameList.add(name);
    }

    public void setNewGoods(Goods goods){
        this.name=goods.getName();
        this.price=goods.getPrice();
        this.info=goods.getInfo();
        this.num=goods.getNum();
    }
}
