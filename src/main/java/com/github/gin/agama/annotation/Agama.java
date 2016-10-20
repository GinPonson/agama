package com.github.gin.agama.annotation;

import java.lang.annotation.*;

/**
 * Created by FSTMP on 2016/10/20.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface Agama {

    Class datastore();

    Class downloader();

    Class processer();

    Configs configs();

}
