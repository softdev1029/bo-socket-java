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

public class Example extends Instance {
    String processState = "send_logon";

    public String recvCallback(Message msg) {
        return "";
    }

    public void runExample(String ip, int port) {
        sc = new SocketController();
        sc.create(ip, port, this::recvCallback);

        // while (true) {
        //     while (sc.isReadable()) {
        //         sc.read();
        //     }
            
        //     if (processState == "send_logon") {
        //         Message msg = new ClientLogon();
        //         sc.send(msg);
        //     } else if (processState == "send_order") {
        //         Message msg = new NewLimitOrder();
        //         sc.send(msg);
        //     } else if (processState == "send_logout") {
        //         Message msg = new ClientLogon();
        //         sc.send(msg);
        //     }
        // }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.printf("usage: <host> <port>\n");
            return;
        }

        Example example = new Example();

        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        example.runExample(ip, port);
    }

}