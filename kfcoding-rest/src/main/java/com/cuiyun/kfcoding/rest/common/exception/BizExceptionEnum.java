package com.cuiyun.kfcoding.rest.common.exception;


import com.cuiyun.kfcoding.core.exception.ServiceExceptionEnum;

/**
 * 所有业务异常的枚举
 *
 * @author maple
 */
public enum BizExceptionEnum implements ServiceExceptionEnum {

    /**
     * 字典
     */
    DICT_EXISTED(500, "字典已经存在"),
    ERROR_CREATE_DICT(500, "创建字典失败"),
    ERROR_WRAPPER_FIELD(500, "包装字典属性失败"),
    /**
     * 错误的请求
     */
    DICT_MUST_BE_NUMBER(400,"字典的值必须为数字"),

    /**
     * token异常
     */
    TOKEN_EXPIRED(700, "token过期"),
    TOKEN_ERROR(700, "token验证失败"),
    TOKEN_NULL(700, "无法获取token中的用户信息"),

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
    GITHUB_CANCAL_OAUTH(002, "取消授权(code为空)"),
    GITHUB_ERROR_URL(001, "url获取失败"),

    /**
     * user模块异常
     */
    USER_ERROR(100, "用户不存在"),
    USER_EXIST(101, "用户账号已存在"),
    USER_CREATE_REQUIRED(102, "用户信息不全"),
    USER_CREATE_EMAIL(103, "邮箱格式不合法"),

    /**
     * book模块异常
     */
    BOOK_ERROR(501, "没有课程"),
    BOOK_CREAT_ERROR(502, "创建课程失败"),

    /**
     * cloudware模块异常
     */
    CLOUDWARE_CREATE_ERROR(601, "创建异常"),

    /**
     * workspace模块异常
     */
    WORKSPACE_CREATE_ERROR(801, "创建异常"),
    WORKSPACE_NULL(802, "工作空间不存在"),
    WORKSPACE_DELETE(803, "删除失败"),
    /**
     * course模块异常
     */

    COURSE_CREATE(1001, "课程创建异常"),
    COURSE_KLASS_CREATE(1002, "班级创建异常"),
    COURSE_WORK_CREATE(1003, "作业创建异常"),
    COURSE_KLASS_EXIST(1004, "班级不存在"),
    COURSE_JOIN(1005, "加入班级异常"),
    COURSE_NULL(1006, "课程不存在"),
    COURSE_STUDENT_CREATE(1007, "学生创建异常"),
    COURSE_STUDENT_NULL(1008, "学生不存在"),
    COURSE_SUBMISSION_CREATE(1009, "作业提交异常"),
    COURSE_WORK_NULL(1010, "作业不存在"),

    ;



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
