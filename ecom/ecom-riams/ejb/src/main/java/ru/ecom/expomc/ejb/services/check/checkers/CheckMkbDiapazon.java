package ru.ecom.expomc.ejb.services.check.checkers;

import java.util.StringTokenizer;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Проверка на диапазон МКБ
 */
@Comment("Проверка на диапазон МКБ")
public class CheckMkbDiapazon extends AbstractCheckStringProperty {

    public boolean accept(String aStr) {
        StringTokenizer st = new StringTokenizer(aStr,",;: ");
        boolean accept = false ;
        while(st.hasMoreTokens()) {
            accept = checkDiapason(aStr, st.nextToken()) ;
            if(accept) break;

        }
        return accept;
    }

    private boolean checkDiapason(String aStr, String aDiapason) {
        if(aDiapason.indexOf('-')>=0) {
            return aStr.equals(aDiapason) ;
        } else {
            StringTokenizer st = new StringTokenizer(aDiapason, "-_=`~");
            return checkDiapason(aStr, st.nextToken(), st.nextToken()) ;
        }
    }

    private boolean checkDiapason(String aStr, String aFrom, String aTo) {
        long mkb = mkbVes(aStr) ;
        long from = mkbVes(aFrom) ;
        long to = mkbVes(aTo) ;
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


    /** Диапазоны МКБ, разделенные запятыми */
    @Comment("Диапазоны МКБ, разделенные запятыми")
    public String getDiapasones() { return theDiapasones ; }
    public void setDiapasones(String aDiapasones) { theDiapasones = aDiapasones ; }

    /** Диапазоны МКБ, разделенные запятыми */
    private String theDiapasones ;
}
