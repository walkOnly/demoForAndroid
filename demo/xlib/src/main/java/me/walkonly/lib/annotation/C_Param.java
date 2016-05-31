package me.walkonly.lib.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;

/**
 * Config URL
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface C_Param {

    String[] value();

}
