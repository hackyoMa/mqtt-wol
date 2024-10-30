# syntax=docker/dockerfile:latest
FROM hackyo/jre:21 AS builder
LABEL maintainer="137120918@qq.com" version="20241023"
ENV APP_HOME=/opt/mqtt_wol
ENV APP_FILE=${APP_HOME}/app.jar
COPY target/mqtt-wol-1.0.0-jar-with-dependencies.jar ${APP_FILE}
ENTRYPOINT ["java", "-jar", "${APP_FILE}", "${JAVA_OPTIONS}"]
