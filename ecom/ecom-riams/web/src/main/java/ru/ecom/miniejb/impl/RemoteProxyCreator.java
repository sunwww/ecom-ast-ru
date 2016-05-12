package ru.ecom.miniejb.impl;

import java.lang.reflect.Proxy;

import javax.persistence.EntityManagerFactory;

import ru.ecom.miniejb.IProxyCreator;

/**
 * Прокси для сервиса
 */
public class RemoteProxyCreator implements IProxyCreator {

    public RemoteProxyCreator(EntityManagerFactory aFactory) {
        theFactory =  aFactory ;
    }

    public <T> T createProxyService(Class aServiceClass, Class<T>[] aInterfaceClasses, EjbHash aMap) {
        try {
            return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
                    , aInterfaceClasses
                    , new RemoteProxyInvocationHandler(aServiceClass, theFactory, aMap));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private final EntityManagerFactory theFactory ;
}
