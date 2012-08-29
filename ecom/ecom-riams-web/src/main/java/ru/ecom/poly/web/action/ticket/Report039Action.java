package ru.ecom.poly.web.action.ticket;

//import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

//import ru.ecom.mis.ejb.service.medcase.IReportsService;
import ru.ecom.mis.ejb.service.medcase.IReportsService;
//import ru.ecom.mis.ejb.service.worker.IWorkerService;
import ru.ecom.mis.web.action.util.ActionUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class Report039Action  extends BaseAction {

	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		JournalBySpecialistForm form = (JournalBySpecialistForm)aRequest.getSession().getAttribute("poly_journalBySpecForm");
		String fr = aRequest.getParameter("ticketIs") ;
		boolean aTicketIs =  fr!=null &&fr.equals("1")?true:false;
		//IWorkerService service = Injection.find(aRequest).getService(IWorkerService.class) ;
		IReportsService repService  = Injection.find(aRequest).getService(IReportsService.class) ;
		String groupBy =ActionUtil.updateParameter("Form039Action","typeGroup","1", aRequest) ;
		String typeView =ActionUtil.updateParameter("Form039Action","typeView","2", aRequest) ;
		aRequest.setAttribute("beginDate", form.getBeginDate()) ;
		aRequest.setAttribute("finishDate", form.getFinishDate()) ;
		//aRequest.setAttribute("specialist", form.getSpecialist()) ;
		aRequest.setAttribute("queryTextBegin", repService.getTextQueryBegin(aTicketIs, groupBy, form.getBeginDate(), form.getFinishDate(), form.getSpecialist(), form.getWorkFunction(), form.getLpu(), form.getServiceStream(),form.getWorkPlaceType())) ;
		aRequest.setAttribute("queryTextEnd", repService.getTextQueryEnd(aTicketIs, groupBy, form.getBeginDate(), form.getFinishDate(), form.getSpecialist(), form.getWorkFunction(), form.getLpu(), form.getServiceStream(),form.getWorkPlaceType())) ;
		aRequest.setAttribute("filterInfo", repService.getFilterInfo(aTicketIs, form.getSpecialist(), form.getWorkFunction(), form.getLpu(), form.getServiceStream(),form.getWorkPlaceType())) ;
		aRequest.setAttribute("groupByTitle", repService.getTitle(groupBy)) ;
		return aMapping.findForward("success") ;
	}
	

}