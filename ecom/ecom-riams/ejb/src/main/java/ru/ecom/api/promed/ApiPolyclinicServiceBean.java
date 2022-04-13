package ru.ecom.api.promed;


import org.apache.log4j.Logger;
import org.json.JSONObject;
import ru.ecom.api.entity.export.ExportType;
import ru.ecom.api.entity.export.MedCaseExportJournal;
import ru.ecom.api.form.PromedPolyclinicTapForm;
import ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase;
import ru.ecom.mis.ejb.service.promed.IPromedExportService;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

/**
 * Сервис для выгрузки в промед поликлинических случаев
 */
@Stateless
@Local(IApiPolyclinicService.class)
@Remote(IApiPolyclinicService.class)
public class ApiPolyclinicServiceBean implements IApiPolyclinicService {

    private static final Logger LOG = Logger.getLogger(ApiPolyclinicServiceBean.class);

    /**
     * Получить инфо по рабочей функции врача (ФИО, СНИЛС, ДОЛЖНОСТЬ)
     *
     * @param wfId workFunction.id
     * @return Object[]
     */
    private final HashMap<Long, Object[]> doctorMap = new HashMap<>();
    private @PersistenceContext
    EntityManager manager;

    private @EJB
    IPromedExportService promedExportService;

    @Override
    public List<PromedPolyclinicTapForm> getPolyclinicCase(Date dateTo, String serviceStream, Long wfId, Integer limitNum, Boolean isUpload) {
        return mapSpoList(getAllSPOByDateFinish(dateTo, serviceStream, wfId, limitNum, Boolean.TRUE.equals(isUpload)));
    }

    @Override
    public List<PromedPolyclinicTapForm> getPolyclinicCaseByVisitDateStart(LocalDate dateStart, String[] serviceStreams) {
        LOG.info("Export for day " + dateStart);
        return mapSpoList(getAllSPOByVisitCreateDate(dateStart, Arrays.asList(serviceStreams)));
    }

    private List<PromedPolyclinicTapForm> mapSpoList(List<BigInteger> spoIds) {

        List<PromedPolyclinicTapForm> returnList = new ArrayList<>();
        for (BigInteger bi : spoIds) {
            PromedPolyclinicTapForm form = promedExportService.getPolyclinicCase(manager.find(PolyclinicMedCase.class, bi.longValue()));
            if (form == null) {
                LOG.error("Не удалось сконвертировать СМО с ИД " + bi + "в правильный случай");
            } else {
                returnList.add(form);
            }
        }
        return returnList;
    }

    /**
     * Получить ID всех СПО с датой закрытия.
     *
     * @param dateTo        MedCase.dateFinish
     * @param serviceStream VocServiceStream.name
     * @param isUpload      Уже был выгружен?
     * @return List<BigInteger>
     */
    private List<BigInteger> getAllSPOByDateFinish(Date dateTo, String serviceStream, Long wfId, Integer limitNum, boolean isUpload) {
        return manager.createNativeQuery("select m.id from medcase m " +
                        "left join vocservicestream vss on vss.id=m.servicestream_id" +
                        " left join workfunction wf on wf.id=m.finishfunction_id" +
                        " left join vocworkfunction  vwf on vwf.id=wf.workfunction_id" +
                        " where m.datefinish = :dateTo and m.dtype='PolyclinicMedCase' and (m.noactuality is null or m.noactuality=false)" +
                        " and (vss.code=:sstream or vss.code is null)" +
                        " and (vwf.isnodiagnosis is null or vwf.isnodiagnosis ='0') and (vwf.isFuncDiag is null or vwf.isFuncDiag='0')" +
                        " and (vwf.isLab is null or vwf.isLab='0')" +
                        " and (select count(id) from medcase vis where vis.parent_id = m.id  and (vis.noactuality is null or vis.noactuality = false)" +
                        " and vis.visitResult_id!=11 and  vis.timeexecute is not null) > 0" +
                        (wfId != null ? " and wf.id = " + wfId : "") +
                        " and :sstream= all(select code from vocservicestream vstr left join medcase vis on vstr.id=vis.servicestream_id" +
                        " where vis.parent_id=m.id)" + (isUpload ? " and (m.upload is null or m.upload=false)" : "") +
                        (limitNum != null ? " limit " + limitNum : ""))
                .setParameter("dateTo", dateTo).setParameter("sstream", serviceStream).getResultList();
    }

    /**
     * Получить ID всех СПО, в которых есть визиты, созданные за указанную дату
     *
     * @param createDate     MedCase.createDate
     * @param serviceStreams VocServiceStream.name
     * @return List<BigInteger> ИД СПО
     */
    private List<BigInteger> getAllSPOByVisitCreateDate(LocalDate createDate, List<String> serviceStreams) {
        return manager.createNativeQuery("select distinct m.id from medcase visit" +
                        "    left join medcase m on m.id= visit.parent_id " +
                        "    left join vocservicestream vss on vss.id=m.servicestream_id" +
                        "    left join workfunction wf on visit.workfunctionexecute_id = wf.id" +
                        "    left join vocworkfunction  vwf on vwf.id=wf.workfunction_id" +
                        " where visit.datestart = :dateTo " +
                        "  and visit.timeexecute is not null" +
                        " and (visit.noactuality is null or visit.noactuality='0')" +
                        " and m.dtype='PolyclinicMedCase'" +
                        " and (m.noactuality is null or m.noactuality='0')" +
                        " and (vss.code in (:sstream))" +
                        " and (vwf.isnodiagnosis is null or vwf.isnodiagnosis='0') and (vwf.isFuncDiag is null or vwf.isFuncDiag='0')" +
                        " and (vwf.isLab is null or vwf.isLab ='0')" +
                        " and visit.visitResult_id!=11"
                )
                .setParameter("dateTo", Date.valueOf(createDate))
                .setParameter("sstream", serviceStreams)
                .getResultList();
    }


