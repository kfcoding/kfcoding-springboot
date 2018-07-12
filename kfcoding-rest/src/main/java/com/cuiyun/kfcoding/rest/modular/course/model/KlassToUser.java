package com.cuiyun.kfcoding.rest.modular.course.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author maple
 * @since 2018-07-12
 */
@TableName("course_klass_to_user")
public class KlassToUser extends Model<KlassToUser> {

    private static final long serialVersionUID = 1L;

    private String id;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    private Long version;
    @TableField("is_del")
    private Integer isDel;
    @TableField("klass_id")
    private String klassId;
    @TableField("user_id")
    private String userId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getKlassId() {
        return klassId;
    }

    public void setKlassId(String klassId) {
        this.klassId = klassId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "KlassToUser{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", version=" + version +
        ", isDel=" + isDel +
        ", klassId=" + klassId +
        ", userId=" + userId +
        "}";
    }
}
