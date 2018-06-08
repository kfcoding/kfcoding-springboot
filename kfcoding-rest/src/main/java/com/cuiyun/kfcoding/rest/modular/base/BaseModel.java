package com.cuiyun.kfcoding.rest.modular.base;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
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
public class BaseModel<T extends Model> extends Model<T>{
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @TableField(value = "createTime")

    private Date createTime;

    @TableField(value = "update_time", update = "now()")
    private Date updateTime;

    @Version
    private Integer version;

    @Override
    protected Serializable pkVal() {
        return this.id;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
