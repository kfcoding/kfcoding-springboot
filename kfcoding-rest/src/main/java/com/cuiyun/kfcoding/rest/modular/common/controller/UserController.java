package com.cuiyun.kfcoding.rest.modular.common.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.core.util.MD5Util;
import com.cuiyun.kfcoding.core.util.ToolUtil;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.auth.controller.dto.AuthPasswordRequest;
import com.cuiyun.kfcoding.rest.modular.auth.enums.AuthTypeEnum;
import com.cuiyun.kfcoding.rest.modular.auth.validator.impl.DbValidator;
import com.cuiyun.kfcoding.rest.modular.base.controller.BaseController;
import com.cuiyun.kfcoding.rest.modular.common.model.User;
import com.cuiyun.kfcoding.rest.modular.common.service.IUserService;
import com.cuiyun.kfcoding.rest.modular.course.enums.KongfuStatusEnum;
import com.cuiyun.kfcoding.rest.modular.course.model.Kongfu;
import com.cuiyun.kfcoding.rest.modular.course.service.IKongfuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
public class UserController extends BaseController {

    @Autowired
    IUserService userService;

    @Autowired
    IKongfuService kongfuService;

    @Autowired
    DbValidator dbValidator;


    @ResponseBody
    @RequestMapping(path = "/{userid}/kongfu", method = RequestMethod.GET)
    @ApiOperation(value = "用户课程列表", notes="列出该用户创建的所有课程")
    public SuccessTip listKongfu(@PathVariable(value = "userid") String userId){
        EntityWrapper ew = new EntityWrapper<Kongfu>();
        ew.eq("user_id", userId);
        ew.eq("status", KongfuStatusEnum.PUBLIC);
        List list = kongfuService.selectList(ew);
        SUCCESSTIP = new SuccessTip();
        MAP = new HashMap<>();
        MAP.put("courses", list);
        SUCCESSTIP.setResult(MAP);
        return SUCCESSTIP;
    }

    @ResponseBody
    @RequestMapping(path = "/current", method = RequestMethod.GET)
    @ApiOperation(value = "用户课程列表", notes="列出该用户创建的所有课程")
    public SuccessTip current(HttpServletRequest request){
        User user = getUser(request);
        SUCCESSTIP = new SuccessTip();
        MAP = new HashMap<>();
        MAP.put("user", user);
        SUCCESSTIP.setResult(MAP);
        return SUCCESSTIP;
    }

    @ResponseBody
    @RequestMapping(path = "/current", method = RequestMethod.POST)
    @ApiOperation(value = "用户类", notes="修改用户信息")
    public SuccessTip getUserInfoById(@RequestBody User user){
        User oldUser = userService.selectById(user.getId());
        // 检验账号是否重复
        if (!oldUser.getAccount().equals(user.getAccount())){
            EntityWrapper ew = new EntityWrapper();
            ew.eq("account", user.getAccount());
            if (userService.selectOne(ew) != null){
                throw new KfCodingException(BizExceptionEnum.USER_EXIST);
            }
        }
        // 修改数据
        String[] ingoreProperties = {"id", "version", "startTime", "updateTime", "isDel"};
        BeanUtil.copyProperties(user, oldUser, ingoreProperties);
        if (!userService.updateById(oldUser)) {
            throw new KfCodingException(BizExceptionEnum.USER_ERROR);
        }
        SUCCESSTIP = new SuccessTip();
        MAP = new HashMap<>();
        MAP.put("user", oldUser);
        SUCCESSTIP.setResult(MAP);
        return SUCCESSTIP;
    }


    @ResponseBody
    @RequestMapping(path = "/current/kongfu", method = RequestMethod.GET)
    @ApiOperation(value = "用户课程列表", notes="列出该用户创建的所有课程")
    public SuccessTip currentKongfu(HttpServletRequest request){
        User user = getUser(request);
        EntityWrapper ew = new EntityWrapper<Kongfu>();
        ew.eq("user_id", user.getId());
        List kongfus = kongfuService.selectList(ew);
        SUCCESSTIP = new SuccessTip();
        MAP = new HashMap<>();
        MAP.put("kongfuList", kongfus);
        SUCCESSTIP.setResult(MAP);
        return SUCCESSTIP;
    }

    @ResponseBody
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "用户Id", notes="获取用户信息")
    public SuccessTip getUserInfoById(@PathVariable String id){
        User user = userService.selectById(id);
        if (user == null) {
            throw new KfCodingException(BizExceptionEnum.USER_ERROR);
        }
        SUCCESSTIP = new SuccessTip();
        MAP = new HashMap<>();
        MAP.put("user", user);
        SUCCESSTIP.setResult(MAP);
        return SUCCESSTIP;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "用户类", notes="创建用户")
    public SuccessTip createUser(@RequestBody User user){
        if (StrUtil.isAllBlank(user.getEmail(), user.getPassword()))
            throw new KfCodingException(BizExceptionEnum.USER_CREATE_REQUIRED);
        if (!ToolUtil.checkEmail(user.getEmail()))
            throw new KfCodingException(BizExceptionEnum.USER_CREATE_EMAIL);
        if (userService.selectOne(new EntityWrapper<User>().eq("email",user.getEmail())) != null)
            throw new KfCodingException(BizExceptionEnum.USER_EXIST);

        // 密码加密
        String password = user.getPassword();
        user.setPassword(MD5Util.encrypt(password));

        if (user.getName() == null){
            user.setName(user.getEmail());
        }
        if (user.getAccount() == null){
            user.setAccount(user.getEmail());
        }
        user.setCreateTime(new Date());
        if (userService.insert(user)){
            AuthPasswordRequest authPasswordRequest = new AuthPasswordRequest();
            authPasswordRequest.setAuthType(AuthTypeEnum.PASSWORD.toString());
            authPasswordRequest.setCredenceCode(password);
            authPasswordRequest.setCredenceName(user.getEmail());
            if(dbValidator.validate(authPasswordRequest) != null){
                String token = jwtTokenUtil.generateToken(user.getId(), jwtTokenUtil.getRandomKey());
                MAP = new HashMap<>();
                SUCCESSTIP = new SuccessTip();
                MAP.put("token", token);
                SUCCESSTIP.setResult(MAP);
                return SUCCESSTIP;
            } else
                throw new KfCodingException(BizExceptionEnum.AUTH_REQUEST_ERROR);
        }
        throw new KfCodingException(BizExceptionEnum.USER_CREATE_REQUIRED);
    }



}
