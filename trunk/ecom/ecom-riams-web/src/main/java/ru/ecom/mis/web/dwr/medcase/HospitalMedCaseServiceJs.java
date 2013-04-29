package ru.ecom.mis.web.dwr.medcase;

import java.text.ParseException;
import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.tags.helper.RolesHelper;

/**
 * Сервис по случаю лечения в стационаре
 * @author Tkacheva Sveltana
 */
public class HospitalMedCaseServiceJs {
	public String changeServiceStreamBySmo(Long aSmo, Long aServiceStream, HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		service.changeServiceStreamBySmo(aSmo, aServiceStream) ;
		return "Поток обслуживания изменен" ;
	}
	public String unionSloWithNextSlo(Long aSlo,HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		service.unionSloWithNextSlo(aSlo) ;
		return "Объединены" ;
	}
	public String deniedHospitalizatingSls(Long aMedCaseId,Long aDeniedHospitalizatingId,HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		service.deniedHospitalizatingSls(aMedCaseId,aDeniedHospitalizatingId) ;
		return "Обновлены" ;
	}
	public String preRecordDischarge(Long aMedCaseId, String aDischargeEpicrisis,HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		service.preRecordDischarge(aMedCaseId, aDischargeEpicrisis) ;
		return "Обновлены" ;
	}
	public String updateDischargeDateByInformationBesk(String aIds, String aDate,HttpServletRequest aRequest) throws Exception {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		service.updateDischargeDateByInformationBesk(aIds, aDate) ;
		return "Обновлены" ;
	}
	//Получить данные диагноза по умолчанию для акушерства
	public String getIdc10ByDocDiag(Long aIdDocDiag,HttpServletRequest aRequest) throws NamingException { 
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		return service.getIdc10ByDocDiag(aIdDocDiag) ;
	}
	public String getTypeDiagByAccoucheur(HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		return service.getTypeDiagByAccoucheur() ;
	}
	public String getRW(Long aMedCase, HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		return service.getRW(aMedCase) ;
	}
	public String setRW(Long aMedCase, String aRwDate, String aRwNumber, HttpServletRequest aRequest) throws NamingException, ParseException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		service.setRW(aMedCase, aRwDate, aRwNumber) ;
		return "" ;
	}
    public String changeStatCardNumber(String aNewStatCardNumber, Long aMedCase, HttpServletRequest aRequest) throws NamingException, JspException {
    	System.out.println(
    			new StringBuffer()
    			.append("Изменение номера стат. карты: ")
    			.append(aNewStatCardNumber)
    			.append(" случая лечения в стационаре #")
    			.append(aMedCase)
    		) ;
    	boolean always = RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Ssl/Admission/AlwaysCreateStatCardNumber", aRequest) ;
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
        //return service.(aStatCardNumber, aDateStart, aEntranceTime, aPatient);
        return service.getChangeStatCardNumber(aMedCase, aNewStatCardNumber,always) ;
    }
    public String getListTemperatureCurve(Long aMedCase, HttpServletRequest aRequest) throws Exception {
    	System.out.println("Температурная кривая");
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	
    	return service.getTemperatureCurve(aMedCase) ;
    }
    
    public Long getPatient(Long aMedCase, HttpServletRequest aRequest) throws NamingException {
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.getPatient(aMedCase) ;
    }
    
    public String isOpenningSlo(Long aMedCase, HttpServletRequest aRequest) throws NamingException {
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.isOpenningSlo(aMedCase) ;
    }
    
    public String deleteDischarge(Long aMedCaseId, HttpServletRequest aRequest) throws NamingException  {
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.deleteDataDischarge(aMedCaseId) ;
    }
    
    public String findDoubleServiceByPatient(Long aMedService, Long aPatient, Long aService, String aDate, HttpServletRequest aRequest) throws NamingException, ParseException{
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.findDoubleServiceByPatient(aMedService, aPatient, aService, aDate) ;
    }
    public String findDoubleOperationByPatient(Long aSurOperation, Long aPatient, Long aOperation, String aDate, HttpServletRequest aRequest) throws NamingException, ParseException{
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.findDoubleOperationByPatient(aSurOperation, aPatient, aOperation, aDate) ;
    }
    
    public String getOperations(Long aPatient, String aDateStart
    		,String aDateFinish, HttpServletRequest aRequest) throws NamingException, ParseException {
    	StringBuilder sql = new StringBuilder() ;
    	if (aDateFinish!=null && !aDateFinish.equals("") && aDateFinish.length()==10) {
    		aDateFinish = "to_date('"+aDateFinish+"','dd.mm.yyyy')" ;
    	} else{
    		aDateFinish = "CURRENT_DATE" ;
    	}
    	sql.append("select to_char(operationDate,'DD.MM.YYYY') as operDate1,cast(operationTime as varchar(5)) as timeFrom,cast(operationTimeTo as varchar(5)) as timeTo,vo.name as voname,so.operationText as sooperationText from SurgicalOperation so left join MedService vo on vo.id=so.operation_id where so.patient_id='")
    		.append(aPatient)
    		.append("' and so.operationDate between to_date('").append(aDateStart).append("','dd.mm.yyyy') and ").append(aDateFinish).append(" order by so.operationDate") ;
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	
    	Collection<WebQueryResult> list1 = service.executeNativeSql(sql.toString()) ;
		StringBuilder result = new StringBuilder() ;
		result.append("\n") ;
		result.append("ХИРУРГИЧЕСКИЕ ОПЕРАЦИИ:");
		result.append("\n") ;
		for (WebQueryResult wqr :list1) {
			result.append(wqr.get1()).append(" ") ;
			result.append(wqr.get4()).append("\n") ;
			result.append(wqr.get5()!=null?wqr.get5():"") ;
		}
		return result.toString() ;
    	
    	
    }
    public String getExpMedservices(Long aPatient, String aDateStart
    		,String aDateFinish, HttpServletRequest aRequest) throws NamingException, ParseException {
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	StringBuilder period = new StringBuilder() ;
    	period.append("between to_date('").append(aDateStart)
    	.append("','dd.mm.yyyy') and  ") ;
    	if (aDateFinish!=null && !aDateFinish.equals("") && aDateFinish.length()==10) {
    		period.append("to_date('").append(aDateFinish).append("','dd.mm.yyyy')") ;
    	} else {
    		period.append("CURRENT_DATE") ;
    	}
		StringBuilder sql = new StringBuilder().append("select em.NumberDoc")
/*			.append(",'Фамилия '||em.PatientLastname||'Имя '||PatientFirstname||'Отчество '")
			.append("||em.PatientMiddlename")
		.append(" ||'Дата рождения'||em.PatientBirthday	as fio,")	
		.append(" 'ЛПУ: '||em.OrderLpu")
		.append(" ||'\nВрач: '||em.Orderer")
		.append(" ||'\nНомер направления: '||em.NumberDoc")
		.append(" ||'\nДата направления: '||to_char(em.OrderDate,'dd.mm.yyyy')")
		.append(" ||'\nВремя направления: '||cast(em.OrderTime as varchar(5))")		
		.append(" ||'\nДата получения результата: '||to_char(em.CreateDate,'dd.mm.yyyy')")
		.append(" ||'\nВремя получения результата: '||cast(em.CreateTime as varchar(5))")
		
		.append("	as orderInfo")*/
		.append(" ,'\nНомер направления: '||em.NumberDoc")
		.append(" ||'\nДата направления: '||to_char(em.OrderDate,'dd.mm.yyyy')")
		.append(" ||'\nДата получения результата: '||to_char(em.CreateDate,'dd.mm.yyyy') as infoDirect")
		.append(" ,replace(list('\n'||em.comment),'\n, \n','\n\n') as comment from Document em") 
		.append(" left join patient p on p.id=em.patient_id")  
		.append(" left join medcase m on m.patient_id=p.id")
		.append(" where p.id='").append(aPatient).append("'")
		.append(" and em.dtype='ExternalMedservice'")
		.append(" and (em.orderDate ").append(period)
		.append(" or em.createDate ").append(period)
		.append(") group by em.numberdoc,em.orderDate,em.createDate order by em.orderDate,em.createDate");
		Collection<WebQueryResult> list1 = service.executeNativeSql(sql.toString()) ;
		StringBuilder result = new StringBuilder() ;
		for (WebQueryResult wqr :list1) {
			result.append(wqr.get2()).append(wqr.get3()) ;
		}
		return result.toString() ;
    }
    public String getLabInvestigations(Long aPatient, String aDateStart
			,String aDateFinish,boolean aLabsIs,boolean aFisioIs,boolean aFuncIs,boolean aConsIs, boolean aLuchIs, HttpServletRequest aRequest) throws NamingException, ParseException {
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.getnvestigationsTextDTM(aPatient, aDateStart, aDateFinish,aLabsIs,aFisioIs,aFuncIs,aConsIs, aLuchIs);
    }
    public String getPlannigHospitalization(String aDateFrom, String aDateTo
    		,Long aDepartment, Long aBedType, Long aCountBed) {
    	
    	return "" ;
    }
    public String setPatientByExternalMedservice(String aNumberDoc, String aOrderDate, String aPatient, HttpServletRequest aRequest) throws NamingException {
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	service.setPatientByExternalMedservice(aNumberDoc, aOrderDate, aPatient) ;
    	return "обновлено" ;
    }
    public String getDefaultBedFundBySlo(Long aParent, Long aDepartment, Long aServiceStream, String aDateFrom,HttpServletRequest aRequest) throws NamingException {
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	StringBuilder sql = new StringBuilder() ;
    	StringBuilder res = new StringBuilder() ;
    	Collection<WebQueryResult> list = service.executeNativeSql("select vht.id,vht.code from MedCase hmc left join VocHospType vht on vht.id=hmc.hospType_id where hmc.id='"+aParent+"' and vht.code is not null",1) ;
    	String bedSubType="";
    	String hospType=null ;
    	if (list.size()>0) {
    		WebQueryResult wqr = list.iterator().next() ;
    		hospType=""+wqr.get2() ;
    	}
    	if (hospType!=null && hospType.equals("DAYTIMEHOSP")) {
    		bedSubType=" and vbst.code='2' ";
    	} else if (hospType==null||hospType.equals("ALLTIMEHOSP")) {
    		bedSubType=" and vbst.code='1' ";
    	}
    	sql = new StringBuilder() ;
    	sql.append("select bf.id, vbt.name||' ('||vbst.name||' ' || case when bf.noFood='1' then 'без питания' else 'с питанием' end ||')' from BedFund bf ") ;
    	sql.append(" left join vocBedType vbt on vbt.id=bf.bedType_id left join vocBedSubType vbst on vbst.id=bf.bedSubType_id ") ;
    	sql.append(" where bf.lpu_id='").append(aDepartment)
    	.append("' and bf.serviceStream_id='").append(aServiceStream)
    	.append("' and to_date('").append(aDateFrom)
    	.append("','dd.mm.yyyy') between bf.dateStart and coalesce(bf.dateFinish,CURRENT_DATE)") ;
    	sql.append(" ").append(bedSubType).append("");
    	
    	list.clear() ;
    	list = service.executeNativeSql(sql.toString(),2) ;
    	if (list.size()==1) {
    		WebQueryResult wqr = list.iterator().next() ;
    		res.append(wqr.get1()).append("#").append(wqr.get2()).append("#") ;
    		
    	} else {
    		res.append("##");
    	}
    	
    	return res.toString() ;
    }
    public String getDefaultInfoBySlo(Long aParent, Long aDepartment, Long aServiceStream, String aDateFrom,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	StringBuilder sql = new StringBuilder() ;
    	StringBuilder res = new StringBuilder() ;
		Collection<WebQueryResult> list = service.executeNativeSql("select vht.id,vht.code from MedCase hmc left join VocHospType vht on vht.id=hmc.hospType_id where hmc.id='"+aParent+"' and vht.code is not null",1) ;
		String bedSubType="";
		String hospType=null ;
		if (list.size()>0) {
			WebQueryResult wqr = list.iterator().next() ;
			hospType=""+wqr.get2() ;
		}
		if (hospType.equals("DAYTIMEHOSP")) {
			bedSubType=" and vbst.code='2' ";
		} else if (hospType.equals("ALLTIMEHOSP")) {
			bedSubType=" and vbst.code='1' ";
		}
    	sql = new StringBuilder() ;
		sql.append("select bf.id, vbt.name||' ('||vbst.name||' ' || case when bf.noFood='1' then 'без питания' else 'с питанием' end ||')' from BedFund bf ") ;
		sql.append(" left join vocBedType vbt on vbt.id=bf.bedType_id left join vocBedSubType vbst on vbst.id=bf.bedSubType_id ") ;
		sql.append(" where bf.lpu_id='").append(aDepartment)
			.append("' and bf.serviceStream_id='").append(aServiceStream)
			.append("' and to_date('").append(aDateFrom)
			.append("','dd.mm.yyyy') between bf.dateStart and coalesce(bf.dateFinish,CURRENT_DATE)") ;
		sql.append(" ").append(bedSubType).append("");
		String username=LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		StringBuilder sql1=new StringBuilder();
		sql1.append("select wf.id as wfid,case when wf.code is null then '' else wf.code||' ' end || vwf.name||' '|| p.lastname||' '||p.firstname||' '||p.middlename from WorkFunction wf")
			.append(" left join Worker w on w.id=wf.worker_id")
			.append(" left join SecUser su on su.id=wf.secUser_id")
			.append(" left join Patient as p on p.id=w.person_id left join VocWorkFunction vwf on vwf.id=wf.workFunction_id")
			.append(" where su.login = '").append(username).append("' and w.lpu_id='").append(aDepartment).append("' and wf.id is not null") ;
		Collection<WebQueryResult> list1 = service.executeNativeSql(sql1.toString(),1) ;
		if (list1.size()>0) {
			WebQueryResult wqr = list1.iterator().next() ;
			res.append(wqr.get1()).append("#").append(wqr.get2()).append("#") ;
		} else {
			sql1=new StringBuilder();
			sql1.append("select wf.id as wfid,case when wf.code is null then '' else wf.code||' ' end || vwf.name||' '|| p.lastname||' '||p.firstname||' '||p.middlename from WorkFunction wf")
				.append(" left join Worker w on w.id=wf.worker_id")
				.append(" left join SecUser su on su.id=wf.secUser_id")
				.append(" left join Patient as p on p.id=w.person_id left join VocWorkFunction vwf on vwf.id=wf.workFunction_id")
				.append(" where w.lpu_id='").append(aDepartment).append("' and wf.isAdministrator='1'") ;
			list1.clear() ;
			list1 = service.executeNativeSql(sql1.toString(),1) ;
			if (list1.size()>0) {
				WebQueryResult wqr = list1.iterator().next() ;
				res.append(wqr.get1()).append("#").append(wqr.get2()).append("#") ;
			} else {
				res.append("##");
			}
		}
		list.clear() ;
		list = service.executeNativeSql(sql.toString(),2) ;
		if (list.size()==1) {
			WebQueryResult wqr = list.iterator().next() ;
			res.append(wqr.get1()).append("#").append(wqr.get2()).append("#") ;
			
		} else {
			res.append("##");
		}

		return res.toString() ;
    }
}
