package com.github.mqttwol.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * WolProperties
 *
 * @author hackyo
 * @since 2022/4/1
 */
@ConfigurationProperties("wol")
@Component
public class WolProperties {

    private String address;
    private String macAddress;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Override
    public String toString() {
        return "Wol配置：{" +
                "address='" + address + '\'' +
                ", macAddress='" + macAddress + '\'' +
                '}';
    }

}
