package com.github.gin.agama.annotation;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface ChildItem {

	Class value();
}
