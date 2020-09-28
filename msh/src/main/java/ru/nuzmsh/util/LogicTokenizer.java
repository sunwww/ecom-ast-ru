package ru.nuzmsh.util;

import ru.nuzmsh.util.format.DateConverter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

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
        theNext = theLine.length() > 0;
        theLineLength = theLine.length() ;
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

        return "1".equals(str) ;
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