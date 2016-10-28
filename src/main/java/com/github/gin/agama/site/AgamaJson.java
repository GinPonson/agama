package com.github.gin.agama.site;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.gin.agama.annotation.Ason;

import java.util.List;

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

    public <T>T toEntity(Class<T> target){
        if(target.isAnnotationPresent(Ason.class)){
            String ason = target.getAnnotation(Ason.class).value();
            json = json.getJSONObject(ason);
        }

        return  JSON.parseObject(json.toJSONString(),target);
    }

    public <T>List<T> toEntityList(Class<T> target){
        JSONArray jsonArray = null;
        if(target.isAnnotationPresent(Ason.class)){
            String ason = target.getAnnotation(Ason.class).value();
            jsonArray = json.getJSONArray(ason);
        }

        return JSON.parseArray(jsonArray.toJSONString(),target);
    }

    public JSONObject getJson(){
        return json;
    }
}
