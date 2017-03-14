package com.github.gin.agama.site.render;


import com.github.gin.agama.annotation.Download;
import com.github.gin.agama.annotation.Url;
import com.github.gin.agama.core.ContextHolder;
import com.github.gin.agama.downloader.Downloader;
import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.Request;
import com.github.gin.agama.site.bean.AgamaEntity;
import com.github.gin.agama.site.converter.TypeConverter;
import com.github.gin.agama.util.AgamaUtils;
import com.github.gin.agama.util.ReflectUtils;
import com.github.gin.agama.util.UrlUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Set;

import static org.reflections.ReflectionUtils.getFields;
import static org.reflections.ReflectionUtils.withAnnotation;

/**
 * Created by GinPonson on 2017/3/11.
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
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("The url of sub request is blank ! return !");
            }
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
}
