package ru.ecom.mis.web.dwr.medcase;

import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.jdom.IllegalDataException;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.ecom.mis.ejb.service.patient.IPatientService;
import ru.ecom.mis.web.webservice.FondWebService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.forms.validator.validators.SnilsStringValidator;
import ru.nuzmsh.util.date.AgeUtil;
import ru.nuzmsh.util.format.DateFormat;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;

import static ru.nuzmsh.util.StringUtil.isNotEmpty;
import static ru.nuzmsh.util.StringUtil.isNullOrEmpty;

public class PatientServiceJs {

    private static final Logger LOG = Logger.getLogger(PatientServiceJs.class);

    public String getSexByName(String firstname, HttpServletRequest request) throws NamingException {
        IPatientService service = Injection.find(request).getService(IPatientService.class);
        return new GsonBuilder().create().toJson(service.getSexByName(firstname));

    }

    public String getOpenHospByPatient(Long medcaseId, HttpServletRequest request) throws NamingException, SQLException {
        String sql = "select sls.id as hospId, coalesce(ss.code,'') as cardNumber, coalesce(pat.works,'') as workplace" +
                " ,sls.patient_id as patient" +
                " from medcase sls " +
                " left join statisticstub ss on ss.medcase_id = sls.id" +
                " left join patient pat on pat.id=sls.patient_id" +
                " where sls.id=" + medcaseId;
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        return service.executeSqlGetJsonObject(sql);

    }

    public String markCovidAsSent(Long cardId, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        String username = LoginInfo.find(request.getSession(true)).getUsername();
        return "обновлено " + service.executeUpdateNativeSql("update Covid19 set exportDate=current_date, exportTime=current_time" +
                ",exportUsername='" + username + "' where id=" + cardId) + " карт";
    }

    public String getPhoneByPatientId(Long patientId, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select coalesce(phone,'') from patient where id=" + patientId);
        return list.isEmpty() ? "" : list.iterator().next().get1().toString();
    }

    /*Устанавливаем признак *полис проверен для конкретного случая СЛС */
    public void setPolicyIsChecked(Long medcaseId, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("update medcase_medpolicy " +
                "set datesync = current_date , isManualSync ='1' where medcase_id=" + medcaseId);
    }

    public String getPaid(Long patientId, HttpServletRequest request) throws NamingException {
        StringBuilder sql = new StringBuilder();
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        sql.append("select count(cao.cost) from patient p " +
                " left join contractperson cp on cp.patient_id= p. id" +
                " left join medcontract mc on mc.customer_id= cp.id" +
                " left join contractaccount ca on ca.contract_id= mc.id" +
                " left join contractaccountoperation cao on cao.account_id= ca.id where p.id =").append(patientId);
        Collection<WebQueryResult> res = service.executeNativeSql(sql.toString());
        return res.isEmpty() ? "" : res.iterator().next().get1().toString();
    }

    public String getPatientFromContractPerson(Long contractPersonId, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        String sql = "select patient_id from contractperson  where id = " + contractPersonId;
        Collection<WebQueryResult> res = service.executeNativeSql(sql);
        return res.iterator().next().get1().toString();
    }

    public void savePrivilege(Long patientId, String numberDoc, String serialDoc,
                              String beginDate, String endDate, String categoryId,
                              HttpServletRequest request) throws NamingException {

        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        StringBuilder headSql = new StringBuilder();
        StringBuilder bodySql = new StringBuilder();

        headSql.append("insert into privilege(person_id,category_id,begindate");
        bodySql.append("VALUES(").append(patientId).append(",").append(categoryId).append(",'").append(beginDate).append("'");

        if (isNotEmpty(numberDoc)) {
            headSql.append(",numberdoc");
            bodySql.append(",'").append(numberDoc).append("'");
        }

        if (isNotEmpty(serialDoc)) {
            headSql.append(",serialdoc");
            bodySql.append(",'").append(serialDoc).append("'");
        }

        if (isNotEmpty(endDate)) {
            headSql.append(",enddate");
            bodySql.append(",'").append(endDate).append("'");
        }

        headSql.append(")");
        bodySql.append(")");
        service.executeUpdateNativeSql(headSql.toString() + bodySql.toString());
    }

