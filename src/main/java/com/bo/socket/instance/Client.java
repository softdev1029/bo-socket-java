package com.bo.socket.read;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*; 

public class Client {
    
    public void runClient(String ip, int port) {
        try {
            Socket socket = new Socket(ip, port);
            System.out.println("Connected to server ...");
            DataInputStream in = new DataInputStream(System.in);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            
            char type = 's'; // s for string
            int length = 29;
            String data = "This is a string of length 29";
            byte[] dataInBytes = data.getBytes(StandardCharsets.UTF_8);         
            //Sending data in TLV format        
            out.writeChar(type);
            out.writeInt(length);
            out.write(dataInBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
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