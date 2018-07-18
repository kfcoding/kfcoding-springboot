package com.cuiyun.kfcoding.rest.modular.course.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cuiyun.kfcoding.core.base.tips.ErrorTip;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.base.tips.Tip;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.rest.common.annotion.Permission;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.base.controller.BaseController;
import com.cuiyun.kfcoding.rest.modular.common.controller.WorkspaceController;
import com.cuiyun.kfcoding.rest.modular.common.model.User;
import com.cuiyun.kfcoding.rest.modular.common.model.Workspace;
import com.cuiyun.kfcoding.rest.modular.common.service.IWorkspaceService;
import com.cuiyun.kfcoding.rest.modular.course.enums.SubmissionStatusEnum;
import com.cuiyun.kfcoding.rest.modular.course.model.Submission;
import com.cuiyun.kfcoding.rest.modular.course.model.Work;
import com.cuiyun.kfcoding.rest.modular.course.service.ISubmissionService;
import com.cuiyun.kfcoding.rest.modular.course.service.IWorkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

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
public class SubmissionController extends BaseController {

    @Autowired
    ISubmissionService submissionService;

    @Autowired
    IWorkspaceService workspaceService;

    @Autowired
    WorkspaceController workspaceController;

    @Autowired
    IWorkService workService;



    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "做作业", notes = "")
    @Transactional
    @Permission
    public Tip create(@RequestBody Submission submission) throws UnsupportedEncodingException {
        User user = getUser();
        submission.setCreateTime(new Date());
        submission.setUserId(user.getId());
        Submission targetSubmission = submissionService.selectOne(new EntityWrapper<Submission>().eq("user_id", user.getId()).eq("work_id", submission.getWorkId()));

        Work work = workService.getWorkById(submission.getWorkId());
        if (work == null)
            return new ErrorTip(BizExceptionEnum.COURSE_WORK_NULL.getCode(), BizExceptionEnum.COURSE_WORK_NULL.getMessage());


        MAP = new HashMap<>();
        SUCCESSTIP = new SuccessTip();
        // 不存在则创建新的。
        if (targetSubmission == null) {
            Workspace workspace = new Workspace();
            workspace.setGitUrl(work.getRepo());
//            workspace.setRepo(work.getRepo());
            workspace.setImage(work.getImage());
            workspace.setTitle(work.getName());
            Workspace targetWorkspace = workspaceController.createWorkSpace(workspace);
            if (targetWorkspace == null)
                throw new KfCodingException(BizExceptionEnum.WORKSPACE_CREATE_ERROR);
            submission.setWorkspaceId(workspace.getId());
            if (!submissionService.insert(submission))
                return new ErrorTip(BizExceptionEnum.COURSE_SUBMISSION_CREATE.getCode(), BizExceptionEnum.COURSE_STUDENT_CREATE.getMessage());
            MAP.put("workspaceId", workspace.getId());
        } else { // 存在则直接返回
            Workspace workspace = workspaceService.selectById(targetSubmission.getWorkspaceId());
            if (workspace == null)
                return new ErrorTip(BizExceptionEnum.WORKSPACE_NULL.getCode(), BizExceptionEnum.WORKSPACE_NULL.getMessage());
            MAP.put("workspaceId", workspace.getId());
        }
        SUCCESSTIP.setResult(MAP);
        return SUCCESSTIP;
    }

    @ResponseBody
    @RequestMapping(path = "/submit",method = RequestMethod.GET)
    @ApiOperation(value = "提交作业", notes = "根据workspaceId提交")
    @Permission
    public Tip sumbit(@RequestParam("workspace_id") String workspaceId) {
        Submission submission = submissionService.selectOne(new EntityWrapper<Submission>().eq("workspace_id", workspaceId));
        if (submission == null)
            return new ErrorTip(BizExceptionEnum.COURSE_SUBMISSION_NULL.getCode(), BizExceptionEnum.COURSE_SUBMISSION_NULL.getMessage());
        submission.setStatus(SubmissionStatusEnum.SUBMIT);
        submissionService.updateById(submission);
        return new SuccessTip();
    }


}
