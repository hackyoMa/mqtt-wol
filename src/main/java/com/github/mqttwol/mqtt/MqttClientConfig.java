package com.github.mqttwol.mqtt;

import com.github.mqttwol.common.MqttProperties;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.ClientManager;
import org.springframework.integration.mqtt.core.Mqttv3ClientManager;

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
    public ClientManager<IMqttAsyncClient, MqttConnectOptions> clientManager() {
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setServerURIs(new String[]{mqttProperties.getServerUri()});
        connectOptions.setMqttVersion(mqttProperties.getVersion());
        connectOptions.setAutomaticReconnect(true);
        connectOptions.setMaxReconnectDelay(1000);
        connectOptions.setConnectionTimeout(60000);
        Mqttv3ClientManager clientManager = new Mqttv3ClientManager(connectOptions, mqttProperties.getClientId());
        clientManager.setPersistence(new MqttDefaultFilePersistence());
        return clientManager;
    }

}
