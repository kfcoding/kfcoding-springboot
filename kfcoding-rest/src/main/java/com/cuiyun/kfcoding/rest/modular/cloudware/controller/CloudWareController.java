package com.cuiyun.kfcoding.rest.modular.cloudware.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.core.support.HttpKit;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.base.controller.BaseController;
import com.cuiyun.kfcoding.rest.modular.cloudware.K8sApi;
import com.cuiyun.kfcoding.rest.modular.cloudware.controller.dto.StartContainerDto;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
public class CloudWareController extends BaseController {

    @Value("${cloudware.namespace}")
    private String namespace;

    @Value("${cloudware.websocket.server.addr}")
    private String cloudwareWss;

    @Value("${terminal.websocket.server.addr}")
    private String terminalWss;

    @Value("${cloudware.Ingress}")
    private String ingress;

    @ResponseBody
    @RequestMapping(path = "/startContainer", method = RequestMethod.POST)
    @ApiOperation(value = "", notes = "")
    public SuccessTip startContainer(@RequestBody StartContainerDto startContainerDto) {
        map = new HashMap<>();
        SUCCESSTIP = new SuccessTip();
        String imageName = startContainerDto.getImageName();
        Integer type = startContainerDto.getType();

        // 初始化
        K8sApi k8sApi = K8sApi.getInstance();
        String podName = RandomUtil.randomUUID();
        // 设置header
        Map headers = new HashMap();
        headers.put("Content-Type", "application/json");
        // 拼接请求json
        Map requestBody = new HashMap();
        requestBody.put("Pod", podName);
        requestBody.put("Namespace", namespace);
        requestBody.put("Ingress", ingress);

        switch (type) {
            case 0://cloudware
                // create cloudware pod and service
                V1Pod podResult = k8sApi.createCloudwarePod(namespace, podName, imageName);
                V1Service serviceResult = k8sApi.createCloudwareService(namespace, podName);
                System.err.println(podResult);
                System.err.println(serviceResult);

                // get cloudware websocket address
                try {
//                    StringBuffer url = new StringBuffer(cloudwareWss);
//                    url.append("/api/websocket/getws/").append(podName).append("/").append(serviceResult.getSpec().getClusterIP());
//                    String wsAddr = HttpKit.get(url.toString(), null, headers);
//                    map.put("WsAddr", wsAddr);
                    int responseCode = HttpKit.put(" http://controller.cloudware.kfcoding.com/api/cloudware", JSON.toJSONString(requestBody), headers);
                    if (responseCode == 200){
                        StringBuffer sb = new StringBuffer();
                        sb.append(podName).append(".cloudware.kfcoding.com");
                        map.put("webSocketAddress", sb.toString());
                    } else {
                        throw new KfCodingException(BizExceptionEnum.CLOUDWARE_CREATE_ERROR);
                    }
                    map.put("podResult", podResult);
                } catch (Exception e) {
                    e.printStackTrace();
                    // if get wsaddr filed, delete pod
                    k8sApi.deletePod(namespace, podName);
                    k8sApi.deleteService(namespace, podName);
                }
                break;
            case 1://terminal
                // create terminal pod
                podResult = k8sApi.createTerminalPod(namespace, podName, imageName);

                System.err.println(podResult);
                // get terminal websocket address
                try {
                    StringBuffer url = new StringBuffer(terminalWss);
                    url.append("/api/v1/pod/").append(namespace).append("/").append(podName).append("/shell/application");

                    String wsAddr = HttpKit.get(url.toString(), null, headers);
                    map.put("WsAddr", wsAddr);
//                    responseCode = HttpKit.put(" http://controller.cloudware.kfcoding.com/api/cloudware", JSON.toJSONString(requestBody), headers);
//                    map.put("podResult", podResult);
                } catch (Exception e) {
                    e.printStackTrace();
                    // if get wsaddr filed, delete pod
                    k8sApi.deletePod(namespace, podName);
                }
                break;
        }


        SUCCESSTIP.setResult(map);
        return SUCCESSTIP;
    }

    @ResponseBody
    @RequestMapping(path = "/deleteContainer", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除容器", notes = "")
    public SuccessTip deleteCloudWare(@RequestParam String podName, @RequestParam int type) {
        K8sApi k8sApi = K8sApi.getInstance();

        k8sApi.deletePod(namespace, podName);
        if (type == 0) { // delete cloudware service
            k8sApi.deleteService(namespace, podName);
        }
        map = new HashMap<>();
        SUCCESSTIP = new SuccessTip();
        return SUCCESSTIP;
    }
}
