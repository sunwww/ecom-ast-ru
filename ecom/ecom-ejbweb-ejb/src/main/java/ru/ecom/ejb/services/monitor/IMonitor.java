package ru.ecom.ejb.services.monitor;

/**
 * Ход выполнения долгой операции
 */
public interface IMonitor {

    void advice(double aAdvice) ;
    void setText(String aText) ;
    void finish(String aParameters) ;
    boolean isCancelled() ;
    void error(String aMessage, Exception aException) ;
}
