package com.bit.socket.base;

import java.net.*;
import java.io.*;
import java.util.function.Function;

public class ServerController extends SocketController {
    private ServerSocket server = null;

    public void createServer(int port, Function<Message, String> callback) {
        try {
            server = new ServerSocket(port);
            Logger.logln("Server Started. Waiting for connection ...");
            Socket socket = server.accept();
            Logger.logln("Got connection from client.");
            // Get input stream from socket variable and convert the same to DataInputStream
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
            recvCallback = callback;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
