package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocHospType;
import ru.ecom.mis.ejb.form.medcase.DiagnosisForm;
import ru.ecom.mis.ejb.form.medcase.hospital.DepartmentMedCaseForm;
import ru.nuzmsh.util.format.DateConverter;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Проверка закрыт ли случай стационарного лечения
 * если да, то есть ли права на открытие случая ????
 * @author stkacheva
 */
public class DepartmentMedCaseCreateInterceptor implements IParentFormInterceptor{
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	DepartmentMedCaseForm form = (DepartmentMedCaseForm) aForm ;
    	HospitalMedCase parentSSL = manager.find(HospitalMedCase.class, aParentId) ;
    	boolean isOwnerFunction = aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/OwnerFunction") ;
    	if (parentSSL!=null) {
    		if (parentSSL.getDeniedHospitalizating()!=null) {
    			throw new IllegalStateException("При отказе от госпитализации случай лечения в отделении (СЛО) не заводится!!! ВСЕ осмотры и назначения оформляются в госпитализации (СЛС)");
    		} 
    		if (parentSSL.getDateFinish()!=null && parentSSL.getDischargeTime()!=null) {
    			throw new IllegalStateException("Нельзя добавить случай лечения в отделении (СЛО) в закрытый случай стационарного лечения (ССЛ) !!!") ;
    		}
    		if (parentSSL.getEmergency()!=null && parentSSL.getEmergency()) {
    			form.setEmergency(Boolean.TRUE) ;
    		}
    		if (parentSSL.getDeniedHospitalizating()!=null) {
                throw new IllegalStateException("При отказе в госпитализации нельзя заводить случай лечения в отделении") ;
               
    		}
    		Object listDep = manager
    			.createNativeQuery("select count(*) from MedCase where parent_id = :parentId and DTYPE='DepartmentMedCase' ")
    			.setParameter("parentId", aParentId)
    			.getSingleResult() ;
    		if (listDep!=null && ConvertSql.parseLong(listDep).equals(0L)) {
    			prepareForCreationFirstSlo(form, parentSSL,manager,isOwnerFunction) ;
    		} else {
    			prepareForCreationNextSlo(form,parentSSL,manager) ;
    		}
    		if (parentSSL.getLpu()!=null) form.setLpu(parentSSL.getLpu().getId());
    		form.setTypeCreate() ;
    		if (isOwnerFunction
    				&&form.getDepartment()!=null&&form.getDepartment()>0L) {
        		String username = aContext.getSessionContext().getCallerPrincipal().toString() ;
            	List<Object[]> listwf =  manager.createNativeQuery("select wf.id as wfid,w.id as wid from WorkFunction wf left join Worker w on w.id=wf.worker_id left join SecUser su on su.id=wf.secUser_id where su.login = :login and w.lpu_id=:lpu and wf.id is not null") 
        				.setParameter("login", username)
        				.setParameter("lpu", form.getDepartment()) 
        				.setMaxResults(1)
        				.getResultList() ;
        		if (!listwf.isEmpty()) {
        			Object[] wf = listwf.get(0) ;
        			form.setOwnerFunction(ConvertSql.parseLong(wf[0])) ;
        		}
        	}
    		 
    	} else {
    		throw new IllegalStateException("Невозможно добавить случай лечения. Сначала надо определить  случай стационарного лечения (ССЛ)") ;
    	}
    	boolean noCheckPregnancy = aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Discharge/DontCheckPregnancy") ;

