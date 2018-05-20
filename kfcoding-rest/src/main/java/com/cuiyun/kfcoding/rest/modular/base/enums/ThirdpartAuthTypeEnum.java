package com.cuiyun.kfcoding.rest.modular.base.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * @program: kfcoding
 * @description: 第三方类型枚举
 * @author: maple
 * @create: 2018-05-20 12:46
 **/
public enum ThirdpartAuthTypeEnum implements IEnum{
    GITHUB("github");

    ThirdpartAuthTypeEnum(String value){
        this.value = value;
    }

    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }
}
