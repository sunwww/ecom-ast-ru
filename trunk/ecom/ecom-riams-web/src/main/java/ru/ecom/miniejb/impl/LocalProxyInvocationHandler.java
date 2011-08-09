package ru.ecom.miniejb.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.ecom.miniejb.ThreadLocalContextManager;

/**
 * Вызов метода сервиса.
 */
public class LocalProxyInvocationHandler implements InvocationHandler {

    private final static Log LOG = LogFactory.getLog(LocalProxyInvocationHandler.class);
    private final static boolean CAN_TRACE = LOG.isTraceEnabled();


    public LocalProxyInvocationHandler(Class aServiceClass, EjbHash aInterfaces) {
        theServiceClass = aServiceClass;
        theInterfaces = aInterfaces;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        if (CAN_TRACE) LOG.trace("invoking " + method);
        Method m = theMethodsHash.get(method);
        if (m == null) {
            m = theServiceClass.getMethod(method.getName(), method.getParameterTypes());
            theMethodsHash.put(method, m);
        }
        Object service = theServiceClass.newInstance();
        injectService(service, theServiceClass);
        Object ret = m.invoke(service, args);
        deleteInjected(service, theServiceClass);
        return ret;
    }

    static void deleteInjected(Object aService, Class aClass) throws IllegalAccessException {
        for (Field field : aClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Resource.class)
                    || field.isAnnotationPresent(PersistenceContext.class)
                    || field.isAnnotationPresent(PersistenceUnit.class)) {
                field.set(aService, null);
            }
        }
        if (aClass.getSuperclass() != null) {
            deleteInjected(aService, aClass.getSuperclass());
        }
    }


    private void injectService(Object aService, Class aClass) throws IllegalAccessException, NamingException {
        if (aClass == null || aClass.equals(Object.class)) return;
//        LOG.info("aClass = " + aClass);
//        LOG.info("aService = " + aService);

        for (Field field : aClass.getDeclaredFields()) {
            if (CAN_TRACE) LOG.trace(" field = " + field);
            if (field.isAnnotationPresent(Resource.class) && field.getType().equals(SessionContext.class)) {
                field.setAccessible(true);
                field.set(aService, ThreadLocalContextManager.getSessionContext());
            } else if (field.isAnnotationPresent(PersistenceContext.class)) {
                try {
                    field.setAccessible(true);
                    field.set(aService, ThreadLocalContextManager.getManager());
                } catch (Exception e) {
                    LOG.error("Поле " + field, e);
                }
            } else if (field.isAnnotationPresent(PersistenceUnit.class)) {
                field.setAccessible(true);
                field.set(aService, ThreadLocalContextManager.getFactory());
            } else if (field.isAnnotationPresent(EJB.class)) {
                field.setAccessible(true);
                InitialContext ctx = new InitialContext();
                try {
                    field.set(aService, ctx.lookup(theInterfaces.get(field.getType()))) ;
                } finally{
                    ctx.close() ;
                }
            }

        }
        if (aClass.getSuperclass() != null) {
            injectService(aService, aClass.getSuperclass());
        }
    }


    final HashMap<Method, Method> theMethodsHash = new HashMap<Method, Method>();
    final Class theServiceClass;
    private final EjbHash theInterfaces ;
}

