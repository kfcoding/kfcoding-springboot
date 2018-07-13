package com.cuiyun.kfcoding.rest.modular.course.service;

import com.baomidou.mybatisplus.service.IService;
import com.cuiyun.kfcoding.rest.modular.course.model.Work;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author maple
 * @since 2018-07-12
 */
public interface IWorkService extends IService<Work> {
    /**
     *  根据ID获取作业
     */
    Work getWorkById(@Param("id") String id);

    /**
     * 根据userId获取作业列表
     */
    List<Work> getWorksByUserId(@Param("id") String id);
}
