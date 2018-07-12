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
@TableName("course_work")
public class Work extends BaseModel<Work> {

    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private String status;
    @TableField("course_id")
    private String courseId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Work{" +
        ", name=" + name +
        ", description=" + description +
        ", status=" + status +
        ", courseId=" + courseId +
        "}";
    }
}
