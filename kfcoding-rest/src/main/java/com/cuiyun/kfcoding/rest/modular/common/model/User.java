package com.cuiyun.kfcoding.rest.modular.common.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.cuiyun.kfcoding.rest.modular.base.Model.BaseModel;
import com.cuiyun.kfcoding.rest.modular.common.enums.RoleEum;
import com.cuiyun.kfcoding.rest.modular.course.model.Student;

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

    /**
     * 账号 （required）
     */
    private String account;
    /**
     * 名称 （required）
     */
    private String name;
    @TableField("avatar_url")
    /**
     * 头像
     */
    private String avatarUrl;
    /**
     * 密码 （required）
     */
    @JSONField(serialize=false)
    private String password;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 邮箱（required）
     */
    private String email;
    /**
     * 城市
     */
    private String city;
    /**
     * 公司
     */
    private String company;
    /**
     * 职位
     */
    private String profession;

    /**
     * 角色
     */
    private RoleEum role;

    @TableField(exist = false)
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public RoleEum getRole() {
        return role;
    }

    public void setRole(RoleEum role) {
        this.role = role;
    }

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
