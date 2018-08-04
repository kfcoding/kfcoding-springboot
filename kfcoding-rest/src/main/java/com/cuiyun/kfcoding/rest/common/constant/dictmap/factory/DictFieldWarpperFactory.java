package com.cuiyun.kfcoding.rest.common.constant.dictmap.factory;

import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.rest.common.constant.factory.ConstantFactory;
import com.cuiyun.kfcoding.rest.common.constant.factory.IConstantFactory;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;

import java.lang.reflect.Method;

/**
 * 字典字段的包装器(从ConstantFactory中获取包装值)
 *
 * @author maple
 * @date 2018-05-06 15:12
 */
public class DictFieldWarpperFactory {

    public static Object createFieldWarpper(Object parameter, String methodName) {
        IConstantFactory constantFactory = ConstantFactory.me();
        try {
            Method method = IConstantFactory.class.getMethod(methodName, parameter.getClass());
            return method.invoke(constantFactory, parameter);
        } catch (Exception e) {
            try {
                Method method = IConstantFactory.class.getMethod(methodName, Integer.class);
                return method.invoke(constantFactory, Integer.parseInt(parameter.toString()));
            } catch (Exception e1) {
                throw new KfCodingException(BizExceptionEnum.ERROR_WRAPPER_FIELD);
            }
        }
    }

}
