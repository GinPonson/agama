package com.github.gin.agama.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface Ason{

	String value();
}
