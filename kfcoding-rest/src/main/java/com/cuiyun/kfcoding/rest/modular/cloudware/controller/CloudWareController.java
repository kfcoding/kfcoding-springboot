package com.cuiyun.kfcoding.rest.modular.cloudware.controller;

import cn.hutool.core.util.RandomUtil;
import com.cuiyun.kfcoding.core.base.controller.BaseController;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.support.HttpKit;
import com.cuiyun.kfcoding.rest.modular.cloudware.K8sApi;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1Service;
import io.kubernetes.client.models.V1Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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

    // 删除此项
    @Value("${cloudware.container}")
    private String container;

    // 增加此项
    @Value("${cloudware.websocket.server.addr}")
    private String cloudwareWss; // value is http://cloudware.wss.kfcoding.com

    // 增加此项
    @Value("${terminal.websocket.server.addr}")
    private String terminalWss; // value is http://terminal.wss.kfcoding.com

    @ResponseBody
    @RequestMapping(path = "/startContainer", method = RequestMethod.GET)
    @ApiOperation(value = "", notes = "")
    public SuccessTip startContainer(@RequestParam String imageName, @RequestParam int type) {
        K8sApi k8sApi = K8sApi.getInstance();
        String podName = RandomUtil.randomUUID();
        switch (type) {
            case 0://cloudware
                // create cloudware pod and service
                V1Pod podResult = k8sApi.createCloudwarePod(namespace, podName, imageName);
                V1Service serviceResult = k8sApi.createCloudwareService(namespace, podName);

                // get cloudware websocket address
                try {
                    StringBuffer url = new StringBuffer(cloudwareWss);
                    url.append("/api/websocket/getws/").append(podName).append("/").append(serviceResult.getSpec().getClusterIP());
                    String wsAddr = HttpKit.get(url.toString());
                    map.put("WsAddr", wsAddr);
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

                // get terminal websocket address
                try {
                    StringBuffer url = new StringBuffer(terminalWss);
                    url.append("/api/v1/pod/").append(namespace).append("/").append(podName).append("/shell/application");
                    String wsAddr = HttpKit.get(url.toString());
                    map.put("WsAddr", wsAddr);
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
    @ApiOperation(value = "", notes = "")
    public SuccessTip deleteCloudWare(@RequestParam String podName, @RequestParam int type) {
        K8sApi k8sApi = K8sApi.getInstance();

        k8sApi.deletePod(namespace, podName);
        if (type == 0) { // delete cloudware service
            k8sApi.deleteService(namespace, podName);
        }

        return SUCCESSTIP;
    }
}
