package com.github.mqttwol.properties;

/**
 * MqttProperties
 *
 * @author hackyo
 * @since 2022/4/1
 */
public final class MqttProperties {

    public final static String SERVER;
    public final static Integer PORT;
    public final static String CLIENT_ID;
    public final static Integer VERSION;
    public final static String TOPIC;

    static {
        String server = System.getenv("MW_MQTT_SERVER");
        if (server == null || server.isBlank()) {
            server = "bemfa.com";
        }
        SERVER = server;

        String port = System.getenv("MW_MQTT_PORT");
        if (port == null || port.isBlank()) {
            port = "9501";
        }
        try {
            PORT = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid MW_MQTT_PORT format");
        }

        String clientId = System.getenv("MW_MQTT_CLIENT_ID");
        if (clientId == null || clientId.isBlank()) {
            clientId = "cba8e6b25bb2ad84b9ad132fa8364b84";
        }
        CLIENT_ID = clientId;

        String version = System.getenv("MW_MQTT_VERSION");
        if (version == null || version.isBlank()) {
            version = "3";
        }
        try {
            VERSION = Integer.parseInt(version);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid MW_MQTT_VERSION format");
        }

        String topic = System.getenv("MW_MQTT_TOPIC");
        if (topic == null || topic.isBlank()) {
            topic = "pc006";
        }
        TOPIC = topic;
    }

    public static String print() {
        return "MQTT configuration：" +
                "server='" + SERVER + '\'' +
                ", port=" + PORT +
                ", clientId='" + CLIENT_ID + '\'' +
                ", version=" + VERSION +
                ", topic='" + TOPIC + '\'';
    }

}
