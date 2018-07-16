package com.cuiyun.kfcoding.rest.modular.course.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cuiyun.kfcoding.core.base.tips.ErrorTip;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.base.tips.Tip;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.base.controller.BaseController;
import com.cuiyun.kfcoding.rest.modular.common.model.User;
import com.cuiyun.kfcoding.rest.modular.common.model.Workspace;
import com.cuiyun.kfcoding.rest.modular.common.service.IWorkspaceService;
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
public class SubmissionController extends BaseController{

    @Autowired
    ISubmissionService submissionService;

    @Autowired
    IWorkspaceService workspaceService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "提交作业", notes = "")
    public Tip create(@RequestBody Submission submission) {
        User user = getUser();
        submission.setCreateTime(new Date());
        submission.setUserId(user.getId());
        Submission targetSubmission = submissionService.selectOne(new EntityWrapper<Submission>().eq("user_id", user.getId()).eq("work_id", submission.getWorkId()));
        if (targetSubmission == null){
            if (!submissionService.insert(submission))
                return new ErrorTip(BizExceptionEnum.COURSE_SUBMISSION_CREATE.getCode(), BizExceptionEnum.COURSE_STUDENT_CREATE.getMessage());
        } else {
            targetSubmission.setRepo(submission.getRepo());
            targetSubmission.setWorkId(submission.getWorkId());
            targetSubmission.setWorkspaceId(submission.getWorkspaceId());
            Workspace workspace = workspaceService.selectById(submission.getWorkspaceId());
            if (workspace == null)
                return new ErrorTip(BizExceptionEnum.WORKSPACE_NULL.getCode(), BizExceptionEnum.WORKSPACE_NULL.getMessage());
            targetSubmission.setImage(workspace.getImage());
            submissionService.updateById(targetSubmission);
        }
        return new SuccessTip();
    }


}
