package com.zjgsu.crazyshopping.service;

import com.zjgsu.crazyshopping.entity.Goods;
import com.zjgsu.crazyshopping.entity.GoodsImages;
import com.zjgsu.crazyshopping.entity.RespBean;
import com.zjgsu.crazyshopping.mapper.GoodsImagesMapper;
import com.zjgsu.crazyshopping.mapper.SortGoodsMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
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
import java.util.*;


@Service
public class ImageService {
    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;
    @Value("${server.port}")
    private String port;
    @Autowired
    SortService sortService;
    @Autowired
    GoodsImagesMapper goodsImagesMapper;
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

    public Boolean saveImg(Goods goods) {
        if(null == goods.getImgFiles() || goods.getImgFiles().isEmpty()) return false;
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
                return false;
            }
            goods.addImgName(filename);
        }
        return true;
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
            return RespBean.ok("/images/"+filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("error");
    }

    public Boolean deleteImgs(List<String> imgNameList){
        for (String imgName:imgNameList
             ) {
            String savePath = UPLOAD_FOLDER+imgName;
            String absolutePath = null;
            try {
                absolutePath = new File(savePath).getCanonicalPath();
                File absolutePathFile = new File(absolutePath);
                if(!absolutePathFile.delete())return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public String updateImg(Goods goods){

        //格式判断
        String ans =doImg(goods.getImgFiles());
        if(!ans.equals("true"))
            return ans;

        //删除本地图片
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodsId", goods.getId());
        List<GoodsImages> goodsImagesList = goodsImagesMapper.selectByMap(map);
        List<String> imgList = new ArrayList<>();
        for (GoodsImages image : goodsImagesList) {
            imgList.add(image.getImgName());
        }
        goods.setImgNameList(imgList);
        System.out.println(goods.getId());
        if(!deleteImgs(goods.getImgNameList())) return "删除失败";
        //删除数据库数据
        deleteByGoodsId(goods.getId());
        //保存图片到本地
        goods.setImgNameList(new ArrayList<>());
        if(!saveImg(goods)){
            return "删除失败";
        }
        //数据库添加数据
        for (String imgName : goods.getImgNameList()
        ) {
            GoodsImages goodsImages=new GoodsImages(goods.getId(),imgName);
            goodsImagesMapper.insert(goodsImages);
        }

        return "true";
    }

    public Integer deleteByGoodsId(Integer goodsId){
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("goodsId",goodsId);
        return goodsImagesMapper.deleteByMap(map);
    }
}
