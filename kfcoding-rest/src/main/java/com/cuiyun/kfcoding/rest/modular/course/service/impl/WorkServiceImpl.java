package com.cuiyun.kfcoding.rest.modular.course.service.impl;

import com.cuiyun.kfcoding.rest.modular.course.model.Work;
import com.cuiyun.kfcoding.rest.modular.course.dao.WorkMapper;
import com.cuiyun.kfcoding.rest.modular.course.service.IWorkService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author maple
 * @since 2018-07-12
 */
@Service
public class WorkServiceImpl extends ServiceImpl<WorkMapper, Work> implements IWorkService {

    @Override
    public Work getWorkById(String id) {
        return this.baseMapper.getWorkById(id);
    }
}
