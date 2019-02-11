package ru.ecom.miniejb.impl;

import ru.ecom.miniejb.ThreadLocalContextManager;

import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Method;

/**
 * Вызов метода сервиса.
 */
public class RemoteProxyInvocationHandler extends LocalProxyInvocationHandler {

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

