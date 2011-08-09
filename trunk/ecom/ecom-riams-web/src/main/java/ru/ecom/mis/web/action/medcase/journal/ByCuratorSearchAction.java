package ru.ecom.mis.web.action.medcase.journal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.ejb.service.medcase.IPolyclinicMedCaseService;
import ru.ecom.mis.ejb.service.worker.IWorkerService;
import ru.ecom.web.actions.entity.ListAction;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.forms.response.FormMessage;
import ru.nuzmsh.web.tags.helper.RolesHelper;

public class ByCuratorSearchAction extends ListAction{

	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		
		IWorkerService service = Injection.find(aRequest).getService(IWorkerService.class) ;
        Long curator ;
        
        if (RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments", aRequest)) {
            if (aForm!=null) {
            	DepartmentJournalForm form =(DepartmentJournalForm) aForm ;
            	curator = form.getDepartment() ;
            	if (curator==null || curator==0) {
            		form.addMessage(new FormMessage("Укажите врача для поиска")) ;
            	}
            	
            } else{
            	 return aMapping.findForward("success") ;
            }
        } else {
                curator = service.getWorkFunction() ;
                System.out.println("curator="+curator) ;
            	
        	
        }
        if (curator!=null && curator!=0) { 
	        System.out.println("curator ="+curator) ;
	        aRequest.setAttribute("curator",curator) ;
	        String curatorinfo = service.getWorkFunctionInfo(curator) ;
	        aRequest.setAttribute("curatorInfo",curatorinfo) ;
        }
        return aMapping.findForward("success") ;
	}
}