package com.bit.socket.base;

import java.nio.*;

public class BasePrice {
    public double BuyPrice = 0;
    public double BuyVolume = 0;
    public double SellPrice = 0;
    public double SellVolume = 0;

    public void put(ByteBuffer byteBuffer) {
        byteBuffer.putDouble(BuyPrice).putDouble(BuyVolume).putDouble(SellPrice).putDouble(SellVolume);
    }

    public void get(ByteBuffer byteBuffer) {
        BuyPrice = byteBuffer.getDouble();
        BuyVolume = byteBuffer.getDouble();
        SellPrice = byteBuffer.getDouble();
        SellVolume = byteBuffer.getDouble();
    }

    public void logln() {
        Logger.logln("BuyPrice: " + BuyPrice);
        Logger.logln("BuyVolume: " + BuyVolume);
        Logger.logln("SellPrice: " + SellPrice);
        Logger.logln("SellVolume: " + SellVolume);
    }
}
