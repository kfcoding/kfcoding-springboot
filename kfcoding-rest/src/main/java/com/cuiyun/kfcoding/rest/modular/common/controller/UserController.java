package com.cuiyun.kfcoding.rest.modular.common.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cuiyun.kfcoding.core.base.controller.BaseController;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.auth.util.JwtTokenUtil;
import com.cuiyun.kfcoding.rest.modular.common.model.User;
import com.cuiyun.kfcoding.rest.modular.common.service.IUserService;
import com.cuiyun.kfcoding.rest.modular.course.model.Kongfu;
import com.cuiyun.kfcoding.rest.modular.course.service.IKongfuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: kfcoding
 * @description: 用户控制层
 * @author: maple
 * @create: 2018-05-10 16:57
 **/
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
@Api(description = "用户相关接口")
public class UserController extends BaseController{

    @Autowired
    IUserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    IKongfuService kongfuService;

    @ResponseBody
    @RequestMapping(path = "/{userid}/kongfu", method = RequestMethod.GET)
    @ApiOperation(value = "用户课程列表", notes="列出该用户创建的所有课程")
    public SuccessTip listKongfu(@PathVariable(value = "userid") String userId){
        List list = kongfuService.selectList(new EntityWrapper<Kongfu>().eq("user_id", userId));
        Map map = new HashMap();
        SUCCESSTIP = new SuccessTip();
        map = new HashMap();
        map.put("courses", list);
        SUCCESSTIP.setResult(map);
        return SUCCESSTIP;
    }

    @ResponseBody
    @RequestMapping(path = "/current", method = RequestMethod.GET)
    @ApiOperation(value = "用户课程列表", notes="列出该用户创建的所有课程")
    public SuccessTip current(HttpServletRequest request){
        String token = (String) request.getAttribute("token");
        String userId = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.selectById(userId);
        SUCCESSTIP = new SuccessTip();
        map = new HashMap();
        map.put("user", user);
        SUCCESSTIP.setResult(map);
        return SUCCESSTIP;
    }

}
