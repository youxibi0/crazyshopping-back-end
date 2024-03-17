package com.zjgsu.crazyshopping.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayOpenPublicTemplateMessageIndustryModifyRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayOpenPublicTemplateMessageIndustryModifyResponse;
import com.unionpay.acp.demo.DemoBase;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.SDKConstants;
import com.zjgsu.crazyshopping.entity.RespBean;
import com.zjgsu.crazyshopping.service.UnionpayService;
import com.zjgsu.crazyshopping.utils.AppUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UnionpayController {

    @Autowired
    private UnionpayService unionpayService;




    @PostMapping("/pay")
    public String pay(Integer ordersId,String time,String money,HttpServletResponse response) throws Exception{
        String pay = unionpayService.pay(ordersId,time,money);
        return pay;
    }

    @RequestMapping("/test")
    public String paytest(){
        String pay = unionpayService.pay(11111111,"20240306203000","1000");
        return pay;
    }

    @PostMapping("/unionQuery")
    public RespBean unionQuery(Integer ordersId, String time){
        String resp =  unionpayService.unionpayQuery(ordersId, time);
        if(resp.equals("success"))return RespBean.ok("交易成功");
        return RespBean.error(resp);
    }

    @RequestMapping("/alipay")
    public void pay(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

//获得初始化的AlipayClient

        AlipayClient alipayClient = new DefaultAlipayClient(AppUtil.gatewayUrl, AppUtil.app_id, AppUtil.merchant_private_key, "json", AppUtil.charset, AppUtil.alipay_public_key, AppUtil.sign_type);
        //设置请求参数

        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AppUtil.return_url);
        alipayRequest.setNotifyUrl(AppUtil.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = "123456";
        //付款金额，必填
        String total_amount = "1000";
        //订单名称，必填
        String subject = "购买";
        //商品描述，可空
        String body = "";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //      + "\"total_amount\":\""+ total_amount +"\","
        //      + "\"subject\":\""+ subject +"\","
        //      + "\"body\":\""+ body +"\","
        //      + "\"timeout_express\":\"10m\","
        //      + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        response.setContentType("text/html;charset=" + AppUtil.charset);

        response.getWriter().write(form);//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }

}
