package ru.ecom.miniejb.impl;

import java.lang.reflect.Proxy;

import ru.ecom.miniejb.IProxyCreator;

/**
 * Прокси для сервиса
 */
public class LocalProxyCreator implements IProxyCreator {

    public LocalProxyCreator() {
    }

    public <T> T createProxyService(Class aServiceClass, Class<T>[] aInterfaceClasses, EjbHash aInterfaces) {
        try {
            return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
                    , aInterfaceClasses
                    , new LocalProxyInvocationHandler(aServiceClass,aInterfaces));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
