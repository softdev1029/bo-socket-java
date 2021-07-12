package com.bo.socket.base;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

import com.bo.socket.base.*;
import com.bo.socket.auth.*;

import com.bo.socket.base.Message;

public class SocketController {
    private Socket socket = null;
    DataInputStream in = null;
    DataOutputStream out = null;

    public void create(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            System.out.println("Connected to server ...");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Message msg) {
        msg.send(out);
    }
}
