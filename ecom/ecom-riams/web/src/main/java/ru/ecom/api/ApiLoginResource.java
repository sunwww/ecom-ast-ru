package ru.ecom.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.api.util.ApiUtil;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Path("login")
public class ApiLoginResource {
    @GET
    @Path("makeTimurHappy")
    @Produces(MediaType.APPLICATION_JSON)
    /*Найдем информация для Тимура. ТЕСТ*/
    public String makeTimurHappy(@Context HttpServletRequest aRequest, @QueryParam("patientId") Long aPatientId, @QueryParam("token") String token) throws NamingException {
        ApiUtil.login(token,aRequest);
        String sql = "select pat.lastname , pat.birthday, sex.name , ss.weight , ss.height , ss.imt , sls.datestart,  sls.id" +
                " from patient pat " +
                " left join vocsex sex on sex.id=pat.sex_id " +
                " left join medcase sls on sls.patient_id = pat.id and sls.dtype='HospitalMedCase' and sls.deniedhospitalizating_id is null" +
                " left join statisticstub ss on ss.medcase_id = sls.id" +
                " where pat.id="+aPatientId+" and sls.id is not null" +
                " order by sls.datestart desc";
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> patientInfo = service.executeNativeSql(sql,1);
        JSONObject ret = new JSONObject();
        if (!patientInfo.isEmpty()) {
            WebQueryResult wqr = patientInfo.iterator().next();
            ret.put("lastname", toS(wqr.get1()));
            ret.put("sex", toS(wqr.get3()));
            ret.put("birthday",toS(wqr.get2()));
            ret.put("weight",toS(wqr.get4()));
            ret.put("height",toS(wqr.get5()));
            ret.put("imt",toS(wqr.get6()));
            String medcaseId = toS(wqr.get8());
            sql = "select mc.id, list(ms.code) as cde, list(ms.name) as nme , to_char(mc.datestart,'dd.MM.yyyy') as f4_dat " +
                    " ,(select json_array(list('value#'||fip.valuebd||'#name#'||par.name||'#code#'||coalesce(par.externalcode, par.code)),'#')) as f5_cd" +
                    " , to_char(mc.timeexecute, 'HH24:MI') as f6_startTime" +
                    " from medcase sls " +
                    " left join medcase slo on slo.parent_id = sls.id and slo.dtype='DepartmentMedCase'" +
                    " left join prescriptionList pl on pl.medcase_id = slo.id or pl.medcase_id = sls.id" +
                    " left join prescription p on p.prescriptionlist_id = pl.id " +
                    " left join medservice ms on ms.id=p.medservice_id" +
                    " left join vocservicetype vst on vst.id=ms.servicetype_id" +
                    " left join medcase mc on mc.id=p.medcase_id" +
                    " left join diary d on d.medcase_id=mc.id" +
                    " left join FormInputProtocol fip on fip.docprotocol_id = d.id" +
                    " left join parameter par on par.id=fip.parameter_id" +
                    " where sls.id = "+medcaseId+" and sls.dtype='HospitalMedCase' and ms.id is not null and mc.datestart is not null" +
                    " group by mc.id" +
                    " order by mc.datestart desc";
            Collection<WebQueryResult> surveyInfo = service.executeNativeSql(sql);
           // ArrayList<String> servicesCode = new ArrayList<>();
            JSONArray ser = new JSONArray();
            for (WebQueryResult r : surveyInfo) {
                String sCode = toS(r.get2());
            //    if (!servicesCode.contains(sCode)) {
                    JSONObject s = new JSONObject();
                    s.put("serviceCode", sCode);
                    s.put("serviceName", toS(r.get3()));
                    s.put("serviceDate", toS(r.get4()));
                    s.put("serviceTime", toS(r.get6()));
                    s.put("result", new JSONArray(toS(r.get5())));
                //    servicesCode.add(sCode);
                    ser.put(s);
            //    }
            }
            ret.put("servicesArray", ser);
            ret.put("status", "ok");
        } else {
            ret.put("status", "error");
            ret.put("errorName", "Не найдено госпитализаций по пациенту или типа того");
        }


        return ret.toString();

    }

    @GET
    @Path("login/{token}")
    public String login(@Context HttpServletRequest aRequest, @PathParam("token") String login) {
        ApiUtil.login(login, aRequest);
        return "404 :-)";
    }

    @GET
    @Path("logout")
    public String logout(@Context HttpServletRequest aRequest) {
        ApiUtil.logout(aRequest);
        return "404 :-(";

    }

    @GET
    @Path("getChildCount")
    public String getChildCount(@Context HttpServletRequest aRequest, @QueryParam("token") String token, @QueryParam("date") String aDate) throws NamingException, JSONException {
        ApiUtil.login(token,aRequest);
        String tmpDate;
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        if (aDate==null||aDate.equals("")) {
            tmpDate=df.format(new Date());
            aDate=" nb.birthdate = current_date";
        } else if (aDate.equals("-1")){
            tmpDate= df.format(new Date(System.currentTimeMillis()-24*60*60*1000));
            aDate=" nb.birthdate = current_date-1";
        } else if (aDate.split("-").length>1) {
            tmpDate= aDate.split("-")[0];
            aDate=" nb.birthdate between to_date('"+aDate.split("-")[0]+"','dd.MM.yyyy') and to_date('"+aDate.split("-")[1]+"','dd.MM.yyyy')";
        } else {
            tmpDate=aDate;
            aDate=" nb.birthdate = to_date('"+aDate+"','dd.MM.yyyy')";
        }
        String sql = "select to_char(nb.birthdate, 'dd.MM.yyyy') as bDate" +
                " ,count (case when vs.omccode='1' then nb.id else null end) as cntMale" +
                " ,count (case when vs.omccode='2' then nb.id else null end) as cntFeMale" +
                " from newBorn nb" +
                " left join vocSex vs on vs.id=nb.sex_id" +
                " left join vocLiveBorn vlb on vlb.id=nb.liveborn_id" +
                " where "+aDate+" and vlb.code='1'  " +
                " group by nb.birthdate" +
                " order by nb.birthdate ";
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        JSONArray arr= new JSONArray(service.executeNativeSqlGetJSON(new String[]{"date","male","female"},sql,10));
        StringBuilder ret = new StringBuilder();
        if (arr.length()>0) {
            for (int i=0;i<arr.length();i++) {
                JSONObject obj = arr.getJSONObject(i);
                ret.append("newBornSize=").append(arr.length()).append(";birthDate=").append(obj.getString("date")).append(";birthMale=").append(obj.getString("male")).append(";birthFeMale=").append(obj.getString("female")).append("\r");
            }
        }
        else
            ret.append("newBornSize=").append("1").append(";birthDate=").append(tmpDate).append(";birthMale=").append("0").append(";birthFeMale=").append("0").append("\r");
        return ret.toString();
    }

    private String toS(Object o ){
        return o!=null ? o.toString() : "";
    }
}
