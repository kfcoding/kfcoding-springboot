package com.cuiyun.kfcoding.rest.modular.common.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cuiyun.kfcoding.rest.modular.common.model.User;
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
public interface UserMapper extends BaseMapper<User> {

    User getUserById(@Param("id") String id);

    /**
     *  根据课程ID得到用户列表
     */
    List<User> getUsersByCourseId(@Param("id") String id);
}
