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

public class Server extends Instance {
    String processState = "";

    public String recvCallback(Message msg) {
        processState = "send_logon_reply";
        return "";
    }
    
    public void runServer(int port) {
        sc = new ServerController();
        sc.createServer(port, this::recvCallback);
        
        while (true) {
            while (sc.isReadable()) {
                sc.read();
            }
            
            if (processState == "send_logon_reply") {
                Message msg = new ClientLogon();
                sc.send(msg);
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.runServer(4444);
    }

}