package com.cuiyun.kfcoding.rest.modular.book.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.cuiyun.kfcoding.rest.modular.book.enums.KongfuStatusEnum;
import com.cuiyun.kfcoding.rest.modular.book.model.Kongfu;
import com.cuiyun.kfcoding.rest.modular.book.dao.KongfuMapper;
import com.cuiyun.kfcoding.rest.modular.book.service.IKongfuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author maple123
 * @since 2018-05-19
 */
@Service
public class KongfuServiceImpl extends ServiceImpl<KongfuMapper, Kongfu> implements IKongfuService {

    @Override
    public Kongfu getKongfuById(String id) {
        return this.baseMapper.getKongfuById(id);
    }

    @Override
    public Page<Kongfu> getKongfuByTag(Page<Kongfu> page, @Param("id") String id, @Param("status")KongfuStatusEnum status) {
        return page.setRecords(this.baseMapper.getKongfuByTag(page, id, status));
    }
}
