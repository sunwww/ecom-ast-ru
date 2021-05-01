package ru.ecom.ejb.services.monitor;

import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 14.11.2006
 * Time: 17:44:32
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
public class LocalMonitorStatus implements IMonitor {

    private static final Logger LOG = Logger.getLogger(LocalMonitorStatus.class) ;

    public LocalMonitorStatus(String aName, double aMaximum) {
        name = aName ;
        maximum = aMaximum ;
    }

    public void advice(double aAdvice) {
        current+=aAdvice ;
    }

    /** Прервано */
    public boolean isCancelled() { return cancelled ; }

    public void error(String aMessage, Exception aException) {
        setText("Ошибка:" + aMessage);
        LOG.error(aMessage, aException) ;
    }


    public void finish(String aParameters) {
        isFinished = true ;
        finishParameters = aParameters ;
    }

    /** Текст */
    private String text ;
    private boolean isFinished = false ;
    private String finishParameters = null ;
    /** Название */
    private final String name ;
    private final double maximum ;
    private double current = 0 ;
    /** Прервано */
    private boolean cancelled = false ;

    public boolean isFinished() {
        return isFinished ;
    }
}
