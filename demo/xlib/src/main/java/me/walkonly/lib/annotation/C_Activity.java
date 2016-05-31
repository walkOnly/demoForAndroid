package me.walkonly.lib.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Config Activity
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface C_Activity {

    int value(); // layoutId

}
