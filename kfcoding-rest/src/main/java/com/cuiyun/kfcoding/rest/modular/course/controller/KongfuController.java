package com.cuiyun.kfcoding.rest.modular.course.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cuiyun.kfcoding.core.base.controller.BaseController;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.auth.util.JwtTokenUtil;
import com.cuiyun.kfcoding.rest.modular.course.model.Kongfu;
import com.cuiyun.kfcoding.rest.modular.course.service.IKongfuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by carrie on 2018/5/12.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/kongfu")
@Api(description = "课程相关接口")
public class KongfuController extends BaseController{
    @Autowired
    IKongfuService kongfuService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @ResponseBody
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "创建课程", notes="")
    public SuccessTip create(@RequestBody Kongfu kongfu){
        boolean flag = kongfuService.insert(kongfu);
        if(flag){
            return SUCCESSTIP;
        }else{
            throw new KfCodingException(BizExceptionEnum.COURSE_CREAT_ERROR);
        }

    }

    @ResponseBody
    @RequestMapping(path = "/findByPage", method = RequestMethod.GET)
    @ApiOperation(value = "课程列表", notes="")
    public SuccessTip findByPage(Page page) {
        Page<Kongfu> tutorialPage = kongfuService.selectPage(page);
        if (tutorialPage.getTotal() != 0) {
            map.put("kongfuPage", tutorialPage);
            SUCCESSTIP.setResult(map);
            return SUCCESSTIP;
        } else {
            throw new KfCodingException(BizExceptionEnum.COURSE_ERROR);
        }
    }


}
