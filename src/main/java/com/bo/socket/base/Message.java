package com.bo.socket.base;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class Message {
    public void send(DataOutputStream out) {

    }

    public char Data1 = Character.MIN_VALUE;
    public char Data2 = Character.MIN_VALUE;
    public short MessageLen = 0;
    public short MessageType = 0;
    public short Padding = 0;
    public int Account = 0;
    public long OrderID = 0;
    public short SymbolEnum = 0;
    public short OrderType = 0;
    public short SymbolType = 0;
    public double BOPrice = 0;
    public short BOSide = 0;
    public double BOOrderQty = 0;
    public short TIF = 0;
    public double StopLimitPrice = 0;
    public char[] BOSymbol = new char[12];
    public long OrigOrderID = 0;
    public double BOCancelShares = 0;
    public long ExecID = 0;
    public double ExecShares = 0;
    public double RemainingQuantity = 0;
    public double ExecFee = 0;
    public char[] ExpirationDate = new char[12];
    public int TraderID = 0;
    public short RejectReason = 0;
    public char[] SendingTime = new char[8];
    public int TradingSessionID = 0;
    public int Key = 0;
    public double DisplaySize = 0;
    public double RefreshSize = 0;
    public short Layers = 0;
    public double SizeIncrement = 0;
    public double PriceIncrement = 0;
    public double PriceOffset = 0;
    public double BOOrigPrice = 0;
    public double ExecPrice = 0;
    public long MsgSeqNum = 0;
    public double TakeProfitPrice = 0;
    public short TriggerType = 0;
    public char[] Attributes = new char[12];

    public short LogonType = 0;
    public char[] TwoFA = new char[6];
    public char[] UserName = new char[6];
    public char[] PrimaryOrderEntryIP = new char[24];
    public char[] SecondaryOrderEntryIP = new char[24];
    public char[] PrimaryMarketDataIP = new char[24];
    public char[] SecondaryMarketDataIP = new char[24];
    public int LastSeqNum = 0;
    public short LoginStatus = 0;
    public char RiskMaster = 0;
}
