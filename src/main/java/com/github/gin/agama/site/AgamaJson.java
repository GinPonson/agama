package com.github.gin.agama.site;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by FSTMP on 2016/10/27.
 */
public class AgamaJson {

    public JSONObject json;

    public AgamaJson(String rawText) {
        json = JSON.parseObject(rawText);
    }

    public String toString(){
        return JSON.toJSONString(json);
    }
}
