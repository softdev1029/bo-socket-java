package com.bit.socket.base;

import java.nio.*;

public class AtomPrice {
    public double Price = 0;
    public double Volume = 0;
    public byte Side = 0;

    public void put(ByteBuffer byteBuffer) {
        byteBuffer.putDouble(Price).putDouble(Volume).put(Side);
    }

    public void get(ByteBuffer byteBuffer) {
        Price = byteBuffer.getDouble();
        Volume = byteBuffer.getDouble();
        Side = byteBuffer.get();
    }

    public void logln() {
        Logger.logln("Price: " + Price);
        Logger.logln("Volume: " + Volume);
        Logger.logln("Side: " + Side);
    }
}
