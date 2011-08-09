package ru.ecom.alg.omc.omcprice;

import java.util.List;

/**
 * Ошибка, при определении цены
 */
public class OmcCalcPriceException extends Exception {
    public OmcCalcPriceException(String aMessage, CalcLog aLog) {
        super(aMessage) ;
        theLog = aLog;
    }

    /** Лог определения цены */
    public List<String> getMessages() {
        return theLog.getMessages() ;
    }

    private final CalcLog theLog ;
}
