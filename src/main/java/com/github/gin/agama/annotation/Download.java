package com.github.gin.agama.annotation;

import java.lang.annotation.*;

/**
 * Created by GinPonson on 2017/3/11.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Download {
    String dist();
}
