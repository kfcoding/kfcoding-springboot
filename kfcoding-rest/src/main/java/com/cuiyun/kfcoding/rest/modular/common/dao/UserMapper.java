package com.cuiyun.kfcoding.rest.modular.common.dao;

import com.cuiyun.kfcoding.rest.modular.common.model.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author maple123
 * @since 2018-05-19
 */
public interface UserMapper extends BaseMapper<User> {

    User getUserById(@Param("id") String id);
}
