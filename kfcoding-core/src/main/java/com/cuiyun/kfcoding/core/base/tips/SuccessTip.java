package com.cuiyun.kfcoding.core.base.tips;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: kfcoding
 * @description: 返回给前台的成功提示
 * @author: maple
 * @create: 2018-05-05 21:15
 **/
public class SuccessTip extends Tip implements Serializable{

    public SuccessTip(){
        super.code = 200;
        super.message = "操作成功";
    }

    /**
     * 输出结果集
     */
    private Map<String, Object> result = new HashMap<String, Object>();


    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }
}
