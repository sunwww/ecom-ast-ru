<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <msh:title mainMenu="StacJournal" title="Отчёт по умершим с ПЦР" />

    </tiles:put>
    <tiles:put name="side" type="string" >

    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:sectionTitle>Умершие с ПЦР</msh:sectionTitle>
        <ecom:webQuery name="analysisCovidDeadList" nativeSql=" select distinct sls.id
,pat.patientinfo as patfio
 , (SELECT count(*)
FROM regexp_matches(cast ((select to_json(array_agg(t)) from	(select cip.id
,vc.code as colorCode,vc.picture as picture
,to_char(cip.startdate,'dd.mm.yyyy') as stDate
, cip.info as info
,vcip.name as vsipnameJust
from vocColorIdentityPatient vcip
left join coloridentitypatient cip on cip.voccoloridentity_id=vcip.id
left join voccolor vc on vcip.color_id=vc.id
 left join medcase_coloridentitypatient
 ss on ss.colorsidentity_id=cip.id where
(medcase_id=sls.id or medcase_id=m.id)   and (cip.startdate<=current_date and cip.finishdate is null
and (vcip.code='LAB_COVID_PLUS' or vcip.code='LAB_COVID_MINUS' or vcip.code='LAB_COVID_USL')
 or (cast ((cip.finishdate||' '||cip.finishtime) as TIMESTAMP) > current_timestamp)) order by cip.startdate asc) as t) as varchar), 'covid_plus.png', 'g')) as plus

 , (SELECT count(*)
FROM regexp_matches(cast ((select to_json(array_agg(t)) from	(select cip.id
,vc.code as colorCode,vc.picture as picture
,to_char(cip.startdate,'dd.mm.yyyy') as stDate
, cip.info as info
,vcip.name as vsipnameJust
from vocColorIdentityPatient vcip
left join coloridentitypatient cip on cip.voccoloridentity_id=vcip.id
left join voccolor vc on vcip.color_id=vc.id
 left join medcase_coloridentitypatient
 ss on ss.colorsidentity_id=cip.id where
(medcase_id=sls.id or medcase_id=m.id)   and (cip.startdate<=current_date and cip.finishdate is null
and (vcip.code='LAB_COVID_PLUS' or vcip.code='LAB_COVID_MINUS' or vcip.code='LAB_COVID_USL')
 or (cast ((cip.finishdate||' '||cip.finishtime) as TIMESTAMP) > current_timestamp)) order by cip.startdate asc) as t) as varchar), 'covid_minus.png', 'g')) as minus

 ,sls.datefinish
from medCase m
left join MedCase as sls on sls.id = m.parent_id
left join medcase sloAll on sloAll.parent_id=sls.id and sloAll.dtype='DepartmentMedCase'
left join Patient pat on m.patient_id = pat.id
left join medcase_coloridentitypatient mcid on mcid.medcase_id=sls.id
left join ColorIdentityPatient cid on cid.id=mcid.colorsidentity_id
left join VocColorIdentityPatient vcid on vcid.id=cid.voccoloridentity_id
left join voccolor vcr on vcr.id=vcid.color_id
                left join bedfund as bf on bf.id=m.bedfund_id
                left join vocbedtype vbt on vbt.id=bf.bedType_id
                left join vochospitalizationresult vhr on vhr.id=sls.result_id
where m.DTYPE='DepartmentMedCase'
and sls.datefinish is not null
  and vbt.code='14' and vhr.id=6
  and sls.datestart >'2020-03-01'
and exists(select mcidi.* from medcase_coloridentitypatient mcidi
left join ColorIdentityPatient cidi on cidi.id=mcidi.colorsidentity_id
left join VocColorIdentityPatient vcidi on vcidi.id=cidi.voccoloridentity_id
where (mcidi.medcase_id=sls.id or mcidi.medcase_id=m.id) and (vcidi.code='LAB_COVID_PLUS' or vcidi.code='LAB_COVID_MINUS' or vcidi.code='LAB_COVID_USL'))
group by  pat.patientinfo,sls.id,m.id
order by  sls.datefinish
"
        />
        <msh:table name="analysisCovidDeadList" action="entityParentView-stac_slo.do" idField="1" openNewWindow="true" printToExcelButton="Сохранить в EXCEL">
            <msh:tableColumn property="sn" columnName="#"/>
            <msh:tableColumn columnName="Пациент" property="2" />
            <msh:tableColumn columnName="Кол-во положительных" property="3" />
            <msh:tableColumn columnName="Кол-во отрицательных" property="4" />
        </msh:table>
    </tiles:put>
    <tiles:put name="side" type="string">
        <tags:stac_journal currentAction="${param.action}"/>
    </tiles:put>
</tiles:insert>

