package com.cuiyun.kfcoding.rest.common.shiro;

import java.io.Serializable;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
 *
 * @author maple
 * @date 2016年12月5日 上午10:26:43
 */
public class ShiroUser implements Serializable {

    private static final long serialVersionUID = 1L;

    public String id;          // 主键ID
//    public String account;      // 账号
    public String name;         // 姓名
//    public Integer deptId;      // 部门id
//    public List<Integer> roleList; // 角色集
//    public String deptName;        // 部门名称
//    public List<String> roleNames; // 角色名称集


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
