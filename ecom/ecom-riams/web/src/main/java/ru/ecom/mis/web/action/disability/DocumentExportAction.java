package ru.ecom.mis.web.action.disability;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.web.util.ActionUtil;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DocumentExportAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        String typeDate = ActionUtil.updateParameter("DocumentExport","typeDate","2", aRequest) ;
		String typeDocument = ActionUtil.updateParameter("DocumentExport","typeDocument","3", aRequest) ;
		String orderBy = ActionUtil.updateParameter("DocumentExport","orderBy","1", aRequest) ;
		DisabilitySearchForm form = (DisabilitySearchForm) aForm;
        if (form.validate(aMapping, aRequest).size()==0) {
            if (form.getBeginDate()!=null && !form.getBeginDate().equals("")) {
    	        String beginDate = DateFormat.formatToJDBC(form.getBeginDate()) ;
    	        aRequest.setAttribute("beginDate", beginDate) ;
    	        String endDate = beginDate ;
    	        if (form.getEndDate()!=null && !form.getEndDate().equals("")) {
    	        	endDate = DateFormat.formatToJDBC(form.getEndDate())  ;
    	        }
    	        aRequest.setAttribute("endDate", endDate) ;
            }
            aRequest.setAttribute("valid", "1") ;
        } else  {
        	aRequest.setAttribute("valid", "0") ;
        }
        
        String dGroup;
        if ("1".equals(typeDate)) {
        	dGroup="(select min(dr2.dateFrom) from disabilityrecord as dr2 where dr2.disabilitydocument_id=dd.id)" ;
        	aRequest.setAttribute("dateGroup",dGroup) ;
        	aRequest.setAttribute("dateSearch","max") ;
        	aRequest.setAttribute("infoSearch"," Поиск по дате закрытия") ;
        } else if ("2".equals(typeDate)) {
        	dGroup = "dd.issuedate" ;
        	aRequest.setAttribute("dateGroup",dGroup) ;
        	aRequest.setAttribute("dateSearch","min") ;
        	aRequest.setAttribute("infoSearch"," Поиск по дате выдачи") ;
        } 
		if ("1".equals(orderBy)) {
			aRequest.setAttribute("orderByInfo", "по номерам больничных");
			aRequest.setAttribute("orderBystatus", " dd.number ") ;        	
		} else if ("2".equals(orderBy)) {
			aRequest.setAttribute("orderByInfo", "по дате выдачи");
			aRequest.setAttribute("orderBystatus", " dd.issueDate, dd.number ") ;
		} else {
			aRequest.setAttribute("orderByInfo", "по ФИО");
			aRequest.setAttribute("orderBystatus", "p.lastname,p.firstname,p.middlename,dd.number") ;        	
		}
        if ("1".equals(typeDocument)) {
        	aRequest.setAttribute("typeDocumentInfo", "всем экспортированным");
        	aRequest.setAttribute("status", "(dd.exportdate is not null) and ") ;        	
        } else if ("2".equals(typeDocument)) {
        	aRequest.setAttribute("typeDocumentInfo", "успешно экспортированным");
        	aRequest.setAttribute("status", "(dd.exportdate is not null and dd.exportdefect='') and ") ;
        } else {
        	aRequest.setAttribute("typeDocumentInfo", "дефекты");
        	aRequest.setAttribute("status", "(dd.exportdate is not null and (dd.exportdefect is not null and dd.exportdefect!='')) and ") ;        	
        }
        
		return aMapping.findForward(SUCCESS);
	}
}