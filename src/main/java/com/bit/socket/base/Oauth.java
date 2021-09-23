package com.bit.socket.base;

import org.apache.commons.codec.binary.*;
import de.taimos.totp.*;

public class Oauth {
    public static String getTOTPCode(String secretKey) {
        String hexKey = Hex.encodeHexString(secretKey.getBytes());
        return TOTP.getOTP(hexKey);
    }
}
