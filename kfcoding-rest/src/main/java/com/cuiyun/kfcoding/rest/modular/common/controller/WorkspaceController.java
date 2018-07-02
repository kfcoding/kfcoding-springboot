package com.cuiyun.kfcoding.rest.modular.common.controller;

import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.base.controller.BaseController;
import com.cuiyun.kfcoding.rest.modular.common.model.Workspace;
import com.cuiyun.kfcoding.rest.modular.common.service.IWorkspaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @program: kfcoding
 * @description: 工作空间控制层
 * @author: maple
 * @create: 2018-07-01 19:45
 **/
@RestController
@RequestMapping("/workspace")
@CrossOrigin(origins = "*")
@Api(description = "工作空间相关接口")
public class WorkspaceController extends BaseController {

    @Autowired
    IWorkspaceService workspaceService;

    @ResponseBody
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "工作空间", notes = "创建工作空间")
    public SuccessTip create(@RequestBody Workspace workspace) {
        if (workspaceService.insert(workspace)) {
            SUCCESSTIP = new SuccessTip();
            map.put("workspace", workspace);
            SUCCESSTIP.setResult(map);
            return SUCCESSTIP;
        } else {
            throw new KfCodingException(BizExceptionEnum.WORKSPACE_CREATE_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "工作空间", notes = "创建工作空间")
    public SuccessTip create(@PathVariable String id) {
        Workspace workspace = workspaceService.selectById(id);
        if (workspace != null) {
            SUCCESSTIP = new SuccessTip();
            map = new HashMap<>();
            map.put("workspace", workspace);
            SUCCESSTIP.setResult(map);
            return SUCCESSTIP;
        } else {
            throw new KfCodingException(BizExceptionEnum.WORKSPACE_NULL);
        }
    }

}
