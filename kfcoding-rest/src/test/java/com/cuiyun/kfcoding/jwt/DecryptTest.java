package com.cuiyun.kfcoding.jwt;

import com.alibaba.fastjson.JSON;
import com.cuiyun.kfcoding.core.util.MD5Util;
import com.cuiyun.kfcoding.rest.common.SimpleObject;
import com.cuiyun.kfcoding.rest.modular.auth.converter.BaseTransferEntity;
import com.cuiyun.kfcoding.rest.modular.auth.security.impl.Base64SecurityAction;

/**
 * jwt测试
 *
 * @author maple
 * @date 2017-08-21 16:34
 */
public class DecryptTest {

    public static void main(String[] args) {

        String salt = "4ed7oy";

        SimpleObject simpleObject = new SimpleObject();
        simpleObject.setUser("maple");
        simpleObject.setAge(12);
        simpleObject.setName("bfl");
        simpleObject.setTips("code");

        String jsonString = JSON.toJSONString(simpleObject);
        String encode = new Base64SecurityAction().doAction(jsonString);
        String md5 = MD5Util.encrypt(encode + salt);

        BaseTransferEntity baseTransferEntity = new BaseTransferEntity();
        baseTransferEntity.setObject(encode);
        baseTransferEntity.setSign(md5);

        System.out.println(JSON.toJSONString(baseTransferEntity));
    }
}
