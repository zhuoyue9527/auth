package com.afis.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 有此注解的，说明是需要登录后才可以访问的
 * 
 * 该注解加载method上，表示该方法是需要登录后才能使用的
 * 
 * @author Chengen
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AfisAuthentication {
}
