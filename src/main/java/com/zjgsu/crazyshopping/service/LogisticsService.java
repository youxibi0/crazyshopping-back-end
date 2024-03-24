package com.zjgsu.crazyshopping.service;

import com.alibaba.fastjson.JSONObject;
import com.zjgsu.crazyshopping.entity.Logistics;
import com.zjgsu.crazyshopping.entity.RespBean;
import com.zjgsu.crazyshopping.mapper.LogisticsMapper;
import com.zjgsu.crazyshopping.utils.HttpUtils;
import com.zjgsu.crazyshopping.utils.PropManager;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogisticsService {

    @Autowired
    LogisticsMapper logisticsMapper;

    public String codeQuery(String logisticsCode) {
        String host = "https://kdzsgw.market.alicloudapi.com";
        String path = "/logistics/discern";
        String method = "POST";
        String appcode = PropManager.getProp().getProperty("logistics.appCode");
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("mailNo", logisticsCode);


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            //System.out.println(response.toString());

            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
            String result = EntityUtils.toString(response.getEntity());
            JSONObject jsonResponse =JSONObject.parseObject(result);
            if(jsonResponse.getInteger("code")==100){
                return jsonResponse.getJSONArray("data").getJSONObject(0).getString("cpCode");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public RespBean logisticsQuery(String logisticsCode,String cpCode,String phone){
        String host = "https://kdzsgw.market.alicloudapi.com";
        String path = "/logistics/search";
        String method = "POST";
        String appcode = PropManager.getProp().getProperty("logistics.appCode");
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("cpCode", cpCode);
        bodys.put("mailNo", logisticsCode);
        bodys.put("phone", phone);


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
            String result = EntityUtils.toString(response.getEntity());
            JSONObject jsonResponse =JSONObject.parseObject(result);
            if(jsonResponse.getInteger("code")!=100)return RespBean.error("服务异常");
            return RespBean.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("未知错误");
    }

    public String getLogisticsCode(Integer ordersId){
        Map<String, Object> map =new HashMap<>();
        map.put("ordersId",ordersId);
        List<Logistics> logisticsList =  logisticsMapper.selectByMap(map);
        if(logisticsList.isEmpty())return null;
        return logisticsList.get(0).getId();
    }
}
