package ru.ecom.api.queue;

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
    /** Список пациентов, поступивших и состоящих в приемном отделении
     * token - Токен
     * emergency - Экстренно / планово
     * pigeonHole - код приемника */
    public String getEmergencyHospitalQueue(@Context HttpServletRequest aRequest
            , @QueryParam("token") String token
            , @QueryParam("emergency") String isEmergency
            ,@QueryParam("pigeonHole") String pigeonHole
    ) throws NamingException {
        ApiUtil.login(token,aRequest);
        if (isEmergency==null) {isEmergency="";}
        String sql = " select sls.id, pat.lastname||' '|| substring (pat.firstname,1,1)||' '||substring (pat.middlename,1,1)" +
                " ,cast(cast(coalesce(cast((sls.transferdate||' '|| sls.transfertime) as timestamp),current_timestamp) - cast ((sls.dateStart||' '||sls.entranceTime) as timestamp) as time)as varchar(5)) as waitTime" +
                " ,to_char(sls.dateStart,'dd.MM.yyyy')||' '||cast(sls.entranceTime as varchar(5)) as startDateTime"+
                ",cast(extract(epoch from age(current_timestamp,cast(sls.dateStart||' '||sls.entranceTime as timestamp)))/60 as int) as minutesCount " +
                ", dep.name as departmentName"+
                " from medcase sls " +
                " left join patient pat on pat.id=sls.patient_id" +
                " left join medcase slo on slo.parent_id = sls.id and slo.dtype='DepartmentMedCase'" +
                " left join mislpu dep on dep.id=sls.department_id" +
                " left join vocpigeonhole vph on vph.id=dep.pigeonhole_id " +
                " left join VocDeniedHospitalizating vdh on vdh.id=sls.deniedHospitalizating_id" +
                " where sls.dtype='HospitalMedCase'" +
                " and (sls.deniedhospitalizating_id is null or vdh.code='IN_PIGEON_HOLE') and slo.id is null" +
                " and sls.transferdate is null and sls.transfertime is null" +
                (isEmergency.equals("0")?" and (sls.emergency is null or sls.emergency='0')":isEmergency.equals("1")?" and sls.emergency='1'":"")+

                (pigeonHole!=null&&!pigeonHole.equals("")?" and vph.code='"+pigeonHole+"'":"") +
                " order by sls.dateStart, sls.entranceTime ";
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String[] fields = {"id","patientInfo","waitTime","startTime","minutes", "departmentName"};
        return service.executeNativeSqlGetJSON(fields,sql,null);
    }
}
