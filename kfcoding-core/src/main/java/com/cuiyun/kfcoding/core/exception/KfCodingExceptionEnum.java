package com.cuiyun.kfcoding.core.exception;

/**
 * @program: kfcoding
 * @description: KfCoding异常枚举
 * @author: maple
 * @create: 2018-05-05 21:05
 **/
public enum KfCodingExceptionEnum implements ServiceExceptionEnum {
    /**
     * 其他
     */
    WRITE_ERROR(500,"渲染界面错误"),

    /**
     * 文件上传
     */
    FILE_READING_ERROR(400,"FILE_READING_ERROR!"),
    FILE_NOT_FOUND(400,"FILE_NOT_FOUND!"),

    /**
     * 错误的请求
     */
    REQUEST_NULL(400, "请求有错误"),
    SERVER_ERROR(500, "服务器异常");

    KfCodingExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
