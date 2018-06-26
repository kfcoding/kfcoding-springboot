FROM java:8

ADD ./kfcoding-rest-0.0.1-SNAPSHOT.jar  /rest-server.jar

EXPOSE 8081

CMD ["java","-jar","/rest-server.jar"]
