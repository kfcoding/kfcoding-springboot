package com.cuiyun.kfcoding.rest.common.aop;

import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.core.support.http.HttpKit;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.config.properties.JwtProperties;
import com.cuiyun.kfcoding.rest.config.properties.RestProperties;
import com.cuiyun.kfcoding.rest.modular.auth.util.JwtTokenUtil;
import io.jsonwebtoken.JwtException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: kfcoding
 * @description: token切面
 * @author: maple
 * @create: 2018-07-18 14:03
 **/
@Aspect
@Component
@Order(200)
public class PermissionAop {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RestProperties restProperties;


    @Pointcut(value = "@annotation(com.cuiyun.kfcoding.rest.common.annotion.Permission)")
    public void cutPermission() {
    }

    @Around("cutPermission()")
    public Object doPermission(ProceedingJoinPoint point) throws Throwable {

        //跳过验证
        if (!restProperties.isAuthOpen()) {
            return point.proceed();
        }

        HttpServletRequest request = HttpKit.getRequest();
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);

            //验证token是否过期,包含了验证jwt是否正确
            try {
                boolean flag = jwtTokenUtil.isTokenExpired(authToken);
                if (flag) {
                    throw new KfCodingException(BizExceptionEnum.TOKEN_EXPIRED);
                }
            } catch (JwtException e) {
                //有异常就是token解析失败
                throw new KfCodingException(BizExceptionEnum.TOKEN_ERROR);
            }
        } else {
            //header没有带Bearer字段
            throw new KfCodingException(BizExceptionEnum.TOKEN_ERROR);
        }
        request.setAttribute("token", authToken);
        return point.proceed();
    }


}
