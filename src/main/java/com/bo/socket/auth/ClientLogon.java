package com.bo.socket.auth;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

import com.bo.socket.base.*;
import com.bo.socket.auth.*;

import com.bo.socket.base.Message;

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
            UserName = "BOU7".toCharArray();
            TradingSessionID = 506;
            PrimaryOrderEntryIP = "1".toCharArray();
            SecondaryOrderEntryIP = "1".toCharArray();
            PrimaryMarketDataIP = "1".toCharArray();
            SecondaryMarketDataIP = "1".toCharArray();
            SendingTime = "".toCharArray();
            MsgSeqNum = 1500201;
            Key = 432451;
            LoginStatus = 0;
            RejectReason = 0;
            RiskMaster = Character.MIN_VALUE;
            
            ByteBuffer buffer = ByteBuffer.allocate(MessageLen);
            buffer.put(Data1)
                .put(Data2)
                .putShort(MessageLen)
                .putShort(LogonType)
                .putInt(Account)
                .put(TwoFA).position(16)
                .put(toBytes(UserName)).position(22);

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

    public void read(DataInputStream in) {
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(MessageLen - 4);
            while (in.available() > 0) {
                byteBuffer.put((byte) in.read());
            }

            System.out.printf("Received %s message, %s bytes ...",
                MessageTypeStr,
                MessageLen);
            printBuffer(byteBuffer);
            System.out.println("");

            byteBuffer.position(0);
            
            LogonType = byteBuffer.getShort();
            Account = byteBuffer.getInt();
            byteBuffer.get(TwoFA);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        System.out.println("LogonType: " + LogonType);
        System.out.println("Account: " + Account);
        System.out.println("TwoFA: " + new String(TwoFA));
    }
}
