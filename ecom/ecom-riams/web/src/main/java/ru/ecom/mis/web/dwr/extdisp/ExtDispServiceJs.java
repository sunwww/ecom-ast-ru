package ru.ecom.mis.web.dwr.extdisp;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.extdisp.IExtDispService;
import ru.ecom.web.util.Injection;



public class ExtDispServiceJs {
	
	
	public String DispCardNotReal(Long dispCardId, HttpServletRequest aRequest) throws NamingException, ParseException {
		if(dispCardId==null||dispCardId.equals(Long.valueOf(0))){
			return "1";
		}
		else {
			IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
			service.executeUpdateNativeSql("update extdispcard set notpaid='1' ,isnoomc='1' where id='"+dispCardId+"'");
			return "0";
		}
	}
	
	public String checkDisableAgeDoubles (Long aDispCardId, Long aDisptypeId, Long aPatientId, Long aAgeGroup, HttpServletRequest aRequest)throws NamingException 
	{
		 IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		 Long haveDis = Long.valueOf(service.executeNativeSql("select count(edc.id) " +
		 		" from extdispcard edc " +
		 		" left join vocextdisp vedc on vedc.id=edc.disptype_id" +
		 		" where edc.patient_id=" +aPatientId +
		 		" and edc.disptype_id="+aDisptypeId +
		 		" and edc.agegroup_id="+aAgeGroup +
		 		" and (edc.notpaid is null or edc.notpaid='0')"+
		 		(aDispCardId!=null&&aDispCardId!=0?(" and edc.id!="+aDispCardId):"")+
		 		" and vedc.disableAgeDoubles='1' ").iterator().next().get1().toString());
		 
		 if(haveDis!=null&&haveDis>0)
		 {
			 return "1";
			 
		 }else return "0";
		 
	}
	// Проверка услуги ДД на: выходной день, дубль со стационаром, дубль с визитом, входит в период ДД	
	// 0 - если всё в порядке, 1 - предупреждение, 2 - запрет на создание
	public String checkDispService (String aDate, Long aDispCardId, Long aPatientId, Long aWorkFunctionId, HttpServletRequest aRequest) throws NamingException, ParseException {
		if (aPatientId==null||aPatientId==0) {
			IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
			try{
			aPatientId= Long.valueOf(service.executeNativeSql("select patient_id from extdispcard where id="+aDispCardId).iterator().next().get1().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}		
		String res = "";
		
		
		if (aDispCardId!=null&& !aDispCardId.equals(Long.valueOf(0))){
			if (isInDispPeriod(aDate, aDispCardId, aRequest)) {res = "1Дата услуги ("+aDate+") выходит за период диспансеризации";}
			if (isAfterDispPeriod(aDate, aDispCardId, aRequest)) {res = "2Услуга оказана позже окончания диспансеризации";}
		} else {
			System.out.println("=== Пришло время проверить полис!");
			if (!isMedPolicyExists(aPatientId,aDate,aRequest)) {
				res = "2У пациета отсутствует актуальный полис ОМС, создание карты невозможно!";
			}
		}
		if (isHoliday(aDate)) {res = "2Услуга приходится на выходной день";}
		if (aWorkFunctionId!=null && aWorkFunctionId!=0){
			if (existDoublesVisit(aDate, aPatientId, aWorkFunctionId, aRequest)) {res = "2У пациента есть посещение к данному специалисту за указанную дату ("+aDate+")";}
		}
		if (existDoublesStac (aDate, aPatientId, aRequest)) {res="2Пациент находился в стационаре на эту дату ("+aDate+")";}
		
		// System.out.println("Res = "+res);
		return res;
	}
	
	public boolean isMedPolicyExists(Long aPatientId, String aDate, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		String sql = "select id from medpolicy where patient_id="+aPatientId+" and dtype='MedPolicyOmc' " +
				"and (actualdateto is null or to_date('"+aDate+"','dd.MM.yyyy') between actualdatefrom and actualdateto)";
		Collection <WebQueryResult> wqr = service.executeNativeSql(sql);
	//	System.out.println("=== Check policy sql = "+sql);
		if (!wqr.isEmpty()) {
			//if (Long.valueOf(wqr.iterator().next().get1().toString())>0) {
			if (Long.parseLong(wqr.iterator().next().get1().toString())>0) {
				return true;
			}
		} else {
			sql = "select kinsman_id from kinsman where person_id="+aPatientId;
			wqr = service.executeNativeSql(sql);
			if (!wqr.isEmpty()) {
				aPatientId = Long.valueOf(wqr.iterator().next().get1().toString());
				return isMedPolicyExists(aPatientId, aDate, aRequest);
			}
			
		}
		return false;
	}
	public boolean isHoliday (String aDate) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(ru.nuzmsh.util.format.DateFormat.parseDate(aDate));
		 if (cal.get(java.util.Calendar.DAY_OF_WEEK)==1) {
	//		 System.out.println("is Holiday = true");
			 return true;
		 }
		// System.out.println("is Holiday = false");
		return false;
	}
	
	public boolean isAfterDispPeriod(String aDate, Long aDispCardId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		boolean ret = false;
		String str = "select count(edc.id) from extdispcard edc" +
				" where edc.id=" +aDispCardId+
				" and to_date('"+aDate+"','dd.MM.yyyy') > edc.finishdate";
		//	System.out.println("isInDispPeriod = "+str);
		Collection<WebQueryResult> wqr = service.executeNativeSql(str);
		if (!wqr.isEmpty()) {
			//if(Long.valueOf(wqr.iterator().next().get1().toString())>0) {
			if(Long.parseLong(wqr.iterator().next().get1().toString())>0) {
				ret = true;
			}
		}
		return ret;		
	}
	public boolean isInDispPeriod(String aDate, Long aDispCardId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		boolean ret = true;
		String str = "select count(edc.id) from extdispcard edc" +
				" where edc.id=" +aDispCardId+
				" and to_date('"+aDate+"','dd.MM.yyyy') between edc.startdate and edc.finishdate";
	//	System.out.println("isInDispPeriod = "+str);
		Collection<WebQueryResult> wqr = service.executeNativeSql(str);
		if (!wqr.isEmpty()) {
			//if(Long.valueOf(wqr.iterator().next().get1().toString())>0) {
			if(Long.parseLong(wqr.iterator().next().get1().toString())>0) {
				ret = false;
			}
		}
		return ret;		
	}
	public boolean existDoublesStac(String aDate, Long aPatientId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		boolean ret = false;
		String str = "select count(sls.id) from medcase sls" +
				" where (sls.dtype='HospitalMedCase') and sls.patient_id=" +aPatientId+
				" and (to_date('"+aDate+"','dd.MM.yyyy') between sls.datestart and sls.datefinish or (to_date('"+aDate+"','dd.MM.yyyy')>=sls.datestart and sls.datefinish is null and sls.deniedhospitalizating_id is null))";
	//	System.out.println("existDoublesStac = "+str);
		Collection<WebQueryResult> wqr = service.executeNativeSql(str);
		if (!wqr.isEmpty()) {
			//if(Long.valueOf(wqr.iterator().next().get1().toString())>0) {
			if(Long.parseLong(wqr.iterator().next().get1().toString())>0) {
				ret = true;
			}
		}
		return ret;		
	}
	
	public boolean existDoublesVisit(String aDate, Long aPatientId, Long aWorkFunctionId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		boolean ret = false;
		String str = "select count(mc.id) from medcase mc" +
				" where (mc.dtype='ShortMedCase' or mc.dtype='Visit') and mc.patient_id=" +aPatientId+
				" and mc.workfunctionexecute_id="+aWorkFunctionId+
				" and mc.datestart=to_date('"+aDate+"','dd.MM.yyyy')";
	//	System.out.println("existDoublesVisit = "+str);
		Collection<WebQueryResult> wqr = service.executeNativeSql(str);
		if (!wqr.isEmpty()) {
		//	if(Long.valueOf(wqr.iterator().next().get1().toString())>0) {
			if(Long.parseLong(wqr.iterator().next().get1().toString())>0) {
				ret = true;
			}
		}
		return ret;		
	}
	
	public String setOrphCodes(HttpServletRequest aRequest) throws NamingException {
		IExtDispService service = Injection.find(aRequest).getService(IExtDispService.class) ;
		return service.setOrphCodes();
	}
	public String exportOrph(String aStartDate, String aFinishDate,
			String aFileNameSuffix, String aSqlAdd, String aFizGroup, String aHeight,
			String aWeight, String aHeadSize, String aAnalysesText,
			String aZOJReccomend, String aReccomend, String aDivideNum, String aLpu, HttpServletRequest aRequest) throws NamingException, ParseException {
		IExtDispService service = Injection.find(aRequest).getService(IExtDispService.class) ;
		return service.exportOrph(aStartDate, aFinishDate,
				aFileNameSuffix, aSqlAdd, aFizGroup, aHeight,
				aWeight, aHeadSize, aAnalysesText,
				aZOJReccomend, aReccomend, aDivideNum, aLpu);
	}
}
