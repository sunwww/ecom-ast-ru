package ru.ecom.alg.omc.omcprice;

import java.util.LinkedList;
import java.util.List;
import java.io.Serializable;

import org.apache.log4j.Logger;


/**
 * Лог определения цены
 */
class CalcLog implements Serializable {

    private static final Logger LOG = Logger.getLogger(CalcLog.class) ;
    private static final boolean CAN_DEBUG = LOG.isDebugEnabled() ;
	
    protected void log(String aMessage) {
    	if(CAN_DEBUG) LOG.debug(aMessage) ;
        theList.add(aMessage) ;
    }

    protected void log(Object ...args) {
        StringBuilder sb = new StringBuilder();
        for (Object o : args) {
            sb.append(o) ;
        }
    	if(CAN_DEBUG) LOG.debug(sb.toString()) ;
        theList.add(sb.toString()) ;
    }

    protected List<String> getMessages() {
        return theList ;
    }

    private LinkedList<String> theList = new LinkedList<String>();
}
