package ru.ecom.mis.web.action.disability;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.web.util.ActionUtil;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DocumentCloseAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        String noActuality = ActionUtil.updateParameter("DocumentClose","noActuality","3", aRequest) ;
		String typeDate = ActionUtil.updateParameter("DocumentClose","typeDate","3", aRequest) ;
		String typeDocument = ActionUtil.updateParameter("DocumentClose","typeDocument","3", aRequest) ;
		String typeLpu= ActionUtil.updateParameter("DocumentClose","typeLpu","1", aRequest) ;
		String orderBy = ActionUtil.updateParameter("DocumentClose","orderBy","1", aRequest) ;
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
        /*
        IPatientService service = Injection.find(aRequest).getService(IPatientService.class);
        IEntityFormService entityService = EntityInjection.find(aRequest).getEntityFormService();
        aRequest.setAttribute("list"
                , service.findPatient(form.getLpu(), form.getLpuArea(), form.getLastname()));
        */
        String dGroup;
        if ("1".equals(typeDate)) {
        	dGroup="(select min(dr2.dateFrom) from disabilityrecord as dr2 where dr2.disabilitydocument_id=dd.id)" ;
        	aRequest.setAttribute("dateGroup",dGroup) ;
        	aRequest.setAttribute("dateSearch","max") ;
        	aRequest.setAttribute("infoSearch"," Поиск по дате закрытия") ;
        } else if ("2".equals(typeDate)) {
        	dGroup = "(select max(dr2.dateTo) from disabilityrecord as dr2 where dr2.disabilitydocument_id=dd.id)" ;
        	aRequest.setAttribute("dateGroup",dGroup) ;
        	aRequest.setAttribute("dateSearch","min") ;
        	aRequest.setAttribute("infoSearch"," Поиск по дате открытия") ;
        } else {
        	aRequest.setAttribute("dateSearch","issued") ;
        	aRequest.setAttribute("infoSearch"," Поиск по дате выдачи") ;
        	dGroup= "dd.issueDate" ;
        	aRequest.setAttribute("dateGroup",dGroup) ;
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
        	aRequest.setAttribute("typeDocumentInfo", "открытым документам");
        	aRequest.setAttribute("status", "(dd.isclose is null or dd.isclose='0') and ") ;        	
        } else if ("2".equals(typeDocument)) {
        	aRequest.setAttribute("typeDocumentInfo", "закрытым документам");
        	aRequest.setAttribute("status", "dd.isclose='1' and ") ;
        } else {
        	aRequest.setAttribute("typeDocumentInfo", "всем документам");
        	aRequest.setAttribute("status", "") ;        	
        }
        if ("1".equals(noActuality)) {
        	aRequest.setAttribute("statusNoActuality", "cast(dd.noActuality as int) =1 and ") ;
        	aRequest.setAttribute("infoSearchNoActuality"," испорченных") ;
        } else if ("2".equals(noActuality)) {
        	aRequest.setAttribute("infoSearchNoActuality"," неиспорченных") ;
        	aRequest.setAttribute("statusNoActuality", "(dd.noActuality is null or cast(dd.noActuality as int) =0) and ") ;        	
        } else {
        	aRequest.setAttribute("dateSearch","issued") ;
        	aRequest.setAttribute("infoSearchNoActuality"," все") ;
        	aRequest.setAttribute("statusNoActuality", "" );
        }
        if (form.getDisabilityReason()!=null && form.getDisabilityReason()> 0L) {
        	aRequest.setAttribute("disReason", " and dd.disabilityReason_id="+form.getDisabilityReason()) ;
        } else {
        	aRequest.setAttribute("disReason", "") ;
        }
		if (form.getDisabilityReason2()!=null && form.getDisabilityReason2()> 0L) {
			aRequest.setAttribute("disReason2", " and dd.disabilityReason2_id="+form.getDisabilityReason2()) ;
		} else {
			aRequest.setAttribute("disReason2", "") ;
		}
        
        if (form.getCloseReason()!=null && form.getCloseReason()> 0L) {
        	aRequest.setAttribute("closeReason", " and dd.closeReason_id="+form.getCloseReason()) ;
        } else {
        	aRequest.setAttribute("closeReason", "") ;
        }
        if (form.getPrimarity()!=null && form.getPrimarity()> 0L) {
        	aRequest.setAttribute("primarity", " and dd.primarity_id="+form.getPrimarity()) ;
        } else {
        	aRequest.setAttribute("primarity", "") ;
        }
        if (typeLpu!=null && typeLpu.equals("1")) {
        	aRequest.setAttribute("anotherlpu", " and dd.anotherLpu_id is null") ;
        	aRequest.setAttribute("anotherlpuinfo", " с учетом выданных др. ЛПУ") ;
        } else if (typeLpu!=null && typeLpu.equals("2")) {
        	aRequest.setAttribute("anotherlpu", " and dd.anotherLpu_id is not null") ;
        	aRequest.setAttribute("anotherlpuinfo", " без учета выданных др. ЛПУ") ;
        } else {
        	aRequest.setAttribute("anotherlpu", "") ;
        	aRequest.setAttribute("anotherlpuinfo", "") ;
        }
        
		return aMapping.findForward(SUCCESS);
	}
}