package com.github.gin.agama.proxy;

import com.alibaba.fastjson.JSONArray;
import com.google.common.io.Files;
import com.google.common.io.Resources;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author  GinPonson
 */
public class Proxys {

    private static List<Proxy> proxyList = new ArrayList<>();

    static {
        URL url = Resources.getResource("Proxy.json");
        try {
            String json = Files.toString(new File(url.getPath()), Charset.defaultCharset());
            JSONArray proxys = JSONArray.parseArray(json);
            for (int i = 0; i < proxys.size(); i++) {
                String[] array = proxys.getString(i).split("@");
                if (array.length == 2) {
                    String[] userpwd = array[0].split(":");
                    String username = userpwd[0].trim();
                    String password = userpwd[1].trim();

                    String[] hostport = array[1].split(":");
                    String host = hostport[0].trim();
                    int port = Integer.valueOf(hostport[1].trim());
                    proxyList.add(new HttpProxy(Proxy.Type.HTTP, host, port, username, password));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addProxy(Proxy httpProxy) {
        proxyList.add(httpProxy);
    }

    public static Proxy getProxy() {
        if (proxyList.isEmpty()) {
            return Proxy.NO_PROXY;
        } else {
            Collections.shuffle(proxyList);
            return proxyList.get(0);
        }
    }

}
