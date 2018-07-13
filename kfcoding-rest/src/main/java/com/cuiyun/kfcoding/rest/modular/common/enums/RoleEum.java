package com.cuiyun.kfcoding.rest.modular.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * @program: kfcoding
 * @description: 角色信息
 * @author: maple
 * @create: 2018-07-12 15:12
 **/
public enum RoleEum implements IEnum{
    NORMAL("normal"),
    TEACHER("teacher"),
    STUDENT("student")
    ;

    RoleEum(String value) {
        this.value = value;
    }

    private String value;

    @Override
    public Serializable getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
