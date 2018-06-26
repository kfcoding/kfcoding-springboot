package com.cuiyun.kfcoding.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @program: kfcoding
 * @description: springboot启动类
 * @author: maple
 * @create: 2018-05-06 12:02
 **/
@SpringBootApplication(scanBasePackages = "com.cuiyun.kfcoding")
//开启缓存配置
@EnableCaching
public class KfCodingRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(KfCodingRestApplication.class, args);

    }
}
