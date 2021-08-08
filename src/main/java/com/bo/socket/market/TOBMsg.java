package com.bo.socket.market;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

import com.bo.socket.base.*;
import com.bo.socket.auth.*;
import com.bo.socket.constant.*;

public class TOBMsg extends Message {
    public TOBMsg() {
        MessageTypeStr = "TOBMsg";
        MessageLen = 100;
    }

    public void createExampleMessage() {
        Data1 = 't';
        Data2 = 0;
        MessageType = 1; // BUY
        Padding = 0;
        SymbolEnum = 4;
        SymbolType = 1; // SPOT
        MarketDataSymbolName = "BTCUSDT".getBytes();
        LastTradePrice = 60000.5;
        Last24Vol = 60000.5;
        High = 60000.5;
        Low = 60000.5;
        LvlPrice.BuyPrice = 60000.5;
        LvlPrice.BuyVolume = 60000.5;
        LvlPrice.SellPrice = 60000.5;
        LvlPrice.SellVolume = 60000.5;
    }

    public void makeByteBuffer() {
        super.makeByteBuffer();
        
        byteBuffer.put(Data1)
            .put(Data2)
            .putShort(MessageLen)
            .putShort(MessageType)
            .putShort(Padding)
            .putShort(SymbolEnum)
            .putShort(SymbolType)
            .put(MarketDataSymbolName).position(24)
            .putDouble(LastTradePrice)
            .putDouble(Last24Vol)
            .putDouble(High)
            .putDouble(Low);
        LvlPrice.put(byteBuffer);
    }

    public int read(DataInputStream in) {
        int error = super.read(in);
        if (error != ReadError.NO_ERROR) {
            return error;
        }

        MessageType = byteBuffer.getShort();
        Padding = byteBuffer.getShort();
        SymbolEnum = byteBuffer.getShort();
        byteBuffer.get(MarketDataSymbolName);
        LastTradePrice = byteBuffer.getDouble();
        Last24Vol = byteBuffer.getDouble();
        High = byteBuffer.getDouble();
        Low = byteBuffer.getDouble();
        LvlPrice.get(byteBuffer);

        return ReadError.NO_ERROR;
    }

    public void print() {
        Logger.logln("MessageType: " + MessageType);
        Logger.logln("Padding: " + Padding);
        Logger.logln("SymbolEnum: " + SymbolEnum);
        Logger.logln("SymbolType: " + SymbolType);
        Logger.logln("SymbolName: " + new String(SymbolName));
        Logger.logln("LastTradePrice: " + LastTradePrice);
        Logger.logln("Last24Vol: " + Last24Vol);
        Logger.logln("High: " + High);
        Logger.logln("Low: " + Low);
        LvlPrice.logln();
    }
}
