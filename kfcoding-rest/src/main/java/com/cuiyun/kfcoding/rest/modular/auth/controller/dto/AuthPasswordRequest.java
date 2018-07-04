package com.cuiyun.kfcoding.rest.modular.auth.controller.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.cuiyun.kfcoding.rest.modular.auth.validator.dto.Credence;
import com.cuiyun.kfcoding.rest.modular.auth.enums.AuthTypeEnum;

/**
 * 认证的请求dto
 *
 * @author maple
 * @Date 2017/8/24 14:00
 */
public class AuthPasswordRequest implements Credence {

    // 验证类型
    private AuthTypeEnum authType;
    // 账号活着邮箱
    private String credenceName;
    // 密码
    private String credenceCode;

    public AuthTypeEnum getAuthType() {
        return authType;
    }

    @JSONField(name = "authType")
    public void setAuthType(String authType) {
        this.authType = AuthTypeEnum.getEnum(authType);
    }

    public void setCredenceName(String credenceName) {
        this.credenceName = credenceName;
    }

    public void setCredenceCode(String credenceCode) {
        this.credenceCode = credenceCode;
    }

    @Override
    public AuthTypeEnum getCredenceAuthType() {
        return this.authType;
    }

    @Override
    public String getCredenceName() {
        return this.credenceName;
    }

    @Override
    public String getCredenceCode() {
        return this.credenceCode;
    }
}
