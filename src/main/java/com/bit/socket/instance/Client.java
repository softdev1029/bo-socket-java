package com.bit.socket.instance;

import com.bit.socket.base.*;

public class Client extends Instance {

    public String recvCallback(Message msg) {
        return "";
    }

    public void runClient(String ip, int port) {
        sc = new SocketController();
        sc.create(ip, port, null, this::recvCallback);

        super.runInstance();
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            Logger.logf("usage: <host> <port>\n");
            return;
        }

        Client client = new Client();

        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        client.runClient(ip, port);
    }

}