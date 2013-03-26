package ru.ecom.mis.web.action.util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.query.WebQueryResult;

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
	public static String setParameter(String aSession, String aNameParameter, String aValue ,HttpServletRequest aRequest) {
		
		aRequest.getSession(true).setAttribute(aSession+"."+aNameParameter, aValue) ;
		aRequest.setAttribute(aNameParameter, aValue) ;		
		return aValue ;
	}
	public static void getValueByList(String aNameGet,String aNameSet,HttpServletRequest aRequest) {
		Collection<WebQueryResult> col = (Collection<WebQueryResult>)aRequest.getAttribute(aNameGet) ;
		aRequest.setAttribute(aNameSet,col.isEmpty()?"0":""+col.iterator().next().get1()) ;
	}

}
