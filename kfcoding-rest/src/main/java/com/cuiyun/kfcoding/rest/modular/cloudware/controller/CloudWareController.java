package com.cuiyun.kfcoding.rest.modular.cloudware.controller;

import cn.hutool.core.util.RandomUtil;
import com.cuiyun.kfcoding.core.base.controller.BaseController;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.support.HttpKit;
import com.cuiyun.kfcoding.rest.modular.cloudware.CloudwareAPI;
import io.kubernetes.client.models.ExtensionsV1beta1Deployment;
import io.kubernetes.client.models.V1Service;
import io.kubernetes.client.models.V1Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: kfcoding
 * @description: k8s控制类
 * @author: maple
 * @create: 2018-05-21 17:08
 **/
@RestController
@RequestMapping("/cloudware")
@CrossOrigin(origins = "*")
@Api(description = "cloudware相关接口")
public class CloudWareController extends BaseController{

    @Value("${cloudware.namespace}")
    private String namespace;

    @Value("${cloudware.container}")
    private String container;

    @ResponseBody
    @RequestMapping(path = "/start", method = RequestMethod.GET)
    @ApiOperation(value = "", notes="")
    public SuccessTip startCloudWare(@RequestParam String imageName){
//        String namespace = "kfcoding-alpha";
//        String container = "application";
        CloudwareAPI cloudwareAPI = CloudwareAPI.getInstance();
        String uuid = RandomUtil.randomUUID();
        ExtensionsV1beta1Deployment extensionsV1beta1Deployment = cloudwareAPI.createDeployment(namespace, uuid, imageName);
        V1Service v1Service = cloudwareAPI.createService(namespace, uuid);
        map.put("extensionsV1beta1Deployment", extensionsV1beta1Deployment);
        map.put("v1Service", v1Service);
        StringBuffer sb = new StringBuffer();
        sb.append("http://wss.kfcoding.com:30081/api/v1/pod/").append(namespace).append(uuid).append("/shell/").append(container);
        String url = sb.toString();
        String data = HttpKit.get(url);
        map.put("shellWsToken", data);
        SUCCESSTIP.setResult(map);
        return SUCCESSTIP;
    }

    @ResponseBody
    @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
    @ApiOperation(value = "", notes="")
    public SuccessTip deleteCloudWare(@RequestParam String podName){
//        String namespace = "kfcoding-alpha";
//        String container = "application";
        CloudwareAPI cloudwareAPI = CloudwareAPI.getInstance();
        V1Status deploymentStatus = cloudwareAPI.deleteDeployment(namespace, podName);
        V1Status serviceStatus = cloudwareAPI.deleteService(namespace, podName);
        map.put("deploymentStatus", deploymentStatus);
        map.put("serviceStatus", serviceStatus);
        SUCCESSTIP.setResult(map);
        return SUCCESSTIP;
    }
}
