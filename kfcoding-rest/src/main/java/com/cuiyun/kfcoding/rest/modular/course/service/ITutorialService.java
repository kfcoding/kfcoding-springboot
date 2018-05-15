package com.cuiyun.kfcoding.rest.modular.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cuiyun.kfcoding.rest.modular.course.model.Tutorial;

import java.util.List;
import java.util.Map;

/**
 * Created by carrie on 2018/5/12.
 */
public interface ITutorialService extends IService<Tutorial> {
    //获取课程列表
    public Map<String,Object> findByPage(Page<Tutorial> page);
}
