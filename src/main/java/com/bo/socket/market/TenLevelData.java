package com.bo.socket.market;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

import com.bo.socket.base.*;
import com.bo.socket.auth.*;
import com.bo.socket.constant.*;

public class TenLevelData extends BaseLevelData {
    public TenLevelData() {
        LevelCount = 20;

        MessageTypeStr = "TenLevelData";
        MessageLen = 378;
        Data1 = 'O';

        Prices = new AtomPrice[LevelCount];
        for (int i = 0; i < LevelCount; i++) {
            Prices[i] = new AtomPrice();
        }
    }
}
