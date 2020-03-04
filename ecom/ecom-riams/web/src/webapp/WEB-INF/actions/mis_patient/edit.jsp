<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="ru.ecom.web.util.ActionUtil"%>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
  <ecom:webQuery isReportBase="${isReportBase}" name="passport_sql" nativeSql="select id,name from Vocidentitycard where code='14'"/>
   <%
   ActionUtil.getValueByList("passport_sql", "passportRF", request) ;
   String passportRF = (String)request.getAttribute("passportRF") ;
   request.setAttribute("passportRF", passportRF);
   %>
    <msh:ifFormTypeIsView formName="mis_patientForm" guid="c1b89933-a744-46a8-ba32-014ac1b4fcb4">
    <div id='identityDiv'></div>
    <div id='patientInfoDiv'></div>
    <msh:ifInRole roles="/Policy/Mis/Patient/CheckByFond">
    	<msh:separator label="Проверка пациента по базе фонда" colSpan="4"/>
    	<msh:link action="javascript:checkPatientBySnils()">Проверка по СНИЛСу</msh:link>
    	<msh:link action="javascript:checkPatientByFioDr()">Проверка по ФИО+ДР</msh:link>
    	<msh:link action="javascript:checkPatientByDocument()">Проверка по документам</msh:link>
    	<msh:link action="javascript:checkPatientByCommonNumber()">Проверка по RZ</msh:link>
    	<msh:link action="javascript:showPatientByFondHistory(1)" roles="/Policy/Mis/Patient/CheckByFond/History">Журнал проверок по ФИО</msh:link>
    	<msh:link action="javascript:showPatientByFondHistory(2)" roles="/Policy/Mis/Patient/CheckByFond/History">Журнал проверок по RZ</msh:link>
    </msh:ifInRole>

              <table>
          <tr valign="top">
          <td style="padding-right: 8px">
      <msh:ifInRole roles="/Policy/Poly/Medcard/View" guid="a7036440-353f-4667-a18e-a0da4885cdaa">
          <msh:ifPropertyIsTrue guid="123" formName="mis_patientForm" propertyName="noActuality" invert="true" >
        <msh:section title="Мед.карты" createUrl="entityParentPrepareCreate-poly_medcard.do?id=${param.id}">
		          <ecom:webQuery name="medcard"
		        nativeSql="select m.id,m.number, ml.name as mlname
		         from MedCard m
		         left join MisLpu ml on ml.id=m.lpu_id
		         where m.person_id='${param.id}'
		         "
		         /><msh:table viewUrl="entityShortView-poly_medcard.do"
			         hideTitle="false" disableKeySupport="false" idField="1"
			         name="medcard" action="entityParentView-poly_medcard.do"
			         disabledGoFirst="false" disabledGoLast="false"
			         >
		            <msh:tableColumn columnName="Номер" identificator="false" property="2" guid="6d12646caf2-c76d-4e99-a8d2-afc6ef8bcdf6" />
		            <msh:tableColumn columnName="ЛПУ" identificator="false" property="3" guid="6d12646caf2-c76d-4e99-a8d2-afc6ef8bcdf6" />
		          </msh:table>
        </msh:section>
          </msh:ifPropertyIsTrue>
      </msh:ifInRole>
      </td><td>
      <msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry" guid="a7036440-353f-4667-a18e-a0da4885cdaa">
        <msh:section title="Мед.карты (псих.помощью)" createRoles="/Policy/Mis/Psychiatry/CareCard/Create" createUrl="entityParentPrepareCreate-psych_careCard.do?id=${param.id}">
		        <ecom:webQuery name="psychCard"
		        nativeSql="select pcc.id,pcc.cardNumber ||' ('||lpu.shortname||')'
,(select list(distinct to_char(po.startDate,'dd.mm.yyyy')||' '||vpac.code)
from PsychiaticObservation po
left join VocPsychAmbulatoryCare vpac on vpac.id=po.ambulatoryCare_id
where po.careCard_id=pcc.id and
(select max(po1.startDate)
from PsychiaticObservation po1
where po1.careCard_id=pcc.id)=po.startDate) as diag1
,(select list(distinct mkb.code) from Diagnosis d
left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id
left join vocidc10 mkb on mkb.id=d.idc10_id
where d.patient_id=pcc.patient_id and d.medcase_id is null and vpd.code='1'
and
(
select max(d1.establishDate) from Diagnosis d1  left join VocPriorityDiagnosis vpd1 on vpd1.id=d1.priority_id
 where d1.patient_id=pcc.patient_id and d1.medcase_id is null and vpd1.code='1'
)=d.establishDate ) as diag3
,(select list(distinct mkb.code) from Diagnosis d left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id
left join medcase mc on mc.id=d.medcase_id
left join vocidc10 mkb on mkb.id=d.idc10_id where mc.patient_id=pcc.patient_id and d.medcase_id is not null and vpd.code='1' and upper(mc.dtype)='SHORTMEDCASE' and (select max(mc1.datestart) from Diagnosis d1
left join VocPriorityDiagnosis vpd1 on vpd1.id=d1.priority_id
left join medcase mc1 on mc1.id=d1.medcase_id
where mc1.patient_id=pcc.patient_id and d1.medcase_id is not null and vpd1.code='1' and upper(mc1.dtype)='SHORTMEDCASE')=MC.DATESTART  )
||' от '|| (select TO_CHAR(max(mc1.datestart),'DD.MM.YYYY') from Diagnosis d1
left join VocPriorityDiagnosis vpd1 on vpd1.id=d1.priority_id
left join medcase mc1 on mc1.id=d1.medcase_id
where mc1.patient_id=pcc.patient_id and d1.medcase_id is not null and vpd1.code='1')
as diag_pol
,(select list(distinct mkb.code) from Diagnosis d left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id
left join medcase mc on mc.id=d.medcase_id
LEFT JOIN VOCDIAGNOSISREGISTRATIONTYPE VDRT ON VDRT.ID=D.REGISTRATIONTYPE_ID
left join vocidc10 mkb on mkb.id=d.idc10_id where mc.patient_id=pcc.patient_id and d.medcase_id is not null and vpd.code='1' and vdrt.code='3' and upper(mc.dtype)='HOSPITALMEDCASE' AND (select max(mc1.dateFINISH) from Diagnosis d1
left join VocPriorityDiagnosis vpd1 on vpd1.id=d1.priority_id
left join medcase mc1 on mc1.id=d1.medcase_id
LEFT JOIN VOCDIAGNOSISREGISTRATIONTYPE VDRT1 ON VDRT1.ID=D1.REGISTRATIONTYPE_ID
where mc1.patient_id=pcc.patient_id and d1.medcase_id is not null and vpd1.code='1' and vdrt1.code='3' and upper(mc1.dtype)='HOSPITALMEDCASE')=MC.DATEFINISH)
||' от '
|| (select TO_CHAR(max(mc1.datefinish),'DD.MM.YYYY') from Diagnosis d1
left join VocPriorityDiagnosis vpd1 on vpd1.id=d1.priority_id
left join medcase mc1 on mc1.id=d1.medcase_id
LEFT JOIN VOCDIAGNOSISREGISTRATIONTYPE VDRT1 ON VDRT1.ID=D1.REGISTRATIONTYPE_ID
where mc1.patient_id=pcc.patient_id  and vpd1.code='1' and vdrt1.code='3' and upper(mc1.dtype)='HOSPITALMEDCASE' )
as diag_hosp

