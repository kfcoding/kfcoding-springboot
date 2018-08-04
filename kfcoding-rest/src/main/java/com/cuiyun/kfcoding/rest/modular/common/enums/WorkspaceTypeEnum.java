package com.cuiyun.kfcoding.rest.modular.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

/**
 * @program: kfcoding
 * @description:
 * @author: maple
 * @create: 2018-08-03 12:14
 **/
public enum WorkspaceTypeEnum implements IEnum {
    TERMINAL("terminal"),
    CLOUDWARE("cloudware"),
    WORKSPACE("workspace")
    ;

    WorkspaceTypeEnum(String value) {
        this.value = value;
    }

    @Override

    public String toString() {
       return this.value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
