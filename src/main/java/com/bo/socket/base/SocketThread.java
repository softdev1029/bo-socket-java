package com.bo.socket.base;

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
import java.util.function.Function;

import com.bo.socket.base.*;

public class SocketThread extends Thread {
    protected SocketController sc = null;
    protected Function<SocketController, String> sendCallback = null;

    // Constructor
    public SocketThread(SocketController sc, Function<SocketController, String> sendCallback)
    {
        this.sc = sc;
        this.sendCallback = sendCallback;
    }

    @Override
    public void run() 
    {
        while (true) {
            while (sc.isReadable()) {
                sc.read();
            }

            sendCallback.apply(sc);
        }
    }
}
