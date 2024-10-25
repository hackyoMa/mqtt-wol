package com.github.mqttwol.properties;

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
public class MqttProperties {

    private String server;
    private Integer port;
    private String clientId;
    private Integer version;
    private String topic;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "MQTT配置：{" +
                "server='" + server + '\'' +
                ", port=" + port +
                ", clientId='" + clientId + '\'' +
                ", version=" + version +
                ", topic='" + topic + '\'' +
                '}';
    }

}
