package ru.ecom.poly.web.action.ticket;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.mis.ejb.service.medcase.IReportsService;
import ru.ecom.web.util.ActionUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Report039Action  extends BaseAction {

	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		JournalBySpecialistForm form = (JournalBySpecialistForm)aRequest.getSession().getAttribute("poly_journalBySpecForm");
		String fr = aRequest.getParameter("ticketIs") ;
		boolean aTicketIs =  "1".equals(fr);

		IReportsService repService  = Injection.find(aRequest).getService(IReportsService.class) ;
		String groupBy =ActionUtil.updateParameter("Form039Action","typeGroup","1", aRequest) ;
		ActionUtil.updateParameter("Form039Action","typeView","2", aRequest) ;
		aRequest.setAttribute("beginDate", form.getBeginDate()) ;
		aRequest.setAttribute("finishDate", form.getFinishDate()) ;
		aRequest.setAttribute("queryTextBegin", repService.getTextQueryBegin(aTicketIs, groupBy, form.getBeginDate(), form.getFinishDate(), form.getSpecialist(), form.getWorkFunction(), form.getLpu(), form.getServiceStream(),form.getWorkPlaceType())) ;
		aRequest.setAttribute("queryTextEnd", repService.getTextQueryEnd(aTicketIs, groupBy, form.getBeginDate(), form.getFinishDate(), form.getSpecialist(), form.getWorkFunction(), form.getLpu(), form.getServiceStream(),form.getWorkPlaceType())) ;
		aRequest.setAttribute("filterInfo", repService.getFilterInfo(aTicketIs, form.getSpecialist(), form.getWorkFunction(), form.getLpu(), form.getServiceStream(),form.getWorkPlaceType())) ;
		aRequest.setAttribute("groupByTitle", repService.getTitle(groupBy)) ;
		return aMapping.findForward(SUCCESS) ;
	}
}