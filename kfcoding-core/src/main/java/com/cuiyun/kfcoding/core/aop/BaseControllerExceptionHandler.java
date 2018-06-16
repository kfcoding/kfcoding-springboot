package com.cuiyun.kfcoding.core.aop;

import com.cuiyun.kfcoding.core.base.tips.ErrorTip;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.core.exception.KfCodingExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @program: kfcoding
 * @description: 全局的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法都会拦截）
 * @author: maple
     * @create: 2018-05-05 20:55
     **/
    public class BaseControllerExceptionHandler {

        private Logger log = LoggerFactory.getLogger(this.getClass());

        /**
         * 拦截业务异常
     */
    @ExceptionHandler(KfCodingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip notFount(KfCodingException e) {
        log.error("业务异常:", e);
        return new ErrorTip(e.getCode(), e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip notFount(RuntimeException e) {
        log.error("运行时异常:", e);
        return new ErrorTip(KfCodingExceptionEnum.SERVER_ERROR.getCode(), KfCodingExceptionEnum.SERVER_ERROR.getMessage());
    }

}
