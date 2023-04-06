package com.example.web.config.security.aop;

import java.lang.annotation.*;

/**
 * @author zhanghuiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@Inherited
public @interface UserDetails {

    String value();

}
