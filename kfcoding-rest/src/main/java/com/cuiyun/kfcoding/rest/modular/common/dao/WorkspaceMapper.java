package com.cuiyun.kfcoding.rest.modular.common.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cuiyun.kfcoding.rest.modular.common.model.Workspace;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author maple
 * @since 2018-07-01
 */
public interface WorkspaceMapper extends BaseMapper<Workspace> {

    /**
     * 根据用户Id获取工作空间（去除submission）
     */
    List<Workspace> getWorkspacesByUserId(@Param("user_id") String userId);

}
