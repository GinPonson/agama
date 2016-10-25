package com.github.gin.agama.proxy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by FSTMP on 2016/10/24.
 */
public class ProxyPool {

    private static List<HttpProxy> proxypool = new ArrayList<>();

    public static void addProxy(HttpProxy httpProxy){
        proxypool.add(httpProxy);
    }

    public static HttpProxy getProxy(){
        if(!proxypool.isEmpty()){
            Collections.shuffle(proxypool);
            return proxypool.get(0);
        }
        return null;
    }

}
