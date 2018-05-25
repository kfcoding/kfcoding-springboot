package com.cuiyun.kfcoding.rest.modular.cloudware;

public class Template {
    public final static String DeplymentTemplate = "{\n" +
            "  \"apiVersion\": \"extensions/v1beta1\",\n" +
            "  \"kind\": \"Deployment\",\n" +
            "  \"metadata\": {\n" +
            "    \"name\": \"cloudware-1\",\n" +
            "    \"namespace\": \"kfcoding-alpha\"\n" +
            "  },\n" +
            "  \"spec\": {\n" +
            "    \"replicas\": 1,\n" +
            "    \"selector\": {\n" +
            "      \"matchLabels\": {\n" +
            "        \"app\": \"cloudware-1\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"template\": {\n" +
            "      \"metadata\": {\n" +
            "        \"labels\": {\n" +
            "          \"app\": \"cloudware-1\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"spec\": {\n" +
            "        \"containers\": [\n" +
            "          {\n" +
            "            \"name\": \"xorg-server\",\n" +
            "            \"command\": [\n" +
            "              \"Xorg\"\n" +
            "            ],\n" +
            "            \"image\": \"daocloud.io/shaoling/kfcoding-xorg:master-094594c\",\n" +
            "            \"volumeMounts\": [\n" +
            "              {\n" +
            "                \"name\": \"app-tmp\",\n" +
            "                \"mountPath\": \"/tmp\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"pulsar\",\n" +
            "            \"image\": \"daocloud.io/shaoling/kfcoding-pulsar:master\",\n" +
            "            \"ports\": [\n" +
            "              {\n" +
            "                \"containerPort\": 9800\n" +
            "              }\n" +
            "            ],\n" +
            "            \"volumeMounts\": [\n" +
            "              {\n" +
            "                \"name\": \"app-tmp\",\n" +
            "                \"mountPath\": \"/tmp\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"rstudio\",\n" +
            "            \"image\": \"daocloud.io/shaoling/kfcoding-rstudio:master-e2af784\",\n" +
            "            \"volumeMounts\": [\n" +
            "              {\n" +
            "                \"name\": \"app-tmp\",\n" +
            "                \"mountPath\": \"/tmp\"\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        ],\n" +
            "        \"volumes\": [\n" +
            "          {\n" +
            "            \"name\": \"app-tmp\",\n" +
            "            \"emptyDir\": {}\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    public static final String ServiceTemplate = "{\n" +
            "  \"apiVersion\": \"v1\",\n" +
            "  \"kind\": \"Service\",\n" +
            "  \"metadata\": {\n" +
            "    \"name\": \"cloudware-1-svc\",\n" +
            "    \"namespace\": \"kfcoding-alpha\",\n" +
            "    \"labels\": {\n" +
            "      \"app\": \"cloudware-1\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"spec\": {\n" +
            "    \"ports\": [\n" +
            "      {\n" +
            "        \"port\": 9800,\n" +
            "        \"targetPort\": 9800\n" +
            "      }\n" +
            "    ],\n" +
            "    \"selector\": {\n" +
            "      \"app\": \"cloudware-1\"\n" +
            "    }\n" +
            "  }\n" +
            "}";

}
