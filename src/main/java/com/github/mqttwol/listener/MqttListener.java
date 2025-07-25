package com.github.mqttwol.listener;

import com.github.mqttwol.properties.MqttProperties;
import com.github.mqttwol.properties.WinRMProperties;
import com.github.mqttwol.properties.WolProperties;
import com.github.mqttwol.util.LogUtils;
import com.github.mqttwol.util.WinRMUtils;
import com.github.mqttwol.util.WolUtils;
import net.dreamlu.iot.mqtt.codec.MqttVersion;
import net.dreamlu.iot.mqtt.core.client.IMqttClientConnectListener;
import net.dreamlu.iot.mqtt.core.client.MqttClient;
import org.tio.core.ChannelContext;

import java.nio.charset.StandardCharsets;

/**
 * MqttListener
 *
 * @author hackyo
 * @since 2022/4/1
 */
public final class MqttListener {

    private static final String ON_COMMAND = "on";
    private static final String OFF_COMMAND = "off";
    private static MqttClient CLIENT;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (CLIENT != null) {
                CLIENT.stop();
            }
        }));
    }

    public static void initListener() {
        CLIENT = MqttClient.create()
                .ip(MqttProperties.SERVER)
                .port(MqttProperties.PORT)
                .version(MqttVersion.MQTT_3_1_1)
                .clientId(MqttProperties.CLIENT_ID)
                .connectListener(new IMqttClientConnectListener() {
                    @Override
                    public void onConnected(ChannelContext context, boolean isReconnect) {
                        LogUtils.info("Successfully connected to bemfa server");
                        LogUtils.info("Start subscribing to message topics：%s", MqttProperties.TOPIC);
                        CLIENT.subQos1(MqttProperties.TOPIC,
                                (channelContext, topic, message, payload) ->
                                        Thread.startVirtualThread(() -> handle(new String(payload, StandardCharsets.UTF_8))));
                        LogUtils.info("Message topic subscription successful: %s", MqttProperties.TOPIC);
                    }

                    @Override
                    public void onDisconnect(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) {
                        LogUtils.info("Failed to connect to bemfa server: %s, remark: %s", MqttProperties.TOPIC, remark);
                    }
                })
                .connect();
        LogUtils.info("Start connecting to the bemfa server");
    }

    private static void handle(String message) {
        if (message == null || message.isBlank()) {
            LogUtils.info("Received empty or blank message");
            return;
        }
        LogUtils.info("Received message with topic %s: %s", MqttProperties.TOPIC, message);
        switch (message.trim().toLowerCase()) {
            case ON_COMMAND:
                processPowerOn();
                break;
            case OFF_COMMAND:
                processPowerOff();
                break;
            default:
                LogUtils.info("Undefined operation for message: %s", message);
        }
    }

    private static void processPowerOn() {
        try {
            LogUtils.info("Start booting up");
            WolUtils.powerOn(WolProperties.ADDRESS, WolProperties.MAC_ADDRESS);
            LogUtils.info("Bootup successful");
        } catch (Exception e) {
            LogUtils.info("Bootup failed: %s", e.getMessage());
        }
    }

    private static void processPowerOff() {
        try {
            LogUtils.info("Start shutting down");
            WinRMUtils.powerOff(WinRMProperties.ADDRESS, WinRMProperties.PORT,
                    WinRMProperties.USERNAME, WinRMProperties.PASSWORD);
            LogUtils.info("Shutdown successful");
        } catch (Exception e) {
            LogUtils.info("Shutdown failed: %s", e.getMessage());
        }
    }

}
