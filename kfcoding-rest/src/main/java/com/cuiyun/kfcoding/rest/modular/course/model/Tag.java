package com.cuiyun.kfcoding.rest.modular.course.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author maple123
 * @since 2018-06-07
 */
@TableName("course_tag")
public class Tag extends Model<Tag> {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Tag{" +
        "id=" + id +
        ", name=" + name +
        "}";
    }
}
