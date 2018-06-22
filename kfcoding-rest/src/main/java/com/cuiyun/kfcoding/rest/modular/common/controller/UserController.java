package com.cuiyun.kfcoding.rest.modular.common.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.base.controller.BaseController;
import com.cuiyun.kfcoding.rest.modular.course.enums.KongfuStatusEnum;
import com.cuiyun.kfcoding.rest.modular.common.model.User;
import com.cuiyun.kfcoding.rest.modular.common.service.IUserService;
import com.cuiyun.kfcoding.rest.modular.course.model.Kongfu;
import com.cuiyun.kfcoding.rest.modular.course.service.IKongfuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserController extends BaseController {

    @Autowired
    IUserService userService;

    @Autowired
    IKongfuService kongfuService;

    @ResponseBody
    @RequestMapping(path = "/{userid}/kongfu", method = RequestMethod.GET)
    @ApiOperation(value = "用户课程列表", notes="列出该用户创建的所有课程")
    public SuccessTip listKongfu(@PathVariable(value = "userid") String userId){
        EntityWrapper ew = new EntityWrapper<Kongfu>();
        ew.eq("user_id", userId);
        ew.eq("status", KongfuStatusEnum.PUBLIC);
        List list = kongfuService.selectList(ew);
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
//        String token = (String) request.getAttribute("token");
//        String userId = jwtTokenUtil.getUsernameFromToken(token);
        User user = getUser(request);
        SUCCESSTIP = new SuccessTip();
        map = new HashMap();
        map.put("user", user);
        SUCCESSTIP.setResult(map);
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
        map = new HashMap();
        map.put("user", oldUser);
        SUCCESSTIP.setResult(map);
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
        map = new HashMap();
        map.put("kongfuList", kongfus);
        SUCCESSTIP.setResult(map);
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
        map = new HashMap();
        map.put("user", user);
        SUCCESSTIP.setResult(map);
        return SUCCESSTIP;
    }



}
