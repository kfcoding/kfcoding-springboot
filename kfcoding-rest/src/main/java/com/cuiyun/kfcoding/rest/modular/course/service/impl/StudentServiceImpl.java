package com.cuiyun.kfcoding.rest.modular.course.service.impl;

import com.cuiyun.kfcoding.rest.modular.course.model.Student;
import com.cuiyun.kfcoding.rest.modular.course.dao.StudentMapper;
import com.cuiyun.kfcoding.rest.modular.course.service.IStudentService;
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
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
