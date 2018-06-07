package com.cuiyun.kfcoding.rest.modular.course.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * @program: kfcoding
 * @description: 课程等级
 * @author: maple
 * @create: 2018-06-07 17:02
 **/
public enum LevelEnum implements IEnum {
    ;

    LevelEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private String key;
    private String value;


    @Override
    public Serializable getValue() {
        return this.key;
    }
}
