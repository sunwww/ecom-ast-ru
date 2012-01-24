package ru.ecom.mis.web.action.util;

import javax.servlet.http.HttpServletRequest;

public class ActionUtil {
	public static String updateParameter(String aSession, String aNameParameter, String aDefaultValue ,HttpServletRequest aRequest) {
		
		String typePat = "" ;
		if (aRequest.getParameter(aNameParameter)!=null) {
			typePat = aRequest.getParameter(aNameParameter).toString() ;
			
		} else {
			if (aRequest.getSession(true).getAttribute(aSession+"."+aNameParameter) !=null) {
				typePat = aRequest.getSession(true).getAttribute(aSession+"."+aNameParameter).toString() ;
			} else {
				typePat=aDefaultValue ;
			}
		}
		aRequest.getSession(true).setAttribute(aSession+"."+aNameParameter, typePat) ;
		aRequest.setAttribute(aNameParameter, typePat) ;		
		return typePat ;
	}

}
