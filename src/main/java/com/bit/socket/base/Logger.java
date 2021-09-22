package com.bit.socket.base;

public class Logger {
    static boolean IS_LOGGING = true;

    public static void logf(String pattern, Object... arguments) {
        if (IS_LOGGING) {
            System.out.printf("[lib] " + pattern, arguments);
        }
    }

    public static void logln(String pattern) {
        if (IS_LOGGING) {
            System.out.println("[lib] " + pattern);
        }
    }

    public static void logln() {
        if (IS_LOGGING) {
            System.out.println();
        }
    }

    public static void log(String pattern) {
        if (IS_LOGGING) {
            System.out.print(pattern);
        }
    }
}
