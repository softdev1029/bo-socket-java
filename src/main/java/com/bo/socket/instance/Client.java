package com.bo.socket.instance;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.*;

import com.bo.socket.base.*;
import com.bo.socket.auth.*;
import com.bo.socket.transaction.*;
import com.bo.socket.constant.*;
import com.bo.socket.util.*;

public class Client extends Instance {
    
    public String recvCallback(Message msg) {
        return "";
    }

    public void runClient(String ip, int port) {
        sc = new SocketController();
        sc.create(ip, port, this::recvCallback);

        super.runInstance();
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            Logger.logf("usage: <host> <port>\n");
            return;
        }

        Client client = new Client();

        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        client.runClient(ip, port);
    }

}