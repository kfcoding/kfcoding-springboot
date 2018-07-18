package com.cuiyun.kfcoding.rest.modular.base.Model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: kfcoding
 * @description: 公共model类
 * @author: maple
 * @create: 2018-06-07 20:25
 **/
public abstract class BaseModel<T extends Model> extends Model<T>{
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time", update = "now()")
    private Date updateTime;

    @Version
    @JSONField(serialize=false)
    private Long version = 1L;

    @TableLogic
    @TableField("is_del")
    @JSONField(serialize=false)
    private Integer isDel = 0;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public String[] getIgnoreProperties(){
        return new String[]{"id", "version", "startTime", "updateTime", "isDel"};
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
}
