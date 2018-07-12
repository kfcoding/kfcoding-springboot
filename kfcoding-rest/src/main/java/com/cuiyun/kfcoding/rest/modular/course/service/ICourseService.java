package com.cuiyun.kfcoding.rest.modular.course.service;

import com.cuiyun.kfcoding.rest.modular.course.model.Course;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

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

}
