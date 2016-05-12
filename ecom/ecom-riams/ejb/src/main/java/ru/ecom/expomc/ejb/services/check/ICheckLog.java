package ru.ecom.expomc.ejb.services.check;

/**
 * Ведение лога
 */
public interface ICheckLog {
    void error(String aMessage) ;
    void error(Object ...aArgs) ;
    void debug(String aMessage) ;
    void info(String aMessage) ;
    void info(Object ...aArgs) ;
    void warn(String aMessage) ;
}
