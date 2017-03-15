package com.github.gin.agama.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.FIELD})
public @interface JS {

    String var();

    String jsonpath() default "";

}
