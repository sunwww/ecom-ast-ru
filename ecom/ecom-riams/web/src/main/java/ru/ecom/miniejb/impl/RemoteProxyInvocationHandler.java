package ru.ecom.miniejb.impl;

import java.lang.reflect.Method;

import javax.persistence.EntityManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.ecom.miniejb.ThreadLocalContextManager;

/**
 * Вызов метода сервиса.
 */
public class RemoteProxyInvocationHandler extends LocalProxyInvocationHandler {

    private final static Log LOG = LogFactory.getLog(RemoteProxyInvocationHandler.class);
    private final static boolean CAN_TRACE = LOG.isTraceEnabled();


    public RemoteProxyInvocationHandler(Class aServiceClass, EntityManagerFactory aFactory,EjbHash aInterfaces) {
        super(aServiceClass, aInterfaces);
        theFactory = aFactory ;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ThreadLocalContextManager.setEntityManagerFactory(theFactory);
        return super.invoke(proxy, method, args);
    }

    private final EntityManagerFactory theFactory ;
}

