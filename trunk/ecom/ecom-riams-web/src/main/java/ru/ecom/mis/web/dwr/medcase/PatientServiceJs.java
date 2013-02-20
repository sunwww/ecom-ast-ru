package ru.ecom.mis.web.dwr.medcase;

import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.ecom.mis.ejb.service.patient.IPatientService;
import ru.ecom.mis.web.webservice.FondWebService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.forms.validator.validators.SnilsStringValidator;
import ru.nuzmsh.util.date.AgeUtil;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.tags.helper.RolesHelper;

public class PatientServiceJs {
	public String checkPolicy(String aRoles,HttpServletRequest aRequest) throws JspException {
		if (RolesHelper.checkRoles(aRoles, aRequest)) {
			return "1" ;
		}
		return "0" ;
	}
	public String getFactorByProfession(Long aProfession,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql
		.append(" select vdp.id,vdp.factorOfProduction from VocDocumentProfession vdp ")
		.append(" where ")
		.append(" vdp.id='").append(aProfession).append("'") ;
		System.out.println(sql) ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1);
		if (!list.isEmpty()) {
			WebQueryResult wqr = list.iterator().next() ;
			if (wqr.get2()!=null) {
				return ""+wqr.get2() ;
			}
		}
		return "" ;
	}
	public String getCodefByRegIcForeign(Long aArea, Long aCompany,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql
		.append(" select smo.id,smo.name from Omc_SprSmo smo ")
		.append(" left join Omc_KodTer ter on ter.okato=smo.fondokato ")
		.append(" left join reg_ic com on com.ogrn=smo.voc_code ")
		.append(" where ")
		.append(" ter.id='").append(aArea)
		.append("' and com.id='").append(aCompany).append("'");
		System.out.println(sql) ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1);
		if (!list.isEmpty()) {
			WebQueryResult wqr = list.iterator().next() ;
			if (wqr.get1()!=null) {
				return wqr.get1()+"#"+wqr.get2() ;
			}
		}
		return "" ;
	}
	public String getRegIcForeignByCodef(Long aOgrnCompany,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql
			.append(" select com.id,coalesce(com.omcCode,'')||' '||com.name from Omc_SprSmo smo ")
			.append(" left join Omc_KodTer ter on ter.okato=smo.fondokato ")
			.append(" left join reg_ic com on com.ogrn=smo.voc_code ")
			.append(" where ")
			.append(" smo.id='").append(aOgrnCompany).append("'")
			;
		//System.out.println(sql) ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1);
		if (!list.isEmpty()) {
			WebQueryResult wqr = list.iterator().next() ;
			if (wqr.get1()!=null) {
				return wqr.get1()+"#"+wqr.get2() ;
			}
		}
		return "" ;
	}
	public String getCodeByMedPolicyOmc(Long aType,HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		return service.getCodeByMedPolicyOmc(aType);
		
	}
	public String getInfoVocForFond(String aPassportType,String aAddress, String aPolicy,HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		return service.getInfoVocForFond(aPassportType,aAddress,aPolicy);
		
	}
	public boolean updateDataByFond(Long aPatientId, String aFiodr
			,String aDocument,String aPolicy,String aAddress
			, boolean aIsPatient, boolean aIsPolicy
			, boolean aIsDocument, boolean aIsAddress,HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		return service.updateDataByFond(username,aPatientId, aFiodr, aDocument, aPolicy, aAddress,aIsPatient,aIsPolicy
				, aIsDocument, aIsAddress);
		
	}
	public Object checkPatientByPolicy(Long aPatientId, String aSeries, String aNumber,HttpServletRequest aRequest) throws Exception {
		Object res = null;
		//System.out.println("checking snils...") ;
		//System.out.println("---username:"+LoginInfo.find(aRequest.getSession(true)).getUsername()) ;
		//System.out.println(new Date()) ;
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		res = FondWebService.checkPatientByMedPolicy(aRequest, getPatientInfo(aPatientId, service),aSeries,aNumber) ;
		
		return res ;
	}
	public Object checkPatientBySnils(Long aPatientId, String aSnils,HttpServletRequest aRequest) throws Exception {
		Object res = null;
		//System.out.println("checking snils...") ;
		//System.out.println("---username:"+LoginInfo.find(aRequest.getSession(true)).getUsername()) ;
		//System.out.println(new Date()) ;
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		res = FondWebService.checkPatientBySnils(aRequest, getPatientInfo(aPatientId, service),aSnils) ;
		
		return res ;
	}
	public Object checkPatientByFioDr(Long aPatientId, String aLastname,String aFirstname
			,String aMiddlename, String aBirthday,HttpServletRequest aRequest) throws Exception {
		Object res = null;
		//System.out.println("checking fiodr...") ;
		//System.out.println("---username:"+LoginInfo.find(aRequest.getSession(true)).getUsername()) ;
		//System.out.println(new Date()) ;
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		res = FondWebService.checkPatientByFioDr( aRequest, getPatientInfo(aPatientId, service),aLastname, aFirstname
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
		res = FondWebService.checkPatientByDocument(aRequest, getPatientInfo(aPatientId, service),type, aSeries, aNumber) ;
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
