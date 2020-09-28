package ru.ecom.web.util;

import ru.ecom.web.login.LoginInfo;
import ru.nuzmsh.util.StringUtil;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 */
public class Injection {

    private static final ThreadLocal<HashMap<String, Object>> THREAD_SERVICES = new ThreadLocal<>();
    private static String KEY;

    private Injection(String aWebName, String aAppName, String aProviderUrl, LoginInfo aLoginInfo, String aInitialContextFactory, String aSecurityProtocol) {
        theAppName = aAppName;
        theWebName = aWebName;
        KEY = Injection.class.getName()+aWebName ;
        Properties env = new Properties();
        // java.naming.factory.initial
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                !StringUtil.isNullOrEmpty(aInitialContextFactory)
                        ? aInitialContextFactory
                        : "org.jboss.security.jndi.LoginInitialContextFactory");
//                        : "org.jboss.security.jndi.JndiLoginInitialContextFactory");
//                , "org.jnp.interfaces.NamingContextFactory") ;
        // java.naming.factory.url.pkgs
        env.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        // java.naming.provider.url
        env.put(Context.PROVIDER_URL, aProviderUrl);

        env.put(Context.SECURITY_PROTOCOL, aSecurityProtocol);
        if(aLoginInfo==null){
            env.put(Context.SECURITY_PRINCIPAL, "");
            env.put(Context.SECURITY_CREDENTIALS, "");
        }else {
            env.put(Context.SECURITY_PRINCIPAL, aLoginInfo.getUsername());
            env.put(Context.SECURITY_CREDENTIALS, aLoginInfo.getPassword());
        }
//        theInitialContext = new InitialContext(env);
        theEnv = env;
    }

    public static String getWebName(HttpServletRequest aRequest, String aWebName) {
        String path = aRequest.getContextPath();
        if (path ==null||path.equals("")) {aWebName="riams";}
        if (aWebName==null || aWebName.equals("")) {aWebName=path;}

        return aWebName ;
    }

    public static Properties loadAppProperties(String aWebName) throws IOException {
        Properties prop = new Properties();

        String catalinaBase = System.getProperty("catalina.base", ".");
        File filePropXml = new File(catalinaBase + "/ecom/" + aWebName + ".xml");
        File fileProp = new File(catalinaBase + "/ecom/" + aWebName + ".properties");
        if (filePropXml.exists() && fileProp.exists()) {
            throw new IllegalStateException("Сразу два файла с настроками доступны :"
                    + filePropXml.getAbsolutePath() + " и " + fileProp.getAbsolutePath()
                    + ". Нужно удалить ненужный.");
        }


        if (filePropXml.exists()) {
            try (FileInputStream in = new FileInputStream(filePropXml)) {
                prop.loadFromXML(in);
            }
        } else if (fileProp.exists()) {
            try (FileInputStream in = new FileInputStream(fileProp)) {
                prop.load(in);
            }
        } else {
            throw new IllegalStateException("Нет файла с настройками: " + filePropXml.getAbsolutePath()
                    + " или " + fileProp.getAbsolutePath());
        }
        return prop;
    }

    public static Injection find(HttpServletRequest aRequest) {
        return find(aRequest,null);
    }
    public static String getKeyDefault(HttpServletRequest aRequest, String aWebName) {
        aWebName = getWebName(aRequest, aWebName) ;
        return Injection.class.getName()+aWebName ;
    }
public static Injection find (ServletContextEvent contextEvent, String aWebName ) {
    return find(contextEvent.getServletContext(),aWebName);
}
    public static Injection find (ServletRequestEvent contextEvent, String aWebName ) {
        return find(contextEvent.getServletContext(),aWebName);
    }
    public static Injection find(ServletContext servletContext, String aWebName ) {

        Injection injection = (Injection) servletContext.getAttribute(Injection.class.getName()+aWebName);
        try {
            Properties prop = loadAppProperties(aWebName);
            if (injection == null) {
                try {
                    injection = new Injection(aWebName, prop.getProperty("ejb-app-name")
                            , prop.getProperty("java.naming.provider.url")
                            ,null
                            , prop.getProperty("java.naming.factory.initial")
                            , prop.getProperty("java.naming.security.protocol", "other")
                    );
                } catch (Exception e) {
                    throw new IllegalStateException("Ошибка подключение к серверу: " + e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка настройки приложения: " + e.getMessage(), e);
        }
        return injection;
    }

    public static Injection find(HttpServletRequest aRequest, String aWebName) {
        aWebName = getWebName(aRequest, aWebName) ;
        Injection injection = (Injection) aRequest.getSession().getAttribute(getKeyDefault(aRequest,aWebName));
        try {
            Properties prop = loadAppProperties(aWebName);
            if (injection == null) {
                HttpSession session = aRequest.getSession();
                if (session == null) throw new IllegalStateException("Нет сессии");
                LoginInfo loginInfo = LoginInfo.find(session);
                if (loginInfo == null) throw new IllegalStateException("Нет информации о пользователе");
                    injection = new Injection(aWebName, prop.getProperty("ejb-app-name")
                            , prop.getProperty("java.naming.provider.url")
                            , loginInfo
                            , prop.getProperty("java.naming.factory.initial")
                            , prop.getProperty("java.naming.security.protocol", "other")
                    );
                    aRequest.getSession().setAttribute(KEY, injection);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка настройки приложения: " + e.getMessage(), e);
        }
        return injection;
    }

    /**
     * Убираем из сессии и очищаем
     */
    public static void removeFromSession(HttpSession aSession) {
        if(aSession!=null) {
            Injection injection = (Injection) aSession.getAttribute(KEY);
            if(injection!=null) {
                if(injection.theEnv!=null) {
                    injection.theEnv.clear() ;
                }
                aSession.removeAttribute(KEY);
            }
        }
    }


    public Object getService(String aServiceName) throws NamingException {
        Object service ;
        HashMap<String,Object> services = THREAD_SERVICES.get();
        if(services==null) {
            services = new HashMap<>() ;
            THREAD_SERVICES.set(services);
            service = null ;
        } else {
            service = services.get(aServiceName+theWebName);
        }
        if(service==null) {
            InitialContext initialContext = new InitialContext(theEnv);
            try {
                String serviceUrl = theAppName+"/"+aServiceName+"Bean/remote";
                service = initialContext.lookup(serviceUrl);
                services.put(aServiceName+theWebName, service);
            } finally {
                initialContext.close() ;
            }
        }
        return service ;
    }

    public <T> T getService(Class<T> aServiceClass) throws NamingException {
        String className = aServiceClass.getSimpleName();
        return (T) getService(className.substring(1));
    }

    public static void clearThreadLocalServices() {
        THREAD_SERVICES.remove();
    }
    public String getWebName() {return theWebName ;}
    private final String theWebName;
    private final String theAppName;
    private final Properties theEnv;
}