package ru.ecom.ejb.util.injection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

import javax.naming.InitialContext;

/**
 *
 */
public class ProxyInjection {

    private ProxyInjection() {

    }


    public static ProxyInjection getInstance() {
        return new ProxyInjection();
    }

    public <T> T getService(String aJndiName, Class<T> aServiceInterface) {
        try {
            InitialContext jndiContext = new InitialContext();
            try {
                Object objRefHome = jndiContext.lookup(aJndiName);
                Object objRefService = objRefHome.getClass().getMethod("create").invoke(objRefHome) ;
                return (T) Proxy.newProxyInstance(aServiceInterface.getClassLoader()
                        , new Class[] {aServiceInterface}, new ProxyInvocationHandler(objRefService)) ;
            } finally {
                jndiContext.close();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

    }

    private static class ProxyInvocationHandler implements InvocationHandler {
        public ProxyInvocationHandler(Object aService) {
            theService = aService;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method m = theMethodsHash.get(method) ;
            if(m==null) {
                m = theService.getClass().getMethod(method.getName(), method.getParameterTypes()) ;
                theMethodsHash.put(method, m) ;
            }
            return m.invoke(theService, args);
        }

        private final HashMap<Method, Method> theMethodsHash = new HashMap<Method, Method>();
        private final Object theService ;
    }
}
