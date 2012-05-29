package ru.ecom.diary.web.action.protocol;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import ru.ecom.mis.ejb.service.diary.IParameterService;
import ru.ecom.mis.ejb.service.medcase.IReportsService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class DocumentPrepareCreateAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
			HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws Exception {
		//IReportsService repService  = Injection.find(aRequest).getService(IReportsService.class) ;
		
		IParameterService servicepar = Injection.find(aRequest).getService(IParameterService.class) ;
		Long id = Long.valueOf(aRequest.getParameter("type")) ;
		String action = servicepar.getActionByDocument(id, "") ;
		return new ActionForward("/"+action+".do?id="+aRequest.getParameter("id"),true);
	}

}
