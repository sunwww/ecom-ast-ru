package ru.ecom.miniejb.ctx;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 */
public class InitialContextFactoryImpl implements InitialContextFactory {
    private final static Log LOG = LogFactory.getLog(InitialContextFactoryImpl.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;

    public InitialContextFactoryImpl() {
        if (CAN_TRACE) LOG.trace("Init()");
    }

    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
        if (CAN_TRACE) LOG.trace("getInitialContext() [environment = " + environment+"]");
        return new ContextImpl(environment);
    }
}
