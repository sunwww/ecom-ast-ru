package ru.ecom.mis.ejb.service.medcase;

import org.apache.log4j.Logger;
import org.jboss.annotation.security.SecurityDomain;
import org.jdom.IllegalDataException;
import org.jdom.input.SAXBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import ru.ecom.diary.ejb.domain.DischargeEpicrisis;
import ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocol;
import ru.ecom.ejb.sequence.service.ISequenceService;
import ru.ecom.ejb.services.entityform.EntityFormException;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.ejb.xml.XmlUtil;
import ru.ecom.expert2.service.IExpert2Service;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentParameter;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentParameterConfig;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.*;
import ru.ecom.mis.ejb.domain.medcase.hospital.TemperatureCurve;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAcuityDiagnosis;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDiagnosisRegistrationType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPrimaryDiagnosis;
import ru.ecom.mis.ejb.domain.patient.MedPolicy;
import ru.ecom.mis.ejb.domain.psychiatry.CompulsoryTreatmentAggregate;
import ru.ecom.mis.ejb.domain.report.AggregateHospitalReport;
import ru.ecom.mis.ejb.form.medcase.hospital.ExtHospitalMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.HospitalMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.SurgicalOperationForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.HospitalMedCaseViewInterceptor;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.StatisticStubStac;
import ru.ecom.mis.ejb.form.patient.MedPolicyForm;
import ru.ecom.mis.ejb.service.patient.QueryClauseBuilder;
import ru.ecom.report.util.XmlDocument;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 26.01.2007
 */
@Stateless
@Remote(IHospitalMedCaseService.class)
@SecurityDomain("other")
public class HospitalMedCaseServiceBean implements IHospitalMedCaseService {

	private static final  Logger LOG = Logger.getLogger(HospitalMedCaseServiceBean.class);

	private HashMap getMiacServiceStreamMap() {
		HashMap<String, String> ss = new HashMap<>();
		ss.put("OBLIGATORY","Средства ОМС");
		ss.put("BUDGET","Средства бюджета");
		ss.put("UFSIN","Личные средства граждан, по договору и ДМС");
		ss.put("CHARGED","Личные средства граждан, по договору и ДМС");
		return ss;
	}

	/**
	 * Формируем "справочник" с профилями поликлинических специалистов
	 * @return map со странами в виде (Код специальности, Название_МИАЦа)
	 */
	private HashMap getPolicProfileMap() {
		HashMap<String, String> policProfiles = new HashMap<>();

		policProfiles.put("80","другое");
		policProfiles.put("0S","другое");
		policProfiles.put("11","акушерство и гинекология");
		policProfiles.put("13","аллергология и иммунология");
		policProfiles.put("14","анестезиология и реаниматология");
		policProfiles.put("15","гастроэнтерология");
		policProfiles.put("16","гематология");
		policProfiles.put("17","генетика");
		policProfiles.put("18","гериатрия");
		policProfiles.put("19","дерматовенерология");
		policProfiles.put("20","детская кардиология");
		policProfiles.put("21","детская хирургия");
		policProfiles.put("22","детская эндокринология");
		policProfiles.put("23","диабетология");
		policProfiles.put("24","диетология");
		policProfiles.put("25","инфекционные болезни");
		policProfiles.put("26","кардиология");
		policProfiles.put("27","клиническая лабораторная диагностика");
		policProfiles.put("29","колопроктология");
		policProfiles.put("30","лабораторная генетика");
		policProfiles.put("31","лечебная физкультура и спортивная медицина");
		policProfiles.put("32","лечебная физкультура и спортивная медицина");
		policProfiles.put("34","мануальная терапия");
		policProfiles.put("35","неврология");
		policProfiles.put("36","нейрохирургия");
		policProfiles.put("37","нефрология");
		policProfiles.put("39","онкология");
		policProfiles.put("103","ортодонтия");
		policProfiles.put("40","оториноларингология (за исключением кохлеарной имплантации)");
		policProfiles.put("41","офтальмология");
		policProfiles.put("49","профпатология");
		policProfiles.put("51","психиатрия");
		policProfiles.put("57","психиатрия-наркология");
		policProfiles.put("50","психотерапия");
		policProfiles.put("58","пульмонология");
		policProfiles.put("59","радиология");
		policProfiles.put("61","ревматология");
		policProfiles.put("60","рентгенология");
		policProfiles.put("62","рефлексотерапия");
		policProfiles.put("64","сердечно-сосудистая хирургия");
		policProfiles.put("105","стоматология детская");
		policProfiles.put("107","стоматология ортопедическая");
		policProfiles.put("106","стоматология терапевтическая");
		policProfiles.put("113","стоматология терапевтическая"); //зубной врач (стоматолог)
		policProfiles.put("108","стоматология хирургическая");
		policProfiles.put("77","токсикология");
		policProfiles.put("78","торакальная хирургия");
		policProfiles.put("79","травматология и ортопедия");
		policProfiles.put("81","ультразвуковая диагностика");
		policProfiles.put("82","урология");
		policProfiles.put("83","физиотерапия");
		policProfiles.put("84","фтизиатрия");
		policProfiles.put("86","функциональная диагностика");
		policProfiles.put("87","хирургия");
		policProfiles.put("109","челюстно-лицевая хирургия");
		policProfiles.put("88","эндокринология");
		policProfiles.put("89","эндоскопия");
		policProfiles.put("71","терапия");
		policProfiles.put("72","терапия");
		policProfiles.put("44","педиатрия");
		policProfiles.put("45","педиатрия");
		policProfiles.put("38","общая врачебная практика (семейная медицина)");
		policProfiles.put("65","скорая медицинская помощь");
		return policProfiles;
	}

	/**
	 * Формируем "справочник" с профилями поликлинических специалистов
	 * @return map со странами в виде (Код специальности, Код ЦБРФ)
	 */
	private HashMap getPolicProfileMapBank() {
		HashMap<String, String> policProfiles = new HashMap<>();

		policProfiles.put("80","CMP48");
		policProfiles.put("0S","CMP48");
		policProfiles.put("11","CMP02");
		policProfiles.put("13","CMP03");
		policProfiles.put("14","CMP04");
		policProfiles.put("15","CMP05");
		policProfiles.put("16","CMP07");
		policProfiles.put("17","CMP48");
		policProfiles.put("18","CMP08");
		policProfiles.put("19","CMP09"); //Дерматология, в ЦБРФ отдельно дерматология и венерология
		policProfiles.put("20","CMP10");
		policProfiles.put("21","CMP13");
		policProfiles.put("22","CMP14");
		policProfiles.put("23","CMP44");  //Диабетология => эндокринология
		policProfiles.put("24","CMP05"); //Диетология => гастро
		policProfiles.put("25","CMP15");
		policProfiles.put("26","CMP16");
		policProfiles.put("27","CMP48"); //КДЛ - прочее
		policProfiles.put("29","CMP17");
		policProfiles.put("30","CMP48");  //лаб. генетика - прочее
		policProfiles.put("31","CMP46");  //лечебная физкультура и спортивная медицина => мед. реаб.
		policProfiles.put("32","CMP46");
		policProfiles.put("34","CMP46");  //мануальная терапия => мед. реаб.
		policProfiles.put("35","CMP18");
		policProfiles.put("36","CMP19");
		policProfiles.put("37","CMP21");
		policProfiles.put("39","CMP22");
		policProfiles.put("103","CMP3301");
		policProfiles.put("40","CMP23");
		policProfiles.put("41","CMP24");
		policProfiles.put("49","CMP26");
		policProfiles.put("51","CMP27");
		policProfiles.put("57","CMP28");
		policProfiles.put("50","CMP48"); //психотерапия - прочее
		policProfiles.put("58","CMP29");
		policProfiles.put("59","CMP2203"); //1103 в начале списка с "детскими" кодами
		policProfiles.put("61","CMP40");
		policProfiles.put("60","CMP48");  //рентгенология - прочее
		policProfiles.put("62","CMP46"); //рефлексотерапия => мед. реаб.
		policProfiles.put("64","CMP31");
		policProfiles.put("105","CMP32");
		//СТОМАТОЛОГИЯ
		policProfiles.put("107","CMP33"); //ортопедическая
		policProfiles.put("106","CMP33"); //терапевтическая
		policProfiles.put("113","CMP33"); //зубной врач (стоматолог)
		policProfiles.put("108","CMP33"); //хирургическая
		policProfiles.put("77","CMP35");
		policProfiles.put("78","CMP36");
		policProfiles.put("79","CMP37");
		policProfiles.put("81","CMP48"); //ультразвуковая диагностика => прочее
		policProfiles.put("82","CMP38");
		policProfiles.put("83","CMP46"); //физиотерапия => мед. реаб.
		policProfiles.put("84","CMP39");
		policProfiles.put("86","CMP48");  //функциональная диагностика => прочее
		policProfiles.put("87","CMP40");
		policProfiles.put("109","CMP43");
		policProfiles.put("88","CMP44");
		policProfiles.put("89","CMP48");  //эндоскопия => прочее
		policProfiles.put("71","CMP34");
		policProfiles.put("72","CMP34");
		policProfiles.put("44","CMP25");
		policProfiles.put("45","CMP25");
		policProfiles.put("38","CMP25");  //общая врачебная практика (семейная медицина)=> терапия
		policProfiles.put("65","CMP48"); //скорая медицинская помощь => прочее
		return policProfiles;
	}

	/**
	 * Формируем "справочник" с профилями коек в стационаре
	 * @return map со странами в виде (профиль койки, Название_МИАЦа)
	 */
	private HashMap getStacProfileMap() {
		HashMap<String, String> stacProfiles = new HashMap<>();
		stacProfiles.put("40","акушерство и гинекология");
		stacProfiles.put("38","акушерство и гинекология");
		stacProfiles.put("39","акушерство и гинекология");
		stacProfiles.put("8","аллергология и иммунология");
		stacProfiles.put("801","анестезиология и реаниматология");
		stacProfiles.put("80","анестезиология и реаниматология");
		stacProfiles.put("6","гастроэнтерология");
		stacProfiles.put("16","гематология");
		stacProfiles.put("3","кардиология");
		stacProfiles.put("63","колопроктология");
		stacProfiles.put("48","неврология");
		stacProfiles.put("22","нейрохирургия");
		stacProfiles.put("18","нефрология");
		stacProfiles.put("37","онкология");
		stacProfiles.put("56","оториноларингология (за исключением кохлеарной имплантации)");
		stacProfiles.put("54","офтальмология");
		stacProfiles.put("69","пульмонология");
		stacProfiles.put("65","ревматология");
		stacProfiles.put("26","сердечно-сосудистая хирургия");
		stacProfiles.put("24","торакальная хирургия");
		stacProfiles.put("31","травматология и ортопедия");
		stacProfiles.put("28","травматология и ортопедия");
		stacProfiles.put("33","урология");
		stacProfiles.put("20","хирургия");
		stacProfiles.put("41","хирургия");
		stacProfiles.put("35","челюстно-лицевая хирургия");
		stacProfiles.put("12","эндокринология");
		stacProfiles.put("2","терапия");
		stacProfiles.put("61","педиатрия");
		stacProfiles.put("81","педиатрия");
		stacProfiles.put("29","другое");
		return stacProfiles;
	}


	/**
	 * Формируем "справочник" с профилями коек в стационаре
	 * @return map со странами в виде (профиль койки, код ЦБРФ)
	 */
	private HashMap getStacProfileMapBank() {
		HashMap<String, String> stacProfiles = new HashMap<>();
		stacProfiles.put("38","CMP02");
		stacProfiles.put("40","CMP02");
		stacProfiles.put("39","CMP02");
		stacProfiles.put("8","CMP03");
		stacProfiles.put("801","CMP04");
		stacProfiles.put("80","CMP04");
		stacProfiles.put("6","CMP05");
		stacProfiles.put("16","CMP07");
		stacProfiles.put("3","CMP16");
		stacProfiles.put("63","CMP17");
		stacProfiles.put("48","CMP18");
		stacProfiles.put("22","CMP19");
		stacProfiles.put("18","CMP20");
		stacProfiles.put("37","CMP22");
		stacProfiles.put("56","CMP23");
		stacProfiles.put("54","CMP24");
		stacProfiles.put("69","CMP29");
		stacProfiles.put("65","CMP40");
		stacProfiles.put("26","CMP31");
		stacProfiles.put("24","CMP36");
		stacProfiles.put("31","CMP37");
		stacProfiles.put("28","CMP37");
		stacProfiles.put("33","CMP38");
		stacProfiles.put("20","CMP40");
		stacProfiles.put("41","CMP40");
		stacProfiles.put("35","CMP43");
		stacProfiles.put("12","CMP44");
		stacProfiles.put("2","CMP34");
		stacProfiles.put("61","CMP20");
		stacProfiles.put("81","CMP20");
		stacProfiles.put("29","CMP42");
		stacProfiles.put("45","CMP22");
		return stacProfiles;
	}

	/**
	 * Формируем "справочник" стран с названиями для экспорта в МИАЦ
	 * @return  map со странами в виде (Код, Название_МИАЦа)
	 */
	private HashMap getCountries() {
		HashMap<String, String> countries = new HashMap<>();
		countries.put("598","Экваториальная Гвинея"); //ПАПУА-НОВАЯ ГВИНЕЯ
		countries.put("226","Экваториальная Гвинея");
		countries.put("148","Республика Чад");
		countries.put("624","Гвинея-Бисау");
		countries.put("31","Азербайджан");
		countries.put("24","Ангола");
		countries.put("51","Армения");
		countries.put("4","Афганистан");
		countries.put("50","Бангладеш");
		countries.put("112","Беларусь");
		countries.put("72","Ботсвана");
		countries.put("76","Бразилия");
		countries.put("862","Венесуэла");
		countries.put("704","Вьетнам");
//		countries.put("598","Гвинея");
		countries.put("276","Германия");
		countries.put("268","Грузия");
		countries.put("818","Египет");
		countries.put("894","Замбия");
		countries.put("716","Зимбабве");
		countries.put("376","Израиль");
		countries.put("356","Индия");
		countries.put("368","Ирак");
		countries.put("364","Иран");
		countries.put("724","Испания");
		countries.put("398","Казахстан");
		countries.put("120","Камерун");
		countries.put("124","Канада");
		countries.put("178","Конго");
		countries.put("192","Куба");
		countries.put("442","Люксембург");
		countries.put("466","Мали");
		countries.put("504","Марокко");
		countries.put("508","Мозамбик");
		countries.put("498","Молдова");
		countries.put("496","Монголия");
		countries.put("516","Намибия");
		countries.put("566","Нигерия");
		countries.put("784","Объединенные Арабские Эмираты");
		countries.put("616","Польша");
		countries.put("895","Республика Абхазия");
		countries.put("144","Республика Шри-Ланка");
		countries.put("642","Румыния");
		countries.put("686","Сенегал");
		countries.put("688","Сербия");
		countries.put("762","Таджикистан");
		countries.put("764","Таиланд");
		countries.put("834","Танзания");
		countries.put("795","Туркменистан");
		countries.put("860","Узбекистан");
		countries.put("804","Украина");
		countries.put("191","Хорватия");
		countries.put("152","Чили");
		countries.put("896","Южная Осетия");
		countries.put("392","Япония");
		countries.put("12","Алжир");
		countries.put("70","Босния");
		countries.put("288","Ганна");
		countries.put("300","Греция");
		countries.put("887","Йемен");
//		countries.put("417","Киргизия");
		countries.put("156","Китай");
		countries.put("384","Кот-д-Вуар");
		countries.put("417","Кыргызстан");
		countries.put("428","Латвия");
		countries.put("422","Ливан");
		countries.put("440","Литва");
		countries.put("484","Мексика");
		countries.put("528","Нидерланды");
		countries.put("9999","Перу");
		countries.put("790","Сирия");
		countries.put("840","США");
		countries.put("788","Тунис");
//		countries.put("795","Туркмения");
		countries.put("792","Турция");
		countries.put("246","Финляндия");
		countries.put("710","Южная-Африканская Республика");
		return countries;
	}

	/**
	 * Формируем "справочник" регионов с названиями для экспорта в МИАЦ
	 * @return  map с регионами в виде (Код, Название_МИАЦа)
	 */
	private HashMap getRegions() {
		HashMap<String, String> countries = new HashMap<>();
		countries.put("01","Республика Адыгея");
		countries.put("04","Республика Алтай");
		countries.put("02","Республика Башкортостан");
		countries.put("03","Республика Бурятия");
		countries.put("05","Республика Дагестан");
		countries.put("06","Республика Ингушетия");
		countries.put("07","Кабардино-Балкарская республика");
		countries.put("08","Республика Калмыкия");
		countries.put("09","Карачаево-Черкесская республика");
		countries.put("10","Республика Карелия");
		countries.put("11","Республика Коми");
		countries.put("91","Республика Крым");
		countries.put("12","Республика Марий Эл");
		countries.put("13","Республика Мордовия");
		countries.put("14","Республика Саха (Якутия)");
		countries.put("15","Республика Северная Осетия-Алания");
		countries.put("16","Республика Татарстан");
		countries.put("17","Республика Тыва");
		countries.put("18","Удмуртская республика");
		countries.put("19","Республика Хакасия");
		countries.put("20","Чеченская республика");
		countries.put("21","Чувашская республика");
		countries.put("22","Алтайский край");
		countries.put("75","Забайкальский край");
		countries.put("41","Камчатский край");
		countries.put("23","Краснодарский край");
		countries.put("24","Красноярский край");
		countries.put("59","Пермский край");
		countries.put("25","Приморский край");
		countries.put("26","Ставропольский край");
		countries.put("27","Хабаровский край");
		countries.put("28","Амурская область");
		countries.put("29","Архангельская область");
		countries.put("31","Белгородская область");
		countries.put("32","Брянская область");
		countries.put("33","Владимирская область");
		countries.put("34","Волгоградская область");
		countries.put("35","Вологодская область");
		countries.put("36","Воронежская область");
		countries.put("37","Ивановская область");
		countries.put("38","Иркутская область");
		countries.put("39","Калининградская область");
		countries.put("40","Калужская область");
		countries.put("42","Кемеровская область");
		countries.put("43","Кировская область");
		countries.put("44","Костромская область");
		countries.put("45","Курганская область");
		countries.put("46","Курская область");
		countries.put("47","Ленинградская область");
		countries.put("48","Липецкая область");
		countries.put("49","Магаданская область");
		countries.put("50","Московская область");
		countries.put("51","Мурманская область");
		countries.put("52","Нижегородская область");
		countries.put("53","Новгородская область");
		countries.put("54","Новосибирская область");
		countries.put("55","Омская область");
		countries.put("56","Оренбургская область");
		countries.put("57","Орловская область");
		countries.put("58","Пензенская область");
		countries.put("60","Псковская область");
		countries.put("61","Ростовская область");
		countries.put("62","Рязанская область");
		countries.put("63","Самарская область");
		countries.put("64","Саратовская область");
		countries.put("65","Сахалинская область");
		countries.put("66","Свердловская область");
		countries.put("67","Смоленская область");
		countries.put("68","Тамбовская область");
		countries.put("69","Тверская область");
		countries.put("70","Томская область");
		countries.put("71","Тульская область");
		countries.put("72","Тюменская область");
		countries.put("73","Ульяновская область");
		countries.put("74","Челябинская область");
		countries.put("76","Ярославская область");
		countries.put("77","Москва");
		countries.put("78","Санкт-Петербург");
		countries.put("92","Севастополь");
		countries.put("79","Еврейская автономнаяа область");
		countries.put("83","Ненецкий автономный округ");
		countries.put("86","Ханты-Мансийский автономный округ - Югра");
		countries.put("87","Чукотский автономный округ");
		countries.put("89","Ямало-Ненецкий автономный округ");
		countries.put("99","Байконур (Казахстан)");
		return countries;
	}

