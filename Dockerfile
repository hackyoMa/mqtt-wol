# syntax=docker/dockerfile:1
FROM hackyo/uv:0

LABEL org.opencontainers.image.authors="hackyo" \
      org.opencontainers.image.version="1.0.0" \
      org.opencontainers.image.source="https://github.com/hackyoMa/mqtt-wol"

RUN set -eux; \
    apt-get update; \
    apt-get install -y --no-install-recommends gcc python3-dev libkrb5-dev; \
    apt-get clean; \
    rm -rf /var/lib/apt/lists/*

USER appuser
WORKDIR /home/appuser/mqtt-wol

COPY --chown=appuser:appuser pyproject.toml .python-version main.py ./
COPY --chown=appuser:appuser app ./app

RUN set -eux; \
    uv sync --no-cache

CMD ["uv", "run", "--no-sync", "main.py"]
