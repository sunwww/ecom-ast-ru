package ru.ecom.mis.web.action.medcase.journal;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.mis.ejb.service.worker.IWorkerService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.forms.response.FormMessage;
import ru.nuzmsh.web.struts.BaseAction;
import ru.nuzmsh.web.tags.helper.RolesHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DepartmentSearchAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        AdmissionJournalForm form = (AdmissionJournalForm) aForm;
        Long lpu ;
        IWorkerService service = Injection.find(aRequest).getService(IWorkerService.class) ;
        if (RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments", aRequest)) {
        	lpu = form.getDepartment() ;
        	if (lpu==null || lpu==0) {
        		form.addMessage(new FormMessage("Укажите отделение поиска")) ;
        	}
        } else {
            try {
                lpu = service.getWorkingLpu() ;
            } catch(Exception e) {
            	return aMapping.findForward("successerror");
            }
        	
        }
        if (lpu!=null && lpu!=0) {
	    	aRequest.setAttribute("department",lpu) ;
	        String lpuinfo = service.getWorkingLpuInfo(lpu) ;
	        aRequest.setAttribute("departmentInfo",lpuinfo) ;
	        if (form.getDischargeIs()!=null && Boolean.TRUE==form.getDischargeIs()) {
	        	aRequest.setAttribute("dateSearch","dateFinish") ;
	        	aRequest.setAttribute("infoSearch"," Поиск по дате выписки") ;
	        } else {
	        	aRequest.setAttribute("dateSearch","dateStart") ;
	        	aRequest.setAttribute("infoSearch"," Поиск по дате поступления") ;
	        }
        }
        return aMapping.findForward(SUCCESS);
    }
}