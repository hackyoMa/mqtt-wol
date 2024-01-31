package com.github.mqttwol.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * WolProperties
 *
 * @author hackyo
 * @since 2022/4/1
 */
@ConfigurationProperties("wol")
@Component
@Data
public class WolProperties {

    private String address;
    private String macAddress;

}
