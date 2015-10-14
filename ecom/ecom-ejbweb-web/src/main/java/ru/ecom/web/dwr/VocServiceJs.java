package ru.ecom.web.dwr;

import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.login.ILoginService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.web.messages.UserMessage;

/**
 * Для aucotomplete
 */
public class VocServiceJs {
    public String getNameById(HttpServletRequest aRequest, String aVocName, String aId, String aParentId) throws NamingException, VocServiceException {
        return EntityInjection.find(aRequest).getVocService().getNameById(aId, aVocName, new VocAdditional(aParentId)) ;
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
    public String getEmergencyMessages(HttpServletRequest aRequest) throws NamingException {
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	StringBuilder sqlA = new StringBuilder() ;
    	String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		sqlA.append("select id,messagetitle,messageText,to_char(dispatchdate,'dd.mm.yyyy')||' '||cast(dispatchtime as varchar(5)) as inforeceipt,messageUrl from CustomMessage") ;
		sqlA.append(" where recipient='").append(username).append("'") ;
		sqlA.append(" and readDate is null");
		sqlA.append(" and isEmergency='1' and (validitydate<current_date or validitydate=current_date and validitytime<=current_time) ");
    	Collection<WebQueryResult> list = service.executeNativeSql(sqlA.toString(),10);
    	StringBuilder sb = new StringBuilder() ;
    	sb.append("{");
		sb.append("\"params\":[") ;
		boolean firstPassed = false ;
		String[][] props = {{"1","id"},{"2","messageTitle"},{"3","messageText"},{"4","infoReceipt"},{"5","messageUrl"}} ;
		for(WebQueryResult wqr : list) {
			StringBuilder par = new StringBuilder() ;
			par.append("{") ;
			boolean isFirtMethod = false ;
			try {
				for(String[] prop : props) {
					Object value = PropertyUtil.getPropertyValue(wqr, prop[0]) ;
					String strValue = value!=null?value.toString():"";
					if(isFirtMethod) par.append(", ") ;else isFirtMethod=true;
					par.append("\"").append(prop[1]).append("\":\"").append(str(strValue)).append("\"") ;
				}
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
			par.append("}") ;
			if(firstPassed) sb.append(", ") ;else firstPassed=true;
			sb.append(par) ;
		}
		sb.append("]") ;
		sb.append("}") ;
    	return sb.toString() ;
    }
    private String str(String aValue) {
    	if (aValue.indexOf("\"")!=-1) {
    		aValue = aValue.replaceAll("\"", "\\\\\"") ;
    	}
    	return aValue ;
    }
}
