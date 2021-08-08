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

public class BaseLevelData extends Message {
    protected int LevelCount = 0;

    public BaseLevelData() {
    }

    public void createExampleMessage() {
        Data2 = 0;
        MessageType = 1; // BUY
        Padding = 0;
        SymbolEnum = 4;
        SymbolType = 1; // SPOT
        SendingTime = 0;
        MsgSeqNum = 1;
        StartLayer = 1;
        MarketDataSymbolName = "BTCUSDT".getBytes();
        // 20 prices
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
            .putLong(SendingTime)
            .putInt(MsgSeqNum)
            .putShort(StartLayer)
            .put(MarketDataSymbolName).position(38);
        for (int i = 0; i < LevelCount; i++) {
            Prices[i].put(byteBuffer);
        }
    }

    public int read(DataInputStream in) {
        int error = super.read(in);
        if (error != ReadError.NO_ERROR) {
            return error;
        }

        MessageType = byteBuffer.getShort();
        Padding = byteBuffer.getShort();
        SymbolEnum = byteBuffer.getShort();
        SendingTime = byteBuffer.getLong();
        MsgSeqNum = byteBuffer.getInt();
        StartLayer = byteBuffer.getShort();
        byteBuffer.get(MarketDataSymbolName);
        for (int i = 0; i < LevelCount; i++) {
            Prices[i].get(byteBuffer);
        }

        return ReadError.NO_ERROR;
    }

    public void print() {
        Logger.logln("MessageType: " + MessageType);
        Logger.logln("Padding: " + Padding);
        Logger.logln("SymbolEnum: " + SymbolEnum);
        Logger.logln("SymbolType: " + SymbolType);
        Logger.logln("SendingTime: " + SendingTime);
        Logger.logln("MsgSeqNum: " + MsgSeqNum);
        Logger.logln("StartLayer: " + StartLayer);
        Logger.logln("SymbolName: " + new String(SymbolName));
        for (int i = 0; i < LevelCount; i++) {
            Prices[i].logln();
        }
    }
}