    /**
     * Установить отметку о выгрузке.
     *
     * @param medcaseId Long id случая
     * @param tapId     Long promed_id
     * @return JSONObject
     */
    @Override
    public String setEvnTap(Long medcaseId, Long tapId) {
        JSONObject res = new JSONObject();
        if (medcaseId == null) {
            res.put("status", "error")
                    .put("reason", "medcase_id is null");
        } else {
            if (manager.createNativeQuery("select id from medcase where id=:medcaseId").setParameter("medcaseId", medcaseId).getResultList().isEmpty()) {
                res.put("status", "error")
                        .put("reason", "medcase not found");
            } else {
                manager.createNativeQuery("update medcase set promedcode=:tapId,upload=true where id=:medcaseId")
                        .setParameter("tapId", tapId.toString()).setParameter("medcaseId", medcaseId).executeUpdate();
                res.put("status", "ok");
            }
        }
        return res.toString();
    }

    @Override
    public void createPacketLog(Long medcaseId, UUID packetGuid, ExportType exportType) {
        MedCaseExportJournal journal = new MedCaseExportJournal();
        journal.setExportType(exportType);
        journal.setMedCase(medcaseId);
        journal.setPacketGuid(packetGuid.toString());
        manager.persist(journal);
    }

    /**
     * Получить ФИО, отделение и promedcode_lpusection и promedcode_workstaff.
     *
     * @param workfunctionId WorkFunction.id
     * @return JSON in String
     */
    @Override
    public String getWfInfo(Long workfunctionId) {
        JSONObject res = new JSONObject();
        if (workfunctionId == null) {
            res.put("status", "error")
                    .put("reason", "workfunction is null");
        } else {
            List<Object[]> info = manager.createNativeQuery("select wpat.lastname||' '||wpat.firstname||' '||wpat.middlename,dep.name as dep" +
                    " ,wf.promedcode_lpusection as promedcode_lpusection,wf.promedcode_workstaff as promedcode_workstaff" +
                    " from Workfunction wf" +
                    " left join Worker w on w.id=wf.worker_id" +
                    " left join VocWorkfunction vwf on vwf.id=wf.workfunction_id" +
                    " left join Patient wpat on wpat.id=w.person_id" +
                    " left join mislpu dep on dep.id=coalesce(wf.lpu_id,w.lpu_id)" +
                    " where wf.id=:workfunctionId").setParameter("workfunctionId", workfunctionId).getResultList();
            if (info.isEmpty()) {
                res.put("status", "error")
                        .put("reason", "workfunction not found");
            } else {
                if (info.get(0)[0] != null)
                    res.put("fio", String.valueOf(info.get(0)[0]));
                if (info.get(0)[1] != null)
                    res.put("dep", String.valueOf(info.get(0)[1]));
                if (info.get(0)[2] != null)
                    res.put("promedcode_lpusection", String.valueOf(info.get(0)[2]));
                if (info.get(0)[3] != null)
                    res.put("promedcode_workstaff", String.valueOf(info.get(0)[3]));
            }
        }
        return res.toString();
    }

    /**
     * Установить promedcode_lpusection и promedcode_workstaff.
     *
     * @param workfunctionId       Long id рабочей функции врача
     * @param promedcodeLpuSection String promed_id
     * @param promedcodeWorkstaff  String promedcode_workstaff
     * @return JSONObject
     */
    public String setWfInfo(Long workfunctionId, Long promedcodeLpuSection, Long promedcodeWorkstaff) {
        JSONObject res = new JSONObject();
        if (workfunctionId == null) {
            res.put("status", "error")
                    .put("reason", "workfunction is null");
        } else {
            List<String> info = manager.createNativeQuery("select id from workfunction where id=:workfunctionId").setParameter("workfunctionId", workfunctionId).getResultList();
            if (info.isEmpty()) {
                res.put("status", "error")
                        .put("reason", "workfunction not found");
            } else {
                manager.createNativeQuery("update workfunction set promedcode_lpusection=:promedcode_lpusection,promedcode_workstaff=:promedcode_workstaff where id=:workfunctionId")
                        .setParameter("promedcode_lpusection", promedcodeLpuSection)
                        .setParameter("promedcode_workstaff", promedcodeWorkstaff)
                        .setParameter("workfunctionId", workfunctionId).executeUpdate();
                res.put("status", "ok");
            }
        }
        return res.toString();
    }

    @Override
    public String setDepInfo(Long departmentId, String promedcodeLpuSection) {
        JSONObject res = new JSONObject();
        if (departmentId!=null) {
            manager.createNativeQuery("update mislpu set promedlpusectionid = :promedLpuSection where id = :id")
                    .setParameter("id", departmentId)
                    .setParameter("promedLpuSection", promedcodeLpuSection)
                    .executeUpdate();
            res.put("status", "ok");

        } else {
            res.put("status", "error")
                    .put("reason", "departmentId is null");
        }
        return res.toString();
    }
}