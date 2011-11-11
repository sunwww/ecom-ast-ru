package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.AdmissionMedCaseForm;
import ru.nuzmsh.util.format.DateFormat;

public class AdmissionPreCreateInterceptor  implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Admission/CreateDoubleOpenHospitalMedCase")) {
    		List<MedCase> list = aContext.getEntityManager().createQuery("from MedCase where patient_id=:pat and DTYPE='HospitalMedCase'  and dateFinish is null and deniedHospitalizating_id is null and ( cast(ambulanceTreatment as int)=0 or ambulanceTreatment is null) ")
    			.setParameter("pat", aParentId).getResultList() ;
        	if (list.size()>0) throw new IllegalArgumentException(
    				"У данного пациента есть открытый случай лечения в стационаре!!!"
    		);
    	}
    	AdmissionMedCaseForm form = (AdmissionMedCaseForm)aForm ;
    	form.setUsername(aContext.getSessionContext().getCallerPrincipal().toString());
        Date date = new Date();
        form.setDateStart(DateFormat.formatToDate(date));
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        form.setEntranceTime(timeFormat.format(date));
        if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Admission/CreateStatCardNumberByHand")) {
            form.addDisabledField("statCardNumber");
        }
    }

}