from PsychiatricCareCard pcc
left join mislpu lpu on lpu.id=pcc.lpu_id
where pcc.patient_id='${param.id}'
		         "
		         />

		        <msh:tableNotEmpty name="psychCard">
			        <msh:table viewUrl="entityShortView-psych_careCard.do" hideTitle="false" disableKeySupport="false" idField="1" name="psychCard" action="entityParentView-psych_careCard.do" disabledGoFirst="false" disabledGoLast="false">
			            <msh:tableColumn columnName="Номер карты" identificator="false" property="2" />
			            <msh:tableColumn columnName="Наблюдения" identificator="false" property="3" />
			            <msh:tableColumn columnName="МКБ10 для ОМКО" identificator="false" property="4" />
			            <msh:tableColumn columnName="МКБ10 по талону" identificator="false" property="5" />
			            <msh:tableColumn columnName="МКБ10 по выписке" identificator="false" property="6" />
			        </msh:table>
		        </msh:tableNotEmpty>
		        <msh:tableEmpty name="psychCard">
		        	<ecom:webQuery name="lastDiag1" nativeSql="select
    	m.id,to_char(m.dateStart,'dd.mm.yyyy')||' '||list(mkb.code) as dateFrom
    	from medCase m
    	left join Diagnosis d on d.medCase_id=m.id
    	left join VocIdc10 mkb on mkb.id=d.idc10_id
    	where m.patient_id=${param.id} and UPPER(m.DTYPE) IN ('VISIT','SHORTMEDCASE')
    	and m.dateStart is not null
    	group by m.id,m.dateStart
    	order by m.dateStart desc
    	" maxResult="1" />
    				<msh:table hideTitle="false" disableKeySupport="false" idField="1" name="lastDiag1" action="javascript:void(0)" disabledGoFirst="false" disabledGoLast="false">
			            <msh:tableColumn columnName="Диагноз посл. посещения" identificator="false" property="2" />
			        </msh:table>
		        </msh:tableEmpty>
        </msh:section>
      </msh:ifInRole>
      </td>
	<td style="padding-right: 4px">

    	<ecom:webQuery name="lastVisit1" nativeSql="select
    	m.id,m.dateStart as dateFrom
    	,coalesce(vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename
    	,vwf1.name||' '||wp1.lastname||' '||wp1.firstname||' '||wp1.middlename) as worker
    	from medCase m
    	left join workfunction wf on wf.id=m.workFunctionExecute_id
    	left join vocworkfunction vwf on vwf.id=wf.workFunction_id
    	left join workfunction wf1 on wf1.id=m.workFunctionPlan_id
    	left join vocworkfunction vwf1 on vwf1.id=wf1.workFunction_id
    	left join WorkCalendarDay wcd on wcd.id=m.datePlan_id
    	left join worker w on w.id=wf.worker_id
    	left join patient wp on wp.id=w.person_id
    	left join worker w1 on w1.id=wf1.worker_id
    	left join patient wp1 on wp1.id=w1.person_id
    	where m.patient_id=${param.id} and upper(m.DTYPE) in ('VISIT','SHORTMEDCASE')
    	and m.dateStart is not null
    	order by m.dateStart desc
    	" maxResult="1" />

     <msh:section title="Последнее посещение <a href='print-begunok.do?s=SmoVisitService&amp;m=printDirectionByPatient&patientId=${param.id}' target='_blank'>бегунок</a>"
     viewRoles="/Policy/Mis/MedCase/Direction/View" shortList="js-mis_patient-viewDirection.do?id=${param.id}">
    	<msh:table name="lastVisit1" action="entitySubclassView-mis_medCase.do" idField="1">
	    	<msh:tableColumn property="2" columnName="Дата"/>
    		<msh:tableColumn property="3" columnName="Специалист"/>
    	</msh:table>
     </msh:section>
    </td>
      </tr>
        </table>

    </msh:ifFormTypeIsView>


    <msh:form action="entitySaveGoView-mis_patient.do" defaultField="lastname" guid="886bd847-1725-44c0-898b-db8de7a06ade">

      <msh:hidden guid="hiddenid123" property="id" />
      <msh:hidden property="saveType" guid="30dc954b-c5f2-49ed-b001-31042904724c" />
      <msh:hidden property="address" guid="b33c0964-6c48-4aac-9896-6d46c1065e1f" />
      <msh:hidden property="flatNumber" guid="69487c69-ba25-4a90-8842-b7077fb48bee" />
      <msh:hidden property="houseBuilding" guid="ab904ba6-ed1c-4bc0-a641-81baed6f8cbd" />
      <msh:hidden property="houseNumber" guid="0468a9e5-872e-4491-a71a-a24ebc07202e" />
      <msh:hidden property="realAddress" guid="b33c0964-6c48-4aac-9896-6d46c1065e1f" />
      <msh:hidden property="realFlatNumber" guid="69487c69-ba25-4a90-8842-b7077fb48bee" />
      <msh:hidden property="realHouseBuilding" guid="ab904ba6-ed1c-4bc0-a641-81baed6f8cbd" />
      <msh:hidden property="realHouseNumber" guid="0468a9e5-872e-4491-a71a-a24ebc07202e" />
      <msh:hidden property="realZipcode" guid="0468a9e5-872e-4491-a71a-a24ebc07202e" />
      <msh:hidden property="colorType" guid="0468a9-a24ebc07202e" />
      <msh:hidden property="zipcode" guid="0468a9-a24ebc07202e" />


        <msh:hidden property="apartmentNonresident"/>
		<msh:hidden property="houseNonresident"/>
		<msh:hidden property="buildingHousesNonresident"/>
		<msh:hidden property="territoryRegistrationNonresident"/>
		<msh:hidden property="regionRegistrationNonresident"/>
		<msh:hidden property="typeSettlementNonresident"/>
		<msh:hidden property="settlementNonresident"/>
		<msh:hidden property="typeStreetNonresident"/>
		<msh:hidden property="streetNonresident"/>
		<msh:hidden property="nonresidentZipcode"/>


      <msh:panel colsWidth="15%, 10%, 1%" guid="a44a0d03-0934-4b43-87e7-77e7fc51af76">
      <msh:ifFormTypeIsNotView formName="mis_patientForm">

      	<msh:row >
      		<msh:textField property="patientSync" label="Код синх." />
      		<msh:textField property="commonNumber" label="Единый номер" horizontalFill="true"/>
      	</msh:row>
      	</msh:ifFormTypeIsNotView>
      	      <msh:ifFormTypeIsView formName="mis_patientForm">
      	<msh:hidden property="birthday"/>
      	<msh:hidden property="lastname"/>
      	<msh:hidden property="firstname"/>
      	<msh:hidden property="middlename"/>
      	<msh:row>
      		<td colspan="4"><div id='medPolicyInformation' style="display: none;" class="errorMessage"></div></td>
      	</msh:row>
      	<msh:row>
      		<td colspan="4" id="syncRow" >
      		</td>
      		</msh:row>
      	<msh:row>
      		<msh:textField property="patientSync" label="Код синх." viewOnlyField="true"/>
      		<msh:textField property="editDate" label="Дата редакт." viewOnlyField="true"/>

      	</msh:row>

      	</msh:ifFormTypeIsView>
        <msh:ifFormTypeIsNotView formName="mis_patientForm" guid="a71e4812-8951-4dcc-918f-dc1a440cc9e2">
          <msh:row  guid="70af394e-52bb-4df4-8ea8-065f194678cf">
            <msh:textField guid="lastname123" property="lastname" label="Фамилия" size="30" fieldColSpan="3"  />
          </msh:row>
          <msh:row guid="ccbe7fee-de86-4c80-9691-c83b60c44e5d">
            <msh:textField guid="firstname123" property="firstname" label="Имя" size="30" fieldColSpan="3" />
          </msh:row>
          <msh:row guid="787b2b06-1c08-4621-b530-6b6734ec955e">
            <msh:textField property="middlename" label="Отчество" size="30" fieldColSpan="3" guid="b32db1c2-6d8a-4164-b3c6-1f5ab2baaf1b" />
          </msh:row>

          <msh:row guid="33effb55-c5d6-4306-804e-a9cd7a8f46e5">
            <msh:textField property="birthday" label="Дата рождения" guid="06db8372-9a2e-4737-8703-9e2b96c06782" />
	          <msh:textField property="age" label="Возраст" horizontalFill="true" viewOnlyField="true"  />
          </msh:row>
        </msh:ifFormTypeIsNotView>
        <msh:ifFormTypeIsView formName="mis_patientForm">
          <msh:row guid="33effb55-c5d6-4306-804e-a9cd7a8f46e5">
	          <msh:textField property="age" label="Возраст" horizontalFill="true" viewOnlyField="true"  fieldColSpan="1"/>
      		<msh:textField property="commonNumber" label="Единый номер" horizontalFill="true"/>
          </msh:row>
        </msh:ifFormTypeIsView>
        <msh:row guid="ab3573ac-7eb3-438d-b732-1e7cc8aeafc4">
          <msh:autoComplete guid="sex123" property="sex" label="Пол" vocName="vocSex" />
          <msh:textField property="snils" label="СНИЛС" size="20" />
          </msh:row>
          <msh:ifInRole roles="/Policy/Mis/Patient/Newborn">
	          <msh:row>
	          	<msh:textField property="newbornWeight" label="Вес новор."/>
	          	<msh:autoComplete property="newborn" label="Новорожденный" fieldColSpan="3" vocName="vocNewBorn" horizontalFill="true" />
	          </msh:row>
          </msh:ifInRole>
          <msh:ifNotInRole roles="/Policy/Mis/Patient/Newborn">
          	<msh:hidden property="newborn"/>
          </msh:ifNotInRole>
        <msh:row guid="e8f9a2e2-5361-41e6-b486-9c92b5693ac8">
          <msh:textField property="works" fieldColSpan="3" label="Место работы" horizontalFill="true" />
        </msh:row>
        <msh:row guid="b8dbed14-010b-44b5-93b7-3d32ccdc8b35">
          <msh:autoComplete property="categoryChild" fieldColSpan="1" label="Кат. ребенка" horizontalFill="true" vocName="vocCategoryChild" />
          <msh:textField property="workPost" label="Должность" fieldColSpan="1" size="30" guid="269ff932-ca36-4d49-b7e2-a088df8ea99e" horizontalFill="true" />
        </msh:row>
        <msh:row guid="8249d4e3-205c-456c-8899-33d055fe7940">
          <msh:autoComplete property="socialStatus" fieldColSpan="1" label="Социальный статус" horizontalFill="true" vocName="vocSocialStatus" guid="016dc681-4c60-4c68-9855-7b84e62373f7" />
          <msh:autoComplete property="additionStatus" fieldColSpan="1" label="Доп. статус" horizontalFill="true" vocName="vocAdditionStatus" guid="016dc681-4c60-4c68-9855-7b84e62373f7" />
        </msh:row>
        <msh:row guid="835168a8-7ce2-44ab-b587-f3c7da3a6730">
          <msh:autoComplete property="ethnicity" horizontalFill="true" label="Национальность"  vocName="vocEthnicity" guid="8c1c2414-0b8b-4445-a39a-59328d39fd88" />
          <msh:autoComplete property="nationality" label="Гражданство" vocName="omcOksm" horizontalFill="true"/>
        </msh:row>
        <msh:row guid="c1d84cb4-8c34-4951-a1e3-ee6a8bc2052b">
          <msh:autoComplete property="marriageStatus" label="Семейное положение"  vocName="vocMarriageStatus" guid="fdac3d46-dbd2-44c0-8601-44015df58401" />
          <msh:autoComplete property="educationType" label="Вид образования" horizontalFill="true"  vocName="vocEducationType" guid="59a5b6dc-662c-472e-a46c-8f2e184bdd48" />
        </msh:row>

        <msh:row >
          <msh:textField property="deathDate" label="Дата смерти" />
          <msh:textField property="notice" label="Примечание"  horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:textField property="phone" label="Телефон" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
          <msh:row>
              <msh:label property="mobileAppPhoneNumber"  fieldColSpan="3"/>
          </msh:row>

        <msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        	<msh:row>
        		<msh:autoComplete property="livelihoodSource" vocName="vocLivelihoodSource" label="Источник ср-в сущес." horizontalFill="true" />
        		<msh:autoComplete property="residenceConditions" vocName="vocResidenceConditions" label="Условия прожив." horizontalFill="true" />
        	</msh:row>
        	<msh:row>
        		<msh:textField property="generalEducationGradeNumber" label="Кол-во закон.классов ООШ"/>
        		<msh:textField property="firstRegistrationDate" label="Дата 1 заполн."/>
        	</msh:row>
        	<msh:row>
        		<msh:checkBox property="townsman" label="Горожанин"/>
        		<msh:checkBox property="greatPatrioticWarPaticipant" label="Участник ВОВ"/>
        	</msh:row>
        	<msh:row>
        		<msh:checkBox property="familyResident" label="Проживает в семье"/>
        		<msh:checkBox property="incapable" label="Недееспособный"/>
        	</msh:row>

        </msh:ifInRole>
        <msh:row guid="050f23d4-4e9f-4c6b-8739-5fd78bdff3d0">
          <msh:checkBox property="noActuality" label="Запись на пациента не действует" fieldColSpan="3" horizontalFill="true" />
        </msh:row>

        <msh:separator colSpan="4" label="Документ, удостоверяющий личность" />
        <msh:row guid="391bb191-d1f0-49d1-9e01-cc432e742118">
          <msh:autoComplete property="passportType" label="Тип" vocName="vocIdentityCard" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="711d49d7-1dd4-4af3-be58-9b1d766961c3">
          <msh:textField property="passportSeries" label="Серия" />
          <msh:textField property="passportNumber" label="Номер" />
        </msh:row>
        <msh:ifFormTypeIsView formName="mis_patientForm">
	        <msh:row guid="8f6ac7f2-e407-42e4-813c-950a6e2ab33a">
	          <msh:textField property="passportDateIssued" label="Дата выдачи"  />
	          <msh:textField property="passportWhomIssued" label="Кем выдан" horizontalFill="true" size="40" guid="7d4d3d33-7bd7-4eff-9d32-83fbdd6715bc" />
	        </msh:row>
	        <msh:row >
	          <msh:textField property="passportCodeDivision" label="Код подраздел."/>
	          <msh:textField property="birthPlace" label="Др. место рождения" horizontalFill="true" size="40" />
	        </msh:row>
         	<msh:row>
        		<msh:autoComplete fieldColSpan="3" property="passportBirthPlace" label="Место рождения" horizontalFill="true" vocName="vocPassportBirthPlace"/>
        	</msh:row>
        	</msh:ifFormTypeIsView>
        <msh:ifFormTypeIsNotView formName="mis_patientForm">
	        <msh:row guid="8f6ac7f2-e407-42e4-813c-950a6e2ab33a">
	          <msh:textField property="passportDateIssued" label="Дата выдачи"  />
	          <msh:autoComplete property="passportDivision" label="Подразделение" horizontalFill="true" vocName="vocPassportWhomIssue"/>
	        </msh:row>
	        <msh:row >
	          <msh:textField property="passportCodeDivision" label="Код подраздел."/>
	          <msh:textField property="passportWhomIssued" label="Кем выдан" horizontalFill="true" size="40" guid="7d4d3d33-7bd7-4eff-9d32-83fbdd6715bc" />
	        </msh:row>

        	<msh:row>
        		<msh:autoComplete fieldColSpan="3" property="passportBirthPlace" label="Место рождения" horizontalFill="true" vocName="vocPassportBirthPlace"/>
        	</msh:row>

	        <msh:row>
	          <msh:textField property="birthPlace" label="Др.место рождения" horizontalFill="true" size="40" fieldColSpan="3"/>
	        </msh:row>
        </msh:ifFormTypeIsNotView>

        <msh:separator colSpan="4" label="Адрес регистрации" guid="5a353bf6-7fee-45e5-96ec-7b019182cfcb" />
        <msh:row guid="e034c8ae-b8b7-40f8-82a7-eac5979cc85e">
          <td colspan="1" title="Адрес (addressField)" class="label">
            <label id="addressFieldLabel" for="addressField"> Российский адрес:</label>
          </td>
          <td colspan="5" class="addressField" id="addressFieldRow">
            <input title="АдресNoneField" class=" horizontalFill" id="addressField" name="addressField" size="10" value="Адрес... " type="text" />
          </td>
        </msh:row>
        <msh:row guid="5f7aa4f8-3933-4dff-8700-80da510d5d77">
          <msh:autoComplete property="rayon" label="Район" vocName="vocRayon" fieldColSpan="3" horizontalFill="true" guid="cc3da2e5-757d-4971-81df-eea01ee6315b" />
        </msh:row>
         <msh:row guid="8d2-e407-42e4-813c-9s33a">
          <msh:textField property="foreignRegistrationAddress" label="Иностранный адрес" horizontalFill="true" fieldColSpan="3" guid="abd74-48cb-4a8b-9f82-63s98e" />
        </msh:row>
        <msh:row>

        <td colspan="1" title="Адрес (nonresidentAddressField)" class="label">
            <label id="nonresidentAddressFieldLabel" for="nonresidentAddressField"> Другой адрес:</label>
          </td>
          <td colspan="5" class="addressField">
            <input title="АдресNoneField" class=" horizontalFill" id="nonresidentAddressField" name="nonresidentAddressField" size="10" value="Адрес... " type="text" />
          </td>
        </msh:row>
        <msh:separator colSpan="4" label="Адрес места проживания" guid="5a353bf6-7fee-45e5-96ec-7b019182cfcb" />
        <msh:row guid="e034c8ae-b8b7-40f8-82a7-eac5979cc85e">
          <td colspan="1" title="Адрес (addressRealField)" class="label">
            <label id="addressRealFieldLabel" for="addressRealField"> Российский адрес:</label>
          </td>
          <td colspan="5" class="realAddressField">
            <input title="АдресNoneField" class=" horizontalFill" id="realAddressField" name="realAddressField" size="10" value="Адрес... " type="text" />
          </td>
        </msh:row>
        <msh:row guid="8dr2-e407-9s33a">
          <msh:textField property="foreignRealAddress" label="Иностранный адрес" horizontalFill="true" fieldColSpan="3" guid="abeed74-48cb-4a8b-9f82-63wws98e" />
        </msh:row>
        <msh:row guid="5f7aa4f8-3933-4dff-8700-80da510d5d77">
          <msh:autoComplete property="realRayon" label="Район" vocName="vocRayon" fieldColSpan="3" horizontalFill="true" guid="cc3da2e5-757d-4971-81df-eea01ee6315b" />
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isCompatriot" label="Соотечественник"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="createDate" label="Дата создания" viewOnlyField="true"/>
        	<msh:textField property="createUsername" label="Пользователь" viewOnlyField="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="editDate" label="Дата редактирования" viewOnlyField="true"/>
        	<msh:textField property="editUsername" label="Пользователь" viewOnlyField="true"/>
        </msh:row>

         <msh:ifFormTypeIsNotView formName="mis_patientForm">
        <msh:separator colSpan="4" label="Добавить полис и прикрепление" guid="d4871cf3-d393-47e3-9d7b-9a5625b0000" />
        <msh:row styleId="rowCheckBoxPolisPrik" guid="c17b2812-85c2-4042-9924-ae99dc3f0000">
          <msh:checkBox property="createNewOmcPolicy" label="Добавить новый полис ОМС" guid="9988ef1b-490f-4290-996b-e559034784c0" />
         <msh:ifInRole roles="/Policy/Mis/Patient/AttachedByDepartment/Create" guid="308cce42-ff6f-43a0-a8ae-6e445dfe187a">
             <msh:checkBox property="createNewAttachment" label="Создать прикрепление" />
          </msh:ifInRole>
        </msh:row>
        </msh:ifFormTypeIsNotView>
        <!--  По какому адресу участка прикреплен -->
        <msh:ifFormTypeIsView formName="mis_patientForm" guid="522244be-3721-4db5-94ea-71f98ce9f391">
          <msh:row styleId="rowLpuAreaAddressText" guid="64d1411d-071f-4852-9af1-bde19f84b4f3">
            <msh:autoComplete property="lpuAreaAddressText" label="Адрес" vocName="lpuAreaAddressText" fieldColSpan="3" horizontalFill="true" guid="51ee2cd2-0f6e-477d-967a-eb4e3590bd2b" />
          </msh:row>
        </msh:ifFormTypeIsView>
        <!--  Добавление нового полиса -->
        <msh:ifFormTypeIsNotView formName="mis_patientForm" guid="817c4185-90ef-4807-ac71-fad06ae151b6">
          <msh:panel colsWidth="15%, 5%, 5%,-" styleId="tableNewOmcPolicy" guid="aada20f1-2321-43bf-a416-ddaf511e15a0">
            <msh:separator label="Добавление нового полиса ОМС" colSpan="4" guid="f4cdcf88-7896-4a47-beea-90823c9fc755" />
                <msh:row>
                    <msh:autoComplete vocName="vocMedPolicyOmc" property="policyOmcForm.type" label="Тип полиса" horizontalFill="true" fieldColSpan="3"/>
                </msh:row>
                <msh:row>
                	<msh:textField property="policyOmcForm.commonNumber" label="Единый номер" fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
            <msh:row guid="3089fafa-d530-454a-b9b6-fa0063a74650">
              <msh:textField property="policyOmcForm.series" label="Серия" guid="22627f62-8708-4fce-9e0b-afafc99c08eb" />
              <msh:textField property="policyOmcForm.polNumber" label="Номер" guid="9232d341-2b21-4a78-bd93-2db01342cbb2" />
            </msh:row>
            <msh:row guid="c1114cff-7ab7-4627-9b65-c79f884c0897">
              <msh:autoComplete vocName="mainLpu" property="policyOmcForm.attachedLpu" label="Прикрепленное ЛПУ" horizontalFill="true" fieldColSpan="3" guid="77df784c-ee7b-4d47-aac7-98cb91baf1fe" />
            </msh:row>
            <msh:row guid="01004820-c8fa-41de-90e6-a1da810b9f38">
              <msh:autoComplete vocName="vocInsuranceCompany" property="policyOmcForm.company" label="Страховая компания" size="50" horizontalFill="true" fieldColSpan="4" guid="54eaf523-8782-4d2c-bb8d-9b9f4c207907" />
            </msh:row>
            <msh:row guid="062ed9af-9256-451c-b02d-193b4b52e03c">
              <msh:autoComplete vocName="vocOrg" property="policyOmcForm.org" label="Предприятие" horizontalFill="true" fieldColSpan="3" guid="47349ef8-c7b9-49fd-b0bb-3bd1cb8d44cd" />
            </msh:row>
            <msh:row guid="c5d91fba-4ccb-43ed-b4b4-bc63a90ff675">
              <msh:textField property="policyOmcForm.actualDateFrom" label="Дата действия с" guid="829d1acb-2214-4640-bff7-23046b79ac44" />
              <msh:textField property="policyOmcForm.actualDateTo" label="по" guid="536274ee-2084-4c4e-bcd8-fb21df680a0f" />
            </msh:row>

          </msh:panel>
          <msh:ifInRole roles="/Policy/Mis/Patient/AttachedByDepartment/Create" guid="308cce42-ff6f-43a0-a8ae-6e445dfe187a">
          <msh:panel colsWidth="15%, 5%, 5%,-" styleId="tableNewAttachedByDepartment">
          <msh:separator colSpan="4" label="Добавление нового прикрепления" guid="d4871cf3-d393-47e3-9d7b-9a5625b0010" />
            	<msh:row>
            		<msh:autoComplete property="attachedForm.attachedType" label="Тип прикрепления" fieldColSpan="3" horizontalFill="true" vocName="vocAttachedType"/>
            	</msh:row>
				<msh:row styleId='rowLpu'>
		            <msh:autoComplete fieldColSpan="3" property="attachedForm.lpu" label="ЛПУ" horizontalFill="true"
		                              vocName="lpu"/>
		        </msh:row>
		        <msh:row styleId='rowLpuArea'>
		            <msh:autoComplete  fieldColSpan="3" property="attachedForm.area"  label="Участок" horizontalFill="true"
		                              parentAutocomplete="attachedForm_lpu" vocName="lpuAreaWithParent"/>
		        </msh:row>
		        <msh:row>
		        	<msh:textField property="attachedForm.dateFrom"  label="Дата прикрепления"/>
		        	<msh:textField property="attachedForm.dateTo"  label="Дата открепления"/>
		        </msh:row>
            </msh:panel>
            </msh:ifInRole>
             </msh:ifFormTypeIsNotView>
	        <msh:ifFormTypeIsNotView formName="mis_patientForm">
        <msh:row>
	        	<br/>
				<msh:submitCancelButtonsRow colSpan="6" guid="457b3fcb-3395-4bd8-881b-b0b167f01b95" />
		</msh:row>
			</msh:ifFormTypeIsNotView>

      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsNotView formName="mis_patientForm" guid="9cb680e9-523e-4691-9059-a241a709bb06">
      <div style="height: 500px" ></div>
    </msh:ifFormTypeIsNotView>
    <msh:ifFormTypeIsView formName="mis_patientForm" guid="c1b89933-a744-46a8-ba32-014ac1b4fcb4">
          <table>
          <tr valign="top"></tr></table>
      <msh:ifInRole roles="/Policy/Mis/Person/Privilege/View">
      <!--  Льготы-->
          <msh:section title="Льготы" createUrl="entityParentPrepareCreate-mis_privilege.do?id=${param.id}">
          <ecom:webQuery name="privileges" nativeSql="
      select p.id,p.begindate,p.enddate, vpc.name,p.serialdoc,p.numberdoc from privilege p
    left join vocprivilegecategory vpc on vpc.id = p.category_id
    where p.person_id = ${param.id} and isdelete is null"/>
        <msh:tableNotEmpty name="privileges" >
            <msh:table  name="privileges" action="entityParentView-mis_privilege.do" idField="1" >
              <msh:tableColumn columnName="#" property="sn" />
              <msh:tableColumn columnName="Дата включения" property="2" />
              <msh:tableColumn columnName="Дата исключения" property="3" />
              <msh:tableColumn columnName="Категоря льготника" property="4" />
              <msh:tableColumn columnName="Серия документа" property="5" />
              <msh:tableColumn columnName="Номер документа" property="6" />
            </msh:table>
        </msh:tableNotEmpty>
          </msh:section>
      </msh:ifInRole>
        <!--  Инвалидность  -->
    	<msh:ifInRole roles="/Policy/Mis/Patient/Invalidity/View" >
    		<msh:section title="Инвалидность" createUrl="entityParentPrepareCreate-mis_invalidity.do?id=${param.id}" createRoles="/Policy/Mis/Patient/Invalidity/Create">
		    		<ecom:webQuery nativeSql="select i.id, i.firstDiscloseDate,i.dateFrom,i.lastRevisionDate,i.dateTo,vi.name as viname,mkb.code as mkbcode,i.childhoodInvalid,i.greatePatrioticWarInvalid,i.isWorking,i.nextRevisionDate,i.withoutExam,i.incapable from invalidity i left join VocInvalidity vi on vi.id=i.group_id left join vocidc10 mkb on mkb.id=i.idc10_id where i.patient_id=${param.id}" name="invalidities"/>
		    		<msh:table viewUrl="entityShortView-mis_invalidity.do" editUrl="entityParentEdit-mis_invalidity.do"  name="invalidities" action="entityParentView-mis_invalidity.do" idField="1">
		    			<msh:tableColumn property="sn" columnName="#"/>
		    			<msh:tableColumn property="6" columnName="Группа инвалидности"/>
		    			<msh:tableColumn property="3" columnName="Дата освид."/>
		    			<msh:tableColumn property="4" columnName="Дата посл. пересмотра"/>
		    			<msh:tableColumn property="11" columnName="Дата след. пересмотра"/>
		    			<msh:tableColumn property="12" columnName="Без преосвид."/>
		    			<msh:tableColumn property="5" columnName="Дата снятия"/>
		    			<msh:tableColumn property="7" columnName="МКБ10"/>
		    			<msh:tableColumn property="9" columnName="Инвалид ВОВ"/>
		    			<msh:tableColumn property="13" columnName="Недеесп."/>
		    		</msh:table>
    		</msh:section>
    	</msh:ifInRole>
    </msh:ifFormTypeIsView>
    <!-- Вемодственное прикрепление -->
    <msh:ifFormTypeIsView formName="mis_patientForm" guid="f0397eb1-40c1-48ba-a497-8bac20657c4d">
      <msh:ifInRole roles="/Policy/Mis/Patient/AttachedByDepartment/View" guid="ac45563d-aed0-4721-9247-0f07b0b40cf8">
        <ecom:webQuery name="attbydep"
        nativeSql="select l.id
        ,vat.name as vatname
        ,ml.name as mlname,la.number as areaname

        ,l.dateFrom as ldateFrom,l.dateTo as ldateTo
        from lpuAttachedByDepartment l
        left join LpuArea la on la.id=l.area_id
        left join MisLpu ml on ml.id=l.lpu_id
        left join VocAttachedType vat on vat.id=l.attachedType_id
        where l.patient_id=${param.id}"/>
          <msh:section  title="Специальное прикрепление" createUrl="entityParentPrepareCreate-mis_lpuAttachedByDepartment.do?id=${param.id}" createRoles="/Policy/Mis/Patient/AttachedByDepartment/Create">
            <msh:table name="attbydep" action="entityParentView-mis_lpuAttachedByDepartment.do" idField="1">
              <msh:tableColumn property="2" columnName="Тип прикрепления"/>
              <msh:tableColumn property="3" columnName="ЛПУ"/>
              <msh:tableColumn property="4" columnName="Участок"/>
              <msh:tableColumn property="5" columnName="Дата прикрепления"/>
              <msh:tableColumn property="6" columnName="Дата открепления"/>
            </msh:table>
          </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
    <!-- ПОЛИСЫ -->
    <msh:ifFormTypeIsView formName="mis_patientForm" guid="432c717b-ce32-4a1f-9184-f50dee6f43ea">
      <msh:ifInRole roles="/Policy/Mis/MedPolicy/View" guid="466939a9-887e-4694-95b1-d9d9f9f0b77f">
 <%--        <ecom:parentEntityListAll formName="mis_medPolicyForm" attribute="policies" guid="12ed9815-1a16-441a-8d89-36bb94761f9b" />--%>
        <ecom:webQuery nativeSql="select mp.id
        , case when (mp.DTYPE='MedPolicyOmc') then 'ОМС' when (mp.DTYPE='MedPolicyDmcForeign') then 'ДМС иногороднего' when (mp.DTYPE='MedPolicyDmc') then 'ДМС' else 'ОМС иногороднего' end
        , ri.name as riname,mp.polnumber,mp.series
        ,mp.actualDateFrom,mp.actualDateTo ,mp.commonNumber,vmo.name as vmoname
        from MedPolicy as mp
         left join reg_ic as ri on ri.id=mp.company_id
         left join vocmedpolicyomc vmo on vmo.id=mp.type_id
         where mp.patient_id=${param.id} and mp.actualDateFrom <=CURRENT_DATE and (mp.actualDateTo is null or mp.actualDateTo >=CURRENT_DATE)"  name="policies"  />
        <table>
          <tr valign="top"><td style="padding-right: 8px">
          <msh:section guid="746b6d8a-b92f-4bd0-9899-d32855f3aa95">
          <msh:sectionTitle>
          АКТУАЛЬНЫЕ ПОЛИСЫ.
          <msh:link action="javascript:void(0)" onclick="getDefinition('entityParentShortList-mis_medPolicy.do?id=${param.id}', event); return false ;"   >Просмотреть все полисы</msh:link>
          Добавить полис:
          <msh:link action="entityParentPrepareCreate-mis_medPolicyOmc.do?id=${param.id}"  roles="/Policy/Mis/MedPolicy/Omc/Create">OMC</msh:link>
          <msh:link action="entityParentPrepareCreate-mis_medPolicyOmcForeign.do?id=${param.id}"  roles="/Policy/Mis/MedPolicy/OmcForeign/Create">OMC иногороднего</msh:link>
          <msh:link action="js-mis_medPolicyDmc-listExist.do?id=${param.id}"  roles="/Policy/Mis/MedPolicy/Dmc/Create">ДMC</msh:link>
          <msh:link action="entityParentPrepareCreate-mis_medPolicyDmcForeign.do?id=${param.id}"  roles="/Policy/Mis/MedPolicy/DmcForeign/Create">ДMC иногороднего</msh:link>

          </msh:sectionTitle>
          <msh:sectionContent>
            <msh:table editUrl="entitySubclassEdit-mis_medPolicy.do" name="policies" hideTitle="false" action="entitySubclassView-mis_medPolicy.do" idField="1" guid="86ef8e69-6d58-4e49-961a-f2f463e02f80">
              <msh:tableColumn property="sn" columnName="#" />
              <msh:tableColumn property="2" columnName="Тип" />
              <msh:tableColumn property="9" columnName="Тип полиса" />
              <msh:tableColumn property="3" columnName="Страховая компания" />
              <msh:tableColumn property="8" columnName="Единый номер" />
              <msh:tableColumn property="5" columnName="Серия"/>
              <msh:tableColumn property="4" columnName="Номер"/>
              <msh:tableColumn property="6" columnName="Дата начала" />
              <msh:tableColumn property="7" columnName="Дата окончания" />
            </msh:table>

