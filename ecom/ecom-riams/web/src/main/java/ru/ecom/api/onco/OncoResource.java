package ru.ecom.api.onco;


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
                ",pat.lastname ||' ' ||LEFT(pat.firstname,1)||'. '|| LEFT(pat.middlename,1)||'.'||' г.р. '||to_char(pat.birthday,'dd.mm.yyyy') as patinfo\n" +
                ",dep.name as depname,sc.code as sccode\n" +
                ",to_char(sls.dateStart,'dd.mm.yyyy')||' - '||to_char(sls.dateFinish,'dd.mm.yyyy') as period\n" +
                ",wp.lastname||' '||LEFT(wp.firstname,1)||'. '|| LEFT(wp.middlename,1)||'.' as worker\n" +
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
                "left join Mislpu dep on dep.id=sls.department_id \n" +
                "where m.DTYPE='DepartmentMedCase'  \n" +
                whereSql +
                "and POSITION('C' in (select cast(idc10.code as varchar) from diagnosis ds\n" +
                "left join vocidc10 idc10 on idc10.id=ds.idc10_id\n" +
                "left join medcase hmc on hmc.id=ds.medcase_id\n" +
                "left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id\n" +
                "left join vocprioritydiagnosis pr on pr.id=ds.priority_id\n" +
                "where hmc.dtype='HospitalMedCase' and reg.code='3' and pr.code='1' and hmc.id=sls.id limit 1))<>0\n" +
                "group by  sls.id,m.dateStart,pat.lastname,pat.firstname,pat.middlename\n" +
                ",pat.birthday,sc.code,wp.lastname,wp.firstname,wp.middlename,sls.dateStart,sls.dateFinish,sls.id,dep.name\n" +
                "order by sls.id ";
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String[] fields = {"id","patinfo","depname","sccode","period", "worker", "d", "done"};
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

    @GET
    @Path("listPolyclinic")
    @Produces(MediaType.APPLICATION_JSON)
    /** Список СПО, где есть/должен быть случай онкологического лечения
     * id - id выводимого
     * next - направление
     * dateBegin - период с
     * dateEnd - период по
     * isCreated - созданы ли случаи*/
    public String getListOncoCasePolyclinic(@Context HttpServletRequest aRequest
            ,  @QueryParam("id") String id
            ,  @QueryParam("next") Integer next
            , @QueryParam("dateBegin") String dateBegin
            ,@QueryParam("dateEnd") String dateEnd
            ,@QueryParam("isCreated") Integer isCreated
            ,@QueryParam("stat") String stat
    ) throws NamingException {
        int aCnt=50;
        String whereSql="";
        if (dateEnd==null || dateBegin.equals("")) dateEnd=dateBegin;
        whereSql+=(dateBegin!=null && !dateBegin.equals(""))? "and spo.dateFinish between to_date('"+dateBegin+"','dd.mm.yyyy') and to_date('"+dateEnd+"','dd.mm.yyyy')\n":"\n";
        if (isCreated==1) whereSql+=" and (select count(*) from oncologycase where medcase_id=spo.id)<>0 ";
        if (isCreated==0) whereSql+=" and (select count(*) from oncologycase where medcase_id=spo.id)=0 ";
        String sql="select spo.id\n" +
                ",pat.lastname ||' ' ||LEFT(pat.firstname,1)||'. '|| LEFT(pat.middlename,1)||'.'||' г.р. '||to_char(pat.birthday,'dd.mm.yyyy') as patinfo\n" +
                ",cast('КОНСУЛЬТАТИВНАЯ ПОЛИКЛИНИКА' as varchar(27)) as depname,cast('-' as varchar(1)) as sccode\n" +
                ",to_char(spo.dateStart,'dd.mm.yyyy')||' - '||to_char(spo.dateFinish,'dd.mm.yyyy') as period\n" +
                ",wp.lastname||' '||LEFT(wp.firstname,1)||'. '|| LEFT(wp.middlename,1)||'.' as worker\n" +
                ",(select cast(idc10.code as varchar)||' '||ds.name from diagnosis ds\n" +
                "left join medcase vis on vis.id=ds.medcase_id\n" +
                "left join medcase spoinner on spoinner.id=vis.parent_id\n" +
                "left join vocidc10 idc10 on idc10.id=ds.idc10_id\n" +
                "left join vocprioritydiagnosis pr on pr.id=ds.priority_id\n" +
                "where spoinner.dtype='PolyclinicMedCase' and pr.code='1' and spoinner.id=spo.id limit 1) as d\n" +
                ", case when (select count(*) from oncologycase where medcase_id=spo.id)=0 then '-' else '+' end as done\n" +
                "from medCase spo \n" +
                "left join WorkFunction wf on wf.id=spo.ownerFunction_id\n" +
                "left join Worker w on w.id=wf.worker_id\n" +
                "left join Patient wp on wp.id=w.person_id\n" +
                "left outer join Patient pat on spo.patient_id = pat.id \n" +
                "where spo.DTYPE='PolyclinicMedCase'  \n" +
                whereSql +
                "and POSITION('C' in (select cast(idc10.code as varchar) from diagnosis ds\n" +
                "left join vocidc10 idc10 on idc10.id=ds.idc10_id\n" +
                "left join medcase vis on vis.id=ds.medcase_id\n" +
                "left join medcase spoinner on spoinner.id=vis.parent_id\n" +
                "left join vocprioritydiagnosis pr on pr.id=ds.priority_id\n" +
                "where spoinner.dtype='PolyclinicMedCase' and pr.code='1' and spoinner.id=spo.id limit 1))<>0\n" +
                "group by  spo.id,spo.dateStart,pat.lastname,pat.firstname,pat.middlename\n" +
                ",pat.birthday,wp.lastname,wp.firstname,wp.middlename,spo.dateStart,spo.dateFinish\n" +
                "order by spo.id ";
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String[] fields = {"id","patinfo","depname","sccode","period", "worker", "d", "done"};
        if (next>0) {
            if (next!=2) {
                sql = sql.replace(" and spoinner.id=spo.id limit 1))<>0", " and spoinner.id=spo.id limit 1))<>0 and spo.id > " + id + " \n");
                sql = sql + " asc limit " + aCnt;
            }
            else {
                sql = sql.replace(" and spoinner.id=spo.id limit 1))<>0", " and spoinner.id=spo.id limit 1))<>0 and spo.id <= (select max(spo.id) from medCase spo \n" +
                        "where spo.DTYPE='PolyclinicMedCase'  \n" +
                        "and spo.dateFinish between to_date('"+dateBegin+"','dd.mm.yyyy') and to_date('"+dateEnd+"','dd.mm.yyyy')\n" +
                        "and POSITION('C' in (select cast(idc10.code as varchar) from diagnosis ds\n" +
                        "left join vocidc10 idc10 on idc10.id=ds.idc10_id\n" +
                        "left join medcase vis on vis.id=ds.medcase_id\n" +
                        "left join medcase spoinner on spoinner.id=vis.parent_id\n" +
                        "left join vocprioritydiagnosis pr on pr.id=ds.priority_id\n" +
                        "where spoinner.dtype='PolyclinicMedCase' and pr.code='1' and spoinner.id=spo.id limit 1))<>0)  \n");
                sql = sql + " desc limit (select count(spo.id) from medCase spo \n" +
                        "where spo.DTYPE='PolyclinicMedCase'  \n" +
                        "and spo.dateFinish between to_date('"+dateBegin+"','dd.mm.yyyy') and to_date('"+dateEnd+"','dd.mm.yyyy')\n" +
                        "and POSITION('C' in (select cast(idc10.code as varchar) from diagnosis ds\n" +
                        "left join vocidc10 idc10 on idc10.id=ds.idc10_id\n" +
                        "left join medcase vis on vis.id=ds.medcase_id\n" +
                        "left join medcase spoinner on spoinner.id=vis.parent_id\n" +
                        "left join vocprioritydiagnosis pr on pr.id=ds.priority_id\n" +
                        "where spoinner.dtype='PolyclinicMedCase' and pr.code='1' and spoinner.id=spo.id limit 1))<>0)%" + aCnt;  //чтобы всё чётко по страницам
                //и чтобы в том же порядке
                sql = sql.replace("select spo.id\n","select * from(select spo.id as tid\n");
                sql=sql+") as t order by t.tid asc";
            }
        } else {
            sql=sql.replace(" and spoinner.id=spo.id limit 1))<>0"," and spoinner.id=spo.id limit 1))<>0 and spo.id < "+id+" \n") ;
            sql=sql + " desc limit " + aCnt;
            //и чтобы в том же порядке
            sql = sql.replace("select spo.id\n","select * from(select spo.id as tid\n");
            sql=sql+") as t order by t.tid asc";
        }
        return service.executeNativeSqlGetJSON(fields,sql,null);
    }
}