package com.bo.socket.base;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.function.Function;

import com.bo.socket.base.*;
import com.bo.socket.auth.*;
import com.bo.socket.trade.*;
import com.bo.socket.transaction.*;
import com.bo.socket.constant.*;

import com.bo.socket.base.Message;

public class SocketController {
    private Socket socket = null;
    protected DataInputStream in = null;
    protected DataOutputStream out = null;
    protected Function<Message, String> recvCallback = null;

    public void createServer(int port, Function<Message, String> callback) {}

    public void create(String ip, int port, Function<Message, String> callback) {
        try {
            socket = new Socket(ip, port);
            System.out.println("Connected to server ...");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            recvCallback = callback;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isReadable() {
        try {
            return in.available() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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

            Message msg = new ClientLogon();
            if (dataType == MessageSymbol.CLIENT_LOGON) {
                msg = new ClientLogon();
            } else if (dataType == MessageSymbol.NEW_LIMIT_ORDER) {
                msg = new NewLimitOrder();
            } else if (dataType == MessageSymbol.INSTRUMENT_REQUEST) {
                msg = new InstrumentRequest();
            } else if (dataType == MessageSymbol.RISK_UPDATE_REQUEST) {
                msg = new RiskUpdateRequest();
            } else if (dataType == MessageSymbol.COLLATERAL_REQUEST) {
                msg = new CollateralRequest();
            }
            int error = msg.read(in);
            msg.print();
            var isValid = msg.validate();
            System.out.println("Valid: " + isValid);
            if (!isValid) {
                msg.printRejectReason();
            } else {
                recvCallback.apply(msg);
            }
               
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(Message msg) {
        // msg.createExampleMessage();
        msg.makeByteBuffer();
        msg.print();
        msg.send(out);
    }
}
