package com.github.mqttwol.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * MqttProperties
 *
 * @author hackyo
 * @since 2022/4/1
 */
@ConfigurationProperties("mqtt")
@Component
@Data
public class MqttProperties {

    private String clientId;
    private String serverHost;
    private Integer serverPort;
    private Boolean serverSsl;
    private String topic;

}
