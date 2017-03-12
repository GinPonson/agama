package com.github.gin.agama.annotation;

import java.lang.annotation.*;

/**
 *  Created by GinPonson on 3/7/2017.
 *  value=true 表示只提取纯文本
 *  value=false 表示提取html文本
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Html {

    boolean value() default true;
}
