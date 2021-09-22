package com.bit.socket.market;

import com.bit.socket.base.AtomPrice;

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
