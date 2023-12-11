package com.zjgsu.crazyshopping.controller.conf;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Component
public class ServerConfig{
    private String serverPort = "5173";

    public String getIp(){
        String savePath = "front-ip.txt";
        String absolutePath = null;
        String port = null;
        try {
            File savePathFile = new File(savePath);
            absolutePath = savePathFile.getCanonicalPath();
            File absolutePathFile = new File(absolutePath);
            FileReader reader = new FileReader(absolutePathFile);
            BufferedReader bufferedReader = new BufferedReader(reader);
            port = bufferedReader.readLine();
            bufferedReader.close();
            return port;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public String getUrlandPort() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "http://"+address.getHostAddress() +":"+this.getIp();
    }

    public List<String> getAllUrlandPort() {
        List<String> list = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue; // 跳过回环接口和未启用的接口
                }

                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    list.add("http://"+addr.getHostAddress()+":"+this.getIp());
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return list;
    }


    @PostConstruct
    public static void init(){
        String savePath = "front-ip.txt";
        String absolutePath = null;
        try {
            File savePathFile = new File(savePath);
            absolutePath = savePathFile.getCanonicalPath();
            File absolutePathFile = new File(absolutePath);
            if (!absolutePathFile.exists()) {
                absolutePathFile.createNewFile();
                FileWriter writer = new FileWriter(absolutePathFile);
                writer.write("http://localhost:5173");
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}