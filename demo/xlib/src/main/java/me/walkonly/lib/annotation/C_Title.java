package me.walkonly.lib.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Config Title
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface C_Title {

    int value() default 0; // 标题-字符串id

}
