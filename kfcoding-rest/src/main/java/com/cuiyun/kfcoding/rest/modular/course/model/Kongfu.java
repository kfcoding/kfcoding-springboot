package com.cuiyun.kfcoding.rest.modular.course.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author maple123
 * @since 2018-06-07
 */
@TableName("course_kongfu")
public class Kongfu extends Model<Kongfu> {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String author;
    private Integer type;
    private String brief;
    @TableField("created_at")
    private Date createdAt;
    @TableField("user_id")
    private String userId;
    private String level;
    @TableField("surface_image")
    private String surfaceImage;
    @TableField("surface_background")
    private String surfaceBackground;
    private Integer status;
    private Integer priority;
    @TableField(exist=false)
    private List<Tag> tags;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSurfaceImage() {
        return surfaceImage;
    }

    public void setSurfaceImage(String surfaceImage) {
        this.surfaceImage = surfaceImage;
    }

    public String getSurfaceBackground() {
        return surfaceBackground;
    }

    public void setSurfaceBackground(String surfaceBackground) {
        this.surfaceBackground = surfaceBackground;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Kongfu{" +
        "id=" + id +
        ", title=" + title +
        ", author=" + author +
        ", type=" + type +
        ", brief=" + brief +
        ", createdAt=" + createdAt +
        ", userId=" + userId +
        ", level=" + level +
        ", image=" + surfaceImage +
        ", status=" + status +
        ", priority=" + priority +
        "}";
    }
}
