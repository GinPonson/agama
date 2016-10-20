package com.github.gin.agama.annotation;

import java.lang.annotation.*;

/**
 * Created by FSTMP on 2016/10/20.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {

    String key();

    String val();
}
