package ru.ecom.mis.ejb.form.medcase.interceptor;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.Visit;
import ru.ecom.mis.ejb.form.medcase.ShortMedCaseForm;
import ru.nuzmsh.util.format.DateFormat;

public class DirectionViewInterceptor implements IFormInterceptor {

    private final static Logger LOG = Logger.getLogger(DirectionViewInterceptor.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
    	ShortMedCaseForm form = (ShortMedCaseForm) aForm ;
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
		if(polerr.size()==0) form.setInfoByPolicy("НЕТ АКТУАЛЬНОГО ПОЛИСА ОМС");
		if(polerr.size()>1) form.setInfoByPolicy("БОЛЬШЕ ОДНОГО АКТУАЛЬНОГО ПОЛИСА ОМС");
    	}
    	}
	}

}
