package me.walkonly.lib.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

/**
 * Config Extra
 */
@Retention(RUNTIME)
@Target({TYPE, FIELD, METHOD})
public @interface C_Extra {

    String[] value() default { };

}
