package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import java.util.List;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.DischargeMedCaseForm;
import ru.nuzmsh.util.format.DateFormat;


public class DischargeMedCaseViewInterceptor implements IFormInterceptor{

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		DischargeMedCaseForm form = (DischargeMedCaseForm)aForm ;
		HospitalMedCase medCase = (HospitalMedCase)aEntity ;
		EntityManager manager = aContext.getEntityManager() ;
		long id = form.getId() ;
		//System.out.println("----------------1") ;
		if (medCase.getDateFinish()==null){
			if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Discharge/CheckPrintAllProtocol")) {
				StringBuilder sql = new StringBuilder() ;
				sql.append("select count(*) from diary p left join medcase m on m.id=p.medcase_id where ((m.id='"+id+"' and m.dtype='HospitalMedCase') or (m.parent_id='"+id+"' and m.dtype='DepartmentMedCase')) and p.printDate is null") ;
				Object obj = manager
					.createNativeQuery(sql.toString()).getSingleResult() ;
				Long count = ConvertSql.parseLong(obj) ;
				if (count>0){
					throw new IllegalArgumentException("Необходимо перед выпиской распечатать все протоколы! <a href='printProtocolsBySLS.do?stNoPrint=selected&id="+id+"'>Есть "+(count.intValue())+" нераспечатанный(х) протокол(ов)</a>");
				}
			}
			if (medCase.getChildMedCase().size()==0)  
				throw new IllegalArgumentException("Необходимо перед выпиской создать случай лечения в отделении");
			//System.out.println("----------------1") ;
			
			@SuppressWarnings("unchecked")
			List<DepartmentMedCase> list = manager
				.createQuery("from MedCase where DTYPE='DepartmentMedCase' and parent_id=:hosp and ((dateFinish is not null) or (dateFinish is null and transferDate is null))")
				.setParameter("hosp",medCase.getId()).getResultList();
			
			if (list.size()==0) {
				throw new IllegalArgumentException("Нет случая лечения в отделении c датой выписки или без перевода");
			} 
			//System.out.println("----------------1") ;
			DepartmentMedCase dischargeDepartment = list.get(0) ;
			form.setDateFinish(DateFormat.formatToDate(dischargeDepartment.getDateFinish())) ;
			form.setDischargeTime(DateFormat.formatToTime(dischargeDepartment.getDischargeTime())) ;
			List<MedCase> listdep = medCase.getChildMedCase();
			//System.out.println("----------------1") ;
			for (MedCase m:listdep) {
				if (m instanceof DepartmentMedCase) {
					DepartmentMedCase dep = (DepartmentMedCase)m ;
					List<Diagnosis> diags= dep.getDiagnosis() ;
					for (Diagnosis diag:diags) {
						
						if (diag.getRegistrationType()!=null && diag.getPriority()!=null) {
							
							String regType = diag.getRegistrationType().getCode() ;
							String prior = diag.getPriority()!=null?diag.getPriority().getCode():"" ;
							Long mkb = diag.getIdc10()!=null?diag.getIdc10().getId():null ;
							Long illnes = diag.getIllnesPrimary()!=null?diag.getIllnesPrimary().getId():null;

							// Concluding
							if (regType.equals("3")&& prior.equals("1")){
								form.setConcludingDiagnos(diag.getName());
								if (mkb!=null) form.setConcludingMkb(mkb) ;
							}
						    //Clinical
							if ( regType.equals("4") && prior.equals("1")){
								form.setClinicalDiagnos(diag.getName());
								if (mkb!=null) form.setClinicalMkb(mkb) ;
								if(illnes!=null) form.setClinicalActuity(illnes) ;
							}
						    //Pathanatomical
							if (regType.equals("5") && prior.equals("1")) {
								form.setPathanatomicalDiagnos(diag.getName());
								if (mkb!=null) form.setPathanatomicalMkb(mkb) ;
							}	
							//Concomitant
							if (regType.equals("4") && prior.equals("3")) {
								form.setConcomitantDiagnos(diag.getName());
								if (mkb!=null) form.setConcomitantMkb(mkb) ;
							}	
							
						}
					}
				}
			}
			
		}
		//System.out.println("----------------1") ;
		if (medCase!=null && medCase.getDiagnosis()!=null) {
			List<Diagnosis> diagList = medCase.getDiagnosis() ;
			for(Diagnosis diag:diagList) {
				if (diag.getRegistrationType()!=null && diag.getPriority()!=null) {
					String regType = diag.getRegistrationType().getCode() ;
					String prior = diag.getPriority().getCode() ;
					Long mkb = diag.getIdc10()!=null?diag.getIdc10().getId():null ;
					//Long actuity = diag.getAcuity()!=null?diag.getAcuity().getId():null;
					Long illnes = diag.getIllnesPrimary()!=null?diag.getIllnesPrimary().getId():null;
					
					// Entrance
					if (regType.equals("1")&& prior.equals("1")){
						form.setEntranceDiagnos(diag.getName());
						if (mkb!=null) form.setEntranceMkb(mkb) ;
					}
					// Concluding
					if (regType.equals("3")&& prior.equals("1")){
						form.setConcludingDiagnos(diag.getName());
						if (mkb!=null) form.setConcludingMkb(mkb) ;
					}
				    //Clinical
					if ( regType.equals("4") && prior.equals("1")){
						form.setClinicalDiagnos(diag.getName());
						if (mkb!=null) form.setClinicalMkb(mkb) ;
						//if (actuity!=null) form.setClinicalActuity(actuity) ;
						if (illnes!=null) form.setClinicalActuity(illnes) ;
					}
				    //Pathanatomical
					if (regType.equals("5") && prior.equals("1")) {
						form.setPathanatomicalDiagnos(diag.getName());
						if (mkb!=null) form.setPathanatomicalMkb(mkb) ;
					}	
					//Concomitant
					if (regType.equals("4") && prior.equals("3")) {
						form.setConcomitantDiagnos(diag.getName());
						if (mkb!=null) form.setConcomitantMkb(mkb) ;
					}	
				}
			}
		}
	}
	

}
