package com.cuiyun.kfcoding.rest.modular.course.controller;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cuiyun.kfcoding.core.base.tips.ErrorTip;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.base.tips.Tip;
import com.cuiyun.kfcoding.rest.common.annotion.BussinessLog;
import com.cuiyun.kfcoding.rest.common.annotion.Permission;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.base.controller.BaseController;
import com.cuiyun.kfcoding.rest.modular.common.model.User;
import com.cuiyun.kfcoding.rest.modular.course.model.Course;
import com.cuiyun.kfcoding.rest.modular.course.model.CourseToUser;
import com.cuiyun.kfcoding.rest.modular.course.service.ICourseService;
import com.cuiyun.kfcoding.rest.modular.course.service.ICourseToUserService;
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
    ICourseToUserService courseToUserService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "创建课程", notes="")
    @BussinessLog("创建课程")
    @Permission
    public Tip create(@RequestBody Course course){
        String code = RandomUtil.randomString(6);
        course.setCode(code);
        while (courseService.selectOne(new EntityWrapper<Course>().eq("code", code))  != null)
            code = RandomUtil.randomString(6);
        course.setUserId(getUser().getId());
        course.setCreateTime(new Date());
        if (!courseService.insert(course))
            return new ErrorTip(BizExceptionEnum.COURSE_CREATE.getCode(), BizExceptionEnum.COURSE_CREATE.getMessage());
        return new SuccessTip();
    }

    @ResponseBody
    @BussinessLog("修改课程")
    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "修改课程", notes="")
    @Permission
    public Tip update(@RequestBody Course course){
        Course targetCourse = courseService.selectOne(new EntityWrapper<Course>().eq("id", course.getId()));
        if (targetCourse == null)
            return new ErrorTip(BizExceptionEnum.COURSE_NULL.getCode(),BizExceptionEnum.COURSE_NULL.getMessage());
        if (!courseService.updateById(course))
            return new ErrorTip(BizExceptionEnum.COURSE_UPDATE.getCode(), BizExceptionEnum.COURSE_UPDATE.getMessage());
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
    @RequestMapping(path = "/join",method = RequestMethod.GET)
    @ApiOperation(value = "加入课程", notes="")
    public Tip join(@RequestParam String code){
        User user = getUser();
        Course course= courseService.selectOne(new EntityWrapper<Course>().eq("code", code));
        if (course == null)
            return new ErrorTip(BizExceptionEnum.COURSE_NULL.getCode(), BizExceptionEnum.COURSE_NULL.getMessage());
        CourseToUser courseToUser = new CourseToUser();
        courseToUser.setUserId(user.getId());
        courseToUser.setCourseId(course.getId());
        courseToUserService.insert(courseToUser);
        return new SuccessTip();
    }

    @ResponseBody
    @RequestMapping(path = "/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "课程列表", notes="")
    public Tip get(@PathVariable String id){
        Course course= courseService.getCourseById(id);
        if (course == null)
            return new ErrorTip(BizExceptionEnum.COURSE_NULL.getCode(), BizExceptionEnum.COURSE_NULL.getMessage());
        MAP = new HashMap<>();
        SUCCESSTIP = new SuccessTip();
        MAP.put("course", course);
        SUCCESSTIP.setResult(MAP);
        return SUCCESSTIP;
    }



}
