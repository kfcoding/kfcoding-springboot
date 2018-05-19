package com.cuiyun.kfcoding.rest.modular.auth.validator.impl;

import com.cuiyun.kfcoding.rest.modular.auth.validator.IReqValidator;
import com.cuiyun.kfcoding.rest.modular.auth.validator.dto.Credence;
import com.cuiyun.kfcoding.rest.modular.github.application.OauthGithub;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * @program: kfcoding
 * @description: github验证
 * @author: maple
 * @create: 2018-05-18 21:45
 **/
public class GithubValidator implements IReqValidator {

    @Override
    public boolean validate(Credence credence) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        return false;
    }
}
