package com.github.mqttwol;

import com.github.mqttwol.listener.MqttListener;
import com.github.mqttwol.properties.MqttProperties;
import com.github.mqttwol.properties.MqttWolConfig;
import com.github.mqttwol.properties.WinRMProperties;
import com.github.mqttwol.properties.WolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    public Application(MqttProperties mqttProperties,
                       WinRMProperties winRMProperties,
                       WolProperties wolProperties) {
        MqttWolConfig.mqttProperties = mqttProperties;
        logger.info(MqttWolConfig.mqttProperties.toString());
        MqttWolConfig.winRMProperties = winRMProperties;
        logger.info(MqttWolConfig.winRMProperties.toString());
        MqttWolConfig.wolProperties = wolProperties;
        logger.info(MqttWolConfig.wolProperties.toString());
        MqttListener.initListener();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
