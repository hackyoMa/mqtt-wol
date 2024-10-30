package com.github.mqttwol.util;

import io.cloudsoft.winrm4j.client.WinRmClientContext;
import io.cloudsoft.winrm4j.winrm.WinRmTool;

/**
 * WinRMUtils
 *
 * @author hackyo
 * @since 2022/4/1
 */
public final class WinRMUtils {

    public static void powerOff(String address, Integer port, String username, String password) {
        WinRmClientContext context = WinRmClientContext.newInstance();
        WinRmTool tool = WinRmTool.Builder.builder(address, username, password)
                .authenticationScheme("NTLM")
                .port(port)
                .useHttps(false)
                .context(context)
                .build();
        tool.executeCommand("shutdown -p");
        context.shutdown();
    }

}
