# syntax=docker/dockerfile:latest
FROM --platform=$TARGETPLATFORM hackyo/debian:bookworm-slim AS build
LABEL maintainer="137120918@qq.com" version="20241023"
ENV MQTT_WOL_HOME=/opt/mqtt_wol
ENV EXECUTABLE_FILE=${MQTT_WOL_HOME}/mqtt-wol
COPY target/mqtt-wol ${EXECUTABLE_FILE}
ENTRYPOINT ./${EXECUTABLE_FILE}
