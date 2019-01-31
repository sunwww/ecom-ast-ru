package ru.ecom.mis.web.dwr.birth;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.birth.IPregnancyService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.tags.helper.RolesHelper;

import java.util.Collection;

public class PregnancyServiceJs {
	public Long getPregnanExchangeCard(Long aPregnancyId, HttpServletRequest aRequest) throws NamingException {
		IPregnancyService service = Injection.find(aRequest).getService(IPregnancyService.class) ;
		System.out.println("Получить PregnanExchangeCard беременности "+aPregnancyId);
		return service.getPregnanExchangeCard(aPregnancyId) ;
	}
	public Long getConfinedExchangeCard(Long aPregnancyId, HttpServletRequest aRequest) throws NamingException {
		IPregnancyService service = Injection.find(aRequest).getService(IPregnancyService.class) ;
		System.out.println("Получить ConfinedExchangeCard беременности  "+aPregnancyId);
		return service.getConfinedExchangeCard(aPregnancyId) ;
	}
	public Long getPregHistoryByMedCase(Long aMedCaseId, HttpServletRequest aRequest) throws NamingException {
		IPregnancyService service = Injection.find(aRequest).getService(IPregnancyService.class) ;
		System.out.println("Получить ConfinedExchangeCard беременности  "+aMedCaseId);
		return service.getPregHistoryByMedCase(aMedCaseId) ;
	}
	public Long getConfinementCertificate(Long aPregnancyId, HttpServletRequest aRequest) throws NamingException {
		IPregnancyService service = Injection.find(aRequest).getService(IPregnancyService.class) ;
		System.out.println("получить родовый сертификат по беременности "+aPregnancyId) ;
		return service.getConfinementCertificate(aPregnancyId) ;
	}
	public Long getNewBornHistoryByMedCase(Long aMedCaseId, HttpServletRequest aRequest) throws NamingException {
		IPregnancyService service = Injection.find(aRequest).getService(IPregnancyService.class) ;
		System.out.println("получить историю развития новорожденного "+aMedCaseId) ;
		return service.getNewBornHistoryByMedCase(aMedCaseId) ;
	}
	
	
	public Boolean able2createByConfinedExchangeCard(HttpServletRequest aRequest) throws JspException {
		return RolesHelper.checkRoles(" /Policy/Mis/Pregnancy/ConfinedExchangeCard/Create", aRequest) ;
	}
	
	public Boolean able2createByPregnanExchangeCard(HttpServletRequest aRequest) throws JspException {
		return RolesHelper.checkRoles(" /Policy/Mis/Pregnancy/PregnanExchangeCard/Create", aRequest) ;
	}
	public Boolean able2createByPregnancyHistory(HttpServletRequest aRequest) throws JspException {
		return RolesHelper.checkRoles(" /Policy/Mis/Pregnancy/History/Create", aRequest) ;
	}
	public Boolean able2createByConfinementCertificate(HttpServletRequest aRequest) throws JspException {
		return RolesHelper.checkRoles(" /Policy/Mis/Pregnancy/ConfinementCertificate/Create", aRequest) ;
	}
	public Boolean able2createByNewBornHistory(HttpServletRequest aRequest) throws JspException {
		return RolesHelper.checkRoles(" /Policy/Mis/NewBorn/History/Create", aRequest) ;
	}
	
	public Long calcApgarEstimation(Long aMuscleTone, Long aPalpitation
			, Long aReflexes, Long aRespiration
			, Long aSkinColor, HttpServletRequest aRequest) throws JspException, NamingException {
		IPregnancyService service = Injection.find(aRequest).getService(IPregnancyService.class) ;
		return  service.calcApgarEstimation(aMuscleTone, aPalpitation, aReflexes, aRespiration, aSkinColor);
	}
	public String calcDownesEstimation(Long aRespirationRate, Long aCyanosis
  			, Long aIntercostalRetraction, Long aDifficultExhalation
  			, Long aAuscultation, HttpServletRequest aRequest) throws JspException, NamingException {
		IPregnancyService service = Injection.find(aRequest).getService(IPregnancyService.class) ;
		return  service.calcDownesEstimation(aRespirationRate, aCyanosis
	  			, aIntercostalRetraction, aDifficultExhalation
	  			, aAuscultation);
	}
	
	public String calcInfRiskEstimation(Long aWaterlessDuration, Long aMotherTemperature
  			, Long aWaterNature, Long aApgar
  			, Long aNewBornWeight, Long aMotherInfectiousDiseases, HttpServletRequest aRequest) throws NamingException {
		IPregnancyService service = Injection.find(aRequest).getService(IPregnancyService.class) ;
		return  service.calcInfRiskEstimation(aWaterlessDuration, aMotherTemperature, aWaterNature, aApgar, aNewBornWeight, aMotherInfectiousDiseases) ;
	}
	//Milamesher #131,#132 есть ли уже классификации Робсона/выкидыш в СЛО
	public String getIfRobbsonClassOrMisbirthAlreadyExists(Long aSlo, Boolean ifRobson,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		StringBuilder sql = new StringBuilder();
		Collection<WebQueryResult> res = service.executeNativeSql(
				"select id from " + (ifRobson? "robsonclass" : "misbirth") +  " where medcase_id="+aSlo);
		return (res.isEmpty())? "" : res.iterator().next().get1().toString();
	}
	//Milamesher #137 возврат кардиоскринингов
	public String getAllCardiacScreenings(Long aSlo,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		StringBuilder sql = new StringBuilder();
		Collection<WebQueryResult> res = service.executeNativeSql("select id,case when dtype='ScreeningCardiacFirst' then 'I' else 'II' end," +
				"to_char(createdate,'dd.mm.yyyy')||' '||createusername from screeningcardiac where medcase_id="+aSlo);
		StringBuilder result = new StringBuilder();
		for (WebQueryResult wqr : res)
			result.append(wqr.get1()).append("#").append(wqr.get2()).append("#").append(wqr.get3()).append("!");
		return result.toString();
	}
}
