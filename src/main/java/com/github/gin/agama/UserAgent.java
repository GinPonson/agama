package com.github.gin.agama;

import com.alibaba.fastjson.JSONArray;
import com.github.gin.agama.proxy.ProxyPool;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;

/**
 * @author  GinPonson
 */
public class UserAgent {

    private static JSONArray userAgents;

    static {
        InputStream in = ProxyPool.class.getResourceAsStream("/UserAgent.json");
        try {
            String json = IOUtils.toString(in,Charset.defaultCharset());
            userAgents = JSONArray.parseArray(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String randomUserAgent(){
        Collections.shuffle(userAgents);
        return userAgents.getString(0);
    }
}
