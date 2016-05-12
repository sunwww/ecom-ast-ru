package ru.ecom.miniejb.impl;

import java.security.Identity;
import java.security.Principal;
import java.util.Properties;
import java.util.Set;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.SessionContext;
import javax.ejb.TimerService;
import javax.transaction.UserTransaction;
import javax.xml.rpc.handler.MessageContext;

import ru.ecom.miniejb.MethodNotImplementedException;

/**
 */
public class SimpleSessionContext implements SessionContext {

    private final Set<String> theRoles ;
    private final Principal thePrincipal ;

    public SimpleSessionContext(String aUsername, Set<String> aRoles) {
        theRoles = aRoles;
        thePrincipal = new SimplePrincipal(aUsername) ;
    }

    public Principal getCallerPrincipal() {
        return thePrincipal;
    }

    public boolean isCallerInRole(String aRole) {
        return theRoles.contains(aRole) ;
    }

    ////////// NOT IMPLEMENTED
    public EJBLocalObject getEJBLocalObject() throws IllegalStateException {
        throw new MethodNotImplementedException() ;
    }


    public EJBObject getEJBObject() throws IllegalStateException {
        throw new MethodNotImplementedException() ;
    }

    public MessageContext getMessageContext() throws IllegalStateException {
        throw new MethodNotImplementedException() ;
    }

    public Object getBusinessObject(Class aClass) throws IllegalStateException {
        throw new MethodNotImplementedException() ;
    }

    public Object getInvokedBusinessInterface() throws IllegalStateException {
        throw new MethodNotImplementedException() ;
    }

    public EJBHome getEJBHome() {
        throw new MethodNotImplementedException() ;
    }

    public EJBLocalHome getEJBLocalHome() {
        throw new MethodNotImplementedException() ;
    }

    public Properties getEnvironment() {
        throw new MethodNotImplementedException() ;
    }

    public Identity getCallerIdentity() {
        throw new MethodNotImplementedException() ;
    }


    public boolean isCallerInRole(Identity identity) {
        throw new MethodNotImplementedException() ;
    }


    public UserTransaction getUserTransaction() throws IllegalStateException {
        throw new MethodNotImplementedException() ;
    }

    public void setRollbackOnly() throws IllegalStateException {
        throw new MethodNotImplementedException() ;
    }

    public boolean getRollbackOnly() throws IllegalStateException {
        throw new MethodNotImplementedException() ;
    }

    public TimerService getTimerService() throws IllegalStateException {
        throw new MethodNotImplementedException() ;
    }

    public Object lookup(String string) {
        throw new MethodNotImplementedException() ;
    }
}
