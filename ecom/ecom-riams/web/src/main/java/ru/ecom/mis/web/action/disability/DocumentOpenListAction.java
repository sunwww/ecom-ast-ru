package ru.ecom.mis.web.action.disability;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.ejb.service.disability.IDisabilityService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class DocumentOpenListAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class);
		String idString = aRequest.getParameter("id") ;
		String type = aRequest.getParameter("type") ;
		String dateSearch = aRequest.getParameter("dateSearch") ;
		if (type!=null && type.equals("close")) {
			idString = idString.substring(8)+"."+idString.substring(5,7)+"."+idString.substring(0,4) ;
			aRequest.setAttribute("list", service.findCloseTicketByDate(idString,dateSearch));
			//2009-12-31
		} else {
			aRequest.setAttribute("list", service.findOpenTicketByDate(idString));
		}
        
		return aMapping.findForward("success");
	}

}
