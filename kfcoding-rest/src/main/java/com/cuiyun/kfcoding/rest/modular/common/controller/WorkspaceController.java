package com.cuiyun.kfcoding.rest.modular.common.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.base.controller.BaseController;
import com.cuiyun.kfcoding.rest.modular.common.model.User;
import com.cuiyun.kfcoding.rest.modular.common.model.Workspace;
import com.cuiyun.kfcoding.rest.modular.common.service.IWorkspaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

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

    @Autowired
    IWorkspaceService workspaceService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "工作空间", notes = "创建工作空间")
    public SuccessTip create(@RequestBody Workspace workspace) {
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
    public SuccessTip delete(@PathVariable String id) {
        if (!workspaceService.deleteById(id))
            throw new KfCodingException(BizExceptionEnum.WORKSPACE_DELETE);

        SUCCESSTIP = new SuccessTip();
        return SUCCESSTIP;
    }

}
