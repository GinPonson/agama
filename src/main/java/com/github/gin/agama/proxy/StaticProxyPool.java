package com.github.gin.agama.proxy;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author GinPonson
 * @since 4/29/2017
 */
public class StaticProxyPool extends ProxyPool{

    @Override
    public List<Proxy> dynamicProxy() {
        return new ArrayList<>();
    }
}
