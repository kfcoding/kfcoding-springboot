package com.cuiyun.kfcoding.core.base.tips;

/**
 * @program: kfcoding
 * @description: 返回给前台的错误提示
 * @author: maple
 * @create: 2018-05-05 21:13
 **/
public class ErrorTip extends Tip {

    public ErrorTip(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

}

