package com.cuiyun.kfcoding.core.base.web.exception;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * @author maple
 * 数据异常
 */
public class DataException extends RuntimeException {
    /**
     * 错误代码
     */
    private Integer errorCode;
    /**
     * 错误说明,此说明只显示在后端、日志中，不显示在前端，此处由 GlobalExceptionHandler 处理
     */
    private String errorMessage;
    /**
     * 抛出对象,在日志中记录
     */
    private Object[] payload;

    /**
     * 格式化错误信息
     */
    private String[] formatMessage;

    /**
     * @param errorCode
     * @param errorMessage
     */
    public DataException(Integer errorCode, String errorMessage) {
        super(MessageFormat.format("*[{0}] {1}", errorCode, errorMessage));
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     *
     * @param errorCode
     * @param pattern
     * @param formatMessage
     * @param payload
     */
    public DataException(Integer errorCode, String pattern, String[] formatMessage, Object... payload) {
        super(MessageFormat.format("*[{0}] {1} {2}", errorCode, MessageFormat.format(pattern, formatMessage), Arrays.toString(payload)));
        this.errorCode = errorCode;
        this.errorMessage = MessageFormat.format(pattern, formatMessage);
        this.formatMessage = formatMessage;
        this.payload = payload;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        if (formatMessage != null){
            this.errorMessage = MessageFormat.format(errorMessage, formatMessage);
        }else {
            this.errorMessage = errorMessage;
        }
    }

    public Object[] getPayload() {
        return payload;
    }

    public void setPayload(Object[] payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "DataException{" +
                "errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                ", payload=" + Arrays.toString(payload) +
                '}';
    }
}
