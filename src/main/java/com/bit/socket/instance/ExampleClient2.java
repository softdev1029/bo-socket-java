package com.bit.socket.instance;

import com.bit.socket.base.*;

import java.util.Scanner;

import com.bit.socket.auth.*;
import com.bit.socket.transaction.*;
import com.bit.socket.constant.*;

public class ExampleClient2 extends Instance {
    String processState = "send_logon";
    String apiKey = null;

    public void println(String msg) {
        System.out.println(msg);
    }

    public void printf(String msg, Object... args) {
        System.out.printf(msg, args);
    }

    public String recvCallback(Message msg) {
        printf("Received message: type=%s, len=%d\n", msg.MessageTypeStr, msg.MessageLen);

        if (msg.MessageTypeStr == "ClientLogon") {
            if (msg.LoginStatus == 1) {
                println("Logon success");
                processState = "send_order";
            } else if (msg.LoginStatus == 2) {
                println("Logon fail");
                processState = "exit";
            }
        } else if (msg.MessageTypeStr == "NewLimitOrder") {
            if (msg.MessageType == MessageTypes.ORDER_ACK) {
                println("Order replied");
                processState = "send_logout";
            }
        }
        return "";
    }

    public String sendCallback(SocketController sc) {
        if (processState == "send_logon") {
            Message msg = new ClientLogon(apiKey);
            createExampleLogon(msg);
            sc.send(msg);
            println("Sending logon ...");
            processState = "recv_logon";
        } else if (processState == "send_order") {
            Message msg = new NewLimitOrder();
            createExampleOrder(msg);
            sc.send(msg);
            println("Sending order ...");
            processState = "recv_order_reply";
        } else if (processState == "send_logout") {
            Message msg = new ClientLogon(apiKey);
            createExampleLogout(msg);
            sc.send(msg);
            println("Sending logout ...");
            processState = "exit";
        }
        return "";
    }

    public void runExample(String ip, int port, String apiKey) {
        this.apiKey = apiKey;
        sc = new SocketController();
        sc.create(ip, port, apiKey, this::recvCallback);
        SocketThread st = new SocketThread(sc, this::sendCallback);
        st.start();
    }

    public void createExampleLogon(Message msg) {
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
        msg.LoginStatus = 0;
        msg.RejectReason = 0;
        msg.RiskMaster = 0;
    }

    public void createExampleLogout(Message msg) {
        msg.Data1 = 'H';
        msg.Data2 = 0;
        msg.LogonType = 2; // logout
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
        msg.LoginStatus = 0;
        msg.RejectReason = 0;
        msg.RiskMaster = 0;
    }

    public void createExampleOrder(Message msg) {
        msg.Data1 = 'T';
        msg.Data2 = 0;
        msg.MessageType = 1; // ORDER_NEW
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
        msg.SecondLegPrice = 111;
        msg.RouteEnum = 1;
        msg.ModifyType = 1;
        msg.Attributes = "".getBytes();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a valid server IP address: ");
        String ip = scanner.nextLine();
        System.out.println("Enter a valid server Port number: ");
        int port = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter a valid API Trading Key: ");
        String apiKey = scanner.nextLine();
        scanner.close();

        ExampleClient2 example = new ExampleClient2();

        example.runExample(ip, port, apiKey);

        while (true) {
            String code = Oauth.getTOTPCode(apiKey);
            System.out.println(code);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

}