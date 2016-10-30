package com.github.gin.agama.site;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.github.gin.agama.annotation.Ason;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    //可用正则优化
    private void anylized(String ason){
        String prefix = ason.substring(0, ason.lastIndexOf("."));
        if(prefix.indexOf(".") != -1){
            String[] asons = prefix.split(".");
            for(String a : asons){
                json = json.getJSONObject(a);
            }
        } else {
            json = json.getJSONObject(prefix);
        }

    }

    public <T>T toEntity(Class<T> target){
        if(target.isAnnotationPresent(Ason.class)){
            String ason = target.getAnnotation(Ason.class).value();
            if(ason.indexOf(".") != -1){
                anylized(ason);
                ason = ason.substring(ason.lastIndexOf(".") + 1,ason.length());
            }
            json = json.getJSONObject(ason);
        }

        return  JSON.parseObject(json.toJSONString(), target);
    }

    public <T>List<T> toEntityList(Class<T> target){
        JSONArray jsonArray = null;
        if(target.isAnnotationPresent(Ason.class)){
            String ason = target.getAnnotation(Ason.class).value();
            if(ason.indexOf(".") != -1){
                anylized(ason);
                ason = ason.substring(ason.lastIndexOf(".") + 1,ason.length());
            }
            jsonArray = json.getJSONArray(ason);
        }

        return JSON.parseArray(jsonArray.toJSONString(), target);
    }

    public JSONObject getJson(){
        return json;
    }

}
