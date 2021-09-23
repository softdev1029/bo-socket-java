package com.bit.socket.auth;

import java.io.*;
import com.bit.socket.base.*;
import com.bit.socket.constant.*;

public class ClientLogon extends Message {
    protected String apiKey = null;

    public ClientLogon(String apiKey) {
        this.apiKey = apiKey != null ? apiKey : "1234";
        MessageTypeStr = "ClientLogon";
        MessageLen = 143;
    }

    public void createExampleMessage() {
        String twoFa = Oauth.getTOTPCode(apiKey);

        Data1 = 'H';
        Data2 = 0;
        LogonType = 1;
        Account = 100700;
        TwoFA = twoFa.getBytes();
        UserName = "BOU7".getBytes();
        TradingSessionID = 506;
        PrimaryOrderEntryIP = "1".getBytes();
        SecondaryOrderEntryIP = "1".getBytes();
        PrimaryMarketDataIP = "1".getBytes();
        SecondaryMarketDataIP = "1".getBytes();
        SendingTime = 0;
        MsgSeqNum = 1500201;
        Key = 432451;
        LoginStatus = 0;
        RejectReason = 0;
        RiskMaster = 0;
    }

    public void makeByteBuffer() {
        super.makeByteBuffer();

        byteBuffer.put(Data1).put(Data2).putShort(MessageLen).putShort(LogonType).putInt(Account).put(TwoFA)
                .position(16).put(UserName).position(22).putInt(TradingSessionID).put(PrimaryOrderEntryIP).position(50)
                .put(SecondaryOrderEntryIP).position(74).put(PrimaryMarketDataIP).position(98)
                .put(SecondaryMarketDataIP).position(122).putLong(SendingTime).putInt(MsgSeqNum).putInt(Key)
                .putShort(LoginStatus).putShort(RejectReason).put(RiskMaster);
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
        SendingTime = byteBuffer.getLong();
        MsgSeqNum = byteBuffer.getInt();
        Key = byteBuffer.getInt();
        LoginStatus = byteBuffer.getShort();
        RejectReason = byteBuffer.getShort();
        RiskMaster = byteBuffer.get();

        return ReadError.NO_ERROR;
    }

    public void print() {
        Logger.logln("LogonType: " + LogonType);
        Logger.logln("Account: " + Account);
        Logger.logln("TwoFA: " + new String(TwoFA));
        Logger.logln("UserName: " + new String(UserName));
        Logger.logln("TradingSessionID: " + TradingSessionID);
        Logger.logln("PrimaryOrderEntryIP: " + new String(PrimaryOrderEntryIP));
        Logger.logln("SecondaryOrderEntryIP: " + new String(SecondaryOrderEntryIP));
        Logger.logln("PrimaryMarketDataIP: " + new String(PrimaryMarketDataIP));
        Logger.logln("SecondaryMarketDataIP: " + new String(SecondaryMarketDataIP));
        Logger.logln("SendingTime: " + SendingTime);
        Logger.logln("MsgSeqNum: " + MsgSeqNum);
        Logger.logln("Key: " + Key);
        Logger.logln("LoginStatus: " + LoginStatus);
        Logger.logln("RejectReason: " + RejectReason);
        Logger.logln("RiskMaster: " + RiskMaster);
    }
}
