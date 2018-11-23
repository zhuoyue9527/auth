package com.afis.cloud.annotation;

import java.lang.annotation.*;

/**
 * Created by hsw on 2017/1/16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface NoTransaction {
}