<%--             <msh:table name="policies" hideTitle="true" action="entitySubclassView-mis_medPolicy.do" idField="id" guid="86ef8e69-6d58-4e49-961a-f2f463e02f80">
              <msh:tableColumn property="text" guid="1a71dc2e-96a6-480d-8dd1-f944bee0344e" />
            </msh:table> --%>
          </msh:sectionContent>
          </msh:section>
          </td><td style="padding-right: 8px">
          <ecom:webQuery name="nat_person" nativeSql="select cp.id,cp.patient_id from ContractPerson cp where cp.patient_id='${param.id}' and cp.dtype='NaturalPerson'"/>
          <msh:tableEmpty name="nat_person">
	          <msh:section title="Физ.обсл.лицо" createRoles="/Policy/Mis/Contract/ContractPerson/NaturalPerson/Create" createUrl="entitySaveGoView-contract_naturalPerson.do?saveType=1&patient=${param.id}"/>
          </msh:tableEmpty>
          <msh:tableNotEmpty name="nat_person">
          <msh:section title="Физ.обсл.лицо">
          	<msh:table name="nat_person" action="entityParentView-contract_naturalPerson.do" idField="1">
          		<msh:tableColumn property="1" columnName="ИД"/>
          	</msh:table>
          </msh:section>
          </msh:tableNotEmpty>
          </td></tr></table>
          <table>
          <tr valign="top"><td style="padding-right: 8px">

          <msh:ifInRole roles="/Policy/Mis/Patient/Kinsman">
          	<msh:section>
          		<msh:sectionTitle>Родственники / представители (для иногородних) <a href='entityParentPrepareCreate-mis_kinsman.do?id=${param.id}'>Добавить</a></msh:sectionTitle>
          		<msh:sectionContent>
          			<%--<ecom:parentEntityListAll attribute="kinsman"  formName="mis_kinsmanForm"/>
          			<msh:table idField="id" name="kinsman" action="entityParentView-mis_kinsman.do">
          				<msh:tableColumn property="kinsmanInfo" columnName="ФИО"/>
          				<msh:tableColumn property="kinsmanRoleInfo" columnName="Статус"/>
          			</msh:table>--%>
          			<ecom:webQuery name="kinsman" nativeSql="select p.id,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'dd.mm.yyyy') ,vk.name as vknam, vk1.name vk1name from Kinsman k
          			left join patient p on p.id=k.kinsman_id
          			left join VocKinsmanRole vk on vk.id=k.KinsmanRole_id
          			left join VocKinsmanRole vk1 on vk.OppositeRole_id = vk1.id
          			where k.person_id=${param.id}"/>
          			<msh:table idField="1" name="kinsman" action="entityView-mis_patient.do">
          				<msh:tableColumn property="2" columnName="ФИО"/>
          				<msh:tableColumn property="3" columnName="Статус"/>
          			</msh:table>
          		</msh:sectionContent>
          		<msh:sectionContent>
          			<ecom:webQuery name="kinsmanHim"
          			nativeSql="select p.id,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'dd.mm.yyyy') ,vk.name as vknam, vk1.name vk1name from Kinsman k
          			left join patient p on p.id=k.person_id
          			left join VocKinsmanRole vk on vk.id=k.KinsmanRole_id
          			left join VocKinsmanRole vk1 on vk.OppositeRole_id = vk1.id
          			where k.kinsman_id=${param.id}"
          			/>
          			<msh:table idField="1" name="kinsmanHim" action="entityView-mis_patient.do">
          				<msh:tableColumn property="2" columnName="ФИО"/>
          				<msh:tableColumn property="3" columnName="Статус пациента"/>
          				<msh:tableColumn property="4" columnName="Статус родственника"/>
          			</msh:table>
          		</msh:sectionContent>
          	</msh:section>
          </msh:ifInRole>
          </td></tr>
        </table>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Document/External/View">
      <msh:section createRoles="/Policy/Mis/MedCase/Document/External/Create"
      createUrl="externalDocument-preView.do?id=${param.id}" title="Внешние документы">
      <ecom:webQuery name="extDocument" nativeSql="
      	select d.id,vedt.name as vedtname,to_char(d.createDate,'dd.mm.yyyy')||coalesce(' '||cast(d.createTime as varchar(5)),''),d.referenceTo from Document d
      	left join VocExternalDocumentType vedt on vedt.id=d.type_id
      	where d.patient_id='${param.id}' AND UPPER(d.dtype)='EXTERNALDOCUMENT'
      "/>
      	<msh:table name="extDocument"
      	viewUrl="entityParentView-doc_externalDocument.do?short=Short"
      	action="entityParentView-doc_externalDocument.do" idField="1">
      		<msh:tableColumn property="2" columnName="Тип документа" />
      		<msh:tableColumn property="3" columnName="Дата и время создания"/>
      		<msh:tableColumn property="4" columnName="Ссылка"/>
      	</msh:table>
      	</msh:section>
      </msh:ifInRole>
      <!-- Открытые СПО -->
      <table>
      <tr valign="top"><td style="padding-right: 8px">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Spo/View" guid="8d094733-57f7-475f-a678-0e3e2b02ce43">
        <msh:section title="Открытые СПО" listUrl="entityParentList-smo_spo.do?id=${param.id}"  guid="91133845-c782-43ce-ba7a-84e75a65f024">
          <ecom:webQuery name="openedSpos" nativeSql="select spo.id, spo.dateStart
