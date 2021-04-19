package ru.ecom.mis.web.dwr.prescription;

import org.apache.log4j.Logger;
import org.jdom.IllegalDataException;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.service.contract.IContractService;
import ru.ecom.mis.ejb.service.prescription.IPrescriptionService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.tags.helper.RolesHelper;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Сервис назначений
 *
 * @author STkacheva
 */
public class PrescriptionServiceJs {
    private static final Logger LOG = Logger.getLogger(PrescriptionServiceJs.class);

    //создаем назначение в листе назначений
    public Long createServicePrescription(Long aMedServiceId, Long aPrescriptionId, HttpServletRequest aRequest) throws NamingException {
        IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class);
        return service.createServicePrescription(aMedServiceId, aPrescriptionId);
    }

    /* Получение ИД отделения по рабочей функции */
    public Long getDepartmentFromWorkfunction(Long aWorkfunctionId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return Long.valueOf(service.executeNativeSql("select coalesce(wf.lpu_id, w.lpu_id) as lpuId" +
                " from workfunction wf" +
                " left join worker w on w.id=wf.worker_id" +
                " where wf.id = " + aWorkfunctionId).iterator().next().get1().toString());
    }

    private boolean allowByPrescriptionType(Long aMedServiceId, Long aPresctiptionType, IWebQueryService aService) {
        return aService.executeNativeSql("select wfs.id from workfunctionservice wfs where wfs.medservice_id = " + aMedServiceId
                + " and wfs.prescripttype_id = " + aPresctiptionType).isEmpty(); //Если не нашли запрещающего правила - то разрешено
    }

    public String getAllowedLabServiciesByPatient(Long aPatientId, Long aPrescriptionType, HttpServletRequest aRequest) throws NamingException {
        JSONArray ret = new JSONArray();
        IContractService contractService = Injection.find(aRequest).getService(IContractService.class);
        List<Object[]> serviceList = contractService.getPaidMedServicesByPatient(aPatientId, null, null, null, null, "LABSURVEY");

        if (!serviceList.isEmpty()) {
            IWebQueryService webQueryService = Injection.find(aRequest).getService(IWebQueryService.class);
            for (Object[] row : serviceList) {
                JSONObject el = new JSONObject();
                Long medServiceId = Long.valueOf(row[2].toString());
                el.put("serviceName", row[0] + " " + row[3]);
                el.put("serviceId", medServiceId);
                el.put("caosId", row[1]);
                el.put("isAllow", aPrescriptionType == null || allowByPrescriptionType(medServiceId, aPrescriptionType, webQueryService));
                ret.put(el);
            }
        }
        return ret.toString();
    }

    /**
     * Прием пробирки в лабораторию по номеру штрих-кода
     *
     * @param aBarcodeNumber Штрих-код пробирки
     * @param aRequest       -
     * @return Текстовая информация о результате приема
     * @throws NamingException
     * @throws JspException
     */
    public String checkTransferServiceBarcode(String aBarcodeNumber, HttpServletRequest aRequest) throws NamingException, JspException {
        if (aBarcodeNumber != null && !aBarcodeNumber.equals("")) {
            IWebQueryService wqs = Injection.find(aRequest).getService(IWebQueryService.class);
            StringBuilder sql = new StringBuilder();
            sql.append("select gwf.id as labCabinet , msGr.id as groupId, replace(list(''||p.id),' ','') as prescIds, replace(list(''||ms.id),' ','')as medServIds" +
                    ", p.transferdate as transferDate")
                    .append(" from prescription p")
                    .append(" left join medservice ms on ms.id=p.medservice_id")
                    .append(" left join medservice msGr on msGr.id=ms.parent_id")
                    .append(" left join workfunctionservice wfs on wfs.medservice_id=msGr.id")
                    .append(" left join workfunction gwf on gwf.id=wfs.workfunction_id").append(" where p.barcodeNumber='").append(aBarcodeNumber.trim()).append("' and gwf.isDefaultLabCabinet='1'")
                    .append(" group by gwf.id, msGr.id, p.transferDate");
            Collection<WebQueryResult> list = wqs.executeNativeSql(sql.toString());
            if (list.isEmpty()) {
                return "Ошибка! Не удается принять исследования, не найден штрих код или нет кабинета для приема.";
            } else if (list.size() > 1) {
                return "Ошибка! Не удается принять исследования, не найден штрих код или слишком много кабинетов для приема (" + list.size() + ")!";
            } else {
                WebQueryResult r = list.iterator().next();
                if (r.get5() != null && !r.get5().toString().equals("")) {
                    return "Пробирка с штрих-кодом №" + aBarcodeNumber + " уже передана в лабораторию!";
                }
                String pres = r.get3().toString();
                String services = r.get4().toString();
                String prescriptionList = r.get2().toString() + "#" + r.get1().toString() + "#" + pres + "#" + services;

                checkTransferService(prescriptionList, aRequest);
                setDefaultDiary(pres, services, aRequest);
                Collection<WebQueryResult> list2 = wqs.executeNativeSql("select coalesce(vsst.name,'---') as f5vsstname" + " ,coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id) as f3codemed" + " ,pat.lastname ||' '|| pat.firstname ||' '|| pat.middlename as fio" + " ,to_char(pat.birthday,'dd.mm.yyyy') ||' г.р.' as f9birthday" + " ,list(case when vst.code='LABSURVEY' " + " then ms.code||coalesce('('||ms.additionCode||')','')||' '||ms.name||coalesce(vpt.shortname,'') else null end) as f10medServicies" + " from prescription p" + " left join medservice ms on ms.id=p.medservice_id" + " left join medservice msGr on msGr.id=ms.parent_id" + " left join workfunctionservice wfs on wfs.medservice_id=msGr.id" + " left join workfunction gwf on gwf.id=wfs.workfunction_id" + " left join PrescriptionList pl on pl.id=p.prescriptionList_id" + " left join MedCase slo on slo.id=pl.medCase_id" + " left join MedCase sls on sls.id=slo.parent_id" + " left join VocServiceSubType vsst on vsst.id=ms.serviceSubType_id" + " left join StatisticStub ssSls on ssSls.id=sls.statisticstub_id" + " left join StatisticStub ssSlo on ssSlo.id=slo.statisticstub_id" + " left join Patient pat on pat.id=slo.patient_id" + " left join VocServiceType vst on vst.id=ms.serviceType_id" + " left join VocPrescriptType vpt on vpt.id=p.prescriptType_id" + " where p.barcodeNumber='" + aBarcodeNumber.trim() + "' and gwf.isDefaultLabCabinet='1'" + " group by gwf.id, msGr.id, p.transferDate,ssSls.code,ssslo.code,pl.medCase_id,vsst.name," + " pat.lastname,pat.firstname,pat.middlename,pat.birthday");
                WebQueryResult re = list2.iterator().next();
                return "Передано!\n" + re.get1().toString() + "\n" + "№ ИБ: " + re.get2().toString() + "\n" + re.get3().toString() + " " + re.get4().toString() + "\n" + re.get5().toString();
            }
        }
        return null;
    }

    /**
     * Копирование назначения с новой услугой (для лаборатории)
     *
     * @param aPrescriptionId ИД назначения
     * @param aMedServiceId   ИД новой мед. услуги
     * @param aRequest        -
     * @return
     * @throws NamingException
     */
    public Long duplicatePrescription(Long aPrescriptionId, Long aMedServiceId, HttpServletRequest aRequest) throws NamingException {
        if (aPrescriptionId != null && aPrescriptionId > 0) {
            IWebQueryService wqs = Injection.find(aRequest).getService(IWebQueryService.class);
            IPrescriptionService psb = Injection.find(aRequest).getService(IPrescriptionService.class);
            String login = LoginInfo.find(aRequest.getSession(true)).getUsername();
            String workFunction = wqs.executeNativeSql("select wf.id " +
                    "from secuser su left join workfunction wf on wf.secuser_id=su.id " +
                    "where su.login='" + login + "'").iterator().next().get1().toString();
            return psb.clonePrescription(aPrescriptionId, aMedServiceId, Long.parseLong(workFunction), login);
        }
        return null;
    }

    private void createAnnulMessageByPrescription(Long aPrescriptionId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        try {
            Long id = Long.valueOf(service.executeNativeSql("select max(id) from AdminChangeJournal where prescription=" + aPrescriptionId).iterator().next().get1().toString());
            createAnnulMessage(id, aRequest);
        } catch (Exception e) {
            LOG.error("Ex=", e);
        }
    }

    private void createAnnulMessage(Long aAnnulJournalRecordId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        List<Object[]> list = service.executeNativeSqlGetObj("select mc.id" +
                " ,pat.lastname||' '||pat.firstname||' '||pat.middlename as fio" +
                " ,ms.name" +
                " ,vwf.name||' '||wpat.lastname||' '||wpat.firstname||' '||wpat.middlename as cancelPerson" +
                " ,to_char(apj.createDate,'dd.mm.yyyy') || ' '|| to_char(apj.createTime,'HH24:MI')  as dtime" +
                ", p.createusername as prescreateuser" +
                " ,apj.annulReason" +
                " ,apj.createusername as cancelUser" +
                " from AdminChangeJournal apj" +

                " left join prescription p on p.id=apj.prescription" +
                " left join medservice ms on ms.id=p.medservice_id left join prescriptionlist pl on pl.id=p.prescriptionlist_id " +
                " left join medcase mc on mc.id=pl.medcase_id left join patient pat on pat.id=mc.patient_id " +
                " left join workfunction wf on wf.id=apj.annulWorkFunction" +
                " left join vocworkfunction vwf on vwf.id=wf.workfunction_id" +
                " left join worker w on w.id=wf.worker_id" +
                " left join patient wpat on wpat.id=w.person_id" +
                " where apj.id=" + aAnnulJournalRecordId + "");
        if (!list.isEmpty()) {
            Object[] obj = list.get(0);
            String username = "" + obj[5];

            for (int i = 0; i < 2; i++) { //экстренное и плановое сообщение
                IPrescriptionService bean = Injection.find(aRequest).getService(IPrescriptionService.class);
                String msgText = "Результаты исследования ''" + obj[2] + "'' пациента ''" + obj[1] + "'' были аннулированы сотрудником " +
                        obj[3] + " " + obj[4] + ". Причина: " + obj[6] + "'";
                bean.sendMessageCurrentDate(msgText, "Аннулирование результатов исследования", username
                        , obj[7].toString(), "entityParentView-stac_slo.do?id=" + obj[0].toString(), i < 1);
            }

        }
    }

    /**
     * Аннулируем результат выполнения назначения (отмена выполнения)
     *
     * @param aPrescriptionId -ID назначения
     * @param aReason         - причина аннулирования
     * @param aRequest        -
     * @return
     * @throws NamingException
     */
    public String annulPrescription(Long aPrescriptionId, String aReason, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService wqs = Injection.find(aRequest).getService(IWebQueryService.class);
        Long medCaseId;
        try {
            medCaseId = Long.parseLong(wqs.executeNativeSql("select medcase_id from prescription where id=" + aPrescriptionId).iterator().next().get1().toString());
        } catch (java.lang.NullPointerException e) {
            LOG.error("Пытаемся отменить невыполненное назначение!");
            return "Исследование не выполнено, аннулировать результат невозможно";
        } catch (Exception e) {
            LOG.error("Ex=", e);
            return "Системная ошибка: " + e.getMessage();
        }
        String login = LoginInfo.find(aRequest.getSession(true)).getUsername();
        String cancelWf = wqs.executeNativeSql("select wf.id " +
                "from secuser su left join workfunction wf on wf.secuser_id=su.id " +
                "where su.login='" + login + "'").iterator().next().get1().toString();
        if (aReason == null) {
            aReason = "";
        }

        //Аннулируем медкейс
        wqs.executeUpdateNativeSql("update medcase set datestart = null , editdate = current_date, edittime = current_time, editusername='" + login + "' where id=" + medCaseId);

        String cancelFunction = Injection.find(aRequest).getService(IPrescriptionService.class).getWorkfuntctionInfoByLabTechUsername(login);
        updateDiaryWhileCancelPrescription(medCaseId, null, "Результаты анализа были аннулированы: " + aReason, cancelFunction, wqs); //запись в дневник
        insertRecordAnnulJournal(aPrescriptionId, medCaseId, login, cancelWf, aReason, aRequest); //сообщение в журнал администратору
        createAnnulMessageByPrescription(aPrescriptionId, aRequest); //сообщение врачу
        return "Результат исследования аннулирован";
    }

    /*В начале дневника пишем информацию о том что результат исследования аннулирован*/
    private void updateDiaryWhileCancelPrescription(Long aMedcaseId, String aPrescriptIds, String aCancelText, String aCancelDoctor, IWebQueryService aService) {
        String sql = "update diary set record='Брак биоматериала: " + aCancelText + ". Дата и время: '" +
                "||to_char(current_date,'dd.mm.yyyy')||' '||to_char(current_timestamp,'HH24:MI:SS')||chr(13)||" +
                "'Отбраковал: " + aCancelDoctor + "'||chr(13)||chr(13)||record" + ", editdate = current_date, edittime=current_time where " + (aMedcaseId != null ? "medcase_id=" + aMedcaseId
                : "medcase_id=ANY(select medcase_id from prescription  where id in (" + aPrescriptIds + "))");
        aService.executeUpdateNativeSql(sql);
    }

    /**
     * Создание записи в журнале аннулирования назначения
     *
     * @param aPrescription
     * @param aMedCase
     * @param aUsername
     * @param aCancelWf
     * @param aReason
     * @param aRequest
     * @throws NamingException
     */
    private void insertRecordAnnulJournal(Long aPrescription, Long aMedCase, String aUsername, String aCancelWf, String aReason, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService wqs = Injection.find(aRequest).getService(IWebQueryService.class);
        if (aUsername == null || aUsername.equals("")) {
            aUsername = LoginInfo.find(aRequest.getSession(true)).getUsername();
        }
        if (aReason == null) {
            aReason = "";
        }
        String sql = "insert into AdminChangeJournal (cType, prescription, medcase, createDate, createTime, createUsername, annulReason, annulWorkFunction, prescriptWorkFunction, annulRecord) " +
                "values ('UN_PRESCRIPT'," + aPrescription + ", " + aMedCase + ", current_date, current_time, '" + aUsername + "', '" + aReason + "'," + aCancelWf + ", (select prescriptspecial_id from prescription where id = " + aPrescription + ")" +
                ",(select record from diary where medcase_id=" + aMedCase + "))";
        wqs.executeUpdateNativeSql(sql);

    }

    /**
     * Отменяем невыполненное назначение
     *
     * @param aMedService
     * @param aWorkCalendarTime
     * @param aPrescriptionId
     * @param aRequest
     * @throws NamingException
     */
    private String annulEmptyPrescription(Long aMedService, Long aWorkCalendarTime, Long aPrescriptionId, String aReason, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        boolean isAnnulPermitted;
        if (aPrescriptionId == null && aWorkCalendarTime != null && aWorkCalendarTime > 0L) { //Аннулируем назначение по времени, на которое назначено.
            Collection<WebQueryResult> wqrList = service.executeNativeSql("select p.id from prescription p where p.calendartime_id=" + aWorkCalendarTime + " and p.medService_id=" + aMedService);
            if (wqrList.isEmpty()) {
                return "Не найдено назначения для аннулирования";
            } else {//можно аннулировать
                WebQueryResult wqr = wqrList.iterator().next();
                aPrescriptionId = Long.valueOf(wqr.get1().toString());
            }
        }
        if (aPrescriptionId != null && aPrescriptionId > 0L) { //Аннулируем по ИД назначения
            if (aReason == null || aReason.trim().equals("")) {
                return "Необходимо указать причину аннулирования!";
            }
            String canAnnulSql = "select mc.id as id, case when p.canceltime is null and p.transferdate is null" +
                    " and mc.datestart is null then '1' else '0' end as isCan" +
                    " , wct.id as f3_calendarTime" +
                    " ,p.medservice_id as f4_medService" +
                    " from prescription p " +
                    " left join workcalendartime wct on wct.id = p.calendartime_id" +
                    " left join medcase mc on mc.id = wct.medcase_id" +
                    " where p.id=" + aPrescriptionId;
            WebQueryResult wqrAnnul = service.executeNativeSql(canAnnulSql).iterator().next();
            isAnnulPermitted = wqrAnnul.get2().toString().equals("1");
            if (isAnnulPermitted) {
                String medcaseId = wqrAnnul.get1() != null ? wqrAnnul.get1().toString() : null;
                aWorkCalendarTime = wqrAnnul.get3() != null ? Long.valueOf(wqrAnnul.get3().toString()) : null;
                aMedService = wqrAnnul.get4() != null ? Long.valueOf(wqrAnnul.get4().toString()) : null;
                canAnnulSql = "update prescription " +
                        " set cancelDate = current_date, " +
                        " canceltime = current_time, " +
                        " cancelReasonText = '" + aReason + "' ," +
                        " cancelusername = '" + username + "' ," +
                        " cancelspecial_id = (select wf.id from secuser su left join workfunction wf on wf.secuser_id=su.id where su.login='" + username + "') " +
                        ", calendartime_id = null" +
                        " where id=" + aPrescriptionId;
                service.executeUpdateNativeSql(canAnnulSql); //Отметили назначение как аннулированное
                if (medcaseId != null) {
                    if (aMedService != null) {
                        service.executeUpdateNativeSql("update medcase set parent_id=null, medService_id = null where parent_id= " + medcaseId + " and dtype='ServiceMedCase' and medService_id=" + aMedService);// помечаем услуг как недействующие у визита
                    }
                    service.executeUpdateNativeSql("update medcase set noactuality='1' where id= " + medcaseId + " and 0=(select count(*) from medcase where parent_id='" + medcaseId + "' and (noactuality is null or noactuality='0'))"); //Если у видита не осталось активных услуг - помечаем его как недействительный
                    service.executeUpdateNativeSql("update workcalendartime set prescription=null, medcase_id = null where id=" + aWorkCalendarTime
                            + " and 0=(select count(*) from medcase where id='" + medcaseId + "'and (noactuality is null or noactuality='0'))");// Если визит недействительный - очищаем время в расписании
                }
                service.executeUpdateNativeSql("update contractaccountoperationbyservice set serviceid=null, servicetype= null where serviceType = 'PRESCRIPTION' and serviceId = " + aPrescriptionId); //аннулируем информацию в договоре
                //если есть браслет с назначением, то ему надо проставить дату окончания
                cancelBraceletetIfExists(aPrescriptionId, aRequest);
                return "Назначение отменено!";
            } else {
                return "Невозможно отменить назначение! Уже было отменено или находится в работе";
            }
        }
        return "Ошибка при определении контекста импедантности!";
    }

    /**
     * Снимаем браслет, если он связан с отменяемым назначением
     *
     * @param aCanseledPrescriptionId
     * @throws NamingException
     */
    private void cancelBraceletetIfExists(Long aCanseledPrescriptionId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        service.executeUpdateNativeSql("update coloridentitypatient set finishdate=current_date, finishtime=current_time,editusername='"
                + username + "' where entityname='prescription' and entityid=" + aCanseledPrescriptionId);
    }

    public String cancelPrescription(Long aPresctiptionId, String aReason, HttpServletRequest aRequest) throws NamingException {
        return annulEmptyPrescription(null, null, aPresctiptionId, aReason, aRequest);
    }

    public String createVisitByPrescription(Long aPrescriptListId, Long aWorkFunctionPlanId,
                                            Long aDatePlanId, Long aTimePlanId, Long aMedServiceId, Long aCountDays, Long aGuaranteeId, HttpServletRequest aRequest) throws NamingException {
        if (aTimePlanId == null || aTimePlanId.equals(0L)) {
            return "";
        }
        IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class);
        IWebQueryService wqs = Injection.find(aRequest).getService(IWebQueryService.class);

        Long patientId = getPatientIdByPrescriptionList(aPrescriptListId, aRequest);
        //Если время занято - не записываем на него
        if (wqs.executeNativeSql("select wct.id from workcalendartime wct left join medcase mc on mc.id =wct.medcase_id " +
                " where wct.id = " + aTimePlanId + " and (wct.medcase_id is null or mc.patient_id =" + patientId + " ) ").isEmpty()) {
            return null;
        }
        Long wf;
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        try {
            wf = Long.valueOf(wqs.executeNativeSql("select wf.id from workfunction wf left join secuser su on su.id=wf.secuser_id where su.login = '" + username + "'").iterator().next().get1().toString());
        } catch (Exception e) {
            LOG.error("Ошибка нахождения раб. фунции = ", e);
            throw new IllegalDataException(e.toString());
        }
        String visit = null;
        if (aCountDays != null && aCountDays > 0) {
            Collection<WebQueryResult> l = wqs.executeNativeSql("select wct.id as f1,wcd.id as f2 from workcalendartime wct1"
                    + " left join workcalendarday wcd1 on wct1.workcalendarday_id = wcd1.id"
                    + " left join workcalendarday wcd on wcd.workcalendar_id = wcd1.workcalendar_id"
                    + " left join workcalendartime wct on wct.workcalendarday_id=wcd.id  and wct.timefrom=wct1.timefrom"
                    + " where wct1.id='" + aTimePlanId + "' "
                    + " and wcd.calendardate BETWEEN  wcd1.calendardate and  wcd1.calendardate+" + aCountDays
                    + " and wct.medcase_id is null"
                    + " and wct.prescription is null"
                    + " and wct.prepatient_id is null"
                    + " and wct.prepatientinfo is null");


            for (WebQueryResult r : l) {
                aTimePlanId = Long.parseLong(r.get1().toString());
                aDatePlanId = Long.parseLong(r.get2().toString());
                visit = service.createNewDirectionFromPrescription(aPrescriptListId, aWorkFunctionPlanId
                        , aDatePlanId, aTimePlanId, aMedServiceId, username, wf, aGuaranteeId);
            }
        } else {
            visit = service.createNewDirectionFromPrescription(aPrescriptListId, aWorkFunctionPlanId
                    , aDatePlanId, aTimePlanId, aMedServiceId, username, wf, aGuaranteeId);
        }
        return visit;
    }

    /**
     * Получение списка предварительных записей на услуги по пациенту
     */
    public String getDirections(Long aId, String aIdType, String aDateFrom, String aServiceType, HttpServletRequest aRequest) throws NamingException {
        //medserviceId:mcName:cabinetID:cabinetName:wctID, WCDName
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String fromSql, leftJoinSql, whereSql;
        StringBuilder ret = new StringBuilder();
        if ("PrescriptionList".equals(aIdType)) {
            fromSql = "prescriptionlist pl ";
            leftJoinSql = " left join medcase mc on mc.id=pl.medcase_id left join patient p on p.id=mc.patient_id";
            whereSql = "pl.id=" + aId;
        } else if ("Medcase".equals(aIdType)) {
            fromSql = "medcase mc ";
            leftJoinSql = " left join patient p on p.id=mc.patient_id";
            whereSql = "mc.id=" + aId;
        } else {
            return "Плохой ID тип = " + aIdType;
        }
        String sql = "select p.id from " + fromSql + " " + leftJoinSql + " where " + whereSql;

        try {
            String patientId = service.executeNativeSql(sql).iterator().next().get1().toString();
            StringBuilder pz = new StringBuilder().append("select ms.id as msId, ms.code||' ' ||ms.name as msName" +
                    ", to_char(wcd.calendardate, 'dd.MM.yyyy') as calDateName, wf.id as wfId , wf.groupname as wfGroupName, wct.id as wctId " +
                    " ,cast (wct.timefrom as varchar(5)) as timeName");
            pz.append(" from patient pat ")
                    .append(" left join workcalendartime wct on wct.prepatient_id=pat.id")
                    .append(" left join workcalendarday wcd on wcd.id=wct.workcalendarday_id")
                    .append(" left join prescription p on p.id=wct.prescription")
                    .append(" left join medservice ms on ms.id=coalesce(wct.service, p.medservice_id)")
                    .append(" left join vocservicetype vst on vst.id=ms.servicetype_id")
                    .append(" left join workcalendar wc on wc.id=wcd.workcalendar_id")
                    .append(" left join workfunction wf on wf.id=wc.workfunction_id")
                    .append(" where pat.id=").append(patientId).append(" and vst.code='").append(aServiceType).append("'");
            if (aDateFrom != null && !aDateFrom.equals("")) {
                pz.append(" and wcd.calendardate>=to_date('").append(aDateFrom).append("','dd.MM.yyyy')");
            }
            Collection<WebQueryResult> res = service.executeNativeSql(pz.toString());
            if ("OPERATION".equals(aServiceType)) {
                aServiceType = "surg";
            }
            if (!res.isEmpty()) {
                boolean isFirst = true;
                for (WebQueryResult r : res) {
                    String msID = r.get1().toString();
                    String msCode = r.get2().toString();
                    String wfID = r.get4().toString();
                    String wfName = r.get5().toString();
                    String timeId = r.get6().toString();
                    String dateName = r.get3().toString();
                    String timeName = r.get7().toString();
                    if (!isFirst) ret.append("#");
                    isFirst = false;
                    ret.append(aServiceType).append(":").append(msID).append(":").append(msCode)
                            .append(":").append(dateName)
                            .append(":").append(wfID).append(":").append(wfName).append(":::").append(timeId).append(":").append(timeName.replace(":", "-"));
                }
            }
        } catch (Exception e) {
            LOG.error("Ошибка нахождения пациента", e);
        }
        return ret.toString();
    }

    private boolean isSLSClosed(Long aMedCaseId, HttpServletRequest aRequest) throws NamingException {
        String sql = "select case when mc.dtype='HospitalMedCase' then" +
                "   case when mc.datefinish is not null and mc.dischargetime is not null then 1 else 0 end" +
                " when mc.dtype='DepartmentMedCase' then" +
                "   case when mcP.datefinish is not null and mcP.dischargetime is not null then 1 else 0 end " +
                " when mc.dtype='PolyclinicMedCase' then " +
                "   case when mc.datefinish is not null then 1 else 0 end " +
                " when mc.dtype='Visit' then " +
                "   case when mcP.datefinish is not null then 1 else 0 end " +
                "end" +
                " from medcase mc" +
                " left join medcase mcP on mcP.id=mc.parent_id" +
                " where mc.id=" + aMedCaseId;
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String res = service.executeNativeSql(sql, 1).iterator().next().get1().toString();
        return "1".equals(res);
    }

    /**
     * Получить, является ли визитом #112.
     *
     * @param aMedCaseId MedCase.id
     * @return Boolean true если визит
     */
    private boolean isMedcaseIsVisit(Long aMedCaseId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return !service.executeNativeSql("select id from medcase where id=" + aMedCaseId + " and dtype='Visit'").isEmpty();
    }

    /**
     * Является ли СМО создания назначения - случаем лечения в отделении
     *
     * @param aMedcaseId ИД случая мед обслуживания
     * @param aRequest   -
     * @return
     * @throws NamingException
     */
    public boolean isMedcaseIsDepartment(Long aMedcaseId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return !service.executeNativeSql("select id from medcase where id=" + aMedcaseId + " and dtype='DepartmentMedCase'").isEmpty();
    }

    public String listProtocolsByUsername(String aFunctionTemp, HttpServletRequest aRequest) throws NamingException {
        StringBuilder sql = new StringBuilder();
        String login = LoginInfo.find(aRequest.getSession(true)).getUsername();
        sql.append("select pl.id as tid,case when su.login!='").append(login).append("' then '(общ) ' else '' end || pl.name as ttile");
        sql.append(" from PrescriptionList pl");
        sql.append(" left join SecUser su on pl.createusername=su.login");
        sql.append(" left join PrescriptionList_secgroup tg on pl.id=tg.prescriptionList_id");
        sql.append(" left join SecGroup_secUser gu on gu.secgroup_id=tg.secgroups_id");
        sql.append(" left join SecUser gsu on gsu.id=gu.secUsers_id");
        sql.append(" where pl.dtype='PrescriptListTemplate' and su.login='").append(login).append("' or gsu.login='").append(login).append("'");
        sql.append(" group by pl.id,pl.name,su.login");
        sql.append(" order by pl.name");


        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder res = new StringBuilder();
        Collection<WebQueryResult> list = service.executeNativeSql(sql.toString());
        res.append("<table>");
        res.append("<tr><td colspan='1'><h2>Выбор осуществляется двойным нажатием мыши</h2></td></tr>");
        res.append("<tr><td colspan='1' valign='top'>");
        res.append("<h2>Список своих шаблонов</h2>");
        res.append("</td></tr><tr><td valign='top'>");

        res.append("<ul>");
        for (WebQueryResult wqr : list) {
            res.append("<li class='liTemp' onclick=\"").append(aFunctionTemp).append("('")
                    .append(wqr.get1()).append("',0)\" ondblclick=\"").append(aFunctionTemp).append("('")
                    .append(wqr.get1()).append("',1)\">");
            res.append(wqr.get2());
            res.append("</li>");
        }
        res.append("</ul></td>");
        res.append("</tr></table>");
        return res.toString();
    }

    /*удаляем назначение сразу же при создании, удаление из шаблона листа назначений*/
    public String removePrescriptionFromList(Long aPrescriptList, Long aMedService, Long aWorkCalendarTime, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        if (aWorkCalendarTime != null && aWorkCalendarTime > 0L) { //удаляем при назначении, не знаем ИД назначения
            return annulEmptyPrescription(aMedService, aWorkCalendarTime, null, "Аннулирован при назначении", aRequest);
        } else { //удаляем из шаблона
            service.executeUpdateNativeSql("delete from prescription where prescriptionlist_id=" + aPrescriptList + " and medservice_id=" + aMedService);
            return "Удалено";
        }
    }

    @Deprecated //см ниже
    public String addPrescriptionToList(Long aPrescriptList, Long aMedService, Long aDepartment, Long aCabinet, String dType, HttpServletRequest aRequest) throws NamingException {
        return addPrescriptionToListWCT(aPrescriptList, aMedService, aDepartment, aCabinet, dType, null, null, "", aRequest);
    }

    /**
     * Создаем назначение. Если на это время есть предварительное направление, то заменяем его на назначение
     *
     * @param aPrescriptList - лист назначений
     * @param aMedService    - мед. услуга
     * @param aDepartment    - Отдел для забора биоматериала
     * @param aCabinet       - рабочая функция для направления
     */
    @Deprecated //Переделано на создание объектом стандартным способом
    public String addPrescriptionToListWCT(Long aPrescriptList, Long aMedService, Long aDepartment, Long aCabinet, String dType, String aDateStart, String aWorkCalendarTime
            , String aComments, HttpServletRequest aRequest) throws NamingException {
        Date date = new Date();
		/*Prescription prescription ; //TODO Переделать на создание объектом для корректного удаления
		if (dType.equals("ServicePrescription")) {
			prescription = new ServicePrescription();
		} else {
			log.error("Ошибка создания назначения: Неизвестный тип назначения:"+dType);
		}*/
        SimpleDateFormat formatD = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat formatT = new SimpleDateFormat("HH:mm");
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        if (aWorkCalendarTime != null && !aWorkCalendarTime.equals("") &&
                service.executeNativeSql("select wct.id from workcalendartime wct " +
                        " left join medcase vis on vis.id=wct.medcase_id" +
                        " where wct.id = " + aWorkCalendarTime + " and (medcase_id is null or vis.patient_id=" +
                        "(select dep.patient_id from prescriptionlist pl left join medcase dep on dep.id=pl.medcase_id where pl.id=" + aPrescriptList + "))" +
                        " and (wct.isDeleted is null or wct.isDeleted='0')").isEmpty()) {
            return null; //Не создаем направления, если время уже занято.
        }
        String login = LoginInfo.find(aRequest.getSession(true)).getUsername();
        String wf;
        try {
            wf = service.executeNativeSql("select wf.id from workfunction wf left join secuser su on su.id=wf.secuser_id where su.login = '" + login + "'").iterator().next().get1().toString();
        } catch (Exception e) {
            LOG.error("Ошибка нахождения раб. функции", e);
            throw new IllegalDataException(e.toString());
        }
        StringBuilder str = new StringBuilder();
        StringBuilder values = new StringBuilder();
        StringBuilder valuesData = new StringBuilder();
        values.append("prescriptionlist_id");
        valuesData.append("'").append(aPrescriptList).append("'");
        values.append(",dtype");
        valuesData.append(",'").append(dType).append("'");
        values.append(",medservice_id");
        valuesData.append(",'").append(aMedService).append("'");
        if (aDepartment != null && !aDepartment.equals(0L)) {
            values.append(",department_id");
            valuesData.append(",'").append(aDepartment).append("'");
        }
        if (aCabinet != null && !aCabinet.equals(0L)) {
            values.append(",prescriptcabinet_id");
            valuesData.append(",'").append(aCabinet).append("'");
        }
        if (aWorkCalendarTime != null && !aWorkCalendarTime.equals("")) {
            values.append(",calendartime_id");
            valuesData.append(",'").append(aWorkCalendarTime).append("'");
        }
        if (aDateStart != null && !aDateStart.equals("")) {
            values.append(",planstartdate");
            valuesData.append(" ,to_date('").append(aDateStart).append("','dd.MM.yyyy')");
        }
        values.append(",comments");
        valuesData.append(" ,'").append(aComments).append("'");
        values.append(",createusername");
        valuesData.append(" ,'").append(login).append("'");
        values.append(",createdate");
        valuesData.append(" ,to_date('").append(formatD.format(date)).append("','dd.MM.yyyy')");
        values.append(",createtime");
        valuesData.append(" ,cast ('").append(formatT.format(date)).append("' as time)");
        values.append(",prescriptspecial_id");
        valuesData.append(" ,").append(wf);
        str.append("insert into prescription (").append(values).append(") values (").append(valuesData).append(")");
        service.executeUpdateNativeSql(str.toString());
        if (aWorkCalendarTime != null && !aWorkCalendarTime.equals("")) {
            String presId = service.executeNativeSql("select id from prescription where calendartime_id=" + aWorkCalendarTime).iterator().next().get1().toString();
            service.executeUpdateNativeSql("update workcalendartime set prescription=" + presId + ", prepatient_id=null where id = " + aWorkCalendarTime);
        }
        return "Выполнено: ";
    }

    /**
     * Брак назначений. Заполняет поля кто и когда аннулировал лаб. назначения
     * upd. 11.05.2018 При отмене назначения в лаборатории без приема, отмечаем что прием был. Чтобы лаборатория видела это назначение
     *
     * @param aPrescripts Строка с ИД назначений (prescription) разделенная запятами
     * @param aReasonId   ИД причины отмены
     * @param aReason     Текст причины отмены
     * @param aRequest
     * @return
     * @throws NamingException
     */
    public void cancelService(String aPrescripts, Long aReasonId, String aReason, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        Date date = new Date();
        SimpleDateFormat formatD = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat formatT = new SimpleDateFormat("HH:mm");
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        String sqlAnnul = "select id from workfunction where dtype='GroupWorkFunction' and code='LAB_BRAK'";
        Collection<WebQueryResult> defaultAnnulCabinets = service.executeNativeSql(sqlAnnul);
        if (!defaultAnnulCabinets.isEmpty()) {
            sqlAnnul = " ,prescriptcabinet_id=" + defaultAnnulCabinets.iterator().next().get1();
        } else {
            sqlAnnul = "";
        }
        sql.append("update Prescription set cancelReason_id='").append(aReasonId).append("', cancelReasonText='").append(aReason).append("'")
                .append(", cancelDate=to_date('").append(formatD.format(date)).append("','dd.mm.yyyy'),cancelTime=cast('").append(formatT.format(date)).append("' as time)")
                .append(", transferDate=coalesce(transferDate,to_date('").append(formatD.format(date)).append("','dd.mm.yyyy')),transferTime=coalesce(transferTime,cast('").append(formatT.format(date)).append("' as time))")
                .append(",transferUsername=coalesce(transferUsername,'").append(username).append("')")
                .append(",cancelUsername='").append(username).append("'").append(sqlAnnul).append(" where id in (").append(aPrescripts).append(")");
        service.executeUpdateNativeSql(sql.toString());

        sql = new StringBuilder();
        sql.append("update medcase set datestart = current_date, noactuality='1' where id in (select medcase_id from prescription where id in (").append(aPrescripts).append("))");
        service.executeUpdateNativeSql(sql.toString());
        String reasonText = "", reasonCode = "";
        if (aReasonId != null && aReasonId > 0) {
            Collection<WebQueryResult> list = service.executeNativeSql("select name,code from VocPrescriptCancelReason where id=" + aReasonId);
            WebQueryResult obj = list.iterator().next();
            reasonText = list.isEmpty() ? "" : obj.get1().toString();
            reasonCode = list.isEmpty() ? "" : obj.get2().toString();
        }
        IPrescriptionService bean = Injection.find(aRequest).getService(IPrescriptionService.class);
        if (!reasonCode.equals("another_lab")) {  //сообщения формируются только в случае именно брака, а не отправки в другую лаб.
            reasonText += StringUtil.isNullOrEmpty(aReason) ? "" : " " + aReason;
            JSONObject res = getPrescriptionInfo(aPrescripts, service);
            String lpuString = getLpuForDefectMessage(aPrescripts, aRequest);
            if (!lpuString.equals("0")) {
                ArrayList<String> usersToSend = getAllUsersToSendMessageCancel(lpuString, aPrescripts, aRequest);
                String msgTitle = res.getString("date") + " пациент " + res.getString("patFio") + " услуга " + res.getString("medService");
                String messageText = "Брак биоматериала: " + reasonText;
                String sender = res.getString("usernameO"); //спорно, ну да пофиг
                for (String user : usersToSend) {
                    for (int i = 0; i < 2; i++) {
                        LOG.info("dbg. send brak " + i + ", user=" + user + ", sender=" + sender); //Сообщение уходят по 4 раза вместо двух
                        bean.sendMessageCurrentDate(messageText, msgTitle, user, sender
                                , "entityView-pres_prescriptList.do?id=" + res.get("plId"), i < 1);
                    }
                }
            }
        }
        //Обновление текста дневника в случае отметки о браке после подтверждения врачом КДЛ
        String wfCnsl = bean.getRealLabTechUsername(Long.valueOf(aPrescripts.split(",")[0]), "");
        updateDiaryWhileCancelPrescription(null, aPrescripts, "Брак биоматериала: " + reasonText, wfCnsl, service);
        //Обновление браслета в случае брака лаборатории
        updateBraceleteIfExists(aPrescripts, aRequest);
    }

    /**
     * Обновление браслета в случае брака лаборатории
     *
     * @param aPrescripts Строка с ИД назначений (prescription) разделенная запятами
     * @param aRequest
     * @return
     * @throws NamingException
     */
    private void updateBraceleteIfExists(String aPrescripts, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        //если брак до ввода результата и браслет связан с назначением
        service.executeUpdateNativeSql("update coloridentitypatient set voccoloridentity_id=(select id from VocColorIdentityPatient where code='Pres_COVID_cans') " +
               " where entityname='prescription' and entityid in (" + aPrescripts + ")");
        //пбрак после введения результата (тогда будет Protocol, а не Prescription)
        service.executeUpdateNativeSql("update coloridentitypatient set voccoloridentity_id=(select id from VocColorIdentityPatient where code='Pres_COVID_cans') " +
                " where entityname='Protocol' and entityid in (select id from diary where medcase_id=ANY(select medcase_id from prescription  where id in (" + aPrescripts + ")))");
    }

    /* Получить всех пользователей для отправки сообщения о браке
     * @param lpu Список отделений через запятую для запроса (выборка только с ролью Специалист стационара - врачи)
     * @param aPrescripts Список назначений через запятую (выборка только с той же должностью, что и назначивший исследования)
     * @param aRequest
     * @return ArrayList Список пользователей
     * @throws NamingException
     * */
    private ArrayList<String> getAllUsersToSendMessageCancel(String lpu, String aPrescripts, HttpServletRequest aRequest) throws NamingException {
        ArrayList<String> resListUsers = new ArrayList<>();
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        sql.append(" select distinct su.login")
                .append(" from workfunction wf1")
                .append(" left join worker w1 on w1.id=wf1.worker_id")
                .append(" left join worker w on w.lpu_id=w1.lpu_id")
                .append(" left join workfunction wfDep on wfDep.worker_id = w.id and wfDep.workfunction_id  = wf1.workfunction_id and (wfDep.archival is null or wfDep.archival ='0')")
                .append(" left join worker wall on wall.id  = wfDep.worker_id")
                .append(" left join worker wall2 on wall2.person_id = wall.person_id")
                .append(" left join workfunction  wfall2 on wfall2.worker_id  = wall2.id")
                .append(" left join secuser su on su.id=wfall2.secuser_id");
        if (lpu.split(",").length > 1) { //если был перевод (СЛО1-СЛО2)
            sql.append(" left join secuser_secrole sur on sur.secuser_id=su.id")
                    .append(" left join secrole sr on sr.id=sur.roles_id")
                    .append(" where w.lpu_id in (").append(lpu).append(")")
                    .append(" and su.id is not null")
                    .append(" group by su.login,sr.id having (sr.id=1)");
        } else { //если перевода не было и всё в одном отделении (приёмник-СЛО или просто в СЛО)
            sql.append(" where wf1.id=ANY(select prescriptspecial_id from prescription where id in (").append(aPrescripts)
                    .append(")) and su.id is not null");
        }
        Collection<WebQueryResult> listUsers = service.executeNativeSql(sql.toString());
        for (WebQueryResult wqr : listUsers)
            resListUsers.add(wqr.get1().toString());
        return resListUsers;
    }

    /* Получить отделения, пользователям которых надо отправить сообщение о браке назначения
     * @param aPrescripts Назначения
     * @param aRequest
     * @return String Отделения через запятую
     * @throws NamingException
     * */
    private String getLpuForDefectMessage(String aPrescripts, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        /*
        Если ЛН сделан в приёмнике, то, если создан СЛО, вернуть ЛПУ последнего СЛО
        Если нет СЛО (это мб в случае отказа от госпитализации или ещё не создали)
        то никому - вернуть 0.
        Если ЛН сделан в СЛО, то, если нет перевода, вернуть ЛПУ этого СЛО
        Если есть перевод, вернуть через запятую ЛПУ изначального СЛО и перевода.
        * */
        sql.append("select case when mc.dtype='HospitalMedCase' then")
                .append(" case when cast((select max(id) from medcase")
                .append(" where dtype='DepartmentMedCase' and parent_id=mc.id) as varchar) is null then '0'")
                .append(" else cast((select department_id from medcase where dtype='DepartmentMedCase' and parent_id=mc.id and id=(select max(id) from medcase")
                .append(" where dtype='DepartmentMedCase' and parent_id=mc.id)) as varchar) end")
                .append(" else case when mc.dtype='DepartmentMedCase' then")
                .append(" case when mc.transferdepartment_id is null then cast (mc.department_id as varchar)")
                .append(" else cast(mc.department_id as varchar)||','||cast(mc.transferdepartment_id as varchar) end end end")
                .append(" from prescription p")
                .append(" left join prescriptionlist pl on pl.id=p.prescriptionlist_id")
                .append(" left join medcase mc on mc.id=pl.medcase_id")
                .append(" where p.id in (").append(aPrescripts).append(")");
        Collection<WebQueryResult> list = service.executeNativeSql(sql.toString());
        return list.isEmpty() ? "0" : list.iterator().next().get1().toString();
    }

    /* Отметка патологии назначений. Заполняет поля о том, кто и когда поставил патологию
     * @param aPrescriptId ИД назначения
     * @param aRequest
     * @return String Сообщение
     * @throws NamingException
     * */
    public String patologyService(Long aPrescriptId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        Date date = new Date();
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();

        Long wfId = getWorkFunction(username, service);
        if (wfId != null) {
            sql.append("update Prescription set setPatologyDate=to_date('").append(new SimpleDateFormat("dd.MM.yyyy").format(date)).append("','dd.mm.yyyy'),setPatologyTime=cast('")
                    .append(new SimpleDateFormat("HH:mm").format(date)).append("' as time)")
                    .append(",setPatologyUsername='").append(username).append("', setPatologySpecial_id='").append(wfId).append("' where id=").append(aPrescriptId);
            service.executeUpdateNativeSql(sql.toString());
            noteUsersAboutPatology(aPrescriptId, aRequest);
            return "Отметка о патологии назначения проставлена.";
        } else return "У пользователя " + username + " нет соответствия с рабочей функцией";


    }

    /* Патология назначений. Отправляет сообщения: экстренное и обычное
     * @param aPrescriptId ИД назначения
     * @param aRequest
     * @return String Сообщение
     * @throws NamingException
     * */
    private void noteUsersAboutPatology(Long aPrescriptId, HttpServletRequest aRequest) throws NamingException {
        JSONObject res = getPrescriptionInfo(String.valueOf(aPrescriptId), Injection.find(aRequest).getService(IWebQueryService.class));
        StringBuilder msgTitle = new StringBuilder();
        msgTitle.append(res.getString("date")).append(" пациент ").append(res.getString("patFio")).append(" услуга ").append(res.getString("medService"));
        IPrescriptionService bean = Injection.find(aRequest).getService(IPrescriptionService.class);
        for (int i = 0; i < 2; i++)
            bean.sendMessageCurrentDate("Критическая патология биоматериала", msgTitle.toString(), res.getString("usernameO")
                    , LoginInfo.find(aRequest.getSession(true)).getUsername()
                    , "entityView-pres_servicePrescription.do?id=" + aPrescriptId, i < 1);
        bean.setPatientIdentityBracelet(res.toString());
    }


    /* Получить информацию о пациенте, услуге и т.п. по назначению
     * @param aPrescripts ИД назначения (или назначения через запятую)
     * @param service
     * @return JSONObject Информация в json
     * @throws NamingException
     * */
    private JSONObject getPrescriptionInfo(String aPrescripts, IWebQueryService service) throws NamingException {
        JSONObject res = new JSONObject(); //TODO возвращает информация только по первому анализу.
        List<Object[]> list = service.executeNativeSqlGetObj("select pat.id as patId,p.createusername,to_char(p.planstartdate,'dd.mm.yyyy')  as dt " +
                " ,pat.lastname||' '||pat.firstname||' '||pat.middlename as fio,ms.code||' '||ms.name,pl.id as plId, hmc.id as mcId " +
                " from prescription p " +
                " left join medservice ms on ms.id=p.medservice_id " +
                " left join prescriptionlist pl on pl.id=p.prescriptionlist_id " +
                " left join medcase mc on mc.id=pl.medcase_id " +
                " left join patient pat on pat.id=mc.patient_id " +
                " left join medcase hmc on hmc.id=case when mc.dtype='DepartmentMedCase' then mc.parent_id else mc.id end" +
                " where p.id in(" + aPrescripts + ")");
        for (Object[] obj : list) {
            res.put("usernameO", "" + obj[1])
                    .put("patId", obj[0])
                    .put("date", obj[2])
                    .put("patFio", obj[3])
                    .put("medService", obj[4])
                    .put("plId", obj[5])
                    .put("mcId", obj[6]);
        }
        return res;
    }

    /* Получить рабочую функцию пользователя
     * @param username String
     * @param service
     * @return Long WorkFunction.id
     * */
    private Long getWorkFunction(String username, IWebQueryService service) {
        Collection<WebQueryResult> specP = service.executeNativeSql("select wf.id from secuser su left join workFunction wf on wf.secuser_id=su.id where su.login='" + username + "'", 1);
        return specP.isEmpty() ? null : ConvertSql.parseLong(specP.iterator().next().get1());
    }

    public void uncancelService(String aPrescripts, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("update Prescription set cancelReason_id=null, cancelReasonText=null, cancelDate=null, "
                + "cancelTime=null, cancelUsername=null where id in (" + aPrescripts + ")");
    }

    /**
     * Возвращает ID листа назначений, если он существует в заданном Medcase
     * <p>
     * UPD 11.2015 - Если ЛН не существует, то создает его
     *
     * @param aMedcase - ИД СЛО
     * @param aRequest -
     * @return - ИД листа назначений
     * @throws NamingException
     */

    public String isPrescriptListExists(Long aMedcase, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String req = "Select pl.id from prescriptionlist pl where pl.medcase_id='" + aMedcase + "' order by id ";
        boolean isMedcaseClosed = isSLSClosed(aMedcase, aRequest);
        Collection<WebQueryResult> wrt = service.executeNativeSql(req, 1);
        if (!wrt.isEmpty()) {
            String plId;
            WebQueryResult obj = wrt.iterator().next();
            if (isMedcaseIsVisit(aMedcase, aRequest) && isMedcaseClosed) {
                plId = "2" + obj.get1().toString();  //если закрыт визит
            } else {
                plId = (isMedcaseClosed ? "0" : "1") + obj.get1().toString();
            }
            return plId;
        } else {
            if (isMedcaseClosed && !isMedcaseIsVisit(aMedcase, aRequest)) {
                return null;
            }
            Date date = new Date();
            SimpleDateFormat formatD = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat formatT = new SimpleDateFormat("HH:mm");
            String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
            try {
                String wf = service.executeNativeSql("select wf.id from workfunction wf left join secuser su on su.id=wf.secuser_id where su.login = '" + username + "'").iterator().next().get1().toString();
                String sqlCreate = "insert into prescriptionlist (dtype,medcase_id, createusername, createdate, createtime, workfunction_id) values ('PrescriptList',"
                        + aMedcase + ", '" + username + "',to_date('" + formatD.format(date) + "','dd.MM.yyyy')"
                        + ", cast('" + formatT.format(date) + "' as time), " + wf + ")";
                service.executeUpdateNativeSql(sqlCreate);
                return isPrescriptListExists(aMedcase, aRequest);
            } catch (Exception e) {
                LOG.error("Не найдена рабочая функция", e);
                throw new IllegalDataException(e.toString());
            }
        }
    }

    /**
     * Получаем ИД пациента по ИД листа назначений
     *
     * @param aPrescriptionListId ИД листа назначений
     * @param aRequest            -
     * @return
     * @throws NamingException
     */
    private Long getPatientIdByPrescriptionList(Long aPrescriptionListId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String req = "select mc.patient_id from prescriptionList pl " +
                " left join medcase mc on mc.id = pl.medcase_id " +
                " where pl.id = " + aPrescriptionListId + " ";
        Collection<WebQueryResult> list = service.executeNativeSql(req, 1);
        return list.isEmpty() ? 0L : ConvertSql.parseLong(list.iterator().next().get1());
    }

    /**
     * Проверка назначений на наличие дублей (имеются назначения на такие же
     * исследования в том же СЛО, в котором создается назначение)
     * **** upd 12.12.2014 - Поиск осуществляется во всех СЛО, которые относятся к тому же СЛС, что и указаное СЛО.
     */
    public String getDuplicatePrescriptions(String aMedCase, String aData, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder req = new StringBuilder();
        req.append("select distinct p.medservice_id ");
        req.append(",ms.code ||' ' || ms.name as labName ");
        req.append("from medcase mc ");
        req.append("left join medcase mc2 on mc2.parent_id = mc.parent_id ");
        req.append("left join prescriptionList pl on pl.medcase_id = mc2.id ");
        req.append("left join prescription p on p.prescriptionList_id = pl.id ");
        req.append("left join medservice ms on ms.id = p.medservice_id ");
        req.append("where mc.id ='").append(aMedCase).append("' ");
        req.append("and p.dtype='ServicePrescription' ");
        req.append("and p.medservice_id is not null ");
        req.append("and p.fulfilmentstate_id is null ");
        req.append("and p.canceldate is null ");
        Collection<WebQueryResult> list = service.executeNativeSql(req.toString());
        StringBuilder res = new StringBuilder();
        if (!list.isEmpty()) {
            String[] aDataArr = aData.split(":");
            for (WebQueryResult obj : list) {
                for (String s : aDataArr) {
                    if (obj.get1().toString().equals(s)) {
                        res.append(obj.get1().toString()).append(":").append(obj.get2().toString()).append("#");
                    }
                }
            }
        }
        return res.length() > 0 ? res.substring(0, res.length() - 1) : "";
    }

    /*
     * Проверяем полученные исследования на соответствие типу листа назначения
     * и возвращаем те, которые соответствуют типу ЛН.
     */
    public String getPresLabTypes(String aPresIDs, Long aPrescriptListType, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        aPresIDs = aPresIDs.substring(0, aPresIDs.length() - 1); // Обрезаем #
        StringBuilder sqlMS = new StringBuilder();
        StringBuilder sqlCab;
        StringBuilder res = new StringBuilder();
        String[] labListArr = aPresIDs.split("#");
        if (labListArr.length > 0) {
            for (String s : labListArr) {
                String[] param = s.split(":");
                String msID = param.length > 0 ? param[0] : null;
                String date = param.length > 1 && param[1] != null ? param[1] : "";
                String cabID = param.length > 2 ? param[2] : null;
                String departmentID = param.length > 3 ? param[3] : null;
                if (msID != null && !msID.equals("")) {
                    sqlMS.setLength(0);
                    sqlMS.append("select vst.code, ms.id, ms.code ||' ' ||ms.name from medservice ms ")
                            .append("left join vocservicetype vst on vst.id=ms.servicetype_id ")
                            .append("where ms.id='").append(msID).append("' ");
                    if (aPrescriptListType > 0) {
                        sqlMS.append("and ms.id not in ");
                        sqlMS.append("(select mss.id from medservice mss");
                        sqlMS.append(" left join workfunctionservice wfss on wfss.medservice_id=mss.id");
                        sqlMS.append(" left join vocprescripttype vpts on vpts.id=wfss.prescripttype_id");
                        sqlMS.append(" left join vocservicetype vsts on vsts.id=mss.servicetype_id where vpts.id='");
                        sqlMS.append(aPrescriptListType).append("' ");
                        sqlMS.append(" and mss.id=ms.id and mss.dtype='MedService' and vsts.code='LABSURVEY')");

                    }

                    Collection<WebQueryResult> listMS = service.executeNativeSql(sqlMS.toString());
                    for (WebQueryResult wqr : listMS) {
                        res.append(wqr.get1()).append(":")
                                .append(wqr.get2()).append(":")
                                .append(wqr.get3()).append(":")
                                .append(date).append(":");

                        if (cabID != null && !cabID.equals("")) {
                            sqlCab = new StringBuilder();
                            sqlCab.append("Select wf.id,wf.groupname from workfunction wf where wf.id='").append(cabID).append("' ");
                            Collection<WebQueryResult> listCab = service.executeNativeSql(sqlCab.toString(), 1);
                            for (WebQueryResult wqr2 : listCab) {
                                res.append(wqr2.get1()).append(":");
                                res.append(wqr2.get2()).append(":");
                            }
                        } else res.append("::");
                        if (departmentID != null && !departmentID.equals("")) {
                            sqlCab = new StringBuilder();
                            sqlCab.append("Select ml.id,ml.name from mislpu ml where ml.id='").append(departmentID).append("' ");
                            Collection<WebQueryResult> listCab = service.executeNativeSql(sqlCab.toString(), 1);
                            for (WebQueryResult wqr2 : listCab) {
                                res.append(wqr2.get1()).append(":");
                                res.append(wqr2.get2()).append(":");
                            }
                        } else res.append("::");
                        res.append("::");
                        res.append("#");
                    }
                }
            }
        }
        return res.length() > 0 ? res.substring(0, res.length() - 1) : "";
    }

    /**
     * Получаем описание шаблона листа назначения
     *
     * @param aIdTemplateList
     * @param aRequest
     * @return
     * @throws NamingException
     */
    public String getDescription(Long aIdTemplateList, HttpServletRequest aRequest) throws NamingException {
        IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class);
        return aIdTemplateList != null && !aIdTemplateList.equals(0L) ? service.getDescription(aIdTemplateList) : "";
    }

    public String getDefectByBiomaterial(String aPrescript, String aBiomaterialType, String aPrefixMethod, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        sql.append("select id,name,case when additionData='1' then additionData else null end as addData from VocPrescriptCancelReason where serviceType='LABSURVEY' and biomaterial='").append(aBiomaterialType).append("'");
        Collection<WebQueryResult> l = service.executeNativeSql(sql.toString());
        StringBuilder ret = new StringBuilder();
        ret.append("<table>");
        for (WebQueryResult wqr : l) {
            ret.append("<tr>");
            ret.append("<td onclick=\"cancel").append(aPrefixMethod).append("InLab('").append(aPrescript).append("','").append(wqr.get1()).append("','").append(wqr.get3() != null ? "1" : "0").append("');\" colspan=\"4\">");
            ret.append("	<input name=\"typeDefect\" value=\"5\" type=\"radio\">").append(wqr.get2());
            ret.append("</td>");
            ret.append("</tr>");
        }
        ret.append("</table>");
        return ret.toString();
    }

    public String getLabCabinetByPres(String aListPrescript, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        sql.append("select pms.id as p1msid,pms.code||' '||pms.name as p2msname,wf.id as w3fid,wf.groupname as w4fgroup,replace(list(''||ms.id),' ','') as m5slist");
        sql.append(" from WorkFunction wf");
        sql.append(" left join WorkFunctionService wfs on wfs.workfunction_id=wf.id");
        sql.append(" left join MedService pms on pms.id=wfs.medservice_id");
        sql.append(" left join MedService ms on ms.parent_id=pms.id");
        sql.append(" left join Prescription p on ms.id=p.medService_id");
        sql.append(" where p.id in (").append(aListPrescript).append(") ");
        sql.append(" group by pms.id,pms.code,pms.name,wf.id,wf.groupname");
        sql.append(" order by pms.name,wf.groupname");
        StringBuilder ret = new StringBuilder();
        Collection<WebQueryResult> lwqr = service.executeNativeSql(sql.toString());
        String oldi = "";
        StringBuilder listCab = new StringBuilder();
        ret.append("<form name='frmCabinet' id='frmCabinet'><table>");
        for (WebQueryResult wqr : lwqr) {
            String newi = String.valueOf(wqr.get1());
            if (!newi.equals(oldi)) {
                oldi = newi;
                if (listCab.length() > 0) listCab.append(",");
                listCab.append(newi);
                ret.append("<tr>");
                ret.append("<th colspan='2'>").append(wqr.get2()).append("</th>");
                ret.append("</tr>");
            }
            ret.append("<tr>");
            ret.append("<td onclick=\"this.childNodes[1].checked=\'checked\';\" colspan=\"4\">");
            ret.append("	<input name=\"typeCabinet").append(wqr.get1()).append("\" id=\"typeCabinet").append(wqr.get1()).append("\" value=\"").append(wqr.get1()).append("#").append(wqr.get3()).append("#").append(aListPrescript).append("#").append(wqr.get5()).append("\" type=\"radio\" />  ").append(wqr.get4());
            ret.append("</td>");
            ret.append("</tr>");

        }
        ret.append("<tr>");
        ret.append("<td onclick=\"this.childNodes[1].checked=\'checked\';\" colspan=\"4\">");
        ret.append("	<input name=\"btnOk\" value=\"Принять\" type=\"button\" onclick=\"transferInLabCheck('").append(listCab).append("')\"/>");
        ret.append("</td>");
        ret.append("</tr>");
        ret.append("</table></form>");
        return ret.toString();
    }

    public void checkLabAnalyzed(Long aPrescript, HttpServletRequest aRequest) throws NamingException {
        IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class);
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        service.checkLabAnalyzed(aPrescript, username);
    }

    public void checkTransferService(String aListPrescript, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql;
        Date date = new Date();
        SimpleDateFormat formatD = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat formatT = new SimpleDateFormat("HH:mm");
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        String[] l = aListPrescript.split(":");
        for (String r : l) {
            String[] cab = r.split("#");
            sql = new StringBuilder();
            sql.append("update Prescription set prescriptCabinet_id='").append(cab[1]).append("',transferDate=to_date('").append(formatD.format(date))
                    .append("','dd.mm.yyyy'),transferTime=cast('").append(formatT.format(date)).append("' as time)")
                    .append(",transferUsername='").append(username).append("' ")
                    .append(" where id in (").append(cab[2]).append(") and medservice_id in (").append(cab[3]).append(")");
            service.executeUpdateNativeSql(sql.toString());
        }
    }

    public String setDefaultDiary(String aPrescriptions, String aServices, HttpServletRequest aRequest) throws NamingException, JspException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        StringBuilder err = new StringBuilder();
        sql.append("select pres.id as pid, ms.id as msid, tp.id as templateId" + " from prescription pres" + " left join medservice ms on ms.id=pres.medservice_id" + " left join templateprotocol tp on tp.medservice_id=ms.id" + " where pres.id in (").append(aPrescriptions).append(") and ms.id in (").append(aServices).append(") and tp.createDiaryByDefault='1'");
        Collection<WebQueryResult> list = service.executeNativeSql(sql.toString());
        if (!list.isEmpty()) {
            String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
            Long workFunctionId;
            Collection<WebQueryResult> wf = service.executeNativeSql("select wf.id from workfunction wf left join secuser su on su.id=wf.secuser_id where su.login='" + username + "'");
            if (wf.isEmpty()) {
                return null;
            }
            workFunctionId = ConvertSql.parseLong(wf.iterator().next().get1());
            for (WebQueryResult res : list) {
                Long pid = Long.parseLong(res.get1().toString());
                String msid = res.get2().toString();
                Long templateId = Long.parseLong(res.get3().toString());

                sql = new StringBuilder();
                sql.append("select p.id as p1id,p.name as p2name");
                sql.append(" , p.shortname as p3shortname,p.type as p4type");
                sql.append(" , p.minimum as p5minimum, p.normminimum as p6normminimum");
                sql.append(" , p.maximum as p7maximum, p.normmaximum as p8normmaximum");
                sql.append(" , p.minimumbd as p9minimumbd, p.normminimumbd as p10normminimumbd");
                sql.append(" , p.maximumbd as p11maximumbd, p.normmaximumbd as p12normmaximumbd");
                sql.append(" , vmu.id as v13muid,vmu.name as v14muname");
                sql.append(" , vd.id as v15did,vd.name as v16dname");
                sql.append(" ,p.cntdecimal as p17cntdecimal");
                sql.append(" , ''||p.id||case when p.type='2' then 'Name' else '' end as p18enterid");
                sql.append(" , case when p.type in ('3','5')  then p.valueTextDefault when p.type='2' and uv.useByDefault='1' then ''||uv.id else '' end as p19valuetextdefault");
                sql.append(" ,case when uv.useByDefault='1' then uv.name else '' end as p20valueVoc");
                sql.append(" from prescription pres");
                sql.append(" left join templateprotocol tp on tp.medservice_id=pres.medservice_id");
                sql.append(" left join parameterbyform pf on pf.template_id = tp.id");
                sql.append(" left join parameter p on p.id=pf.parameter_id");
                sql.append(" left join userDomain vd on vd.id=p.valueDomain_id");
                sql.append(" left join userValue uv on uv.domain_id=vd.id and uv.useByDefault='1'");
                sql.append(" left join vocMeasureUnit vmu on vmu.id=p.measureUnit_id");
                sql.append(" where pres.id='").append(pid).append("' and tp.medservice_id='").append(msid).append("' and tp.id='").append(templateId).append("' ");
                sql.append(" order by pf.position");
                Collection<WebQueryResult> lwqr = service.executeNativeSql(sql.toString());

                sb.setLength(0);
                sb.append("{");
                sb.append("\"workFunction\":\"").append(workFunctionId).append("\",");
                sb.append("\"workFunctionName\":\"" + "\",");
                if (RolesHelper.checkRoles("/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratory", aRequest)) {
                    sb.append("\"isdoctoredit\":\"1\",");
                } else {
                    sb.append("\"isdoctoredit\":\"0\",");
                }
                sb.append("\"params\":[");
                boolean firstPassed = false;
                boolean firstError = false;
                String[][] props = {{"1", "id"}, {"2", "name"}, {"3", "shortname"}
                        , {"4", "type"}, {"5", "min"}, {"6", "nmin"}, {"7", "max"}, {"8", "nmax"}
                        , {"9", "minbd"}, {"10", "nminbd"}, {"11", "maxbd"}, {"12", "nmaxbd"}
                        , {"13", "unitid"}, {"14", "unitname"}
                        , {"15", "vocid"}, {"16", "vocname"}, {"17", "cntdecimal"}
                        , {"18", "idEnter"}, {"19", "value"}, {"20", "valueVoc"}
                };
                for (WebQueryResult wqr : lwqr) {

                    StringBuilder par = new StringBuilder();
                    par.append("{");
                    boolean isFirtMethod = false;
                    boolean isError = false;
                    if (String.valueOf(wqr.get4()).equals("2") && wqr.get15() == null) {
                        isError = true;
                    }
                    try {
                        for (String[] prop : props) {
                            Object value = PropertyUtil.getPropertyValue(wqr, prop[0]);
                            String strValue = value != null ? value.toString() : "";
                            if (isFirtMethod) par.append(", ");
                            else isFirtMethod = true;
                            par.append("\"").append(prop[1]).append("\":\"").append(str(strValue)).append("\"");

                        }

                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                    par.append("}");
                    if (isError) {
                        if (firstError) err.append(", ");
                        else firstError = true;
                        err.append(par);
                    } else {
                        if (firstPassed) sb.append(", ");
                        else firstPassed = true;
                        sb.append(par);
                    }
                }
                sb.append("]");
                sb.append(",\"errors\":[").append(err).append("]");
                sb.append(",\"template\":\"").append(templateId).append("\"");
                sb.append(",\"protocol\":\"").append("\"");
                sb.append("}");
                saveParameterByProtocol(0L, pid, 0L, sb.toString(), templateId, aRequest);
            }
        }
        return sb.toString();
    }

    public String intakeService(String aListPrescript, String aDate, String aTime, String materialPcr, HttpServletRequest aRequest) throws NamingException, ParseException {
        return intakeServiceWithBarcode(aListPrescript, aDate, aTime, null, materialPcr, aRequest);

    }

    /**
     * Разрешён ли этот номер пробирки: разрещён, если он уникальный в рамках одной даты забора aDate
     *
     * @param aDate       Дата забора
     * @param materialPcr Номер пробирки
     * @param aRequest    HttpServletRequest
     * @return Boolean true - разрешён, false - нет
     */
    private boolean checkMaterialPcrIntake(String aDate, String materialPcr, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String req = "select p.id" +
                " from prescription p " +
                " left join medservice ms on ms.id=p.medservice_id" +
                " left join vocservicesubtype vsst on vsst.id=ms.servicesubtype_id" +
                " where vsst.code='COVID' and p.materialpcrid='" + materialPcr + "'" +
                " and p.intakedate=to_date('" + aDate + "','dd.mm.yyyy')";
        return service.executeNativeSql(req).isEmpty();
    }

    private String intakeServiceWithBarcode(String aListPrescript, String aDate, String aTime, String aBarcode, String materialPcr, HttpServletRequest aRequest) throws NamingException, ParseException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        IPrescriptionService service2 = Injection.find(aRequest).getService(IPrescriptionService.class);
        StringBuilder sql = new StringBuilder();
        Date currentDate = new Date();
        Date intakeDate = DateFormat.parseDate(aDate + aTime, "dd.MM.yyyyHH:mm");
        if (intakeDate.getTime() > currentDate.getTime() + 1000 * 60) {
            return "Дата забора не может быть больше текущей даты!";
        }
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date yesterday = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);
        String yString = df.format(yesterday);
        yesterday = DateFormat.parseDate(yString, pattern); //календарное вчера
        Date intakeJustDate = DateFormat.parseDate(aDate, "dd.MM.yyyy"); //календарная дата забора
        if (intakeJustDate.getTime() < yesterday.getTime()) {
            return "Дата забора не может быть раньше вчерашней даты!";
        }
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        Long spec = getWorkFunction(username, service);
        if (spec == null)
            return "У пользователя " + username + " нет соответствия с рабочей функцией";
        if (!materialPcr.equals("") && !checkMaterialPcrIntake(aDate, materialPcr, aRequest))
            return "В эту дату забора уже есть пробирка ПЦР с таким номером. Дубли запрещены!";

        sql.append("update Prescription set intakeDate=to_date('").append(aDate).append("','dd.mm.yyyy'),intakeTime=cast('").append(aTime).append("' as time)")
                .append(",intakeUsername='").append(username).append("' ")
                .append(",intakeSpecial_id='").append(spec).append("' ");
        if (aBarcode != null && !aBarcode.equals("")) {
            sql.append(",barcodeNumber='").append(aBarcode).append("' ");
        }
        if (!materialPcr.equals("")) {
            sql.append(",materialpcrid='").append(materialPcr).append("' ");
        }
        sql.append(" where id in (").append(aListPrescript).append(")");
        service.executeUpdateNativeSql(sql.toString());
        service2.setPatientDateNumber(aListPrescript, aDate, aTime, username, spec);
        return "1";
    }

    //Отмена взятие забора биоматериала в случае если он еще не доставлен в лабораторию
    public void intakeServiceRemove(String aListPrescript, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("update Prescription set intakeSpecial_id=null,intakeDate=null,intakeTime=null,intakeUsername=null where id in (" + aListPrescript + ") and transferdate is null");
    }

    public boolean checkMedCaseEmergency(Long aIdTemplateList, String idType, HttpServletRequest aRequest) throws NamingException {
        IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class);
        return service.checkMedCaseEmergency(aIdTemplateList, idType);
    }

    public String getPrescriptionTypes(boolean isEmergency, HttpServletRequest aRequest) throws NamingException {
        IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class);
        return service.getPrescriptionTypes(isEmergency);
    }

    public String getLabListFromTemplate(Long aIdTemplateList, Long aPrescriptTypeId, HttpServletRequest aRequest) throws NamingException {
        IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class);
        StringBuilder sb = new StringBuilder();
        String ret = service.getLabListFromTemplate(aIdTemplateList);
        if (aPrescriptTypeId != null && aPrescriptTypeId != 0) {
            String[] arr = ret.split("#");
            for (String s : arr) {
                String[] row = s.split(":");
                if (row.length > 1) {
                    sb.append(row[1]).append("#");
                }
            }
            if (sb.length() > 0) {
                return getPresLabTypes(sb.toString(), aPrescriptTypeId, aRequest);
            }
        }
        return ret;
    }

    public String savePrescription(Long aIdParent, Long aIdTemplateList, Long aFlag, HttpServletRequest aRequest) throws NamingException {
        IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class);
        if (aFlag == 1) return savePrescriptExists(aIdTemplateList, aIdParent, service);
        if (aFlag == 2) return savePrescriptNew(aIdTemplateList, aIdParent, service);
        return "";

    }

    private String savePrescriptExists(Long aIdTemplateList, Long aIdPrescript, IPrescriptionService service) {
        String ret;
        try {
            if (service.savePrescriptExists(aIdTemplateList, aIdPrescript)) ret = "Сохранено в текущий лист назначений";
            else ret = "Ошибка при сохранении  в текущий лист назначений";
        } catch (Exception e) {
            ret = "Ошибка при сохранении  в текущий лист назначений" + e.getMessage();
        }

        return ret;
    }

    public String getTemplateByService(Long aSmoId, Long aPrescriptId, Long aService, String aFunctionGo, String aFunctionSave, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder ret = new StringBuilder();
        StringBuilder sql;
        Long aProtocolId = null;
        Collection<WebQueryResult> list = null;
        if (aSmoId != null && !aSmoId.equals(0L)) {
            list = service.executeNativeSql("select id from diary where medcase_id=" + aSmoId);
        }
        if (list != null && !list.isEmpty()) {
            aProtocolId = ConvertSql.parseLong(list.iterator().next().get1());
        }
        if (aProtocolId != null && !aProtocolId.equals(0L)) {
            ret.append(2).append("#").append(aProtocolId);
        } else {
            sql = new StringBuilder();
            sql.append("select ms.id as msid,ms.name as msname,tp.id as tpid,tp.title as tptitle");
            sql.append(" ,list(pg.name||': '||p.name)");
            sql.append(" from templateprotocol tp");
            sql.append(" left join parameterbyform pf on pf.template_id = tp.id");
            sql.append(" left join parameter p on p.id=pf.parameter_id");
            sql.append(" left join parametergroup pg on pg.id=p.group_id");
            sql.append(" left join medservice ms on ms.id=tp.medservice_id");
            sql.append(" where tp.medservice_id='").append(aService).append("'");
            sql.append(" group by tp.id,tp.title,ms.id,ms.name");
            Collection<WebQueryResult> lwqr = service.executeNativeSql(sql.toString());


            String oldi = "";
            if (lwqr.isEmpty()) {
                ret.append("0#");
                ret.append("<form name='frmTemplate' id='frmTemplate'>");
                ret.append("<a target='_blank' href='entityView-mis_medService.do?id=").append(aService).append("'>").append("НЕТ ШАБЛОНА НА УСЛУГУ</a>");
                ret.append("</form>");
            } else if (lwqr.size() == 1) {
                WebQueryResult wqr = lwqr.iterator().next();
                ret.append("1#").append(wqr.get3()).append("##").append(wqr.get2());
            } else {
                ret.append("0#");
                ret.append("<form name='frmTemplate' id='frmTemplate'><table>");
                for (WebQueryResult wqr : lwqr) {
                    String newi = String.valueOf(wqr.get1());
                    if (!newi.equals(oldi)) {
                        oldi = newi;
                        ret.append("<tr>");
                        ret.append("<th colspan='2'>").append(wqr.get2()).append("</th>");
                        ret.append("</tr>");
                    }
                    ret.append("<tr>");
                    ret.append("<td onclick=\"this.childNodes[1].checked=\'checked\';").append(aFunctionGo).append("('")
                            .append(aSmoId).append("','").append(aPrescriptId).append("','").append(aService)
                            .append("','").append(wqr.get2())
                            .append("','").append(aProtocolId != null ? aProtocolId : "")
                            .append("','").append(wqr.get3())
                            .append("','").append(aFunctionSave)
                            .append("')\" colspan=\"4\">");
                    ret.append("	<input name=\"typeTemplate\" id=\"typeTemplate\" value=\"").append(wqr.get1()).append("#").append(wqr.get3()).append("#").append(aSmoId).append("#").append(wqr.get5()).append("\" type=\"radio\" />  ").append(wqr.get4());
                    ret.append("</td>");
                    ret.append("</tr>");

                }

                ret.append("</table></form>");
            }
        }

        return ret.toString();
    }

    public void saveParameterByProtocol(Long aSmoId, Long aPrescriptId, Long aProtocolId, String aParams, Long aTemplateId, HttpServletRequest aRequest) throws NamingException {
        IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class);
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        service.saveLabAnalyzed(aSmoId, aPrescriptId, aProtocolId, aParams, username, aTemplateId);
    }

    //Подтверждение выполненного анализа по ИД назначения
    public void checkLabontrolByPrescriptionId(Long aPrescriptionId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String sql = "select vis.id as visitId, d.id as diaryId" +
                " from prescription p" +
                " left join medcase vis on vis.id=p.medcase_id" +
                " left join diary d on d.medcase_id=vis.id" +
                " where p.id=" + aPrescriptionId + " and d.id is not null";
        Collection<WebQueryResult> list = service.executeNativeSql(sql);
        if (list.size() != 1) {
            LOG.error("Найдено визитов для проставления " + list.size());
            return;
        }
        WebQueryResult wqr = list.iterator().next();
        Long smoId = Long.parseLong(wqr.get1().toString());
        Long diaryId = Long.parseLong(wqr.get2().toString());
        checkLabControl(smoId, diaryId, aPrescriptionId, aRequest);
    }

    /**
     * Подтверждение выполнения лабораторного анализа
     *
     * @param aSmoId       - ИД 'визита' к врачу лаборатории
     * @param aProtocol    ИД дневника врача-лаборанта
     * @param aPrescriptId ИД назначения
     * @param aRequest     -
     * @return
     * @throws NamingException
     */
    public void checkLabControl(Long aSmoId, Long aProtocol, Long aPrescriptId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        Date date = new Date();
        SimpleDateFormat formatD = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat formatT = new SimpleDateFormat("HH:mm");
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        Long spec = getWorkFunction(username, service);
        if (spec == null)
            throw new IllegalDataException("У пользователя " + username + " нет соответствия с рабочей функцией");
        sql.append("update MedCase set workFunctionExecute_id='").append(spec).append("',dateStart=to_date('").append(formatD.format(date))
                .append("','dd.mm.yyyy'),timeExecute=cast('").append(formatT.format(date)).append("' as time)")
                .append(",editUsername='").append(username).append("' ,editdate=to_date('").append(formatD.format(date))
                .append("','dd.mm.yyyy'),edittime=cast('").append(formatT.format(date)).append("' as time)")
                .append(", noactuality ='0'")
                .append(" where id in (").append(aSmoId).append(")");
        service.executeUpdateNativeSql(sql.toString());
        sql = new StringBuilder();
        sql.append("update Diary set dtype='Protocol',specialist_id='").append(spec).append("',dateRegistration=to_date('").append(formatD.format(date))
                .append("','dd.mm.yyyy'),timeRegistration=cast('").append(formatT.format(date)).append("' as time)")
                .append(" where id in (").append(aProtocol).append(")");
        service.executeUpdateNativeSql(sql.toString());
        //Сохраним информацию о выполнении исследования для платных услуг:
        service.executeUpdateNativeSql("update ContractAccountOperationByService set medCase_id=" + aSmoId +
                " where serviceType='PRESCRIPTION' and serviceId in (select id from Prescription where medCase_id = " + aSmoId + ")");

        // проставляем информацию о выполненной услуги для ДМС в CAMS
        sql = new StringBuilder();
        sql.append("select pat.id as patientId, cg.id as letterId, ms.code as serviceCode" +
                " from prescription p" +
                " left join prescriptionlist pl on pl.id=p.prescriptionlist_id" +
                " left join medcase slo on slo.id = pl.medcase_id" +
                " left join medcase sls on sls.id = slo.parent_id" +
                " left join vocservicestream vss on vss.id=slo.servicestream_id" +
                " left join contractguarantee cg on cg.id=coalesce(sls.guarantee_id, slo.guarantee_id)" +
                " left join medservice ms on ms.id=p.medservice_id" +
                " left join patient pat on pat.id=coalesce(sls.patient_id, slo.patient_id)" +
                " where p.id= " + aPrescriptId + " and vss.iscalcdogovor='1' and cg.id is not null");
        IContractService contractService = Injection.find(aRequest).getService(IContractService.class);
        Collection<WebQueryResult> guaranteeList = service.executeNativeSql(sql.toString());
        if (!guaranteeList.isEmpty()) {
            LOG.info("find guarantee!! sql = " + sql);
            //String typeService, Long idService, String medServiceCode, Long patientId, ContractGuarantee letter
            WebQueryResult guarantee = guaranteeList.iterator().next();
            String typeService = "PRESCRIPTION";
            contractService.addMedServiceAccount(typeService, aPrescriptId, guarantee.get3().toString()
                    , Long.parseLong(guarantee.get1().toString()), Long.parseLong(guarantee.get2().toString()));
        }
        createEmergencyReferenceMsg(aProtocol, aPrescriptId, aRequest);
    }

    /**
     * Отправить сообщение лечащему враче о выходе за границы реф. инт.
     *
     * @param aDiaryId     Diary.id
     * @param aPrescriptId Prescription.id
     * @param aRequest     HttpServletRequest
     */
    private void createEmergencyReferenceMsg(Long aDiaryId, Long aPrescriptId, HttpServletRequest aRequest) throws NamingException {
        IPrescriptionService bean = Injection.find(aRequest).getService(IPrescriptionService.class);
        bean.sendEmergencyReferenceMsg(aDiaryId, aPrescriptId);
    }

    /*находим информацию по пациенту для нахождения реф. значений*/
    private WebQueryResult getPatientAgeAndSexByPrescription(Long aPrescription, IWebQueryService aService) {
        String sql = "select pat.sex_id as sexId, cast(date_part('year',age(p.planStartDate, pat.birthday)) as int) as age" +
                " from prescription p " +
                " left join prescriptionlist pl on pl.id = p.prescriptionlist_id" +
                " left join medcase mc on mc.id=pl.medcase_id" +
                " left join patient pat on pat.id = mc.patient_id" +
                " where p.id=" + aPrescription;
        Collection<WebQueryResult> list = aService.executeNativeSql(sql);
        return list.isEmpty() ? new WebQueryResult() : list.iterator().next();
    }

    public String getParameterByTemplate(Long aSmoId, Long aPrescript, Long aServiceId, Long aProtocolId, Long aTemplateId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        Collection<WebQueryResult> lwqr = null;
        WebQueryResult patientInfo = getPatientAgeAndSexByPrescription(aPrescript, service);
        Long wfId = 0L;
        String wfName = "";
        if (aProtocolId != null && !aProtocolId.equals(0L)) { //результаты по существующему протоколу
            sql.append("select p.id as p1id,p.name as p2name");
            sql.append(" , p.shortname as p3shortname,p.type as p4type");
            sql.append(" , prv.superMin as p5minimum, prv.normaMin as p6normminimum");
            sql.append(" , prv.superMax as p7maximum, prv.normaMax as p8normmaximum");
            sql.append(", cast('' as varchar) as f9, cast('' as varchar) as f10, cast('' as varchar) as f11, cast('' as varchar) as f12 ");
            sql.append(" , vmu.id as v13muid,vmu.name as v14muname");
            sql.append(" , vd.id as v15did,vd.name as v16dname");
            sql.append(" ,p.cntdecimal as p17cntdecimal");
            sql.append(" , ''||p.id||case when p.type='2' then 'Name' else '' end as p18enterid");
            sql.append(" , case when p.type in ('3','5')  then pf.valueText");
            sql.append(" when p.type ='4' then to_char(round(pf.valueBD,case when p.cntdecimal is null then 0 else cast(p.cntdecimal as int) end),'fm99990.'||repeat('0',cast(p.cntdecimal as int)))");
            sql.append(" when p.type ='1' then to_char(round(pf.valueBD,case when p.cntdecimal is null then 0 else cast(p.cntdecimal as int) end),'fm99990') ");
            sql.append(" when p.type='2' then ''||pf.valueVoc_id end as p19val");
            sql.append(" ,vv.name as d20val4v");
            sql.append(" from FormInputProtocol pf");
            sql.append(" left join Diary d on pf.docProtocol_id = d.id");
            sql.append(" left join parameter p on p.id=pf.parameter_id");
            sql.append(" left join ParameterReferenceValue prv on prv.parameter_id= p.id and (prv.sex_id is null or prv.sex_id=").append(patientInfo.get1())
                    .append(") and (").append(patientInfo.get2()).append(" between prv.ageFrom and prv.ageTo or (prv.ageFrom is null and prv.ageTo is null))");
            sql.append(" left join userDomain vd on vd.id=p.valueDomain_id");
            sql.append(" left join userValue vv on vv.id=pf.valueVoc_id");
            sql.append(" left join vocMeasureUnit vmu on vmu.id=p.measureUnit_id");
            sql.append(" where d.id='").append(aProtocolId).append("'");
            sql.append(" order by pf.position");
            lwqr = service.executeNativeSql(sql.toString());

        }
        if (lwqr == null || lwqr.isEmpty()) {
            sql = new StringBuilder();
            sql.append("select p.id as p1id,p.name as p2name");
            sql.append(" , p.shortname as p3shortname,p.type as p4type");
            sql.append(" , prv.superMin as p5minimum, prv.normaMin as p6normminimum");
            sql.append(" , prv.superMax as p7maximum, prv.normaMax as p8normmaximum");
            sql.append(", cast('' as varchar) as f9, cast('' as varchar) as f10, cast('' as varchar) as f11, cast('' as varchar) as f12 ");
            sql.append(" , vmu.id as v13muid,vmu.name as v14muname");
            sql.append(" , vd.id as v15did,vd.name as v16dname");
            sql.append(" ,p.cntdecimal as p17cntdecimal");
            sql.append(" , ''||p.id||case when p.type='2' then 'Name' else '' end as p18enterid");
            sql.append(" , case when p.type in ('3','5')  then p.valueTextDefault else '' end as p19valuetextdefault");
            sql.append(" from parameterbyform pf");
            sql.append(" left join templateprotocol tp on pf.template_id = tp.id");
            sql.append(" left join parameter p on p.id=pf.parameter_id");
            sql.append(" left join ParameterReferenceValue prv on prv.parameter_id= p.id and (prv.sex_id is null or prv.sex_id=").append(patientInfo.get1())
                    .append(") and (").append(patientInfo.get2()).append(" between prv.ageFrom and prv.ageTo or (prv.ageFrom is null and prv.ageTo is null))");
            sql.append(" left join userDomain vd on vd.id=p.valueDomain_id");
            sql.append(" left join vocMeasureUnit vmu on vmu.id=p.measureUnit_id");
            sql.append(" where tp.id='").append(aTemplateId).append("'");
            sql.append(" order by pf.position");
            lwqr = service.executeNativeSql(sql.toString());
        } else {
            sql = new StringBuilder();
            sql.append("select mc.workFunctionexecute_id, vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as vwfname from diary d left join medcase mc on mc.id=d.medcase_id left join workfunction wf on wf.id=mc.workfunctionexecute_id left join worker w on w.id=wf.worker_id left join patient wp on wp.id=w.person_id left join vocworkfunction vwf on vwf.id=wf.workfunction_id where d.id=").append(aProtocolId).append(" and mc.workFunctionExecute_id is not null");
            Collection<WebQueryResult> lwf = service.executeNativeSql(sql.toString());
            if (!lwf.isEmpty()) {
                WebQueryResult wqr = lwf.iterator().next();
                wfId = ConvertSql.parseLong(wqr.get1());
                wfName = "" + wqr.get2();
            }
        }


        StringBuilder sb = new StringBuilder();
        StringBuilder err = new StringBuilder();
        sb.append("{");
        sb.append("\"workFunction\":\"").append(wfId).append("\",");
        sb.append("\"workFunctionName\":\"").append(wfName).append("\",");
        sb.append("\"isdoctoredit\":\"1\",");
        sb.append("\"params\":[");
        boolean firstPassed = false;
        boolean firstError = false;
        String[][] props = {{"1", "id"}, {"2", "name"}, {"3", "shortname"}
                , {"4", "type"}, {"5", "min"}, {"6", "nmin"}, {"7", "max"}, {"8", "nmax"}
                , {"9", "minbd"}, {"10", "nminbd"}, {"11", "maxbd"}, {"12", "nmaxbd"}
                , {"13", "unitid"}, {"14", "unitname"}
                , {"15", "vocid"}, {"16", "vocname"}, {"17", "cntdecimal"}
                , {"18", "idEnter"}, {"19", "value"}, {"20", "valueVoc"}
        };
        for (WebQueryResult wqr : lwqr) {

            StringBuilder par = new StringBuilder();
            par.append("{");
            boolean isFirtMethod = false;
            boolean isError = String.valueOf(wqr.get4()).equals("2") && wqr.get15() == null;
            try {

                for (String[] prop : props) {
                    Object value = PropertyUtil.getPropertyValue(wqr, prop[0]);
                    String strValue = value != null ? value.toString() : "";

                    if (isFirtMethod) par.append(", ");
                    else isFirtMethod = true;
                    par.append("\"").append(prop[1]).append("\":\"").append(str(strValue)).append("\"");

                }

            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            par.append("}");
            if (isError) {
                if (firstError) err.append(", ");
                else firstError = true;
                err.append(par);
            } else {
                if (firstPassed) sb.append(", ");
                else firstPassed = true;
                sb.append(par);
            }
        }
        sb.append("]");
        sb.append(",\"errors\":[").append(err).append("]");
        sb.append(",\"template\":\"").append(aTemplateId).append("\"");
        sb.append(",\"protocol\":\"").append(aProtocolId).append("\"");
        sb.append("}");
        return sb.toString();

    }

    private String str(String aValue) {
        if (aValue.indexOf('\"') != -1) {
            aValue = aValue.replaceAll("\"", "\\\\\"");
        }
        return aValue;
    }

    private String savePrescriptNew(Long aTemplateList, Long aMedCase, IPrescriptionService service) {
        String ret;
        try {
            if (service.savePrescriptNew(aTemplateList, aMedCase) > 0) ret = "Сохранено в новый лист назначений";
            else ret = "Ошибка при сохранении  в новый лист назначений";
        } catch (Exception e) {
            ret = "Ошибка при сохранении  в новый лист назначений:" + e.getMessage();
        }
        return ret;
    }

    public Long createTemplateFromList(Long aPrescriptList, String aName, HttpServletRequest aRequest) throws NamingException {
        IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class);
        return service.savePrescriptNew(aPrescriptList, 0L, aName);
    }

    public String getInfoAboutPrescription(Long aId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();

        sql.append("select to_char(p.createdate,'dd.MM.yyyy')||' '||cast(p.createtime as varchar(5))||' '||p.createusername as createInfo ")
                .append(",to_char(p.intakedate,'dd.MM.yyyy')||' '||cast(p.intaketime as varchar(5)) ||' '|| p.intakeusername as intakeInfo")
                .append(",to_char(p.transferdate,'dd.MM.yyyy') ||' '||cast(p.transfertime as varchar(5)) ||' '||p.transferusername as transferInfo")
                .append(",to_char(vis.createdate,'dd.MM.yyyy')||' '||cast(vis.createtime as varchar(5))||' '||vis.username as researchIndo")
                .append(",to_char(vis.datestart,'dd.MM.yyyy')||' '||cast(vis.timeexecute as varchar(5))||' '||vis.editusername as labDoctorIndo")
                .append(",to_char(p.canceldate,'dd.MM.yyyy')||' '||cast(p.canceltime as varchar(5))||' '||p.cancelusername ||coalesce(vpcr.name,'')||' '||coalesce(p.cancelreasontext,'')  as cancelInfo").append(" from prescription p " + " left join medcase vis on vis.id=p.medcase_id" + " left join vocprescriptcancelreason vpcr on vpcr.id=p.cancelreason_id" + " where p.id='").append(aId).append("'");

        Collection<WebQueryResult> res = service.executeNativeSql(sql.toString());


        StringBuilder sb = new StringBuilder();
        for (WebQueryResult wqr : res) {
            sb.append(unnul(wqr.get1())).append("|").append(unnul(wqr.get2())).append("|").append(unnul(wqr.get3())).append("|").append(unnul(wqr.get4())).append("|").append(unnul(wqr.get5())).append("|").append(unnul(wqr.get6()));
        }
        return sb.toString();
    }

    private String unnul(Object o) {
        return o == null ? "" : o.toString();
    }

    /**
     * Получить, можно ли создавать ЛН из СЛС (можно, пока не сделан СЛО).
     *
     * @param aMedcase MedCase.id
     * @param aRequest HttpServletRequest
     * @return Boolean true - department
     */
    public Boolean isPrescriptListCanBeChangedFromSLS(String aMedcase, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return !service.executeNativeSql("select id from medcase where dtype='DepartmentMedCase' and parent_id=" + aMedcase, 1).isEmpty();
    }

    /**
     * Получить, СЛО или нет.
     *
     * @param aMedcase MedCase.id
     * @param aRequest HttpServletRequest
     * @return Boolean true - department
     */
    public Boolean isPrescriptListfromSLO(String aMedcase, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> wrt = service.executeNativeSql("select id from medcase where id=" + aMedcase + " and dtype='DepartmentMedCase'", 1);
        return !wrt.isEmpty();
    }

    /**
     * Отменить назначения на консультацию - пока не выполнено.
     *
     * @param aPresctiptionId Presctiption.id
     * @param aReason         String причина отмены
     * @param oper            String 1 - еcли назначение на операцию, 0 - нет
     * @param aRequest        HttpServletRequest
     * @return String сообщение
     */
    public String cancelWFPrescription(String aPresctiptionId, String aReason, String oper, HttpServletRequest aRequest) throws NamingException {
        if (aReason == null || aReason.trim().equals("")) {
            return "Необходимо указать причину аннулирования!";
        }
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String permSql = "select case when wf.intakeDate is null then '1' else '0' end" +
                " from prescription wf" +
                " left join vocconsultingtype t on wf.vocconsultingtype_id=t.id" +
                " where cancelDate is null and wf.id=" + aPresctiptionId;
        Collection<WebQueryResult> wrt = service.executeNativeSql(permSql, 1);
        if (!wrt.isEmpty() && wrt.iterator().next().get1().equals("1")) {
            String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
            String canAnnulSql = "update prescription " +
                    "set cancelDate = current_date, " +
                    "canceltime = current_time, " +
                    "cancelReasonText = '" + aReason + "' ," +
                    "cancelusername = '" + username + "' ," +
                    "cancelspecial_id = (select wf.id from secuser su left join workfunction wf on wf.secuser_id=su.id where su.login='" + username + "') " +
                    "where id=" + aPresctiptionId;
            service.executeUpdateNativeSql(canAnnulSql); //Отметили назначение как аннулированное
            if ("1".equals(oper)) {
                //нужно освободить времена
                releaseOperationTimes(aPresctiptionId, aRequest);
            }
            return "Назначение отменено.";
        } else {
            return "Невозможно отменить назначение! Уже было отменено или находится в работе. Можно отменять невыполненные назначения.";
        }
    }

    /**
     * Освободить занятые отменённой операцией времена
     *
     * @param aPresctiptionId Presctiption.id
     * @param aRequest        HttpServletRequest
     * @return String сообщение
     */
    private void releaseOperationTimes(String aPresctiptionId, HttpServletRequest aRequest) throws NamingException {
        final int numAfterCells = 50;  //сколько максимально ячеек проверять после
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> wrt =
                service.executeNativeSql("select id from workcalendartime where prescription = " + aPresctiptionId, 1);
        if (!wrt.isEmpty()) {
            Long startId = Long.valueOf(wrt.iterator().next().get1().toString());
            startId++;
            Long endId = startId + numAfterCells;
            Collection<WebQueryResult> list =
                    service.executeNativeSql(
                            "select id,(rest is not null and rest) or prescription is not null from workcalendartime where id between " + startId + " and " + endId);
            for (WebQueryResult r : list) {
                Long pId = Long.parseLong(r.get1().toString());
                Boolean next = Boolean.parseBoolean(r.get2().toString());
                if (!next) //если ячейка занята или уже с другим назначением
                    break; //завершить, если ячейка не занята или заняты уже другим назначением
                else
                    //set rest null
                    service.executeUpdateNativeSql("update workcalendartime set rest = null where id=" + pId);
            }
            startId--;
            service.executeUpdateNativeSql("update workcalendartime set rest = null where id=" + startId); //освободить 1е время
        }
    }

    /**
     * Проверка дублей назначений на ковид на следующий день (поиск по всем СЛО из СЛС)
     *
     * @param aMedCase СЛО
     * @param aRequest HttpServletRequest
     * @return String 0 если нет дублей, -1 - если есть
     */
    public String checkDoublesNextDayCovid(String aMedCase, String planStartDate, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder req = new StringBuilder();
        req.append("select p.id");
        req.append(" from medcase mc ");
        req.append(" left join medcase mc2 on mc2.parent_id = mc.parent_id");
        req.append(" left join prescriptionList pl on pl.medcase_id = mc2.id");
        req.append(" left join prescription p on p.prescriptionList_id = pl.id");
        req.append(" left join medservice ms on ms.id = p.medservice_id");
        req.append(" where mc.id ='").append(aMedCase).append("' ");
        req.append(" and ms.id=22347");
        req.append(" and p.dtype='ServicePrescription'");
        req.append(" and p.medservice_id is not null");
        req.append(" and p.canceldate is null  and p.planstartdate=to_date('");
        req.append(planStartDate).append("','dd.mm.yyyy')");
        return service.executeNativeSql(req.toString()).isEmpty() ?
                "0" : "-1";
    }

    /**
     * Проверка дублей назначений на ковид (только по поликлинике). Нельзя дублировать, пока не выполнено
     *
     * @param aMedCase Визит
     * @param aRequest HttpServletRequest
     * @return String 0 если нет дублей, -1 - если есть
     */
    public String checkDoublesPolyclinicCovid(String aMedCase, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder req = new StringBuilder();
        req.append("select pall.id");
        req.append(" from medcase vis ");
        req.append(" left join patient pat on pat.id=vis.patient_id");
        req.append(" left join medcase allvis on allvis.patient_id=pat.id and allvis.dtype='Visit'");
        req.append(" left join prescriptionList plall on plall.medcase_id = allvis.id");
        req.append(" left join prescription pall on pall.prescriptionList_id = plall.id");
        req.append(" left join medservice msall on msall.id = pall.medservice_id");
        req.append(" where vis.id ='").append(aMedCase).append("' ");
        req.append(" and msall.id=22347");
        req.append(" and pall.dtype='ServicePrescription'");
        req.append(" and pall.medservice_id is not null");
        req.append(" and pall.canceldate is null");
        req.append(" and pall.medcase_id is null");
        return service.executeNativeSql(req.toString()).isEmpty() ?
                "0" : "-1";
    }

    /**
     * Отменить брак назначения (для админов)
     *
     * @param aPrescript Назначение
     * @param aRequest   HttpServletRequest
     */
    public void unCancelPrescript(Long aPrescript, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder req = new StringBuilder();
        req.append("update prescription set canceldate=null,canceltime=null")
                .append(" ,cancelreason_id=null,cancelreasontext=null")
                .append(" ,transferdate=null,transfertime=null,transferspecial_id=null,transferusername=null")
                .append(" where id=").append(aPrescript);
        service.executeUpdateNativeSql(req.toString());
    }

    /**
     * Нужно ли при заборе биоматериала вводить номер пробирки ПЦР
     *
     * @param aPrescriptList Номер назначения
     * @param aRequest       HttpServletRequest
     * @return String 0 если не нужно, 1 - если нужно
     */
    public String intakeServiceShowSetMaterialPCR(String aPrescriptList, HttpServletRequest aRequest) throws NamingException {
        if (aPrescriptList.contains(",")) return "0";
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder req = new StringBuilder();
        req.append("select p.id")
                .append(" from prescription p ")
                .append(" left join medservice ms on ms.id=p.medservice_id")
                .append(" left join vocservicesubtype vsst on vsst.id=ms.servicesubtype_id")
                .append(" where vsst.code='COVID' and (p.materialpcrid is null or p.materialpcrid='')")
                .append(" and p.id='").append(aPrescriptList).append("' ");
        return service.executeNativeSql(req.toString()).isEmpty() ?
                "0" : "1";
    }

    /**
     * Является ли отделение ковидным.
     *
     * @param plId     PrescriptionList.id
     * @param aRequest HttpServletRequest
     * @return 1 - ковидное, 0 - не ковидное
     */
    public String checkSloCovid(Long plId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String query = "select vbt.id from medcase slo" +
                " left join prescriptionlist pl on pl.medcase_id=slo.id" +
                " left join bedfund as bf on bf.id=slo.bedfund_id" +
                " left join vocbedtype vbt on vbt.id=bf.bedType_id" +
                " where vbt.code='14' and pl.id=" + plId;
        return service.executeNativeSql(query).isEmpty() ?
                "0" : "1";
    }

    /**
     * Проверка, нужно ли создавать примечание к назначению на лаб. услугу
     *
     * @param msIds Medservice.id услуг в назначении
     * @return список через запятую 1,2,3, где указаны типы примечаний (noteForLabPrescript)
     */
    public String checkNoteNecessary(String msIds, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String query = "select list(distinct case when m1.isaskstatuswomen then '1' else '' end" +
                " ||(case when m2.isaskbiotype then '2' else '' end)" +
                " ||(case when m3.isaskhwa then '3' else '' end))" +
                " from medservice m" +
                " left join medservice m1 on m1.id=m.id and m1.isaskstatuswomen=true" +
                " left join medservice m2 on m2.id=m.id and m2.isaskbiotype=true" +
                " left join medservice m3 on m3.id=m.id and m3.isaskhwa=true" +
                " where m.id in (" + msIds + ")";
        Collection<WebQueryResult> list = service.executeNativeSql(query);
        return list.isEmpty() ? "" : list.iterator().next().get1().toString();
    }

    /**
     * Проверка, нужно ли создавать примечание к назначению на лаб. услугу
     *
     * @param plId PrescriptionList.id услуг в назначении
     * @return возраст
     */
    public String getPatientAge(String plId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String query = "select cast(to_char(current_date,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)" +
                " from patient pat" +
                " left join medcase mc on mc.patient_id = pat.id" +
                " left join prescriptionlist pl on pl.medcase_id = mc.id" +
                " where pl.id=" + plId;
        Collection<WebQueryResult> list = service.executeNativeSql(query);
        return list.isEmpty() ? "" : list.iterator().next().get1().toString();
    }
}