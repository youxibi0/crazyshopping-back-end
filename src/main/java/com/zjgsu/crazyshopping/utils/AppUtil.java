package com.zjgsu.crazyshopping.utils;

import java.io.FileWriter;
import java.io.IOException;

public class AppUtil {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "9021000135636416";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCd+O2DPX1PknK6cYDNOrfDDbRKC9XTPHMEcDex+zwM6Wx7fG3sSjR33QrxybY24zRWW6KihMkF2Hu2wD8NCcBzOhI2YuQfr9d/H+pf5CpYeCdyKAScdAeDvw//EnefrPszHBu43CbD7mmS4sy6qwj+CCQd18bZxSyk2aHupprEHtJR2UPvUwPTfsYZwKQ837AprOuHeq1Fz/b2vrU2PQBi6DwUWcuYC72MD94MnuW7s50NqbOZWWsAkMGvqa4zHhZDvdcB9hglMvdznsujG0yaRPf4a3vb/HoACV7E0h4ICH37frAGXVZlQZAFX+JkwsfkykMYThJnpX1j9H4aaBUZAgMBAAECggEAOqmjAdaGv1Lh+eyGaTAB55QiPFES0YOkLqTBef2S+jSAjYbShymzPEPAebNvUAileeBDj9iBkgMnReE0SNIzPlb1Rm8Iyd+RxeqDLaLcOsg71b+YnsTTuY/tD8q603f4PK2Wedp/JslVOS+x2SwEhdleGBr2BA6dmoYbBTWnSWaVzXxgCFNy5nmJqC4at0Hs3gSr59vAPWxdJP/WPaWbpEbolDZMw7MupnJavmzLURZ7SFJlO+C8bZvQUdhjuH7Dgwfl5kwiMhq71jDGO/pfchpFiM2bt4AmSsO2HeZIrjs0lLjQUAMa7j2gaNYaWk04xl8EsDjz9LSdxJmbj7bB0QKBgQDoD2qadDFOhxunBu0DL7JuIlgpGpih8eYW9aK8P8m0fu3RPfGWtp8H9I88wBoBWOnoIRg4SULaa4+EGFK/qHhrWRfSeEzWkBQo9slqYTgHa5hVAUGgWS/AtrYT7FbQGlkWnDcpb9/S4ij8vyT6Xs7Lrwf+GadQxL46xt4Bthl4HQKBgQCuRORPqaboTj6Tvgkr9gnxx7I7VdGJ+WmfMXRhwg6Yas3x1i1KQ/+Xq1Sl9gwt4hR7bfR1k78V+tt/0ng/+wmDeP9QXexHH50ecWyuIe9vrIa1gnbyCpnV0JQBmy72UjSg4U+Hwd24g3VTHUONCl1v3spOLN1unR9Xo4WAV/RYLQKBgG5or1KvR6pSE/EDFY83iVBnHMTpt4cNfWidFAE25DPxVBsCusf3OXDihafkyjqr4BYmcMyO5rTfChhSLkSr1idU60WjIQPQ5xaaXDg16x90rLR89YliBZtOExElmCRI9OwSBRi89qPyIIgoNWK96uvz6fyWngmKoet7JLNxvWptAoGAZtiHeTAhLZYHvj93wDb61XLKD9RsziWgnO+dxJe272KQ2flRTzPkIoldcnhL5cQ33EQe8kjYi8s5QS1gBDpN+gYtfyt+5taUbkP4JKgoW1qRQYuWUO/Kq/HldzEzPPF018FAo1jZVixW0E9D73e/fucXs50srTwTJDB/+CdwL50CgYEAkukZSP52upqxGsZjWfwhOy2POYRnLF7U8WKXYb53vwel8noRg2If2kc/LVGdEoitCznYoIDhybjxoxDDDMPe+JEkHVaZe1h3XzYhRUndpaP3m9zIFWbij0/v97BU+98VKVn3+fcAdjxQlE5b2vcW1/0xTYZFZ0v/047H0n3/M64=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoex/TPpIOnxuRLpDn9ykcKwVJrDQiGKynzoi1FEiifs+RreymN/4sKDGhlr3Qk3TQiTDfnvmTsnNoURyB962n/2y6jWOYS0+mKgKWTQohoWIPRsdzYDhc+4MUCRUjXuiiLaoTzRUM0eJEza79lOqncWpwHp2OIACqvBb4PGsRCqPv7OATW98K2G0XqZjeNrQf8dumcuUKXWWu7uY13eK3hEGdqsY7QHbIJR5OyymJoaDsUhoeULG/bSWYC+0quAbTAGHU2+l2mbuK8kJbe5e47DUpZK2oCuQC3rED8KvheXBLQtms0upJkG6Yh7FyCqOh+vEEJX1lgaY69AYdcsEPQIDAQAB";
    // 服务器异步通知页面路径
    //需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://fwnytt.natappfree.cc/getnotify";

    // 页面跳转同步通知页面路径
    //需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://fwnytt.natappfree.cc/getreturn";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关,注意这些使用的是沙箱的支付宝网关，与正常网关的区别是多了dev
    public static String gatewayUrl = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

    public static String foramt="json";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
