package com.cuiyun.kfcoding.rest.modular.book.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.rest.common.annotion.BussinessLog;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.auth.util.JwtTokenUtil;
import com.cuiyun.kfcoding.rest.modular.base.controller.BaseController;
import com.cuiyun.kfcoding.rest.modular.book.enums.KongfuStatusEnum;
import com.cuiyun.kfcoding.rest.modular.book.model.Kongfu;
import com.cuiyun.kfcoding.rest.modular.book.model.KongfuToTag;
import com.cuiyun.kfcoding.rest.modular.book.model.Tag;
import com.cuiyun.kfcoding.rest.modular.book.service.IKongfuService;
import com.cuiyun.kfcoding.rest.modular.book.service.IKongfuToTagService;
import com.cuiyun.kfcoding.rest.modular.book.service.ITagService;
import com.cuiyun.kfcoding.rest.modular.common.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by carrie on 2018/5/12.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/kongfu")
@Api(description = "功夫相关接口")
public class KongfuController extends BaseController {
    @Autowired
    IKongfuService kongfuService;

    @Autowired
    IKongfuToTagService kongfuToTagService;

    @Autowired
    ITagService tagService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @ResponseBody
    @BussinessLog(value = "创建课程")
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "创建课程", notes="")
    @Transactional
    public SuccessTip create(@RequestBody Kongfu kongfu){
        User user = getUser();
        kongfu.setAuthor(user.getName());
        kongfu.setUserId(user.getId());
        List<Tag> tags = kongfu.getTags();
        kongfuService.insert(kongfu);
        List<KongfuToTag> kongfuToTags = setKongfuToTag(tags, kongfu.getId());
        boolean flag = kongfuToTagService.insertBatch(kongfuToTags);
        MAP = new HashMap<>();
        SUCCESSTIP = new SuccessTip();
        if(flag){
            return SUCCESSTIP;
        }else{
            throw new KfCodingException(BizExceptionEnum.BOOK_CREAT_ERROR);
        }
    }

    @ResponseBody
    @BussinessLog(value = "修改课程")
    @RequestMapping(path = "/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "修改课程", notes="")
    @Transactional
    public SuccessTip update(@RequestBody Kongfu kongfu){
        User user = getUser();
        kongfu.setAuthor(user.getName());
        kongfu.setUserId(user.getId());

        // 删除这个课程和tag的对应关系
        EntityWrapper ew = new EntityWrapper<>();
        ew.eq("kongfu_id", kongfu.getId());
        kongfuToTagService.delete(ew);

        // 添加这个课程和tag的对应关系
        List<Tag> tags = kongfu.getTags();
        kongfuService.updateById(kongfu);
        List<KongfuToTag> kongfuToTags = setKongfuToTag(tags, kongfu.getId());
        boolean flag = kongfuToTagService.insertBatch(kongfuToTags);
        MAP = new HashMap<>();
        SUCCESSTIP = new SuccessTip();
        if(flag){
            return SUCCESSTIP;
        }else{
            throw new KfCodingException(BizExceptionEnum.BOOK_CREAT_ERROR);
        }
    }

    private List<KongfuToTag> setKongfuToTag(List<Tag> tags, String kongfuId){
        List<KongfuToTag> kongfuToTags = new ArrayList<>();
        KongfuToTag kongfuToTag;
        for (Tag tag: tags) {
            kongfuToTag = new KongfuToTag();
            kongfuToTag.setKongfuId(kongfuId);
            kongfuToTag.setTagId(tag.getId());
            kongfuToTags.add(kongfuToTag);
        }
        return kongfuToTags;
    }

    @ResponseBody
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取某一个功夫的信息", notes="")
    public SuccessTip create(@PathVariable String id){
        Kongfu kongfu = kongfuService.getKongfuById(id);
        if (kongfu == null) {
            throw new KfCodingException(BizExceptionEnum.BOOK_ERROR);
        }
        MAP = new HashMap<>();
        SUCCESSTIP = new SuccessTip();
        MAP.put("kongfu", kongfu);
        SUCCESSTIP.setResult(MAP);
        return SUCCESSTIP;
    }

    @ResponseBody
    @RequestMapping(path = "/findByPage", method = RequestMethod.GET)
    @ApiOperation(value = "课程列表", notes="")
    public SuccessTip findByPage(Page page) {
        Page tutorialPage = kongfuService.selectPage(page);
        MAP = new HashMap<>();
        SUCCESSTIP = new SuccessTip();
        if (tutorialPage.getTotal() != 0) {
            MAP.put("kongfuPage", tutorialPage);
            SUCCESSTIP.setResult(MAP);
            return SUCCESSTIP;
        } else {
            throw new KfCodingException(BizExceptionEnum.BOOK_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "课程列表", notes="")
    public SuccessTip list(Page page) {
//        EntityWrapper ew = new EntityWrapper<Kongfu>();
//        ew.eq("status", KongfuStatusEnum.PUBLIC);
//        List list = kongfuService.selectList(ew);
        List list = kongfuService.findListByStatus(KongfuStatusEnum.PUBLIC);
        MAP = new HashMap<>();
        SUCCESSTIP = new SuccessTip();
        MAP.put("kongfus", list);
        SUCCESSTIP.setResult(MAP);
        return SUCCESSTIP;
    }

    @ResponseBody
    @RequestMapping(path = "/taglist", method = RequestMethod.GET)
    @ApiOperation(value = "标签列表", notes="")
    public SuccessTip Taglist() {
        List taglist = tagService.selectList(new EntityWrapper<>());
        MAP = new HashMap<>();
        SUCCESSTIP = new SuccessTip();
        MAP.put("taglist", taglist);
        SUCCESSTIP.setResult(MAP);
        return SUCCESSTIP;
    }

    @ResponseBody
    @RequestMapping(path = "/findByTag", method = RequestMethod.GET)
    @ApiOperation(value = "按tag获取课程列表", notes="")
    public SuccessTip findByTag(Page<Kongfu> page, @RequestParam(value="tag") String id) {
        Page<Kongfu> kongfuList = kongfuService.getKongfuByTag(page, id, KongfuStatusEnum.PUBLIC);
        MAP = new HashMap<>();
        SUCCESSTIP = new SuccessTip();
        if (kongfuList.getSize() != 0) {
            MAP.put("kongfuList", kongfuList);
            SUCCESSTIP.setResult(MAP);
            return SUCCESSTIP;
        } else {
            throw new KfCodingException(BizExceptionEnum.BOOK_ERROR);
        }
    }

    @ResponseBody
    @BussinessLog(value = "删除课程")
    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "课程Id", notes="根据Id删除课程")
    public SuccessTip deleteById(@RequestParam String id) {
        if (kongfuService.deleteById(id)){
            return SUCCESSTIP;
        } else {
            throw new KfCodingException(BizExceptionEnum.BOOK_ERROR);
        }
    }
}
