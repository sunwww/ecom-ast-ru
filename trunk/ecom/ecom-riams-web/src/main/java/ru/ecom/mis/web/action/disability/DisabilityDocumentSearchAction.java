package ru.ecom.mis.web.action.disability;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.ejb.service.disability.IDisabilityService;
import ru.ecom.poly.web.action.ticket.SearchForm;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class DisabilityDocumentSearchAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        SearchForm form = (SearchForm) aForm;
        //form.validate(aMapping, aRequest);
        IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class);
        System.out.println("search="+form.getNumberTicket()) ;
        aRequest.setAttribute("list", service.findDocumentBySeriesAndNumber(form.getNumberTicket() ));

        return aMapping.findForward("success");
    }
}