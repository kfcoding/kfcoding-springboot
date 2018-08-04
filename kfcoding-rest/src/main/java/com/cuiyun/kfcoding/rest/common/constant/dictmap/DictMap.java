package com.cuiyun.kfcoding.rest.common.constant.dictmap;

import com.cuiyun.kfcoding.rest.common.constant.dictmap.base.AbstractDictMap;

/**
 * 字典map
 *
 * @author maple
 * @date 2018-05-06 15:43
 */
public class DictMap extends AbstractDictMap {

    @Override
    public void init() {
        put("dictId","字典名称");
        put("dictName","字典名称");
        put("dictValues","字典内容");
    }

    @Override
    protected void initBeWrapped() {

    }
}
