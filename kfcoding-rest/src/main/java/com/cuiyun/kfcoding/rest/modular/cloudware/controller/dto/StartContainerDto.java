package com.cuiyun.kfcoding.rest.modular.cloudware.controller.dto;

/**
 * @program: kfcoding
 * @description: 创建容器dto
 * @author: maple
 * @create: 2018-06-05 21:18
 **/
public class StartContainerDto {
    private String imageName;
    private Integer type;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
