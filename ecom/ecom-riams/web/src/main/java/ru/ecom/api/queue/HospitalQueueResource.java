package ru.ecom.api.queue;

import org.apache.log4j.Logger;
import org.json.JSONArray;
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
    private static final Logger LOG = Logger.getLogger(HospitalQueueResource.class);

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
            , @QueryParam("pigeonHole") String pigeonHole
            , @QueryParam("startDate") String aStartDate
            , @QueryParam("finishDate") String aFinishDate
            , @QueryParam("isDoctor") Boolean isDoctor
            , @QueryParam("onlyDenied") String onlyDenied
    ) throws NamingException {
        ApiUtil.login(token,aRequest);
        if (isEmergency==null) {isEmergency="";}
        String sql = " select sls.id, "+(isDoctor ? "pat.lastname||' '|| substring (pat.firstname,1,1)||' '||substring (pat.middlename,1,1)" : "left(pat.lastname,1)||'-'||right(''||pat.id,3)") +
                (isDoctor ?
                        " ,cast(cast(coalesce(cast((sls.transferdate||' '|| sls.transfertime) as timestamp),current_timestamp) - cast ((sls.dateStart||' '||sls.entranceTime) as timestamp) as time)as varchar(5))"
                        : ", cast('' as varchar)") +" as waitTime" + //только для врачей
                (isDoctor ? " ,to_char(sls.dateStart,'dd.MM.yyyy')||' '||cast(sls.entranceTime as varchar(5)) " : ", cast('' as varchar)") +" as startDateTime"+ //только для врачей
                ",cast(extract(epoch from age(coalesce(cast((sls.transferdate||' '|| sls.transfertime) as timestamp),current_timestamp),cast(sls.dateStart||' '||sls.entranceTime as timestamp)))/60 as int) as minutesCount " +
                (isDoctor ? ", dep.name as departmentName" : ", cast('' as varchar)")+
                ",list (case when p.dtype='WfConsultation' and p.diary_id is not null then 'Консультация: '||consVwf.name||'<br>' " +
                "   when vis.datestart is not null then coalesce(ms.name,'')||'<br>' end) || list('Осмотр: '||vwf.name||'<br>') as madeServices" +
                (isDoctor ? ",list ( case when p.dtype='WfConsultation' then" +
                        " case when p.diary_id is not null then '<s>Консультация: '||consVwf.name||'</s><br>' else 'Консультация: '||consVwf.name||'<br>' end  " +
                        " when vis.datestart is not null then '<s>'||coalesce(ms.name,'')||'</s>' else coalesce(ms.name,'') end ||'<br>') " : ", cast('' as varchar) ") +" as planServices" +
                " from medcase sls " +
                " left join patient pat on pat.id=sls.patient_id" +
                " left join mislpu dep on dep.id=sls.department_id" +
                " left join vocpigeonhole vph on vph.id=dep.pigeonhole_id " +
                " left join VocDeniedHospitalizating vdh on vdh.id=sls.deniedHospitalizating_id" +
                " left join prescriptionlist pl on pl.medcase_id=sls.id" +
                " left join prescription p on p.prescriptionlist_id=pl.id" +
                " left join workfunction consWf on consWf.id=p.prescriptcabinet_id" +
                " left join vocWorkfunction consVwf on consVwf.id=consWf.workfunction_id" +
                " left join workcalendartime wct on wct.id=p.calendartime_id" +
                " left join medcase vis on vis.id=coalesce(wct.medcase_id, p.medcase_id)" +
                " left join medcase smc on smc.parent_id=vis.id" +
                " left join medservice ms on ms.id=smc.medservice_id" +
                " left join diary d on d.medcase_id=sls.id" +
                " left join workfunction wf on wf.id=d.specialist_id" +
                " left join vocWorkFunction vwf on vwf.id=wf.workfunction_id" +
                " where sls.dtype='HospitalMedCase'" +
                ("1".equals(onlyDenied) ? " and sls.deniedhospitalizating_id is not null" : ("0".equals(onlyDenied) ? "and sls.deniedhospitalizating_id is null":"")) +

                (aStartDate==null || aStartDate.equals("")
                        ? " and sls.datestart between current_date-1 and current_date and vdh.code='IN_PIGEON_HOLE'"
                        : " and sls.datestart between to_date('"+aStartDate+"','dd.MM.yyyy') and to_date('"+aFinishDate+"','dd.MM.yyyy')") +
                (isEmergency.equals("0") ? " and (sls.emergency is null or sls.emergency='0')" : isEmergency.equals("1") ? " and sls.emergency='1'" : "")+
                (pigeonHole!=null&&!pigeonHole.equals("")?" and vph.id="+Long.valueOf(pigeonHole)+"":"") +
                " group by sls.id, pat.id,dep.id"+
                " order by sls.dateStart, sls.entranceTime ";
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        LOG.info("sql = "+sql);
        String[] fields = {"id","patientInfo","waitTime","startTime","minutes", "departmentName", "madeServices", "planServices"};
        JSONArray ret = new JSONArray(service.executeNativeSqlGetJSON(fields,sql,null));
        return ret.toString();

    }
}
