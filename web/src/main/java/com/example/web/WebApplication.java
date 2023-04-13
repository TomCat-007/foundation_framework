package com.example.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zhangguiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */

@Slf4j
@MapperScan(basePackages = "com.example.*.mapper")
@ComponentScan(
        basePackages = {
                "common",
                "com.example.web",
                "com.example.web.convert",
                "cn.hutool.extra.spring",
        })
@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
