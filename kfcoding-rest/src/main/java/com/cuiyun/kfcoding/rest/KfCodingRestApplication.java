package com.cuiyun.kfcoding.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: kfcoding
 * @description: springboot启动类
 * @author: maple
 * @create: 2018-05-06 12:02
 **/
@SpringBootApplication(scanBasePackages = "com.cuiyun.kfcoding")
public class KfCodingRestApplication {

//    // 设置时区
//    @PostConstruct
//    void started() {
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//    }

    public static void main(String[] args) {
        SpringApplication.run(KfCodingRestApplication.class, args);

    }
}
