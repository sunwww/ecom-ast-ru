package ru.ecom.mis.ejb.form.medcase.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.form.medcase.poly.DirectionMedCaseForm;

import javax.persistence.EntityManager;
import java.util.List;

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
		//Milamesher mc.dischargetime вместо mc.dateFinish для учёта предварительной выписки
		//врач может направлять сам к себе
		List<Object[]> hospInfo = manager.createNativeQuery("select list(to_char(mc.dateStart,'dd.mm.yyyy')||' '||ml.name),list(ml.name) as mlname from MedCase mc left join MisLpu ml on ml.id=mc.department_id " +
				" left join vocdeniedhospitalizating vdh on vdh.id=mc.deniedhospitalizating_id where mc.patient_id=:patientId and mc.dtype='HospitalMedCase' and mc.dischargetime is null " +
				"and (mc.deniedHospitalizating_id is null or vdh.code='IN_PIGEON_HOLE') group by mc.patient_id").setParameter("patientId",aParentId).getResultList() ;
		String errorInfo = null ;
		if (!hospInfo.isEmpty()) {
			errorInfo = "Пациент ГОСПИТАЛИЗИРОВАН в стационар. "+hospInfo.get(0)[0] ;

			if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Direction/CreateDirectionInHospital")) {
				throw new IllegalArgumentException(errorInfo+". Создание направления возможно только через лист назначения!" ) ;
	        }
			
			if (polerr.size()!=1) errorInfo="<br/>"+errorInfo ;
		}
		//form.setNotice(form.getNotice()+form.getId() +polerr[0]+"----"+polerr.length+polerr.toString()) ;
		if(polerr.isEmpty()) errorInfo="НЕТ АКТУАЛЬНОГО ПОЛИСА ОМС"+(errorInfo!=null ? errorInfo : "");
		if(polerr.size()>1) errorInfo="БОЛЬШЕ ОДНОГО АКТУАЛЬНОГО ПОЛИСА ОМС"+(errorInfo!=null ? errorInfo : "");
		form.setInfoByPolicy(errorInfo) ;

	// 
	
    }
}