package com.cuiyun.kfcoding.rest.modular.cloudware;

import com.google.gson.Gson;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.apis.ExtensionsV1beta1Api;
import io.kubernetes.client.models.ExtensionsV1beta1Deployment;
import io.kubernetes.client.models.V1DeleteOptions;
import io.kubernetes.client.models.V1Service;
import io.kubernetes.client.models.V1Status;
import io.kubernetes.client.util.Config;

import java.io.IOException;

public class CloudwareAPI {

    private final static String URL = "https://120.132.94.141:6443";
    private final static String TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJhZG1pbi11c2VyLXRva2VuLWo3cjZrIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluLXVzZXIiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiI0NmVlN2VmOC00YWFkLTExZTgtYmEwYi01MjU0MDAzMjgyNjUiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6a3ViZS1zeXN0ZW06YWRtaW4tdXNlciJ9.Fx4GIjOtOTJA2u4vLsl6mMDcpD5cqW31ErwR-7fvEfN_2oMBiJUSE75V-EAngiZpyxlCyjUahXfq1T2qWJhEWSvdzNOJBbbQSVDAcRgHUC7QT9QIZS0si5rwBBJUCz92k_55BltYI9oa9HEFp3u41BrK73714xWXbdMWhev2YNAWIeGFB0BA2SSTHEByTfaXka5eslTYDg23j8-qs_JmLjQPvxcPXxh_vu89zxkKzDjwpVy-EkcQr5r-ubW03qku7xHfDVMc2MVepZOS-VJbnUcGNGNBvGQtItdeJsUdx18gJ7ABERE-h9FtMvBCnH_Yilg7wJM3_iC9W6ilDHCRPg";
    private static CloudwareAPI instance = new CloudwareAPI();
    private CoreV1Api coreV1Api;
    private ExtensionsV1beta1Api extensionsV1beta1Api;

    private CloudwareAPI() {
        ApiClient client = null;//.fromToken(URL, TOKEN, false);
        try {
            client = Config.fromCluster();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Configuration.setDefaultApiClient(client);

        coreV1Api = new CoreV1Api();
        extensionsV1beta1Api = new ExtensionsV1beta1Api();
    }

    public static CloudwareAPI getInstance() {
        return instance;
    }

    public ExtensionsV1beta1Deployment createDeployment(String namespace, String deploymentName, String imageName) {

        // create deployment config file
        ExtensionsV1beta1Deployment deploymentBody =
                (ExtensionsV1beta1Deployment)
                        deserialize(Template.DeplymentTemplate, ExtensionsV1beta1Deployment.class);
        deploymentBody.getMetadata().setName(deploymentName);
        deploymentBody.getMetadata().setNamespace(namespace);
        deploymentBody.getSpec().getTemplate().getSpec().getContainers().get(2).setImage(imageName);
        deploymentBody.getSpec().getSelector().getMatchLabels().clear();
        deploymentBody.getSpec().getSelector().getMatchLabels().put("app", deploymentName);
        deploymentBody.getSpec().getTemplate().getMetadata().getLabels().clear();
        deploymentBody.getSpec().getTemplate().getMetadata().getLabels().put("app", deploymentName);

        // create deployment
        ExtensionsV1beta1Deployment result = null;
        try {
            result = extensionsV1beta1Api.createNamespacedDeployment(namespace, deploymentBody, "false");
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return result;
    }

    public V1Service createService(String namespace, String deploymentName) {

        // create service config file
        V1Service serviceBody =
                (V1Service) deserialize(Template.ServiceTemplate, V1Service.class);
        serviceBody.getMetadata().setNamespace(namespace);
        serviceBody.getMetadata().setName(deploymentName);
        serviceBody.getMetadata().getLabels().clear();
        serviceBody.getMetadata().getLabels().put("app", deploymentName);
        serviceBody.getSpec().getSelector().clear();
        serviceBody.getSpec().getSelector().put("app", deploymentName);

        // create service
        V1Service result = null;
        try {
            result = coreV1Api.createNamespacedService(namespace, serviceBody, "false");
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Object deserialize(String jsonStr, Class<?> targetClass) {
        Object obj = (new Gson()).fromJson(jsonStr, targetClass);
        return obj;
    }

    public V1Status deleteDeployment(String namespace, String deploymentName) {
        V1Status result = null;
        V1DeleteOptions options = new V1DeleteOptions();
        options.setApiVersion("extensions/v1beta1");
        options.setKind("DeleteOptions");
        options.setOrphanDependents(false);

        try {
            result = extensionsV1beta1Api.deleteNamespacedDeployment(
                    deploymentName,
                    namespace,
                    options,
                    "false",
                    0,
                    false,
                    "Background");
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return result;
    }

    public V1Status deleteService(String namespace, String serviceName) {
        V1Status result = null;
        try {
            result = coreV1Api.deleteNamespacedService(serviceName, namespace, "false");
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return result;
    }

}
