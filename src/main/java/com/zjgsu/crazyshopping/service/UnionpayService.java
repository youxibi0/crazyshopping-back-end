package com.zjgsu.crazyshopping.service;

import com.unionpay.acp.demo.DemoBase;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.SDKConfig;
import com.zjgsu.crazyshopping.utils.PropManager;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UnionpayService {

    public String pay(Integer ordersId,String time,String money) {
        Map<String, String> requestData = new HashMap<>();
        Properties prop= PropManager.getProp();

        requestData.put("version", DemoBase.version);         //版本号，全渠道默认值
        requestData.put("encoding", DemoBase.encoding);       //字符集编码，可以使用UTF-8,GBK两种方式
        requestData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
        requestData.put("txnType", "01");                 //交易类型 ，01：消费
        requestData.put("txnSubType", "01");              //交易子类型， 01：自助消费
        requestData.put("bizType", "000201");             //业务类型 000202: B2B
        requestData.put("channelType", "07");             //渠道类型 固定07


        /***商户接入参数***/
        requestData.put("merId", prop.getProperty("union.merId"));      //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
        requestData.put("accessType", "0");               //接入类型，0：直连商户
        requestData.put("orderId",String.format("%08d",ordersId));               //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
        requestData.put("txnTime", time);   //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
        requestData.put("currencyCode", "156");           //交易币种（境内商户一般是156 人民币）
        requestData.put("txnAmt", money);                //交易金额，单位分，不要带小数点
        requestData.put("bizScene","100001");
        //requestData.put("accNo","123456789001");

        //前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”按钮的时候将异步通知报文post到该地址
        //如果想要实现过几秒中自动跳转回商户页面权限，需联系银联业务申请开通自动返回商户权限
        //异步通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
        requestData.put("frontUrl", prop.getProperty("union.frontUrl"));

        //后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
        //后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
        //注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码
        //    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
        //    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
        requestData.put("backUrl", prop.getProperty("union.backUrl"));


        //实现网银前置的方法：
        //上送issInsCode字段，该字段的值参考《平台接入接口规范-第5部分-附录》（全渠道平台银行名称-简码对照表）2）联系银联业务运营部门开通商户号的网银前置权限
        //requestData.put("issInsCode", "ABC");                 //发卡机构代码

        // 订单超时时间，超过此时间后，除网银交易外，其他交易银联系统会拒绝受理，提示超时。 跳转银行网银交易如果超时后交易成功，会自动退款，大约5个工作日金额返还到持卡人账户。
        // 此时间建议取支付时的北京时间加15分钟，超过超时时间调查询接口应答origRespCode不是A6或者00的就可以判断为失败。
        requestData.put("payTimeout", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime() + 15 * 60 * 1000));


        // 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现，如有需要请启用并修改自己希望透传的数据。
        // 出现部分特殊字符时可能影响解析，请按下面建议的方式填写：
        // 1. 如果能确定内容不会出现&={}[]"'等符号时，可以直接填写数据，建议的方法如下。
        //requestData.put("reqReserved", "透传信息1|透传信息2|透传信息3");

        // 2. 内容可能出现&={}[]"'符号时：
        // 1) 如果需要对账文件里能显示，可将字符替换成全角＆＝｛｝【】“‘字符（自己写代码，此处不演示）；
        // 2) 如果对账文件没有显示要求，可做一下base64（如下）。
        //    注意控制数据长度，实际传输的数据长度不能超过1024位。
        //    查询、通知等接口解析时使用new String(Base64.decodeBase64(reqReserved), DemoBase.encoding);解base64后再对数据做后续解析。
        //requestData.put("reqReserved", Base64.encodeBase64String("任意格式的信息都可以".toString().getBytes(DemoBase.encoding)));



        Map<String, String> submitFromData = AcpService.sign(requestData,DemoBase.encoding);  //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。

        String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();  //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
        String html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData,DemoBase.encoding);   //生成自动跳转的Html表单

        return html;
    }

    public String unionpayQuery(Integer ordersId,String time){

        Map<String, String> data = new HashMap<String, String>();
        Properties prop= PropManager.getProp();

        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        data.put("version", DemoBase.version);                 //版本号
        data.put("encoding", DemoBase.encoding);               //字符集编码 可以使用UTF-8,GBK两种方式
        data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
        data.put("txnType", "00");                             //交易类型 00-默认
        data.put("txnSubType", "00");                          //交易子类型  默认00
        data.put("bizType", "000201");                         //业务类型

        /***商户接入参数***/
        data.put("merId", prop.getProperty("union.merId"));                  			   //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
        data.put("accessType", "0");                           //接入类型，商户接入固定填0，不需修改

        /***要调通交易以下字段必须修改***/
        data.put("orderId", ordersId.toString());                 //****商户订单号，每次发交易测试需修改为被查询的交易的订单号
        data.put("txnTime", time);                 //****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间

        /**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/

        Map<String, String> reqData = AcpService.sign(data,DemoBase.encoding);			//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
        String url = SDKConfig.getConfig().getSingleQueryUrl();								//交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.singleQueryUrl
        Map<String, String> rspData = AcpService.post(reqData,url,DemoBase.encoding);  //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

        /**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
        //应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
        if(!rspData.isEmpty()){
            if(AcpService.validate(rspData, DemoBase.encoding)){
                //logger.info("验证签名成功");
                if(("00").equals(rspData.get("respCode"))){//如果查询交易成功
                    String origRespCode = rspData.get("origRespCode");
                    if(("00").equals(origRespCode)){
                        //交易成功，更新商户订单状态
                        return "success";
                    }else if(("03").equals(origRespCode)||
                            ("04").equals(origRespCode)||
                            ("05").equals(origRespCode)){
                        //订单处理中或交易状态未明，需稍后发起交易状态查询交易 【如果最终尚未确定交易是否成功请以对账文件为准】
                        return "订单处理中或交易状态未明，需稍后发起交易状态查询交易";
                    }else{
                        //其他应答码为交易失败
                        return "交易失败";
                    }
                }else if(("34").equals(rspData.get("respCode"))){
                    //订单不存在，可认为交易状态未明，需要稍后发起交易状态查询，或依据对账结果为准
                        return "订单不存在";
                }else{//查询交易本身失败，如应答码10/11检查查询报文是否正确
                    return "查询失败";
                }
            }else{
                //logger.error("验证签名失败");
                return "验证签名失败";
            }
        }else{
            //未返回正确的http状态
            //logger.error("未获取到返回报文或返回http状态码非200");
            return "查询失败";
        }

//        String reqMessage = DemoBase.genHtmlResult(reqData);
//        String rspMessage = DemoBase.genHtmlResult(rspData);
//         return  "交易状态查询交易</br>请求报文:<br/>"+reqMessage+"<br/>" + "应答报文:</br>"+rspMessage;
    }

    public static String getUUID(){
        //随机生成一位整数
        int random = (int) (Math.random()*9+1);
        String valueOf = String.valueOf(random);
        //生成uuid的hashCode值
        int hashCode = UUID.randomUUID().toString().hashCode();
        //可能为负数
        if(hashCode<0){
            hashCode = -hashCode;
        }
        String value = valueOf + String.format("%015d", hashCode);
        return value;
    }

}
