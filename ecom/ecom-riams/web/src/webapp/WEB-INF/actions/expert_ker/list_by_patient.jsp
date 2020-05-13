<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Disability">Список ВК по пациенту</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:dis_menu currentAction="journalOpenKER"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
	    <msh:section title="Список ВК по пациенту">
	    <ecom:webQuery nameFldSql="journal_expert_sql" name="journal_expert" nativeSql="
	select 
	cec.id,to_char(expertDate,'dd.mm.yyyy')
	,ovwf.name||' '||owp.lastname||' '||owp.firstname||' '||owp.middlename as workfunction
	,veps.code||', '||cec.profession as job
	,mkb.code as mkbcode
	,vepc.code as vepccode
	,vemc.code||coalesce(', № Л/Н'||dd.number,'')||', д. '||(cec.orderDate-cec.disabilityDate+1)||', '||ves.code as disability
	,veds.name||' '||cec.deviationStandardsText as deviationStandards
	,cec.defects as defects,cec.resultStep as resultStep
	,vec.code||' '||coalesce(to_char(cec.conclusionDate,'dd.mm.yyyy'),'')||' '||coalesce(cec.additionInfo,'') as conclusion
	,cec.orderHADate as orderHADate,cec.conclusionHA as conlusionHA
	,cec.receiveHADate as receiveHADate,cec.additionInfoHA as addtionInfoHA
	from  ClinicExpertCard cec 
	left join MedCase slo on slo.id=cec.medCase_id
	left join MisLpu ml on ml.id=slo.department_id
	left join WorkFunction owf on owf.id=cec.orderFunction_id
	left join Worker ow on ow.id=owf.worker_id
	left join Patient owp on owp.id=ow.person_id
	left join VocWorkFunction ovwf on ovwf.id=owf.workFunction_id
	left join Patient p on p.id=cec.patient_id
	left join VocSex vs on vs.id=p.sex_id
	left join VocExpertPatientStatus veps on veps.id=cec.patientStatus_id
	left join VocIdc10 mkb on mkb.id=cec.mainDiagnosis_id
	left join VocExpertPatternCase vepc on vepc.id=cec.patternCase_id
	left join DisabilityDocument dd on dd.id=cec.disabilityDocument_id
	left join VocExpertModeCase vemc on vemc.id=cec.modeCase_id
	left join VocExpertSubject ves on ves.id=cec.subjectCase_id
	left join VocExpertDeviationStandards veds on veds.id=cec.deviationStandards_id
	left join VocExpertConclusion vec on vec.id=cec.conclusion_id
	left join Address2 a on a.addressId=p.address_addressId
	left join Omc_KodTer okt on okt.id=p.territoryRegistrationNonresident_id
	left join Omc_Qnp oq on oq.id=p.TypeSettlementNonresident_id
	left join Omc_StreetT ost on ost.id=p.TypeStreetNonresident_id
	
	    where cec.patient_id=${param.id} and cec.expertDate is not null
	    order by cec.expertDate
	    " />
	   
	    <msh:table name="journal_expert"
	    viewUrl="entityParentView-expert_ker.do?short=Short" 
	     action="entityParentView-expert_ker.do" idField="1" >
	      <msh:tableColumn columnName="#" property="sn" />
	      <msh:tableColumn columnName="Дата экспертизы" property="2" />
	      <msh:tableColumn columnName="ФИО врача" property="3" />
	      <msh:tableColumn columnName="Соц.статус, профессия" property="4" />
	      <msh:tableColumn columnName="Диагноз" property="5" />
	      <msh:tableColumn columnName="Характеристика случая экспертизы" property="6" />
	      <msh:tableColumn columnName="Вид, предмет экспертизы" property="7" />
	      <msh:tableColumn columnName="Отклонения от стандарта" property="8" />
	      <msh:tableColumn columnName="Дефекты" property="9" />
	      <msh:tableColumn columnName="Достижения ЛПМ" property="10" />
	      <msh:tableColumn columnName="Обоснование заключения" property="11" />
	      <msh:tableColumn columnName="Дата направ. в бюро МСЭ" property="12" />
	      <msh:tableColumn columnName="Заключеие МСЭ" property="13" />
	      <msh:tableColumn columnName="Дата получения закл. МСЭ" property="14" />
	      <msh:tableColumn columnName="Доп. инф. по закл. др. учреж." property="15" />
	    </msh:table>
	    
	    </msh:section>
    </tiles:put>

</tiles:insert>