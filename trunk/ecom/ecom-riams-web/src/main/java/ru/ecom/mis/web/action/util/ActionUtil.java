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
	public static void setParameterFilterSql(String aParameter,String aFldId,HttpServletRequest aRequest) {
		setParameterFilterSql(aParameter, aParameter, aFldId, aRequest) ;
	}
	public static void setParameterFilterSql(String aParameter,String aAttributeName,String aFldId,HttpServletRequest aRequest) {
		if (aAttributeName==null) aAttributeName=aParameter ;
		String param = (String)aRequest.getParameter(aParameter) ;
    	if (param!=null && !param.equals("") && !param.equals("0")) {
    		aRequest.setAttribute(aAttributeName+"SqlId", "'&"+aParameter+"="+param+"'") ;
    		aRequest.setAttribute(aAttributeName+"Sql", " and "+aFldId+"="+param) ;
    		aRequest.setAttribute(aAttributeName,param) ;
    	} else {
    		aRequest.setAttribute(aAttributeName,"0") ;
    		aRequest.setAttribute(aAttributeName+"SqlId", "''") ;
    	}
	}
	public static void setLikeSql(String aParameter,String aFldId,HttpServletRequest aRequest) {
		setLikeSql(aParameter, aParameter, aFldId, aRequest) ;
	}
	public static void setLikeSql(String aParameter,String aAttributeName,String aFldId,HttpServletRequest aRequest) {
		if (aAttributeName==null) aAttributeName=aParameter ;
		String param = (String)aRequest.getParameter(aParameter) ;
    	if (param!=null && !param.equals("") && !param.equals("0")) {
    		aRequest.setAttribute(aAttributeName+"SqlId", "'&"+aParameter+"="+param+"'") ;
    		aRequest.setAttribute(aAttributeName+"Sql", " and "+aFldId+" like '%"+param+"%'") ;
    		aRequest.setAttribute(aAttributeName,param) ;
    	} else {
    		aRequest.setAttribute(aAttributeName,"0") ;
    		aRequest.setAttribute(aAttributeName+"SqlId", "''") ;
    	}
	}
	public static void setGroupSql(String aNameFld,HttpServletRequest aRequest) {
			aRequest.setAttribute(aNameFld+"Sql", "to_char(v.dateStart,'dd.mm.yyyy')") ;
			aRequest.setAttribute(aNameFld+"SqlId", "'&beginDate='||to_char(v.dateStart,'dd.mm.yyyy')||'&finishDate='||to_char(v.dateStart,'dd.mm.yyyy')") ;
			aRequest.setAttribute(aNameFld+"Name", "Дата") ;
			aRequest.setAttribute(aNameFld+"Group", "v.dateStart") ;
			aRequest.setAttribute(aNameFld+"Order", "v.dateStart") ;
		
		
	}

}
