package com.cuiyun.kfcoding.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: kfcoding
 * @description: springboot启动类
 * @author: maple
 * @create: 2018-05-06 12:02
 **/
@SpringBootApplication(scanBasePackages = "com.cuiyun.kfcoding")
public class KfCodingRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(KfCodingRestApplication.class, args);

    }
}
