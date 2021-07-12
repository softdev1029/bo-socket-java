package com.bo.socket.auth;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

import com.bo.socket.base.*;
import com.bo.socket.auth.*;

import com.bo.socket.base.Message;

public class ClientLogon extends Message {
    public void send(DataOutputStream out) {
        try {
            Data1 = 'H';
            Data2 = Character.MIN_VALUE;
            MessageLen = 143;
            LogonType = 1;
            Account = 100700;
            TwoFA = "1F6A".toCharArray();
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
            
            out.writeChar(Data1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
