package ru.ecom.mis.ejb.service.sync.lpuattachment;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.mis.ejb.domain.patient.LpuAttachedByDepartment;
import ru.ecom.mis.ejb.service.synclpufond.ISyncLpuFondService;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.*;
import java.math.BigInteger;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static ru.nuzmsh.util.StringUtil.isNullOrEmpty;

/**
 * @author VTsybulin 01.12.2014
 * Импорт дефектов прикрепленного населения из фонда
 */
@Stateless
@Local(ISyncAttachmentDefectService.class)
@Remote(ISyncAttachmentDefectService.class)

public class SyncAttachmentDefectServiceBean implements ISyncAttachmentDefectService {
    private static final Logger LOG = Logger.getLogger(SyncAttachmentDefectServiceBean.class);
    /*
     * 1. Находим человека в базе
     * Есть человек найден, ищем его прикрепление по типу и по дате.
     * Если нашлось - добавляем в поля период дефекта, текст дефекта.
     * 		Меняем пользователя, дату и время редактирования записи.
     */
    private @PersistenceContext
    EntityManager manager;
    private @EJB
    ISyncLpuFondService syncService;
    private @EJB
    ILocalMonitorService monitorService;

    public String changeAttachmentArea(Long oldAreaId, Long newLpuId, Long newAreaId) {
        String sql = "update LpuAttachedByDepartment set area_id=" + newAreaId + ", lpu_id=" + newLpuId + " where area_id=" + oldAreaId;
        String sql2 = "update LpuAreaAddressText set area_id=" + newAreaId + " where area_id=" + oldAreaId;
        try {
            manager.createNativeQuery(sql2).executeUpdate();
            return "Изменено записей: " + manager.createNativeQuery(sql).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка при перекреплении: " + e;
        }
    }

    @Override
    public void cleanDefect(long attachmentId) {
        manager.createNativeQuery("update LpuAttachmentByDepartment set defectText='' where id=" + attachmentId).executeUpdate();
    }

