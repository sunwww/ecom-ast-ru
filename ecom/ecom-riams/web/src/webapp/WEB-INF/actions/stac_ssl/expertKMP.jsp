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
        <msh:title mainMenu="StacJournal">ОТЧЁТ ПО ЭКСПЕРТИЗЕ KP</msh:title>
        <h2>Учитываются только те отделения, в которых нужно проводить экспертизу</h2>
    </tiles:put>

    <tiles:put name='side' type='string'>

    </tiles:put>
    <tiles:put name="body" type="string">
        <%
            String department = request.getParameter("department") ;
            String depOnlyKmp = " and dep.isreportkmp=true ";
            if (department!=null && !department.equals(""))
                depOnlyKmp=depOnlyKmp+" and dep.id="+department;
            request.setAttribute("department",depOnlyKmp);
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
        <msh:form action="/expertKMP.do" defaultField="department" disableFormDataConfirm="true" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:autoComplete property="department" fieldColSpan="16" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdOnlyKMP"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с" />
                    <msh:textField property="dateEnd" label="по" />
                </msh:row>
                <msh:row>
                    <td>
                        <input type="button" onclick="find()" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            if (request.getParameter("dateBegin")!=null &&  !request.getParameter("dateBegin").equals("")) {

        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery  name="justdeps" nameFldSql="justdeps_sql" nativeSql="
select dep.name, count (mc.id) as discharge
,(select count(distinct mc.id) as prKMP from medcase mc
left join diagnosis ds on ds.medcase_id=mc.id
left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join MisLpu dep2 on dep2.id=mc.department_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
    left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
    left join VocQualityEstimationCrit qec on qec.id=qd.vqecrit_id
  	left join vocqualityestimationkind qek on qek.id=qec.kind_id
  	    left join medcase hmc on hmc.id=mc.parent_id
where mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department}
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
and dep2.id=dep.id and mc.DTYPE='DepartmentMedCase' and dep.name is not null
    and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mc.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
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
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
  	    left join medcase hmc on hmc.id=mc.parent_id
where qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department}
	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mc.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='KMP' and qe.isdraft<>true and mc.DTYPE='DepartmentMedCase'
and dep.name is not null
and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='BranchManager') is not null
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
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
  	    left join medcase hmc on hmc.id=mc.parent_id
where qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department}
	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mc.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='KMP' and qe.isdraft<>true and mc.DTYPE='DepartmentMedCase'
and dep.name is not null
and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='BranchManager') is not null
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
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
  	    left join medcase hmc on hmc.id=mc.parent_id
where qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department}
	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mc.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='KMP' and qe.isdraft<>true and mc.DTYPE='DepartmentMedCase'
and dep.name is not null
and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='BranchManager') is not null)/
cast((select count(distinct mc.id) as prKMP from medcase mc
left join diagnosis ds on ds.medcase_id=mc.id
left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join MisLpu dep2 on dep2.id=mc.department_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
    left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
    left join VocQualityEstimationCrit qec on qec.id=qd.vqecrit_id
  	left join vocqualityestimationkind qek on qek.id=qec.kind_id
  	    left join medcase hmc on hmc.id=mc.parent_id
where mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department}
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
and dep2.id=dep.id and mc.DTYPE='DepartmentMedCase' and dep.name is not null
    and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mc.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP') as numeric),2)
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
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
  	    left join medcase hmc on hmc.id=mc.parent_id
where qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department}
  	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mc.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='KMP' and mc.DTYPE='DepartmentMedCase'
and dep.name is not null
 and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='BranchManager') is not null
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
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
  	    left join medcase hmc on hmc.id=mc.parent_id
where qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department}
  	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mc.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='KMP' and mc.DTYPE='DepartmentMedCase'
and dep.name is not null
 and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='BranchManager') is not null)
=0 then '0' else
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
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
  	    left join medcase hmc on hmc.id=mc.parent_id
where qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department}
  	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mc.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='KMP' and mc.DTYPE='DepartmentMedCase'
