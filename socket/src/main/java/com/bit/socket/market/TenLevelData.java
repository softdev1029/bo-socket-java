package com.bit.socket.market;

import com.bit.socket.base.AtomPrice;

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
