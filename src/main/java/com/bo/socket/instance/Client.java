package com.bo.socket.instance;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

import com.bo.socket.base.*;
import com.bo.socket.auth.*;

public class Client {
    
    public void runClient(String ip, int port) {
        SocketController sc = new SocketController();
        sc.create(ip, port);

        ClientLogon msg = new ClientLogon();
        sc.send(msg);
        sc.send(msg);
        sc.send(msg);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.printf("usage: <host> <port>\n");
            return;
        }

        Client client = new Client();

        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        client.runClient(ip, port);
    }

}