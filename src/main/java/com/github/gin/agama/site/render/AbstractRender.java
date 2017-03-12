package com.github.gin.agama.site.render;


import com.github.gin.agama.annotation.Download;
import com.github.gin.agama.annotation.Url;
import com.github.gin.agama.entity.AgamaEntity;
import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.TagNodes;
import com.github.gin.agama.site.converter.TypeConverter;
import com.github.gin.agama.util.AgamaUtils;
import com.github.gin.agama.util.ReflectUtils;
import com.github.gin.agama.util.UrlUtils;
import com.github.gin.agama.util.XpathUtils;
import org.apache.commons.io.FileUtils;
import org.htmlcleaner.TagNode;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Set;

import static com.github.gin.agama.site.render.XpathRender.HTML_CLEANER;
import static org.reflections.ReflectionUtils.getFields;
import static org.reflections.ReflectionUtils.withAnnotation;

/**
 * Created by GinPonson on 2017/3/11.
 */
public abstract class AbstractRender implements Render{

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


}
