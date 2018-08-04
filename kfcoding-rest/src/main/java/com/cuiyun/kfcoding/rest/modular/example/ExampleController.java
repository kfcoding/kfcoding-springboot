package com.cuiyun.kfcoding.rest.modular.example;

import com.cuiyun.kfcoding.rest.common.SimpleObject;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 常规控制器
 *
 * @author maple
 * @date 2018-08-23 16:02
 */
@Controller
@RequestMapping("/hello")
public class ExampleController {

    @RequestMapping("")
    public ResponseEntity hello(SimpleObject simpleObject, HttpServletRequest request) {
        System.err.println(request.getParameter("user"));
        System.out.println(simpleObject.getUser());
        return ResponseEntity.ok("请求成功!");
    }
}
