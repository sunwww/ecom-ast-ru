package ru.ecom.mis.ejb.service.medcase;

import org.apache.log4j.Logger;
import org.jboss.annotation.security.SecurityDomain;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.diary.ejb.domain.DischargeEpicrisis;
import ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocol;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.expert2.service.IExpert2Service;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentParameter;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentParameterConfig;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.MedCaseMedPolicy;
import ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase;
import ru.ecom.mis.ejb.domain.medcase.hospital.TemperatureCurve;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAcuityDiagnosis;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDiagnosisRegistrationType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPrimaryDiagnosis;
import ru.ecom.mis.ejb.domain.patient.MedPolicy;
import ru.ecom.mis.ejb.domain.psychiatry.CompulsoryTreatmentAggregate;
import ru.ecom.mis.ejb.domain.report.AggregateHospitalReport;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.HospitalMedCaseViewInterceptor;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.StatisticStubStac;
import ru.ecom.mis.ejb.form.patient.MedPolicyForm;
import ru.nuzmsh.util.format.DateFormat;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
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

    private static final Logger LOG = Logger.getLogger(HospitalMedCaseServiceBean.class);
    @EJB
    ILocalEntityFormService entityFormService;
    @PersistenceContext
    EntityManager manager;
    @Resource
    SessionContext context;
    @EJB
    ILocalMonitorService monitorService;
    @EJB
    IExpert2Service expertService;

    public static void saveDischargeEpicrisisByCase(MedCase aMedCase, String aDischargeEpicrisis, EntityManager aManager) {
        aManager.createNativeQuery("delete from diary d where d.medcase_id= " + aMedCase.getId() + " and upper(d.dtype)='DISCHARGEEPICRISIS' ").executeUpdate();
        int len = 15000;
        int lend = aDischargeEpicrisis.length();
        int cnt = lend / len;
        for (int i = 0; i < cnt; i++) {
            DischargeEpicrisis prot = new DischargeEpicrisis();
            prot.setRecord(aDischargeEpicrisis.substring(i * len, Math.min((i + 1) * len, lend)));
            prot.setMedCase(aMedCase);
            aManager.persist(prot);
        }
        if (lend % len > 0) {
            DischargeEpicrisis prot = new DischargeEpicrisis();
            prot.setRecord(aDischargeEpicrisis.substring(len * cnt));
            prot.setMedCase(aMedCase);
            aManager.persist(prot);
        }
    }

    public static void saveDischargeEpicrisis(long aMedCaseId, String aDischargeEpicrisis, EntityManager aManager) {
        HospitalMedCase medCase = aManager.find(HospitalMedCase.class, aMedCaseId);
        saveDischargeEpicrisisByCase(medCase, aDischargeEpicrisis, aManager);
    }

    private static Collection<MedPolicyForm> convert(Collection<MedPolicy> aPolicies) {
        LinkedList<MedPolicyForm> list = new LinkedList<>();
        for (MedPolicy policy : aPolicies) {
            MedPolicyForm frm = new MedPolicyForm();
            frm.setId(policy.getId());
            frm.setActualDateFrom(DateFormat.formatToDate(policy.getActualDateFrom()));
            frm.setActualDateTo(DateFormat.formatToDate(policy.getActualDateTo()));
            frm.setText(policy.getText());
            list.add(frm);
        }
        return list;

    }

    private HashMap<String, String> getMiacServiceStreamMap() {
        HashMap<String, String> ss = new HashMap<>();
        ss.put("OBLIGATORY", "Средства ОМС");
        ss.put("BUDGET", "Средства бюджета");
        ss.put("UFSIN", "Личные средства граждан, по договору и ДМС");
        ss.put("CHARGED", "Личные средства граждан, по договору и ДМС");
        return ss;
    }

    /**
     * Формируем "справочник" с профилями поликлинических специалистов
     *
     * @return map со странами в виде (Код специальности, Название_МИАЦа)
     */
    private HashMap<String, String> getPolicProfileMap() {
        HashMap<String, String> policProfiles = new HashMap<>();

        policProfiles.put("80", "другое");
        policProfiles.put("0S", "другое");
        policProfiles.put("11", "акушерство и гинекология");
        policProfiles.put("13", "аллергология и иммунология");
        policProfiles.put("14", "анестезиология и реаниматология");
        policProfiles.put("15", "гастроэнтерология");
        policProfiles.put("16", "гематология");
        policProfiles.put("17", "генетика");
        policProfiles.put("18", "гериатрия");
        policProfiles.put("19", "дерматовенерология");
        policProfiles.put("20", "детская кардиология");
        policProfiles.put("21", "детская хирургия");
        policProfiles.put("22", "детская эндокринология");
        policProfiles.put("23", "диабетология");
        policProfiles.put("24", "диетология");
        policProfiles.put("25", "инфекционные болезни");
        policProfiles.put("26", "кардиология");
        policProfiles.put("27", "клиническая лабораторная диагностика");
        policProfiles.put("29", "колопроктология");
        policProfiles.put("30", "лабораторная генетика");
        policProfiles.put("31", "лечебная физкультура и спортивная медицина");
        policProfiles.put("32", "лечебная физкультура и спортивная медицина");
        policProfiles.put("34", "мануальная терапия");
        policProfiles.put("35", "неврология");
        policProfiles.put("36", "нейрохирургия");
        policProfiles.put("37", "нефрология");
        policProfiles.put("39", "онкология");
        policProfiles.put("103", "ортодонтия");
        policProfiles.put("40", "оториноларингология (за исключением кохлеарной имплантации)");
        policProfiles.put("41", "офтальмология");
        policProfiles.put("49", "профпатология");
        policProfiles.put("51", "психиатрия");
        policProfiles.put("57", "психиатрия-наркология");
        policProfiles.put("50", "психотерапия");
        policProfiles.put("58", "пульмонология");
        policProfiles.put("59", "радиология");
        policProfiles.put("61", "ревматология");
        policProfiles.put("60", "рентгенология");
        policProfiles.put("62", "рефлексотерапия");
        policProfiles.put("64", "сердечно-сосудистая хирургия");
        policProfiles.put("105", "стоматология детская");
        policProfiles.put("107", "стоматология ортопедическая");
        policProfiles.put("106", "стоматология терапевтическая");
        policProfiles.put("113", "стоматология терапевтическая"); //зубной врач (стоматолог)
        policProfiles.put("108", "стоматология хирургическая");
        policProfiles.put("77", "токсикология");
        policProfiles.put("78", "торакальная хирургия");
        policProfiles.put("79", "травматология и ортопедия");
        policProfiles.put("81", "ультразвуковая диагностика");
        policProfiles.put("82", "урология");
        policProfiles.put("83", "физиотерапия");
        policProfiles.put("84", "фтизиатрия");
        policProfiles.put("86", "функциональная диагностика");
        policProfiles.put("87", "хирургия");
        policProfiles.put("109", "челюстно-лицевая хирургия");
        policProfiles.put("88", "эндокринология");
        policProfiles.put("89", "эндоскопия");
        policProfiles.put("71", "терапия");
        policProfiles.put("72", "терапия");
        policProfiles.put("44", "педиатрия");
        policProfiles.put("45", "педиатрия");
        policProfiles.put("38", "общая врачебная практика (семейная медицина)");
        policProfiles.put("65", "скорая медицинская помощь");
        return policProfiles;
    }

    /**
     * Формируем "справочник" с профилями поликлинических специалистов
     *
     * @return map со странами в виде (Код специальности, Код ЦБРФ)
     */
    private HashMap<String, String> getPolicProfileMapBank() {
        HashMap<String, String> policProfiles = new HashMap<>();

        policProfiles.put("80", "CMP48");
        policProfiles.put("0S", "CMP48");
        policProfiles.put("11", "CMP02");
        policProfiles.put("13", "CMP03");
        policProfiles.put("14", "CMP04");
        policProfiles.put("15", "CMP05");
        policProfiles.put("16", "CMP07");
        policProfiles.put("17", "CMP48");
        policProfiles.put("18", "CMP08");
        policProfiles.put("19", "CMP09"); //Дерматология, в ЦБРФ отдельно дерматология и венерология
        policProfiles.put("20", "CMP10");
        policProfiles.put("21", "CMP13");
        policProfiles.put("22", "CMP14");
        policProfiles.put("23", "CMP44");  //Диабетология => эндокринология
        policProfiles.put("24", "CMP05"); //Диетология => гастро
        policProfiles.put("25", "CMP15");
        policProfiles.put("26", "CMP16");
        policProfiles.put("27", "CMP48"); //КДЛ - прочее
        policProfiles.put("29", "CMP17");
        policProfiles.put("30", "CMP48");  //лаб. генетика - прочее
        policProfiles.put("31", "CMP46");  //лечебная физкультура и спортивная медицина => мед. реаб.
        policProfiles.put("32", "CMP46");
        policProfiles.put("34", "CMP46");  //мануальная терапия => мед. реаб.
        policProfiles.put("35", "CMP18");
        policProfiles.put("36", "CMP19");
        policProfiles.put("37", "CMP21");
        policProfiles.put("39", "CMP22");
        policProfiles.put("103", "CMP3301");
        policProfiles.put("40", "CMP23");
        policProfiles.put("41", "CMP24");
        policProfiles.put("49", "CMP26");
        policProfiles.put("51", "CMP27");
        policProfiles.put("57", "CMP28");
        policProfiles.put("50", "CMP48"); //психотерапия - прочее
        policProfiles.put("58", "CMP29");
        policProfiles.put("59", "CMP2203"); //1103 в начале списка с "детскими" кодами
        policProfiles.put("61", "CMP40");
        policProfiles.put("60", "CMP48");  //рентгенология - прочее
        policProfiles.put("62", "CMP46"); //рефлексотерапия => мед. реаб.
        policProfiles.put("64", "CMP31");
        policProfiles.put("105", "CMP32");
        //СТОМАТОЛОГИЯ
        policProfiles.put("107", "CMP33"); //ортопедическая
        policProfiles.put("106", "CMP33"); //терапевтическая
        policProfiles.put("113", "CMP33"); //зубной врач (стоматолог)
        policProfiles.put("108", "CMP33"); //хирургическая
        policProfiles.put("77", "CMP35");
        policProfiles.put("78", "CMP36");
        policProfiles.put("79", "CMP37");
        policProfiles.put("81", "CMP48"); //ультразвуковая диагностика => прочее
        policProfiles.put("82", "CMP38");
        policProfiles.put("83", "CMP46"); //физиотерапия => мед. реаб.
        policProfiles.put("84", "CMP39");
        policProfiles.put("86", "CMP48");  //функциональная диагностика => прочее
        policProfiles.put("87", "CMP40");
        policProfiles.put("109", "CMP43");
        policProfiles.put("88", "CMP44");
        policProfiles.put("89", "CMP48");  //эндоскопия => прочее
        policProfiles.put("71", "CMP34");
        policProfiles.put("72", "CMP34");
        policProfiles.put("44", "CMP25");
        policProfiles.put("45", "CMP25");
        policProfiles.put("38", "CMP25");  //общая врачебная практика (семейная медицина)=> терапия
        policProfiles.put("65", "CMP48"); //скорая медицинская помощь => прочее
        return policProfiles;
    }

    /**
     * Формируем "справочник" с профилями коек в стационаре
     *
     * @return map со странами в виде (профиль койки, Название_МИАЦа)
     */
    private HashMap<String, String> getStacProfileMap() {
        HashMap<String, String> stacProfiles = new HashMap<>();
        stacProfiles.put("40", "акушерство и гинекология");
        stacProfiles.put("38", "акушерство и гинекология");
        stacProfiles.put("39", "акушерство и гинекология");
        stacProfiles.put("8", "аллергология и иммунология");
        stacProfiles.put("801", "анестезиология и реаниматология");
        stacProfiles.put("80", "анестезиология и реаниматология");
        stacProfiles.put("6", "гастроэнтерология");
        stacProfiles.put("16", "гематология");
        stacProfiles.put("3", "кардиология");
        stacProfiles.put("63", "колопроктология");
        stacProfiles.put("48", "неврология");
        stacProfiles.put("22", "нейрохирургия");
        stacProfiles.put("18", "нефрология");
        stacProfiles.put("37", "онкология");
        stacProfiles.put("56", "оториноларингология (за исключением кохлеарной имплантации)");
        stacProfiles.put("54", "офтальмология");
        stacProfiles.put("69", "пульмонология");
        stacProfiles.put("65", "ревматология");
        stacProfiles.put("26", "сердечно-сосудистая хирургия");
        stacProfiles.put("24", "торакальная хирургия");
        stacProfiles.put("31", "травматология и ортопедия");
        stacProfiles.put("28", "травматология и ортопедия");
        stacProfiles.put("33", "урология");
        stacProfiles.put("20", "хирургия");
        stacProfiles.put("41", "хирургия");
        stacProfiles.put("35", "челюстно-лицевая хирургия");
        stacProfiles.put("12", "эндокринология");
        stacProfiles.put("2", "терапия");
        stacProfiles.put("61", "педиатрия");
        stacProfiles.put("81", "педиатрия");
        stacProfiles.put("14", "инфекционные болезни"); //TODO уточнить в "справочнике" миаца

        stacProfiles.put("29", "другое");
        return stacProfiles;
    }

    /**
     * Формируем "справочник" с профилями коек в стационаре
     *
     * @return map со странами в виде (профиль койки, код ЦБРФ)
     */
    private HashMap<String, String> getStacProfileMapBank() {
        HashMap<String, String> stacProfiles = new HashMap<>();
        stacProfiles.put("38", "CMP02");
        stacProfiles.put("40", "CMP02");
        stacProfiles.put("39", "CMP02");
        stacProfiles.put("8", "CMP03");
        stacProfiles.put("801", "CMP04");
        stacProfiles.put("80", "CMP04");
        stacProfiles.put("6", "CMP05");
        stacProfiles.put("16", "CMP07");
        stacProfiles.put("3", "CMP16");
        stacProfiles.put("63", "CMP17");
        stacProfiles.put("48", "CMP18");
        stacProfiles.put("22", "CMP19");
        stacProfiles.put("18", "CMP20");
        stacProfiles.put("37", "CMP22");
        stacProfiles.put("56", "CMP23");
        stacProfiles.put("54", "CMP24");
        stacProfiles.put("69", "CMP29");
        stacProfiles.put("65", "CMP40");
        stacProfiles.put("26", "CMP31");
        stacProfiles.put("24", "CMP36");
        stacProfiles.put("31", "CMP37");
        stacProfiles.put("28", "CMP37");
        stacProfiles.put("33", "CMP38");
        stacProfiles.put("20", "CMP40");
        stacProfiles.put("41", "CMP40");
        stacProfiles.put("35", "CMP43");
        stacProfiles.put("12", "CMP44");
        stacProfiles.put("2", "CMP34");
        stacProfiles.put("61", "CMP20");
        stacProfiles.put("81", "CMP20");
        stacProfiles.put("29", "CMP42");
        stacProfiles.put("45", "CMP22");
        return stacProfiles;
    }

    /**
     * Формируем "справочник" стран с названиями для экспорта в МИАЦ
     *
     * @return map со странами в виде (Код, Название_МИАЦа)
     */
    private HashMap<String, String> getCountries() {
        HashMap<String, String> countries = new HashMap<>();
        countries.put("100", "Болгария"); //TODO уточнить
        countries.put("598", "Экваториальная Гвинея"); //ПАПУА-НОВАЯ ГВИНЕЯ
        countries.put("226", "Экваториальная Гвинея");
        countries.put("148", "Республика Чад");
        countries.put("624", "Гвинея-Бисау");
        countries.put("31", "Азербайджан");
        countries.put("24", "Ангола");
        countries.put("51", "Армения");
        countries.put("4", "Афганистан");
        countries.put("50", "Бангладеш");
        countries.put("112", "Беларусь");
        countries.put("72", "Ботсвана");
        countries.put("76", "Бразилия");
        countries.put("862", "Венесуэла");
        countries.put("704", "Вьетнам");
        countries.put("276", "Германия");
        countries.put("268", "Грузия");
        countries.put("818", "Египет");
        countries.put("894", "Замбия");
        countries.put("716", "Зимбабве");
        countries.put("376", "Израиль");
        countries.put("356", "Индия");
        countries.put("368", "Ирак");
        countries.put("364", "Иран");
        countries.put("724", "Испания");
        countries.put("398", "Казахстан");
        countries.put("120", "Камерун");
        countries.put("124", "Канада");
        countries.put("178", "Конго");
        countries.put("192", "Куба");
        countries.put("442", "Люксембург");
        countries.put("466", "Мали");
        countries.put("504", "Марокко");
        countries.put("508", "Мозамбик");
        countries.put("498", "Молдова");
        countries.put("496", "Монголия");
        countries.put("516", "Намибия");
        countries.put("566", "Нигерия");
        countries.put("784", "Объединенные Арабские Эмираты");
        countries.put("616", "Польша");
        countries.put("895", "Республика Абхазия");
        countries.put("144", "Республика Шри-Ланка");
        countries.put("642", "Румыния");
        countries.put("686", "Сенегал");
        countries.put("688", "Сербия");
        countries.put("762", "Таджикистан");
        countries.put("764", "Таиланд");
        countries.put("834", "Танзания");
        countries.put("795", "Туркменистан");
        countries.put("860", "Узбекистан");
        countries.put("804", "Украина");
        countries.put("191", "Хорватия");
        countries.put("152", "Чили");
        countries.put("896", "Южная Осетия");
        countries.put("392", "Япония");
        countries.put("12", "Алжир");
        countries.put("70", "Босния");
        countries.put("288", "Ганна");
        countries.put("300", "Греция");
        countries.put("887", "Йемен");
        countries.put("156", "Китай");
        countries.put("384", "Кот-д-Вуар");
        countries.put("417", "Кыргызстан");
        countries.put("428", "Латвия");
        countries.put("422", "Ливан");
        countries.put("440", "Литва");
        countries.put("484", "Мексика");
        countries.put("528", "Нидерланды");
        countries.put("9999", "Перу");
        countries.put("790", "Сирия");
        countries.put("840", "США");
        countries.put("788", "Тунис");
        countries.put("792", "Турция");
        countries.put("246", "Финляндия");
        countries.put("710", "Южная-Африканская Республика");
        return countries;
    }

    /**
     * Формируем "справочник" регионов с названиями для экспорта в МИАЦ
     *
     * @return map с регионами в виде (Код, Название_МИАЦа)
     */
    private HashMap<String, String> getRegions() {
        HashMap<String, String> countries = new HashMap<>();
        countries.put("01", "Республика Адыгея");
        countries.put("04", "Республика Алтай");
        countries.put("02", "Республика Башкортостан");
        countries.put("03", "Республика Бурятия");
        countries.put("05", "Республика Дагестан");
        countries.put("06", "Республика Ингушетия");
        countries.put("07", "Кабардино-Балкарская республика");
        countries.put("08", "Республика Калмыкия");
        countries.put("09", "Карачаево-Черкесская республика");
        countries.put("10", "Республика Карелия");
        countries.put("11", "Республика Коми");
        countries.put("91", "Республика Крым");
        countries.put("12", "Республика Марий Эл");
        countries.put("13", "Республика Мордовия");
        countries.put("14", "Республика Саха (Якутия)");
        countries.put("15", "Республика Северная Осетия-Алания");
        countries.put("16", "Республика Татарстан");
        countries.put("17", "Республика Тыва");
        countries.put("18", "Удмуртская республика");
        countries.put("19", "Республика Хакасия");
        countries.put("20", "Чеченская республика");
        countries.put("21", "Чувашская республика");
        countries.put("22", "Алтайский край");
        countries.put("75", "Забайкальский край");
        countries.put("41", "Камчатский край");
        countries.put("23", "Краснодарский край");
        countries.put("24", "Красноярский край");
        countries.put("59", "Пермский край");
        countries.put("25", "Приморский край");
        countries.put("26", "Ставропольский край");
        countries.put("27", "Хабаровский край");
        countries.put("28", "Амурская область");
        countries.put("29", "Архангельская область");
        countries.put("31", "Белгородская область");
        countries.put("32", "Брянская область");
        countries.put("33", "Владимирская область");
        countries.put("34", "Волгоградская область");
        countries.put("35", "Вологодская область");
        countries.put("36", "Воронежская область");
        countries.put("37", "Ивановская область");
        countries.put("38", "Иркутская область");
        countries.put("39", "Калининградская область");
        countries.put("40", "Калужская область");
        countries.put("42", "Кемеровская область");
        countries.put("43", "Кировская область");
        countries.put("44", "Костромская область");
        countries.put("45", "Курганская область");
        countries.put("46", "Курская область");
        countries.put("47", "Ленинградская область");
        countries.put("48", "Липецкая область");
        countries.put("49", "Магаданская область");
        countries.put("50", "Московская область");
        countries.put("51", "Мурманская область");
        countries.put("52", "Нижегородская область");
        countries.put("53", "Новгородская область");
        countries.put("54", "Новосибирская область");
        countries.put("55", "Омская область");
        countries.put("56", "Оренбургская область");
        countries.put("57", "Орловская область");
        countries.put("58", "Пензенская область");
        countries.put("60", "Псковская область");
        countries.put("61", "Ростовская область");
        countries.put("62", "Рязанская область");
        countries.put("63", "Самарская область");
        countries.put("64", "Саратовская область");
        countries.put("65", "Сахалинская область");
        countries.put("66", "Свердловская область");
        countries.put("67", "Смоленская область");
        countries.put("68", "Тамбовская область");
        countries.put("69", "Тверская область");
        countries.put("70", "Томская область");
        countries.put("71", "Тульская область");
        countries.put("72", "Тюменская область");
        countries.put("73", "Ульяновская область");
        countries.put("74", "Челябинская область");
        countries.put("76", "Ярославская область");
        countries.put("77", "Москва");
        countries.put("78", "Санкт-Петербург");
        countries.put("92", "Севастополь");
        countries.put("79", "Еврейская автономнаяа область");
        countries.put("83", "Ненецкий автономный округ");
        countries.put("86", "Ханты-Мансийский автономный округ - Югра");
        countries.put("87", "Чукотский автономный округ");
        countries.put("89", "Ямало-Ненецкий автономный округ");
        countries.put("99", "Байконур (Казахстан)");
        return countries;
    }

    public String toString(Object o) {
        return o != null ? o.toString().trim() : "";
    }

    /**
     * Формируем файл для импорта в "базу данных" МИАЦа со сведениями об оказанной мед. помощи иногородним и иностранным гражданам
     *
     * @param aDateFrom
     * @param aDateTo
     * @param aType
     * @param aReportType тип отчета (МИАЦ либо ЦБРФ)
     * @return
     */
    @Override
    public String makeReportCostCase(String aDateFrom, String aDateTo, String aType, String aLpuCode, String aReportType) {
        //Начинаем стационар
        if (aLpuCode == null || aLpuCode.equals("")) {
            return "Не указан код ЛПУ";
        }
        try {
            Integer findDays = 10; //дни до госпитализации/СПО, в течение которых надо искать договор
            StringBuilder sqlSelect = new StringBuilder();
            StringBuilder sqlAppend = new StringBuilder();
            HashMap<String, String> regionOrCountry;
            HashMap<String, String> profileMap = "BANK".equals(aReportType) ?
                    getStacProfileMapBank() : getStacProfileMap();
            HashMap<String, String> sredstvaMap = getMiacServiceStreamMap();

            if ("inog".equals(aType)) {
                sqlAppend.append(" and a.addressid is not null and coalesce(a.kladr,'')!='' and a.kladr not like '30%' ");
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
            String ageSelect = "", ageGroup = "", vbtSelect = "", emSelect = "", highTechSelect = "", emGroup = "", perGroup = "", vssGroup = "";
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
                perGroup = ",sls.datefinish-sls.datestart";
                vssSelect.append(",case when vss.code='CHARGED' then 'SFC05' else case when vss.code in ('OBLIGATORYINSURANCE','BUDGET') then 'SFC03'")
                        .append(" else case when vss.code='PRIVATEINSURANCE' then 'SFC04' else 'SFC06' end end end as f11_code_fin_md");
                vssGroup = " ,vss.code";
                vbtSelect2 = ", vbt.name as vbtNameNote ";
            }
            Long priceListId = getDefaultPriceListId();
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
            List<Object[]> list = manager.createNativeQuery(sql.toString()).getResultList();
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
                    if (i % 100 == 0) {
                        LOG.info("report_stac make " + i + " records");
                    }
                    period = toString(row[0]).split("-");
                    patientCount = toString(row[1]);
                    region = getRegion(row[2].toString(), aReportType, regionOrCountry);
                    profile = profileMap.get(toString(row[3])) != null ? profileMap.get(toString(row[3])) : "PROFILE_CODE=" + toString(row[3]);
                    financeSource = toString(row[4]);
                    hosps = toString(row[5]).split(",");
                    totalSum = 0;
                    if (financeSource.equals("CHARGED")) { //Платные случаи
                        String firstHosp = "";
                        for (String hosp : hosps) {
                            if (firstHosp.equals(""))
                                firstHosp = hosp.trim();
                            JSONObject hospitalInfo = new JSONObject(countMedcaseCost(Long.valueOf(hosp.trim()), priceListId));
                            totalSum += hospitalInfo.getDouble("totalSum");
                        }
                        if (!firstHosp.equals("") && "BANK".equals(aReportType))
                            pay = getPayType(firstHosp, findDays);

                    } else if (financeSource.equals("OBLIGATORY") || "BANK".equals(aReportType)) { //ОМС
                        for (String hosp : hosps) {
                            try {
                                JSONObject costJson = new JSONObject(expertService.getMedcaseCost(Long.valueOf(hosp.trim())));
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
                            patientCount = "" + (Long.parseLong(patientCount) + Long.parseLong(rec.getString("patientCount")));
                            rec.remove("sum");
                            rec.remove("patientCount");
                            rec.put("patientCount", patientCount).put("sum", BigDecimal.valueOf(totalSum).setScale(2, RoundingMode.HALF_EVEN));
                        } else {
                            rec = new JSONObject();
                            rec.put("hash", recordHash).put("period0", period[0]).put("period1", period[1]).put("region", region).put("uslovia", uslovia).put("profile", profile).put("financeSource", sredstvaMap.get(financeSource))
                                    .put("patientCount", patientCount).put("sum", BigDecimal.valueOf(totalSum).setScale(2, RoundingMode.HALF_EVEN));
                            if ("BANK".equals(aReportType)) {
                                rec.put("age", row[6]);
                                rec.put("code_vid_mp", row[7]);
                                rec.put("code_usl_mp", row[8]);
                                rec.put("code_form_ok", row[9]);
                                rec.put("code_prod", row[10]);
                                rec.put("code_fin_md", row[11]);
                                rec.put("profile", profile);
                                rec.put("note", row[12]);
                                rec.put("pay", pay);
                            }
                        }
                        allRecords.put(recordHash, rec);
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
                    .append(periodSelect.toString().replaceAll("sls.datefinish-sls.datestart", "spo.datefinish-spo.datestart"))
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
                    .append(perGroup.replaceAll("sls.datefinish-sls.datestart", "spo.datefinish-spo.datestart"))
                    .append(vssGroup)
                    .append(vwfSelect);

            LOG.info("===========repotr_pol = " + sql);
            list = manager.createNativeQuery(sql.toString()).getResultList();
            LOG.info("repotr_pol found " + list.size() + " records");
            uslovia = "амбулаторно";
            profileMap = "BANK".equals(aReportType) ?
                    getPolicProfileMapBank() : getPolicProfileMap();
            try {
                i = 0;
                for (Object[] row : list) {
                    i++;
                    if (i % 100 == 0) {
                        LOG.info("making " + i + " records pol");
                    }
                    period = toString(row[0]).split("-");
                    patientCount = toString(row[1]);
                    region = getRegion(row[2].toString(), aReportType, regionOrCountry);
                    profile = profileMap.get(toString(row[3])) != null ? profileMap.get(toString(row[3])) : "PROFILE_CODE=" + toString(row[3]);
                    financeSource = toString(row[4]);
                    totalSum = 0;
                    if (financeSource.equals("OBLIGATORY") || financeSource.equals("BUDGET")) { //считаем цену за ОМС
                        String[] spoIds = row[7].toString().split(",");
                        String firstSpo = "";
                        for (String spoId : spoIds) {
                            if (firstSpo.equals(""))
                                firstSpo = spoId.trim();
                            JSONObject costJson = new JSONObject(expertService.getMedcaseCost(Long.valueOf(spoId.trim())));
                            if (costJson.has("price")) {
                                double cost = costJson.getDouble("price");
                                totalSum += cost;
                            }
                        }
                        if (!firstSpo.equals("") && "BANK".equals(aReportType))
                            pay = getPayType(firstSpo, findDays);
                    } else {
                        try {
                            totalSum = Double.parseDouble(toString(row[5]));
                        } catch (NumberFormatException e) {
                            LOG.error("Не удалось посчитать цену поликлинического случая: " + toString(row[5]));
                            totalSum = 0.0;
                        }
                    }
                    if (totalSum > 0.0) {
                        period[1] = period[1].startsWith("0") ? period[1].substring(1) : period[1];
                        String recordHash = (period[0] + "" + period[1]).hashCode() + "#" + (region != null ? region.hashCode() : "_NULL_REGION") + "" + uslovia.hashCode() + "" + profile.hashCode() + "" + financeSource.hashCode();
                        JSONObject rec = allRecords.get(recordHash);
                        if (rec != null) {
                            totalSum += rec.getDouble("sum");
                            patientCount = "" + (Long.parseLong(patientCount) + Long.parseLong(rec.getString("patientCount")));
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
                                rec.put("pay", pay);
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
            return "BANK".equals(aReportType) ? allRecords.values().toString() : createFile(txtFile, aType);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Получить форму оплаты случая лечения (в случае, если CHARGED) - первая оплата
     *
     * @param aMedcaseId Случай лечения
     * @param findDays   За сколько дней до операции искать договор
     * @return String Тип оплаты PAY01 (нал) / PAY03 (безнал)
     */
    private String getPayType(String aMedcaseId, Integer findDays) {
        String sql = "select case when co.ispaymentterminal =true then '1' else '0' end" +
                " from contractaccount c" +
                " left join contractaccountoperation co on co.account_id =c.id" +
                " where c.datefrom between (select datestart from medcase where id=" +
                aMedcaseId + ")-" + findDays + " and" +
                " (select datestart from medcase where id=" + aMedcaseId +
                ") limit 1";
        List<Object> list = manager.createNativeQuery(sql).getResultList();
        //первая оплата
        return !list.isEmpty() && list.get(0).equals("1") ?
                "PAY03" : "PAY01";
    }

    /**
     * Получить код страны для банка (всегда трёхзначный)
     *
     * @param regCode Код региона/страны
     * @return String Регион для отчёта
     */
    private String getRegionForBank(String regCode) {
        return regCode.length() > 2 ? regCode : ("000" + regCode).substring(regCode.length());
    }

    /**
     * Получить регион для разных типов отчёта
     *
     * @param regCode         Код региона/страны
     * @param aReportType     Тип отчёта
     * @param regionOrCountry Соответствие код-регион/страна
     * @return String Регион для отчёта
     */
    private String getRegion(String regCode, String aReportType, HashMap<String, String> regionOrCountry) {
        String region;
        if ("MIAC".equals(aReportType)) {
            region = regionOrCountry.get(toString(regCode)) != null ? regionOrCountry.get(toString(regCode)) : "REGION_CODE=" + toString(regCode);
        } else if ("BANK".equals(aReportType)) {
            region = getRegionForBank(toString(regCode));
        } else {
            region = null;
            LOG.info("unknown type report");
        }
        return region;
    }

    private String createFile(StringBuilder aText, String aFileName) {
        EjbEcomConfig config = EjbEcomConfig.getInstance();
        String workDir = config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf") + "/" + aFileName + "." + aFileName;
        try (OutputStream os = new FileOutputStream(workDir)) {
            os.write(aText.toString().getBytes());
            LOG.info("i create file = /rtf/" + aFileName + "." + aFileName);
            return "/rtf/" + aFileName + "." + aFileName;
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return "NO_FILE";
    }

    @Override
    public String getAllServicesByMedCase(Long medcaseId) {
        return countMedcaseCost(medcaseId, getDefaultPriceListId(), true);
    }

    private String countMedcaseCost(Long medcaseId, Long priceListId) {
        return countMedcaseCost(medcaseId, priceListId, false);
    }

    private Long getDefaultPriceListId() {
        List<BigInteger> list = manager.createNativeQuery("select id from pricelist where isdefault='1'").getResultList();
        return list != null && !list.isEmpty() ?
                list.get(0).longValue()
                : null;
    }

    /**
     * Расчитываем стоимость случая госпитализации
     *
     * @param medcaseId ID госпитализации
     * @return - JSON объект с полной стоимостью + список оказанных услуг
     */
    private String countMedcaseCost(Long medcaseId, Long priceListId, boolean allIsCharged) {
        JSONObject root = new JSONObject();
        String ppidNull = allIsCharged ? " " : " and pp.id is not null ";
        try {
            String idsertypebed = "11";
            String dtype;
            long patientId;
            Long serviceStreamId;
            MedCase mc = (MedCase) manager.createQuery(" from MedCase where id=:id").setParameter("id", medcaseId).getSingleResult();
            if (mc instanceof HospitalMedCase) {
                HospitalMedCase hmc = (HospitalMedCase) mc;
                patientId = hmc.getPatient().getId();
                serviceStreamId = hmc.getServiceStream().getId();
                String startDate = DateFormat.formatToDate(hmc.getDateStart());
                String finishDate = DateFormat.formatToDate(hmc.getDateFinish() != null ? hmc.getDateFinish() : new java.sql.Date(new java.util.Date().getTime()));
                if (allIsCharged || Objects.equals(hmc.getServiceStream().getFinanceSource(), "CHARGED")) { //Для платных считаем цену случая сами

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
                            .append(" and ms.servicetype_id='").append(idsertypebed).append("' ").append((priceListId != null ? " and pp.priceList_id='" + priceListId + "'" : ""))
                            .append(" group by slo.id,ml.name,vbt.name,vbst.code,vbst.name,vrt.name,slo.datefinish,slo.transferdate,slo.datestart,vht.code,ms.code");
                    List<Object[]> list = manager.createNativeQuery(sql.toString()).getResultList();
                    JSONArray arr = new JSONArray(); //Койко-дни
                    double cost;
                    double sum = 0.0;
                    double totalSum = 0.0;
                    for (Object[] o : list) { //Считаем стоимость койко-дней
                        JSONObject kd = new JSONObject();
                        cost = Double.parseDouble(o[5].toString());
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
                    if (priceListId != null) {
                        sql.append(" and pp.priceList_id='").append(priceListId).append("'");
                    }
                    sql.append(" and (vis.noActuality='0' or vis.noActuality is null)")
                            .append(" order by vis.datestart");
                    list = manager.createNativeQuery(sql.toString()).getResultList();
                    arr = new JSONArray(); //Диагностика
                    sum = 0.0;

                    for (Object[] o : list) { //Считаем стоимость диагностики
                        JSONObject kd = new JSONObject();
                        cost = Double.parseDouble(o[1].toString());
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
                            .append(" where vis.parent_id='").append(medcaseId).append("'")
                            .append(" and vis.datestart between to_date('").append(startDate).append("','dd.mm.yyyy') and to_date('").append(finishDate).append("','dd.mm.yyyy')")
                            .append(" and upper(vis.dtype)='VISIT' and upper(smc.dtype)='SERVICEMEDCASE'")
                            .append(" and (vss.code='HOSPITAL' or vss.id is null)")
                            .append(" and (vis.noActuality='0' or vis.noActuality is null) ")
                            .append(ppidNull);
                    list = manager.createNativeQuery(sql.toString()).getResultList();
                    arr = new JSONArray();
                    sum = 0.0;

                    for (Object[] o : list) { //Считаем стоимость лаборатории
                        JSONObject kd = new JSONObject();
                        cost = o[1] == null ? 0 : Double.parseDouble(o[1].toString());
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
                            .append(" where (slo.parent_id='").append(medcaseId).append("' or slo.id='").append(medcaseId).append("') ")
                            .append(ppidNull);
                    list = manager.createNativeQuery(sql.toString()).getResultList();
                    arr = new JSONArray();
                    sum = 0.0;

                    for (Object[] o : list) { //Считаем стоимость операций
                        JSONObject kd = new JSONObject();
                        cost = o[1] == null ? 0 : Double.parseDouble(o[1].toString());
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
                            .append(" where (slo.parent_id='").append(medcaseId).append("' or slo.id='").append(medcaseId).append("') and pp.priceList_id='").append(priceListId).append("'");
                    list = manager.createNativeQuery(sql.toString()).getResultList();
                    arr = new JSONArray(); //Операции
                    sum = 0.0;

                    for (Object[] o : list) { //Считаем стоимость анастезии
                        JSONObject kd = new JSONObject();
                        cost = Double.parseDouble(o[1].toString());
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
                            .append(" where (slo.parent_id='").append(medcaseId).append("' or slo.id='").append(medcaseId).append("')")
                            .append(" and upper(so.dtype)='SERVICEMEDCASE' and upper(slo.dtype)!='VISIT' ")
                            .append(ppidNull);
                    list = manager.createNativeQuery(sql.toString()).getResultList();
                    arr = new JSONArray(); //Операции
                    sum = 0.0;

                    for (Object[] o : list) { //Считаем стоимость Доп. услуги
                        JSONObject kd = new JSONObject();
                        cost = o[1] == null ? 0 : Double.parseDouble(o[1].toString());
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
                } else {
                    root.put("totalSum", 0.00);
                }
                dtype = "Hospital";
            } else if (mc instanceof PolyclinicMedCase) {
                dtype = "POlyclinic";
            } else {
                dtype = "unknow";
            }
            root.put("dtype", dtype);
        } catch (JSONException e) {
            LOG.error("some JSON exception happens");
            e.printStackTrace();
        }
        return root.toString();
    }

    /**
     * Расчитываем значения для отправки на сервис экспертизы
     */

    @Override
    public String getDischargeEpicrisis(long medcaseId) {
        return HospitalMedCaseViewInterceptor.getDischargeEpicrisis(medcaseId, manager);
    }

    public void addMonitor(long monitorId, int someInt) {
        try {
            IMonitor monitor = monitorService.getMonitor(monitorId);
            monitor.advice(someInt);
        } catch (Exception ignored) {

        }
    }

    @Override
    public void finishMonitor(long monitorId) {
        try {
            monitorService.getMonitor(monitorId).finish("");
        } catch (Exception ignored) {

        }
    }

    @Override
    public void startMonitor(long monitorId) {
        try {
            monitorService.startMonitor(monitorId, "Обработка данных", 100);
        } catch (Exception ignored) {

        }
    }

    //психушка
    @Override
    public void refreshCompTreatmentReportByPeriod(String entranceDate, String dischargeDate, long idMonitor) {
        IMonitor monitor = null;
        java.util.Date dt = new java.util.Date();
        String curDate = DateFormat.formatToDate(dt);
        try {
            monitor = monitorService.startMonitor(idMonitor, "Обработка данных за период: " + entranceDate + " " + dischargeDate, 100);

            String sqlD = " delete from CompulsoryTreatmentAggregate where entrancehospdate <= (to_date('" + dischargeDate + "','dd.mm.yyyy')-1)" +
                    " and (dischargehospdate >= (to_date('" + entranceDate + "','dd.mm.yyyy')-1) or dischargehospdate is null)";
            manager.createNativeQuery(sqlD).executeUpdate();

            monitor.advice(20);

            String sql = "select ct.ordernumber,ct.carecard_id,pcc.patient_id from CompulsoryTreatment ct " +
                    " left join PsychiatricCareCard pcc on pcc.id=ct.careCard_id " +
                    " where coalesce(ct.registrationdate,ct.DecisionDate)<=to_date('" + dischargeDate + "','dd.mm.yyyy') " +
                    " and coalesce(ct.RegistrationReplaceDate,ct.Datereplace,to_date('" + entranceDate + "','dd.mm.yyyy'))>=to_date('" + entranceDate + "','dd.mm.yyyy') and ct.kind_id in (2,3) " +
                    " group by ct.ordernumber,ct.carecard_id,pcc.patient_id";
            List<Object[]> list = manager.createNativeQuery(sql).getResultList();
            int size = list.size() / 80;

            for (int ii = 0; ii < list.size(); ii++) {
                Object[] obj = list.get(ii);
                if (monitor.isCancelled()) {
                    throw new IllegalMonitorStateException("Прервано пользователем");
                }
                String sql1 = "select to_char(ct1.registrationdate,'dd.mm.yyyy') as r0egistrationdate,to_char(ct1.registrationreplacedate,'dd.mm.yyyy') as r1egistrationreplacedate" +
                        ",to_char(ct1.decisiondate,'dd.mm.yyyy') as d2ecisiondate,to_char(ct1.datereplace,'dd.mm.yyyy') as d3atereplace " +
                        " ,(select count(*) from CompulsoryTreatment ct2 where ct2.careCard_id='" + obj[1] + "' and ct2.orderNumber='" + obj[0] + "' and ct2.kind_id in (2,3) and ct1.decisiondate=ct2.datereplace) as p4revCT" +
                        " ,(select list(''||mc.id) from medcase mc where mc.patient_id=pcc.patient_id and upper(mc.dtype)='HOSPITALMEDCASE' and case when (mc.datestart <= coalesce(ct1.datereplace,current_date)" +
                        " and coalesce(mc.datefinish,current_date) >=ct1.decisiondate  ) then 1 else 0 end = 1) as s5ls" +
                        " ,vs.omcCode as f6sex" +
                        " from CompulsoryTreatment ct1 " +
                        " left join PsychiatricCareCard pcc on pcc.id=ct1.careCard_id" +
                        " left join Patient pat on pat.id=pcc.patient_id" +
                        " left join VocSex vs on pat.sex_id=vs.id" +
                        " where ct1.careCard_id='" + obj[1] + "' and ct1.orderNumber='" + obj[0] + "' and ct1.kind_id in (2,3)" +
                        " order by ct1.decisiondate";
                List<Object[]> l1 = manager.createNativeQuery(sql1).getResultList();
                List<Object[]> l2 = new ArrayList<>();

                for (Object[] o1 : l1) {
                    manager.createNativeQuery("delete from CompulsoryTreatmentAggregate where sls in (" + o1[5] + ")").executeUpdate();
                    boolean prevCT = o1[4] != null && Integer.parseInt("" + o1[4]) == 1;
                    if (!prevCT) {
                        l2.add(o1);
                    } else {
                        if (!l2.isEmpty()) {
                            l2.get(l2.size() - 1)[1] = o1[1];
                            l2.get(l2.size() - 1)[3] = o1[3];
                        } else {
                            l2.add(o1);
                        }
                    }

                }
                long hospInd = 0;
                for (Object[] o2 : l2) {
                    hospInd++;

                    String sql3 = "select mc.parent_id as s0ls,mc.id as s1lo,case when mc.datestart>to_date('" + (o2[2] == null ? curDate : o2[2]) + "','dd.mm.yyyy') then '1' else null end as s2rdate" +
                            ", to_char(mc.datestart,'dd.mm.yyyy') as d3atestart,to_char(mc.transferdate,'dd.mm.yyyy') as t4ransferdate,to_char(mc.datefinish,'dd.mm.yyyy') as d5atefinish,mc.department_id as d6epartment" +
                            ",case when ('" + (o2[3] == null ? "" : o2[3]) + "'='' or '" + (o2[3] == null ? "" : o2[3]) + "'!='' and coalesce(mc.transferdate,mc.datefinish) is not null and coalesce(mc.transferdate,mc.datefinish)<to_date('" + (o2[3] == null ? curDate : o2[3]) + "','dd.mm.yyyy')) then to_char(coalesce(mc.transferdate,mc.datefinish),'dd.mm.yyyy') else '" + (o2[3] == null ? "" : o2[3]) + "' end  as s7rdate" +
                            " ,list(distinct case when vdrtD.code='4' and vpdD.code='1' then mkbD.code else null end) as f8depDiag" +
                            " ,list(distinct case when vdrt.code='2' then mkb.code else null end) as f9orderDiag" +
                            " ,list(distinct case when vdrt.code='3' and vpd.code='1' then mkb.code else null end) as f10dischargeDiag" +
                            " ,case when vhr.code='11' then cast('1' as int) else null end as f11isDeath" +
                            " , case when sls.dateFinish is not null then cast(to_char(sls.dateFinish,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int) +(case when (cast(to_char(sls.dateFinish, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int) +(case when (cast(to_char(sls.dateFinish,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) else null end as f12ageDischarge" +
                            " , cast(to_char(sls.dateStart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int) +(case when (cast(to_char(sls.dateStart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int) +(case when (cast(to_char(sls.dateStart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) as f13ageEntranceSls" +
                            " , cast(to_char(mc.dateStart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int) +(case when (cast(to_char(mc.dateStart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int) +(case when (cast(to_char(mc.dateStart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) as f14ageEntranceSlo" +
                            " , case when coalesce(mc.transferDate,mc.dateFinish) is not null then cast(to_char(coalesce(mc.transferDate,mc.dateFinish),'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int) +(case when (cast(to_char(coalesce(mc.transferDate,mc.dateFinish), 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int) +(case when (cast(to_char(coalesce(mc.transferDate,mc.dateFinish),'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) end as f15ageDischarge" +
                            " , pat.birthday as f16birthday" +
                            " from medcase mc" +
                            " left join medcase sls on mc.parent_id=sls.id" +
                            " left join patient pat on mc.patient_id=sls.patient_id" +
                            " left join bedfund bf on bf.id=mc.bedfund_id" +
                            " left join vocbedsubtype vbst on bf.bedsubtype_id=vbst.id" +
                            " left join mislpu ml on ml.id=mc.department_id" +
                            " left join diagnosis diag on sls.id=diag.medcase_id" +
                            " left join vocidc10 mkb on mkb.id=diag.idc10_id" +
                            " left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id" +
                            " left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id" +
                            " left join diagnosis diagD on mc.id=diagD.medcase_id" +
                            " left join vocidc10 mkbD on mkbD.id=diagD.idc10_id" +
                            " left join VocDiagnosisRegistrationType vdrtD on vdrtD.id=diagD.registrationType_id" +
                            " left join VocPriorityDiagnosis vpdD on vpdD.id=diagD.priority_id" +
                            " left join VocHospitalizationResult  vhr on vhr.id=sls.result_id" +
                            " left join VocHospitalization  vh on vh.id=sls.hospitalization_id" +
                            " left join VocHospType vht on vht.id=sls.targetHospType_id" +
                            " left join VocHospType vhtHosp on vhtHosp.id=sls.hospType_id" +
                            " where mc.patient_id='" + obj[2] + "' and upper(mc.dtype)='DEPARTMENTMEDCASE' and case when coalesce(mc.transferdate,mc.datefinish,current_date) >= to_date('" + (o2[0] == null ? curDate : o2[0]) + "','dd.mm.yyyy') and mc.datestart<=to_date('" + (o2[3] == null ? curDate : o2[3]) + "','dd.mm.yyyy') " +
                            " then 1 else 0 end = 1" +
                            " group by mc.datestart,mc.datefinish,mc.transferdate" +
                            " ,sls.id,mc.id" +
                            " ,sls.admissionInHospital_id,vh.code" +
                            " ,sls.admissionOrder_id,vhr.code" +
                            " ,mc.department_id" +
                            " ,sls.serviceStream_id,bf.bedType_id,bf.bedSubType_id" +
                            " ,mc.entranceTime, mc.dischargeTime,sls.emergency,vht.code,mc.transferTime,vhtHosp.id,vbst.code,sls.datefinish,sls.datestart,mc.parent_id,pat.birthday " +
                            " order by mc.datestart" +
                            "";
                    List<Object[]> l3 = manager.createNativeQuery(sql3).getResultList();
                    if (l3.isEmpty()) {
                        CompulsoryTreatmentAggregate ahr = new CompulsoryTreatmentAggregate();
                        ahr.setOrderCompTr(ConvertSql.parseString(obj[0]));
                        ahr.setPatient(ConvertSql.parseLong(obj[2]));
                        ahr.setDeath(false);
                        ahr.setEntranceHospDate(ConvertSql.parseDate(o2[3] == null ? curDate : o2[3]));
                        ahr.setNumberHosp(hospInd);
                        manager.persist(ahr);
                    } else {
                        Date begDate = null;
                        Date endDate = null;
                        Object[] oF = l3.get(0);
                        Object[] oL = l3.get(l3.size() - 1);

                        if (oF[2] == null) {
                            begDate = ConvertSql.parseDate(o2[2]);
                        } else {
                            begDate = ConvertSql.parseDate(oF[3]);
                        }


                        if (oL[7] != null) {
                            endDate = ConvertSql.parseDate(oL[7]);
                        }

                        for (int ind = 0; ind < l3.size(); ind++) {

                            Object[] o3 = l3.get(ind);
                            Date begDateDep = null;
                            Date endDateDep = null;

                            if (o3[3] != null && o3[2] != null) {
                                begDateDep = ConvertSql.parseDate(o3[3]);
                            } else {
                                begDateDep = ConvertSql.parseDate(o2[2]);
                            }

                            if (o3[7] != null) {
                                endDateDep = ConvertSql.parseDate(o3[7]);
                            }

                            CompulsoryTreatmentAggregate ahr = new CompulsoryTreatmentAggregate();
                            ahr.setSls(ConvertSql.parseLong(o3[0]));
                            ahr.setSlo(ConvertSql.parseLong(o3[1]));
                            ahr.setSexCode(ConvertSql.parseString(o2[6]));
                            ahr.setOrderCompTr(ConvertSql.parseString(obj[0]));
                            ahr.setPatient(ConvertSql.parseLong(obj[2]));
                            ahr.setDepartment(ConvertSql.parseLong(o3[6]));
                            ahr.setEntranceHospDate(ConvertSql.parseDate(begDate));
                            ahr.setEntranceDepDate(ConvertSql.parseDate(begDateDep));
                            ahr.setDischargeHospDate(ConvertSql.parseDate(endDate));
                            ahr.setDischargeDepDate(ConvertSql.parseDate(endDateDep));
                            ahr.setDeath(o3[11] != null && ConvertSql.parseBoolean(o3[11]));
                            ahr.setNumberHosp(hospInd);
                            ahr.setIdcDepartmentCode(ConvertSql.parseString(o3[8]));
                            ahr.setIdcDischarge(ConvertSql.parseString(o3[10]));
                            ahr.setIdcEntranceCode(ConvertSql.parseString(o3[9]));
                            ahr.setAgeDischargeSls(ConvertSql.parseLong(o3[12]));
                            ahr.setAgeEntranceSlo(ConvertSql.parseLong(o3[13]));
                            ahr.setAgeEntranceSls(ConvertSql.parseLong(o3[14]));
                            ahr.setAgeDischargeSlo(ConvertSql.parseLong(o3[15]));
                            ahr.setBirthday(ConvertSql.parseDate(o3[16]));
                            if (ind > 0) {
                                ahr.setIdcTransferCode(ConvertSql.parseString(l3.get(ind - 1)[8], true));
                                ahr.setTransferDepartmentFrom(ConvertSql.parseLong(l3.get(ind - 1)[6]));
                            }
                            if (ind < l3.size() - 1)
                                ahr.setTransferDepartmentIn(ConvertSql.parseLong(l3.get(ind + 1)[6]));
                            manager.persist(ahr);
                        }
                    }
                }
                if (ii % 10 == 0)
                    monitor.setText("Импортируется: " + ConvertSql.parseLong(obj[0]) + " " + ConvertSql.parseLong(obj[2]) + "...");
                if (size > 0 && ii % size == 0) monitor.advice(1);

                if (ii % 80 == 0) {
                    monitor.setText("Импортировано " + ii);
                }
            }
            monitor.finish("");
        } catch (Exception e) {
            e.printStackTrace();
            if (monitor != null) monitor.setText(e + "");
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void refreshReportByPeriod(String aEntranceDate, String aDischargeDate, long aIdMonitor) {
        LOG.error("ВНИМАНИЕ!! Выполняется неизвестный код. никто не знает что он делает..");
        IMonitor monitor = null;
        try {
            monitor = monitorService.startMonitor(aIdMonitor, "Обработка данных за период: " + aEntranceDate + " " + aDischargeDate, 100);

            String sqlD = " delete from AggregateHospitalReport where entrancedate24 <= (to_date('" + aDischargeDate + "','dd.mm.yyyy')-1)" +
                    " and (dischargedate24 >= (to_date('" + aEntranceDate + "','dd.mm.yyyy')-1) or dischargedate24 is null)";
            manager.createNativeQuery(sqlD).executeUpdate();

            monitor.advice(20);

            String sql = " select pat.id as f0patid" +
                    " ,sls.id as f1slsid" +
                    " ,slo.id as f2sloid" +
                    " ,vs.omccode as f3assex" +
                    " ,slo.datestart as f4entrancedate" +
                    " ,coalesce(slo.datefinish,slo.transferdate) as f5dischargedate" +
                    " ,case when a.addressisvillage='1' then cast('1' as int) else null end as f6isvillage" +
                    " ,case when sls.admissionInHospital_id='1' then cast('1' as int) else null end as f7isFirstLife" +
                    " ,case when (vh.code='1' or vh.code='2' or sls.admissionInHospital_id='1') then cast('1' as int) else null end as f8isFirstCurrentYear" +
                    " ,case when sls.admissionOrder_id in (2,4,5,6,7,8,9) then cast('1' as int) end as f9isIncompetent" +
                    " ,case when vhr.code='11' then cast('1' as int) end as f10isDeath" +
                    " ,slo.department_id as f11slodepartment" +
                    " ,nextslo.department_id as f12nextslodepartment" +
                    " ,prevslo.department_id as f13prevslodepartment" +
                    " ,list(distinct case when vdrtD.code='4' and vpdD.code='1' then mkbD.code end) as f14depDiag" +
                    " ,list(distinct case when vdrt.code='2' then mkb.code end) as f15orderDiag" +
                    " ,list(distinct case when vdrt.code='3' and vpd.code='1' then mkb.code end) as f16dischargeDiag" +
                    " ,case when count(ahr.id)>0 then cast('1' as int) end as f17cntAggregate" +
                    " ,bf.bedType_id as f18bedType" +
                    " ,bf.bedSubType_id as f19bedSubType" +
                    " ,sls.serviceStream_id as f20serviceStream" +
                    " , case when slo.entranceTime<cast('07:00' as time) then cast('1' as int) end as f21entranceTime7" +
                    " , case when slo.entranceTime<cast('09:00' as time) then cast('1' as int) end as f22entranceTime9" +
                    " , case when coalesce(slo.dischargeTime,slo.transferTime)<cast('07:00' as time) then cast('1' as int) end as f23entranceTime7" +
                    " , case when coalesce(slo.dischargeTime,slo.transferTime)<cast('09:00' as time) then cast('1' as int) end as f24entranceTime9" +
                    " , case when sls.emergency='1' then cast('1' as int) end as f25emergency" +
                    " , cast('0' as int) as f26operation" +
                    " , vht.code as f27transferLpu" +
                    " , vhtHosp.id as f28hospTypeId" +
                    " , vs.id as f29sexId" +
                    " , case when firstSlo.entranceTime<cast('07:00' as time) then cast('1' as int) end as f30entranceTime7" +
                    " , case when firstSlo.entranceTime<cast('09:00' as time) then cast('1' as int) end as f31entranceTime9" +
                    " , firstSlo.datestart as f32entrancedate" +
                    " , case when vbst.code='1' then '0' else '1' end  as f33isdayhosp" +
                    " ,list(distinct case when prevVdrtD.code='4' and prevVpdD.code='1' then prevMkbD.code end) as f34prevdepDiag" +
                    " , case when sls.dateFinish is not null then cast(to_char(sls.dateFinish,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int) +(case when (cast(to_char(sls.dateFinish, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int) +(case when (cast(to_char(sls.dateFinish,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) end as f35ageDischarge" +
                    " , cast(to_char(sls.dateStart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int) +(case when (cast(to_char(sls.dateStart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int) +(case when (cast(to_char(sls.dateStart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) as f36ageEntranceSls" +
                    " , cast(to_char(slo.dateStart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int) +(case when (cast(to_char(slo.dateStart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int) +(case when (cast(to_char(slo.dateStart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) as f37ageEntranceSlo" +
                    " , pat.birthday as f38birthday" +
                    " from medcase sls" +
                    " left join medcase slo on sls.id=slo.parent_id" +
                    " left join medcase prevSlo on prevSlo.id=slo.prevMedCase_id" +
                    " left join diagnosis prevDiagD on prevSlo.id=prevDiagD.medcase_id" +
                    " left join vocidc10 prevMkbD on prevMkbD.id=prevDiagD.idc10_id" +
                    " left join VocDiagnosisRegistrationType prevVdrtD on prevVdrtD.id=prevDiagD.registrationType_id" +
                    " left join VocPriorityDiagnosis prevVpdD on prevVpdD.id=prevDiagD.priority_id" +
                    " left join AggregateHospitalReport ahr on ahr.slo=slo.id" +
                    " left join medcase nextSlo on nextSlo.prevmedcase_id=slo.id" +
                    " left join medcase firstSlo on sls.id=firstSlo.parent_id and firstSlo.prevmedcase_id is null" +
                    " left join patient pat on pat.id=sls.patient_id" +
                    " left join address2 a on a.addressid=pat.address_addressid" +
                    " left join vocsex vs on vs.id=pat.sex_id" +
                    " left join bedfund bf on bf.id=slo.bedfund_id" +
                    " left join vocbedsubtype vbst on bf.bedsubtype_id=vbst.id" +
                    " left join mislpu ml on ml.id=slo.department_id" +
                    " left join mislpu mlN on mlN.id=nextSlo.department_id" +
                    " left join mislpu mlP on mlP.id=prevSlo.department_id" +
                    " left join diagnosis diag on sls.id=diag.medcase_id" +
                    " left join vocidc10 mkb on mkb.id=diag.idc10_id" +
                    " left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id" +
                    " left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id" +
                    " left join diagnosis diagD on slo.id=diagD.medcase_id" +
                    " left join vocidc10 mkbD on mkbD.id=diagD.idc10_id" +
                    " left join VocDiagnosisRegistrationType vdrtD on vdrtD.id=diagD.registrationType_id" +
                    " left join VocPriorityDiagnosis vpdD on vpdD.id=diagD.priority_id" +
                    " left join VocHospitalizationResult  vhr on vhr.id=sls.result_id" +
                    " left join VocHospitalization  vh on vh.id=sls.hospitalization_id" +
                    " left join VocHospType vht on vht.id=sls.targetHospType_id" +
                    " left join VocHospType vhtHosp on vhtHosp.id=sls.hospType_id" +
                    " where sls.dtype='HospitalMedCase' and firstSlo.dtype='DepartmentMedCase'  and firstSlo.dtype='DepartmentMedCase' and sls.dateStart <= (to_date('" + aDischargeDate + "','dd.mm.yyyy')-1)" +
                    " and (sls.dateFinish >= (to_date('" + aEntranceDate + "','dd.mm.yyyy')-1) or sls.dateFinish is null)" +
                    " and sls.deniedHospitalizating_id is null" +
                    " group by vs.omccode,vs.id,slo.datestart,slo.datefinish,slo.transferdate" +
                    " ,pat.id,sls.id,slo.id,a.addressisvillage" +
                    " ,sls.admissionInHospital_id,vh.code,firstSlo.datestart,firstSlo.entranceTime" +
                    " ,sls.admissionOrder_id,vhr.code" +
                    " ,slo.department_id,nextslo.department_id,prevslo.department_id" +
                    " ,sls.serviceStream_id,bf.bedType_id,bf.bedSubType_id, pat.birthday" +
                    " ,slo.entranceTime, slo.dischargeTime,sls.emergency,vht.code,slo.transferTime,vhtHosp.id,vbst.code,sls.datefinish,pat.birthday,sls.datestart " +
                    " order by sls.id";
            List<Object[]> list = manager.createNativeQuery(sql).getResultList();
            int size = list.size() / 80;

            for (int i = 0; i < list.size(); i++) {
                Object[] obj = list.get(i);
                if (monitor.isCancelled()) {
                    throw new IllegalMonitorStateException("Прервано пользователем");
                }
                AggregateHospitalReport ahr = new AggregateHospitalReport();
                ahr.setAgeDischargeSls(ConvertSql.parseLong(obj[35]));
                ahr.setAgeEntranceSlo(ConvertSql.parseLong(obj[37]));
                ahr.setAgeEntranceSls(ConvertSql.parseLong(obj[36]));
                ahr.setBedSubType(ConvertSql.parseLong(obj[19]));
                ahr.setBedType(ConvertSql.parseLong(obj[18]));
                ahr.setCntDaysSls(ConvertSql.parseLong(0));
                ahr.setDepartment(ConvertSql.parseLong(obj[11]));
                ahr.setDischargeDate24(ConvertSql.parseDate(obj[5]));
                ahr.setDischargeDate7(ConvertSql.parseDate(obj[5], ConvertSql.parseBoolean(obj[21]) ? -1 : 0));
                ahr.setDischargeDate9(ConvertSql.parseDate(obj[5], ConvertSql.parseBoolean(obj[22]) ? -1 : 0));
                ahr.setEntranceDate24(ConvertSql.parseDate(obj[4]));
                ahr.setEntranceDate7(ConvertSql.parseDate(obj[4], ConvertSql.parseBoolean(obj[23]) ? -1 : 0));
                ahr.setEntranceDate9(ConvertSql.parseDate(obj[4], ConvertSql.parseBoolean(obj[24]) ? -1 : 0));
                ahr.setEntranceHospDate24(ConvertSql.parseDate(obj[32]));
                ahr.setEntranceHospDate7(ConvertSql.parseDate(obj[32], ConvertSql.parseBoolean(obj[30]) ? -1 : 0));
                ahr.setEntranceHospDate9(ConvertSql.parseDate(obj[32], ConvertSql.parseBoolean(obj[31]) ? -1 : 0));
                ahr.setIdcDepartmentCode(ConvertSql.parseString(obj[14]));
                ahr.setIdcDischarge(ConvertSql.parseString(obj[16]));
                ahr.setIdcEntranceCode(ConvertSql.parseString(obj[15]));
                ahr.setIdcTransferCode(ConvertSql.parseString(obj[34]));
                ahr.setDeath(ConvertSql.parseBoolean(obj[10]));
                ahr.setEmergency(ConvertSql.parseBoolean(obj[25]));
                ahr.setFirstCurrentYear(ConvertSql.parseBoolean(obj[8]));
                ahr.setFirstLife(ConvertSql.parseBoolean(obj[7]));
                ahr.setIncompetent(ConvertSql.parseBoolean(obj[9]));
                ahr.setOperation(ConvertSql.parseBoolean(obj[26]));
                ahr.setVillage(ConvertSql.parseBoolean(obj[8]));
                ahr.setPatient(ConvertSql.parseLong(obj[0]));
                ahr.setServiceStream(ConvertSql.parseLong(obj[20]));
                ahr.setSexCode(ConvertSql.parseString(obj[3]));
                ahr.setSlo(ConvertSql.parseLong(obj[2]));
                ahr.setSls(ConvertSql.parseLong(obj[1]));
                ahr.setTransferDepartmentFrom(ConvertSql.parseLong(obj[13]));
                ahr.setTransferDepartmentIn(ConvertSql.parseLong(obj[12]));
                ahr.setTransferLpuCode(ConvertSql.parseString(obj[27]));
                ahr.setHospType(ConvertSql.parseLong(obj[28]));
                ahr.setSex(ConvertSql.parseLong(obj[29]));
                ahr.setAddBedDays(ConvertSql.parseLong(obj[33]));
                ahr.setBirthday(ConvertSql.parseDate(obj[38]));
                manager.persist(ahr);
                if (i % 10 == 0)
                    monitor.setText("Импортируется: " + ConvertSql.parseLong(obj[0]) + " " + ConvertSql.parseLong(obj[2]) + "...");
                if (i % size == 0) monitor.advice(1);

                if (i % 300 == 0) {
                    monitor.setText("Импортировано " + i);
                }
            }
            monitor.finish("");
        } catch (Exception e) {
            if (monitor != null) monitor.setText(e + "");
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void createNewDiary(String aTitle, String aText, String aUsername) {
        TemplateProtocol protocol = new TemplateProtocol();
        protocol.setText(aText);
        protocol.setTitle(aTitle);
        protocol.setUsername(aUsername);
        protocol.setDate(new java.sql.Date(new java.util.Date().getTime()));
        manager.persist(protocol);
    }

    @Override
    public void updateDataFromParameterConfig(Long aDepartment, boolean aIsLowerCase, String aIds, boolean aIsRemoveExist) {
        String[] obj = aIds.split(",");
        String aTableSql = "VocDocumentParameterConfig where department_id='" + aDepartment + "' and documentParameter_id ";
        MisLpu department = manager.find(MisLpu.class, aDepartment);
        for (String jsId : obj) {
            if (jsId != null && !jsId.equals("") && !jsId.equals("0")) {

                Long jsonId = Long.valueOf(jsId);

                String sql = "from " + aTableSql + " ='" + jsonId + "' ";
                List<VocDocumentParameterConfig> count = manager.createQuery(sql).setMaxResults(1).getResultList();
                VocDocumentParameterConfig vdpc;
                if (count.isEmpty()) {
                    VocDocumentParameter documentParameter = manager.find(VocDocumentParameter.class, jsonId);
                    vdpc = new VocDocumentParameterConfig();
                    vdpc.setDepartment(department);
                    vdpc.setDocumentParameter(documentParameter);
                } else {
                    vdpc = count.get(0);
                }
                vdpc.setIsLowerCase(aIsLowerCase);
                manager.persist(vdpc);
            }
        }
        if (aIsRemoveExist && aIds.length() > 0) {
            String sql = "delete " + aTableSql + " not in (" + aIds + ") ";
            manager.createNativeQuery(sql).executeUpdate();
        }
    }

    @Override
    public void removeDataFromParameterConfig(Long aDepartment, String aIds) {
        String aTableSql = "VocDocumentParameterConfig where department_id='" + aDepartment + "' and documentParameter_id ";
        String sql = "delete from " + aTableSql + " in (" + aIds + ") ";
        manager.createNativeQuery(sql).executeUpdate();
    }

    @Override
    public void changeServiceStreamBySmo(Long aSmo, Long aServiceStream) {
        List<Object[]> list = manager.createNativeQuery("select sls.dtype,count(distinct slo.id) from medcase sls" +
                " left join MedCase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'" +
                " where sls.id=" + aSmo + " group by sls.id,sls.dtype").getResultList();
        if (!list.isEmpty()) {
            Object[] obj = list.get(0);
            if (obj[0] != null) {
                String dtype = obj[0].toString();
                switch (dtype) {
                    case "HospitalMedCase": {
                        List<Object[]> listBedFund = manager.createNativeQuery("select slo.id as sloid,bfNew.id as bfNewid from MedCase slo"
                                        + " left join BedFund bf on bf.id=slo.bedFund_id"
                                        + " left join BedFund bfNew on bfNew.lpu_id=bf.lpu_id"
                                        + " where slo.parent_id='" + aSmo + "' and slo.dtype='DepartmentMedCase'"
                                        + " and bfNew.bedSubType_id = bf.bedSubType_id"
                                        + " and bfNew.bedType_id = bf.bedType_id"
                                        + " and bfNew.serviceStream_id = '" + aServiceStream + "' and slo.dateStart between bfNew.dateStart and coalesce(bfNew.dateFinish,CURRENT_DATE)")
                                .getResultList();
                        Long cntSlo = ConvertSql.parseLong(obj[1]);
                        if (cntSlo.intValue() == listBedFund.size()) {
                            for (Object[] slo : listBedFund) {
                                manager.createNativeQuery("update MedCase set serviceStream_id='" + aServiceStream + "',bedFund_id='" + slo[1] + "' where id='" + slo[0] + "'").executeUpdate();
                                manager.createNativeQuery("update SurgicalOperation set serviceStream_id='" + aServiceStream + "' where medCase_id='" + slo[0] + "'").executeUpdate();
                            }
                            manager.createNativeQuery("update MedCase set serviceStream_id='" + aServiceStream + "' where id='" + aSmo + "'").executeUpdate();
                            manager.createNativeQuery("update SurgicalOperation set serviceStream_id='" + aServiceStream + "' where medCase_id='" + aSmo + "'").executeUpdate();
                        } else {
                            throw new IllegalArgumentException("По данному потоку обслуживания не во всех отделениях, в которых производилось лечение, заведен коечный фонд");
                        }
                        break;
                    }
                    case "DepartmentMedCase": { // не будет случаться, убрать
                        List<Object[]> listBedFund = manager.createNativeQuery("select slo.id as sloid,bfNew.id as bfNewid from MedCase slo"
                                        + " left join BedFund bf on bf.id=slo.bedFund_id"
                                        + " left join BedFund bfNew on bfNew.lpu_id=bf.lpu_id"
                                        + " where slo.id='" + aSmo + "' and slo.dtype='DepartmentMedCase'"
                                        + " and bfNew.bedSubType_id = bf.bedSubType_id"
                                        + " and bfNew.bedType_id = bf.bedType_id"
                                        + " and bfNew.serviceStream_id = '" + aServiceStream + "' and slo.dateStart between bfNew.dateStart and coalesce(bfNew.dateFinish,CURRENT_DATE)")
                                .getResultList();
                        Object[] slo = listBedFund.get(0);
                        if (listBedFund.size() == 1) {
                            manager.createNativeQuery("update MedCase set serviceStream_id='" + aServiceStream + "',bedFund_id='" + slo[1] + "' where id='" + aSmo + "'").executeUpdate();
                            manager.createNativeQuery("update SurgicalOperation set serviceStream_id='" + aServiceStream + "' where medCase_id='" + aSmo + "'").executeUpdate();
                        } else {
                            throw new IllegalArgumentException("По данному потоку обслуживания в отделение не заведен коечный фонд");
                        }
                        break;
                    }
                    case "Visit":
                        manager.createNativeQuery("update MedCase set serviceStream_id='" + aServiceStream + "' where id='" + aSmo + "'").executeUpdate();
                        break;
                    case "PolyclinicMedCase":
                        manager.createNativeQuery("update MedCase set serviceStream_id='" + aServiceStream + "' where parent_id='" + aSmo + "' and (dtype='Visit' or dtype='ShortMedCase')").executeUpdate();
                        //Milamesher update spo after visits
                        manager.createNativeQuery("update MedCase set serviceStream_id='" + aServiceStream + "' where id='" + aSmo + "'").executeUpdate();
                        break;
                }
            }
        }
    }

    @Override
    public void unionSloWithNextSlo(Long aSlo) {
        List<Object[]> list = manager.createNativeQuery("select  "
                        + "case when sloNext1.department_id is not null and"
                        + " sloNext1.department_id=slo.department_id then '1' end equalsDep"
                        + " ,sloNext.id as sloNext,sloNext1.id as sloNext1,sloNext2.id as sloNext2"
                        + " ,sloNext.dateFinish as sloNextDateFinish,sloNext.dischargeTime as sloNextDischargeTime"
                        + " ,sloNext.transferDate as sloNextTransferDate,sloNext.transferTime as sloNextTransferTime"
                        + " ,sloNext1.dateFinish as sloNext1DateFinish,sloNext1.dischargeTime as sloNext1DischargeTime"
                        + " ,sloNext1.transferDate as sloNext1TransferDate,sloNext1.transferTime as sloNext1TransferTime"

                        + " from medcase slo"
                        + " left join MedCase sloNext on sloNext.prevMedCase_id=slo.id"
                        + " left join MedCase sloNext1 on sloNext1.prevMedCase_id=sloNext.id"
                        + " left join MedCase sloNext2 on sloNext2.prevMedCase_id=sloNext1.id"
                        + " where slo.id='" + aSlo + "'")
                .getResultList();
        if (!list.isEmpty()) {
            Object[] obj = list.get(0);
            if (obj[1] != null) {
                if (obj[0] != null) { // если КАРДИО -  невр - КАРДИО
                    // Отд next1=current (объединять 2 отделения)
                    manager.createNativeQuery("update assessmentCard cb set medcase_id='" + aSlo + "' where cb.medCase_id='" + obj[1] + "' or medcase_id=" + obj[2]).executeUpdate();
                    manager.createNativeQuery("update qualityestimationcard set medcase_id='" + aSlo + "' where medCase_id='" + obj[1] + "' or medcase_id=" + obj[2]).executeUpdate();
                    manager.createNativeQuery("update childBirth cb set medcase_id='" + aSlo + "' where cb.medCase_id='" + obj[1] + "' or medcase_id=" + obj[2]).executeUpdate();
                    manager.createNativeQuery("update newBorn cb set medcase_id='" + aSlo + "' where cb.medCase_id='" + obj[1] + "' or medcase_id=" + obj[2]).executeUpdate();
                    manager.createNativeQuery("update medcase  set parent_id='" + aSlo + "' where parent_id='" + obj[1] + "' or parent_id=" + obj[2]).executeUpdate();

                    //Закрытие диет и Режимов из объединяемого.Копирование назначений из листа назначения объединяемого СЛО и затем его удаление.
                    manager.createNativeQuery("update prescription" +
                            " set planEndDate=planStartDate, planEndTime=planStartTime" +
                            " where prescriptionlist_id in (select id from prescriptionlist where medcase_id = " + obj[1] + " or medcase_id=" + aSlo + ") and (dtype='DietPrescription' or dtype='ModePrescription')").executeUpdate();
                    manager.createNativeQuery("update prescription " +
                            " set prescriptionlist_id = (select id from prescriptionlist where medcase_id = " + aSlo + ") " +
                            " where prescriptionlist_id in (select id from prescriptionlist where medcase_id = '" + obj[2] + "' or medcase_id=" + obj[1] + ")").executeUpdate();
                    manager.createNativeQuery("delete from prescriptionlist where medcase_id =" + obj[2] + " or medcase_id=" + obj[1]).executeUpdate();
                    manager.createNativeQuery("update screeningcardiac cd set medcase_id='" + aSlo + "' where cd.medCase_id='" + obj[1] + "' or medcase_id=" + obj[2]).executeUpdate();
                    manager.createNativeQuery("update diary d set medcase_id='" + aSlo + "' where d.medCase_id='" + obj[1] + "' or medcase_id=" + obj[2]).executeUpdate();
                    manager.createNativeQuery("update diagnosis d set medcase_id='" + aSlo + "' where d.medCase_id='" + obj[1] + "' or medcase_id=" + obj[2]).executeUpdate();
                    manager.createNativeQuery("update SurgicalOperation d set medcase_id='" + aSlo + "' where d.medCase_id='" + obj[1] + "' or medcase_id=" + obj[2]).executeUpdate();
                    manager.createNativeQuery("update ClinicExpertCard d set medcase_id='" + aSlo + "' where d.medCase_id='" + obj[1] + "' or medcase_id=" + obj[2]).executeUpdate();
                    manager.createNativeQuery("update transfusion d set medcase_id='" + aSlo + "' where d.medCase_id='" + obj[1] + "' or medcase_id=" + obj[2]).executeUpdate();
                    manager.createNativeQuery("update hitechmedicalcase d set medcase_id='" + aSlo + "' where d.medCase_id='" + obj[1] + "' or medcase_id=" + obj[2]).executeUpdate();
                    manager.createNativeQuery("update robsonclass d set medcase_id='" + aSlo + "' where d.medCase_id='" + obj[1] + "' or medcase_id=" + obj[2]).executeUpdate();
                    manager.createNativeQuery("update calculationsresult cr set departmentmedcase_id='" + aSlo + "' where cr.departmentmedcase_id='" + obj[1] + "' or departmentmedcase_id=" + obj[2]).executeUpdate();
                    manager.createNativeQuery("update temperaturecurve d set medcase_id='" + aSlo + "' where d.medCase_id='" + obj[1] + "' or medcase_id=" + obj[2]).executeUpdate();
                    manager.createNativeQuery("update medcase set dateFinish=(select dateFinish from medcase where id='" + obj[2] + "') "
                            + " ,transferDate=(select transferDate from medcase where id='" + obj[2] + "')"
                            + " ,transferTime=(select transferTime from medcase where id='" + obj[2] + "')"
                            + " ,dischargeTime=(select dischargeTime from medcase where id='" + obj[2] + "')"
                            + " ,transferDepartment_id=(select transferDepartment_id from medcase where id='" + obj[2] + "')"
                            + " ,targetHospType_id=(select targetHospType_id from medcase where id='" + obj[2] + "')"
                            + " where id='" + aSlo + "'").executeUpdate();
                    if (obj[3] != null) {
                        manager.createNativeQuery("update MedCase set prevMedCase_id='" + aSlo + "' where id='" + obj[3] + "'").executeUpdate();
                    }
                    manager.createNativeQuery("delete from medcase m where m.id='" + obj[2] + "'").executeUpdate();
                    manager.createNativeQuery("delete from medcase m where m.id='" + obj[1] + "'").executeUpdate();
                } else {
                    manager.createNativeQuery("update robsonclass d set medcase_id='" + aSlo + "' where d.medCase_id='" + obj[1] + "'").executeUpdate();
                    manager.createNativeQuery("update assessmentCard cb set medcase_id='" + aSlo + "' where cb.medCase_id='" + obj[1] + "'").executeUpdate();
                    manager.createNativeQuery("update qualityestimationcard set medcase_id='" + aSlo + "' where medCase_id='" + obj[1] + "'").executeUpdate();
                    manager.createNativeQuery("update childBirth cb set medcase_id='" + aSlo + "' where cb.medCase_id='" + obj[1] + "' and '1'=(select case when dep.isMaternityWard='1' then '1' else '0' end from medcase slo left join mislpu dep on dep.id=slo.department_id where slo.id='" + aSlo + "')").executeUpdate();
                    manager.createNativeQuery("update newBorn nb    set medcase_id='" + aSlo + "' where nb.medCase_id='" + obj[1] + "' and '1'=(select case when dep.isMaternityWard='1' then '1' else '0' end from medcase slo left join mislpu dep on dep.id=slo.department_id where slo.id='" + aSlo + "')").executeUpdate();
                    manager.createNativeQuery("update medcase  set parent_id='" + aSlo + "' where parent_id='" + obj[1] + "'").executeUpdate();

                    manager.createNativeQuery("update prescription " +
                            "set planEndDate=planStartDate, planEndTime=planStartTime " +
                            "where (dtype='DietPrescription' or dtype='ModePrescription') " +
                            "and prescriptionlist_id = (select id from prescriptionlist where medcase_id = '" + aSlo + "')").executeUpdate();
                    manager.createNativeQuery("update prescription " +
                            "set prescriptionlist_id = (select id from prescriptionlist where medcase_id = '" + aSlo + "') " +
                            "where prescriptionlist_id in (select id from prescriptionlist where medcase_id = '" + obj[1] + "')").executeUpdate();
                    manager.createNativeQuery("delete from prescriptionlist where medcase_id ='" + obj[1] + "'").executeUpdate();


                    manager.createNativeQuery("update screeningcardiac cd set medcase_id='" + aSlo + "' where cd.medCase_id='" + obj[1] + "'").executeUpdate();
                    manager.createNativeQuery("update diary d set medcase_id='" + aSlo + "' where d.medCase_id='" + obj[1] + "'").executeUpdate();
                    manager.createNativeQuery("update diagnosis d set medcase_id='" + aSlo + "' where d.medCase_id='" + obj[1] + "'").executeUpdate();
                    manager.createNativeQuery("update SurgicalOperation d set medcase_id='" + aSlo + "' where d.medCase_id='" + obj[1] + "'").executeUpdate();
                    manager.createNativeQuery("update ClinicExpertCard d set medcase_id='" + aSlo + "' where d.medCase_id='" + obj[1] + "'").executeUpdate();
                    manager.createNativeQuery("update transfusion d set medcase_id='" + aSlo + "' where d.medCase_id='" + obj[1] + "'").executeUpdate();
                    manager.createNativeQuery("update hitechmedicalcase d set medcase_id='" + aSlo + "' where d.medCase_id='" + obj[1] + "'").executeUpdate();
                    manager.createNativeQuery("update temperaturecurve d set medcase_id='" + aSlo + "' where d.medCase_id='" + obj[1] + "'").executeUpdate();
                    manager.createNativeQuery("update medcase set dateFinish=(select dateFinish from medcase where id='" + obj[1] + "') "
                            + " ,transferDate=(select transferDate from medcase where id='" + obj[1] + "')"
                            + " ,transferTime=(select transferTime from medcase where id='" + obj[1] + "')"
                            + " ,dischargeTime=(select dischargeTime from medcase where id='" + obj[1] + "')"
                            + " ,transferDepartment_id=(select transferDepartment_id from medcase where id='" + obj[1] + "')"
                            + " ,targetHospType_id=(select targetHospType_id from medcase where id='" + obj[1] + "')"
                            + " where id='" + aSlo + "'").executeUpdate();
                    if (obj[2] != null) {
                        manager.createNativeQuery("update MedCase set prevMedCase_id='" + aSlo + "' where id='" + obj[2] + "'").executeUpdate();
                    }
                    if (!manager.createNativeQuery("select id from newborn where medcase_id='" + obj[1] + "'").getResultList().isEmpty()
                            || !manager.createNativeQuery("select id from childbirth where medcase_id='" + obj[1] + "'").getResultList().isEmpty()) {
                        throw new IllegalArgumentException("Невозможность объединить СЛО, т.к. имеются данные по родам!");
                    }
                    manager.createNativeQuery("update calculationsresult cr set departmentmedcase_id='" + aSlo + "' where cr.departmentmedcase_id='" + obj[1] + "'").executeUpdate();
                    manager.createNativeQuery("delete from medcase m where m.id='" + obj[1] + "'").executeUpdate();
                }
            } else {
                throw new IllegalArgumentException("Нет след. СЛО");
            }
        }
    }

    @Override
    public void deniedHospitalizatingSls(Long aMedCaseId, Long aDeniedHospitalizating) {
        manager.createNativeQuery("update diary d set medcase_id='" + aMedCaseId + "' where d.medCase_id in (select m.id from medcase m where m.parent_id='" + aMedCaseId + "')").executeUpdate();
        manager.createNativeQuery("update diagnosis d set medcase_id='" + aMedCaseId + "' where d.medCase_id in (select m.id from medcase m where m.parent_id='" + aMedCaseId + "')").executeUpdate();
        manager.createNativeQuery("update SurgicalOperation d set medcase_id='" + aMedCaseId + "' where d.medCase_id in (select m.id from medcase m where m.parent_id='" + aMedCaseId + "')").executeUpdate();
        manager.createNativeQuery("update ClinicExpertCard d set medcase_id='" + aMedCaseId + "' where d.medCase_id in (select m.id from medcase m where m.parent_id='" + aMedCaseId + "')").executeUpdate();
        manager.createNativeQuery("update PrescriptionList d set medcase_id='" + aMedCaseId + "' where d.medCase_id in (select m.id from medcase m where m.parent_id='" + aMedCaseId + "')").executeUpdate();
        manager.createNativeQuery("delete from medcase m where m.parent_id='" + aMedCaseId + "' and m.dtype='DepartmentMedCase'").executeUpdate();
        manager.createNativeQuery("update medcase set deniedHospitalizating_id='" + aDeniedHospitalizating + "',ambulanceTreatment='1' where id='" + aMedCaseId + "'").executeUpdate();
        HospitalMedCase medCase = manager.find(HospitalMedCase.class, aMedCaseId);
        StatisticStubStac.removeStatCardNumber(manager, context, medCase);
    }

    @Override
    public void preRecordDischarge(Long aMedCaseId, String aDischargeEpicrisis) {
        saveDischargeEpicrisis(aMedCaseId, aDischargeEpicrisis, manager);
    }

    @Override
    public void updateDischargeDateByInformationBesk(String aIds, String aDate) {
        String[] ids = aIds.split(",");
        for (String id : ids) {
            Object cnt = manager.createNativeQuery("select count(*) from medcase where id='" + id + "' and dateStart<=to_date('" + aDate + "','dd.mm.yyyy') and dischargeTime is null and deniedHospitalizating_id is null")
                    .getSingleResult();
            Long cntL = ConvertSql.parseLong(cnt);
            if (cntL > 0L) {
                manager.createNativeQuery("update MedCase set dateFinish=to_date('" + aDate + "','dd.mm.yyyy') where id='" + id + "' and dtype='HospitalMedCase' and dischargeTime is null")
                        .executeUpdate();
            } else {
                List<Object[]> list = manager.createNativeQuery("select p.lastname||' '||p.firstname||' '||coalesce(p.middlename)||' '||to_char(p.birthday,'dd.mm.yyyy'),ss.code,case when sls.deniedHospitalizating_id is not null then 'при отказе от госпитализации дата выписки не ставится' when sls.dischargeTime is not null then 'Изменение даты выписки у оформленных историй болезни производится через выписку' when sls.dateStart>to_date('" + aDate + "','dd.mm.yyyy') then 'Дата выписки должна быть больше, чем дата поступления' else '' end from medcase sls left join patient p on p.id=sls.patient_id left join statisticstub ss on ss.id=sls.statisticstub_id where sls.id='" + id + "'")
                        .getResultList();
                if (!list.isEmpty()) {
                    Object[] objs = list.get(0);
                    throw new IllegalArgumentException(
                            "Пациент:" + objs[0] +
                                    (objs[1] != null ? " стат.карта №" + objs[1] : "") +
                                    " ОШИБКА:" + objs[2]
                    );
                }
            }
        }
    }

    private final Map<Long, String> addressFullNameMap = new HashMap<>();
    private String addressInfo(EntityManager aManager, Long aAddressId, Object[] aAddress) {
        if (addressFullNameMap.containsKey(aAddressId)) {
            return addressFullNameMap.get(aAddressId);
        }
        String fullname = aAddress[1] == null ? "" : aAddress[1].toString().trim();
        if (aAddress[1] != null && !fullname.equals("")) {
            addressFullNameMap.put(aAddressId, fullname);
            return fullname;
        }
        StringBuilder sb = new StringBuilder();
        sb.insert(0, aAddress[2]);
        if (aAddress[3] != null) {
            String shortName = aAddress[3] + " ";
            sb.insert(0, shortName);
        }

        if (aAddress[4] != null) {
            Long id1 = ConvertSql.parseLong(aAddress[4]);
            List<Object[]> list = manager.createNativeQuery("select a.addressid,a.fullname,a.name,att.shortName,a.parent_addressid from address2 a" +
                            " left join AddressType att on att.id=a.type_id" +
                            " where a.addressid=:parentId")
                    .setParameter("parentId", id1)
                    .setMaxResults(1)
                    .getResultList();
            if (!list.isEmpty()) {
                sb.insert(0, ", ");
                sb.insert(0, addressInfo(aManager, id1, list.get(0)));
            }
        }
        String calcedFullName = sb.toString().trim();
        aManager.createNativeQuery("update Address2 set fullname='" + calcedFullName + "' where addressid=" + aAddressId).executeUpdate();
        addressFullNameMap.put(aAddressId, calcedFullName);
        return calcedFullName;
    }

    @Override
    public void addressClear() {
        manager.createNativeQuery("update Address2 set fullname=null").executeUpdate();
    }

    @Override
    public long addressUpdate(long id) {
        List<Object[]> list;
        list = manager.createNativeQuery("select a.addressid,a.fullname,a.name,att.shortName,a.parent_addressid from address2 a left join AddressType att on att.id=a.type_id where a.addressid>" + id + " and a.fullname is null order by a.addressid")
                .setMaxResults(450)
                .getResultList();
        if (!list.isEmpty()) {
            for (Object[] adr : list) {
                id = ConvertSql.parseLong(adr[0]);
                addressInfo(manager, id, adr);
            }
        } else {
            id = -1;
            addressFullNameMap.clear();
        }
        return id;
    }

    @Override
    public String getTypeDiagByAccoucheur() {
        StringBuilder ret = new StringBuilder();
        List<VocPrimaryDiagnosis> prDiag = manager.createQuery("from VocPrimaryDiagnosis where code=1").getResultList();
        List<VocAcuityDiagnosis> actDiag = manager.createQuery("from VocAcuityDiagnosis where code=1 or omcCode=1").getResultList();
        List<VocDiagnosisRegistrationType> regTypeDiag = manager.createQuery("from VocDiagnosisRegistrationType where code=4").getResultList();
        if (!prDiag.isEmpty()) {
            VocPrimaryDiagnosis pr = prDiag.get(0);
            ret.append(pr.getId()).append("#").append(pr.getName()).append("#");
        } else {
            ret.append("##");
        }
        if (!actDiag.isEmpty()) {
            VocAcuityDiagnosis act = actDiag.get(0);
            ret.append(act.getId()).append("#").append(act.getName()).append("#");
        } else {
            ret.append("##");
        }
        if (!regTypeDiag.isEmpty()) {
            VocDiagnosisRegistrationType regType = regTypeDiag.get(0);
            ret.append(regType.getId()).append("#").append(regType.getName());
        } else {
            ret.append("#");
        }
        return ret.toString();
    }

    @Override
    public String findDoubleServiceByPatient(Long aMedService, Long aPatient, Long aService, String aDate) throws ParseException {
        StringBuilder sql = new StringBuilder();
        Date date = DateFormat.parseSqlDate(aDate);
        sql.append("select smc.id,to_char(smc.dateExecute,'dd.mm.yyyy') as dateexecute,smc.timeExecute,vss.name,'Оказана в '|| case when p.DTYPE='DepartmentMedCase' then ' отделении '||d.name when p.DTYPE='HospitalMedCase' then 'приемном отделении ' else 'поликлинике' end from medcase as smc ")
                .append(" left join medcase as p on p.id=smc.parent_id ")
                .append(" left join mislpu as d on d.id=p.department_id ")
                .append(" left join vocservicestream as vss on vss.id=smc.servicestream_id")
                .append(" where smc.patient_id=:pat and smc.DTYPE='ServiceMedCase' and smc.medService_id=:usl and smc.dateExecute=:dat");
        if (aMedService != null && aMedService > 0) {
            sql.append(" and smc.id!=").append(aMedService);
        }
        List<Object[]> doubles = manager.createNativeQuery(
                        sql.toString())
                .setParameter("pat", aPatient)
                .setParameter("usl", aService)
                .setParameter("dat", date)
                .getResultList();
        if (!doubles.isEmpty()) {
            StringBuilder ret = new StringBuilder();
            ret.append("<br/><ol>");
            for (Object[] res : doubles) {
                ret.append("<li>")
                        .append("<a href='entitySubclassView-mis_medCase.do?id=").append(res[0]).append("'>")
                        .append(res[1]).append(" ").append(res[2]).append(" ").append(res[3]).append(" ").append(res[4])
                        .append("</a>")
                        .append("</li>");
            }
            ret.append("</ol><br/>");
            return ret.toString();
        } else {
            return null;
        }


    }

    @Override
    public String findDoubleOperationByPatient(Long aSurOperation, Long aParentMedCase, Long aOperation, String aDate) throws ParseException {
        StringBuilder sql = new StringBuilder();
        Date date = DateFormat.parseSqlDate(aDate);
        sql.append("select so.id,to_char(so.operationDate,'dd.mm.yyyy'),so.operationTime,to_char(so.operationDateTo,'dd.mm.yyyy'),so.operationTimeTo,'Зарегистрирована в '|| case when p.DTYPE='DepartmentMedCase' then ' отделении '||d.name when p.DTYPE='HospitalMedCase' then 'приемном отделении ' else 'поликлинике' end ")
                .append(" from medcase as mc")
                .append(" left join SurgicalOperation as so on so.patient_id=mc.patient_id")
                .append(" left join medcase as p on p.id=so.medcase_id ")
                .append(" left join mislpu as d on d.id=p.department_id ")
                .append(" where mc.id=:mcid and so.medService_id=:usl and so.operationDate=:dat");
        if (aSurOperation != null && aSurOperation > 0) {
            sql.append(" and so.id!='").append(aSurOperation).append("'");
        }

        List<Object[]> doubles = manager.createNativeQuery(
                        sql.toString())
                .setParameter("mcid", aParentMedCase)
                .setParameter("usl", aOperation)
                .setParameter("dat", date)
                .getResultList();

        if (!doubles.isEmpty()) {
            StringBuilder ret = new StringBuilder();
            ret.append("<br/><ol>");
            for (Object[] res : doubles) {
                ret.append("<li>")
                        .append("<a href='entityView-stac_surOperation.do?id=").append(res[0]).append("'>")
                        .append(res[1]).append(" ").append(res[2]).append("-").append(res[3]).append(" ").append(res[4]).append(" ").append(res[5])
                        .append("</a>")
                        .append("</li>");
            }
            ret.append("</ol><br/>");
            return ret.toString();
        } else {
            return null;
        }
    }

    @Override
    public String deleteDataDischarge(Long aMedCaseId) {
        String sql = "update MedCase set dischargeTime=null,dateFinish=null" +
                " where (id=:idMC and DTYPE='HospitalMedCase')" +
                " or (parent_id=:idMC and DTYPE='DepartmentMedCase' and dateFinish is not null)";
        int result = manager.createNativeQuery(sql).setParameter("idMC", aMedCaseId).executeUpdate();
        return "Запрос выполнен: " + result;
    }

    @Override
    public void setPatientByExternalMedservice(String aNumberDoc, String aOrderDate, String aPatient) {
        manager.createNativeQuery("update Document set patient_id='" + aPatient + "' where NumberDoc='" + aNumberDoc + "' and OrderDate=to_date('" + aOrderDate + "','dd.mm.yyyy')").executeUpdate();
    }

    /**
     * Есть ли открытый случай лечения в отделении
     *
     * @param aIdSls
     * @return
     */
    @Override
    public String isOpenningSlo(long aIdSls) {
        String sql = "select mc.id,ml.name from MedCase as mc" +
                " left join MisLpu as ml on ml.id=mc.department_id" +
                " where mc.parent_id=:idsls and mc.DTYPE='DepartmentMedCase' and mc.dateFinish is null and mc.transferDate is null";
        List<Object[]> list = manager.createNativeQuery(sql).setParameter("idsls", aIdSls).getResultList();
        if (!list.isEmpty()) {
            StringBuilder ret = new StringBuilder();
            Object[] row = list.get(0);
            ret.append(" СЛО №").append(row[0]).append(" отделение: ").append(row[1]);
            return ret.toString();
        }
        return "";

    }

    @Override
    public Long getPatient(long aIdSsl) {
        HospitalMedCase hospital = manager.find(HospitalMedCase.class, aIdSsl);
        if (hospital == null) throw new IllegalArgumentException("Нет стационарного случая лечения с ИД " + aIdSsl);
        return hospital.getPatient().getId();
    }

    @Override
    public String getChangeStatCardNumber(Long aMedCase, String aNewStatCardNumber, boolean aAlwaysCreate) {
        HospitalMedCase hospital = manager.find(HospitalMedCase.class, aMedCase);
        try {
            if (!aAlwaysCreate) {
                if (hospital.getDeniedHospitalizating() != null) {
                    throw new IllegalArgumentException("Нельзя изменить номер стат.карты при отказе госпитализации");
                }
            }
            StatisticStubStac.changeStatCardNumber(aMedCase, aNewStatCardNumber, manager, context);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return hospital.getStatCardNumber();
    }

    @Override
    public Collection<MedPolicyForm> listPolicies(Long aMedCase) {
        HospitalMedCase hospital = manager.find(HospitalMedCase.class, aMedCase);
        List<MedPolicy> listPolicies = new ArrayList<>();
        List<MedCaseMedPolicy> hospmp = manager.createQuery("from MedCaseMedPolicy where medCase=:mc").setParameter("mc", hospital).getResultList();
        for (MedCaseMedPolicy mp : hospmp) {
            listPolicies.add(mp.getPolicies());
        }
        return convert(listPolicies);
    }

    @Override
    public Collection<MedPolicyForm> listPoliciesToAdd(Long aMedCase) {
        HospitalMedCase hospital = manager.find(HospitalMedCase.class, aMedCase);
        List<Object[]> listPolicies = manager.createNativeQuery("select p.id,count(case when mp.medCase_id='"
                        + aMedCase
                        + "' then 1 end) from MedPolicy p left join MedCase_MedPolicy mp on p.id=mp.policies_id left join MedCase m on m.id=mp.medCase_id where p.patient_id='"
                        + hospital.getPatient().getId() + "' group by p.id")
                .getResultList();
        List<MedPolicy> allPolicies = new ArrayList<>();
        for (Object[] obj : listPolicies) {
            Long cnt = ConvertSql.parseLong(obj[1]);
            if (cnt == null || cnt.equals(0L)) {
                Long pId = ConvertSql.parseLong(obj[0]);
                MedPolicy p = manager.find(MedPolicy.class, pId);
                allPolicies.add(p);
            }
        }
        return convert(allPolicies);
    }

    @Override
    public void addPolicies(long aMedCaseId, long[] aPolicies) {
        HospitalMedCase hospital = manager.find(HospitalMedCase.class, aMedCaseId);
        for (long policyId : aPolicies) {
            if (!checkExistsAttachedPolicy(aMedCaseId, policyId)) {
                MedCaseMedPolicy mp = new MedCaseMedPolicy();
                mp.setMedCase(hospital);
                MedPolicy p = manager.find(MedPolicy.class, policyId);
                mp.setPolicies(p);
                manager.persist(mp);
            }
        }
        manager.persist(hospital);
    }

    private boolean checkExistsAttachedPolicy(long aMedCaseId, long aPolicy) {
        String sql = " select count(*) from MedCase_MedPolicy where medCase_id='" +
                aMedCaseId +
                "' and policies_id='" + aPolicy + "'";
        Object res = manager.createNativeQuery(sql).getSingleResult();
        return ConvertSql.parseLong(res) > 0L;
    }

    @Override
    public void removePolicies(long aMedCaseId, long[] aPolicies) {
        for (long policyId : aPolicies) {
            manager.createNativeQuery("delete from MedCase_MedPolicy where medCase_id='" +
                    aMedCaseId + "' and policies_id='" + policyId + "'").executeUpdate();
        }
    }

    @Override
    public String getTemperatureCurve(long aMedCaseId) {
        List<TemperatureCurve> list = manager.createQuery("from TemperatureCurve where medCase_id=:medCase").setParameter("medCase", aMedCaseId).getResultList();

        StringBuilder json = new StringBuilder();
        json.append("{\"childs\":[");
        boolean isFirst = true;
        for (TemperatureCurve curve : list) {
            if (!isFirst) {
                json.append(",");
            } else {
                isFirst = false;
            }
            json.append("{");
            if (curve.getTakingDate() != null) {
                SimpleDateFormat FORMAT_1 = new SimpleDateFormat("yyyy/MM/dd");
                String date = FORMAT_1.format(curve.getTakingDate().getTime());
                json.append("\"date\":")
                        .append("\"")
                        .append(date)
                        .append(" ");
                if (curve.getDayTime() != null && curve.getDayTime().getCode() != null && curve.getDayTime().getCode().equals("2")) {
                    json.append("20");
                } else {
                    json.append("8");
                }
                json.append(":00")
                        .append("\",");


            }
            json.append("\"id\":").append(curve.getId()).append(",");
            json.append("\"degree\":")
                    .append(curve.getDegree())
                    .append("}");
        }
        json.append("]}");
        return json.toString();
    }
}
