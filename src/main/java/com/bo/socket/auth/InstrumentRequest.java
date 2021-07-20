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

public class InstrumentRequest extends Message {
    public InstrumentRequest() {
        MessageTypeStr = "InstrumentRequest";
        MessageLen = 64;
    }

    public void createExampleMessage() {
        Data1 = 'Y';
        Data2 = 0;
        MessageType = 0;
        RejectReason4Byte = 0;
        Account = 100700;
        RequestType = 2;
        Key = 0;
        SymbolName = "".getBytes();
        SymbolType = 0;
        SymbolEnum = 0;
        TradingSessionID = 506;
        SendingTime = 0;
        MsgSeqNum = 1500201;
    }

    public void makeByteBuffer() {
        byteBuffer = ByteBuffer.allocate(MessageLen);
        byteBuffer.put(Data1)
            .put(Data2)
            .putShort(MessageLen)
            .putShort(MessageType)
            .putInt(RejectReason4Byte)
            .putInt(Account)
            .putShort(RequestType)
            .putInt(Key)
            .put(SymbolName).position(44)
            .putShort(SymbolType)
            .putShort(SymbolEnum)
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
        RejectReason4Byte = byteBuffer.getInt();
        Account = byteBuffer.getInt();
        RequestType = byteBuffer.getShort();
        Key = byteBuffer.getInt();
        byteBuffer.get(SymbolName);
        SymbolType = byteBuffer.getShort();
        SymbolEnum = byteBuffer.getShort();
        TradingSessionID = byteBuffer.getInt();
        SendingTime = byteBuffer.getLong();
        MsgSeqNum = byteBuffer.getInt();

        return ReadError.NO_ERROR;
    }

    public void print() {
        System.out.println("MessageType: " + MessageType);
        System.out.println("RejectReason4Byte: " + RejectReason4Byte);
        System.out.println("Account: " + Account);
        System.out.println("RequestType: " + RequestType);
        System.out.println("Key: " + Key);
        System.out.println("SymbolName: " + new String(SymbolName));
        System.out.println("SymbolType: " + SymbolType);
        System.out.println("SymbolEnum: " + SymbolEnum);
        System.out.println("TradingSessionID: " + TradingSessionID);
        System.out.println("SendingTime: " + SendingTime);
        System.out.println("MsgSeqNum: " + MsgSeqNum);
    }
}