		if (form.getPrevMedCase()!=null) {
			DepartmentMedCase prevMedCase = manager.find(DepartmentMedCase.class, form.getPrevMedCase());
			if (prevMedCase.getDepartment().getIsMaternityWard()!=null && prevMedCase.getDepartment().getIsMaternityWard()
					&& !isWithoutChildBirthDiagnosis(prevMedCase)) {
				if (!isCalcCardBornExists(manager, prevMedCase) && !isDsO82(manager, form.getPrevMedCase())) {
					throw new IllegalStateException("Перевод из отделения невозможен, т.к.не создано вычисление риска ВТЭО после родов!");
				}
				if (!noCheckPregnancy && !isPregnancyExists(manager, prevMedCase) && !isMisbirthClassExists(manager, form.getPrevMedCase())) {
					throw new IllegalStateException("Перевод из отделения невозможен, т.к.не заполнены данные по родам либо данные по выкидышу!");
				}
				/*if (!isMisbirthClassExists(manager, form.getPrevMedCase()) &&!isRobsonClassExists(manager, prevMedCase )) {
					throw new IllegalStateException ("Перевод из отделения невозможен, т.к.не создана классификация Робсона!");
				}*/
			}
		}
	}
    //Milamesher не O82 ли диагноз (тогда следующая проверка не нужна)
	private static boolean isDsO82(EntityManager aManager, Long aMedCaseId) {
		if (aMedCaseId==null) {return true;}
		DepartmentMedCase parentSLO = aManager.find(DepartmentMedCase.class, aMedCaseId) ;
		if (parentSLO.getDepartment()!=null && parentSLO.getDepartment().getIsMaternityWard()!=null && parentSLO.getDepartment().getIsMaternityWard()){
			String sql = "select count(idc.id) from vocidc10 idc" +
					" left join diagnosis ds on ds.idc10_id=idc.id" +
					" left join medcase mc on mc.id=ds.medcase_id" +
					" where idc.code like 'O82%' and mc.id=:medcase";
			Object list = aManager.createNativeQuery(sql).setParameter("medcase",aMedCaseId).getSingleResult();
			return Long.valueOf(list.toString())>0;
		} else {
			return true;
		}
	}
	//Milamesher существует ли вычисление риска ВТЭО в госпитализации
	private static boolean isCalcCardBornExists(EntityManager aManager, MedCase aMedCase) {
		String sql = "select count(cr.id) from calculationsresult cr" +
				" left join medcase dmc on dmc.id=cr.departmentmedcase_id" +
				" left join medcase hmc on hmc.id=dmc.parent_id" +
				" where hmc.id=(select parent_id from medcase where id=:medcaseId)" +
				" and calculator_id=15";
		Object list = aManager.createNativeQuery(sql).setParameter("medcaseId",aMedCase.getId()).getSingleResult();
		return Long.valueOf(list.toString())>0;

	}
    //Milamesher 10122018 #131 существует ли классификация Робсона
	private static boolean isRobsonClassExists(EntityManager aManager, DepartmentMedCase aMedCase) {
		//Milmesher 28122018 классификация необязательная для след. диагнозов
		Diagnosis diagnosis = aMedCase.getMainDiagnosis();
		ArrayList<String> withoutChildBirth = getDiagosisWithoutChldBirth();
		if (diagnosis == null || withoutChildBirth.contains(diagnosis.getIdc10().getCode())) return true;
		if (aMedCase.getDepartment()!=null && aMedCase.getDepartment().getIsMaternityWard()!=null && aMedCase.getDepartment().getIsMaternityWard()){
			String sql = "select count(id) from robsonclass where medcase_id= "+aMedCase.getId();
			Object list = aManager.createNativeQuery(sql).getSingleResult();
			return Long.valueOf(list.toString())>0;
		} else {
			return true;
		}
	}
	//Milamesher 24122018 #132 существует ли выкидыш
	private static boolean isMisbirthClassExists(EntityManager aManager, Long aMedCaseId) {
		if (aMedCaseId==null) {return true;}
		DepartmentMedCase parentSLO = aManager.find(DepartmentMedCase.class, aMedCaseId) ;
		if (parentSLO.getDepartment()!=null && parentSLO.getDepartment().getIsMaternityWard()!=null && parentSLO.getDepartment().getIsMaternityWard()){
			String sql = "select count(id) from misbirth where medcase_id=:medcaseId";
			Object list = aManager.createNativeQuery(sql).setParameter("medcaseId",aMedCaseId).getSingleResult();
			return Long.valueOf(list.toString())>0;
		} else {
			return true;
		}
	}
	/** Проверяем - заполнены ли данные по родам
	 * Если диагноз входит в список разрешенных, то разрешаем перевод без заполнения информации о родах*/
    public static boolean isPregnancyExists(EntityManager aManager, Long aMedCase) {
    	return isPregnancyExists(aManager,aManager.find(DepartmentMedCase.class,aMedCase));
	}

	/** Список диагнозов, по которым возможно незаполнение родов*/
	private static ArrayList<String> getDiagosisWithoutChldBirth() {
		ArrayList<String> withoutChildBirth = new ArrayList<>();
		withoutChildBirth.add("O47.0");
		withoutChildBirth.add("O47.1");
		withoutChildBirth.add("O42.2");
		return withoutChildBirth;
	}

	/** Есть ли в СЛО диагноз, при котором не нужно проверять роды и т.п.*/
	private static boolean isWithoutChildBirthDiagnosis(DepartmentMedCase aMedCase) {
		Diagnosis diagnosis = aMedCase.getMainDiagnosis();
		ArrayList<String> withoutChildBirth = getDiagosisWithoutChldBirth();
		return diagnosis == null || withoutChildBirth.contains(diagnosis.getIdc10().getCode());
}

    private static boolean isPregnancyExists(EntityManager aManager, DepartmentMedCase aMedCase) {
    	if (aMedCase.getDepartment().getIsMaternityWard()!=null && aMedCase.getDepartment().getIsMaternityWard()) {
			if (isWithoutChildBirthDiagnosis(aMedCase)) return true;
			String sql = "select count(cb.id) from medcase slo " +
					" left join medcase slos on slos.parent_id=slo.parent_id and slos.dtype='DepartmentMedCase'" +
					" left join childBirth cb on cb.medcase_id=slos.id" +
					" where slo.id=:medcaseId and cb.pangsStartDate is not null";
			Object list = aManager.createNativeQuery(sql).setParameter("medcaseId",aMedCase.getId()).getSingleResult();
			return Long.valueOf(list.toString())>0;
		} else {
    		return true;
		}
	}
    /**
     * Новый первый случай лечения в отделении
     * */
    private void prepareForCreationFirstSlo(DepartmentMedCaseForm aForm, HospitalMedCase aMedCase ,EntityManager aManager,boolean aIsOwnerFunction) {
    	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    	if (aIsOwnerFunction) {
    		Date date = new Date();
    		aForm.setDateStart(DateFormat.formatToDate(date));
            aForm.setEntranceTime(timeFormat.format(date));
            
    	} else {
    		try {
	        	Calendar cal = Calendar.getInstance() ;
	        	cal.setTime(DateConverter.createDateTime(aMedCase.getDateStart(),aMedCase.getEntranceTime())) ;
	        	cal.add(Calendar.MINUTE,1) ;
	        	aForm.setDateStart(DateFormat.formatToDate(cal.getTime()));
	            aForm.setEntranceTime(timeFormat.format(cal.getTime()));
	            aForm.addDisabledField("dateStart");
	            aForm.addDisabledField("entranceTime");
			} catch (ParseException e) {
				e.printStackTrace();
				throw new IllegalStateException("Неправильно заведена дата поступления в стационар");
	    		
			}
    	}
    	aForm.setEmergency(aMedCase.getEmergency());
        aForm.setPatient(aMedCase.getPatient().getId());
        if (aMedCase.getDepartment()!=null) {
        	aForm.setDepartment(aMedCase.getDepartment().getId());
        	aForm.setServiceStream(aMedCase.getServiceStream()!=null?aMedCase.getServiceStream().getId():null);
        	aForm.addDisabledField("department");
        	//aForm.addDisabledField("serviceStream");
        	aForm.setBedFund(getBedFund(aManager, aForm.getDepartment(), aForm.getServiceStream(), aForm.getDateStart(), aMedCase.getHospType())) ;
        }
        
        aForm.setlpuAndDate(aForm.getDepartment()+":"+aForm.getDateStart()) ;
    }
    
    /**
     * Продолжение предыдущего случая лечения в отделении
     */
    private void prepareForCreationNextSlo(DepartmentMedCaseForm aForm, HospitalMedCase aMedCaseParent, EntityManager aManager) {
    	aForm.setPatient(aMedCaseParent.getPatient().getId());
    	aForm.setEmergency(aMedCaseParent.getEmergency());
    	StringBuilder sql = new StringBuilder() ;
    	
    	sql.append("select max(ms1.dateFinish) as maxdatefinish")
    		.append(",ms.id, to_char(ms.transferDate,'dd.mm.yyyy') as mstransferdate,cast(ms.transferTime as varchar(5)) as mstransfertime,ms.transferDepartment_id as mstransferdepartment,ms.lpu_id as mslpu,ms.serviceStream_id as msservicestream")
    		.append(" from MedCase as ms ")
    		.append(" left join MedCase as ms1 on ms1.parent_id=ms.parent_id and ms1.dtype='DepartmentMedCase'")
    		.append(" left join MedCase as ms2 on ms2.prevMedCase_id=ms.id and ms2.dtype='DepartmentMedCase'")
    		.append(" where ms.parent_id =:parentId and ms.DTYPE='DepartmentMedCase' ")
    		.append(" group by ms.id,ms.transferDate,ms.transferTime,ms.transferDepartment_id,ms.lpu_id,ms2.id,ms.serviceStream_id")
    		.append(" having ms2.id is null ")
    		.append(" order by ms.transferDate desc,ms.transferTime desc");
    	List<Object[]> list = aManager.createNativeQuery(sql.toString())
    			.setParameter("parentId", aMedCaseParent.getId()).setMaxResults(3).getResultList() ;
    	if (!list.isEmpty()) {
    		Object[] obj = list.get(0) ;
    		if (obj[0]!=null) {
    			throw new IllegalStateException("Уже есть лечение в отделении с выпиской.") ;
    		}
    		if (obj[2]!=null) aForm.setDateStart((String)obj[2]);
            if (obj[3]!=null) aForm.setEntranceTime((String)obj[3]);
            aForm.setPrevMedCase(ConvertSql.parseLong(obj[1]));
            if (obj[4]!=null) aForm.setDepartment(ConvertSql.parseLong(obj[4]));
            aForm.setServiceStream(ConvertSql.parseLong(obj[6])) ;
            if ((obj[2]!=null)&&(obj[4]!=null)&&(obj[3]!=null)) aForm.setBedFund(getBedFund(aManager, aForm.getDepartment(), aForm.getServiceStream(), aForm.getDateStart(), aMedCaseParent.getHospType())) ;
            Long idPrev = ConvertSql.parseLong(obj[1]) ; 
            DiagnosisForm diag = DischargeMedCaseViewInterceptor.getDiagnosis(aManager, idPrev, "4", "1", false) ;
            if (diag!=null){
            	aForm.setClinicalDiagnos(diag.getName());
            	if (diag.getIdc10()!=null) aForm.setClinicalMkb(diag.getIdc10()) ;
            	if (diag.getIllnesPrimary()!=null) aForm.setClinicalActuity(diag.getIllnesPrimary()) ;
            } else {
            	throw new IllegalStateException("Для ПЕРЕВОДА необходимо заполнить данные ДИАГНОЗА <a href='entityParentEdit-stac_slo.do?id="+idPrev+"'>в отделении</a>!!!") ;
            }
            diag = DischargeMedCaseViewInterceptor.getDiagnosis(aManager, idPrev, "3", "1", false) ;
            if (diag!=null){
            	aForm.setConcludingDiagnos(diag.getName());
				if (diag.getIdc10()!=null) aForm.setConcludingMkb(diag.getIdc10()) ;
			}
            diag = DischargeMedCaseViewInterceptor.getDiagnosis(aManager, idPrev, "4", "3", false) ;
            if (diag!=null){
            	aForm.setConcomitantDiagnos(diag.getName());
				if (diag.getIdc10()!=null) aForm.setConcomitantMkb(diag.getIdc10()) ;
			}
    		
    	} else {
    		throw new IllegalStateException("Нет случая лечения в отделении оформленного для перевода") ;
    	}
    	
    }
    
    
    private Long getBedFund(EntityManager aManager, Long aDepartment, Long aServiceStream, String aDateFrom,VocHospType aHospType) {
    	String bedSubType = aHospType!=null ? (aHospType.getCode().startsWith("DAY") ? "2" : "1") : "1" ;
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("select bf.id,vbst.id from BedFund bf ") ;
		sql.append(" left join vocBedType vbt on vbt.id=bf.bedType_id left join vocBedSubType vbst on vbst.id=bf.bedSubType_id ") ;
		sql.append(" where bf.lpu_id='").append(aDepartment!=null?aDepartment:"0")
			.append("' and bf.serviceStream_id='").append(aServiceStream!=null?aServiceStream:"0")
			.append("' and to_date('").append(aDateFrom)
			.append("','dd.mm.yyyy') between bf.dateStart and coalesce(bf.dateFinish,CURRENT_DATE)") ;
		sql.append(" and vbst.code='").append(bedSubType).append("' order by bf.amount desc");
		List<Object[]> idT = aManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
		return idT.isEmpty() ? null : ConvertSql.parseLong(idT.get(0)[0]);
    }
}