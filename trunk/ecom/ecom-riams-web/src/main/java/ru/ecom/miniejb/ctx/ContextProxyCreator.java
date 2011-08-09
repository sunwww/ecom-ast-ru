package ru.ecom.miniejb.ctx;

import java.lang.reflect.Proxy;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Прокси для сервиса
 */
public class ContextProxyCreator {

    public <T> T createProxyService(String aUsername, Set<String> aRoles, String aJdni) {
        try {
            ContextProxyInvocationHandler hander = new ContextProxyInvocationHandler(aUsername, aRoles, aJdni);


            return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
                    , getInterfaces(aJdni)
                    , hander);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private Class[] getInterfaces(String aJdni) throws NamingException {
        InitialContext ctx = new InitialContext() ;
        try {
            Object o = ctx.lookup(aJdni) ;
            return o.getClass().getInterfaces() ;
        } finally  {
            ctx.close() ;
        }
    }

}
