package ru.ecom.api.queue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.api.util.ApiUtil;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/queue/hospital")
public class HospitalQueueResource {

    @GET
    @Path("emergencyQueue")
    @Produces(MediaType.APPLICATION_JSON)
    /** Список пациентов, поступивших и состоящих в приемном отделении */
    public String getEmergencyHospitalQueue(@Context HttpServletRequest aRequest, @QueryParam("token") String token, @QueryParam("emergency") String isEmergency) throws NamingException, JSONException {
        ApiUtil.login(token,aRequest);
        String sql = " select sls.id, pat.lastname||' '|| substring (pat.firstname,1,1)||' '||substring (pat.middlename,1,1)" +
                " ,cast(cast(current_timestamp - cast ((sls.dateStart||' '||sls.entranceTime) as timestamp) as time)as varchar(5)) as waitTime" +
                " ,to_char(sls.dateStart,'dd.MM.yyyy')||' '||cast(sls.entranceTime as varchar(5)) as startDateTime"+
                ",cast(extract(epoch from age(current_timestamp,cast(sls.dateStart||' '||sls.entranceTime as timestamp)))/60 as int) as minutesCount "+
                " from medcase sls " +
                " left join patient pat on pat.id=sls.patient_id" +
                " left join medcase slo on slo.parent_id = sls.id and slo.dtype='DepartmentMedCase'" +
                " where sls.dtype='HospitalMedCase'" +
                " and sls.deniedhospitalizating_id is null and slo.id is null" +
                (isEmergency!=null&&isEmergency.equals("0")?" and (sls.emergency is null or sls.emergency='0')":" and sls.emergency='1'") +
                " order by sls.dateStart, sls.entranceTime ";
        System.out.println(sql);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String[] fields = {"id","patientInfo","waitTime","startTime","minutes"};
        String ret =service.executeNativeSqlGetJSON(fields,sql,null);
        return ret;
        /*JSONArray arr  = new JSONArray(ret);
        if (arr.length()>0) {
            return arr.getJSONObject(0);
        }
    return new JSONObject("{\"status\":\"error\"}");*/
    }
}
