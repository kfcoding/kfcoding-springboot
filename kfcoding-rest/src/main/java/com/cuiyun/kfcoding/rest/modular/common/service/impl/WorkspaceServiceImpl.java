package com.cuiyun.kfcoding.rest.modular.common.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cuiyun.kfcoding.rest.modular.common.dao.WorkspaceMapper;
import com.cuiyun.kfcoding.rest.modular.common.model.Workspace;
import com.cuiyun.kfcoding.rest.modular.common.service.IWorkspaceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author maple
 * @since 2018-07-01
 */
@Service
public class WorkspaceServiceImpl extends ServiceImpl<WorkspaceMapper, Workspace> implements IWorkspaceService {

    @Override
    public List<Workspace> getWorkspacesByUserId(String userId) {
        return this.baseMapper.getWorkspacesByUserId(userId);
    }

}
