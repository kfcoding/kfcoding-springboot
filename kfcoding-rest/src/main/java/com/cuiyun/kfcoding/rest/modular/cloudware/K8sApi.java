package com.cuiyun.kfcoding.rest.modular.cloudware;

import com.google.gson.Gson;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.apis.ExtensionsV1beta1Api;
import io.kubernetes.client.models.V1DeleteOptions;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1Service;
import io.kubernetes.client.models.V1Status;
import io.kubernetes.client.util.Config;

import java.io.IOException;

public class K8sApi {
    // URL and TOKEN will be deleted in release

    private static K8sApi instance = new K8sApi();
    private CoreV1Api coreV1Api;
    private ExtensionsV1beta1Api extensionsV1beta1Api;

    private K8sApi() {

        ApiClient client = null;
        try {
            client = Config.defaultClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Configuration.setDefaultApiClient(client);

        coreV1Api = new CoreV1Api();
        extensionsV1beta1Api = new ExtensionsV1beta1Api();
    }

    public static K8sApi getInstance() {
        return instance;
    }

    /**
     * create terminal pod
     *
     * @param namespace
     * @param podName
     * @param imageName
     * @return
     */
    public V1Pod createTerminalPod(String namespace, String podName, String imageName) {

        V1Pod podBody = (V1Pod)
                deserialize(Template.TerminalPodTemplate, V1Pod.class);

        podBody.getMetadata().setNamespace(namespace);
        podBody.getMetadata().setName(podName);
        podBody.getMetadata().getLabels().clear();
        podBody.getMetadata().getLabels().put("app", podName);
        podBody.getSpec().getContainers().get(0).setImage(imageName);

        return createPod(namespace, podBody);
    }

    /**
     * create cloudware pod
     *
     * @param namespace
     * @param podName
     * @param imageName
     * @return
     */
    public V1Pod createCloudwarePod(String namespace, String podName, String imageName) {

        V1Pod podBody = (V1Pod)
                deserialize(Template.CloudwarePodTemplate, V1Pod.class);

        podBody.getMetadata().setNamespace(namespace);
        podBody.getMetadata().setName(podName);
        podBody.getMetadata().getLabels().clear();
        podBody.getMetadata().getLabels().put("app", podName);
        podBody.getSpec().getContainers().get(1).setImage(imageName);

        return createPod(namespace, podBody);
    }


    /**
     * create cloudware service
     *
     * @param namespace
     * @param deploymentName
     * @return
     */
    public V1Service createCloudwareService(String namespace, String deploymentName) {

        V1Service serviceBody =
                (V1Service) deserialize(Template.CloudwareServiceTemplate, V1Service.class);
        serviceBody.getMetadata().setNamespace(namespace);
        serviceBody.getMetadata().setName(deploymentName);
        serviceBody.getMetadata().getLabels().clear();
        serviceBody.getMetadata().getLabels().put("app", deploymentName);
        serviceBody.getSpec().getSelector().clear();
        serviceBody.getSpec().getSelector().put("app", deploymentName);

        return createService(namespace, serviceBody);
    }

    /**
     * common api for create k8s pod
     *
     * @param namespace
     * @param podBody
     * @return
     */
    private V1Pod createPod(String namespace, V1Pod podBody) {
        V1Pod result = null;
        try {
            result = coreV1Api.createNamespacedPod(namespace, podBody, "false");
        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
            e.printStackTrace();
        }
        return result;
    }


    /**
     * common api for create k8s service
     *
     * @param namespace
     * @param serviceBody
     * @return
     */
    private V1Service createService(String namespace, V1Service serviceBody) {
        V1Service result = null;
        System.out.println(serviceBody.toString());
        try {
            result = coreV1Api.createNamespacedService(namespace, serviceBody, "false");
        } catch (ApiException e) {
            System.out.println(e.getResponseBody());
            e.printStackTrace();
        }

        return result;
    }


    /**
     * common api for delete k8s service
     *
     * @param namespace
     * @param serviceName
     * @return
     */
    public V1Status deleteService(String namespace, String serviceName) {
        V1Status result = null;
        try {
            result = coreV1Api.deleteNamespacedService(serviceName, namespace, "false");
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * common api for delete k8s pod
     *
     * @param namespace
     * @param podName
     * @return
     */
    public V1Status deletePod(String namespace, String podName) {
        V1Status result = null;
        V1DeleteOptions options = new V1DeleteOptions();
        options.setApiVersion("v1");
        options.setKind("DeleteOptions");
        options.setOrphanDependents(false);

        try {
            result = coreV1Api.deleteNamespacedPod(
                    podName,
                    namespace,
                    options,
                    "false",
                    0,
                    false,
                    "Background");
        } catch (Exception e) {
            // Bug exist
            // e.printStackTrace();
        }
        return result;
    }


    public Object deserialize(String jsonStr, Class<?> targetClass) {
        Object obj = (new Gson()).fromJson(jsonStr, targetClass);
        return obj;
    }


//    /**
//     * common api for delete k8s deployment
//     *
//     * @param namespace
//     * @param deploymentName
//     * @return
//     */
//    public V1Status deleteDeployment(String namespace, String deploymentName) {
//        V1Status result = null;
//        V1DeleteOptions options = new V1DeleteOptions();
//        options.setApiVersion("extensions/v1beta1");
//        options.setKind("DeleteOptions");
//        options.setOrphanDependents(false);
//
//        try {
//            result = extensionsV1beta1Api.deleteNamespacedDeployment(
//                    deploymentName,
//                    namespace,
//                    options,
//                    "false",
//                    0,
//                    false,
//                    "Background");
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }


//    /**
//     * common api for create k8s deployment
//     *
//     * @param namespace
//     * @param deploymentBody
//     * @return
//     */
//    private ExtensionsV1beta1Deployment createDeployment(String namespace, ExtensionsV1beta1Deployment deploymentBody) {
//        ExtensionsV1beta1Deployment result = null;
//        try {
//            result = extensionsV1beta1Api.createNamespacedDeployment(namespace, deploymentBody, "false");
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }


//    /**
//     * create terminal deployment
//     *
//     * @param namespace
//     * @param deploymentName
//     * @param imageName
//     * @return
//     */
//    public ExtensionsV1beta1Deployment createTerminalDeployment(String namespace, String deploymentName, String imageName) {
//
//        ExtensionsV1beta1Deployment deploymentBody =
//                (ExtensionsV1beta1Deployment)
//                        deserialize(Template.TerminalDeploymentTemplate, ExtensionsV1beta1Deployment.class);
//        deploymentBody.getMetadata().setName(deploymentName);
//        deploymentBody.getSpec().getSelector().getMatchLabels().clear();
//        deploymentBody.getSpec().getSelector().getMatchLabels().put("app", deploymentName);
//        deploymentBody.getSpec().getTemplate().getMetadata().getLabels().put("app", deploymentName);
//        deploymentBody.getSpec().getTemplate().getSpec().getContainers().get(0).setImage(imageName);
//
//        return createDeployment(namespace, deploymentBody);
//    }
//
//    /**
//     * create cloudware deployment
//     *
//     * @param namespace
//     * @param deploymentName
//     * @param imageName
//     * @return
//     */
//    public ExtensionsV1beta1Deployment createCloudwareDeployment(String namespace, String deploymentName, String imageName) {
//
//        ExtensionsV1beta1Deployment deploymentBody =
//                (ExtensionsV1beta1Deployment)
//                        deserialize(Template.CloudwareDeplymentTemplate, ExtensionsV1beta1Deployment.class);
//        deploymentBody.getMetadata().setName(deploymentName);
//        deploymentBody.getMetadata().setNamespace(namespace);
//        deploymentBody.getSpec().getTemplate().getSpec().getContainers().get(0).setImage(imageName);
//        deploymentBody.getSpec().getSelector().getMatchLabels().clear();
//        deploymentBody.getSpec().getSelector().getMatchLabels().put("app", deploymentName);
//        deploymentBody.getSpec().getTemplate().getMetadata().getLabels().clear();
//        deploymentBody.getSpec().getTemplate().getMetadata().getLabels().put("app", deploymentName);
//
//        return createDeployment(namespace, deploymentBody);
//    }
}
