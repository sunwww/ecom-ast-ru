package ru.ecom.mis.ejb.service.promed;

import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import ru.ecom.api.entity.export.ExportType;
import ru.ecom.api.entity.export.MedCaseExportJournal;
import ru.ecom.api.form.*;
import ru.ecom.api.form.hospital.PromedDepartmentForm;
import ru.ecom.api.form.hospital.PromedHospitalForm;
import ru.ecom.api.webclient.IWebClientService;
import ru.ecom.ejb.domain.simple.VocIdCodeName;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.mis.ejb.domain.lpu.BedFund;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.*;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPreAdmissionTime;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;
import ru.ecom.mis.ejb.domain.patient.voc.VocWorkPlaceType;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.PersonalWorkFunction;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction;
import ru.ecom.poly.ejb.domain.voc.VocReason;
import ru.ecom.poly.ejb.domain.voc.VocVisitResult;
import ru.nuzmsh.forms.validator.ValidateException;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static ru.nuzmsh.util.EqualsUtil.isEquals;

/**
 * Выгрузка данных в РИАМС промед. Работает через сервис "Промедатор" за авторством @Rashgild'a
 */
@Stateless
@Local(IPromedExportService.class)
@Remote(IPromedExportService.class)
public class PromedExportServiceBean implements IPromedExportService {

    private static final Logger LOG = Logger.getLogger(PromedExportServiceBean.class);
    private static final String POL_EXPORT_URL = "ambulatory/epicrisis-export"; //отправка пол. случая
    private static final String PARACLINICS_EXPORT_URL = "par/par-export"; //отправка параклиники
    private static final String STAC_EXPORT_URL = "hospital/hosp-export"; //отправка стац. случая
    private static final String GET_BY_GUID_URL = "ambulatory/get-by-guid"; //получение информации по отправке СМО
    private static String PROMEDATOR_URL = null;
    @PersistenceContext
    private EntityManager manager;
    private @EJB
    IWebClientService webService;

    /**
     * Экспорт поликлинического случая в РИАМС Промед
     *
     * @param medCase
     */
    private String exportMedCase(MedCase medCase) throws ValidateException {
        if (medCase != null && isEnabled()) {
            validate(medCase);
            String formString;
            String methodUrl;
            if (medCase instanceof HospitalMedCase) {
                formString = toString(getHospitalCase((HospitalMedCase) medCase));
                methodUrl = STAC_EXPORT_URL;
            } else if (medCase instanceof PolyclinicMedCase) {
                formString = toString(getPolyclinicCase((PolyclinicMedCase) medCase));
                methodUrl = POL_EXPORT_URL;
            } else if (medCase instanceof Visit) { //выгружаем диагностический визит
                //TODO если сюда попадет визит к врачу - не диагносту, всё равно выгрузим как диагноста. Поправить!
                formString = toString(getParaclinicCase((Visit) medCase));
                methodUrl = PARACLINICS_EXPORT_URL;
            } else {
                throw new IllegalArgumentException("Невозможно выгрузить случай с типом " + medCase.getClass().getSimpleName());
            }
            LOG.warn("made medcase form: " + formString);
            return processExportMedCaseToPromed(formString, methodUrl, medCase.getId());
        }
        return null;
    }

    @Override
    public PromedParaclinicForm getParaclinicCase(Visit visit) {
        try {
            String startDateTime = getDateTime(visit.getDateStart(), visit.getTimeExecute());
            Patient pat = visit.getPatient();
            PromedParaclinicForm tap = PromedParaclinicForm.builder()
                    .patient(buildPatient(pat))
                    .startDate(startDateTime)
                    .internalSpoId(String.valueOf(visit.getParent().getId()))
                    .diary(getDiaryInVisit(visit.getId()))
                    .medServices(mapServices(visit.getId(), mapDoctor(visit.getWorkFunctionExecute()), startDateTime))
                    .build();
            tap.setPromedCode(visit.getPromedCode());
            tap.setMedosId(visit.getId());
            tap.setServiceStream(ofNullable(visit.getServiceStream()).map(VocServiceStream::getPromedCode).orElse(null));
            return tap;
        } catch (Exception e) {
            LOG.error("Ошибка создания ДТО:" + e.getMessage(), e);
            return null;
        }
    }

    private PromedPatientForm buildPatient(Patient pat) {
        return PromedPatientForm.builder()
                .lastName(pat.getLastname())
                .firstName(pat.getFirstname())
                .middleName(pat.getMiddlename())
                .snils(pat.getSnils())
                .birthDate(pat.getBirthday())
                .build();
    }

