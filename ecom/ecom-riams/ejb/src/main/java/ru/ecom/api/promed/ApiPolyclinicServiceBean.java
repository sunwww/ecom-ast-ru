package ru.ecom.api.promed;


import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.ecom.api.record.ApiRecordServiceBean;
import ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase;
import ru.ecom.mis.ejb.domain.medcase.ShortMedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocHospitalization;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocWorkPlaceType;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.poly.ejb.domain.voc.VocReason;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Сервис для выгрузки в промед поликлинических случаев
 */
@Stateless
@Local(IApiPolyclinicService.class)
@Remote(IApiPolyclinicService.class)
public class ApiPolyclinicServiceBean implements IApiPolyclinicService  {

    private static final Logger LOG = Logger.getLogger(ApiRecordServiceBean.class);

    private static final String POLYCLINICID="EvnPL_NumCard";           //уникальный номер талона
    private static final String ISCASEFINISHED="EvnPL_IsFinish";        //случай закончен (1 - да, 0 - нет)
    private static final String DATETIMEVISIT="Evn_setDT";              //дата и время посещения
    private static final String WFLPUSECTIION="LpuSection_id";          //отделение специалиста
    private static final String WORKSTAFF="MedStaffFact_id";            //ид врача
    private static final String WORKPLACETYPE="ServiceType_id";         //место обслуживания
    private static final String VISITREASON="VizitType_id";             //цель посещения
    private static final String SSTREAM="PayType_id";                   //тип оплаты
    private static final String MESID="Mes_id";                         //МЭС
    private static final String DESEASETYPE="DeseaseType_id";           //характер заболевания
    private static final String MEDCICALCARE="MedicalCareKind_id";      //идентификатор вида медицинской помощи
    private static final String MEDCICALCAREVAL="87";
    private static final String VISITID="MedosId";                      //id визита
    private static final String FIRSTVISITID="firstVisit";              //id первого визита
    private static final String VISITRESULT="ResultClass_id";           //результат обращения (обязательно, если случай закончен)
    private static final String DIAGRES="Diag_lid";                     //заключительный диагноз
    private static final String DIAGMKB="Diag_code";                     //заключительный диагноз (код МКБ)
    private static final String DIARY="diary";                          //заключение
    private static final String DOCTORWF="doctorWf";                    //должность врача
    private static final String DOCTORLASTNAME="doctorLastname";        //ФИО врача
    private static final String DOCTORFIRSTNAME="doctorFirstname";
    private static final String DOCTORMIDNAME="doctorMiddlename";
    private static final String DOCTORSNILS="doctorSnils";              //СНИЛС врача
    private static final String PATLASTNAME="lastname";                 //ФИО пациента
    private static final String PATFIRSTNAME="firstname";
    private static final String PATMIDNAME="middlename";
    private static final String PATSNILS="snils";                       //СНИЛС пациента
    private static final String PATBIRTHDAY="birthday";                 //ДР пациента
    private static final String WORKSTAFFINFO="WorkStaffInfo";          //инфо о враче (ФИО, должность, СНИЛС)
    private static final String PATIENT="patient";                      //пациент
    private static final String VISITS="visits";                        //визиты случая
    private static final String CACES="tap";                            //все визиты
    private static final String DOCTORPROMEDCODE = "promedCode_workstaff"; //ИД врача в промеде

