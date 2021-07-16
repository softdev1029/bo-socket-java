package com.bo.socket.base;

import java.util.*;

import com.bo.socket.constant.*;

public class CreateMessage {
    public static Map<String, String> REQUEST_MESSAGE_TYPES = new HashMap<String, String>();

    static {
        REQUEST_MESSAGE_TYPES.put("H", RequestMessageTypes.MSG_CLIENT_LOGON);
        REQUEST_MESSAGE_TYPES.put("Y", RequestMessageTypes.MSG_INSTRUMENT_REQUEST);
        REQUEST_MESSAGE_TYPES.put("w", RequestMessageTypes.MSG_RISK_UPDATE_REQUEST);
        REQUEST_MESSAGE_TYPES.put("E", RequestMessageTypes.MSG_OPEN_ORDER_REQUEST);
        REQUEST_MESSAGE_TYPES.put("f", RequestMessageTypes.MSG_COLLATERAL_REQUEST);
        REQUEST_MESSAGE_TYPES.put("T", RequestMessageTypes.MSG_NEW_LIMIT_ORDER);
    }

    public static boolean isValidMessageType(String msgTypeKey) {
        
        if (REQUEST_MESSAGE_TYPES.containsKey(msgTypeKey)) {
            return true;
        }

        if (msgTypeKey.equals("0")) {
            return true;
        }

        return false;
    }

    public static void printAllRequestMessageTypes() {
        System.out.println();
        System.out.println("0 - Go To Receive Mode");

        for (String key : REQUEST_MESSAGE_TYPES.keySet()) {
            System.out.printf("%s - %s", key, REQUEST_MESSAGE_TYPES.get(key));
            System.out.println("");
        }

        System.out.println();
    }
}