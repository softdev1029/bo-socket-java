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

public class MDSubscribe extends Message {
    public MDSubscribe() {
        MessageTypeStr = "MDSubscribe";
        MessageLen = 32;
    }

    public void createExampleMessage() {
        Data1 = 's';
        Data2 = 0;
        MessageType = 1; // TOB
        Padding = 0;
        Account = 100700;
        Key = 123456;
        TradingSessionID = 200;
        SendingTime = 1842320291;
        MsgSeqNum = 52488131;
    }

    public void makeByteBuffer() {
        super.makeByteBuffer();
        
        byteBuffer.put(Data1)
            .put(Data2)
            .putShort(MessageLen)
            .putShort(MessageType)
            .putShort(Padding)
            .putInt(Account)
            .putInt(Key)
            .putInt(TradingSessionID)
            .putLong(SendingTime)
            .putInt(MsgSeqNum);
    }

    public int read(DataInputStream in) {
        int error = super.read(in);
        if (error != ReadError.NO_ERROR) {
            return error;
        }

        MessageType = byteBuffer.getShort();
        Padding = byteBuffer.getShort();
        Account = byteBuffer.getInt();
        Key = byteBuffer.getInt();
        TradingSessionID = byteBuffer.getInt();
        SendingTime = byteBuffer.getLong();
        MsgSeqNum = byteBuffer.getInt();

        return ReadError.NO_ERROR;
    }

    public void print() {
        Logger.logln("MessageType: " + MessageType);
        Logger.logln("Padding: " + Padding);
        Logger.logln("Account: " + Account);
        Logger.logln("Key: " + Key);
        Logger.logln("TradingSessionID: " + TradingSessionID);
        Logger.logln("SendingTime: " + SendingTime);
        Logger.logln("MsgSeqNum: " + MsgSeqNum);
    }
}