and dep.name is not null
 and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='BranchManager') is not null
and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='BranchManager') is not null)/
cast((select count(distinct mc.id) as prKMP from medcase mc
left join diagnosis ds on ds.medcase_id=mc.id
left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join MisLpu dep2 on dep2.id=mc.department_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
left join patient pat on pat.id=mc.patient_id
    left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
    left join VocQualityEstimationCrit qec on qec.id=qd.vqecrit_id
  	left join vocqualityestimationkind qek on qek.id=qec.kind_id
  	    left join medcase hmc on hmc.id=mc.parent_id
where qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department}
 	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mc.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
and dep2.id=dep.id and mc.DTYPE='DepartmentMedCase' and dep.name is not null) as numeric),2)
end  as per2
,(select count(distinct mc.id) as noqueExpert
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
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
  	    left join medcase hmc on hmc.id=mc.parent_id
where qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department}
	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mc.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
and dep2.id=dep.id and qe.experttype='Expert' and qek.code='KMP' and qe.isdraft<>true and mc.DTYPE='DepartmentMedCase'
and dep.name is not null
and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='Expert') is not null
)
, case when (select count(distinct mc.id) as noqueExpert
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
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
  	    left join medcase hmc on hmc.id=mc.parent_id
where qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department}
	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mc.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
