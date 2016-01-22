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

	public static String setViewed (String aId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		String sql = "update Claim set viewDate = current_date, viewTime = current_time, operatorUsername='"+login+"' where id = "+aId;
		System.out.println(sql);
		service.executeUpdateNativeSql(sql);
		return aId;
	}
	
	public static String setStartClaim(String aId, String aDate, String aTime, String aExecutorLogin, HttpServletRequest aRequest) throws NamingException {
		if (aExecutorLogin==null||aExecutorLogin.equals("")) {
			throw new IllegalDataException ("Не указан исполнитель");
		}
		
		if (aDate!=null&&!aDate.equals("")) {
			aDate = "to_date('dd.MM.yyyy','"+aDate+"')";
		} else {
			aDate = "current_date";
		}
		if (aTime!=null&&!aTime.equals("")) {
			aTime = "to_time('HH:MI','"+aTime+"')";
		} else {
			aTime = "current_time";
		}
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		
		String sql = "update Claim set startWorkDate = "+aDate+", startWorkTime = "+aTime+", executorUsername = '"+aExecutorLogin+"' where id="+aId;
		System.out.println("===== "+sql);
		service.executeUpdateNativeSql(sql);
		return aId;
	}
}