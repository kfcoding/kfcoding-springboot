package com.cuiyun.kfcoding.rest.modular.course.service;

import com.baomidou.mybatisplus.service.IService;
import com.cuiyun.kfcoding.rest.modular.course.model.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author maple
 * @since 2018-07-12
 */
public interface ICourseService extends IService<Course> {

    /**
     *  根据id查找课程
     */
    Course getCourseById(@Param("id") String id);

    /**
     *  根据用户ID获取课程列表
     */
    List<Course> getCoursesByUserId(@Param("id") String id);
}
