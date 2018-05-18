package com.cuiyun.kfcoding.rest.modular.course.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cuiyun.kfcoding.core.base.controller.BaseController;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.cache.ICache;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.course.model.Tutorial;
import com.cuiyun.kfcoding.rest.modular.course.service.ITutorialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by carrie on 2018/5/12.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/tutorial")
@Api(description = "course相关接口")
public class TutorialController extends BaseController{
    @Autowired
    ITutorialService tutorialService;
    Map<String,Object> map = new HashMap<String, Object>();

    @ResponseBody
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "创建课程", notes="")
    public SuccessTip create(@RequestParam Tutorial tutorial){
        boolean flag = tutorialService.insert(tutorial);
        if(flag){
            return SUCCESSTIP;
        }else{
            throw new KfCodingException(BizExceptionEnum.COURSE_CREAT_ERROR);
        }

    }

    @ResponseBody
    @RequestMapping(path = "/findByPage", method = RequestMethod.GET)
    @ApiOperation(value = "课程列表", notes="")
    public SuccessTip findByPage(@RequestParam Integer current,@RequestParam Integer size){
        /*Page<Tutorial> page = new Page<Tutorial>(current,size);
        Map<String,Object> map = tutorialService.findByPage(page);
        */
        Page<Tutorial> tutorialPage = tutorialService.selectPage(new Page<>(current,size),new EntityWrapper<Tutorial>());
        if (tutorialPage.getTotal()!=0){
            map.put("tutorialPage",tutorialPage);
            SUCCESSTIP.setResult(map);
            return SUCCESSTIP;
        }else{
            throw new KfCodingException(BizExceptionEnum.COURSE_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(path = "/findByUserId", method = RequestMethod.GET)
    @ApiOperation(value = "用户课程列表", notes="列出该用户创建的所有课程")
    public SuccessTip findByUserId(@RequestParam Integer current, @RequestParam Integer size, @RequestParam Integer userId){
        /*Page<Tutorial> page = new Page<Tutorial>(current,size);
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("user_id",userId);
        page.setCondition(condition);
        Map<String,Object> map = tutorialService.findByPage(page);
        */
        Page<Tutorial> tutorialPage = tutorialService.selectPage(new Page<>(current,size),new EntityWrapper<Tutorial>().eq("user_id",userId));
        if (tutorialPage.getTotal()!=0){
            map.put("tutorialPage",tutorialPage);
            SUCCESSTIP.setResult(map);
            return SUCCESSTIP;
        }else{
            throw new KfCodingException(BizExceptionEnum.COURSE_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(path = "/findAllByUserId", method = RequestMethod.GET)
    @ApiOperation(value = "用户课程列表", notes="列出该用户创建的所有课程")
    public SuccessTip findAllByUserId(@RequestParam Integer userId){
        List list = tutorialService.selectList(new EntityWrapper<Tutorial>().eq("user_id", userId));
        Map map = new HashMap();
        map.put("courses", list);
        SUCCESSTIP.setResult(map);
        return SUCCESSTIP;
//        if(!(map.isEmpty())){
//            SUCCESSTIP.setResult(map);
//            return SUCCESSTIP;
//        }else{
//            throw new KfCodingException(BizExceptionEnum.COURSE_ERROR);
//        }
    }
}
