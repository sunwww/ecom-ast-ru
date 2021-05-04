package ru.ecom.ejb.util.injection;

import javax.naming.InitialContext;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

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
            service = aService;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method m = methodsHash.get(method) ;
            if(m==null) {
                m = service.getClass().getMethod(method.getName(), method.getParameterTypes()) ;
                methodsHash.put(method, m) ;
            }
            return m.invoke(service, args);
        }

        private final HashMap<Method, Method> methodsHash = new HashMap<>();
        private final Object service ;
    }
}
