package com.github.mqttwol.mqtt;

import com.github.mqttwol.common.MqttProperties;
import com.github.mqttwol.service.MqttWolService;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

/**
 * MqttInboundConfig
 *
 * @author hackyo
 * @since 2022/4/1
 */
@Configuration
public class MqttInboundConfig implements Consumer<Mqtt3Publish> {

    private final MqttWolService mqttWolService;

    @Autowired
    public MqttInboundConfig(MqttProperties mqttProperties,
                             Mqtt3AsyncClient mqttClient,
                             MqttWolService mqttWolService) {
        this.mqttWolService = mqttWolService;
        mqttClient
                .toAsync()
                .subscribeWith()
                .topicFilter(mqttProperties.getTopic())
                .qos(MqttQos.AT_LEAST_ONCE)
                .callback(this)
                .send();
    }

    @Override
    public void accept(Mqtt3Publish mqtt3Publish) {
        this.mqttWolService.handle(new String(mqtt3Publish.getPayloadAsBytes(), StandardCharsets.UTF_8));
    }

}
