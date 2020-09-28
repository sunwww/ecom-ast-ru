package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.AdmissionMedCaseForm;
import ru.nuzmsh.util.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdmissionPreCreateInterceptor  implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Admission/CreateDoubleOpenHospitalMedCase")) {
    		List<MedCase> list = aContext.getEntityManager().createQuery("from MedCase where patient_id=:pat and DTYPE='HospitalMedCase'  and dateFinish is null and deniedHospitalizating_id is null and ( cast(ambulanceTreatment as int)=0 or ambulanceTreatment is null) ")
    			.setParameter("pat", aParentId).getResultList() ;
        	if (!list.isEmpty()) throw new IllegalArgumentException(
    				"У данного пациента есть открытый случай лечения в стационаре!!!"
    		);
    	}
    	AdmissionMedCaseForm form = (AdmissionMedCaseForm)aForm ;
    	form.setUsername(aContext.getSessionContext().getCallerPrincipal().getName());
        Date date = new Date();
        if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/ShortEnter")) {
        	form.setDateStart(DateFormat.formatToDate(date));
        	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            form.setEntranceTime(timeFormat.format(date));
        }
        if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Admission/CreateStatCardNumberByHand")) {
            form.addDisabledField("statCardNumber");
        }
    }

}