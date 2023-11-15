package com.zjgsu.crazyshopping.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

        @GetMapping("/{img}")
        public ResponseEntity<Resource> getGoodsImage(@PathVariable String img, HttpServletResponse response, HttpServletRequest request) throws MalformedURLException {
            String projectDirectory = System.getProperty("user.dir");
            String path = projectDirectory+"\\images\\"+img;
            System.out.println(path);
            Resource file = new UrlResource(Paths.get(path).toUri());
            if (!file.exists() || !file.isReadable()) {
                throw new RuntimeException("无法读取图片");
            }
            // 返回图片内容
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(file);
    }
}
