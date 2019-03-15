package ru.ecom.mis.ejb.form.medcase.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.Visit;
import ru.ecom.mis.ejb.form.medcase.ticket.TicketMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.ticket.interceptors.TicketMedCaseViewInterceptor;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.EntityManager;
import java.util.List;

public class DirectionViewInterceptor implements IFormInterceptor {

    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
    	TicketMedCaseForm form = (TicketMedCaseForm) aForm ;
    	Visit entity = (Visit) aEntity ;
    	EntityManager manager = aContext.getEntityManager() ;
    	if (entity.getServiceStream()!=null && (entity.getServiceStream().getCode()==null || entity.getServiceStream().getCode().equals("1"))) {
	    	String datePlan = form.getDateStart()!=null && !form.getDateStart().equals("")?form.getDateStart():entity.getDatePlan()!=null?DateFormat.formatToDate(entity.getDatePlan().getCalendarDate()):"" ;
	    	if (datePlan.equals("")) {
	    		form.setInfoByPolicy("НЕТ ДАТЫ НАПРАВЛЕНИЯ");
	    	} else {
	    	List<Object[]> polerr = manager
	    			.createNativeQuery("SELECT id " 
	                      +"FROM MedPolicy where patient_id='"+(entity.getPatient()!=null?entity.getPatient().getId():"0")+"' "
	                      +"AND to_date('"+datePlan+"','dd.mm.yyyy') between actualDateFrom and coalesce(actualDateTo,CURRENT_DATE) "
	                      +"and DTYPE like 'MedPolicyOmc%'")
	    				.getResultList();
	    			//form.setNotice(form.getNotice()+form.getId() +polerr[0]+"----"+polerr.length+polerr.toString()) ;
				if(polerr.isEmpty()) form.setInfoByPolicy("НЕТ АКТУАЛЬНОГО ПОЛИСА ОМС");
				if(polerr.size()>1) form.setInfoByPolicy("БОЛЬШЕ ОДНОГО АКТУАЛЬНОГО ПОЛИСА ОМС");
	    	}
    	}
    	if (entity.getTimePlan()!=null&&entity.getTimePlan().getPatientComment()!=null) {
    		form.setPatientComment(entity.getTimePlan().getPatientComment());
		}
    	form.setMedServices(TicketMedCaseViewInterceptor.getArray(manager,"MedCase","medService_id"
				, "parent_id='" + entity.getId() + "'" + " and dtype='ServiceMedCase'"
			)) ;
	}

}
