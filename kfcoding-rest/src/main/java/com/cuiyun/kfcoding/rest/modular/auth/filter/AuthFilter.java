package com.cuiyun.kfcoding.rest.modular.auth.filter;

import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.core.util.RenderUtil;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.config.properties.JwtProperties;
import com.cuiyun.kfcoding.rest.config.properties.RestProperties;
import com.cuiyun.kfcoding.rest.modular.auth.util.JwtTokenUtil;
import io.jsonwebtoken.JwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 对客户端请求的jwt token验证过滤器
 *
 * @author maple
 * @Date 2017/8/24 14:04
 */
public class AuthFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RestProperties restProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //跳过验证
        if (!restProperties.isAuthOpen()){
            return;
        }

        if (request.getServletPath().equals("/" + jwtProperties.getAuthPath())) {
            chain.doFilter(request, response);
            return;
        }
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);

            //验证token是否过期,包含了验证jwt是否正确
            try {
                boolean flag = jwtTokenUtil.isTokenExpired(authToken);
                if (flag) {
                    RenderUtil.renderJson(response, new KfCodingException(BizExceptionEnum.TOKEN_EXPIRED));
                    return;
                }
            } catch (JwtException e) {
                //有异常就是token解析失败
                RenderUtil.renderJson(response, new KfCodingException(BizExceptionEnum.TOKEN_ERROR));
                return;
            }
        } else {
            //header没有带Bearer字段
            RenderUtil.renderJson(response, new KfCodingException(BizExceptionEnum.TOKEN_ERROR));
            return;
        }
        chain.doFilter(request, response);
    }
}