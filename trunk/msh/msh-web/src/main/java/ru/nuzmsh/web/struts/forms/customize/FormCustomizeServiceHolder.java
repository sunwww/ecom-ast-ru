package ru.nuzmsh.web.struts.forms.customize;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 */
public class FormCustomizeServiceHolder {

    private static final String KEY = "IFormCustomizeService" ;

    private final static Log LOG = LogFactory.getLog(FormCustomizeServiceHolder.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;

    private static IFormCustomizeService SERVICE = null ;

    public static void putService(IFormCustomizeService aService) {
        if (CAN_TRACE) LOG.trace("Putting service ("+KEY+") " + aService);
    	try {
    		InitialContext ctx = new InitialContext() ;
    		try {
    			ctx.bind(KEY, aService) ;
    		} finally {
    			ctx.close();
    		}
    	} catch (Exception e) {
    		if(CAN_TRACE) LOG.warn("Ошибка поиска сервиса "+KEY,e) ;
    	}
        SERVICE =  aService ;
    }

    public static IFormCustomizeService getService() {
    	if(SERVICE==null) {
        	try {
        		InitialContext ctx = new InitialContext() ;
        		try {
        			return (IFormCustomizeService) ctx.lookup(KEY) ;
        			//return null ;
        		} finally {
        			ctx.close();
        		}
        	} catch (Exception e) {
        		if(CAN_TRACE) LOG.warn("Ошибка поиска сервиса "+KEY,e) ;
        		return null ;
        	}
    	} else {
    		return SERVICE ;
    	}
    }

    public static void removeService() {
    	try {
    		InitialContext ctx = new InitialContext() ;
    		try {
    			ctx.unbind(KEY) ;
    		} finally {
    			ctx.close();
    		}
    	} catch (Exception e) {
    		throw new IllegalStateException(e) ;
    	}
    }

//    public static IFormCustomizeService getService(HttpServletRequest aRequest) {
//        IFormCustomizeService service = (IFormCustomizeService) aRequest.getSession().getAttribute(KEY) ;
//        if (CAN_TRACE) LOG.trace("Getting service ("+KEY+") " + service);
//        return service ;
//    }

//    public IFormCustomizeService findService(HttpServletRequest aRequest) {
//        IFormCustomizeService service = (IFormCustomizeService) aRequest.getSession().getAttribute(KEY) ;
//        if(service==null) {
//            throw new IllegalStateException("В сессии нет сервиса IFormCustomizeService ("+KEY+")") ;
//        }
//        return service ;
//    }
}
