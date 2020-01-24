package ru.ecom.web.dwr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.ejb.services.login.ILoginService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.web.messages.ClaimMessage;
import ru.nuzmsh.web.messages.UserMessage;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * Для aucotomplete
 */
public class VocServiceJs {
	public Patient getTestPatient(HttpServletRequest aRequest) {
		Patient patient = new Patient();
		patient.setLastname("TEST");
		return patient;
	}
	public String getTestPatientLastname(HttpServletRequest aRequest) {
		Patient patient = new Patient();
		patient.setLastname("TEST");
		return patient.getLastname();
	}

	public String getWebSocketServer(HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		Collection<WebQueryResult> list = service.executeNativeSql("select keyvalue from softconfig where key='WEBSOCKET_SERVER'") ;
		JSONObject ret = new JSONObject();
		if (!list.isEmpty()) {
			String queue="", queueCode="",server;
			server=list.iterator().next().get1().toString();
			list= service.executeNativeSql("select vq.code, vq.id from secuser su " +
					" left join workfunction wf on wf.secuser_id=su.id" +
					" left join vocQueue vq on vq.id=wf.queue_id where su.login='"+username+"'");
			try {WebQueryResult wqr = list.iterator().next();queue=wqr.get2().toString();queueCode=wqr.get1().toString();} catch (Exception e) {}
			ret.put("status","ok");
			ret.put("server",server);
			ret.put("queue",queue);
			ret.put("queueCode",queueCode);
			ret.put("username",username);
			ret.put("appendUrl","/"+(username.startsWith("TV_")?"TV":"OPERATOR"));
		} else {
			ret.put("status","error");
			ret.put("errorCode","empty");
		}
		return ret.toString();
	}
    public String getNameById(HttpServletRequest aRequest, String aVocName, String aId, String aParentId) throws NamingException, VocServiceException {
        return EntityInjection.find(aRequest).getVocService().getNameById(aId, aVocName, new VocAdditional(aParentId)) ;
    }
    public String getCodeById(String aVocName, String aId, String aFldCode, HttpServletRequest aRequest) throws NamingException {
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	Collection<WebQueryResult> list = service.executeNativeSql("select "+aFldCode+" from "+aVocName+" where id='"+aId+"'") ;
    		
    	return list.isEmpty()?"":""+list.iterator().next().get1() ; 
    }
    public boolean checkClaimMessage (HttpServletRequest aRequest, Long aClaimId, Long aStatus, String aComment) throws NamingException {
    	String commentSql = "";
    	if (aComment!=null&&!aComment.trim().equals("")) {
    		commentSql = " , creatorComment = '"+aComment+"' ";
    	}
    	String sql = "update Claim set completeConfirmed = '"+aStatus+"' "+commentSql+" where id = "+aClaimId;
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	service.executeUpdateNativeSql(sql) ;
    	ClaimMessage.removeFromSession(aRequest,aClaimId) ;
    	return true;
  
    }
    
    public boolean checkMessage(HttpServletRequest aRequest, Long aIdMessage) throws NamingException {
    	ILoginService serviceLogin = Injection.find(aRequest).getService(ILoginService.class) ;
    	serviceLogin.checkMessage(aIdMessage) ;
    	UserMessage.removeFromSession(aRequest,aIdMessage) ;
    	return true ;
    }
    public boolean checkEmergencyMessages(String aIds,String aTxt,HttpServletRequest aRequest) throws NamingException {
    	StringBuilder sqlA = new StringBuilder() ;
    	sqlA.append("update CustomMessage set readDate=current_date,readtime=current_time") ;
		sqlA.append(" where id in (").append(aIds).append(")") ;
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	service.executeUpdateNativeSql(sqlA.toString()) ;
    	return true ;
    }
    public boolean hiddenMessage(HttpServletRequest aRequest, Long aIdMessage) throws NamingException {
    	ILoginService serviceLogin = Injection.find(aRequest).getService(ILoginService.class) ;
    	serviceLogin.hideMessage(aIdMessage) ;
    	return true ;
    }

