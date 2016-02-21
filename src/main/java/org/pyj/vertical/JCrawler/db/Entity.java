package org.pyj.vertical.JCrawler.db;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Target(value={ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
	String name();
}
