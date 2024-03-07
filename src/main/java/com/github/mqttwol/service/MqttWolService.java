package com.github.mqttwol.service;

import com.github.mqttwol.common.WinRMProperties;
import com.github.mqttwol.common.WolProperties;
import com.github.mqttwol.util.WinRMUtils;
import com.github.mqttwol.util.WolUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * MqttWolService
 *
 * @author hackyo
 * @since 2022/4/1
 */
@Service
public class MqttWolService {

    private static final String MSG_TYPE_ON = "on";
    private static final String MSG_TYPE_OFF = "off";

    private final WolProperties wolProperties;
    private final WinRMProperties winRMProperties;

    public MqttWolService(WolProperties wolProperties,
                          WinRMProperties winRMProperties) {
        this.wolProperties = wolProperties;
        this.winRMProperties = winRMProperties;
    }

    public void handle(String message) {
        if (!StringUtils.hasText(message)) {
            return;
        }
        if (MSG_TYPE_ON.equalsIgnoreCase(message)) {
            WolUtils.powerOn(this.wolProperties.getAddress(), this.wolProperties.getMacAddress());
        }
        if (MSG_TYPE_OFF.equalsIgnoreCase(message)) {
            WinRMUtils.powerOff(this.winRMProperties.getAddress(), this.winRMProperties.getPort(), this.winRMProperties.getUsername(), this.winRMProperties.getPassword());
        }
    }

}
