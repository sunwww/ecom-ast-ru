package ru.ecom.miniejb.ctx;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import java.util.Hashtable;

/**
 *
 */
public class InitialContextFactoryImpl implements InitialContextFactory {
    private static final Logger LOG = Logger.getLogger(InitialContextFactoryImpl.class) ;
    private static final boolean CAN_TRACE = LOG.isDebugEnabled() ;

    public InitialContextFactoryImpl() {
        if (CAN_TRACE) LOG.info("Init()");
    }

    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
        if (CAN_TRACE) LOG.info("getInitialContext() [environment = " + environment+"]");
        return new ContextImpl(environment);
    }
}