, swp.lastname || ' ' ||  swp.firstname || ' ' || swp.middlename as startFunction
, owp.lastname || ' ' ||  owp.firstname || ' ' || owp.middlename as ownerFunction
from MedCase spo
left join WorkFunction swf on swf.id=spo.startFunction_id
left join Worker sw on swf.worker_id = sw.id
left join Patient swp on sw.person_id = swp.id
left join WorkFunction owf on owf.id=spo.ownerFunction_id
left join Worker ow  on ow.id = owf.worker_id
left join Patient owp on ow.person_id           = owp.id
where spo.patient_id='${param.id}'
and UPPER(spo.DTYPE)='POLYCLINICMEDCASE'    and spo.dateFinish is null " />
          <msh:table idField="1" name="openedSpos" action="entityParentView-smo_spo.do" guid="2b5a4a9c-3bf0-4e1a-a1d0-2884bdb3e011" noDataMessage="Нет открытых СПО">
            <msh:tableColumn columnName="№" identificator="false" property="sn" guid="96a6e146-6273-4318-a397-f07c0af06825" />
            <msh:tableColumn columnName="Номер" property="1" guid="7d9ea677-7644-4167-af7e-320593fcb061" />
            <msh:tableColumn columnName="Дата начала" identificator="false" property="2" guid="8c4a6d65-791d-4124-8a6e-196c01b6062d" />
            <msh:tableColumn columnName="Кто начал" identificator="false" property="3" guid="3daeaca1-2998-4186-aca2-b368dd4f3bf2" />
            <msh:tableColumn columnName="Владелец" identificator="false" property="4" guid="d8d88639-e4bb-4690-b9ac-c1a4a3ca7bc3" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      </td>
      <td style="padding-right: 8px">
            <!-- Активные направления -->
      <msh:ifInRole roles="/Policy/Mis/MedCase/Visit/View" guid="31447227-d69b-4477-92bf-57dfcbffb7e0">
        <msh:section createRoles="/Policy/Mis/MedCase/Direction/Create" createUrl="entityParentPrepareCreate-smo_direction.do?id=${param.id}" title="Активные направления" guid="2908c043-8780-4025-8b28-911f0ce35018">
          <ecom:webQuery name="directions" nativeSql="
