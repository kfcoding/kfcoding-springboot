package com.cuiyun.kfcoding.core.base.tips;

/**
 * @program: kfcoding
 * @description: 返回给前台的成功提示
 * @author: maple
 * @create: 2018-05-05 21:15
 **/
public class SuccessTip extends Tip{

    public SuccessTip(){
        super.code = 200;
        super.message = "操作成功";
    }

}
