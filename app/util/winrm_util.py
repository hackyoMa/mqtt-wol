import winrm

SCHEME_HTTP = "http"
SCHEME_HTTPS = "https"
TRANSPORT_SSL = "ssl"
TARGET_CONTEXT = "wsman"
SERVER_CERT_VALIDATION = "ignore"
SHUTDOWN_COMMAND = "shutdown /s /t 0"


def power_off(host: str, port: int, transport: str, username: str, password: str):
    scheme = SCHEME_HTTPS if transport == TRANSPORT_SSL else SCHEME_HTTP
    target = f"{scheme}://{host}:{port}/{TARGET_CONTEXT}"
    session = winrm.Session(target=target, auth=(username, password), transport=transport,
                            server_cert_validation=SERVER_CERT_VALIDATION)
    result = session.run_ps(SHUTDOWN_COMMAND)
    if result.status_code != 0:
        raise RuntimeError(result.std_err.decode("utf-8").strip())
