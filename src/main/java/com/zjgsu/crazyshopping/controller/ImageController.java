package com.zjgsu.crazyshopping.controller;

import com.zjgsu.crazyshopping.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.MalformedURLException;
import java.net.http.HttpResponse;
import java.nio.file.Paths;

@RestController
@RequestMapping("/images")
public class ImageController {


    @Autowired
    private ImageService imageService;
    @GetMapping("/{img}")
    public ResponseEntity<Resource> getGoodsImage(@PathVariable String img, HttpServletResponse response, HttpServletRequest request) throws MalformedURLException {
        return imageService.getGoodsImage(img,response,request);
    }
}