and dep2.id=dep.id and qe.experttype='Expert' and qek.code='KMP' and qe.isdraft<>true and mc.DTYPE='DepartmentMedCase'
and dep.name is not null
and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='Expert') is not null
)=0 then '0' else
round(100*(select count(distinct mc.id) as noqueExpert
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
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
  	    left join medcase hmc on hmc.id=mc.parent_id
where qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department}
	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mc.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
and dep2.id=dep.id and qe.experttype='Expert' and qek.code='KMP' and qe.isdraft<>true and mc.DTYPE='DepartmentMedCase'
and dep.name is not null
and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='Expert') is not null)/
cast((select count(distinct mc.id) as prKMP from medcase mc
left join diagnosis ds on ds.medcase_id=mc.id
left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join MisLpu dep2 on dep2.id=mc.department_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
    left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
    left join VocQualityEstimationCrit qec on qec.id=qd.vqecrit_id
  	left join vocqualityestimationkind qek on qek.id=qec.kind_id
  	    left join medcase hmc on hmc.id=mc.parent_id
where mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department}
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
and dep2.id=dep.id and mc.DTYPE='DepartmentMedCase' and dep.name is not null
    and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mc.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP') as numeric),2)
end as per1Expert
from medcase mc
left join MisLpu dep on dep.id=mc.department_id
left join medcase as hmc on hmc.id=mc.parent_id
where mc.DTYPE='DepartmentMedCase' and dep.name is not null
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
${department}
group by dep.id
order by dep.name
 "/>

                <form action="expertKMP.do" method="post" target="_blank">

                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="justdeps"
                           action="expertKMP.do" idField="5" cellFunction="true" >
                    <msh:tableColumn columnName="#" property="sn" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Отделение" property="1" addParam="&short=Short&view=treatDoctors&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&department=${param.department}&depId=${depId}" />
                    <msh:tableColumn columnName="ВСЕГО выписаны" property="2" isCalcAmount="true" addParam="&short=Short&view=dishAll&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&department=${param.department}&depId=${depId}"/>
                    <msh:tableColumn columnName="Выписаны по экспертизе KP" property="3"  isCalcAmount="true" addParam="&short=Short&view=kmpAll&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&department=${param.department}&depId=${depId}"/>
                    <msh:tableColumn columnName="Карта врача" property="7"  isCalcAmount="true" addParam="&short=Short&view=kmpEK&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&department=${param.department}&depId=${depId}&isDraft=&draft=врача&expertType=BranchManager"/>
                    <msh:tableColumn columnName="%" property="8" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Экспертная карта зав." property="4"  isCalcAmount="true" addParam="&short=Short&view=kmpEK&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&department=${param.department}&depId=${depId}&isDraft= and qe.isdraft<>true&draft=заведующего&expertType=BranchManager"/>
                    <msh:tableColumn columnName="%" property="6" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Карта эксперта" property="9"  isCalcAmount="true" addParam="&short=Short&view=kmpEK&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&department=${param.department}&depId=${depId}&isDraft= and qe.isdraft<>true&draft=заведующего&expertType=Expert"/>
                    <msh:tableColumn columnName="%" property="10" addParam="&nul=nul" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }
        %>
        <script type='text/javascript'>
            function find() {
                if(okDate()) {
                    var frm = document.forms[0];
                    frm.target = '';
                    frm.action = 'expertKMP.do';
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

        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery  name="dishAll" nameFldSql="dishAll_sql" nativeSql="
             select mc.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
from MedCase as mc
left join medcase as hmc on hmc.id=mc.parent_id
left join MisLpu dep on dep.id=mc.department_id
left join patient pat on mc.patient_id=pat.id
where mc.DTYPE='DepartmentMedCase' and dep.id=${param.depId}
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department}
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
group by mc.id,pat.id
order by pat.lastname||' '||pat.firstname||' '||pat.middlename
  "/>

                <form action="expertKMP.do" method="post" target="_blank">
                    Пациенты, находящиеся в отделении ${param.depname} за период с ${param.dateBegin} ${dateTo}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="dishAll"
                           openNewWindow="true"
                           action="entityView-stac_slo.do" idField="1" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="ФИО" property="2" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }
            if (view.equals("kmpAll")) {
                if (dateEnd!=null && !dateEnd.equals(""))
                    request.setAttribute("dateTo"," по "+dateEnd);

        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery  name="kmpAll" nameFldSql="kmpAll_sql" nativeSql="
select mc.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
,(select case when(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='KMP' and qe.isdraft<>true and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='BranchManager') is not null
group by pat.id) is not null then 'Да' else '-' end ) as zav
,(select case when(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='KMP' and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='BranchManager') is not null
group by pat.id) is not null then 'Да' else '-' end ) as doc
,(select case when(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='KMP' and qe.isdraft<>true group by pat.id) is not null then
(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='KMP' and qe.isdraft<>true group by pat.id) else
(select
'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id group by pat.id) end)
,(select case when(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
where mc1.id=mc.id and qe.experttype='Expert' and qek.code='KMP' and qe.isdraft<>true and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='Expert') is not null
group by pat.id) is not null then 'Да' else '-' end ) as exp
from medcase mc
left join Mislpu dep on mc.department_id=dep.id
left join Patient pat on pat.id=mc.patient_id
left join diagnosis ds on ds.medcase_id=mc.id
left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
    left join VocQualityEstimationCrit qec on qec.id=qd.vqecrit_id
  	left join vocqualityestimationkind qek on qek.id=qec.kind_id
        left join medcase hmc on hmc.id=mc.parent_id
where mc.dtype='DepartmentMedCase' and qd.vocidc10_id=ds.idc10_id
and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mc.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
and mc.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and dep.id=${param.depId}
and dep.name is not null
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
group by mc.id,pat.id
order by pat.lastname||' '||pat.firstname||' '||pat.middlename
"/>

                <form action="expertKMP.do" method="post" target="_blank">
                    Пациенты, находящиеся в отделении ${param.depname} по экспертизе KP за период с ${param.dateBegin} ${dateTo}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="kmpAll"
                           openNewWindow="true"
                           action="entityView-stac_slo.do" idField="1" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="ФИО" property="2" />
                    <msh:tableColumn columnName="Карта врача есть?" property="4" />
                    <msh:tableColumn columnName="Экспертная карта зав. есть?" property="3" />
                    <msh:tableColumn columnName="Карта эксперта есть?" property="6" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }
            if (view.equals("kmpEK")) {
                if (dateEnd!=null && !dateEnd.equals(""))
                    request.setAttribute("dateTo"," по "+dateEnd);

        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery  name="kmpEK" nameFldSql="kmpEK_sql" nativeSql="
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
  left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
  	    left join medcase hmc on hmc.id=mc.parent_id
where mc1.id=mc.id and mc.dtype='DepartmentMedCase' and qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
 ${department} and reg.code='4' and prior.code='1' and dep.id=${param.depId}
 and qe.experttype='${param.expertType}' and qek.code='KMP' and dep.name is not null
 ${param.isDraft}
 and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='${param.expertType}') is not null
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
  left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
  	    left join medcase hmc on hmc.id=mc.parent_id
  where mc.dtype='DepartmentMedCase' and qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
 ${department} and reg.code='4' and prior.code='1' and dep.id=${param.depId}
 and qe.experttype='${param.expertType}' and qek.code='KMP' and dep.name is not null
 ${param.isDraft}
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
 and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='${param.expertType}') is not null
