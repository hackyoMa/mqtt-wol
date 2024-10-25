package com.github.mqttwol.listener;

import com.github.mqttwol.properties.MqttWolConfig;
import com.github.mqttwol.util.WinRMUtils;
import com.github.mqttwol.util.WolUtils;
import net.dreamlu.iot.mqtt.codec.MqttVersion;
import net.dreamlu.iot.mqtt.core.client.MqttClient;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * MqttListener
 *
 * @author hackyo
 * @since 2022/4/1
 */
public final class MqttListener {

    public static void initListener() {
        MqttClient client = MqttClient.create()
                .ip(MqttWolConfig.mqttProperties.getServer())
                .port(MqttWolConfig.mqttProperties.getPort())
                .version(MqttVersion.MQTT_3_1_1)
                .clientId(MqttWolConfig.mqttProperties.getClientId())
                .connectSync();
        client.subQos0(MqttWolConfig.mqttProperties.getTopic(),
                (context, topic, message, payload) -> handle(new String(payload, StandardCharsets.UTF_8)));
    }

    private static void handle(String message) {
        if (!StringUtils.hasText(message)) {
            return;
        }
        if ("on".equalsIgnoreCase(message)) {
            WolUtils.powerOn(MqttWolConfig.wolProperties.getAddress(), MqttWolConfig.wolProperties.getMacAddress());
        } else if ("off".equalsIgnoreCase(message)) {
            WinRMUtils.powerOff(MqttWolConfig.winRMProperties.getAddress(), MqttWolConfig.winRMProperties.getPort(),
                    MqttWolConfig.winRMProperties.getUsername(), MqttWolConfig.winRMProperties.getPassword());
        }
    }

}
