package com.cuiyun.kfcoding.rest.modular.course.controller;

import com.cuiyun.kfcoding.core.base.tips.ErrorTip;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.base.tips.Tip;
import com.cuiyun.kfcoding.rest.common.annotion.Permission;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.base.controller.BaseController;
import com.cuiyun.kfcoding.rest.modular.course.model.Work;
import com.cuiyun.kfcoding.rest.modular.course.service.IWorkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @program: kfcoding
 * @description: 作业控制类
 * @author: maple
 * @create: 2018-07-12 16:05
 **/
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/works")
@Api(description = "作业相关接口")
public class WorkController extends BaseController{

    @Autowired
    IWorkService workService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "创建作业", notes="")
    @Permission
    public Tip create(@RequestBody Work work){
        work.setCreateTime(new Date());
        if (work.getCourseId() == null)
            return new ErrorTip(BizExceptionEnum.COURSE_WORK_CREATE.getCode(), BizExceptionEnum.COURSE_WORK_CREATE.getMessage());
        if (!workService.insert(work))
            return new ErrorTip(BizExceptionEnum.COURSE_WORK_CREATE.getCode(), BizExceptionEnum.COURSE_WORK_CREATE.getMessage());
        return new SuccessTip();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "修改作业", notes="")
    @Permission
    public Tip update(@RequestBody Work work){
        Work targetWork = workService.selectById(work.getId());
        if (targetWork == null)
            return new ErrorTip(BizExceptionEnum.COURSE_WORK_NULL.getCode(), BizExceptionEnum.COURSE_WORK_NULL.getMessage());
//        BeanUtil.copyProperties();
        targetWork.setName(work.getName());
        targetWork.setDescription(work.getDescription());
        targetWork.setStartTime(work.getStartTime());
        targetWork.setEndTime(work.getEndTime());
        targetWork.setRepo(work.getRepo());
        targetWork.setImage(work.getImage());
        if (!workService.updateById(targetWork))
            return new ErrorTip(BizExceptionEnum.COURSE_WORK_UPDATE.getCode(), BizExceptionEnum.COURSE_WORK_UPDATE.getMessage());
        return new SuccessTip();
    }

    @ResponseBody
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取作业信息", notes="")
    public Tip get(@PathVariable String id){
        Work work = workService.getWorkById(id);
        if (work == null)
            return new ErrorTip(BizExceptionEnum.COURSE_WORK_NULL.getCode(), BizExceptionEnum.COURSE_WORK_NULL.getMessage());
        MAP.put("work", work);
        SUCCESSTIP.setResult(MAP);
        return SUCCESSTIP;
    }

}
