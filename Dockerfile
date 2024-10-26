# syntax=docker/dockerfile:latest
LABEL maintainer="137120918@qq.com" version="20241023"
ARG MQTT_WOL_HOME=/opt/mqtt_wol

FROM --platform=$TARGETPLATFORM hackyo/maven:3.9-graalvm-jdk-21 AS builder
WORKDIR ${MQTT_WOL_HOME}
COPY . .
RUN mvn -Pnative package

FROM --platform=$TARGETPLATFORM hackyo/debian:bookworm-slim
COPY --from=builder ${MQTT_WOL_HOME}/target/mqtt-wol ${MQTT_WOL_HOME}/mqtt-wol
ENTRYPOINT .${MQTT_WOL_HOME}/mqtt-wol
