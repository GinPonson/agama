package com.github.gin.agama.proxy;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author GinPonson
 */
public abstract class ProxyPool {

    private List<Proxy> staticProxyList = new ArrayList<>();

    private List<Proxy> dynamicProxyList = new ArrayList<>();

    private boolean enable;

    private long refreshTime = 1000 * 60;

    private long timeOut;

    private List<Proxy> staticProxy(){
        List<Proxy> proxyList = new ArrayList<>();
        InputStream in = this.getClass().getResourceAsStream("/Proxy.json");
        try {
            String json = IOUtils.toString(in, Charset.defaultCharset());
            JSONArray proxys = JSONArray.parseArray(json);
            for (int i = 0; i < proxys.size(); i++) {
                String[] array = proxys.getString(i).split("@");
                if (array.length == 2) {
                    String[] userPwd = array[0].split(":");
                    String username = userPwd[0].trim();
                    String password = userPwd[1].trim();

                    String[] hostPort = array[1].split(":");
                    String host = hostPort[0].trim();
                    int port = Integer.valueOf(hostPort[1].trim());
                    proxyList.add(new HttpProxy(Proxy.Type.HTTP, host, port, username, password));
                }

                if (array.length == 1) {
                    String[] hostPort = array[0].split(":");
                    String host = hostPort[0].trim();
                    int port = Integer.valueOf(hostPort[1].trim());
                    proxyList.add(new HttpProxy(Proxy.Type.HTTP, host, port));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return proxyList;
    }

    public abstract List<Proxy> dynamicProxy();

    public Proxy getProxy() {
        if(!isEnable()) {
            return Proxy.NO_PROXY;
        }

        if (timeOut - System.currentTimeMillis() < 0) {
            dynamicProxyList.clear();
        }

        if(staticProxyList.isEmpty()){
            dynamicProxyList.addAll(staticProxy());
        }

        if (dynamicProxyList.isEmpty()) {
            dynamicProxyList.addAll(dynamicProxy());
            this.timeOut = System.currentTimeMillis() + refreshTime;
        }

        List<Proxy> proxyList = new ArrayList<>();
        proxyList.addAll(staticProxyList);
        proxyList.addAll(dynamicProxyList);

        if(proxyList.isEmpty()){
            return Proxy.NO_PROXY;
        } else {
            Collections.shuffle(proxyList);
            return proxyList.get(0);
        }
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public long getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(long refreshTime) {
        this.refreshTime = refreshTime;
    }
}
