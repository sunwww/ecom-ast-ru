package ru.ecom.mis.ejb.form.medcase.ticket.interceptors;

import org.json.JSONException;
import org.json.JSONWriter;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.medcase.ShortMedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPriorityDiagnosis;
import ru.ecom.mis.ejb.form.medcase.DiagnosisForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseViewInterceptor;
import ru.ecom.mis.ejb.form.medcase.ticket.TicketMedCaseForm;

import javax.persistence.EntityManager;
import java.io.StringWriter;
import java.util.List;

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
			if (frm.getMkbAdc()!=null) form.setMkbAdc(frm.getMkbAdc()) ;
		}
		//TODO
		//form.getConcomitantDiseases()
		long aIdEntity = smc.getId() ;
		if (aIdEntity>0L) {
			form.setConcomitantDiseases( getArray(manager,"Diagnosis","idc10_id"
					, "medCase_id='" + aIdEntity + "'" + " and priority_id='" + vocConcomType.getId() + "'"
					)) ;
			/*form.setMedServices(getArray(manager,"MedCase","medService_id"
					,new StringBuilder().append("parent_id='").append(aIdEntity).append("'").append(" and dtype='ServiceMedCase'").toString()
				)) ;*/
			form.setMedServices(getMedServiceArray(form, manager)) ;
			List<Object[]> listac = manager.createNativeQuery("select id,numbercard, cast(callReceiveTime as varchar(5)) as receiveTime, cast(arrivalTime as varchar(5)) as arrivalTime from ambulanceCard where medcase_id="+aIdEntity).getResultList() ;
			if (!listac.isEmpty()) {
				form.setAmbulanceCard(""+listac.get(0)[1]) ;
				form.setCallReceiveTime(""+listac.get(0)[2]);
				form.setArrivalTime(""+listac.get(0)[3]);
			}
		}
    	List<Object[]> list =aContext.getEntityManager().createNativeQuery("select pat.categoryChild_id,mc.id from medcase mc left join patient pat on pat.id=mc.patient_id where mc.id='"+aIdEntity+"'")
    				.setMaxResults(1).getResultList() ;
    	if (list.size()>0) {
    		Object[] row = list.get(0) ;
    		if (row[0]!=null)form.setCategoryChild(ConvertSql.parseLong(row[0])) ;
    	}

	}
	public static String  getMedServiceArray(TicketMedCaseForm aForm, EntityManager aManager){
		StringBuilder sql = new StringBuilder() ;
		StringBuilder res = new StringBuilder() ;
		sql.append("select mc.medservice_id,ms.code||' '||ms.name,mc.uet,mc.ordernumber,mc.medserviceamount from MedCase mc left join MedService ms on ms.id=mc.medservice_id where mc.parent_id='").append(aForm.getId()).append("' and mc.dtype='ServiceMedCase' order by mc.id") ;
		List<Object[]> list = aManager.createNativeQuery(sql.toString()).getResultList();
		for (Object[] child : list) {
			res.append(child[0]).append("@").append(child[2]).append("@") ;
			res.append(child[3]).append("@").append(child[4]).append("@") ;
			res.append(child[1]).append("##") ;
		}
			
			
		return res.length()>1 ? res.substring(0,res.length()-2) : "" ;
	}
	public static String  getArray(EntityManager aManager
			, String aTableName
			, String aFieldChildren, String aWhere){
		StringWriter out = new StringWriter();
		JSONWriter j = new JSONWriter(out);
		try {
			j.object();

			j.key("childs").array();

			List<Object> list = aManager.createNativeQuery("select " + aFieldChildren + " from " + aTableName + " where " + aWhere).getResultList();
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
	
