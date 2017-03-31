package com.github.gin.agama.annotation;

import java.lang.annotation.*;

/**
 * @author  GinPonson
 * Created on 3/31/2017.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Regex {

    String regex();

    int group();
}
