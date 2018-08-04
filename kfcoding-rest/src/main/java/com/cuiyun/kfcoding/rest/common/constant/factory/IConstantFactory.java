package com.cuiyun.kfcoding.rest.common.constant.factory;

import java.util.List;

/**
 * 常量生产工厂的接口
 *
 * @author maple
 * @date 2018-06-14 21:12
 */
public interface IConstantFactory {

    /**
     * 根据用户id获取用户名称
     *
     * @author maple
     * @Date 2018/5/9 23:41
     */
    String getUserNameById(Integer userId);

    /**
     * 根据用户id获取用户账号
     *
     * @author maple
     * @date 2018年5月16日21:55:371
     */
    String getUserAccountById(Integer userId);

    /**
     * 获取字典名称
     */
    String getDictName(Integer dictId);

    /**
     * 根据字典名称和字典中的值获取对应的名称
     */
    String getDictsByName(String name, Integer val);

    /**
     * 查询字典
     */
    List<com.cuiyun.kfcoding.rest.modular.system.model.Dict> findInDict(Integer id);

    /**
     * 获取被缓存的对象(用户删除业务)
     */
    String getCacheObject(String para);

}
