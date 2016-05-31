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

    int value(); // 标题-字符串id

    int layoutId() default 1; // 0:无标题栏  1:默认标题栏  R.layout.view_title_new:自定义标题栏

}
