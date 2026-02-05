# syntax=docker/dockerfile:1
FROM hackyo/uv:0

LABEL maintainer="137120918@qq.com" version="20260204"

WORKDIR /opt/app

COPY pyproject.toml .python-version main.py ./
COPY app ./app

RUN set -eux; \
    apt-get update; \
    apt-get install -y --no-install-recommends gcc python3-dev libkrb5-dev; \
    rm -rf /var/lib/apt/lists/*; \
    chmod -R 755 /opt/app; \
    uv sync --no-cache

ENTRYPOINT ["container-init.sh"]
CMD uv run --no-sync main.py
