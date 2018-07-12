package com.cuiyun.kfcoding.rest.modular.course.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cuiyun.kfcoding.core.base.tips.ErrorTip;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.base.tips.Tip;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.base.controller.BaseController;
import com.cuiyun.kfcoding.rest.modular.course.model.Course;
import com.cuiyun.kfcoding.rest.modular.course.model.Klass;
import com.cuiyun.kfcoding.rest.modular.course.service.ICourseService;
import com.cuiyun.kfcoding.rest.modular.course.service.IKlassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @program: kfcoding
 * @description: 课程控制类
 * @author: maple
 * @create: 2018-07-12 14:57
 **/
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/courses")
@Api(description = "课程相关接口")
public class CourseController extends BaseController{
    @Autowired
    ICourseService courseService;

    @Autowired
    IKlassService klassService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "创建课程", notes="")
    public Tip create(@RequestBody Course course){
        course.setUserId(getUser().getId());
        course.setCreateTime(new Date());
        if (!courseService.insert(course))
            return new ErrorTip(BizExceptionEnum.COURSE_CREATE.getCode(), BizExceptionEnum.COURSE_CREATE.getMessage());
        return new SuccessTip();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "课程列表", notes="")
    public Tip list(){
        List list = courseService.selectList(new EntityWrapper<>());
        MAP = new HashMap<>();
        SUCCESSTIP = new SuccessTip();
        MAP.put("courses", list);
        SUCCESSTIP.setResult(MAP);
        return SUCCESSTIP;
    }

    @ResponseBody
    @RequestMapping(path = "/{id}/klasses", method = RequestMethod.GET)
    @ApiOperation(value = "班级列表", notes="")
    public Tip list(@PathVariable String id){
        List list = klassService.selectList(new EntityWrapper<Klass>().eq("course_id" , id));
        MAP = new HashMap<>();
        SUCCESSTIP = new SuccessTip();
        MAP.put("courses", list);
        SUCCESSTIP.setResult(MAP);
        return SUCCESSTIP;
    }
}
