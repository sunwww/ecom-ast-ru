<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <msh:title mainMenu="StacJournal" title="Отчёт по критическим акушерским состояниям" />

    </tiles:put>
    <tiles:put name="side" type="string" >

    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:sectionTitle>Реестр пациентов с критическим акушерским состоянием</msh:sectionTitle>
        <ecom:webQuery name="patKasList" nativeSql="  select m.id
    ,sc.code as sccode
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as patfio
    ,to_char(pat.birthday,'dd.mm.yyyy') as birthday
    ,to_char(m.dateStart,'dd.mm.yyyy')
    ||case when m.datestart!=sls.dateStart then '(госп. с '||to_char(sls.dateStart,'dd.mm.yyyy')||')' else '' end
    ||case when m.dateFinish is not null then ' выписка '||to_char(m.dateFinish,'dd.mm.yyyy')||' '||cast(m.dischargeTime as varchar(5)) else '' end as datestar
    	,wp.lastname||' '||wp.firstname||' '||wp.middlename as worker
   ,max(dep.name) as depn
    ,list(vdrt.name||' '||vpd.name||' '||mkb.code) as diag
    from medCase m
    left join Diagnosis diag on diag.medcase_id=m.id
    left join vocidc10 mkb on mkb.id=diag.idc10_id
	left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
	left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    left join MedCase as sls on sls.id = m.parent_id
    left join medcase sloAll on sloAll.parent_id=sls.id and sloAll.dtype='DepartmentMedCase'
left join Mislpu dep on dep.id=sloAll.department_id
    left join bedfund as bf on bf.id=m.bedfund_id
    left join StatisticStub as sc on sc.medCase_id=sls.id
     left join WorkFunction wf on wf.id=m.ownerFunction_id
    left join Worker w on w.id=wf.worker_id
    left join Patient wp on wp.id=w.person_id
    left join Patient pat on m.patient_id = pat.id
   left join medcase_coloridentitypatient mcid on mcid.medcase_id=sls.id
left join ColorIdentityPatient cip on cip.id=mcid.colorsidentity_id
left join VocColorIdentityPatient vcid on vcid.id=cip.voccoloridentity_id
left join voccolor vcr on vcr.id=vcid.color_id
    where m.DTYPE='DepartmentMedCase'
    and m.transferDate is null
     and (m.dateFinish is null or m.dateFinish=current_date and m.dischargetime>CURRENT_TIME)
    and mcid.colorsidentity_id is not null
    and vcid.id in (18)
    group by  m.id,m.dateStart,pat.lastname,pat.firstname
    ,pat.middlename,pat.birthday,sc.code,wp.lastname,wp.firstname,wp.middlename,sls.dateStart
    ,bf.addCaseDuration,m.dateFinish,m.dischargeTime,sls.id
    order by pat.lastname,pat.firstname,pat.middlename
"
        />
        <msh:table name="patKasList" action="entityParentView-stac_ssl.do" idField="1" openNewWindow="true" printToExcelButton="Сохранить в EXCEL">
            <msh:tableColumn property="sn" columnName="#"/>
            <msh:tableColumn columnName="Стат.карта" property="2"/>
            <msh:tableColumn columnName="ФИО пациента" property="3"/>
            <msh:tableColumn columnName="Год рождения" property="4"/>
            <msh:tableColumn columnName="Дата поступления" property="5"/>
            <msh:tableColumn columnName="Леч.врач" property="6"/>
            <msh:tableColumn columnName="Отделение" property="7"/>
            <msh:tableColumn columnName="Диагноз" property="8"/>
        </msh:table>
    </tiles:put>
</tiles:insert>

