<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
              <table>
          <tr valign="top">
          <td style="padding-right: 8px">
      <msh:ifInRole roles="/Policy/Poly/Medcard/View">
        <msh:section title="Мед.карты" createUrl="entityParentPrepareCreate-poly_medcard.do?id=${param.id}">
		          <ecom:webQuery name="medcard" 
		        nativeSql="select m.id,m.number
		         from MedCard m
		         where m.person_id='${param.id}'
		         "/>
            <msh:table viewUrl="entityShortView-poly_medcard.do"
			         hideTitle="false" disableKeySupport="false" idField="1" 
			         name="medcard" action="entityParentView-poly_medcard.do" 
			         disabledGoFirst="false" disabledGoLast="false" 
			         >
		            <msh:tableColumn columnName="Номер" identificator="false" 
		            	property="2" />
		          </msh:table>
        </msh:section>
      </msh:ifInRole>
      </td><td>
      <msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:section title="Мед.карты (псих.помощью)" createUrl="entityParentPrepareCreate-psych_careCard.do?id=${param.id}">
		        <ecom:webQuery name="psychCard" 
		        nativeSql="select pcc.id,pcc.cardNumber
							,(select to_char(po.startDate,'dd.mm.yyyy')||' '||vpac.code from PsychiaticObservation po
							left join VocPsychAmbulatoryCare vpac on vpac.id=po.ambulatoryCare_id where po.careCard_id=pcc.id
							having max(po.startDate)=po.startDate)
							,(select mkb.code from Diagnosis d
							left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id
							left join vocidc10 mkb on mkb.id=d.idc10_id
							where d.patient_id=pcc.patient_id and vpd.code='1'
							having max(d.establishDate)=d.establishDate
							)
							from PsychiatricCareCard pcc
							where pcc.patient_id='${param.id}'
		         "
		         />
		        <msh:table viewUrl="entityShortView-psych_careCard.do" hideTitle="false" disableKeySupport="false" idField="1" name="psychCard" action="entityParentView-psych_careCard.do" disabledGoFirst="false" disabledGoLast="false">
		            <msh:tableColumn columnName="Номер карты" identificator="false" property="2" />
		            <msh:tableColumn columnName="Наблюдения" identificator="false" property="3" />
		            <msh:tableColumn columnName="МКБ10" identificator="false" property="4" />
		            
		        </msh:table>
        </msh:section>
      </msh:ifInRole>
      </td>
      <td style="padding-right: 4px">
    	<ecom:webQuery name="lastVisit" nativeSql="select 
    	t.id,t.date,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename
    	from ticket t 
    	left join medcard m on m.id=t.medcard_id
    	left join patient p on p.id=m.person_id
    	left join workfunction wf on wf.id=t.workFunction_id
    	left join vocworkfunction vwf on vwf.id=wf.workFunction_id
    	left join worker w on w.id=wf.worker_id
    	left join patient wp on wp.id=w.person_id
    	where m.person_id=${param.id}
    	order by t.date desc
    	" maxResult="1" />
    <msh:tableNotEmpty name="lastVisit">
     <msh:section title="Последнее посещение по талонам">
    	<msh:table name="lastVisit" action="entityParentView-poly_ticket.do" idField="1">
	    	<msh:tableColumn property="2" columnName="Дата"/>
    		<msh:tableColumn property="3" columnName="Специалист"/>
    	</msh:table>
     </msh:section>
    </msh:tableNotEmpty>
          </td><td style="padding-right: 4px">
    
    	<ecom:webQuery name="lastVisit1" nativeSql="select 
    	m.id,coalesce(m.dateStart,wcd.calendarDate) as dateFrom
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
    	where m.patient_id=${param.id} and m.DTYPE='Visit'
    	order by coalesce(m.dateStart,wcd.calendarDate) desc
    	" maxResult="1" />
    <msh:tableNotEmpty name="lastVisit1">
     <msh:section title="Последнее посещение" viewRoles="/Policy/Mis/MedCase/Direction/View" shortList="js-mis_patient-viewDirection.do?id=${param.id}">
    	<msh:table name="lastVisit1" action="entitySubclassView-mis_medCase.do" idField="1">
	    	<msh:tableColumn property="2" columnName="Дата"/>
    		<msh:tableColumn property="3" columnName="Специалист"/>
    	</msh:table>
     </msh:section>
    </msh:tableNotEmpty>  
    </td>
      </tr>
        </table>
    <msh:form action="entitySaveGoView-mis_patient.do" title="<a href='entityView-mis_patient.do?id=${param.id}'>Персона</a>" defaultField="lastname">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel>
       	<msh:row >
      		<msh:textField property="patientSync" label="Код синх." viewOnlyField="true"/>
			<msh:textField property="editDate" label="Дата редактирования" viewOnlyField="true"/>
      	</msh:row>
      	<msh:row>
      		<msh:autoComplete property="medcardLast" viewAction="entityView-poly_medcard.do" shortViewAction="entityShortView-poly_medcard.do"
      			label="Медкарта" vocName="medcardLast" viewOnlyField="true"/>
      		<msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
      		<msh:ifInRole roles="/Policy/Mis/Psychiatry/CareCard/View">
	      		<msh:autoComplete property="careCard"  viewAction="entityView-psych_careCard.do" shortViewAction="entityShortView-psych_careCard.do"
	      			label="Медкарта (псих.помощью)" vocName="psychiatricCareCard" viewOnlyField="true" 
	      		/>
      		</msh:ifInRole>
      		<msh:ifNotInRole roles="/Policy/Mis/Psychiatry/CareCard/View">
	      		<msh:autoComplete property="careCard"  
	      			label="Медкарта (псих.помощью)" vocName="psychiatricCareCard" viewOnlyField="true" 
	      		/>
      		</msh:ifNotInRole>
      		</msh:ifInRole>
      	</msh:row>
          <msh:row>
            <msh:label  property="lastname" label="Фамилия"  />
            <msh:label property="firstname" label="Имя"  />
          </msh:row>
          <msh:row>
            <msh:label property="middlename" label="Отчество" />
            <msh:autoComplete viewOnlyField="true" property="sex" label="Пол" vocName="vocSex" />
          </msh:row>
          <msh:row>
            <msh:label property="birthday" label="Дата рождения" />
          	<msh:textField property="age" label="Возраст" viewOnlyField="true"  size="20"/>
          </msh:row>
          <msh:ifInRole roles="/Policy/Mis/Patient/Newborn">
	          <msh:row>
	          	<msh:autoComplete property="newborn" label="Новорожденный" fieldColSpan="3" vocName="vocNewBorn" horizontalFill="true" />
	          </msh:row>
          </msh:ifInRole>

        <msh:row>
          <msh:autoComplete property="works" fieldColSpan="3" label="Место работы" viewOnlyField="true" vocName="vocOrg"  />
        </msh:row>
        <msh:row>
          <msh:label property="workPost" label="Должность" size="20"/>
          <msh:label property="snils" label="СНИЛС" size="20"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete property="socialStatus" fieldColSpan="1" label="Социальный статус" horizontalFill="true" vocName="vocSocialStatus" />
          <msh:autoComplete property="additionStatus" fieldColSpan="1" label="Доп. статус" horizontalFill="true" vocName="vocAdditionStatus" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="ethnicity" horizontalFill="true" label="Национальность"  vocName="vocEthnicity" />
          <msh:autoComplete property="nationality" label="Гражданство" vocName="omcOksm" horizontalFill="true"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete property="marriageStatus" label="Семейное положение"  vocName="vocMarriageStatus" />
          <msh:autoComplete property="educationType" label="Вид образования" horizontalFill="true"  vocName="vocEducationType" />
        </msh:row>
        <msh:separator colSpan="4" label="Документ, удостоверяющий личность"/>
        <msh:row>
          <msh:autoComplete property="passportType" label="Тип" vocName="vocIdentityCard" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:label property="passportSeries" label="Серия"/>
          <msh:label property="passportNumber" label="Номер"/>
        </msh:row>
        <msh:row>
          <msh:label property="passportDateIssued" label="Дата выдачи"/>
          <msh:label property="passportWhomIssued" label="Кем выдан"/>
        </msh:row>
        <msh:row >
          <msh:textField property="birthPlace" label="Место рождения" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
	        <msh:label property="addressInfo" fieldColSpan="3" label="Адрес рег."/>
        </msh:row>
        <msh:separator colSpan="4" label="Доп.сведения" />
         <msh:row >
          <msh:label property="deathDate" label="Дата смерти"  />
            <msh:label property="notice" label="Примечание"  />
        </msh:row>
        <msh:row >
          <msh:checkBox property="noActuality" label="Запись на пациента не действует" fieldColSpan="3"/>
        </msh:row>
      </msh:panel>
    </msh:form>
    <!-- ПОЛИСЫ -->
    <msh:ifFormTypeIsView formName="mis_patientForm">
      <msh:ifInRole roles="/Policy/Mis/MedPolicy/View">
 <%--        <ecom:parentEntityListAll formName="mis_medPolicyForm" attribute="policies" />--%>
        <ecom:webQuery nativeSql="select mp.id
        , case when (mp.DTYPE='MedPolicyOmc') then 'ОМС' when (mp.DTYPE='MedPolicyDmcForeign') then 'ДМС иногороднего' when (mp.DTYPE='MedPolicyDmc') then 'ДМС' else 'ОМС иногороднего' end
        , ri.name as riname,mp.polnumber,mp.series
        ,mp.actualDateFrom,mp.actualDateTo 
        from MedPolicy as mp 
         left join reg_ic as ri on ri.id=mp.company_id 
         where mp.patient_id=${param.id} and mp.actualDateFrom <=CURRENT_DATE and (mp.actualDateTo is null or mp.actualDateTo >=CURRENT_DATE)"  name="policies"  />
        
          <msh:section>
          <msh:sectionTitle>
          АКТУАЛЬНЫЕ ПОЛИСЫ. 
          <msh:link action="javascript:void(0)" onclick="getDefinition('entityParentShortList-mis_medPolicy.do?id=${param.id}', event); return false ;"   >Просмотреть все полисы</msh:link>
          Добавить полис: 
          <msh:link action="entityParentPrepareCreate-mis_medPolicyOmc.do?id=${param.id}"  roles="/Policy/Mis/MedPolicy/Omc/Create">OMC</msh:link>
          <msh:link action="entityParentPrepareCreate-mis_medPolicyOmcForeign.do?id=${param.id}"  roles="/Policy/Mis/MedPolicy/OmcForeign/Create">OMC иногороднего</msh:link><br/>
          <msh:link action="js-mis_medPolicyDmc-listExist.do?id=${param.id}"  roles="/Policy/Mis/MedPolicy/Dmc/Create">ДMC</msh:link>
          <msh:link action="entityParentPrepareCreate-mis_medPolicyDmcForeign.do?id=${param.id}"  roles="/Policy/Mis/MedPolicy/DmcForeign/Create">ДMC иногороднего</msh:link>
                     
          </msh:sectionTitle>
          <msh:sectionContent>
            <msh:table name="policies" hideTitle="false" action="entitySubclassView-mis_medPolicy.do" idField="1">
              <msh:tableColumn property="sn" columnName="#" />
              <msh:tableColumn property="2" columnName="Тип" />
              <msh:tableColumn property="3" columnName="Страховая компания" />
              <msh:tableColumn property="5" columnName="Серия"/>
              <msh:tableColumn property="4" columnName="Номер"/>
              <msh:tableColumn property="6" columnName="Дата начала" />
              <msh:tableColumn property="7" columnName="Дата окончания" />
            </msh:table>
          
<%--             <msh:table name="policies" hideTitle="true" action="entitySubclassView-mis_medPolicy.do" idField="id">
              <msh:tableColumn property="text" />
            </msh:table> --%>
          </msh:sectionContent>
          </msh:section>
     </msh:ifInRole>
     </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

