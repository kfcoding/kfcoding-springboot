package com.cuiyun.kfcoding.rest.modular.auth.controller.dto;

import com.cuiyun.kfcoding.rest.modular.auth.validator.dto.Credence;

/**
 * 认证的请求dto
 *
 * @author maple
 * @Date 2017/8/24 14:00
 */
public class AuthRequest implements Credence {

    private String authType;
    private String code;

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String userName;
    private String password;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String getCredenceName() {
        return this.userName;
    }

    @Override
    public String getCredenceCode() {
        return this.password;
    }
}
