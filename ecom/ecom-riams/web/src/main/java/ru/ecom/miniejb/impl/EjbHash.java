package ru.ecom.miniejb.impl;

import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 07.02.2007
 * Time: 2:22:17
 * To change this template use File | Settings | File Templates.
 */
public class EjbHash {
    private final static Logger LOG = Logger.getLogger(EjbHash.class) ;
    private final static boolean CAN_TRACE = LOG.isDebugEnabled() ;


    public EjbHash() {

    }

    public void put(Class aClass, String aJndi) {
        if (CAN_TRACE) LOG.info(" putting " + aClass+" to "+aJndi);
        theHashMap.put(aClass.getName(), aJndi) ;
    }

    public String get(Class aClass) {
        String jndi = theHashMap.get(aClass.getName()) ;
        if (CAN_TRACE) LOG.info(" getting " + aClass+" ("+jndi+")");
        return  jndi;
    }
    private final HashMap<String, String> theHashMap = new HashMap<String, String>();
}
