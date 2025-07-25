package com.github.mqttwol.properties;

/**
 * WinRMProperties
 *
 * @author hackyo
 * @since 2022/4/1
 */
public final class WinRMProperties {

    public final static String ADDRESS;
    public final static Integer PORT;
    public final static String USERNAME;
    public final static String PASSWORD;

    static {
        String address = System.getenv("MW_WINRM_ADDRESS");
        if (address == null || address.isBlank()) {
            address = "192.168.1.10";
        }
        ADDRESS = address;

        String port = System.getenv("MW_WINRM_PORT");
        if (port == null || port.isBlank()) {
            port = "5985";
        }
        try {
            PORT = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid MW_WINRM_PORT format");
        }

        String username = System.getenv("MW_WINRM_USERNAME");
        if (username == null || username.isBlank()) {
            username = "winrm";
        }
        USERNAME = username;

        String password = System.getenv("MW_WINRM_PASSWORD");
        if (password == null || password.isBlank()) {
            password = "pa55w0rd!";
        }
        PASSWORD = password;
    }

    public static String print() {
        return "WinRM configuration：" +
                "address='" + ADDRESS + '\'' +
                ", port=" + PORT +
                ", username='" + USERNAME + '\'' +
                ", password='" + PASSWORD + '\'';
    }

}
