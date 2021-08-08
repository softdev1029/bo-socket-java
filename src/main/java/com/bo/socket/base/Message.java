package com.bo.socket.base;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.nio.channels.*;
import java.util.*;

import com.bo.socket.constant.*;
import com.bo.socket.util.*;

abstract public class Message {
    protected ByteBuffer byteBuffer = null;

    protected int HEADER_LEN = 4;
    public String MessageTypeStr = "";

    public byte Data1 = 0;
    public byte Data2 = 0;
    public short MessageLen = 0;
    public short MessageType = 0;
    public short Padding = 0;
    public int Account = 0;
    public long OrderID = 0;
    public short SymbolEnum = 0;
    public short OrderType = 0;
    public short SymbolType = 0;
    public double BOPrice = 0;
    public short BOSide = 0;
    public double BOOrderQty = 0;
    public short TIF = 0;
    public double StopLimitPrice = 0;
    public byte[] BOSymbol = new byte[12];
    public long OrigOrderID = 0;
    public double BOCancelShares = 0;
    public long ExecID = 0;
    public double ExecShares = 0;
    public double RemainingQuantity = 0;
    public double ExecFee = 0;
    public byte[] ExpirationDate = new byte[12];
    public int TraderID = 0;
    public short RejectReason = 0;
    public long SendingTime = 0;
    public int TradingSessionID = 0;
    public int Key = 0;
    public double DisplaySize = 0;
    public double RefreshSize = 0;
    public short Layers = 0;
    public double SizeIncrement = 0;
    public double PriceIncrement = 0;
    public double PriceOffset = 0;
    public double BOOrigPrice = 0;
    public double ExecPrice = 0;
    public int MsgSeqNum = 0;
    public double TakeProfitPrice = 0;
    public short TriggerType = 0;
    public byte[] Attributes = new byte[12];
    
    // Client Logon
    public short LogonType = 0;
    public byte[] TwoFA = new byte[6];
    public byte[] UserName = new byte[6];
    public byte[] PrimaryOrderEntryIP = new byte[24];
    public byte[] SecondaryOrderEntryIP = new byte[24];
    public byte[] PrimaryMarketDataIP = new byte[24];
    public byte[] SecondaryMarketDataIP = new byte[24];
    public int LastSeqNum = 0;
    public short LoginStatus = 0;
    public byte RiskMaster = 0;

    // Instrument Request
    public short RequestType = 0;
    public byte[] SymbolName = new byte[24];
    public int RejectReason4Byte = 0;

    // Risk Update Request
    public short ResponseType = 0;

    // Collateral Request
    public short UpdateType = 0;

    // MDExecReport
    public double Volume = 0;

    // TOBMsg
    public BasePrice LvlPrice = new BasePrice();

    // FiveLevelData
    public byte[] MarketDataSymbolName = new byte[12];
    public double LastTradePrice = 0;
    public double Last24Vol = 0;
    public double High = 0;
    public double Low = 0;
    public Price Lvl1Price = new Price();
    public Price Lvl2Price = new Price();
    public Price Lvl3Price = new Price();
    public Price Lvl4Price = new Price();
    public Price Lvl5Price = new Price();

    // TenLevelData
    public short StartLayer = 0;
    public AtomPrice[] Prices = new AtomPrice[20];

    abstract public void createExampleMessage();

