<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%
    String nul = request.getParameter("nul") ;
    if (nul==null) {

%>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >


    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">ОТЧЁТ ПО 203 ПРИКАЗУ</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>

    </tiles:put>
    <tiles:put name="body" type="string">
        <%
            String department = request.getParameter("department") ;
            if (department!=null && !department.equals("")) request.setAttribute("department"," and dep.id="+department);
            String dateBegin = request.getParameter("dateBegin") ;
            if (dateBegin!=null && !dateBegin.equals("")) {
                request.setAttribute("dateBegin",dateBegin);
            }
            String dateEnd = request.getParameter("dateEnd") ;
            if (dateEnd==null || dateEnd.equals("")) dateEnd=dateBegin;
            if (dateEnd!=null && !dateEnd.equals("")) {
                request.setAttribute("dateEnd",dateEnd);
            }

            if (request.getParameter("short")==null) {

        %>
        <msh:form action="/report203.do" defaultField="department" disableFormDataConfirm="true" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:autoComplete property="department" fieldColSpan="16" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
                </msh:row>
                <msh:row>
                <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
                <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
                </msh:row>
                <msh:row>
                <td colspan="3">
                    <input type="button" onclick="find()" value="Найти" />
                </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            if (request.getParameter("dateBegin")!=null &&  !request.getParameter("dateBegin").equals("")) {
                request.setAttribute("isReportBase", ActionUtil.isReportBase(dateBegin,dateEnd,request));
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="justdeps" nameFldSql="justdeps_sql" nativeSql="
 select dep.name, count (mc.id) as discharge
 ,(select count(distinct mc.id) as pr203 from medcase mc
 left join diagnosis ds on ds.medcase_id=mc.id
 left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join MisLpu dep2 on dep2.id=mc.department_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
left join patient pat on pat.id=mc.patient_id
where qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
  ${department} and reg.code='4' and prior.code='1'
and dep2.id=dep.id and mc.DTYPE='DepartmentMedCase' and dep.name is not null
and (EXTRACT(YEAR from AGE(birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(birthday))<18 and vqecrit.ischild=true)
)
,(select count(distinct mc.id) as noque
  from medcase mc
 left join MisLpu dep2 on dep2.id=mc.department_id
 left join Medcase dmc on dmc.parent_id=mc.id
  left join Patient pat on pat.id=mc.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mc.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
 ${department} and reg.code='4' and prior.code='1'
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='PR203' and qe.isdraft<>true and mc.DTYPE='DepartmentMedCase'
and dep.name is not null
)
 ,'&depId='||coalesce(dep.id,0)||'&depname='||coalesce(dep.name,'')
 , case when (select count(distinct mc.id) as noque
  from medcase mc
 left join MisLpu dep2 on dep2.id=mc.department_id
 left join Medcase dmc on dmc.parent_id=mc.id
  left join Patient pat on pat.id=mc.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mc.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
 ${department} and reg.code='4' and prior.code='1'
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='PR203' and qe.isdraft<>true and mc.DTYPE='DepartmentMedCase'
and dep.name is not null
)=0 then '0' else
 round(100*(select count(distinct mc.id) as noque
  from medcase mc
 left join MisLpu dep2 on dep2.id=mc.department_id
 left join Medcase dmc on dmc.parent_id=mc.id
  left join Patient pat on pat.id=mc.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mc.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
 ${department} and reg.code='4' and prior.code='1'
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='PR203' and qe.isdraft<>true and mc.DTYPE='DepartmentMedCase'
and dep.name is not null)/
cast((select count(distinct mc.id) as pr203 from medcase mc
 left join diagnosis ds on ds.medcase_id=mc.id
 left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join MisLpu dep2 on dep2.id=mc.department_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
left join patient pat on pat.id=mc.patient_id
where qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
 ${department} and reg.code='4' and prior.code='1'
and dep2.id=dep.id and mc.DTYPE='DepartmentMedCase' and dep.name is not null
and (EXTRACT(YEAR from AGE(birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(birthday))<18 and vqecrit.ischild=true)) as numeric),2)
end as per1
,(select count(distinct mc.id) as noquedraft
  from medcase mc
 left join MisLpu dep2 on dep2.id=mc.department_id
 left join Medcase dmc on dmc.parent_id=mc.id
  left join Patient pat on pat.id=mc.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mc.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
 ${department} and reg.code='4' and prior.code='1'
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='PR203' and mc.DTYPE='DepartmentMedCase'
and dep.name is not null
)
, case when (select count(distinct mc.id) as noquedraft
  from medcase mc
 left join MisLpu dep2 on dep2.id=mc.department_id
 left join Medcase dmc on dmc.parent_id=mc.id
  left join Patient pat on pat.id=mc.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mc.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
 ${department} and reg.code='4' and prior.code='1'
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='PR203' and mc.DTYPE='DepartmentMedCase'
and dep.name is not null
)=0 then '0' else
 round(100*(select count(distinct mc.id) as noquedraft
  from medcase mc
 left join MisLpu dep2 on dep2.id=mc.department_id
 left join Medcase dmc on dmc.parent_id=mc.id
  left join Patient pat on pat.id=mc.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mc.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
 ${department} and reg.code='4' and prior.code='1'
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='PR203' and mc.DTYPE='DepartmentMedCase'
and dep.name is not null)/
cast((select count(distinct mc.id) as pr203 from medcase mc
 left join diagnosis ds on ds.medcase_id=mc.id
 left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join MisLpu dep2 on dep2.id=mc.department_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
left join patient pat on pat.id=mc.patient_id
where qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
 ${department} and reg.code='4' and prior.code='1'
and dep2.id=dep.id and mc.DTYPE='DepartmentMedCase' and dep.name is not null
and (EXTRACT(YEAR from AGE(birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(birthday))<18 and vqecrit.ischild=true)) as numeric),2)
end  as per2
 from medcase mc
 left join MisLpu dep on dep.id=mc.department_id
left join medcase as hmc on hmc.id=mc.parent_id
 where mc.DTYPE='DepartmentMedCase' and dep.name is not null
  and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
  ${department}
 group by dep.id
 "/>

                <form action="report203.do" method="post" target="_blank">

                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="justdeps"
                           viewUrl="report203.do"
                           action="report203.do" idField="5" cellFunction="true" >
                    <msh:tableColumn columnName="#" property="sn" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Отделение" property="1" addParam="&short=Short&view=treatDoctors&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&department=${param.department}&depId=${depId}" />
                    <msh:tableColumn columnName="ВСЕГО выписаны" property="2" isCalcAmount="true" addParam="&short=Short&view=dishAll&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&department=${param.department}&depId=${depId}"/>
                    <msh:tableColumn columnName="Выписаны по 203 приказу" property="3"  isCalcAmount="true" addParam="&short=Short&view=203All&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&department=${param.department}&depId=${depId}"/>
                    <msh:tableColumn columnName="Карта врача" property="7"  isCalcAmount="true" addParam="&short=Short&view=203EK&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&department=${param.department}&depId=${depId}&isDraft=&draft=врача"/>
                    <msh:tableColumn columnName="%" property="8" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Экспертная карта зав." property="4"  isCalcAmount="true" addParam="&short=Short&view=203EK&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&department=${param.department}&depId=${depId}&isDraft= and qe.isdraft<>true&draft=заведующего"/>
                    <msh:tableColumn columnName="%" property="6" addParam="&nul=nul" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="transfer" nameFldSql="transfer_sql" nativeSql="
                 select CAST('Перевод из патологии беременности в родовое' AS varchar(50))
,count(distinct slo.id)
,(select count(distinct mc.id) as pr203
 from medcase mc
left join medcase slo1 on slo1.prevMedCase_id=mc.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 left join diagnosis ds on ds.medcase_id=mc.id
 left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
left join patient pat on pat.id=mc.patient_id
 where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
  and mc.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mc.department_id='182' and slo1.department_id='203' and mc.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
 and (EXTRACT(YEAR from AGE(birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(birthday))<18 and vqecrit.ischild=true))
,(select count(distinct mc.id) as noque
  from medcase mc
left join medcase slo1 on slo1.prevMedCase_id=mc.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 left join MisLpu dep2 on dep2.id=mc.department_id
 left join Medcase dmc on dmc.parent_id=mc.id
  left join Patient pat on pat.id=mc.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mc.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
 and qe.experttype='BranchManager' and qek.code='PR203' and qe.isdraft<>true
and mc.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mc.department_id='182' and slo1.department_id='203' and mc.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null))
 ,case when (select count(distinct mc.id) as noque
from medcase mc
left join medcase slo1 on slo1.prevMedCase_id=mc.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 left join MisLpu dep2 on dep2.id=mc.department_id
 left join Medcase dmc on dmc.parent_id=mc.id
  left join Patient pat on pat.id=mc.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mc.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
 and qe.experttype='BranchManager' and qek.code='PR203' and qe.isdraft<>true
and mc.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mc.department_id='182' and slo1.department_id='203' and mc.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null))
=0 then '0' else
round(100*(select count(distinct mc.id) as noque
from medcase mc
left join medcase slo1 on slo1.prevMedCase_id=mc.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 left join MisLpu dep2 on dep2.id=mc.department_id
 left join Medcase dmc on dmc.parent_id=mc.id
  left join Patient pat on pat.id=mc.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mc.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
 and qe.experttype='BranchManager' and qek.code='PR203' and qe.isdraft<>true
and mc.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mc.department_id='182' and slo1.department_id='203' and mc.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null))
/cast((select count(distinct mc.id) as pr203
from medcase mc
left join medcase slo1 on slo1.prevMedCase_id=mc.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 left join diagnosis ds on ds.medcase_id=mc.id
 left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
left join patient pat on pat.id=mc.patient_id
 where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
  and mc.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mc.department_id='182' and slo1.department_id='203' and mc.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
 and (EXTRACT(YEAR from AGE(birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(birthday))<18 and vqecrit.ischild=true)) as numeric),2)
end as per1
,(select count(distinct mc.id) as noquedraft
  from medcase mc
left join medcase slo1 on slo1.prevMedCase_id=mc.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 left join MisLpu dep2 on dep2.id=mc.department_id
 left join Medcase dmc on dmc.parent_id=mc.id
  left join Patient pat on pat.id=mc.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mc.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
 and qe.experttype='BranchManager' and qek.code='PR203'
and mc.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mc.department_id='182' and slo1.department_id='203' and mc.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null))
 ,case when (select count(distinct mc.id) as noquedraft
from medcase mc
left join medcase slo1 on slo1.prevMedCase_id=mc.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 left join MisLpu dep2 on dep2.id=mc.department_id
 left join Medcase dmc on dmc.parent_id=mc.id
  left join Patient pat on pat.id=mc.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mc.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
 and qe.experttype='BranchManager' and qek.code='PR203'
and mc.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mc.department_id='182' and slo1.department_id='203' and mc.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null))
=0 then '0' else
round(100*(select count(distinct mc.id) as noquedraft
from medcase mc
left join medcase slo1 on slo1.prevMedCase_id=mc.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 left join MisLpu dep2 on dep2.id=mc.department_id
 left join Medcase dmc on dmc.parent_id=mc.id
  left join Patient pat on pat.id=mc.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mc.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
 and qe.experttype='BranchManager' and qek.code='PR203'
and mc.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mc.department_id='182' and slo1.department_id='203' and mc.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null))
/cast((select count(distinct mc.id) as pr203
from medcase mc
left join medcase slo1 on slo1.prevMedCase_id=mc.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 left join diagnosis ds on ds.medcase_id=mc.id
 left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
left join patient pat on pat.id=mc.patient_id
 where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
  and mc.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mc.department_id='182' and slo1.department_id='203' and mc.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
 and (EXTRACT(YEAR from AGE(birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(birthday))<18 and vqecrit.ischild=true)) as numeric),2)
end as per2
 from medcase slo
 left join medcase slo1 on slo1.prevMedCase_id=slo.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 where slo.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and slo.department_id='182' and slo1.department_id='203' and slo.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
                "/>

                <form action="report203.do" method="post" target="_blank">
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="transfer"
                           viewUrl="report203.do"
                           action="report203.do" idField="5" cellFunction="true" noDataMessage="Нет данных о пациентах, переведённых из АКУШЕРСКОГО ОТДЕЛЕНИЯ ПАТОЛОГИИ БЕРЕМЕННОСТИ в РОДОВОЕ (РОДИЛЬНОЕ) ОТДЕЛЕНИЕ" >
                    <msh:tableColumn columnName="#" property="sn" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Отделение" property="1" addParam="&short=Short&view=treatDoctors2&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&department=${param.department}&depId=${depId}"/>
                    <msh:tableColumn columnName="ВСЕГО выписаны" property="2" isCalcAmount="true" addParam="&short=Short&view=dishAll2&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}"/>
                    <msh:tableColumn columnName="Выписаны по 203 приказу" property="3"  isCalcAmount="true" addParam="&short=Short&view=203All2&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}"/>
                    <msh:tableColumn columnName="Карта врача" property="6"  isCalcAmount="true" addParam="&short=Short&view=203EK2&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&isDraft=&draft=врача"/>
                    <msh:tableColumn columnName="%" property="7" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Экспертная карта зав." property="4"  isCalcAmount="true" addParam="&short=Short&view=203EK2&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&isDraft= and qe.isdraft<>true&draft=заведующего"/>
                    <msh:tableColumn columnName="%" property="5" addParam="&nul=nul" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }
        %>
        <script type='text/javascript'>
            function find() {
                var from = $('dateBegin').value.split(".");
                var d1 = new Date(from[2], from[1] - 1, from[0]);
                var d2=new Date(2017, 11, 1);
                if(d1<d2) {
                    alert("Работа с 203 приказом началась с 1 декабря, раньше этого периода отчётности нет!");
                    $('dateBegin').value="01.12.2017";
                } else {
                    var frm = document.forms[0];
                    frm.target = '';
                    frm.action = 'report203.do';
                    frm.submit();
                }
            }
        </script>
        <%
            } else {
                String view = (String)request.getParameter("view") ;
                request.setAttribute("view", view);
                if (view.equals("dishAll")) {
                if (dateEnd!=null && !dateEnd.equals(""))
                    request.setAttribute("dateTo"," по "+dateEnd);
                    request.setAttribute("isReportBase", ActionUtil.isReportBase(dateBegin,dateEnd,request));
                %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="dishAll" nameFldSql="dishAll_sql" nativeSql="
             select mc.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
from MedCase as mc
left join medcase as hmc on hmc.id=mc.parent_id
left join MisLpu dep on dep.id=mc.department_id
left join patient pat on mc.patient_id=pat.id
where mc.DTYPE='DepartmentMedCase' and dep.id=${param.depId}
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department}
group by mc.id,pat.id
  "/>

                <form action="report203.do" method="post" target="_blank">
                    Пациенты, выписанные из отделения ${param.depname} за период с ${param.dateBegin} ${dateTo}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="dishAll"
                           viewUrl="report203.do" openNewWindow="true"
                           action="entityView-stac_slo.do" idField="1" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="ФИО" property="2" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
                }
            if (view.equals("203All")) {
                if (dateEnd!=null && !dateEnd.equals(""))
                    request.setAttribute("dateTo"," по "+dateEnd);
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="203All" nameFldSql="203All_sql" nativeSql="
                select mc.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
,(select case when(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='PR203' and qe.isdraft<>true group by pat.id) is not null then 'Да' else '-' end ) as zav
,(select case when(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='PR203' group by pat.id) is not null then 'Да' else '-' end ) as doc
,(select case when(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='PR203' and qe.isdraft<>true group by pat.id) is not null then
(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='PR203' and qe.isdraft<>true group by pat.id) else
(select
'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id group by pat.id) end)
from medcase mc
left join Mislpu dep on mc.department_id=dep.id
left join Patient pat on pat.id=mc.patient_id
left join diagnosis ds on ds.medcase_id=mc.id
left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
where mc.dtype='DepartmentMedCase' and qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
  and mc.dateFInish between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and dep.id=${param.depId}
 and dep.name is not null
and (EXTRACT(YEAR from AGE(birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(birthday))<18 and vqecrit.ischild=true)
 group by mc.id,pat.id
"/>

                <form action="report203.do" method="post" target="_blank">
                    Пациенты, выписанные по 203 приказу из отделения ${param.depname} за период с ${param.dateBegin} ${dateTo}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="203All"
                           viewUrl="report203.do" openNewWindow="true"
                           action="entityView-stac_slo.do" idField="1" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="ФИО" property="2" />
                    <msh:tableColumn columnName="Карта врача есть?" property="4" />
                    <msh:tableColumn columnName="Экспертная карта зав. есть?" property="3" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
                }
            if (view.equals("203EK")) {
                if (dateEnd!=null && !dateEnd.equals(""))
                    request.setAttribute("dateTo"," по "+dateEnd);
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="203EK" nameFldSql="203EK_sql" nativeSql="
               select distinct mc.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
 ,( select min(qec.id)
from medcase mc1
 left join MisLpu dep on dep.id=mc1.department_id
 left join Medcase dmc on dmc.parent_id=mc1.id
 left join Patient pat on pat.id=mc1.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mc1.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and mc.dtype='DepartmentMedCase' and qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
 ${department} and reg.code='4' and prior.code='1' and dep.id=${param.depId}
 and qe.experttype='BranchManager' and qek.code='PR203' and dep.name is not null
 ${param.isDraft}
  )
 from medcase mc
 left join MisLpu dep on dep.id=mc.department_id
 left join Medcase dmc on dmc.parent_id=mc.id
 left join Patient pat on pat.id=mc.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mc.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
  where mc.dtype='DepartmentMedCase' and qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
 ${department} and reg.code='4' and prior.code='1' and dep.id=${param.depId}
 and qe.experttype='BranchManager' and qek.code='PR203' and dep.name is not null
 ${param.isDraft}
group by mc.id,pat.id,qec.id"/>

                <form action="report203.do" method="post" target="_blank">
                    Пациенты, выписанные по 203 приказу из отделения ${param.depname} за период с ${param.dateBegin} ${dateTo}, в СЛС которых созданы экспертные карты ${param.draft}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="203EK"
                           viewUrl="report203.do" openNewWindow="true"
                           action="entityParentView-expert_card.do" idField="3">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="ФИО" property="2" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
                }
            if (view.equals("dishAll2")) {
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="dishAll2" nameFldSql="dishAll2_sql" nativeSql="
             select
slo.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
from medcase slo
left join patient pat on slo.patient_id=pat.id
 left join medcase slo1 on slo1.prevMedCase_id=slo.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 where slo.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and slo.department_id='182' and slo1.department_id='203' and slo.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
  "/>

                <form action="report203.do" method="post" target="_blank">
                    Пациенты, переведённые из АКУШЕРСКОГО ОТДЕЛЕНИЯ ПАТОЛОГИИ БЕРЕМЕННОСТИ в РОДОВОЕ (РОДИЛЬНОЕ) ОТДЕЛЕНИЕ за период с ${dateBegin} ${dateEnd}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="dishAll2"
                           viewUrl="report203.do" openNewWindow="true"
                           action="entityView-stac_slo.do" idField="1" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="ФИО" property="2" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }
            if (view.equals("203All2")) {
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="203All2" nameFldSql="203All2_sql" nativeSql="
select distinct mc.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
,(select case when(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='PR203' and qe.isdraft<>true group by pat.id) is not null then 'Да' else '-' end ) as zav
,(select case when(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='PR203' group by pat.id) is not null then 'Да' else '-' end ) as вщс
,(select case when(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='PR203' group by pat.id) is not null then
(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='PR203' group by pat.id) else
(select
'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id group by pat.id) end)
from medcase mc
left join MisLpu dep on dep.id=mc.department_id
left join Patient pat on pat.id=mc.patient_id
left join diagnosis ds on ds.medcase_id=mc.id
left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join medcase slo1 on slo1.prevMedCase_id=mc.id
left join medcase slo2 on slo2.prevMedCase_id=slo1.id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'

and mc.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mc.department_id='182' and slo1.department_id='203' and mc.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null
and (EXTRACT(YEAR from AGE(birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(birthday))<18 and vqecrit.ischild=true))
  "/>

                <form action="report203.do" method="post" target="_blank">
                    Пациенты, переведённые из АКУШЕРСКОГО ОТДЕЛЕНИЯ ПАТОЛОГИИ БЕРЕМЕННОСТИ в РОДОВОЕ (РОДИЛЬНОЕ) ОТДЕЛЕНИЕ за период с ${dateBegin} ${dateEnd}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="203All2"
                           viewUrl="report203.do" openNewWindow="true"
                           action="entityView-stac_slo.do" idField="1" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="ФИО" property="2" />
                    <msh:tableColumn columnName="Карта врача есть?" property="4" />
                    <msh:tableColumn columnName="Экспертная карта зав. есть?" property="3" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
                }
            if (view.equals("203EK2")) {
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="203EK2" nameFldSql="203EK2_sql" nativeSql="
             select distinct mc.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
 ,( select min(qec.id)
from medcase mc1
 left join MisLpu dep on dep.id=mc1.department_id
 left join Medcase dmc on dmc.parent_id=mc1.id
 left join Patient pat on pat.id=mc1.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mc1.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
  left join medcase slo1 on slo1.prevMedCase_id=mc1.id
left join medcase slo2 on slo2.prevMedCase_id=slo1.id
where mc1.id=mc.id and qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
 and qe.experttype='BranchManager' and qek.code='PR203'
and mc.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mc.department_id='182' and slo1.department_id='203' and mc.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
 ${param.isDraft}
  )
 from medcase mc
 left join MisLpu dep on dep.id=mc.department_id
 left join Medcase dmc on dmc.parent_id=mc.id
 left join Patient pat on pat.id=mc.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mc.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
  left join medcase slo1 on slo1.prevMedCase_id=mc.id
left join medcase slo2 on slo2.prevMedCase_id=slo1.id
  where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
 and qe.experttype='BranchManager' and qek.code='PR203'
and mc.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mc.department_id='182' and slo1.department_id='203' and mc.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
 ${param.isDraft}
group by mc.id,pat.id,qec.id
  "/>

                <form action="report203.do" method="post" target="_blank">
                    Пациенты, переведённые из АКУШЕРСКОГО ОТДЕЛЕНИЯ ПАТОЛОГИИ БЕРЕМЕННОСТИ в РОДОВОЕ (РОДИЛЬНОЕ) ОТДЕЛЕНИЕ за период с ${dateBegin} ${dateEnd}, в СЛС которых созданы экспертные карты  ${param.draft}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="203EK2"
                           viewUrl="report203.do" openNewWindow="true"
                           action="entityView-stac_slo.do" idField="1" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="ФИО" property="2" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
                }
            if (view.equals("treatDoctors")) {
                if (dateEnd!=null && !dateEnd.equals(""))
                    request.setAttribute("dateTo"," по "+dateEnd);
                request.setAttribute("isReportBase", ActionUtil.isReportBase(dateBegin,dateEnd,request));
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="treatDoctors" nameFldSql="treatDoctors_sql" nativeSql="
                select mc.ownerFunction_id,ovwf.name||' '||owp.lastname||' '||substring(owp.firstname,1,1)||' '||coalesce(substring(owp.middlename,1,1),'') as worker,
count (mc.id) as discharge
 ,(select count(distinct mcinner.id) as pr203 from medcase mcinner
 left join diagnosis ds on ds.medcase_id=mcinner.id
 left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join MisLpu dep2 on dep2.id=mcinner.department_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
left join patient pat on pat.id=mcinner.patient_id
where qd.vocidc10_id=ds.idc10_id
and mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
  ${department} and dep.id=${param.depId} and reg.code='4' and prior.code='1'
and dep2.id=dep.id and mcinner.DTYPE='DepartmentMedCase' and dep.name is not null
and (EXTRACT(YEAR from AGE(birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(birthday))<18 and vqecrit.ischild=true)
and mcinner.ownerFunction_id=mc.ownerFunction_id
)
,(select count(distinct mcinner.id) as noque
  from medcase mcinner
 left join MisLpu dep2 on dep2.id=mcinner.department_id
 left join Medcase dmc on dmc.parent_id=mcinner.id
  left join Patient pat on pat.id=mcinner.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mcinner.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mcinner.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
and mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department} and dep.id=${param.depId} and reg.code='4' and prior.code='1'
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='PR203' and qe.isdraft<>true and mcinner.DTYPE='DepartmentMedCase'
and dep.name is not null
and mcinner.ownerFunction_id=mc.ownerFunction_id
)
 ,'&depId='||coalesce(dep.id,0)||'&depname='||coalesce(dep.name,'')||'&wfId='||coalesce(mc.ownerFunction_id,0)||'&wfname='||ovwf.name||' '||owp.lastname||' '||substring(owp.firstname,1,1)||' '||coalesce(substring(owp.middlename,1,1),'')
 , case when (select count(distinct mcinner.id) as noque
  from medcase mcinner
 left join MisLpu dep2 on dep2.id=mcinner.department_id
 left join Medcase dmc on dmc.parent_id=mcinner.id
  left join Patient pat on pat.id=mcinner.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mcinner.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mcinner.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
and mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department} and dep.id=${param.depId} and reg.code='4' and prior.code='1'
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='PR203' and qe.isdraft<>true and mcinner.DTYPE='DepartmentMedCase'
and dep.name is not null
and mcinner.ownerFunction_id=mc.ownerFunction_id
)=0 then '0' else
 round(100*(select count(distinct mcinner.id) as noque
  from medcase mcinner
 left join MisLpu dep2 on dep2.id=mcinner.department_id
 left join Medcase dmc on dmc.parent_id=mcinner.id
  left join Patient pat on pat.id=mcinner.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mcinner.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mcinner.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
and mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department} and dep.id=${param.depId}  and reg.code='4' and prior.code='1'
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='PR203' and qe.isdraft<>true and mcinner.DTYPE='DepartmentMedCase'
and dep.name is not null
and mcinner.ownerFunction_id=mc.ownerFunction_id)/
cast((select count(distinct mcinner.id) as pr203 from medcase mcinner
 left join diagnosis ds on ds.medcase_id=mcinner.id
 left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join MisLpu dep2 on dep2.id=mcinner.department_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
left join patient pat on pat.id=mcinner.patient_id
where qd.vocidc10_id=ds.idc10_id
and mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
  ${department} and dep.id=${param.depId}  and reg.code='4' and prior.code='1'
and dep2.id=dep.id and mcinner.DTYPE='DepartmentMedCase' and dep.name is not null
and mcinner.ownerFunction_id=mc.ownerFunction_id
and (EXTRACT(YEAR from AGE(birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(birthday))<18 and vqecrit.ischild=true)) as numeric),2)
end as per1
,(select count(distinct mcinner.id) as noquedraft
  from medcase mcinner
 left join MisLpu dep2 on dep2.id=mcinner.department_id
 left join Medcase dmc on dmc.parent_id=mcinner.id
  left join Patient pat on pat.id=mcinner.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mcinner.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mcinner.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
and mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department} and dep.id=${param.depId} and reg.code='4' and prior.code='1'
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='PR203' and mcinner.DTYPE='DepartmentMedCase'
and dep.name is not null
and mcinner.ownerFunction_id=mc.ownerFunction_id
)
, case when (select count(distinct mcinner.id) as noquedraft
  from medcase mcinner
 left join MisLpu dep2 on dep2.id=mcinner.department_id
 left join Medcase dmc on dmc.parent_id=mcinner.id
  left join Patient pat on pat.id=mcinner.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mcinner.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mcinner.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
and mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department} and dep.id=${param.depId} and reg.code='4' and prior.code='1'
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='PR203' and mcinner.DTYPE='DepartmentMedCase'
and dep.name is not null
and mcinner.ownerFunction_id=mc.ownerFunction_id
)=0 then '0' else
 round(100*(select count(distinct mcinner.id) as noquedraft
  from medcase mcinner
 left join MisLpu dep2 on dep2.id=mcinner.department_id
 left join Medcase dmc on dmc.parent_id=mcinner.id
  left join Patient pat on pat.id=mcinner.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mcinner.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mcinner.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
and mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department} and dep.id=${param.depId}  and reg.code='4' and prior.code='1'
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='PR203' and mcinner.DTYPE='DepartmentMedCase'
and dep.name is not null
and mcinner.ownerFunction_id=mc.ownerFunction_id)/
cast((select count(distinct mcinner.id) as pr203 from medcase mcinner
 left join diagnosis ds on ds.medcase_id=mcinner.id
 left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join MisLpu dep2 on dep2.id=mcinner.department_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
left join patient pat on pat.id=mcinner.patient_id
where qd.vocidc10_id=ds.idc10_id
and mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
  ${department} and dep.id=${param.depId}  and reg.code='4' and prior.code='1'
and dep2.id=dep.id and mcinner.DTYPE='DepartmentMedCase' and dep.name is not null
and mcinner.ownerFunction_id=mc.ownerFunction_id
and (EXTRACT(YEAR from AGE(birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(birthday))<18 and vqecrit.ischild=true)) as numeric),2)
end as per2
 from medcase mc
 left join MisLpu dep on dep.id=mc.department_id
left join medcase as hmc on hmc.id=mc.parent_id
    left join WorkFunction owf on owf.id=mc.ownerFunction_id
    left join VocWorkFunction ovwf on ovwf.id=owf.workFunction_id
    left join Worker ow on ow.id=owf.worker_id
    left join Patient owp on owp.id=ow.person_id
 where mc.DTYPE='DepartmentMedCase' and dep.name is not null
  and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
 ${department} and dep.id=${param.depId}
 group by dep.id,mc.ownerfunction_id,ovwf.name,owp.id
  "/>

                <form action="report203.do" method="post" target="_blank">
                    Распределение выписанных пациентов отделения ${param.depname} по лечащим врачам за период с ${param.dateBegin} ${dateTo}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="treatDoctors" cellFunction="true"
                           viewUrl="report203.do"
                           action="report203.do" idField="6" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="Врач" property="2" addParam="&nul=nul" />
                    <msh:tableColumn columnName="ВСЕГО выписаны" property="3" isCalcAmount="true" addParam="&short=Short&view=dishAll3&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&wfId=${wfId}&wfname=${wfname}"/>
                    <msh:tableColumn columnName="Выписаны по 203 приказу" property="4"  isCalcAmount="true" addParam="&short=Short&view=203All3&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&wfId=${wfId}&wfname=${wfname}"/>
                    <msh:tableColumn columnName="Карта врача" property="8"  isCalcAmount="true" addParam="&short=Short&view=203EK3&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&wfId=${wfId}&wfname=${wfname}&isDraft=&draft=врача"/>
                    <msh:tableColumn columnName="%" property="9" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Экспертная карта зав." property="5"  isCalcAmount="true" addParam="&short=Short&view=203EK3&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&wfId=${wfId}&wfname=${wfname}&isDraft= and qe.isdraft<>true&draft=заведующего"/>
                    <msh:tableColumn columnName="%" property="7" addParam="&nul=nul" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
                }
            if (view.equals("dishAll3")) {
                if (dateEnd!=null && !dateEnd.equals(""))
                    request.setAttribute("dateTo"," по "+dateEnd);
                request.setAttribute("isReportBase", ActionUtil.isReportBase(dateBegin,dateEnd,request));
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="dishAll3" nameFldSql="dishAll3_sql" nativeSql="
             select mc.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
from MedCase as mc
left join medcase as hmc on hmc.id=mc.parent_id
left join MisLpu dep on dep.id=mc.department_id
left join patient pat on mc.patient_id=pat.id
where mc.DTYPE='DepartmentMedCase' and dep.id=${param.depId}
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department}
and mc.ownerFunction_id=${param.wfId}
group by mc.id,pat.id
  "/>

                <form action="report203.do" method="post" target="_blank">
                    Пациенты, выписанные из отделения ${param.depname} за период с ${param.dateBegin} ${dateTo} врачом ${param.wfname}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="dishAll3"
                           viewUrl="report203.do" openNewWindow="true"
                           action="entityView-stac_slo.do" idField="1" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="ФИО" property="2" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
                }
            if (view.equals("203All3")) {
                if (dateEnd!=null && !dateEnd.equals(""))
                    request.setAttribute("dateTo"," по "+dateEnd);
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="203All3" nameFldSql="203All3_sql" nativeSql="
                select mc.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
,(select case when(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='PR203' and qe.isdraft<>true group by pat.id) is not null then 'Да' else '-' end ) as zav
,(select case when(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='PR203' group by pat.id) is not null then 'Да' else '-' end ) as doc
,(select case when(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='PR203' group by pat.id) is not null then
(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='PR203' group by pat.id) else
(select
'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id group by pat.id) end)
from medcase mc
left join Mislpu dep on mc.department_id=dep.id
left join Patient pat on pat.id=mc.patient_id
left join diagnosis ds on ds.medcase_id=mc.id
left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
where mc.dtype='DepartmentMedCase' and qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
  and mc.dateFInish between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and dep.id=${param.depId}
 and dep.name is not null
and (EXTRACT(YEAR from AGE(birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(birthday))<18 and vqecrit.ischild=true)
 and mc.ownerFunction_id=${param.wfId}
 group by mc.id,pat.id
"/>

                <form action="report203.do" method="post" target="_blank">
                    Пациенты, выписанные по 203 приказу из отделения ${param.depname}  за период с ${param.dateBegin} ${dateTo} врачом ${param.wfname}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="203All3"
                           viewUrl="report203.do" openNewWindow="true"
                           action="entityView-stac_slo.do" idField="1" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="ФИО" property="2" />
                    <msh:tableColumn columnName="Карта врача есть?" property="4" />
                    <msh:tableColumn columnName="Экспертная карта зав. есть?" property="3" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
                }
            if (view.equals("203EK3")) {
                if (dateEnd!=null && !dateEnd.equals(""))
                    request.setAttribute("dateTo"," по "+dateEnd);
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="203EK3" nameFldSql="203EK_sql" nativeSql="
               select distinct mc.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
 ,( select min(qec.id)
from medcase mc1
 left join MisLpu dep on dep.id=mc1.department_id
 left join Medcase dmc on dmc.parent_id=mc1.id
 left join Patient pat on pat.id=mc1.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mc1.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
where mc1.id=mc.id and mc.dtype='DepartmentMedCase' and qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
 ${department} and reg.code='4' and prior.code='1' and dep.id=${param.depId}
 and qe.experttype='BranchManager' and qek.code='PR203' and dep.name is not null
and (EXTRACT(YEAR from AGE(birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(birthday))<18 and vqecrit.ischild=true)
  )
 from medcase mc
 left join MisLpu dep on dep.id=mc.department_id
 left join Medcase dmc on dmc.parent_id=mc.id
 left join Patient pat on pat.id=mc.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mc.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
  where mc.dtype='DepartmentMedCase' and qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
 ${department} and reg.code='4' and prior.code='1' and dep.id=${param.depId}
 and qe.experttype='BranchManager' and qek.code='PR203' and dep.name is not null
 and mc.ownerFunction_id=${param.wfId}
and (EXTRACT(YEAR from AGE(birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(birthday))<18 and vqecrit.ischild=true)
group by mc.id,pat.id,qec.id"/>

                <form action="report203.do" method="post" target="_blank">
                    Пациенты, выписанные по 203 приказу из отделения ${param.depname} за период с ${param.dateBegin} ${dateTo} врачом ${param.wfname}, в СЛС которых созданы экспертные карты  ${param.draft}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="203EK3"
                           viewUrl="report203.do" openNewWindow="true"
                           action="entityParentView-expert_card.do" idField="3">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="ФИО" property="2" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
                }
            if (view.equals("treatDoctors2")) {
                if (dateEnd!=null && !dateEnd.equals(""))
                    request.setAttribute("dateTo"," по "+dateEnd);
                request.setAttribute("isReportBase", ActionUtil.isReportBase(dateBegin,dateEnd,request));
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="treatDoctors2" nameFldSql="treatDoctors2_sql" nativeSql="
                select slo.ownerFunction_id,ovwf.name||' '||owp.lastname||' '||substring(owp.firstname,1,1)||' '||coalesce(substring(owp.middlename,1,1),'') as worker
,count(distinct slo.id)
,(select count(distinct mcinner.id) as pr203
 from medcase mcinner
left join medcase slo1 on slo1.prevMedCase_id=mcinner.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 left join diagnosis ds on ds.medcase_id=mcinner.id
 left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
left join patient pat on pat.id=mcinner.patient_id
 where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
  and mcinner.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mcinner.department_id='182' and slo1.department_id='203' and mcinner.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
 and (EXTRACT(YEAR from AGE(birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(birthday))<18 and vqecrit.ischild=true)
and mcinner.ownerFunction_id=slo.ownerFunction_id)
,(select count(distinct mcinner.id) as noque
  from medcase mcinner
left join medcase slo1 on slo1.prevMedCase_id=mcinner.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 left join MisLpu dep2 on dep2.id=mcinner.department_id
 left join Medcase dmc on dmc.parent_id=mcinner.id
  left join Patient pat on pat.id=mcinner.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mcinner.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mcinner.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
 and qe.experttype='BranchManager' and qek.code='PR203' and qe.isdraft<>true
and mcinner.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mcinner.department_id='182' and slo1.department_id='203' and mcinner.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
and mcinner.ownerFunction_id=slo.ownerFunction_id)
 ,case when (select count(distinct mcinner.id) as noque
  from medcase mcinner
left join medcase slo1 on slo1.prevMedCase_id=mcinner.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 left join MisLpu dep2 on dep2.id=mcinner.department_id
 left join Medcase dmc on dmc.parent_id=mcinner.id
  left join Patient pat on pat.id=mcinner.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mcinner.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mcinner.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
 and qe.experttype='BranchManager' and qek.code='PR203' and qe.isdraft<>true
and mcinner.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mcinner.department_id='182' and slo1.department_id='203' and mcinner.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
and mcinner.ownerFunction_id=slo.ownerFunction_id)
=0 then '0' else
round(100*(select count(distinct mcinner.id) as noque
  from medcase mcinner
left join medcase slo1 on slo1.prevMedCase_id=mcinner.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 left join MisLpu dep2 on dep2.id=mcinner.department_id
 left join Medcase dmc on dmc.parent_id=mcinner.id
  left join Patient pat on pat.id=mcinner.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mcinner.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mcinner.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
 and qe.experttype='BranchManager' and qek.code='PR203' and qe.isdraft<>true
and mcinner.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mcinner.department_id='182' and slo1.department_id='203' and mcinner.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
and mcinner.ownerFunction_id=slo.ownerFunction_id)
/cast((select count(distinct mcinner.id) as pr203
 from medcase mcinner
left join medcase slo1 on slo1.prevMedCase_id=mcinner.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 left join diagnosis ds on ds.medcase_id=mcinner.id
 left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
left join patient pat on pat.id=mcinner.patient_id
 where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
  and mcinner.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mcinner.department_id='182' and slo1.department_id='203' and mcinner.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
 and (EXTRACT(YEAR from AGE(birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(birthday))<18 and vqecrit.ischild=true)
and mcinner.ownerFunction_id=slo.ownerFunction_id) as numeric),2)
end as per1
, '&wfId='||coalesce(slo.ownerFunction_id,0)||'&wfname='||ovwf.name||' '||owp.lastname||' '||substring(owp.firstname,1,1)||' '||coalesce(substring(owp.middlename,1,1),'')
,(select count(distinct mcinner.id) as noquedraft
  from medcase mcinner
left join medcase slo1 on slo1.prevMedCase_id=mcinner.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 left join MisLpu dep2 on dep2.id=mcinner.department_id
 left join Medcase dmc on dmc.parent_id=mcinner.id
  left join Patient pat on pat.id=mcinner.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mcinner.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mcinner.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
 and qe.experttype='BranchManager' and qek.code='PR203'
and mcinner.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mcinner.department_id='182' and slo1.department_id='203' and mcinner.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
and mcinner.ownerFunction_id=slo.ownerFunction_id)
,case when (select count(distinct mcinner.id) as noquedraft
  from medcase mcinner
left join medcase slo1 on slo1.prevMedCase_id=mcinner.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 left join MisLpu dep2 on dep2.id=mcinner.department_id
 left join Medcase dmc on dmc.parent_id=mcinner.id
  left join Patient pat on pat.id=mcinner.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mcinner.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mcinner.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
 and qe.experttype='BranchManager' and qek.code='PR203'
and mcinner.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mcinner.department_id='182' and slo1.department_id='203' and mcinner.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
and mcinner.ownerFunction_id=slo.ownerFunction_id)
=0 then '0' else
round(100*(select count(distinct mcinner.id) as noquedraft
  from medcase mcinner
left join medcase slo1 on slo1.prevMedCase_id=mcinner.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 left join MisLpu dep2 on dep2.id=mcinner.department_id
 left join Medcase dmc on dmc.parent_id=mcinner.id
  left join Patient pat on pat.id=mcinner.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mcinner.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mcinner.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
 and qe.experttype='BranchManager' and qek.code='PR203'
and mcinner.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mcinner.department_id='182' and slo1.department_id='203' and mcinner.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
and mcinner.ownerFunction_id=slo.ownerFunction_id)
/cast((select count(distinct mcinner.id) as pr203
 from medcase mcinner
left join medcase slo1 on slo1.prevMedCase_id=mcinner.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 left join diagnosis ds on ds.medcase_id=mcinner.id
 left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
left join patient pat on pat.id=mcinner.patient_id
 where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
  and mcinner.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mcinner.department_id='182' and slo1.department_id='203' and mcinner.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
 and (EXTRACT(YEAR from AGE(birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(birthday))<18 and vqecrit.ischild=true)
and mcinner.ownerFunction_id=slo.ownerFunction_id) as numeric),2)
end as per2
 from medcase slo
 left join medcase slo1 on slo1.prevMedCase_id=slo.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
    left join WorkFunction owf on owf.id=slo.ownerFunction_id
    left join VocWorkFunction ovwf on ovwf.id=owf.workFunction_id
    left join Worker ow on ow.id=owf.worker_id
    left join Patient owp on owp.id=ow.person_id
 where slo.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and slo.department_id='182' and slo1.department_id='203' and slo.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
 group by slo.ownerfunction_id,ovwf.name,owp.id
  "/>

                <form action="report203.do" method="post" target="_blank">
                    Распределение пациентов, переведённых из АКУШЕРСКОГО ОТДЕЛЕНИЯ ПАТОЛОГИИ БЕРЕМЕННОСТИ в РОДОВОЕ (РОДИЛЬНОЕ) ОТДЕЛЕНИЕ, по лечащим врачам за период с ${param.dateBegin} ${dateTo}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="treatDoctors2" cellFunction="true"
                           viewUrl="report203.do"
                           action="report203.do" idField="7" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="Врач" property="2" addParam="&nul=nul" />
                    <msh:tableColumn columnName="ВСЕГО выписаны" property="3" isCalcAmount="true" addParam="&short=Short&view=dishAll4&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&wfId=${wfId}&wfname=${wfname}"/>
                    <msh:tableColumn columnName="Выписаны по 203 приказу" property="4"  isCalcAmount="true" addParam="&short=Short&view=203All4&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&wfId=${wfId}&wfname=${wfname}"/>
                    <msh:tableColumn columnName="Карта врача" property="8"  isCalcAmount="true" addParam="&short=Short&view=203EK4&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&wfId=${wfId}&wfname=${wfname}&isDraft=&draft=врача"/>
                    <msh:tableColumn columnName="%" property="9" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Экспертная карта зав." property="5"  isCalcAmount="true" addParam="&short=Short&view=203EK4&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&wfId=${wfId}&wfname=${wfname}&isDraft= and qe.isdraft<>true&draft=заведующего"/>
                    <msh:tableColumn columnName="%" property="6" addParam="&nul=nul" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
                }
                //////////
            if (view.equals("dishAll4")) {
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="dishAll4" nameFldSql="dishAll2_sql" nativeSql="
             select
slo.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
from medcase slo
left join patient pat on slo.patient_id=pat.id
 left join medcase slo1 on slo1.prevMedCase_id=slo.id
 left join medcase slo2 on slo2.prevMedCase_id=slo1.id
 where slo.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and slo.department_id='182' and slo1.department_id='203' and slo.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
and slo.ownerFunction_id=${param.wfId}
  "/>

                <form action="report203.do" method="post" target="_blank">
                    Пациенты, переведённые из АКУШЕРСКОГО ОТДЕЛЕНИЯ ПАТОЛОГИИ БЕРЕМЕННОСТИ в РОДОВОЕ (РОДИЛЬНОЕ) ОТДЕЛЕНИЕ за период с ${dateBegin} ${dateEnd}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="dishAll4"
                           viewUrl="report203.do" openNewWindow="true"
                           action="entityView-stac_slo.do" idField="1" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="ФИО" property="2" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }
            if (view.equals("203All4")) {
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="203All4" nameFldSql="203All2_sql" nativeSql="
select distinct mc.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
,(select case when(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='PR203' and qe.isdraft<>true group by pat.id) is not null then 'Да' else '-' end ) as zav
,(select case when(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='PR203' group by pat.id) is not null then 'Да' else '-' end ) as doc
,(select case when(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='PR203' group by pat.id) is not null then
(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='PR203' group by pat.id) else
(select
'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id group by pat.id) end)
from medcase mc
left join MisLpu dep on dep.id=mc.department_id
left join Patient pat on pat.id=mc.patient_id
left join diagnosis ds on ds.medcase_id=mc.id
left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join medcase slo1 on slo1.prevMedCase_id=mc.id
left join medcase slo2 on slo2.prevMedCase_id=slo1.id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'

and mc.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mc.department_id='182' and slo1.department_id='203' and mc.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
and (EXTRACT(YEAR from AGE(birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(birthday))<18 and vqecrit.ischild=true)
 and mc.ownerFunction_id=${param.wfId}
  "/>

                <form action="report203.do" method="post" target="_blank">
                    Пациенты, переведённые из АКУШЕРСКОГО ОТДЕЛЕНИЯ ПАТОЛОГИИ БЕРЕМЕННОСТИ в РОДОВОЕ (РОДИЛЬНОЕ) ОТДЕЛЕНИЕ за период с ${dateBegin} ${dateEnd}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="203All4"
                           viewUrl="report203.do" openNewWindow="true"
                           action="entityView-stac_slo.do" idField="1" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="ФИО" property="2" />
                    <msh:tableColumn columnName="Карта врача есть?" property="4" />
                    <msh:tableColumn columnName="Экспертная карта зав. есть?" property="3" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }
            if (view.equals("203EK4")) {
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="203EK4" nameFldSql="203EK2_sql" nativeSql="
             select distinct mc.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
 ,( select min(qec.id)
from medcase mc1
 left join MisLpu dep on dep.id=mc1.department_id
 left join Medcase dmc on dmc.parent_id=mc1.id
 left join Patient pat on pat.id=mc1.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mc1.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
  left join medcase slo1 on slo1.prevMedCase_id=mc1.id
left join medcase slo2 on slo2.prevMedCase_id=slo1.id
where mc1.id=mc.id and qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
 and qe.experttype='BranchManager' and qek.code='PR203'
and mc.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mc.department_id='182' and slo1.department_id='203' and mc.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
  )
 from medcase mc
 left join MisLpu dep on dep.id=mc.department_id
 left join Medcase dmc on dmc.parent_id=mc.id
 left join Patient pat on pat.id=mc.patient_id or pat.id=dmc.patient_id
  left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
  left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
  left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  left join vocprioritydiagnosis prior on prior.id=ds.priority_id
  left join qualityestimationcard qec on qec.medcase_id=mc.id or qec.medcase_id=dmc.id
  left join qualityestimation qe on qe.card_id=qec.id
  left join vocqualityestimationkind qek on qek.id=qec.kind_id
  left join medcase slo1 on slo1.prevMedCase_id=mc.id
left join medcase slo2 on slo2.prevMedCase_id=slo1.id
  where qd.vocidc10_id=ds.idc10_id
 and reg.code='4' and prior.code='1'
 and qe.experttype='BranchManager' and qek.code='PR203'
and mc.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and mc.department_id='182' and slo1.department_id='203' and mc.dtype='DepartmentMedCase'
 and slo1.dtype='DepartmentMedCase' and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
 and mc.ownerFunction_id=${param.wfId}
 ${param.isDraft}
group by mc.id,pat.id,qec.id
  "/>

                <form action="report203.do" method="post" target="_blank">
                    Пациенты, переведённые из АКУШЕРСКОГО ОТДЕЛЕНИЯ ПАТОЛОГИИ БЕРЕМЕННОСТИ в РОДОВОЕ (РОДИЛЬНОЕ) ОТДЕЛЕНИЕ за период с ${dateBegin} ${dateEnd}, в СЛС которых созданы экспертные карты  ${param.draft}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="203EK4"
                           viewUrl="report203.do" openNewWindow="true"
                           action="entityView-stac_slo.do" idField="1" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="ФИО" property="2" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
                }
            }
        %>
    </tiles:put>
</tiles:insert>
<%
    }
%>
<script type="text/javascript">
    eventutil.addEventListener($('dateBegin'), "blur",
    function() {
        var from = $('dateBegin').value.split(".");
        var d1 = new Date(from[2], from[1] - 1, from[0]);
        var d2=new Date(2017, 11, 1);
        if(d1<d2) {
            alert("Работа с 203 приказом началась с 01.12.2017, раньше этого периода отчётности нет!");
            $('dateBegin').value="01.12.2017";
        }
    }) ;

</script>