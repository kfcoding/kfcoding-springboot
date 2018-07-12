package com.cuiyun.kfcoding.rest.modular.course.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cuiyun.kfcoding.rest.modular.course.dao.CourseMapper;
import com.cuiyun.kfcoding.rest.modular.course.model.Course;
import com.cuiyun.kfcoding.rest.modular.course.service.ICourseService;
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
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    /**
     *  根据Id查找课程
     */
    @Override
    public Course getCourseById(String id) {
        return this.baseMapper.getCourseById(id);
    }
}
