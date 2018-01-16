package ru.ecom.mis.web.dwr.claim;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.jdom.IllegalDataException;

import ru.ecom.diary.ejb.service.template.ITemplateProtocolService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;

import java.util.List;

/**
 * 
 * @author vtsybulin
 * 01.2016
 * Сервис для работы с заявками в тех. поддержку
 *
 */
public class ClaimServiceJs {
	public static String sendToUserConfirm (String aId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		return ""+service.executeUpdateNativeSql("update claim set completeconfirmed = null where id="+aId);
	}
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
				sql +=", finishUsername = startWorkUsername, canceldate=null, cancelusername=null";
			}
			if (!aStatus.toUpperCase().equals("FREEZE")) {
				sql +=", freezeDate = null, freezeusername = null";
			}
			sql+=" where id="+aId;
			if (aStatus.toUpperCase().equals("STARTWORK")) {
				sendClaimToExecutorBySMS(aId,aUsername,aRequest,service);
			}
			
			return aStatus+" : "+ service.executeUpdateNativeSql(sql);
		
	}
	private static void sendClaimToExecutorBySMS(String aClaimId, String aUsername, HttpServletRequest aRequest, IWebQueryService aService) throws NamingException {
		String sql = "select pesa.phoneNumber,pesa.id from secuser su left join workfunction wf on wf.secuser_id=su.id " +
				" left join worker w on w.id=wf.worker_id left join PatientExternalServiceAccount pesa on pesa.patient_id=w.person_id " +
				" where su.login='"+aUsername+"' and pesa.newClaimNotification='1' ";
		List<Object[]> list = aService.executeNativeSqlGetObj(sql);
		if (!list.isEmpty()) {
			String phone = list.get(0)[0].toString();
			sql = "select cl.description||' в'||cl.address||' от '||vwf.name||' '||wpat.lastname as text,cl.id from claim cl " +
					" left join workfunction wf on wf.id=cl.workFunction left join worker w on w.id=wf.worker_id" +
					" left join vocworkfunction vwf on vwf.id=wf.workfunction_id" +
					" left join patient wpat on wpat.id=w.person_id where cl.id="+aClaimId;
			list = aService.executeNativeSqlGetObj(sql);
			String text = list.get(0)[0].toString();
			ITemplateProtocolService bean = Injection.find(aRequest).getService(ITemplateProtocolService.class);
				bean.sendSms(phone,text);
		}
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
		//System.out.println("===== "+sql);
		service.executeUpdateNativeSql(sql);
		return aId;
	}
}