package com.github.gin.agama.site;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.github.gin.agama.annotation.Json;
import com.github.gin.agama.entity.AgamaEntity;
import com.github.gin.agama.site.converter.TypeConverter;
import com.github.gin.agama.util.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;

/**
 * Created by FSTMP on 2016/10/27.
 */
public class JsonRender implements Render {

    @Override
    public AgamaEntity inject(Page page, Class<? extends AgamaEntity> clazz) {
        AgamaEntity entity = ReflectUtils.newInstance(clazz);

        /*String json = page.getRawText();
        if (clazz.isAnnotationPresent(Json.class)) {
            String jsonPath = clazz.getAnnotation(Json.class).value();

            JSONObject jsonObject = JSONObject.parseObject(json);
            json = JSONPath.eval(jsonObject, jsonPath).toString();

            List<? extends AgamaEntity> subEntityList = JSONArray.parseArray(json, clazz);
            System.out.print("a");
        }*/

        Set<Field> fieldSet = getAllFields(clazz, withAnnotation(Json.class));

        for (Field field : fieldSet) {
            String jsonPath = field.getAnnotation(Json.class).value();

            Class<?> fieldClass = field.getType();
            boolean isList = ReflectUtils.haveSuperType(fieldClass, List.class);

            if (isList) {
                //获取list的泛型
                Type type = field.getGenericType();
                Class<?> genericClass = ReflectUtils.getGenericClass(type, 0);

                boolean isAgamaEntity = ReflectUtils.haveSuperType(genericClass, AgamaEntity.class);
                if (isAgamaEntity) {
                    JSONObject jsonObject = JSONObject.parseObject(page.getRawText());
                    Object segment = JSONPath.eval(jsonObject, jsonPath);
                    List<?> subEntityList = JSONArray.parseArray(segment.toString(), genericClass);

                    ReflectUtils.setValue(field.getName(), entity, subEntityList);
                }
            } else {
                //处理普通类型
                JSONObject jsonObject = JSONObject.parseObject(page.getRawText());
                Object segment = JSONPath.eval(jsonObject, jsonPath);

                Object data = TypeConverter.convert(segment.toString(), field.getType());
                ReflectUtils.setValue(field.getName(), entity, data);
            }
        }

        return entity;
    }
}
