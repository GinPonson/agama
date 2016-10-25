package com.github.gin.agama.proxy;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by FSTMP on 2016/10/24.
 */
public class ProxyPool {

    private static List<Proxy> proxypool = new ArrayList<>();

    public static void addProxy(Proxy httpProxy){
        proxypool.add(httpProxy);
    }

    public static Proxy getProxy(){
        if(proxypool.isEmpty()){
            return Proxy.NO_PROXY;
        } else {
            Collections.shuffle(proxypool);
            return proxypool.get(0);
        }
    }

}
