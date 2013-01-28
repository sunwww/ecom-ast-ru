package ru.ecom.mis.web.action.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.jaas.ejb.service.ISoftConfigService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class HelpSaveAction   extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		HelpForm frm = (HelpForm)aForm ;
		
		String code = frm.getCode() ;
		ISoftConfigService service = Injection.find(aRequest).getService(ISoftConfigService.class) ;
		service.saveContextHelp(code, frm.getContextText()) ;
		String context = service. getContextHelp(code) ;
		frm.setContextText(context) ;
		aRequest.setAttribute("code", code) ;
		aRequest.setAttribute("contextText", context) ;
		aRequest.setAttribute("nextUrl", frm.getNextUrl()) ;
		return aMapping.findForward("success");
	}

}