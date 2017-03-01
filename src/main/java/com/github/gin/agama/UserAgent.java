package com.github.gin.agama;

import com.alibaba.fastjson.JSONArray;
import com.google.common.io.Files;
import com.google.common.io.Resources;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;

/**
 * Created by GinPonson on 3/1/2017.
 */
public class UserAgent {

    public static JSONArray userAgents;

    static {
        URL url = Resources.getResource("UserAgent.json");
        try {
            String json  = Files.toString(new File(url.getPath()), Charset.defaultCharset());
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
