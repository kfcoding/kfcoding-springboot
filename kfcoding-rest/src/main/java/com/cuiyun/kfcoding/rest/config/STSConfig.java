package com.cuiyun.kfcoding.rest.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @program: kfcoding
 * @description: 阿里云STS配置类
 * @author: maple
 * @create: 2018-05-21 13:58
 **/
public class STSConfig {
    private static Properties props = new Properties();
    static {
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sts.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setProps(Properties prop) {
        props = prop;
    }

    public static String getValue(String key) {
        return props.getProperty(key);
    }
}
