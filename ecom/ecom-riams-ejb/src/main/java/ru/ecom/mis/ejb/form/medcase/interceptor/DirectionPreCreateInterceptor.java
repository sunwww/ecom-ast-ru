package ru.ecom.mis.ejb.form.medcase.interceptor;

import java.util.List;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.Visit;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.form.medcase.DiagnosisForm;
import ru.ecom.mis.ejb.form.medcase.poly.DirectionMedCaseForm;
import ru.nuzmsh.forms.response.FormMessage;
import ru.nuzmsh.util.format.DateFormat;

public class DirectionPreCreateInterceptor implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	DirectionMedCaseForm form = (DirectionMedCaseForm)aForm ;
    	
	    	/*if (parent.getPatient()!=null) {
	    		form.setPatient(parent.getPatient().getId()) ;
	    	}*/
    	//Date date = new Date() ;
		List<Object[]> polerr = manager.createNativeQuery("SELECT id " 
		                              +" FROM MedPolicy where patient_id='"+aParentId+"' "
		                              +" and CURRENT_DATE >= actualDateFrom and (actualDateTo is null or actualDateTo>=CURRENT_DATE) "
		                              +"  and DTYPE like 'MedPolicyOmc%'")
			//.setParameter("patid", form.getId())
			.getResultList();
		List<Object[]> hospInfo = manager.createNativeQuery("select list(to_char(mc.dateStart,'dd.mm.yyyy')||' '||ml.name),list(ml.name) as mlname from MedCase mc left join MisLpu ml on ml.id=mc.department_id where mc.patient_id='"+aParentId+"' and mc.dtype='HospitalMedCase' and mc.dateFinish is null group by mc.patient_id").getResultList() ;
		String errorInfo = null ;
		if (hospInfo.size()>0) {
			errorInfo = "Пацинет ГОСПИТАЛИЗИРОВАН в стационар "+hospInfo.get(0)[0] ;
			if (polerr.size()!=1) errorInfo="<br/>"+errorInfo ;
		}
		//form.setNotice(form.getNotice()+form.getId() +polerr[0]+"----"+polerr.length+polerr.toString()) ;
		if(polerr.size()==0) errorInfo="НЕТ АКТУАЛЬНОГО ПОЛИСА ОМС"+errorInfo!=null?errorInfo:"";
		if(polerr.size()>1) errorInfo="БОЛЬШЕ ОДНОГО АКТУАЛЬНОГО ПОЛИСА ОМС"+errorInfo!=null?errorInfo:"";
		form.setInfoByPolicy(errorInfo) ;

	// 
	
    }
}