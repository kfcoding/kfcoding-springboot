package com.cuiyun.kfcoding.rest.modular.course.controller;

import com.cuiyun.kfcoding.core.base.tips.ErrorTip;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.base.tips.Tip;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.course.model.Submission;
import com.cuiyun.kfcoding.rest.modular.course.service.ISubmissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @program: kfcoding
 * @description: 提交作业控制层
 * @author: maple
 * @create: 2018-07-12 22:21
 **/
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/submissions")
@Api(description = "作业提交相关接口")
public class SubmissionController {

    @Autowired
    ISubmissionService submissionService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "提交作业", notes = "")
    public Tip create(@RequestBody Submission submission) {
        submission.setCreateTime(new Date());
        if (!submissionService.insert(submission))
            return new ErrorTip(BizExceptionEnum.COURSE_SUBMISSION_CREATE.getCode(), BizExceptionEnum.COURSE_STUDENT_CREATE.getMessage());
        return new SuccessTip();
    }


}
