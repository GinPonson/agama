package com.github.gin.agama.site.render;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.github.gin.agama.annotation.Json;
import com.github.gin.agama.annotation.Url;
import com.github.gin.agama.site.entity.AgamaEntity;
import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.converter.TypeConverter;
import com.github.gin.agama.util.AgamaUtils;
import com.github.gin.agama.util.ReflectUtils;
import com.github.gin.agama.util.UrlUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.getFields;
import static org.reflections.ReflectionUtils.withAnnotation;

/**
 * Created by FSTMP on 2016/10/27.
 */
public class JsonRender extends AbstractRender {

    @Override
    public List<AgamaEntity> renderToList(Page page, Class<? extends AgamaEntity> clazz) {
        List<AgamaEntity> result = new ArrayList<>();

        String rootJsonStr = page.getRawText();
        JSONObject rootJson = JSONObject.parseObject(rootJsonStr);

        //如果类带@Json注解，则只处理某段json
        if (clazz.isAnnotationPresent(Json.class)) {
            String jsonPath = clazz.getAnnotation(Json.class).value();
            Object segmentJson = JSONPath.eval(rootJson, jsonPath);

            if (ReflectUtils.haveSuperType(segmentJson.getClass(), List.class)) {
                //处理json数组
                JSONArray jsonArray = (JSONArray) segmentJson;

                //为了统一使用JsonPath解析
                for (Object json : jsonArray) {
                    page = new Page(page.getUrl(), JSON.toJSONString(json));
                    AgamaEntity agamaEntity = renderToBean(page, clazz);
                    result.add(agamaEntity);
                }
            } else {
                //处理Json Object
                page = new Page(page.getUrl(), JSON.toJSONString(segmentJson));
                AgamaEntity agamaEntity = renderToBean(page, clazz);
                result.add(agamaEntity);
            }
        } else {
            //处理全段Json
            result.add(renderToBean(page, clazz));
        }
        return result;
    }

    @Override
    public AgamaEntity renderToBean(Page page, Class<? extends AgamaEntity> clazz) {
        AgamaEntity entity = ReflectUtils.newInstance(clazz);

        String rootJsonStr = page.getRawText();
        JSONObject rootJson = JSONObject.parseObject(rootJsonStr);

        Set<Field> fieldSet = getAllFields(clazz, withAnnotation(Json.class));
        for (Field field : fieldSet) {
            String jsonPath = field.getAnnotation(Json.class).value();

            boolean isList = ReflectUtils.haveSuperType(field.getType(), List.class);
            if (isList) {
                //获取list的泛型
                Type type = field.getGenericType();
                Class<?> genericClass = ReflectUtils.getGenericClass(type, 0);

                boolean isAgamaEntity = ReflectUtils.haveSuperType(genericClass, AgamaEntity.class);
                if (isAgamaEntity) {
                    Class<? extends AgamaEntity> $genericClass = (Class<? extends AgamaEntity>) genericClass;

                    Object segmentJson = JSONPath.eval(rootJson, jsonPath);
                    List<AgamaEntity> subEntityList = new ArrayList<>();
                    if (ReflectUtils.haveSuperType(segmentJson.getClass(), List.class)) {
                        JSONArray jsonArray = (JSONArray) segmentJson;

                        for (Object object : jsonArray) {
                            page = new Page(page.getUrl(), JSON.toJSONString(object));
                            subEntityList.add(renderToBean(page, $genericClass));
                        }
                    }

                    ReflectUtils.setValue(field.getName(), entity, subEntityList);
                }
            } else {
                //处理普通类型
                Object segment = JSONPath.eval(rootJson, jsonPath);

                Object data = TypeConverter.convert(segment.toString(), field.getType());
                ReflectUtils.setValue(field.getName(), entity, data);
            }
        }
        renderUrl(page, entity);

        download(page, entity);

        return entity;
    }

    private void renderUrl(Page page, AgamaEntity entity) {
        String rootJsonStr = page.getRawText();
        JSONObject rootJson = JSONObject.parseObject(rootJsonStr);

        Set<Field> urlFieldSet = getFields(entity.getClass(), withAnnotation(Url.class));
        for (Field field : urlFieldSet) {
            String src = field.getAnnotation(Url.class).src();

            Object segment = JSONPath.eval(rootJson, src);
            String segmentStr = AgamaUtils.isNotBlank(segment) ? segment.toString() : "";
            String domain = UrlUtils.getDomain(page.getUrl());
            String url = UrlUtils.toAsbLink(domain, segmentStr);

            renderSubRequest(entity, field, url);
        }
    }

}
