package com.cuiyun.kfcoding.rest.modular.common.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.cuiyun.kfcoding.rest.modular.base.Model.BaseModel;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author maple
 * @since 2018-06-19
 */
@TableName("common_user")
public class User extends BaseModel<User> {

    private static final long serialVersionUID = 1L;

    private String account;
    private String name;
    @TableField("avatar_url")
    private String avatarUrl;
    private String password;
    private Integer status;
    private String email;
    private String city;
    private String company;
    private String profession;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    public String toString() {
        return "User{" +
        ", account=" + account +
        ", name=" + name +
        ", avatarUrl=" + avatarUrl +
        ", password=" + password +
        ", status=" + status +
        ", email=" + email +
        ", city=" + city +
        ", company=" + company +
        ", profession=" + profession +
        "}";
    }
}
