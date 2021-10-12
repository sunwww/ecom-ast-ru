package ru.ecom.expert2.service;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import ru.ecom.api.webclient.IWebClientService;
import ru.ecom.expert2.domain.E2Entry;
import ru.ecom.expert2.dto.Hosp;
import ru.ecom.expert2.dto.HospLeave;
import ru.ecom.expert2.dto.Refferal;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateConverter;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import static ru.nuzmsh.util.BooleanUtils.isNotTrue;
import static ru.nuzmsh.util.EqualsUtil.isEquals;

@Stateless
@Local(IExpert2AlkonaService.class)
@Remote(IExpert2AlkonaService.class)
public class Expert2AlkonaServiceBean implements IExpert2AlkonaService {
    private static final Logger LOG = Logger.getLogger(Expert2AlkonaServiceBean.class);
    private static final String OMC_SERVICE_STREAM = "OBLIGATORYINSURANCE";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat YEAR_YY = new SimpleDateFormat("yy");
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:00");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:00");
    private @EJB
    IWebClientService clientService;
    private @PersistenceContext
    EntityManager manager;

    @Override
    public void exportHospLeaveToAlkona(Long entryListId) {
        LOG.info("start send to alkona");
        String alkonaUrl = getAlkonaUrl();
        List<E2Entry> entries = manager.createNamedQuery("E2Entry.getAllByListEntryIdAndServiceStream")
                .setParameter("listEntryId", entryListId).setParameter("serviceStream", OMC_SERVICE_STREAM).getResultList();
        int i = 0;
        LOG.info("found " + entries.size() + " entries");
        for (E2Entry entry : entries) {
            try {
                i++;
                if (i % 100 == 0) LOG.info("Отправлено в алькону " + i + " записей");
                LOG.info("id:" + entry.getId() + " Response: " + clientService.makePOSTRequest(toString(mapEntryHospLeave(entry)), alkonaUrl, "postDischarge", Collections.emptyMap()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LOG.info("Finish. sent entries: " + i);
    }

    @Override
    public void exportDirectionsToAlkona(Long entryListId) {
        LOG.info("start send to alkona");
        String alkonaUrl = getAlkonaUrl();
        List<E2Entry> entries = manager.createNamedQuery("E2Entry.getAllByListEntryIdAndServiceStream")
                .setParameter("listEntryId", entryListId).setParameter("serviceStream", OMC_SERVICE_STREAM).getResultList();
        int i = 0;
        LOG.info("found " + entries.size() + " entries");
        String directLpuCode = "300001"; //выгружаем направления только своей ЛПУ TODO
        for (E2Entry entry : entries) {
            if (isNotTrue(entry.getIsEmergency()) && isEquals(entry.getDirectLpu(), directLpuCode)) {
                try {
                    i++;
                    if (i % 100 == 0) LOG.info("Отправлено в алькону " + i + " записей");
                    LOG.info("id:" + entry.getId() + " Response: " + clientService.makePOSTRequest(toString(mapEntryRefferal(entry)), alkonaUrl, "postRefferals", Collections.emptyMap()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        LOG.info("Finish. sent entries: " + i);
    }

    @Override
    public String exportDirectionToAlkona(Long entryId) {
        LOG.info("start send to alkona: " + entryId);
        String alkonaUrl = getAlkonaUrl();
        E2Entry entry = manager.find(E2Entry.class, entryId);
        String request = toString(mapEntryRefferal(entry));
        String response = clientService.makePOSTRequest(request, alkonaUrl, "postRefferals", Collections.emptyMap());
        LOG.info("id:" + entry.getId() + "{" + request + "}" + " Response: " + response);
        return response;
    }

    @Override
    public String exportHospLeaveEntryToAlkona(Long entryId) {
        LOG.info("start send to alkona");
        String alkonaUrl = getAlkonaUrl();
        E2Entry entry = manager.find(E2Entry.class, entryId);
        String request = toString(mapEntryHospLeave(entry));
        String response = clientService.makePOSTRequest(request, alkonaUrl, "postDischarge", Collections.emptyMap());
        LOG.info("id:" + entry.getId() + "{" + request + "}" + " Response: " + response);
        return response;
    }

    private String getAlkonaUrl() {
        return getExpertConfigValue("ALKONA_URL") + "/medcase";
    }

    @Override
    public void exportHospToAlkona(Long entryListId, Boolean isEmergency) {
        LOG.info("start send to alkona");
        String alkonaUrl = getAlkonaUrl();
        List<E2Entry> entries = manager.createNamedQuery("E2Entry.getAllByListEntryIdAndServiceStream")
                .setParameter("listEntryId", entryListId).setParameter("serviceStream", OMC_SERVICE_STREAM).getResultList();
        int i = 0;
        LOG.info("found " + entries.size() + " entries");
        for (E2Entry entry : entries) {
            if (isEmergency == null || isEmergency.equals(entry.getIsEmergency())) {
                try {
                    i++;
                    if (i % 100 == 0) LOG.info("Отправлено в алькону " + i + " записей");
                    LOG.info("id:" + entry.getId() + " Response: " + clientService.makePOSTRequest(toString(mapEntryHosp(entry)), alkonaUrl, "postHosp", Collections.emptyMap()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            LOG.info("Finish. sent entries: " + i);
        }
    }

    @Override
    public String exportHospEntryToAlkona(Long entryId) {
        LOG.info("start send to alkona");
        String alkonaUrl = getAlkonaUrl();
        E2Entry entry = manager.find(E2Entry.class, entryId);
        String entryString = toString(mapEntryHosp(entry));
        String response = clientService.makePOSTRequest(entryString, alkonaUrl, "postHosp", Collections.emptyMap());
        LOG.info("id:" + entry.getId() + "{" + entryString + "}" + " Response: " + response);
        return response;
    }

    //выписка
    private HospLeave mapEntryHospLeave(E2Entry entry) {
        HospLeave leave = new HospLeave();
        leave.setExternalId(entry.getExternalId() + "");
        leave.setDirectNumber(getTickerNumber(entry));
        leave.setDirectDate(toLocalDate(entry.getDirectDate()));
        leave.setServiceKind(Boolean.TRUE.equals(entry.getIsEmergency()) ? "2" : "1");
        leave.setLpuCode(entry.getLpuCode());
        leave.setHospStartDate(toLocalDateTime(entry.getStartDate(), entry.getStartTime()));
        leave.setHospFinishDate(toLocalDateTime(entry.getFinishDate(), entry.getFinishTime()));
        leave.setMedHelpProfile(entry.getBedProfile().getCode());
        leave.setDepartmentName(entry.getDepartmentName());
        leave.setStatisticStub(entry.getHistoryNumber());
        leave.setPatientLastname(entry.getLastname());
        leave.setPatientFirstname(entry.getFirstname());
        if (StringUtil.isNotEmpty(entry.getMiddlename())) {
            leave.setPatientMiddlename(entry.getMiddlename());
        }
        leave.setMedTerms(mapMedTerms(entry.getMedHelpUsl().getCode()));

        return leave;
    }

    private Refferal mapEntryRefferal(E2Entry entry) {
        Refferal ref = new Refferal();
        ref.setExternalId(entry.getExternalId() + "");
        ref.setDirectNumber(getTickerNumber(entry));
        ref.setDirectDate(toLocalDate(entry.getDirectDate() != null ? entry.getDirectDate() : entry.getStartDate()));
        ref.setServiceKind(Boolean.TRUE.equals(entry.getIsEmergency()) ? 2 : 1);
        ref.setDirectLpu(entry.getDirectLpu());
        ref.setLpuCode(entry.getLpuCode());
        ref.setPlanHospDate(toLocalDate(entry.getStartDate()));
        ref.setMedPolicyType(Integer.parseInt(entry.getMedPolicyType()));
        if ("3".equals(entry.getMedPolicyType())) {
            ref.setMedPolicyNumber(entry.getCommonNumber());
        } else {
            ref.setMedPolicyNumber(entry.getMedPolicyNumber());
            if (StringUtil.isNotEmpty(entry.getMedPolicySeries())) {
                ref.setMedPolicySeries(entry.getMedPolicySeries());
            }
        }
        ref.setInsuranceCompanyCode(entry.getInsuranceCompanyCode());
        ref.setDiagnosis(entry.getMainMkb());
        ref.setRegionOkato("12000");
        ref.setPatientLastname(entry.getLastname());
        ref.setPatientFirstname(entry.getFirstname());
        if (StringUtil.isNotEmpty(entry.getMiddlename())) {
            ref.setPatientMiddlename(entry.getMiddlename());
        }
        ref.setSex("1".equals(entry.getSex()) ? 1 : 0); //палка - мальчик, дырка - девочка!
        ref.setBirthDate(toLocalDate(entry.getBirthDate()));
        ref.setMedHelpProfile(entry.getBedProfile().getCode());
        ref.setHospitalBranch(entry.getDepartmentName());
        ref.setMedTerms(mapMedTerms(entry.getMedHelpUsl().getCode()));
        ref.setDoctorInfo("СУХОВА ИРИНА АЛЕКСЕЕВНА");//TODO не тот доктор
        ref.setDoctorSnils("040-482-502 20");//TODO не тот доктор
        return ref;
    }

    private String getTickerNumber(E2Entry entry) {

        if (entry.getTicket263Number() != null && entry.getTicket263Number().length() == 13) {
            return entry.getTicket263Number();
        }
        //ММММММГГВННННН - ММММММ - код МО, ГГ - последние цифры года, В - вид номера направления (0-авто, 1-ручное формирование); ННННН - порядковый номер направления в данной МО за заданный год;
        String code = entry.getDirectLpu() + YEAR_YY.format(entry.getDirectDate() != null ? entry.getDirectDate() : entry.getStartDate())+"0" +
                "00000".substring(0, Math.max(5 - entry.getHistoryNumber().length(),0)) + entry.getHistoryNumber();
        entry.setTicket263Number(code);
        return code;

    }


    private Hosp mapEntryHosp(E2Entry entry) {
        Hosp hosp = new Hosp();
        hosp.setExternalId(entry.getExternalId() + "");
        hosp.setIsEmergency(Boolean.TRUE.equals(entry.getIsEmergency()) ? 1 : 0);
        hosp.setDirectNumber(entry.getTicket263Number());
        hosp.setDirectDate(toLocalDate(entry.getDirectDate()));
        hosp.setServiceKind(Boolean.TRUE.equals(entry.getIsEmergency()) ? 2 : 1);
        hosp.setDirectLpu(entry.getDirectLpu());
//        hosp.setDirectLpuDepartment(entry.getDirectLpu());
        hosp.setLpuCode(entry.getLpuCode());
        hosp.setDepartmentName(entry.getDepartmentName());
        hosp.setHospStartDate(toLocalDate(entry.getStartDate()));
        hosp.setHospStartTime(toLocalTime(entry.getStartTime()));
        hosp.setMedPolicyType(Integer.parseInt(entry.getMedPolicyType()));
        if ("3".equals(entry.getMedPolicyType())) {
            hosp.setMedPolicyNumber(entry.getCommonNumber());
        } else {
            hosp.setMedPolicyNumber(entry.getMedPolicyNumber());
            if (StringUtil.isNotEmpty(entry.getMedPolicySeries())) {
                hosp.setMedPolicySeries(entry.getMedPolicySeries());
            }
        }
        hosp.setInsuranceCompanyCode(entry.getInsuranceCompanyCode());
        hosp.setDiagnosis(entry.getMainMkb());
        hosp.setRegionOkato("12000");
        hosp.setPatientLastname(entry.getLastname());
        hosp.setPatientFirstname(entry.getFirstname());
        if (StringUtil.isNotEmpty(entry.getMiddlename())) {
            hosp.setPatientMiddlename(entry.getMiddlename());
        }
        hosp.setSex("1".equals(entry.getSex()) ? 1 : 0); //палка - мальчик, дырка - девочка!
        hosp.setBirthDate(toLocalDate(entry.getBirthDate()));
        hosp.setMedHelpProfile(entry.getBedProfile().getCode());
        hosp.setHospitalBranch(entry.getDepartmentName());
        hosp.setStatisticStub(entry.getHistoryNumber());
        hosp.setMedTerms(mapMedTerms(entry.getMedHelpUsl().getCode()));
        return hosp;
    }

    private String mapMedTerms(String medHelpUsl) {
        switch (medHelpUsl) {
            case "3":
                return "ДП";
            case "2":
                return "ДС";
            case "1":
                return "КР";
            default:
                throw new IllegalStateException("HZ");
        }
    }

    private String toLocalDate(Date date) {
        return date == null ? null : DATE_FORMAT.format(date);
    }

    private String toLocalDateTime(Date date, Time time) {
        try {
            return DATE_TIME_FORMAT.format(DateConverter.createDateTime(date, time));
        } catch (ParseException e) {
            return "";
        }
    }

    private String toLocalTime(Time time) {
        return time == null ? null : TIME_FORMAT.format(time);
    }

    private String toString(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    /**
     * Получаем значение из настроек экспертизы по коду
     */
    private String getExpertConfigValue(String parameterName) {
        List<Object> ret = manager.createNativeQuery("select value from Expert2Config where code=:code AND (isDeleted is null or isDeleted='0')").setParameter("code", parameterName).getResultList();
        if (ret.isEmpty()) {
            throw new IllegalStateException("Не удалось найти настройку с кодом: " + parameterName);
        }
        return ret.get(0).toString();
    }

}
