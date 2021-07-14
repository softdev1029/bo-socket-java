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

public class ClientLogon extends Message {
    public ClientLogon() {
        MessageTypeStr = "ClientLogon";
        MessageLen = 143;
    }

    public void send(DataOutputStream out) {
        try {
            Data1 = 'H';
            Data2 = 0;
            LogonType = 1;
            Account = 100700;
            TwoFA = "1F6A".getBytes();
            UserName = "BOU7".getBytes();
            TradingSessionID = 506;
            PrimaryOrderEntryIP = "1".getBytes();
            SecondaryOrderEntryIP = "1".getBytes();
            PrimaryMarketDataIP = "1".getBytes();
            SecondaryMarketDataIP = "1".getBytes();
            SendingTime = "".getBytes();
            MsgSeqNum = 1500201;
            Key = 432451;
            LoginStatus = 0;
            RejectReason = 0;
            RiskMaster = 0;
            
            ByteBuffer buffer = ByteBuffer.allocate(MessageLen);
            buffer.put(Data1)
                .put(Data2)
                .putShort(MessageLen)
                .putShort(LogonType)
                .putInt(Account)
                .put(TwoFA).position(16)
                .put(UserName).position(22)
                .putInt(TradingSessionID)
                .put(PrimaryOrderEntryIP).position(50)
                .put(SecondaryOrderEntryIP).position(74)
                .put(PrimaryMarketDataIP).position(98)
                .put(SecondaryMarketDataIP).position(122)
                .put(SendingTime).position(130)
                .putInt(MsgSeqNum)
                .putInt(Key)
                .putShort(LoginStatus)
                .putShort(RejectReason)
                .put(RiskMaster);

            buffer.position(0);
            WritableByteChannel channel = Channels.newChannel(out);
            channel.write(buffer);

            System.out.printf("Sending %s message, %s bytes ...",
                MessageTypeStr,
                MessageLen);
            printBuffer(buffer);
            System.out.println("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int read(DataInputStream in) {
        int error = super.read(in);
        if (error != ReadError.NO_ERROR) {
            return error;
        }

        LogonType = byteBuffer.getShort();
        Account = byteBuffer.getInt();
        byteBuffer.get(TwoFA);
        byteBuffer.get(UserName);
        TradingSessionID = byteBuffer.getInt();
        byteBuffer.get(PrimaryOrderEntryIP);
        byteBuffer.get(SecondaryOrderEntryIP);
        byteBuffer.get(PrimaryMarketDataIP);
        byteBuffer.get(SecondaryMarketDataIP);
        byteBuffer.get(SendingTime);
        MsgSeqNum = byteBuffer.getInt();
        Key = byteBuffer.getInt();
        LoginStatus = byteBuffer.getShort();
        RejectReason = byteBuffer.getShort();
        RiskMaster = byteBuffer.get();

        return ReadError.NO_ERROR;
    }

    public void print() {
        System.out.println("LogonType: " + LogonType);
        System.out.println("Account: " + Account);
        System.out.println("TwoFA: " + new String(TwoFA));
        System.out.println("UserName: " + new String(UserName));
        System.out.println("TradingSessionID: " + TradingSessionID);
        System.out.println("PrimaryOrderEntryIP: " + new String(PrimaryOrderEntryIP));
        System.out.println("SecondaryOrderEntryIP: " + new String(SecondaryOrderEntryIP));
        System.out.println("PrimaryMarketDataIP: " + new String(PrimaryMarketDataIP));
        System.out.println("SecondaryMarketDataIP: " + new String(SecondaryMarketDataIP));
        System.out.println("SendingTime: " + new String(SendingTime));
        System.out.println("MsgSeqNum: " + MsgSeqNum);
        System.out.println("Key: " + Key);
        System.out.println("LoginStatus: " + LoginStatus);
        System.out.println("RejectReason: " + RejectReason);
        System.out.println("RiskMaster: " + RiskMaster);
    }
}
