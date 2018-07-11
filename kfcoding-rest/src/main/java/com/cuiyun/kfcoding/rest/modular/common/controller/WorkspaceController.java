package com.cuiyun.kfcoding.rest.modular.common.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.core.support.http.HttpKit;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.base.controller.BaseController;
import com.cuiyun.kfcoding.rest.modular.common.model.User;
import com.cuiyun.kfcoding.rest.modular.common.model.Workspace;
import com.cuiyun.kfcoding.rest.modular.common.service.IWorkspaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Value("${workspace.release}")
    private String workSpaceRelease;

    @Autowired
    IWorkspaceService workspaceService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "工作空间", notes = "创建工作空间")
    public SuccessTip create(@RequestBody Workspace workspace) {
        workspace.setCreateTime(new Date());
        workspace.setRelease(workSpaceRelease);
        if (workspaceService.insert(workspace)) {
            SUCCESSTIP = new SuccessTip();
            MAP.put("workspace", workspace);
            SUCCESSTIP.setResult(MAP);
            return SUCCESSTIP;
        } else {
            throw new KfCodingException(BizExceptionEnum.WORKSPACE_CREATE_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "", notes = "获取当前用户的所有workspace")
    public SuccessTip getWorkspaces(HttpServletRequest request) {
        User user = getUser(request);
        EntityWrapper ew = new EntityWrapper();
        ew.eq("user_id", user.getId());
        List<Workspace> workspaces = workspaceService.selectList(ew);
        SUCCESSTIP = new SuccessTip();
        MAP = new HashMap<>();
        MAP.put("workspaces", workspaces);
        SUCCESSTIP.setResult(MAP);
        return SUCCESSTIP;
    }

    @ResponseBody
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "工作空间", notes = "创建工作空间")
    public SuccessTip create(@PathVariable String id) {
        Workspace workspace = workspaceService.selectById(id);
        if (workspace != null) {
            SUCCESSTIP = new SuccessTip();
            MAP = new HashMap<>();
            MAP.put("workspace", workspace);
            SUCCESSTIP.setResult(MAP);
            return SUCCESSTIP;
        } else {
            throw new KfCodingException(BizExceptionEnum.WORKSPACE_NULL);
        }
    }

    @ResponseBody
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除工作空间", notes = "删除工作空间")
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
