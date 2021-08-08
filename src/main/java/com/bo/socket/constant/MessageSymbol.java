package com.bo.socket.constant;

public class MessageSymbol {
    public static byte CLIENT_LOGON = 'H';
    public static byte NEW_LIMIT_ORDER = 'T';
    public static byte INSTRUMENT_REQUEST = 'Y';
    public static byte RISK_UPDATE_REQUEST = 'w';
    public static byte COLLATERAL_REQUEST = 'f';
    public static byte MD_SUBSCRIBE = 's';
    public static byte MD_EXEC_REPORT = 'V';
    public static byte TOB_MSG = 't';
    public static byte THREE_LEVEL_DATA = 'M';
    public static byte FIVE_LEVEL_DATA = 'm';
    public static byte TEN_LEVEL_DATA = 'O';
    public static byte TWENTY_LEVEL_DATA = 'S';
}
