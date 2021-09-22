package com.bit.socket.base;

import java.util.function.Function;

public class SocketThread extends Thread {
    protected SocketController sc = null;
    protected Function<SocketController, String> sendCallback = null;

    // Constructor
    public SocketThread(SocketController sc, Function<SocketController, String> sendCallback) {
        this.sc = sc;
        this.sendCallback = sendCallback;
    }

    @Override
    public void run() {
        while (true) {
            while (sc.isReadable()) {
                sc.read();
            }

            sendCallback.apply(sc);
        }
    }
}
