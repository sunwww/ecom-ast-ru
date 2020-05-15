package ru.ecom.jaas.web.action.service;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.messages.InfoMessage;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Collection;

public class ServiceAfterUpdateAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	Calendar cal = Calendar.getInstance() ;
		cal.add(Calendar.MINUTE, 5) ;
    	String t=DateFormat.formatToTime(new java.sql.Time(cal.getTime().getTime()));
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql("select su.login from secuser su where su.disabled = '0' or su.disabled is null") ;
		for (WebQueryResult user:list) {
			service.executeUpdateNativeSql("insert into CustomMessage (isemergency,Recipient,MessageTitle,MessageText,Username,MessageUrl,DispatchDate,DispatchTime,ValidityDate,ValidityTime,issystem)"
				+" values ('1','"+user.get1()+"','Обновление','Через 5 минут будет обновление системы МедОС'"
				+" ,'update_message','ecom_releases.do',current_date,current_time,current_date,cast('"+t+"' as time),'0')") ;
		}
        new InfoMessage(aRequest, "Сообщение об обновление отправлено всем пользователям") ;
        return new ActionForward(aMapping.findForward(SUCCESS)) ;
    }
}
