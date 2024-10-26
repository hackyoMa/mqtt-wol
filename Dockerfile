# syntax=docker/dockerfile:latest
ARG MAINTAINER="137120918@qq.com"
ARG VERSION="20241023"

ARG PROJECT_HOME=/opt/mqtt_wol

FROM --platform=${TARGETPLATFORM} hackyo/maven:3.9-graalvm-jdk-21 AS builder
WORKDIR ${PROJECT_HOME}
COPY . .
RUN mvn -Pnative package

FROM --platform=${TARGETPLATFORM} hackyo/debian:bookworm-slim
COPY --from=builder ${PROJECT_HOME}/target/mqtt-wol ${PROJECT_HOME}/mqtt-wol
ENTRYPOINT .${PROJECT_HOME}/mqtt-wol
