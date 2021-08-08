package com.bo.socket.instance;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.*;

import com.bo.socket.base.*;
import com.bo.socket.auth.*;
import com.bo.socket.trade.*;
import com.bo.socket.transaction.*;
import com.bo.socket.market.*;
import com.bo.socket.constant.*;
import com.bo.socket.util.*;

public class Instance {
    protected SocketController sc = null;

    protected void runInstance() {
      while (true) {
        while (sc.isReadable()) {
            sc.read();
        }
        
        Scanner scanner = new Scanner(System.in);
        String msgTypeKey = "";
        while (!CreateMessage.isValidMessageType(msgTypeKey)) {
            CreateMessage.printAllRequestMessageTypes();

            Logger.logln("Enter a valid message type: ");
    
            msgTypeKey = scanner.nextLine();
        }
        
        if (msgTypeKey.equals("0")) {
            // receive from server
        } else {
            Logger.logln("Message type is: " + CreateMessage.REQUEST_MESSAGE_TYPES.get(msgTypeKey));

            String msgCount = null;
            while (!Util.isNumeric(msgCount)) {
                Logger.logln("Enter a valid count: ");
        
                msgCount = scanner.nextLine();
            }

            Logger.logln("Message count is: " + msgCount);

            Message msg = new ClientLogon();
            if (msgTypeKey.equals("H")) {
                msg = new ClientLogon();
            } else if (msgTypeKey.equals("T")) {
                msg = new NewLimitOrder();
            } else if (msgTypeKey.equals("Y")) {
                msg = new InstrumentRequest();
            } else if (msgTypeKey.equals("w")) {
                msg = new RiskUpdateRequest();
            } else if (msgTypeKey.equals("f")) {
                msg = new CollateralRequest();
            } else if (msgTypeKey.equals("s")) {
                msg = new MDSubscribe();
            } else if (msgTypeKey.equals("V")) {
                msg = new MDExecReport();
            } else if (msgTypeKey.equals("t")) {
                msg = new TOBMsg();
            } else if (msgTypeKey.equals("M")) {
                msg = new ThreeLevelData();
            } else if (msgTypeKey.equals("m")) {
                msg = new FiveLevelData();
            } else if (msgTypeKey.equals("O")) {
                msg = new TenLevelData();
            }
            for (int i = 0; i < Integer.valueOf(msgCount); i++) {
                msg.createExampleMessage();
                sc.send(msg);
            }
        }
    }
    }
}
