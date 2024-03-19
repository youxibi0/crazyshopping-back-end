package com.zjgsu.crazyshopping.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.SDKConstants;
import com.zjgsu.crazyshopping.entity.*;
import com.zjgsu.crazyshopping.mapper.OrdersMainMapper;
import com.zjgsu.crazyshopping.service.OrderService;
import com.zjgsu.crazyshopping.utils.AppUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrdersMainMapper ordersMainMapper;
    @PostMapping(value = "/add")
    public RespBean addOrder(@RequestBody OrderRequest orderRequest){
        int tmp = orderService.addOrder(orderRequest);
        if(tmp!=0){
            return RespBean.ok("添加成功!",tmp);
        }
        return RespBean.error("添加失败!");
    }

    @PostMapping(value = "/addCart")
    public RespBean addCart(@RequestBody OrderRequest orderRequest){
        if(orderService.addCart(orderRequest)==1){
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @DeleteMapping(value = "/delete/{id}")
    public  RespBean delOrder(@PathVariable Integer id){
        if(orderService.deleteOrder(id)==1){
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
    @PutMapping(value = "/accept/{id}")
    public  RespBean acceptOrder(@PathVariable Integer id){
        if(orderService.acceptOrder(id)==1){
            return RespBean.ok("接受成功!");
        }
        return RespBean.error("接受失败!");
    }
    @PutMapping(value = "/stockup")
    public  RespBean stockup(Integer id){
        if(orderService.stockup(id)==1){
            return RespBean.ok("备货完成!");
        }
        return RespBean.error("失败!");
    }
    @PutMapping(value = "/sendGoods")
    public  RespBean sendGoods(Integer id){
        if(orderService.sendGoods(id)==1){
            return RespBean.ok("开始发货!");
        }
        return RespBean.error("失败!");
    }

    @PutMapping(value = "/refuse/{id}")
    public RespBean refuseOrder(@PathVariable Integer id){
        if(orderService.refuseOrder(id)==1)
            return  RespBean.ok("拒绝成功!");
        return  RespBean.error("拒绝失败!");
    }

    @GetMapping(value = "")
    public List<OrdersMain> selectAllOrders(){
        return orderService.getAllOrder();
    }
    @GetMapping(value = "/{username}")
    public List<OrdersMain> selectOrderByName(@PathVariable String username){
        return orderService.getOrderByName(username);
    }

    /**
     * 完成订单
     * @param id
     * @return
     */
    @PutMapping(value = "/finish/{id}")
    public RespBean finishOrder(@PathVariable Integer id){
        if(orderService.finishOrder(id)==1)
            return  RespBean.ok("完成订单成功!");
        return  RespBean.error("完成订单失败!");
    }

    @PostMapping("/getById")
    public OrdersMain getOrderById(Integer id){
        return orderService.getOrdersMainById(id);
    }

    @PostMapping("/payUpdate")
    public void payUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //logger.info("BackRcvResponse接收后台通知开始");

        String encoding = req.getParameter(SDKConstants.param_encoding);
        // 获取银联通知服务器发送的后台通知参数
        Map<String, String> reqParam = getAllRequestParam(req);

        //logger.info(reqParam);
        //重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
        if (!AcpService.validate(reqParam, encoding)) {
            //logger.info("验证签名结果[失败].");
            //验签失败，需解决验签问题

        } else {
            //logger.info("验证签名结果[成功].");
            //交易成功，更新商户订单状态

            String orderId_string =reqParam.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
            String respCode = reqParam.get("respCode");
            //判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
            Integer orderId = Integer.parseInt(orderId_string);
            OrdersMain ordersMain = ordersMainMapper.selectById(orderId);
            ordersMain.setState(1);
            UpdateWrapper<OrdersMain> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", orderId);
            ordersMainMapper.update(ordersMain,updateWrapper);
        }
        //logger.info("BackRcvResponse接收后台通知结束");
        //返回给银联服务器http 200状态码
        resp.getWriter().print("ok");
    }
    public static Map<String, String> getAllRequestParam(
            final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (res.get(en) == null || "".equals(res.get(en))) {
                    // System.out.println("======为空的字段名===="+en);
                    res.remove(en);
                }
            }
        }
        return res;
    }

    @PostMapping("/alipayUpdate")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                // System.out.println(name + " = " + request.getParameter(name));
            }

            String tradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");

            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, AppUtil.alipay_public_key, "UTF-8"); // 验证签名
            // 支付宝验签
            if (checkSignature) {
                String orderId =tradeNo;

                OrdersMain ordersMain = ordersMainMapper.selectById(orderId);
                ordersMain.setState(1);
                UpdateWrapper<OrdersMain> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", orderId);
                ordersMainMapper.update(ordersMain,updateWrapper);
            }
        }
        return "success";
    }

}
