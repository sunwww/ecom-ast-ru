package ru.ecom.poly.web.action.ticket;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.mis.ejb.service.patient.HospitalLibrary;
import ru.ecom.mis.ejb.service.worker.IWorkerService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TicketBySpecialistDataAction  extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		IWorkerService service = Injection.find(aRequest).getService(IWorkerService.class) ;
		String idString = aRequest.getParameter("id") ;
		int ind1 = idString.indexOf(':');
		int ind2 = idString.indexOf(':',ind1+1);
		String date =idString.substring(0,ind1) ;
		String typePat = idString.substring(ind1+1,ind2) ;

		switch (typePat) {
			case "2":
				//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)>0") ;
				aRequest.setAttribute("add", HospitalLibrary.getSqlForPatient(true, true, "t.date", "p", "pvss", "pmp", "ok"));
				aRequest.setAttribute("infoTypePat", " (по иногородним)");
				break;
			case "1":
				//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
				aRequest.setAttribute("add", HospitalLibrary.getSqlForPatient(true, false, "t.date", "p", "pvss", "pmp", "ok"));
				aRequest.setAttribute("infoTypePat", " (по региональным)");
				break;
			case "3":
				//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
				aRequest.setAttribute("add", HospitalLibrary.getSqlGringo(true, "ok"));
				aRequest.setAttribute("infoTypePat", "Поиск по иностранцам");
				break;
			default:
				aRequest.setAttribute("add", "");
				aRequest.setAttribute("infoTypePat", " (по всем)");
				break;
		}
		String spec ;
		if (date.indexOf('.')>0) {
			spec = idString.substring(ind2+1) ;
			aRequest.setAttribute("date", date);
		} else {
			int ind3 = idString.indexOf(':',ind2+1);
			int ind4 = idString.indexOf(':',ind3+1);
			spec = idString.substring(ind2+1,ind3) ;
			String begin= idString.substring(ind3+1,ind4) ;
			String end = idString.substring(ind4+1);
			aRequest.setAttribute("mkb", date);
			aRequest.setAttribute("type", typePat) ;
			aRequest.setAttribute("dateBegin", begin);
			aRequest.setAttribute("dateEnd", end) ;
		}
		aRequest.setAttribute("spec", spec) ;
		try{
		aRequest.setAttribute("specInfo", service.getWorkFunctionInfo(Long.valueOf(spec))) ;
		} catch(Exception e){
			aRequest.setAttribute("specInfo", "") ;
		}

		return aMapping.findForward(SUCCESS);
	}

}