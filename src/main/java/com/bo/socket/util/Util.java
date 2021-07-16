package com.bo.socket.util;

public class Util {
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean shortArrayContains(short[] arr, short target) {
        for (var i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return true;
            }
        }

        return false;
    }
}
