package ru.ecom.web.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.tags.helper.RolesHelper;

public class ActionUtil {
	 public static String getContentOfHTTPPage(String pageAddress, String codePage) throws Exception {
	        StringBuilder sb = new StringBuilder();
	        URL pageURL = new URL(pageAddress);
	        URLConnection uc = pageURL.openConnection();
	        BufferedReader br = new BufferedReader(
	                new InputStreamReader(
	                        uc.getInputStream(), codePage));
	        try {
	            String inputLine;
	            while ((inputLine = br.readLine()) != null) {
	                sb.append(inputLine);
	            }         
	        } finally {
	        	br.close();
	        }
	        return sb.toString();
	    }
	public static WebQueryResult getElementArrayByCode(String aCode,String aAttribList,HttpServletRequest aRequest) {
		Collection<WebQueryResult> col = (Collection<WebQueryResult>)aRequest.getAttribute(aAttribList) ;
		//System.out.println(" --- code="+aCode);
		WebQueryResult wqr = null ;
		if (!col.isEmpty()) for (WebQueryResult w:col) {
			String code = ""+w.get1() ;
			//System.out.println("id="+code+" --- code="+aCode);
			if (code.indexOf(aCode)!=-1) {
				wqr = w ;
				break ;
			}
		}
		return wqr ;
	}
	
	public static String isReportBase(String aBeginDate,String aEndDate,HttpServletRequest aRequest) {
		String isRepBase = "true" ;
		try {
			if (!RolesHelper.checkRoles("/Policy/Config/IsReportBase", aRequest)) {
				isRepBase="false" ;
			} else {
				try {
					java.util.Date d1 = DateFormat.parseDate(aBeginDate) ;
					Calendar c1 = Calendar.getInstance() ;c1.setTime(d1) ;
					c1.set(Calendar.AM_PM, 0) ;
					java.util.Date d2 = DateFormat.parseDate(aEndDate) ;
					Calendar c2 = Calendar.getInstance() ;c2.setTime(d2) ;
					c2.set(Calendar.AM_PM, 0) ;
					java.util.Date d3 = new java.sql.Date(new java.util.Date().getTime()) ;
					Calendar c3 = Calendar.getInstance() ;c3.setTime(d3) ;
					c3.set(Calendar.SECOND, 0) ;
					c3.set(Calendar.HOUR_OF_DAY, 0) ;
					c3.set(Calendar.MINUTE, 0) ;
					c3.set(Calendar.MILLISECOND, 0) ;
					c1.add(Calendar.DAY_OF_MONTH, 7) ;
					if (c2.getTime().getTime() == c3.getTime().getTime() && c1.after(c3)) {
						isRepBase = "false";
					}
				} catch (ParseException e) {
					
				}
			}
		} catch (JspException e1) {
			e1.printStackTrace();
		}
		
		return isRepBase ;
	}
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
	public static void getValueByListDef(String aNameGet,String aNameSet,String aDefault,HttpServletRequest aRequest) {
		Collection<WebQueryResult> col = (Collection<WebQueryResult>)aRequest.getAttribute(aNameGet) ;
		aRequest.setAttribute(aNameSet,col.isEmpty()?aDefault:""+col.iterator().next().get1()) ;
	}
	
	public static void getValueBySql(String aSql,String aFieldId, String aFieldName, HttpServletRequest aRequest) {
		IWebQueryService service;
		try {
			service = Injection.find(aRequest).getService(IWebQueryService.class);
			Collection<WebQueryResult> col = service.executeNativeSql(aSql,1) ;
			if (!col.isEmpty()) {
				WebQueryResult obj = col.iterator().next() ;
				aRequest.setAttribute(aFieldId, obj.get1()) ;
				aRequest.setAttribute(aFieldName, obj.get2()) ;
			} else {
				
			}
		} catch (NamingException e) {
			aRequest.setAttribute(aFieldId, "") ;
			aRequest.setAttribute(aFieldName, "ОШИБКА SQL: "+aSql) ;
			e.printStackTrace();
		}
		
	}
	public static String getValueInfoById(String aSql, String aParameter, String aFldId, HttpServletRequest aRequest) {
		return getValueInfoById(aSql, null, aParameter, aParameter, aFldId, aRequest) ;
	}
	public static String getValueInfoById(String aSql, String aTitle, String aParameter, String aFldId, HttpServletRequest aRequest) {
		return getValueInfoById(aSql, aTitle, aParameter, aParameter, aFldId, aRequest) ;
	}
	public static String getValueInfoById(String aSql,String aTitle, String aParameter, String aAttributeName, String aFldId, HttpServletRequest aRequest) {
		IWebQueryService service;
		String sql ="" ;
		try {
			if (aAttributeName==null) aAttributeName=aParameter ;
			String param = (String)aRequest.getParameter(aParameter) ;
			
	    	if (param!=null && !param.equals("") && !param.equals("0")) {
	    		service = Injection.find(aRequest).getService(IWebQueryService.class);
	    		aSql = aSql.replaceAll(":id", param) ;
				Collection<WebQueryResult> col = service.executeNativeSql(aSql,1) ;
				if (!col.isEmpty()) {
					WebQueryResult obj = col.iterator().next() ;
					String title = aTitle!=null && !aTitle.equals("")?aTitle+": ":"" ;
					aRequest.setAttribute(aAttributeName+"Info", title+obj.get2()) ;
				} else {
					
				}
	    		aRequest.setAttribute(aAttributeName+"SqlId", "'&"+aParameter+"="+param+"'") ;
	    		sql=" and "+aFldId+"='"+param+"'";
	    		aRequest.setAttribute(aAttributeName+"Sql", sql) ;
	    		aRequest.setAttribute(aAttributeName,param) ;
		    	
	    	} else {
	    		aRequest.setAttribute(aAttributeName,"0") ;
	    		aRequest.setAttribute(aAttributeName+"SqlId", "''") ;
	    	}
    	
		} catch (NamingException e) {
			
		}
		return sql ;
			
		
		
	}
	
	public static String setParameterFilterSql(String aParameter,String aFldId,HttpServletRequest aRequest) {
		return setParameterFilterSql(aParameter, aParameter, aFldId, aRequest) ;
	}
	public static String setParameterFilterSql(String aParameter,String aAttributeName,String aFldId,HttpServletRequest aRequest) {
		if (aAttributeName==null) aAttributeName=aParameter ;
		String param = (String)aRequest.getParameter(aParameter) ;
		String sql ="" ;
    	if (param!=null && !param.equals("") && !param.equals("0")) {
    		aRequest.setAttribute(aAttributeName+"SqlId", "'&"+aParameter+"="+param+"'") ;
    		sql=" and "+aFldId+"='"+param+"'";
    		aRequest.setAttribute(aAttributeName+"Sql", sql) ;
    		aRequest.setAttribute(aAttributeName,param) ;
    	} else {
    		aRequest.setAttribute(aAttributeName,"0") ;
    		aRequest.setAttribute(aAttributeName+"SqlId", "''") ;
    	}
    	return sql ;
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
