package com.github.mqttwol.mqtt;

import com.github.mqttwol.common.MqttProperties;
import com.github.mqttwol.service.MqttWolService;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.ClientManager;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * MqttInboundConfig
 *
 * @author hackyo
 * @since 2022/4/1
 */
@Configuration
public class MqttInboundConfig {

    private final MqttProperties mqttProperties;
    private final ClientManager<IMqttAsyncClient, MqttConnectOptions> clientManager;
    private final MqttWolService mqttWolService;

    @Autowired
    public MqttInboundConfig(MqttProperties mqttProperties,
                             ClientManager<IMqttAsyncClient, MqttConnectOptions> clientManager,
                             MqttWolService mqttWolService) {
        this.mqttProperties = mqttProperties;
        this.clientManager = clientManager;
        this.mqttWolService = mqttWolService;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound(MessageChannel mqttInputChannel) {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(clientManager, mqttProperties.getTopic());
        adapter.setQos(0);
        adapter.setOutputChannel(mqttInputChannel);
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> mqttWolService.handle((String) message.getPayload());
    }

}
