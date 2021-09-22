package com.bit.socket.constant;

public class MessageTypes {
    public static short ORDER_NEW = 1;
    public static short CANCEL_REPLACE = 2;
    public static short MARGIN_CANCEL_REPLACE = 3;
    public static short MARGIN_EXECUTE = 4;
    public static short ORDER_STATUS = 5;
    public static short ORDER_CANCEL = 6;
    public static short MARGIN_CANCEL = 7;
    public static short EXECUTION = 8;
    public static short EXECUTION_PARTIAL = 9;
    public static short MARGIN_EXECUTION = 10;
    public static short MARGIN_PARTIAL_EXECUTION = 11;
    public static short REJECT = 12;
    public static short ORDER_REJECT = 13;
    public static short ORDER_ACK = 14;
    public static short CANCELLED = 15;
    public static short REPLACED = 16;
    public static short QUOTE_FILL = 17;
    public static short QUOTE_FILL_PARTIAL = 18;
    public static short MARGIN_REPLACED = 19;
    public static short CANCEL_REPLACE_REJECT = 20;
    public static short INSTRUMENT_DATA = 21;
    public static short INSTRUMENT_RESPONSE = 22;
    public static short RISK_REJECT = 23;
}
