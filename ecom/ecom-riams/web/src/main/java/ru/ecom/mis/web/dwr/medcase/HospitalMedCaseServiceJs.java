package ru.ecom.mis.web.dwr.medcase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.script.IScriptService;
import ru.ecom.mis.ejb.service.IPromedExportService;
import ru.ecom.mis.ejb.service.contract.IContractService;
import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.mis.ejb.service.worker.IWorkerService;
import ru.ecom.poly.web.dwr.TicketServiceJs;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.tags.helper.RolesHelper;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Сервис по случаю лечения в стационаре
 *
 * @author Tkacheva Sveltana
 */
public class HospitalMedCaseServiceJs {

    public String exportToPromed(Long slsId, HttpServletRequest aRequest) throws NamingException {
        String guid = Injection.find(aRequest).getService(IPromedExportService.class).exportHospitalById(slsId);
        return guid == null ? "Отправка выключена" : "success: " + guid;
    }

    /**
     * Календарь с предварительной госпитализацией
     *
     * @param aYear       the a year
     * @param aMonth      the a month
     * @param aDepartment the a department
     * @param isOpht      the is opht
     * @param aRequest    the a request
     * @return the pre hosp calendar
     * @throws NamingException the naming exception
     */

    public String getPreHospCalendar(Integer aYear, Integer aMonth, Long aDepartment, Boolean isOpht, HttpServletRequest aRequest) throws NamingException {
        JSONArray preHosps = new JSONArray(getPreHospByMonth(aYear, aMonth, aDepartment, Boolean.TRUE.equals(isOpht), aRequest));
        StringBuilder res = new StringBuilder();
        res.append("<form name='frmDate' id='frmDate' action='javascript:step5()'>");
        res.append("<span class = 'spanNavigMonth'>");
        if (aMonth == 1) {
            res.append("<a href=\"javascript:showPreHospCalendar('")
                    .append(getMonth(12, false))
                    .append("','").append(aYear - 1);
            res.append("');\">")
                    .append("<-")
                    .append("</a> ");
        } else {
            res.append("<a href=\"javascript:showPreHospCalendar('")
                    .append(getMonth(aMonth - 1, false))
                    .append("','").append(aYear);
            res.append("');\">").append("<-")
                    .append("</a> ");
        }
        res.append(" ").append(getMonth(aMonth, true).toUpperCase()).append(" ").append(aYear);
        if (aMonth == 12) {
            res.append(" <a href=\"javascript:showPreHospCalendar('")
                    .append(getMonth(1, false))
                    .append("','").append(aYear + 1);
            res.append("');\">")
                    .append("-></a>");
        } else {
            res.append("<a href=\"javascript:showPreHospCalendar('")
                    .append(getMonth(aMonth + 1, false))
                    .append("','").append(aYear);
            res.append("');\">-></a> ");
        }
        res.append("</span>");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, aYear);
        aMonth--;
        cal.set(Calendar.MONTH, aMonth);
        cal.set(Calendar.DATE, 1);
        int day = 1;
        int oldday = 0;
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0) {
            week = 7;
        }
        week--;

        res.append("<table class='listDates'>");
        res.append("<tr>")
                .append("<th>Пон</th>")
                .append("<th>Вт</th>")
                .append("<th>Ср</th>")
                .append("<th>Чет</th>")
                .append("<th>Пят</th>")
                .append("<th>Суб</th>")
                .append("<th>Вос</th>")
                .append("</tr>");

        res.append("<tr>");
        boolean isDepOpht = checkIsDepOpht(aDepartment, aRequest).length()>0 && !isOpht; //выбрано ли офтальмологическое отделение (не ангиогенез)
        res.append(getFreeDay(0, week, false, 1, aMonth, aYear, isDepOpht, aRequest));
        for (int i = 0; i < preHosps.length(); i++) {
            JSONObject preHosp = preHosps.getJSONObject(i);
            int monthDate = preHosp.getInt("monthDate");
            int amount = preHosp.getInt("amount");
            oldday = monthDate;
            res.append(getFreeDay(day, oldday, true, week, aMonth, aYear, isDepOpht, aRequest));
            week = (week + oldday - day) % 7;
            if (week == 0) week = 7;
            week++;
            if (week > 7) {
                res.append("</tr><tr>");
            }
            boolean isBusy = amount == 0;

            res.append("<td id='tdDay").append(monthDate).append("'");
            res.append("onclick=\"showPreHospByDate(this,'").append(preHosp.getString("calendarDate"));
            res.append("',").append(isOpht).append(")\"");
            res.append(" class='").append(isBusy ? "busyDay" : "visitDay").append("'>");
            res.append(isBusy ? "" : "<b>").append(monthDate);
            res.append(" <br>(").append(amount).append(")");
            res.append(isBusy ? "" : "</b>").append("</td>");
            day = oldday + 1;
        }
        int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        res.append(getFreeDay(day, max + 1, true, week, aMonth, aYear, isDepOpht, aRequest));
        if (oldday > 0) {
            week = (week + max - day) % 7;
            if (week == 0) week = 7;
        } else {
            week = (week + max) % 7;
            if (week == 0) week = 7;
        }
        week++;
        res.append(getFreeDay(week, 7, false, 1, aMonth, aYear, isDepOpht, aRequest));
        res.append("</tr>");

        res.append("</table></form>");
        return res.toString();
    }

    private String getMonth(int aMonth, boolean aFullname) {
        String month;
        switch (aMonth) {
            case 1:
                month = aFullname ? "Январь" : "01";
                break;
            case 2:
                month = aFullname ? "Февраль" : "02";
                break;
            case 3:
                month = aFullname ? "Март" : "03";
                break;
            case 4:
                month = aFullname ? "Апрель" : "04";
                break;
            case 5:
                month = aFullname ? "Май" : "05";
                break;
            case 6:
                month = aFullname ? "Июнь" : "06";
                break;
            case 7:
                month = aFullname ? "Июль" : "07";
                break;
            case 8:
                month = aFullname ? "Август" : "08";
                break;
            case 9:
                month = aFullname ? "Сентябрь" : "09";
                break;
            case 10:
                month = aFullname ? "Октябрь" : "10";
                break;
            case 11:
                month = aFullname ? "Ноябрь" : "11";
                break;
            case 12:
                month = aFullname ? "Декабрь" : "12";
                break;
            default:
                month = aFullname ? "" : "" + aMonth;
                break;
        }
        return month;
    }

    private StringBuilder getFreeDay(int aFrom, int aTo, boolean aView, int aWeek, int aMonth, int aYear, boolean isDepOpht, HttpServletRequest aRequest) throws NamingException {
        StringBuilder res = new StringBuilder();
        for (int i = aFrom; i < aTo; i++) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, aYear);
            cal.set(Calendar.MONTH, aMonth);
            cal.set(Calendar.DATE, i);
            String calendarDate = new SimpleDateFormat("dd.MM.yyyy").format(cal.getTime());
            //если офтальм. отделение и день закрыт
            String cssClassName = isDepOpht && !checkDayOphtOpened(calendarDate, aRequest)?
                    "closedDay" : "freeDay";
            aWeek = aWeek % 7;
            if (aWeek == 0) aWeek = 7;
            aWeek++;

            if (aWeek > 7) {
                res.append("</tr><tr>");
                aWeek = 1;
            }
            if (aView) {
                res.append("<td id='tdDay")
                        .append(getMonth(i, false)).append("'")
                        .append(" onclick=\"if (typeof setChoosenDate !== 'undefined') setChoosenDate('").append(calendarDate).append("');\"")
                        .append("class='").append(cssClassName).append("'").append(">").append(i).append("</td>");
            } else {
                res.append("<td>&nbsp;</td>");
            }
        }
        return res;
    }

    /*Количество пред. госпитализаций за месяц*/
    public String getPreHospByMonth(Integer aYear, Integer aMonth, Long aDepartment, boolean isOpht, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String date = isOpht ? "createDate" : "dateFrom";
        String sql = "select to_char(pre." + date + ",'dd.MM.yyyy'), cast(to_char(pre." + date + ",'dd') as int) as dat, count(pre.id) as cnt" +
                " from workcalendarhospitalbed pre" +
                " left join voceye e on e.id=pre.eye_id" +
                " where to_char(pre." + date + ",'MM.yyyy')='" + (aMonth > 9 ? aMonth : "0" + aMonth) + "." + aYear + "'" +
                (aDepartment != null && aDepartment > 0L ? " and pre.department_id=" + aDepartment : "") +
                (isOpht ? " and e.id is not null " : "") +
                " group by pre." + date +
                " order by pre." + date;
        return service.executeNativeSqlGetJSON(new String[]{"calendarDate", "monthDate", "amount"}, sql, 31);
    }

    /**
     * Информация о направлении на госпитализацию для автоматического заполнения госпитализации
     */
    public String getInfoByPreHosp(Long aPreHospId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String sql = "select" +
                " vss.id as vssId, vss.name as vssName" +
                " ,oMl.id as orderId, oml.name as orderName" +
                " ,ml.id as depId, ml.name as depName" +
                " ,mkb.id as dsId,mkb.code||' '||mkb.name as dsName,wchb.diagnosis as diagnos" +
                " from WorkCalendarHospitalBed wchb" +
                " left join vocservicestream vss on vss.id=wchb.servicestream_id" +
                " left join mislpu oMl on oMl.id=wchb.orderlpu_id" +
                " left join VocIdc10 mkb on mkb.id=wchb.idc10_id" +
                " left join MisLpu ml on ml.id=wchb.department_id" +
                " where wchb.id =" + aPreHospId;
        return service.executeNativeSqlGetJSON(new String[]{"serviceStream", "serviceStreamName", "orderLpu", "orderLpuName"
                , "department", "departmentName", "orderMkb", "orderMkbName", "orderDiagnos"}, sql, 1);

    }

    public boolean isAbortRequiredByOperation(Long aMedServiceId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String ret;
        try {
            ret = service.executeNativeSql("select case when isAbortRequired='1' then '1' else '0' end from medservice where id=" + aMedServiceId).iterator().next().get1().toString();
        } catch (Exception e) {
            ret = null;
        }
        return "1".equals(ret);
    }


    public String getDiagnosisAndModelByVMPMethod(Long aMethodId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return service.executeNativeSqlGetJSON(new String[]{"diagnosis", "patientModel"}, "select diagnosis as f1, patientModel as f2 from vocmethodhighcare where id=" + aMethodId, 1);

    }

    public String getMedcaseCost(String aDateFrom, String aDateTo, String aType, String aLpuCode, String aReportType, HttpServletRequest aRequest) throws NamingException {
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
        return service.makeReportCostCase(aDateFrom, aDateTo, aType, aLpuCode, aReportType);
    }

    public String getAllServicesByMedCase(Long aMedcaseId, HttpServletRequest aRequest) throws NamingException {
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
        return service.getAllServicesByMedCase(aMedcaseId);
    }

    public Long checkIsEndoscopyMethod(Long aMethodId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> l = service.executeNativeSql("select case when endoscopyUse='1' then '1' else '0' end from VocOperationMethod where id=" + aMethodId);
        return Long.valueOf(l.isEmpty() ? "0" : l.iterator().next().get1().toString());
    }

    public String getCriminalPhoneMessageByTrauma(Long aMedCase, Long aDeathReason, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> l = service.executeNativeSql("select id from VocDeathReason where id=" + aDeathReason + " and code='9'");
        if (!l.isEmpty()) {
            l = service.executeNativeSql("select to_char(pm.WhenDateEventOccurred,'dd.mm.yyyy'),pm.Place,substring(pm.Comment,0,200) from PhoneMessage pm where pm.dtype='CriminalPhoneMessage' and pm.medcase_id=" + aMedCase);
            if (!l.isEmpty()) {
                WebQueryResult wqr = l.iterator().next();
                return wqr.get1() + "@#@" + wqr.get2() + "@#@" + wqr.get3() + "@#@";
            }
        }
        return null;
    }

    private Float parseFloat(String value) throws ParseException {
        value = value.replace(",", ".");
        if (value == null || value.length() == 0)
            return null;
        try {
            return Float.valueOf(value);
        } catch (NumberFormatException e) {
            throw new ParseException("Некорректное значение для вещественного числа!", 0);
        }
    }

    private Integer parseInt(String value) throws ParseException {
        if (value == null || value.length() == 0)
            return null;
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            throw new ParseException("Некорректное значение для целого числа!", 0);
        }
    }

    /**
     * Cоздать темп. лист
     *
     * @param aMedCase  DepMedCase.id
     * @param aTempData json с данными
     * @return результат insert
     */
    public String createTemperatureCurve(Long aMedCase, String aTempData, HttpServletRequest aRequest) throws NamingException, ParseException {
        String identTempStr = getSettingsKeyValueByKey("identTemp", aRequest);
        if (identTempStr.length() == 0)
            identTempStr = "19";
        Long identTemp = Long.valueOf(identTempStr);

        Integer daysToFinish = 2;

        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        JSONObject obj = new JSONObject(aTempData);
        String takingDate = getString(obj, "takingDate");
        String degreeStr = getString(obj, "degree");
        float degree = parseFloat(degreeStr);
        String dayTime = getString(obj, "dayTime");

        String sql = "insert into temperatureCurve (takingDate, degree, daytime_id, medcase_id,date,time,username) values (" +
                "to_date('" + takingDate + "','dd.MM.yyyy')," + degree + "," + dayTime +
                ", " + aMedCase + ",current_date,current_time,'" + LoginInfo.find(aRequest.getSession(true)).getUsername() + "') returning id";
        String tempCurveId = "";
        Collection<WebQueryResult> res = service.executeNativeSql(sql);
        for (WebQueryResult wqr : res) {
            tempCurveId = wqr.get1().toString();
        }
        if (!tempCurveId.equals("")) {
            float highTemp = Float.parseFloat(getDefaultParameterByConfig("highTemp", "37.5", aRequest).toString());
            if (degree >= highTemp && checkThirdDayHighTemp(aMedCase, takingDate, highTemp, aRequest))
                addIdentityPatient(aMedCase, true, identTemp, daysToFinish, Long.valueOf(tempCurveId), aRequest);
            else
                check2DaysNormalTempAndClose(aMedCase, takingDate, highTemp, aRequest);
        }
        return tempCurveId.equals("") ? "" : "1";
    }

    /**
     * Проверить, 2й ли день у пациента нормальная температура и закрыть браслет, если есть
     *
     * @param aMedCase   DepMedCase.id
     * @param takingDate Дата регистрации темп. листа, с которой надо сравнивать
     * @param highTemp   Нижний предел высокой температуры
     */
    private void check2DaysNormalTempAndClose(Long aMedCase, String takingDate, float highTemp, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        sql.append("select sum(case when y1.degree<").append(highTemp)
                .append(" and y2.degree<").append(highTemp)
                .append(" then 1 else 0 end) as yC")
                .append(" ,sum(case when befY1.degree<").append(highTemp)
                .append(" and befY2.degree<").append(highTemp)
                .append(" then 1 else 0 end) as befYC")
                .append(" from temperaturecurve y1")
                .append(" inner join temperaturecurve y2 on y1.medcase_id=y2.medcase_id and y1.takingdate=y2.takingdate")
                .append(" inner join temperaturecurve befY1 on befY1.medcase_id=y1.medcase_id and befY1.takingdate=y1.takingdate-1")
                .append(" inner join temperaturecurve befY2 on befY2.medcase_id=y2.medcase_id and befY2.takingdate=y2.takingdate-1")
                .append(" inner join vocdaytime vy1 on vy1.id=y1.daytime_id and vy1.code='1'")
                .append(" inner join vocdaytime vy2 on vy2.id=y2.daytime_id and vy2.code='2'")
                .append(" inner join vocdaytime vbefY1 on vbefY1.id=befY1.daytime_id and vbefY1.code='1'")
                .append(" inner join vocdaytime vbefY2 on vbefY2.id=befY2.daytime_id and vbefY2.code='1'")
                .append(" where y1.medcase_id = ").append(aMedCase)
                .append(" and vy1.id is not null and vy2.id is not null")
                .append(" and befY1.id is not null and befY2.id is not null")
                .append(" and y1.takingdate=to_date('").append(takingDate).append("','dd.mm.yyyy')-1");

        Collection<WebQueryResult> beforeTemp = service.executeNativeSql(sql.toString());
        Long cipId = checkExistBraceletHighTempOpened(aMedCase, aRequest);
        if (!beforeTemp.isEmpty() && cipId > 0) { //закрываю браслет
            String login = LoginInfo.find(aRequest.getSession(true)).getUsername();
            WebQueryResult wqr = beforeTemp.iterator().next();
            if (wqr.get1() != null && wqr.get2() != null &&
                    Integer.parseInt(wqr.get1().toString()) > 0 && Integer.parseInt(wqr.get2().toString()) > 0)
                service.executeUpdateNativeSql("update coloridentitypatient set finishdate=current_date, finishtime=current_time,editusername='"
                        + login + "' where id=" + cipId);
        }
    }

    /**
     * Проверить, 3й ли день у пациента высокая температура
     *
     * @param aMedCase   DepMedCase.id
     * @param takingDate Дата регистрации темп. листа, с которой надо сравнивать
     * @param highTemp   Нижний предел высокой температуры
     * @return true, если да
     */
    private boolean checkThirdDayHighTemp(Long aMedCase, String takingDate, float highTemp, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();

        sql.append("select sum(case when y.degree>=").append(highTemp)
                .append(" then 1 else 0 end) as yC")
                .append(",sum(case when befY.degree>=").append(highTemp).append(" then 1 else 0 end) as befYC")
                .append(" from temperaturecurve y")
                .append(" left join temperaturecurve befY on befY.medcase_id=y.medcase_id and befY.takingdate=y.takingdate-1")
                .append(" where y.medcase_id = ").append(aMedCase)
                .append(" and y.takingdate=to_date('").append(takingDate).append("','dd.mm.yyyy')-1");

        Collection<WebQueryResult> beforeTemp = service.executeNativeSql(sql.toString());
        if (!beforeTemp.isEmpty() && checkExistBraceletHighTempOpened(aMedCase, aRequest) == 0L) {
            WebQueryResult wqr = beforeTemp.iterator().next();
            if (wqr.get1() != null && wqr.get2() != null &&
                    Integer.parseInt(wqr.get1().toString()) > 0 && Integer.parseInt(wqr.get2().toString()) > 0)
                return true;
        }
        return false;
    }

    /**
     * Проверить, есть ли открытый браслет
     *
     * @param aMedCase DepMedCase.id
     * @return true, если есть открытый браслет
     */
    private Long checkExistBraceletHighTempOpened(Long aMedCase, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select cip.id from coloridentitypatient cip" +
                " left join voccoloridentitypatient vip on vip.id=cip.voccoloridentity_id" +
                " left join medcase_coloridentitypatient mcip on mcip.colorsidentity_id=cip.id" +
                " left join medcase slo on slo.id=" + aMedCase +
                " left join medcase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'" +
                " where vip.code='high_temp' and (cast ((cip.finishdate||' '||cip.finishtime) as TIMESTAMP) > current_timestamp" +
                " or finishdate is null) and mcip.medcase_id=sls.id");
        return list.isEmpty() ? 0L :
                Long.valueOf(list.iterator().next().get1().toString());
    }

    public String getServiceStreamAndMkbByMedCase(Long aMedCase, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        sql.append("select smc.serviceStream_id as ssId, vss.name as vssname")
                .append(", max(case when vpd.code='1' and vdrt.code='3' then mkb.id else null end) as mkbId")
                .append(", max(case when vpd.code='1' and vdrt.code='3' then mkb.code||' '||mkb.name else null end) as mkbname")
                .append(", max(case when vpd.code='1' and vdrt.code='4' then mkb.id else null end) as mkbid1")
                .append(", max(case when vpd.code='1' and vdrt.code='4' then mkb.code||' '||mkb.name else null end) as mkbname1")
                .append(",smc.dtype as dtype , vss.code as vssCode")
                .append(" from medcase smc")
                .append(" left join Diagnosis d on d.medCase_id=smc.id")
                .append(" left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id")
                .append(" left join VocDiagnosisRegistrationType vdrt on vdrt.id=d.registrationType_id")
                .append(" left join VocIdc10 mkb on mkb.id=d.idc10_id")
                .append(" left join VocServiceStream vss on vss.id=smc.serviceStream_id")
                .append(" where smc.id='").append(aMedCase).append("' group by smc.serviceStream_id,vss.name,vss.code, smc.dtype");
        List<Object[]> resL = service.executeNativeSqlGetObj(sql.toString());
        JSONObject ret = new JSONObject();
        if (!resL.isEmpty()) {
            Object[] obj = resL.get(0);
            ret.put("medcaseType", obj[6]);
            if (obj[0] != null) { //поток обслуживания
                ret.put("serviceStreamId", obj[0]);
                ret.put("serviceStreamName", obj[1]);
                ret.put("serviceStreamCode", obj[7]);
            }
            ret.put("mkbId", obj[2] != null ? obj[2] : (obj[4] != null ? obj[4] : ""));
            ret.put("mkbName", obj[2] != null ? obj[3] : (obj[4] != null ? obj[5] : ""));
        }
        return ret.toString();
    }

    public String getServiceByMedCase(Long aMedCase, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        StringBuilder res = new StringBuilder();
        sql.append("select smc.medservice_id,ms.code||' '||ms.name as serviceName")
                .append(",smc.workfunctionexecute_id,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as wfinfo")
                .append(",smc.idc10_id,mkb.code||' '||mkb.name as mkb")
                .append(",to_char(coalesce(smc.datestart,vis.datestart),'dd.mm.yyyy') as datestart,coalesce(smc.medserviceAmount,1)")
                .append(",coalesce(smc.serviceStream_id,vis.serviceStream_id),coalesce(vss.name, visSS.name) as vssname")
                .append(" from medcase smc")
                .append(" left join medcase vis on vis.id=smc.parent_id")
                .append(" left join MedService ms on ms.id=smc.medservice_id")
                .append(" left join WorkFunction wf on wf.id=smc.workFunctionExecute_id")
                .append(" left join Worker w on w.id=wf.worker_id")
                .append(" left join Patient wp on wp.id=w.person_id")
                .append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id")
                .append(" left join VocIdc10 mkb on mkb.id=smc.idc10_id")
                .append(" left join VocServiceStream vss on vss.id=smc.serviceStream_id")
                .append(" left join VocServiceStream visSs on visSs.id=vis.serviceStream_id")
                .append(" where smc.parent_id='").append(aMedCase).append("' and smc.dtype='ServiceMedCase' order by smc.id");
        List<Object[]> resL = service.executeNativeSqlGetObj(sql.toString());
        for (Object[] objects : resL) {
            for (int j = 0; j < 10; j++) {
                res.append(objects[j]).append("@#@");
            }
            res = new StringBuilder(res.length() > 0 ? res.toString().trim().substring(0, res.length() - 3) : "");
            res.append("#@#");
        }
        return res.length() > 0 ? res.toString().trim().substring(0, res.length() - 3) : "";
    }

    public String setAccountBySmo(Long aSmo, Long aAccount, HttpServletRequest aRequest) throws NamingException, ParseException {

        IWebQueryService serviceW = Injection.find(aRequest).getService(IWebQueryService.class);
        IContractService service = Injection.find(aRequest).getService(IContractService.class);
        Collection<WebQueryResult> l = serviceW.executeNativeSql("select upper(mc.dtype) from medcase mc where mc.id=" + aSmo);
        if (l.isEmpty()) throw new ParseException("НЕОПРЕДЕЛЕН СМО", 0);
        String dtype = "" + l.iterator().next().get1();
        service.setSmoByAccount(aAccount, dtype, aSmo, true, true);
        return "";
    }

    public String saveServiceByMedCase(Long aMedCase, String aServices, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String login = LoginInfo.find(aRequest.getSession(true)).getUsername();
        if (aServices != null && !aServices.equals("")) {
            String[] otherServs = aServices.split("#@#");
            if (otherServs.length > 0) {
                for (String otherServ : otherServs) {
                    String[] serv = otherServ.split("@#@");
                    String sql = "insert into medcase (noActuality,dtype,createdate,createtime,username,parent_id" +
                            ",medservice_id,workfunctionexecute_id,idc10_id" +
                            ",datestart,medserviceAmount,serviceStream_id) values (" +
                            "'0','ServiceMedCase',current_date,current_time,'" + login +
                            "','" + aMedCase + "','" + serv[0] + "','" + serv[1] + "','" + serv[2] +
                            "',to_date('" + serv[3] + "','dd.mm.yyyy'),'" + serv[4] + "','" + serv[5] +
                            "')";
                    service.executeUpdateNativeSql(sql);
                }
            }
        }

        return "";
    }

    public void checkEditProtocolControl(Long aDiary, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String login = LoginInfo.find(aRequest.getSession(true)).getUsername();
        List<Object[]> list = service.executeNativeSqlGetObj("select dm.diary_id,dm.record from diarymessage dm left join diary d on d.id=dm.diary_id where (dm.validitydate>current_date or dm.validitydate=current_date and dm.validitytime>=current_time) and d.username='" + login + "' and d.id='" + aDiary + "'");
        if (!list.isEmpty()) {
            Object[] obj = list.get(0);
            service.executeUpdateNativeSql("update diary set record='" + obj[1] + "',editdate=current_date,edittime=current_time where id=" + aDiary);
            service.executeUpdateNativeSql("update diarymessage dm set IsDoctorCheck='1' where dm.diary_id=" + aDiary);
        }
    }


    public String getDiaryDefects(Long aDiaryId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder res = new StringBuilder();
        String req = "select vdd.id, vdd.name from VocDefectDiary vdd " +
                "order by vdd.id ";
        List<Object[]> list = service.executeNativeSqlGetObj(req);
        for (Object[] obj : list) {
            res.append(obj[0]).append(":").append(obj[1]).append("#");
        }

        return res.length() > 0 ? res.substring(0, res.length() - 1) : "";
    }

    public String getDiaryText(Long aDiaryId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        List<Object[]> list = service.executeNativeSqlGetObj("select id,record from diary where id=" + aDiaryId);
        return list.isEmpty() ? "" : list.get(0)[1].toString();
    }

    public String setDiaryDefect(Long aDiaryId, Long aDefectId, String aComment, String aRecord, String aVk, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        String login = LoginInfo.find(aRequest.getSession(true)).getUsername();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 24);
        boolean vk = aVk != null && aVk.equals("1");
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        sql.append("insert into DiaryMessage (isVk,diary_id,defect_id,comment,record,createusername,createdate,createtime,validitydate,validitytime) ")
                .append("values ('").append(vk ? "1" : "0").append("','").append(aDiaryId).append("','")
                .append(aDefectId).append("','").append(aComment).append("','").append(aRecord)
                .append("','").append(login).append("',current_date,current_time,to_date('").append(f.format(cal.getTime())).append("','dd.mm.yyyy'),current_time)");
        service.executeUpdateNativeSql(sql.toString());
        List<Object[]> list = service.executeNativeSqlGetObj("select d.id,d.username,to_char(d.dateregistration,'dd.mm.yyyy')||' '||pat.lastname from diary d left join medcase mc on mc.id=d.medcase_id left join patient pat on pat.id=mc.patient_id where d.id='" + aDiaryId + "'");
        if (!list.isEmpty()) {
            Object[] obj = list.get(0);
            String username = "" + obj[1];

            sql = new StringBuilder();

            sql.append("insert into CustomMessage (messageText,messageTitle,recipient")
                    .append(",dispatchDate,dispatchTime,username,validityDate,messageUrl)")
                    .append("values ('").append("На исправление дневник").append("','")
                    .append(obj[2]).append("','").append(username)
                    .append("',current_date,current_time,'").append(login).append("',current_date,'")
                    .append("js-stac_slo-list_edit_protocol.do?id=").append(aDiaryId).append("')");
            service.executeUpdateNativeSql(sql.toString());
        }
        return "1";
    }

    /**
     * Возвращаем список дневников, исследований, лаб. анализов по госпитализации
     */
    public String getDiariesByHospital(Long aMedcaseId, String aServiceType, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String addSql = "LAB".equals(aServiceType) ? " and vst.code='LABSURVEY'" :
                ("DIAG".equals(aServiceType) ? " and vst.code in ('SERVICE', 'DIAGNOSTIC','SURVEY')" : "");
        String sql = "select d.id as id, to_char(d.dateregistration,'dd.MM.yyyy') as recordDate" +
                " ,to_char(d.timeregistration,'HH24:MI') as recordTime," +
                " list(mc.code) as serviceCode, list(mc.name) as serviceName" +
                " ,d.record as recordText" +
                " from medcase sls " +
                " left join medcase vis on vis.patient_id=sls.patient_id" +
                " left join medcase servmc on servmc.parent_id=vis.id" +
                " left join medservice mc on mc.id=servmc.medservice_id" +
                " left join diary d on d.medcase_id= vis.id" +
                " left join VocServiceType vst on vst.id=mc.serviceType_id" +
                " where sls.id=" + aMedcaseId +
                " and d.dtype='Protocol'" +
                " and (vis.dtype='Visit' or vis.dtype='HospitalMedCase' or vis.dtype='DepartmentMedCase')" +
                " and d.dateregistration is not null and d.dateregistration>=sls.datestart" +
                " and case when vis.dtype='Visit' and vis.parent_id!=sls.id then (select case when vwf.isNoDiagnosis='1' then '1' else '0' end from medcase v" +
                " left join workfunction wf on wf.id=v.workfunctionexecute_id" +
                " left join vocworkfunction vwf on vwf.id=wf.workfunction_id where v.id=vis.id) else '1' end = '1'" +
                addSql +
                " group by d.id, d.dateregistration, d.timeregistration, d.record " +
                " order by d.dateregistration desc, d.timeregistration desc";
        return service.executeNativeSqlGetJSON(new String[]{"id", "recordDate", "recordTime", "serviceCode", "serviceName", "recordText"}, sql, null);
    }

    public String getPrefixByProtocol(Long aDiaryId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select vtp.prefixprint from Diary d left join voctypeprotocol vtp on vtp.id=d.type_id where d.id=" + aDiaryId + " and vtp.prefixprint is not null");
        return list.isEmpty() ? null : list.iterator().next().get1().toString();
    }

    public String deleteTableHDFEmpty(String aMode, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        if (aMode == null || aMode.equals("1")) {
            service.executeUpdateNativeSql("delete from HospitalDataFond where isTable4='1'");
        } else if (aMode.equals("2")) {
            service.executeUpdateNativeSql("delete from HospitalDataFond where (isTable2 is null or isTable2='0') and (isTable3 is null or isTable3='0') and (isTable4 is null or isTable4='0') and (isTable5 is null or isTable5='0')");
        } else if (aMode.equals("3")) {
            service.executeUpdateNativeSql("delete from HospitalDataFond where (isTable2='1' or isTable3='1') and (isTable5 is null or isTable5='0')");
        }
        return null;
    }

    public String updateTable(String aTable, String aFldId, String aValId, String aFldSet, String aValSet, String aWhereAdd, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String add = aWhereAdd != null && !aWhereAdd.equals("") ? " and " + aWhereAdd : "";
        if (aValSet == null || aValSet.equals("null")) {
            service.executeUpdateNativeSql("update " + aTable + " set " + aFldSet + "=null where " + aFldId + "='" + aValId + "' " + add);
        } else {
            service.executeUpdateNativeSql("update " + aTable + " set " + aFldSet + "='" + aValSet + "' where " + aFldId + "='" + aValId + "' " + add);
        }
        return "";
    }

    public String isCanDischarge(Long aMedCaseId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

        String sql = "select slo.id as sloid,ml.name||' '||to_char(slo.dateStart,'dd.mm.yyyy')||coalesce('-'||to_char(slo.transferDate,'dd.mm.yyyy'),'') as info from medcase sls " +
                " left join medcase slo on sls.id=slo.parent_id and slo.dtype='DepartmentMedCase'" +
                " left join mislpu ml on ml.id=slo.department_id" +
                " left join diagnosis d on slo.id = d.medcase_id" +
                " left join vocdiagnosisregistrationtype vdrt on vdrt.id=d.registrationtype_id" +
                " left join vocprioritydiagnosis vpd on vpd.id=d.priority_id" + " where sls.id='" + aMedCaseId + "' and (ml.isnoomc is null or ml.isnoomc='0') " +
                " group by sls.id,slo.id,ml.name,slo.dateStart,slo.transferDate" +
                " having count(case when (vdrt.code='3' or vdrt.code='4') and (vpd.code='1') and d.idc10_id is not null then 1 else null end)=0  ";
        Collection<WebQueryResult> list = service.executeNativeSql(sql);
        if (!list.isEmpty()) {
            StringBuilder ret = new StringBuilder();
            ret.append("Не полностью заполнены данные по диагнозам в отделениях!!! ");
            for (WebQueryResult wqr : list) {
                ret.append(" <a href='entitySubclassView-mis_medCase.do?id=")
                        .append(wqr.get1())
                        .append("' onclick='return  msh.util.FormData.getInstance().isChangedForLink() ;'>")
                        .append(wqr.get2())
                        .append("</a>");
            }
            return ret.toString();
        }
        return null;
    }

    public String getYesNo(Long aYesNoId, HttpServletRequest aRequest) throws NamingException {
        if (aYesNoId != null && !aYesNoId.equals(0L)) {
            IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
            Collection<WebQueryResult> list = service.executeNativeSql("select code from vocyesno where id='" + aYesNoId + "' ", 1);
            if (!list.isEmpty()) {
                return "" + list.iterator().next().get1();
            }
        }
        return "-1";
    }

    public static Object getDefaultParameterByConfig(String aParameter, String aValueDefault, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> l = service.executeNativeSql("select sf.id,sf.keyvalue from SoftConfig sf where  sf.key='" + aParameter + "'");
        if (l.isEmpty()) {
            return aValueDefault;
        } else {
            return l.iterator().next().get2();
        }

    }

    public String createNewDiary(String aTitle, String aText, HttpServletRequest aRequest) throws NamingException {
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        service.createNewDiary(aTitle, aText, username);
        return "Сохранено";
    }

    public void updateDataFromParameterConfig(Long aDepartment, Long aIsLowerCase, String aIds, HttpServletRequest aRequest) throws NamingException {
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
        service.updateDataFromParameterConfig(aDepartment, aIsLowerCase != null && aIsLowerCase.equals(1L), aIds, false);
    }

    public void removeDataFromParameterConfig(Long aDepartment, String aIds, HttpServletRequest aRequest) throws NamingException {
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
        service.removeDataFromParameterConfig(aDepartment, aIds);
    }

    public String unionSloWithNextSlo(Long aSlo, HttpServletRequest aRequest) throws NamingException {
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
        service.unionSloWithNextSlo(aSlo);
        createAdminChangeMessageBySmo(aSlo, "UNION_SLO_WITH_NEXT_SLO", "Объединено СЛО со след. СЛО: " + aSlo, aRequest);

        return "Объединены";
    }

    public String getPatientDefaultInfo(Long aPatient, String aDateFrom, HttpServletRequest aRequest) throws NamingException {
        StringBuilder ret = new StringBuilder();
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> col = service.executeNativeSql("select vi.name||' дата установления '||to_char(i.dateFrom,'dd.mm.yyyy') || case when i.withoutexam='1' then ' без переосвидетельствования ' else coalesce(' дата следующего пересмотра '||to_char(i.nextRevisionDate,'dd.mm.yyyy'),'') end || case when i.incapable='1' then ' недееспособный ' ||' '||coalesce(' дата суда '||to_char(i.lawCourtDate,'dd.mm.yyyy'))||coalesce(' суд '||vlc.name) else '' end  from invalidity i left join VocInvalidity vi on vi.id=i.group_id        left join VocLawCourt vlc on vlc.id=i.lawCourt_id where i.patient_id=" + aPatient + "   order by i.dateFrom desc ");
        if (!col.isEmpty()) {
            ret.append("\nИНВАЛИДНОСТЬ: ");
            for (WebQueryResult wqr : col) {
                ret.append(wqr.get1());
            }

        }
        col.clear();
        col = service.executeNativeSql("select ml.name||' С '||to_char(sls.dateStart,'dd.mm.yyyy')||' ПО '||to_char(sls.dateFinish,'dd.mm.yyyy') from medcase sls left join medcase slo on slo.parent_id=sls.id  left join mislpu ml on ml.id=slo.department_id where sls.patient_id=" + aPatient + " and sls.dtype='HospitalMedCase' and sls.dateFinish <= to_date('" + aDateFrom + "','dd.mm.yyyy') and slo.datefinish is not null order by sls.datefinish desc ");

        if (!col.isEmpty()) {
            ret.append("\nПРЕДЫДУЩИЕ ГОСПИТАЛИЗАЦИИ: ");
            for (WebQueryResult wqr : col) {
                ret.append(wqr.get1());
            }

        }

        return ret.toString();
    }

    public String deniedHospitalizatingSls(Long aMedCaseId, Long aDeniedHospitalizatingId, HttpServletRequest aRequest) throws NamingException {
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
        service.deniedHospitalizatingSls(aMedCaseId, aDeniedHospitalizatingId);
        createAdminChangeMessageBySmo(aMedCaseId, "DENIED_HOSPITALIZATING", "Оформлен отказ от госпитализации", aRequest);

        return "Обновлены";
    }

    public String preRecordDischarge(Long aMedCaseId, String aDischargeEpicrisis, HttpServletRequest aRequest) throws Exception {
        IWebQueryService service1 = Injection.find(aRequest).getService(IWebQueryService.class);
        boolean isEdit = true;
        boolean isCurentOnly = RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Ssl/Discharge/OnlyCurrentDay", aRequest);
        if (isCurentOnly) {
            Collection<WebQueryResult> list = service1.executeNativeSql("select SLS.id from medcase SLS  left join vochospitalizationresult vhr on vhr.id= SLS.result_id where SLS.id='" + aMedCaseId + "' and (sls.dateFinish is not null or (vhr.code='11' and (current_date-sls.datefinish)<4))");
            if (!list.isEmpty()) {
                IScriptService service = (IScriptService) Injection.find(aRequest).getService("ScriptService");
                isEdit = TicketServiceJs.checkPermission(service, "DischargeMedCase", "editDischargeEpicrisis", aMedCaseId, aRequest);
            }
        }
        if (isEdit) {
            IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
            service.preRecordDischarge(aMedCaseId, aDischargeEpicrisis);
            return "Сохранено";
        }
        throw new IllegalAccessError("У Вас стоит запрет на редактирование выписанного пациента!!!");

    }

    public String updateDischargeDateByInformationBesk(String aIds, String aDate, HttpServletRequest aRequest) throws Exception {

        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
        service.updateDischargeDateByInformationBesk(aIds, aDate);
        return "Обновлены";
    }

    public String getTypeDiagByAccoucheur(HttpServletRequest aRequest) throws NamingException {
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
        return service.getTypeDiagByAccoucheur();
    }

    public static void createAdminChangeMessageBySmo(Long aSmo, String aType, String aTextInfo
            , HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        String login = LoginInfo.find(aRequest.getSession(true)).getUsername();
        String dateFinish = "", timeDisch = "";

        if (aType.equals("HOSP_DELETE_DATA_DISCHARGE")) {
            IWebQueryService sqlService = Injection.find(aRequest).getService(IWebQueryService.class);
            Collection<WebQueryResult> list = sqlService.executeNativeSql("select datefinish, dischargetime from medcase where id = " + aSmo);
            if (!list.isEmpty()) {
                WebQueryResult wqr = list.iterator().next();
                if (wqr.get1() != null && wqr.get2() != null) {
                    dateFinish = wqr.get1().toString();
                    timeDisch = wqr.get2().toString();
                }
            }
        }

        //при удалении данных выписки сохраняется только если дата и время непустые
        if (!aType.equals("HOSP_DELETE_DATA_DISCHARGE") || !dateFinish.equals("") && !timeDisch.equals("")) {

            sql.append("insert into AdminChangeJournal ( medcase, createDate, createTime")
                    .append(", createUsername, ctype,  annulRecord ")
                    .append(aType.equals("HOSP_DELETE_DATA_DISCHARGE") ? ", datefinishDisch, timeDisch" : "")
                    .append(") values (").append(aSmo).append(", current_date, current_time, '")
                    .append(login)
                    .append("', '")
                    .append(aType)
                    .append("','")
                    .append(aTextInfo)
                    .append(aType.equals("HOSP_DELETE_DATA_DISCHARGE") ? "','" + dateFinish + "' , '" + timeDisch + "'" : "'")
                    .append(")")
            ;
            service.executeUpdateNativeSql(sql.toString());
        }
    }

    public String changeStatCardNumber(String aNewStatCardNumber, Long aMedCase, HttpServletRequest aRequest) throws NamingException, JspException {

        boolean always = RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Ssl/Admission/AlwaysCreateStatCardNumber", aRequest);
        IWebQueryService serviceWQR = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> l = serviceWQR.executeNativeSql("select ss.code from statisticstub ss where ss.medCase_id=" + aMedCase);
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);

        createAdminChangeMessageBySmo(aMedCase, "CHANGE_STAT_CARD_NUMBER", "Изменение номера стат. карты с:" +
                (l.isEmpty() ? "-" : l.iterator().next().get1()) +
                " на " +
                aNewStatCardNumber +
                " случая лечения в стационаре #" +
                aMedCase, aRequest);
        return service.getChangeStatCardNumber(aMedCase, aNewStatCardNumber, always);
    }

    public String getListTemperatureCurve(Long aMedCase, HttpServletRequest aRequest) throws Exception {
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
        return service.getTemperatureCurve(aMedCase);
    }

    public Long getPatient(Long aMedCase, HttpServletRequest aRequest) throws NamingException {
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
        return service.getPatient(aMedCase);
    }

    public String isOpenningSlo(Long aMedCase, HttpServletRequest aRequest) throws NamingException {
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
        return service.isOpenningSlo(aMedCase);
    }

    public String deleteDischarge(Long aMedCaseId, HttpServletRequest aRequest) throws NamingException {
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
        createAdminChangeMessageBySmo(aMedCaseId, "HOSP_DELETE_DATA_DISCHARGE", "Удалены данные о дате выписки", aRequest);

        return service.deleteDataDischarge(aMedCaseId);
    }

    public String findDoubleServiceByPatient(Long aMedService, Long aPatient, Long aService, String aDate, HttpServletRequest aRequest) throws NamingException, ParseException {
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
        return service.findDoubleServiceByPatient(aMedService, aPatient, aService, aDate);
    }

    public String findDoubleOperationByPatient(Long aSurOperation, Long aPatient, Long aOperation, String aDate, HttpServletRequest aRequest) throws NamingException, ParseException {
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
        return service.findDoubleOperationByPatient(aSurOperation, aPatient, aOperation, aDate);
    }

    public String getOperations(Long aPatient, String aDateStart
            , String aDateFinish, HttpServletRequest aRequest) throws NamingException {
        StringBuilder sql = new StringBuilder();
        if (aDateFinish != null && !aDateFinish.equals("") && aDateFinish.length() == 10) {
            aDateFinish = "to_date('" + aDateFinish + "','dd.mm.yyyy')";
        } else {
            aDateFinish = "CURRENT_DATE";
        }
        sql.append("select to_char(operationDate,'DD.MM.YYYY') as operDate1,cast(operationTime as varchar(5)) as timeFrom,cast(operationTimeTo as varchar(5)) as timeTo,vo.name as voname,so.operationText as sooperationText from SurgicalOperation so left join MedService vo on vo.id=so.operation_id where so.patient_id='")
                .append(aPatient)
                .append("' and so.operationDate between to_date('").append(aDateStart).append("','dd.mm.yyyy') and ").append(aDateFinish).append(" order by so.operationDate");
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

        Collection<WebQueryResult> list1 = service.executeNativeSql(sql.toString());
        StringBuilder result = new StringBuilder();
        result.append("\n");
        if (!list1.isEmpty()) {
            result.append("ХИРУРГИЧЕСКИЕ ОПЕРАЦИИ:");
            result.append("\n");
            for (WebQueryResult wqr : list1) {
                result.append(wqr.get1()).append(" ");
                result.append(wqr.get4()).append("\n");
                result.append(wqr.get5() != null ? wqr.get5() : "");
            }
        }
        return result.toString();


    }

    public String getExpMedservices(int aIsParamDepartment, int aIsLowerCase, int aIsNewStr, Long aPatient, String aDateStart
            , String aDateFinish, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder period = new StringBuilder();
        period.append("between to_date('").append(aDateStart)
                .append("','dd.mm.yyyy') and  ");
        if (aDateFinish != null && !aDateFinish.equals("") && aDateFinish.length() == 10) {
            period.append("to_date('").append(aDateFinish).append("','dd.mm.yyyy')");
        } else {
            period.append("CURRENT_DATE");
        }
        StringBuilder sql = new StringBuilder();
        String razd = "\n";
        if (aIsNewStr == 1) {
            razd = "; ";
        }
        if (aIsParamDepartment == 2) {
            sql.append("select em.NumberDoc ||' от ','").append(razd).append("'||to_char(em.OrderDate,'dd.mm.yyyy')||' '||vdpg.name||': '|| ")
                    .append(" list(vdp.name||': '||dp.value||' '||vdp.dimension)  as infoDirect")
                    .append(" from Document em ")
                    .append(" left join DocumentParameter dp on dp.document_id=em.id ")
                    .append(" left join VocDocumentParameter vdp on vdp.id = dp.parameter_id")
                    .append(" left join VocDocumentParameterGroup vdpg on vdpg.id=vdp.parameterGroup_id")
                    .append(" where em.patient_id='").append(aPatient).append("'")
                    .append(" and em.dtype='ExternalMedservice'")
                    .append(" and (em.orderDate ").append(period)
                    .append(" or em.createDate ").append(period)
                    .append(" )")
                    .append(" group by em.numberdoc,em.orderDate,vdpg.id,vdpg.name order by vdpg.name,em.orderDate");

        } else {
            IWorkerService serviceWorker = Injection.find(aRequest).getService(IWorkerService.class);
            Long department = serviceWorker.getWorkingLpu();
            sql.append("select em.NumberDoc ||' от ','").append(razd).append("'||to_char(em.OrderDate,'dd.mm.yyyy')||' '||lower(vdpg.name)||': '|| ")
                    .append(" list(case when vdpc.isLowerCase='1'")
                    .append(" then lower(vdp.name||': '||dp.value||' '||vdp.dimension)")
                    .append(" else  vdp.name||': '||dp.value||' '||vdp.dimension")
                    .append(" end)  as infoDirect")
                    .append(" from Document em ")
                    .append(" left join DocumentParameter dp on dp.document_id=em.id ")
                    .append(" left join VocDocumentParameter vdp on vdp.id = dp.parameter_id")
                    .append(" left join VocDocumentParameterConfig vdpc on vdpc.documentParameter_id = vdp.id")
                    .append(" left join VocDocumentParameterGroup vdpg on vdpg.id=vdp.parameterGroup_id")
                    .append(" where em.patient_id='").append(aPatient).append("'")
                    .append(" and em.dtype='ExternalMedservice'")
                    .append(" and (em.orderDate ").append(period)
                    .append(" or em.createDate ").append(period)
                    .append(" ) and vdpc.department_id='").append(department).append("'")
                    .append(" group by em.orderDate,em.numberdoc,vdpg.id,vdpg.name order by vdpg.name,em.orderDate");
        }
        Collection<WebQueryResult> list1 = service.executeNativeSql(sql.toString());
        StringBuilder result = new StringBuilder();
        for (WebQueryResult wqr : list1) {
            if (wqr.get2() != null) result.append(wqr.get2());

        }
        result.append("\n");
        return aIsLowerCase == 1 ? result.substring(1).trim().toLowerCase() : result.substring(1).trim();
    }

    public String setPatientByExternalMedservice(String aNumberDoc, String aOrderDate, String aPatient, HttpServletRequest aRequest) throws NamingException {
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
        service.setPatientByExternalMedservice(aNumberDoc, aOrderDate, aPatient);
        return "обновлено";
    }

    public String getDefaultDepartmentByUser(HttpServletRequest aRequest) throws NamingException {
        StringBuilder res = new StringBuilder();
        StringBuilder sql = new StringBuilder();
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        sql.append(" select ml.id,ml.name||coalesce(' ('||ml1.name||')','') from workfunction wf");
        sql.append(" left join secuser su on su.id=wf.secuser_id");
        sql.append(" left join worker w on w.id=wf.worker_id");
        sql.append(" left join mislpu ml on ml.id=w.lpu_id");
        sql.append(" left join mislpu ml1 on ml1.id=ml.parent_id");
        sql.append(" where su.login='").append(username).append("'");
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(), 2);
        if (!list.isEmpty()) {
            WebQueryResult wqr = list.iterator().next();
            res.append(wqr.get1()).append("#").append(wqr.get2()).append("#");
        } else {
            res.append("##");
        }
        return res.toString();

    }

    public String getTextDiaryByMedCase(Long aMedCase, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        StringBuilder res = new StringBuilder();
        sql.append("select d.id,d.record from Diary d ");
        sql.append(" where d.medCase_id='").append(aMedCase).append("'");

        Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(), 2);
        for (WebQueryResult wqr : list) {
            res.append(wqr.get2());
        }

        return res.toString();
    }

    public String getDefaultBedTypeByDepartment(Long aDepartment, Long aServiceStream, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        StringBuilder res = new StringBuilder();
        sql.append("select vbt.id as vbtid, vbt.name as vbtname,vbst.id as vbstid,vbst.name as vbstname from BedFund bf ");
        sql.append(" left join vocBedType vbt on vbt.id=bf.bedType_id left join vocBedSubType vbst on vbst.id=bf.bedSubType_id ");
        sql.append(" where bf.lpu_id='").append(aDepartment);
        if (aServiceStream != null && aServiceStream.intValue() > 0)
            sql.append("' and bf.serviceStream_id='").append(aServiceStream);
        sql.append("' and bf.dateFinish is null order by bf.amount desc");


        Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(), 2);
        if (!list.isEmpty()) {
            WebQueryResult wqr = list.iterator().next();
            res.append(wqr.get1()).append("#").append(wqr.get2()).append("#");
            res.append(wqr.get3()).append("#").append(wqr.get4()).append("#");

        } else {
            res.append("####");
        }

        return res.toString();
    }

    public String getDefaultBedSubTypeByDepartment(Long aDepartment, Long aServiceStream, Long aBedType, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        StringBuilder res = new StringBuilder();
        sql.append("select vbt.id as vbtid, vbt.name as vbtname,vbst.id as vbstid,vbst.name as vbstname from BedFund bf ");
        sql.append(" left join vocBedType vbt on vbt.id=bf.bedType_id left join vocBedSubType vbst on vbst.id=bf.bedSubType_id ");
        sql.append(" where bf.lpu_id='").append(aDepartment);
        if (aServiceStream != null && aServiceStream.intValue() > 0)
            sql.append("' and bf.bedType_id='").append(aBedType);
        sql.append("' and bf.serviceStream_id='").append(aServiceStream);
        sql.append("' and bf.dateFinish is null");
        Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(), 2);
        if (!list.isEmpty()) {
            WebQueryResult wqr = list.iterator().next();
            res.append(wqr.get3()).append("#").append(wqr.get4()).append("#");
        } else {
            res.append("##");
        }
        return res.toString();
    }

    public String getDefaultBedFundBySlo(Long aParent, Long aDepartment, Long aServiceStream, String aDateFrom, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql;
        StringBuilder res = new StringBuilder();
        Collection<WebQueryResult> list = service.executeNativeSql("select vht.id,vht.code from MedCase hmc left join VocHospType vht on vht.id=hmc.hospType_id where hmc.id='" + aParent + "' and vht.code is not null", 1);
        String bedSubType = "";
        String hospType = null;
        if (!list.isEmpty()) {
            WebQueryResult wqr = list.iterator().next();
            hospType = "" + wqr.get2();
        }
        if ("DAYTIMEHOSP".equals(hospType)) {
            bedSubType = " and vbst.code='2' ";
        } else if (hospType == null || hospType.equals("ALLTIMEHOSP")) {
            bedSubType = " and vbst.code='1' ";
        }
        sql = new StringBuilder();
        sql.append("select bf.id, vbt.name||' ('||vbst.name||' ' || case when bf.noFood='1' then 'без питания' else 'с питанием' end ||')' from BedFund bf ");
        sql.append(" left join vocBedType vbt on vbt.id=bf.bedType_id left join vocBedSubType vbst on vbst.id=bf.bedSubType_id ");
        sql.append(" where bf.lpu_id='").append(aDepartment)
                .append("' and bf.serviceStream_id='").append(aServiceStream)
                .append("' and to_date('").append(aDateFrom)
                .append("','dd.mm.yyyy') between bf.dateStart and coalesce(bf.dateFinish,CURRENT_DATE)");
        sql.append(" ").append(bedSubType).append(" order by bf.amount desc");

        list.clear();
        list = service.executeNativeSql(sql.toString(), 2);
        if (list.size() == 1) {
            WebQueryResult wqr = list.iterator().next();
            res.append(wqr.get1()).append("#").append(wqr.get2()).append("#");

        } else {
            res.append("##");
        }

        return res.toString();
    }

    public String getDefaultInfoBySlo(Long aParent, Long aDepartment, Long aServiceStream, String aDateFrom, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        StringBuilder res = new StringBuilder();
        Collection<WebQueryResult> list = service.executeNativeSql("select vht.id,vht.code from MedCase hmc left join VocHospType vht on vht.id=hmc.hospType_id where hmc.id='" + aParent + "' and vht.code is not null", 1);
        String bedSubType = "";
        String hospType = null;
        if (!list.isEmpty()) {
            WebQueryResult wqr = list.iterator().next();
            hospType = "" + wqr.get2();
        }
        if (hospType != null && hospType.equals("DAYTIMEHOSP")) {
            bedSubType = " and vbst.code='2' ";
        } else if (hospType != null && hospType.equals("ALLTIMEHOSP")) {
            bedSubType = " and vbst.code='1' ";
        }
        sql.append("select bf.id, vbt.name||' ('||vbst.name||' ' || case when bf.noFood='1' then 'без питания' else 'с питанием' end ||')' from BedFund bf ");
        sql.append(" left join vocBedType vbt on vbt.id=bf.bedType_id left join vocBedSubType vbst on vbst.id=bf.bedSubType_id ");
        sql.append(" where bf.lpu_id='").append(aDepartment)
                .append("' and bf.serviceStream_id='").append(aServiceStream)
                .append("' and to_date('").append(aDateFrom)
                .append("','dd.mm.yyyy') between bf.dateStart and coalesce(bf.dateFinish,CURRENT_DATE)");
        sql.append(" ").append(bedSubType);
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        StringBuilder sql1 = new StringBuilder();
        sql1.append("select wf.id as wfid,case when wf.code is null then '' else wf.code||' ' end || vwf.name||' '|| p.lastname||' '||p.firstname||' '||p.middlename from WorkFunction wf")
                .append(" left join Worker w on w.id=wf.worker_id")
                .append(" left join SecUser su on su.id=wf.secUser_id")
                .append(" left join Patient as p on p.id=w.person_id left join VocWorkFunction vwf on vwf.id=wf.workFunction_id")
                .append(" where su.login = '").append(username).append("' and w.lpu_id='").append(aDepartment).append("' and wf.id is not null");
        Collection<WebQueryResult> list1 = service.executeNativeSql(sql1.toString(), 1);
        if (!list1.isEmpty()) {
            WebQueryResult wqr = list1.iterator().next();
            res.append(wqr.get1()).append("#").append(wqr.get2()).append("#");
        } else {
            sql1 = new StringBuilder();
            sql1.append("select wf.id as wfid,case when wf.code is null then '' else wf.code||' ' end || vwf.name||' '|| p.lastname||' '||p.firstname||' '||p.middlename from WorkFunction wf")
                    .append(" left join Worker w on w.id=wf.worker_id")
                    .append(" left join SecUser su on su.id=wf.secUser_id")
                    .append(" left join Patient as p on p.id=w.person_id left join VocWorkFunction vwf on vwf.id=wf.workFunction_id")
                    .append(" where w.lpu_id='").append(aDepartment).append("' and wf.isAdministrator='1'");
            list1 = service.executeNativeSql(sql1.toString(), 1);
            if (!list1.isEmpty()) {
                WebQueryResult wqr = list1.iterator().next();
                res.append(wqr.get1()).append("#").append(wqr.get2()).append("#");
            } else {
                res.append("##");
            }
        }
        list.clear();
        list = service.executeNativeSql(sql.toString(), 2);
        if (list.size() == 1) {
            WebQueryResult wqr = list.iterator().next();
            res.append(wqr.get1()).append("#").append(wqr.get2()).append("#");

        } else {
            res.append("##");
        }

        return res.toString();
    }

    /**
     * Проверить наличие других предварительных госпитализаций.
     *
     * @param patId    Patient.id
     * @param aRequest HttpServletRequest
     * @return String json c результатом
     */
    public String prevPlanHospital(Long patId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String query = "select distinct to_char(wchb.datefrom,'dd.mm.yyyy')" +
                " ,case when wchb.diagnosis<>'null. null' then coalesce(wchb.diagnosis, mkb.code||' ' ||mkb.name) else" +
                " mkb.code||' ' ||mkb.name end as mkb,m.name" +
                " ,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as fiopost" +
                " ,wchb.id as f5_preId" +
                " from workcalendarhospitalbed wchb" +
                " left join mislpu m on wchb.department_id=m.id" +
                " left join medcase mc on wchb.patient_id=mc.patient_id" +
                " left join workfunction wf on wf.id=wchb.workfunction_id" +
                " left join VocWorkFunction vwf on vwf.id=wf.workFunction_id" +
                " left join worker w on w.id=wf.worker_id" +
                " left join patient wp on wp.id=w.person_id" +
                " left join vocidc10 mkb on mkb.id=wchb.idc10_id" +
                " where wchb.dateFrom is not null and wchb.datefrom>=CAST('today' AS DATE) and wchb.patient_id=" + patId;
        JSONArray res = new JSONArray();
        Collection<WebQueryResult> list = service.executeNativeSql(query);
        for (WebQueryResult w : list) {
            JSONObject o = new JSONObject();
            o.put("id", w.get5());
            o.put("date", w.get1())
                    .put("diagnosis", w.get2())
                    .put("lpu", w.get3())
                    .put("fiopost", w.get4());
            res.put(o);
        }
        return res.toString();
    }

    /**
     * Поставить пациента на наблюдение.
     *
     * @param slsId    HospitalMedCase.id
     * @param aRequest HttpServletRequest
     * @return String c сообщением пользователю
     */
    public String watchThisPatient(Long slsId, HttpServletRequest aRequest) throws NamingException {
        String res = "Пациент добавлен в список наблюдения!";
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String query = "select id from listwatch where datewatch=current_date";
        Collection<WebQueryResult> list = service.executeNativeSql(query, 1);
        int idlistwatch = 0;
        if (!list.isEmpty()) {
            WebQueryResult wqr = list.iterator().next();
            idlistwatch = Integer.parseInt(wqr.get1().toString());
        }
        if (idlistwatch == 0) { // надо добавить его
            query = "INSERT into listwatch(datewatch) VALUES(current_date)";
            service.executeUpdateNativeSql(query);
            query = "select id from listwatch where datewatch=current_date";
            list = service.executeNativeSql(query, 1);
            if (!list.isEmpty()) {
                WebQueryResult wqr = list.iterator().next();
                idlistwatch = Integer.parseInt(wqr.get1().toString());
            }
        }
        query = "select medcase_id from patientwatch where listwatch_id='" + idlistwatch + "' and medcase_id=(select parent_id from medcase where id='" + slsId + "')"; //есть ли уже
        list = service.executeNativeSql(query, 1);
        if (!list.isEmpty()) res = "Пациент уже был добавлен в список наблюдения!";
        else {
            query = "INSERT INTO patientwatch(medcase_id,listwatch_id) VALUES((select parent_id from medcase where id='" + slsId + "'),'" + idlistwatch + "')";
            service.executeUpdateNativeSql(query);
        }
        return res;
    }

    /**
     * Снять пациента с наблюдения.
     *
     * @param slsId    HospitalMedCase.id
     * @param aRequest HttpServletRequest
     * @return String c сообщением пользователю
     */
    public String notWatchThisPatient(Long slsId, HttpServletRequest aRequest) throws NamingException {
        String res = "Пациент убран из списка наблюдения!";
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String query = "select medcase_id from patientwatch where listwatch_id=(select id from listwatch where datewatch=CAST('today' AS DATE)) and medcase_id=(select parent_id from medcase where id='" + slsId + "')"; //есть ли уже
        Collection<WebQueryResult> list = service.executeNativeSql(query, 1);
        if (!list.isEmpty()) { //удаляем
            query = "delete from patientwatch where listwatch_id=(select id from listwatch where datewatch=CAST('today' AS DATE)) and medcase_id=(select parent_id from medcase where id='" + slsId + "')";
            service.executeUpdateNativeSql(query);
        } else res = "Пациент и не был в списке наблюдения!";
        return res;
    }

    /**
     * Получить рост, вес, ИМТ.
     *
     * @param slsId    HospitalMedCase.id
     * @param aRequest HttpServletRequest
     * @return String json c результатом
     */
    public String getHWeightIMT(Long slsId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select height,weight,imt from statisticstub where medcase_id ='" + slsId + "'", 1);
        JSONObject res = new JSONObject();
        if (!list.isEmpty()) {
            WebQueryResult w = list.iterator().next();
            res.put("height", w.get1())
                    .put("weight", w.get2())
                    .put("imt", w.get3());
        }
        return res.toString();
    }

    /**
     * Сохранить рост, вес, ИМТ.
     *
     * @param slsId    HospitalMedCase.id
     * @param height   Вес
     * @param weight   Рост
     * @param imt      ИМТ
     * @param aRequest HttpServletRequest
     * @return void
     */
    public void setHWeightIMT(Long slsId, int height, int weight, double imt, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("update statisticstub set height='" + height + "',weight='" + weight + "',imt='"
                + imt + "' where medcase_id ='" + slsId + "'");
    }

    /**
     * Проставить отметку в отчёте о том, что консультация была оказана (для диетолога).
     *
     * @param slsId    HospitalMedCase.id
     * @param aRequest HttpServletRequest
     * @return void
     */
    public void setDietVisitIsDoneReportIMT(Long slsId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String query = "update statisticstub set dietdone=true where medcase_id =" + slsId;
        service.executeUpdateNativeSql(query);
    }

    /**
     * Проставить перед удалением выписки: что юзер - лечащий врач (одна из его раб. ф-ий) последнего СЛО.
     *
     * @param hmcId    HospitalMedCase.id
     * @param aRequest HttpServletRequest
     * @return Boolean - true - юзер - лечащий врач, false - нет
     */
    public Boolean checkUserIsALastSloTreatDoctor(Long hmcId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String login = LoginInfo.find(aRequest.getSession(true)).getUsername();
        String query = "select case when (select ownerfunction_id from medcase where transferdate is null" +
                " and dtype='DepartmentMedCase' and parent_id=" + hmcId + ")" +
                " =ANY(select wf.id" +
                " from WorkFunction wf" +
                " left join WorkFunction gwf on gwf.id=wf.group_id" +
                " left join VocWorkFunction vwf on vwf.id=wf.workFunction_id" +
                " left join Worker w on w.id=wf.worker_id" +
                " left join MisLpu ml on ml.id=w.lpu_id" +
                " left join Worker sw on sw.person_id=w.person_id" +
                " left join WorkFunction swf on swf.worker_id=sw.id" +
                " left join SecUser su on su.id=swf.secUser_id" +
                " where su.login='" + login + "' and (wf.archival is null or wf.archival='0'))" +
                " then '1' else '0' end";
        Collection<WebQueryResult> list = service.executeNativeSql(query, 1);
        return !list.isEmpty() && list.iterator().next().get1().toString().equals("1");
    }

    /**
     * Проставить перед удалением выписки, что это в течение настроенного количества дней.
     *
     * @param hmcId    HospitalMedCase.id
     * @param aRequest HttpServletRequest
     * @return Boolean - true - в течение календарного дня, false - нет
     */
    private Boolean checkDischargeDay(Long hmcId, HttpServletRequest aRequest) throws NamingException, ParseException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

        boolean flag = false;
        String query = "select datefinish,dischargetime from medcase where id=" + hmcId;
        Collection<WebQueryResult> list = service.executeNativeSql(query);
        String datefinish = "", timedisharge = "";
        if (!list.isEmpty()) {
            WebQueryResult wqr = list.iterator().next();
            datefinish = wqr.get1() != null ? wqr.get1().toString() : "";
            timedisharge = wqr.get2() != null ? wqr.get2().toString() : "";
        }
        if (!datefinish.equals("") && timedisharge != null && !timedisharge.equals("")) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new java.util.Date();
            String dateCurrent = simpleDateFormat.format(now);

            String daysStr = getSettingsKeyValueByKey("daysDischargeNotCovid", aRequest);
            if (daysStr.equals(""))
                daysStr = "1";
            int days = -Integer.valueOf(daysStr);

            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            cal.add(Calendar.DATE, days);
            String dateBefore = simpleDateFormat.format(cal.getTime());

            flag = datefinish.equals(dateCurrent) || datefinish.equals(dateBefore);
        }
        return flag;
    }

    /**
     * Проверить перед удалением выписки, что это админ.
     *
     * @param aRequest HttpServletRequest
     * @return Boolean - true -админ, false - нет
     */
    private Boolean checkDeleteDischargeAdmin(HttpServletRequest aRequest) throws JspException {
        return RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Ssl/DeleteAdmin,/Policy/Mis/MedCase/Stac/Ssl/Delete", aRequest);
    }

    /**
     * Проставить перед удалением выписки, можно ли её удалить.
     * Админ может удалять админ что угодно
     * Неифекционные отделения: в течение календарного дня могут удалить: люди с ролью/лечащий врач
     *
     * @param hmcId    HospitalMedCase.id
     * @param aRequest HttpServletRequest
     * @return Boolean - true - в течение календарного дня, false - нет
     */
    public Boolean deleteDischargeCheck(Long hmcId, HttpServletRequest aRequest) throws JspException, NamingException, ParseException {
        boolean canDelete = checkDeleteDischargeAdmin(aRequest) ||  //админ
                (!checkMisLpuCovid(hmcId, aRequest) && checkDischargeDay(hmcId, aRequest) //неинфекционное и в течение одного каленарного дня
                        && (RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Ssl/DeleteDischargeOneDay", aRequest) //роль для удаления
                        || checkUserIsALastSloTreatDoctor(hmcId, aRequest))) //лечащий врач
                ;
        if (canDelete)
            deleteDischarge(hmcId, aRequest);
        return canDelete;
    }

    /**
     * Проставить перед удалением выписки, что это не ковидное отделение.
     *
     * @param hmcId    HospitalMedCase.id
     * @param aRequest HttpServletRequest
     * @return Boolean - true - это ковидное, false - не ковидное
     */
    private boolean checkMisLpuCovid(Long hmcId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String query = " select vbt.id from medcase sls" +
                " left join medcase sloAll on sloAll.parent_id=sls.id and sloAll.dtype='DepartmentMedCase'" +
                " left join Mislpu dep on dep.id=sloAll.department_id" +
                " left join bedfund as bf on bf.id=sloAll.bedfund_id" +
                " left join vocbedtype vbt on vbt.id=bf.bedType_id" +
                " where vbt.code='14' and sls.id=" + hmcId;
        return !service.executeNativeSql(query).isEmpty();
    }

    /**
     * Вывести список микробиологических исследований пациента с положительным результатом #91.
     *
     * @param dmcId    DepartmentMedCase.id
     * @param aRequest HttpServletRequest
     * @return String json с результатом
     */
    public String showMBioResResList(int dmcId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String sql = "select ms.code as name1,ms.name as name3,ms.shortname as shname,to_char(aslo.datestart,'dd.mm.yyyy') as dt" +
                " from diary d" +
                " left join forminputprotocol fipr on fipr.docprotocol_id=d.id and fipr.parameter_id=(select cast(keyvalue as int) from softconfig where key='FIP_parameterMicroBio')" +
                " left join uservalue uv on uv.id=fipr.valuevoc_id" +
                " left join medcase aslo on d.medcase_id=aslo.id and aslo.dtype='Visit'" +
                " left join medcase dmc on dmc.parent_id=aslo.parent_id" +
                " left join prescriptionlist pl on dmc.id=pl.medcase_id" +
                " left join prescription pr on pr.medcase_id=aslo.id" +
                " left join medservice ms on pr.medservice_id=ms.id" +
                " where d.dtype='Protocol' and dmc.DTYPE='DepartmentMedCase' and uv.cntball=1 and dmc.id=" + dmcId;
        Collection<WebQueryResult> list = service.executeNativeSql(sql);
        JSONArray res = new JSONArray();
        for (WebQueryResult w : list) {
            JSONObject o = new JSONObject();
            o.put("name1", w.get1())
                    .put("name3", w.get2())
                    .put("shname", w.get3() == null ? "" : w.get3())
                    .put("dt", w.get4());
            res.put(o);
        }
        return res.toString();
    }

    /**
     * Получить ФИО пациента и его стат. карту по dmc.id.
     *
     * @param dmcId    DepartmentMedCase.id
     * @param aRequest HttpServletRequest
     * @return String результат
     */
    public String getPatientFIOStat(int dmcId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql(
                "select pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename|| ' ' || to_char(pat.birthday,'dd.mm.yyyy')" +
                        " from medCase dmc" +
                        " left join MedCase as sls on sls.id = dmc.parent_id" +
                        " left join Patient pat on dmc.patient_id = pat.id " +
                        " where dmc.id=" + dmcId);
        return (list.isEmpty()) ? "" : list.iterator().next().get1().toString();
    }

    /**
     * Получить настройки по ключу.
     *
     * @param keyvalue Ключ
     * @param aRequest HttpServletRequest
     * @return String результат
     */
    public String getSettingsKeyValueByKey(String keyvalue, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select keyvalue from  softconfig where key='" + keyvalue + "'");
        return (list.isEmpty()) ? "" : list.iterator().next().get1().toString();
    }

    /**
     * Получить код потока обслуживания для отчётов jasper.
     *
     * @param name     Поток обслуживания
     * @param aRequest HttpServletRequest
     * @return String результат
     */
    public String getVocServiceStreamCodeByName(String name, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select code from  vocSstreamE2Entry where name='" + name + "'");
        return (list.isEmpty()) ? "" : list.iterator().next().get1().toString();
    }

    /**
     * Получить текст шаблона оценки риска по id шаблона aCardTemplId #97.
     *
     * @param aCardTemplId Поток обслуживания
     * @param aRequest     HttpServletRequest
     * @return String результат
     */
    public String getVocAssesmentCardById(String aCardTemplId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select name from assessmentCardTemplate where id=" + aCardTemplId);
        return (list.isEmpty()) ? "" : list.iterator().next().get1().toString();
    }

    /**
     * Проставить палату по умолчанию по отделению #101.
     *
     * @param slo      DepartmentMedCase.id
     * @param aRequest HttpServletRequest
     * @return String json с результатом
     */
    public String getDefaultWorkPlaceByDepartment(String slo, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql(
                "select wp.id as wpid,'№'||wp.name||' ('||wp1.name||')' as wpname, wp2.id as wp2id,wp2.name as wp2name" +
                        " from workplace wp" +
                        " left join WorkPlace wp1 on wp1.id=wp.parent_id" +
                        " left join workplace wp2 on wp2.parent_id=wp.id" +
                        " where wp.dtype='HospitalRoom' and (wp.isnoactuality is null or wp.isnoactuality='0')" +
                        " and wp2.dtype='HospitalBed' and (wp2.isnoactuality is null or wp2.isnoactuality='0')" +
                        " and wp.defaultroom=true" +
                        " and wp.lpu_id=" + slo, 1);
        JSONObject res = new JSONObject();
        if (!list.isEmpty()) {
            WebQueryResult w = list.iterator().next();
            res.put("wpid", w.get1())
                    .put("wpname", w.get2())
                    .put("wp2id", w.get3())
                    .put("wp2name", w.get4());
        }
        return res.toString();
    }

    /**
     * Обязательно ли заполнение услуги в дневнике? #122.
     * Обязательно, если:
     * это в СЛО
     * поток облуживания платный/ДМС
     * отделение создающего дневник не совпадает с отделением СЛО
     *
     * @param medCaseId MedCase.id
     * @param aRequest  HttpServletRequest
     * @return String "1" - необходима, "0" - нет
     */
    public String getMedServiceNecessaryInDiary(Long medCaseId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String sql = "select case when (ss.code='PRIVATEINSURANCE' or ss.code='CHARGED') and mc.dtype='DepartmentMedCase'" +
                " and ml.id<>mc.department_id" +
                " then '1' else '0' end" +
                " from medcase mc" +
                " left join vocservicestream ss on mc.servicestream_id=ss.id" +
                " left join secuser su on su.login='" + LoginInfo.find(aRequest.getSession(true)).getUsername() +
                "' left join workfunction wf on su.id=wf.secuser_id" +
                " left join Worker w on w.id=wf.worker_id" +
                " left join MisLpu ml on ml.id=w.lpu_id" +
                " where  mc.id=" + medCaseId;
        Collection<WebQueryResult> l = service.executeNativeSql(sql);
        return !l.isEmpty() && l.iterator().next().get1().toString().equals("1") ? "1" : "0";
    }

    /**
     * Получить id потока обслуживания в СЛО #122.
     *
     * @param medCaseId MedCase.id
     * @param aRequest  HttpServletRequest
     * @return String VocServiceStream.id
     */
    public String getSstreamId(Long medCaseId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String sql = "select ss.id from medcase mc" +
                " left join vocservicestream ss on mc.servicestream_id=ss.id" +
                " where  mc.id=" + medCaseId;
        Collection<WebQueryResult> l = service.executeNativeSql(sql);
        return !l.isEmpty() ? l.iterator().next().get1().toString() : "";
    }

    /**
     * Получить dtype medcase для экспертных карт
     *
     * @param aMedcaseId MedCase.id
     * @return String (0 - hospital, 1 - dep, 2 - visit, 3 - другое)
     */
    public String getMedcaseDtype(Long aMedcaseId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> l = service.executeNativeSql("select case when mc.dtype='HospitalMedCase' then '0'" +
                " else case when mc.dtype='DepartmentMedCase' then '1'" +
                " else case when mc.dtype='Visit' then '2' else '0' end end end" +
                " from assessmentcard  ac" +
                " left join medcase mc on mc.id=ac.medcase_id" +
                " where ac.medcase_id=" + aMedcaseId);
        return l.isEmpty() ? "" : l.iterator().next().get1().toString();
    }

    /**
     * Получить dtype medcase
     *
     * @param aMedcaseId MedCase.id
     * @return String (0 - hospital, 1 - dep, 2 - visit, 3 - polyclinic, 4 - short, 5 - service, -1 - другое)
     */
    public String getMedcaseDtypeById(Long aMedcaseId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> l = service.executeNativeSql("select case when mc.dtype='HospitalMedCase' then '0'" +
                " else case when mc.dtype='DepartmentMedCase' then '1'" +
                " else case when mc.dtype='Visit' then '2'" +
                " else case when mc.dtype='PolyclinicMedCase' then '3'" +
                " else case when mc.dtype='ShortMedCase' then '4'" +
                " else case when mc.dtype='ServiceMedCase' then '5' else '-1' end end end end end end" +
                " from medcase mc where mc.id=" + aMedcaseId);
        return l.isEmpty() ? "" : l.iterator().next().get1().toString();
    }

    /**
     * Получить кол-во дней с начала СЛС при создании дневника в приёмнике #135
     *
     * @param aMedcaseId HospitalMedCase.id
     * @return String Кол-во дней/NULL если есть отказ от госпитализации
     */
    public String getSlsCountDays(Long aMedcaseId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> l = service.executeNativeSql(
                "select cast(round((EXTRACT(EPOCH FROM current_timestamp)-(SELECT EXTRACT(EPOCH FROM (mc.datestart + mc.entrancetime))  " +
                        "from medcase mc where id=" + aMedcaseId + "  and deniedhospitalizating_id is null))/3600/24) as int)");
        return (!l.isEmpty() && l.iterator().next().get1() != null) ? l.iterator().next().get1().toString() : "";
    }

    /**
     * Получить кол-во дней с начала СЛО и СЛС при создании дневника в СЛО #135
     *
     * @param aMedcaseId DepartmentMedCase.id
     * @return String json Кол-во дней
     */
    public String getSloCountDays(Long aMedcaseId, HttpServletRequest aRequest) throws NamingException {
        JSONObject res = new JSONObject();
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select cast(round((EXTRACT(EPOCH FROM current_timestamp)" +
                "-(SELECT EXTRACT(EPOCH FROM (hmc.datestart + hmc.entrancetime))  from medcase hmc where hmc.id=dmc.parent_id ))/3600/24) as int) as t1" +
                " ,cast(round((EXTRACT(EPOCH FROM current_timestamp)-(SELECT EXTRACT(EPOCH FROM (dmc.datestart + dmc.entrancetime))  " +
                " from medcase dmc where dmc.id=" + aMedcaseId + "))/3600/24) as int) as t2" +
                " from medcase dmc" +
                " left join medcase hmc on hmc.id=dmc.parent_id" +
                " where dmc.id=" + aMedcaseId);
        if (!list.isEmpty()) {
            WebQueryResult w = list.iterator().next();
            res.put("hmcCnt", w.get1())
                    .put("dmcCnt", w.get2());
        }
        return res.toString();
    }

    /**
     * Получить браслеты пациента в госпитализации или в персоне #151
     *
     * @param aSlsOrPatId Sls.id or Patient.id
     * @param aSlsOrPat   1 - СЛС, 0 - пациент
     * @return String json Браслеты пациента
     */
    public String selectIdentityPatient(Long aSlsOrPatId, boolean aSlsOrPat, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String sql = "select cip.id,vc.name||' ('||vcip.name||')',vc.code as colorCode,vcip.name as vsipnameJust" +
                ",vc.picture as pic, cip.info as inf,case when vcip.isforpatology then '1' else '0' end as isforpat " +
                "from vocColorIdentityPatient vcip " +
                "left join coloridentitypatient cip on cip.voccoloridentity_id=vcip.id " +
                "left join voccolor vc on vcip.color_id=vc.id " +
                (aSlsOrPat ? "left join medcase_coloridentitypatient " : "left join patient_coloridentitypatient ") +
                " ss on ss.colorsidentity_id=cip.id " +
                " where " +
                (aSlsOrPat ? "(ss.medcase_id='" : "ss.patient_id='") +
                aSlsOrPatId + "'" +
                (aSlsOrPat ? "or ss.medcase_id=(select parent_id from medcase where id='" : "") +
                (aSlsOrPat ? aSlsOrPatId : "") + (aSlsOrPat ? "'))" : "") +
                " and (cip.startdate<=current_date and cip.finishdate is null " +
                " or (cast ((cip.finishdate||' '||cip.finishtime) as TIMESTAMP) > current_timestamp))" +
                " order by cip.startDate,cip.id";
        return service.executeNativeSqlGetJSON(new String[]{"vcipId", "vsipName", "colorCode", "vsipnameJust"
                , "picture", "info", "isforpat"}, sql, null);
    }

    /**
     * Добавить браслет пациенту/СЛС #151
     *
     * @param aSlsOrPatId  Sls.id or Patient.id
     * @param aSlsOrPat    1 - СЛС, 0 - пациент
     * @param idP          vocColorIdentityPatient.id
     * @param daysToFinish Через сколько дней браслет снять
     * @param aRequest     the a request
     * @return void
     * @throws NamingException the naming exception
     */
    public void addIdentityPatient(Long aSlsOrPatId, Boolean aSlsOrPat, Long idP, int daysToFinish, Long tempCurveId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String login = LoginInfo.find(aRequest.getSession(true)).getUsername();
        StringBuilder sql = new StringBuilder();
        String id = "";
        if (tempCurveId == 0)
            tempCurveId = null;
        sql.append("insert into coloridentitypatient(startdate,starttime,finishdate,finishtime,voccoloridentity_id,createusername,tempCurve_id)" +
                " values(current_date,current_time,")
                .append(daysToFinish > 0 ? "current_date + " + daysToFinish + ",current_time," : "null,null,")
                .append(idP).append(",'")
                .append(login)
                .append("',").append(tempCurveId)
                .append(") returning id");
        Collection<WebQueryResult> res = service.executeNativeSql(sql.toString());
        for (WebQueryResult wqr : res) {
            id = wqr.get1().toString();
        }
        if (!id.equals("")) {
            sql = new StringBuilder();
            if (Boolean.TRUE.equals(aSlsOrPat))
                sql.append("insert into medcase_coloridentitypatient(medcase_id,colorsidentity_id) values")
                        .append(" ((case when (select dtype from medcase where id=")
                        .append(aSlsOrPatId).append(") = 'HospitalMedCase' then ")
                        .append(aSlsOrPatId).append(" else (select parent_id from medcase where id=")
                        .append(aSlsOrPatId).append(") end), ").append(id).append(")");
            else
                sql.append("insert into patient_coloridentitypatient(patient_id,colorsidentity_id) values(")
                        .append(aSlsOrPatId).append(",").append(id).append(")");
            service.executeUpdateNativeSql(sql.toString());
        }
    }

    /**
     * Удалить браслет у пациента/СЛС (проставить дату окончания) #151
     *
     * @param aColorIdentityId ColorIdentityPatient.id
     * @return void
     */
    public String deleteIdentityPatient(Long aColorIdentityId, HttpServletRequest aRequest) throws NamingException, JspException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String login = LoginInfo.find(aRequest.getSession(true)).getUsername();
        Collection<WebQueryResult> l = service.executeNativeSql("select case when voc.isdeniedmanual='1' then 0 else 1 end" +
                " from coloridentitypatient c" +
                " left join voccoloridentitypatient voc on voc.id=c.voccoloridentity_id" +
                " where c.id = " + aColorIdentityId);
        if (!l.isEmpty() && l.iterator().next().get1() != null && l.iterator().next().get1().toString().equals("0")
                && !RolesHelper.checkRoles("/Policy/Mis/ColorIdentityEdit/AdminCancel", aRequest))
            return "Только администратор системы может снять этот браслет!";
        else {
            service.executeUpdateNativeSql("update coloridentitypatient set finishdate=current_date, finishtime=current_time,editusername='" + login + "' where id=" + aColorIdentityId);
            return "";
        }
    }

    /**
     * Получить, была ли проведена идентификация #173
     *
     * @param aMedCaseId Medcase.id (СЛС или СЛО)
     * @return String 1 - была проведена идентификация, 0 - нет
     */
    public String getIsPatientIdentified(Long aMedCaseId, HttpServletRequest aRequest) throws NamingException, JspException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        if (!RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/PatientIdentify", aRequest)) return "1";
        Collection<WebQueryResult> l = service.executeNativeSql
                ("select case when max(cast(isidentified as int))=1 then '1' else '0' end from medcase where id='" + aMedCaseId + "' or id=(select parent_id from medcase where id='" + aMedCaseId + "')");
        return (!l.isEmpty() && l.iterator().next().get1() != null) ? l.iterator().next().get1().toString() : "";
    }

    /**
     * Проставить, что была проведена идентификация #173
     *
     * @param aMedCaseId Medcase.id (СЛС или СЛО)
     */
    public void setIsPatientIdentified(Long aMedCaseId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String login = LoginInfo.find(aRequest.getSession(true)).getUsername();
        service.executeUpdateNativeSql
                ("update medcase set isidentified='1', identdate=current_date,identtime=current_time,identusername='"
                        + login + "' where id='" + aMedCaseId + "' or id=(select parent_id from medcase where id='" + aMedCaseId + "')");
    }

    /**
     * Получить id карты оценки по коду
     *
     * @param aCode Code
     * @return String
     */
    public String getDiabetCardByCode(String aCode, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> l = service.executeNativeSql("select id from assessmentCardTemplate where code='" + aCode + "'");
        return (!l.isEmpty() && l.iterator().next().get1() != null) ? l.iterator().next().get1().toString() : "";
    }


    /**
     * Получить офтальмологическое отделение
     *
     * @return
     */
    public String getOpthalmicDep(HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> l = service.executeNativeSql("select id from mislpu where isophthalmic=true");
        return l.isEmpty() ? "" : l.iterator().next().get1().toString();
    }

    /**
     * Проверка на количество предв. госпитализаций в день
     *
     * @param aPreId   Long Предв. госп.
     * @param dateFrom String Дата предв. госп.
     * @returns maxCnt - макс. кол-во в день, если лимит превышен
     *                  0 - если нет
     */
    private int checkPatientCountPerDay(Long aPreId, String dateFrom, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String sql = "select count(pre.id)" +
                " from WorkCalendarHospitalBed pre" +
                " left join mislpu lpu on lpu.id=pre.department_id" +
                " where pre.datefrom = to_date('" + dateFrom + "','dd.MM.yyyy')" +
                " and lpu.id=(select department_id from WorkCalendarHospitalBed where id=" + aPreId + ") and lpu.isophthalmic";
        Collection<WebQueryResult> l = service.executeNativeSql(sql);
        if (!l.isEmpty()) {
            int cnt = Integer.parseInt(l.iterator().next().get1().toString());
            int maxCnt = Integer.parseInt(getDefaultParameterByConfig("patientCountPreHospPerDay", "20", aRequest).toString());
            if (cnt >= maxCnt)
                return maxCnt;
        }
        return 0;
    }

    /**
     * Установить дату предварительной госп на введении инг. анг. #181
     *
     * @param aPreId   Long Предв. госп.
     * @param dateFrom String Дата предв. госп.
     */
    public void setPreHospOphtDate(Long aPreId, String dateFrom, HttpServletRequest aRequest) throws NamingException {
        int maxCnt = checkPatientCountPerDay(aPreId, dateFrom, aRequest);
        if (maxCnt == 0) {
            IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
            Collection<WebQueryResult> list = service.executeNativeSql("select day from datepreopht where day=to_date('" + dateFrom + "','dd.MM.yyyy')");
            if (list.isEmpty()) {
                String login = LoginInfo.find(aRequest.getSession(true)).getUsername();
                service.executeUpdateNativeSql
                        ("update WorkCalendarHospitalBed set dateFrom=to_date('" + dateFrom + "','dd.mm.yyyy'), editdate=current_date,edittime=current_time,editusername='"
                                + login + "' where id=" + aPreId);
            }
            else
                throw new IllegalArgumentException("Этот день недоступен для предварительной госпитализации в офтальмологическое отделение!");
        }
        else
            throw new IllegalArgumentException("На этот день в этом отделении уже создано максимально допустимое количество ("
                    + maxCnt + ")  предварительных госпитализаций. Выберите другую дату.");
    }

    /**
     * Получить, необходимо ли при переводе заполнять карту по нозологиям в акушерстве
     * Необходимо, в случае перевода и родового или патологии беременности
     *
     * @param aSloId DepartmentMedCase.id
     * @return 0 если необходимо
     */
    public String checkNessessaryTransferNosologyCard(String aSloId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> l = service.executeNativeSql("select case when lpu.ismaternityward=true or lpu.ispatologypregnant is true then '0' else '' end" +
                " from medcase dmc" +
                " left join mislpu lpu on lpu.id=dmc.department_id" +
                " where dmc.id=" + aSloId);
        return l.isEmpty() ? "" : l.iterator().next().get1().toString();
    }

    /**
     * Получить, необходимо ли при выписке заполнять карту по нозологиям в акушерстве
     * Необходимо, в случае выписки из патологии беременности (последнее отделение в СЛС)
     *
     * @param aSlsId DepartmentMedCase.id
     * @return 0 если необходимо
     */
    public String checkNessessaryDischargeNosologyCard(String aSlsId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> l = service.executeNativeSql("select case when lpu.ispatologypregnant is true then '0' else '' end" +
                " from medcase dmc" +
                " left join mislpu lpu on lpu.id=dmc.department_id" +
                " left join medcase hmc on hmc.id=dmc.parent_id" +
                " where hmc.id=" + aSlsId + " and dmc.id = (select max(id) from medcase " +
                " where dtype='DepartmentMedCase' and parent_id=" + aSlsId + ")");
        return l.isEmpty() ? "" : l.iterator().next().get1().toString();
    }

    /**
     * Вывести данные для сопутствующих диагнозов в карте нозологий #185.
     *
     * @param aSlsId   HospitalMedCase.id
     * @param aRequest HttpServletRequest
     * @return String json с результатом
     */
    public String getConcomitantDiagnosisFromNosCard(Long aSlsId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String sql = "select idc.id as id,idc.code||' '||idc.name as idcname" +
                " from birthnosologycard_vocbirthnosology bb" +
                " left join vocbirthnosology vb on vb.id=bb.nosologies_id" +
                " left join vocidc10 idc on idc.id=vb.idcCode_id" +
                " left join birthnosologycard b on b.id=bb.birthnosologycard_id" +
                " where b.medcase_id=" + aSlsId;
        Collection<WebQueryResult> list = service.executeNativeSql(sql);
        JSONArray res = new JSONArray();
        for (WebQueryResult w : list) {
            JSONObject o = new JSONObject();
            o.put("idcId", w.get1())
                    .put("idcName", w.get2());
            res.put(o);
        }
        return res.toString();
    }

    /**
     * Получить все осложнения в json.
     *
     * @param surgOperId SurgicalOperation.id
     * @return String Выборка в json
     * @throws NamingException,SQLException
     */
    public String getCompJson(String surgOperId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return service.executeSqlGetJson("select to_char(c.datecomp,'dd.mm.yyyy') as date,c.compreasonstring as rtext" +
                " ,c.complicationstring as ctext,c.complication_id as cid" +
                " , vc.name as cname from surgcomplication c" +
                " left join voccomplication vc on vc.id=c.complication_id" +
                " where surgicaloperation_id=" + surgOperId);
    }

    /**
     * Получить, есть ли браслет у услуги для операции.
     *
     * @param aMedServiceId MedService.id
     * @return Boolean true - если есть
     * @throws NamingException
     */
    public boolean isMedServiceGotBracelet(Long aMedServiceId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return !service.executeNativeSql("select id from medservice where id=" + aMedServiceId
                + " and VocColorIdentity_id is not null ").isEmpty();
    }

    /**
     * Получить информацию о свободных местах в госпитале.
     *
     * @param aRequest HttpServletRequest
     * @return String json c результатом
     */
    public String getFreeHospBedInfo(HttpServletRequest aRequest) throws NamingException, JspException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String login = LoginInfo.find(aRequest.getSession(true)).getUsername();
        boolean isWatchAll = RolesHelper.checkRoles("/Policy/Mis/Journal/ShowHospitalFreeInfo/All", aRequest);
        String sql = "select distinct fhb.lpu as id, ml.name as lpu, fhb.meno2 as meno2" +
                " ,fhb.mennoo2 as mennoo2,fhb.womeno2 as womeno2,fhb.womennoo2 as womennoo2" +
                " from worker w" +
                " left join workfunction wf on wf.worker_id =w.id " +
                " left join secuser su on su.id=wf.secuser_id" +
                " left join freehospbed fhb on fhb.lpu=w.lpu_id" +
                " left join mislpu ml on ml.id=w.lpu_id where ";
        sql += isWatchAll ?
                " ml.isforcovid=true and ml.id not in(512,501,507,499)" :
                " login='" + login + "'";
        sql += " order by fhb.lpu";
        return service.executeNativeSqlGetJSON(new String[]{"id", "lpu", "meno2", "mennoo2", "womeno2", "womennoo2"}, sql, 100);
    }


    /**
     * Преобразовать элемент в json в строку.
     *
     * @param obj   JSONObject
     * @param alias Название элемента
     * @return String Строковое представление/null
     * @throws NamingException
     */
    private String getString(JSONObject obj, String alias) {

        return obj.has(alias)
                ? (obj.get(alias).toString().equals("") ? null : obj.get(alias).toString()) : null;
    }

    /**
     * Сохранить данные по доступным местам в госипталях.
     *
     * @param json значения
     * @throws NamingException,JSONException
     */
    public void saveFreeHospBedInfo(String json, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String login = LoginInfo.find(aRequest.getSession(true)).getUsername();
        JSONObject obj = new JSONObject(json);
        String js = getString(obj, "list");
        JSONArray arr = new JSONArray(js);
        arr.forEach(element -> {
            JSONObject objEl = (JSONObject) element;
            String id = getString(objEl, "id");
            String meno2 = getString(objEl, "meno2");
            String mennoo2 = getString(objEl, "mennoo2");
            String womeno2 = getString(objEl, "womeno2");
            String womennoo2 = getString(objEl, "womennoo2");
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE freehospbed set meno2=").append(meno2)
                    .append(", mennoo2=").append(mennoo2)
                    .append(", womeno2=").append(womeno2)
                    .append(", womennoo2=").append(womennoo2)
                    .append(", editdate=current_date, edittime=current_time, editusername = '").append(login).append("'")
                    .append(" where lpu = ").append(id);
            service.executeUpdateNativeSql(sql.toString());
        });
    }

    /**
     * Получить id существующего в СЛС чек-листа
     *
     * @param aPatientId  Patient.id
     * @param aMedcaseId  Medcase.id
     * @param aTypeCardId AssessmentCardTemplate.id
     * @return CovidMark.id
     */
    public String getIdIfAsCard1011AlreadyExists(Long aPatientId, Long aMedcaseId, Long aTypeCardId, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select id from assessmentcard a where patient=" + aPatientId +
                " and medcase_id=" + aMedcaseId + " and template=" + aTypeCardId + " limit 1");
        return list.isEmpty() ? "" : list.iterator().next().get1().toString();
    }

    /**
     * Получить, является ли отделение офтальмологическим
     *
     * @param aDepartmentId  Department.id
     * @return пустая строка - не является
     */
    public String checkIsDepOpht(Long aDepartmentId, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select id from mislpu where isophthalmic=true and id=" + aDepartmentId);
        return list.isEmpty() ? "" : list.iterator().next().get1().toString();
    }

    /**
     * Открыть день для планирования госпитализаций в офтальмологическое отделение
     *
     * @param day  String
     * @return Cообщение String
     */
    public String openDayOphtPreHosp(String day, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        if (checkDayOphtOpened(day, request))
            return "День уже был открыт (все дни по умолчанию открыты)";
        service.executeUpdateNativeSql("delete from datepreopht where day=to_date('" + day + "','dd.MM.yyyy')");
        return "";
    }

    /**
     * Открыть день для планирования госпитализаций в офтальмологическое отделение
     *
     * @param day  String
     * @return Cообщение String
     */
    public String closeDayOphtPreHosp(String day, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        if (!checkDayOphtOpened(day, request))
            return "День уже был закрыт!";
        Collection<WebQueryResult> listPre = service.executeNativeSql("select b.id from workcalendarhospitalbed b" +
                " left join mislpu dep on dep.id=b.department_id " +
                " where datefrom=to_date('" + day + "','dd.MM.yyyy') and dep.isophthalmic=true");
        if (!listPre.isEmpty())
            return "Уже есть предварительная госпитализация на эту дату в офтальмологическое отделение!";
        service.executeUpdateNativeSql("insert into datepreopht(day) values(to_date('" + day + "','dd.MM.yyyy'))");
        return "";
    }

    /**
     * Открыт ли день для плановой госпитализации
     *
     * @param day  boolean
     * @return true - день открыт, false - день закрыт
     */
    private boolean checkDayOphtOpened(String day, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select day from datepreopht where day=to_date('" + day + "','dd.MM.yyyy')");
        return list.isEmpty();
    }
}