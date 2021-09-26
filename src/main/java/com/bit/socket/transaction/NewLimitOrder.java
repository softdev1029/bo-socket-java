package com.bit.socket.transaction;

import java.io.DataInputStream;

import com.bit.socket.base.Logger;
import com.bit.socket.base.Message;
import com.bit.socket.constant.ReadError;

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
        SendingTime = 1000;
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
        SecondLegPrice = 111;
        RouteEnum = 1;
        ModifyType = 1;
        Attributes = "".getBytes();
    }

    public void makeByteBuffer() {
        super.makeByteBuffer();

        byteBuffer.put(Data1).put(Data2).putShort(MessageLen).putShort(MessageType).putShort(Padding).putInt(Account)
                .putLong(OrderID).putShort(SymbolEnum).putShort(OrderType).putShort(SymbolType).putDouble(BOPrice)
                .putShort(BOSide).putDouble(BOOrderQty).putShort(TIF).putDouble(StopLimitPrice).put(BOSymbol)
                .position(66).putLong(OrigOrderID).putDouble(BOCancelShares).putLong(ExecID).putDouble(ExecShares)
                .putDouble(RemainingQuantity).putDouble(ExecFee).put(ExpirationDate).position(126).putInt(TraderID)
                .putShort(RejectReason).putLong(SendingTime).putInt(TradingSessionID).putInt(Key).putDouble(DisplaySize)
                .putDouble(RefreshSize).putShort(Layers).putDouble(SizeIncrement).putDouble(PriceIncrement)
                .putDouble(PriceOffset).putDouble(BOOrigPrice).putDouble(ExecPrice).putInt(MsgSeqNum)
                .putDouble(TakeProfitPrice).putShort(TriggerType).putDouble(SecondLegPrice).putShort(RouteEnum)
                .putShort(ModifyType).put(Attributes);
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
        BOPrice = byteBuffer.getDouble();
        BOSide = byteBuffer.getShort();
        BOOrderQty = byteBuffer.getDouble();
        TIF = byteBuffer.getShort();
        StopLimitPrice = byteBuffer.getLong();
        byteBuffer.get(BOSymbol);
        OrigOrderID = byteBuffer.getLong();
        BOCancelShares = byteBuffer.getDouble();
        ExecID = byteBuffer.getLong();
        ExecShares = byteBuffer.getDouble();
        RemainingQuantity = byteBuffer.getDouble();
        ExecFee = byteBuffer.getDouble();
        byteBuffer.get(ExpirationDate);
        TraderID = byteBuffer.getInt();
        RejectReason = byteBuffer.getShort();
        SendingTime = byteBuffer.getLong();
        TradingSessionID = byteBuffer.getInt();
        Key = byteBuffer.getInt();
        DisplaySize = byteBuffer.getDouble();
        RefreshSize = byteBuffer.getDouble();
        Layers = byteBuffer.getShort();
        SizeIncrement = byteBuffer.getDouble();
        PriceIncrement = byteBuffer.getDouble();
        PriceOffset = byteBuffer.getDouble();
        BOOrigPrice = byteBuffer.getDouble();
        ExecPrice = byteBuffer.getDouble();
        MsgSeqNum = byteBuffer.getInt();
        TakeProfitPrice = byteBuffer.getDouble();
        TriggerType = byteBuffer.getShort();
        SecondLegPrice = byteBuffer.getDouble();
        RouteEnum = byteBuffer.getShort();
        ModifyType = byteBuffer.getShort();
        byteBuffer.get(Attributes);

        return ReadError.NO_ERROR;
    }

    public void print() {
        Logger.logln("MessageType: " + MessageType);
        Logger.logln("Padding: " + Padding);
        Logger.logln("Account: " + Account);
        Logger.logln("OrderID: " + OrderID);
        Logger.logln("SymbolEnum: " + SymbolEnum);
        Logger.logln("OrderType: " + OrderType);
        Logger.logln("SymbolType: " + SymbolType);
        Logger.logln("BOPrice: " + BOPrice);
        Logger.logln("BOSide: " + BOSide);
        Logger.logln("BOOrderQty: " + BOOrderQty);
        Logger.logln("TIF: " + TIF);
        Logger.logln("StopLimitPrice: " + StopLimitPrice);
        Logger.logln("BOSymbol: " + new String(BOSymbol));
        Logger.logln("OrigOrderID: " + OrigOrderID);
        Logger.logln("BOCancelShares: " + BOCancelShares);
        Logger.logln("ExecID: " + ExecID);
        Logger.logln("ExecShares: " + ExecShares);
        Logger.logln("RemainingQuantity: " + RemainingQuantity);
        Logger.logln("ExecFee: " + ExecFee);
        Logger.logln("ExpirationDate: " + new String(ExpirationDate));
        Logger.logln("TraderID: " + TraderID);
        Logger.logln("RejectReason: " + RejectReason);
        Logger.logln("SendingTime: " + SendingTime);
        Logger.logln("TradingSessionID: " + TradingSessionID);
        Logger.logln("Key: " + Key);
        Logger.logln("DisplaySize: " + DisplaySize);
        Logger.logln("RefreshSize: " + RefreshSize);
        Logger.logln("Layers: " + Layers);
        Logger.logln("SizeIncrement: " + SizeIncrement);
        Logger.logln("PriceIncrement: " + PriceIncrement);
        Logger.logln("PriceOffset: " + PriceOffset);
        Logger.logln("BOOrigPrice: " + BOOrigPrice);
        Logger.logln("ExecPrice: " + ExecPrice);
        Logger.logln("MsgSeqNum: " + MsgSeqNum);
        Logger.logln("TakeProfitPrice: " + TakeProfitPrice);
        Logger.logln("TriggerType: " + TriggerType);
        Logger.logln("TriggerType: " + SecondLegPrice);
        Logger.logln("TriggerType: " + RouteEnum);
        Logger.logln("TriggerType: " + ModifyType);
        Logger.logln("Attributes: " + new String(Attributes));
    }
}
