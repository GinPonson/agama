package com.github.gin.agama.annotation;

import java.lang.annotation.*;

/**
 * @author  GinPonson
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Url {

    String src();

    boolean click() default false;
}
