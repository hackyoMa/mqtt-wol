package com.github.mqttwol.properties;

/**
 * WolProperties
 *
 * @author hackyo
 * @since 2022/4/1
 */
public final class WolProperties {

    public final static String ADDRESS;
    public final static String MAC_ADDRESS;

    static {
        String address = System.getenv("MW_WOL_ADDRESS");
        if (address == null || address.isBlank()) {
            address = "192.168.1.10";
        }
        ADDRESS = address;

        String macAddress = System.getenv("MW_WOL_MAC_ADDRESS");
        if (macAddress == null || macAddress.isBlank()) {
            macAddress = "E8:9C:22:BB:44:A4";
        }
        MAC_ADDRESS = macAddress;
    }

    public static String print() {
        return "Wol配置：{" +
                "address='" + ADDRESS + '\'' +
                ", macAddress='" + MAC_ADDRESS + '\'' +
                '}';
    }

}
