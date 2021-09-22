package com.bit.socket.market;

import com.bit.socket.base.AtomPrice;

public class TwentyLevelData extends BaseLevelData {
    public TwentyLevelData() {
        LevelCount = 40;

        MessageTypeStr = "TwentyLevelData";
        MessageLen = 718;
        Data1 = 'S';

        Prices = new AtomPrice[LevelCount];
        for (int i = 0; i < LevelCount; i++) {
            Prices[i] = new AtomPrice();
        }
    }
}
