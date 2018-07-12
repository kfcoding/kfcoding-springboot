package com.cuiyun.kfcoding.rest.modular.auth.filter;

import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.core.util.RenderUtil;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.config.properties.JwtProperties;
import com.cuiyun.kfcoding.rest.config.properties.RestProperties;
import com.cuiyun.kfcoding.rest.modular.auth.util.JwtTokenUtil;
import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.JwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RestProperties restProperties;

    @Override
    @CrossOrigin(origins = "*", maxAge = 3600)
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        //跳过验证
        if (!restProperties.isAuthOpen()){
            return;
        }

        request.getServletPath();
        // 白名单
        if (request.getServletPath().startsWith("/" + jwtProperties.getAuthPath()) ||
                (request.getServletPath().startsWith("/users") && !request.getServletPath().equals("/users/current") && !request.getServletPath().equals("/users/current/kongfu") && !request.getServletPath().equals("/users/current/courses")) ||
                (request.getServletPath().startsWith("/cloudware")) ||
                (request.getServletPath().startsWith("/druid")) ||
                (request.getServletPath().startsWith("/kongfu") && request.getMethod().equals("GET")) ||
                (request.getServletPath().startsWith("/workspaces") && request.getMethod().equals("GET") &&  !request.getServletPath().equals("/workspaces"))
                || (request.getServletPath().startsWith("/swagger"))
                ) {
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
        request.setAttribute("token", authToken);
        chain.doFilter(request, response);
    }

    public static boolean isCorsRequest(HttpServletRequest request) {
        return (request.getHeader(HttpHeaders.ORIGIN) != null);
    }
}