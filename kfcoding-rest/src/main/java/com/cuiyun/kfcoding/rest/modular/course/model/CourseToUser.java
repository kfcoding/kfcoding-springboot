package com.cuiyun.kfcoding.rest.modular.course.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.cuiyun.kfcoding.rest.modular.base.Model.BaseModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author maple
 * @since 2018-07-12
 */
@TableName("course_course_to_user")
public class CourseToUser extends BaseModel<CourseToUser> {

    private static final long serialVersionUID = 1L;

    @TableField("course_id")
    private String courseId;
    @TableField("user_id")
    private String userId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CourseToUser{" +
        ", courseId=" + courseId +
        ", userId=" + userId +
        "}";
    }
}