    /**
     * Получить cписок закрытых случаев поликлинического обслуживания в JSON.
     *
     * @param dateTo MedCase.dateFinish
     * @param sstream VocServiceStream.code (по умолчанию - ОМС)
     * @return JSON in String
     */
    public String getPolyclinicCase(Date dateTo, String sstream, Boolean isUpload) {
        JSONArray jTap = new JSONArray();

            List<BigInteger> list = getAllVisitsBeforeDate(dateTo,sstream,isUpload);
            LOG.warn(list.size());
        for (BigInteger bigInteger : list) {
            JSONObject json = new JSONObject();

            Long polyclinicCaseId = bigInteger.longValue();

            PolyclinicMedCase polyclinicCase = theManager.find(PolyclinicMedCase.class, polyclinicCaseId);

            json.put(POLYCLINICID, polyclinicCaseId)
                    .put(ISCASEFINISHED, "1");

            List<ShortMedCase> allVisits = getAllVisitsInSpo(polyclinicCaseId, isUpload);
            if (allVisits.isEmpty()) {
                LOG.error("Законченный случай без визитов, быть такого не может");
                return "";
            }
            ShortMedCase lastVisit = allVisits.get(allVisits.size() - 1);
            if (lastVisit != null && lastVisit.getVisitResult() != null) {
                json.put(VISITRESULT, lastVisit.getVisitResult().getCodefpl());
                Object[] ds = getDiagnosisFromDiagnosisInVisit(lastVisit.getId());
                if (ds != null) {
                    json.put(DIAGRES, ds[0]);
                    json.put(DIAGMKB, ds[1]);
                }

            }

            Long firstResultId = allVisits.get(0).getId();
            JSONArray jVisit = new JSONArray();

            for (ShortMedCase visit : allVisits)
                jVisit.put(getVisitInJson(visit, firstResultId));

            json.put(PATIENT, getPatient(polyclinicCase));
            json.put(VISITS, jVisit);
            jTap.put(json);
        }
        return new JSONObject().put(CACES, jTap).toString();
    }
    /**
     * Получить ID всех СПО с датой закрытия.
     *
     * @param dateTo MedCase.dateFinish
     * @return List<BigInteger>
     */
    private List<BigInteger> getAllVisitsBeforeDate(Date dateTo,String sstream,Boolean isUpload) {
        return theManager.createNativeQuery("select m.id from medcase m " +
                "left join vocservicestream vss on vss.id=m.servicestream_id" +
                " left join workfunction wf on wf.id=m.finishfunction_id" +
                " left join vocworkfunction  vwf on vwf.id=wf.workfunction_id" +
                " where m.datefinish = :dateTo and m.dtype='PolyclinicMedCase' and (m.noactuality is null or m.noactuality=false)" +
                " and (m.upload is null or m.upload =false) and (vss.code=:sstream or vss.code is null)" +
                " and (vwf.isnodiagnosis is null or vwf.isnodiagnosis ='0') and (vwf.isFuncDiag is null or vwf.isFuncDiag='0') and (vwf.isLab is null or vwf.isLab='0')" +
                " and (select count(id) from medcase vis where (vis.noactuality is null or vis.noactuality = false)" +
                " and vis.visitResult_id!=11 and vis.parent_id = m.id) > 0" +
                " and :sstream= all(select code from vocservicestream vstr left join medcase vis on vstr.id=vis.servicestream_id where vis.parent_id=m.id)" +  (isUpload? " and (m.upload is null or m.upload=false)" : ""))
                .setParameter("dateTo",dateTo).setParameter("sstream",sstream).getResultList();
    }
    /**
     * Получить код из диагноза визита (заключительный основной клинический диагноз)
     *
     * @param visitId
     * @return Long
     */
    private Object[] getDiagnosisFromDiagnosisInVisit(Long visitId) {
        List<String[]> mkbPromedCode=theManager.createNativeQuery("select mkb.promedCode as dPromed,mkb.code as dCode" +
                " from diagnosis ds"+
                " left join vocidc10 mkb on mkb.id=ds.idc10_id"+
                " left join vocprioritydiagnosis vpd on vpd.id=ds.priority_id"+
                " where ds.medcase_id =:visitId and vpd.code='1' ").setParameter("visitId",visitId).getResultList();
        return mkbPromedCode.isEmpty() ? null : mkbPromedCode.get(0);
    }
    /**
     * Получить список всех визитов случая
     *
     * @param polyclinicCaseId
     * @return List<BigInteger>
     */
    private List<ShortMedCase> getAllVisitsInSpo(Long polyclinicCaseId,Boolean isUpload) {
        String upStr = isUpload? " and (upload is null or upload=false)" : "";
        return theManager.createQuery("from ShortMedCase where parent_id  = :parentId and (noActuality is null or noActuality='0') and dateStart is not null " + upStr + " order by dateStart , timeExecute ")
                .setParameter("parentId",polyclinicCaseId).getResultList();
    }
    /**
     * Получить заключение по визиту
     *
     * @param visitId
     * @return String
     */
    private String getDiaryInVisit(Long visitId) {
        List<String> diary = theManager.createNativeQuery("select record from Diary where medcase_id=:visitId")
                .setParameter("visitId",visitId).getResultList();
        return diary.isEmpty() ? "" : diary.get(0);
    }
    /**
     * Получить инфо по рабочей функции врача (ФИО, СНИЛС, ДОЛЖНОСТЬ)
     *
     * @param wfId workFunction.id
     * @return Object[]
     */
    private HashMap<Long, Object[]> doctorMap = new HashMap<>();
    private Object[] getDoctorInfo(Long wfId) {
        if (doctorMap.containsKey(wfId)) return doctorMap.get(wfId); //Не будем каждый раз искать врача в БД
        List<Object[]> docInfoList =  theManager.createNativeQuery("select vwf.name, wpat.lastname,wpat.firstname,wpat.middlename,wpat.snils,wf.promedCode_workstaff as promedCode" +
                " from Workfunction wf" +
                " left join Worker w on w.id=wf.worker_id" +
                " left join VocWorkfunction vwf on vwf.id=wf.workfunction_id" +
                " left join Patient wpat on wpat.id=w.person_id" +
                " where wf.id=:workfunctionId").setParameter("workfunctionId",wfId).getResultList();
        doctorMap.put(wfId, docInfoList.get(0));
        return docInfoList.get(0);
    }
    /**
     * Получить в JSON инфо по рабочей функции врача (ФИО, СНИЛС, ДОЛЖНОСТЬ)
     *
     * @param wf WorkFunction
     * @return JSONObject
     */
    private JSONObject getDoctorInfoInJson(WorkFunction wf) {
        JSONObject dccInfoJson = new JSONObject();
            Object[] doctorInfo=getDoctorInfo(wf.getId());
            if (doctorInfo!=null) {
                dccInfoJson.put(DOCTORWF,doctorInfo[0])
                        .put(DOCTORLASTNAME,doctorInfo[1])
                        .put(DOCTORFIRSTNAME,doctorInfo[2])
                        .put(DOCTORMIDNAME,doctorInfo[3])
                        .put(DOCTORSNILS,doctorInfo[4])
                        .put(DOCTORPROMEDCODE,doctorInfo[5])
                ;
            }
        return dccInfoJson;
    }
    /**
     * Получить в JSON инфо о пациенте
     *
     * @param polyclinicCase
     * @return JSONObject
     */
    private JSONObject getPatient(PolyclinicMedCase polyclinicCase) {
        Patient patientEntity = polyclinicCase.getPatient(); //Не может быть пациент пустым в СПО. Если будет - будем падать с ошибкой
        if (patientEntity == null) throw  new IllegalStateException("Не найден пациент");
            JSONObject patient = new JSONObject();
            patient.put(PATLASTNAME, patientEntity.getLastname())
                    .put(PATFIRSTNAME, patientEntity.getFirstname())
                    .put(PATMIDNAME, patientEntity.getMiddlename())
                    .put(PATBIRTHDAY, patientEntity.getBirthday())
                    .put(PATSNILS, patientEntity.getSnils());
            return patient;
    }
    /**
     * Получить весь визит в json
     *
     * @param visit
     * @param firstResultId
     * @return JSONObject
     */
    private JSONObject getVisitInJson(ShortMedCase visit,Long firstResultId) {
        JSONObject jsonVis = new JSONObject();
        WorkFunction wf = visit.getWorkFunctionExecute();
        VocReason vr = visit.getVisitReason();
        VocWorkPlaceType vwr = visit.getWorkPlaceType();
        VocServiceStream vss = visit.getServiceStream();
        VocHospitalization vh = visit.getHospitalization();
        jsonVis.put(DATETIMEVISIT, visit.getDateStart()+" "+ (visit.getTimeExecute()==null ? "" : visit.getTimeExecute()))
                .put(WFLPUSECTIION,  wf.getPromedCodeLpuSection())
                .put(WORKSTAFF, wf.getPromedCodeWorkstaff())
                .put(WORKPLACETYPE, (vwr==null || vwr.getOmcCode()==null)? "" : vwr.getOmcCode())
                .put(VISITREASON, (vr==null || vr.getOmcCode()==null)? "" : vr.getOmcCode())
                .put(SSTREAM, (vss==null || vss.getCode()==null)? "" : vss.getCode())
                .put(MESID, wf.getPromedCodeLpuSection())
                .put(DESEASETYPE, vh!=null ? vh.getCode() : "1")
                .put(MEDCICALCARE, MEDCICALCAREVAL)
                .put(VISITID, visit.getId())
                .put(FIRSTVISITID, (visit.getId() == firstResultId) ? "true" : "false")
         //       .put(DIAGRES, getPromedCodeFromDiagnosisInVisit(visit.getId()))
                .put(DIARY,getDiaryInVisit(visit.getId()))
                .put(WORKSTAFFINFO,getDoctorInfoInJson(wf));
        Object[] ds = getDiagnosisFromDiagnosisInVisit(visit.getId());
        if (ds!=null) {
            jsonVis.put(DIAGRES,ds[0]);
            jsonVis.put(DIAGMKB,ds[1]);
        }
        return jsonVis;
    }

    /**
     * Получить весь визит в json
     *
     * @param medcase_id Long id случая
     * @param tap_id String promed_id
     * @return JSONObject
     */
    public String setEvnTap(Long medcase_id, String tap_id) {
        JSONObject res = new JSONObject();
        List<String> info=theManager.createNativeQuery("select promedcode from medcase where id=:medcaseId").setParameter("medcaseId",medcase_id).getResultList() ;
        if (info.isEmpty()) {
            res.put("status","error")
                    .put("reason","medcase not found");
        } else {
            theManager.createNativeQuery("update medcase set promedcode=:tapId,upload=true where id=:medcaseId").setParameter("tapId",tap_id).setParameter("medcaseId",medcase_id).executeUpdate();
            res.put("status","Ok");
        }
        return res.toString();
    }
    private @PersistenceContext
    EntityManager theManager;
}