    public LpuAttachedByDepartment getAttachment(long patientId, Date date, String method, String type) {
        LpuAttachedByDepartment lpu = null;
        String aDateType = "2".equals(type) ? "dateTo" : "dateFrom";
        try {
            String req = "Select id from LpuAttachedByDepartment where patient_id=" + patientId + " and " + aDateType + " =to_date('" + date + "','yyyy-MM-dd') and attachedType_id=(select id from vocattachedtype where code='" + method + "') ";
            List<Object> list = manager.createNativeQuery(req).getResultList();
            if (!list.isEmpty()) {
                lpu = manager.find(LpuAttachedByDepartment.class, Long.valueOf(list.get(0).toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lpu;
    }

    @Override
    public String importDefectFromXML(String fileContent) {
        try {
            if (fileContent != null && fileContent.startsWith("<")) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat formatOutput = new SimpleDateFormat("dd.MM.yyyy");
                SAXBuilder saxBuilder = new SAXBuilder();
                Document xdoc = saxBuilder.build(new StringReader(fileContent));
                org.jdom.Element rootElement = xdoc.getRootElement();
                List<org.jdom.Element> elements = rootElement.getChildren("ZAP");
                int i = 0;
                StringBuilder sb = new StringBuilder();
                for (org.jdom.Element el : elements) {
                    i++;
                    StringBuilder refrSB = new StringBuilder();
                    List<org.jdom.Element> refresions = el.getChildren("REFREASON");
                    List<String> goodDef = Arrays.asList("3", "4", "5", "6"); //некритичные дефекты
                    for (org.jdom.Element r : refresions) {
                        String refText = r.getText();
                        if ("включен в регистр".equalsIgnoreCase(refText)) {
                            refrSB.setLength(0);
                            break;
                        }
                        if (refText != null && !goodDef.contains(refText)) {
                            refrSB.append(refText).append(",");
                        }
                    }
                    String refreason;
                    String lastname = el.getChildText("FAM");
                    String firstname = el.getChildText("IM");
                    String middlename = el.getChildText("OT");
                    java.util.Date birthday = format.parse(el.getChildText("DR"));
                    String spPrik = el.getChildText("SP_PRIK");
                    String tPrik = el.getChildText("T_PRIK");
                    String datePrik = el.getChildText("DATE_1");

                    String birthday2 = formatOutput.format(birthday) + " г.р.";
                    String patientInfo = lastname + " " + firstname + " " + middlename + " " + birthday2;

                    String prikName;
                    if ("2".equals(tPrik)) { //При дефекте случая, поданного на открепление, не учитываем дефект(МАКС-М - 14 -открепление неправомерно, когда пациент уже откреплен)
                        refreason = "";
                        prikName = "Открепление";
                    } else {
                        refreason = refrSB.length() > 0 ? refrSB.substring(0, refrSB.length() - 1) : "";
                        prikName = "Прикрепление";
                    }
                    if (isNullOrEmpty(datePrik)) {
                        sb.append("green:").append(i).append(":::Открепление пациента ").append(patientInfo).append(" не может быть загружено. Дефекты по записи - ").append(refreason).append("#");
                        continue;
                    }

                    Long patientId = syncService.findPatientId(lastname, firstname, middlename, new java.sql.Date(birthday.getTime()));
                    if (patientId != null && patientId != 0) {
                        LpuAttachedByDepartment att = getAttachment(patientId, new java.sql.Date(format.parse(datePrik).getTime()), spPrik, tPrik);
                        if (att == null) {
                            sb.append("blue:").append(i).append(":").append(patientId).append("::").append(prikName).append(" не найдено в базе. Данные пациента= '").append(patientInfo).append("'#");
                        } else {
                            if (!refreason.equals("")) { //Дефект
                                sb.append("red:").append(i).append(":").append(patientId).append(":").append(att.getId()).append(":").append(prikName).append(" пациента '").append(patientInfo).append("'обновлено. Дефект='").append(refreason).append("'#");
                            } else { //Не дефект
                                sb.append("green:").append(i).append(":").append(patientId).append("::").append(prikName).append(" принято без дефектов. Данные пациента= '").append(patientInfo).append("'#");
                            }
                            att.setDefectText(refreason);
                            att.setDefectPeriod(formatOutput.format(new Date(new java.util.Date().getTime())));
                            manager.persist(att);
                        }
                    } else {
                        sb.append("black:").append(i).append(":::Пациент не найден в базе. Данные пациента= '").append(patientInfo).append("'#");
                    }
                }
                return sb.toString();
            } else {
                return importDefectFromCsv(fileContent);
            }
        } catch (Exception e) {
            LOG.error("------------" + fileContent, e);
            return "ERROR";
        }
    }

    /**
     * импорт либо Е, либо L файла с ошибкой ФЛК либо другой ошибкой
     * находим самое последнее прикрепление и помечаем его дефектным
     *
     * @param fileContent
     * @return
     */
    private String importDefectFromCsv(String fileContent) {
        StringBuilder sb = new StringBuilder();
        try (LineNumberReader in = new LineNumberReader(new StringReader(fileContent))) {
            String line;
            int i = 0;
            while ((line = in.readLine()) != null) {
                i++;
                String[] arr = line.replace("\"", "").split(";");
                String commonNumber = arr[1];
                if (StringUtil.isNotEmpty(commonNumber) && arr.length > 2) {
                    LpuAttachedByDepartment attached = getLastSendAttachmentByPersonCommonNumber(commonNumber);
                    if (attached != null) {
                        String defect = arr[2];
                        String patientInfo = attached.getPatient().getLastname() + " " + attached.getPatient().getFirstname() + " " + attached.getPatient().getMiddlename();
                        LOG.warn("attachment id " + attached.getId() + " with defect" + defect);
                        attached.setDefectText(defect);
                        attached.setDefectPeriod(DateFormat.formatCurrentDate());
                        sb.append("red:").append(i).append(":").append(attached.getPatient().getId())
                                .append(":").append(attached.getId()).append(": Информация была обновлена. Пациент - ").append(patientInfo).append(" Код дефекта - ").append(defect).append("#");
                    } else {
                        sb.append("red:").append(i).append(":::Не удалось найти прикрепление по записи №").append(arr[0]).append(" c RZ - ").append(commonNumber).append("#");
                    }
                } else {
                    sb.append("red:").append(i).append(":::Не удалось найти прикрепление по записи №").append(arr[0]).append("#");
                    LOG.warn("can't parse defect: '" + line + "'");
                }
            }
        } catch (IOException e) {
            LOG.error(e);
        }
        return sb.toString();
    }

    private LpuAttachedByDepartment getLastSendAttachmentByPersonCommonNumber(String commonNumber) {
        List<BigInteger> ids = manager.createNativeQuery("select att.id from patient pat" +
                " inner join LpuAttachedByDepartment att on att.patient_id = pat.id" +
                " where pat.commonNumber='" + commonNumber + "' order by att.exportdate desc limit 1 ").getResultList();
        return ids.isEmpty() ? null : manager.find(LpuAttachedByDepartment.class, ids.get(0).longValue());
    }
}