    private boolean isEnabled() {
        PROMEDATOR_URL = EjbEcomConfig.getInstance().get("promedator.url", null);
        return PROMEDATOR_URL != null;
    }

    private <T extends BasePromedForm> String toString(T form) {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(form);
    }

    @Override
    public String exportPolyclinicById(Long medCaseId) throws ValidateException {
        return exportMedCase(manager.find(MedCase.class, medCaseId));
    }

    @Override
    public String exportHospitalById(Long hospitalMedCaseId) throws ValidateException {
        return exportMedCase(manager.find(HospitalMedCase.class, hospitalMedCaseId));
    }

    private String processExportMedCaseToPromed(String formString, String promedMethodUrl, Long medcaseId) {
        String response;
        try {
            Map.Entry<Integer, JSONObject> responseMap = webService.makePOSTRequestExt(formString, PROMEDATOR_URL, promedMethodUrl, new HashMap<>());

            if (responseMap != null) {
                JSONObject jso = responseMap.getValue();
                if (isEquals(responseMap.getKey(), 200) && jso.getBoolean("success")) { //success
                    MedCaseExportJournal journal = new MedCaseExportJournal();
                    journal.setMedCase(medcaseId);
                    journal.setExportType(ExportType.MANUAL);
                    response = jso.getString("data");
                    journal.setPacketGuid(response);
                    manager.persist(journal);
                } else {
                    LOG.error("Ошибка отправки СМО с ИД " + medcaseId + " в Промед: " + jso);
                    response = jso.getJSONObject("error").getString("text");
                }
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


    private PromedHospitalForm getHospitalCase(HospitalMedCase sls) {
        long slsId = sls.getId();
        List<DepartmentMedCase> deps = getDepartmentCases(slsId);
        if (deps.isEmpty()) {
            LOG.error("Не найдено случаев лечения в отделении, а отказ от госпитализации мы не выгружаем");
            throw new IllegalArgumentException("Не найдено случаев лечения в отделении, а отказ от госпитализации мы не выгружаем");
        }
        PromedHospitalForm.PromedHospitalFormBuilder form = PromedHospitalForm.builder();
        Patient pat = sls.getPatient();
        form.patient(buildPatient(pat));
        form.statStubNumber(sls.getStatCardNumber());
        form.isFinished(true); //только закрытые СЛС!
        form.dischargeResultCode(getVocCode(sls.getResult()))
                .dischargeOutcomeCode(getVocCode(sls.getOutcome()))
                .dischargeReasonCode(getVocCode(sls.getStatisticStub().getReasonDischarge()));
        form.isEmergency(sls.getEmergency());

        if (!Boolean.TRUE.equals(sls.getEmergency())) {
            form.directDate(sls.getOrderDate());
            form.directLpuCode(ofNullable(sls.getOrderLpu()).map(MisLpu::getCodef).orElse(null));
            form.directNumber(sls.getOrderNumber());
            form.directDiagnosis(mapDiagnosis(sls.getDiagnoses(), "2", slsId)); //направительные диагноз
        }
        form.entranceDate(getDateTime(sls.getDateStart(), sls.getEntranceTime()));
        form.preAdmissionHour(mapAdmissionHour(sls.getPreAdmissionTime()));
        form.isUnLawTrauma(wasCrimeMessages(slsId));
        MisLpu entranceDepartment = sls.getDepartment();
        form.departmentPromedId(entranceDepartment.getPromedLpuSectionId());
        form.isAmbulanceTreatment(sls.getAmbulanceTreatment());
        form.transferLpuCode(ofNullable(sls.getMoveToAnotherLPU()).map(MisLpu::getCodef).orElse(null));
        List<PromedDepartmentForm> cases = mapDepartmentCases(deps);
        form.departmentCases(cases);
        form.priemDischargeDate(cases.get(0).getEntranceDate().substring(0, 10));//TODO сделать красиво //дата выбытия из приемного отделения = дата поступления в первое отделение
        form.bedTypeId(cases.get(0).getBedTypeId());
        PromedHospitalForm hosp = form.build();
        hosp.setMedosId(sls.getId());
        hosp.setPromedCode(sls.getPromedCode());
        hosp.setServiceStream(ofNullable(sls.getServiceStream()).map(VocServiceStream::getPromedCode).orElse(null));
        return hosp;
    }

    private List<PromedDepartmentForm> mapDepartmentCases(List<DepartmentMedCase> deps) {
        List<PromedDepartmentForm> cases = new ArrayList<>();
        for (DepartmentMedCase slo : deps) {
            PromedDepartmentForm form = new PromedDepartmentForm();
            form.setEntranceDate(getDateTime(slo.getDateStart(), slo.getEntranceTime()));
            if (slo.getDateFinish() != null) { //пациент выписан из этого отделения
                form.setDischargeDate(getDateTime(slo.getDateFinish(), slo.getDischargeTime()));
            } else if (slo.getTransferDate() != null) {
                form.setTransferDate(getDateTime(slo.getTransferDate(), slo.getTransferTime()));
            } else {
                LOG.error("Что ты такое?!?!?!?!" + slo.getId());
            }
            form.setDepartmentPromedId(slo.getDepartment().getPromedLpuSectionId());
            form.setDoctor(mapDoctor(slo.getOwnerFunction()));
            form.setMainDiagnosis(mapDiagnosis(slo.getMainDiagnosis(), slo.getId()));
            form.setDiagnoses(mapDiagnosis(slo.getDiagnoses(), slo.getId()));
            form.setPromedCode(slo.getPromedCode());
            form.setServiceStream(ofNullable(slo.getServiceStream()).map(VocServiceStream::getPromedCode).orElse(null));
            form.setMedosId(slo.getId());
            form.setBedTypeId(ofNullable(slo.getBedFund()).map(BedFund::getBedSubType).map(VocIdCodeName::getCode).orElse(null));
            cases.add(form);
        }
        return cases;
    }

    /**
     * Мапим диагноз с определенным типом
     *
     * @param diagnoses            все диагнозы по СЛС
     * @param registrationTypeCode тип регистрации
     * @return
     */
    private PromedDiagnosis mapDiagnosis(List<Diagnosis> diagnoses, String registrationTypeCode, Long medcaseId) {
        for (Diagnosis d : diagnoses) {
            if (d.getRegistrationType() != null && registrationTypeCode.equals(d.getRegistrationType().getCode())) {
                return mapDiagnosis(d, medcaseId);
            }
        }
        return null;
    }

    private List<PromedDiagnosis> mapDiagnosis(List<Diagnosis> diagnoses, Long medcaseId) {
        return diagnoses.stream()
                .map(d -> mapDiagnosis(d, medcaseId))
                .collect(Collectors.toList());

    }

    //были ли сообщения в полицию
    private boolean wasCrimeMessages(Long medcaseId) {
        return !manager.createNativeQuery("select id from phonemessage where medcase_id=:medcaseId")
                .setParameter("medcaseId", medcaseId)
                .getResultList().isEmpty();
    }

    /**
     * Кол-во часов с момента госпитализации
     *
     * @param time приблизительное кол-во часов с момента заболевания
     * @return
     */
    private Integer mapAdmissionHour(VocPreAdmissionTime time) {
        if (time == null || time.getCode() == null) {
            return 48;
        }
        switch (time.getCode()) {
            case "1":
                return 4;
            case "2":
                return 16;
            case "3":
                return 25;
        }

        return 48;
    }

    private List<DepartmentMedCase> getDepartmentCases(Long hospitalMedCaseId) {
        return manager.createQuery("from DepartmentMedCase where parent_id  = :parentId and (noActuality is null or noActuality='0') and dateStart is not null order by dateStart , timeExecute ")
                .setParameter("parentId", hospitalMedCaseId)
                .getResultList();
    }

    @Override
    public String getJournalToPromed(Long spoId) {
        if (PROMEDATOR_URL != null || isEnabled()) {
            List<MedCaseExportJournal> journals = manager.createQuery(" from MedCaseExportJournal j where medcase = :spoId")
                    .setParameter("spoId", spoId).getResultList();
            if (!journals.isEmpty()) {
                String guids = journals.stream()
                        .map(MedCaseExportJournal::getPacketGuid)
                        .collect(Collectors.joining(","));
                Map.Entry<Integer, JSONObject> ret = webService.makeGetRequest(PROMEDATOR_URL, GET_BY_GUID_URL + "?guids=" + guids);
                if (ret == null || ret.getKey() != 200) {
                    LOG.error("ERROR: " + ret);
                } else {
                    return ret.getValue().toString();
                }
            }
        }

        return null;
    }

    private void validate(MedCase medCase) throws ValidateException {
        List<String> errors = new ArrayList<>();
        if (medCase == null) {
            throw new ValidateException("СМО не найдено");
        }
        if (medCase.getPatient() == null) {
            errors.add("Не указан пациент");
        }
        if (!errors.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String err : errors) {
                sb.append(err).append(";");
            }
            throw new ValidateException(sb.toString());
        }
    }

    @Override
    public PromedPolyclinicTapForm getPolyclinicCase(PolyclinicMedCase polyclinicCase) {
        try {
            Long polyclinicCaseId = polyclinicCase.getId();
            List<ShortMedCase> allVisits = getAllVisitsInSpo(polyclinicCaseId);
            if (allVisits.isEmpty()) {
                LOG.error("Законченный случай без визитов, быть такого не может: " + polyclinicCaseId);
                return null;
            }
            PromedPolyclinicTapForm.PromedPolyclinicTapFormBuilder tap = PromedPolyclinicTapForm.builder();
            Patient pat = polyclinicCase.getPatient();
            tap.patient(buildPatient(pat))
                    .isFinished(polyclinicCase.getDateFinish() != null)
                    .ticketNumber(String.valueOf(polyclinicCaseId))
                    .medosId(polyclinicCaseId)
                    .promedCode(polyclinicCase.getPromedCode())
                    .isEmergency(polyclinicCase.getEmergency())
                    .serviceStream(ofNullable(polyclinicCase.getServiceStream()).map(VocServiceStream::getPromedCode).orElse(null))
                    .directLpuCode(findDirectLpu(allVisits));

            ShortMedCase lastVisit = allVisits.get(allVisits.size() - 1);
            List<PromedPolyclinicVisitForm> visitForms = mapVisits(allVisits);
            if (lastVisit != null && lastVisit.getVisitResult() != null) {
                tap.tapResult(lastVisit.getVisitResult().getOmcCode());
                PromedDiagnosis lastDiagnosis;
                if (Boolean.TRUE.equals(lastVisit.getWorkFunctionExecute().getWorkFunction().getIsNoDiagnosis())) {
                    //диагностика - диагноз Z
                    lastDiagnosis = PromedDiagnosis.builder()
                            .mkbCode("Z34.9").build();
                } else {
                    lastDiagnosis = mapDiagnosis(getPrioryDiagnosis(lastVisit.getDiagnoses()), polyclinicCaseId);
                }
                tap.diagnosis(lastDiagnosis);
            }
            tap.visits(visitForms);
            return tap.build();
        } catch (Exception e) {
            LOG.error("Ошибка создания ДТО:" + e.getMessage(), e);
            return null;
        }
    }

    private String findDirectLpu(List<ShortMedCase> visits) {
        return visits.stream()
                .map(ShortMedCase::getOrderLpu)
                .filter(Objects::nonNull)
                .map(MisLpu::getCodef)
                .findFirst().orElse(null);
    }

    private String getDateTime(java.sql.Date date, Time time, String defaultDateTime) {
        return date == null || time == null
                ? defaultDateTime
                : date + " " + time;
    }

    private String getDateTime(java.sql.Date date, Time time) {
        return getDateTime(date, time, null);
    }

    private String getVocCode(VocIdCodeName voc) {
        return ofNullable(voc).map(VocIdCodeName::getCode).orElse(null);
    }

    private List<PromedPolyclinicVisitForm> mapVisits(List<ShortMedCase> visits) {
        List<PromedPolyclinicVisitForm> visitForms = new ArrayList<>();
        for (ShortMedCase visit : visits) {
            PromedPolyclinicVisitForm.PromedPolyclinicVisitFormBuilder visitForm = PromedPolyclinicVisitForm.builder();
            PersonalWorkFunction wf = (PersonalWorkFunction) visit.getWorkFunctionExecute();
            VocReason vr = visit.getVisitReason();
            VocWorkPlaceType vwr = visit.getWorkPlaceType();
            VocServiceStream vss = visit.getServiceStream();
            PromedDoctor doc = mapDoctor(wf);
            String startDateTime = getDateTime(visit.getDateStart(), visit.getTimeExecute());
            PromedDiagnosis diagnosis;
            if (Boolean.TRUE.equals(visit.getWorkFunctionExecute().getWorkFunction().getIsNoDiagnosis())) {
                //диагностика - диагноз Z
                diagnosis = PromedDiagnosis.builder()
                        .promedId("11052")
                        .mkbCode("Z34.9").build();
            } else {
                diagnosis = mapDiagnosis(getPrioryDiagnosis(visit.getDiagnoses()), visit.getId());
            }

            visitForm.startTime(startDateTime)
                    .internalId(String.valueOf(visit.getId()))
                    .diagnosis(diagnosis)
                    .doctor(doc)
                    .workPlaceCode(ofNullable(vwr).map(VocWorkPlaceType::getOmcCode).orElse(null))
                    .diary(getDiaryInVisit(visit.getId()))
                    .serviceStream(getVocCode(vss)) //unused
                    .visitPurpose(ofNullable(vr).map(VocIdNameOmcCode::getOmcCode).orElse(null)) //1,2,3,4
                    .ishodCode(ofNullable(visit.getVisitResult()).map(VocVisitResult::getCodefpl).orElse(null))
                    .medicalCareKindCode(mapMedicalCare(wf))
                    .promedCode(visit.getPromedCode())
                    .services(mapServices(visit.getId(), doc, startDateTime))
            ;
            visitForms.add(visitForm.build());
        }
        return visitForms;
    }

    private List<PromedMedService> mapServices(long visitId, PromedDoctor visitDoctor, String defaultDateStart) {
        List<ServiceMedCase> serviceList = manager.createQuery("from ServiceMedCase where parent_id=:visitId")
                .setParameter("visitId", visitId)
                .getResultList();
        return serviceList.stream().map(ms -> {
                    PromedMedService pms = new PromedMedService();
                    pms.setAmount(ofNullable(ms.getMedServiceAmount()).orElse(1));
                    pms.setFinishTime(getDateTime(ms.getDateStart(), ms.getTimeExecute(), defaultDateStart));
                    pms.setMedserviceCode(ms.getMedService().getCode());
                    pms.setStartTime(pms.getFinishTime());
                    pms.setDoctor(ofNullable(ms.getWorkFunctionExecute()).map(this::mapDoctor).orElse(visitDoctor));
                    return pms;
                })
                .collect(Collectors.toList());

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
                .setParameter("visitId", visitId)
                .getResultList();
        return diary.isEmpty() ? "" : diary.get(0);
    }

    private PromedDoctor mapDoctor(WorkFunction wf) {
        if (wf instanceof PersonalWorkFunction) {
            PersonalWorkFunction doctor = (PersonalWorkFunction) wf;
            Patient person = doctor.getWorker().getPerson();
            return PromedDoctor.builder()
                    .lastName(person.getLastname())
                    .firstName(person.getFirstname())
                    .middleName(person.getMiddlename())
                    .snils(person.getSnils())
                    .workfunctionCode(ofNullable(doctor.getWorkFunction())
                            .map(VocWorkFunction::getFondSpeciality)
                            .map(VocIdCodeName::getCode)
                            .orElse(null))
                    .promedWorkstaffId(ofNullable(doctor.getPromedCodeWorkstaff())
                            .filter(code -> !code.isEmpty())
                            .map(Long::valueOf)
                            .orElse(null))
                    .build();
        } else {
            LOG.error("Рабочая функция для экспорта в промед - неперсональная!!! " + (wf == null ? "NULL" : wf.getId()));
            return null;
        }

    }

    private List<ShortMedCase> getAllVisitsInSpo(Long polyclinicCaseId) {
        return manager.createQuery("from ShortMedCase where parent_id  = :parentId and (noActuality is null or noActuality='0') and dateStart is not null order by dateStart , timeExecute ")
                .setParameter("parentId", polyclinicCaseId)
                .getResultList();
    }

    private Diagnosis getPrioryDiagnosis(List<Diagnosis> diagnoses) {
        return diagnoses == null || diagnoses.isEmpty()
                ? null
                : diagnoses.stream()
                .filter(d -> d.getPriority() != null && "1".equals(d.getPriority().getCode()))
                .findFirst().orElse(null);

    }

    private PromedDiagnosis mapDiagnosis(Diagnosis diagnosis, Long medcaseId) {
        if (diagnosis == null) {
            LOG.warn("Нет основного диагноза: " + medcaseId);
            return null;
        } else {
            return PromedDiagnosis.builder()
                    .promedId(diagnosis.getIdc10().getPromedCode())
                    .mkbCode(diagnosis.getIdc10().getCode())
                    .comment(diagnosis.getName())
                    .acuity(getVocCode(diagnosis.getIllnesPrimary()))
                    .diagReasonMkb(getVocCode(diagnosis.getIdc10Reason()))
                    .build();
        }

    }
}