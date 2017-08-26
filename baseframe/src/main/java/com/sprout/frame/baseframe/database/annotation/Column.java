package com.sprout.frame.baseframe.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Sprout on 2017/8/25
 * 列名注解
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    /**
     * 列名, 一张表必须保证至少有一个列名, 否则抛异常
     */
    String name();

    /**
     * 是否为主键<br>
     * 1. 被修饰的成员变量类型只能是int, 否则抛异常<br>
     * 2. 一张表中最多有一个主键
     */
    boolean primaryKey() default false;
}