    public String getPatients(String lastname, String firstname, String middlename
            , String year, HttpServletRequest request) throws NamingException {
        if (lastname == null || (lastname + (firstname != null ? firstname : "")
                + (middlename != null ? middlename : "")).length() < 4) {
            return "введите данные для поиска";
        }
        StringBuilder sql = new StringBuilder();
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        sql.append("select p.id,p.patientSync,p.lastname,p.firstname,p.middlename,to_char(p.birthday,'dd.mm.yyyy') from patient p left join medpolicy mp on mp.patient_id=p.id where p.lastname like '").append(lastname.toUpperCase()).append("%' ");
        if (isNotEmpty(firstname)) {
            sql.append(" and p.firstname like '").append(firstname.toUpperCase()).append("%' ");
        }
        if (isNotEmpty(middlename)) {
            sql.append(" and p.middlename like '").append(middlename.toUpperCase()).append("%' ");
        }
        if (isNotEmpty(year)) {
            sql.append(" and to_char(p.birthday,'yyyy')='").append(year).append("' ");
        }

        sql.append(" group by p.id,p.patientSync,p.lastname,p.firstname,p.middlename,p.birthday");
        sql.append(" order by p.lastname,p.firstname,p.middlename,p.birthday");
        Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(), 20);
        StringBuilder res = new StringBuilder();
        res.append("<ul id='listPatients'>");
        boolean isFirst = true;
        for (WebQueryResult wqr : list) {

            StringBuilder s = new StringBuilder().append(wqr.get1()).append("#").append(wqr.get3()).append(" ").append(wqr.get4())
                    .append(" ").append(wqr.get5()).append(" г.р. ").append(wqr.get6());
            res.append("<li ondblclick=\"this.childNodes[1].checked='checked';checkPatient('").append(s).append("');\" onclick=\"this.childNodes[1].checked='checked';checkPatient('").append(s).append("')\">");
            res.append(" <input class='radio' type='radio' name='rdPatient' id='rdPatient' ");
            if (isFirst) {
                res.append(" checked='true' ");
                isFirst = false;
            }
            res.append(" value='").append(s).append("'>");
            res.append(wqr.get2());
            res.append(" ").append(wqr.get3());
            res.append(" ").append(wqr.get4());
            res.append(" ").append(wqr.get5());
            res.append(" ").append(wqr.get6());
            if (wqr.get1() != null) {
                res.append("<a onclick='getDefinition(\"entityShortView-mis_patient.do?id=")
                        .append(wqr.get1()).append("\", event); return false ;' ondblclick='javascript:goToPage(\"entityView-mis_patient.do\",\"")
                        .append(wqr.get1()).append("\")'><img src=\"/skin/images/main/view1.png\" alt=\"Просмотр записи\" title=\"Просмотр записи\" height=\"16\" width=\"16\"></a>");

            }
            res.append("</li>");
        }
        res.append("</ul>");

