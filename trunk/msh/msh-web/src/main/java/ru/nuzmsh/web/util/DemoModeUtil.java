package  ru.nuzmsh.web.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ru.nuzmsh.web.tags.helper.RolesHelper;



public class DemoModeUtil {

	private final static Logger LOG = Logger.getLogger(DemoModeUtil.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	 
	public static boolean isInDemoMode(HttpServletRequest aRequest) {
		try {
			return RolesHelper.checkRoles("/Policy/DEMO", aRequest);
		} catch (Exception e) {
			return false ;
		}
	}
	
	public static Object secureValue(Object value) {
  	  // Для ДЕМО-режима
        try {
        	if(value!=null && value instanceof String) {
        		String str = value.toString();
        		if(str.length()>2) {
        			value = str.substring(0,3)+"...";
        		}
            	
        	}
        } catch (Exception e) {
        	LOG.warn("Ошибка при удалении символов в DEMO режиме [value="+value+"]", e);
        }                	
    	return value ;
	}
}
