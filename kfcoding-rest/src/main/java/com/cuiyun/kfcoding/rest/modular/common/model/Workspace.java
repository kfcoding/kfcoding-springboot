package com.cuiyun.kfcoding.rest.modular.common.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cuiyun.kfcoding.rest.modular.base.Model.BaseModel;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author maple
 * @since 2018-07-01
 */
@TableName("common_workspace")
public class Workspace extends BaseModel<Workspace> {

    private static final long serialVersionUID = 1L;

    private String title;
    private String description;
    @TableField("git_url")
    private String gitUrl;
    private String environment;
    private String status;
    @TableField("user_id")
    private String userId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "Workspace{" +
        "id=" + getId() +
        ", title=" + title +
        ", description=" + description +
        ", gitUrl=" + gitUrl +
        ", environment=" + environment +
        ", version=" + getVersion() +
        ", createTime=" + getCreateTime() +
        ", updateTime=" + getUpdateTime() +
        ", isDel=" + getIsDel() +
        ", status=" + status +
        ", userId=" + userId +
        "}";
    }
}