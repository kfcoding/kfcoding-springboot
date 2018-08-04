package com.cuiyun.kfcoding.rest.modular.auth.validator.dto;

import com.cuiyun.kfcoding.rest.modular.auth.enums.AuthTypeEnum;

/**
 * 验证的凭据
 *
 * @author maple
 * @date 2018-08-27 13:27
 */
public interface Credence {

    /**
     * 验证类型
     */
    AuthTypeEnum getCredenceAuthType();

    /**
     * 凭据名称
     */
    String getCredenceName();

    /**
     * 密码或者是其他的验证码之类的
     */
    String getCredenceCode();
}
