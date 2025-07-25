package com.github.mqttwol.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HexFormat;

/**
 * WolUtils
 *
 * @author hackyo
 * @since 2022/4/1
 */
public final class WolUtils {

    private static final int WOL_PORT = 9;
    private static final int MAC_REPEAT_COUNT = 16;
    private static final byte[] MAGIC_PACKET_PREFIX = new byte[]{
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF
    };

    private static byte[] buildMagicPacket(byte[] macAddressBytes) {
        byte[] magicPacketBytes = new byte[MAGIC_PACKET_PREFIX.length + MAC_REPEAT_COUNT * macAddressBytes.length];
        System.arraycopy(MAGIC_PACKET_PREFIX, 0, magicPacketBytes, 0, MAGIC_PACKET_PREFIX.length);
        int position = MAGIC_PACKET_PREFIX.length;
        for (int i = 0; i < MAC_REPEAT_COUNT; i++) {
            System.arraycopy(macAddressBytes, 0, magicPacketBytes, position, macAddressBytes.length);
            position += macAddressBytes.length;
        }
        return magicPacketBytes;
    }

    public static void powerOn(InetAddress ipAddress, String macAddress) {
        byte[] macAddressBytes = HexFormat.of().parseHex(macAddress);
        byte[] magicPacketBytes = buildMagicPacket(macAddressBytes);
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);
            socket.send(new DatagramPacket(magicPacketBytes, magicPacketBytes.length, ipAddress, WOL_PORT));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
