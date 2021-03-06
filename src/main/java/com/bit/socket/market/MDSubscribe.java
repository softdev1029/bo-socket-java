package com.bit.socket.market;

import java.io.DataInputStream;

import com.bit.socket.base.Logger;
import com.bit.socket.base.Message;
import com.bit.socket.base.Subscribe;
import com.bit.socket.constant.ReadError;

public class MDSubscribe extends Message {
    public MDSubscribe() {
        MessageTypeStr = "MDSubscribe";
        MessageLen = 147; // 32;

        for (int i = 0; i < 5; i++) {
            Subscribes[i] = new Subscribe();
        }
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
        // subscribes
    }

    public void makeByteBuffer() {
        super.makeByteBuffer();

        byteBuffer.put(Data1).put(Data2).putShort(MessageLen).putShort(MessageType).putShort(Padding).putInt(Account)
                .putInt(Key).putInt(TradingSessionID).putLong(SendingTime).putInt(MsgSeqNum);
        int pos = 28;
        for (int i = 0; i < 5; i++) {
            Subscribes[i].put(byteBuffer, pos);
            pos = pos + 23;
        }
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
        for (int i = 0; i < 5; i++) {
            Subscribes[i].get(byteBuffer);
        }

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
        for (int i = 0; i < 5; i++) {
            Subscribes[i].logln();
        }
    }
}
