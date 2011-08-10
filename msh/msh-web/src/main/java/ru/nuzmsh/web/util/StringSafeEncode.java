package ru.nuzmsh.web.util;

import java.io.UnsupportedEncodingException;

import sun.misc.UCDecoder;
import sun.misc.UCEncoder;

/**
 *
 */
public class StringSafeEncode {

    public String encode(String aStr) {
        if(aStr==null) {
            return null ;
        } else {
            try {
                return new UCEncoder().encode(aStr.getBytes("utf-8")) ;
            } catch (UnsupportedEncodingException e) {
                return aStr ;
            }
        }
    }

    public String decode(String aStr) {
        if(aStr==null) {
            return null ;
        } else {
            try {
                return new String(new UCDecoder().decodeBuffer(aStr),"utf-8") ;
            } catch (Exception e) {
                return aStr ;
            }
        }
    }

    public static void main(String[] args) {
        StringSafeEncode s = new StringSafeEncode();
        String str = s.encode("фываолжфйцщушгфываоляюййцуккенгшщзхфывп") ;
        System.out.println("str = " + str);

        System.out.println(s.decode(str));

    }
}
