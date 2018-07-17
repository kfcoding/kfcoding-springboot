package com.cuiyun.kfcoding.rest.modular.course.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cuiyun.kfcoding.core.base.tips.ErrorTip;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.base.tips.Tip;
import com.cuiyun.kfcoding.core.support.http.HttpKit;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.base.controller.BaseController;
import com.cuiyun.kfcoding.rest.modular.common.model.User;
import com.cuiyun.kfcoding.rest.modular.common.service.IWorkspaceService;
import com.cuiyun.kfcoding.rest.modular.course.model.Submission;
import com.cuiyun.kfcoding.rest.modular.course.model.Work;
import com.cuiyun.kfcoding.rest.modular.course.service.ISubmissionService;
import com.cuiyun.kfcoding.rest.modular.course.service.IWorkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    IWorkService workspace;

    @Value("${workspace.domain}")
    private String domain;

    @Value("${workspace.release}")
    private String release;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "提交作业", notes = "")
    public Tip create(@RequestBody Submission submission) throws UnsupportedEncodingException {
        User user = getUser();
        StringBuffer sb = new StringBuffer();
        sb.append(domain).append("/workspace/vlolume");
        Work work = workspace.getWorkById(submission.getWorkId());
        if (work == null)
            return new ErrorTip(BizExceptionEnum.COURSE_WORK_NULL.getCode(), BizExceptionEnum.COURSE_WORK_NULL.getMessage());

        submission.setCreateTime(new Date());
        submission.setUserId(user.getId());
        Submission targetSubmission = submissionService.selectOne(new EntityWrapper<Submission>().eq("user_id", user.getId()).eq("work_id", submission.getWorkId()));
        if (targetSubmission == null){
            if (!submissionService.insert(submission))
                return new ErrorTip(BizExceptionEnum.COURSE_SUBMISSION_CREATE.getCode(), BizExceptionEnum.COURSE_STUDENT_CREATE.getMessage());

            Map<String, String> params = new HashMap<String, String>();
            params.put("name", submission.getId());
            params.put("repo", submission.getRepo());
            HttpKit.post(sb.toString(), params);
        } else {
            targetSubmission.setRepo(submission.getRepo());
            targetSubmission.setWorkId(submission.getWorkId());
            targetSubmission.setImage(work.getImage());
            submissionService.updateById(targetSubmission);
        }
        return new SuccessTip();
    }

}
