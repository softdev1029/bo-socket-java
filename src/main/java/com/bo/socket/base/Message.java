package com.bo.socket.base;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.util.*;

abstract public class Message {
    protected String MessageTypeStr = "";

    public byte Data1 = 0;
    public byte Data2 = 0;
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
    public byte[] TwoFA = new byte[6];
    public char[] UserName = new char[6];
    public char[] PrimaryOrderEntryIP = new char[24];
    public char[] SecondaryOrderEntryIP = new char[24];
    public char[] PrimaryMarketDataIP = new char[24];
    public char[] SecondaryMarketDataIP = new char[24];
    public int LastSeqNum = 0;
    public short LoginStatus = 0;
    public char RiskMaster = 0;

    abstract public void send(DataOutputStream out);
    abstract public void read(DataInputStream in);
    abstract public void print();

    protected byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                  byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }

    protected char[] getChars(ByteBuffer buffer, int len) {
        byte[] bytes = new byte[len];
        buffer.get(bytes);
        String str = new String(bytes);
        return str.toCharArray();
    }

    protected void printBytes(byte[] bytes) {
        for (byte b : bytes) {
            String st = String.format("%02X", b);
            System.out.print(st);
        }
    }

    protected void printBuffer(ByteBuffer buffer) {
        buffer.position(0);
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        printBytes(bytes);
    }
}
