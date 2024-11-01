# syntax=docker/dockerfile:latest
FROM hackyo/jre:21
LABEL maintainer="137120918@qq.com" version="20241030"
COPY target/mqtt-wol-1.0.0-jar-with-dependencies.jar /opt/app/app.jar
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
