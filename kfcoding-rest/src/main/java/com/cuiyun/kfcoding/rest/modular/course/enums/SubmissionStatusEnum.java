package com.cuiyun.kfcoding.rest.modular.course.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * @program: kfcoding
 * @description: 提交状态
 * @author: maple
 * @create: 2018-07-17 22:37
 **/
public enum SubmissionStatusEnum implements IEnum {
    SUBMIT("submit"),
    UNSUBMIT("unsubmit")
        ;


    SubmissionStatusEnum(String value) {
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
