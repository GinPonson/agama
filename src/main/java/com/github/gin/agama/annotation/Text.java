package com.github.gin.agama.annotation;

import java.lang.annotation.*;

/**
 * Created by GinPonson on 3/7/2017.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Text {

    boolean value() default true;
}
