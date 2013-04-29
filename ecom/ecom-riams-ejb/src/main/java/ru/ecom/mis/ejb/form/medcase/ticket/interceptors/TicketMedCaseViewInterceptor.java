package ru.ecom.mis.ejb.form.medcase.ticket.interceptors;

import java.io.StringWriter;
import java.util.List;

import javax.persistence.EntityManager;

import org.json.JSONException;
import org.json.JSONWriter;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.medcase.ShortMedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPriorityDiagnosis;
import ru.ecom.mis.ejb.form.medcase.DiagnosisForm;
import ru.ecom.mis.ejb.form.medcase.hospital.HospitalMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseViewInterceptor;
import ru.ecom.mis.ejb.form.medcase.ticket.TicketMedCaseForm;

public class TicketMedCaseViewInterceptor  implements IFormInterceptor{

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		TicketMedCaseForm form = (TicketMedCaseForm)aForm ;
		EntityManager manager = aContext.getEntityManager() ;
		ShortMedCase smc = (ShortMedCase)aEntity ;
		VocPriorityDiagnosis vocConcomType = (VocPriorityDiagnosis)DischargeMedCaseSaveInterceptor.getVocByCode(manager,"VocPriorityDiagnosis","3") ;

		long id = form.getId() ;
		DiagnosisForm frm ;
		//Clinical
		frm = DischargeMedCaseViewInterceptor.getDiagnosis(manager, id, null, "1", false) ;
		if (frm!=null){
			form.setConcludingDiagnos(frm.getName());
			if (frm.getIdc10()!=null) form.setConcludingMkb(frm.getIdc10()) ;
			if (frm.getIllnesPrimary()!=null) form.setConcludingActuity(frm.getIllnesPrimary()) ;
			if (frm.getTraumaType()!=null) form.setConcludingTrauma(frm.getTraumaType()) ;
		}
		//TODO
		//form.getConcomitantDiseases()
		long aIdEntity = smc.getId() ;
		if (aIdEntity>Long.valueOf(0)) ;
		form.setConcomitantDiseases( getArray(manager,"Diagnosis","idc10_id"
				,new StringBuilder().append("medCase_id='").append(aIdEntity).append("'").append(" and priority_id='").append(vocConcomType.getId()).append("'").toString()
				)) ;
		form.setMedServices(getArray(manager,"MedCase","medService_id"
				,new StringBuilder().append("parent_id='").append(aIdEntity).append("'").append(" and dtype='ServiceMedCase'").toString()
			)) ;
	}
	private String  getArray(EntityManager aManager
			, String aTableName
			, String aFieldChildren, String aWhere){
		StringWriter out = new StringWriter();
		JSONWriter j = new JSONWriter(out);
		try {
			j.object();

			j.key("childs").array();

			StringBuilder sql = new StringBuilder() ;
			sql.append("select ").append(aFieldChildren).append(" from ").append(aTableName).append(" where ").append(aWhere) ;
			//System.out.println(sql) ;
			List<Object> list = aManager.createNativeQuery(sql.toString()).getResultList(); 
			for (Object child : list) {
				j.object().key("value").value(ConvertSql.parseLong(child));
				j.endObject();
			}
			j.endArray();
	
			j.endObject();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return out.toString() ;
	}
}
	
