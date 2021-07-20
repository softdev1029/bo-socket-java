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

public class Server {
    
    public void runServer(int port) {
        ServerController sc = new ServerController();
        sc.create(port);
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
                // receive from client
            } else {
                System.out.println("Message type is: " + CreateMessage.REQUEST_MESSAGE_TYPES.get(msgTypeKey));

                String msgCount = "1";
                Message msg = new ClientLogon();
                if (msgTypeKey.equals("H")) {
                    msg = new ClientLogon();
                } else if (msgTypeKey.equals("T")) {
                    msg = new NewLimitOrder();
                } else if (msgTypeKey.equals("Y")) {
                    msg = new InstrumentRequest();
                }
                for (int i = 0; i < Integer.valueOf(msgCount); i++) {
                    sc.send(msg);
                }
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.runServer(4444);
    }

}