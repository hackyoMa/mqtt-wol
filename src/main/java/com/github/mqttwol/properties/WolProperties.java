package com.github.mqttwol.properties;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * WolProperties
 *
 * @author hackyo
 * @since 2022/4/1
 */
public final class WolProperties {

    public final static InetAddress ADDRESS;
    public final static String MAC_ADDRESS;

    static {
        String address = System.getenv("MW_WOL_ADDRESS");
        if (address == null || address.isBlank()) {
            address = "pc.hackyo.cn";
        }
        try {
            ADDRESS = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("Invalid MW_WOL_ADDRESS format");
        }

        String macAddress = System.getenv("MW_WOL_MAC_ADDRESS");
        if (macAddress == null || macAddress.isBlank()) {
            macAddress = "E8:9C:25:DD:55:A4";
        }
        MAC_ADDRESS = macAddress.replaceAll("[^A-Fa-f0-9]", "").toUpperCase();
        if (MAC_ADDRESS.length() != 12) {
            throw new IllegalArgumentException("Invalid MW_WOL_MAC_ADDRESS format");
        }
    }

    public static String print() {
        return "Wol configuration：" +
                "address='" + ADDRESS + '\'' +
                ", macAddress='" + MAC_ADDRESS + '\'';
    }

}
