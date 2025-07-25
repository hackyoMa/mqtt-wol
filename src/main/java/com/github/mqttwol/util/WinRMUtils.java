package com.github.mqttwol.util;

import io.cloudsoft.winrm4j.client.WinRmClientContext;
import io.cloudsoft.winrm4j.winrm.WinRmTool;
import io.cloudsoft.winrm4j.winrm.WinRmToolResponse;

/**
 * WinRMUtils
 *
 * @author hackyo
 * @since 2022/4/1
 */
public final class WinRMUtils {

    private static final String AUTHENTICATION_SCHEME = "NTLM";
    private static final String SHUTDOWN_COMMAND = "shutdown /s /t 0";

    public static void powerOff(String address, Integer port, String username, String password) {
        WinRmClientContext context = null;
        try {
            context = WinRmClientContext.newInstance();
            WinRmTool tool = WinRmTool.Builder.builder(address, username, password)
                    .authenticationScheme(AUTHENTICATION_SCHEME)
                    .port(port)
                    .useHttps(false)
                    .disableCertificateChecks(true)
                    .context(context)
                    .build();
            WinRmToolResponse response = tool.executePs(SHUTDOWN_COMMAND);
            if (response.getStatusCode() != 0) {
                throw new RuntimeException("Failed to execute shutdown command: " + response.getStdErr());
            }
        } finally {
            if (context != null) {
                context.shutdown();
            }
        }
    }

}
