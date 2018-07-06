package com.cuiyun.kfcoding.rest.modular.cloudware.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.core.support.http.HttpKit;
import com.cuiyun.kfcoding.core.support.http.HttpResult;
import com.cuiyun.kfcoding.core.util.shortid.ShortId;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.base.controller.BaseController;
import com.cuiyun.kfcoding.rest.modular.cloudware.K8sApi;
import com.cuiyun.kfcoding.rest.modular.cloudware.controller.dto.StartContainerDto;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
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

    @ResponseBody
    @RequestMapping(path = "/startContainer", method = RequestMethod.POST)
    @ApiOperation(value = "", notes = "")
    public SuccessTip startContainer(@RequestBody StartContainerDto startContainerDto) {
        MAP = new HashMap<>();
        SUCCESSTIP = new SuccessTip();
        String imageName = startContainerDto.getImageName();
        Integer type = startContainerDto.getType();

        // 初始化
        K8sApi k8sApi = K8sApi.getInstance();
        String podName = (RandomUtil.randomString("abcdefghijklmnopqrstuvwxyz", 1) +
                ShortId.generate()).toLowerCase().replace("_", "a");

        // header
        Map headers = new HashMap();
        headers.put("Content-Type", "application/json");


        switch (type) {
            case 0:
                // 创建 pod 及 service
                // 请求 cloudware controller 添加 traefik 转发规则
                // 如果请求失败或抛异常，删除 pod 及 service
                V1Pod podResult = k8sApi.createCloudwarePod(namespace, podName, imageName);
                V1Service serviceResult = k8sApi.createCloudwareService(namespace, podName);
                System.err.println(podResult);
                System.err.println(serviceResult);
                try {
                    // set header
                    headers.put("Authorization", "Bearer ad3efe453a786f036a946015feff19f78a80192f462ea1d56e3d89e8c4f5d833");
                    // set body
                    Map requestBody = new HashMap();
                    requestBody.put("Name", podName);
                    requestBody.put("URL", "http://" + serviceResult.getSpec().getClusterIP() + ":9800");
                    requestBody.put("Rule", "Path: /" + podName);

                    HttpResult result = HttpKit.postResult(cloudwareWss + "/api/cloudware/routing", JSON.toJSONString(requestBody), headers);

                    if (result.getCode() == 200) {
                        MAP.put("webSocketAddress", new StringBuffer("cloudware.kfcoding.com/").append(podName).toString());
                    } else {
                        k8sApi.deletePod(namespace, podName);
                        k8sApi.deleteService(namespace, podName);
                        throw new KfCodingException(BizExceptionEnum.CLOUDWARE_CREATE_ERROR);
                    }
                    MAP.put("podResult", podResult);
                } catch (Exception e) {
                    k8sApi.deletePod(namespace, podName);
                    k8sApi.deleteService(namespace, podName);
                    e.printStackTrace();
                }
                break;
            case 1:
                podResult = k8sApi.createTerminalPod(namespace, podName, imageName);
                System.err.println(podResult);

                try {
                    StringBuffer url = new StringBuffer(terminalWss);
                    url.append("/api/v1/pod/").append(namespace).append("/").append(podName).append("/shell/application");

                    HttpResult result = HttpKit.getResult(url.toString(), null, headers);

                    if (result.getCode() == 200) {
                        MAP.put("WsAddr", result.getResult());
                    } else {
                        k8sApi.deletePod(namespace, podName);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    k8sApi.deletePod(namespace, podName);
                }
                break;
        }

        SUCCESSTIP.setResult(MAP);
        return SUCCESSTIP;
    }

    @ResponseBody
    @RequestMapping(path = "/deleteContainer", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除容器", notes = "")
    public SuccessTip deleteCloudWare(@RequestParam String podName, @RequestParam int type) {
        K8sApi k8sApi = K8sApi.getInstance();

        k8sApi.deletePod(namespace, podName);
        if (type == 0) {
            k8sApi.deleteService(namespace, podName);
        }

        MAP = new HashMap<>();
        SUCCESSTIP = new SuccessTip();
        return SUCCESSTIP;
    }

    @ResponseBody
    @RequestMapping(path = "/keepalive", method = RequestMethod.POST)
    @ApiOperation(value = "心跳", notes = "")
    public SuccessTip keepalive(@RequestBody String data) throws UnsupportedEncodingException {
        String result;
        try {
            String url = cloudwareWss + "/api/cloudware/keepalive";

            Map headers = new HashMap();
            headers.put("Content-Type", "application/json");

            JSONObject body = new JSONObject(data);
            body.put("Name", body.get("Pod"));
            body.remove("Pod");

            if (body.getString("Name") != "" && body.getString("Name") != null) {

                HttpResult response = HttpKit.postResult(url, body.toString(), headers);
                if (response.getCode() != 200) {
                    System.err.println(response.getResult());
                }
                result = String.valueOf(response.getCode());

            } else {
                result = "Error, name is null";
            }
        } catch (Exception e) {
            result = "Read body error!";
            e.printStackTrace();
        }

        MAP = new HashMap<>();
        SUCCESSTIP = new SuccessTip();
        MAP.put("result", result);
        SUCCESSTIP.setResult(MAP);
        return SUCCESSTIP;
    }
}