package com.cuiyun.kfcoding.rest.common.exception;


import com.cuiyun.kfcoding.core.exception.ServiceExceptionEnum;

/**
 * 所有业务异常的枚举
 *
 * @author maple
 */
public enum BizExceptionEnum implements ServiceExceptionEnum {

    /**
     * token异常
     */
    TOKEN_EXPIRED(700, "token过期"),
    TOKEN_ERROR(700, "token验证失败"),

    /**
     * 签名异常
     */
    SIGN_ERROR(700, "签名验证失败"),

    /**
     * 其他
     */
    AUTH_REQUEST_ERROR(400, "账号密码错误"),

    /**
     * github模块异常
     */
    GITHUB_CANCAL_OAUTH(002,"取消授权"),
    GITHUB_ERROR_URL(001, "url获取失败"),

    /**
     * user模块异常
     */
    USER_ERROR(100, "用户不存在"),


    /**
     * course模块异常
     */
    COURSE_ERROR(200, "没有课程"),
    COURSE_CREAT_ERROR(200, "创建课程失败");

    BizExceptionEnum(Integer code, String message) {
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
