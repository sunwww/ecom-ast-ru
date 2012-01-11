package ru.ecom.mis.web.action.disability;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.ejb.service.disability.IDisabilityService;
import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.poly.web.action.ticket.SearchForm;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class AddressUpdateAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        //SearchForm form = (SearchForm) aForm;
        //form.validate(aMapping, aRequest);
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
        //System.out.println("search="+form.getNumberTicket()) ;
        String clear = aRequest.getParameter("clear") ;
    	long id = 0 ;
        if (clear!=null && clear.equals("1")) {
        	service.addressClear();
        }
    	while (id!=-1) {
    		id=service.addressUpdate(id);
    	}
        

        return aMapping.findForward("success");
    }
}