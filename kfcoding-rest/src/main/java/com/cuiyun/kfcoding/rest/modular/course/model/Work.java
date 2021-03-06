package com.cuiyun.kfcoding.rest.modular.course.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.cuiyun.kfcoding.rest.modular.base.Model.BaseModel;

import java.util.Date;
import java.util.List;

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
    private String image;
    private String repo;
    @TableField("start_time")
    private Date startTime;
    @TableField("end_time")
    private Date endTime;
    @TableField(exist = false)
    private List<Submission> submissions;

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
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
