package com.zjgsu.crazyshopping.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.http.HttpResponse;
import java.nio.file.Paths;

@RestController
public class Test {
    @GetMapping(value = "/a")
    public ResponseEntity<Resource> a(HttpServletResponse response) throws IOException {
        String filename = "C:/Users/碳酸/Pictures/screenshots/khl20230115143046523.png";
        // 读取图片资源
        Resource file = new UrlResource(Paths.get(filename).toUri());
        if (!file.exists() || !file.isReadable()) {
            throw new RuntimeException("无法读取图片");
        }

        // 返回图片内容
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
    }
}
