# syntax=docker/dockerfile:1
FROM hackyo/uv:0

LABEL maintainer="137120918@qq.com" version="20260312"

RUN set -eux; \
    apt-get update; \
    apt-get install -y --no-install-recommends gcc python3-dev libkrb5-dev; \
    rm -rf /var/lib/apt/lists/*

USER appuser
WORKDIR /home/appuser/mqtt-wol

COPY pyproject.toml .python-version main.py ./
COPY app ./app

RUN set -eux; \
    uv sync --no-cache

CMD ["uv", "run", "--no-sync", "main.py"]
