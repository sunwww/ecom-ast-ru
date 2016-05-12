package ru.ecom.miniejb;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.ecom.miniejb.impl.EjbHash;
import ru.nuzmsh.util.StringUtil;

/**
 * Регистрация сервисов
 */
public class ServiceRegister {

    private final static Log LOG = LogFactory.getLog(ServiceRegister.class);
    private final static boolean CAN_TRACE = LOG.isTraceEnabled();


    public ServiceRegister(String aAppName, InitialContext aContext, IProxyCreator aLocalProxyCreator, IProxyCreator aRemoteProxyCreator) {
        theInitialContext = aContext;
        theLocalProxyCreator = aLocalProxyCreator;
        theRemoteProxyCreator = aRemoteProxyCreator;
        theAppname = aAppName;
    }

    public void registerAppContext() throws NamingException {
        theInitialContext.createSubcontext(theAppname);
    }

    public void register(Class aServiceClass) throws NamingException {
        LOG.info("Registering " + aServiceClass.getSimpleName() + "...");
        Local local = (Local) aServiceClass.getAnnotation(Local.class);
        if (local != null) {
            register(aServiceClass, "local", local.value(), theLocalProxyCreator, true);
        }
        Remote remote = (Remote) aServiceClass.getAnnotation(Remote.class);
        if (remote != null) {
            register(aServiceClass, "remote", remote.value(), theRemoteProxyCreator, false);
        }
    }

    private void register(Class aServiceClass, String aSuffix, Class[] aInterfaceClass, IProxyCreator aProxyCreator, boolean aCanRegistInterfaces) throws NamingException {
        String jndiParent = createServiceJndi(aServiceClass);
        boolean isJndiParentRegistered = false;

        try {
            if (CAN_TRACE) LOG.trace("jndiParent = " + jndiParent);
            theInitialContext.lookup(jndiParent);
        } catch (Exception e) {
            if (CAN_TRACE) LOG.trace("Registering subcontext " + jndiParent + " ...");
            theInitialContext.createSubcontext(jndiParent);
            isJndiParentRegistered = true;
        }
        String jndi = createServiceJndi(aServiceClass, aSuffix);
        if (CAN_TRACE) LOG.trace("Registering " + jndi + " ...");
        Object proxy = aProxyCreator.createProxyService(aServiceClass, aInterfaceClass, theInterfacesHash);
        theInitialContext.bind(jndi, proxy);

        theRegisterJndis.add(jndi);
        if (isJndiParentRegistered) theRegisterJndis.add(jndiParent);

        for (Class interfaze : aInterfaceClass) {
            theInterfacesHash.put(interfaze, jndi);
        }

    }

    private String createServiceJndi(Class aServiceClass, String aSuffix) {
        StringBuilder sb = new StringBuilder();
        sb.append(theAppname);
        sb.append("/");
        sb.append(aServiceClass.getSimpleName());
        sb.append("/");
        sb.append(aSuffix);
        return sb.toString();
    }

    private String createServiceJndi(Class aServiceClass) {
        StringBuilder sb = new StringBuilder();
        sb.append(theAppname);
        sb.append("/");
        sb.append(aServiceClass.getSimpleName());
        return sb.toString();
    }


    public void unregisterAll() {
        for (String jndi : theRegisterJndis) {
            try {
                theInitialContext.unbind(jndi);
            } catch (Exception e) {
                LOG.error("Ошибка удаления " + jndi, e);
            }
        }
        try {
            theInitialContext.unbind(theAppname);
        } catch (Exception e) {
            LOG.error("Ошибка удаления " + theAppname, e);
        }
    }

    public void findServicesAndRegisterIt() throws IOException, ClassNotFoundException, NamingException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String file = "/META-INF/miniejbservices.txt";
        InputStream resourceIn = loader.getResourceAsStream(file);
        if (resourceIn == null) throw new IllegalArgumentException("Нет файла " + file);
        try {
            LineNumberReader in = new LineNumberReader(new InputStreamReader(resourceIn));
            String line;
            while ((line = in.readLine()) != null) {
                if (!StringUtil.isNullOrEmpty(line)) register(loader.loadClass(line));
            }
        } finally {
            resourceIn.close();
        }
    }

    private final ArrayList<String> theRegisterJndis = new ArrayList<String>();
    private final InitialContext theInitialContext;
    private final IProxyCreator theLocalProxyCreator;
    private final IProxyCreator theRemoteProxyCreator;
    private final String theAppname;
    private final EjbHash theInterfacesHash = new EjbHash();

}
