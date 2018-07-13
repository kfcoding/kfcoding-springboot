package com.cuiyun.kfcoding.rest.modular.common.service;

import com.cuiyun.kfcoding.rest.modular.common.model.Workspace;
import com.baomidou.mybatisplus.service.IService;
import com.cuiyun.kfcoding.rest.modular.course.model.Work;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author maple
 * @since 2018-07-01
 */
public interface IWorkspaceService extends IService<Workspace> {

    /**
     *  根据Id查找作业
     */
    Work getWorkById(@Param("id") String id);
}