group by mc.id,pat.id,qec.id
order by pat.lastname||' '||pat.firstname||' '||pat.middlename"/>

                <form action="expertKMP.do" method="post" target="_blank">
                    Пациенты, находящиеся в отделении ${param.depname} по экспертизе KP за период с ${param.dateBegin} ${dateTo}, в СЛС которых созданы экспертные карты ${param.draft}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="kmpEK"
                           openNewWindow="true"
                           action="entityParentView-expert_card.do" idField="3">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="ФИО" property="2" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }
            if (view.equals("treatDoctors")) {
                if (dateEnd!=null && !dateEnd.equals(""))
                    request.setAttribute("dateTo"," по "+dateEnd);
                if(request.getAttribute("department")!=null
                        && !request.getAttribute("department").toString().replace("and dep.id=","").equals(request.getAttribute("depId")))
                    request.setAttribute("department","");
                request.setAttribute("dateTo"," по "+dateEnd);

        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery  name="treatDoctors" nameFldSql="treatDoctors_sql" nativeSql="
                select mc.ownerFunction_id,ovwf.name||' '||owp.lastname||' '||substring(owp.firstname,1,1)||' '||coalesce(substring(owp.middlename,1,1),'') as worker,
count (mc.id) as discharge
,(select count(distinct mcinner.id) as prKMP from medcase mcinner
left join diagnosis ds on ds.medcase_id=mcinner.id
left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join MisLpu dep2 on dep2.id=mcinner.department_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
    left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
    left join VocQualityEstimationCrit qec on qec.id=qd.vqecrit_id
  	left join vocqualityestimationkind qek on qek.id=qec.kind_id
  	    left join medcase hmc on hmc.id=mcinner.parent_id
where mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department} and dep.id=${param.depId}
and mcinner.ownerFunction_id=mc.ownerFunction_id
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id)>1
        then mcinner.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id) else 1=1 end
	and dep2.id=dep.id and mcinner.DTYPE='DepartmentMedCase' and dep.name is not null
    and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mcinner.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
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
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
  	    left join medcase hmc on hmc.id=mcinner.parent_id
where qd.vocidc10_id=ds.idc10_id
and mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id)>1
        then mcinner.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id) else 1=1 end
	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mcinner.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='KMP' and qe.isdraft<>true and mcinner.DTYPE='DepartmentMedCase'
and dep.name is not null
and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='BranchManager') is not null
${department} and dep.id=${param.depId}
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
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
  	    left join medcase hmc on hmc.id=mcinner.parent_id
where qd.vocidc10_id=ds.idc10_id
and mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id)>1
        then mcinner.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id) else 1=1 end
	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  	    left join medcase hmc on hmc.id=mcinner.parent_id
    where mcinner.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='KMP' and qe.isdraft<>true and mcinner.DTYPE='DepartmentMedCase'
and dep.name is not null
and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='BranchManager') is not null
${department} and dep.id=${param.depId}
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
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
  	    left join medcase hmc on hmc.id=mcinner.parent_id
