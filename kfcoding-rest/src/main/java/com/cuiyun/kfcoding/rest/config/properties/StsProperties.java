package com.cuiyun.kfcoding.rest.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: kfcoding
 * @description: 阿里云Sts相关配置
 * @author: maple
 * @create: 2018-07-17 14:45
 **/
@Configuration
@ConfigurationProperties(prefix = StsProperties.STS_PREFIX)
public class StsProperties {
    public static final String STS_PREFIX = "sts";
    private String endpoint = "";
    private String accessKeyId = "";
    private String accessKeySecret = "";
    private String roleArn = "";

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getRoleArn() {
        return roleArn;
    }

    public void setRoleArn(String roleArn) {
        this.roleArn = roleArn;
    }
}
