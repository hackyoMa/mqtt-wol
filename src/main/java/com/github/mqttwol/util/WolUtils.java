package com.github.mqttwol.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * WolUtils
 *
 * @author hackyo
 * @since 2022/4/1
 */
public final class WolUtils {

    private static final String MAGIC_PACKET_PREFIX = "FFFFFFFFFFFF";
    private static final int WOL_PORT = 9;

    private static byte[] hexToBinary(String hexString) {
        byte[] result = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length() / 2; i++) {
            result[i] = (byte) ((hexToDec(hexString.charAt(i + i)) << 4) | (hexToDec(hexString.charAt(i + i + 1))));
        }
        return result;
    }

    private static byte hexToDec(char c) {
        return (byte) (c < 'A' ? (c - '0') : (c - 'A' + 10));
    }

    public static void powerOn(String ipAddress, String macAddress) {
        macAddress = macAddress.replace("-", "")
                .replace(":", "")
                .replace(".", "")
                .toUpperCase();
        byte[] magicPacketBytes = hexToBinary(MAGIC_PACKET_PREFIX + macAddress.repeat(16));
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.send(new DatagramPacket(magicPacketBytes, magicPacketBytes.length, InetAddress.getByName(ipAddress), WOL_PORT));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