where qd.vocidc10_id=ds.idc10_id
and mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id)>1
        then mcinner.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id) else 1=1 end
	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  	    left join medcase hmc on hmc.id=mcinner.parent_id
    where mcinner.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='KMP' and qe.isdraft<>true and mcinner.DTYPE='DepartmentMedCase'
and dep.name is not null
and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='BranchManager') is not null
${department} and dep.id=${param.depId}
and mcinner.ownerFunction_id=mc.ownerFunction_id)/
cast((select count(distinct mcinner.id) as prKMP from medcase mcinner
left join diagnosis ds on ds.medcase_id=mcinner.id
left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join MisLpu dep2 on dep2.id=mcinner.department_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
    left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
    left join VocQualityEstimationCrit qec on qec.id=qd.vqecrit_id
  	left join vocqualityestimationkind qek on qek.id=qec.kind_id
  	    left join medcase hmc on hmc.id=mcinner.parent_id
where mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department} and dep.id=${param.depId}
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id)>1
        then mcinner.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id) else 1=1 end
and mcinner.ownerFunction_id=mc.ownerFunction_id
	and dep2.id=dep.id and mcinner.DTYPE='DepartmentMedCase' and dep.name is not null
    and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mcinner.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP') as numeric),2)
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
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
  	    left join medcase hmc on hmc.id=mcinner.parent_id
where qd.vocidc10_id=ds.idc10_id
and mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id)>1
        then mcinner.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id) else 1=1 end
  	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mcinner.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='KMP' and mcinner.DTYPE='DepartmentMedCase'
and dep.name is not null
 and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='BranchManager') is not null
${department} and dep.id=${param.depId} and reg.code='4' and prior.code='1'
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
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
  	    left join medcase hmc on hmc.id=mcinner.parent_id
where qd.vocidc10_id=ds.idc10_id
and mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id)>1
        then mcinner.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id) else 1=1 end
  	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mcinner.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='KMP' and mcinner.DTYPE='DepartmentMedCase'
and dep.name is not null
 and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='BranchManager') is not null
${department} and dep.id=${param.depId} and reg.code='4' and prior.code='1'
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
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
  	    left join medcase hmc on hmc.id=mcinner.parent_id
where qd.vocidc10_id=ds.idc10_id
and mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id)>1
        then mcinner.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id) else 1=1 end
  	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mcinner.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
and dep2.id=dep.id and qe.experttype='BranchManager' and qek.code='KMP' and mcinner.DTYPE='DepartmentMedCase'
and dep.name is not null
 and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='BranchManager') is not null
${department} and dep.id=${param.depId} and reg.code='4' and prior.code='1'
and mcinner.ownerFunction_id=mc.ownerFunction_id)/
cast((select count(distinct mcinner.id) as prKMP from medcase mcinner
left join diagnosis ds on ds.medcase_id=mcinner.id
left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join MisLpu dep2 on dep2.id=mcinner.department_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
    left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
    left join VocQualityEstimationCrit qec on qec.id=qd.vqecrit_id
  	left join vocqualityestimationkind qek on qek.id=qec.kind_id
  	    left join medcase hmc on hmc.id=mcinner.parent_id
where mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department} and dep.id=${param.depId}
and mcinner.ownerFunction_id=mc.ownerFunction_id
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id)>1
        then mcinner.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id) else 1=1 end
	and dep2.id=dep.id and mcinner.DTYPE='DepartmentMedCase' and dep.name is not null
    and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mcinner.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP') as numeric),2)
end as per2
,(select count(distinct mcinner.id) as noqueExpert
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
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
  	    left join medcase hmc on hmc.id=mcinner.parent_id
where qd.vocidc10_id=ds.idc10_id
and mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id)>1
        then mcinner.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id) else 1=1 end
	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mcinner.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
