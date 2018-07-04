package com.cuiyun.kfcoding.rest.modular.auth.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: kfcoding
 * @description: 第三方类型枚举
 * @author: maple
 * @create: 2018-05-20 12:46
 **/
public enum AuthTypeEnum implements IEnum{
    GITHUB("github"),
    PASSWORD("password");


    private static final Map<String, AuthTypeEnum> CODE_MAP = new HashMap<String, AuthTypeEnum>();

    static {
        for (AuthTypeEnum typeEnum : AuthTypeEnum.values()) {
            CODE_MAP.put(typeEnum.getValue().toString(), typeEnum);
        }
    }

    AuthTypeEnum(String value){
        this.value = value;
    }

    /**
     * string转enum
     */
    public static AuthTypeEnum getEnum(String value) {
        return CODE_MAP.get(value);
    }

    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
