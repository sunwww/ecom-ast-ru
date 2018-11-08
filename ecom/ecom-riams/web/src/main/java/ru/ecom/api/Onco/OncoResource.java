package ru.ecom.api.Onco;


import ru.ecom.api.util.ApiUtil;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.web.util.Injection;

import javax.ejb.ApplicationException;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/onco")
public class OncoResource {
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    /** Список СЛС, где есть/должен быть случай онкологического лечения
     * id - id выводимого
     * next - направление
     * department - отделение
     * dateBegin - период с
     * dateEnd - период по
     * isCreated - созданы ли случаи*/
    public String getListOncoCase(@Context HttpServletRequest aRequest
            ,  @QueryParam("id") String id
            ,  @QueryParam("next") Integer next
            , @QueryParam("department") String department
            , @QueryParam("dateBegin") String dateBegin
            ,@QueryParam("dateEnd") String dateEnd
            ,@QueryParam("isCreated") Integer isCreated
            ,@QueryParam("stat") String stat
    ) throws NamingException {
        int aCnt=50;
        String whereSql=(department!=null && !department.equals(""))? "and m.department_id='"+department+"'\n":"\n";
        if (isCreated==1) whereSql+=" and (select count(*) from oncologycase where medcase_id=sls.id)<>0 ";
        if (isCreated==0) whereSql+=" and (select count(*) from oncologycase where medcase_id=sls.id)=0 ";
        if (dateEnd==null || dateBegin.equals("")) dateEnd=dateBegin;
        whereSql+=(dateBegin!=null && !dateBegin.equals(""))? "and m.dateFinish between to_date('"+dateBegin+"','dd.mm.yyyy') and to_date('"+dateEnd+"','dd.mm.yyyy')\n":"\n";
        if (stat!=null && !stat.equals("")) whereSql+=" and sc.code='"+stat+"' ";
        String sql="select sls.id\n" +
                ",pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as patfio\n" +
                ",to_char(pat.birthday,'dd.mm.yyyy') as bd,sc.code as sccode\n" +
                ",to_char(sls.dateStart,'dd.mm.yyyy')||' - '||to_char(sls.dateFinish,'dd.mm.yyyy') as period\n" +
                ",wp.lastname||' '||wp.firstname||' '||wp.middlename as worker\n" +
                ",(select cast(idc10.code as varchar)||' '||ds.name from diagnosis ds\n" +
                "left join vocidc10 idc10 on idc10.id=ds.idc10_id\n" +
                "left join medcase hmc on hmc.id=ds.medcase_id\n" +
                "left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id\n" +
                "left join vocprioritydiagnosis pr on pr.id=ds.priority_id\n" +
                "where hmc.dtype='HospitalMedCase' and reg.code='3' and pr.code='1' and hmc.id=sls.id limit 1) as d\n" +
                ", case when (select count(*) from oncologycase where medcase_id=sls.id)=0 then '-' else '+' end as done\n" +
                "from medCase m \n" +
                "left join MedCase as sls on sls.id = m.parent_id \n" +
                "left join StatisticStub as sc on sc.medCase_id=sls.id\n" +
                "left join WorkFunction wf on wf.id=m.ownerFunction_id\n" +
                "left join Worker w on w.id=wf.worker_id\n" +
                "left join Patient wp on wp.id=w.person_id\n" +
                "left outer join Patient pat on m.patient_id = pat.id \n" +
                "where m.DTYPE='DepartmentMedCase'  \n" +
                whereSql +
                "and POSITION('C' in (select cast(idc10.code as varchar) from diagnosis ds\n" +
                "left join vocidc10 idc10 on idc10.id=ds.idc10_id\n" +
                "left join medcase hmc on hmc.id=ds.medcase_id\n" +
                "left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id\n" +
                "left join vocprioritydiagnosis pr on pr.id=ds.priority_id\n" +
                "where hmc.dtype='HospitalMedCase' and reg.code='3' and pr.code='1' and hmc.id=sls.id limit 1))<>0\n" +
                "group by  sls.id,m.dateStart,pat.lastname,pat.firstname,pat.middlename\n" +
                ",pat.birthday,sc.code,wp.lastname,wp.firstname,wp.middlename,sls.dateStart,sls.dateFinish,sls.id\n" +
                "order by sls.id ";
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String[] fields = {"id","patfio","bd","sccode","period", "worker", "d", "done"};
        if (next>0) {
            if (next!=2) {
                sql = sql.replace(" and hmc.id=sls.id limit 1))<>0", " and hmc.id=sls.id limit 1))<>0 and sls.id > " + id + " \n");
                sql = sql + " asc limit " + aCnt;
            }
            else {
                sql = sql.replace(" and hmc.id=sls.id limit 1))<>0", " and hmc.id=sls.id limit 1))<>0 and sls.id <= (select max(sls.id) from medCase m \n" +
                        "left join MedCase as sls on sls.id = m.parent_id \n" +
                        "where m.DTYPE='DepartmentMedCase'  \n" +
                        "and m.dateFinish between to_date('"+dateBegin+"','dd.mm.yyyy') and to_date('"+dateEnd+"','dd.mm.yyyy')\n" +
                        "and POSITION('C' in (select cast(idc10.code as varchar) from diagnosis ds\n" +
                        "left join vocidc10 idc10 on idc10.id=ds.idc10_id\n" +
                        "left join medcase hmc on hmc.id=ds.medcase_id\n" +
                        "left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id\n" +
                        "left join vocprioritydiagnosis pr on pr.id=ds.priority_id\n" +
                        "where hmc.dtype='HospitalMedCase' and reg.code='3' and pr.code='1' and hmc.id=sls.id limit 1))<>0)  \n");
                sql = sql + " desc limit (select count(sls.id) from medCase m \n" +
                        "left join MedCase as sls on sls.id = m.parent_id \n" +
                        "where m.DTYPE='DepartmentMedCase'  \n" +
                        "and m.dateFinish between to_date('"+dateBegin+"','dd.mm.yyyy') and to_date('"+dateEnd+"','dd.mm.yyyy')\n" +
                        "and POSITION('C' in (select cast(idc10.code as varchar) from diagnosis ds\n" +
                        "left join vocidc10 idc10 on idc10.id=ds.idc10_id\n" +
                        "left join medcase hmc on hmc.id=ds.medcase_id\n" +
                        "left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id\n" +
                        "left join vocprioritydiagnosis pr on pr.id=ds.priority_id\n" +
                        "where hmc.dtype='HospitalMedCase' and reg.code='3' and pr.code='1' and hmc.id=sls.id limit 1))<>0)%" + aCnt;  //чтобы всё чётко по страницам
                //и чтобы в том же порядке
                sql = sql.replace("select sls.id\n","select * from(select sls.id as tid\n");
                sql=sql+") as t order by t.tid asc";
            }
        } else {
                sql=sql.replace(" and hmc.id=sls.id limit 1))<>0"," and hmc.id=sls.id limit 1))<>0 and sls.id < "+id+" \n") ;
                sql=sql + " desc limit " + aCnt;
                //и чтобы в том же порядке
                sql = sql.replace("select sls.id\n","select * from(select sls.id as tid\n");
                sql=sql+") as t order by t.tid asc";
        }
        return service.executeNativeSqlGetJSON(fields,sql,null);
    }
}