and dep2.id=dep.id and qe.experttype='Expert' and qek.code='KMP' and qe.isdraft<>true and mcinner.DTYPE='DepartmentMedCase'
and dep.name is not null
and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='Expert') is not null
${department} and dep.id=${param.depId}
and mcinner.ownerFunction_id=mc.ownerFunction_id
)
, case when (select count(distinct mcinner.id) as noqueExpert
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
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
  	    left join medcase hmc on hmc.id=mcinner.parent_id
where qd.vocidc10_id=ds.idc10_id
and mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id)>1
        then mcinner.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id) else 1=1 end
	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
  	    left join medcase hmc on hmc.id=mcinner.parent_id
    where mcinner.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
and dep2.id=dep.id and qe.experttype='Expert' and qek.code='KMP' and qe.isdraft<>true and mcinner.DTYPE='DepartmentMedCase'
and dep.name is not null
and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='Expert') is not null
${department} and dep.id=${param.depId}
and mcinner.ownerFunction_id=mc.ownerFunction_id
)=0 then '0' else
round(100*(select count(distinct mcinner.id) as noqueExpert
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
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
  	    left join medcase hmc on hmc.id=mcinner.parent_id
where qd.vocidc10_id=ds.idc10_id
and mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id)>1
        then mcinner.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id) else 1=1 end
	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mcinner.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
and dep2.id=dep.id and qe.experttype='Expert' and qek.code='KMP' and qe.isdraft<>true and mcinner.DTYPE='DepartmentMedCase'
and dep.name is not null
and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='Expert') is not null
${department} and dep.id=${param.depId}
and mcinner.ownerFunction_id=mc.ownerFunction_id)/
cast((select count(distinct mcinner.id) as prKMP from medcase mcinner
left join diagnosis ds on ds.medcase_id=mcinner.id
left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join MisLpu dep2 on dep2.id=mcinner.department_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
    left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
    left join VocQualityEstimationCrit qec on qec.id=qd.vqecrit_id
  	left join vocqualityestimationkind qek on qek.id=qec.kind_id
  	    left join medcase hmc on hmc.id=mcinner.parent_id
where mcinner.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mcinner.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
${department} and dep.id=${param.depId}
and mcinner.ownerFunction_id=mc.ownerFunction_id
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id)>1
        then mcinner.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mcinner.department_id) else 1=1 end
	and dep2.id=dep.id and mcinner.DTYPE='DepartmentMedCase' and dep.name is not null
    and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mcinner.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP') as numeric),2)
