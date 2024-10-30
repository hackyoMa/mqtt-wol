package com.github.mqttwol;

import com.github.mqttwol.listener.MqttListener;
import com.github.mqttwol.properties.MqttProperties;
import com.github.mqttwol.properties.WinRMProperties;
import com.github.mqttwol.properties.WolProperties;
import com.github.mqttwol.util.LogUtils;

/**
 * Application
 *
 * @author hackyo
 * @since 2022/4/1
 */
public final class Application {

    public static void main(String[] args) {
        LogUtils.info(MqttProperties.print());
        LogUtils.info(WinRMProperties.print());
        LogUtils.info(WolProperties.print());
        MqttListener.initListener();
    }

}
