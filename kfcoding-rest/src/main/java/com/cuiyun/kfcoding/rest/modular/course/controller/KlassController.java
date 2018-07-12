package com.cuiyun.kfcoding.rest.modular.course.controller;

import com.cuiyun.kfcoding.core.base.tips.ErrorTip;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.base.tips.Tip;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.base.controller.BaseController;
import com.cuiyun.kfcoding.rest.modular.common.model.User;
import com.cuiyun.kfcoding.rest.modular.course.model.Klass;
import com.cuiyun.kfcoding.rest.modular.course.model.KlassToUser;
import com.cuiyun.kfcoding.rest.modular.course.service.IKlassService;
import com.cuiyun.kfcoding.rest.modular.course.service.IKlassToUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @program: kfcoding
 * @description: 班级控制类
 * @author: maple
 * @create: 2018-07-12 16:26
 **/
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/klasses")
@Api(description = "班级相关接口")
public class KlassController extends BaseController{

    @Autowired
    IKlassService klassService;

    @Autowired
    IKlassToUserService klassToUserService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "创建班级", notes="")
    public Tip create(@RequestBody Klass klass){
        if (klass.getCourseId() == null)
            return new ErrorTip(BizExceptionEnum.COURSE_CREATE.getCode(), BizExceptionEnum.COURSE_KLASS_CREATE.getMessage());
        if (!klassService.insert(klass))
            return new ErrorTip(BizExceptionEnum.COURSE_KLASS_CREATE.getCode(), BizExceptionEnum.COURSE_KLASS_CREATE.getMessage());
        return new SuccessTip();
    }

    @ResponseBody
    @RequestMapping(path = "/{id}/join", method = RequestMethod.GET)
    @ApiOperation(value = "加入班级", notes="")
    public Tip join(@PathVariable String id){
        User user = getUser();
        Klass klass = klassService.selectById(id);
        if (klass == null)
            return new ErrorTip(BizExceptionEnum.COURSE_KLASS_EXIST.getCode(), BizExceptionEnum.COURSE_KLASS_EXIST.getMessage());
        KlassToUser klassToUser = new KlassToUser();
        klassToUser.setKlassId(klass.getId());
        klassToUser.setUserId(user.getId());
        klassToUser.setCreateTime(new Date());
        if (!klassToUserService.insert(klassToUser))
            return new ErrorTip(BizExceptionEnum.COURSE_JOIN.getCode(), BizExceptionEnum.COURSE_JOIN.getMessage());
        return new SuccessTip();
    }




}
