package com.cuiyun.kfcoding.rest.modular.auth.enums;

/**
 * @program: kfcoding
 * @description: 登陆类型枚举
 * @author: maple
 * @create: 2018-05-18 21:58
 **/
public enum AuthTypeEnum {
    GITHUB("github");

    AuthTypeEnum(String type){
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
