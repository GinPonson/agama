package com.github.gin.agama.util;

import com.github.gin.agama.annotation.CSV;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

public class BeanUtils {

    public static Map<String,String> toCSVRecord(Object record){
        Map<String ,String> map = new TreeMap<>();
        Class<?> clazz = record.getClass();
        for(Field field : clazz.getDeclaredFields()){
            if(field.isAnnotationPresent(CSV.class)){
                CSV csv = field.getAnnotation(CSV.class);
                Object value = ReflectUtils.getValue(field.getName(), record);
                map.put(csv.title(),value == null ? null : value.toString());
            }
        }
        return map;
    }
}
