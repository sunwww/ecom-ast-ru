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

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;

/**Сервис для выгрузки в промед поликлинических случаев */

@Stateless
@Local(IApiPolyclinicService.class)
@Remote(IApiPolyclinicService.class)
public class ApiPolyclinicServiceBean implements IApiPolyclinicService  {
    private static final Logger LOG = Logger.getLogger(ApiRecordServiceBean.class);
    /** Список случаев поликлинического обслуживания
     * dateTo - период по*/
    public String getPolyclinicCase(String dateTo) {
        //Все СПО с датой окончания до dateTo
        String sql="select m.id from medcase m where\n" +
                "   (m.upload is null or m.upload =false)\n" +
                "   and servicestream_id = 1\n" +
                "   and datefinish = '"+dateTo+"'\n" +
                "   and dtype='PolyclinicMedCase'\n" +
                "   and (noactuality is null or noactuality=false)\n" +
                "   and (select isnodiagnosis from vocworkfunction\n" +
                "   where id =(select workfunction_id from workfunction where id =\n" +
                "   (select min(vis.workfunctionexecute_id) from medcase vis where vis.parent_id = m.id))) is null\n" +
                "   and (select count(id) from medcase vis where (vis.noactuality is null or vis.noactuality = false)\n" +
                "   and vis.visitResult_id!=11 and vis.parent_id = m.id) > 0\n" +
                "   and 1= all(select servicestream_id from medcase vis where parent_id=m.id)";
        JSONArray jtap = new JSONArray();
        List<BigInteger> list = theManager.createNativeQuery(sql).getResultList();
        for (int i=0; i<list.size(); i++) {
            Long id = list.get(i).longValue();
            LOG.warn(id);
            PolyclinicMedCase policlinicCase = theManager.find(PolyclinicMedCase.class,id);
            LOG.warn(policlinicCase);
            JSONObject json = new JSONObject();
            //EvnPL_NumCard - уникальный номер талона (medcase_id)
            json.put("EvnPL_NumCard", id);
            //EvnPL_IsFinish - случай закончен (1 - да, 0 - нет)
            json.put("EvnPL_IsFinish", policlinicCase.getDateFinish()!= null ? "1" : "0");
            //Визит в случае, из которого брать результат - пока это первый
            List<BigInteger> visitsForResult = theManager.createNativeQuery("select id from medcase\n" +
                    "   where servicestream_id is not null and id =" +
                    "   (select min(id) from medcase where (servicestream_id is not null) and parent_id  = "
                    + id +" and noactuality is not true) and parent_id ="+id).getResultList();
            Long firstResultId=0L;
            if (!visitsForResult.isEmpty()) {
                ShortMedCase visitForResult = theManager.find(ShortMedCase.class,visitsForResult.get(0).longValue());
                 firstResultId=visitsForResult.get(0).longValue();
                //Если случай закончен - обязательные поля
                //ResultClass_id - Результат обращения
                //ResultDeseaseType_id - Исход обращения
                if (policlinicCase.getDateFinish() != null && visitForResult.getVisitResult()!=null) {
                    json.put("ResultClass_id", visitForResult.getVisitResult().getPromedCode1())
                            .put("ResultClass_id", visitForResult.getVisitResult().getPromedCode2());
                }
                //Diag_lid - Заключ. диагноз (основной клинический)
                List<String> mkbPromedCode=theManager.createNativeQuery("select mkb.promedCode from vocidc10 mkb \n" +
                        "left join diagnosis ds on ds.idc10_id=mkb.id\n" +
                        "left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id\n" +
                        "left join vocprioritydiagnosis prior on prior.id=ds.priority_id\n" +
                        "where ds.medcase_id = " + visitForResult.getId() + " and prior.code='1' and reg.code='4'").getResultList();
                if (!mkbPromedCode.isEmpty())
                    json.put("Diag_lid", mkbPromedCode.get(0));
            }
            //Все визиты
            JSONArray jvisit = new JSONArray();
            List<BigInteger> allVisits = theManager.createNativeQuery(" select vis.id from medcase vis where parent_id=" + id +
                    "and servicestream_id is not null and visitresult_id != 11 and (noactuality  = false or noactuality is null)  " +
                    "and (select islab from vocWorkFunction where id =(select workfunction_id from workfunction where id=vis.workfunctionexecute_id)) is null").getResultList();
            for (BigInteger visitIndex : allVisits) {
                ShortMedCase visit = theManager.find(ShortMedCase.class,visitIndex.longValue());
                JSONObject jsonVis = new JSONObject();

                WorkFunction wf=visit.getWorkFunctionExecute();
                VocReason vr=visit.getVisitReason();
                VocWorkPlaceType vwr=visit.getWorkPlaceType();
                VocServiceStream vss=visit.getServiceStream();
                VocHospitalization vh=visit.getHospitalization();
                //TODO Диагнозы - основной клинический сейчас беру
                List<String> mkbPromedCode=theManager.createNativeQuery("select mkb.promedCode from vocidc10 mkb \n" +
                        "left join diagnosis ds on ds.idc10_id=mkb.id\n" +
                        "left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id\n" +
                        "left join vocprioritydiagnosis prior on prior.id=ds.priority_id\n" +
                        "where ds.medcase_id = " + visit.getId() + " and prior.code='1' and reg.code='4'").getResultList();
                try {
                    if (!mkbPromedCode.isEmpty())
                        jsonVis.put("Diag_id", mkbPromedCode.get(0));

                    jsonVis.put("Evn_setDT", isNull(visit.getDateStart() + " " + visit.getTimeExecute()) ? "" : visit.getDateStart() + " " + visit.getTimeExecute())
                            .put("LpuSection_id", isNull(wf) && isNull(wf.getPromedCode_lpusection()) ? null : wf.getPromedCode_lpusection())
                            .put("MedStaffFact_id", isNull(wf) && isNull(wf.getPromedCode_workstaff()) ? null : wf.getPromedCode_workstaff())
                            .put("TreatmentClass_id", isNull(vr) && isNull(vr.getPromedCode()) ? null : vr.getPromedCode())
                            .put("ServiceType_id", isNull(vwr) && isNull(vwr.getPromedCode()) ? null : vwr.getPromedCode())
                            .put("VizitType_id", isNull(vr) && isNull(vr.getPromedCode2()) ? null : vr.getPromedCode2())
                            .put("PayType_id", isNull(vss) && isNull(vss.getPromedCode()) ? null : vss.getPromedCode())
                            .put("Mes_id", isNull(wf.getPromedCode_lpusection()) ? null : wf.getPromedCode_lpusection())
                            .put("DeseaseType_id", isNull(vh.getPromedCode()) ? null : vh.getPromedCode())
                            .put("MedicalCareKind_id", "87")
                            .put("MedosId", visit.getId())
                            .put("firstVisit", (visit.getId()==firstResultId) ? "true" : "false");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                jvisit.put(jsonVis);
            }
            //Пациент
            Patient patientEntity = policlinicCase.getPatient();
            if (patientEntity!=null) {
                JSONObject patient = new JSONObject();
                patient.put("lastname", isNull(patientEntity.getLastname()) ? null : patientEntity.getLastname())
                        .put("firstname", isNull(patientEntity.getFirstname()) ? null : patientEntity.getFirstname())
                        .put("middlename", isNull(patientEntity.getMiddlename()) ? null : patientEntity.getMiddlename())
                        .put("birthday", isNull(String.valueOf(patientEntity.getBirthday())) ? null : patientEntity.getBirthday())
                        .put("snils", isNull(patientEntity.getSnils()) ? null : patientEntity.getSnils());
                json.put("patient", patient);
            }
            json.put("visits", jvisit);
            jtap.put(json);
        }
        return new JSONObject().put("tap", jtap).toString();
    }
    private static Boolean isNull(Object field) {
        return (field == null);
    }

    private @PersistenceContext
    EntityManager theManager;

    @Resource
    SessionContext theContext ;
}
