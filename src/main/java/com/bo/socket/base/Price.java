package com.bo.socket.base;

import com.bo.socket.base.*;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class Price {
    public double BuyPrice = 0;
    public double BuyVolume = 0;
    public short NumBuyOrders = 0;
    public double SellPrice = 0;
    public double SellVolume = 0;
    public short NumSellOrders = 0;

    public void put(ByteBuffer byteBuffer) {
        byteBuffer
            .putDouble(BuyPrice)
            .putDouble(BuyVolume)
            .putShort(NumBuyOrders)
            .putDouble(SellPrice)
            .putDouble(SellVolume)
            .putShort(NumSellOrders);
    }

    public void get(ByteBuffer byteBuffer) {
        BuyPrice = byteBuffer.getDouble();
        BuyVolume = byteBuffer.getDouble();
        NumBuyOrders = byteBuffer.getShort();
        SellPrice = byteBuffer.getDouble();
        SellVolume = byteBuffer.getDouble();
        NumSellOrders = byteBuffer.getShort();
    }

    public void logln() {
        Logger.logln("BuyPrice: " + BuyPrice);
        Logger.logln("BuyVolume: " + BuyVolume);
        Logger.logln("NumBuyOrders: " + NumBuyOrders);
        Logger.logln("SellPrice: " + SellPrice);
        Logger.logln("SellVolume: " + SellVolume);
        Logger.logln("NumSellOrders: " + NumSellOrders);
    }
}
