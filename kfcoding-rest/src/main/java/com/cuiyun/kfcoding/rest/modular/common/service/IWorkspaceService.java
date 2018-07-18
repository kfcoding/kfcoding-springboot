package com.cuiyun.kfcoding.rest.modular.common.service;

import com.baomidou.mybatisplus.service.IService;
import com.cuiyun.kfcoding.rest.modular.common.model.Workspace;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
     * 根据用户Id获取工作空间（去除submission）
     */
    List<Workspace> getWorkspacesByUserId(@Param("user_id") String userId);

}