end as per1Expert
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
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
 group by dep.id,mc.ownerfunction_id,ovwf.name,owp.id
 order by ovwf.name||' '||owp.lastname||' '||substring(owp.firstname,1,1)||' '||coalesce(substring(owp.middlename,1,1),'')
  "/>

                <form action="expertKMP.do" method="post" target="_blank">
                    Распределение пациентов, находящихся в отделении ${param.depname} по лечащим врачам за период с ${param.dateBegin} ${dateTo}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="treatDoctors" cellFunction="true"

                           action="expertKMP.do" idField="6" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="Врач" property="2" addParam="&nul=nul" />
                    <msh:tableColumn columnName="ВСЕГО выписаны" property="3" isCalcAmount="true" addParam="&short=Short&view=dishAll3&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&wfId=${wfId}&wfname=${wfname}"/>
                    <msh:tableColumn columnName="Выписаны по экспертизе KP" property="4"  isCalcAmount="true" addParam="&short=Short&view=kmpAll3&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&wfId=${wfId}&wfname=${wfname}"/>
                    <msh:tableColumn columnName="Карта врача" property="8"  isCalcAmount="true" addParam="&short=Short&view=kmpEK3&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&wfId=${wfId}&wfname=${wfname}&isDraft=&draft=врача&expertType=BranchManager"/>
                    <msh:tableColumn columnName="%" property="9" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Экспертная карта зав." property="5"  isCalcAmount="true" addParam="&short=Short&view=kmpEK3&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&wfId=${wfId}&wfname=${wfname}&isDraft= and qe.isdraft<>true&draft=заведующего&expertType=BranchManager"/>
                    <msh:tableColumn columnName="%" property="7" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Карта эксперта" property="10"  isCalcAmount="true" addParam="&short=Short&view=kmpEK3&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&wfId=${wfId}&wfname=${wfname}&isDraft= and qe.isdraft<>true&draft=заведующего&expertType=Expert"/>
                    <msh:tableColumn columnName="%" property="11" addParam="&nul=nul" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }
            if (view.equals("dishAll3")) {
                if (dateEnd!=null && !dateEnd.equals(""))
                    request.setAttribute("dateTo"," по "+dateEnd);

        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery  name="dishAll3" nameFldSql="dishAll3_sql" nativeSql="
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
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
group by mc.id,pat.id
order by mc.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
  "/>

                <form action="expertKMP.do" method="post" target="_blank">
                    Пациенты, находящиеся в отделении ${param.depname} за период с ${param.dateBegin} ${dateTo} врачом ${param.wfname}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="dishAll3"
                           openNewWindow="true"
                           action="entityView-stac_slo.do" idField="1" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="ФИО" property="2" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }
            if (view.equals("kmpAll3")) {
                if (dateEnd!=null && !dateEnd.equals(""))
                    request.setAttribute("dateTo"," по "+dateEnd);

        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery  name="kmpAll3" nameFldSql="kmpAll3_sql" nativeSql="
select mc.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
,(select case when(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='KMP' and qe.isdraft<>true  and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='BranchManager') is not null
group by pat.id) is not null then 'Да' else '-' end ) as zav
,(select case when(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='KMP' and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='BranchManager') is not null
group by pat.id) is not null then 'Да' else '-' end ) as doc
,(select case when(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='KMP' group by pat.id) is not null then
(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id and qe.experttype='BranchManager' and qek.code='KMP' group by pat.id) else
(select
'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
where mc1.id=mc.id group by pat.id) end)
,(select case when(select
min('&qecid='||coalesce(qec.id,0))||'&patid='||coalesce(pat.id,0) from medcase mc1
left join Medcase dmc on dmc.parent_id=mc1.id
left join Patient pat on pat.id=mc1.patient_id
left join qualityestimationcard qec on qec.medcase_id=mc1.id or qec.medcase_id=dmc.id
left join qualityestimation qe on qe.card_id=qec.id
left join vocqualityestimationkind qek on qek.id=qec.kind_id
left join diagnosis ds on ds.medcase_id=mc.id or ds.medcase_id=dmc.id
left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocqualityestimationcrit vqecrit on vqecrit.id=qd.vqecrit_id
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
where mc1.id=mc.id and qe.experttype='Expert' and qek.code='KMP' and qe.isdraft<>true  and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='Expert') is not null
group by pat.id) is not null then 'Да' else '-' end ) as exp
from medcase mc
left join Mislpu dep on mc.department_id=dep.id
left join Patient pat on pat.id=mc.patient_id
left join diagnosis ds on ds.medcase_id=mc.id
left join vocqualityestimationcrit_diagnosis qd on qd.vocidc10_id=ds.idc10_id
left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
left join vocprioritydiagnosis prior on prior.id=ds.priority_id
left join vocqualityestimationcrit vqecrit on qd.vqecrit_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
    left join VocQualityEstimationCrit qec on qec.id=qd.vqecrit_id
  	left join vocqualityestimationkind qek on qek.id=qec.kind_id
        left join medcase hmc on hmc.id=mc.parent_id
where mc.dtype='DepartmentMedCase' and qd.vocidc10_id=ds.idc10_id
 	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mc.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
