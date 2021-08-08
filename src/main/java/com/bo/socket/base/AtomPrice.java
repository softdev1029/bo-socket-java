package com.bo.socket.base;

import com.bo.socket.base.*;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class AtomPrice {
    public double Price = 0;
    public double Volume = 0;
    public byte Side = 0;
    
    public void put(ByteBuffer byteBuffer) {
        byteBuffer
            .putDouble(Price)
            .putDouble(Volume)
            .put(Side);
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
