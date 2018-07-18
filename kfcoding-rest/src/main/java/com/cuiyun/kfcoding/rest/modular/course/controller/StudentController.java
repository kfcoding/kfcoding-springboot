package com.cuiyun.kfcoding.rest.modular.course.controller;

import com.cuiyun.kfcoding.core.base.tips.ErrorTip;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.base.tips.Tip;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.base.controller.BaseController;
import com.cuiyun.kfcoding.rest.modular.common.enums.RoleEum;
import com.cuiyun.kfcoding.rest.modular.common.model.User;
import com.cuiyun.kfcoding.rest.modular.common.service.IUserService;
import com.cuiyun.kfcoding.rest.modular.course.model.Course;
import com.cuiyun.kfcoding.rest.modular.course.model.Student;
import com.cuiyun.kfcoding.rest.modular.course.model.Work;
import com.cuiyun.kfcoding.rest.modular.course.service.ICourseService;
import com.cuiyun.kfcoding.rest.modular.course.service.ICourseToUserService;
import com.cuiyun.kfcoding.rest.modular.course.service.IStudentService;
import com.cuiyun.kfcoding.rest.modular.course.service.IWorkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @program: kfcoding
 * @description: 学生控制类
 * @author: maple
 * @create: 2018-07-12 19:53
 **/
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/students")
@Api(description = "学生相关接口")
public class StudentController extends BaseController {

    @Autowired
    IStudentService studentService;

    @Autowired
    IUserService userService;

    @Autowired
    IWorkService workService;

    @Autowired
    ICourseService courseService;

    @Autowired
    ICourseToUserService courseToUserService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "学生认证", notes = "")
    public Tip create(@RequestBody Student student) {
        User user = getUser();
        if (user.getStudent() != null) {
            Student targetStudent = user.getStudent();
            targetStudent.setRealName(student.getRealName());
            targetStudent.setSchool(student.getSchool());
            targetStudent.setStudentNumber(student.getStudentNumber());
            studentService.updateById(targetStudent);
        } else {
            student.setUserId(user.getId());
            student.setCreateTime(new Date());
            if (!studentService.insert(student))
                return new ErrorTip(BizExceptionEnum.COURSE_STUDENT_CREATE.getCode(), BizExceptionEnum.COURSE_STUDENT_CREATE.getMessage());
        }

        user.setRole(RoleEum.STUDENT);
        userService.updateById(user);
        return new SuccessTip();
    }

//    @ResponseBody
//    @RequestMapping(method = RequestMethod.GET)
//    @ApiOperation(value = "查看学生信息", notes="")
//    public Tip get(){
//        User user = getUser();
//        Student student = studentService.selectOne(new EntityWrapper<Student>().eq("user_id", user.getId()));
//        if (student == null)
//            return new ErrorTip(BizExceptionEnum.COURSE_STUDENT_NULL.getCode(), BizExceptionEnum.COURSE_STUDENT_NULL.getMessage());
//        MAP = new HashMap<>();
//        SUCCESSTIP = new SuccessTip();
//        MAP.put("student", student);
//        SUCCESSTIP.setResult(MAP);
//        return SUCCESSTIP;
//    }

    @ResponseBody
    @RequestMapping(path = "/current/submissions", method = RequestMethod.GET)
    @ApiOperation(value = "学生提交的所有作业", notes = "")
    public Tip currentBubmissions(@RequestBody Student student) {
        User user = getUser();
        List<Work> works = workService.getWorksByUserId(user.getId());
        MAP = new HashMap<>();
        SUCCESSTIP = new SuccessTip();
        MAP.put("works", works);
        SUCCESSTIP.setResult(MAP);
        return SUCCESSTIP;
    }

    @ResponseBody
    @RequestMapping(path = "/current", method = RequestMethod.GET)
    @ApiOperation(value = "学生加入的所有课程", notes = "")
    public Tip current() {
        User user = getUser();
        List<Course> courses = courseService.getCoursesByUserId(user.getId());
        MAP = new HashMap<>();
        SUCCESSTIP = new SuccessTip();
        MAP.put("courses", courses);
        SUCCESSTIP.setResult(MAP);
        return SUCCESSTIP;
    }

}
