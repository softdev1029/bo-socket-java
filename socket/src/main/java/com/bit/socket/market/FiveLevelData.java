package com.bit.socket.market;

import java.io.*;

import com.bit.socket.base.*;
import com.bit.socket.constant.*;

public class FiveLevelData extends Message {
    public FiveLevelData() {
        MessageTypeStr = "FiveLevelData";
        MessageLen = 236;
    }

    public void createExampleMessage() {
        Data1 = 'm';
        Data2 = 0;
        MessageType = 1; // BUY
        Padding = 0;
        SymbolEnum = 4;
        SymbolType = 1; // SPOT
        MarketDataSymbolName = "BTCUSDT".getBytes();
        LastTradePrice = 60000.5;
        Last24Vol = 60000.5;
        High = 60000.5;
        Low = 60000.5;

        // level1
        Lvl1Price.BuyPrice = 60000.5;
        Lvl1Price.BuyVolume = 60000.5;
        Lvl1Price.NumBuyOrders = 5;
        Lvl1Price.SellPrice = 60000.5;
        Lvl1Price.SellVolume = 60000.5;
        Lvl1Price.NumSellOrders = 5;

        // level2
        Lvl2Price.BuyPrice = 60000.5;
        Lvl2Price.BuyVolume = 60000.5;
        Lvl2Price.NumBuyOrders = 5;
        Lvl2Price.SellPrice = 60000.5;
        Lvl2Price.SellVolume = 60000.5;
        Lvl2Price.NumSellOrders = 5;

        // level3
        Lvl3Price.BuyPrice = 60000.5;
        Lvl3Price.BuyVolume = 60000.5;
        Lvl3Price.NumBuyOrders = 5;
        Lvl3Price.SellPrice = 60000.5;
        Lvl3Price.SellVolume = 60000.5;
        Lvl3Price.NumSellOrders = 5;

        // level4
        Lvl4Price.BuyPrice = 60000.5;
        Lvl4Price.BuyVolume = 60000.5;
        Lvl4Price.NumBuyOrders = 5;
        Lvl4Price.SellPrice = 60000.5;
        Lvl4Price.SellVolume = 60000.5;
        Lvl4Price.NumSellOrders = 5;

        // level5
        Lvl5Price.BuyPrice = 60000.5;
        Lvl5Price.BuyVolume = 60000.5;
        Lvl5Price.NumBuyOrders = 5;
        Lvl5Price.SellPrice = 60000.5;
        Lvl5Price.SellVolume = 60000.5;
        Lvl5Price.NumSellOrders = 5;
    }

    public void makeByteBuffer() {
        super.makeByteBuffer();

        byteBuffer.put(Data1).put(Data2).putShort(MessageLen).putShort(MessageType).putShort(Padding)
                .putShort(SymbolEnum).putShort(SymbolType).put(MarketDataSymbolName).position(24)
                .putDouble(LastTradePrice).putDouble(Last24Vol).putDouble(High).putDouble(Low);
        Lvl1Price.put(byteBuffer);
        Lvl2Price.put(byteBuffer);
        Lvl3Price.put(byteBuffer);
        Lvl4Price.put(byteBuffer);
        Lvl5Price.put(byteBuffer);
    }

    public int read(DataInputStream in) {
        int error = super.read(in);
        if (error != ReadError.NO_ERROR) {
            return error;
        }

        MessageType = byteBuffer.getShort();
        Padding = byteBuffer.getShort();
        SymbolEnum = byteBuffer.getShort();
        byteBuffer.get(MarketDataSymbolName);
        LastTradePrice = byteBuffer.getDouble();
        Last24Vol = byteBuffer.getDouble();
        High = byteBuffer.getDouble();
        Low = byteBuffer.getDouble();
        Lvl1Price.get(byteBuffer);
        Lvl2Price.get(byteBuffer);
        Lvl3Price.get(byteBuffer);
        Lvl4Price.get(byteBuffer);
        Lvl5Price.get(byteBuffer);

        return ReadError.NO_ERROR;
    }

    public void print() {
        Logger.logln("MessageType: " + MessageType);
        Logger.logln("Padding: " + Padding);
        Logger.logln("SymbolEnum: " + SymbolEnum);
        Logger.logln("SymbolType: " + SymbolType);
        Logger.logln("SymbolName: " + new String(SymbolName));
        Logger.logln("LastTradePrice: " + LastTradePrice);
        Logger.logln("Last24Vol: " + Last24Vol);
        Logger.logln("High: " + High);
        Logger.logln("Low: " + Low);
        Lvl1Price.logln();
        Lvl2Price.logln();
        Lvl3Price.logln();
        Lvl4Price.logln();
        Lvl5Price.logln();
    }
}
