package com.cuiyun.kfcoding.rest.modular.common.controller;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cuiyun.kfcoding.core.base.tips.ErrorTip;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.base.tips.Tip;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.core.support.http.HttpKit;
import com.cuiyun.kfcoding.rest.common.annotion.BussinessLog;
import com.cuiyun.kfcoding.rest.common.annotion.Permission;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @program: kfcoding
 * @description: 工作空间控制层
 * @author: maple
 * @create: 2018-07-01 19:45
 **/
@RestController
@RequestMapping("/workspaces")
@CrossOrigin(origins = "*")
@Api(description = "工作空间相关接口")
public class WorkspaceController extends BaseController {

    @Value("${workspace.deleteUrl}")
    private String deleteUrl;

    @Value("${workspace.createUrl}")
    private String createUrl;

    @Value("${workspace.release}")
    private String workSpaceRelease;

    @Value("${workspace.startUrl}")
    private String startUrl;

    @Autowired
    IWorkspaceService workspaceService;

    @Autowired
    ISubmissionService submissionService;

    @ResponseBody
    @BussinessLog(value = "创建工作空间")
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "工作空间", notes = "创建工作空间")
    @Permission
    public Tip create(@RequestBody Workspace workspace) throws UnsupportedEncodingException {
        Workspace targetWorkspace = createWorkSpace(workspace);
        if (targetWorkspace != null) {
            MAP.put("workspace", targetWorkspace);
            SUCCESSTIP.setResult(MAP);
            return SUCCESSTIP;
        } else {
            throw new KfCodingException(BizExceptionEnum.WORKSPACE_CREATE_ERROR);
        }
    }

    public Workspace createWorkSpace(Workspace workspace) throws UnsupportedEncodingException {
        User user = getUser();
        workspace.setUserId(user.getId());
        workspace.setCreateTime(new Date());
        workspace.setRelease(workSpaceRelease);

        Map params = new HashMap();
        params.put("image", workspace.getImage());
        Map<String, String> header = new HashMap<String, String>();
//        HttpResult httpResult = HttpKit.postResult(createUrl, JSON.toJSONString(params), header);
//        String result = httpResult.getResult();
//        String url
        System.err.println(JSON.toJSONString(params));
        String result = HttpRequest.post(createUrl).header(Header.CONTENT_TYPE, "application/json").body(JSON.toJSONString(params)).execute().body();
        Map resultMap = JSON.parseObject(result);
        if (resultMap.containsKey("error"))
            throw new KfCodingException(BizExceptionEnum.WORKSPACE_SERVER);
        workspace.setContainerName((String) resultMap.get("data"));
        if (workspaceService.insert(workspace)) {
            return workspace;
        } else {
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    @Permission
    @ApiOperation(value = "获取workspace", notes = "获取当前用户的所有workspace")
    public SuccessTip getWorkspaces() {
        User user = getUser();
        SUCCESSTIP = new SuccessTip();
        MAP = new HashMap<>();

        List workspaces = workspaceService.getWorkspacesByUserId(user.getId());
        MAP.put("workspaces", workspaces);

        SUCCESSTIP.setResult(MAP);
        return SUCCESSTIP;
    }

    @ResponseBody
    @RequestMapping(path = "/{id}/start", method = RequestMethod.GET)
    @ApiOperation(value = "开启工作空间", notes = "开启工作空间")
    public Tip start(@PathVariable String id) {
        Workspace workspace = workspaceService.selectById(id);
        if (workspace != null) {
            Map params = new HashMap();
            params.put("name", workspace.getContainerName());
            String result = HttpRequest.post(startUrl).body(JSON.toJSONString(params)).execute().body();
            Map resultMap = (Map) JSON.parse(result);
            if (resultMap.containsKey("error"))
                return new ErrorTip(BizExceptionEnum.WORKSPACE_SERVER.getCode(), BizExceptionEnum.WORKSPACE_SERVER.getMessage());
            StringBuffer sb = new StringBuffer();
            sb.append("http://").append(workspace.getContainerName()).append(".workspace.cloudwarehub.com");
            MAP.put("socketAddr", sb.toString());
            SUCCESSTIP.setResult(MAP);
            return SUCCESSTIP;
        } else {
            throw new KfCodingException(BizExceptionEnum.WORKSPACE_NULL);
        }
    }
    @ResponseBody
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "工作空间", notes = "获取工作空间")
    public SuccessTip create(@PathVariable String id) {
        Workspace workspace = workspaceService.selectById(id);
        MAP = new HashMap<>();
        SUCCESSTIP = new SuccessTip();
        if (workspace != null) {
            Submission submission = submissionService.selectOne(new EntityWrapper<Submission>().eq("workspace_id", id));
            if (submission != null){
                MAP.put("submission", submission);
            }
            MAP.put("workspace", workspace);
            SUCCESSTIP.setResult(MAP);
            return SUCCESSTIP;
        } else {
            throw new KfCodingException(BizExceptionEnum.WORKSPACE_NULL);
        }
    }



    @ResponseBody
    @BussinessLog(value = "删除工作空间")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除工作空间", notes = "删除工作空间")
    @Permission
    public SuccessTip delete(@PathVariable String id) throws UnsupportedEncodingException {
        if (!workspaceService.deleteById(id))
            throw new KfCodingException(BizExceptionEnum.WORKSPACE_DELETE);
        Map<String, String> param = new HashMap<String, String>();
        param.put("name", id);
        HttpKit.post(deleteUrl, param);
        SUCCESSTIP = new SuccessTip();
        return SUCCESSTIP;
    }

}
