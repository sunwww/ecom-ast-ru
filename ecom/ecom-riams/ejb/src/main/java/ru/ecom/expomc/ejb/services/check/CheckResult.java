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
     * @param aDate
     */
    public void set(String aFieldName, Object aDate) {
        values.put(aFieldName, aDate) ;
    }
    
    /**
     * Установка значения
     * @param aFieldName
     * @param aValue
     */
    public void set(String aFieldName, String aValue) {
        values.put(aFieldName, aValue) ;
    }

    /**
     * Установка значения
     * @param aFieldName
     * @param aValue
     */
    public void set(String aFieldName, BigDecimal aValue) {
        values.put(aFieldName, aValue) ;
    }

    public String toString() {
        return "CheckResult [ accepted = " + accepted +
                ", values = " + values +
                ", logs = " + logs +
                " ]";
    }
    protected Map<String, Object> getChanged() {
        return values;
    }
    /** Найдено ли условие проверки */
    public boolean isAccepted() { return accepted ; }
    public void setAccepted(boolean aAccepted) { accepted = aAccepted ; }

    /** Найдено ли условие проверки */
    private boolean accepted = false ;

    /** Сообщения */
    public ArrayList<ResultLog> getLogs() { return logs ; }
    private final TreeMap<String, Object> values = new TreeMap<>();

    /** Сообщения */
    private final ArrayList<ResultLog> logs = new ArrayList<>();
}
