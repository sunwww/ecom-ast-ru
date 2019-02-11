package ru.ecom.miniejb;

import org.apache.log4j.Logger;
import ru.ecom.miniejb.impl.LocalProxyCreator;
import ru.ecom.miniejb.impl.RemoteProxyCreator;
import ru.nuzmsh.util.StringUtil;

import javax.naming.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.*;
import java.io.IOException;

/**
 * Инициализация InitialContext
 *
 */
public class MiniEjbFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(MiniEjbFilter.class) ;
    private static final boolean CAN_TRACE = LOG.isDebugEnabled() ;


    private static String getConfigParam(FilterConfig aFilterConfig, String aName) {
        String value = aFilterConfig.getInitParameter(aName) ;
        if(StringUtil.isNullOrEmpty(value)) {
            throw new IllegalArgumentException("Нет параметра "+aName) ;
        }
        return value ;
    }


    public void init(FilterConfig aFilterConfig) throws ServletException {
        LOG.info("Регистрация сервисов");


        try {

            theFactory = Persistence.createEntityManagerFactory("mainManager") ;

            theInitialContext = new InitialContext();

            theLocalProxyCreator = new LocalProxyCreator();
            theRemoteProxyCreator = new RemoteProxyCreator(theFactory) ;

            theServiceRegister = new ServiceRegister(getConfigParam(aFilterConfig, "appname"), theInitialContext, theLocalProxyCreator, theRemoteProxyCreator);

            printContext("Перед инициализацией");
            theServiceRegister.registerAppContext();

            theServiceRegister.findServicesAndRegisterIt();

            printContext("После инициализации");

        } catch (Exception e) {
            registerException("",e);
        }
    }
    private void registerException(String aMessage, Exception e) {
        LOG.error(aMessage,e) ;
        theException = e ;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(theException!=null) {
            theException.printStackTrace(servletResponse.getWriter());
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }


    private void printContext(String aStr) {
        if (CAN_TRACE) LOG.debug("Печать " + aStr);
        printContext(" ", theInitialContext) ;

    }
    private void printContext(String aStr, Context aContext) {
        try {
            if(aContext==null) return ;
            NamingEnumeration<NameClassPair> en = aContext.list("") ;
            while (en.hasMoreElements()) {
                NameClassPair pair = en.nextElement();
                if (CAN_TRACE) LOG.debug(aStr+"  pair  " + pair.getName()+" "+pair.getClassName());
                Object obj = aContext.lookup(pair.getName()) ;
                if(obj instanceof Context) {
                    printContext(aStr+" ", (Context) obj) ;
                }
            }
        } catch (NamingException e) {
            LOG.error("Ошибка печати контекста",e) ;
        }
    }
    public void destroy() {
        printContext("Перед удалением");
        //theInitialContext.
        LOG.info("Удаление сервисов") ;
        theLocalProxyCreator = null ;
        if(theServiceRegister!=null) theServiceRegister.unregisterAll();
        printContext("После удалением");
        try {
            if(theInitialContext!=null) theInitialContext.close() ;
        } catch(NamingException e) {
            LOG.error("Ошибка закрытия InitialContext",e) ;
        }
        if(theFactory!=null) theFactory.close();
    }

    private InitialContext theInitialContext ;
    private IProxyCreator theLocalProxyCreator ;
    private IProxyCreator theRemoteProxyCreator ;
    private ServiceRegister theServiceRegister ;
    private EntityManagerFactory theFactory ;
    private Exception theException ;

}
