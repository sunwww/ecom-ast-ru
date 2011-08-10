package ru.nuzmsh.util.med;

import ru.nuzmsh.util.StringUtil;

import java.util.StringTokenizer;

/**
 * Проверка на диапазон МКБ
 */
public class CheckMkbRangeHelper {

    public boolean isIn(String aMkb, String aFromMkb, String aToMkb) {
        if(StringUtil.isNullOrEmpty(aMkb)) throw new IllegalArgumentException("Нет МКБ aMkb") ;
        if(StringUtil.isNullOrEmpty(aFromMkb)) throw new IllegalArgumentException("Нет МКБ aFromMkb") ;
        if(StringUtil.isNullOrEmpty(aToMkb)) throw new IllegalArgumentException("Нет МКБ aToMkb") ;
        long mkb = mkbVes(aMkb) ;
        long from = mkbVes(aFromMkb) ;
        long to = mkbVes(aToMkb) ;
        return mkb>=from && mkb<=to ;
    }

    private long mkbVes(String aMkb) {
        char letter = aMkb.charAt(0) ;
        long big = 1 ;
        long small = 1 ;
        StringTokenizer st = new StringTokenizer(aMkb.substring(1), ".");
        if(st.hasMoreTokens()) big = Long.parseLong(st.nextToken()) ;
        if(st.hasMoreTokens()) small = Long.parseLong(st.nextToken()) ;
        return (letter * 100000000)  + (big * 10000) + ( small * 100) ;
    }

}