	public String s(Object o) {
	return o!=null ? o.toString().trim() : "";
}
	public String getContentOfHTTPPage(String pageAddress, String codePage) throws Exception {
		StringBuilder sb = new StringBuilder();
		URL pageURL = new URL(pageAddress);
		URLConnection uc = pageURL.openConnection();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream(), codePage))) {
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}
		}
		return sb.toString();
	}

	/**
	 * Формируем файл для импорта в "базу данных" МИАЦа со сведениями об оказанной мед. помощи иногородним и иностранным гражданам
	 * @param aDateFrom
	 * @param aDateTo
	 * @param aType
	 * @param aReportType тип отчета (МИАЦ либо ЦБРФ)
	 * @return
	 */
	public String makeReportCostCase(String aDateFrom, String aDateTo, String aType, String aLpuCode, String aReportType) {
		//Начинаем стационар
		try {
			Integer findDays=10; //дни до госпитализации/СПО, в течение которых надо искать договор
		StringBuilder sqlSelect = new StringBuilder();
		StringBuilder sqlAppend = new StringBuilder();
		HashMap<String, String> regionOrCountry;
		HashMap<String, String> profileMap = "BANK".equals(aReportType)?
				getStacProfileMapBank() : getStacProfileMap();
		HashMap<String, String> sredstvaMap = getMiacServiceStreamMap();
		if (aLpuCode == null || aLpuCode.equals("")) {
			return "Не указан код ЛПУ";
		}
		if ("inog".equals(aType)) {
			sqlAppend.append(" and a.addressid is not null and a.kladr not like '30%' ");
			sqlSelect.append(",substring(a.kladr,0,3)");
			regionOrCountry = getRegions();
		} else if ("inos".equals(aType)) {
			sqlSelect.append(",nat.voc_code");
			sqlAppend.append(" and nat.id is not null and nat.voc_code!='643' ");
			regionOrCountry = getCountries();
		} else {
			LOG.error("NO VALID TYPE");
			return "---1";
		}
		String ageSelect="",ageGroup="",vbtSelect="",emSelect="",highTechSelect="",emGroup="",perGroup="", vssGroup="";
		StringBuilder highTechJoin = new StringBuilder();
		StringBuilder periodSelect = new StringBuilder();
		StringBuilder vssSelect = new StringBuilder();
		String vbtSelect2 = "";
		if ("BANK".equals(aReportType)) {
			ageSelect = " ,case when EXTRACT(YEAR from AGE(pat.birthday))>=18 then 'AGE02' else 'AGE01' end as f_age ";
			ageGroup = ", pat.birthday";
			highTechSelect = " ,case when count(h.id)>0 then 'TMC03' else 'TMC02' end as f7_code_vid_mp";
			highTechJoin.append(" left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'")
					.append(" left join hitechmedicalcase h on h.medcase_id=slo.id ");
			vbtSelect = " ,case when (vbt.code='78') then 'CMC02 в дневном стационаре' else 'CMC03 стационарно' end as f8_code_usl_mp";
			emSelect = " ,case when sls.emergency then 'FMC01 экстренная' else 'FMC03 плановая' end as f9_code_form_ok";
			emGroup = " ,sls.emergency";
			periodSelect.append(" ,case when sls.datefinish-sls.datestart <=1 then 'DMC01' else")
					.append(" case when sls.datefinish-sls.datestart between 2 and 5 then 'DMC02' else")
					.append(" case when sls.datefinish-sls.datestart between 6 and 14 then 'DMC03' else")
					.append(" case when sls.datefinish-sls.datestart between 15 and 30 then 'DMC04' else")
					.append(" case when sls.datefinish-sls.datestart >31 then 'DMC05' end end end end end as f10_count");
			perGroup=",sls.datefinish-sls.datestart";
			vssSelect.append(",case when vss.code='CHARGED' then 'SFC05' else case when vss.code in ('OBLIGATORYINSURANCE','BUDGET') then 'SFC03'")
					.append(" else case when vss.code='PRIVATEINSURANCE' then 'SFC04' else 'SFC06' end end end as f11_code_fin_md");
			vssGroup=" ,vss.code";
			vbtSelect2 = ", vbt.name as vbtNameNote ";
        }
		List<Object> listPr = theManager.createNativeQuery("select id from PriceList where isdefault='1'").setMaxResults(1).getResultList();
		String priceListId = null;
		if (!listPr.isEmpty()) {
			priceListId = listPr.get(0).toString();
		}
		StringBuilder sql = new StringBuilder() //считаем стационар
				.append(" select to_char(sls.datefinish,'yyyy-MM') as f0_date")
				.append(" ,count(distinct pat.id) as f1_person_count")
				.append(sqlSelect).append(" as f2_address")
				.append(" ,vbt.code as f3_profile")
				.append(" , vss.financesource as f4_financesource")
				.append(" ,list(sls.id||'') as f5_list")
                .append(ageSelect).append(highTechSelect).append(vbtSelect)
				.append(emSelect).append(periodSelect).append(vssSelect).append(vbtSelect2)
				.append(" from medcase sls")
				.append(" left join medcase dep1 on dep1.parent_id=sls.id and dep1.prevmedcase_id is null and dep1.dtype='DepartmentMedCase'")
				.append(" left join bedfund bf on bf.id=dep1.bedfund_id")
				.append(" left join vocbedtype vbt on vbt.id=bf.bedtype_id")
				.append(" left join mislpu ml on ml.id=sls.department_id")
				.append(" left join patient pat on pat.id=sls.patient_id")
				.append(" left join Omc_Oksm nat on nat.id=pat.nationality_id")
				.append(" left join address2 a on a.addressid=pat.address_addressid")
				.append(" left join vocservicestream vss on vss.id=sls.servicestream_id")
				.append(highTechJoin)
				.append(" where sls.dateFinish between to_date('").append(aDateFrom).append("','dd.MM.yyyy') and to_date('").append(aDateTo).append("','dd.MM.yyyy') ")
				.append(" and sls.dtype='HospitalMedCase'")
				.append(sqlAppend)
				.append(" group by vss.financesource, vbt.code, vbt.name, to_char(sls.datefinish,'yyyy-MM')").append(sqlSelect)
                .append(ageGroup).append(emGroup)
				.append(perGroup).append(vssGroup)
				.append(" order by to_char(sls.datefinish,'yyyy-MM')");
		LOG.info("repotr_stac = " + sql);
		List<Object[]> list = theManager.createNativeQuery(sql.toString()).getResultList();
		LOG.info("repotr_stac found " + list.size() + " records");
		String region, profile, financeSource, patientCount;
		String[] period, hosps;
		double totalSum;
		String uslovia = "стационар"; //AMOKB
		StringBuilder txtFile = new StringBuilder();
		HashMap<String, JSONObject> allRecords = new HashMap<>();
		String pay = "PAY01"; //по умолчанию - наличка
		//1 строка = 9 строчек
		int i = 0;
		try {
			for (Object[] row : list) {
				i++;
				if (1 % 100 == 0) {
					LOG.info("report_stac make " + i + " records");
				}
				period = s(row[0]).split("-");
				patientCount = s(row[1]);
				region=getRegion(row[2].toString(),aReportType,regionOrCountry);
				profile = profileMap.get(s(row[3])) != null ? profileMap.get(s(row[3])) : "PROFILE_CODE=" + s(row[3]);
				financeSource = s(row[4]);
				hosps = s(row[5]).split(",");
				totalSum = 0;
				if (financeSource.equals("CHARGED")) { //Платные случаи
					String firstHosp="";
					for (String hosp : hosps) {
						if (firstHosp.equals(""))
							firstHosp=hosp.trim();
						JSONObject hospitalInfo = new JSONObject(countMedcaseCost(Long.valueOf(hosp.trim()), priceListId));
						totalSum += hospitalInfo.getDouble("totalSum");
					}
					if (!firstHosp.equals("") && "BANK".equals(aReportType))
						pay = getPayType(firstHosp,findDays);

				} else if (financeSource.equals("OBLIGATORY") || "BANK".equals(aReportType)) { //ОМС
					for (String hosp : hosps) {
						try {
							JSONObject costJson = new JSONObject(theExpertService.getMedcaseCost(Long.valueOf(hosp.trim())));
							if (costJson.has("price")) {
								double cost = costJson.getDouble("price");
								totalSum += cost;
							}
						} catch (Exception e) {
							LOG.warn("Не удалось расчитать цену OMC " + e);
							totalSum = 0.0;
						}
					}
				} else {
					continue;
				}
				if (totalSum > 0.0) {
					period[1] = period[1].startsWith("0") ? period[1].substring(1) : period[1];
					String recordHash = (period[0] + "" + period[1]).hashCode() + "#" + region.hashCode() + "" + uslovia.hashCode() + "" + profile.hashCode() + "" + financeSource.hashCode();
					JSONObject rec = allRecords.get(recordHash);
					if (rec != null) {
						totalSum += rec.getDouble("sum");
						patientCount = "" + (Long.valueOf(patientCount) + Long.valueOf(rec.getString("patientCount")));
						rec.remove("sum");
						rec.remove("patientCount");
						rec.put("patientCount", patientCount).put("sum", BigDecimal.valueOf(totalSum).setScale(2, RoundingMode.HALF_EVEN));
					} else {
						rec = new JSONObject();
						rec.put("hash", recordHash).put("period0", period[0]).put("period1", period[1]).put("region", region).put("uslovia", uslovia).put("profile", profile).put("financeSource", sredstvaMap.get(financeSource))
								.put("patientCount", patientCount).put("sum", BigDecimal.valueOf(totalSum).setScale(2, RoundingMode.HALF_EVEN));
						if ("BANK".equals(aReportType)) {
							rec.put("age", row[6]);
							rec.put("code_vid_mp",row[7]);
							rec.put("code_usl_mp",row[8]);
							rec.put("code_form_ok",row[9]);
							rec.put("code_prod",row[10]);
							rec.put("code_fin_md",row[11]);
							rec.put("profile",profile);
							rec.put("note",row[12]);
							rec.put("pay",pay);
						}
					}
					allRecords.put(recordHash, rec);
				} else {
					//	LOG.error("HOSP, price = null");
				}
			}
			LOG.info("report_stac_finished");
		} catch (JSONException e) {
			LOG.error("JSONEXEPTION HAPP", e);
		}
		//Начинаем искать пол-ку
		String vwfSelect = "";
		if ("BANK".equals(aReportType))
			vwfSelect = " , vwf.name ";
		sql = new StringBuilder();
		LOG.warn("Start search policlinic");
		sql.append("select to_char(spo.datefinish,'yyyy-MM') as f0_date")
				.append(" ,count(distinct pat.id) as f1_person_count")
				.append(sqlSelect).append(" as f2_address")
				.append(" ,vwf.code as f3_profile")
				.append(" , vss.financesource as f4_financesource")
				.append(" ,sum (coalesce(smc.medserviceamount ,1)*pp.cost) as f5_totalSum")
				.append(",list(''||vis.id) as f6_listVisits")
				.append(", list(distinct (vis.parent_id) ||'') as f7_listSpo")
				.append(ageSelect)
                .append(periodSelect.toString().replaceAll("sls.datefinish-sls.datestart","spo.datefinish-spo.datestart"))
				.append(vssSelect)
				.append(vwfSelect)
				.append(" from medcase spo")
				.append(" left join medcase vis on vis.parent_id=spo.id")
				.append(" left join medcase smc on smc.parent_id=vis.id and smc.dtype='ServiceMedCase'")
				.append(" left join pricemedservice pms on pms.medservice_id=smc.medservice_id")
				.append(" left join priceposition pp on pp.id=pms.priceposition_id ").append(priceListId != null ? " and pp.pricelist_id=" + priceListId : "")
				.append(" left join patient pat on pat.id=vis.patient_id")
				.append(" left join Omc_Oksm nat on nat.id=pat.nationality_id")
				.append(" left join address2 a on a.addressid=pat.address_addressid")
				.append(" left join workfunction wf on wf.id=vis.workfunctionexecute_id")
				.append(" left join vocworkfunction vwf on vwf.id=wf.workfunction_id")
				.append(" left join vocservicestream vss on vss.id=vis.servicestream_id")
				.append(" where spo.dtype='PolyclinicMedCase' and (vis.dtype='Visit' or vis.dtype='ShortMedCase') ")
				.append(" and spo.datefinish between to_date('").append(aDateFrom).append("','dd.MM.yyyy') and to_date('").append(aDateTo).append("','dd.MM.yyyy') ")
				.append(" and vss.financesource is not null and vss.financesource!='' ")
				.append(sqlAppend)
				.append(" group by to_char(spo.datefinish,'yyyy-MM'),vwf.code , vss.financesource").append(sqlSelect)
				.append(ageGroup)
                .append(perGroup.replaceAll("sls.datefinish-sls.datestart","spo.datefinish-spo.datestart"))
				.append(vssGroup)
				.append(vwfSelect);

		LOG.info("===========repotr_pol = " + sql);
		list = theManager.createNativeQuery(sql.toString()).getResultList();
		LOG.info("repotr_pol found " + list.size() + " records");
		uslovia = "амбулаторно";
		profileMap = "BANK".equals(aReportType)?
				getPolicProfileMapBank() : getPolicProfileMap();
		try {
			i = 0;
			for (Object[] row : list) {
				i++;
				if (i % 100 == 0) {
					LOG.info("making " + i + " records pol");
				}
				period = s(row[0]).split("-");
				patientCount = s(row[1]);
				region=getRegion(row[2].toString(),aReportType,regionOrCountry);
				profile = profileMap.get(s(row[3])) != null ? profileMap.get(s(row[3])) : "PROFILE_CODE=" + s(row[3]);
				financeSource = s(row[4]);
				totalSum = 0;
				if (financeSource.equals("OBLIGATORY") || financeSource.equals("BUDGET")) { //считаем цену за ОМС
					String[] spoIds = row[7].toString().split(",");
					String firstSpo="";
					for (String spoId : spoIds) {
						if (firstSpo.equals(""))
							firstSpo=spoId.trim();
						JSONObject costJson = new JSONObject(theExpertService.getMedcaseCost(Long.valueOf(spoId.trim())));
						if (costJson.has("price")) {
							double cost = costJson.getDouble("price");
							totalSum += cost;
						}
					}
					if (!firstSpo.equals("") && "BANK".equals(aReportType))
						pay = getPayType(firstSpo,findDays);
				} else {
					try {
						totalSum = Double.valueOf(s(row[5]));
					} catch (NumberFormatException e) {
						LOG.error("Не удалось посчитать цену поликлинического случая: " + s(row[5]));
						totalSum = 0.0;
					}
				}
				if (totalSum > 0.0) {
					period[1] = period[1].startsWith("0") ? period[1].substring(1) : period[1];
					String recordHash = (period[0] + "" + period[1]).hashCode() + "#" + (region != null ? region.hashCode() : "_NULL_REGION") + "" + uslovia.hashCode() + "" + profile.hashCode() + "" + financeSource.hashCode();
					JSONObject rec = allRecords.get(recordHash);
					if (rec != null) {
						totalSum += rec.getDouble("sum");
						patientCount = "" + (Long.valueOf(patientCount) + Long.valueOf(rec.getString("patientCount")));
						rec.remove("sum");
						rec.remove("patientCount");
						rec.put("patientCount", patientCount).put("sum", BigDecimal.valueOf(totalSum).setScale(2, RoundingMode.HALF_EVEN));
					} else {
						rec = new JSONObject();
						rec.put("hash", recordHash).put("period0", period[0]).put("period1", period[1]).put("region", region).put("uslovia", uslovia).put("profile", profile).put("financeSource", sredstvaMap.get(financeSource))
								.put("patientCount", patientCount).put("sum", BigDecimal.valueOf(totalSum).setScale(2, RoundingMode.HALF_EVEN));
                        if ("BANK".equals(aReportType)) {
							rec.put("age", row[8].toString());
							rec.put("code_vid_mp", "TMC02"); //только специализированная в поликлинике
							rec.put("code_usl_mp", "CMC01 амбулаторно"); //только амбулаторно
							rec.put("code_form_ok", "FMC03 плановая"); //только плановая
							rec.put("code_prod", row[9]);
							rec.put("code_fin_md", row[10]);
							rec.put("note", row[11]);
							rec.put("profile", profile);
							rec.put("pay",pay);
						}
					}
					allRecords.put(recordHash, rec);

				}
			}

			if ("MIAC".equals(aReportType)) {
				//Закончили искать пол-ку
				//Начинаем формировать файл.
				i = 0;
				LOG.warn("====start make file " + i);
				for (JSONObject rec : allRecords.values()) {
					i++;
					if (i % 100 == 0) {
						LOG.info("making " + i + " records");
					}
					if (rec != null) {
						txtFile.append(aLpuCode).append("\n")
								.append(rec.getString("period0")).append("\n")
								.append(rec.getString("period1")).append("\n")
								.append(rec.getString("region")).append("\n")
								.append(rec.getString("uslovia")).append("\n")
								.append(rec.getString("profile")).append("\n")
								.append(rec.getString("financeSource")).append("\n")
								.append(rec.getString("patientCount")).append("\n")
								.append(String.valueOf(BigDecimal.valueOf(rec.getDouble("sum")).setScale(2, RoundingMode.HALF_UP).doubleValue()).replace(".", ",")).append("\n");
					} else {
						LOG.error("make file object = NULL!!!");
					}
				}
			}
		} catch (Exception e) {
			LOG.error("some exception");
			e.printStackTrace();
		}
		return "BANK".equals(aReportType)? allRecords.values().toString() : createFile(txtFile, aType);
	}catch(Exception e) {
			LOG.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Получить форму оплаты случая лечения (в случае, если CHARGED) - первая оплата
	 *
	 * @param aMedcaseId Случай лечения
	 * @param findDays За сколько дней до операции искать договор
	 * @return String Тип оплаты PAY01 (нал) / PAY03 (безнал)
	 */
	private String getPayType(String aMedcaseId, Integer findDays) {
		StringBuilder sql = new StringBuilder();
		sql.append("select case when co.ispaymentterminal =true then '1' else '0' end")
				.append(" from contractaccount c")
				.append(" left join contractaccountoperation co on co.account_id =c.id")
				.append(" where c.datefrom between (select datestart from medcase where id=")
				.append(aMedcaseId).append(")-").append(findDays).append(" and")
				.append(" (select datestart from medcase where id=").append(aMedcaseId)
				.append(") limit 1");
		List<Object> list = theManager.createNativeQuery(sql.toString()).getResultList();
		//первая оплата
		return !list.isEmpty() && list.get(0).equals("1")?
				"PAY03" : "PAY01";
	}

	/**
	 * Получить код страны для банка (всегда трёхзначный)
	 *
	 * @param regCode Код региона/страны
	 * @return String Регион для отчёта
	 */
	private String getRegionForBank(String regCode) {
		while (regCode.length()<3)
			regCode="0"+regCode;
		return regCode;
	}

	/**
	 * Получить регион для разных типов отчёта
	 *
	 * @param regCode Код региона/страны
	 * @param aReportType Тип отчёта
	 * @param regionOrCountry Соответствие код-регион/страна
	 * @return String Регион для отчёта
	 */
	private String getRegion(String regCode,String aReportType,HashMap<String, String> regionOrCountry) {
		String region=null;
		if ("MIAC".equals(aReportType))
			region = regionOrCountry.get(s(regCode)) != null ? regionOrCountry.get(s(regCode)) : "REGION_CODE=" + s(regCode);
		else if ("BANK".equals(aReportType))
			region = getRegionForBank(s(regCode));
		else {
			LOG.info("unknown type report");
		}
		return region;
	}

	public String createFile (StringBuilder aText, String aFileName) {
        EjbEcomConfig config = EjbEcomConfig.getInstance() ;
        String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
        workDir+="/"+aFileName+"."+aFileName;
		try (OutputStream os = new FileOutputStream(workDir)){
			os.write(aText.toString().getBytes());
			LOG.info("i create file = /rtf/"+aFileName+"."+aFileName);
			return "/rtf/"+aFileName+"."+aFileName;
		} catch (IOException e) {
			LOG.error(e.getLocalizedMessage(),e);
		}
		return "NO_FILE";
	}
	public String countMedcaseCost (Long aMedcaseId) {return  countMedcaseCost(aMedcaseId,null);}
	public String countMedcaseCost (Long aMedcaseId,String aPriceList) {return  countMedcaseCost(aMedcaseId,aPriceList,false);}
	public String getAllServicesByMedCase (Long aMedcaseId) {return  countMedcaseCost(aMedcaseId,null,true);}
	/**
	 * Расчитываем стоимость случая госпитализации
	 * @param aMedcaseId ID госпитализации
	 * @return - JSON объект с полной стоимостью + список оказанных услуг
	 */
	public String countMedcaseCost (Long aMedcaseId, String aPriceListId, boolean allIsCharged) {
		JSONObject root = new JSONObject();
		String ppidNull=allIsCharged? " " : " and pp.id is not null ";
		try {
			String priceListId=null;
			if (aPriceListId!=null) {
				priceListId=aPriceListId;
			} else {
				List<Object> list = theManager.createNativeQuery("select id from pricelist where isdefault='1'").getResultList();
				if (list!=null && !list.isEmpty()) {
					priceListId=list.get(0).toString();
				}
			}
			String idsertypebed = "11";
			String dtype="";
			Long patientId;
			Long serviceStreamId;
			MedCase mc = (MedCase) theManager.createQuery(" from MedCase where id=:id").setParameter("id",aMedcaseId).getSingleResult();
			if (mc instanceof HospitalMedCase) {
				HospitalMedCase hmc = (HospitalMedCase) mc;
				patientId = hmc.getPatient().getId();
				serviceStreamId = hmc.getServiceStream().getId();
				String startDate = DateFormat.formatToDate(hmc.getDateStart());
				String finishDate = DateFormat.formatToDate(hmc.getDateFinish() != null ? hmc.getDateFinish() : new java.sql.Date(new java.util.Date().getTime()));
				if (hmc.getServiceStream().getFinanceSource() != null && hmc.getServiceStream().getFinanceSource().equals("CHARGED") || allIsCharged) { //Для платных считаем цену случая сами

				StringBuilder sql = new StringBuilder().append("select slo.id as f0,ml.name||' '||vbt.name||' '||vbst.name||' '||vrt.name as sloinfo")
						.append(" ,list(pp.code||' '||pp.name) as f2_ppname")
						.append(" ,case when coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart=0 then '1'")
						.append("else coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart+case when vbst.code='1' then 0 else 1 end end as f3_cntDays")
						.append(",max(pp.cost) as f4_ppcost")
						.append(",max((")
						.append("case when coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart=0 then '1'")
						.append("else coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart+case when vbst.code='1' then 0 else 1 end end")
						.append("* pp.cost)) as f5_ppsum,list(ms.code||' '||ms.name) as f6_msifo,ms.code ")
						.append(" from medcase slo")
						.append(" left join medcase sls on sls.id=slo.parent_id")
						.append(" left join Vochosptype vht on vht.id=sls.hosptype_id")
						.append(" left join statisticstub ss on ss.id=sls.statisticStub_id")
						.append(" left join bedfund bf on bf.id=slo.bedfund_id")
						.append(" left join vocbedtype vbt on vbt.id=bf.bedtype_id")
						.append(" left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id")
						.append(" left join workPlace wp on wp.id=slo.roomNumber_id")
						.append(" left join Patient pat on pat.id=slo.patient_id")
						.append(" left join VocRoomType vrt on vrt.id=wp.roomType_id")
						.append(" left join mislpu ml on ml.id=slo.department_id")
						.append(" left join workfunctionservice wfs on wfs.lpu_id=slo.department_id")
						.append(" and bf.bedtype_id=wfs.bedtype_id and bf.bedsubtype_id=wfs.bedsubtype_id")
						.append(" and wfs.roomType_id=wp.roomType_id")
						.append(" left join medservice ms on ms.id=wfs.medservice_id")
						.append(" left join pricemedservice pms on pms.medservice_id=wfs.medservice_id")
						.append(" left join priceposition pp on pp.id=pms.priceposition_id")
						.append(" and (pp.isvat is null or pp.isvat='0')")
						.append(" where slo.parent_id=").append(mc.getId())
						.append(" and ms.servicetype_id='").append(idsertypebed).append("' ").append((priceListId!=null?" and pp.priceList_id='"+priceListId+"'":""))
						.append(" group by slo.id,ml.name,vbt.name,vbst.code,vbst.name,vrt.name,slo.datefinish,slo.transferdate,slo.datestart,vht.code,ms.code");
				List<Object[]> list = theManager.createNativeQuery(sql.toString()).getResultList();
				JSONArray arr = new JSONArray(); //Койко-дни
				double cost ;
				double sum = 0.0;
				double totalSum = 0.0;
				for (Object[] o : list) { //Считаем стоимость койко-дней
					JSONObject kd = new JSONObject();
					cost = Double.valueOf(o[5].toString());
					kd.put("sum", cost);
					kd.put("name", o[6]);
					kd.put("count", o[3]);
					kd.put("vmscode", o[7]);
					sum += cost;
					arr.put(kd);
				}
				root.put("bedDays", arr);
				totalSum += sum;

				sql = new StringBuilder()
						.append("select")
						.append(" pp.code||' '||pp.name as ppname")
						.append(" ,pp.cost as ppcost, ms.code ")
						.append(" from medcase vis")
						.append(" left join workfunction wf on wf.id=vis.workfunctionexecute_id")
						.append(" left join vocworkfunction vwf on vwf.id=wf.workfunction_id")
						.append(" left join worker w on w.id=wf.worker_id")
						.append(" left join patient wp on wp.id=w.person_id")
						.append(" left join vocservicestream vss on vss.id=vis.servicestream_id")
						.append(" left join medcase smc on smc.parent_id=vis.id and upper(smc.dtype)='SERVICEMEDCASE'")
						.append(" left join medservice ms on ms.id=smc.medservice_id")
						.append(" left join pricemedservice pms on pms.medservice_id=smc.medservice_id")
						.append(" left join priceposition pp on pp.id=pms.priceposition_id")
						.append(" where vis.patient_id=").append(patientId).append(" and (vis.datestart between to_date('").append(startDate).append("','dd.mm.yyyy') and to_date('").append(finishDate).append("','dd.mm.yyyy')")
						.append("	and upper(vis.dtype)='VISIT' and (vss.code='HOSPITAL' or vss.id='").append(serviceStreamId).append("' or vss.code='OTHER')")
						.append("	or")
						.append("	vis.datestart-to_date('").append(startDate).append("','dd.mm.yyyy') = -1")
						.append("	and upper(vis.dtype)='VISIT' and ( vss.id='").append(serviceStreamId).append("' ))");
						if (priceListId!=null){sql.append(" and pp.priceList_id='").append(priceListId).append("'");}
						sql.append(" and (vis.noActuality='0' or vis.noActuality is null)")
						.append(" order by vis.datestart");
				list = theManager.createNativeQuery(sql.toString()).getResultList();
				arr = new JSONArray(); //Диагностика
				sum = 0.0;

				for (Object[] o : list) { //Считаем стоимость диагностики
					JSONObject kd = new JSONObject();
					cost = Double.valueOf(o[1].toString());
					kd.put("sum", cost);
					kd.put("name", o[0]);
					kd.put("count", "1");
					kd.put("vmscode", o[2]);
					sum += cost;
					arr.put(kd);
				}
				root.put("diagnostic", arr);
				totalSum += sum;

				//Лаборатория
				sql = new StringBuilder()
						.append("select")
						.append(" pp.code||' '||pp.name as ppname")
						.append(" ,pp.cost as ppcost, ms.code ")
						.append(" from medcase vis")
						.append(" left join workfunction wf on wf.id=vis.workfunctionexecute_id")
						.append(" left join vocworkfunction vwf on vwf.id=wf.workfunction_id")
						.append(" left join worker w on w.id=wf.worker_id")
						.append(" left join patient wp on wp.id=w.person_id")
						.append(" left join vocservicestream vss on vss.id=vis.servicestream_id")
						.append(" left join medcase smc on smc.parent_id=vis.id")
						.append(" left join medservice ms on ms.id=smc.medservice_id")
						.append(" left join pricemedservice pms on pms.medservice_id=smc.medservice_id")
						.append(" left join priceposition pp on pp.id=pms.priceposition_id and pp.priceList_id='").append(priceListId).append("'")
						.append(" where vis.parent_id='").append(aMedcaseId).append("'")
						.append(" and vis.datestart between to_date('").append(startDate).append("','dd.mm.yyyy') and to_date('").append(finishDate).append("','dd.mm.yyyy')")
						.append(" and upper(vis.dtype)='VISIT' and upper(smc.dtype)='SERVICEMEDCASE'")
						.append(" and (vss.code='HOSPITAL' or vss.id is null)")
						.append(" and (vis.noActuality='0' or vis.noActuality is null) ")
						.append(ppidNull);//pp.id is not null");
				//LOG.info("calc price, labsurvey = " + sql);
				list = theManager.createNativeQuery(sql.toString()).getResultList();
				arr = new JSONArray();
				sum = 0.0;

				for (Object[] o : list) { //Считаем стоимость лаборатории
					JSONObject kd = new JSONObject();
					cost = (o[1]==null)? 0:Double.valueOf(o[1].toString());
					kd.put("sum", cost);
					kd.put("name", o[0]);
					kd.put("count", "1");
					kd.put("vmscode", o[2]);
					sum += cost;
					arr.put(kd);
				}
				root.put("laboratory", arr);
				totalSum += sum;

				//Операции
				sql = new StringBuilder()
						.append("select")
						.append(" pp.code||' '||pp.name as ppname")
						.append(" ,pp.cost as ppcost, ms.code ")
						.append(" from SurgicalOperation so")
						.append(" left join workfunction wf on wf.id=so.surgeon_id")
						.append(" left join vocworkfunction vwf on vwf.id=wf.workfunction_id")
						.append(" left join worker w on w.id=wf.worker_id")
						.append(" left join patient wp on wp.id=w.person_id")
						.append(" left join medcase slo on slo.id=so.medcase_id")
						.append(" left join vocservicestream vss on vss.id=so.servicestream_id")
						.append(" left join medservice ms on ms.id=so.medservice_id")
						.append(" left join pricemedservice pms on pms.medservice_id=so.medservice_id")
						.append(" left join priceposition pp on pp.id=pms.priceposition_id and  pp.priceList_id='").append(priceListId).append("'")
						.append(" where (slo.parent_id='").append(aMedcaseId).append("' or slo.id='").append(aMedcaseId).append("') ")
						.append(ppidNull);
				//LOG.info("calc price, operation = " + sql);
				list = theManager.createNativeQuery(sql.toString()).getResultList();
				arr = new JSONArray();
				sum = 0.0;

				for (Object[] o : list) { //Считаем стоимость операций
					JSONObject kd = new JSONObject();
					cost = (o[1]==null)? 0:Double.valueOf(o[1].toString());
					kd.put("sum", cost);
					kd.put("name", o[0]);
					kd.put("count", "1");
					kd.put("vmscode", o[2]);
					sum += cost;
					arr.put(kd);
				}
				root.put("surgicaloperation", arr);
				totalSum += sum;

				//Анастезия
				sql = new StringBuilder()
						.append("select")
						.append(" pp.code||' '||pp.name as ppname")
						.append(" ,pp.cost as ppcost, ms.code ")
						.append(" from Anesthesia aso")
						.append(" left join VocAnesthesiaMethod vam on vam.id=aso.method_id")
						.append(" left join VocAnesthesia va on va.id=aso.type_id")
						.append(" left join SurgicalOperation so on so.id=aso.surgicalOperation_id")
						.append(" left join workfunction wf on wf.id=aso.anesthesist_id")
						.append(" left join vocworkfunction vwf on vwf.id=wf.workfunction_id")
						.append(" left join worker w on w.id=wf.worker_id")
						.append(" left join patient wp on wp.id=w.person_id")
						.append(" left join medcase slo on slo.id=so.medcase_id")
						.append(" left join vocservicestream vss on vss.id=so.servicestream_id")
						.append(" left join medservice ms on ms.id=aso.medservice_id")
						.append(" left join pricemedservice pms on pms.medservice_id=aso.medservice_id")
						.append(" left join priceposition pp on pp.id=pms.priceposition_id")
						.append(" where (slo.parent_id='").append(aMedcaseId).append("' or slo.id='").append(aMedcaseId).append("') and pp.priceList_id='").append(priceListId).append("'");
				//LOG.info("calc price, anathesia = " + sql);
				list = theManager.createNativeQuery(sql.toString()).getResultList();
				arr = new JSONArray(); //Операции
				sum = 0.0;

				for (Object[] o : list) { //Считаем стоимость анастезии
					JSONObject kd = new JSONObject();
					cost = Double.valueOf(o[1].toString());
					kd.put("sum", cost);
					kd.put("name", o[0]);
					kd.put("count", "1");
					kd.put("vmscode", o[2]);
					sum += cost;
					arr.put(kd);
				}
				root.put("anesthesia", arr);
				totalSum += sum;

				//Доп. услуги
				sql = new StringBuilder()
						.append("select")
						//.append(" so.id,to_char(so.dateStart,'dd.mm.yyyy')||' - '||ms.code||'. '||ms.name||' - '||vwf.name||' '||wp.lastname as sloinfo")
						.append(" pp.code||' '||pp.name as ppname")
						.append(" ,pp.cost as ppcost, ms.code ")
						.append(" ,mkb.code as mkbcode")
						.append(" ,coalesce(so.medserviceamount,'1') as f6_amount")
						.append(" ,pp.cost*coalesce(so.medserviceamount,'1') as f7_sum")
						.append(" from MedCase so")
						.append(" left join VocIdc10 mkb on mkb.id=so.idc10_id")
						.append(" left join workfunction wf on wf.id=so.workFunctionExecute_id")
						.append(" left join vocworkfunction vwf on vwf.id=wf.workfunction_id")
						.append(" left join worker w on w.id=wf.worker_id")
						.append(" left join patient wp on wp.id=w.person_id")
						.append(" left join medcase slo on slo.id=so.parent_id")
						.append(" left join vocservicestream vss on vss.id=so.servicestream_id")
						.append(" left join medservice ms on ms.id=so.medservice_id")
						.append(" left join pricemedservice pms on pms.medservice_id=so.medservice_id")
						.append(" left join priceposition pp on pp.id=pms.priceposition_id and pp.priceList_id='").append(priceListId).append("'")
						.append(" where (slo.parent_id='").append(aMedcaseId).append("' or slo.id='").append(aMedcaseId).append("')")
						.append(" and upper(so.dtype)='SERVICEMEDCASE' and upper(slo.dtype)!='VISIT' ")
						.append(ppidNull);
				//LOG.info("calc price, dop_uslugi = " + sql);
				list = theManager.createNativeQuery(sql.toString()).getResultList();
				arr = new JSONArray(); //Операции
				sum = 0.0;

				for (Object[] o : list) { //Считаем стоимость Доп. услуги
					JSONObject kd = new JSONObject();
					cost = (o[1]==null)? 0:Double.valueOf(o[1].toString());
					kd.put("sum", cost);
					kd.put("name", o[0]);
					kd.put("count", "1");
					kd.put("vmscode", o[2]);
					sum += cost;
					arr.put(kd);
				}
				root.put("otherServices", arr);
				totalSum += sum;
				root.put("totalSum", totalSum);
			} else if (hmc.getServiceStream().getFinanceSource()!=null&&(hmc.getServiceStream().getFinanceSource().equals("BUDGET1")||hmc.getServiceStream().getFinanceSource().equals("OBLIGATORY1"))) { //ОМС - узнаем у Звягина
					try {
						String[] arr ={"",""};
						for (int j=0;j<arr.length;j++) {
							if (arr[j].startsWith("render")) {
								String[] arrPrice =arr[j].split("%23");
								String price = arrPrice[0].substring(7,arrPrice[0].length());
								root.put("totalSum",price);


							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					root.put("totalSum",0.00);
				}
			dtype="Hospital";
            } else if (mc instanceof PolyclinicMedCase) {

				dtype="POlyclinic";
			}else {
                dtype="unknow";
            }
			root.put("dtype",dtype);
		} catch (JSONException e) {
			LOG.error("some JSON exception happens");
			e.printStackTrace();
		}

		return root.toString();
	}
public String getDefaultParameterByConfig (String aParameter, String aDefaultValue) {
		List<Object[]> list =  theManager.createNativeQuery("select sf.id,sf.keyvalue from SoftConfig sf where  sf.key='" +aParameter+"'").getResultList();
		return  list.isEmpty() ? aDefaultValue : list.get(0)[1].toString();
}
	private  StringBuilder param(String aParam, Object aValue) {
		StringBuilder ret = new StringBuilder();
		if (aValue!=null &&!(""+aValue).equals("")) {
			ret.append("&").append(aParam).append("=").append(aValue) ;
		}
		return ret ;
	}
	private  StringBuilder getRender(String aHtmlCode,Object aAdditionDataFrom, Object aAdditionDataTo) {
		String[] htm = aHtmlCode.toUpperCase().split("BODY") ;
		StringBuilder ret = new StringBuilder() ;
		if (htm.length>2) {
			aHtmlCode = htm[1].substring(1) ;
			if (aHtmlCode.length()>2) {
				aHtmlCode = aHtmlCode.substring(0,aHtmlCode.length()-2) ;
			}
			String[] s = aHtmlCode.trim().split("#") ;
			if (s.length>1) {
				ret.append(s[0]).append("#").append(aAdditionDataFrom).append(s[1]).append(aAdditionDataTo) ;
			}
			return ret;
		} else {
			return ret ;
		}
	}
	/**
	 * Расчитываем значения для отправки на сервис экспертизы


	 */


	public String getDischargeEpicrisis(long aMedCaseId) {
		return HospitalMedCaseViewInterceptor.getDischargeEpicrisis(aMedCaseId, theManager) ;
	}

	public static void saveDischargeEpicrisisByCase(MedCase aMedCase,String aDischargeEpicrisis,EntityManager aManager) {
		aManager.createNativeQuery("delete from diary d where d.medcase_id= "+aMedCase.getId()+" and upper(d.dtype)='DISCHARGEEPICRISIS' ").executeUpdate() ;
		int len = 15000 ;
		int lend = aDischargeEpicrisis.length() ;
		int cnt = lend/len;
		for (int i=0;i<cnt;i++) {
			DischargeEpicrisis prot = new DischargeEpicrisis() ;
			prot.setRecord(aDischargeEpicrisis.substring(i*len,(i+1)*len<lend?(i+1)*len:lend)) ;
			prot.setMedCase(aMedCase) ;
			aManager.persist(prot);
		}
		if (lend%len>0) {
			DischargeEpicrisis prot = new DischargeEpicrisis() ;
			prot.setRecord(aDischargeEpicrisis.substring(len*cnt)) ;
			prot.setMedCase(aMedCase) ;
			aManager.persist(prot);
		}
	}
	public static void saveDischargeEpicrisis(long aMedCaseId,String aDischargeEpicrisis,EntityManager aManager) {
		HospitalMedCase medCase = aManager.find(HospitalMedCase.class, aMedCaseId) ;
		saveDischargeEpicrisisByCase(medCase, aDischargeEpicrisis, aManager) ;
	}
	private String getText(org.jdom.Element aEl, String aParameter) {
		return aEl!=null?
				(aEl.getChild(aParameter)!=null && !aEl.getChild(aParameter).getText().trim().equals("")?(aEl.getChild(aParameter).getText().trim()):null)
				:
				null ;
	}
	private String getChildText(org.jdom.Element aEl, String aParameter) {
		StringBuilder ret = new StringBuilder() ;
		if (aEl!=null &&( aEl.getChild(aParameter)!=null) ) {
			for (org.jdom.Element e : (List<org.jdom.Element>) aEl.getChild(aParameter).getChildren() ) {
				ret.append(", ").append(e.getName()).append(" ").append(e.getText()) ;
			}
		}
		return ret.substring(2) ;
	}
	private String getDate(org.jdom.Element aEl,String aParameter) {
		String value=getText(aEl,aParameter) ;
		if (value==null) return null ;
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd") ;
		long l = 0 ;
		try {
			l=f.parse(value).getTime() ;
		} catch(Exception e) {
			e.printStackTrace() ;
			//String aFormat1 =  ;
			f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss") ;
			try {
				l=f.parse(value).getTime() ;

			} catch(Exception e1) {
				e1.printStackTrace() ;
				return null ;
			}
		}
		return f.format(l) ;
	}
	private Object getTime(org.jdom.Element aEl,String aParameter) {
		String value=getText(aEl,aParameter) ;
		try {
			return value==null || value.equals("")?null:value ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return null ;
		}
	}


	public String importFileDataFond(long aMonitorId, String aFilename) throws Exception {
		File f = new File(aFilename) ;
		String type =null;
		List<WebQueryResult> list = new LinkedList<>() ;

		try {
			org.jdom.Document doc = new SAXBuilder().build(f);
			XmlDocument xmlDocError = new XmlDocument() ;
			org.w3c.dom.Element root_error = xmlDocError.newElement(xmlDocError.getDocument(), "ZL_LIST", null);
			org.jdom.Element parConfigElement = doc.getRootElement();
			for (Object o : parConfigElement.getChildren()) {
				org.jdom.Element parEl = (org.jdom.Element) o;
				if ("ZGLV".equals(parEl.getName())) {
					type = parEl.getChild("FILENAME").getText().trim().toUpperCase().substring(0,2) ;
				} else if("NPR".equals(parEl.getName())) {
					if (parEl.getChildren("REFREASON").isEmpty()) {
						WebQueryResult wqr = new WebQueryResult() ;
						wqr.set1(getText(parEl,"N_NPR")) ;
						wqr.set2(getDate(parEl,"D_NPR")) ;
						wqr.set3(getText(parEl,"FOR_POM")) ;
						wqr.set4(getText(parEl,"NCODE_MO")) ;
						wqr.set5(getText(parEl,"DCODE_MO")) ;
						wqr.set6(getText(parEl,"VPOLIS")) ;
						wqr.set7(getText(parEl,"SPOLIS")) ;
						wqr.set8(getText(parEl,"NPOLIS")) ;
						wqr.set9(getText(parEl,"SMO")) ;
						wqr.set10(getText(parEl,"SMO_OGRN")) ;
						wqr.set11(getText(parEl,"SMO_OK")) ;
						wqr.set12(getText(parEl,"SMO_NAM")) ;
						wqr.set13(getText(parEl,"FAM")) ;
						wqr.set14(getText(parEl,"IM")) ;
						wqr.set15(getText(parEl,"OT")) ;
						wqr.set16(getText(parEl,"W")) ;
						wqr.set17(getDate(parEl,"DR")) ;
						wqr.set18(getText(parEl,"CT")) ;
						wqr.set19(getText(parEl,"DS1")) ;
						wqr.set20(getText(parEl,"PROFIL")) ;
						wqr.set21(getText(parEl,"IDDOKT")) ;
						if ("N1".equals(type)) {
							wqr.set22(getDate(parEl,"DATE_1")) ;
						} else {
							wqr.set23(getDate(parEl,"DATE_1")) ;
						}
						wqr.set24(getText(parEl,"NHISTORY")) ;
						wqr.set25(getTime(parEl,"TIME_1")) ;
						wqr.set26(getDate(parEl,"DATE_2")) ;
						wqr.set27(getText(parEl,"DET")) ;
						wqr.set28(getText(parEl,"USL_OK")) ;
						if (wqr.get1()!=null && !(""+wqr.get1()).equals("")){
							list.add(wqr) ;
						}
					} else {
					//	isErrorFile = true ;
						org.w3c.dom.Element zap = xmlDocError.newElement(root_error, "NPR", null);
						String[] flds ={"N_NPR","D_NPR","FOR_POM","NCODE_MO","DCODE_MO"
								,"VPOLIS","SPOLIS","NPOLIS","SMO","SMO_OGRN","SMO_OK"
								,"SMO_NAM","FAM","IM","OT","W","DR","CT","DS1"
								,"PROFIL","IDDOKT","DATE_1","NHISTORY","TIME_1","DATE_2"
						} ;
						for (String fld:flds) {
							XmlUtil.recordElementInDocumentXml(xmlDocError,zap,fld,getText(parEl,fld),true,"") ;
						}
						String fld = "REFREASON" ;
						XmlUtil.recordElementInDocumentXml(xmlDocError,zap,fld,getChildText(parEl,fld),true,"") ;
					}
				}
			}
			importDataFond(aMonitorId, type,list) ;
		} catch (Exception e) {

		}
		return null ;
	}
	public String importDataFond(long aMonitorId, String aFileType,List<WebQueryResult> aList) {
		IMonitor monitor = null;
		try {
			try {
				monitor = theMonitorService.getMonitor(aMonitorId);
				monitor.advice(20) ;
			} catch(Exception e) {
				e.fillInStackTrace() ;
			}

			int size = aList.size()/80 ;

			for (int i=0;i<aList.size();i++) {
				WebQueryResult wqr=aList.get(i) ;
				if (monitor.isCancelled()) {
					throw new IllegalMonitorStateException("Прервано пользователем");
				}
				//Object id = null ;
				if (wqr.get1()!=null) {
					List<Object> lf = theManager.createNativeQuery("select id from HospitalDataFond where numberFond='"+wqr.get1()+"' order by id desc").setMaxResults(1).getResultList() ;
					//HospitalDataFond hdf ;
					Object id ;
					if (!lf.isEmpty()) {
						id=lf.get(0) ;
					} else  {
						id = null;
					}
					StringBuilder sql1 = new StringBuilder() ;
					StringBuilder sql2 = new StringBuilder() ;
					// create
					if (id==null) {
						sql1.append("insert into HospitalDataFond (") ;
						if (wqr.get1()!=null) {sql1.append("numberFond,") ; sql2.append("'").append(wqr.get1()).append("',") ;}
						if (wqr.get2()!=null) {sql1.append("directDate,") ; sql2.append("to_date('").append(wqr.get2()).append("','yyyy-mm-dd'),") ;}
						if (wqr.get3()!=null) {sql1.append("formHelp,") ; sql2.append("'").append(wqr.get3()).append("',") ;}
						if (wqr.get4()!=null) {sql1.append("OrderLpuCode,") ; sql2.append("'").append(wqr.get4()).append("',") ;}
						if (wqr.get5()!=null) {sql1.append("DirectLpuCode,") ; sql2.append("'").append(wqr.get5()).append("',") ;}
						if (wqr.get6()!=null) {sql1.append("TypePolicy,") ; sql2.append("'").append(wqr.get6()).append("',") ;}
						if (wqr.get7()!=null) {sql1.append("SeriesPolicy,") ; sql2.append("'").append(wqr.get7()).append("',") ;}
						if (wqr.get8()!=null) {sql1.append("NumberPolicy,") ; sql2.append("'").append(wqr.get8()).append("',") ;}
						if (wqr.get9()!=null) {sql1.append("Smo,") ; sql2.append("'").append(wqr.get9()).append("',") ;}
						if (wqr.get10()!=null) {sql1.append("SmoOgrn,") ; sql2.append("'").append(wqr.get10()).append("',") ;}
						if (wqr.get11()!=null) {sql1.append("SmoOkato,") ; sql2.append("'").append(wqr.get11()).append("',") ;}
						if (wqr.get12()!=null) {sql1.append("SmoName,") ; sql2.append("'").append(wqr.get12()).append("',") ;}
						if (wqr.get13()!=null) {sql1.append("Lastname,") ; sql2.append("'").append(wqr.get13()).append("',") ;}
						sql1.append("Firstname,") ; sql2.append("'").append(wqr.get14()!=null?wqr.get14():"").append("',") ;
						sql1.append("Middlename,") ; sql2.append("'").append(wqr.get15()!=null?wqr.get15():"").append("',") ;
						if (wqr.get16()!=null) {
							String sex = ""+wqr.get16() ;
							if (sex.equals("М")) {sex="1";} else if (sex.equals("Ж")) {sex="2" ;}
							List<Object> l = theManager.createNativeQuery("select id from VocSex where omcCode='"+sex+"'").setMaxResults(1).getResultList() ;
							if (!l.isEmpty()) {sql1.append("sex_id,") ; sql2.append("'").append(l.get(0)).append("',") ;}
						}
						if (wqr.get17()!=null) {sql1.append("Birthday,") ; sql2.append("to_date('").append(wqr.get17()).append("','yyyy-mm-dd'),") ;}
						if (wqr.get18()!=null) {sql1.append("Phone,") ; sql2.append("'").append(wqr.get18()).append("',") ;}
						if (wqr.get19()!=null) {sql1.append("Diagnosis,") ; sql2.append("'").append(wqr.get19()).append("',") ;}
						if (wqr.get20()!=null) {sql1.append("Profile,") ; sql2.append("'").append(wqr.get20()).append("',") ;}
						if (wqr.get21()!=null) {sql1.append("Snils,") ; sql2.append("'").append(wqr.get21()).append("',") ;}
						if (wqr.get22()!=null) {sql1.append("PreHospDate,") ; sql2.append("to_date('").append(wqr.get22()).append("','yyyy-mm-dd'),") ;}
						if (wqr.get23()!=null) {sql1.append("HospDate,") ; sql2.append("to_date('").append(wqr.get23()).append("','yyyy-mm-dd'),") ;}
						if (wqr.get24()!=null) {sql1.append("StatCard,") ; sql2.append("'").append(wqr.get24()).append("',") ;}
						if (wqr.get25()!=null) {sql1.append("HospTime,") ; sql2.append("'").append(wqr.get25()).append("',") ;}
						if (wqr.get26()!=null) {sql1.append("HospDischargeDate,") ; sql2.append("to_date('").append(wqr.get26()).append("','yyyy-mm-dd'),") ;}
						if (wqr.get27()!=null) {sql1.append("ForChild,") ; sql2.append("'").append(wqr.get27()).append("',") ;}
						if (wqr.get28()!=null) {sql1.append("BedSubType,") ; sql2.append("'").append(wqr.get28()).append("',") ;}
						sql1.append("Voc_time,") ; sql2.append("'0',") ;
						if (aFileType.equals("N1")) {
							sql1.append("IsTable1") ; sql2.append("'1'") ;
						} else if (aFileType.equals("N2")) {
							sql1.append("IsTable2,") ; sql2.append("'1',") ;
							sql1.append("deniedhospital,") ; sql2.append("null,") ;
							sql1.append("IsTable4,") ; sql2.append("null,") ;
							sql1.append("IsTable5") ; sql2.append("null") ;
						} else if (aFileType.equals("N3")) {
							sql1.append("IsTable3") ; sql2.append("'1'") ;
						} else if (aFileType.equals("N4")) {
							sql1.append("IsTable4") ; sql2.append("'1'") ;
						} else if (aFileType.equals("N5")) {
							sql1.append("IsTable5") ; sql2.append("'1'") ;
						}
						sql1.append(") values (").append(sql2).append(")") ;
						//update
					} else {
						sql1.append("update HospitalDataFond set ") ;
						if (wqr.get2()!=null) {sql1.append("directDate").append("=").append("to_date('").append(wqr.get2()).append("','yyyy-mm-dd')").append(",") ;}
						if (wqr.get3()!=null) {sql1.append("formHelp") .append("=").append("'").append(wqr.get3()).append("'").append(",") ;}
						if (wqr.get4()!=null) {sql1.append("OrderLpuCode") .append("=").append("'").append(wqr.get4()).append("'").append(",") ;}
						if (wqr.get5()!=null) {sql1.append("DirectLpuCode") .append("=").append("'").append(wqr.get5()).append("'").append(",") ;}
						if (wqr.get6()!=null) {sql1.append("TypePolicy") .append("=").append("'").append(wqr.get6()).append("'").append(",") ;}
						if (wqr.get7()!=null) {sql1.append("SeriesPolicy") .append("=").append("'").append(wqr.get7()).append("'").append(",") ;}
						if (wqr.get8()!=null) {sql1.append("NumberPolicy") .append("=").append("'").append(wqr.get8()).append("'").append(",") ;}
						if (wqr.get9()!=null) {sql1.append("Smo") .append("=").append("'").append(wqr.get9()).append("'").append(",") ;}
						if (wqr.get10()!=null) {sql1.append("SmoOgrn") .append("=").append("'").append(wqr.get10()).append("'").append(",") ;}
						if (wqr.get11()!=null) {sql1.append("SmoOkato") .append("=").append("'").append(wqr.get11()).append("'").append(",") ;}
						if (wqr.get12()!=null) {sql1.append("SmoName") .append("=").append("'").append(wqr.get12()).append("'").append(",") ;}
						if (wqr.get13()!=null) {sql1.append("Lastname") .append("=").append("'").append(wqr.get13()).append("'").append(",") ;}
						if (wqr.get14()!=null) {sql1.append("Firstname") .append("=").append("'").append(wqr.get14()).append("'").append(",") ;}
						if (wqr.get15()!=null) {sql1.append("Middlename") .append("=").append("'").append(wqr.get15()).append("'").append(",") ;}
						if (wqr.get16()!=null) {
							String sex = ""+wqr.get16() ;
							if (sex.equals("М")) {sex="1";} else if (sex.equals("Ж")) {sex="2" ;}
							List<Object> l = theManager.createNativeQuery("select id from VocSex where omcCode='"+sex+"'").setMaxResults(1).getResultList() ;
							if (!l.isEmpty()) {sql1.append("sex_id") .append("=").append("'").append(l.get(0)).append("'").append(",") ;}
						}
						if (wqr.get17()!=null) {sql1.append("Birthday").append("=").append("to_date('").append(wqr.get17()).append("','yyyy-mm-dd')").append(",") ;}
						if (wqr.get18()!=null) {sql1.append("Phone").append("=").append("'").append(wqr.get18()).append("'").append(",") ;}
						if (wqr.get19()!=null) {sql1.append("Diagnosis").append("=").append("'").append(wqr.get19()).append("'").append(",") ;}
						if (wqr.get20()!=null) {sql1.append("Profile").append("=").append("'").append(wqr.get20()).append("'").append(",") ;}
						if (wqr.get21()!=null) {sql1.append("Snils").append("=").append("'").append(wqr.get21()).append("'").append(",") ;}
						if (wqr.get22()!=null) {sql1.append("PreHospDate").append("=").append("to_date('").append(wqr.get22()).append("','yyyy-mm-dd')").append(",") ;}
						if (wqr.get23()!=null) {sql1.append("HospDate").append("=").append("to_date('").append(wqr.get23()).append("','yyyy-mm-dd')").append(",") ;}
						if (wqr.get24()!=null) {sql1.append("StatCard").append("=").append("'").append(wqr.get24()).append("'").append(",") ;}
						if (wqr.get25()!=null) {sql1.append("HospTime").append("=").append("cast('").append(wqr.get25()).append("' as time)").append(",") ;}
						if (wqr.get26()!=null) {sql1.append("HospDischargeDate").append("=").append("to_date('").append(wqr.get26()).append("','yyyy-mm-dd')").append(",") ;}
						if (aFileType.equals("N1")) {
							sql1.append("IsTable1").append("=").append("'1'") ;
						} else if (aFileType.equals("N2")) {
							sql1.append("IsTable2").append("=").append("'1',") ;
							sql1.append("deniedhospital").append("=").append("null,") ;
							sql1.append("IsTable4").append("=").append("null,") ;
							sql1.append("IsTable5").append("=").append("null") ;
						} else if (aFileType.equals("N3")) {
							sql1.append("IsTable3").append("=").append("'1'") ;
						} else if (aFileType.equals("N4")) {
							sql1.append("IsTable4").append("=").append("'1'") ;
						} else if (aFileType.equals("N5")) {
							sql1.append("IsTable5").append("=").append("'1'") ;
						}
						sql1.append(" where id=").append(id) ;
					}
					//try {
					theManager.createNativeQuery(sql1.toString()).executeUpdate() ;
					//}catch(Exception e) {
					//	e.printStackTrace() ;
					//	if (id==null) {
					//theManager.createNativeQuery("alter table hospitaldatafond alter column id set default nextval('hospitaldatafond_sequence')").executeUpdate() ;
					//theManager.createNativeQuery(sql1.toString()).executeUpdate() ;
					//	}
					//}
				}
				if(i%10==0) monitor.setText("Импортируется: "+i+" "+wqr.get1()+" "+wqr.get2()+"...");
				if(size>0&&i%size==0) monitor.advice(1);

				if (i % 300 == 0) {
					monitor.setText("Импортировано " + i);
				}
			}
			monitor.finish("");
		} catch (Exception e) {
			e.printStackTrace();
			if(monitor!=null) monitor.setText(e+"");
			throw new IllegalStateException(e) ;
		}
		return "" ;
	}
	public void addMonitor(long aMonitorId, int aInt) {
		IMonitor monitor = null;
		try {
			monitor = theMonitorService.getMonitor(aMonitorId) ;
			monitor.advice(aInt) ;
		}catch (Exception e) {

		}
	}
	public void finishMonitor(long aMonitorId) {
		IMonitor monitor = null;
		try {
			monitor = theMonitorService.getMonitor(aMonitorId) ;
			monitor.finish("") ;
		}catch (Exception e) {

		}
	}
	public void startMonitor(long aMonitorId) {
		IMonitor monitor = null;
		try {
			monitor = theMonitorService.startMonitor(aMonitorId, "Обработка данных", 100);
		}catch (Exception e) {

		}
	}
	public String importDataFondForDBF(long aMonitorId) {
		IMonitor monitor = null;
		try {
			monitor = theMonitorService.startMonitor(aMonitorId, "Обработка данных", 100);
			List<Object[]> lf = theManager.createNativeQuery("select id,fio from HospitalDataFond where fio is not null and lastname is null").setMaxResults(1).getResultList() ;

			for (Object[] obj:lf) {
				String[] fio = new StringBuilder().append(obj).toString().split(")")[1].split(" ") ;
				String birthday = new StringBuilder().append(obj).toString().split(")")[1].replaceAll(")", "").trim() ;
				theManager.createNativeQuery(new StringBuilder()
						.append("update hospitaldatafond set withoutHosp='1',lastname='").append(fio[0])
						.append("',firstname='").append(fio[1])
						.append("',middlename='").append(fio.length>2?fio[2]:"")
						.append("',birthday=to_date('").append(birthday).append("','dd.mm.yyyy')")
						.append(" where id=").append(obj[0])
						.toString()).executeUpdate() ;
			}
			monitor.advice(50) ;
			monitor.finish("") ;
		} catch (Exception e) {
			if(monitor!=null) monitor.setText(e+"");
			throw new IllegalStateException(e) ;
		}
		return "" ;
	}


	public void refreshCompTreatmentReportByPeriod(String aEntranceDate,String aDischargeDate,long aIdMonitor) {
		IMonitor monitor = null;
		java.util.Date dt = new java.util.Date() ;
		String curDate = DateFormat.formatToDate(dt) ;
		try {
			monitor = theMonitorService.startMonitor(aIdMonitor, "Обработка данных за период: "+aEntranceDate+" "+aDischargeDate, 100);

			StringBuilder sqlD = new StringBuilder() ;
			sqlD.append(" delete from CompulsoryTreatmentAggregate where entrancehospdate <= (to_date('").append(aDischargeDate).append("','dd.mm.yyyy')-1)") ;
			sqlD.append(" and (dischargehospdate >= (to_date('").append(aEntranceDate).append("','dd.mm.yyyy')-1) or dischargehospdate is null)") ;
			theManager.createNativeQuery(sqlD.toString()).executeUpdate() ;
			StringBuilder sql = new StringBuilder() ;
			sql.append("select ct.ordernumber,ct.carecard_id,pcc.patient_id from CompulsoryTreatment ct ");
			sql.append(" left join PsychiatricCareCard pcc on pcc.id=ct.careCard_id ");
			sql.append(" where coalesce(ct.registrationdate,ct.DecisionDate)<=to_date('").append(aDischargeDate).append("','dd.mm.yyyy') ");
			sql.append(" and coalesce(ct.RegistrationReplaceDate,ct.Datereplace,to_date('").append(aEntranceDate).append("','dd.mm.yyyy'))>=to_date('").append(aEntranceDate).append("','dd.mm.yyyy') and ct.kind_id in (2,3) ");
			sql.append(" group by ct.ordernumber,ct.carecard_id,pcc.patient_id") ;

			monitor.advice(20) ;

			List<Object[]> list = theManager.createNativeQuery(sql.toString()).getResultList() ;
			int size = list.size()/80 ;

			for (int ii=0;ii<list.size();ii++) {
				Object[] obj = list.get(ii) ;
				if (monitor.isCancelled()) {
					throw new IllegalMonitorStateException("Прервано пользователем");
				}
				StringBuilder sql1 = new StringBuilder() ;
				sql1.append("select to_char(ct1.registrationdate,'dd.mm.yyyy') as r0egistrationdate,to_char(ct1.registrationreplacedate,'dd.mm.yyyy') as r1egistrationreplacedate");
				sql1.append(",to_char(ct1.decisiondate,'dd.mm.yyyy') as d2ecisiondate,to_char(ct1.datereplace,'dd.mm.yyyy') as d3atereplace ") ;
				sql1.append(" ,(select count(*) from CompulsoryTreatment ct2 where ct2.careCard_id='").append(obj[1]).append("' and ct2.orderNumber='").append(obj[0]).append("' and ct2.kind_id in (2,3) and ct1.decisiondate=ct2.datereplace) as p4revCT") ;
				//sql1.append(" ,(select count(*) from CompulsoryTreatment ct2 where ct2.careCard_id='").append(obj[1]).append("' and ct2.orderNumber='").append(obj[0]).append("' and ct2.kind_id in (2,3) and ct2.decisiondate=ct1.datereplace) as nextCT") ;
				sql1.append(" ,(select list(''||mc.id) from medcase mc where mc.patient_id=pcc.patient_id and upper(mc.dtype)='HOSPITALMEDCASE' and case when (mc.datestart <= coalesce(ct1.datereplace,current_date)") ;
				sql1.append(" and coalesce(mc.datefinish,current_date) >=ct1.decisiondate  ) then 1 else 0 end = 1) as s5ls") ;
				sql1.append(" ,vs.omcCode as f6sex") ;
				sql1.append(" from CompulsoryTreatment ct1 ") ;
				sql1.append(" left join PsychiatricCareCard pcc on pcc.id=ct1.careCard_id") ;
				sql1.append(" left join Patient pat on pat.id=pcc.patient_id") ;
				sql1.append(" left join VocSex vs on pat.sex_id=vs.id") ;
				sql1.append(" where ct1.careCard_id='").append(obj[1]).append("' and ct1.orderNumber='").append(obj[0]).append("' and ct1.kind_id in (2,3)") ;
				sql1.append(" order by ct1.decisiondate") ;
				List<Object[]> l1 = theManager.createNativeQuery(sql1.toString()).getResultList() ;
				List<Object[]> l2 = new ArrayList<>() ;

				for (Object[] o1:l1) {
					theManager.createNativeQuery("delete from CompulsoryTreatmentAggregate where sls in ("+o1[5]+")").executeUpdate() ;
					boolean prevCT = (o1[4]!=null && Integer.parseInt(""+o1[4])==1) ;
					//boolean nextCT = (o1[5]!=null && Integer.valueOf(""+o1[5]).intValue()==1)?true:false ;
					if (!prevCT) {
						l2.add(o1) ;
					} else {

						if (!l2.isEmpty()) {
							l2.get(l2.size()-1)[1]=o1[1] ;
							l2.get(l2.size()-1)[3]=o1[3] ;
						} else {
							l2.add(o1);
						}
					}

				}
				long hospInd = 0;
				for (Object[] o2:l2) {
					hospInd++ ;
					StringBuilder sql3 = new StringBuilder() ;
					sql3.append("select mc.parent_id as s0ls,mc.id as s1lo,case when mc.datestart>to_date('").append(o2[2]==null?curDate:o2[2]).append("','dd.mm.yyyy') then '1' else null end as s2rdate");
					sql3.append(", to_char(mc.datestart,'dd.mm.yyyy') as d3atestart,to_char(mc.transferdate,'dd.mm.yyyy') as t4ransferdate,to_char(mc.datefinish,'dd.mm.yyyy') as d5atefinish,mc.department_id as d6epartment") ;
					sql3.append(",case when ('").append(o2[3]==null?"":o2[3]).append("'='' or '").append(o2[3]==null?"":o2[3]).append("'!='' and coalesce(mc.transferdate,mc.datefinish) is not null and coalesce(mc.transferdate,mc.datefinish)<to_date('").append(o2[3]==null?curDate:o2[3]).append("','dd.mm.yyyy')) then to_char(coalesce(mc.transferdate,mc.datefinish),'dd.mm.yyyy') else '").append(o2[3]==null?"":o2[3]).append("' end  as s7rdate");
					sql3.append(" ,list(distinct case when vdrtD.code='4' and vpdD.code='1' then mkbD.code else null end) as f8depDiag") ;
					sql3.append(" ,list(distinct case when vdrt.code='2' then mkb.code else null end) as f9orderDiag") ;
					sql3.append(" ,list(distinct case when vdrt.code='3' and vpd.code='1' then mkb.code else null end) as f10dischargeDiag") ;
					sql3.append(" ,case when vhr.code='11' then cast('1' as int) else null end as f11isDeath") ;
					sql3.append(" , case when sls.dateFinish is not null then cast(to_char(sls.dateFinish,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int) +(case when (cast(to_char(sls.dateFinish, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int) +(case when (cast(to_char(sls.dateFinish,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) else null end as f12ageDischarge") ;
					sql3.append(" , cast(to_char(sls.dateStart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int) +(case when (cast(to_char(sls.dateStart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int) +(case when (cast(to_char(sls.dateStart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) as f13ageEntranceSls") ;
					sql3.append(" , cast(to_char(mc.dateStart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int) +(case when (cast(to_char(mc.dateStart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int) +(case when (cast(to_char(mc.dateStart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) as f14ageEntranceSlo") ;
					sql3.append(" , case when coalesce(mc.transferDate,mc.dateFinish) is not null then cast(to_char(coalesce(mc.transferDate,mc.dateFinish),'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int) +(case when (cast(to_char(coalesce(mc.transferDate,mc.dateFinish), 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int) +(case when (cast(to_char(coalesce(mc.transferDate,mc.dateFinish),'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) else null end as f15ageDischarge") ;
					sql3.append(" , pat.birthday as f16birthday") ;
					sql3.append(" from medcase mc") ;
					sql3.append(" left join medcase sls on mc.parent_id=sls.id") ;
					sql3.append(" left join patient pat on mc.patient_id=sls.patient_id") ;
					sql3.append(" left join bedfund bf on bf.id=mc.bedfund_id") ;
					sql3.append(" left join vocbedsubtype vbst on bf.bedsubtype_id=vbst.id") ;
					sql3.append(" left join mislpu ml on ml.id=mc.department_id") ;
					sql3.append(" left join diagnosis diag on sls.id=diag.medcase_id") ;
					sql3.append(" left join vocidc10 mkb on mkb.id=diag.idc10_id") ;
					sql3.append(" left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id") ;
					sql3.append(" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id") ;
					sql3.append(" left join diagnosis diagD on mc.id=diagD.medcase_id") ;
					sql3.append(" left join vocidc10 mkbD on mkbD.id=diagD.idc10_id") ;
					sql3.append(" left join VocDiagnosisRegistrationType vdrtD on vdrtD.id=diagD.registrationType_id") ;
					sql3.append(" left join VocPriorityDiagnosis vpdD on vpdD.id=diagD.priority_id") ;
					sql3.append(" left join VocHospitalizationResult  vhr on vhr.id=sls.result_id") ;
					sql3.append(" left join VocHospitalization  vh on vh.id=sls.hospitalization_id") ;
					sql3.append(" left join VocHospType vht on vht.id=sls.targetHospType_id") ;
					sql3.append(" left join VocHospType vhtHosp on vhtHosp.id=sls.hospType_id") ;

					sql3.append(" where mc.patient_id='").append(obj[2]).append("' and upper(mc.dtype)='DEPARTMENTMEDCASE' and case when coalesce(mc.transferdate,mc.datefinish,current_date) >= to_date('").append(o2[0]==null?curDate:o2[0]).append("','dd.mm.yyyy') and mc.datestart<=to_date('").append(o2[3]==null?curDate:o2[3]).append("','dd.mm.yyyy') ") ;
					sql3.append(" then 1 else 0 end = 1") ;
					sql3.append(" group by mc.datestart,mc.datefinish,mc.transferdate") ;
					sql3.append(" ,sls.id,mc.id") ;
					sql3.append(" ,sls.admissionInHospital_id,vh.code") ;
					sql3.append(" ,sls.admissionOrder_id,vhr.code") ;
					sql3.append(" ,mc.department_id") ;
					sql3.append(" ,sls.serviceStream_id,bf.bedType_id,bf.bedSubType_id") ;
					sql3.append(" ,mc.entranceTime, mc.dischargeTime,sls.emergency,vht.code,mc.transferTime,vhtHosp.id,vbst.code,sls.datefinish,sls.datestart,mc.parent_id,pat.birthday ") ;

					sql3.append(" order by mc.datestart") ;
					sql3.append("");
					List<Object[]> l3=theManager.createNativeQuery(sql3.toString()).getResultList() ;
					if (l3.isEmpty()) {
						CompulsoryTreatmentAggregate ahr = new CompulsoryTreatmentAggregate() ;
						ahr.setOrderCompTr(ConvertSql.parseString(obj[0])) ;
						ahr.setPatient(ConvertSql.parseLong(obj[2])) ;
						ahr.setIsDeath(false) ;
						ahr.setEntranceHospDate(ConvertSql.parseDate(o2[3]==null?curDate:o2[3])) ;
						ahr.setNumberHosp(hospInd) ;
						theManager.persist(ahr);
					} else {
						Date begDate = null ;
						Date endDate = null ;
						Object[] oF = l3.get(0) ;
						Object[] oL = l3.get(l3.size()-1) ;

						if (oF[2]==null) {begDate=ConvertSql.parseDate(o2[2]) ;} else {begDate=ConvertSql.parseDate(oF[3]) ;}


						if (oL[7]!=null) {
							endDate=ConvertSql.parseDate(oL[7]) ;
						}

						for (int ind =0;ind<l3.size();ind++) {

							Object[]o3 = l3.get(ind) ;
							Date begDateDep = null ;
							Date endDateDep = null ;

							if (o3[3]!=null&&o3[2]!=null) {
								begDateDep=ConvertSql.parseDate(o3[3]) ;
							} else {
								begDateDep=ConvertSql.parseDate(o2[2]) ;
							}

							if (o3[7]!=null) {
								endDateDep=ConvertSql.parseDate(o3[7]) ;
							}

							CompulsoryTreatmentAggregate ahr = new CompulsoryTreatmentAggregate() ;
							ahr.setSls(ConvertSql.parseLong(o3[0])) ;
							ahr.setSlo(ConvertSql.parseLong(o3[1])) ;
							ahr.setSexCode(ConvertSql.parseString(o2[6])) ;
							ahr.setOrderCompTr(ConvertSql.parseString(obj[0])) ;
							ahr.setPatient(ConvertSql.parseLong(obj[2])) ;
							ahr.setDepartment(ConvertSql.parseLong(o3[6])) ;
							ahr.setEntranceHospDate(ConvertSql.parseDate(begDate));
							ahr.setEntranceDepDate(ConvertSql.parseDate(begDateDep));
							ahr.setDischargeHospDate(ConvertSql.parseDate(endDate)) ;
							ahr.setDischargeDepDate(ConvertSql.parseDate(endDateDep)) ;
							ahr.setIsDeath(o3[11]==null?false:ConvertSql.parseBoolean(o3[11])) ;
							ahr.setNumberHosp(hospInd) ;
							ahr.setIdcDepartmentCode(ConvertSql.parseString(o3[8])) ;
							ahr.setIdcDischarge(ConvertSql.parseString(o3[10])) ;
							ahr.setIdcEntranceCode(ConvertSql.parseString(o3[9])) ;
							//ahr.setIdcTransferCode(ConvertSql.parseString(obj[34])) ;
							ahr.setAgeDischargeSls(ConvertSql.parseLong(o3[12]));
							ahr.setAgeEntranceSlo(ConvertSql.parseLong(o3[13]));
							ahr.setAgeEntranceSls(ConvertSql.parseLong(o3[14]));
							ahr.setAgeDischargeSlo(ConvertSql.parseLong(o3[15]));
							ahr.setBirthday(ConvertSql.parseDate(o3[16]));
							if (ind>0) {
								ahr.setIdcTransferCode(ConvertSql.parseString(l3.get(ind-1)[8], true));
								ahr.setTransferDepartmentFrom(ConvertSql.parseLong(l3.get(ind-1)[6]));
							}
							if (ind<l3.size()-1) ahr.setTransferDepartmentIn(ConvertSql.parseLong(l3.get(ind+1)[6]));
							theManager.persist(ahr);
						}
    	/*		//ahr.setAgeDischargeSlo(ConvertSql.parseLong(0));
    			ahr.setAgeDischargeSls(ConvertSql.parseLong(obj[35]));
    			ahr.setAgeEntranceSlo(ConvertSql.parseLong(obj[37]));
    			ahr.setAgeEntranceSls(ConvertSql.parseLong(obj[36]));
    			ahr.setCntDaysSls(ConvertSql.parseLong(0)) ;
    			ahr.setDepartment(ConvertSql.parseLong(obj[11])) ;
    			ahr.setDischargeHospDate(ConvertSql.parseDate(obj[5])) ;
    			ahr.setEntranceHospDate(ConvertSql.parseDate(obj[4])) ;
    			ahr.setEntranceDate7(ConvertSql.parseDate(obj[4],ConvertSql.parseBoolean(obj[23])?-1:0)) ;
    			ahr.setEntranceDate9(ConvertSql.parseDate(obj[4],ConvertSql.parseBoolean(obj[24])?-1:0)) ;
    			ahr.setIdcDepartmentCode(ConvertSql.parseString(obj[14])) ;
    			ahr.setIdcDischarge(ConvertSql.parseString(obj[16])) ;
    			ahr.setIdcTransferCode(ConvertSql.parseString(obj[34])) ;
    			ahr.setIsDeath(ConvertSql.parseBoolean(obj[10])) ;
    			ahr.setIsFirstCurrentYear(ConvertSql.parseBoolean(obj[8])) ;
    			ahr.setIsFirstLife(ConvertSql.parseBoolean(obj[7])) ;
    			ahr.setIsIncompetent(ConvertSql.parseBoolean(obj[9])) ;
    			ahr.setIsVillage(ConvertSql.parseBoolean(obj[8])) ;
    			ahr.setPatient(ConvertSql.parseLong(obj[0])) ;
    			ahr.setSexCode(ConvertSql.parseString(obj[3])) ;
    			ahr.setSlo(ConvertSql.parseLong(obj[2])) ;
    			ahr.setSls(ConvertSql.parseLong(obj[1])) ;
    			ahr.setTransferDepartmentFrom(ConvertSql.parseLong(obj[13])) ;
    			ahr.setTransferDepartmentIn(ConvertSql.parseLong(obj[12])) ;
    			ahr.setTransferLpuCode(ConvertSql.parseString(obj[27])) ;
    			ahr.setSex(ConvertSql.parseLong(obj[29])) ;
    			ahr.setAddBedDays(ConvertSql.parseLong(obj[33])) ;
    			ahr.setBirthday(ConvertSql.parseDate(obj[38]));*/
					}
				}
				if(ii%10==0) monitor.setText("Импортируется: "+ConvertSql.parseLong(obj[0])+" "+ConvertSql.parseLong(obj[2])+"...");
				if(size>0&&ii%size==0) monitor.advice(1);

				if (ii % 80 == 0) {
					//              theUserTransaction.commit();
					//              theUserTransaction.begin();
					monitor.setText("Импортировано " + ii);
				}
			}
			monitor.finish("");
		} catch (Exception e) {
			e.printStackTrace();
			if(monitor!=null) monitor.setText(e+"");
			throw new IllegalStateException(e) ;
		}
	}
	public void refreshReportByPeriod(String aEntranceDate,String aDischargeDate,long aIdMonitor) {
		LOG.error("ВНИМАНИЕ!! Выполняется неизвестный код. никто не знает что он делает..");
		IMonitor monitor = null;
		try {
			monitor = theMonitorService.startMonitor(aIdMonitor, "Обработка данных за период: "+aEntranceDate+" "+aDischargeDate, 100);

			StringBuilder sqlD = new StringBuilder() ;
			sqlD.append(" delete from AggregateHospitalReport where entrancedate24 <= (to_date('").append(aDischargeDate).append("','dd.mm.yyyy')-1)") ;
			sqlD.append(" and (dischargedate24 >= (to_date('").append(aEntranceDate).append("','dd.mm.yyyy')-1) or dischargedate24 is null)") ;
			theManager.createNativeQuery(sqlD.toString()).executeUpdate() ;
			StringBuilder sql = new StringBuilder() ;
			sql.append(" select pat.id as f0patid") ;
			sql.append(" ,sls.id as f1slsid") ;
			sql.append(" ,slo.id as f2sloid") ;
			sql.append(" ,vs.omccode as f3assex") ;
			sql.append(" ,slo.datestart as f4entrancedate") ;
			sql.append(" ,coalesce(slo.datefinish,slo.transferdate) as f5dischargedate") ;
			sql.append(" ,case when a.addressisvillage='1' then cast('1' as int) else null end as f6isvillage") ;
			sql.append(" ,case when sls.admissionInHospital_id='1' then cast('1' as int) else null end as f7isFirstLife") ;
			sql.append(" ,case when (vh.code='1' or vh.code='2' or sls.admissionInHospital_id='1') then cast('1' as int) else null end as f8isFirstCurrentYear") ;
			sql.append(" ,case when sls.admissionOrder_id in (2,4,5,6,7,8,9) then cast('1' as int) else null end as f9isIncompetent") ;
			sql.append(" ,case when vhr.code='11' then cast('1' as int) else null end as f10isDeath") ;
			sql.append(" ,slo.department_id as f11slodepartment") ;
			sql.append(" ,nextslo.department_id as f12nextslodepartment") ;
			sql.append(" ,prevslo.department_id as f13prevslodepartment") ;
			sql.append(" ,list(distinct case when vdrtD.code='4' and vpdD.code='1' then mkbD.code else null end) as f14depDiag") ;
			sql.append(" ,list(distinct case when vdrt.code='2' then mkb.code else null end) as f15orderDiag") ;
			sql.append(" ,list(distinct case when vdrt.code='3' and vpd.code='1' then mkb.code else null end) as f16dischargeDiag") ;
			sql.append(" ,case when count(ahr.id)>0 then cast('1' as int) else null end as f17cntAggregate") ;
			sql.append(" ,bf.bedType_id as f18bedType") ;
			sql.append(" ,bf.bedSubType_id as f19bedSubType") ;
			sql.append(" ,sls.serviceStream_id as f20serviceStream") ;
			sql.append(" , case when slo.entranceTime<cast('07:00' as time) then cast('1' as int) else null end as f21entranceTime7") ;
			sql.append(" , case when slo.entranceTime<cast('09:00' as time) then cast('1' as int) else null end as f22entranceTime9") ;
			sql.append(" , case when coalesce(slo.dischargeTime,slo.transferTime)<cast('07:00' as time) then cast('1' as int) else null end as f23entranceTime7") ;
			sql.append(" , case when coalesce(slo.dischargeTime,slo.transferTime)<cast('09:00' as time) then cast('1' as int) else null end as f24entranceTime9") ;
			sql.append(" , case when sls.emergency='1' then cast('1' as int) else null end as f25emergency") ;
			sql.append(" , cast('0' as int) as f26operation") ;
			sql.append(" , vht.code as f27transferLpu") ;
			sql.append(" , vhtHosp.id as f28hospTypeId") ;
			sql.append(" , vs.id as f29sexId") ;
			sql.append(" , case when firstSlo.entranceTime<cast('07:00' as time) then cast('1' as int) else null end as f30entranceTime7") ;
			sql.append(" , case when firstSlo.entranceTime<cast('09:00' as time) then cast('1' as int) else null end as f31entranceTime9") ;
			sql.append(" , firstSlo.datestart as f32entrancedate") ;
			sql.append(" , case when vbst.code='1' then '0' else '1' end  as f33isdayhosp") ;
			sql.append(" ,list(distinct case when prevVdrtD.code='4' and prevVpdD.code='1' then prevMkbD.code else null end) as f34prevdepDiag") ;
			sql.append(" , case when sls.dateFinish is not null then cast(to_char(sls.dateFinish,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int) +(case when (cast(to_char(sls.dateFinish, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int) +(case when (cast(to_char(sls.dateFinish,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) else null end as f35ageDischarge") ;
			sql.append(" , cast(to_char(sls.dateStart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int) +(case when (cast(to_char(sls.dateStart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int) +(case when (cast(to_char(sls.dateStart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) as f36ageEntranceSls") ;
			sql.append(" , cast(to_char(slo.dateStart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int) +(case when (cast(to_char(slo.dateStart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int) +(case when (cast(to_char(slo.dateStart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) as f37ageEntranceSlo") ;
			sql.append(" , pat.birthday as f38birthday") ;
			sql.append(" from medcase sls") ;
			sql.append(" left join medcase slo on sls.id=slo.parent_id") ;
			sql.append(" left join medcase prevSlo on prevSlo.id=slo.prevMedCase_id") ;
			sql.append(" left join diagnosis prevDiagD on prevSlo.id=prevDiagD.medcase_id") ;
			sql.append(" left join vocidc10 prevMkbD on prevMkbD.id=prevDiagD.idc10_id") ;
			sql.append(" left join VocDiagnosisRegistrationType prevVdrtD on prevVdrtD.id=prevDiagD.registrationType_id") ;
			sql.append(" left join VocPriorityDiagnosis prevVpdD on prevVpdD.id=prevDiagD.priority_id") ;

			sql.append(" left join AggregateHospitalReport ahr on ahr.slo=slo.id") ;
			sql.append(" left join medcase nextSlo on nextSlo.prevmedcase_id=slo.id") ;
			sql.append(" left join medcase firstSlo on sls.id=firstSlo.parent_id and firstSlo.prevmedcase_id is null") ;
			sql.append(" left join patient pat on pat.id=sls.patient_id") ;
			sql.append(" left join address2 a on a.addressid=pat.address_addressid") ;
			sql.append(" left join vocsex vs on vs.id=pat.sex_id") ;
			sql.append(" left join bedfund bf on bf.id=slo.bedfund_id") ;
			sql.append(" left join vocbedsubtype vbst on bf.bedsubtype_id=vbst.id") ;
			sql.append(" left join mislpu ml on ml.id=slo.department_id") ;
			sql.append(" left join mislpu mlN on mlN.id=nextSlo.department_id") ;
			sql.append(" left join mislpu mlP on mlP.id=prevSlo.department_id") ;
			sql.append(" left join diagnosis diag on sls.id=diag.medcase_id") ;
			sql.append(" left join vocidc10 mkb on mkb.id=diag.idc10_id") ;
			sql.append(" left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id") ;
			sql.append(" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id") ;
			sql.append(" left join diagnosis diagD on slo.id=diagD.medcase_id") ;
			sql.append(" left join vocidc10 mkbD on mkbD.id=diagD.idc10_id") ;
			sql.append(" left join VocDiagnosisRegistrationType vdrtD on vdrtD.id=diagD.registrationType_id") ;
			sql.append(" left join VocPriorityDiagnosis vpdD on vpdD.id=diagD.priority_id") ;
			sql.append(" left join VocHospitalizationResult  vhr on vhr.id=sls.result_id") ;
			sql.append(" left join VocHospitalization  vh on vh.id=sls.hospitalization_id") ;
			sql.append(" left join VocHospType vht on vht.id=sls.targetHospType_id") ;
			sql.append(" left join VocHospType vhtHosp on vhtHosp.id=sls.hospType_id") ;
			sql.append(" where sls.dtype='HospitalMedCase' and firstSlo.dtype='DepartmentMedCase'  and firstSlo.dtype='DepartmentMedCase' and sls.dateStart <= (to_date('").append(aDischargeDate).append("','dd.mm.yyyy')-1)") ;
			sql.append(" and (sls.dateFinish >= (to_date('").append(aEntranceDate).append("','dd.mm.yyyy')-1) or sls.dateFinish is null)") ;
			sql.append(" and sls.deniedHospitalizating_id is null") ;
			sql.append(" group by vs.omccode,vs.id,slo.datestart,slo.datefinish,slo.transferdate") ;
			sql.append(" ,pat.id,sls.id,slo.id,a.addressisvillage") ;
			sql.append(" ,sls.admissionInHospital_id,vh.code,firstSlo.datestart,firstSlo.entranceTime") ;
			sql.append(" ,sls.admissionOrder_id,vhr.code") ;
			sql.append(" ,slo.department_id,nextslo.department_id,prevslo.department_id") ;
			sql.append(" ,sls.serviceStream_id,bf.bedType_id,bf.bedSubType_id, pat.birthday") ;
			sql.append(" ,slo.entranceTime, slo.dischargeTime,sls.emergency,vht.code,slo.transferTime,vhtHosp.id,vbst.code,sls.datefinish,pat.birthday,sls.datestart ") ;
			sql.append(" order by sls.id") ;
			monitor.advice(20) ;

			List<Object[]> list = theManager.createNativeQuery(sql.toString()).getResultList() ;
			int size = list.size()/80 ;

			for (int i=0;i<list.size();i++) {
				Object[] obj = list.get(i) ;
				if (monitor.isCancelled()) {
					throw new IllegalMonitorStateException("Прервано пользователем");
				}
				AggregateHospitalReport ahr = new AggregateHospitalReport() ;
				//ahr.setAgeDischargeSlo(ConvertSql.parseLong(0));
				ahr.setAgeDischargeSls(ConvertSql.parseLong(obj[35]));
				ahr.setAgeEntranceSlo(ConvertSql.parseLong(obj[37]));
				ahr.setAgeEntranceSls(ConvertSql.parseLong(obj[36]));
				ahr.setBedSubType(ConvertSql.parseLong(obj[19])) ;
				ahr.setBedType(ConvertSql.parseLong(obj[18])) ;
				ahr.setCntDaysSls(ConvertSql.parseLong(0)) ;
				ahr.setDepartment(ConvertSql.parseLong(obj[11])) ;
				ahr.setDischargeDate24(ConvertSql.parseDate(obj[5])) ;
				ahr.setDischargeDate7(ConvertSql.parseDate(obj[5],ConvertSql.parseBoolean(obj[21])?-1:0)) ;
				ahr.setDischargeDate9(ConvertSql.parseDate(obj[5],ConvertSql.parseBoolean(obj[22])?-1:0)) ;
				ahr.setEntranceDate24(ConvertSql.parseDate(obj[4])) ;
				ahr.setEntranceDate7(ConvertSql.parseDate(obj[4],ConvertSql.parseBoolean(obj[23])?-1:0)) ;
				ahr.setEntranceDate9(ConvertSql.parseDate(obj[4],ConvertSql.parseBoolean(obj[24])?-1:0)) ;
				ahr.setEntranceHospDate24(ConvertSql.parseDate(obj[32])) ;
				ahr.setEntranceHospDate7(ConvertSql.parseDate(obj[32],ConvertSql.parseBoolean(obj[30])?-1:0)) ;
				ahr.setEntranceHospDate9(ConvertSql.parseDate(obj[32],ConvertSql.parseBoolean(obj[31])?-1:0)) ;
				ahr.setIdcDepartmentCode(ConvertSql.parseString(obj[14])) ;
				ahr.setIdcDischarge(ConvertSql.parseString(obj[16])) ;
				ahr.setIdcEntranceCode(ConvertSql.parseString(obj[15])) ;
				ahr.setIdcTransferCode(ConvertSql.parseString(obj[34])) ;
				ahr.setIsDeath(ConvertSql.parseBoolean(obj[10])) ;
				ahr.setIsEmergency(ConvertSql.parseBoolean(obj[25])) ;
				ahr.setIsFirstCurrentYear(ConvertSql.parseBoolean(obj[8])) ;
				ahr.setIsFirstLife(ConvertSql.parseBoolean(obj[7])) ;
				ahr.setIsIncompetent(ConvertSql.parseBoolean(obj[9])) ;
				ahr.setIsOperation(ConvertSql.parseBoolean(obj[26])) ;
				ahr.setIsVillage(ConvertSql.parseBoolean(obj[8])) ;
				ahr.setPatient(ConvertSql.parseLong(obj[0])) ;
				ahr.setServiceStream(ConvertSql.parseLong(obj[20])) ;
				ahr.setSexCode(ConvertSql.parseString(obj[3])) ;
				ahr.setSlo(ConvertSql.parseLong(obj[2])) ;
				ahr.setSls(ConvertSql.parseLong(obj[1])) ;
				ahr.setTransferDepartmentFrom(ConvertSql.parseLong(obj[13])) ;
				ahr.setTransferDepartmentIn(ConvertSql.parseLong(obj[12])) ;
				ahr.setTransferLpuCode(ConvertSql.parseString(obj[27])) ;
				ahr.setHospType(ConvertSql.parseLong(obj[28])) ;
				ahr.setSex(ConvertSql.parseLong(obj[29])) ;
				ahr.setAddBedDays(ConvertSql.parseLong(obj[33])) ;
				ahr.setBirthday(ConvertSql.parseDate(obj[38]));
				theManager.persist(ahr);
				if(i%10==0) monitor.setText(new StringBuilder().append("Импортируется: ").append(ConvertSql.parseLong(obj[0])).append(" ").append(ConvertSql.parseLong(obj[2])).append("...").toString());
				if(i%size==0) monitor.advice(1);

				if (i % 300 == 0) {
					//              theUserTransaction.commit();
					//              theUserTransaction.begin();
					monitor.setText("Импортировано " + i);
				}
			}
			monitor.finish("");
		} catch (Exception e) {
			if(monitor!=null) monitor.setText(e+"");
			throw new IllegalStateException(e) ;
		}
	}

	private String getTitleFile(String aReestr,String aLpu, String aPeriodByReestr,String aNPackage) {
		return "N"+aReestr+"M"+aLpu+"T30_"+aPeriodByReestr+XmlUtil.namePackage(aNPackage) ;
	}
	public WebQueryResult[] exportFondZip23(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu, boolean aSaveInFolder)
			throws ParserConfigurationException, TransformerException {
		String nPackage = EjbInjection.getInstance()
				.getLocalService(ISequenceService.class)
				.startUseNextValueNoCheck("PACKAGE_HOSP","number");
		WebQueryResult[] fileExpList = {exportN2(aDateFrom,aDateTo,aPeriodByReestr,aLpu,nPackage,aSaveInFolder)
				, exportN3(aDateFrom,aDateTo,aPeriodByReestr,aLpu,nPackage,aSaveInFolder)
				,new WebQueryResult()
		};

		//EjbEcomConfig config = EjbEcomConfig.getInstance() ;
	//	String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
	//	workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
		fileExpList[2].set1((""+fileExpList[0].get1()).substring(2).replaceAll("\\.xml", "")+".263") ;

		StringBuilder sb=new StringBuilder();
		sb.append("zip -r -9 ") ;
		for (int i=2;i>-1;i--){
			sb.append(fileExpList[i].get1()).append(" ");
			if (fileExpList[i].get2()!=null) sb.append(fileExpList[i].get2()).append(" ");
			if (fileExpList[i].get3()!=null) sb.append(fileExpList[i].get3()).append(" ");
		}
		try {
			//String[] arraCmd = {new StringBuilder().append("cd ").append(workDir).append("").toString(),sb.toString()} ;
			Runtime.getRuntime().exec(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileExpList ;
	}
	public String[] exportFondZip45(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu, boolean aSaveInFolder)
			throws ParserConfigurationException, TransformerException {
		String nPackage = EjbInjection.getInstance()
				.getLocalService(ISequenceService.class)
				.startUseNextValueNoCheck("PACKAGE_HOSP","number");
		String[] fileExpList = {exportN4(aDateFrom,aDateTo,aPeriodByReestr,aLpu,nPackage,aSaveInFolder)
				, exportN5(aDateFrom,aDateTo,aPeriodByReestr,aLpu,nPackage,aSaveInFolder)
				,""
		};

		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
		workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
		fileExpList[2]=fileExpList[0].substring(2).replaceAll("\\.xml", "")+".263" ;

		StringBuilder sb=new StringBuilder();
		sb.append("zip -r -j -9 ") ;
		for (int i=2;i>-1;i--){
			sb.append(workDir).append("/").append(fileExpList[i]).append(" ");
		}
		try {
			//String[] arraCmd = {sb.toString()} ;
			Runtime.getRuntime().exec(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileExpList ;
	}

	public WebQueryResult exportN0(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage
			, String aVidN, boolean aSaveInFolder) throws TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		if (aNPackage==null || aNPackage.equals("")) {aNPackage = EjbInjection.getInstance()
				.getLocalService(ISequenceService.class)
				.startUseNextValueNoCheck("PACKAGE_HOSP","number");
		}
		String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
		workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;

		String workAddDir =aSaveInFolder ? config.get("data.dir.order263.out", null) : null;


		String filename = getTitleFile("0",aLpu,aPeriodByReestr,aNPackage) ;
		WebQueryResult res = new WebQueryResult() ;
		XmlDocument xmlDoc = new XmlDocument() ;
		Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
		Element title = xmlDoc.newElement(root, "ZGLV", null);
		xmlDoc.newElement(title, "VERSION", "1.0");
		xmlDoc.newElement(title, "DATA", aDateFrom);
		xmlDoc.newElement(title, "FILENAME", filename);
		xmlDoc.newElement(title, "VID_N", aVidN);

		XmlUtil.saveXmlDocument(xmlDoc,new File(workDir+"/"+filename+".xml")) ;
		if (workAddDir!=null) XmlUtil.saveXmlDocument(xmlDoc,new File(workAddDir+"/"+filename+".xml")) ;
		res.set1(filename+".xml") ;
		if (workAddDir!=null) {
			chmodDir(workAddDir);
		}
		return res;
	}
	public WebQueryResult exportN1(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aIsPolyc, boolean aIsHospital, boolean aSaveInFolder)
			throws ParserConfigurationException, TransformerException {

		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		if (aNPackage==null || aNPackage.equals("")) {aNPackage = EjbInjection.getInstance()
				.getLocalService(ISequenceService.class)
				.startUseNextValueNoCheck("PACKAGE_HOSP","number");
		}
		String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
		workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;

		String workAddDir =aSaveInFolder?config.get("data.dir.order263.out", null):null;


		String filename = getTitleFile("1",aLpu,aPeriodByReestr,aNPackage) ;
		WebQueryResult res = new WebQueryResult() ;

		XmlDocument xmlDoc = new XmlDocument() ;
		XmlDocument xmlDocError = new XmlDocument() ;
		XmlDocument xmlDocExist = new XmlDocument() ;
		Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
		Element root_error = xmlDocError.newElement(xmlDocError.getDocument(), "ZL_LIST", null);
		Element root_exist = xmlDocExist.newElement(xmlDocExist.getDocument(), "ZL_LIST", null);
		StringBuilder sql = new StringBuilder() ;

		if (!aIsPolyc&&aIsHospital) {
			sql.append(" select to_char(mc.datefinish,'yyyy-MM-dd') as w0chbcreatedate");
		} else if (aIsPolyc&&!aIsHospital) {
			sql.append(" select to_char(wchb.createDate,'yyyy-MM-dd') as w0chbcreatedate");
		} else if (aIsPolyc&&aIsHospital) {
			sql.append(" select to_char(wchb.createDate,'yyyy-MM-dd') as w0chbcreatedate");
		}
		sql.append(" ,cast('1' as varchar(1)) as f1orPom");
		sql.append(" ,case when lpu.codef is null or lpu.codef='' then plpu.codef else lpu.codef end as l2puSent");
		sql.append(" ,case when olpu.codef is null or olpu.codef='' then oplpu.codef else olpu.codef end as l3puDirect");
		sql.append(" ,vmc.code as m4edpolicytype");
		sql.append(" ,mp.series as m5pseries");
		sql.append(" , mp.polnumber as p6olnumber");
		sql.append(" , case when oss.smocode is null or oss.smocode='' then ri.smocode else oss.smoCode end as o7ossmocode");
		sql.append(" , ri.ogrn as o8grnSmo");
		sql.append(" ,case when mp.dtype='MedPolicyOmc' then '12000' else okt.okato end as o9katoSmo");
		sql.append(" ,p.lastname as l10astname");
		sql.append(" ,p.firstname as f11irstname");
		sql.append(" ,p.middlename as m12iddlename");
		sql.append(" ,vs.omcCode as v13somccode");
		sql.append(" ,to_char(p.birthday,'yyyy-mm-dd') as b14irthday");
		sql.append(" ,wchb.phone as p15honepatient");
		sql.append(" ,mkb.code as m16kbcode");
		sql.append(" ,coalesce(vmhp.code,vbt.codeF) as v17btcodef");
		sql.append(" ,wp.snils as w18psnils");
		sql.append(" ,wchb.dateFrom as w19chbdatefrom");
		sql.append(", wchb.visit_id as v20isit");
		sql.append(", case when vbst.code='3' then '2' else vbst.code end as v21bstcode");
		sql.append(", cast(case when cast(to_char(current_date,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)>=18 then '0' else '1' end as varchar(1)) as f22det");
		sql.append(", wchb.internalcode as f23_internalDirectionNumber");
		sql.append(" from WorkCalendarHospitalBed wchb");
		sql.append(" left join VocBedType vbt on vbt.id=wchb.bedType_id");
		sql.append(" left join VocE2MedHelpProfile vmhp on vmhp.id=vbt.medHelpProfile_id");
		sql.append(" left join VocBedSubType vbst on vbst.id=wchb.bedSubType_id");
		sql.append(" left join Patient p on p.id=wchb.patient_id");
		sql.append(" left join VocSex vs on vs.id=p.sex_id");
		sql.append(" left join VocServiceStream vss on vss.id=wchb.serviceStream_id");
		sql.append(" left join MedCase mc on mc.id=wchb.visit_id");
		if (!aIsPolyc&&aIsHospital) {
			sql.append(" left join medcase_medpolicy mcp on mcp.medcase_id=mc.id ");
			sql.append(" left join medpolicy mp on mp.id=mcp.policies_id");
			sql.append(" left join WorkFunction wf on wf.id=wchb.workFunction_id");
		} else if (aIsPolyc) {
			sql.append(" left join WorkFunction wf on wf.id=mc.workFunctionExecute_id");
			sql.append(" left join medpolicy mp on mp.patient_id=wchb.patient_id and mp.actualdatefrom<=wchb.createDate and coalesce(mp.actualdateto,current_date)>=wchb.createDate");
		}/* else if (aIsPolyc&&aIsHospital) {
			sql.append(" left join WorkFunction wf on wf.id=mc.workFunctionExecute_id");
			sql.append(" left join medpolicy mp on mp.patient_id=wchb.patient_id and mp.actualdatefrom<=wchb.createDate and coalesce(mp.actualdateto,current_date)>=wchb.createDate");
		}*/

		sql.append(" left join VocIdc10 mkb on mkb.id=wchb.idc10_id");
		sql.append(" left join MisLpu ml on ml.id=wchb.department_id");
		sql.append(" left join Vocmedpolicyomc vmc on mp.type_id=vmc.id");
		sql.append(" left join Omc_kodter okt on okt.id=mp.insuranceCompanyArea_id");
		sql.append(" left join Omc_SprSmo oss on oss.id=mp.insuranceCompanyCode_id");
		sql.append(" left join reg_ic ri on ri.id=mp.company_id");

		sql.append(" left join Worker w on w.id=wf.worker_id");
		sql.append(" left join Patient wp on wp.id=w.person_id");
		sql.append(" left join mislpu lpu on lpu.id=w.lpu_id");
		sql.append(" left join mislpu plpu on plpu.id=lpu.parent_id");
		sql.append(" left join mislpu olpu on olpu.id=wchb.orderLpu_id");
		sql.append(" left join mislpu oplpu on oplpu.id=olpu.parent_id");
		sql.append(" where wchb.visit_id is not null");
		sql.append(" and wchb.createDate between to_date('").append(aDateFrom).append("','yyyy-MM-dd') and to_date('").append(aDateTo).append("','yyyy-MM-dd')");
		sql.append(" and vss.code in ('OBLIGATORYINSURANCE','OTHER') and wchb.visit_id is not null");
		sql.append(" and mp.dtype='MedPolicyOmc'"); //только местные полиса, иногородних не выгружаем
		if (aIsPolyc&&aIsHospital) {
		} else if (aIsPolyc&&!aIsHospital) {
			sql.append(" and upper(mc.dtype) = 'VISIT'") ;
		} else if (!aIsPolyc&&aIsHospital) {
			sql.append(" and upper(mc.dtype) = 'HOSPITALMEDCASE'") ;
		}
		sql.append(" order by p.lastname,p.firstname,p.middlename");
		List<Object[]> list = theManager.createNativeQuery(sql.toString())
				.setMaxResults(70000).getResultList() ;
		Element title = xmlDoc.newElement(root, "ZGLV", null);
		Element title_error = xmlDocError.newElement(root_error, "ZGLV", null);
		Element title_exist = xmlDocExist.newElement(root_exist, "ZGLV", null);
		xmlDoc.newElement(title, "VERSION", "1.0");
		xmlDoc.newElement(title, "DATA", aDateFrom);xmlDocExist.newElement(title_exist, "DATA", aDateFrom);xmlDocError.newElement(title_error, "DATA", aDateFrom);
		xmlDoc.newElement(title, "FILENAME", filename);
		ArrayList<WebQueryResult> i_exist=new ArrayList<>() ;
		List<WebQueryResult> i_error=new ArrayList<>() ;
		for (Object[] obj:list) {
			if (checkDirect(obj[10],obj[11],obj[12],obj[14],obj[19],obj[17]
					, obj[4], obj[6],obj[18],obj[16])) {
				if (!checkHospitalDataFond(obj[10],obj[11],obj[12],obj[14],obj[19],obj[17],obj[20])) {
					Element zap = xmlDoc.newElement(root, "NPR", null);
					recordN1(xmlDoc, zap, obj, false) ;

				} else {
					Element zapExist = xmlDocExist.newElement(root_exist, "NPR", null);
					i_exist.add(recordN1(xmlDocExist, zapExist, obj,true)) ;
				}
			} else {
				Element zapError = xmlDocError.newElement(root_error, "NPR", null);
				i_error.add(recordN1(xmlDocError, zapError, obj,true)) ;

			}
		}
		XmlUtil.saveXmlDocument(xmlDoc,new File(new StringBuilder().append(workDir).append("/").append(filename).append(".xml").toString())) ;
		if (workAddDir!=null) XmlUtil.saveXmlDocument(xmlDoc,new File(new StringBuilder().append(workAddDir).append("/").append(filename).append(".xml").toString())) ;
		if (!i_exist.isEmpty()) {
			res.set2(new StringBuilder().append("exist_").append(filename).append(".xml").toString()) ;
			res.set4(i_exist) ;
			XmlUtil.saveXmlDocument(xmlDocExist,new File(new StringBuilder().append(workDir).append("/exist_").append(filename).append(".xml").toString())) ;
			if (workAddDir!=null) XmlUtil.saveXmlDocument(xmlDocExist,new File(new StringBuilder().append(workAddDir).append("/").append("/exist_").append(filename).append(".xml").toString())) ;
		}
		if (!i_error.isEmpty()) {
			res.set3("error_"+filename+".xml") ;
			res.set5(i_error) ;
			XmlUtil.saveXmlDocument(xmlDocError,new File(new StringBuilder().append(workDir).append("/error_").append(filename).append(".xml").toString())) ;
			if (workAddDir!=null) XmlUtil.saveXmlDocument(xmlDocError,new File(new StringBuilder().append(workAddDir).append("/").append("/error_").append(filename).append(".xml").toString())) ;
		}
		res.set1(filename+".xml") ;
		if (workAddDir!=null) {
			chmodDir(workAddDir);
		}
		return res;
	}

	private WebQueryResult recordN3(XmlDocument xmlDoc, Element zap, Object[] obj, boolean aIsCreateWQR) {
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DCODE_MO",obj[16],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DLPU_1",null,false,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DATE_1",obj[0],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"TIME_1",XmlUtil.formatTime(obj[1]),true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"VPOLIS",obj[2],false,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SPOLIS",obj[3],false,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"NPOLIS",obj[4],false,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SMO",obj[5],false,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SMO_OGRN",obj[6],false,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SMO_OK",obj[7],false,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SMO_NAM",null,false,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"FAM",obj[8],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"IM",obj[9],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"OT",obj[10],false,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"W",obj[11],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DR",obj[12],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"USL_OK",obj[17],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DET",obj[18],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PROFIL",obj[13],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PODR",null,false,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"NHISTORY",obj[14],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DS1",obj[15],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"REFREASON",null,false,"") ;
		WebQueryResult res = null ;
		if (aIsCreateWQR) {
			res= new WebQueryResult() ;
			res.set1(obj[0]) ;res.set2(obj[1]) ;res.set3(obj[2]) ;res.set4(obj[3]) ;res.set5(obj[4]) ;
			res.set6(obj[5]) ;res.set7(obj[6]) ;res.set8(obj[7]) ;res.set9(obj[8]) ;res.set10(obj[9]) ;
			res.set11(obj[10]) ;res.set12(obj[11]) ;res.set13(obj[12]) ;res.set14(obj[13]) ;res.set15(obj[14]) ;
			res.set16(obj[15]) ;res.set17(obj[16]) ;res.set18(obj[17]) ;res.set19(obj[18]) ;
			//res.set20(obj[21]);res.set21(obj[22]);
		}
		return res ;
	}
	private WebQueryResult recordN2(XmlDocument xmlDoc, Element zap, Object[] obj, boolean aIsCreateWQR) {

		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"N_NPR",obj[18],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"D_NPR",obj[19],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"FOR_POM",obj[20],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DCODE_MO",obj[16],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DLPU_1",null,false,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"NCODE_MO",obj[17],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"NLPU_1",null,false,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DATE_1",obj[0],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"TIME_1",XmlUtil.formatTime(obj[1]),true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"VPOLIS",obj[2],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SPOLIS",obj[3],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"NPOLIS",obj[4],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"FAM",obj[8],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"IM",obj[9],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"OT",obj[10],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"W",obj[11],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DR",obj[12],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"USL_OK",obj[21],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DET",obj[22],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PROFIL",obj[13],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PODR",null,false,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"NHISTORY",obj[14],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DS1",obj[15],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"REFREASON",null,false,"") ;
		WebQueryResult res = null ;
		if (aIsCreateWQR) {
			res= new WebQueryResult() ;
			res.set1(obj[0]) ;res.set2(obj[1]) ;res.set3(obj[2]) ;res.set4(obj[3]) ;res.set5(obj[4]) ;
			res.set6(obj[5]) ;res.set7(obj[6]) ;res.set8(obj[7]) ;res.set9(obj[8]) ;res.set10(obj[9]) ;
			res.set11(obj[10]) ;res.set12(obj[11]) ;res.set13(obj[12]) ;res.set14(obj[13]) ;res.set15(obj[14]) ;
			res.set16(obj[15]) ;res.set17(obj[16]) ;res.set18(obj[17]) ;res.set19(obj[18]) ;
			res.set20(obj[21]);res.set21(obj[22]);
		}
		return res ;

	}
	private WebQueryResult recordN1(XmlDocument xmlDoc, Element zap, Object[] obj, boolean aIsCreateWQR) {

		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"N_NPR","",true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"N_NPR_LPU",obj[23],false,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"D_NPR",obj[0],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"FOR_POM",obj[1],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"NCODE_MO",obj[3],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"NLPU_1","",true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DCODE_MO",obj[2],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DLPU_1",null,false,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"VPOLIS",obj[4],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SPOLIS",obj[5],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"NPOLIS",obj[6],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SMO",obj[7],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SMO_OGRN",obj[8],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SMO_OK",obj[9],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SMO_NAM",null,true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"FAM",obj[10],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"IM",obj[11],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"OT",obj[12],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"W",obj[13],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DR",obj[14],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"CT",obj[15],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DS1",obj[16],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"USL_OK",obj[21],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DET",obj[22],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PROFIL",obj[17],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PODR",null,true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"IDDOKT",obj[18],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DATE_1",obj[19],true,"") ;
		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"REFREASON",null,false,"") ;
		WebQueryResult res = null ;
		if (aIsCreateWQR) {
			res= new WebQueryResult() ;
			res.set1(obj[0]) ;res.set2(obj[1]) ;res.set3(obj[2]) ;res.set4(obj[3]) ;res.set5(obj[4]) ;
			res.set6(obj[5]) ;res.set7(obj[6]) ;res.set8(obj[7]) ;res.set9(obj[8]) ;res.set10(obj[9]) ;
			res.set11(obj[10]) ;res.set12(obj[11]) ;res.set13(obj[12]) ;res.set14(obj[13]) ;res.set15(obj[14]) ;
			res.set16(obj[15]) ;res.set17(obj[16]) ;res.set18(obj[17]) ;res.set19(obj[18]) ;res.set20(obj[19]) ;
		}
		return res ;

	}
	private boolean checkDirect(Object aLastname, Object aFirstname,Object aMiddlename
			, Object aBirthday, Object aPreDate, Object aProfile, Object aTypePolicy
			, Object aNumberPolicy,Object aSnilsDoctor,Object aDiagnosis) {
		if (aProfile==null) return false ;
		if (aPreDate==null) return false ;
		if (aSnilsDoctor==null) return false ;
		if (aTypePolicy==null) return false ;
    	/*try {
    		java.util.Date preDate = new SimpleDateFormat("yyyy-DD-mm").parse(""+aPreDate) ;

    	} catch(Exception e) {
    		return false ;
    	}*/
		return true ;
	}
	private boolean checkN2(Object[] aObj) {
		if (aObj[15]==null) return false ;
		return true ;
	}
	private boolean checkN3(Object[] aObj) {
		if (aObj[2]==null) return false ;
		if (aObj[4]==null) return false ;
		if (aObj[8]==null) return false ;
		if (aObj[13]==null) return false ;
		if (aObj[15]==null) return false ;
		return true ;
	}
	private boolean checkHospitalDataFond(Object aLastname, Object aFirstname,Object aMiddlename
			, Object aBirthday, Object aPreDate, Object aProfile, Object aDirectMedCase) {
		StringBuilder fld = new StringBuilder() ;
		fld.append("select case when hdf.istable1='1' then '1' else null end as hdfistable1,hdf.istable2,hdf.istable3,hdf.istable4,hdf.istable5 from HospitalDataFond hdf where");

		StringBuilder sql = new StringBuilder() ;
		if (aDirectMedCase!=null) {
			sql.append(fld) ;

			sql.append(" hdf.directMedCase_id='").append(aDirectMedCase).append("'") ;
			List<Object[]> l = theManager.createNativeQuery(sql.toString()).getResultList() ;
			if (l.isEmpty()) {
				return false ;
			}
			sql = new StringBuilder() ;
		}
		sql.append(fld);
		sql.append(" hdf.lastname='").append(aLastname).append("'") ;
		sql.append(" and hdf.firstname='").append(aFirstname).append("'") ;
		sql.append(" and hdf.middlename='").append(aMiddlename).append("'") ;
		sql.append(" and hdf.birthday=to_date('").append(aBirthday).append("','yyyy-mm-dd')") ;
		sql.append(" and hdf.profile='").append(aProfile).append("'") ;
		sql.append(" and coalesce(hdf.prehospdate,hdf.hospdate)=to_date('").append(aPreDate)
				.append("','yyyy-mm-dd')") ;
		List<Object[]> l1 = theManager.createNativeQuery(sql.toString()).getResultList() ;
		if (l1.isEmpty()) {
			return false ;
		} else if (l1.size()==1) {
			Object[] o=l1.get(0) ;
			if (o[1]==null) return false ;
		}
			return true ;
	}
	public WebQueryResult exportN2_plan_otherLpu(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage
			, boolean aSaveInFolder)
			throws ParserConfigurationException, TransformerException {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		if (aNPackage==null || aNPackage.equals("")) {aNPackage = EjbInjection.getInstance()
				.getLocalService(ISequenceService.class)
				.startUseNextValueNoCheck("PACKAGE_HOSP","number");
		}
		String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
		workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
		String workAddDir =aSaveInFolder?config.get("data.dir.order263.out", null):null;
		String filename = getTitleFile("2",aLpu,aPeriodByReestr,aNPackage) ;
		WebQueryResult res = new WebQueryResult() ;

		XmlDocument xmlDoc = new XmlDocument() ;
		XmlDocument xmlDocError = new XmlDocument() ;
		XmlDocument xmlDocExist = new XmlDocument() ;
		Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
		Element root_error = xmlDocError.newElement(xmlDocError.getDocument(), "ZL_LIST", null);
		Element root_exist = xmlDocExist.newElement(xmlDocExist.getDocument(), "ZL_LIST", null);
//		ArrayList<WebQueryResult> i_exist=new ArrayList<>() ;
		List<WebQueryResult> i_error=new ArrayList<>() ;

		StringBuilder sql = new StringBuilder() ;
		sql.append("select to_char(sls.dateStart,'yyyy-mm-dd') as f0datestart");
		sql.append(" ,cast(sls.entranceTime as varchar(5)) as f1entrancetime");
		sql.append(" ,vmc.code as f2medpolicytype");
		sql.append(" ,mp.series as f3mpseries");
		sql.append(" , mp.polnumber as f4polnumber");
		sql.append(" , case when oss.smocode is null or oss.smocode='' then ri.smocode else oss.smoCode end as f5oossmocode");
		sql.append(" , ri.ogrn as f6ogrnSmo");
		sql.append(" ,case when mp.dtype='MedPolicyOmc' then '12000' else okt.okato end as f7okatoSmo");
		sql.append(" ,p.lastname as f8lastname");
		sql.append(" ,p.firstname as f9firstname");
		sql.append(" ,p.middlename as f10middlename");
		sql.append(" ,vs.omcCode as f11vsomccode");
		sql.append(" ,to_char(p.birthday,'yyyy-mm-dd') as f12birthday");
		sql.append(" ,coalesce(vmhp.code,vbt.codeF) as f13vbtomccode");
		sql.append(" ,ss.code as f14sscode");
		sql.append(" ,(select max(mkb.code) from diagnosis diag left join VocIdc10 mkb on mkb.id=diag.idc10_id left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationtype_id where diag.medcase_id=slo.id and vpd.code='1' and vdrt.code = '4')  as f15mkbcode");
		sql.append(" ,coalesce(hdf.directLpuCode,lpu.codef,plpu.codef) as f16lpucodef") ;
		sql.append(" ,coalesce(hdf.orderLpuCode,olpu.codef,oplpu.codef) as f17olpucodef") ;
		sql.append(" ,cast('0' as varchar(1)) as f18numberfond") ;
		sql.append(" ,to_char(sls.orderDate,'yyyy-mm-dd') as f19directDate") ;
		sql.append(" ,cast('1' as varchar(1))  as f20pokaz");
		sql.append(" ,case when vbst.code='3' then '2' else vbst.code end as f21vbtomccode");
		sql.append(" ,case when bf.forChild='1' then cast('1' as varchar(1)) else cast('0' as varchar(1)) end as f22vbtomccode");

		sql.append("  from medcase sls");
		sql.append(" left join HospitalDataFond hdf on hdf.hospitalMedCase_id=sls.id");
		sql.append(" left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id");
		sql.append(" left join medpolicy mp on mp.id=mcmp.policies_id");
		sql.append(" left join Vocmedpolicyomc vmc on mp.type_id=vmc.id");
		sql.append(" left join Omc_kodter okt on okt.id=mp.insuranceCompanyArea_id");
		sql.append(" left join Omc_SprSmo oss on oss.id=mp.insuranceCompanyCode_id");
		sql.append(" left join reg_ic ri on ri.id=mp.company_id");
		sql.append(" left join mislpu lpu on lpu.id=sls.lpu_id");
		sql.append(" left join mislpu plpu on plpu.id=lpu.parent_id");
		sql.append(" left join mislpu olpu on olpu.id=sls.orderlpu_id");
		sql.append(" left join mislpu oplpu on oplpu.id=olpu.parent_id");

		sql.append(" left join StatisticStub ss on ss.id=sls.statisticStub_id");
		sql.append(" left join Patient p on p.id=sls.patient_id");
		sql.append(" left join VocSex vs on vs.id=p.sex_id");
		sql.append(" left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'");
		sql.append(" left join BedFund bf on bf.id=slo.bedFund_id");
		sql.append(" left join VocBedType vbt on vbt.id=bf.bedType_id");
		sql.append(" left join VocE2MedHelpProfile vmhp on vmhp.id=vbt.medHelpProfile_id");
		sql.append(" left join VocBedSubType vbst on vbst.id=bf.bedSubType_id");
		sql.append(" left join VocServiceStream vss on vss.id=sls.serviceStream_id");
		sql.append(" where sls.dtype='HospitalMedCase' and sls.dateStart between to_date('").append(aDateFrom).append("','yyyy-mm-dd') and to_date('").append(aDateTo).append("','yyyy-mm-dd')");
		sql.append(" and sls.deniedHospitalizating_id is null and slo.prevMedCase_id is null");
		sql.append(" and vss.code in ('OBLIGATORYINSURANCE')  and (sls.emergency is null or sls.emergency='0')") ;
		sql.append(" and coalesce(hdf.orderLpuCode,olpu.codef,oplpu.codef)!='").append(aLpu).append("'") ;
		sql.append(" and hdf.id is null and (hdf.numberfond is null or hdf.numberfond='')") ;
		sql.append(" and (hdf.istable3 is null or hdf.istable3='0')") ;
		sql.append(" and (hdf.istable2 is null or hdf.istable2='0')") ;
		sql.append(" and (hdf.istable4 is null or hdf.istable4='0')") ;
		sql.append(" and (hdf.istable5 is null or hdf.istable5='0')") ;
		sql.append(" and mp.dtype='MedPolicyOmc'"); //только местные полиса, иногородних не выгружаем

		sql.append(" order by p.lastname,p.firstname,p.middlename") ;

		List<Object[]> list = theManager.createNativeQuery(sql.toString())
				.setMaxResults(70000).getResultList() ;
		Element title = xmlDoc.newElement(root, "ZGLV", null);
		Element title_error = xmlDocError.newElement(root_error, "ZGLV", null);
		Element title_exist = xmlDocExist.newElement(root_exist, "ZGLV", null);
		xmlDoc.newElement(title, "VERSION", "1.0");
		xmlDoc.newElement(title, "DATA", aDateFrom);xmlDocExist.newElement(title_exist, "DATA", aDateFrom);xmlDocError.newElement(title_error, "DATA", aDateFrom);
		xmlDoc.newElement(title, "FILENAME", filename);
		//int i=0 ;
		for (Object[] obj:list) {
			if (checkN2(obj)) {
				Element zap = xmlDoc.newElement(root, "NPR", null);
				recordN2(xmlDoc, zap, obj, false) ;
			} else {
				Element zapError = xmlDocError.newElement(root_error, "NPR", null);
				i_error.add(recordN2(xmlDocError, zapError, obj,true)) ;

			}
		}
		XmlUtil.saveXmlDocument(xmlDoc,new File(workDir+"/"+filename+".xml")) ;
		if (workAddDir!=null) XmlUtil.saveXmlDocument(xmlDoc,new File(workAddDir+"/"+filename+".xml")) ;
/*		if (!i_exist.isEmpty()) {
			res.set2(new StringBuilder().append("exist_").append(filename).append(".xml").toString()) ;
			res.set4(i_exist) ;
			XmlUtil.saveXmlDocument(xmlDocExist,new File(new StringBuilder().append(workDir).append("/exist_").append(filename).append(".xml").toString())) ;
			if (workAddDir!=null) XmlUtil.saveXmlDocument(xmlDocExist,new File(new StringBuilder().append(workAddDir).append("/").append("exist_").append(filename).append(".xml").toString())) ;
		} */
		if (!i_error.isEmpty()) {
			res.set3(new StringBuilder().append("error_").append(filename).append(".xml").toString()) ;
			res.set5(i_error) ;
			XmlUtil.saveXmlDocument(xmlDocError,new File(new StringBuilder().append(workDir).append("/error_").append(filename).append(".xml").toString())) ;
			if (workAddDir!=null) XmlUtil.saveXmlDocument(xmlDocError,new File(new StringBuilder().append(workAddDir).append("/").append("error_").append(filename).append(".xml").toString())) ;
		}
		res.set1(filename+".xml") ;
		if (workAddDir!=null) {
			chmodDir(workAddDir);
		}
		return res;
	}

	public WebQueryResult exportN2_trasferInLpu(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder)
			throws ParserConfigurationException, TransformerException {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		if (aNPackage==null || aNPackage.equals("")) {aNPackage = EjbInjection.getInstance()
				.getLocalService(ISequenceService.class)
				.startUseNextValueNoCheck("PACKAGE_HOSP","number");
		}
		String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
		workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
		String workAddDir =aSaveInFolder?config.get("data.dir.order263.out", null):null;
		String filename = getTitleFile("2",aLpu,aPeriodByReestr,aNPackage) ;
		WebQueryResult res = new WebQueryResult() ;

		XmlDocument xmlDoc = new XmlDocument() ;
		XmlDocument xmlDocError = new XmlDocument() ;
		XmlDocument xmlDocExist = new XmlDocument() ;
		Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
		Element root_error = xmlDocError.newElement(xmlDocError.getDocument(), "ZL_LIST", null);
		Element root_exist = xmlDocExist.newElement(xmlDocExist.getDocument(), "ZL_LIST", null);
//		ArrayList<WebQueryResult> i_exist=new ArrayList<>() ;
		List<WebQueryResult> i_error=new ArrayList<>() ;

		StringBuilder sql = new StringBuilder() ;
		sql.append("select  sls.id as slsid, count(distinct case when sloALL.dtype='DepartmentMedCase' then coalesce(vmhpALL.code,vbtALL.codef) else null end) cntProfile" );
		sql.append(", count(distinct case when sloALL.dtype='DepartmentMedCase' then sloALL.id else null end) as cntDep");

		sql.append("  from medcase sls");
		sql.append(" left join medcase sloALL on sloALL.parent_id=sls.id");
		sql.append(" left join bedfund bfALL on bfALL.id=sloALL.bedfund_id");
		sql.append(" left join vocbedtype vbtALL on vbtALL.id=bfALL.bedtype_id");
		sql.append(" left join VocE2MedHelpProfile vmhpALL on vmhpALL.id=vbtALL.medHelpProfile_id");
		sql.append(" left join HospitalDataFond hdf on hdf.hospitalMedCase_id=sls.id");
		sql.append(" left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id");
		sql.append(" left join medpolicy mp on mp.id=mcmp.policies_id");
		sql.append(" left join Vocmedpolicyomc vmc on mp.type_id=vmc.id");
		sql.append(" left join Omc_kodter okt on okt.id=mp.insuranceCompanyArea_id");
		sql.append(" left join Omc_SprSmo oss on oss.id=mp.insuranceCompanyCode_id");
		sql.append(" left join reg_ic ri on ri.id=mp.company_id");
		sql.append(" left join mislpu lpu on lpu.id=sls.lpu_id");
		sql.append(" left join mislpu plpu on plpu.id=lpu.parent_id");
		sql.append(" left join mislpu olpu on olpu.id=sls.orderlpu_id");
		sql.append(" left join mislpu oplpu on oplpu.id=olpu.parent_id");

		sql.append(" left join StatisticStub ss on ss.id=sls.statisticStub_id");
		sql.append(" left join Patient p on p.id=sls.patient_id");
		sql.append(" left join VocSex vs on vs.id=hdf.sex_id");
		sql.append(" left join medcase slo on slo.parent_id=sls.id ");
		sql.append(" left join BedFund bf on bf.id=slo.bedFund_id");
		sql.append(" left join VocBedType vbt on vbt.id=bf.bedType_id");
		sql.append(" left join VocBedSubType vbst on vbst.id=bf.bedSubType_id");
		sql.append(" left join VocServiceStream vss on vss.id=sls.serviceStream_id");
		sql.append(" where sls.dtype='HospitalMedCase' and sls.dateFinish between to_date('").append(aDateFrom).append("','yyyy-mm-dd') and to_date('").append(aDateTo).append("','yyyy-mm-dd')");
		sql.append(" and sls.deniedHospitalizating_id is null and slo.prevMedCase_id is null and upper(slo.dtype)='DEPARTMENTMEDCASE'");
		sql.append(" and vss.code in ('OBLIGATORYINSURANCE')") ;
		sql.append(" and hdf.id is not null and hdf.numberfond is not null and hdf.numberfond!=''") ;
		sql.append(" and (hdf.istable3 is null or hdf.istable3='0')") ;
		sql.append(" and (hdf.istable2 is null or hdf.istable2='0')") ;
		sql.append(" and (hdf.istable4 is null or hdf.istable4='0')") ;
		sql.append(" and (hdf.istable5 is null or hdf.istable5='0')") ;
		sql.append(" and mp.dtype='MedPolicyOmc'"); //только местные полиса, иногородних не выгружаем
		sql.append(" group by sls.id,p.lastname,p.firstname,p.middlename") ;
		sql.append(" order by p.lastname,p.firstname,p.middlename") ;

		List<Object[]> list = theManager.createNativeQuery(sql.toString())
				.setMaxResults(70000).getResultList() ;

		Element title = xmlDoc.newElement(root, "ZGLV", null);
		Element title_error = xmlDocError.newElement(root_error, "ZGLV", null);
		Element title_exist = xmlDocExist.newElement(root_exist, "ZGLV", null);
		xmlDoc.newElement(title, "VERSION", "1.0");
		xmlDoc.newElement(title, "DATA", aDateFrom);xmlDocExist.newElement(title_exist, "DATA", aDateFrom);xmlDocError.newElement(title_error, "DATA", aDateFrom);
		xmlDoc.newElement(title, "FILENAME", filename);
		//int i=0 ;
		for (Object[] obj:list) {
			if (ConvertSql.parseLong(obj[1])>1L) {
				StringBuilder sqlDep = new StringBuilder() ;
				sqlDep.append("select to_char(slo.dateStart,'yyyy-mm-dd') as f0datestart");
				sqlDep.append(" ,cast(slo.entranceTime as varchar(5)) as f1entrancetime");
				sqlDep.append(" ,vmc.code as f2medpolicytype");
				sqlDep.append(" ,mp.series as f3mpseries");
				sqlDep.append(" , mp.polnumber as f4polnumber");
				sqlDep.append(" , case when oss.smocode is null or oss.smocode='' then ri.smocode else oss.smoCode end as f5oossmocode");
				sqlDep.append(" , ri.ogrn as f6ogrnSmo");
				sqlDep.append(" ,case when mp.dtype='MedPolicyOmc' then '12000' else okt.okato end as f7okatoSmo");
				sqlDep.append(" ,hdf.lastname as f8lastname");
				sqlDep.append(" ,hdf.firstname as f9firstname");
				sqlDep.append(" ,hdf.middlename as f10middlename");
				sqlDep.append(" ,vs.omcCode as f11vsomccode");
				sqlDep.append(" ,to_char(hdf.birthday,'yyyy-mm-dd') as f12birthday");
				sqlDep.append(" ,coalesce(vmhp.code,vbt.codeF) as f13vbtomccode");
				sqlDep.append(" ,ss.code as f14sscode");
				sqlDep.append(" ,(select max(mkb.code) from diagnosis diag left join VocIdc10 mkb on mkb.id=diag.idc10_id left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationtype_id where diag.medcase_id=slo.id and vpd.code='1' and vdrt.code = '4')  as f15mkbcode");
				sqlDep.append(" ,coalesce(hdf.directLpuCode,lpu.codef,plpu.codef) as f16lpucodef") ;
				sqlDep.append(" ,coalesce(hdf.orderLpuCode,olpu.codef,oplpu.codef) as f17olpucodef") ;
				sqlDep.append(" ,hdf.numberfond as f18numberfond") ;
				sqlDep.append(" ,to_char(slo.dateStart,'yyyy-mm-dd') as f19directDate") ;
				sqlDep.append(" ,hdf.formHelp  as f20pokaz");
				sqlDep.append(" ,case when vbst.code='3' then '2' else vbst.code end as f21vbtomccode");
				sqlDep.append(" ,case when bf.forChild='1' then cast('1' as varchar(1)) else cast('0' as varchar(1)) end as f22vbtomccode");

				sqlDep.append("  from medcase sls");
				sqlDep.append(" left join HospitalDataFond hdf on hdf.hospitalMedCase_id=sls.id");
				sqlDep.append(" left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id");
				sqlDep.append(" left join medpolicy mp on mp.id=mcmp.policies_id");
				sqlDep.append(" left join Vocmedpolicyomc vmc on mp.type_id=vmc.id");
				sqlDep.append(" left join Omc_kodter okt on okt.id=mp.insuranceCompanyArea_id");
				sqlDep.append(" left join Omc_SprSmo oss on oss.id=mp.insuranceCompanyCode_id");
				sqlDep.append(" left join reg_ic ri on ri.id=mp.company_id");
				sqlDep.append(" left join mislpu lpu on lpu.id=sls.lpu_id");
				sqlDep.append(" left join mislpu plpu on plpu.id=lpu.parent_id");
				sqlDep.append(" left join mislpu olpu on olpu.id=sls.orderlpu_id");
				sqlDep.append(" left join mislpu oplpu on oplpu.id=olpu.parent_id");

				sqlDep.append(" left join StatisticStub ss on ss.id=sls.statisticStub_id");
				sqlDep.append(" left join Patient p on p.id=sls.patient_id");
				sqlDep.append(" left join VocSex vs on vs.id=hdf.sex_id");
				sqlDep.append(" left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'");
				sqlDep.append(" left join BedFund bf on bf.id=slo.bedFund_id");
				sqlDep.append(" left join VocBedType vbt on vbt.id=bf.bedType_id");
				sqlDep.append(" left join VocE2MedHelpProfile vmhp on vmhp.id=vbt.medHelpProfile_id");
				sqlDep.append(" left join VocBedSubType vbst on vbst.id=bf.bedSubType_id");
				sqlDep.append(" left join VocServiceStream vss on vss.id=sls.serviceStream_id");
				sqlDep.append(" where sls.id=").append(obj[0]).append(" and slo.prevdocument_id is null") ;
				sqlDep.append(" order by p.lastname,p.firstname,p.middlename,slo.datestart,slo.entrancetime") ;

				if (checkN2(obj)) {
					Element zap = xmlDoc.newElement(root, "NPR", null);
					recordN2(xmlDoc, zap, obj, false) ;
				} else {
					Element zapError = xmlDocError.newElement(root_error, "NPR", null);
					i_error.add(recordN2(xmlDocError, zapError, obj,true)) ;

				}
			}
		}
		XmlUtil.saveXmlDocument(xmlDoc,new File(workDir+"/"+filename+".xml")) ;
		if (workAddDir!=null) XmlUtil.saveXmlDocument(xmlDoc,new File(workAddDir+"/"+filename+".xml")) ;

/*		if (!i_exist.isEmpty()) {
			res.set2(new StringBuilder().append("exist_").append(filename).append(".xml").toString()) ;
			res.set4(i_exist) ;
			XmlUtil.saveXmlDocument(xmlDocExist,new File(new StringBuilder().append(workDir).append("/exist_").append(filename).append(".xml").toString())) ;
			if (workAddDir!=null) XmlUtil.saveXmlDocument(xmlDocExist,new File(new StringBuilder().append(workAddDir).append("/").append("/exist_").append(filename).append(".xml").toString())) ;
		} */
		if (!i_error.isEmpty()) {
			res.set3(new StringBuilder().append("error_").append(filename).append(".xml").toString()) ;
			res.set5(i_error) ;
			XmlUtil.saveXmlDocument(xmlDocError,new File(new StringBuilder().append(workDir).append("/error_").append(filename).append(".xml").toString())) ;
			if (workAddDir!=null) XmlUtil.saveXmlDocument(xmlDocError,new File(new StringBuilder().append(workAddDir).append("/").append("/error_").append(filename).append(".xml").toString())) ;
		}
		res.set1(filename+".xml") ;
		if (workAddDir!=null) {
			chmodDir(workAddDir);
		}
		return res;
	}

	public WebQueryResult exportN2(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder)
			throws ParserConfigurationException, TransformerException {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		if (aNPackage==null || aNPackage.equals("")) {aNPackage = EjbInjection.getInstance()
				.getLocalService(ISequenceService.class)
				.startUseNextValueNoCheck("PACKAGE_HOSP","number");
		}
		String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
		workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
		String workAddDir =aSaveInFolder?config.get("data.dir.order263.out", null):null;
		String filename = getTitleFile("2",aLpu,aPeriodByReestr,aNPackage) ;
		WebQueryResult res = new WebQueryResult() ;

		XmlDocument xmlDoc = new XmlDocument() ;
		XmlDocument xmlDocError = new XmlDocument() ;
		XmlDocument xmlDocExist = new XmlDocument() ;
		Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
		Element root_error = xmlDocError.newElement(xmlDocError.getDocument(), "ZL_LIST", null);
		Element root_exist = xmlDocExist.newElement(xmlDocExist.getDocument(), "ZL_LIST", null);
//		ArrayList<WebQueryResult> i_exist=new ArrayList<WebQueryResult>() ;
		List<WebQueryResult> i_error=new ArrayList<>() ;

		StringBuilder sql = new StringBuilder() ;
		sql.append("select to_char(sls.dateStart,'yyyy-mm-dd') as f0datestart");
		sql.append(" ,cast(sls.entranceTime as varchar(5)) as f1entrancetime");
		sql.append(" ,vmc.code as f2medpolicytype");
		sql.append(" ,mp.series as f3mpseries");
		sql.append(" , mp.polnumber as f4polnumber");
		sql.append(" , case when oss.smocode is null or oss.smocode='' then ri.smocode else oss.smoCode end as f5oossmocode");
		sql.append(" , ri.ogrn as f6ogrnSmo");
		sql.append(" ,case when mp.dtype='MedPolicyOmc' then '12000' else okt.okato end as f7okatoSmo");
		sql.append(" ,hdf.lastname as f8lastname");
		sql.append(" ,hdf.firstname as f9firstname");
		sql.append(" ,hdf.middlename as f10middlename");
		sql.append(" ,vs.omcCode as f11vsomccode");
		sql.append(" ,to_char(hdf.birthday,'yyyy-mm-dd') as f12birthday");
		sql.append(" ,coalesce(vmhp.code,vbt.codeF) as f13vbtomccode");
		sql.append(" ,ss.code as f14sscode")
		.append(" ,coalesce (")
			.append("(select max(mkb.code) from diagnosis diag left join VocIdc10 mkb on mkb.id=diag.idc10_id ") //Клиинческий диагноз в отделении
			.append(" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id ")
			.append(" left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationtype_id ")
			.append(" where diag.medcase_id=slo.id and vpd.code='1' and vdrt.code = '4')")
			.append(",(select max(mkb.code) from diagnosis diag left join VocIdc10 mkb") // Направительный диагноз в отделении
			.append(" on mkb.id=diag.idc10_id ")
			.append(" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id")
			.append(" left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationtype_id")
			.append(" where diag.medcase_id=slo.id and vpd.code='1' and (vdrt.code = '1' or vdrt.code = '2'))")
			.append(",(select max(mkb.code) from diagnosis diag left join VocIdc10 mkb") // Основной направительный диагноз в госпитализации
			.append(" on mkb.id=diag.idc10_id ")
			.append(" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id")
			.append(" left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationtype_id")
			.append(" where diag.medcase_id=sls.id and vpd.code='1' and (vdrt.code = '1' or vdrt.code = '2'))")
			.append(",(select max(mkb.code) from diagnosis diag left join VocIdc10 mkb") // Любой направительный диагноз в госпитализации
			.append(" on mkb.id=diag.idc10_id ")
			.append(" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id")
			.append(" left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationtype_id")
			.append(" where diag.medcase_id=sls.id and (vdrt.code = '1' or vdrt.code = '2'))")
				.append(") as f15mkbcode");
		//sql.append(" ,(select max(mkb.code) from diagnosis diag left join VocIdc10 mkb on mkb.id=diag.idc10_id left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationtype_id where diag.medcase_id=slo.id and vpd.code='1' and vdrt.code = '4')  as f15mkbcode");
		sql.append(" ,coalesce(hdf.directLpuCode,lpu.codef,plpu.codef) as f16lpucodef") ;
		sql.append(" ,coalesce(hdf.orderLpuCode,olpu.codef,oplpu.codef) as f17olpucodef") ;
		sql.append(" ,hdf.numberfond as f18numberfond") ;
		sql.append(" ,to_char(hdf.directDate,'yyyy-mm-dd') as f19directDate") ;
		sql.append(" ,hdf.formHelp  as f20pokaz");
		sql.append(" ,case when vbst.code='3' then '2' else vbst.code end as f21vbtomccode");
		sql.append(" ,case when bf.forChild='1' then cast('1' as varchar(1)) else cast('0' as varchar(1)) end as f22vbtomccode");

		sql.append("  from medcase sls");
		sql.append(" left join HospitalDataFond hdf on hdf.hospitalMedCase_id=sls.id");
		sql.append(" left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id");
		sql.append(" left join medpolicy mp on mp.id=mcmp.policies_id");
		sql.append(" left join Vocmedpolicyomc vmc on mp.type_id=vmc.id");
		sql.append(" left join Omc_kodter okt on okt.id=mp.insuranceCompanyArea_id");
		sql.append(" left join Omc_SprSmo oss on oss.id=mp.insuranceCompanyCode_id");
		sql.append(" left join reg_ic ri on ri.id=mp.company_id");
		sql.append(" left join mislpu lpu on lpu.id=sls.lpu_id");
		sql.append(" left join mislpu plpu on plpu.id=lpu.parent_id");
		sql.append(" left join mislpu olpu on olpu.id=sls.orderlpu_id");
		sql.append(" left join mislpu oplpu on oplpu.id=olpu.parent_id");

		sql.append(" left join StatisticStub ss on ss.id=sls.statisticStub_id");
		sql.append(" left join Patient p on p.id=sls.patient_id");
		sql.append(" left join VocSex vs on vs.id=hdf.sex_id");
		sql.append(" left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'");
		sql.append(" left join BedFund bf on bf.id=slo.bedFund_id");
		sql.append(" left join VocBedType vbt on vbt.id=bf.bedType_id");
		sql.append(" left join VocE2MedHelpProfile vmhp on vmhp.id=vbt.medHelpProfile_id");
		sql.append(" left join VocBedSubType vbst on vbst.id=bf.bedSubType_id");
		sql.append(" left join VocServiceStream vss on vss.id=sls.serviceStream_id");
		sql.append(" where sls.dtype='HospitalMedCase' and sls.dateStart between to_date('").append(aDateFrom).append("','yyyy-mm-dd') and to_date('").append(aDateTo).append("','yyyy-mm-dd')");
		sql.append(" and sls.deniedHospitalizating_id is null and slo.prevMedCase_id is null");
		sql.append(" and vss.code in ('OBLIGATORYINSURANCE','OTHER')") ;
		sql.append(" and hdf.id is not null and hdf.numberfond is not null and hdf.numberfond!=''") ;
		sql.append(" and (hdf.istable3 is null or hdf.istable3='0')") ;
		sql.append(" and (hdf.istable2 is null or hdf.istable2='0')") ;
		sql.append(" and (hdf.istable4 is null or hdf.istable4='0')") ;
		sql.append(" and (hdf.istable5 is null or hdf.istable5='0')") ;
		sql.append(" and mp.dtype='MedPolicyOmc'"); //только местные полиса, иногородних не выгружаем
		sql.append(" order by p.lastname,p.firstname,p.middlename") ;

		List<Object[]> list = theManager.createNativeQuery(sql.toString())
				.setMaxResults(70000).getResultList() ;
		Element title = xmlDoc.newElement(root, "ZGLV", null);
		Element title_error = xmlDocError.newElement(root_error, "ZGLV", null);
		Element title_exist = xmlDocExist.newElement(root_exist, "ZGLV", null);
		xmlDoc.newElement(title, "VERSION", "1.0");
		xmlDoc.newElement(title, "DATA", aDateFrom);xmlDocExist.newElement(title_exist, "DATA", aDateFrom);xmlDocError.newElement(title_error, "DATA", aDateFrom);
		xmlDoc.newElement(title, "FILENAME", filename);
		//int i=0 ;
		for (Object[] obj:list) {
			if (checkN2(obj)) {
				Element zap = xmlDoc.newElement(root, "NPR", null);
				recordN2(xmlDoc, zap, obj, false) ;
			} else {
				Element zapError = xmlDocError.newElement(root_error, "NPR", null);
				i_error.add(recordN2(xmlDocError, zapError, obj,true)) ;

			}
		}
		XmlUtil.saveXmlDocument(xmlDoc,new File(workDir+"/"+filename+".xml")) ;
		if (workAddDir!=null) XmlUtil.saveXmlDocument(xmlDoc,new File(workAddDir+"/"+filename+".xml")) ;

/*		if (!i_exist.isEmpty()) {
			res.set2(new StringBuilder().append("exist_").append(filename).append(".xml").toString()) ;
			res.set4(i_exist) ;
			XmlUtil.saveXmlDocument(xmlDocExist,new File(new StringBuilder().append(workDir).append("/exist_").append(filename).append(".xml").toString())) ;
			if (workAddDir!=null) XmlUtil.saveXmlDocument(xmlDocExist,new File(new StringBuilder().append(workAddDir).append("/").append("/exist_").append(filename).append(".xml").toString())) ;
		} */
		if (!i_error.isEmpty()) {
			res.set3(new StringBuilder().append("error_").append(filename).append(".xml").toString()) ;
			res.set5(i_error) ;
			XmlUtil.saveXmlDocument(xmlDocError,new File(new StringBuilder().append(workDir).append("/error_").append(filename).append(".xml").toString())) ;
			if (workAddDir!=null) XmlUtil.saveXmlDocument(xmlDocError,new File(new StringBuilder().append(workAddDir).append("/").append("/error_").append(filename).append(".xml").toString())) ;
		}
		res.set1(filename+".xml") ;
		if (workAddDir!=null) {
			chmodDir(workAddDir);
		}
		return res;
	}
	public WebQueryResult exportN1_planHosp(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder)
			throws ParserConfigurationException, TransformerException {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		WebQueryResult res = new WebQueryResult() ;
	//	Map<SecPolicy, String> hash = new HashMap<>() ;
		String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
		workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
		String workAddDir = aSaveInFolder?config.get("data.dir.order263.out", null):null;

		if (aNPackage==null || aNPackage.equals("")) {aNPackage = EjbInjection.getInstance()
				.getLocalService(ISequenceService.class)
				.startUseNextValueNoCheck("PACKAGE_HOSP","number");
		}
		String filename = getTitleFile("1",aLpu,aPeriodByReestr,aNPackage) ;
		XmlDocument xmlDoc = new XmlDocument() ;
		XmlDocument xmlDocError = new XmlDocument() ;
		XmlDocument xmlDocExist = new XmlDocument() ;
		Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
		Element root_error = xmlDocError.newElement(xmlDocError.getDocument(), "ZL_LIST", null);
		Element root_exist = xmlDocExist.newElement(xmlDocExist.getDocument(), "ZL_LIST", null);
		StringBuilder sql = new StringBuilder() ;
		sql.append(" select to_char(sls.dateStart,'yyyy-mm-dd') as wchbcreatedate");
		sql.append(" ,cast('1' as varchar(1)) as forPom");
		sql.append(" ,coalesce(lpu.codef,plpu.codef) as lpuSent");
		sql.append(" ,coalesce(lpu.codef,plpu.codef) as lpuDirect");
		sql.append(" ,vmc.code as medpolicytype");
		sql.append(" ,mp.series as mpseries");
		sql.append(" , mp.polnumber as polnumber");
		sql.append(" , case when oss.smocode is null or oss.smocode='' then ri.smocode else oss.smoCode end as oossmocode");
		sql.append(" , ri.ogrn as ogrnSmo");
		sql.append(" ,case when mp.dtype='MedPolicyOmc' then '12000' else okt.okato end as okatoSmo");
		sql.append(" ,p.lastname as lastname");
		sql.append(" ,p.firstname as firstname");
		sql.append(" ,p.middlename as middlename");
		sql.append(" ,vs.omcCode as vsomccode");
		sql.append(" ,to_char(p.birthday,'yyyy-mm-dd') as birthday");
		sql.append(" ,case when p.phone is null or p.phone='' then '*' else p.phone end as phonepatient");
		sql.append(" ,mkb.code as mkbcode");
		sql.append(" ,coalesce(vmhp.code,vbt.codeF) as vbtcodef");
		sql.append(" ,bf.snilsDoctorDirect263 as wpsnils");
		sql.append(" ,to_char(sls.dateStart,'yyyy-mm-dd') as wchbdatefrom");
		sql.append(", cast(null as int) as visit");
		sql.append(", case when vbst.code='3' then '2' else vbst.code end as v22bstcode");
		sql.append(", case when bf.forChild='1' then cast('1' as varchar(1)) else cast('0' as varchar(1)) end as f23det");
		sql.append(",to_char(sls.dateStart,'yyyy')||'"+aLpu+"'||ss.id as f23_internalDirectionNumber");
		sql.append("  from medcase sls");
		sql.append(" left join HospitalDataFond hdf on hdf.hospitalMedCase_id=sls.id");
		sql.append(" left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id");
		sql.append(" left join medpolicy mp on mp.id=mcmp.policies_id");
		sql.append(" left join Vocmedpolicyomc vmc on mp.type_id=vmc.id");
		sql.append(" left join Omc_kodter okt on okt.id=mp.insuranceCompanyArea_id");
		sql.append(" left join Omc_SprSmo oss on oss.id=mp.insuranceCompanyCode_id");
		sql.append(" left join reg_ic ri on ri.id=mp.company_id");
		sql.append(" left join mislpu lpu on lpu.id=sls.lpu_id");
		sql.append(" left join mislpu plpu on plpu.id=lpu.parent_id");
		sql.append(" left join mislpu olpu on olpu.id=sls.orderlpu_id");
		sql.append(" left join mislpu oplpu on oplpu.id=olpu.parent_id");
		sql.append(" left join StatisticStub ss on ss.id=sls.statisticStub_id");
		sql.append(" left join Patient p on p.id=sls.patient_id");
		sql.append(" left join VocSex vs on vs.id=p.sex_id");
		sql.append(" left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'");
		sql.append(" left join diagnosis diag on diag.medcase_id=slo.id and diag.priority_id='1' and diag.registrationType_id = '4'");
		sql.append(" left join VocIdc10 mkb on mkb.id=diag.idc10_id") ;
		sql.append(" left join BedFund bf on bf.id=slo.bedFund_id");
		sql.append(" left join VocBedType vbt on vbt.id=bf.bedType_id");
		sql.append(" left join VocE2MedHelpProfile vmhp on vmhp.id=vbt.medHelpProfile_id");
		sql.append(" left join VocBedSubType vbst on vbst.id=bf.bedSubType_id");
		sql.append(" left join VocServiceStream vss on vss.id=sls.serviceStream_id");
		sql.append(" where sls.dtype='HospitalMedCase' and sls.dateStart between to_date('").append(aDateFrom).append("','yyyy-mm-dd') and to_date('").append(aDateTo).append("','yyyy-mm-dd')");
		sql.append(" and sls.deniedHospitalizating_id is null and (sls.emergency is null or sls.emergency='0') and slo.prevMedCase_id is null");
		sql.append(" and vss.code in ('OBLIGATORYINSURANCE')  and (sls.emergency is null or sls.emergency='0')") ;
		sql.append(" and mkb.code is not null and (hdf.id is null)") ;
		sql.append(" and mp.dtype='MedPolicyOmc'"); //только местные полиса, иногородних не выгружаем
		sql.append(" and coalesce(hdf.orderLpuCode,olpu.codef,oplpu.codef)='").append(aLpu).append("'") ;
		sql.append(" order by p.lastname,p.firstname,p.middlename") ;

		List<Object[]> list = theManager.createNativeQuery(sql.toString())
				.setMaxResults(70000).getResultList() ;
		Element title = xmlDoc.newElement(root, "ZGLV", null);
		Element title_error = xmlDocError.newElement(root_error, "ZGLV", null);
		Element title_exist = xmlDocExist.newElement(root_exist, "ZGLV", null);
		xmlDoc.newElement(title, "VERSION", "1.0");
		xmlDoc.newElement(title, "DATA", aDateFrom);xmlDocExist.newElement(title_exist, "DATA", aDateFrom);xmlDocError.newElement(title_error, "DATA", aDateFrom);
		xmlDoc.newElement(title, "FILENAME", filename);
		ArrayList<WebQueryResult> i_exist=new ArrayList<>() ;
		List<WebQueryResult> i_error=new ArrayList<>() ;
		for (Object[] obj:list) {
			if (checkDirect(obj[10],obj[11],obj[12],obj[14],obj[19],obj[17]
					, obj[4], obj[6],obj[18],obj[16])) {
				if (!checkHospitalDataFond(obj[10],obj[11],obj[12],obj[14],obj[19],obj[17],obj[20])) {
					Element zap = xmlDoc.newElement(root, "NPR", null);
					recordN1(xmlDoc, zap, obj, false) ;
				} else {
					Element zapExist = xmlDocExist.newElement(root_exist, "NPR", null);
					i_exist.add(recordN1(xmlDocExist, zapExist, obj,true)) ;
				}
			} else {
				Element zapError = xmlDocError.newElement(root_error, "NPR", null);
				i_error.add(recordN1(xmlDocError, zapError, obj,true)) ;

			}
		}
		XmlUtil.saveXmlDocument(xmlDoc,new File(new StringBuilder().append(workDir).append("/").append(filename).append(".xml").toString())) ;
		if (workAddDir!=null) XmlUtil.saveXmlDocument(xmlDoc,new File(new StringBuilder().append(workAddDir).append("/").append(filename).append(".xml").toString())) ;
		if (!i_exist.isEmpty()) {
			res.set2(new StringBuilder().append("exist_").append(filename).append(".xml").toString()) ;
			res.set4(i_exist) ;
			XmlUtil.saveXmlDocument(xmlDocExist,new File(new StringBuilder().append(workDir).append("/exist_").append(filename).append(".xml").toString())) ;
			if (workAddDir!=null) XmlUtil.saveXmlDocument(xmlDocExist,new File(new StringBuilder().append(workAddDir).append("/").append("exist_").append(filename).append(".xml").toString())) ;
		}
		if (!i_error.isEmpty()) {
			res.set3(new StringBuilder().append("error_").append(filename).append(".xml").toString()) ;
			res.set5(i_error) ;
			XmlUtil.saveXmlDocument(xmlDocError,new File(new StringBuilder().append(workDir).append("/error_").append(filename).append(".xml").toString())) ;
			if (workAddDir!=null) XmlUtil.saveXmlDocument(xmlDocError,new File(new StringBuilder().append(workAddDir).append("/").append("error_").append(filename).append(".xml").toString())) ;
		}
		res.set1(filename+".xml") ;
		if (workAddDir!=null) {
			chmodDir(workAddDir);
		}
		return res;
	}

	public WebQueryResult exportN3(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder)
			throws ParserConfigurationException, TransformerException {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		WebQueryResult res = new WebQueryResult() ;
		String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
		workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
		String workAddDir =aSaveInFolder?config.get("data.dir.order263.out", null):null;

		if (aNPackage==null || aNPackage.equals("")) {aNPackage = EjbInjection.getInstance()
				.getLocalService(ISequenceService.class)
				.startUseNextValueNoCheck("PACKAGE_HOSP","number");
		}
		String filename = getTitleFile("3",aLpu,aPeriodByReestr,aNPackage) ;

		XmlDocument xmlDoc = new XmlDocument() ;
		XmlDocument xmlDocError = new XmlDocument() ;
		Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
		Element root_error = xmlDocError.newElement(xmlDocError.getDocument(), "ZL_LIST", null);
		//int i=0 ;
		List<WebQueryResult> i_error=new ArrayList<WebQueryResult>() ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select to_char(sls.dateStart,'yyyy-mm-dd') as d0atestart");
		sql.append(" ,cast(sls.entranceTime as varchar(5)) as e1ntrancetime");
		sql.append(" ,vmc.code as m2edpolicytype");
		sql.append(" ,mp.series as m3pseries");
		sql.append(" , mp.polnumber as p4olnumber");
		sql.append(" , case when oss.smocode is null or oss.smocode='' then ri.smocode else oss.smoCode end as o5ossmocode");
		sql.append(" , ri.ogrn as o6grnSmo");
		sql.append(" ,case when mp.dtype='MedPolicyOmc' then '12000' else okt.okato end as o7katoSmo");
		sql.append(" ,coalesce(mp.lastname,p.lastname) as l8astname");
		sql.append(" ,coalesce(mp.firstname,p.firstname) as f9irstname");
		sql.append(" ,coalesce(mp.middlename,p.middlename) as m10iddlename");
		sql.append(" ,vs.omcCode as v11somccode");
		sql.append(" ,to_char(p.birthday,'yyyy-mm-dd') as b12irthday");
		sql.append(" ,coalesce(vmhp.code,vbt.codeF) as v13btomccode");
		sql.append(" ,ss.code as s14scode");
		sql.append(" ,mkb.code as m15kbcode");
		sql.append(" ,case when lpu.codef is null or lpu.codef='' then plpu.codef else lpu.codef end as l16pucodef") ;
		sql.append(" ,case when vbst.code='3' then '2' else vbst.code end as f17vbtomccode");
		sql.append(" ,case when bf.forChild='1' then cast('1' as varchar(1)) else cast('0' as varchar(1)) end as f18vbtomccode");
		sql.append("  from medcase sls");
		sql.append(" left join HospitalDataFond hdf on hdf.hospitalMedCase_id=sls.id");
		sql.append(" left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id");
		sql.append(" left join medpolicy mp on mp.id=mcmp.policies_id");
		sql.append(" left join Vocmedpolicyomc vmc on mp.type_id=vmc.id");
		sql.append(" left join Omc_kodter okt on okt.id=mp.insuranceCompanyArea_id");
		sql.append(" left join Omc_SprSmo oss on oss.id=mp.insuranceCompanyCode_id");
		sql.append(" left join reg_ic ri on ri.id=mp.company_id");
		sql.append(" left join mislpu lpu on lpu.id=sls.lpu_id");
		sql.append(" left join mislpu plpu on plpu.id=lpu.parent_id");
		sql.append(" left join StatisticStub ss on ss.id=sls.statisticStub_id");
		sql.append(" left join Patient p on p.id=sls.patient_id");
		sql.append(" left join VocSex vs on vs.id=p.sex_id");
		sql.append(" left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'");
		sql.append(" left join diagnosis diag on diag.medcase_id=slo.id and diag.priority_id='1' and diag.registrationType_id = '4'");
		sql.append(" left join VocIdc10 mkb on mkb.id=diag.idc10_id") ;
		sql.append(" left join BedFund bf on bf.id=slo.bedFund_id");
		sql.append(" left join VocBedType vbt on vbt.id=bf.bedType_id");
		sql.append(" left join VocE2MedHelpProfile vmhp on vmhp.id=vbt.medHelpProfile_id");
		sql.append(" left join VocBedSubType vbst on vbst.id=bf.bedSubType_id");
		sql.append(" left join VocServiceStream vss on vss.id=sls.serviceStream_id");
		sql.append(" where sls.dtype='HospitalMedCase' and sls.dateStart between to_date('").append(aDateFrom).append("','yyyy-mm-dd') and to_date('").append(aDateTo).append("','yyyy-mm-dd')");
		sql.append(" and sls.deniedHospitalizating_id is null and sls.emergency='1' and slo.prevMedCase_id is null");
		sql.append(" and vss.code in ('OBLIGATORYINSURANCE')") ;
		sql.append(" and mkb.code is not null and (hdf.id is not null and (hdf.numberfond is null or hdf.numberfond='') or hdf.id is null)") ;
		sql.append(" and mp.dtype='MedPolicyOmc'"); //только местные полиса, иногородних не выгружаем
		sql.append(" order by p.lastname,p.firstname,p.middlename") ;

		List<Object[]> list = theManager.createNativeQuery(sql.toString())
				.setMaxResults(10000).getResultList() ;
		Element title = xmlDoc.newElement(root, "ZGLV", null);
		xmlDoc.newElement(title, "VERSION", "1.0");
		xmlDoc.newElement(title, "DATA", aDateFrom);
		xmlDoc.newElement(title, "FILENAME", filename);
		for (Object[] obj:list) {
			if (checkN3(obj)) {
				Element zap = xmlDoc.newElement(root, "NPR", null);
				recordN3(xmlDoc, zap, obj, false) ;
			} else {
				Element zapError = xmlDocError.newElement(root_error, "NPR", null);
				i_error.add(recordN3(xmlDocError, zapError, obj,true)) ;
			}
		}
		XmlUtil.saveXmlDocument(xmlDoc,new File(new StringBuilder().append(workDir).append("/").append(filename).append(".xml").toString())) ;
		if (workAddDir!=null) XmlUtil.saveXmlDocument(xmlDoc,new File(new StringBuilder().append(workAddDir).append("/").append(filename).append(".xml").toString())) ;
		res.set1(filename+".xml") ;

		if (!i_error.isEmpty()) {
			res.set3(new StringBuilder().append("error_").append(filename).append(".xml").toString()) ;
			res.set5(i_error) ;
			XmlUtil.saveXmlDocument(xmlDocError,new File(new StringBuilder().append(workDir).append("/error_").append(filename).append(".xml").toString())) ;
			if (workAddDir!=null) XmlUtil.saveXmlDocument(xmlDocError,new File(new StringBuilder().append(workAddDir).append("/").append("/error_").append(filename).append(".xml").toString())) ;
		}
		if (workAddDir!=null) {
			chmodDir(workAddDir);
		}
		return res;
	}

	public String exportN4(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder)
			throws ParserConfigurationException, TransformerException {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
		String workAddDir = aSaveInFolder?config.get("data.dir.order263.out", null):null;

		if (aNPackage==null || aNPackage.equals("")) {aNPackage = EjbInjection.getInstance()
				.getLocalService(ISequenceService.class)
				.startUseNextValueNoCheck("PACKAGE_HOSP","number");
		}
		String filename = getTitleFile("4",aLpu,aPeriodByReestr,aNPackage) ;

		XmlDocument xmlDoc = new XmlDocument() ;

		Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
		StringBuilder sql = new StringBuilder() ;
		sql.append("select hdf.numberFond as orderNumber");
		sql.append(" ,to_char(hdf.directDate,'yyyy-mm-dd') as direct");
		sql.append(" ,hdf.directLpuCode as vsomccode");
		sql.append(" ,hdf.DeniedHospital as DeniedHospital");
		sql.append("  from HospitalDataFond hdf");
		sql.append(" where hdf.preHospDate between to_date('").append(aDateFrom).append("','yyyy-mm-dd') and to_date('").append(aDateTo).append("','yyyy-mm-dd')");
		sql.append(" and hdf.hospitalMedCase_id is null ");
		sql.append(" and hdf.DeniedHospital is not null") ;
		sql.append(" and (hdf.istable4 is null or hdf.istable4='0')") ;
		sql.append(" order by hdf.numberFond") ;

		List<Object[]> list = theManager.createNativeQuery(sql.toString())
				.setMaxResults(70000).getResultList() ;
		Element title = xmlDoc.newElement(root, "ZGLV", null);
		xmlDoc.newElement(title, "VERSION", "1.0");
		xmlDoc.newElement(title, "DATA", aDateFrom);
		xmlDoc.newElement(title, "FILENAME", filename);
		for (Object[] obj:list) {
			Element zap = xmlDoc.newElement(root, "NPR", null);
			//xmlDoc.newElement(zap, "IDCASE", AddressPointServiceBean.getStringValue(++i)) ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"N_NPR",obj[0],true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"D_NPR",obj[1],true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"ISTNPR","2",true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SMOLPU",obj[2],true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"LPU_1",null,false,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PRNPR",obj[3],true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"REFREASON",null,false,"") ;
		}
		XmlUtil.saveXmlDocument(xmlDoc,new File(new StringBuilder().append(workDir).append("/").append(filename).append(".xml").toString())) ;
		if (workAddDir!=null) XmlUtil.saveXmlDocument(xmlDoc,new File(new StringBuilder().append(workAddDir).append("/").append(filename).append(".xml").toString())) ;
		if (workAddDir!=null) {
			chmodDir(workAddDir);
		}
		return filename+".xml";
	}
	public String exportN4b(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage)
			throws ParserConfigurationException, TransformerException {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
		workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;

		String filename = getTitleFile("4",aLpu,aPeriodByReestr,aNPackage) ;

		File outFile = new File(workDir+"/"+filename+".xml") ;
		XmlDocument xmlDoc = new XmlDocument() ;

		Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
		StringBuilder sql = new StringBuilder() ;
		sql.append("select sls.orderNumber as orderNumber");
		sql.append(" ,case when sls.emergency='1' then to_char(sls.dateStart,'yyyy-mm-dd') else to_char(sls.orderDate,'yyyy-mm-dd') end as orderDate");
		sql.append(" ,case when sls.emergency='1' then cast('3' as varchar(1)) else cast('1' as varchar(1)) end as emergency");
		sql.append(" ,to_char(sls.dateStart,'yyyy-mm-dd') as datestart");
		sql.append(" ,to_char(sls.dateFinish,'yyyy-mm-dd') as dateFinish");
		sql.append(" ,cast(sls.entranceTime as varchar(5)) as entrancetime");
		sql.append(" ,vmc.code as medpolicytype");
		sql.append(" ,mp.series as mpseries");
		sql.append(" , mp.polnumber as polnumber");
		sql.append(" , case when oss.smocode is null or oss.smocode='' then ri.smocode else oss.smoCode end as oossmocode");
		sql.append(" , ri.ogrn as ogrnSmo");
		sql.append(" ,case when mp.dtype='MedPolicyOmc' then '12000' else okt.okato end as okatoSmo");
		sql.append(" ,p.lastname as lastname");
		sql.append(" ,p.firstname as firstname");
		sql.append(" ,p.middlename as middlename");
		sql.append(" ,vs.omcCode as vsomccode");
		sql.append(" ,to_char(p.birthday,'yyyy-mm-dd') as birthday");
		sql.append(" ,coalesce(vmhp.code,vbt.codeF) as vbtomccode");
		sql.append(" ,ss.code as sscode");
		sql.append(" ,mkb.code as mkbcode");
		sql.append(" ,coalesce(lpu.codef,plpu.codef) as lpucodef") ;
		sql.append("  from medcase sls");
		sql.append(" left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id");
		sql.append(" left join medpolicy mp on mp.id=mcmp.policies_id");
		sql.append(" left join Vocmedpolicyomc vmc on mp.type_id=vmc.id");
		sql.append(" left join Omc_kodter okt on okt.id=mp.insuranceCompanyArea_id");
		sql.append(" left join Omc_SprSmo oss on oss.id=mp.insuranceCompanyCode_id");
		sql.append(" left join reg_ic ri on ri.id=mp.company_id");
		sql.append(" left join mislpu lpu on lpu.id=sls.lpu_id");
		sql.append(" left join mislpu plpu on plpu.id=lpu.parent_id");

		sql.append(" left join StatisticStub ss on ss.id=sls.statisticStub_id");
		sql.append(" left join Patient p on p.id=sls.patient_id");
		sql.append(" left join VocSex vs on vs.id=p.sex_id");
		sql.append(" left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'");
		sql.append(" left join diagnosis diag on diag.medcase_id=sls.id and diag.priority_id='1' ");
		sql.append(" left join VocIdc10 mkb on mkb.id=diag.idc10_id") ;
		sql.append(" left join BedFund bf on bf.id=slo.bedFund_id");
		sql.append(" left join VocBedType vbt on vbt.id=bf.bedType_id");
		sql.append(" left join VocE2MedHelpProfile vmhp on vmhp.id=vbt.medHelpProfile_id");
		sql.append(" left join VocServiceStream vss on vss.id=sls.serviceStream_id");
		sql.append(" where sls.dtype='HospitalMedCase' and sls.dateStart between to_date('").append(aDateFrom).append("','yyyy-mm-dd') and to_date('").append(aDateTo).append("','yyyy-mm-dd')");
		sql.append(" and sls.deniedHospitalizating_id is not null ");
		sql.append(" and vss.code in ('OBLIGATORYINSURANCE','OTHER')") ;
		sql.append(" and mkb.code is not null") ;
		sql.append(" order by p.lastname,p.firstname,p.middlename") ;

		List<Object[]> list = theManager.createNativeQuery(sql.toString())
				.setMaxResults(70000).getResultList() ;
		Element title = xmlDoc.newElement(root, "ZGLV", null);
		xmlDoc.newElement(title, "VERSION", "1.0");
		xmlDoc.newElement(title, "DATA", aDateFrom);
		xmlDoc.newElement(title, "FILENAME", filename);
		for (Object[] obj:list) {
			Element zap = xmlDoc.newElement(root, "NPR", null);
			//xmlDoc.newElement(zap, "IDCASE", AddressPointServiceBean.getStringValue(++i)) ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"N_NPR",obj[0],true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"D_NPR",obj[1],true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"ISTNPR","2",true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SMOLPU",obj[20],true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"LPU_1",null,false,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PRNPR","5",true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"REFREASON",null,false,"") ;
		}
		XmlUtil.saveXmlDocument(xmlDoc,outFile) ;
		return filename+".xml";
	}
	public String exportN5(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder)
			throws ParserConfigurationException, TransformerException {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
		String workAddDir = aSaveInFolder?config.get("data.dir.order263.out", null):null;

		if (aNPackage==null || aNPackage.equals("")) {aNPackage = EjbInjection.getInstance()
				.getLocalService(ISequenceService.class)
				.startUseNextValueNoCheck("PACKAGE_HOSP","number");
		}
		String filename = getTitleFile("5",aLpu,aPeriodByReestr,aNPackage) ;

		//File outFile = new File(workDir+"/"+filename+".xml") ;
		XmlDocument xmlDoc = new XmlDocument() ;
		Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
		StringBuilder sql = new StringBuilder() ;
		sql.append("select hdf.numberfond as orderNumber");
		sql.append(" ,to_char(coalesce(hdf.directDate,hdf.hospdate),'yyyy-mm-dd')  as orderDate");
		sql.append(" ,case when hdf.formHelp is null and hdf.istable3='1' then '3' else hdf.formHelp end as pokaz");
		sql.append(" ,hdf.DirectLpuCode  as lpuSent");
		sql.append(" ,to_char(coalesce(hdf.hospDate,sls.dateStart),'yyyy-mm-dd') as datestart");
		sql.append(" ,to_char(sls.dateFinish,'yyyy-mm-dd') as dateFinish");
		sql.append(" ,cast(coalesce(hdf.hospTime,sls.entranceTime) as varchar(5)) as entrancetime");
		sql.append(" ,vmc.code as medpolicytype");
		sql.append(" ,mp.series as mpseries");
		sql.append(" , mp.polnumber as polnumber");
		sql.append(" , case when oss.smocode is null or oss.smocode='' then ri.smocode else oss.smoCode end as oossmocode");
		sql.append(" , ri.ogrn as ogrnSmo");
		sql.append(" ,case when mp.dtype='MedPolicyOmc' then '12000' else okt.okato end as okatoSmo");
		sql.append(" ,hdf.lastname as lastname");
		sql.append(" ,hdf.firstname as firstname");
		sql.append(" ,hdf.middlename as middlename");
		sql.append(" ,vs.omcCode as vsomccode");
		sql.append(" ,to_char(hdf.birthday,'yyyy-mm-dd') as birthday");
		sql.append(" ,hdf.profile as vbtomccode");
		sql.append(" ,hdf.statcard as sscode");
		//sql.append(" ,mkb.code as mkbcode");
		sql.append(" ") ;
		sql.append("  from medcase sls");
		sql.append(" left join HospitalDataFond hdf on hdf.hospitalMedCase_id=sls.id");
		sql.append(" left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id");
		sql.append(" left join medpolicy mp on mp.id=mcmp.policies_id");
		sql.append(" left join Vocmedpolicyomc vmc on mp.type_id=vmc.id");
		sql.append(" left join Omc_kodter okt on okt.id=mp.insuranceCompanyArea_id");
		sql.append(" left join Omc_SprSmo oss on oss.id=mp.insuranceCompanyCode_id");
		sql.append(" left join reg_ic ri on ri.id=mp.company_id");
		sql.append(" left join mislpu lpu on lpu.id=sls.lpu_id");
		sql.append(" left join mislpu plpu on plpu.id=lpu.parent_id");

		sql.append(" left join StatisticStub ss on ss.id=sls.statisticStub_id");
		sql.append(" left join Patient p on p.id=sls.patient_id");
		sql.append(" left join VocSex vs on vs.id=p.sex_id");
		//sql.append(" left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'");
		//sql.append(" left join diagnosis diag on diag.medcase_id=slo.id and diag.priority_id='1' and diag.registrationType_id = '4'");
		//sql.append(" left join VocIdc10 mkb on mkb.id=diag.idc10_id") ;
		//sql.append(" left join BedFund bf on bf.id=slo.bedFund_id");
		//sql.append(" left join VocBedType vbt on vbt.id=bf.bedType_id");
		sql.append(" left join VocServiceStream vss on vss.id=sls.serviceStream_id");
		sql.append(" where sls.dtype='HospitalMedCase' and sls.dateFinish between to_date('").append(aDateFrom).append("','yyyy-mm-dd') and to_date('").append(aDateTo).append("','yyyy-mm-dd')");
		sql.append(" and sls.deniedHospitalizating_id is null");
		//sql.append(" and vss.code in ('OBLIGATORYINSURANCE','OTHER')") ;
		sql.append(" ") ;
		sql.append(" and hdf.id is not null and hdf.numberfond is not null and hdf.numberfond!=''") ;
		sql.append(" and (hdf.istable2='1' or hdf.istable3='1')") ;
		sql.append(" and (hdf.istable4 is null or hdf.istable4='0')") ;
		sql.append(" and (hdf.istable5 is null or hdf.istable5='0')") ;
		sql.append(" order by p.lastname,p.firstname,p.middlename") ;

		List<Object[]> list = theManager.createNativeQuery(sql.toString())
				.setMaxResults(70000).getResultList() ;
		Element title = xmlDoc.newElement(root, "ZGLV", null);
		xmlDoc.newElement(title, "VERSION", "1.0");
		xmlDoc.newElement(title, "DATA", aDateFrom);
		xmlDoc.newElement(title, "FILENAME", filename);
		for (Object[] obj:list) {
			Element zap = xmlDoc.newElement(root, "NPR", null);
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"N_NPR",obj[0],true,"") ;
			//xmlDoc.newElement(zap, "IDCASE", AddressPointServiceBean.getStringValue(++i)) ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"D_NPR",obj[1],true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"FOR_POM",obj[2],true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"LPU",obj[3],true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"LPU_1",null,false,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DATE_1",obj[4],true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DATE_2",obj[5],true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"W",obj[16],true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DR",obj[17],true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PROFIL",obj[18],true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PODR",null,false,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"NHISTORY",obj[19],true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"REFREASON",null,false,"") ;
		}
		XmlUtil.saveXmlDocument(xmlDoc,new File(new StringBuilder().append(workDir).append("/").append(filename).append(".xml").toString())) ;
		if (workAddDir!=null) XmlUtil.saveXmlDocument(xmlDoc,new File(new StringBuilder().append(workAddDir).append("/").append(filename).append(".xml").toString())) ;
		if (workAddDir!=null) {
			chmodDir(workAddDir);
		}

		return filename+".xml";
	}
	public String exportN6(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder)
			throws ParserConfigurationException, TransformerException, ParseException {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		String time_263 =config.get("injuction263.format.time"
				, "09:00");
		String workDir =config.get("tomcat.data.dir"
				, "/opt/tomcat/webapps/rtf");
		workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
		String workAddDir = aSaveInFolder?config.get("data.dir.order263.out", null):null;

		if (aNPackage==null || aNPackage.equals("")) {aNPackage = EjbInjection.getInstance()
				.getLocalService(ISequenceService.class)
				.startUseNextValueNoCheck("PACKAGE_HOSP","number");
		}
		String filename = getTitleFile("6",aLpu,aPeriodByReestr,aNPackage) ;

		//File outFile = new File(workDir+"/"+filename+".xml") ;
		XmlDocument xmlDoc = new XmlDocument() ;
		Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);

		StringBuilder sqlB = new StringBuilder() ;
		sqlB.append("select   coalesce(vmhp.code,vbt.codeF) as v0btcodef");
		sqlB.append(",  case when vbst.code='3' then '2' else vbst.code end as u1sl_ok");
		sqlB.append(", case when bf.forChild='1' then cast('1' as varchar(1)) else cast('0' as varchar(1)) end as d2et ") ;
		sqlB.append(", sum(bf.amount) as b3famount");
		sqlB.append(", list(distinct ''||bf.id) as b4famount");
		sqlB.append(", (select count(hdf.id) from hospitaldatafond hdf where hdf.profile=vbt.codef ");
		sqlB.append(" 		and forchild=case when bf.forChild='1' then cast('1' as varchar(1)) else cast('0' as varchar(1)) end ");
		sqlB.append(" 		and hdf.bedsubtype=case when vbst.code='3' then '2' else vbst.code end") ;
		sqlB.append("		and prehospdate between to_date('").append(aDateFrom).append("','yyyy-MM-dd') and to_date('").append(aDateTo).append("','yyyy-MM-dd')") ;
		sqlB.append("		and hospitalmedcase_id is null and hdf.directLpuCode=lpu.codef) as a5mountDirect") ;
		sqlB.append("  from BedFund bf ");
		sqlB.append("   left join VocBedType vbt on vbt.id=bf.bedType_id");
		sqlB.append(" left join VocE2MedHelpProfile vmhp on vmhp.id=vbt.medHelpProfile_id");
		sqlB.append("   left join VocBedSubType vbst on vbst.id=bf.bedSubType_id");
		sqlB.append("   left join VocServiceStream vss on vss.id=bf.serviceStream_id");
		sqlB.append("   left join MisLpu lpu on lpu.id=bf.lpu_id ");
		sqlB.append("   left join MisLpu lpuP on lpuP.id=lpu.parent_id ");
		sqlB.append("  where bf.dateStart<=to_date('").append(aDateFrom).append("','yyyy-MM-dd')");
		sqlB.append("  and coalesce(bf.dateFinish,to_date('").append(aDateTo).append("','yyyy-MM-dd')) >=to_date('").append(aDateTo).append("','yyyy-MM-dd')");
		sqlB.append("  and vss.code in ('OBLIGATORYINSURANCE') and (lpu.codef='").append(aLpu).append("' or lpuP.codef='").append(aLpu).append("')");
		sqlB.append("  group by  coalesce(vmhp.code,vbt.codeF) ,   vbst.code , bf.forChild, lpu.codef");
		sqlB.append("  order by coalesce(vmhp.code,vbt.codeF),vbst.code");
		List<Object[]> listB = theManager.createNativeQuery(sqlB.toString())
				.setMaxResults(100).getResultList() ;
		Element title = xmlDoc.newElement(root, "ZGLV", null);
		xmlDoc.newElement(title, "VERSION", "1.0");
		xmlDoc.newElement(title, "DATA", aDateFrom);
		xmlDoc.newElement(title, "FILENAME", filename);
		Calendar cal = Calendar.getInstance() ;
		//SimpleDateFormat format_n=new SimpleDateFormat("yyyy-MM-dd") ;
		SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy") ;
		String dateBegin = aDateFrom ;
		String dateEnd = aDateTo ;

		java.util.Date dat =  ru.nuzmsh.util.format.DateFormat.parseDate(dateBegin,"yyyy-MM-dd") ;
		cal.setTime(dat) ;
		cal.add(Calendar.DAY_OF_MONTH, -1) ;
		dateBegin = format.format(cal.getTime()) ;

		dat = ru.nuzmsh.util.format.DateFormat.parseDate(dateEnd,"yyyy-MM-dd") ;
		cal.setTime(dat) ;
		cal.add(Calendar.DAY_OF_MONTH, -1) ;
		dateEnd = format.format(cal.getTime()) ;


		dat =  ru.nuzmsh.util.format.DateFormat.parseDate(dateBegin) ;
		cal.setTime(dat) ;
		cal.add(Calendar.DAY_OF_MONTH, 1) ;
		String dateNextBegin=format.format(cal.getTime()) ;
		//request.setAttribute("dateNextBegin", date1) ;
		cal.add(Calendar.DAY_OF_MONTH, -2) ;
		//request.setAttribute("datePrevBegin", date1) ;

		dat = ru.nuzmsh.util.format.DateFormat.parseDate(dateEnd) ;
		cal.setTime(dat) ;
		cal.add(Calendar.DAY_OF_MONTH, 1) ;
		String dateNextEnd=format.format(cal.getTime()) ;

		for (Object[] obj:listB) {
			Element zap = xmlDoc.newElement(root, "NPR", null);
			StringBuilder sqlB1 = new StringBuilder() ;
			sqlB1.append("select ") ;
			//sqlB1.append(" '&dateBegin=21.06.2017&dateEnd=22.06.2017&serviceStream=1&typeView=5&department=' as fldId") ;
			//sqlB1.append(" ,vbt.codeF as vbtcodeF") ;
			//sqlB1.append(" ,list(distinct lpu.name) as fldName") ;
			//sqlB1.append(" ,list(distinct vbst.name) as vbstname") ;
			//sqlB1.append(" ,list(distinct vss.name) as vssname") ;
			sqlB1.append(" count(distinct case when (slo.datestart = to_date('").append(dateBegin).append("','dd.mm.yyyy') and cast('").append(time_263).append(":00' as time)>slo.entrancetime") ;
			sqlB1.append("	or to_date('").append(dateBegin).append("','dd.mm.yyyy')>slo.datestart)") ;
			sqlB1.append("	and (slo.datefinish is null ") ;
			sqlB1.append("	or slo.datefinish > to_date('").append(dateBegin).append("','dd.mm.yyyy') ") ;
			sqlB1.append("	or slo.datefinish = to_date('").append(dateBegin).append("','dd.mm.yyyy') and slo.dischargetime>=cast('").append(time_263).append("' as time))") ;
			sqlB1.append("	and (slo.transferdate is null ") ;
			sqlB1.append("	or slo.transferdate > to_date('").append(dateBegin).append("','dd.mm.yyyy')") ;
			sqlB1.append("	or") ;
			sqlB1.append("	slo.transferdate = to_date('").append(dateBegin).append("','dd.mm.yyyy') and slo.transfertime>=cast('").append(time_263).append("' as time))") ;
			sqlB1.append("	 then slo.id else null end)") ;
			sqlB1.append("	as cnt0CurrentFrom") ;

			sqlB1.append("	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart between to_date('").append(dateNextBegin).append("','dd.mm.yyyy') and to_date('").append(dateEnd).append("','dd.mm.yyyy')") ;
			sqlB1.append("	 or slo.datestart = to_date('").append(dateBegin).append("','dd.mm.yyyy') and slo.entrancetime>=cast('").append(time_263).append(":00' as time)") ;
			sqlB1.append(" or slo.datestart = to_date('").append(dateNextEnd).append("','dd.mm.yyyy') and cast('").append(time_263).append("' as time)>slo.entrancetime) then slo.id else null end)") ;
			sqlB1.append("	as cnt1Entrance") ;

			sqlB1.append("	,count(distinct case when slo.prevmedcase_id is not null and (slo.datestart between to_date('").append(dateNextBegin).append("','dd.mm.yyyy') and to_date('").append(dateEnd).append("','dd.mm.yyyy')") ;
			sqlB1.append("	 or slo.datestart = to_date('").append(dateBegin).append("','dd.mm.yyyy') and slo.entrancetime>=cast('").append(time_263).append(":00' as time)") ;
			sqlB1.append("	or slo.datestart = to_date('").append(dateNextEnd).append("','dd.mm.yyyy') and cast('").append(time_263).append("' as time)>slo.entrancetime) then slo.id else null end)") ;
			sqlB1.append("	as cnt2TransferOutOtherDepartment") ;

			sqlB1.append("	,count(distinct case when  (slo.transferdate between to_date('").append(dateNextBegin).append("','dd.mm.yyyy') and to_date('").append(dateEnd).append("','dd.mm.yyyy')") ;
			sqlB1.append("	 or slo.transferdate = to_date('").append(dateBegin).append("','dd.mm.yyyy') and slo.transfertime>=cast('").append(time_263).append(":00' as time)") ;
			sqlB1.append("	or slo.transferdate = to_date('").append(dateNextEnd).append("','dd.mm.yyyy') and cast('").append(time_263).append("' as time)>slo.transfertime) then slo.id else null end)") ;
			sqlB1.append("	as cnt3TransferInOtherDepartment") ;

			sqlB1.append("	,count(distinct case when vhr.code!='11' and (slo.dateFinish between to_date('").append(dateNextBegin).append("','dd.mm.yyyy') and to_date('").append(dateEnd).append("','dd.mm.yyyy')") ;
			sqlB1.append("	 or slo.dateFinish = to_date('").append(dateBegin).append("','dd.mm.yyyy') and slo.dischargetime>=cast('").append(time_263).append(":00' as time)") ;
			sqlB1.append("	or slo.dateFinish = to_date('").append(dateNextEnd).append("','dd.mm.yyyy') and cast('").append(time_263).append("' as time)>slo.dischargetime) then slo.id else null end)") ;
			sqlB1.append("	as cnt4Finished") ;

			sqlB1.append("	,count(distinct case when vhr.code='11' and (slo.dateFinish between to_date('").append(dateNextBegin).append("','dd.mm.yyyy') and to_date('").append(dateEnd).append("','dd.mm.yyyy')") ;
			sqlB1.append("	 or slo.dateFinish = to_date('").append(dateBegin).append("','dd.mm.yyyy') and slo.dischargetime>=cast('").append(time_263).append(":00' as time)") ;
			sqlB1.append("	or slo.dateFinish = to_date('").append(dateNextEnd).append("','dd.mm.yyyy') and cast('").append(time_263).append("' as time)>slo.dischargetime) then slo.id else null end)") ;
			sqlB1.append("	as cnt5Death") ;

			sqlB1.append("	, count(distinct case when") ;
			sqlB1.append("			(") ;
			sqlB1.append("				slo.transferdate is null") ;
			sqlB1.append("				or slo.transferdate > to_date('").append(dateNextEnd).append("','dd.mm.yyyy')") ;
			sqlB1.append("				or slo.transferdate = to_date('").append(dateNextEnd).append("','dd.mm.yyyy') and slo.transfertime>=cast('").append(time_263).append("' as time)") ;
			sqlB1.append("			) and (") ;
			sqlB1.append("				slo.datefinish is null or") ;
			sqlB1.append("				slo.datefinish > to_date('").append(dateNextEnd).append("','dd.mm.yyyy')") ;
			sqlB1.append("				or slo.datefinish = to_date('").append(dateNextEnd).append("','dd.mm.yyyy') and slo.dischargetime>=cast('").append(time_263).append("' as time)") ;
			sqlB1.append("			)") ;
			sqlB1.append("		 then slo.id else null end") ;
			sqlB1.append("	)") ;
			sqlB1.append("	 as cnt6CurrentTo") ;

			sqlB1.append("	 from medcase slo") ;
			sqlB1.append("	 left join patient pat on pat.id=slo.patient_id") ;
			sqlB1.append(" left join vocsex vs on vs.id=pat.sex_id") ;
			sqlB1.append("	 left join mislpu lpu on lpu.id=slo.department_id") ;
			sqlB1.append("	 left join medcase sls on sls.id=slo.parent_id") ;
			sqlB1.append("	 left join vochospitalizationoutcome vho on vho.id=sls.outcome_id") ;
			sqlB1.append("	 left join vochospitalizationresult vhr on vhr.id=sls.result_id") ;
			sqlB1.append("	left join bedfund bf on bf.id=slo.bedfund_id") ;
			sqlB1.append("	left join vocbedtype vbt on vbt.id=bf.bedtype_id") ;
			sqlB1.append("	left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id") ;
			sqlB1.append("	left join vocservicestream vss on vss.id=bf.servicestream_id") ;
			sqlB1.append("	left join vochosptype vht on vht.id=sls.sourceHospType_id") ;
			sqlB1.append("	where ") ;
			sqlB1.append("	slo.dtype='DepartmentMedCase' ") ;
			sqlB1.append("	and (to_date('").append(dateNextEnd).append("','dd.mm.yyyy')>slo.datestart") ;
			sqlB1.append("	or to_date('").append(dateNextEnd).append("','dd.mm.yyyy')=slo.dateStart and cast('").append(time_263).append("' as time)>slo.entrancetime") ;
			sqlB1.append("	)") ;
			sqlB1.append("	and (slo.datefinish is null") ;
			sqlB1.append("	or") ;
			sqlB1.append("	slo.datefinish>to_date('").append(dateBegin).append("','dd.mm.yyyy')") ;
			sqlB1.append("	or to_date('").append(dateBegin).append("','dd.mm.yyyy')=slo.datefinish and slo.dischargetime>=cast('").append(time_263).append("' as time)") ;
			sqlB1.append("	)") ;
			sqlB1.append("	and (slo.transferdate is null ") ;
			sqlB1.append("	or slo.transferdate > to_date('").append(dateBegin).append("','dd.mm.yyyy')") ;
			sqlB1.append("	or to_date('").append(dateBegin).append("','dd.mm.yyyy')=slo.transferdate and slo.transfertime>=cast('").append(time_263).append("' as time)") ;
			sqlB1.append("	)") ;
			sqlB1.append("	  and vss.code='OBLIGATORYINSURANCE' and bf.id in (").append(obj[4]).append(")") ;
			sqlB1.append("	group by vbt.codeF") ;
			sqlB1.append("	order by vbt.codeF") ;
			List<Object[]> listB1 = theManager.createNativeQuery(sqlB1.toString())
					.setMaxResults(1).getResultList() ;
			Object[] objB1 = !listB1.isEmpty()? listB1.get(0):null ;
			//xmlDoc.newElement(zap, "IDCASE", AddressPointServiceBean.getStringValue(++i)) ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DATA",aDateFrom,true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"LPU",aLpu,true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"LPU_1",null,false,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"USL_OK",ConvertSql.parseLong(obj[1]),true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DET",ConvertSql.parseLong(obj[2]),true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PROFIL",obj[0],true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"COUNTP",objB1==null?0:(ConvertSql.parseLong(objB1[0])),true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"POSTP",objB1==null?0:(ConvertSql.parseLong(objB1[1])+ConvertSql.parseLong(objB1[2])),true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"VIBP",objB1==null?0:(ConvertSql.parseLong(objB1[3])+ConvertSql.parseLong(objB1[4])+ConvertSql.parseLong(objB1[5])),true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PLANP",obj==null?0:(ConvertSql.parseLong(obj[5])),true,"") ;
			Long freek = objB1==null?0:(ConvertSql.parseLong(obj[3])-ConvertSql.parseLong(objB1[6])
					-(obj==null?Long.valueOf(0):(ConvertSql.parseLong(obj[5])))) ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"FREEK",freek>Long.valueOf(0)?freek:0,true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"FREEM",0,true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"FREEW",0,true,"") ;
			XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"FREED",0,true,"") ;
    		/*String[] smoCodes = {"30002","30004"} ;
    		for (String smoCode:smoCodes) {
        		Element obsmo = xmlDoc.newElement(zap, "OBSMO", null) ;
        		XmlUtil.recordElementInDocumentXml(xmlDoc,obsmo,"SMO",smoCode,true,"") ;
        		XmlUtil.recordElementInDocumentXml(xmlDoc,obsmo,"SMOSL",0,true,"") ;
        		XmlUtil.recordElementInDocumentXml(xmlDoc,obsmo,"SMOKD",0,true,"") ;
    		}*/
		}
		XmlUtil.saveXmlDocument(xmlDoc,new File(workDir+"/"+filename+".xml")) ;
		if (workAddDir!=null) {
            XmlUtil.saveXmlDocument(xmlDoc,new File(workAddDir+"/"+filename+".xml")) ;
			chmodDir(workAddDir);
		}

		return filename+".xml";
	}

	/**Выставляем права на папку*/
	private void chmodDir(String aDir) {
		try{Runtime.getRuntime().exec("chmod -R 777 "+aDir);} catch (Exception e) {}
	}
	public void createNewDiary(String aTitle, String aText, String aUsername) {
		TemplateProtocol protocol = new TemplateProtocol() ;
		protocol.setText(aText) ;
		protocol.setTitle(aTitle) ;
		protocol.setUsername(aUsername) ;
		protocol.setDate(new java.sql.Date(new java.util.Date().getTime()));
		theManager.persist(protocol) ;
	}
	public void updateDataFromParameterConfig(Long aDepartment, boolean aIsLowerCase, String aIds, boolean aIsRemoveExist) {
		String[] obj = aIds.split(",") ;
		String aTableSql = "VocDocumentParameterConfig where department_id='"+aDepartment+"' and documentParameter_id " ;
		MisLpu department = theManager.find(MisLpu.class, aDepartment) ;
		for (int i = 0; i < obj.length; i++) {
			String jsId = obj[i];
			if (jsId!=null && !jsId.equals("") && !jsId.equals("0")) {

				Long jsonId=java.lang.Long.valueOf(jsId) ;

				String sql ="from "+aTableSql+" ='"+jsonId+"' " ;
				List<VocDocumentParameterConfig> count = theManager.createQuery(sql).setMaxResults(1).getResultList() ;
				VocDocumentParameterConfig vdpc ;
				if (count.isEmpty()) {
					VocDocumentParameter documentParameter = theManager.find(VocDocumentParameter.class, jsonId) ;
					vdpc = new VocDocumentParameterConfig() ;
					vdpc.setDepartment(department) ;
					vdpc.setDocumentParameter(documentParameter) ;
				} else {
					vdpc = count.get(0) ;
				}
				vdpc.setIsLowerCase(aIsLowerCase) ;
				theManager.persist(vdpc) ;
			}
		}
		if (aIsRemoveExist && aIds.length()>0 ) {
			String sql = "delete "+aTableSql+" not in ("+aIds+") " ;
			theManager.createNativeQuery(sql).executeUpdate();
		} else {
		}
	}
	public void removeDataFromParameterConfig(Long aDepartment, String aIds) {
		String aTableSql = "VocDocumentParameterConfig where department_id='"+aDepartment+"' and documentParameter_id " ;
		String sql = "delete from "+aTableSql+" in ("+aIds+") " ;
		theManager.createNativeQuery(sql).executeUpdate();
	}

	public void changeServiceStreamBySmo(Long aSmo,Long aServiceStream) {
		List<Object[]> list = theManager.createNativeQuery("select sls.dtype,count(distinct slo.id) from medcase sls" +
				" left join MedCase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'" +
				" where sls.id="+aSmo+" group by sls.id,sls.dtype").getResultList() ;
		if (!list.isEmpty()) {
			Object[] obj =list.get(0) ;
			if (obj[0]!=null) {
				String dtype = obj[0].toString() ;
				if (dtype.equals("HospitalMedCase")) {
					List<Object[]> listBedFund = theManager.createNativeQuery("select slo.id as sloid,bfNew.id as bfNewid from MedCase slo"
							+" left join BedFund bf on bf.id=slo.bedFund_id"
							+" left join BedFund bfNew on bfNew.lpu_id=bf.lpu_id"
							+" where slo.parent_id='"+aSmo+"' and slo.dtype='DepartmentMedCase'"
							+" and bfNew.bedSubType_id = bf.bedSubType_id"
							+" and bfNew.bedType_id = bf.bedType_id"
							+" and bfNew.serviceStream_id = '"+aServiceStream+"' and slo.dateStart between bfNew.dateStart and coalesce(bfNew.dateFinish,CURRENT_DATE)")
							.getResultList() ;
					Long cntSlo=ConvertSql.parseLong(obj[1]) ;
					if (cntSlo.intValue()==listBedFund.size()) {
						for (Object[] slo:listBedFund) {
							theManager.createNativeQuery("update MedCase set serviceStream_id='"+aServiceStream+"',bedFund_id='"+slo[1]+"' where id='"+slo[0]+"'").executeUpdate() ;
							theManager.createNativeQuery("update SurgicalOperation set serviceStream_id='"+aServiceStream+"' where medCase_id='"+slo[0]+"'").executeUpdate() ;
						}
						theManager.createNativeQuery("update MedCase set serviceStream_id='"+aServiceStream+"' where id='"+aSmo+"'").executeUpdate() ;
						theManager.createNativeQuery("update SurgicalOperation set serviceStream_id='"+aServiceStream+"' where medCase_id='"+aSmo+"'").executeUpdate() ;
					} else {
						throw new IllegalArgumentException("По данному потоку обслуживания не во всех отделениях, в которых производилось лечение, заведен коечный фонд");
					}
				} else if (dtype.equals("DepartmentMedCase")) { // не будет случаться, убрать
					List<Object[]> listBedFund = theManager.createNativeQuery("select slo.id as sloid,bfNew.id as bfNewid from MedCase slo"
							+" left join BedFund bf on bf.id=slo.bedFund_id"
							+" left join BedFund bfNew on bfNew.lpu_id=bf.lpu_id"
							+" where slo.id='"+aSmo+"' and slo.dtype='DepartmentMedCase'"
							+" and bfNew.bedSubType_id = bf.bedSubType_id"
							+" and bfNew.bedType_id = bf.bedType_id"
							+" and bfNew.serviceStream_id = '"+aServiceStream+"' and slo.dateStart between bfNew.dateStart and coalesce(bfNew.dateFinish,CURRENT_DATE)")
							.getResultList() ;
					Object[] slo=listBedFund.get(0) ;
					if (listBedFund.size()==1) {
						theManager.createNativeQuery("update MedCase set serviceStream_id='"+aServiceStream+"',bedFund_id='"+slo[1]+"' where id='"+aSmo+"'").executeUpdate() ;
						theManager.createNativeQuery("update SurgicalOperation set serviceStream_id='"+aServiceStream+"' where medCase_id='"+aSmo+"'").executeUpdate() ;
					} else {
						throw new IllegalArgumentException("По данному потоку обслуживания в отделение не заведен коечный фонд");
					}
				} else if (dtype.equals("Visit")) {
					theManager.createNativeQuery("update MedCase set serviceStream_id='"+aServiceStream+"' where id='"+aSmo+"'").executeUpdate() ;
				} else if (dtype.equals("PolyclinicMedCase")) {
					theManager.createNativeQuery("update MedCase set serviceStream_id='"+aServiceStream+"' where parent_id='"+aSmo+"' and (dtype='Visit' or dtype='ShortMedCase')").executeUpdate() ;
					//Milamesher update spo after visits
					theManager.createNativeQuery("update MedCase set serviceStream_id='"+aServiceStream+"' where id='"+aSmo+"'").executeUpdate() ;
				}
			}
		}
	}
	public void unionSloWithNextSlo(Long aSlo) {
		List<Object[]> list = theManager.createNativeQuery("select  "
				+"case when sloNext1.department_id is not null and"
				+" sloNext1.department_id=slo.department_id then '1' else null end equalsDep"
				+" ,sloNext.id as sloNext,sloNext1.id as sloNext1,sloNext2.id as sloNext2"
				+" ,sloNext.dateFinish as sloNextDateFinish,sloNext.dischargeTime as sloNextDischargeTime"
				+" ,sloNext.transferDate as sloNextTransferDate,sloNext.transferTime as sloNextTransferTime"
				+" ,sloNext1.dateFinish as sloNext1DateFinish,sloNext1.dischargeTime as sloNext1DischargeTime"
				+" ,sloNext1.transferDate as sloNext1TransferDate,sloNext1.transferTime as sloNext1TransferTime"

				+" from medcase slo"
				+" left join MedCase sloNext on sloNext.prevMedCase_id=slo.id"
				+" left join MedCase sloNext1 on sloNext1.prevMedCase_id=sloNext.id"
				+" left join MedCase sloNext2 on sloNext2.prevMedCase_id=sloNext1.id"
				+" where slo.id='"+aSlo+"'")
				.getResultList() ;
		if (!list.isEmpty()) {
			Object[] obj = list.get(0) ;
			if (obj[1]!=null) {
				if (obj[0]!=null) { // если КАРДИО -  невр - КАРДИО
					// Отд next1=current (объединять 2 отделения)
					theManager.createNativeQuery("update assessmentCard cb set medcase_id='"+aSlo+"' where cb.medCase_id='"+obj[1]+"' or medcase_id="+obj[2]).executeUpdate() ;
					theManager.createNativeQuery("update childBirth cb set medcase_id='"+aSlo+"' where cb.medCase_id='"+obj[1]+"' or medcase_id="+obj[2]).executeUpdate() ;
					theManager.createNativeQuery("update newBorn cb set medcase_id='"+aSlo+"' where cb.medCase_id='"+obj[1]+"' or medcase_id="+obj[2]).executeUpdate() ;
					theManager.createNativeQuery("update medcase  set parent_id='"+aSlo+"' where parent_id='"+obj[1]+"' or parent_id="+obj[2]).executeUpdate() ;

					//Закрытие диет и Режимов из объединяемого.Копирование назначений из листа назначения объединяемого СЛО и затем его удаление.
					theManager.createNativeQuery("update prescription" +
							" set planEndDate=planStartDate, planEndTime=planStartTime" +
							" where prescriptionlist_id in (select id from prescriptionlist where medcase_id = "+obj[1]+" or medcase_id="+aSlo+") and (dtype='DietPrescription' or dtype='ModePrescription')").executeUpdate();
					theManager.createNativeQuery("update prescription " +
							" set prescriptionlist_id = (select id from prescriptionlist where medcase_id = "+aSlo+") " +
							" where prescriptionlist_id in (select id from prescriptionlist where medcase_id = '"+obj[2]+"' or medcase_id="+obj[1]+")").executeUpdate();
					theManager.createNativeQuery("delete from prescriptionlist where medcase_id ="+obj[2]+" or medcase_id="+obj[1]).executeUpdate();
					theManager.createNativeQuery("update screeningcardiac cd set medcase_id='"+aSlo+"' where cd.medCase_id='"+obj[1]+"' or medcase_id="+obj[2]).executeUpdate() ;
					theManager.createNativeQuery("update diary d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[1]+"' or medcase_id="+obj[2]).executeUpdate() ;
					theManager.createNativeQuery("update diagnosis d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[1]+"' or medcase_id="+obj[2]).executeUpdate() ;
					theManager.createNativeQuery("update SurgicalOperation d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[1]+"' or medcase_id="+obj[2]).executeUpdate() ;
					theManager.createNativeQuery("update ClinicExpertCard d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[1]+"' or medcase_id="+obj[2]).executeUpdate() ;
					theManager.createNativeQuery("update transfusion d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[1]+"' or medcase_id="+obj[2]).executeUpdate() ;
					theManager.createNativeQuery("update hitechmedicalcase d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[1]+"' or medcase_id="+obj[2]).executeUpdate() ;
					theManager.createNativeQuery("update robsonclass d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[1]+"' or medcase_id="+obj[2]).executeUpdate() ;
					theManager.createNativeQuery("update calculationsresult cr set departmentmedcase_id='"+aSlo+"' where cr.departmentmedcase_id='"+obj[1]+"' or departmentmedcase_id="+obj[2]).executeUpdate() ;
					theManager.createNativeQuery("update medcase set dateFinish=(select dateFinish from medcase where id='"+obj[2]+"') "
							+" ,transferDate=(select transferDate from medcase where id='"+obj[2]+"')"
							+" ,transferTime=(select transferTime from medcase where id='"+obj[2]+"')"
							+" ,dischargeTime=(select dischargeTime from medcase where id='"+obj[2]+"')"
							+" ,transferDepartment_id=(select transferDepartment_id from medcase where id='"+obj[2]+"')"
							+" ,targetHospType_id=(select targetHospType_id from medcase where id='"+obj[2]+"')"
							+" where id='"+aSlo+"'").executeUpdate() ;
					if (obj[3]!=null) {
						theManager.createNativeQuery("update MedCase set prevMedCase_id='"+aSlo+"' where id='"+obj[3]+"'").executeUpdate() ;
					}
					theManager.createNativeQuery("delete from medcase m where m.id='"+obj[2]+"'").executeUpdate() ;
					theManager.createNativeQuery("delete from medcase m where m.id='"+obj[1]+"'").executeUpdate() ;
				} else {
					theManager.createNativeQuery("update robsonclass d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[1]+"'").executeUpdate() ;
					theManager.createNativeQuery("update assessmentCard cb set medcase_id='"+aSlo+"' where cb.medCase_id='"+obj[1]+"'").executeUpdate() ;
					theManager.createNativeQuery("update childBirth cb set medcase_id='"+aSlo+"' where cb.medCase_id='"+obj[1]+"' and '1'=(select case when dep.isMaternityWard='1' then '1' else '0' end from medcase slo left join mislpu dep on dep.id=slo.department_id where slo.id='"+aSlo+"')").executeUpdate() ;
					theManager.createNativeQuery("update newBorn nb    set medcase_id='"+aSlo+"' where nb.medCase_id='"+obj[1]+"' and '1'=(select case when dep.isMaternityWard='1' then '1' else '0' end from medcase slo left join mislpu dep on dep.id=slo.department_id where slo.id='"+aSlo+"')").executeUpdate() ;
					theManager.createNativeQuery("update medcase  set parent_id='"+aSlo+"' where parent_id='"+obj[1]+"'").executeUpdate() ;

					theManager.createNativeQuery("update prescription " +
							"set planEndDate=planStartDate, planEndTime=planStartTime " +
							"where (dtype='DietPrescription' or dtype='ModePrescription') " +
							"and prescriptionlist_id = (select id from prescriptionlist where medcase_id = '"+aSlo+"')").executeUpdate();
					theManager.createNativeQuery("update prescription " +
							"set prescriptionlist_id = (select id from prescriptionlist where medcase_id = '"+aSlo+"') " +
							"where prescriptionlist_id in (select id from prescriptionlist where medcase_id = '"+obj[1]+"')").executeUpdate();
					theManager.createNativeQuery("delete from prescriptionlist where medcase_id ='"+obj[1]+"'").executeUpdate();


					theManager.createNativeQuery("update screeningcardiac cd set medcase_id='"+aSlo+"' where cd.medCase_id='"+obj[1]+"'").executeUpdate() ;
					theManager.createNativeQuery("update diary d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[1]+"'").executeUpdate() ;
					theManager.createNativeQuery("update diagnosis d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[1]+"'").executeUpdate() ;
					theManager.createNativeQuery("update SurgicalOperation d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[1]+"'").executeUpdate() ;
					theManager.createNativeQuery("update ClinicExpertCard d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[1]+"'").executeUpdate() ;
					theManager.createNativeQuery("update transfusion d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[1]+"'").executeUpdate() ;
					theManager.createNativeQuery("update hitechmedicalcase d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[1]+"'").executeUpdate() ;
					theManager.createNativeQuery("update medcase set dateFinish=(select dateFinish from medcase where id='"+obj[1]+"') "
							+" ,transferDate=(select transferDate from medcase where id='"+obj[1]+"')"
							+" ,transferTime=(select transferTime from medcase where id='"+obj[1]+"')"
							+" ,dischargeTime=(select dischargeTime from medcase where id='"+obj[1]+"')"
							+" ,transferDepartment_id=(select transferDepartment_id from medcase where id='"+obj[1]+"')"
							+" ,targetHospType_id=(select targetHospType_id from medcase where id='"+obj[1]+"')"
							+" where id='"+aSlo+"'").executeUpdate() ;
					if (obj[2]!=null) {
						theManager.createNativeQuery("update MedCase set prevMedCase_id='"+aSlo+"' where id='"+obj[2]+"'").executeUpdate() ;
					}
					if (!theManager.createNativeQuery("select id from newborn where medcase_id='"+obj[1]+"'").getResultList().isEmpty()
                            || !theManager.createNativeQuery("select id from childbirth where medcase_id='"+obj[1]+"'").getResultList().isEmpty()) {
						throw new IllegalArgumentException("Невозможность объединить СЛО, т.к. имеются данные по родам!");
					}
					theManager.createNativeQuery("update calculationsresult cr set departmentmedcase_id='"+aSlo+"' where cr.departmentmedcase_id='"+obj[1]+"'").executeUpdate() ;
					theManager.createNativeQuery("delete from medcase m where m.id='"+obj[1]+"'").executeUpdate() ;
				}
			} else {
				throw new IllegalArgumentException("Нет след. СЛО");
			}
		}
	}
	public void deniedHospitalizatingSls(Long aMedCaseId, Long aDeniedHospitalizating) {
		theManager.createNativeQuery("update diary d set medcase_id='"+aMedCaseId+"' where d.medCase_id in (select m.id from medcase m where m.parent_id='"+aMedCaseId+"')").executeUpdate() ;
		theManager.createNativeQuery("update diagnosis d set medcase_id='"+aMedCaseId+"' where d.medCase_id in (select m.id from medcase m where m.parent_id='"+aMedCaseId+"')").executeUpdate() ;
		theManager.createNativeQuery("update SurgicalOperation d set medcase_id='"+aMedCaseId+"' where d.medCase_id in (select m.id from medcase m where m.parent_id='"+aMedCaseId+"')").executeUpdate() ;
		theManager.createNativeQuery("update ClinicExpertCard d set medcase_id='"+aMedCaseId+"' where d.medCase_id in (select m.id from medcase m where m.parent_id='"+aMedCaseId+"')").executeUpdate() ;
		theManager.createNativeQuery("update PrescriptionList d set medcase_id='"+aMedCaseId+"' where d.medCase_id in (select m.id from medcase m where m.parent_id='"+aMedCaseId+"')").executeUpdate() ;
		theManager.createNativeQuery("delete from medcase m where m.parent_id='"+aMedCaseId+"' and m.dtype='DepartmentMedCase'").executeUpdate() ;
		theManager.createNativeQuery("update medcase set deniedHospitalizating_id='"+aDeniedHospitalizating+"',ambulanceTreatment='1' where id='"+aMedCaseId+"'").executeUpdate() ;
		HospitalMedCase medCase = theManager.find(HospitalMedCase.class, aMedCaseId) ;
		StatisticStubStac.removeStatCardNumber(theManager, theContext,medCase);
	}
	public void preRecordDischarge(Long aMedCaseId, String aDischargeEpicrisis) {

	//	HospitalMedCase sls = theManager.find(HospitalMedCase.class, aMedCaseId) ;
	//	if (sls.getDateFinish()!=null) throw new IllegalArgumentException("На выписанных пациентов, предварительная выписка не оформляется!!!!"); //Проверка перенесена в *ServiceJs
		saveDischargeEpicrisis(aMedCaseId,aDischargeEpicrisis,theManager);
	}
	public void updateDischargeDateByInformationBesk(String aIds, String aDate) throws ParseException {
		String[] ids = aIds.split(",") ;
		//Date date = DateFormat.parseSqlDate(aDate) ;

		for (String id :ids) {
			Object cnt = theManager.createNativeQuery("select count(*) from medcase where id='"+id+"' and dateStart<=to_date('"+aDate+"','dd.mm.yyyy') and dischargeTime is null and deniedHospitalizating_id is null")
					//.setParameter("dat", date)
					.getSingleResult() ;
			Long cntL = ConvertSql.parseLong(cnt) ;
			if (cntL>Long.valueOf(0)) {
				theManager.createNativeQuery("update MedCase set dateFinish=to_date('"+aDate+"','dd.mm.yyyy') where id='"+id+"' and dtype='HospitalMedCase' and dischargeTime is null")
						//.setParameter("dat", date)
						.executeUpdate() ;
			} else {
				List<Object[]> list = theManager.createNativeQuery("select p.lastname||' '||p.firstname||' '||coalesce(p.middlename)||' '||to_char(p.birthday,'dd.mm.yyyy'),ss.code,case when sls.deniedHospitalizating_id is not null then 'при отказе от госпитализации дата выписки не ставится' when sls.dischargeTime is not null then 'Изменение даты выписки у оформленных историй болезни производится через выписку' when sls.dateStart>to_date('"+aDate+"','dd.mm.yyyy') then 'Дата выписки должна быть больше, чем дата поступления' else '' end from medcase sls left join patient p on p.id=sls.patient_id left join statisticstub ss on ss.id=sls.statisticstub_id where sls.id='"+id+"'")
						//.setParameter("dat", date)
						.getResultList() ;
				if (!list.isEmpty()) {
					Object[] objs = list.get(0) ;
					throw new IllegalArgumentException(
							new StringBuilder().append("Пациент:").append(objs[0])
									.append(objs[1]!=null?" стат.карта №"+objs[1]:"")
									.append(" ОШИБКА:").append(objs[2]).toString()
					);
				}
			}
		}
		//return "" ;
	}
	public String addressInfo(EntityManager aManager,Long aAddressId, Object[] aAddress) {
		StringBuilder sb = new StringBuilder();
		String fullname = aAddress[1].toString().trim() ;

		//Address a = aAddress ;
		//Long id = a.getId() ;
		if (aAddress[1]!=null && !fullname.equals("")) return fullname;

		sb.insert(0, aAddress[2]) ;
		if (aAddress[3]!=null) {
			String shortName = aAddress[3]+" " ;
			sb.insert(0,  shortName) ;
		}

		//long oldId = a.getId() ;
		////a = a.getParent() ;

		//sb.insert(0, aAddress[2]) ;
		if(aAddress[4]!=null) {
			Long id1 = ConvertSql.parseLong(aAddress[4]) ;
			List<Object[]> list = theManager.createNativeQuery("select a.addressid,a.fullname,a.name,att.shortName,a.parent_addressid from address2 a left join AddressType att on att.id=a.type_id where a.addressid="+id1+" order by a.addressid")
					.setMaxResults(1)
					.getResultList() ;
			if (!list.isEmpty()) {

				sb.insert(0, ", ") ;
				sb.insert(0, addressInfo(aManager,id1,list.get(0))) ;
			}
		}
		aManager.createNativeQuery("update Address2 set fullname='"+sb.toString().trim()+"' where addressid="+aAddressId).executeUpdate() ;
		return sb.toString().trim() ;
	}

	public void addressClear() {
		theManager.createNativeQuery("update Address2 set fullname=null").executeUpdate() ;
	}
	public long addressUpdate(long id) {
		List<Object[]> list ;
		list = theManager.createNativeQuery("select a.addressid,a.fullname,a.name,att.shortName,a.parent_addressid from address2 a left join AddressType att on att.id=a.type_id where a.addressid>"+id+" and a.fullname is null order by a.addressid")
				.setMaxResults(450)
				.getResultList() ;
		if (!list.isEmpty()) {
			for (Object[] adr:list) {

				//Address adr = list.get(i) ;
				id = ConvertSql.parseLong(adr[0]);
				addressInfo(theManager,id,adr) ;
				//adr.setFullname() ;
				//aCtx.manager.persist(adr) ;

				//throw id ;
			}
			//list.clear() ;
		} else {
			id=-1 ;
		}
		return id ;
	}

	public String getOperationsText(Long aPatient, String aDateStart
			,String aDateFinish) {
		if (aDateFinish==null || aDateFinish.equals("")) {
			aDateFinish = "CURRENT_DATE";
		} else {
			aDateFinish = new StringBuilder().append("to_date('").append(aDateFinish).append("','dd.mm.yyyy')").toString();
		}
		StringBuilder sql = new StringBuilder() ;
		sql.append("select to_char(operationDate,'DD.MM.YYYY') as operDate1,cast(operationTime as varchar(5)) as timeFrom,cast(operationTimeTo as varchar(5)) as timeTo,vo.name as voname from SurgicalOperation so left join MedService vo on vo.id=so.operation_id where so.patient_id='")
				.append(aPatient)
				.append("' and so.operationDate between to_date('").append(aDateStart).append("','dd.mm.yyyy') and ").append(aDateFinish).append(" order by so.operationDate") ;
		List<Object[]> opers = theManager.createNativeQuery(sql.toString()).getResultList() ;
		StringBuilder res = new StringBuilder() ;
		for (Object[] obj :opers) {
			if (res.length()>0) res.append("; ") ;
			res.append("").append(obj[3]).append(" ").append(obj[0]).append(" ").append(obj[1]).append("-").append(obj[2]) ;
		}
		return res.toString() ;
	}

	public String getTypeDiagByAccoucheur() {
		StringBuilder ret= new StringBuilder() ;
		List<VocPrimaryDiagnosis> prDiag = theManager.createQuery("from VocPrimaryDiagnosis where code=1").getResultList();
		List<VocAcuityDiagnosis> actDiag = theManager.createQuery("from VocAcuityDiagnosis where code=1 or omcCode=1").getResultList();
		List<VocDiagnosisRegistrationType> regTypeDiag = theManager.createQuery("from VocDiagnosisRegistrationType where code=4").getResultList();
		if (!prDiag.isEmpty()) {
			VocPrimaryDiagnosis pr = prDiag.get(0) ;
			ret.append(pr.getId()).append("#").append(pr.getName()).append("#") ;
		} else {
			ret.append("##") ;
		}
		if (!actDiag.isEmpty()) {
			VocAcuityDiagnosis act = actDiag.get(0) ;
			ret.append(act.getId()).append("#").append(act.getName()).append("#") ;
		} else {
			ret.append("##") ;
		}
		if (!regTypeDiag.isEmpty()) {
			VocDiagnosisRegistrationType regType = regTypeDiag.get(0) ;
			ret.append(regType.getId()).append("#").append(regType.getName()) ;
		} else {
			ret.append("#") ;
		}
		return ret.toString();
	}

	public String findDoubleServiceByPatient(Long aMedService, Long aPatient, Long aService, String aDate) throws ParseException {
		StringBuilder sql = new StringBuilder() ;
		Date date=DateFormat.parseSqlDate(aDate) ;
		sql.append("select smc.id,to_char(smc.dateExecute,'dd.mm.yyyy') as dateexecute,smc.timeExecute,vss.name,'Оказана в '|| case when p.DTYPE='DepartmentMedCase' then ' отделении '||d.name when p.DTYPE='HospitalMedCase' then 'приемном отделении ' else 'поликлинике' end from medcase as smc ")
				.append(" left join medcase as p on p.id=smc.parent_id ")
				.append(" left join mislpu as d on d.id=p.department_id ")
				.append(" left join vocservicestream as vss on vss.id=smc.servicestream_id")
				.append(" where smc.patient_id=:pat and smc.DTYPE='ServiceMedCase' and smc.medService_id=:usl and smc.dateExecute=:dat") ;
		if (aMedService!=null && aMedService>0) {
			sql.append(" and smc.id!='").append(aMedService).append("'") ;
		}
		List<Object[]> doubles = theManager.createNativeQuery(
				sql.toString())
				.setParameter("pat", aPatient)
				.setParameter("usl", aService)
				.setParameter("dat", date)
				.getResultList() ;
		if (!doubles.isEmpty()) {
			StringBuilder ret = new StringBuilder() ;
			ret.append("<br/><ol>") ;
			for (Object[] res:doubles) {
				ret.append("<li>")
						.append("<a href='entitySubclassView-mis_medCase.do?id=").append(res[0]).append("'>")
						.append(res[1]).append(" ").append(res[2]).append(" ").append(res[3]).append(" ").append(res[4])
						.append("</a>")
						.append("</li>") ;
			}
			ret.append("</ol><br/>") ;
			return ret.toString() ;
		} else {
			return null ;
		}


	}
	public String findDoubleOperationByPatient(Long aSurOperation, Long aParentMedCase, Long aOperation, String aDate) throws ParseException {
		StringBuilder sql = new StringBuilder() ;
		Date date=DateFormat.parseSqlDate(aDate) ;
		sql.append("select so.id,to_char(so.operationDate,'dd.mm.yyyy'),so.operationTime,to_char(so.operationDateTo,'dd.mm.yyyy'),so.operationTimeTo,'Зарегистрирована в '|| case when p.DTYPE='DepartmentMedCase' then ' отделении '||d.name when p.DTYPE='HospitalMedCase' then 'приемном отделении ' else 'поликлинике' end ")
				.append(" from medcase as mc")
				.append(" left join SurgicalOperation as so on so.patient_id=mc.patient_id")
				.append(" left join medcase as p on p.id=so.medcase_id ")
				.append(" left join mislpu as d on d.id=p.department_id ")
//    			.append(" left join vocservicestream as vss on vss.id=so.servicestream_id")
				.append(" where mc.id=:mcid and so.medService_id=:usl and so.operationDate=:dat") ;
		if (aSurOperation!=null && aSurOperation>0) {
			sql.append(" and so.id!='").append(aSurOperation).append("'") ;
		}

		List<Object[]> doubles = theManager.createNativeQuery(
				sql.toString())
				.setParameter("mcid", aParentMedCase)
				.setParameter("usl", aOperation)
				.setParameter("dat", date)
				.getResultList() ;

		if (!doubles.isEmpty()) {
			StringBuilder ret = new StringBuilder() ;
			ret.append("<br/><ol>") ;
			for (Object[] res:doubles) {
				ret.append("<li>")
						.append("<a href='entityView-stac_surOperation.do?id=").append(res[0]).append("'>")
						.append(res[1]).append(" ").append(res[2]).append("-").append(res[3]).append(" ").append(res[4]).append(" ").append(res[5])
						.append("</a>")
						.append("</li>") ;
			}
			ret.append("</ol><br/>") ;
			return ret.toString() ;
		} else {
			return null ;
		}
	}
	public String deleteDataDischarge(Long aMedCaseId) {
		StringBuilder sql = new StringBuilder() ;
		sql.append("update MedCase set dischargeTime=null,dateFinish=null")
				.append(" where (id=:idMC and DTYPE='HospitalMedCase')")
				.append(" or (parent_id=:idMC and DTYPE='DepartmentMedCase' and dateFinish is not null)");
		int result = theManager.createNativeQuery(sql.toString()).setParameter("idMC", aMedCaseId).executeUpdate() ;
		return "Запрос выполнен: "+result ;
	}

	public List<HospitalMedCaseForm> findSlsByStatCard(String aNumber) {
		StringBuilder query = new StringBuilder() ;
		query.append("from HospitalMedCase c")
				.append(" where statisticStub.code like :number order by dateStart");
		Query query2 = theManager.createQuery(query.toString()) ;
		query2.setParameter("number", "%"+aNumber+"%") ;
		return createHospitalList(query2);
	}
	public void setPatientByExternalMedservice(String aNumberDoc, String aOrderDate, String aPatient) {
		theManager.createNativeQuery("update Document set patient_id='"+aPatient+"' where NumberDoc='"+aNumberDoc+"' and OrderDate=to_date('"+aOrderDate+"','dd.mm.yyyy')").executeUpdate() ;
	}

	/**
	 * Есть ли открытый случай лечения в отделении
	 * @param aIdSls
	 * @return
	 */
	public String isOpenningSlo(long aIdSls) {
		StringBuilder sql = new StringBuilder() ;
		sql.append("select mc.id,ml.name from MedCase as mc")
				.append(" left join MisLpu as ml on ml.id=mc.department_id")
				.append( " where mc.parent_id=:idsls and mc.DTYPE='DepartmentMedCase' and mc.dateFinish is null and mc.transferDate is null") ;
		List<Object[]> list = theManager.createNativeQuery(sql.toString()).setParameter("idsls", aIdSls).getResultList() ;
		if (!list.isEmpty()) {
			StringBuilder ret = new StringBuilder() ;
			Object[] row = list.get(0) ;
			ret.append(" СЛО №").append(row[0]).append(" отделение: ").append(row[1]) ;
			return ret.toString() ;
		}
		return "" ;

	}
	public Long getPatient(long aIdSsl) {
		HospitalMedCase hospital = theManager.find(HospitalMedCase.class, aIdSsl) ;
		if(hospital==null) throw new IllegalArgumentException("Нет стационарного случая лечения с ИД "+aIdSsl) ;
		return hospital.getPatient().getId() ;
	}

	public String getChangeStatCardNumber(Long aMedCase, String aNewStatCardNumber, boolean aAlwaysCreate){
		HospitalMedCase hospital = theManager.find(HospitalMedCase.class,aMedCase);
		try {
			if (!aAlwaysCreate) {
				if (hospital.getDeniedHospitalizating()!=null) {
					throw new IllegalArgumentException("Нельзя изменить номер стат.карты при отказе госпитализации");
				}
			}
			StatisticStubStac.changeStatCardNumber(aMedCase, aNewStatCardNumber, theManager, theContext);
		} catch(Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return hospital.getStatCardNumber();
	}

	public Collection<MedPolicyForm> listPolicies(Long aMedCase) {
		HospitalMedCase hospital = theManager.find(HospitalMedCase.class,aMedCase);
		//List<MedCaseMedPolicy> listPolicies = hospital.getPolicies() ;
		List<MedPolicy> listPolicies =new ArrayList<>() ;
		List<MedCaseMedPolicy> hospmp = theManager.createQuery("from MedCaseMedPolicy where medCase=:mc").setParameter("mc", hospital).getResultList() ;
		for (MedCaseMedPolicy mp : hospmp) {
			listPolicies.add(mp.getPolicies()) ;
		}
		return convert(listPolicies);
	}

	public Collection<MedPolicyForm> listPoliciesToAdd(Long aMedCase) {
		HospitalMedCase hospital = theManager.find(HospitalMedCase.class,aMedCase);
		List<Object[]> listPolicies = theManager.createNativeQuery("select p.id,count(case when mp.medCase_id='"
				+aMedCase
				+"' then 1 else null end) from MedPolicy p left join MedCase_MedPolicy mp on p.id=mp.policies_id left join MedCase m on m.id=mp.medCase_id where p.patient_id='"
				+hospital.getPatient().getId()+"' group by p.id")
				.getResultList();
		//List<MedPolicy> allPolicies = theManager.createQuery("from MedPolicy where patient_id=:pat").setParameter("pat", hospital.getPatient().getId()).getResultList();
		List<MedPolicy> allPolicies = new ArrayList<>() ;
		for (Object[] obj:listPolicies) {
			Long cnt = ConvertSql.parseLong(obj[1]) ;
			if (cnt==null || cnt.equals(Long.valueOf(0))) {
				Long pId = ConvertSql.parseLong(obj[0]) ;
				MedPolicy p = theManager.find(MedPolicy.class, pId) ;
				allPolicies.add(p) ;
			}
			//allPolicies.remove(pol);
		}
		return convert(allPolicies);
	}

	private static Collection<MedPolicyForm> convert(Collection<MedPolicy> aPolicies) {
		LinkedList<MedPolicyForm> list = new LinkedList<>();
		for (MedPolicy policy:aPolicies) {
			MedPolicyForm frm = new MedPolicyForm() ;
			frm.setId(policy.getId());
			frm.setActualDateFrom(DateFormat.formatToDate(policy.getActualDateFrom()));
			frm.setActualDateTo(DateFormat.formatToDate(policy.getActualDateTo()));
			frm.setText(policy.getText());
			list.add(frm);
		}
		return list;

	}

	public void addPolicies(long aMedCaseId, long[] aPolicies) {
		HospitalMedCase hospital = theManager.find(HospitalMedCase.class,aMedCaseId);
		//List<MedCaseMedPolicy> listPolicies = hospital.getPolicies() ;
		for (long policyId: aPolicies) {
			if (!checkExistsAttachedPolicy(aMedCaseId, policyId)) {
				MedCaseMedPolicy mp = new MedCaseMedPolicy() ;
				mp.setMedCase(hospital) ;
				MedPolicy p = theManager.find(MedPolicy.class, policyId) ;
				mp.setPolicies(p) ;
				theManager.persist(mp) ;
			}
		}
		theManager.persist(hospital) ;
		//theManager.refresh(hospital);
	}
	private boolean checkExistsAttachedPolicy(long aMedCaseId,long aPolicy) {
		StringBuilder sql = new StringBuilder() ;
		sql.append(" select count(*) from MedCase_MedPolicy where medCase_id='")
				.append(aMedCaseId)
				.append("' and policies_id='").append(aPolicy).append("'") ;
		Object res= theManager.createNativeQuery(sql.toString()).getSingleResult() ;
		return ConvertSql.parseLong(res)>0L;
	}

	public void removePolicies(long aMedCaseId, long[] aPolicies) {
		//HospitalMedCase hospital = theManager.find(HospitalMedCase.class,aMedCaseId);
		//List<MedCaseMedPolicy> listPolicies = hospital.getPolicies() ;
		for (long policyId:aPolicies) {
			//MedPolicy policy= theManager.find(MedPolicy.class, policyId);
			//listPolicies.remove(policy) ;
			theManager.createNativeQuery("delete from MedCase_MedPolicy where medCase_id='"+
					aMedCaseId+"' and policies_id='"+policyId+"'").executeUpdate();
		}

		//theManager.persist(hospital);
		//theManager.refresh(hospital);

	}

	public String getTemperatureCurve(long aMedCaseId) {
		List<TemperatureCurve> list = theManager.createQuery("from TemperatureCurve where medCase_id=:medCase").setParameter("medCase", aMedCaseId).getResultList() ;

		StringBuilder json = new StringBuilder() ;
		json.append("{\"childs\":[") ;
		boolean isFirst = true ;
		for (TemperatureCurve curve:list) {
			if (!isFirst) {
				json.append(",") ;
				//isFirst =  ;
			} else {
				isFirst=false ;
			}
			json.append("{");
			if (curve.getTakingDate()!=null) {
				SimpleDateFormat FORMAT_1 = new SimpleDateFormat("yyyy/MM/dd");
				String date = FORMAT_1.format(curve.getTakingDate().getTime()) ;
				json.append("\"date\":")
						.append("\"")
						.append(date)
						.append(" ") ;
				if (curve.getDayTime()!=null && curve.getDayTime().getCode()!=null && curve.getDayTime().getCode().equals("2")) {
					json.append("20") ;
				} else {
					json.append("8") ;
				}
				json.append(":00")
						.append("\",") ;


			}
			json.append("\"id\":").append(curve.getId()).append(",") ;
			json.append("\"pulse\":")
					.append(curve.getPulse())
					.append(",");
			json.append("\"bloodPressureDown\":")
					.append(curve.getBloodPressureDown())
					.append(",");
			json.append("\"bloodPressureUp\":")
					.append(curve.getBloodPressureUp())
					.append(",");
			json.append("\"weight\":")
					.append(curve.getWeight())
					.append(",");
			json.append("\"respirationRate\":")
					.append(curve.getRespirationRate())
					.append(",");
			json.append("\"degree\":")
					.append(curve.getDegree())
					.append("}");
		}
		json.append("]}");
		return json.toString();
	}

	public List<IEntityForm> listAll(Long aParentId) throws EntityFormException {
		//Collection<HospitalMedCase> results = null ;
		//Patient patient = theManager.find(Patient.class, aParentId) ;
		StringBuilder query = new StringBuilder() ;
		query.append("select sls.id as f0slsid,case when sls.dtype='ExtHospitalMedCase' then sls.dtype else null end as f1isexthosp");
		query.append(",to_char(sls.dateStart,'dd.mm.yyyy') as f2dateStart,to_char(sls.dateFinish,'dd.mm.yyyy') as f3dateFinish") ;
		query.append(" ,vdh.id as f4vhdid,sls.username as f5slsusername,case when sls.emergency='1' then 'да' else null end as f6emergency") ;
		query.append(" ,coalesce(ss.code,'')||case when vdh.id is not null then ' '||vdh.name else '' end as f7stacard");
		query.append(" ,ml.name as f8entdep,mlLast.name as f9mlLastdep") ;
		query.append(" ,case when vdh.id is not null then null when (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)=0 then 1 when vht.code='DAYTIMEHOSP' then ((coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)+1) else (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart) end as f10countDays") ;
		query.append(" ,list(vpd.name||' '||mkb.code) as f11diagDisch") ;
		query.append(" ,list(vpd1.name||' '||mkb1.code) as f12diagClinic") ;
		query.append(" from MedCase sls");
		query.append(" left join VocHospType vht on vht.id=sls.hospType_id");
		query.append(" left join VocDeniedHospitalizating vdh on vdh.id=sls.deniedHospitalizating_id");
		query.append(" left join MedCase sloLast on sloLast.parent_id=sls.id and UPPER(sloLast.dtype)='DEPARTMENTMEDCASE'");
		query.append(" left join StatisticStub ss on ss.id=sls.statisticStub_id");
		query.append(" left join MisLpu mlLast on mlLast.id=sloLast.department_id");
		query.append(" left join MisLpu ml on ml.id=sls.department_id");
		query.append("	left join Diagnosis diag on sls.id=diag.medCase_id");
		query.append("	left join VocIdc10 mkb on mkb.id=diag.idc10_id");
		query.append("	left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id and vdrt.code='3'");
		query.append("	left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id");
		query.append("	left join Diagnosis diag1 on sloLast.id=diag1.medCase_id");
		query.append("	left join VocIdc10 mkb1 on mkb1.id=diag1.idc10_id");
		query.append("	left join VocDiagnosisRegistrationType vdrt1 on vdrt1.id=diag1.registrationType_id and vdrt1.code='4'");
		query.append("	left join VocPriorityDiagnosis vpd1 on vpd1.id=diag1.priority_id");

		query.append(" where sls.patient_id=:patient and UPPER(sls.DTYPE) IN ('HOSPITALMEDCASE','EXTHOSPITALMEDCASE') and  (sloLast.id is null or sloLast.transferDate is null) ");
		query.append(" group by sls.id,sls.dtype,sls.dateStart,sls.dateFinish,vdh.id ,sls.username ,sls.emergency, ss.code,vdh.id,vdh.name ,ml.name,mlLast.name,vht.code");
		query.append(" order by sls.dateStart");
		//Query query2 = theManager.createQuery(query.toString()) ;
		// query2.setParameter("patient", aParentId) ;
		//results = query2.setMaxResults(1000).getResultList();
		List<Object[]> list = theManager.createNativeQuery(query.toString()).setParameter("patient", aParentId).getResultList() ;
		LinkedList<IEntityForm> ret = new LinkedList<>();
		for (Object[] hospit : list) {
			HospitalMedCaseForm form ;
			if (hospit[1]!=null) {
				form = new ExtHospitalMedCaseForm() ;
			} else {
				form = new HospitalMedCaseForm() ;
			}
			form.setId(ConvertSql.parseLong(hospit[0])) ;
			form.setIsDeniedHospitalizating(hospit[4]!=null?Boolean.TRUE:Boolean.FALSE) ;
			form.setDateStart(ConvertSql.parseString(hospit[2]));
			form.setDateFinish(ConvertSql.parseString(hospit[3]));
			//form.setFinishWorkerText(hospit.getFinishWorkerText());
			form.setUsername(ConvertSql.parseString(hospit[5]));
			form.setDaysCount(ConvertSql.parseString(hospit[10])) ;
			form.setStatCardNumber(ConvertSql.parseString(hospit[7])) ;
			form.setEmergency(hospit[6]!=null?Boolean.TRUE:Boolean.FALSE);
			form.setDischargeEpicrisis(ConvertSql.parseString(hospit[9]));
			form.setDepartmentInfo(ConvertSql.parseString(hospit[8]));
			form.setClinicalDiagnos(ConvertSql.parseString(hospit[12])) ;
			form.setConcludingDiagnos(ConvertSql.parseString(hospit[11])) ;
			ret.add(form);

		}
		return ret;
	}

	public List<SurgicalOperationForm> getSurgicalOperationByDate(String aDate) {
		QueryClauseBuilder builder = new QueryClauseBuilder();
		//builder.add("operationDate", aDate);
		Query query = builder.build(theManager, "from SurgicalOperation where operationDate='"+aDate+"' ", " order by operationTime");
		return createList(query);
	}
	private List<SurgicalOperationForm> createList(Query aQuery) {
		List<SurgicalOperation> list = aQuery.getResultList();
		List<SurgicalOperationForm> ret = new LinkedList<>();
		for (SurgicalOperation surOper : list) {
			try {
				ret.add(theEntityFormService.loadForm(SurgicalOperationForm.class, surOper));
			} catch (EntityFormException e) {
				throw new IllegalStateException(e);
			}
		}
		return ret;
	}

	public List<HospitalMedCaseForm> findOpenHospitalByDate(String aDate) {
		QueryClauseBuilder builder = new QueryClauseBuilder();
		Date date = null;
		if(!StringUtil.isNullOrEmpty(aDate)) {
			try {
				date = DateFormat.parseSqlDate(aDate);
			} catch (Exception e) {
				LOG.warn("Ошибка преобразования даты "+aDate, e);
			}
		}
		if (date != null){
			builder.add("dateStart", date);
		} else {
			throw new IllegalDataException("Неправильная дата") ;
		}
		Query query = builder.build(theManager, "from MedCase where DTYPE='HospitalMedCase' and dateFinish is null  and deniedHospitalizating_id is null and (ambulanceTreatment is null or cast(ambulanceTreatment as int)=0)", " order by entranceTime");
		return createHospitalList(query);
	}

	private List<HospitalMedCaseForm> createHospitalList(Query aQuery) {
		List<HospitalMedCase> list = aQuery.getResultList();
		List<HospitalMedCaseForm> ret = new LinkedList<>();
		for (HospitalMedCase medCase : list) {
			try {
				ret.add(theEntityFormService.loadForm(HospitalMedCaseForm.class, medCase));
			} catch (EntityFormException e) {
				throw new IllegalStateException(e);
			}
		}
		return ret;
	}

	@EJB ILocalEntityFormService theEntityFormService ;
	@PersistenceContext EntityManager theManager ;
	@Resource SessionContext theContext ;
	@EJB ILocalMonitorService theMonitorService;
	@EJB IExpert2Service theExpertService;



}