select smo.id , wcd.calendarDate , wct.timeFrom
, owp.lastname || ' ' || owp.firstname || ' ' || owp.middlename as workerOrder
, vr.name as reasonName
, case when pwf.dtype='PersonalWorkFunction' then pvwf.name||' '||ppw.lastname || ' ' || ppw.firstname || ' ' || ppw.middlename else pwf.groupname end as workerPlan
from MedCase smo
left join WorkCalendarDay wcd on smo.datePlan_id = wcd.id
left join WorkCalendarTime wct on smo.timePlan_id = wct.id
left join WorkFunction owf on owf.id=smo.orderWorkFunction_id
left join Worker ow on owf.worker_id = ow.id
left join Patient owp on ow.person_id = owp.id
left join VocReason vr on smo.visitReason_id = vr.id
left join WorkFunction pwf on smo.workFunctionPlan_id = pwf.id
left join Worker pw on pwf.worker_id = pw.id
left join Patient ppw on pw.person_id = ppw.id
left join VocWorkFunction pvwf on pwf.workFunction_id = pvwf.id
where smo.patient_id='${param.id}' and UPPER(smo.DTYPE)='VISIT' and smo.dateStart is null
and (smo.noActuality is null or smo.noActuality='0')
order by wcd.calendarDate, wct.timeFrom" guid="624771b1-fdf1-449e-b49e-5fcc34e03fb5" />
          <msh:table guid="tableChilds" name="directions" action="entityParentEdit-smo_visit.do" idField="1">
            <msh:tableColumn columnName="№" identificator="false" property="sn" guid="73dd6ce1-81ea-4f13-b141-5d3f604f305d" />
            <msh:tableColumn columnName="Номер" identificator="false" property="1" guid="709291f1-be97-4cd5-87c3-04a112a96639" />
            <msh:tableColumn columnName="Дата" property="2" guid="23eed88f-9ea7-4b8f-a955-20ecf89ca86c" />
            <msh:tableColumn columnName="Время" property="3" guid="a744754f-5212-4807-910f-e4b252aec108" />
            <msh:tableColumn columnName="Кто направил" property="4" guid="bf4cb2b2-eb35-4e8f-b8cb-4ccccb06d5ac" />
            <msh:tableColumn columnName="Цель визита" property="5" guid="470b21ea-45ac-43b3-b592-349baaad13a8" />
            <msh:tableColumn columnName="Куда" identificator="false" property="6" guid="b7ec7b33-46a5-4e0b-8288-52743f5e5b19" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      </td></tr></table>
      <!-- Открытые СЛС -->
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/View" guid="5e0b8545-8dc8-48fd-a0ac-3a9f762f00dc">
	      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
		        <msh:section createUrl="entityParentPrepareCreate-stac_sslAdmissionShort.do?id=${param.id}"
	        createRoles="/Policy/Mis/MedCase/Stac/Ssl/Admission/Create" listUrl="stac_sslList.do?id=${param.id}"
	        title="Открытые СЛС." guid="e3ff39ac-f290-4ef1-9de8-df91bbfc9f3b">
		          <ecom:webQuery name="openedSLSs" nativeSql="select
		          sls.id as slsid, sls.dateStart as slsdatestart, ss.code as sscode,vdh.name as vdhname
		          ,case when sls.emergency='1' then 'Экстренно' else 'Планово' end emerg
		          ,ml.name as mlname
					from MedCase sls
					left join MisLpu ml on ml.id=sls.department_id
					left join StatisticStub ss on sls.statisticStub_id = ss.id
					left join VocDeniedHospitalizating vdh on vdh.id=sls.deniedHospitalizating_id
					where sls.patient_id=${param.id}
						and UPPER(sls.DTYPE)='HOSPITALMEDCASE'
					and sls.dischargeTime is null
					and (sls.dateStart=CURRENT_DATE
					 or sls.deniedHospitalizating_id is null)
					"  />
	          <msh:table idField="1" name="openedSLSs" action="entityParentView-stac_sslAdmission.do" noDataMessage="Нет открытых СЛС" guid="d44ef5a2-f5a8-426a-ba79-5812701542bb">
		            <msh:tableColumn columnName="Дата пост." property="2"/>
		            <msh:tableColumn columnName="Стат.карта" property="3"/>
		            <msh:tableColumn columnName="Тип" property="5"/>
		            <msh:tableColumn columnName="Направлен в отделение" property="6"/>
		            <msh:tableColumn columnName="Отказ от госпитализации" property="4"/>
	          </msh:table>
	        </msh:section>

	      </msh:ifInRole>
	      <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
		        <msh:section createUrl="entityParentPrepareCreate-stac_sslAdmission.do?id=${param.id}"
		        createRoles="/Policy/Mis/MedCase/Stac/Ssl/Admission/Create" listUrl="stac_sslList.do?id=${param.id}"
		        title="Открытые СЛС." guid="e3ff39ac-f290-4ef1-9de8-df91bbfc9f3b">
		          <ecom:webQuery name="openedSLSs" nativeSql="select
		          sls.id as slsid, sls.dateStart as slsdatestart, ss.code as sscode,vdh.name as vdhname
		          ,case when sls.emergency='1' then 'Экстренно' else 'Планово' end emerg
		          ,ml.name as mlname,(select list(distinct ml1.name) from MedCase slo
					left join MisLpu ml1 on ml1.id=slo.department_id where slo.parent_id=sls.id and UPPER(slo.dtype)='DEPARTMENTMEDCASE' and slo.transferDate is null ) as ml1name
					from MedCase sls
					left join MisLpu ml on ml.id=sls.department_id
					left join StatisticStub ss on sls.statisticStub_id = ss.id
					left join VocDeniedHospitalizating vdh on vdh.id=sls.deniedHospitalizating_id

					where sls.patient_id=${param.id}
						and UPPER(sls.DTYPE)='HOSPITALMEDCASE'
					and  sls.dischargeTime is null
					and (sls.dateStart=CURRENT_DATE
					 or sls.deniedHospitalizating_id is null )

					"  />
		          <msh:table idField="1" name="openedSLSs"
		          viewUrl="entityShortView-stac_ssl.do"
		           action="entityParentView-stac_sslAdmission.do" noDataMessage="Нет открытых СЛС">
		            <msh:tableColumn columnName="Дата пост." property="2"/>
		            <msh:tableColumn columnName="Стат.карта" property="3"/>
		            <msh:tableColumn columnName="Тип" property="5"/>
		            <msh:tableColumn columnName="Отделение поступления" property="6"/>
		            <msh:tableColumn columnName="Отделение текущее" property="7"/>
		            <msh:tableColumn columnName="Отказ от госпитализации" property="4"/>

		          </msh:table>
		        </msh:section>
	      </msh:ifNotInRole>
      </msh:ifInRole>
       <tags:mis_userDocumentList type="PATIENT" name="Pat" m="printInfo" s="PatientPrintService"/>
    </msh:ifFormTypeIsView>


    <tags:identityPatient name="identityPatient" />
    <tags:addressTag nextField="rayonName" fieldRayon="rayon"/>
    <tags:addressNewTag form="mis_patientForm" name="realAddress" zipcode="realZipcode" flatNumber="realFlatNumber" houseNumber="realHouseNumber" houseBuilding="realHouseBuilding" addressField="realAddressField" />
    <tags:addressNonresident form="mis_patientForm" name="nonresidentAddress" flatNumber="apartmentNonresident"
		houseNumber="houseNonresident" houseBuilding="buildingHousesNonresident" addressField="nonresidentAddressField"
		territory="territoryRegistrationNonresident" region="regionRegistrationNonresident"
		typeSettlement="typeSettlementNonresident" settlement="settlementNonresident"
		typeStreet="typeStreetNonresident" street="streetNonresident" zipcode="nonresidentZipcode"/>
    <tags:mis_double name='Patient' title='Существующие пациенты в базе:' rolesBan="/Policy/Mis/Patient/BanDoubleCreate" cmdAdd="document.forms[0].submitButton.disabled = false "/>
    <tags:mis_findPatientByFond name='Patient' patientField="id"/>
    <tags:mis_showPatientCheckByFondHistory name='Patient' patientField="id"/>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="mis_patientForm" insideJavascript="false" guid="998aa692-c1d3-4d79-bc37-cfa204fa846c">
      <msh:sideMenu title="Персона" guid="e1d0c54a-0817-479d-9997-c367ab09f3fd">
        <msh:sideLink key="ALT+1" params="" action="/mis_patients" name="Поиск" guid="31e83931-c7ab-4040-8f46-3306ac07cb26" />
        <msh:ifFormTypeIsView formName="mis_patientForm" guid="f9dbd33e-9573-4fb0-b011-5434eb7c9424">
          <msh:sideLink roles="/Policy/Mis/Patient/Edit" key="ALT+2" params="id" action="/entityEdit-mis_patient" name="Изменить" guid="49efba32-2741-4a9c-9b80-6722e5fb4e4a" />
        </msh:ifFormTypeIsView>
        <msh:ifFormTypeAreViewOrEdit formName="mis_patientForm" guid="6c8ddaec-6990-410d-8e58-1780385ef2d3">
          <msh:sideLink roles="/Policy/Mis/Patient/Delete" key="ALT+DEL" params="id" action="/entityDelete-mis_patient" name="Удалить" confirm="Удалить персону?" guid="3322c218-9d9b-4996-9ba9-7d5bef9d0b00" />
          <msh:sideLink roles="/Policy/Mis/Patient/View" styleId="viewShort" action="/javascript:getDefinition('js-mis_patient-viewChange.do?id=${param.id}&short=Short')" name="Изменения ПД" title="Изменения персональных данных" />
        </msh:ifFormTypeAreViewOrEdit>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
    <msh:ifFormTypeIsCreate formName="mis_patientForm">
    	<msh:sideMenu title="База фонда">
    		<msh:sideLink name="Добавить данные из базы фонда по ФИО+ДР" action="/javascript:checkPatientByFioDr('.do')"
    		 roles="/Policy/Mis/Patient/CheckByFond"/>
    		<msh:sideLink name="Добавить данные из базы фонда по СНИЛСу" action="/javascript:checkPatientBySnils('.do')"
    		 roles="/Policy/Mis/Patient/CheckByFond"/>
    		<msh:sideLink name="Добавить данные из базы фонда по документам" action="/javascript:checkPatientByDocument('.do')"
    		 roles="/Policy/Mis/Patient/CheckByFond"/>
    		<msh:sideLink name="Добавить данные из базы фонда по RZ" action="/javascript:checkPatientByCommonNumber('.do')"
    		 roles="/Policy/Mis/Patient/CheckByFond"/>

    	</msh:sideMenu>
    </msh:ifFormTypeIsCreate>
    <msh:ifFormTypeIsView formName="mis_patientForm" guid="82ccfbf3-9c28-4416-ba41-529f2cea7691">
      <msh:sideMenu title="Администрирование">
      	<tags:mis_moveDoublePatient name="Data" title="Перенести данные пациента" roles="/Policy/Mis/Patient/MoveDoublePatient"/>
      	<msh:sideLink action="/js-work_personalWorkFunction-listByPerson" params="id" roles="/Policy/Mis/Worker/WorkFunction/View"
      		name="Рабочие функции" title="Просмотреть рабочие функции по персоне"
      	/>
      	<msh:sideLink action=".javascript:updateColorPatient(${param.id})" roles="/Policy/Mis/Patient/EditColorType"
      		name="Изменить цвет" title="Изменить цвет "
      	/>
      </msh:sideMenu>
