package com.zjgsu.crazyshopping.service;

import com.zjgsu.crazyshopping.entity.Goods;
import com.zjgsu.crazyshopping.entity.RespBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImageService {
    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;
    @Value("${server.port}")
    private String port;
    public Resource loadImage(String img) {
        String savePath = UPLOAD_FOLDER;
        String absolutePath = null;
        try {
            File savePathFile = new File(savePath);
            absolutePath = savePathFile.getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File imageFile = new File(absolutePath + "/" + img);
        if (imageFile.exists()) {
            return new FileSystemResource(imageFile);
        } else {
            return null;
        }
    }

    public ResponseEntity<Resource> getGoodsImage(String img, HttpServletResponse response, HttpServletRequest request) throws MalformedURLException {
        String projectDirectory = System.getProperty("user.dir");
        String path = projectDirectory + "\\images\\" + img;
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

    public String doImg(List<MultipartFile> imgFiles) {
        if(imgFiles==null)return "true";
        for (MultipartFile imgFile : imgFiles
        ) {
            if (imgFile.getSize() > 1024 * 1024 * 10) {
                return "单个文件大小不能大于10M";
            }
            //获取文件后缀
            String suffix = imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf(".") + 1, imgFile.getOriginalFilename().length());
            if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
                return "请选择jpg,jpeg,gif,png格式的图片";
            }
        }
        return "true";
    }

    public void saveImg(Goods goods) {
        if(goods.getImgFiles() == null) return;
        for (MultipartFile file : goods.getImgFiles()
        ) {
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
            String savePath = UPLOAD_FOLDER;
            String filename = null;
            String absolutePath = null;
            try {
                File savePathFile = new File(savePath);
                absolutePath = savePathFile.getCanonicalPath();
                File absolutePathFile = new File(absolutePath);
                if (!absolutePathFile.exists()) {
                    absolutePathFile.mkdir();
                }
                //通过UUID生成唯一文件名
                filename = UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
                //将文件保存指定目录
                file.transferTo(new File(absolutePath + "/" + filename));
            } catch (Exception e) {
                e.printStackTrace();
            }
            goods.addImgName(filename);
        }

    }

    public RespBean addImage(MultipartFile imgFile){
        if (imgFile.getSize() > 1024 * 1024 * 10) {
            return RespBean.error("单个文件大小不能大于10M");
        }
        String suffix = imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf(".") + 1, imgFile.getOriginalFilename().length());
        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
            return RespBean.error("请选择jpg,jpeg,gif,png格式的图片");
        }

        String savePath = UPLOAD_FOLDER;
        String filename = null;
        String absolutePath = null;
        try {
            File savePathFile = new File(savePath);
            absolutePath = savePathFile.getCanonicalPath();
            File absolutePathFile = new File(absolutePath);
            if (!absolutePathFile.exists()) {
                absolutePathFile.mkdir();
            }
            filename = UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
            imgFile.transferTo(new File(absolutePath + "/" + filename));
            InetAddress localhost =InetAddress.getLocalHost();
            System.out.println(localhost.getHostAddress());
            return RespBean.ok(localhost.getHostAddress().toString()+":"+port+"/images/"+filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("error");
    }
}
