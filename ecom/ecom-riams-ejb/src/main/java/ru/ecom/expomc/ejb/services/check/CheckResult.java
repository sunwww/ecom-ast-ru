package ru.ecom.expomc.ejb.services.check;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

//import ru.ecom.expomc.ejb.domain.message.MessageLog;
import ru.ecom.expomc.ejb.services.check.result.ResultLog;

/**
 * @author esinev
 * Date: 23.08.2006
 * Time: 11:28:42
 */
public class CheckResult  {

    private final static Logger LOG = Logger.getLogger(CheckResult.class) ;
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;


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


    private void addLog(int aType, String aMessage) {
        if (CAN_DEBUG) LOG.debug("aMessage = " + aMessage);
        theLogs.add(new ResultLog(aType, aMessage)) ;
    }
    /**
     * Ход выполнения
     * @param aMessage
     */
    public void debug(String aMessage) {
        //addLog(MessageLog.DEBUG, aMessage);
    }

    public void info(String aMessage) {
       // addLog(MessageLog.INFO, aMessage);
    }

    public void warn(String aMessage) {
        //addLog(MessageLog.WARN, aMessage);
    }

    public void error(String aMessage) {
        //addLog(MessageLog.ERROR, aMessage);
    }

    public String toString() {
    	StringBuilder sb = new StringBuilder() ;
    	sb.append("CheckResult [ accepted = ").append(theAccepted) ;
    	sb.append(", values = ").append(theValues) ;
    	sb.append(", logs = ").append(theLogs) ;
    	sb.append(" ]") ;
    	return sb.toString() ;
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
    private TreeMap<String, Object> theValues = new TreeMap<String, Object>();

    /** Сообщения */
    private final ArrayList<ResultLog> theLogs = new ArrayList<ResultLog>();
}
