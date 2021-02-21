package ru.ecom.expomc.ejb.services.check;

import ru.ecom.expomc.ejb.services.check.result.ResultLog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author esinev
 * Date: 23.08.2006
 * Time: 11:28:42
 */
public class CheckResult  {



    public static CheckResult createAccepted(boolean aAccepted) {
        CheckResult r = new CheckResult();
        r.setAccepted(aAccepted);
        return r ;
    }

    public CheckResult() {
    }

    /**
     * Установка значения
     * @param aFieldName
     * @param aValue
     */
    public void set(String aFieldName, Object aDate) {
        theValues.put(aFieldName, aDate) ;
    }
    
    /**
     * Установка значения
     * @param aFieldName
     * @param aValue
     */
    public void set(String aFieldName, String aValue) {
        theValues.put(aFieldName, aValue) ;
    }

    /**
     * Установка значения
     * @param aFieldName
     * @param aValue
     */
    public void set(String aFieldName, BigDecimal aValue) {
        theValues.put(aFieldName, aValue) ;
    }

    public String toString() {
        return "CheckResult [ accepted = " + theAccepted +
                ", values = " + theValues +
                ", logs = " + theLogs +
                " ]";
    }
    protected Map<String, Object> getChanged() {
        return theValues;
    }
    /** Найдено ли условие проверки */
    public boolean isAccepted() { return theAccepted ; }
    public void setAccepted(boolean aAccepted) { theAccepted = aAccepted ; }

    /** Найдено ли условие проверки */
    private boolean theAccepted = false ;

    /** Сообщения */
    public ArrayList<ResultLog> getLogs() { return theLogs ; }
    private TreeMap<String, Object> theValues = new TreeMap<>();

    /** Сообщения */
    private final ArrayList<ResultLog> theLogs = new ArrayList<>();
}
