package com.cigarette.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author EdwardLee
 * @create 2021-08-20 14:16
 */
@Target(ElementType.METHOD)  // 作用到方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
public @interface NoRepeatSubmit {
}