        return res.toString();
    }

    public String getIsPatientInList(Long patientId, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        StringBuilder sb = new StringBuilder();
        String sql = "select pl.name as plName, pl.colorName as colorName, coalesce(plr.message,pl.message) as plrMessage" +
                " from patientListRecord plr" +
                " left join patientList pl on pl.id=plr.patientList" +
                " where plr.patient=" + patientId;
        Collection<WebQueryResult> res = service.executeNativeSql(sql);
        for (WebQueryResult wqr : res) {
            if (sb.length() > 0) {
                sb.append(":");
            }
            sb.append("<div style='").append(wqr.get2().toString()).append("'>");
            sb.append(wqr.get3().toString());
            sb.append("</div>");
        }
        return sb.toString();
    }

    public String showPatientCheckByFondHistory(Long patientId, String type, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        StringBuilder ret = new StringBuilder();
        try {
            Object[] re = service.executeNativeSqlGetObj("select lastname, firstname, middlename, birthday, commonnumber from patient where id = " + patientId).get(0);
            String whereSql;
            if ("1".equals(type)) {
                whereSql = "pf.lastname = '" + re[0] + "' and pf.firstname = '" + re[1] + "' and middlename = '" + re[2] + "' and pf.birthday = '" + re[3] + "'";
            } else {
                whereSql = "pf.commonnumber='" + re[4] + "'";
            }

            String sql = "select pf.lastname, pf.firstname, pf.middlename, to_char(pf.birthday,'dd.MM.yyyy') as f3_birthday" +
                    " ,to_char(pf.checkdate,'dd.MM.yyyy') as f4_check_date, coalesce(pf.lpuattached,'') as f5, coalesce(pf.attachedtype,'') as f6" +
                    " ,coalesce(to_char(pf.attacheddate,'dd.MM.yyyy'),'') as f7_att_date" +
                    ", coalesce(department,'') as f8, coalesce(doctorsnils,'') as f9" +
                    " from patientfond pf" +
                    " where " + whereSql +
                    " order by pf.checkdate desc limit 50";

            Collection<WebQueryResult> res = service.executeNativeSql(sql);
            if (!res.isEmpty()) {
                ret.append("<table border='1'>");
                ret.append("<tr><td>Фамилия</td><td>Имя</td><td>Отчество</td><td>ДР</td><td>Дата проверки</td><td>ЛПУ прикрепления</td><td>Тип прикрепления</td><td>Дата прикрепления</td><td>Подразделение</td><td>СНИЛС врача</td></tr>");
                for (WebQueryResult wqr : res) {
                    ret.append("<tr>")
                            .append("<td>").append(wqr.get1()).append("</td>")
                            .append("<td>").append(wqr.get2()).append("</td>")
                            .append("<td>").append(wqr.get3()).append("</td>")
                            .append("<td>").append(wqr.get4()).append("</td>")
                            .append("<td>").append(wqr.get5()).append("</td>")
                            .append("<td>").append(wqr.get6()).append("</td>")
                            .append("<td>").append(wqr.get7()).append("</td>")
                            .append("<td>").append(wqr.get8()).append("</td>")
                            .append("<td>").append(wqr.get9()).append("</td>")
                            .append("<td>").append(wqr.get10()).append("</td>")
                            .append("</tr>");
                }
                ret.append("</table>");
            }
            return ret.toString();
        } catch (Exception e) {
            LOG.error("ERR=", e);
            return "" + e;
        }

    }

    /**
     * Получение списка печатных документов по группе
     *
     * @param groupName - Имя группы документов
     * @param request   -
     * @return JSON со списком документов
     * @throws NamingException
     */
    public String getUserDocumentList(String groupName, HttpServletRequest request) throws NamingException {
        String sql = "select ud.id, ud.name, ud.filename from  userDocument ud" +
                " left join VocUserDocumentGroup vudg on ud.groupType_id = vudg.id where upper(vudg.code) = upper('" + groupName + "')";
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        Collection<WebQueryResult> res = service.executeNativeSql(sql);
        JSONArray ret = new JSONArray();
        if (!res.isEmpty()) {
            for (WebQueryResult r : res) {
                JSONObject el = new JSONObject();
                el.put("docId", r.get1().toString()).put("docName", r.get2().toString()).put("docFileName", r.get3().toString());
                ret.put(el);
            }
        }
        return ret.toString();
    }

    public void changeMedPolicyType(Long policyId, Long newMedPolicyId, HttpServletRequest request) throws NamingException {
        IPatientService service = Injection.find(request).getService(IPatientService.class);
        service.changeMedPolicyType(policyId, newMedPolicyId);
    }

    public boolean updateDataByFondAutomatic(Long patientFondId, Long checkId
            , boolean isUpdatePatient, boolean isUpdateDocument, boolean isUpdatePolicy, boolean isUpdateAttachment
            , HttpServletRequest request) throws NamingException {
        IPatientService service = Injection.find(request).getService(IPatientService.class);
        return service.updateDataByFondAutomatic(patientFondId
                , checkId, isUpdatePatient, isUpdateDocument
                , isUpdatePolicy, isUpdateAttachment);
    }

    public String checkAllPatients(String updPatient, String updDocument, String updPolicy, String updAttachment, String type, String patientList, HttpServletRequest request) {
        return FondWebService.checkAllPatientsByFond(updPatient, updDocument, updPolicy, updAttachment, type, patientList, request);
    }

    public String checkDispAttached(Long dispTypeId, Long patientId, HttpServletRequest request) throws NamingException {
        if (dispTypeId == null || dispTypeId == 0 || patientId == null) return "1";
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        Collection<WebQueryResult> isAttachedDisp = service.executeNativeSql("select id" +
                " from vocextdisp where id=" + dispTypeId + " and attachmentpopulation='1'", 1);
        JSONObject isPatAttached = new JSONObject(checkPatientAttachedOrDead(patientId, request));
        return !isAttachedDisp.isEmpty() && isPatAttached.getString("statusCode").equals("1") ? "1" : "0";
    }

    public String checkPatientAttachedOrDead(Long aPatientId, HttpServletRequest request) throws NamingException {
        JSONObject ret = new JSONObject();
        String statusCode; //0 - not attached or err, 1 - attached, 2 - dead
        String statusName = "-";
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select coalesce(pf.lpuattached,'') as lpuAttached" +
                ", to_char(pf.checkdate,'dd.mm.yyyy') as checkDate" +
                " ,case when pf.deathdate is not null then to_char(pf.deathdate,'dd.mm.yyyy') else '' end as deathDate" +
                " ,coalesce(pf.doctorsnils,'') as doctorId" +
                " from patient p " +
                " left join patientfond pf on (pf.lastname=p.lastname and pf.firstname=p.firstname and pf.middlename=p.middlename " +
                " and pf.birthday=p.birthday) where p.id=" + aPatientId + " and pf.id is not null order by pf.checkdate desc", 1);
        if (!list.isEmpty()) {
            Collection<WebQueryResult> defLpu = service.executeNativeSql("select sc.keyvalue, case when sc.description!='' then sc.description else '№ '|| sc.keyvalue end from softconfig sc where sc.key='DEFAULT_LPU_OMCCODE'");
            String defaultLpu;
            String defaultLpuName;
            if (defLpu.isEmpty()) {
                ret.put("status", "0");
                ret.put("statusName", "Необходимо указать ЛПУ по умолчанию в настройках (DEFAULT_LPU_OMCCODE)");
                return ret.toString();
            } else {
                WebQueryResult wqr = defLpu.iterator().next();
                defaultLpu = wqr.get1() != null ? wqr.get1().toString() : "";
                defaultLpuName = wqr.get2() != null ? wqr.get2().toString() : "";
            }
            WebQueryResult wqr = list.iterator().next();
            String lastAttachment = wqr.get1().toString();
            String checkDate = wqr.get2() != null ? wqr.get2().toString() : "";
            String deathDate = wqr.get3() != null ? wqr.get3().toString() : "";
            String doctorSnils = wqr.get4() != null ? wqr.get4().toString() : "";
            if (lastAttachment.equals(defaultLpu)) {
                if (isNullOrEmpty(doctorSnils)) {
                    statusName = " Внимание! ФОНД не имеет информации о прикреплении пациента к участку!";
                }
                statusCode = "1";
                statusName = "По данным ФОМС на " + checkDate + " пациент прикреплен к ЛПУ " + defaultLpuName + "." + statusName;
            } else {
                statusCode = "0";
                statusName = "По данным ФОМС на " + checkDate + " пациент прикреплен к ЛПУ " + lastAttachment;
            }
            if (deathDate.length() == 10) {
                statusCode = "2";
                statusName = "По данным ФОМС на " + checkDate + " пациент умер " + deathDate;
                service.executeUpdateNativeSql("update Patient set deathDate=to_date('" + deathDate + "','dd.mm.yyyy') where id='" + aPatientId + "'");
            }
        } else {
            statusCode = "0";
            statusName = "Необходимо выполнить проверку по базе ФОМС";
        }
        ret.put("statusCode", statusCode);
        ret.put("statusName", statusName);
        return ret.toString();
    }

    public String getSexByOmccode(String omcCode, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select id,name from vocSex where omcCode='" + omcCode + "'", 1);
        if (list.isEmpty()) {
            return "";
        } else {
            WebQueryResult wqr = list.iterator().next();
            return wqr.get1() + "#" + wqr.get2();
        }
    }

    public void editColorType(Long patientId, String colorTypeCurrent, HttpServletRequest request) throws NamingException {
        String colorType = colorTypeCurrent != null && colorTypeCurrent.trim().equals("1") ? "0" : "1";
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("update Patient set colorType='" + colorType + "' where id='" + patientId + "'");
    }

    public String getFactorByProfession(Long professionId, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        String sql = " select vdp.id,vdp.factorOfProduction from VocDocumentProfession vdp " +
                " where  vdp.id='" + professionId + "'";
        Collection<WebQueryResult> list = service.executeNativeSql(sql, 1);
        if (!list.isEmpty()) {
            WebQueryResult wqr = list.iterator().next();
            if (wqr.get2() != null) {
                return "" + wqr.get2();
            }
        }
        return "";
    }

    public String getCodefByRegIcForeign(Long areaId, Long companyId, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        String sql = " select smo.id,smo.name from Omc_SprSmo smo " +
                " left join Omc_KodTer ter on ter.okato=smo.fondokato " +
                " left join reg_ic com on com.ogrn=smo.voc_code " +
                " where " +
                " ter.id='" + areaId +
                "' and com.id='" + companyId + "'";
        Collection<WebQueryResult> list = service.executeNativeSql(sql, 1);
        if (!list.isEmpty()) {
            WebQueryResult wqr = list.iterator().next();
            if (wqr.get1() != null) {
                return wqr.get1() + "#" + wqr.get2();
            }
        }
        return "";
    }

    public String getRegIcForeignByCodef(Long companyOgrn, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        String sql = " select com.id,coalesce(com.omcCode,'')||' '||com.name from Omc_SprSmo smo " +
                " left join Omc_KodTer ter on ter.okato=smo.fondokato " +
                " left join reg_ic com on com.ogrn=smo.voc_code " +
                " where " +
                " smo.id='" + companyOgrn + "'";
        Collection<WebQueryResult> list = service.executeNativeSql(sql, 1);
        if (!list.isEmpty()) {
            WebQueryResult wqr = list.iterator().next();
            if (wqr.get1() != null) {
                return wqr.get1() + "#" + wqr.get2();
            }
        }
        return "";
    }

    public String getCodeByMedPolicyOmc(Long typeId, HttpServletRequest request) throws Exception {
        IPatientService service = Injection.find(request).getService(IPatientService.class);
        return service.getCodeByMedPolicyOmc(typeId);

    }

    public String getInfoVocForFond(String passportType, String address, String policy, HttpServletRequest request) throws Exception {
        IPatientService service = Injection.find(request).getService(IPatientService.class);
        return service.getInfoVocForFond(passportType, address, policy);

    }

    public boolean updateDataByFond(Long patientId, String fiodr
            , String document, String policy, String address
            , boolean isUpdatePatient, boolean isUpdatePolicy
            , boolean isUpdateDocument, boolean isUpdateAddress, boolean isUpdateAttachment, HttpServletRequest request) throws Exception {
        IPatientService service = Injection.find(request).getService(IPatientService.class);
        String username = LoginInfo.find(request.getSession(true)).getUsername();
        return service.updateDataByFond(username, patientId, fiodr, document, policy, address, isUpdatePatient, isUpdatePolicy
                , isUpdateDocument, isUpdateAddress, isUpdateAttachment);

    }

    public Object checkPatientByPolicy(Long patientId, String series, String number, HttpServletRequest request) throws Exception {
        IPatientService service = Injection.find(request).getService(IPatientService.class);
        return FondWebService.checkPatientByMedPolicy(request, getPatientInfo(patientId, service), series, number, patientId);
    }

    public Object checkPatientByCommonNumber(Long paptientId, String commonNumber, HttpServletRequest request) throws Exception {
        IPatientService service = Injection.find(request).getService(IPatientService.class);
        return FondWebService.checkPatientByCommonNumber(request, getPatientInfo(paptientId, service), commonNumber, paptientId);
    }

    public Object checkPatientBySnils(Long patientId, String snils, HttpServletRequest request) throws Exception {
        if (isNotEmpty(snils)) {
            IPatientService service = Injection.find(request).getService(IPatientService.class);
            return FondWebService.checkPatientBySnils(request, getPatientInfo(patientId, service), snils, patientId);
        } else {
            return "Не заполнено поле \"СНИЛС\"";
        }
    }

    public Object checkPatientByFioDr(Long patientId, String lastname, String firstname
            , String middlename, String birthday, HttpServletRequest request) throws Exception {
        IPatientService service = Injection.find(request).getService(IPatientService.class);
        return FondWebService.checkPatientByFioDr(request, getPatientInfo(patientId, service), lastname, firstname
                , middlename, birthday, patientId);
    }

    public Object checkPatientByDocument(Long patientId, Long typeId, String series
            , String number, HttpServletRequest request) throws Exception {
        IPatientService service = Injection.find(request).getService(IPatientService.class);
        String passportOmcCode = service.getOmcCodeByPassportType(typeId);
        return FondWebService.checkPatientByDocument(request, getPatientInfo(patientId, service), passportOmcCode, series, number, patientId);
    }

    private PatientForm getPatientInfo(Long patientId, IPatientService patientService) {
        return (patientId != null && patientId > 0L) ? patientService.getPatientById(patientId) : null;
    }

    public String getAge(String date) {
        try {
            java.sql.Date birthday = DateFormat.parseSqlDate(date);
            java.sql.Date currentDate = new java.sql.Date(new java.util.Date().getTime());
            return AgeUtil.getAgeCache(currentDate, birthday, 3);
        } catch (Exception e) {
            LOG.error("Cant get age", e);
            return "";
        }
    }

    public String getDoubleByFio(String id, String lastname, String firstname, String middlename,
                                 String snils, String aBirthday, String passportType, String passportNumber, String passportSeries
            , String action, HttpServletRequest request) throws ParseException, NamingException {

        IPatientService service = Injection.find(request).getService(IPatientService.class);
        return service.getDoubleByBaseData(id, lastname, firstname, middlename, snils, aBirthday, passportType, passportNumber, passportSeries, action, true);
    }

    private void createAdminChangeMessageByPatient(Long smo, String type, String textInfo
            , HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        String login = LoginInfo.find(request.getSession(true)).getUsername();
        sql.append("insert into AdminChangeJournal ( patient, createDate, createTime")
                .append(", createUsername, ctype,  annulRecord) ")
                .append("values (").append(smo).append(", current_date, current_time, '")
                .append(login)
                .append("', '")
                .append(type)
                .append("','")
                .append(textInfo)
                .append("')");
        service.executeUpdateNativeSql(sql.toString());

    }

    public void movePatientDoubleData(Long idNew, Long idOld, HttpServletRequest request) throws Exception {
        IPatientService service = Injection.find(request).getService(IPatientService.class);
        service.movePatientDoubleData(idNew, idOld);
        createAdminChangeMessageByPatient(idNew, "MOVE_PATIENT_DOUBLE_DATA", "Перенесены данные из персоны " + idOld + " в " + idNew, request);
    }

    public String addPatient(String lastname, String firstname, String middlename, String birthday, Long sexId
            , Long socialStatusId, String snils, HttpServletRequest request) throws Exception {
        IPatientService service = Injection.find(request).getService(IPatientService.class);
        SnilsStringValidator val = new SnilsStringValidator();
        val.validate(snils, null, request);
        return service.addPatient(lastname, firstname, middlename, birthday, sexId, socialStatusId, snils);
    }

    public void setAddParamByMedCase(String param, Long medcase, Long status, HttpServletRequest request) throws Exception {
        IPatientService service = Injection.find(request).getService(IPatientService.class);
        service.setAddParamByMedCase(param, medcase, status);
    }

    public boolean checkSLSonDepartment(Long medcaseId, HttpServletRequest request) throws Exception {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);

        Collection<WebQueryResult> res = service.executeNativeSql("select count(c.id) from Certificate c " +
                "where dtype='ShortConfinementCertificate' and medcase_id = " + medcaseId);
        int count = !res.isEmpty() ? Integer.parseInt(res.iterator().next().get1().toString()) : 0;

        if (count == 0) {
            res = service.executeNativeSql("select department_id from medcase where id =" + medcaseId);
            int id = !res.isEmpty() ? Integer.parseInt(res.iterator().next().get1().toString()) : 0;
            res = service.executeNativeSql("select keyvalue from softconfig  where key = 'BirthDepId'");
            int depId = !res.isEmpty() ? Integer.parseInt(res.iterator().next().get1().toString()) : 0;
            return depId == id;
        }
        return false;
    }

    /**
     * Получить статус листка наблюдения #171
     *
     * @param patientId Patient.id
     * @param dType     String Новрождённые/Беременные
     * @return String Статус (0 - нет никаких, можно открыть
     * 1 - есть открытый: можно просмотреть, закрыть, добавить всё
     * 2 - есть закрытый: можно просмотреть, открыть)
     */
    public String getObservationSheetStatus(String patientId, String dType, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        Collection<WebQueryResult> l = service.executeNativeSql("select min(case when finishdate is null then '1' else '2' end)" +
                " from observationsheet" +
                " where patient_id=" + patientId + " and dtype='" + dType + "'");
        return l.isEmpty() || l.iterator().next().get1() == null ? "0" :
                l.iterator().next().get1().toString();
    }

    /**
     * Получить id текущего открытого листка наблюдения #171
     *
     * @param patientId Patient.id
     * @param dType     String Новрождённые/Беременные
     * @return String OnservationSheet.id
     */
    public String getObservationSheetOpenedId(String patientId, String dType, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        Collection<WebQueryResult> l = service.executeNativeSql("select id" +
                " from observationsheet" +
                " where patient_id='" + patientId + "' and finishdate is null and dtype='" + dType + "'");
        return l.isEmpty() || l.iterator().next().get1() == null ? "0" :
                l.iterator().next().get1().toString();
    }

    /**
     * Получить текущую рабочую функцию
     *
     * @param request HttpServletRequest
     * @return Long WorkFunction.id
     */
    private Long getCurrentWorkFunction(HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        long wf;
        try {
            wf = Long.parseLong(service.executeNativeSql("select wf.id from workfunction wf left join secuser su on su.id=wf.secuser_id where su.login = '" + LoginInfo.find(request.getSession(true)).getUsername() + "'").iterator().next().get1().toString());
        } catch (Exception e) {
            LOG.error("Ошибка нахождения раб. фунции = ", e);
            throw new IllegalDataException(e.toString());
        }
        return wf;
    }

    /**
     * Открыть лист наблюдения #171
     *
     * @param patientId Patient.id
     * @param dType     String Новрождённые/Беременные
     * @return String 1 - ok, 0 - error
     */
    public String openObservSheet(String patientId, String dType, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        String status = getObservationSheetStatus(patientId, dType, request);
        if (status.equals("0") || status.equals("2")) {
            service.executeNativeSql("insert into observationsheet(dtype,createusername,startdate,specialiststart_id,patient_id) values('"
                    + dType + "','" + LoginInfo.find(request.getSession(true)).getUsername() + "',current_date," + getCurrentWorkFunction(request) +
                    ", " + patientId + ") returning id");
            return "1";
        }
        return "0";
    }

    /**
     * Закрыть лист наблюдения #171
     *
     * @param patientId   Patient.id
     * @param observResId Результат наблюдения
     * @param dtype       String Новрождённые/Беременные
     * @return String 1 - ok, 0 - error
     */
    public String closeObservSheet(String patientId, String observResId, String dtype, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        String status = getObservationSheetStatus(patientId, dtype, request);
        if (status.equals("1")) {
            service.executeUpdateNativeSql("update observationsheet set editusername='" + LoginInfo.find(request.getSession(true)).getUsername()
                    + "',finishdate=current_date,specialistfin_id='" + getCurrentWorkFunction(request) + "',observresult_id='" + observResId
                    + "' where patient_id='" + patientId + "' and finishdate is null and dtype='" + dtype + "'");
            return "1";
        }
        return "0";
    }

    /**
     * Получить листки наблюдений персоны #171
     *
     * @param patientId Sls.id or Patient.id
     * @param dtype     String Новрождённые/Беременные
     * @return String json Листки наблюдений персоны
     */
    public String selectObservSheetPatient(Long patientId, String dtype, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        JSONArray res = new JSONArray();
        String sql = "select o.id as oId, to_char(o.startdate,'dd.mm.yyyy') as std " +
                ",vwf.name||' '||wpat.lastname||' '||wpat.firstname||' '||wpat.middlename as  wp " +
                ",to_char(o.finishdate,'dd.mm.yyyy') as fd " +
                ",vwf2.name||' '||wpat2.lastname||' '||wpat2.firstname||' '||wpat2.middlename as  wp2 " +
                ", case when " +
                "count(case when o.finishdate is null then '1' else null end)>0 " +
                "then null " +
                "else case when max(o.finishdate)=min(o.startdate) then '1' else max(o.finishdate)-min(o.startdate)+1 end end as dlit " +
                ",vr.name as vrn " +
                "from  observationsheet o " +
                "left join workfunction wf on wf.id=o.specialiststart_id " +
                "left join workfunction wf2 on wf2.id=o.specialistfin_id " +
                "left join worker w on w.id=wf.worker_id " +
                "left join worker w2 on w2.id=wf2.worker_id " +
                "left join vocworkfunction vwf on vwf.id=wf.workfunction_id " +
                "left join vocworkfunction vwf2 on vwf2.id=wf2.workfunction_id " +
                "left join patient wpat on wpat.id=w.person_id " +
                "left join patient wpat2 on wpat2.id=w2.person_id " +
                "left join vocobservationresult vr on vr.id=o.observresult_id " +
                "where o.patient_id= " + patientId +
                " and o.dtype='" + dtype + "' " +
                "group by o.id,vwf.name,vwf2.name,wpat.id,wpat2.id,vr.name " +
                "order by o.id";
        Collection<WebQueryResult> list = service.executeNativeSql(sql);
        for (WebQueryResult w : list) {
            JSONObject o = new JSONObject();
            o.put("id", w.get1())
                    .put("startDate", w.get2())
                    .put("wpStart", w.get3())
                    .put("finishDate", w.get4())
                    .put("wpFin", w.get5())
                    .put("dlit", w.get6())
                    .put("res", w.get7());
            res.put(o);
        }
        return res.toString();
    }

    /**
     * Получить тип протокола
     *
     * @param protocolCode vocTypeProtocol.code
     * @return String Статус vocTypeProtocol.name
     */
    public String getNameTypeProtocol(String protocolCode, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        Collection<WebQueryResult> l = service.executeNativeSql("select id,name from vocTypeProtocol where code='" + protocolCode + "'");
        JSONObject o = new JSONObject();
        if (!l.isEmpty()) {
            WebQueryResult w = l.iterator().next();
            o.put("id", w.get1())
                    .put("name", w.get2());
        }
        Collection<WebQueryResult> l2 = service.executeNativeSql("select keyvalue from softconfig where key='" + protocolCode + "'");
        if (!l2.isEmpty()) {
            WebQueryResult w = l2.iterator().next();
            o.put("tmpl", w.get1());
        }
        return o.toString();
    }

    /**
     * Получить id и телефон пациента по его визиту #181
     *
     * @param visitId medcase.id
     * @return Json id и телефон пациента
     */
    public String getIdPhoneByVisitId(Long visitId, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select p.id,coalesce(p.phone,'') from patient p" +
                " left join medcase v on v.patient_id=p.id where v.id=" + visitId);
        JSONObject o = new JSONObject();
        if (!list.isEmpty()) {
            WebQueryResult w = list.iterator().next();
            o.put("id", w.get1())
                    .put("phone", w.get2());
        }
        return o.toString();
    }

    /**
     * Получить id существующего в СПО/СЛС акта РВК + dtype medcase-а
     *
     * @param medcaseId medcase.id
     * @return json
     */
    public String getIfRVKAlreadyExists(Long medcaseId, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select a.id,mc.dtype from actrvk a" +
                " left join medcase mc on mc.id=a.medcase_id " +
                " where a.medcase_id=" + medcaseId + " or a.medcase_id=ANY(select id from medcase where" +
                " parent_id=(select parent_id from medcase where id=" + medcaseId + "))");
        JSONObject o = new JSONObject();
        if (!list.isEmpty()) {
            WebQueryResult w = list.iterator().next();
            o.put("id", w.get1())
                    .put("dtype", w.get2());
        }
        return o.toString();
    }

    /**
     * Очистить таблицу с импортированными анализами
     *
     * @return Сообщение
     */
    public String deleteAllInmpirtedAnalyses(HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        String msg = "Удалено " + service.executeUpdateNativeSql("delete from ExternalCovidAnalysis") + " результатов";
        service.executeNativeSql("select 1"); //для персиста
        return msg;
    }

    /**
     * Получить логин, если пользователь зарегистрирован в системе
     *
     * @param patientId Patient.id
     * @return html
     */
    public String checkLogin(Long patientId, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        String sql = "select su.login from SecUser su" +
                " left join WorkFunction wf on su.id=wf.secUSer_id" +
                " left join Worker w on wf.worker_id=w.id" +
                " where w.person_id=" + patientId;
        Collection<WebQueryResult> l = service.executeNativeSql(sql);
        return l.isEmpty() ? "" : "<div>Логин в МедОСе: <b>" + l.iterator().next().get1().toString() + "</b></div>";
    }

    /**
     * Проверить выгрузку карты с указанием периода (первично/повторно/при выписке)
     *
     * @param cardId Covid19.id
     * @param number String Наименование
     * @return 0 - ещё не была выгружена, -1 - уже была
     */
    public String markCovidAsSentNumberCheck(Long cardId, String number, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        Collection<WebQueryResult> l = service.executeNativeSql("select id from Covid19 where id=" + cardId + " and "
                + "export" + number + "Date is not null");
        return l.isEmpty() ? "" : "" + "-1";
    }

    /**
     * Проверить выгрузку карты с указанием периода (первично/повторно/при выписке)
     *
     * @param cardId Covid19.id
     * @param number String Наименование
     * @return Сообщение
     */
    public String markCovidAsSentNumber(Long cardId, String number, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        String username = LoginInfo.find(request.getSession(true)).getUsername();
        return "обновлено " + service.executeUpdateNativeSql("update Covid19 set export"
                + number + "Date=current_date, export" + number + "Time=current_time" +
                ",export" + number + "Username='" + username + "' where id=" + cardId) + " карт";
    }

    /**
     * брать отметку о выгрузке карты
     *
     * @param cardId Covid19.id
     * @param number String Наименование
     */
    public void markCovidAsUnSent(Long cardId, String number, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("update Covid19 set export"
                + number + "Date=null, export" + number + "Time=null" +
                ",export" + number + "Username=null where id=" + cardId);
    }

    /**
     * Получить должность
     *
     * @param vwfId VocWorkfunction.id
     * @return VocWorkfunction.name
     */
    public String getVocWorkfunctionName(Long vwfId, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        Collection<WebQueryResult> l = service.executeNativeSql("select name from vocworkfunction where id=" + vwfId);
        return l.isEmpty() ? "" : l.iterator().next().get1().toString();
    }
}