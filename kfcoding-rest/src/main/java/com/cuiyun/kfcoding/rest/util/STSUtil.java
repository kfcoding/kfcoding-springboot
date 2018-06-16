package com.cuiyun.kfcoding.rest.util;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.cuiyun.kfcoding.core.util.ToolUtil;
import com.cuiyun.kfcoding.rest.config.STSConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: kfcoding
 * @description: 阿里云OSS授权工具类
 * @author: maple
 * @create: 2018-05-21 11:00
 **/
public class STSUtil {
    private static Logger log = LoggerFactory.getLogger(STSUtil.class);

    private String roleArn;
    private static STSUtil stsUtil;

    static {
        try {
            stsUtil = new STSUtil();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    private static DefaultAcsClient client;

    public STSUtil() throws ClientException {
        DefaultProfile.addEndpoint("", "", "Sts", STSConfig.getValue("endpoint"));
        IClientProfile profile = DefaultProfile.getProfile("", STSConfig.getValue("accessKeyId"), STSConfig.getValue("accessKeySecret"));
        DefaultAcsClient client = new DefaultAcsClient(profile);
        setClient(client);
        setRoleArn(STSConfig.getValue("roleArn"));
    }

    /**
     * 用于链式操作
     * @return
     */
    public static STSUtil instance() {
        return stsUtil;
    }

    public AssumeRoleResponse getAssumeRoleResponse(String reg) throws ClientException {
        Map policy = new HashMap();
        List statements = new ArrayList();
        Map statement = new HashMap();

        // 添加action
        List actions = new ArrayList();
        actions.add("oss:*");

        // 添加resource
        List resources = new ArrayList();
        resources.add("acs:oss:*:*:" + reg);

        // 整合statement
        statement.put("Effect", "Allow");
        statement.put("Action", actions);
        statement.put("Resource", resources);
        statements.add(statement);

        //整合policy
        policy.put("Version", "1");
        policy.put("Statement", statements);
        String policyString = JSON.toJSONString(policy);

        final AssumeRoleRequest request = new AssumeRoleRequest();
        request.setPolicy(policyString);
        request.setMethod(MethodType.POST);
        request.setRoleArn(roleArn);
        request.setRoleSessionName(ToolUtil.getRandomString(10));
        final AssumeRoleResponse response = client.getAcsResponse(request);
        return response;
    }

    public static DefaultAcsClient getClient() {
        return client;
    }

    public static void setClient(DefaultAcsClient client) {
        STSUtil.client = client;
    }

    public String getRoleArn() {
        return roleArn;
    }

    public void setRoleArn(String roleArn) {
        this.roleArn = roleArn;
    }
}
