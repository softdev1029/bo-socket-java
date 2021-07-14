package com.bo.socket.base;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

import com.bo.socket.base.*;
import com.bo.socket.auth.*;
import com.bo.socket.constant.*;

import com.bo.socket.base.Message;

public class ServerController {
    private ServerSocket server = null;
    DataInputStream in = null;
    DataOutputStream out = null;

    public void create(int port) {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server Started. Waiting for connection ...");
            Socket socket = server.accept();
            System.out.println("Got connection from client.");
            //Get input stream from socket variable and convert the same to DataInputStream
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read() {
        try {
            // Read type and length of data
            byte dataType = in.readByte();
            byte dataType2 = in.readByte();
            int length = in.readShort();
            System.out.println("Type: " + dataType);
            System.out.println("Length:" + length);

            if (dataType == MessageSymbol.CLIENT_LOGON) {
                ClientLogon msg = new ClientLogon();
                msg.read(in);
                msg.print();
            }
               
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(Message msg) {
        msg.send(out);
    }
}