<msh:ifInRole roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet">
        <msh:sideMenu title="Лист наблюдения">
            <tags:observSheet name="observSheet"/>
            <tags:vocObservRes name="vocObservRes"/>
            <msh:sideLink action="/javascript:openObservSheet(${param.id})" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet"
                          name="Открыть ЛН" title="Открыть ЛН"
            />
            <msh:sideLink action="/javascript:showobservSheet(${param.id})" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet"
                          name="Просмотреть ЛН" title="Просмотреть все ЛН"
            />
            <msh:sideLink action="/javascript:showvocObservRes(${param.id},'/riams/entityView-mis_patient.do?id=${param.id}')" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet"
                          name="Закрыть ЛН" title="Закрыть текущий ЛН"
            />
            <msh:sideLink action="/javascript:everydayProtocol(${param.id})" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet"
                          name="Протокол ежесуточного наблюдения" title="Протокол ежесуточного наблюдения"
            />
            <msh:sideLink action="/javascript:consultProtocol(${param.id})" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet"
                          name="Протокол консультации" title="Протокол консультации"
            />
        </msh:sideMenu>
</msh:ifInRole>
      <msh:sideMenu title="Добавить" guid="fdcda21a-c1c6-4e0e-a74e-1bf843a8c1c8">
        <msh:sideLink roles="/Policy/Mis/MedPolicy/Omc/Create" key="CTRL+1" params="id" action="/entityParentPrepareCreate-mis_medPolicyOmc" name="Полис ОМС" guid="80b6bfa6-fbef-412b-a343-b387a4b1499a" />
        <msh:sideLink roles="/Policy/Mis/MedPolicy/OmcForeign/Create" key="CTRL+2" params="id" action="/entityParentPrepareCreate-mis_medPolicyOmcForeign" name="Полис ОМС иногороднего" guid="d0fd7ed7-245e-4b8-9938-3e3a7d220f12" />
        <msh:sideLink roles="/Policy/Mis/MedPolicy/Dmc/Create" key="CTRL+3" params="id" action="/entityParentPrepareCreate-mis_medPolicyDmc" name="Полис ДМС" guid="d0fd7ed7-245e-4b89-9938-3e3a7d220f12" />
        <msh:sideLink roles="/Policy/Mis/MedPolicy/DmcForeign/Create" key="CTRL+4" params="id" action="/entityParentPrepareCreate-mis_medPolicyDmcForeign" name="Полис ДМС иногороднего" guid="d0fd7ed7-245e-4b9-9938-3e3a7d220f12" />
        <msh:sideLink roles="/Policy/Mis/MedCase/Direction/Create" key="CTRL+5" params="id" action="/entityParentPrepareCreate-smo_direction" name="Направление" guid="5b663421-eef1-46c3-b860-ae0c45ed402f" />
        <msh:sideLink roles="/Policy/Mis/Person/Privilege/Create" key="CTRL+6" params="id" action="/entityParentPrepareCreate-mis_privilege" name="Льготу" guid="68f67345-1b09-4942-81b1-b19180b048f6" />
        <msh:sideLink roles="/Policy/Poly/Medcard/Create" key="CTRL+7" params="id" action="/entityParentPrepareCreate-poly_medcard" name="Медицинскую карту" guid="6a1aaa44-d582-47fd-a70f-6b1a6f8d8fc8" />
        <msh:sideLink roles="/Policy/Mis/Patient/AttachedByDepartment/Create" params="id" action="/entityParentPrepareCreate-mis_lpuAttachedByDepartment" name="Специальное прикрепление" guid="9fc8edbb-dcbb-4134-b33c-f4e9ca033cfc" />
		<msh:sideLink params="id" action="/entityParentPrepareCreate-mis_assessmentCard" name="Карту оценки"  title="Добавить карту оценки" roles="/Policy/Mis/AssessmentCard/Create" />
		<msh:sideLink params="id" action="/entityParentPrepareCreate-mis_invalidity" name="Инвалидность"  title="Добавить инвалидность" roles="/Policy/Mis/Patient/Invalidity/Create" />
		<msh:sideLink params="id" action="/entityParentPrepareCreate-mis_dispensaryCard" name="Карту Д учета"  title="Добавить карту Д учета" roles="/Policy/Mis/Patient/Dispensary/Create" />
        <%--
         --%>
          <msh:sideLink params="id" action="/entityParentPrepareCreate-mis_patientExternalServiceAccount" name="Согласие на передачу данных"  title="Согласие на передачу данных" roles="/Policy/Mis/Patient/PatientExternalServiceAccount/Create"/>
          <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_planOphtHospital" name="Добавить планирование введения ангиогенеза" title="Добавить планирование введения ангиогенеза" guid="2209b5f9-4b4f-4ed5-b825-b66f2ac57e87" roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Opht/Create"/>
      </msh:sideMenu>
      <msh:sideMenu title="Показать все" guid="9f390953-ddd1-426b-bf16-5198c38f449b">

        <msh:sideLink roles="/Policy/E2/View" params="id" action="/javascript:showAllE2EntriesByPatient()" name="Экономические случаи ОМС" title="Показать все экономические случаи по ОМС"/>
        <msh:sideLink key="SHIFT+1" roles="/Policy/Mis/MedCase/Stac/Ssl/View" params="id" action="/stac_sslList" name="СЛС" title="Показать все случаи лечения в стационаре" guid="ca5196e9-9239-47e3-aec4-9a0336e47144" />
        <msh:sideLink params="id" action="/entityParentList-smo_spo" name="СПО" title="Показать все случаи поликлинического обслуживания" guid="dd2ad6a3-5fb2-4586-a24e-1a0f1b796397" roles="/Policy/Mis/MedCase/Spo/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:getDefinition('js-extDisp_card-listByPatient.do?id=${param.id}&short=Short')" name="Доп.дисп." title="Показать все случаи дополнительной диспансеризации" guid="dd2ad6a3-5fb2-4586-a24e-1a0f1b796397" roles="/Policy/Mis/ExtDisp/Card/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:getDefinition('js-extDispCom_card-listByPatient.do?id=${param.id}&short=Short')" name="Медосмотры" title="Показать все медицинские осмотры" guid="dd2ad6a3-5fb2-4586-a24e-1a0f1b796397" roles="/Policy/Mis/ExtDisp/ExtDispCom" />
        <msh:sideLink params="id" action="/js-smo_visit-infoByPatient" name="Информация по визитам" title="Показать информацию посещений по пациенту" guid="dd2ad6a3-5fb2-4586-a24e-1a0f1b796397" roles="/Policy/Mis/MedCase/Spo/View" />
        <msh:sideLink params="id" action="/entityParentList-mis_medPolicy" name="Полисы" title="Показать все полисы" roles="/Policy/Mis/MedPolicy/View"/>
