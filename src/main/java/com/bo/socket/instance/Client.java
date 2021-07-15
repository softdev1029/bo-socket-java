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

public class Client {
    
    public void runClient(String ip, int port) {
        SocketController sc = new SocketController();
        sc.create(ip, port);

        while (true) {
            while (sc.isReadable()) {
                sc.read();
            }
            
            Scanner scanner = new Scanner(System.in);
            String msgTypeKey = "";
            while (!CreateMessage.isValidMessageType(msgTypeKey)) {
                CreateMessage.printAllRequestMessageTypes();

                System.out.println("Enter a valid message type: ");
        
                msgTypeKey = scanner.nextLine();
            }
            
            if (msgTypeKey.equals("0")) {
                // receive from server
            } else {
                System.out.println("Message type is: " + CreateMessage.REQUEST_MESSAGE_TYPES.get(msgTypeKey));

                String msgCount = null;
                while (!Util.isNumeric(msgCount)) {
                    System.out.println("Enter a valid count: ");
            
                    msgCount = scanner.nextLine();
                }

                System.out.println("Message count is: " + msgCount);

                Message msg = new ClientLogon();
                if (msgTypeKey.equals("H")) {
                    msg = new ClientLogon();
                } else if (msgTypeKey.equals("T")) {
                    msg = new NewLimitOrder();
                }
                for (int i = 0; i < Integer.valueOf(msgCount); i++) {
                    sc.send(msg);
                }
            }
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