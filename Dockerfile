# syntax=docker/dockerfile:latest
FROM hackyo/maven:3.9-graalvm-jdk-21 AS builder
LABEL maintainer="137120918@qq.com" version="20241023"

WORKDIR /opt/mqtt_wol
COPY . .
RUN apt install -y build-essential zlib1g-dev && \
    mvn -Pnative package

FROM hackyo/debian:bookworm-slim
COPY --from=builder /opt/mqtt_wol/target/mqtt-wol /opt/mqtt_wol/mqtt-wol
ENTRYPOINT ["/opt/mqtt_wol/mqtt-wol"]
