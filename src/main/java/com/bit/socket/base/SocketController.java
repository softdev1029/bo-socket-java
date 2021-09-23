package com.bit.socket.base;

import java.net.*;
import java.io.*;
import java.util.function.Function;

import com.bit.socket.auth.*;
import com.bit.socket.trade.*;
import com.bit.socket.transaction.*;
import com.bit.socket.market.*;
import com.bit.socket.constant.*;

public class SocketController {
    private Socket socket = null;
    protected DataInputStream in = null;
    protected DataOutputStream out = null;
    protected Function<Message, String> recvCallback = null;
    protected String apiKey = null;

    public void createServer(int port, Function<Message, String> callback) {
    }

    public void create(String ip, int port, String apiKey, Function<Message, String> callback) {
        try {
            this.apiKey = apiKey;

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
            if (dataType2 == 0) {
            }
            int length = in.readShort();
            Logger.logln("Type: " + dataType);
            Logger.logln("Length:" + length);

            Message msg = new ClientLogon(null);
            if (dataType == MessageSymbol.CLIENT_LOGON) {
                msg = new ClientLogon(null);
            } else if (dataType == MessageSymbol.NEW_LIMIT_ORDER) {
                msg = new NewLimitOrder();
            } else if (dataType == MessageSymbol.INSTRUMENT_REQUEST) {
                msg = new InstrumentRequest();
            } else if (dataType == MessageSymbol.RISK_UPDATE_REQUEST) {
                msg = new RiskUpdateRequest();
            } else if (dataType == MessageSymbol.COLLATERAL_REQUEST) {
                msg = new CollateralRequest();
            } else if (dataType == MessageSymbol.MD_SUBSCRIBE) {
                msg = new MDSubscribe();
            } else if (dataType == MessageSymbol.MD_EXEC_REPORT) {
                msg = new MDExecReport();
            } else if (dataType == MessageSymbol.TOB_MSG) {
                msg = new TOBMsg();
            } else if (dataType == MessageSymbol.THREE_LEVEL_DATA) {
                msg = new ThreeLevelData();
            } else if (dataType == MessageSymbol.FIVE_LEVEL_DATA) {
                msg = new FiveLevelData();
            } else if (dataType == MessageSymbol.TEN_LEVEL_DATA) {
                msg = new TenLevelData();
            } else if (dataType == MessageSymbol.TWENTY_LEVEL_DATA) {
                msg = new TwentyLevelData();
            } else if (dataType == MessageSymbol.THIRTY_LEVEL_DATA) {
                msg = new ThirtyLevelData();
            }
            int error = msg.read(in);
            if (error == ReadError.NO_ERROR) {
            }
            msg.print();
            boolean isValid = msg.validate();
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
