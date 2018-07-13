package com.cuiyun.kfcoding.rest.modular.course.dao;

import com.cuiyun.kfcoding.rest.modular.course.model.Student;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author maple
 * @since 2018-07-12
 */
public interface StudentMapper extends BaseMapper<Student> {
    Student getByUserId(@Param("id") String id);

    Student getBySubmissionId(@Param("id") String id);
}
