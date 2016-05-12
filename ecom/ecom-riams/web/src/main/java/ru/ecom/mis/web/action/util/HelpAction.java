package ru.ecom.mis.web.action.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.jaas.ejb.service.ISoftConfigService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;
import ru.nuzmsh.web.tags.helper.RolesHelper;

public class HelpAction  extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		
		String code = aRequest.getParameter("code") ;
		ISoftConfigService service = Injection.find(aRequest).getService(ISoftConfigService.class) ;
		String context = service.getContextHelp(code) ;
		HelpForm frm = (HelpForm)aForm ;
		
		frm.setCode(code) ;
		frm.setContextText(context) ;
		aRequest.setAttribute("code", code) ;
		aRequest.setAttribute("contextText", context) ;
		aRequest.setAttribute("nextUrl", frm.getNextUrl()) ;
		if (RolesHelper.checkRoles("/Policy/Mis/Help/Edit", aRequest)) {
			if (context==null || context.equals("")) {
				context = service.getContextHelp("/riams/default.htm") ;
				frm.setContextText(context) ;
				aRequest.setAttribute("contextText", context) ;
				
			}
			
			return aMapping.findForward("success");
		} else {
			return aMapping.findForward("success_short");
		}
		
	}

}
