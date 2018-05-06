package com.cuiyun.kfcoding.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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
    @ResponseBody
    @ApiOperation(value = "测试hello接口", notes="返回输入的内容")
    public String hello(@RequestParam String name){
        return name;
    }
}
