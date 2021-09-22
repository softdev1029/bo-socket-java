package com.bit.socket.base;

import java.nio.*;

public class Subscribe {
    public short SymbolEnum = 0;
    public byte[] Symbol = new byte[16];
    public short SymbolType = 0;
    public short Layers = 0;
    public byte Subscribe = 0;

    public void put(ByteBuffer byteBuffer, int pos) {
        byteBuffer.putShort(SymbolEnum).put(Symbol).position(pos + 2 + 16).putShort(SymbolType).putShort(Layers)
                .put(Subscribe);
    }

    public void get(ByteBuffer byteBuffer) {
        SymbolEnum = byteBuffer.getShort();
        byteBuffer.get(Symbol);
        SymbolType = byteBuffer.getShort();
        Layers = byteBuffer.getShort();
        Subscribe = byteBuffer.get();
    }

    public void logln() {
        Logger.logln("SymbolEnum: " + SymbolEnum);
        Logger.logln("Symbol: " + new String(Symbol));
        Logger.logln("SymbolType: " + SymbolType);
        Logger.logln("Layers: " + Layers);
        Logger.logln("Subscribe: " + Subscribe);
    }
}
