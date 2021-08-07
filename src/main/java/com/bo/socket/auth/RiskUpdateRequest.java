package com.bo.socket.auth;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

import com.bo.socket.base.*;
import com.bo.socket.auth.*;
import com.bo.socket.constant.*;

public class RiskUpdateRequest extends Message {
    public RiskUpdateRequest() {
        MessageTypeStr = "RiskUpdateRequest";
        MessageLen = 34;
    }

    public void createExampleMessage() {
        Data1 = 'w';
        Data2 = 0;
        MessageType = 0;
        ResponseType = 2;
        Account = 10070;
        TradingSessionID = 506;
        SymbolEnum = 1;
        Key = 0;
        MsgSeqNum = 1005231;
        SendingTime = 0;
    }

    public void makeByteBuffer() {
        super.makeByteBuffer();
        
        byteBuffer.put(Data1)
            .put(Data2)
            .putShort(MessageLen)
            .putShort(MessageType)
            .putShort(ResponseType)
            .putInt(Account)
            .putInt(TradingSessionID)
            .putShort(SymbolEnum)
            .putInt(Key)
            .putInt(MsgSeqNum)
            .putLong(SendingTime);
    }

    public int read(DataInputStream in) {
        int error = super.read(in);
        if (error != ReadError.NO_ERROR) {
            return error;
        }

        MessageType = byteBuffer.getShort();
        ResponseType = byteBuffer.getShort();
        Account = byteBuffer.getInt();
        TradingSessionID = byteBuffer.getInt();
        SymbolEnum = byteBuffer.getShort();
        Key = byteBuffer.getInt();
        MsgSeqNum = byteBuffer.getInt();
        SendingTime = byteBuffer.getLong();

        return ReadError.NO_ERROR;
    }

    public void print() {
        Logger.logln("MessageType: " + MessageType);
        Logger.logln("ResponseType: " + ResponseType);
        Logger.logln("Account: " + Account);
        Logger.logln("TradingSessionID: " + TradingSessionID);
        Logger.logln("SymbolEnum: " + SymbolEnum);
        Logger.logln("Key: " + Key);
        Logger.logln("MsgSeqNum: " + MsgSeqNum);
        Logger.logln("SendingTime: " + SendingTime);
    }
}
