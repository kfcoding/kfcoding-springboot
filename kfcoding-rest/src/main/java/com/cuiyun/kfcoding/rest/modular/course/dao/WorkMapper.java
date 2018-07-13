package com.cuiyun.kfcoding.rest.modular.course.dao;

import com.cuiyun.kfcoding.rest.modular.course.model.Work;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author maple
 * @since 2018-07-12
 */
public interface WorkMapper extends BaseMapper<Work> {

    /**
     *  根据Id获取work信息
     */
    Work getWorkById(@Param("id") String id);
}
