package ru.nuzmsh.web.util;

import sun.misc.UCDecoder;
import sun.misc.UCEncoder;

import java.nio.charset.StandardCharsets;

/**
 *
 */
public class StringSafeEncode {

    public String encode(String aStr) {
        if (aStr == null) {
            return null;
        } else {
            return new UCEncoder().encode(aStr.getBytes(StandardCharsets.UTF_8));
        }
    }

    public String decode(String aStr) {
        if (aStr == null) {
            return null;
        } else {
            try {
                return new String(new UCDecoder().decodeBuffer(aStr), StandardCharsets.UTF_8);
            } catch (Exception e) {
                return aStr;
            }
        }
    }
}
