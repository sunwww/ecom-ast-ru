package ru.nuzmsh.util;

import ru.nuzmsh.util.format.DateConverter;

import java.util.Date;
import java.text.ParseException;
import java.math.BigDecimal;

/**
 * Разбивает строку на подстроки с помощью разделителя
 */
public final class LogicTokenizer {

    /**
     * Создание нового
     * @param aLine  строка
     * @param aDelim разделитель
     */
    public LogicTokenizer(String aLine, char aDelim) {

        theLine = aLine==null ? "" : aLine;
        theDelim = aDelim;
        theNext = (theLine != null && theLine.length() > 0);
        theLineLength = theLine != null ? theLine.length() : -1;
    }

    /**
     * Есть ли следующий ?
     */
    public final boolean hasNext() {
        return theNext;
    }

    /**
     * Следующий Long
     */
    public final Long getNextLong() {
        String str = getNextString() ;
        return str!=null ? Long.valueOf(str) : null ;
    }

    /**
     * Следующий boolean
     */
    public final boolean getNextBoolean() {
        String str = getNextString() ;
//        System.out.println("LogicTokenizer.boolean = " + str +" = "+ (str != null && "1".equals(str)));
        if(str!=null) {
            if("1".equals(str) || "2".equals(str)) {
            } else {
                //throw new RuntimeException("Значение boolean :"+str) ;
            }
        }
        return str != null && "1".equals(str) ;
    }

    /**
     * Следующая дата
     * @return
     * @throws ParseException
     */
    public final Date getNextDate() throws ParseException {
        return DateConverter.getDateFromGlobale(getNextString());
    }

    /**
     * Следующая подстрока
     */
    public final String getNextString() {
        String ret;
        if (thePosition == theLineLength) {
            ret = null ;
            theNext = false ;
        } else {
            if (theLine.charAt(thePosition) == theDelim) {
                ret = null;
                thePosition ++;
            } else {
                int nextPosition = theLine.indexOf(theDelim, thePosition);
                if (nextPosition == -1) nextPosition = theLineLength;
                ret = theLine.substring(thePosition, nextPosition);
                thePosition = nextPosition + 1;
                theNext = thePosition <= theLineLength;
            }
        }
        return ret;
    }

    /**
     * Следующий BigDecimal
     */
    public BigDecimal getNextBigDecimal() {
        String value = getNextString() ;
        return value!=null ? new BigDecimal(value) : null ;
    }


    private int thePosition = 0;
    private final String theLine;
    private final char theDelim;
    private boolean theNext;
    private final int theLineLength;

}