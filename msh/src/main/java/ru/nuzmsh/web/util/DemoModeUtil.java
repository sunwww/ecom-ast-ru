package ru.nuzmsh.web.util;

import org.apache.log4j.Logger;
import ru.nuzmsh.web.tags.helper.RolesHelper;

import javax.servlet.http.HttpServletRequest;



public class DemoModeUtil {

	private static final Logger LOG = Logger.getLogger(DemoModeUtil.class);

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
        	if(value instanceof String) {
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
