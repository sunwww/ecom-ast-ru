package ru.ecom.mis.web.action.disability;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.web.action.util.ActionUtil;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

public class DocumentCloseAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
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
        String noActuality = ActionUtil.updateParameter("DocumentClose","noActuality","3", aRequest) ;
		String typeDate = ActionUtil.updateParameter("DocumentClose","typeDate","3", aRequest) ;
		String typeDocument = ActionUtil.updateParameter("GroupByBedFund","typeDocument","3", aRequest) ;
        if (typeDocument!=null && typeDocument.equals("1")) {
        	aRequest.setAttribute("typeDocumentInfo", "открытым документам");
        	aRequest.setAttribute("status", "(dd.isclose is null or cast(dd.isclose as int) =0) and ") ;        	
        } else if (typeDocument!=null && typeDocument.equals("2")) {
        	aRequest.setAttribute("typeDocumentInfo", "закрытым документам");
        	aRequest.setAttribute("status", "cast(dd.isclose as int) =1 and ") ;
        } else {
        	aRequest.setAttribute("typeDocumentInfo", "всем документам");
        	aRequest.setAttribute("status", "") ;        	
        }
        if (typeDate!=null && typeDate.equals("1")) {
        	aRequest.setAttribute("dateGroup","(select min(dr2.dateFrom) from disabilityrecord as dr2 where dr2.disabilitydocument_id=dd.id)") ;
        	aRequest.setAttribute("dateSearch","max") ;
        	aRequest.setAttribute("infoSearch"," Поиск по дате закрытия") ;
        } else if (typeDate!=null&&typeDate.equals("2")) {
        	aRequest.setAttribute("dateGroup","(select max(dr2.dateTo) from disabilityrecord as dr2 where dr2.disabilitydocument_id=dd.id)") ;
        	aRequest.setAttribute("dateSearch","min") ;
        	aRequest.setAttribute("infoSearch"," Поиск по дате открытия") ;
        } else {
        	aRequest.setAttribute("dateSearch","issued") ;
        	aRequest.setAttribute("infoSearch"," Поиск по дате выдачи") ;
        	aRequest.setAttribute("dateGroup", "dd.issueDate" );
        }
        if (noActuality!=null && noActuality.equals("1")) {
        	aRequest.setAttribute("statusNoActuality", "cast(dd.noActuality as int) =1 and ") ;
        	aRequest.setAttribute("infoSearchNoActuality"," испорченных") ;
        } else if (noActuality!=null&&noActuality.equals("2")) {
        	aRequest.setAttribute("infoSearchNoActuality"," неиспорченных") ;
        	aRequest.setAttribute("statusNoActuality", "(dd.noActuality is null or cast(dd.noActuality as int) =0) and ") ;        	
        } else {
        	aRequest.setAttribute("dateSearch","issued") ;
        	aRequest.setAttribute("infoSearchNoActuality"," все") ;
        	aRequest.setAttribute("statusNoActuality", "" );
        }
        if (form.getDisabilityReason()!=null && form.getDisabilityReason()>Long.valueOf(0)) {
        	aRequest.setAttribute("disReason", " and dd.disabilityReason_id="+form.getDisabilityReason()) ;
        } else {
        	aRequest.setAttribute("disReason", "") ;
        }
        
        if (form.getCloseReason()!=null && form.getCloseReason()>Long.valueOf(0)) {
        	aRequest.setAttribute("closeReason", " and dd.closeReason_id="+form.getCloseReason()) ;
        } else {
        	aRequest.setAttribute("closeReason", "") ;
        }
        if (form.getPrimarity()!=null && form.getPrimarity()>Long.valueOf(0)) {
        	aRequest.setAttribute("primarity", " and dd.primarity_id="+form.getPrimarity()) ;
        } else {
        	aRequest.setAttribute("primarity", "") ;
        }
        
		return aMapping.findForward("success");
	}
}