package ru.ecom.poly.web.action.ticket;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.mis.ejb.service.worker.IWorkerService;
import ru.ecom.poly.ejb.services.ITicketService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;
import ru.nuzmsh.web.tags.helper.RolesHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: morgun
 * Date: 29.01.2007
 * Time: 9:02:24
 * To change this template use File | Settings | File Templates.
 */
public class TicketSpecSearchAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        SearchForm form = (SearchForm) aForm;
        ITicketService service = Injection.find(aRequest).getService(ITicketService.class);
        if (RolesHelper.checkRoles("/Policy/Poly/Ticket/IsDoctorEdit", aRequest)) {
        	IWorkerService servWorker = Injection.find(aRequest).getService(IWorkerService.class) ;
        	Long doctor = servWorker.getWorkFunction() ;
        	if (form.getDateFilter() == null || form.getDateFilter().equals("")) {
        		form.setDateFilter(DateFormat.formatCurrentDate()) ;
        		form.setDoctor(doctor) ;
        	}
        	aRequest.setAttribute("listDirect", service.findAllWorkerTickets(doctor, form.getDateFilter(),0));
        	aRequest.setAttribute("listAccepted", service.findAllWorkerTickets(doctor, form.getDateFilter(),1));
        } else {
        	aRequest.setAttribute("listDirect", service.findAllSpecialistTickets(form.getDoctor(), form.getDateFilter(),0));
        	aRequest.setAttribute("listAccepted", service.findAllSpecialistTickets(form.getDoctor(), form.getDateFilter(),1));
        }
        
        
        return aMapping.findForward(SUCCESS);
    }
}
