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

public class ThirtyLevelData extends BaseLevelData {
    public ThirtyLevelData() {
        LevelCount = 60;

        MessageTypeStr = "ThirtyLevelData";
        MessageLen = 1058;
        Data1 = 'U';

        Prices = new AtomPrice[LevelCount];
        for (int i = 0; i < LevelCount; i++) {
            Prices[i] = new AtomPrice();
        }
    }
}
