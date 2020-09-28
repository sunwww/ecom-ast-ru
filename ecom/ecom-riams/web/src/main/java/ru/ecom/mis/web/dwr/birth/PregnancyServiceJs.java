package ru.ecom.mis.web.dwr.birth;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.birth.IPregnancyService;
import ru.ecom.web.login.LoginInfo;
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

	/**
	 * Можно ли редактировать карту нозологий? #185
	 * нельзя редактировать выписанного пациента, либо пациента, имеющего СЛО с обсервационным отделением в СЛС
	 * @param aSlsId HospitalMedCase.id
	 * @return true - да, false - нет
	 */
	private Boolean getIfICanEditNosologyCard(Long aSlsId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql("select case when hmc.datefinish is not null or" +
				" (select count(dmc.id) from medcase dmc" +
				" left join mislpu lpu on lpu.id=dmc.department_id" +
				" where dmc.dtype='DepartmentMedCase' and IsObservable=true" +
				" and dmc.parent_id="+aSlsId+")>0" +
				" then '0' else '1' end" +
				" from medcase hmc" +
				" where hmc.id="+aSlsId);
		return list.isEmpty() || list.iterator().next().get1().equals("0")? false:true;
	}

	/**
	 * Получить id карты назологий, если есть #185
	 * если нет - 0
	 * @param aSlsId HospitalMedCase.id
	 * @return card.id или 0
	 */
	private Long getNosologyCardIfExists(Long aSlsId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql("select c.id from  birthnosologycard_vocbirthnosology bv" +
				" left join birthnosologycard c on c.id=bv.birthnosologycard_id" +
				" where c.medcase_id=" + aSlsId);
		return list.isEmpty() ? 0L
				: Long.valueOf(list.iterator().next().get1().toString());
	}

	/**
	 * Получить нозологии по medcase #185
	 * @param aSlsId HospitalMedCase.id
	 * @param aCode VocNosologyCard.code (является номером страницы вывода чекбоксов в тэге)
	 * @return String json нозологии
	 */
	public String getBirthNosologyCard(Long aSlsId, String aCode, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		JSONArray res = new JSONArray() ;
		Collection<WebQueryResult> list = service.executeNativeSql("select vb.id,idc.code||' - '||idc.name from vocbirthnosology vb " +
                " left join vocidc10 idc on idc.id=vb.idccode_id where vb.code like '" + aCode + "' order by id"); //получили все нозологии
		for (WebQueryResult w : list) {
			JSONObject o = new JSONObject() ;
			o.put("vocID",w.get1())
					.put("vocName", w.get2());
			if (!getIfICanEditNosologyCard(aSlsId,aRequest))
				o.put("disabled",1); //уже нельзя редактировать
			res.put(o);
		}
		Long aCardId = getNosologyCardIfExists(aSlsId,aRequest);
		if (aCardId==0L)
			return res.toString(); //нет карты - вернуть просто нозологии
		else { //уже существующая карта
			for (int i=0; i<res.length(); i++) {
				Long vocID = res.getJSONObject(i).getLong("vocID");
				list = service.executeNativeSql("select * from birthnosologycard_vocbirthnosology where birthnosologycard_id="+aCardId +
				" and nosologies_id="+vocID);
				if (!list.isEmpty())
					res.getJSONObject(i).put("checked",1); //если была выбрана, проставить checked
			}
		}
		return res.toString();
	}

	/**
	 * Получить только отмеченные нозологии по medcase #185
	 * @param aSlsId HospitalMedCase.id
	 * @return String json нозологии
	 */
	public String getBirthNosologyCardOnlyChecked(Long aSlsId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		JSONArray res = new JSONArray() ;
		Long aCardId = getNosologyCardIfExists(aSlsId,aRequest);
		if (aCardId==0L)
			return "0"; //нет карты
		else { //уже существующая карта
			Collection<WebQueryResult> list = service.executeNativeSql("select vb.id,idc.code||' - '||idc.name from birthnosologycard_vocbirthnosology bb" +
					" left join vocbirthnosology vb on vb.id=bb.nosologies_id" +
                    " left join vocidc10 idc on idc.id=vb.idccode_id " +
					" where bb.birthnosologycard_id=" + aCardId);
			for (WebQueryResult w : list) {
				JSONObject o = new JSONObject();
				o.put("vocID", w.get1())
						.put("vocName", w.get2())
						.put("checked", 1);
				if (!getIfICanEditNosologyCard(aSlsId, aRequest))
					o.put("disabled", 1); //уже нельзя редактировать
				res.put(o);
			}
		}
		return res.toString();
	}

	/**
	 * Сохранить нозологии #185
	 * @param aSlsId HospitalMedCase.id
	 * @param mas String массив с выбранными нозологиями
	 */
	public String saveBirthNosologyCard(Long aSlsId, String mas, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Long aCardId = getNosologyCardIfExists(aSlsId,aRequest);
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername();
		String workFunction = service.executeNativeSql("select wf.id " +
				"from secuser su left join workfunction wf on wf.secuser_id=su.id " +
				"where su.login='"+login+"'").iterator().next().get1().toString();
		if (aCardId==0L) {  //если карты нет, нужно добавить
			Collection<WebQueryResult> res = service.executeNativeSql("INSERT INTO birthnosologycard(medcase_id,createdate,createtime,createusername,creator_id)" +
					"values ("+aSlsId+",current_date,current_time,'"+login+"',"+workFunction+") returning id");
			for (WebQueryResult wqr : res) {
				aCardId = Long.valueOf(wqr.get1().toString());
			}
		}
		else {
			service.executeUpdateNativeSql("update birthnosologycard set editdate=current_date,edittime=current_time,editusername='"+login+
					"',editor_id=" + workFunction + " where id="+aCardId); //отметка о редактировании
			service.executeUpdateNativeSql("delete from birthnosologycard_vocbirthnosology where birthnosologycard_id="+aCardId);  //удалить старные
		}
		String[] arr = mas.split(",");
		for (int i=0; i<arr.length; i++)
			service.executeUpdateNativeSql("INSERT INTO birthnosologycard_vocbirthnosology(birthnosologycard_id,nosologies_id) values("+aCardId+","+arr[i]+")");
		return "Сохранено.";
	}

	/**
	 * Создать пустого ребёнка (на случай, если врачи ошиблись кол-вом плодов)
	 * @param aChbId ChildBirth.id
	 */
	public void addEmptyChild(Long aChbId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		service.executeUpdateNativeSql("insert into newborn(createdate,childbirth_id) values(current_date,"+aChbId+")");
	}
}
