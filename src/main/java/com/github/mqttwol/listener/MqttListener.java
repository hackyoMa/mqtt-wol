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

    public static void initListener() {
        LogUtils.info("开始订阅主题：%s", MqttProperties.TOPIC);
        MqttClient client = MqttClient.create()
                .ip(MqttProperties.SERVER)
                .port(MqttProperties.PORT)
                .version(MqttVersion.MQTT_3_1_1)
                .clientId(MqttProperties.CLIENT_ID)
                .connectListener(new IMqttClientConnectListener() {
                    @Override
                    public void onConnected(ChannelContext context, boolean isReconnect) {
                        LogUtils.info("链接MQTT服务器成功：%s", MqttProperties.SERVER);
                    }

                    @Override
                    public void onDisconnect(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) {
                        LogUtils.info("与MQTT服务器断开连接：%s", MqttProperties.SERVER);
                    }
                })
                .connectSync();
        client.subQos0(MqttProperties.TOPIC,
                (context, topic, message, payload) -> handle(new String(payload, StandardCharsets.UTF_8)));
        LogUtils.info("订阅主题成功：%s", MqttProperties.TOPIC);
    }

    private static void handle(String message) {
        if (message == null || message.isBlank()) {
            return;
        }
        LogUtils.info("接收到%s主题消息：%s", MqttProperties.TOPIC, message);
        if ("on".equalsIgnoreCase(message)) {
            WolUtils.powerOn(WolProperties.ADDRESS, WolProperties.MAC_ADDRESS);
            LogUtils.info("执行开机成功");
        } else if ("off".equalsIgnoreCase(message)) {
            WinRMUtils.powerOff(WinRMProperties.ADDRESS, WinRMProperties.PORT,
                    WinRMProperties.USERNAME, WinRMProperties.PASSWORD);
            LogUtils.info("执行关机成功");
        } else {
            LogUtils.info("未定义的操作");
        }
    }

}
