# syntax=docker/dockerfile:1
FROM hackyo/uv:0.8

LABEL maintainer="137120918@qq.com" version="20250904"

WORKDIR /opt/app

COPY pyproject.toml .python-version ./

RUN set -eux; \
    apt-get update; \
    apt-get install -y --no-install-recommends gcc python3-dev libkrb5-dev; \
    apt-get autoremove -y; \
    apt-get clean; \
    rm -rf /var/lib/apt/lists/*; \
    uv sync --no-cache

COPY main.py ./
COPY app ./app

RUN set -eux; \
    chmod -R a+rwX /opt/app /usr/local/uv

ENTRYPOINT ["docker-entrypoint.sh"]
CMD ["uv", "run", "--no-sync", "main.py"]
