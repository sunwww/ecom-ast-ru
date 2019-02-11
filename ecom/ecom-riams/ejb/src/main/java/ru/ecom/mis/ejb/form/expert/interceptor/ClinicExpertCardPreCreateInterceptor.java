package ru.ecom.mis.ejb.form.expert.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.form.expert.DirectOfMedicalCommissionForm;
import ru.ecom.mis.ejb.form.medcase.DiagnosisForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseViewInterceptor;

import java.util.List;

public class ClinicExpertCardPreCreateInterceptor implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	Long parentId = ConvertSql.parseLong(aParentId) ;
    	DirectOfMedicalCommissionForm form = (DirectOfMedicalCommissionForm)aForm ;
    	List<Object[]> list =aContext.getEntityManager().createNativeQuery("select smo.patient_id,smo.ownerFunction_id,smo.department_id,pat.workPost from medcase as smo left join Patient pat on pat.id=smo.patient_id where smo.id=:parent").setParameter("parent", aParentId).getResultList() ;
    	if (!list.isEmpty()) {
    		Object[] row = list.get(0) ;
    		form.setPatient(ConvertSql.parseLong(row[0])) ;
    		form.setOrderFunction(ConvertSql.parseLong(row[1])) ;
    		form.setOrderLpu(ConvertSql.parseLong(row[2])) ;
    		form.setProfession(row[3]!=null?""+row[3]:"") ;
    	}
    	DiagnosisForm frm = DischargeMedCaseViewInterceptor.getDiagnosis(aContext.getEntityManager(), parentId, "4", "1", true) ;
    	if (frm!=null) {
    		form.setMainDiagnosis(frm.getIdc10()) ;
    	}
    }
}