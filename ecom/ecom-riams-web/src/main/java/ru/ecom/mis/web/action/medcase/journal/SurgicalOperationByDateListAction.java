package ru.ecom.mis.web.action.medcase.journal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.web.actions.parententity.ListAction;
import ru.ecom.web.util.Injection;

public class SurgicalOperationByDateListAction extends ListAction {
	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		//IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
		//String idString = aRequest.getParameter("id") ;
        //aRequest.setAttribute("list", service.getSurgicalOperationByDate(idString));
		
		return aMapping.findForward("success");
	}
}
