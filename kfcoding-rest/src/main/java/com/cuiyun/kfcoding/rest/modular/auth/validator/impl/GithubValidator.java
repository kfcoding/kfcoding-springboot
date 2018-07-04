package com.cuiyun.kfcoding.rest.modular.auth.validator.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.auth.enums.AuthTypeEnum;
import com.cuiyun.kfcoding.rest.modular.auth.validator.IReqValidator;
import com.cuiyun.kfcoding.rest.modular.auth.validator.dto.Credence;
import com.cuiyun.kfcoding.rest.modular.common.model.Thirdpart;
import com.cuiyun.kfcoding.rest.modular.common.model.User;
import com.cuiyun.kfcoding.rest.modular.common.service.IThirdpartService;
import com.cuiyun.kfcoding.rest.modular.common.service.IUserService;
import com.cuiyun.kfcoding.rest.modular.github.application.OauthGithub;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: kfcoding
 * @description: github验证
 * @author: maple
 * @create: 2018-05-18 21:45
 **/
@Service
public class GithubValidator implements IReqValidator {

    @Autowired
    IThirdpartService thirdpartService;

    @Autowired
    IUserService userService;

    @Override
    public String validate(Credence credence) {

        // 取消了授权
        if (StringUtils.isBlank(credence.getCredenceCode())) {
            throw new KfCodingException(BizExceptionEnum.GITHUB_CANCAL_OAUTH);
        }
        try {
            //获取token
            String gitHubToken = OauthGithub.me().getTokenByCode(credence.getCredenceCode());
            if (StringUtils.isBlank(gitHubToken))
                throw new KfCodingException(BizExceptionEnum.GITHUB_CANCAL_OAUTH);
            JSONObject userInfo = OauthGithub.me().getUserInfo(gitHubToken);
            Thirdpart thirdpart = JSON.parseObject(userInfo.toJSONString(), new TypeReference<Thirdpart>() {
            });
            Thirdpart tempThirdPart = new Thirdpart();
            tempThirdPart.setThirdpartId(thirdpart.getId());
            // 如果信息不存在则添加用户
            User user;
            tempThirdPart = thirdpartService.selectOne(new EntityWrapper<>(tempThirdPart));
            if (tempThirdPart == null) {
                user = new User();
                // TODO: 2018/5/19  密码生成策略
                user.setPassword(RandomStringUtils.random(10, "1234567890"));
                user.setAvatarUrl(thirdpart.getAvatarUrl());
                user.setName(thirdpart.getLogin());
                userService.insertOrUpdate(user);
                tempThirdPart = changeThirdPart(thirdpart, tempThirdPart);
                tempThirdPart.setUserId(user.getId());
                tempThirdPart.setAuthType(AuthTypeEnum.GITHUB);
                thirdpartService.insert(tempThirdPart);
            } else { // 存在就更新用户信息
                user = userService.selectById(tempThirdPart.getUserId());
                user.setAvatarUrl(thirdpart.getAvatarUrl());
                user.setName(thirdpart.getLogin());
                userService.insertOrUpdate(user);
                tempThirdPart = changeThirdPart(thirdpart, tempThirdPart);
                tempThirdPart.setAuthType(AuthTypeEnum.GITHUB);
                thirdpartService.updateById(tempThirdPart);
            }
            return tempThirdPart.getUserId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构造信息
     */
    private Thirdpart changeThirdPart(Thirdpart thirdpart, Thirdpart tempThirdPart) {
        if (tempThirdPart == null) {
            String tempId = thirdpart.getId();
            thirdpart.setThirdpartId(tempId);
            thirdpart.setId(null);
            return thirdpart;
        } else {
            String tempId = tempThirdPart.getId();
            String userId = tempThirdPart.getUserId();
            BeanUtils.copyProperties(tempThirdPart, thirdpart);
            thirdpart.setId(tempId);
            thirdpart.setUserId(userId);
        }
        return thirdpart;
    }
}
