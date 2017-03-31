package com.github.gin.agama.annotation;

import java.lang.annotation.*;

/**
 * Created by FSTMP on 2017/3/31.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Prey {
    String matchUrl () default "";
}
