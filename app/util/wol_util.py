import socket

WOL_PORT = 9
SEND_RETRIES = 3


def power_on(address: str, magic_packet: bytes):
    with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as sock:
        sock.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)
        for attempt in range(SEND_RETRIES):
            try:
                sock.sendto(magic_packet, (address, WOL_PORT))
                return
            except Exception:
                if attempt == SEND_RETRIES - 1:
                    raise
