package ru.ecom.mis.web.dwr.medcase;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.ecom.mis.ejb.service.patient.IPatientService;
import ru.ecom.mis.web.webservice.FondWebService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.forms.validator.validators.SnilsStringValidator;
import ru.nuzmsh.util.date.AgeUtil;
import ru.nuzmsh.util.format.DateFormat;

public class PatientServiceJs {
	public String getInfoVocForFond(String aPassportType,String aAddress,HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		return service.getInfoVocForFond(aPassportType,aAddress);
		
	}
	public boolean updateDataByFond(Long aPatientId, String aFiodr
			,String aDocument,String aPolicy,String aAddress,HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		return service.updateDataByFond(aPatientId, aFiodr, aDocument, aPolicy, aAddress);
		
	}
	public Object checkPatientBySnils(Long aPatientId, String aSnils,HttpServletRequest aRequest) throws Exception {
		Object res = null;
		//System.out.println("checking snils...") ;
		//System.out.println("---username:"+LoginInfo.find(aRequest.getSession(true)).getUsername()) ;
		//System.out.println(new Date()) ;
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		res = FondWebService.checkPatientBySnils(getPatientInfo(aPatientId, service),aSnils) ;
		
		return res ;
	}
	public Object checkPatientByFioDr(Long aPatientId, String aLastname,String aFirstname
			,String aMiddlename, String aBirthday,HttpServletRequest aRequest) throws Exception {
		Object res = null;
		//System.out.println("checking fiodr...") ;
		//System.out.println("---username:"+LoginInfo.find(aRequest.getSession(true)).getUsername()) ;
		//System.out.println(new Date()) ;
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		res = FondWebService.checkPatientByFioDr( getPatientInfo(aPatientId, service),aLastname, aFirstname
				, aMiddlename,  aBirthday) ;
		return res ;
	}
	public Object checkPatientByDocument(Long aPatientId, Long aType, String aSeries
			,String aNumber,HttpServletRequest aRequest) throws Exception {
		Object res = null;
		//System.out.println("checking doc...") ;
		//System.out.println("---username:"+LoginInfo.find(aRequest.getSession(true)).getUsername()) ;
		//System.out.println(new Date()) ;
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		String type = service.getOmcCodeByPassportType(aType) ;
		res = FondWebService.checkPatientByDocument(getPatientInfo(aPatientId, service),type, aSeries, aNumber) ;
		return res ;
	}
	private PatientForm getPatientInfo(Long aPatientId, IPatientService aService) {
		return (aPatientId!=null &&aPatientId>Long.valueOf(0))?aService.getPatientById(aPatientId):null ;
	}
	public String getAge(String aDate)  {
		try {
			java.sql.Date birthday = DateFormat.parseSqlDate(aDate) ;
			java.sql.Date curdate = new java.sql.Date(new java.util.Date().getTime()) ;
			return AgeUtil.getAgeCache(curdate, birthday, 3);
		} catch(Exception e) {
			System.out.println(e) ;
			return "" ;
		}
	}
	public String getDoubleByFio(String aId, String aLastname, String aFirstname, String aMiddlename,
			String aSnils, String aBirthday, String aPassportNumber, String aPassportSeries,HttpServletRequest aRequest) throws NamingException, Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		return service.getDoubleByBaseData(aId , aLastname, aFirstname, aMiddlename, aSnils, aBirthday, aPassportNumber, aPassportSeries) ;
	}
	
	public void movePatientDoubleData(Long aIdNew, Long aIdOld,HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		service.movePatientDoubleData(aIdNew, aIdOld) ;
	}
	public String getDoubleByFio(String aId, String aLastname, String aFirstname, String aMiddlename,
			String aSnils, String aBirthday, String aPassportNumber, String aPassportSeries,String aAction, HttpServletRequest aRequest) throws NamingException, Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		return service.getDoubleByBaseData(aId , aLastname, aFirstname, aMiddlename, aSnils, aBirthday, aPassportNumber, aPassportSeries, aAction) ;
	}
	public String addPatient(String aLastname, String aFirstname, String aMiddlename, String aBirthday, Long aSex, Long aSocialStatus, String aSnils, HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		SnilsStringValidator val = new SnilsStringValidator() ;
		val.validate(aSnils, null, aRequest) ;
		
		return service.addPatient(aLastname, aFirstname, aMiddlename, aBirthday, aSex, aSocialStatus, aSnils) ;
	}
	public String infoByPolicy(Long aPatient, HttpServletRequest aRequest) throws Exception  {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		return service.infoMedPolicy(aPatient);
	}
	public boolean setAddParamByMedCase(String aParam, Long aMedCase,Long aStatus,HttpServletRequest aRequest)throws Exception  {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ; 
		service.setAddParamByMedCase(aParam,aMedCase,aStatus);
		return true ;
	}
}
