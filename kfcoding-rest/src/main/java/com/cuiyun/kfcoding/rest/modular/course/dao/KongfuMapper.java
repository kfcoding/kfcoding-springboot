package com.cuiyun.kfcoding.rest.modular.course.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.cuiyun.kfcoding.rest.modular.course.enums.KongfuStatusEnum;
import com.cuiyun.kfcoding.rest.modular.course.model.Kongfu;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author maple123
 * @since 2018-05-19
 */
public interface KongfuMapper extends BaseMapper<Kongfu> {

    /**
     * 根据ID查找功夫
     */
    Kongfu getKongfuById(@Param("id") String id);

    /**
     * 根据Tag查找功夫
     */
    List<Kongfu> getKongfuByTag(Pagination page,@Param("id") String id, @Param("status") KongfuStatusEnum status);
}
