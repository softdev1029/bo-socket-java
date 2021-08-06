package com.bo.socket.base;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.function.Function;

import com.bo.socket.base.*;
import com.bo.socket.auth.*;
import com.bo.socket.constant.*;

import com.bo.socket.base.Message;

public class ServerController extends SocketController {
    private ServerSocket server = null;
    
    public void createServer(int port, Function<Message, String> callback) {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server Started. Waiting for connection ...");
            Socket socket = server.accept();
            System.out.println("Got connection from client.");
            //Get input stream from socket variable and convert the same to DataInputStream
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
            recvCallback = callback;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
