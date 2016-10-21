package com.github.gin.agama.annotation;

import java.lang.annotation.*;

/**
 * Created by FSTMP on 2016/10/21.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Proxy {

    String username() default "";

    String password() default "";

    String host();

    int port();
}
