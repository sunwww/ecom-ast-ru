package ru.ecom.miniejb;

import ru.ecom.miniejb.impl.EjbHash;

/**
 * Создание proxy
 */
public interface IProxyCreator {
    public <T> T createProxyService(Class aServiceClass, Class<T>[] aInterfaceClasses, EjbHash aInterfacesHash) ;

}
