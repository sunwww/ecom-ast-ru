package ru.ecom.mis.ejb.service;

import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import ru.ecom.api.entity.export.ExportType;
import ru.ecom.api.entity.export.MedCaseExportJournal;
import ru.ecom.api.form.*;
import ru.ecom.api.webclient.IWebClientService;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase;
import ru.ecom.mis.ejb.domain.medcase.ShortMedCase;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocWorkPlaceType;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.PersonalWorkFunction;
import ru.ecom.poly.ejb.domain.voc.VocReason;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.nuzmsh.util.EqualsUtil.isEquals;

/**
 * Выгрузка данных в РИАМС промед. Работает через сервис "Промедатор" за авторством @Rashgild'a
 */
@Stateless
@Local(IPromedExportService.class)
@Remote(IPromedExportService.class)
public class PromedExportServiceBean implements IPromedExportService {

    private static final Logger LOG = Logger.getLogger(PromedExportServiceBean.class);
    private static String PROMEDATOR_URL = null;

    private static final String POL_EXPORT_URL = "ambulatory/epicrisis-export"; //отправка пол. случая
    private static final String GET_BY_GUID_URL = "ambulatory/get-by-guid"; //получение информации по отправке СМО

    /**
     * Экспорт поликлинического случая в РИАМС Промед
     *
     * @param medCase
     */
    @Override
    public String exportPolyclinic(PolyclinicMedCase medCase) {
        if (isEnabled()) {
            validate(medCase);
            PromedPolyclinicTapForm form = getPolyclinicCase(medCase);
            LOG.warn("made form: " + form);
            String response;
            try {
                //TODO выпилить неактуальный промедатор
                Map.Entry<Integer, JSONObject> responseMap = webService.makePOSTRequestExt(toString(form), PROMEDATOR_URL, POL_EXPORT_URL, new HashMap<>());

                if (responseMap != null) {
                    LOG.info(">>" + responseMap + "<<");
                    response = responseMap.getValue().getString("data");
                    MedCaseExportJournal journal = new MedCaseExportJournal();
                    journal.setMedCase(medCase.getId());
                    journal.setExportType(ExportType.MANUAL);

                    if (isEquals(responseMap.getKey(), 200)) { //success
                        journal.setPacketGuid(response);
                    } else {
                        journal.setErrorText(responseMap.getValue().getString("error"));
                    }
                    manager.persist(journal);
                } else {
                    LOG.error("Error sending medcase to promedator");
                    response = null;
                }


            } catch (Exception e) {
                e.printStackTrace();
                response = e.getMessage();
            }
            return response;
        }
        return null;
    }

    private boolean isEnabled() {
        PROMEDATOR_URL = EjbEcomConfig.getInstance().get("promedator.url", null);
        return PROMEDATOR_URL != null;
    }

    private String toString(PromedPolyclinicTapForm form) {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(form);
    }

    @Override
    public String exportPolyclinicById(Long medCaseId) {
        return exportPolyclinic(manager.find(PolyclinicMedCase.class, medCaseId));
    }

    @Override
    public String getJournalToPromed(Long spoId) {
        if (PROMEDATOR_URL!=null ||  isEnabled()) {
            List<MedCaseExportJournal> journals = manager.createQuery(" from MedCaseExportJournal j where medcase = :spoId")
                    .setParameter("spoId", spoId).getResultList();
            if (!journals.isEmpty()) {
                StringBuilder guids = new StringBuilder();
                for (MedCaseExportJournal j : journals) {
                    guids.append(",").append(j.getPacketGuid());
                }
                Map.Entry<Integer, JSONObject> ret = webService.makeGetRequest(PROMEDATOR_URL, GET_BY_GUID_URL + "?guids=" + guids.substring(1));
                if (ret==null || ret.getKey()!=200) {
                    LOG.error("ERROR: "+ret);
                } else {
                    return ret.getValue().toString();
                }

            }
        }

        return null;
    }

    private void validate(PolyclinicMedCase medCase) {
    }

    private PromedPolyclinicTapForm getPolyclinicCase(PolyclinicMedCase polyclinicCase) {
        Long polyclinicCaseId = polyclinicCase.getId();
        List<ShortMedCase> allVisits = getAllVisitsInSpo(polyclinicCaseId);
        if (allVisits.isEmpty()) {
            throw new IllegalStateException("Законченный случай без визитов, быть такого не может");
        }
        PromedPolyclinicTapForm.PromedPolyclinicTapFormBuilder tap = PromedPolyclinicTapForm.builder();
        Patient pat = polyclinicCase.getPatient();
        tap.patient(PromedPatientForm.builder().lastName(pat.getLastname()).firstName(pat.getFirstname()).middleName(pat.getMiddlename())
                .snils(pat.getSnils()).birthDate(pat.getBirthday()).build());
        tap.isFinished(polyclinicCase.getDateFinish() != null);
        tap.ticketNumber(String.valueOf(polyclinicCaseId));
        tap.promedCode(polyclinicCase.getPromedCode());
        tap.isEmergency(polyclinicCase.getEmergency());

        ShortMedCase lastVisit = allVisits.get(allVisits.size() - 1);
        if (lastVisit != null && lastVisit.getVisitResult() != null) {
            tap.tapResult(lastVisit.getVisitResult().getOmcCode());
            PromedDiagnosis lastDiagnosis;
            if (Boolean.TRUE.equals(lastVisit.getWorkFunctionExecute().getWorkFunction().getIsNoDiagnosis())) {
                //диагностика - диагноз Z
                lastDiagnosis = PromedDiagnosis.builder()
                        .promedId("11052")
                        .mkbCode("Z34.9").build();
            } else {
                lastDiagnosis = mapDiagnosis(getPrioryDiagnosis(lastVisit.getDiagnoses()));
            }
            tap.diagnosis(lastDiagnosis);
        }
        tap.visits(mapVisits(allVisits));
        return tap.build();
    }