<%-- <msh:sideLink params="id" action="/js-dis_case-listByPatient" name="Нетрудоспособности" title="Показать все случаи нетрудоспособности" guid="c06bc0ce-868d-4c9c-b75a-2ff72b205d92" roles="/Policy/Mis/Disability/DisabilityCase/View" /> --%>

        <msh:sideLink styleId="viewShort" action="/javascript:getDefinition('entityParentList-dis_case.do?id=${param.id}&short=Short')" name="Нетрудосп." title="Показать все случаи нетрудоспособности по пациенту" guid="c06bc0ce-868d-4c9c-b75a-2ff72b205d92" roles="/Policy/Mis/Disability/Case/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:getDefinition('entityParentList-psych_suicideMessage.do?id=${param.id}&short=Short')" name="Суициды" title="Показать все сообщения о суициде" roles="/Policy/Mis/Psychiatry/CareCard/SuicideMessage/View" />

        <msh:sideLink params="id" action="/js-smo_diagnosis-infoByPatient" name="Диагнозы" title="Показать все диагнозы" guid="68b36632-8d07-4a87-b469-6695694b2bab" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        <msh:sideLink params="id" action="/entityParentList-vac_vaccinationByPatient" name="Вакцинации" title="Показать все вакцинации" guid="12d6632c-5a14-42d7-9c7e-fd5852d9a789" roles="/Policy/Mis/Vaccination/View" />
        <mis:sideLinkForWoman classByObject="Patient" id="${param.id}" params="id" action="/entityParentList-preg_pregnancy" name="Беременности" title="Показать все беременности" roles="/Policy/Mis/Pregnancy/View"/>
        <msh:sideLink params="id" action="/entityParentList-mis_kinsman" name="Родственников (представителей)"  title="Показать всех родственников (представителей)" roles="/Policy/Mis/Patient/Kinsman/View"/>
        <msh:sideLink params="id" action="/js-mis_assessmentCard-listByPatient" name="Карты оценки"  title="Показать все карты оценки" roles="/Policy/Mis/AssessmentCard/View"/>
        <msh:sideLink params="id" action="/entityParentList-mis_patientExternalServiceAccount" name="Согласие на передачу данных"  title="Согласие на передачу данных" roles="/Policy/Mis/Patient/PatientExternalServiceAccount/View"/>
        <msh:sideLink params="id" action="/entityParentList-mis_dispensaryCard" name="Карты Д учета"  title="Карты Д учета" roles="/Policy/Mis/Patient/Dispensary/View"/>
        <msh:sideLink roles="/Policy/Mis/ColorIdentityEdit/PatientSet" name="Браслеты" styleId="viewShort" action="/javascript:showidentityPatient(${param.id},0)"  title='Браслеты' />
          <msh:sideLink action="/javascript:getDefinition('js-smo_planHospitalByVisit-allByPatient.do?short=Short&patient=${param.id}')" name="Предварит. госпитализации"  title="Пред. госпитализации" roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/View"/>
          <msh:sideLink styleId="viewShort" action="/javascript:getDefinition('js-smo_planHospitalByVisit-allByPatientOpht.do?short=Short&patient=${param.id}')" name='Введение ингибиторов ангиогенеза' title="Просмотр направлений на введение ингибиторов ангиогенеза" params="" roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Opht/View" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать" guid="157c0645-4549-461e-acf7-34072c393951">
        <msh:sideLink params="id" action="/print-ambcard.do?s=PatientPrintService&amp;m=printInfo"
        name="Амбул.карты" title="Печать амбул.карты"
        guid="783bad66-e5a6-44a8-9046-23921d00121e" roles="/Policy/Mis/Patient/View" />
        <msh:sideLink key="SHIFT+8" params="id" action="/print-pat.do?s=PatientPrintService&amp;m=printInfo" name="Сведений о пациенте" title="Печать сведений о пациенте" guid="783bad66-e5a6-44a8-9046-23921d00121e" roles="/Policy/Mis/Patient/View" />
        <msh:sideLink key="SHIFT+9" action="/print-agreement.do?s=PatientPrintService&amp;m=printAgreement" params="id"
        	name="Информ. согласия на мед. вмешательство" title="Печать информационного согласия"
        	roles="/Policy/Mis/MedCase/Stac/Ssl/View"/>
        <msh:sideLink action="/javascript:showPatClaimStart()"
        	name="Документов по пациенту" title="Печать документов по пациенту" roles="/Policy/Mis/Patient/View"/>

      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
      <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>

    <msh:ifInRole roles="/Policy/Mis/Patient/EditColorType">
    <script type="text/javascript">
	function updateColorPatient() {
		PatientService.editColorType($('id').value,$('colorType').value=="true"?"1":"0", {
            callback: function(aResult) {
            	 window.document.location.reload();
            }
        	});

	}
    </script>
    </msh:ifInRole>

      <script type="text/javascript">
          function banEditPerson(){
              document.getElementById("lastname").setAttribute("readonly", "readonly");
              document.getElementById("birthday").setAttribute("readonly", "readonly");
              document.getElementById("firstname").setAttribute("readonly", "readonly");
              document.getElementById("middlename").setAttribute("readonly", "readonly");
              document.getElementById("lastname").setAttribute("class", "viewOnly");
              document.getElementById("birthday").setAttribute("class", "viewOnly");
              document.getElementById("firstname").setAttribute("class", "viewOnly");
              document.getElementById("middlename").setAttribute("class", "viewOnly");
          }
      </script>

      <msh:ifFormTypeAreViewOrEdit formName="mis_patientForm" guid="6c8ddaec-6990-410d-8e58-1780385ef2d3">
          <msh:ifInRole roles="/Policy/Mis/Patient/BanEdit">
              <script type="text/javascript">
                  banEditPerson();
              </script>
          </msh:ifInRole>
      </msh:ifFormTypeAreViewOrEdit>

      <msh:ifFormTypeAreViewOrEdit formName="mis_patientForm" guid="6c8ddaec-6990-410d-8e58-1780385ef2d3">
      <msh:ifInRole roles="/Policy/Mis/Patient/BanEditIfPaid">
      <script type="text/javascript">
          iamdummy();
      function iamdummy() {
      PatientService.getPaid($('id').value, {
      callback: function(aResult) {
          if(aResult>0){
              banEditPerson();
          }
      }});
      }
      </script>
      </msh:ifInRole>
    </msh:ifFormTypeAreViewOrEdit>
  <script type="text/javascript">
      function checkPassportSeriesAndNumber(){

          var ret=true;
          const SpaceIntoDigits = /\d\d\s\d\d/g;
          const Digits1 = /\d\d\d\d/g;
          var passportSeries = $('passportSeries').value;
          var passportNumber = $('passportNumber').value;

          if((passportSeries.length == 0) && (passportNumber.length==0)){
              return true;
          }else if ($('passportType').value=='${passportRF}') {
              if(passportSeries.length == 5 && passportSeries.match(SpaceIntoDigits)){
                  ret=true;
              }else if(passportSeries.length == 4 && passportSeries.match(Digits1)){
                  var text =passportSeries[0]+passportSeries[1]+" "+passportSeries[2]+passportSeries[3];
                  $('passportSeries').value = text;
                  ret=true;
              }else{
                  alert('Неверный формат серии паспорта! \n Должно быть: "ЧЧ ЧЧ"!');
                  ret=false;
              }

              if(passportNumber.length == 6 && passportNumber.match(/[0-9]/g)){
              }else{
                  alert('Неверный формат номера паспорта! \n Должно быть: "ЧЧЧЧЧЧ"!');
                  ret=false;
              }
          }
          return ret;
      }
    </script>

  <msh:ifInRole roles="/Policy/Mis/Patient/CheckByFond">
    <script type="text/javascript">
    function checkIsAttachedOrDead() {
    	PatientService.checkPatientAttachedOrDead($('id').value, {
    		callback: function (aResult) {
    			if (aResult){
    			    aResult = JSON.parse(aResult);
    			if (aResult.statusCode=='1') {
    				$('syncRow').style.backgroundColor="green";
    				$('syncRow').style.color="white";
    			} else if (aResult.statusCode=='0'){
    				$('syncRow').style.backgroundColor="yellow";
    			} else if (aResult.statusCode=='2'){
    				$('syncRow').style.backgroundColor="red";
    			}
    			$('syncRow').innerHTML="<p>"+aResult.statusName+"</p>";
    			}
    		}
    	});
    }
    function checkPatientByCommonNumber(a) {
    		showPatientFindPatientByFond("Подождите идет поиск...") ;
    		PatientButtonView(0) ;
    		PatientService.checkPatientByCommonNumber($('id').value,$('commonNumber').value, {
                   callback: function(aResult) {
                	  cancelPatientFindPatientByFond() ;
                      if (aResult) {
                    	  showPatientFindPatientByFond(aResult) ;
                       }
                   }, errorHandler:function(message) {
                	   cancelPatientFindPatientByFond() ;
                	   showPatientFindPatientByFond(message,1) ;
                   }
	        	});
		}
    	function checkPatientBySnils(a) {
    		showPatientFindPatientByFond("Подождите идет поиск...") ;
    		PatientButtonView(0) ;
    		PatientService.checkPatientBySnils($('id').value,$('snils').value, {
                   callback: function(aResult) {
                	  cancelPatientFindPatientByFond() ;
                      if (aResult) {
                    	  showPatientFindPatientByFond(aResult) ;
                       }
                   }, errorHandler:function(message) {
                	   cancelPatientFindPatientByFond() ;
                	   showPatientFindPatientByFond(message,1) ;
                   }
	        	});
		}
    	function checkPatientByFioDr(a) {

    		showPatientFindPatientByFond("Подождите идет поиск...") ;
    		PatientButtonView(0) ;
    		var dr = $('birthday').value.split('.') ;
    		PatientService.checkPatientByFioDr($('id').value,$('lastname').value
    				,$('firstname').value,$('middlename').value,dr[2]+'-'+dr[1]+'-'+dr[0]
    				,{
                   callback: function(aResult) {
                	  cancelPatientFindPatientByFond() ;
                      if (aResult) {
                    	  showPatientFindPatientByFond(aResult) ;
				    	  //alert(aResult) ;
                       }
                   }, errorHandler:function(message) {
                	   cancelPatientFindPatientByFond() ;
                	   showPatientFindPatientByFond(message,1) ;
                   }
	        	});
		}
    	function checkPatientByDocument(a) {
    		showPatientFindPatientByFond("Подождите идет поиск...") ;
    		PatientButtonView(0) ;
    		var dr = $('birthday').value.split('.') ;
    		PatientService.checkPatientByDocument($('id').value,$('passportType').value
    				,$('passportSeries').value
    				,$('passportNumber').value
    				,{
                   callback: function(aResult) {
                	   cancelPatientFindPatientByFond() ;
                      if (aResult) {
                    	  showPatientFindPatientByFond(aResult) ;
				    	  //alert(aResult) ;
                       }
                   }, errorHandler:function(message) {
                	   cancelPatientFindPatientByFond() ;
                	   showPatientFindPatientByFond(message,1) ;
                   }
	        	});
		}
    </script>
  </msh:ifInRole>
  <msh:ifFormTypeIsView formName="mis_patientForm">
      <msh:ifInRole roles="/Policy/Mis/Patient/PatientList/PatientListView">
      	<script type="text/javascript">
      	function checkPatientInList() {
      		PatientService.getIsPatientInList($('id').value, {
      			callback: function(a) {
      				if (a!=null&&a!=''){
      				$('patientInfoDiv').innerHTML=a;
      				}
      			}
      		});
      	}
			onload=checkPatientInList();
		</script>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/Patient/CheckByFondAttachment">
      	<script type="text/javascript">
			onload=checkIsAttachedOrDead();
		</script>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet">
          <script type="text/javascript">
              //в завимисости от наличия/отсутствия и статуса листка наблюдения скрывает/показывает кнопки
              function updateObservButtons(status) {
                  //0 - нет никаких, можно открыть
                  //1 - есть открытый: можно просмотреть, закрыть, добавить всё
                  //2 - есть закрытый: можно просмотреть, открыть
                  var аMas = document.getElementsByTagName('a');
                  for (var i=0; i<аMas.length; i++) {
                      if (аMas[i].innerHTML=='Открыть ЛН') {
                          if (status==0 || status==2)
                              аMas[i].parentNode.removeAttribute('hidden');
                          else
                              аMas[i].parentNode.setAttribute('hidden',true);
                      }
                      else if (аMas[i].innerHTML=='Просмотреть ЛН') {
                          if (status==1 || status==2)
                              аMas[i].parentNode.removeAttribute('hidden');
                          else
                              аMas[i].parentNode.setAttribute('hidden',true);
                      }
                      else if (аMas[i].innerHTML=='Закрыть ЛН' || аMas[i].innerHTML=='Протокол ежесуточного наблюдения' || аMas[i].innerHTML=='Протокол консультации') {
                          if (status==1)
                              аMas[i].parentNode.removeAttribute('hidden');
                          else
                              аMas[i].parentNode.setAttribute('hidden',true);
                      }
                  }
              }
              //получает статус листка наблюдения
              function checkObservStatus() {
                  PatientService.getObservationSheetStatus($('id').value, {
                      callback: function(status) {
                          updateObservButtons(status);
                      }
                  });
              }
              checkObservStatus();
              //открыть лист наблюдения
              function openObservSheet(aPatId) {
                  PatientService.openObservSheet(aPatId, {
                      callback: function(res) {
                          if (res=='1') {
                              showToastMessage('Лист наблюдения открыт.',null,true);
                              window.location.reload();
                          }
                          else
                              showToastMessage('Уже есть открытый лист наблюдения, нельзя открыть ещё один!',null,true);
                      }
                  });
              }
          </script>
      </msh:ifInRole>
      </msh:ifFormTypeIsView>
 <msh:ifFormTypeIsCreate formName="mis_patientForm">
  <script type="text/javascript">
  if ('${param.lastname}'!="") {
		var lfm='${param.lastname}' ;
		var lfm1='${param.hiddendata}' ;
		if (lfm1!=null && lfm1!='') {
	  		var lfm1 = subvalue(lfm1,'#','lastname');
	  		var lfm1 = subvalue(lfm1,'#','firstname');
	  		var lfm1 = subvalue(lfm1,'#','middlename');
	  		var lfm1 = subvalue(lfm1,'#','birthday');
	  		var lfm1 = subvalue(lfm1,'#','commonNumber');
	  		var lfm1 = subvalue(lfm1,'#','snils');
	  		var lfm1 = subvalue(lfm1,'#','sex');
	  		if ($('sex').value!='') {
	  		PatientService.getSexByOmccode($('sex').value, {
	            callback: function(aResult) {
	            	aResult=subvalue(aResult,'#','sex');
	            	aResult=subvalue(aResult,'#','sexName');
	            }});
	  		}
	  		var lfm1 = subvalue(lfm1,'#','phone');
	  		var sex="" ;
	  		checkPatientByCommonNumber();


		} else {
	  		var lfm = subvalue(lfm,' ','lastname');
	  		var lfm = subvalue(lfm,' ','firstname');
	  		var lfm = subvalue(lfm,' ','middlename');
		}


	}
	function subvalue(aValue,aDel,aField) {
		//alert ('subValue'+aValue+" : "+aField);
		var ind = aValue.indexOf(aDel) ;
		if (ind==-1) {
			if ($(aField).value=="") $(aField).value=aValue ;
			return "" ;
		} else {
			if ($(aField).value=="") $(aField).value=aValue.substring(0,ind) ;
			if (aField=='sex') alert ($('sex').value);
			return aValue.substring(ind+1) ;

		}

	}
  </script>
  </msh:ifFormTypeIsCreate>
  <msh:ifFormTypeIsNotView formName="mis_patientForm">
    <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
    <script type="text/javascript">// <![CDATA[//

    	var oldaction = document.forms[0].action ;
    	document.forms[0].action = 'javascript:isExistPatient()';
			eventutil.addEventListener($('birthday'),'change',function(){updateAge() ;}) ;
		if ($('passportDivision')) {
			passportDivisionAutocomplete.addOnChangeCallback(function() {
				if ((+$('passportDivision').value)>0) {
					var divName = $('passportDivisionName').value ;
					var ind = divName.indexOf(' ') ;
	            	$('passportCodeDivision').value = divName.substring(0,ind) ;
	            	$('passportWhomIssued').value = divName.substring(ind+1) ;
				}
            }) ;
		}/*
		if ($('passportBirthPlace')) {
			passportBirthPlaceAutocomplete.addOnChangeCallback(function() {
				if ((+$('passportBirthPlace').value)>0) {
	            	$('birthPlace').value = $('passportBirthPlaceName').value ;
				}
            }) ;
		}*/
    	function updateAge() {
    		PatientService.getAge($('birthday').value, {
        		callback: function(aResult) {
       				$('ageReadOnly').value = aResult ;
        		}
        	});
    	}
    	function isExistPatient() {
    		if (!checkPassportSeriesAndNumber()) {
    			document.getElementById('submitButton').disabled=false;
                document.getElementById('submitButton').value='Создать';
    			return;
    		}

            if (($('middlename').value && !isFioCorrent($('middlename'))) || !isFioCorrent($('firstname'))
                || !isFioCorrent($('lastname')) ) {
    		    alert("Неверный формат ФИО!");
                document.getElementById('submitButton').disabled=false;
                document.getElementById('submitButton').value='Создать';
                return;
            }

    		PatientService.getDoubleByFio($('id').value,$('lastname').value, $('firstname').value, $('middlename').value,
				$('snils').value, $('birthday').value, getValue($('passportType')),getValue($('passportNumber')), getValue($('passportSeries')),'entityView-mis_patient.do', {
                   callback: function(aResult) {
                      if (aResult) {
				    		showPatientDouble(aResult) ;
                       } else {
                       		document.forms[0].action = oldaction ;
				    		document.forms[0].submit() ;
                       }
                   }
	        	});

    		}
    		function getValue(aFld) {
                return aFld ? aFld.value : "none";
    		}

    		function isFioCorrent(field){
    	     field = getValue(field);
                if(field=="-" ) return false;
                return field.match(/^[а-яйЙёЁА-Я -]+$/g);
            }

    	</script>

  </msh:ifFormTypeIsNotView>
    <script type="text/javascript">

    	function showRow(aRowId, aCanShow, aField ) {
    		//alert(aRowId) ;
			try {
				//alert( aCanShow ? 'table-row' : 'none') ;
				$(aRowId).style.display = aCanShow ? 'table-row' : 'none' ;
			} catch (e) {
				// for IE
				//alert(aCanShow ? 'block' : 'none') ;
				try{
				$(aRowId).style.display = aCanShow ? 'block' : 'none' ;
				}catch(e) {}
			}
		}
		//$('attachedByDepartment').style.visibility = 'hidden' ;
		//$('attachedByDepartmentLabel').style.visibility = 'hidden' ;
	</script>
    <!-- При просмотре -->
    <msh:ifFormTypeIsView formName="mis_patientForm" guid="b8c4d74b-4db5-433e-982c-e3133e4993ea">
        <script type='text/javascript' src='./dwr/interface/HospitalMedCaseService.js'></script>
      <script type="text/javascript">
          function showAllE2EntriesByPatient() {
              var commonNumber = $('commonNumber').value;
              var href="";
              if (commonNumber && commonNumber.length==16) {
                  href="commonnumber:"+commonNumber;
              } else {
                  href="lastname:"+$('lastname').value+" "+$('firstname').value+" "+$('middlename').value+" "+$('birthday').value;
              }
              window.open("entityList-e2_entry.do?id=0&filter="+href);
          }
		$('buttonShowAddress').style.display = 'none';
    	showRow('tableNewOmcPolicy',false) ;
    	showRow('tableNewAttachedByDepartment',false) ;
    	/*
      		$('buttonShowAddress').style.display = 'none';
        	showRow('rowCreateNewOmcPolicy',false) ;
        	$('attachedByDepartment').style.display = 'none' ;
        	$('attachedByDepartmentLabel').style.display = 'none' ;
        	//alert(!$('attachedByPolicy').checked) ;
        	showRow('rowLpuAreaAddressText', ! $('attachedByPolicy').checked) ;
        	*/

    	function loadBracelets() {
          //вывод браслетов #151
          HospitalMedCaseService.selectIdentityPatient(
              ${param.id},0, {
                  callback: function(res) {
                      if (res!=null && res!='[]') {
                          var aResult = JSON.parse(res);
                          var str='<table style="margin-left:45%"><tr>';
                          for (var i=0; i<aResult.length; i++) {
                              str+='<td><div title="'+aResult[i].vsipnameJust+'" style="background: '+aResult[i].colorCode+';width: 30px;height: 30px;outline: 1px solid gray; border:2px; margin-right: 2px; margin-left: 2px;"></div></td>';
                          }
                          str+="</tr></table>";
                          document.getElementById('identityDiv').innerHTML=str;
                      }
                      else
                          document.getElementById('identityDiv').innerHTML='';
                  }
              }
          );
        }
          loadBracelets();
      </script>
    </msh:ifFormTypeIsView>

    <!-- Редактирование  -->
    <msh:ifFormTypeIsNotView formName="mis_patientForm" guid="0ac15607-1ca6-4aa0-b9f0-ff3b31cb5a46">
        <script type="text/javascript">
            var isTableNewAttachedByDepartment = null ;
        </script>
        <msh:ifInRole roles="/Policy/Mis/Patient/AttachedByDepartment/Create" guid="308cce42-ff6f-43a0-a8ae-6e445dfe187a">
        <script type="text/javascript">
           isTableNewAttachedByDepartment = 1 ;
        </script>
    	</msh:ifInRole>
      <script type="text/javascript">
        showRow('tableNewOmcPolicy',false) ;
    	if (isTableNewAttachedByDepartment) showRow('tableNewAttachedByDepartment',false) ;

      	function onCreateNewAttachment() {
      		var isCreatedNewAttachment = $('createNewAttachment').checked ;
			$('tableNewAttachedByDepartment').style.display = isCreatedNewAttachment ? 'block' : 'none' ;
			try {

				$('attachedForm.attachedTypeName').select() ;
				$('attachedForm.attachedTypeName').focus() ;

			} catch (e) {}

		}
      	function onCheckBoxFocus() {
			try {
				$(this.id+'Label').style.border='1px dotted black' ;
			} catch(e) {}
		}
		function onCheckBoxBlur() {
			try {
				$(this.id+'Label').style.border='none' ;
			} catch(e) {}
		}

		function onCreateNewOmcPolicy() {
			//var attachedByPolicy = $('attachedByPolicy').checked ;

			var isCreateNewOmcPolicy = $('createNewOmcPolicy').checked ;//&& attachedByPolicy;
			//showRow('rowAttachedOmcPolicy', !isCreateNewOmcPolicy) ;
			//if(attachedByPolicy) showRow('rowAttachedOmcPolicy', !isCreateNewOmcPolicy) ;
			$('tableNewOmcPolicy').style.display = isCreateNewOmcPolicy ? 'block' : 'none' ;


				//showRow('rowAttachedOmcPolicy', !isCreateNewOmcPolicy) ;
				try {

					$('policyOmcForm.typeName').select() ;
					$('policyOmcForm.typeName').focus() ;

				} catch (e) {}

		}
		if (isTableNewAttachedByDepartment) {
	        eventutil.addEventListener($('createNewAttachment'), 'click', onCreateNewAttachment) ;
	        eventutil.addEventListener($('createNewAttachment'), 'focus', onCheckBoxFocus) ;
	        eventutil.addEventListener($('createNewAttachment'), 'blur', onCheckBoxBlur) ;
	    }
		eventutil.addEventListener($('createNewOmcPolicy'), 'click', onCreateNewOmcPolicy) ;
        eventutil.addEventListener($('createNewOmcPolicy'), 'focus', onCheckBoxFocus) ;
        eventutil.addEventListener($('createNewOmcPolicy'), 'blur', onCheckBoxBlur) ;

        eventutil.addEnterSupport('addressFlatNumber1', 'buttonSaveAddressOk') ;
		eventutil.addEnterSupport('birthPlace', 'buttonShowAddress') ;
		eventutil.addEnterSupport('foreignRegistrationAddress', 'buttonShowrealAddressAddress') ;

		function checkPatientLpu() {
            var addressId = $('address').value ;
            var houseNumber = $('houseNumber').value ;
            var houseBuilding = $('houseBuilding').value ;
            var flatNumber = $('flatNumber').value ;
            var birthDay = $('birthday').value ;

            var p = $('patientLpuDiv') ;
            p.style.color = "gray";
            p.innerHTML = "Вычисление...";
            setSubmitButtonDisabled(true) ;

            AddressService.findPatientLpu(addressId, houseNumber, houseBuilding, birthDay, flatNumber, {
                callback: function(aString) {
                    p.innerHTML = "Прикрепленное ЛПУ: " + aString;
                    p.style.color = "blue";
                    setSubmitButtonDisabled(false) ;
                },
                errorHandler: function(aMessage) {
                    var s = new String(aMessage) ;
                    s = s.replace("java.lang.IllegalStateException", "");
                    s = s.replace("java.lang.IllegalStateException", "");
                    s = s.replace("java.lang.IllegalArgumentException", "");
                    p.innerHTML = s;
                    p.style.color = "red";
                    setSubmitButtonDisabled(true) ;
                }
            });
			
        }
			eventutil.addEnterSupport('addressFlatNumber1', 'buttonSaveAddressOk') ;
			eventutil.addEnterSupport('birthPlace', 'buttonShowAddress') ;
			eventutil.addEnterSupport('foreignRegistrationAddress', 'buttonShowrealAddressAddress') ;
			
   </script>
    </msh:ifFormTypeIsNotView>

 
    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="mis_patientForm" guid="659a5e0c-3949-4788-a96b-846f3104205b" />
  </tiles:put>
  <tiles:put name="style" type="string">
    <style type="text/css">
      <msh:ifFormTypeIsView formName="mis_patientForm" guid="db1d04a7-d019-47e3-9ce5-3ee343345bce">#addressPar {
	  		font-weight: bold;
	  	}</msh:ifFormTypeIsView>
    </style>
  </tiles:put>
</tiles:insert>
