package com.ziwow.scrmapp.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 小程序请求服务号认证
 *  限制. 第一个参数必须为signture 第二个参数必须为timeStamp
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MiniAuthentication {
}
