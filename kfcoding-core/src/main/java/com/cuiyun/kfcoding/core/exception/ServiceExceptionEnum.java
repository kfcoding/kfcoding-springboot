package com.cuiyun.kfcoding.core.exception;

/**
 * @program: kfcoding
 * @description: 异常抽象接口
 * @author: maple
 * @create: 2018-05-05 20:59
 **/
public interface ServiceExceptionEnum {

    /**
     * 获取异常编码
     */
    Integer getCode();

    /**
     * 获取异常信息
     */
    String getMessage();
}
