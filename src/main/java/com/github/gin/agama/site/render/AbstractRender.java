package com.github.gin.agama.site.render;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.github.gin.agama.annotation.Download;
import com.github.gin.agama.annotation.JS;
import com.github.gin.agama.annotation.Json;
import com.github.gin.agama.annotation.Url;
import com.github.gin.agama.core.ContextHolder;
import com.github.gin.agama.downloader.Downloader;
import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.Request;
import com.github.gin.agama.site.entity.AgamaEntity;
import com.github.gin.agama.site.converter.TypeConverter;
import com.github.gin.agama.site.serekuta.JsoupSerekuta;
import com.github.gin.agama.site.serekuta.Serekuta;
import com.github.gin.agama.util.AgamaUtils;
import com.github.gin.agama.util.ReflectUtils;
import com.github.gin.agama.util.UrlUtils;
import jdk.nashorn.internal.runtime.ScriptObject;
import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mozilla.javascript.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.getFields;
import static org.reflections.ReflectionUtils.withAnnotation;

/**
 * @author  GinPonson
 */
public abstract class AbstractRender implements Render {

    private static final Logger LOGGER = LoggerFactory.getLogger(XpathRender.class);

    public void download(Page page, AgamaEntity entity) {
        Set<Field> downField = getFields(entity.getClass(), withAnnotation(Download.class));
        for (Field field : downField) {
            String dist = field.getAnnotation(Download.class).dist();
            for (String param : UrlUtils.getParam(dist)) {
                Object object = ReflectUtils.getValue(param, entity);
                dist = dist.replace("${" + param + "}", object.toString());
            }
            try {
                Object ad = ReflectUtils.getValue(field.getName(), entity);
                if (AgamaUtils.isNotBlank(ad)) {
                    URL url = new URL(ad.toString());
                    //新开一个线程??
                    FileUtils.copyURLToFile(url, new File(dist));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public void renderSubRequest(AgamaEntity entity, Field field, String url) {
        if (!AgamaUtils.isNotBlank(url.trim())) {
            return;
        }

        boolean isAgamaEntity = ReflectUtils.haveSuperType(field.getType(), AgamaEntity.class);
        boolean click = field.getAnnotation(Url.class).click();
        if (click && isAgamaEntity) {
            //TODO
            //新开一个线程??
            Downloader downloader = ContextHolder.getContext().getDownloader();
            try {
                Page subpage = downloader.download(new Request(url));
                AgamaEntity agamaEntity = renderToBean(subpage, (Class<? extends AgamaEntity>) field.getType());

                ReflectUtils.setValue(field.getName(), entity, agamaEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Object data = TypeConverter.convert(url, field.getType());
            ReflectUtils.setValue(field.getName(), entity, data);
        }
    }

    public void renderJs(Page page, AgamaEntity entity) {
        Set<Field> jsFields = getFields(entity.getClass(), withAnnotation(JS.class));
        if (jsFields.isEmpty()) {
            return;
        }

        Context context = Context.enter();
        ScriptableObject scope = context.initSafeStandardObjects();
        String windowScript = "var window = {};var document = {};";
        context.evaluateString(scope, windowScript, "window", 1, null);
        Serekuta serekuta = new JsoupSerekuta(page.getRawText());
        Elements elements = ((JsoupSerekuta)serekuta.select("script")).getElements();

        for(Element element : elements){
            String script = element.html();
            try {
                context.evaluateString(scope, script, "", 1, null);
            } catch (Exception e)
            {

            }
        }

        for (Field jsField : jsFields) {
            Class clazz = jsField.getType();
            JS jsVar = jsField.getAnnotation(JS.class);
            String var = jsVar.var();
            Object jsObj = scope.get(var, scope);
            if (jsObj instanceof NativeObject || jsObj instanceof NativeArray) {
                String jsonPath = jsVar.jsonpath();
                // 将javascript变量格式化为json对象
                Object jsonObj = NativeJSON.stringify(context, scope, jsObj, null, null);
                // 使用fastjson将json字符串格式化为JSONObject
                Object json = JSON.parse(jsonObj.toString());
                // 解析jsonpath
                Object src = JSONPath.eval(json, jsonPath);
                // 如果解析出来的是字符串，尝试转换为json对象
                try {
                    if (src instanceof String) {
                        src = JSON.parse(src.toString());
                    }
                } catch (Exception ex) {
                }
                // 将json对象转换为javabean属性
                try {

                    boolean isList = ReflectUtils.haveSuperType(jsField.getType(), List.class);
                    if (isList) {
                        //获取list的泛型
                        Type type = jsField.getGenericType();
                        Class<?> genericClass = ReflectUtils.getGenericClass(type, 0);

                        boolean isAgamaEntity = ReflectUtils.haveSuperType(genericClass, AgamaEntity.class);
                        if (isAgamaEntity) {
                            Class<? extends AgamaEntity> $genericClass = (Class<? extends AgamaEntity>) genericClass;

                            //Object segmentJson = JSONPath.eval(src, jsonPath);
                            List<AgamaEntity> subEntityList = new ArrayList<>();
                            if (ReflectUtils.haveSuperType(src.getClass(), List.class)) {
                                JSONArray jsonArray = (JSONArray) src;

                                for (Object object : jsonArray) {
                                    page = new Page(page.getUrl(), JSON.toJSONString(object));
                                    subEntityList.add(renderToBean1(page, $genericClass));
                                }
                            }

                            ReflectUtils.setValue(jsField.getName(), entity, subEntityList);
                        }
                    } else {
                        //处理普通类型
                        Object segment = JSONPath.eval(src, jsonPath);

                        Object data = TypeConverter.convert(segment.toString(), jsField.getType());
                        ReflectUtils.setValue(jsField.getName(), entity, data);
                    }

                } catch (Exception e) {
                    LOGGER.error("field [" + jsField.getName() + "] conversion error, value=" + src);
                }
            } else if (jsObj instanceof Boolean || jsObj instanceof Number || jsObj instanceof String) {
                try {
                    Object value = TypeConverter.convert(jsObj.toString(), clazz);
                    ReflectUtils.setValue(jsField.getName(), entity, value);
                } catch (Exception e) {
                    LOGGER.error("field [" + jsField.getName() + "] conversion error, value=" + jsObj);
                }
            }
        }
    }

    public AgamaEntity renderToBean1(Page page, Class<? extends AgamaEntity> clazz) {
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
        //renderUrl(page, entity);

        //download(page, entity);

        return entity;
    }

}
