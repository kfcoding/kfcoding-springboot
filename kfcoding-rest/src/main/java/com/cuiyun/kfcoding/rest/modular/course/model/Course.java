package com.cuiyun.kfcoding.rest.modular.course.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.cuiyun.kfcoding.rest.modular.base.Model.BaseModel;

import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author maple
 * @since 2018-07-12
 */
@TableName("course_course")
public class Course extends BaseModel<Course> {

    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    @TableField("user_id")
    private String userId;
    private String status;
    /**
     *  班级
     */
    @TableField(exist=false)
    private List<Klass> klasses;
    /**
     *  作业
     */
    @TableField(exist=false)
    private List<Work> works;

    public List<Work> getWorks() {
        return works;
    }

    public void setWorks(List<Work> works) {
        this.works = works;
    }

    public List<Klass> getKlasses() {
        return klasses;
    }


    public void setKlasses(List<Klass> klasses) {
        this.klasses = klasses;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Course{" +
        "id=" + getId() +
        ", createTime=" + getCreateTime() +
        ", updateTime=" + getUpdateTime() +
        ", version=" + getVersion() +
        ", isDel=" + getIsDel() +
        ", name=" +  name+
        ", description=" + description +
        ", userId=" + userId +
        ", status=" + status +
        "}";
    }
}
