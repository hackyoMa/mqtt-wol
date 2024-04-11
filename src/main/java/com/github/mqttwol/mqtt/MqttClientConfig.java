package com.github.mqttwol.mqtt;

import com.github.mqttwol.common.MqttProperties;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3Client;
import com.hivemq.client.mqtt.mqtt3.Mqtt3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MqttClientConfig
 *
 * @author hackyo
 * @since 2022/4/1
 */
@Configuration
public class MqttClientConfig {

    private final MqttProperties mqttProperties;

    @Autowired
    public MqttClientConfig(MqttProperties mqttProperties) {
        this.mqttProperties = mqttProperties;
    }

    @Bean
    public Mqtt3AsyncClient mqttClient() {
        Mqtt3ClientBuilder mqttClientBuilder = Mqtt3Client.builder()
                .identifier(this.mqttProperties.getClientId())
                .serverHost(this.mqttProperties.getServerHost())
                .serverPort(this.mqttProperties.getServerPort())
                .automaticReconnectWithDefaultConfig();
        Mqtt3AsyncClient mqttClient;
        if (Boolean.TRUE.equals(this.mqttProperties.getServerSsl())) {
            mqttClient = mqttClientBuilder.sslWithDefaultConfig().buildAsync();
        } else {
            mqttClient = mqttClientBuilder.buildAsync();
        }
        mqttClient.connect();
        return mqttClient;
    }

}
