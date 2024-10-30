package com.github.mqttwol.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * LogUtils
 *
 * @author hackyo
 * @since 2022/4/1
 */
public final class LogUtils {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void info(String msg, Object... args) {
        System.out.printf(SDF.format(new Date()) + " INFO - " + msg + "%n", args);
    }

}
