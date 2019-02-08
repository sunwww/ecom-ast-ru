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
    private static final String VISITRESULTTYPE="TreatmentClass_id";    //вид обращения
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
    private static final String VISITDISRESULT="ResultDeseaseType_id";  //исход обращения (обязательно, если случай закончен)
    private static final String DIAGRES="Diag_lid";                     //заключительный диагноз
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

    /**
     * Получить cписок случаев поликлинического обслуживания в JSON.
     *
     * @param dateTo MedCase.dateFinish
     * @return JSON in String
     */
    public String getPolyclinicCase(String dateTo) {
        JSONArray jTap = new JSONArray();

            List<BigInteger> list = getAllVisitsBeforeDate(dateTo);
            LOG.warn(list.size());
            for (int i = 0; i < list.size(); i++) {
                JSONObject json = new JSONObject();

                Long polyclinicCaseId = list.get(i).longValue();

                PolyclinicMedCase polyclinicCase = theManager.find(PolyclinicMedCase.class, polyclinicCaseId);

                json.put(POLYCLINICID, polyclinicCaseId)
                        .put(ISCASEFINISHED, polyclinicCase.getDateFinish() != null ? "1" : "0");

                ShortMedCase visitForResult = getVisitForResult(polyclinicCaseId);
                if (visitForResult != null) {
                    if (polyclinicCase.getDateFinish() != null && visitForResult.getVisitResult() != null)
                        json.put(VISITRESULT, visitForResult.getVisitResult().getPromedCode1())
                                .put(VISITDISRESULT, visitForResult.getVisitResult().getPromedCode2());
                    json.put(DIAGRES, getPromedCodeFromDiagnosisInVisit(visitForResult.getId()));
                }

                Long firstResultId = getFirstVisitId(polyclinicCaseId);
                JSONArray jVisit = new JSONArray();
                List<BigInteger> allVisits = getAllVisitsInSpo(polyclinicCaseId);
                for (BigInteger visitIndex : allVisits)
                    jVisit.put(getVisitInJson(theManager.find(ShortMedCase.class, visitIndex.longValue()),firstResultId));

                json.put(PATIENT, getPatient(polyclinicCase));
                json.put(VISITS, jVisit);
                jTap.put(json);
            }
        return new JSONObject().put(CACES, jTap).toString();
    }
    /**
     * Получить ID всех визиты с датой закрытия.
     *
     * @param dateTo MedCase.dateFinish
     * @return List<BigInteger>
     */
    private List<BigInteger> getAllVisitsBeforeDate(String dateTo) {
        return theManager.createNativeQuery(new StringBuilder().append("select m.id from medcase m where (m.upload is null or m.upload =false) and servicestream_id = 1" +
                "   and datefinish = '").append(dateTo).append("' and dtype='PolyclinicMedCase' and (noactuality is null or noactuality=false)" +
                "   and (select isnodiagnosis from vocworkfunction where id =(select workfunction_id from workfunction where id =" +
                "   (select min(vis.workfunctionexecute_id) from medcase vis where vis.parent_id = m.id))) is null" +
                "   and (select count(id) from medcase vis where (vis.noactuality is null or vis.noactuality = false)" +
                "   and vis.visitResult_id!=11 and vis.parent_id = m.id) > 0" +
                "   and 1= all(select servicestream_id from medcase vis where parent_id=m.id)")
                .toString()).getResultList();
    }
    /**
     * Получить визит, из которого брать результат - пока это первый
     *
     * @param polyclinicCaseId
     * @return ShortMedCase
     */
    private ShortMedCase getVisitForResult(Long polyclinicCaseId) {
        List<ShortMedCase> visitsForResult = theManager.createQuery("from ShortMedCase" +
                "   where servicestream_id is not null and id =(select min(id) from ShortMedCase where (servicestream_id is not null)" +
                " and parent_id  = :pCId and noactuality is not true) and parent_id =:pCId").setParameter("pCId",polyclinicCaseId).getResultList();
        return(visitsForResult.isEmpty())? null: visitsForResult.get(0);
    }
    /**
     * Получить id первого визита (на случай, если результат брать не из первого)
     *
     * @param polyclinicCaseId
     * @return Long
     */
    private Long getFirstVisitId(Long polyclinicCaseId) {
        List<BigInteger> firstId = theManager.createNativeQuery(new StringBuilder().append("select id from Medcase" +
                        "   where servicestream_id is not null and id =(select min(id) from Medcase where (servicestream_id is not null)" +
                        " and parent_id  = ").append(polyclinicCaseId).append(" and noactuality is not true) and parent_id =")
                .append(polyclinicCaseId).toString()).getResultList();
        return(firstId.isEmpty())? null: firstId.get(0).longValue();
    }
    /**
     * Получить код из диагноза визита (заключительный основной клинический диагноз)
     *
     * @param visitId
     * @return Long
     */
    private String getPromedCodeFromDiagnosisInVisit(Long visitId) {
        List<String> mkbPromedCode=theManager.createNativeQuery(new StringBuilder().append("select mkb.promedCode from vocidc10 mkb" +
                " left join diagnosis ds on ds.idc10_id=mkb.id" +
                " left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id" +
                " left join vocprioritydiagnosis prior on prior.id=ds.priority_id" +
                " where prior.code='1' and reg.code='4' and ds.medcase_id = ").append(visitId).toString()).getResultList();
        return (mkbPromedCode.isEmpty())? null:mkbPromedCode.get(0);
    }
    /**
     * Получить список ID всех визитов случая
     *
     * @param polyclinicCaseId
     * @return List<BigInteger>
     */
    private List<BigInteger> getAllVisitsInSpo(Long polyclinicCaseId) {
        return theManager.createNativeQuery(new StringBuilder().append(" select vis.id from medcase vis where parent_id=").append(polyclinicCaseId)
                .append("and servicestream_id is not null and visitresult_id != 11 and (noactuality  = false or noactuality is null)  " +
                "and (select islab from vocWorkFunction where id =(select workfunction_id from workfunction where id=vis.workfunctionexecute_id)) is null")
                .toString()).getResultList();
    }
    /**
     * Получить заключение по визиту
     *
     * @param visitId
     * @return String
     */
    private String getDiaryVisit(Long visitId) {
        List<String> diary=theManager.createNativeQuery(new StringBuilder().append("select record from Diary where medcase_id=")
                .append(visitId).toString()).getResultList();
        return (diary.isEmpty())? null:diary.get(0);
    }
    /**
     * Получить инфо по рабочей функции врача (ФИО, СНИЛС, ДОЛЖНОСТЬ)
     *
     * @param wfId workFunction.id
     * @return Object[]
     */
    private Object[] getDoctorInfo(Long wfId) {
        List<Object[]> docInfoList =  theManager.createNativeQuery(new StringBuilder().append("select vwf.name, wpat.lastname,wpat.firstname,wpat.middlename,wpat.snils" +
                " from Workfunction wf" +
                " left join Worker w on w.id=wf.worker_id" +
                " left join VocWorkfunction vwf on vwf.id=wf.workfunction_id" +
                " left join Patient wpat on wpat.id=w.person_id" +
                " where wf.id=").append(wfId).toString()).getResultList();
        return (docInfoList.isEmpty())? null:docInfoList.get(0);
    }
    /**
     * Получить в JSON инфо по рабочей функции врача (ФИО, СНИЛС, ДОЛЖНОСТЬ)
     *
     * @param wf WorkFunction
     * @return JSONObject
     */
    private JSONObject getDoctorInfoInJson(WorkFunction wf) {
        JSONObject dccInfoJson = new JSONObject();
        if (wf!=null) {
            Object[] doctorInfo=getDoctorInfo(wf.getId());
            if (doctorInfo!=null) {
                dccInfoJson.put(DOCTORWF,doctorInfo[0])
                        .put(DOCTORLASTNAME,doctorInfo[1])
                        .put(DOCTORFIRSTNAME,doctorInfo[2])
                        .put(DOCTORMIDNAME,doctorInfo[3])
                        .put(DOCTORSNILS,doctorInfo[4]);
            }
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
        Patient patientEntity = polyclinicCase.getPatient();
        if (patientEntity != null) {
            JSONObject patient = new JSONObject();
            patient.put(PATLASTNAME, patientEntity.getLastname())
                    .put(PATFIRSTNAME, patientEntity.getFirstname())
                    .put(PATMIDNAME, patientEntity.getMiddlename())
                    .put(PATBIRTHDAY, patientEntity.getBirthday())
                    .put(PATSNILS, patientEntity.getSnils());
            return patient;
        }
        else return null;
    }
    /**
     * Получить весь визит в json
     *
     * @param visit
     * @param firstResultId
     * @return JSONObject
     */
    //Весь визит в json
    private JSONObject getVisitInJson(ShortMedCase visit,Long firstResultId) {
        JSONObject jsonVis = new JSONObject();

        WorkFunction wf = visit.getWorkFunctionExecute();
        VocReason vr = visit.getVisitReason();
        VocWorkPlaceType vwr = visit.getWorkPlaceType();
        VocServiceStream vss = visit.getServiceStream();
        VocHospitalization vh = visit.getHospitalization();

        jsonVis.put(DATETIMEVISIT, new StringBuilder().append((visit.getDateStart()==null)? "":visit.getDateStart())
                .append((visit.getTimeExecute()==null)? "":visit.getTimeExecute()))
                .put(WFLPUSECTIION,  wf.getPromedCode_lpusection())
                .put(WORKSTAFF, wf.getPromedCode_workstaff())
                .put(VISITRESULTTYPE, vr.getPromedCode())
                .put(WORKPLACETYPE, vwr.getPromedCode())
                .put(VISITREASON, vr.getPromedCode2())
                .put(SSTREAM, vss.getPromedCode())
                .put(MESID, wf.getPromedCode_lpusection())
                .put(DESEASETYPE, vh.getPromedCode())
                .put(MEDCICALCARE, MEDCICALCAREVAL)
                .put(VISITID, visit.getId())
                .put(FIRSTVISITID, (visit.getId() == firstResultId) ? "true" : "false")
                .put(DIAGRES, getPromedCodeFromDiagnosisInVisit(visit.getId()))
                .put(DIARY,getDiaryVisit(visit.getId()))
                .put(WORKSTAFFINFO,getDoctorInfoInJson(wf));
        return jsonVis;
    }

    private @PersistenceContext
    EntityManager theManager;
}