package com.zjgsu.crazyshopping.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.zjgsu.crazyshopping.entity.Order;
import com.zjgsu.crazyshopping.entity.OrdersMain;
import com.zjgsu.crazyshopping.entity.RespBean;
import com.zjgsu.crazyshopping.service.OrderService;
import com.zjgsu.crazyshopping.service.UnionpayService;
import com.zjgsu.crazyshopping.utils.AppUtil;
import com.zjgsu.crazyshopping.utils.PropManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Properties;

@RestController
public class UnionpayController {

    @Autowired
    private UnionpayService unionpayService;
    @Autowired
    OrderService orderService;


    @PostMapping("/pay")
    public String pay(Integer ordersId) throws Exception {
        OrdersMain ordersMain = orderService.getOrdersMainById(ordersId);
        String time = ordersMain.getTime();
        Double price=0.0;
        for (Order order : ordersMain.getOrderList()
        ) {
            price+=order.getAmount()*order.getGoodsPrice();
        }
        String money =String.valueOf((int)Math.floor(price*100));
        String pay = unionpayService.pay(ordersId, time, money);
        return pay;
    }

    @RequestMapping("/test")
    public String paytest() {
        String pay = unionpayService.pay(11111111, "20240306203000", "1000");
        return pay;
    }

    @PostMapping("/unionQuery")
    public RespBean unionQuery(Integer ordersId) {
        OrdersMain ordersMain=orderService.getOrdersMainById(ordersId);
        String time = ordersMain.getTime();
        String resp = unionpayService.unionpayQuery(ordersId, time);
        if (resp.equals("success")) return RespBean.ok("交易成功");
        return RespBean.error(resp);
    }

    @PostMapping("/alipay")
    public String pay(Integer ordersId, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Properties prop = PropManager.getProp();
        //获得初始化的AlipayClient

        AlipayClient alipayClient = new DefaultAlipayClient(prop.getProperty("ali.gatewayUrl"), prop.getProperty("ali.app_id"),
                prop.getProperty("ali.merchant_private_key"), "json", prop.getProperty("ali.charset"), prop.getProperty("ali.alipay_public_key"), prop.getProperty("ali.sign_type"));
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(prop.getProperty("ali.return_url"));
        alipayRequest.setNotifyUrl(prop.getProperty("ali.notify_url"));

        OrdersMain ordersMain = orderService.getOrdersMainById(ordersId);
        Double price=0.0;
        for (Order order : ordersMain.getOrderList()
        ) {
            price+=order.getAmount()*order.getGoodsPrice();
        }

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = ordersId.toString();
        //付款金额，必填
        String total_amount = price.toString();
        //订单名称，必填
        String subject = "crazyshopping的消费";
        //商品描述，可空
        String body = "";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
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
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
//        response.setContentType("text/html;charset=" + AppUtil.charset);
//
//        response.getWriter().write(form);//直接将完整的表单html输出到页面
//        response.getWriter().flush();
//        response.getWriter().close();
    }

}
