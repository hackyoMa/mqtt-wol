package com.github.mqttwol.listener;

import com.github.mqttwol.properties.MqttWolConfig;
import com.github.mqttwol.util.WinRMUtils;
import com.github.mqttwol.util.WolUtils;
import net.dreamlu.iot.mqtt.codec.MqttVersion;
import net.dreamlu.iot.mqtt.core.client.IMqttClientConnectListener;
import net.dreamlu.iot.mqtt.core.client.MqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.tio.core.ChannelContext;

import java.nio.charset.StandardCharsets;

/**
 * MqttListener
 *
 * @author hackyo
 * @since 2022/4/1
 */
public final class MqttListener {

    private static final Logger logger = LoggerFactory.getLogger(MqttListener.class);

    public static void initListener() {
        logger.info("开始订阅主题：{}", MqttWolConfig.mqttProperties.getTopic());
        MqttClient client = MqttClient.create()
                .ip(MqttWolConfig.mqttProperties.getServer())
                .port(MqttWolConfig.mqttProperties.getPort())
                .version(MqttVersion.MQTT_3_1_1)
                .clientId(MqttWolConfig.mqttProperties.getClientId())
                .connectListener(new IMqttClientConnectListener() {
                    @Override
                    public void onConnected(ChannelContext context, boolean isReconnect) {
                        logger.info("链接MQTT服务器成功：{}", MqttWolConfig.mqttProperties.getServer());
                    }

                    @Override
                    public void onDisconnect(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) {
                        logger.info("与MQTT服务器断开连接：{}", MqttWolConfig.mqttProperties.getServer());
                    }
                })
                .connectSync();
        client.subQos0(MqttWolConfig.mqttProperties.getTopic(),
                (context, topic, message, payload) -> handle(new String(payload, StandardCharsets.UTF_8)));
        logger.info("订阅主题成功：{}", MqttWolConfig.mqttProperties.getTopic());
    }

    private static void handle(String message) {
        if (!StringUtils.hasText(message)) {
            return;
        }
        logger.info("接收到{}主题消息：{}", MqttWolConfig.mqttProperties.getTopic(), message);
        if ("on".equalsIgnoreCase(message)) {
            WolUtils.powerOn(MqttWolConfig.wolProperties.getAddress(), MqttWolConfig.wolProperties.getMacAddress());
            logger.info("执行开机成功");
        } else if ("off".equalsIgnoreCase(message)) {
            WinRMUtils.powerOff(MqttWolConfig.winRMProperties.getAddress(), MqttWolConfig.winRMProperties.getPort(),
                    MqttWolConfig.winRMProperties.getUsername(), MqttWolConfig.winRMProperties.getPassword());
            logger.info("执行关机成功");
        } else {
            logger.info("未定义的操作");
        }
    }

}
