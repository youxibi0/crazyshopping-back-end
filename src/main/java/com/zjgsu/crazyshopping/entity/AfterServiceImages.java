package com.zjgsu.crazyshopping.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("afterserviceimages")
public class AfterServiceImages {
    private Integer asId;
    private String imgName;

    public AfterServiceImages() {

    }

    public AfterServiceImages(Integer asId, String imgName) {
        this.asId = asId;
        this.imgName = imgName;
    }

    public Integer getAsId() {
        return asId;
    }

    public void setAsId(Integer asId) {
        this.asId = asId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    @Override
    public String toString() {
        return "AfterServiceImages{" +
                "asId=" + asId +
                ", imgName='" + imgName + '\'' +
                '}';
    }
}
