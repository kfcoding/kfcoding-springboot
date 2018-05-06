package com.cuiyun.kfcoding.core.exception;

/**
 * @program: kfcoding
 * @description: 封装KfCoding的异常
 * @author: maple
 * @create: 2018-05-05 20:57
 **/
public class KfCodingException extends RuntimeException {

    private int code;
    private String message;

    public KfCodingException(ServiceExceptionEnum serviceExceptionEnum){
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
