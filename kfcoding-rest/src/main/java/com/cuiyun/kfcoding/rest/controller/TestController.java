package com.cuiyun.kfcoding.rest.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: kfcoding
 * @description: 测试类
 * @author: maple
 * @create: 2018-05-06 16:56
 **/
@RestController
@RequestMapping("/test")
@Api(description = "测试相关接口")
public class TestController {

    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam String name){
        return name;
    }
}
