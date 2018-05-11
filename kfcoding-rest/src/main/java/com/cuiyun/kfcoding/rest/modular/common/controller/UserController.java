package com.cuiyun.kfcoding.rest.modular.common.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.common.model.User;
import com.cuiyun.kfcoding.rest.modular.common.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: kfcoding
 * @description: 用户控制层
 * @author: maple
 * @create: 2018-05-10 16:57
 **/
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    IUserService userService;

    @ResponseBody
    @RequestMapping(path = "/getinfobytoken", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiOperation(value = "回调接口", notes="")
    public ResponseEntity<User> getInfoByToken(@RequestBody String token){
        User user = userService.selectOne(new EntityWrapper<User>().eq("token",token));
        if(user != null){
            return ResponseEntity.ok(user);
        }else {
            throw new KfCodingException(BizExceptionEnum.USER_ERROR);
        }
    }
}
