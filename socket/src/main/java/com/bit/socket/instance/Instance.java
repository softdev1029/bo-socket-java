package com.bit.socket.instance;

import java.util.*;

import com.bit.socket.base.*;
import com.bit.socket.auth.*;
import com.bit.socket.trade.*;
import com.bit.socket.transaction.*;
import com.bit.socket.market.*;
import com.bit.socket.util.*;

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
                } else if (msgTypeKey.equals("S")) {
                    msg = new TwentyLevelData();
                } else if (msgTypeKey.equals("U")) {
                    msg = new ThirtyLevelData();
                }
                for (int i = 0; i < Integer.valueOf(msgCount); i++) {
                    msg.createExampleMessage();
                    sc.send(msg);
                }
            }
            scanner.close();
        }
    }
}
