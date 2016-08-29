package net.osc.gin.agama.util;

import net.osc.gin.agama.annotation.CSV;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by FSTMP on 2016/8/29.
 */
public class BeanUtils {

    public static Map<String,String> toCSVRecord(Object record){
        Map<String ,String> map = new TreeMap<>();
        Class<?> clazz = record.getClass();
        for(Field field : clazz.getDeclaredFields()){
            if(field.isAnnotationPresent(CSV.class)){
                CSV csv = field.getAnnotation(CSV.class);
                map.put(csv.title(),ReflectUtils.getValue(field.getName(),record).toString());
            }
        }
        return map;
    }
}