    private List<PromedPolyclinicVisitForm> mapVisits(List<ShortMedCase> visits) {
        List<PromedPolyclinicVisitForm> visitForms = new ArrayList<>();
        for (ShortMedCase visit : visits) {
            PromedPolyclinicVisitForm.PromedPolyclinicVisitFormBuilder visitForm = PromedPolyclinicVisitForm.builder();
            PersonalWorkFunction wf = (PersonalWorkFunction) visit.getWorkFunctionExecute();
            VocReason vr = visit.getVisitReason();
            VocWorkPlaceType vwr = visit.getWorkPlaceType();
            VocServiceStream vss = visit.getServiceStream();
            visitForm.startTime(visit.getDateStart() + " " + (visit.getTimeExecute() == null ? "" : visit.getTimeExecute())) //TODO check timezone!!
                    .internalId(String.valueOf(visit.getId()))
                    .diagnosis(mapDiagnosis(getPrioryDiagnosis(visit.getDiagnoses())))
                    .doctor(mapDoctor(wf))
                    .workPlaceCode(vwr.getOmcCode())
                    .diary(getDiaryInVisit(visit.getId()))
                    .serviceStream(vss.getCode()) //unused
                    .visitPurpose(vr.getOmcCode()) //1,2,3,4
                    .ishodCode(visit.getVisitResult().getCodefpl())
                    .medicalCareKindCode(mapMedicalCare(wf))
                    .promedCode(visit.getPromedCode())
            ;
            visitForms.add(visitForm.build());
        }
        return visitForms;
    }

    //TODO check, скопипащено с экспертизы
    private String mapMedicalCare(PersonalWorkFunction wf) {
        String defaultHelpKindCode = "13";
        if (wf.getWorkFunction().getCode() == null) {
            if (wf.getWorkFunction().getFondSpeciality() != null && isEquals(wf.getWorkFunction().getFondSpeciality().getCode(), "76")) { //НМП без
                return "12";
            }
            return defaultHelpKindCode;
        }
        String code;
        switch (wf.getWorkFunction().getCode()) {
            case "49": //педиатр
            case "97": //терапевт (не очень, но оставим)
            case "76": // терапия
                code = "12"; //первичная МСП
                break;
            case "206": //фельдшер
                code = "11"; //первичная доврачебная МСП
                break;
            default:
                code = defaultHelpKindCode; //первичная специализированная МСП
        }
        return code;
    }

    private String getDiaryInVisit(Long visitId) {
        List<String> diary = manager.createNativeQuery("select record from Diary where medcase_id=:visitId")
                .setParameter("visitId", visitId).getResultList();
        return diary.isEmpty() ? "" : diary.get(0);
    }

    private PromedDoctor mapDoctor(PersonalWorkFunction doctor) {
        Patient person = doctor.getWorker().getPerson();
        return PromedDoctor.builder()
                .lastName(person.getLastname())
                .firstName(person.getFirstname())
                .middleName(person.getMiddlename())
                .snils(person.getSnils())
                .promedWorkstaffId(doctor.getPromedCodeWorkstaff() == null ? null : Long.valueOf(doctor.getPromedCodeWorkstaff()))
                .build();
    }

    private List<ShortMedCase> getAllVisitsInSpo(Long polyclinicCaseId) {
        return manager.createQuery("from ShortMedCase where parent_id  = :parentId and (noActuality is null or noActuality='0') and dateStart is not null order by dateStart , timeExecute ")
                .setParameter("parentId", polyclinicCaseId).getResultList();
    }

    private Diagnosis getPrioryDiagnosis(List<Diagnosis> diagnoses) {
        for (Diagnosis d : diagnoses) {
            if (d.getPriority() != null && isEquals(d.getPriority().getCode(), "1")) {
                return d;
            }
        }
        return null;
    }

    private PromedDiagnosis mapDiagnosis(Diagnosis diagnosis) {
        if (diagnosis == null) {
            throw new IllegalStateException("Нет основного диагноза");
        } else {
            return PromedDiagnosis.builder()
                    .promedId(diagnosis.getIdc10().getPromedCode())
                    .mkbCode(diagnosis.getIdc10().getCode())
                    .build();
        }

    }

    @PersistenceContext
    private EntityManager manager;

    private @EJB
    IWebClientService webService;
}
