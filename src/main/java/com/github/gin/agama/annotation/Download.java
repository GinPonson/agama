package com.github.gin.agama.annotation;

import java.lang.annotation.*;

/**
 * @author  GinPonson
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Download {
    String dist();
}
