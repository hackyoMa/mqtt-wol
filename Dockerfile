# syntax=docker/dockerfile:latest
ARG MAINTAINER="137120918@qq.com"
ARG VERSION="20241023"

ARG PROJECT_HOME=/opt/mqtt_wol

FROM hackyo/maven:3.9-graalvm-jdk-21 AS builder
WORKDIR ${PROJECT_HOME}
COPY . .
RUN apt install -y build-essential zlib1g-dev && \
    mvn -Pnative package

FROM hackyo/debian:bookworm-slim
COPY --from=builder ${PROJECT_HOME}/target/mqtt-wol ${PROJECT_HOME}/mqtt-wol
ENTRYPOINT ["${PROJECT_HOME}/mqtt-wol"]
