package com.cuiyun.kfcoding.rest.common.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @program: kfcoding
 * @description: jwtToken
 * @author: maple
 * @create: 2018-06-07 15:17
 **/
public class JWTToken implements AuthenticationToken {

    // 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
