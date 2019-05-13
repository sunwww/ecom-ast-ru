package ru.ecom.mis.web.dwr.birth;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.json.JSONArray;
import org.json.JSONObject;
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

	/**
	 * Узнать, есть ли уже классификации Робсона/выкидыш в СЛО #131,#132.
	 * @param aSlo DepartmentMedCase.id
	 * @param ifRobson true - поиск кл-ии Робсона, false - выкидыша
	 * @param aRequest HttpServletRequest
	 * @return String id
	 */
	public String getIfRobbsonClassOrMisbirthAlreadyExists(Long aSlo, Boolean ifRobson,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		Collection<WebQueryResult> res = service.executeNativeSql("select id from " + (ifRobson? "robsonclass" : "misbirth") +  " where medcase_id="+aSlo);
		return res.isEmpty()? "" : res.iterator().next().get1().toString();
	}

	/**
	 * Получить кардиоскрининги в СЛО #137.
	 * @param aSlo DepartmentMedCase.id
	 * @param aRequest HttpServletRequest
	 * @return String json список кардиоскринингов
	 */
	public String getAllCardiacScreenings(Long aSlo,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		Collection<WebQueryResult> list = service.executeNativeSql("select id,case when dtype='ScreeningCardiacFirst' then 'I' else 'II' end," +
				"to_char(createdate,'dd.mm.yyyy')||' '||createusername from screeningcardiac where medcase_id="+aSlo);
		JSONArray res = new JSONArray() ;
		for (WebQueryResult w : list) {
			JSONObject o = new JSONObject() ;
			o.put("id", w.get1())
					.put("type", w.get2())
					.put("date", w.get3());
			res.put(o);
		}
		return res.toString();
	}

	/**
	 * Получить подгруппы классификации Робсона #147.
	 * @param aRobsonId RobsonClass.id
	 * @param aRequest HttpServletRequest
	 * @return String json подгруппы
	 */
	public String getRobsonSub(Long aRobsonId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String query="select sub.id,sub.name from vocsubrobson sub\n" +
				"left join  vocrobsonclass_vocsubrobson  vv on vv.subs_id=sub.id\n" +
				"where vv.vocrobsonclass_id=" + aRobsonId;
		JSONArray res = new JSONArray() ;
		Collection<WebQueryResult> list = service.executeNativeSql(query);
		for (WebQueryResult w :list) {
			JSONObject o = new JSONObject() ;
			o.put("id", w.get1())
					.put("name", w.get2());
			res.put(o);
		}
		return res.toString();
	}

	/**
	 * Получить всю инфу о Робсона #147.
	 * @param aSloId RobsonClass.id
	 * @param aRequest HttpServletRequest
	 * @return String json Инфа о Робсоне
	 */
	public String getAllRobsonInfo(Long aSloId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql("select robsontype_id,robsonsub_id from robsonclass where medcase_id=" + aSloId);
		JSONObject res = new JSONObject() ;
		if (!list.isEmpty()) {
			WebQueryResult w = list.iterator().next() ;
			res.put("robsonClass", w.get1())
					.put("robsonSub", w.get2());
		}
		return res.toString();
	}

	/**
	 * Получить (если есть) единственное значение для браслета новорождённого #151
	 * @return String json Значение
	 */
	public String getAutoBracelet(HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql("select id,name from vocColorIdentityPatient  where isfornewborn=true");
		JSONObject res = new JSONObject() ;
		if (list.size()==1) {
			WebQueryResult w = list.iterator().next() ;
			res.put("id", w.get1())
					.put("name", w.get2());
		}
		return res.toString();
	}
}
