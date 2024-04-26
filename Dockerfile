# syntax=docker/dockerfile:latest
FROM --platform=$TARGETPLATFORM hackyo/jre:21 AS build
LABEL maintainer="137120918@qq.com" version="20240426"
ENV MQTT_WOL_HOME=/opt/mqtt_wol
ENV JAVA_APP_JAR=${MQTT_WOL_HOME}/mqtt-wol.jar
COPY target/mqtt-wol-1.0.0.jar ${MQTT_WOL_HOME}/mqtt-wol.jar
ENTRYPOINT java -jar ${JAVA_APP_JAR} ${JAVA_OPTIONS}