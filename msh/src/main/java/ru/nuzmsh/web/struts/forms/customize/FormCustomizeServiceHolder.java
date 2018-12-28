package ru.nuzmsh.web.struts.forms.customize;

import org.apache.log4j.Logger;

import javax.naming.InitialContext;

/**
 *
 */
public class FormCustomizeServiceHolder {

    private static final String KEY = "IFormCustomizeService" ;

	private static final Logger LOG = Logger.getLogger(FormCustomizeServiceHolder.class) ;
    private static final boolean CAN_TRACE = LOG.isDebugEnabled() ;

    private static IFormCustomizeService SERVICE = null ;

    public static void putService(IFormCustomizeService aService) {
        if (CAN_TRACE) LOG.info("Putting service ("+KEY+") " + aService);
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
//        if (CAN_TRACE) LOG.info("Getting service ("+KEY+") " + service);
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
