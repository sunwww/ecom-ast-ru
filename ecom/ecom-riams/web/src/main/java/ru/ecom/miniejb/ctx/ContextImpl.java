package ru.ecom.miniejb.ctx;

import org.apache.log4j.Logger;
import ru.ecom.miniejb.MethodNotImplementedException;

import javax.naming.*;
import java.util.Hashtable;
import java.util.TreeSet;

/**
 * Перенаправление запроса в стандартный InitialContext
 */
public class ContextImpl implements Context {

    private static final Logger LOG = Logger.getLogger(ContextImpl.class) ;
    private static final boolean CAN_TRACE = LOG.isDebugEnabled() ;

    private final Hashtable theEnvironment ;
    private final ContextProxyCreator theProxyCreator = new ContextProxyCreator();

    public ContextImpl(Hashtable aEnvironment) {
        if (CAN_TRACE) LOG.info("ContextImpl()");
        theEnvironment = aEnvironment ;
    }

    public Object lookup(String name) throws NamingException {
        if (CAN_TRACE) LOG.info("lookup() [name = " + name+"]");
        String username = (String)theEnvironment.get(Context.SECURITY_PRINCIPAL) ;
        TreeSet<String> roles = new TreeSet<>();
        roles.add("/Policy/Exp/Document/View") ;
        roles.add("/Policy/Exp/Format/View") ;
        roles.add("/Policy/Exp/Time/View") ;
        roles.add("/Policy/Exp/Field/View") ;

        return theProxyCreator.createProxyService(username, roles, name) ;
    }

    public void close() throws NamingException {
        if (CAN_TRACE) LOG.info("close()");
    }

    ///////////// NOT IMPLEMENTED

    public Object lookup(Name name) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public void bind(Name name, Object obj) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public void bind(String name, Object obj) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public void rebind(Name name, Object obj) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public void rebind(String name, Object obj) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public void unbind(Name name) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public void unbind(String name) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public void rename(Name oldName, Name newName) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public void rename(String oldName, String newName) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public void destroySubcontext(Name name) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public void destroySubcontext(String name) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public Context createSubcontext(Name name) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public Context createSubcontext(String name) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public Object lookupLink(Name name) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public Object lookupLink(String name) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public NameParser getNameParser(Name name) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public NameParser getNameParser(String name) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public Name composeName(Name name, Name prefix) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public String composeName(String name, String prefix) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public Object addToEnvironment(String propName, Object propVal) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public Object removeFromEnvironment(String propName) throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public Hashtable<?, ?> getEnvironment() throws NamingException {
        throw new MethodNotImplementedException() ;
    }

    public String getNameInNamespace() throws NamingException {
        throw new MethodNotImplementedException() ;
    }
}
