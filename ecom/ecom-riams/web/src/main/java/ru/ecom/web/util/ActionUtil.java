package ru.ecom.web.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.tags.helper.RolesHelper;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

public class ActionUtil {
	
	public static boolean isCacheCurrentLpu(HttpServletRequest aRequest) throws NamingException {// Согласен, немного неправильно, но пока работает 
		try {
			IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
			String ver = service.executeNativeSql("select version()").iterator().next().get1().toString();
			if (ver.toLowerCase().contains("postgres")) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	} 
	public static List<Object[]> getListObjFromNativeQuery(String aSql, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
	//	System.out.println(aSql) ;
		return service.executeNativeSqlGetObj(aSql) ;
	}
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
					c1.add(Calendar.DAY_OF_MONTH, 1) ;
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
			//System.out.println(aParameter+"="+param) ;
	    	if (param!=null && !param.equals("") && !param.equals("0")) {
	    		service = Injection.find(aRequest).getService(IWebQueryService.class);
	    		aSql = aSql.replaceAll(":id", param) ;
				Collection<WebQueryResult> col = service.executeNativeSql(aSql,1) ;
				if (!col.isEmpty()) {
					WebQueryResult obj = col.iterator().next() ;
					String title = aTitle!=null && !aTitle.equals("")?aTitle+": ":"" ;
					aRequest.setAttribute(aAttributeName+"Info", title+obj.get2()) ;
				} else {
					aRequest.setAttribute(aAttributeName+"Info", "") ;
				}
	    		aRequest.setAttribute(aAttributeName+"SqlId", "'&"+aParameter+"="+param+"'") ;
	    		sql=" and "+aFldId+"='"+param+"'";
	    		aRequest.setAttribute(aAttributeName+"Sql", sql) ;
	    		aRequest.setAttribute(aAttributeName,param!=null?param:"") ;
	    		
		    	
	    	} else {
	    		aRequest.setAttribute(aAttributeName,"0") ;
	    		aRequest.setAttribute(aAttributeName+"SqlId", "''") ;
	    		aRequest.setAttribute(aAttributeName+"Info", "") ;
	    	}
    	
		} catch (NamingException e) {
			e.printStackTrace() ;
		}
		return sql ;
			
		
		
	}
	
	public static String setParameterFilterSql(String aParameter,String aFldId,HttpServletRequest aRequest) {
		return setParameterFilterSql(aParameter, aParameter, aFldId, aRequest) ;
	}
	public static String setParameterFilterMkb(String aParameter,String aAttributeName,String aFldId,HttpServletRequest aRequest) {
		String filter = (String)aRequest.getParameter(aParameter) ;
    	if (filter!=null && !filter.equals("")) {
    		filter = filter.toUpperCase() ;
    		
    		String[] fs1=filter.split(",") ;
    		StringBuilder filtOr = new StringBuilder() ;
    		
    		for (int i=0;i<fs1.length;i++) {
    			String filt1 = fs1[i].trim() ;
    			String[] fs=filt1.split("-") ;
    			if (filt1.length()>0) {
	    			if (filtOr.length()>0) filtOr.append(" or ") ;
		    		if (fs.length>1) {
		    			filtOr.append(" ").append(aFldId).append(" between '"+fs[0].trim()+"' and '"+fs[1].trim()+"'") ;
		    		} else {
		    			filtOr.append(" substring(").append(aFldId).append(",1,"+filt1.length()+") = '"+filt1+"'") ;
		    		}
    			}
    		}
    		
    		if (filtOr.length()>0) {
    			aRequest.setAttribute(aAttributeName+"SqlId", "'&"+aParameter+"='||"+aFldId+"||'") ;
    			aRequest.setAttribute(aAttributeName+"UrlId", "&"+aParameter+"="+filter+"") ;
    			filtOr.insert(0, " and (").append(")") ;
    			aRequest.setAttribute(aAttributeName+"Sql", filtOr.toString()) ;
    			aRequest.setAttribute(aAttributeName,filter) ;
    			return filtOr.toString() ;
    		}
    	} 
    	return "" ;
	}

	public static String setParameterManyFilterSql(String aParameter,String aAttributeName,String aFldId,HttpServletRequest aRequest) throws JSONException {
		if (aAttributeName==null) aAttributeName=aParameter ;
		String param = aRequest.getParameter(aParameter) ;
		String sql ="" ;
		if (param!=null) {
		JSONObject obj = new JSONObject(param) ;
		JSONArray childs = obj.getJSONArray("childs");
		StringBuilder sb = new StringBuilder() ;
		StringBuilder sb1 = new StringBuilder() ;
		sb1.append("%7B");
		sb1.append("&quot;childs&quot;%3A[") ;
		for (int i = 0; i < childs.length(); i++) {
			JSONObject child = (JSONObject) childs.get(i);
			if (sb.length()!=0) {
				sb.append(",") ;sb1.append(",");
			}
			sb.append(child.get("value")) ;
			sb1.append("%7B&quot;value&quot;%3A&quot;").append(child.get("value")).append("&quot;%7D") ;
		}
		sb1.append("]%7D") ;
		
		if (childs.length()>0 && sb.length()>0) {
			
			aRequest.setAttribute(aAttributeName+"UrlId", "&"+aParameter+"="+sb1.toString()+"") ;
			sql=" and "+aFldId+"  in ("+sb.toString()+")";
			aRequest.setAttribute(aAttributeName+"Sql", sql) ;
			aRequest.setAttribute(aAttributeName,param) ;
		} else {
			aRequest.setAttribute(aAttributeName,"") ;
			
		}
		} else {
			aRequest.setAttribute(aAttributeName,"") ;
			
		}
		aRequest.setAttribute(aAttributeName+"SqlId", "'&"+aParameter+"=%7B&quot;childs&quot;%3A[%7B&quot;value&quot;:&quot;'||"+aFldId+"||'&quot;%7D]%7D'") ;
		return sql ;
	}
	public static String setParameterFilterSql(String aParameter,String aAttributeName,String aFldId,HttpServletRequest aRequest) {
		if (aAttributeName==null) aAttributeName=aParameter ;
		String param = (String)aRequest.getParameter(aParameter) ;
		String sql ="" ;
		if (param!=null && param.equals("-1")) {
    		aRequest.setAttribute(aAttributeName+"SqlId", "'&"+aParameter+"="+param+"'") ;
    		sql=" and "+aFldId+" is null";
    		aRequest.setAttribute(aAttributeName+"Sql", sql) ;
    		aRequest.setAttribute(aAttributeName,param) ;
    	} else if (param!=null && !param.equals("") && !param.equals("0")) {
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
	public static void setUpperLikeSql(String aParameter,String aFldId,HttpServletRequest aRequest) {
		setUpperLikeSql(aParameter, aParameter, aFldId, aRequest) ;
	}
	public static void setUpperLikeSql(String aParameter,String aAttributeName,String aFldId,HttpServletRequest aRequest) {
		if (aAttributeName==null) aAttributeName=aParameter ;
		String param = aRequest.getParameter(aParameter) ;
		if (param!=null && !param.equals("") && !param.equals("0")) {
			aRequest.setAttribute(aAttributeName+"SqlId", "'&"+aParameter+"="+param+"'") ;
			aRequest.setAttribute(aAttributeName+"Sql", " and upper("+aFldId+") like upper('%"+param+"%')") ;
			aRequest.setAttribute(aAttributeName,param) ;
		} else {
			aRequest.setAttribute(aAttributeName,"0") ;
			aRequest.setAttribute(aAttributeName+"SqlId", "''") ;
		}
	}
	public static void setLikeSql(String aParameter,String aAttributeName,String aFldId,HttpServletRequest aRequest) {
		if (aAttributeName==null) aAttributeName=aParameter ;
		String param = aRequest.getParameter(aParameter) ;
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
	
	public static  String getDefaultParameterByConfig(String aParameter, String aValueDefault, IWebQueryService aService) {
		List<Object[]> l = aService.executeNativeSqlGetObj("select sf.id,sf.keyvalue from SoftConfig sf where  sf.key='"+aParameter+"'");
		if (l.isEmpty()) {
			return aValueDefault ;
		} else {
			return new StringBuilder().append(l.get(0)[1]).toString() ;
		}
	}
	public static  String getDefaultParameterByConfig(String aParameter, String aValueDefault, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		List<Object[]> l = service.executeNativeSqlGetObj("select sf.id,sf.keyvalue from SoftConfig sf where  sf.key='"+aParameter+"'");
		if (l.isEmpty()) {
			return aValueDefault ;
		} else {
			return new StringBuilder().append(l.get(0)[1]).toString() ;
		}
	}
	public static  String getDefaultDescriptionParameterByConfig(String aParameter, String aValueDefault, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		List<Object[]> l = service.executeNativeSqlGetObj("select sf.id,sf.description from SoftConfig sf where  sf.key='"+aParameter+"'");
		if (l.isEmpty()) {
			return aValueDefault ;
		} else {
			return new StringBuilder().append(l.get(0)[1]).toString() ;
		}
	}
}
