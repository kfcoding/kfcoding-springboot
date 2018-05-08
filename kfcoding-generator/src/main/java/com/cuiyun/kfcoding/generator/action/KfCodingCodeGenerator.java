package com.cuiyun.kfcoding.generator.action;


import com.cuiyun.kfcoding.generator.action.config.KfCodingGeneratorConfig;

/**
 * 代码生成器,可以生成实体,dao,service,controller,html,js
 *
 * @author cuiyun
 * @Date 2017/5/21 12:38
 */
public class KfCodingCodeGenerator {

    public static void main(String[] args) {

        /**
         * Mybatis-Plus的代码生成器:
         *      mp的代码生成器可以生成实体,mapper,mapper对应的xml,service
         */
        KfCodingGeneratorConfig KfCodingGeneratorConfig = new KfCodingGeneratorConfig();
        KfCodingGeneratorConfig.doMpGeneration();

        /**
         * KfCoding的生成器:
         *      KfCoding的代码生成器可以生成controller,html页面,页面对应的js
         */
//        KfCodingGeneratorConfig.doKfCodingGeneration();
    }

}