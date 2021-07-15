package com.bo.socket.transaction;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

import com.bo.socket.base.*;
import com.bo.socket.auth.*;
import com.bo.socket.constant.*;

public class NewLimitOrder extends Message {
    public NewLimitOrder() {
        MessageTypeStr = "NewLimitOrder";
        MessageLen = 238;
    }

    public void createExampleMessage() {
        Data1 = 'T';
        Data2 = 0;
        MessageType = 1; // ORDER_NEW
        Padding = 0;
        Account = 100700;
        OrderID = 46832151;
        SymbolEnum = 1;
        OrderType = 1; // LMT
        SymbolType = 1; // SPOT
        BOPrice = 50100.5;
        BOSide = 3; // BUY
        BOOrderQty = 2.0;
        TIF = 2; // GTC
        StopLimitPrice = 0;
        BOSymbol = "BTCUSD".getBytes();
        OrigOrderID = 0;
        BOCancelShares = 0;
        ExecID = 0;
        ExecShares = 0;
        RemainingQuantity = 0;
        ExecFee = 0;
        ExpirationDate = "".getBytes();
        TraderID = 0;
        RejectReason = 1;
        SendingTime = "ABCD1234".getBytes();
        TradingSessionID = 506;
        Key = 42341;
        DisplaySize = 0;
        RefreshSize = 0;
        Layers = 0;
        SizeIncrement = 0;
        PriceIncrement = 0;
        PriceOffset = 0;
        BOOrigPrice = 0;
        ExecPrice = 0;
        MsgSeqNum = 79488880;
        TakeProfitPrice = 0;
        TriggerType = 0;
        Attributes = "".getBytes();
    }

    public void makeByteBuffer() {
        byteBuffer = ByteBuffer.allocate(MessageLen);
        byteBuffer.put(Data1)
            .put(Data2)
            .putShort(MessageLen)
            .putShort(MessageType)
            .putShort(Padding)
            .putInt(Account)
            .putLong(OrderID)
            .putShort(SymbolEnum)
            .putShort(OrderType)
            .putShort(SymbolType)
            .putDouble(BOPrice)
            .putShort(BOSide)
            .putDouble(BOOrderQty)
            .putShort(TIF)
            .putDouble(StopLimitPrice)
            .put(BOSymbol).position(66)
            .putLong(OrigOrderID)
            .putDouble(BOCancelShares)
            .putLong(ExecID)
            .putDouble(ExecShares)
            .putDouble(RemainingQuantity)
            .putDouble(ExecFee)
            .put(ExpirationDate).position(126)
            .putInt(TraderID)
            .putShort(RejectReason)
            .put(SendingTime).position(142)
            .putInt(TradingSessionID)
            .putInt(Key)
            .putDouble(DisplaySize)
            .putDouble(RefreshSize)
            .putShort(Layers)
            .putDouble(SizeIncrement)
            .putDouble(PriceIncrement)
            .putDouble(PriceOffset)
            .putDouble(BOOrigPrice)
            .putDouble(ExecPrice)
            .putInt(MsgSeqNum)
            .putDouble(TakeProfitPrice)
            .putShort(TriggerType)
            .put(Attributes);
    }

    public int read(DataInputStream in) {
        int error = super.read(in);
        if (error != ReadError.NO_ERROR) {
            return error;
        }

        MessageType = byteBuffer.getShort();
        Padding = byteBuffer.getShort();
        Account = byteBuffer.getInt();
        OrderID = byteBuffer.getLong();
        SymbolEnum = byteBuffer.getShort();
        OrderType = byteBuffer.getShort();
        SymbolType = byteBuffer.getShort();
        BOPrice = byteBuffer.getLong();
        BOSide = byteBuffer.getShort();
        BOOrderQty = byteBuffer.getLong();
        TIF = byteBuffer.getShort();
        StopLimitPrice = byteBuffer.getLong();
        byteBuffer.get(BOSymbol);
        OrigOrderID = byteBuffer.getLong();
        BOCancelShares = byteBuffer.getLong();
        ExecID = byteBuffer.getLong();
        ExecShares = byteBuffer.getLong();
        RemainingQuantity = byteBuffer.getLong();
        ExecFee = byteBuffer.getLong();
        byteBuffer.get(ExpirationDate);
        TraderID = byteBuffer.getInt();
        RejectReason = byteBuffer.getShort();
        byteBuffer.get(SendingTime);
        TradingSessionID = byteBuffer.getInt();
        Key = byteBuffer.getInt();
        DisplaySize = byteBuffer.getLong();
        RefreshSize = byteBuffer.getLong();
        Layers = byteBuffer.getShort();
        SizeIncrement = byteBuffer.getLong();
        PriceIncrement = byteBuffer.getLong();
        PriceOffset = byteBuffer.getLong();
        BOOrigPrice = byteBuffer.getLong();
        ExecPrice = byteBuffer.getLong();
        MsgSeqNum = byteBuffer.getInt();
        TakeProfitPrice = byteBuffer.getLong();
        TriggerType = byteBuffer.getShort();
        byteBuffer.get(Attributes);

        return ReadError.NO_ERROR;
    }

    public void print() {
        System.out.println("MessageType: " + MessageType);
        System.out.println("Padding: " + Padding);
        System.out.println("Account: " + Account);
        System.out.println("OrderID: " + OrderID);
        System.out.println("SymbolEnum: " + SymbolEnum);
        System.out.println("OrderType: " + OrderType);
        System.out.println("SymbolType: " + SymbolType);
        System.out.println("BOPrice: " + BOPrice);
        System.out.println("BOSide: " + BOSide);
        System.out.println("BOOrderQty: " + BOOrderQty);
        System.out.println("TIF: " + TIF);
        System.out.println("StopLimitPrice: " + StopLimitPrice);
        System.out.println("BOSymbol: " + new String(BOSymbol));
        System.out.println("OrigOrderID: " + OrigOrderID);
        System.out.println("BOCancelShares: " + BOCancelShares);
        System.out.println("ExecID: " + ExecID);
        System.out.println("ExecShares: " + ExecShares);
        System.out.println("RemainingQuantity: " + RemainingQuantity);
        System.out.println("ExecFee: " + ExecFee);
        System.out.println("ExpirationDate: " + new String(ExpirationDate));
        System.out.println("TraderID: " + TraderID);
        System.out.println("RejectReason: " + RejectReason);
        System.out.println("SendingTime: " + new String(SendingTime));
        System.out.println("TradingSessionID: " + TradingSessionID);
        System.out.println("Key: " + Key);
        System.out.println("DisplaySize: " + DisplaySize);
        System.out.println("RefreshSize: " + RefreshSize);
        System.out.println("Layers: " + Layers);
        System.out.println("SizeIncrement: " + SizeIncrement);
        System.out.println("PriceIncrement: " + PriceIncrement);
        System.out.println("PriceOffset: " + PriceOffset);
        System.out.println("BOOrigPrice: " + BOOrigPrice);
        System.out.println("ExecPrice: " + ExecPrice);
        System.out.println("MsgSeqNum: " + MsgSeqNum);
        System.out.println("TakeProfitPrice: " + TakeProfitPrice);
        System.out.println("TriggerType: " + TriggerType);
        System.out.println("Attributes: " + new String(Attributes));
    }
}
