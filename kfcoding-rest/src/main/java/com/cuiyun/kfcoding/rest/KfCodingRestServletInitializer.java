package com.cuiyun.kfcoding.rest;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * kfcoding REST Web程序启动类
 *
 * @author maple
 * @date 2017年9月29日09:00:42
 */
public class KfCodingRestServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(KfCodingRestApplication.class);
    }

}
