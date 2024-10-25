package com.github.mqttwol;

import com.github.mqttwol.listener.MqttListener;
import com.github.mqttwol.properties.MqttProperties;
import com.github.mqttwol.properties.MqttWolConfig;
import com.github.mqttwol.properties.WinRMProperties;
import com.github.mqttwol.properties.WolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application
 *
 * @author hackyo
 * @since 2022/4/1
 */
@SpringBootApplication
public class Application {

    @Autowired
    public Application(MqttProperties mqttProperties,
                       WinRMProperties winRMProperties,
                       WolProperties wolProperties) {
        MqttWolConfig.mqttProperties = mqttProperties;
        MqttWolConfig.winRMProperties = winRMProperties;
        MqttWolConfig.wolProperties = wolProperties;
        MqttListener.initListener();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
