package com.cuiyun.kfcoding.rest.modular.course.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.cuiyun.kfcoding.rest.modular.course.model.Kongfu;
import com.cuiyun.kfcoding.rest.modular.course.dao.KongfuMapper;
import com.cuiyun.kfcoding.rest.modular.course.service.IKongfuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

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
    public Page<Kongfu> getKongfuByTag(@Param("id") String id){
        return this.baseMapper.getKongfuByTag(id);
    }
}
