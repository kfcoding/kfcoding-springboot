package com.cuiyun.kfcoding.core.support.http;

/**
 * @program: kfcoding
 * @description: http返回结果实体
 * @author: maple
 * @create: 2018-06-16 11:26
 **/
public class HttpResult {

    private Integer code;
    private String result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
