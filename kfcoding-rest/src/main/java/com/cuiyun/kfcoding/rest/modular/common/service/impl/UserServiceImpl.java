package com.cuiyun.kfcoding.rest.modular.common.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cuiyun.kfcoding.rest.common.constant.cache.Cache;
import com.cuiyun.kfcoding.rest.common.constant.cache.CacheKey;
import com.cuiyun.kfcoding.rest.modular.common.dao.UserMapper;
import com.cuiyun.kfcoding.rest.modular.common.model.User;
import com.cuiyun.kfcoding.rest.modular.common.service.IUserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author maple123
 * @since 2018-05-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    @Cacheable(value = Cache.USER, key = "'" + CacheKey.USER + "' + #id")
    public User getUserById(String id) {
        return this.baseMapper.getUserById(id);
    }

    @Override
    @CacheEvict(value = Cache.USER, key = "'" + CacheKey.USER + "' + #p0.id")
    public boolean updateById(User entity) {
        return super.updateById(entity);
    }


    @Override
    @CacheEvict(value = Cache.USER, key="'" + CacheKey.USER + "' + #id")
    public boolean deleteById(Serializable id) {
        return super.deleteById(id);
    }
}
