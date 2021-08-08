package com.bo.socket.base;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.function.Function;

import com.bo.socket.base.*;
import com.bo.socket.auth.*;
import com.bo.socket.trade.*;
import com.bo.socket.transaction.*;
import com.bo.socket.market.*;
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
            Logger.logln("Connected to server ...");

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
            Logger.logln("Type: " + dataType);
            Logger.logln("Length:" + length);

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
            } else if (dataType == MessageSymbol.THREE_LEVEL_DATA) {
                msg = new ThreeLevelData();
            } else if (dataType == MessageSymbol.FIVE_LEVEL_DATA) {
                msg = new FiveLevelData();
            }
            int error = msg.read(in);
            msg.print();
            var isValid = msg.validate();
            Logger.logln("Valid: " + isValid);
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
        msg.makeByteBuffer();
        msg.print();
        msg.send(out);
    }
}
