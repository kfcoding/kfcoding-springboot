package com.cuiyun.kfcoding.rest.modular.auth.validator.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.core.util.ToolUtil;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.auth.validator.IReqValidator;
import com.cuiyun.kfcoding.rest.modular.auth.validator.dto.Credence;
import com.cuiyun.kfcoding.rest.modular.common.model.User;
import com.cuiyun.kfcoding.rest.modular.common.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 账号密码验证
 *
 * @author maple
 * @date 2018-08-23 12:34
 */
@Service
public class DbValidator implements IReqValidator {

    @Autowired
    IUserService userService;

    @Override
    public String validate(Credence credence) {
        EntityWrapper ew = new EntityWrapper<User>();
        // 若是邮箱格式
        if (ToolUtil.checkEmail(credence.getCredenceName())){
            ew.eq("email", credence.getCredenceName());
        } else {
            ew.eq("account", credence.getCredenceName());
        }
//        ew.eq("password", MD5Util.encrypt(credence.getCredenceCode()));
        ew.eq("password", credence.getCredenceCode());

        User user = userService.selectOne(ew);
        if (user == null)
            throw new KfCodingException(BizExceptionEnum.AUTH_REQUEST_ERROR);

        return user.getId();
    }
}
