package ru.ecom.mis.web.action.disability;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.ejb.service.disability.IDisabilityService;
import ru.ecom.mis.web.action.medcase.journal.AdmissionJournalForm;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class DocumentCloseAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		AdmissionJournalForm form = (AdmissionJournalForm) aForm;
        form.validate(aMapping, aRequest) ;
        /*IPatientService service = Injection.find(aRequest).getService(IPatientService.class);
//        IEntityFormService entityService = EntityInjection.find(aRequest).getEntityFormService();
        aRequest.setAttribute("list"
                , service.findPatient(form.getLpu(), form.getLpuArea(), form.getLastname()));
        */
        if (form.getDischargeIs()!=null && form.getDischargeIs()==true) {
        	aRequest.setAttribute("dateGroup","(select max(dr2.dateTo) from disabilityrecord as dr2 where dr2.disabilitydocument_id=dd.id)") ;
        	aRequest.setAttribute("dateSearch","max") ;
        	aRequest.setAttribute("infoSearch"," Поиск по дате закрытия") ;
        } else {
        	aRequest.setAttribute("dateGroup","(select min(dr2.dateFrom) from disabilityrecord as dr2 where dr2.disabilitydocument_id=dd.id)") ;
        	aRequest.setAttribute("dateSearch","min") ;
        	aRequest.setAttribute("infoSearch"," Поиск по дате открытия") ;
        }
		return aMapping.findForward("success");
	}
}