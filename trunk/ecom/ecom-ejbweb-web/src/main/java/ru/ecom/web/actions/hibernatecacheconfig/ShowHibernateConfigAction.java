package ru.ecom.web.actions.hibernatecacheconfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.hibernatecacheconfig.IHibernateCacheConfigService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class ShowHibernateConfigAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		aRequest.setAttribute("code",
				Injection.find(aRequest).getService(IHibernateCacheConfigService.class).generateHibernateCacheProperties()) ;
		aRequest.setAttribute("title", "Кэш для hibernate.properties") ;
		return aMapping.findForward("success");
	}

	
}
