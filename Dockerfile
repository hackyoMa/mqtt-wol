# syntax=docker/dockerfile:latest
FROM --platform=$TARGETPLATFORM hackyo/maven:3.9-graalvm-jdk-21 AS build
LABEL maintainer="137120918@qq.com" version="20241023"
RUN mvn -Pnative package

FROM --platform=$TARGETPLATFORM hackyo/debian:bookworm-slim AS run
ENV MQTT_WOL_HOME=/opt/mqtt_wol
ENV EXECUTABLE_FILE=${MQTT_WOL_HOME}/mqtt-wol
COPY --from=build target/mqtt-wol ${EXECUTABLE_FILE}
ENTRYPOINT ./${EXECUTABLE_FILE}
