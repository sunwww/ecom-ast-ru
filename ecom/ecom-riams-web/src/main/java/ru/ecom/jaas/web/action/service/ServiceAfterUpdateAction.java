package ru.ecom.jaas.web.action.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.messages.InfoMessage;
import ru.nuzmsh.web.struts.BaseAction;

public class ServiceAfterUpdateAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
        if (aRequest.getParameter("emergency")!=null) {
        	service.executeUpdateNativeSql("insert into CustomMessage (isemergency,Recipient,MessageTitle,MessageText,Username,MessageUrl,DispatchDate,DispatchTime,ValidityDate,ValidityTime,issystem)" 
            		+" (select '1',su.login,'Обновление','Через 10 минут будет обновление системы МедОС'" 
            		+" ,'update_message','ecom_releases.do',current_date,current_time,current_date,current_time,'0' from secuser su where su.disabled = '0' or su.disabled is null)") ;
        } else {
        	service.executeUpdateNativeSql("insert into CustomMessage (Recipient,MessageTitle,MessageText,Username,MessageUrl,DispatchDate,DispatchTime,issystem)" 
        		+" (select su.login,'Обновление','Произведено обновление системы. Информацию о последних изменений смотрите в новостях.'" 
        		+" ,'update_message','ecom_releases.do',current_date,current_time,'1' from secuser su where su.disabled = '0' or su.disabled is null)") ;
        }
        new InfoMessage(aRequest, "Сообщение об обновление отправлено всем пользователям") ;
        return new ActionForward(aMapping.findForward("success")) ;
    }
}
