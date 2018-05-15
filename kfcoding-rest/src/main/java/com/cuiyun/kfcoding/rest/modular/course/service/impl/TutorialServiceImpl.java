package com.cuiyun.kfcoding.rest.modular.course.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cuiyun.kfcoding.rest.modular.course.dao.TutorialMapper;
import com.cuiyun.kfcoding.rest.modular.course.model.Tutorial;
import com.cuiyun.kfcoding.rest.modular.course.service.ITutorialService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by carrie on 2018/5/12.
 */
@Service
public class TutorialServiceImpl extends ServiceImpl<TutorialMapper, Tutorial> implements ITutorialService{

    //将结果存入缓存
    @CachePut(value = "tutorials")
    //获取课程列表
    public Map<String,Object> findByPage(Page<Tutorial> page){
        Map<String,Object> map = new HashMap<String, Object>();
        Page<Tutorial> tutorialList = new Page<Tutorial>();
        if(page.getCondition()==null){
            tutorialList = selectPage(new Page<>(page.getCurrent(),page.getSize()),new EntityWrapper<Tutorial>());
        }else if(page.getCondition().get("user_id")!=null){
            tutorialList = selectPage(new Page<>(page.getCurrent(),page.getSize()),new EntityWrapper<Tutorial>().eq("user_id",page.getCondition().get("user_id")));
        }else if(page.getCondition().get("type")!=null){

        }
        map.put("tutorialList",tutorialList);
        return map;
    }
}
