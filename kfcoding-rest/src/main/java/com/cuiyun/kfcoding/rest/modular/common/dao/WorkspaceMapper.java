package com.cuiyun.kfcoding.rest.modular.common.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cuiyun.kfcoding.rest.modular.common.model.Workspace;
import com.cuiyun.kfcoding.rest.modular.course.model.Work;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author maple
 * @since 2018-07-01
 */
public interface WorkspaceMapper extends BaseMapper<Workspace> {

    Work getWorkById(@Param("id") String id);


}
