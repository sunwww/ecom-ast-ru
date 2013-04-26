package ru.ecom.mis.ejb.form.medcase.ticket.interceptors;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.ejb.util.IFormInterceptor;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.domain.medcase.ShortMedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPriorityDiagnosis;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTraumaType;
import ru.ecom.mis.ejb.form.medcase.DiagnosisForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseViewInterceptor;
import ru.ecom.mis.ejb.form.medcase.ticket.TicketMedCaseForm;
import ru.ecom.poly.ejb.domain.voc.VocIllnesPrimary;
import ru.nuzmsh.util.StringUtil;

public class TicketMedCaseSaveInterceptor implements IFormInterceptor {
	 
	private final static Logger LOG = Logger.getLogger(DischargeMedCaseSaveInterceptor.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
    public void intercept(IEntityForm aForm, Object aEntity, EntityManager aManager) {
    	TicketMedCaseForm form = (TicketMedCaseForm)aForm ;
		ShortMedCase smc = (ShortMedCase)aEntity ;
		long id = form.getId() ;
		DiagnosisForm frm ;
		//Clinical
		VocPriorityDiagnosis vocPriorType = (VocPriorityDiagnosis)DischargeMedCaseSaveInterceptor.getVocByCode(aManager,"VocPriorityDiagnosis","1") ;
		VocPriorityDiagnosis vocConcomType = (VocPriorityDiagnosis)DischargeMedCaseSaveInterceptor.getVocByCode(aManager,"VocPriorityDiagnosis","3") ;
		
		frm = DischargeMedCaseViewInterceptor.getDiagnosis(aManager, id, null, "1", false) ;
		VocIllnesPrimary vip = isEmpty(form.getConcludingActuity())?null:aManager.find(VocIllnesPrimary.class, form.getConcludingActuity()) ;
		VocTraumaType vtt = isEmpty(form.getConcludingTrauma())?null:aManager.find(VocTraumaType.class, form.getConcludingTrauma()) ;
		VocIdc10 mkb = isEmpty(form.getConcludingMkb())?null:aManager.find(VocIdc10.class, form.getConcludingMkb()) ;
		Diagnosis diag = null ;
		if (frm!=null && frm.getId()>Long.valueOf(0)){
			diag = aManager.find(Diagnosis.class, frm.getId()) ;
		} else {
			diag = new Diagnosis() ;
			diag.setMedCase(smc) ;
			diag.setPriority(vocPriorType) ;
		}
		diag.setIllnesPrimary(vip) ;
		diag.setTraumaType(vtt) ;
		diag.setIdc10(mkb) ;
		diag.setName(form.getConcludingDiagnos()) ;
		diag.setEstablishDate(smc.getDateStart()) ;
		aManager.persist(diag) ;
		// Сопутствующий диагноз
		String addSql = " medCase_id="+smc.getId()+" and priority_id='"+vocConcomType.getId()+"'" ;
		saveArray(aManager,form.getConcomitantDiseases(),addSql, "Diagnosis", "medCase_id,priority_id"
				, "'"+smc.getId()+"','"+vocConcomType.getId()+"'" , "idc10_id" ) ;
		// Медицинские услуги
		saveArray(aManager,form.getMedServices(),"parent_id="+smc.getId()+" and dtype='ServiceMedCase' ","MedCase","parent_id,DTYPE,noActuality"
				,"'"+smc.getId()+"','ServiceMedCase','0'","medService_id") ;
	}
    
    private void saveArray(EntityManager aManager, String aJsonString,String aAddSql,String aTableName
    		, String aFieldParent,String aIdEntity,String aFieldChildren) {
    	try {
			JSONObject obj = new JSONObject(aJsonString) ;
			JSONArray ar = obj.getJSONArray("childs");
			StringBuilder ids = new StringBuilder() ;
			StringBuilder sql = new StringBuilder () ;

			
			for (int i = 0; i < ar.length(); i++) {
				JSONObject child = (JSONObject) ar.get(i);
				String jsonId = String.valueOf(child.get("value"));
				if (!StringUtil.isNullOrEmpty(jsonId) || "0".equals(jsonId)) {
					//System.out.println("    id="+jsonId) ;
					ids.append(",").append(jsonId) ;
					sql = new StringBuilder() ;
					sql.append("select count(*) as cnt from ").append(aTableName).append(" where ").append(aAddSql).append(" and ").append(aFieldChildren).append("='").append(jsonId).append("'") ;
					System.out.println(sql) ;
					List<Object> count = aManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
					System.out.println("!!!!!!!!!count="+(count.size()==0?"0000":count.get(0))) ;
					if (count.isEmpty()|| (!count.isEmpty()&&ConvertSql.parseLong(count.get(0))<Long.valueOf(1))) {
						sql = new StringBuilder() ;
						
						sql.append("insert into ").append(aTableName).append(" ( ")
							.append(aFieldChildren).append(",").append(aFieldParent)
							.append(") values ('")
							.append(jsonId).append("',").append(aIdEntity).append(")") ;
						try {
							aManager.createNativeQuery(sql.toString()).executeUpdate() ;
						} catch (Exception e) {
							sql = new StringBuilder() ;
							
							sql.append("insert into ").append(aTableName).append(" (id, ")
								.append(aFieldChildren).append(",").append(aFieldParent)
								.append(") values (nextval('").append(aTableName.toLowerCase()).append("_sequence'),'")
								.append(jsonId).append("',").append(aIdEntity).append(")") ;
							
						}
					}
				}
			}
			if (ids.length()>0) {
				sql = new StringBuilder() ;
				sql.append("delete from ").append(aTableName)
					.append(" where ").append(aAddSql).append(" and ").append(aFieldChildren)
					.append(" not in (").append(ids.substring(1)+") ") ;
				aManager.createNativeQuery(sql.toString()).executeUpdate();
			} else {
			}
		} catch (JSONException e) {
			StringBuilder sql = new StringBuilder() ;
			sql.append("delete from ").append(aTableName)
				.append(" where ").append(aAddSql);
			aManager.createNativeQuery(sql.toString()).executeUpdate() ;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public static boolean isEmpty(Long aLong) {
	    return (aLong == null)||(aLong == 0) ;
	}
	public static boolean isEmpty(String aString) {
	    return (aString == null)||(aString.trim().equals("")) ;
	}


}
