package ru.ecom.miniejb.impl;

import ru.ecom.miniejb.MethodNotImplementedException;

import javax.ejb.*;
import javax.transaction.UserTransaction;
import javax.xml.rpc.handler.MessageContext;
import java.security.Identity;
import java.security.Principal;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 *
 */
public class SimpleSessionContext implements SessionContext {

    private final Set<String> theRoles;
    private final Principal thePrincipal;

    public SimpleSessionContext(String aUsername, Set<String> aRoles) {
        theRoles = aRoles;
        thePrincipal = new SimplePrincipal(aUsername);
    }

    public Principal getCallerPrincipal() {
        return thePrincipal;
    }

    public boolean isCallerInRole(String aRole) {
        return theRoles.contains(aRole);
    }

    ////////// NOT IMPLEMENTED
    public EJBLocalObject getEJBLocalObject() {
        throw new MethodNotImplementedException();
    }


    public EJBObject getEJBObject() {
        throw new MethodNotImplementedException();
    }

    public MessageContext getMessageContext() {
        throw new MethodNotImplementedException();
    }

    public Object getBusinessObject(Class aClass) {
        throw new MethodNotImplementedException();
    }

    public Class getInvokedBusinessInterface() {
        throw new MethodNotImplementedException();
    }

    public boolean wasCancelCalled() {
        return false;
    }

    public EJBHome getEJBHome() {
        throw new MethodNotImplementedException();
    }

    public EJBLocalHome getEJBLocalHome() {
        throw new MethodNotImplementedException();
    }

    public Properties getEnvironment() {
        throw new MethodNotImplementedException();
    }

    public Identity getCallerIdentity() {
        throw new MethodNotImplementedException();
    }


    public boolean isCallerInRole(Identity identity) {
        throw new MethodNotImplementedException();
    }


    public UserTransaction getUserTransaction() {
        throw new MethodNotImplementedException();
    }

    public void setRollbackOnly() {
        throw new MethodNotImplementedException();
    }

    public boolean getRollbackOnly() {
        throw new MethodNotImplementedException();
    }

    public TimerService getTimerService() {
        throw new MethodNotImplementedException();
    }

    public Object lookup(String string) {
        throw new MethodNotImplementedException();
    }

    public Map<String, Object> getContextData() {
        return null;
    }
}
