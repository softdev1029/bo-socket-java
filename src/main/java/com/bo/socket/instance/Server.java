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
    
    public void runServer(int port) {
        sc = new ServerController();
        sc.createServer(port);
        
        super.runInstance();
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.runServer(4444);
    }

}