package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAcuityDiagnosis;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDiagnosisRegistrationType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPrimaryDiagnosis;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPriorityDiagnosis;
import ru.ecom.mis.ejb.form.medcase.hospital.DepartmentMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.DischargeMedCaseForm;
import ru.ecom.poly.ejb.domain.voc.VocIllnesPrimary;
import ru.nuzmsh.util.format.DateFormat;
import sun.awt.windows.ThemeReader;

public class DepartmentSaveInterceptor  implements IFormInterceptor{

	private final static Logger LOG = Logger.getLogger(DischargeMedCaseSaveInterceptor.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
    
	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		DepartmentMedCaseForm form=(DepartmentMedCaseForm)aForm ;
		if (CAN_DEBUG) LOG.debug("Проверка правильности введенных данных");
		EntityManager manager = aContext.getEntityManager() ;
		DepartmentMedCase medCase = (DepartmentMedCase)aEntity ;
		String dateFinish = "null" ;
		if (medCase.getDateFinish()!=null) {
			dateFinish = new StringBuilder().append("to_date('").append(DateFormat.formatToDate(medCase.getDateFinish())).append("','dd.mm.yyyy')").toString() ;
		}
		String timeFinish ="null" ;
		if (medCase.getDischargeTime()!=null) {
			timeFinish = new StringBuilder().append("'").append(DateFormat.formatToTime(medCase.getDischargeTime())).append("'").toString() ;
		}
		StringBuilder sqlupdate = new StringBuilder() ;
		
		if (!isDiagnosisAllowed(form.getClinicalMkb(), form.getDepartment(), form.getPatient(), form.getServiceStream(), null,null,manager)) {
			throw new IllegalStateException ("Данный диагноз запрещен в отделении!");
		}
		sqlupdate.append("update MedCase set dateFinish="+dateFinish+", dischargeTime="+timeFinish+" where parent_id=:parent and DTYPE='DepartmentMedCase' and (dateFinish is not null or (transferDate is null and dateFinish is null))") ;
		
		manager.createNativeQuery(sqlupdate.toString())
			.setParameter("parent", form.getId())
			.executeUpdate() ;
		setDiagnosis(manager, form.getId(), new StringBuilder().append(form.getClinicalMkb()).append("@#@ @#@").append(form.getClinicalDiagnos()).toString(), "4", "1") ;
		setDiagnosis(manager, form.getId(), form.getComplicationDiags(), "4", "4") ;
		setDiagnosis(manager, form.getId(), form.getConcomitantDiags(), "4","3") ;
		
	}
    public static void setDiagnosis(EntityManager aManager, Long aMedCase, String aListDiags, String aRegistrationType, String aPriority) {
    	VocDiagnosisRegistrationType vocDRT= (VocDiagnosisRegistrationType)getVocByCode(aManager,"VocDiagnosisRegistrationType",aRegistrationType);
		VocPriorityDiagnosis vocPrior = (VocPriorityDiagnosis)getVocByCode(aManager,"VocPriorityDiagnosis",aPriority) ;
		StringBuilder sql = new StringBuilder() ;
    	sql.append("select id from Diagnosis where medCase_id=").append(aMedCase)
    		.append(" and registrationType_id='").append(vocDRT.getId())
    		.append("' and priority_id='").append(vocPrior.getCode()).append("' order by id") ;
    	List<Object> list = aManager.createNativeQuery(sql.toString()).getResultList() ;
    	if (aListDiags!=null&&!aListDiags.equals("")) {
    		String[] otherServs = aListDiags.split("#@#");
    		if (otherServs.length>0) {
    			for (int i=0;i<otherServs.length;i++) {
    				String[] serv = otherServs[i].split("@#@") ;
    				if (list.size()>i) {
    					aManager.createNativeQuery("update Diagnosis set name=:name,idc10_id=:idc10 where id="+list.get(i))
    					.setParameter("name", serv[2]) 
    					.setParameter("idc10", Long.valueOf(serv[0])).executeUpdate() ;
    					
    				} else {
    					aManager.createNativeQuery("insert into Diagnosis (name,idc10_id,medCase_id,priority_id,registrationType_id) values (:name,'"+serv[0]+"','"+aMedCase+"','"+vocPrior.getId()+"','"+vocDRT.getId()+"')")
    					.setParameter("name", serv[2]) 
    						.executeUpdate() ;
    				}
    			}
    			for (int i=otherServs.length;i<list.size();i++) {
    				aManager.createNativeQuery("delete from Diagnosis where id="+list.get(i)).executeUpdate() ;
    			}
    		}
    	}
    }
    public static Object getVocByCode(EntityManager aManager,String aTable, String aCode) {
    	List list = aManager.createQuery(new StringBuilder().append("from ").append(aTable).append(" where code='").append(aCode).append("'").toString()).getResultList() ;
    	return list.size()>0?list.get(0):null ; 
    }

    private boolean setDiagnosisByType(boolean aNewIs, Diagnosis aDiag, VocDiagnosisRegistrationType aType, String aName, String aDate, Long aCode, HospitalMedCase aMedCase, EntityManager aManager, VocPriorityDiagnosis aPriorityType, Long aActuity) {
    	return setDiagnosisByType( aNewIs,  aDiag, aType, aName, aDate,  aCode, aMedCase, aManager, aPriorityType, aActuity,null);
    }
    
