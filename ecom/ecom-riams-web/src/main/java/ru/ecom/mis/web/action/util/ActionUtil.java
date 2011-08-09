package ru.ecom.mis.web.action.util;

import javax.servlet.http.HttpServletRequest;

public class ActionUtil {
	public static String updateParameter(String aSeesion, String aNameParameter, String aDefaultValue ,HttpServletRequest aRequest) {
		
		String typePat = "" ;
		if (aRequest.getParameter(aNameParameter)!=null) {
			typePat = aRequest.getParameter(aNameParameter).toString() ;
			
		} else {
			if (aRequest.getSession(true).getAttribute(aSeesion+"."+aNameParameter) !=null) {
				typePat = aRequest.getSession(true).getAttribute(aSeesion+"."+aNameParameter).toString() ;
			} else {
				typePat=aDefaultValue ;
			}
		}
		aRequest.getSession(true).setAttribute(aSeesion+"."+aNameParameter, typePat) ;
		aRequest.setAttribute(aNameParameter, typePat) ;		
		return typePat ;
	}

}
