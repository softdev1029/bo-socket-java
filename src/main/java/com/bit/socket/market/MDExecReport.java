package com.bit.socket.market;

import java.io.DataInputStream;

import com.bit.socket.base.Logger;
import com.bit.socket.base.Message;
import com.bit.socket.constant.ReadError;

public class MDExecReport extends Message {
    public MDExecReport() {
        MessageTypeStr = "MDExecReport";
        MessageLen = 54;
    }

    public void createExampleMessage() {
        Data1 = 'V';
        Data2 = 0;
        MessageType = 1; // ORDER_NEW
        Padding = 0;
        SymbolEnum = 4;
        SymbolType = 1; // SPOT
        BOPrice = 60000.5;
        Volume = 1.3;
        SendingTime = 1833810281;
        MsgSeqNum = 52;
        BOSide = 1; // BUY
        MarketDataSymbolName = "BTCUSDT".getBytes();
    }

    public void makeByteBuffer() {
        super.makeByteBuffer();

        byteBuffer.put(Data1).put(Data2).putShort(MessageLen).putShort(MessageType).putShort(Padding)
                .putShort(SymbolEnum).putShort(SymbolType).putDouble(BOPrice).putDouble(Volume).putLong(SendingTime)
                .putInt(MsgSeqNum).putShort(BOSide).put(MarketDataSymbolName);
    }

    public int read(DataInputStream in) {
        int error = super.read(in);
        if (error != ReadError.NO_ERROR) {
            return error;
        }

        MessageType = byteBuffer.getShort();
        Padding = byteBuffer.getShort();
        SymbolEnum = byteBuffer.getShort();
        SymbolType = byteBuffer.getShort();
        BOPrice = byteBuffer.getDouble();
        Volume = byteBuffer.getDouble();
        SendingTime = byteBuffer.getLong();
        MsgSeqNum = byteBuffer.getInt();
        BOSide = byteBuffer.getShort();
        byteBuffer.get(MarketDataSymbolName);

        return ReadError.NO_ERROR;
    }

    public void print() {
        Logger.logln("MessageType: " + MessageType);
        Logger.logln("Padding: " + Padding);
        Logger.logln("SymbolEnum: " + SymbolEnum);
        Logger.logln("SymbolType: " + SymbolType);
        Logger.logln("BOPrice: " + BOPrice);
        Logger.logln("Volume: " + Volume);
        Logger.logln("SendingTime: " + SendingTime);
        Logger.logln("MsgSeqNum: " + MsgSeqNum);
        Logger.logln("BOSide: " + BOSide);
        Logger.logln("MarketDataSymbolName: " + new String(MarketDataSymbolName));
    }
}