	private boolean setDiagnosisByType(boolean aNewIs, Diagnosis aDiag, VocDiagnosisRegistrationType aType, String aName, String aDate, Long aCode, HospitalMedCase aMedCase, EntityManager aManager, VocPriorityDiagnosis aPriorityType, Long aActuity, String aMkbAdc) {
		boolean resault = false ;
		if (!aNewIs) {
			aNewIs = aDiag.getRegistrationType()!=null && aDiag.getRegistrationType().equals(aType) 
				&& aDiag.getPriority()!=null && aDiag.getPriority().equals(aPriorityType)  
				//&& aDiag.getPriority()!=null && aDiag.getPrimary().equals(aPriorityType) 
				; 
		}
		if (aNewIs) {
			aDiag.setMkbAdc(aMkbAdc);
			aDiag.setName(aName);
			if (aCode!=null) {
				VocIdc10 mkb = aManager.find(VocIdc10.class, aCode) ;
				aDiag.setIdc10(mkb);
			}
			aDiag.setMedCase(aMedCase);
			try {aDiag.setEstablishDate(DateFormat.parseSqlDate(aDate));} 
			catch(Exception e) {
				
			}
			if (aDiag.getRegistrationType()==null) aDiag.setRegistrationType(aType);
			if (aDiag.getPriority()==null) aDiag.setPriority(aPriorityType) ;
			
			if(!isEmpty(aActuity)) {
				VocIllnesPrimary illnes = aManager.find(VocIllnesPrimary.class, aActuity) ;
				VocAcuityDiagnosis actuity = illnes.getIllnesType() ;
				VocPrimaryDiagnosis primary = illnes.getPrimary() ;
				//System.out.println("      actuity ="+actuity+""); 
				aDiag.setAcuity(actuity) ;
				aDiag.setPrimary(primary) ;
				aDiag.setIllnesPrimary(illnes) ;
				//VocAcuityDiagnosis actuity = aManager.find(VocAcuityDiagnosis.class, aActuity) ;
				//System.out.println("      actuity ="+actuity+""); 
				//aDiag.setAcuity(actuity) ;
			}
			resault = true ;
		}
		aManager.persist(aDiag) ;
		return resault ;
		
	}
	private static boolean isEmpty(Long aLong) {
	    return (aLong == null)||(aLong == 0) ;
	}
	private static boolean isEmpty(String aString) {
	    return (aString == null)||(aString.trim().equals("")) ;
	}
	
	
	
	public static boolean isDiagnosisAllowed(Long clinicalMkb, Long department, Long patient, Long serviceStream, Long diagnosisRegistrationType, Long diagnosisPriority, EntityManager manager) {
		if (clinicalMkb==null || clinicalMkb.equals(Long.valueOf(0))) return true ;
		if (diagnosisRegistrationType==null) {
			diagnosisRegistrationType = Long.valueOf(manager.createNativeQuery("select id from vocdiagnosisregistrationtype where code='4'").getResultList().get(0).toString());  //4
		}
		
		if (diagnosisPriority==null) {
			diagnosisPriority =Long.valueOf(manager.createNativeQuery("select id from vocprioritydiagnosis where code='1'").getResultList().get(0).toString()); //1
		}
		VocIdc10 mkb = manager.find(VocIdc10.class, clinicalMkb);
		boolean ret = true;
		boolean isPermitted = false;
		String sql = "select ni.id as f1, case when ldr.permissionrule='1' then '1' else '0' end as f2" +
			" ,case when '"+mkb.getCode()+"' between ni.fromidc10code and ni.toidc10code then '1' else '0' end as f3" +
			" from lpudiagnosisrule ldr" +
			" left join lpucontractnosologygroup lcng on lcng.lpudiagnosisrule = ldr.id" +
			" left join contractnosologygroup cng on cng.id=lcng.nosologygroup" +
			" left join nosologyinterval ni on ni.nosologygroup_id=cng.id" +
			" left join patient p on p.id=" + patient +
			" where ldr.department = "+department +
			" and ldr.id is not null" +
			" and ((ldr.diagnosisregistrationtype is null or ldr.diagnosisregistrationtype=0) or ldr.diagnosisregistrationtype="+diagnosisRegistrationType+")" +
			" and ((ldr.sex is null or ldr.sex=0) or ldr.sex=p.sex_id)" +
			" and ((ldr.diagnosispriority is null or ldr.diagnosispriority=0) or ldr.diagnosispriority="+diagnosisPriority+")" +
			" and ((ldr.servicestream is null or ldr.servicestream=0) or ldr.servicestream="+serviceStream+")";
			
		//System.out.println("=== DIAG, sql ="+sql);
			List<Object[]> o = manager.createNativeQuery(sql).getResultList();
		//	System.out.println("=== DIAG o = "+o.size());
			if (o==null||o.isEmpty()) {
				return true;
			} 
			boolean first = true;
			for (Object[] oo: o) {
			//	System.out.println("=== DIAG_n, o[] = "+oo[0]+":"+oo[1]+":"+oo[2]);
				if (first){
					isPermitted = oo[1].toString().equals("1")?true:false;
					first = false;
					ret = isPermitted?false:true;
				}
				boolean isEnter = oo[2].toString().equals("1")?true:false;
				if (isPermitted) {
					if (isEnter) {
						ret =  true;
						break;
					}
				} else {
					if (isEnter) {
						ret =  false;
						break;
					}
				}
				
			}
			return ret;
	}

}
