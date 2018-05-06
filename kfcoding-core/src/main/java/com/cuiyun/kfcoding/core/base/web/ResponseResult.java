package com.cuiyun.kfcoding.core.base.web;


import com.cuiyun.kfcoding.core.base.web.exception.DataException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author maple
 * Rest 接口返回类
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult implements Serializable {

    /**
     * 输出时间
     */
    private Date timestamp = new Date();
    /**
     * 输出ID
     */
    private String id = UUID.randomUUID().toString();
    /**
     * 自定义状态码，200：成功，其它为错误
     */
    private Integer code = 200;
    /**
     * 自定义提示信息
     */
    private String message = "ok";
    /**
     * 自定义命令名称
     */
    private String commandName;
    /**
     * 输出结果集
     */
    private Map<String, Object> result = new HashMap<String, Object>();
    /**
     * 认证token
     */
    private String authToken = "";

    public ResponseResult put(String name, Object obj) {
        result.put(name, obj);
        return this;
    }

    public void putException(DataException de) {
        this.code = de.getErrorCode();
        this.message = de.getErrorMessage();
    }

    public void putException(Exception e) {
        this.code = -1;
        this.message = e.getMessage() + "," + e.toString();
    }

    /**
     * 转换为类对象，主要用于跨服务传输转换
     *
     * @param obj
     * @param valueType
     * @param <T>
     * @return
     */
    public <T> T toClassObject(Object obj, Class<T> valueType) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        T value = null;
        try {
            value = mapper.readValue(mapper.writeValueAsString(obj), valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }


    public String getId() {
        return id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
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

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "timestamp=" + timestamp +
                ", id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", commandName='" + commandName + '\'' +
                ", result=" + result +
                ", authToken='" + authToken + '\'' +
                '}';
    }
}
