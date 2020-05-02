package ru.ecom.ejb.services.monitor;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 14.11.2006
 * Time: 17:44:32
 * To change this template use File | Settings | File Templates.
 */
public class LocalMonitorStatus implements IMonitor {

    private static final Logger LOG = Logger.getLogger(LocalMonitorStatus.class) ;

    public LocalMonitorStatus(String aName, double aMaximum) {
        theName = aName ;
        theMaximum = aMaximum ;
    }

    public void advice(double aAdvice) {
        theCurrent+=aAdvice ;
    }

    /** Прервано */
    public boolean isCancelled() { return theCancelled ; }
    public void setCancelled(boolean aCancelled) { theCancelled = aCancelled ; }

    public void error(String aMessage, Exception aException) {
        setText("Ошибка:" + aMessage);
        LOG.error(aMessage, aException) ;
    }

    /** Название */
    public String getName() { return theName ; }

    /** Текст */
    public String getText() { return theText ; }
    public void setText(String aText) {
    	theText = aText ;
    }

    public void finish(String aParameters) {
        theIsFinished = true ;
        theFinishedParameters = aParameters ;
    }

    public String getFinishParameters() {
        return theFinishedParameters ;
    }

    /** Текст */
    private String theText ;
    private boolean theIsFinished = false ;
    private String theFinishedParameters = null ;
    /** Название */
    private final String theName ;
    private final double theMaximum ;
    private double theCurrent = 0 ;
    /** Прервано */
    private boolean theCancelled = false ;

    public double getMaximum() {
        return theMaximum ;
    }


    public double getCurrent() {
        return theCurrent ;
    }


    public boolean isFinished() {
        return theIsFinished ;
    }
}
