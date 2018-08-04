package com.cuiyun.kfcoding.rest.modular.book.model;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.cuiyun.kfcoding.rest.modular.base.Model.BaseModel;
import com.cuiyun.kfcoding.rest.modular.book.enums.KongfuStatusEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author maple123
 * @since 2018-06-07
 */
@TableName("book_kongfu")
public class Kongfu extends BaseModel<Kongfu> {

    private static final long serialVersionUID = 1L;
    private String title;
    private String author;
    private Integer type;
    private String brief;
    @TableField("user_id")
    private String userId;
    private String level;
    @TableField("surface_image")
    private String surfaceImage;
    @TableField("surface_background")
    private String surfaceBackground;
    private KongfuStatusEnum status = KongfuStatusEnum.PRIVATE;
    private Integer priority;
    @TableField(exist=false)
    private List<Tag> tags = new ArrayList<>();

    @Override
    public String[] getIgnoreProperties() {
        String[] properties = super.getIgnoreProperties();
        String[] addProperties = {"userId", "tags"};
        ArrayUtil.append(properties, addProperties);
        return properties;
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

    public KongfuStatusEnum getStatus() {
        return status;
    }

    public void setStatus(KongfuStatusEnum status) {
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
    public String toString() {
        return "Kongfu{" +
        ", title=" + title +
        ", author=" + author +
        ", type=" + type +
        ", brief=" + brief +
        ", userId=" + userId +
        ", level=" + level +
        ", image=" + surfaceImage +
        ", status=" + status +
        ", priority=" + priority +
        "}";
    }
}
