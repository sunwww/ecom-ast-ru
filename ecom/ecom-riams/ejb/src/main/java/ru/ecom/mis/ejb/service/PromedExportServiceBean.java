package ru.ecom.mis.ejb.service;

import org.apache.log4j.Logger;
import ru.ecom.api.form.*;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase;
import ru.ecom.mis.ejb.domain.medcase.ShortMedCase;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocWorkPlaceType;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.PersonalWorkFunction;
import ru.ecom.poly.ejb.domain.voc.VocReason;
import ru.ecom.rabbit.IRabbitService;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.nuzmsh.util.EqualsUtil.isEquals;

@Stateless
@Local(IPromedExportService.class)
@Remote(IPromedExportService.class)
public class PromedExportServiceBean implements IPromedExportService {

    private static final Logger LOG = Logger.getLogger(PromedExportServiceBean.class);

    /**
     * Экспорт поликлинического случая в РИАМС Промед
     *
     * @param medCase
     */
    @Override
    public void exportPolyclinic(PolyclinicMedCase medCase) {
        validate(medCase);
        LOG.warn("validated");
        PromedPolyclinicTapForm form = getPolyclinicCase(medCase);
        LOG.warn("made form: " + form);
        try {
            rabbitService.sendPromedPolycMessage(form);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOG.warn("sent");
    }

    @Override
    public void exportPolyclinicById(Long medCaseId) {
        exportPolyclinic(manager.find(PolyclinicMedCase.class, medCaseId));
    }

    private void validate(PolyclinicMedCase medCase) {
    }

    public PromedPolyclinicTapForm getPolyclinicCase(PolyclinicMedCase polyclinicCase) {
        Long polyclinicCaseId = polyclinicCase.getId();
        List<ShortMedCase> allVisits = getAllVisitsInSpo(polyclinicCaseId);
        if (allVisits.isEmpty()) {
            throw new IllegalStateException("Законченный случай без визитов, быть такого не может");
        }
        PromedPolyclinicTapForm.PromedPolyclinicTapFormBuilder tap = PromedPolyclinicTapForm.builder();
        Patient pat = polyclinicCase.getPatient();
        tap.patient(PromedPatientForm.builder().lastName(pat.getLastname()).firstName(pat.getFirstname()).middleName(pat.getMiddlename())
                .snils(pat.getSnils()).birthDate(pat.getBirthday()).build());
        LOG.info("С датой окончания СПО  записей для выгрузки в промед" + polyclinicCase.getId());
        tap.isFinished(polyclinicCase.getDateFinish() != null);
        tap.ticketNumber(String.valueOf(polyclinicCaseId));

        ShortMedCase lastVisit = allVisits.get(allVisits.size() - 1);
        if (lastVisit != null && lastVisit.getVisitResult() != null) {
            tap.tapResult(lastVisit.getVisitResult().getCodefpl());
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
                    .workplaceCode(vwr.getOmcCode())
                    .diary(getDiaryInVisit(visit.getId()))
                    .serviceStream(vss.getCode()) //unused
                    .visitPurpose(vr.getOmcCode()) //1,2,3,4
                    .ishodCode(visit.getVisitResult().getCodefpl())
                    .medicalCareKindCode(mapMedicalCare(wf))
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
                .workfunctionCode("123")
                .lastName(person.getLastname())
                .firstName(person.getFirstname())
                .middleName(person.getMiddlename())
                .snils(person.getSnils())
                .birthDate(person.getBirthday())
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
        if (diagnosis ==null) {
            throw  new IllegalStateException("Нет основного диагноза");
        } else {
            return PromedDiagnosis.builder()
                    .promedId(diagnosis.getIdc10().getPromedCode())
                    .mkbCode(diagnosis.getIdc10().getCode())
                    .build() ;
        }

    }

    @PersistenceContext
    private EntityManager manager;

    private @EJB
    IRabbitService rabbitService;
}
