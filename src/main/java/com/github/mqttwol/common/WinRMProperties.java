package com.github.mqttwol.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * WinRMProperties
 *
 * @author hackyo
 * @since 2022/4/1
 */
@ConfigurationProperties("winrm")
@Component
@Data
public class WinRMProperties {

    private String address;
    private Integer port;
    private String username;
    private String password;

}
