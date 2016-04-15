package ru.ecom.mis.web.dwr.claim;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.jdom.IllegalDataException;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;

/**
 * 
 * @author vtsybulin
 * 01.2016
 * Сервис для работы с заявками в тех. поддержку
 *
 */
public class ClaimServiceJs {

	public static String setComment (String aId, String comment, HttpServletRequest aRequest) throws NamingException {
		
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		return ""+service.executeUpdateNativeSql("update claim set executorcomment = '"+comment+"' where id="+aId);
	}
	public static String setStatusClaim (String aStatus, String aId,String aDate, String aTime, String aUsername, String aComment, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		if (aStatus==null) return "Не указан статус";
		if (!aStatus.toUpperCase().equals("FINISH")&&(aUsername==null||aUsername.equals(""))) {
			aUsername = LoginInfo.find(aRequest.getSession(true)).getUsername();
		}
			
		if (aDate!=null&&!aDate.equals("")) {
			aDate = "to_date('"+aDate+"', 'dd.MM.yyyy')";
		} else {
			aDate = "current_date";
		}
		if (aTime!=null&&!aTime.equals("")) {
			aTime = "cast('"+aTime+"' as time)";
		} else {
			aTime = "current_time";
		}
		String sql = "update claim set "+aStatus+"Date = "+aDate;
			sql+=", "+aStatus+"Time =" +aTime;
			
			if (aComment!=null&&!aComment.equals("")) {
				sql+=", executorComment='"+aComment +"'";
				
			}
			if (aUsername!=null&&!aUsername.equals("")) {
				sql+=", "+aStatus+"Username='"+aUsername+"'";
						
			} else if (aStatus.toUpperCase().equals("FINISH")) {
				sql +=", finishUsername = startWorkUsername";
			}
			sql+=" where id="+aId;
			
			return aStatus+" : "+ service.executeUpdateNativeSql(sql);
		
	}

	public static String setViewed (String aId, HttpServletRequest aRequest) throws NamingException {
		return setStatusClaim("View", aId, null, null, null, null, aRequest);
	}
	
	public static String setStartClaim(String aId, String aDate, String aTime, String aExecutorLogin, HttpServletRequest aRequest) throws NamingException {
		if (aExecutorLogin==null||aExecutorLogin.equals("")) {
			throw new IllegalDataException ("Не указан исполнитель");
		}
		
		if (aDate!=null&&!aDate.equals("")) {
			aDate = "to_date('"+aDate+"', 'dd.MM.yyyy')";
		} else {
			aDate = "current_date";
		}
		if (aTime!=null&&!aTime.equals("")) {
			aTime = "cast('"+aTime+"' as time)";
		} else {
			aTime = "current_time";
		}
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		
		String sql = "update Claim set startWorkDate = "+aDate+", startWorkTime = "+aTime+", startworkUsername = '"+aExecutorLogin+"' where id="+aId;
		System.out.println("===== "+sql);
		service.executeUpdateNativeSql(sql);
		return aId;
	}
}