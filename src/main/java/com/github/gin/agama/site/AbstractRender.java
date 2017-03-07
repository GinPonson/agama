package com.github.gin.agama.site;

import com.github.gin.agama.annotation.Xpath;
import com.github.gin.agama.entity.AgamaEntity;
import com.github.gin.agama.util.ReflectUtils;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created by FSTMP on 2016/10/27.
 */
public abstract class AbstractRender {

    public AgamaEntity inject(Request request,Page page,Class<? extends AgamaEntity> clazz){
        AgamaEntity entity = ReflectionUtils.nnewInstance(clazz);

        Set<Field> fieldSet = ReflectionUtils.getAllFields(clazz,ReflectionUtils.withAnnotation(Xpath.class));

        return entity;
    }

}
