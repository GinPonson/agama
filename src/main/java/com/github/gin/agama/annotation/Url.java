package com.github.gin.agama.annotation;

import java.lang.annotation.*;

/**
 * Created by GinPonson on 2017/3/11.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Url {

    String src();

    boolean click() default false;
}
