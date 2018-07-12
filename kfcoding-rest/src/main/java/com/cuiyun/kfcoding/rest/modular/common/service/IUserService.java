package com.cuiyun.kfcoding.rest.modular.common.service;

import com.cuiyun.kfcoding.rest.modular.common.model.User;
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
public interface IUserService extends IService<User> {

    /**
     *  通过ID查找用户
     */
    User getUserById(@Param("id") String id);
}
