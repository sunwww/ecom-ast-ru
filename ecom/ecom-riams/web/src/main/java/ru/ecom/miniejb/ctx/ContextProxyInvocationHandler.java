package ru.ecom.miniejb.ctx;

import org.apache.log4j.Logger;
import ru.ecom.miniejb.ThreadLocalContextManager;
import ru.ecom.miniejb.impl.SimpleSessionContext;

import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Вызов метода сервиса.
 */
public class ContextProxyInvocationHandler implements InvocationHandler {

    private final static Logger LOG = Logger.getLogger(ContextProxyInvocationHandler.class);
    private final static boolean CAN_TRACE = LOG.isDebugEnabled();


    private final String theUsername;
    private final Set<String> theRoles;
    private final String theJdni;

    public ContextProxyInvocationHandler(String aUsername, Set<String> aRoles, String aJdni) {
        theUsername = aUsername;
        theRoles = aRoles;
        theJdni = aJdni;
    }

    public Object invoke(Object proxy, Method aMethod, Object[] args) throws Throwable {
        if (CAN_TRACE) LOG.info("BEFORE: totalMemory() = " + Runtime.getRuntime().totalMemory());
        LOG.info(" thread = "+Thread.currentThread().getId()) ;
        ThreadLocalContextManager.start();
        try {
            InitialContext context = new InitialContext();
            ThreadLocalContextManager.setSessionContext(getSessionContext());
            Object service = context.lookup(theJdni);
            Class clazz = service.getClass();
            Method method = clazz.getMethod(aMethod.getName(), aMethod.getParameterTypes());
            return method.invoke(service, args);
        } finally {
            ThreadLocalContextManager.stop();
        }
    }

    private SessionContext getSessionContext() throws IOException {
        LOG.info("Creating new SessionContext for "+theUsername+" ...");
        FileInputStream in = new FileInputStream(new File(System.getProperty("jboss.server.config.dir"),"roles.properties"));
        try {
            Properties prop = new Properties();
            prop.load(in);
            String roles = prop.getProperty(theUsername) ;
            HashSet<String> set = new HashSet<String>();
            if(roles!=null) {
                StringTokenizer st = new StringTokenizer(roles,", \t\n");
                while(st.hasMoreTokens()) {
                    String policy = st.nextToken() ;
                    set.add(policy) ;
                }
            }
            return new SimpleSessionContext(theUsername, set);
        } finally{
            in.close() ;
        }
    }
}

