package com.cuiyun.kfcoding.rest.modular.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.cuiyun.kfcoding.rest.modular.course.model.Kongfu;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author maple123
 * @since 2018-05-19
 */
public interface IKongfuService extends IService<Kongfu> {

    /**
     * 通过Id查询Kongfu
     */
    Kongfu getKongfuById(@Param("id") String id);

    /**
     * 通过tag查询Kongfu
     */
    Page<Kongfu> getKongfuByTag(@Param("id") String id);
}
