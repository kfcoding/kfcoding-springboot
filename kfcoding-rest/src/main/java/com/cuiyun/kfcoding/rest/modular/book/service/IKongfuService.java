package com.cuiyun.kfcoding.rest.modular.book.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cuiyun.kfcoding.rest.modular.book.enums.KongfuStatusEnum;
import com.cuiyun.kfcoding.rest.modular.book.model.Kongfu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
     * 根据status查询list
     */
    List<Kongfu> findListByStatus(@Param("status") KongfuStatusEnum status);

    /**
     * 通过Id查询Kongfu
     */
    Kongfu getKongfuById(@Param("id") String id);

    /**
     * 通过tag查询Kongfu
     */
    Page<Kongfu> getKongfuByTag(Page<Kongfu> page, @Param("id") String id, @Param("status")KongfuStatusEnum status);
}
