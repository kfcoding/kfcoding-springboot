package com.cuiyun.kfcoding.rest.modular.book.model;

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
 * @author maple123
 * @since 2018-06-07
 */
@TableName("book_kongfu_to_tag")
public class KongfuToTag extends Model<KongfuToTag> {

    private static final long serialVersionUID = 1L;

    private String id;
    @TableField("kongfu_id")
    private String kongfuId;
    @TableField("tag_id")
    private String tagId;
    private Date date;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKongfuId() {
        return kongfuId;
    }

    public void setKongfuId(String kongfuId) {
        this.kongfuId = kongfuId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "KongfuToTag{" +
        "id=" + id +
        ", kongfuId=" + kongfuId +
        ", tagId=" + tagId +
        ", date=" + date +
        "}";
    }
}