	/**
	 * Получить непрочитанные сообщения #187
	 *
	 * @param isEmergency true - экстренные
	 * @param aMaxCount макс. кол-во
	 * @param isOrderByDesc сначала самые новые
	 * @param aRequest HttpServletRequest
	 * @return String json с результатом
	 */
    public String getUnreadMessages(String isEmergency, Integer aMaxCount, Boolean isOrderByDesc, HttpServletRequest aRequest) throws NamingException {
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	StringBuilder sqlA = new StringBuilder() ;
    	java.util.Date date = new java.util.Date() ;
    	java.sql.Time dispatchTime = new java.sql.Time(date.getTime()) ;
    	String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		sqlA.append("select id,messagetitle,messageText as messageText,to_char(dispatchdate,'dd.mm.yyyy')||' '||cast(dispatchtime as varchar(5)) as inforeceipt,messageUrl from CustomMessage") ;
		sqlA.append(" where recipient='").append(username).append("'") ;
		sqlA.append(" and readDate is null");
		String isEmergencyStr = "";
		if (!isEmergency.equals(""))
			isEmergencyStr = isEmergency.equals("1")? " and isEmergency='1' " : " and (isEmergency is null or isEmergency='0') ";
		sqlA.append(isEmergencyStr)
		.append(" and ((validitydate>current_date or validitydate=current_date and validitytime>=cast('")
		.append(dispatchTime)
		.append("' as time)) or validitydate is null)");
		if (isOrderByDesc) sqlA.append(" order by id desc");
    	Collection<WebQueryResult> list = service.executeNativeSql(sqlA.toString(),aMaxCount);
		JSONArray params = new JSONArray() ;
		String[][] props = {{"1","id"},{"2","messageTitle"},{"3","messageText"},{"4","infoReceipt"},{"5","messageUrl"}} ;
		for(WebQueryResult wqr : list) {
			JSONObject param = new JSONObject();
			try {
				for(String[] prop : props) {
					Object value = PropertyUtil.getPropertyValue(wqr, prop[0]) ;
					String strValue = value!=null?value.toString():"";
					param.put(prop[1],strValue);
				}
				params.put(param);
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
		JSONObject root = new JSONObject();
		try {
			root.put("params",params);
			//return root.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return root.toString() ;
    }

	/**
	 * Получить кол-во непрочитанных сообщений #187
	 *
	 * @param isEmergency true - экстренные
	 * @param aRequest HttpServletRequest
	 * @return String кол-во
	 */
	public String getCountUnreadMessages(String isEmergency,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sqlA = new StringBuilder() ;
		java.util.Date date = new java.util.Date() ;
		java.sql.Time dispatchTime = new java.sql.Time(date.getTime()) ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		sqlA.append("select count(id) from CustomMessage") ;
		sqlA.append(" where recipient='").append(username).append("'") ;
		sqlA.append(" and readDate is null");
		String isEmergencyStr = "";
		if (!isEmergency.equals(""))
			isEmergencyStr = isEmergency.equals("1")? " and isEmergency='1' " : " and (isEmergency is null or isEmergency='0') ";
		sqlA.append(isEmergencyStr)
		.append(" and ((validitydate>current_date or validitydate=current_date and validitytime>=cast('")
		.append(dispatchTime)
		.append("' as time)) or validitydate is null)");
		Collection<WebQueryResult> list = service.executeNativeSql(sqlA.toString(),10);
		return list.iterator().next()!=null? list.iterator().next().get1().toString() : "0";
	}

    private String str(String aValue) {
    	if (aValue.indexOf('\"')!=-1) {
    		aValue = aValue.replaceAll("\"", "\\\"") ;
    	}
    	if (aValue.indexOf('\n')!=-1) {
    		aValue = aValue.replaceAll("\n", "\\\\n") ;
    	}
    	if (aValue.indexOf('\r')!=-1) {
    		aValue = aValue.replaceAll("\r", "\\\\r") ;
    	}
    	if (aValue.indexOf('\t')!=-1) {
    		aValue = aValue.replaceAll("\t", "\\\\t") ;
    	}
    	return aValue ;
    }
    
    public String getAllValueByVocs(String aVocs,HttpServletRequest aRequest) throws NamingException {
    	String sql="select id,name from ";
    	StringBuilder sb = new StringBuilder() ;
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	sb.append("{");
		sb.append("\"vocs\":[") ;
		
		String[][] props = {{"1","id"},{"2","name"}} ;
		String[] vocs = aVocs.split(",") ;
		boolean isFistrVoc = true ;
		for(String voc : vocs) {
			//System.out.println("voc"+voc) ;
			Collection<WebQueryResult> list= service.executeNativeSql(sql+voc) ;
			if (isFistrVoc) {isFistrVoc=false ;} else {sb.append(", ");}
			sb.append("{\"name\":\"").append(voc).append("\",\"values\":[");
			boolean firstPassed = true ;
			for (WebQueryResult wqr:list) {
				
				StringBuilder par = new StringBuilder() ;
				par.append("{") ;
				boolean isFirtMethod = true ;
				try {
					for(String[] prop : props) {
						Object value = PropertyUtil.getPropertyValue(wqr, prop[0]) ;
						String strValue = value!=null?value.toString():"";
						if(!isFirtMethod) par.append(", ") ;else isFirtMethod=false;
						par.append("\"").append(prop[1]).append("\":\"").append(str(strValue)).append("\"") ;
						//System.out.println("value="+value) ;
					}
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
				par.append("}") ;
				if(!firstPassed) sb.append(", ") ;else firstPassed=false;
				sb.append(par) ;
			}
			sb.append("]}") ;
		}
		sb.append("]}") ;
    	return sb.toString();
    }
}
