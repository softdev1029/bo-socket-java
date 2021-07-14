package com.bo.socket.instance;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*; 

import com.bo.socket.base.*;
import com.bo.socket.auth.*;

public class Server {
    
    public void runServer(int port) {
        ServerController sc = new ServerController();
        sc.create(port);
        while (true) {
            while (sc.isReadable()) {
                System.out.println("1111===");
                sc.read();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.runServer(4444);
    }

}