    public void makeByteBuffer() {
        byteBuffer = ByteBuffer.allocate(MessageLen);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    public void send(DataOutputStream out) {
        try {
            byteBuffer.position(0);
            WritableByteChannel channel = Channels.newChannel(out);
            channel.write(byteBuffer);

            Logger.logf("Sending %s message, %s bytes ...",
                MessageTypeStr,
                MessageLen);
            printBuffer(byteBuffer);
            Logger.logln("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int read(DataInputStream in) {
        try {
            int needRead = MessageLen - HEADER_LEN;
            byteBuffer = ByteBuffer.allocate(needRead);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            int alreadyRead = 0;
            while (in.available() > 0 && alreadyRead != needRead) {
                byteBuffer.put((byte) in.read());
                alreadyRead++;
            }

            Logger.logf("Received %s message, need %s bytes, read %s bytes.",
                MessageTypeStr,
                MessageLen,
                alreadyRead + HEADER_LEN);
            printBuffer(byteBuffer);
            Logger.logln("");

            if (needRead != alreadyRead) {
                Logger.logln("Invalid read length");
                return ReadError.INVALID_BUFFER_SIZE;
            }

            byteBuffer.position(0);
            return ReadError.NO_ERROR;
        } catch (IOException e) {
            e.printStackTrace();
            return ReadError.UNKNOWN_ERROR;
        }
    }

    abstract public void print();

    protected byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                  byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }

    protected char[] getChars(ByteBuffer buffer, int len) {
        byte[] bytes = new byte[len];
        buffer.get(bytes);
        String str = new String(bytes);
        return str.toCharArray();
    }

    protected void printBytes(byte[] bytes) {
        for (byte b : bytes) {
            String st = String.format("%02X", b);
            Logger.log(st);
        }
    }

    protected void printBuffer(ByteBuffer buffer) {
        buffer.position(0);
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        printBytes(bytes);
    }

    protected boolean validate() {
        if (
            MessageType < Constant.MSGTYPE_MIN_VALUE
            || MessageType > Constant.ORDTYPE_MAX_VALUE
        ) {
            RejectReason = RejectCode.MSG_TYPE_INVALID;
            return false;
        }
    
        if (
            OrderType < Constant.ORDTYPE_MIN_VALUE
            || OrderType > Constant.ORDTYPE_MAX_VALUE
        ) {
            RejectReason = RejectCode.ORD_TYPE_INVALID;
            return false;
        }
    
        if (MessageType == MessageTypes.ORDER_NEW) {
            if (Util.shortArrayContains(new short[]{
                OrderTypes.LMT,
                OrderTypes.HIDDEN,
                OrderTypes.PEG,
                OrderTypes.PEG_HIDDEN,
                OrderTypes.OCO,
                OrderTypes.ICE,
            }, OrderType)) {
                var ds = Attributes[Attribute.DISPLAYSIZE_ATTRIBUTE];
                if (ds == 'Y') {
                    if (DisplaySize <= 0) {
                        RejectReason = RejectCode.DISPLAY_SIZE_INVALID;
                    }
                } else if (RejectReason <= 0) {
                    RejectReason = RejectCode.REFRESH_SIZE_INVALID;
                }
    
                var se = SymbolEnum;
                if (se < Constant.SE_MIN_VALUE || se > Constant.SE_MAX_VALUE) {
                    RejectReason = RejectCode.SYMBOL_ENUM_INVALID;
                    return false;
                }
    
                if (TradingSessionID < 0) {
                    RejectReason = RejectCode.TRADING_SESSION_INVALID;
                    return false;
                }
    
                if (BOPrice < Constant.ORDER_MIN_PRICE) {
                    RejectReason = RejectCode.PRICE_INVALID;
                    return false;
                }
    
                if (BOOrderQty < Constant.ORDER_MIN_SIZE) {
                    RejectReason = RejectCode.SIZE_INVALID;
                    return false;
                }
    
                var side = BOSide;
                if (side < 1 || side > 3) {
                    RejectReason = RejectCode.ORDER_SIDE_INVALID;
                    return false;
                }
    
                if (Account <= 0) {
                    RejectReason = RejectCode.ACCOUNT_INVALID;
                    return false;
                }
    
                if (OrderID <= 0) {
                    RejectReason = RejectCode.ORDERID_INVALID;
                    return false;
                }
    
                if (SendingTime <= 0) {
                    RejectReason = RejectCode.SENDING_TIME_INVALID;
                    return false;
                }
            }
    
            if (OrderType == OrderTypes.ICE) {
                if (Layers > Constant.MAX_LAYERS) {
                    RejectReason = RejectCode.EXCEEDED_MAX_LAYERS;
                    return false;
                }
    
                if (SizeIncrement <= 0) {
                    RejectReason = RejectCode.SIZE_INCREMENT_INVALID;
                    return false;
                }
    
                if (PriceOffset < 0) {
                    RejectReason = RejectCode.PRICE_OFFSET_INVALID;
                    return false;
                }
    
                if (PriceIncrement < 0) {
                    RejectReason = RejectCode.PRICE_INCREMENT_INVALID;
                    return false;
                }
            }
    
            if (OrderType == OrderTypes.OCO) {
                if (StopLimitPrice <= 0) {
                    RejectReason = RejectCode.STOP_PRICE_INVALID;
                    return false;
                }
            }
        } else if (MessageType == MessageTypes.ORDER_CANCEL) {
            if (Util.shortArrayContains(new short[]{
                OrderTypes.LMT,
                OrderTypes.HIDDEN,
                OrderTypes.PEG,
                OrderTypes.PEG_HIDDEN,
                OrderTypes.OCO,
                OrderTypes.ICE,
            }, OrderType)) {
                var se = SymbolEnum;
                if (se < Constant.SE_MIN_VALUE || se > Constant.SE_MAX_VALUE) {
                    RejectReason = RejectCode.SYMBOL_ENUM_INVALID;
                    return false;
                }
    
                if (BOPrice < Constant.ORDER_MIN_PRICE) {
                    RejectReason = RejectCode.PRICE_INVALID;
                    return false;
                }
    
                if (BOOrigPrice < Constant.ORDER_MIN_PRICE) {
                    RejectReason = RejectCode.ORIG_PRICE_INVALID;
                    return false;
                }
    
                var side = BOSide;
                if (side < 1 || side > 3) {
                    RejectReason = RejectCode.ORDER_SIDE_INVALID;
                    return false;
                }
    
                if (Account <= 0) {
                    RejectReason = RejectCode.ACCOUNT_INVALID;
                    return false;
                }
    
                if (OrderID <= 0) {
                    RejectReason = RejectCode.ORDERID_INVALID;
                    return false;
                }
    
                if (OrigOrderID <= 0) {
                    RejectReason = RejectCode.ORIG_ORDER_ID_INVALID;
                    return false;
                }
    
                if (SendingTime <= 0) {
                    RejectReason = RejectCode.SENDING_TIME_INVALID;
                    return false;
                }
            }
        } else if (MessageType == MessageTypes.CANCEL_REPLACE) {
            if (Util.shortArrayContains(new short[]{
                OrderTypes.LMT,
                OrderTypes.HIDDEN,
                OrderTypes.PEG,
                OrderTypes.PEG_HIDDEN,
                OrderTypes.OCO,
                OrderTypes.ICE,
            }, OrderType)) {
                var attributes = Attributes;
                var ds = attributes[Attribute.DISPLAYSIZE_ATTRIBUTE];
                if (ds == 'Y') {
                    if (DisplaySize <= 0) {
                        RejectReason = RejectCode.DISPLAY_SIZE_INVALID;
                    }
    
                    if (RefreshSize <= 0) {
                        RejectReason = RejectCode.REFRESH_SIZE_INVALID;
                    }
                }
    
                var se = SymbolEnum;
                if (se < Constant.SE_MIN_VALUE || se > Constant.SE_MAX_VALUE) {
                    RejectReason = RejectCode.SYMBOL_ENUM_INVALID;
                    return false;
                }
    
                if (BOPrice < Constant.ORDER_MIN_PRICE) {
                    RejectReason = RejectCode.PRICE_INVALID;
                    return false;
                }
    
                if (OrigOrderID < Constant.ORDER_MIN_PRICE) {
                    RejectReason = RejectCode.ORIG_PRICE_INVALID;
                    return false;
                }
    
                var side = BOSide;
                if (side < 1 || side > 3) {
                    RejectReason = RejectCode.ORDER_SIDE_INVALID;
                    return false;
                }
    
                if (Account <= 0) {
                    RejectReason = RejectCode.ACCOUNT_INVALID;
                    return false;
                }
    
                if (OrderID <= 0) {
                    RejectReason = RejectCode.ORDERID_INVALID;
                    return false;
                }
    
                if (OrigOrderID <= 0) {
                    RejectReason = RejectCode.ORIG_ORDER_ID_INVALID;
                    return false;
                }
    
                if (SendingTime <= 0) {
                    RejectReason = RejectCode.SENDING_TIME_INVALID;
                    return false;
                }
            } else if (Util.shortArrayContains(new short[]{
                OrderTypes.STOP_MKT,
                OrderTypes.STOP_LMT,
                OrderTypes.SNIPER_MKT,
                OrderTypes.SNIPER_LIMIT,
                OrderTypes.TSM,
                OrderTypes.TSL,
            }, OrderType)) {
                var se = SymbolEnum;
                if (se < Constant.SE_MIN_VALUE || se > Constant.SE_MAX_VALUE) {
                    RejectReason = RejectCode.SYMBOL_ENUM_INVALID;
                    return false;
                }

                if (StopLimitPrice < Constant.ORDER_MIN_PRICE) {
                    RejectReason = RejectCode.PRICE_INVALID;
                    return false;
                }
    
                if (OrigOrderID < Constant.ORDER_MIN_PRICE) {
                    RejectReason = RejectCode.ORIG_PRICE_INVALID;
                    return false;
                }
    
                var side = BOSide;
                if (side < 1 || side > 3) {
                    RejectReason = RejectCode.ORDER_SIDE_INVALID;
                    return false;
                }
    
                if (Account <= 0) {
                    RejectReason = RejectCode.ACCOUNT_INVALID;
                    return false;
                }
    
                if (OrderID <= 0) {
                    RejectReason = RejectCode.ORDERID_INVALID;
                    return false;
                }
    
                if (SendingTime <= 0) {
                    RejectReason = RejectCode.SENDING_TIME_INVALID;
                    return false;
                }
            }
        }

        return true;
    }

    public void printRejectReason() {
        Logger.logln("Reject Code: " + RejectReason);
        var msg = "Reject Reason: ";
        if ( RejectReason == RejectCode.ORDER_NOT_FOUND ) {
            msg += "ORDER_NOT_FOUND";
        } else if ( RejectReason == RejectCode.USER_NOT_FOUND ) {
            msg += "USER_NOT_FOUND";
        } else if ( RejectReason == RejectCode.ACCOUNT_NOT_FOUND ) {
            msg += "ACCOUNT_NOT_FOUND";
        } else if ( RejectReason == RejectCode.INVALID_KEY ) {
            msg += "INVALID_KEY";
        } else if ( RejectReason == RejectCode.ACCOUNT_DISABLED ) {
            msg += "ACCOUNT_DISABLED";
        } else if ( RejectReason == RejectCode.TRADING_SESSION_INVALID ) {
            msg += "TRADING_SESSION_INVALID";
        } else if ( RejectReason == RejectCode.RISK_ACCOUNT_NOT_FOUND ) {
            msg += "RISK_ACCOUNT_NOT_FOUND";
        } else if ( RejectReason == RejectCode.RISK_SYMBOL_NOT_FOUND ) {
            msg += "RISK_SYMBOL_NOT_FOUND";
        } else if ( RejectReason == RejectCode.MES_NOT_AVAILABLE_TRADING_DISABLED ) {
            msg += "MES_NOT_AVAILABLE_TRADING_DISABLED";
        } else if ( RejectReason == RejectCode.OES_NOT_AVAILABLE_TRADING_DISABLED ) {
            msg += "OES_NOT_AVAILABLE_TRADING_DISABLED";
        } else if ( RejectReason == RejectCode.MDS_NOT_AVAILABLE_TRADING_DISABLED ) {
            msg += "MDS_NOT_AVAILABLE_TRADING_DISABLED";
        } else if ( RejectReason == RejectCode.MSG_TYPE_INVALID ) {
            msg += "MSG_TYPE_INVALID";
        } else if ( RejectReason == RejectCode.ORD_TYPE_INVALID ) {
            msg += "ORD_TYPE_INVALID";
        } else if ( RejectReason == RejectCode.PRICE_INVALID ) {
            msg += "PRICE_INVALID";
        } else if ( RejectReason == RejectCode.SIZE_INVALID ) {
            msg += "SIZE_INVALID";
        } else if ( RejectReason == RejectCode.STOP_PRICE_INVALID ) {
            msg += "STOP_PRICE_INVALID";
        } else if ( RejectReason == RejectCode.STOP_SIZE_INVALID ) {
            msg += "STOP_SIZE_INVALID";
        } else if ( RejectReason == RejectCode.ORDER_SIDE_INVALID ) {
            msg += "ORDER_SIDE_INVALID";
        } else if ( RejectReason == RejectCode.ACCOUNT_INVALID ) {
            msg += "ACCOUNT_INVALID";
        } else if ( RejectReason == RejectCode.ORDERID_INVALID ) {
            msg += "ORDERID_INVALID";
        } else if ( RejectReason == RejectCode.SENDING_TIME_INVALID ) {
            msg += "SENDING_TIME_INVALID";
        } else if ( RejectReason == RejectCode.ORIG_PRICE_INVALID ) {
            msg += "ORIG_PRICE_INVALID";
        } else if ( RejectReason == RejectCode.ORIG_SIZE_INVALID ) {
            msg += "ORIG_SIZE_INVALID";
        } else if ( (
            RejectReason
            == RejectCode.ICE_SIZEINCREMENT_TIMES_LAYERS_NOT_EQUAL_ORDQTY
        ) ) {
            msg += "ICE_SIZEINCREMENT_TIMES_LAYERS_NOT_EQUAL_ORDQTY";
        } else if ( RejectReason == RejectCode.ORIG_ORDER_ID_INVALID ) {
            msg += "ORIG_ORDER_ID_INVALID";
        } else if ( RejectReason == RejectCode.SYMBOL_ENUM_INVALID ) {
            msg += "SYMBOL_ENUM_INVALID";
        } else if ( RejectReason == RejectCode.SIZE_INCREMENT_INVALID ) {
            msg += "SIZE_INCREMENT_INVALID";
        } else if ( RejectReason == RejectCode.PRICE_OFFSET_INVALID ) {
            msg += "PRICE_OFFSET_INVALID";
        } else if ( RejectReason == RejectCode.PRICE_INCREMENT_INVALID ) {
            msg += "PRICE_INCREMENT_INVALID";
        } else if ( RejectReason == RejectCode.EXCEEDED_MAX_LAYERS ) {
            msg += "EXCEEDED_MAX_LAYERS";
        } else if ( RejectReason == RejectCode.DISPLAY_SIZE_INVALID ) {
            msg += "DISPLAY_SIZE_INVALID";
        } else if ( RejectReason == RejectCode.REFRESH_SIZE_INVALID ) {
            msg += "REFRESH_SIZE_INVALID";
        } else if ( RejectReason == RejectCode.INVALID_SECURITY_KEY ) {
            msg += "INVALID_SECURITY_KEY";
        } else if ( RejectReason == RejectCode.USER_ALREADY_LOGGED_IN ) {
            msg += "USER_ALREADY_LOGGED_IN";
        } else if ( RejectReason == RejectCode.INVALID_FIELD_VALUE ) {
            msg += "INVALID_FIELD_VALUE";
        } else if ( (
            RejectReason
            == RejectCode.PERCENTAGE_MOVE_EXCEEDED_COOLING_OFF_PERIOD_IN_FORCE
        ) ) {
            msg += "PERCENTAGE_MOVE_EXCEEDED_COOLING_OFF_PERIOD_IN_FORCE";
        } else if ( (
            RejectReason == RejectCode.INSTRUMET_WOULD_CAUSE_MARGIN_TO_BE_EXCEEDED
        ) ) {
            msg += "INSTRUMET_WOULD_CAUSE_MARGIN_TO_BE_EXCEEDED";
        } else if ( RejectReason == RejectCode.INSTRUMENT_MARGIN_EXCEEDED ) {
            msg += "INSTRUMENT_MARGIN_EXCEEDED";
        } else if ( RejectReason == RejectCode.MARGIN_BUY_ORDER_CANCELLATION_IN_PROGRESS ) {
            msg += "MARGIN_BUY_ORDER_CANCELLATION_IN_PROGRESS";
        } else if ( (
            RejectReason == RejectCode.MARGIN_SELL_ORDER_CANCELLATION_IN_PROGRESS
        ) ) {
            msg += "MARGIN_SELL_ORDER_CANCELLATION_IN_PROGRESS";
        } else if ( (
            RejectReason
            == RejectCode.MARGIN_LONG_POSITION_LIQUIDATION_IN_PROGRESS
        ) ) {
            msg += "MARGIN_LONG_POSITION_LIQUIDATION_IN_PROGRESS";
        } else if ( (
            RejectReason
            == RejectCode.MARGIN_SHORT_POSITION_LIQUIDATION_IN_PROGRESS
        ) ) {
            msg += "MARGIN_SHORT_POSITION_LIQUIDATION_IN_PROGRESS";
        } else if ( RejectReason == RejectCode.OUTSTANDING_OPEN_REQUESTS_EXCEEDED ) {
            msg += "OUTSTANDING_OPEN_REQUESTS_EXCEEDED";
        } else if ( RejectReason == RejectCode.NO_RISK_DATA ) {
            msg += "NO_RISK_DATA";
        } else if ( RejectReason == RejectCode.DUPLICATE_ORDER_ID ) {
            msg += "DUPLICATE_ORDER_ID";
        } else if ( RejectReason == RejectCode.EXCEEDS_OPEN_ORDER_REQUESTS ) {
            msg += "EXCEEDS_OPEN_ORDER_REQUESTS";
        } else if ( RejectReason == RejectCode.NOT_ENOUGH_EQUITY_TO_COMPLETE ) {
            msg += "NOT_ENOUGH_EQUITY_TO_COMPLETE";
        } else if ( RejectReason == RejectCode.MATCHING_ENGINE_REJECTED ) {
            msg += "MATCHING_ENGINE_REJECTED";
        } else if ( RejectReason == RejectCode.NONE ) {
            msg += "NONE";
        } else if ( RejectReason == RejectCode.ACCEPTED ) {
            msg += "ACCEPTED";
        } else if ( RejectReason == RejectCode.KEY_INVALID ) {
            msg += "KEY_INVALID";
        } else if ( RejectReason == RejectCode.MSG_SEQ_NUM_INVALID ) {
            msg += "MSG_SEQ_NUM_INVALID";
        } else if ( RejectReason == RejectCode.USER_ALREADY_LGGGED_IN ) {
            msg += "USER_ALREADY_LGGGED_IN";
        } else if ( RejectReason == RejectCode.ORIG_ORDER_NOT_FOUND ) {
            msg += "ORIG_ORDER_NOT_FOUND";
        } else if ( RejectReason == RejectCode.USER_ALREADY_LGGGED_IN ) {
            msg += "USER_ALREADY_LGGGED_IN";
        } else if ( RejectReason == RejectCode.INVALID_LOGON_TYPE ) {
            msg += "INVALID_LOGON_TYPE";
        } else if ( RejectReason == RejectCode.CANT_EXECUTE_AGAINST_EXCHANGE_ORDER ) {
            msg += "CANT_EXECUTE_AGAINST_EXCHANGE_ORDER";
        } else if ( RejectReason == RejectCode.NO_MARKET_MAKER_VOLUME ) {
            msg += "NO_MARKET_MAKER_VOLUME";
        } else {
            msg += "UNKNOWN_REJCT";
        }
        Logger.logln(msg);
    }
}
