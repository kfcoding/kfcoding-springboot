package com.cuiyun.kfcoding.core.base.tips;

/**
 * @program: kfcoding
 * @description: 返回给前台的提示
 * @author: maple
 * @create: 2018-05-05 21:14
 **/
public abstract class Tip {

    protected int code;
    protected String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
