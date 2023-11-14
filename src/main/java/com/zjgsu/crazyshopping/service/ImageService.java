package com.zjgsu.crazyshopping.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {
    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;
    public Resource loadImage(String img) {
        String savePath = UPLOAD_FOLDER;
        String absolutePath = null;
        try {
            File savePathFile = new File(savePath);
            absolutePath = savePathFile.getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File imageFile = new File(absolutePath + "/" +img);
        if (imageFile.exists()) {
            return new FileSystemResource(imageFile);
        } else {
            return null;
        }
    }
}