and mc.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and dep.id=${param.depId}
and dep.name is not null
and mc.ownerFunction_id=${param.wfId}
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
group by mc.id,pat.id
order by mc.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
"/>

                <form action="expertKMP.do" method="post" target="_blank">
                    Пациенты, нахрдящиеся в отделении ${param.depname} по экспертизе KP за период с ${param.dateBegin} ${dateTo} врачом ${param.wfname}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="kmpAll3"
                           openNewWindow="true"
                           action="entityView-stac_slo.do" idField="1" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="ФИО" property="2" />
                    <msh:tableColumn columnName="Карта врача есть?" property="4" />
                    <msh:tableColumn columnName="Экспертная карта зав. есть?" property="3" />
                    <msh:tableColumn columnName="Карта эксперта есть?" property="6" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }
            if (view.equals("kmpEK3")) {
                if (dateEnd!=null && !dateEnd.equals(""))
                    request.setAttribute("dateTo"," по "+dateEnd);

        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery  name="kmpEK3" nameFldSql="kmpEK_sql" nativeSql="
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
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
    left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
  	    left join medcase hmc on hmc.id=mc.parent_id
where mc1.id=mc.id and mc.dtype='DepartmentMedCase' and qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mc.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
${department} and dep.id=${param.depId}
and qe.experttype='${param.expertType}' and qek.code='KMP' and dep.name is not null
and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='${param.expertType}') is not null
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
left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id
	left join vocqualityestimationcrit_diagnosis qd_conc
    on qd_conc.vqecrit_id=vqecrit.id and qd_conc.isconcomitant=true
    left join vocidc10 d on d.id=qd.vocidc10_id
  	    left join medcase hmc on hmc.id=mc.parent_id
where mc.dtype='DepartmentMedCase' and qd.vocidc10_id=ds.idc10_id
and mc.dateFinish >= to_date('${dateBegin}','dd.mm.yyyy')
and mc.dateFinish <= to_date('${dateEnd}','dd.mm.yyyy')
	and reg.code='4' and (prior.code='1' and (qd.isconcomitant is null or qd.isconcomitant=false)
    or prior.code='3' and qd.isconcomitant=true)
    and ds.idc10_id=qd.vocidc10_id and d.id=ds.idc10_id
    and case when qd_conc.id is not null then (select count(ds.id) from diagnosis ds
    left join medcase mcase on mcase.id=ds.medcase_id
    left join vocprioritydiagnosis prior on prior.id=ds.priority_id
    left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id
    where mc.id=mcase.id and ds.idc10_id=qd_conc.vocidc10_id)>0 else 1=1 end
    and qek.code='KMP'
${department} and dep.id=${param.depId}
and qe.experttype='${param.expertType}' and qek.code='KMP' and dep.name is not null
and mc.ownerFunction_id=${param.wfId}
  ${param.isDraft}
and (select min(qecC.mark_id)
from qualityestimationcrit qecC
left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id
left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id
where vqemC.criterion_id=vqecrit.id and qeC.expertType='${param.expertType}') is not null
        and case when (select count(distinct mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id)>1
        then mc.id=(select max(mcc.id) from medcase mcc where mcc.parent_id=hmc.id and mcc.dtype='DepartmentMedCase' and mcc.department_id=mc.department_id) else 1=1 end
group by mc.id,pat.id,qec.id
order by mc.id,pat.lastname||' '||pat.firstname||' '||pat.middlename"/>

                <form action="expertKMP.do" method="post" target="_blank">
                    Пациенты, находящиеся в отделении ${param.depname} по экспертизе KP за период с ${param.dateBegin} ${dateTo} врачом ${param.wfname}, в СЛС которых созданы экспертные карты  ${param.draft}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="kmpEK3"
                           openNewWindow="true"
                           action="entityParentView-expert_card.do" idField="3">
                    <msh:tableColumn columnName="#" property="sn"/>
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
    function okDate() {
        /*var from = $('dateBegin').value.split(".");
        var d1 = new Date(from[2], from[1] - 1, from[0]);
        var d2=new Date(2020, 1, 20);
        if(d1<d2) {
            alert("Работа с экспертизой КМР началась с 20.02.2020, раньше этого периода отчётности нет!");
            $('dateBegin').value="20.02.2020";
            return false;
        }*/
        return true;
    }
    eventutil.addEventListener($('dateBegin'), "blur",okDate()) ;

</script>