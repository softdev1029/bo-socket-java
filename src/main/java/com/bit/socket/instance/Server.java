package com.bit.socket.instance;

import com.bit.socket.base.*;
import com.bit.socket.auth.*;
import com.bit.socket.transaction.*;

public class Server extends Instance {
    String processState = "recv_logon";

    public void println(String msg) {
        System.out.println(msg);
    }

    public void printf(String msg, Object... args) {
        System.out.printf(msg, args);
    }

    public String recvCallback(Message msg) {
        printf("Received message: type=%s, len=%d\n", msg.MessageTypeStr, msg.MessageLen);

        if (processState == "recv_logon") {
            processState = "send_logon_reply";
        } else if (processState == "recv_order") {
            processState = "send_order_reply";
        }
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
                Message msg = new ClientLogon(null);
                createExampleLogonReply(msg);
                sc.send(msg);
                println("Sending logon reply ...");
                processState = "recv_order";
            } else if (processState == "send_order_reply") {
                Message msg = new NewLimitOrder();
                createExampleOrderReply(msg);
                sc.send(msg);
                println("Sending order reply ...");
                processState = "";
            }
        }
    }

    public void createExampleLogonReply(Message msg) {
        msg.Data1 = 'H';
        msg.Data2 = 0;
        msg.LogonType = 1; // logon
        msg.Account = 100700;
        msg.TwoFA = "1F6A".getBytes();
        msg.UserName = "BOU7".getBytes();
        msg.TradingSessionID = 506;
        msg.PrimaryOrderEntryIP = "1".getBytes();
        msg.SecondaryOrderEntryIP = "1".getBytes();
        msg.PrimaryMarketDataIP = "1".getBytes();
        msg.SecondaryMarketDataIP = "1".getBytes();
        msg.SendingTime = 0;
        msg.MsgSeqNum = 1500201;
        msg.Key = 432451;
        msg.LoginStatus = 1; // success
        msg.RejectReason = 0;
        msg.RiskMaster = 0;
    }

    public void createExampleOrderReply(Message msg) {
        msg.Data1 = 'T';
        msg.Data2 = 0;
        msg.MessageType = 14; // ORDER_ACK
        msg.Padding = 0;
        msg.Account = 100700;
        msg.OrderID = 46832151;
        msg.SymbolEnum = 1;
        msg.OrderType = 1; // LMT
        msg.SymbolType = 1; // SPOT
        msg.BOPrice = 50100.5;
        msg.BOSide = 3; // BUY
        msg.BOOrderQty = 2.0;
        msg.TIF = 2; // GTC
        msg.StopLimitPrice = 0;
        msg.BOSymbol = "BTCUSD".getBytes();
        msg.OrigOrderID = 0;
        msg.BOCancelShares = 0;
        msg.ExecID = 0;
        msg.ExecShares = 0;
        msg.RemainingQuantity = 0;
        msg.ExecFee = 0;
        msg.ExpirationDate = "".getBytes();
        msg.TraderID = 0;
        msg.RejectReason = 1;
        msg.SendingTime = 1000;
        msg.TradingSessionID = 506;
        msg.Key = 42341;
        msg.DisplaySize = 0;
        msg.RefreshSize = 0;
        msg.Layers = 0;
        msg.SizeIncrement = 0;
        msg.PriceIncrement = 0;
        msg.PriceOffset = 0;
        msg.BOOrigPrice = 0;
        msg.ExecPrice = 0;
        msg.MsgSeqNum = 79488880;
        msg.TakeProfitPrice = 0;
        msg.TriggerType = 0;
        msg.Attributes = "".getBytes();
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.runServer(4444);
    }

}