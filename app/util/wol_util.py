import binascii
import socket

WOL_PORT = 9
MAC_REPEAT_COUNT = 16
SEND_RETRIES = 3
MAGIC_PACKET_PREFIX = b"\xFF" * 6


def power_on(address: str, mac_address: str):
    magic_packet = MAGIC_PACKET_PREFIX + binascii.unhexlify(mac_address) * MAC_REPEAT_COUNT
    with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as sock:
        sock.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)
        for attempt in range(SEND_RETRIES):
            try:
                sock.sendto(magic_packet, (address, WOL_PORT))
                return
            except Exception:
                if attempt == SEND_RETRIES - 1:
                    raise
