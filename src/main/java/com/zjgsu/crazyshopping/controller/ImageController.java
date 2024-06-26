package com.zjgsu.crazyshopping.controller;

import com.zjgsu.crazyshopping.entity.RespBean;
import com.zjgsu.crazyshopping.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {


    @Autowired
    private ImageService imageService;
    @GetMapping("/{img}")
    public ResponseEntity<Resource> getGoodsImage(@PathVariable String img, HttpServletResponse response, HttpServletRequest request) throws MalformedURLException {
        return imageService.getGoodsImage(img,response,request);
    }

    @PostMapping("/add")
    public RespBean addImage(MultipartFile imgFile){
        return imageService.addImage(imgFile);
    }

    @DeleteMapping("/delete")
    public RespBean deleteImage(List<String> imgNameList){
        if(imageService.deleteImgs(imgNameList))return RespBean.ok("删除成功");
        return RespBean.error("删除失败");
    }

}
