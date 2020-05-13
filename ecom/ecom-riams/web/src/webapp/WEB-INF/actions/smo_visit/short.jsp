<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form title="<a href='entityParentView-smo_visit.do?id=${param.id}'>Визит</a>" action="/entitySaveGoView-smo_visit.do" defaultField="dateStart">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parent" />
      <msh:hidden property="isDiaryCreate"/>
      <msh:hidden property="isPrint"/>
      <msh:hidden property="isDiagnosisCreate"/>
            <msh:hidden property="patient" />
      <msh:panel colsWidth="20%, 10%, 10%">
        <msh:separator colSpan="4" label="Направлен" />
        <msh:row>
          <msh:autoComplete viewAction="entityParentView-mis_lpu.do" vocName="mainLpu" property="orderLpu" label="Внешний направитель" horizontalFill="true" fieldColSpan="3" viewOnlyField="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunctionByDirect" property="workFunctionPlan" label="Куда" fieldColSpan="3" size="50" viewOnlyField="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocWorkCalendarDayByWorkFunction" property="datePlan" label="Направлен на дату" horizontalFill="true" parentAutocomplete="workFunctionPlan" viewOnlyField="true" />
          <msh:autoComplete vocName="vocWorkCalendarTimeWorkCalendarDay" property="timePlan" label="Время" parentAutocomplete="datePlan" viewOnlyField="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="orderWorkFunction" label="Функция направителя" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" size="50" />
        </msh:row>
        <msh:row>
          <msh:autoComplete viewAction="entityParentView-smo_spo.do" property="parent" label="СПО" fieldColSpan="3" horizontalFill="true" vocName="vocOpenedSpoByPatient" parentId="smo_visitForm.patient" />
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" vocName="vocServiceStream" property="serviceStream" label="Поток" horizontalFill="true" />
          <msh:autoComplete showId="false" vocName="vocWorkPlaceType" property="workPlaceType" label="Место" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:checkBox label="Неотложная помощь" property="emergency" fieldColSpan="3"/>
        </msh:row>
        <msh:separator colSpan="4" label="Прием" />
        <msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель" viewAction="entityParentView-mis_kinsman.do" 
        	parentId="smo_visitForm.patient" vocName="kinsmanBySMO" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
          <msh:textField property="dateStart" label="Дата приема" />
          <msh:textField property="timeExecute" label="Время" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocReason" property="visitReason" viewOnlyField="false" label="Цель визита" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" vocName="vocVisitResult" property="visitResult" viewOnlyField="false" label="Результат визита" fieldColSpan="1" horizontalFill="true" />
          <msh:textField property="nextVisitDate" label="След.дата посещен. по плану"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocDispanseryRegistration" property="dispRegistration" label="Диспансерный учет" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
	        <msh:textField property="uet" label="Усл.един.трудоем."/>
          <msh:textField property="privilegeRecipeAmount" label="Количество выписанных льготных рецептов" />
        </msh:row>
        <msh:row>
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="workFunctionExecute" label="Функция" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительно" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isPrintInfo" label="Распечатан?" viewOnlyField="true"/>
			<msh:checkBox property="noActuality" viewOnlyField="false" label="Недействительность визита" horizontalFill="false" fieldColSpan="1" labelColSpan="1" />
		</msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
          <msh:label property="username" label="Пользователь" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
          	<msh:label property="editUsername" label="Пользователь" />
        </msh:row>
		
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="smo_visitForm">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Diagnosis/View">
        <msh:section title="Диагнозы">
          <ecom:parentEntityListAll formName="smo_diagnosisForm" attribute="childs" />
          <msh:table name="childs" action="entityParentView-smo_diagnosis.do" idField="id">
            <msh:tableColumn columnName="Дата установления" property="establishDate" />
            <msh:tableColumn columnName="Наименование" property="name" />
            <msh:tableColumn columnName="Приоритет" property="priorityDiagnosisText" />
            <msh:tableColumn columnName="Код МКБ" property="idc10Text" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/View">
        <msh:section title="Заключение">
          <ecom:parentEntityListAll formName="smo_visitProtocolForm" attribute="protocols" />
          <msh:table hideTitle="false" disableKeySupport="false" idField="id" name="protocols" action="entityParentView-smo_visitProtocol.do" disabledGoFirst="false" disabledGoLast="false">
            <msh:tableColumn columnName="Дата" property="dateRegistration" />
            <msh:tableColumn columnName="Запись" identificator="false" property="record" cssClass="preCell" />
            <msh:tableColumn columnName="Специалист" property="specialistInfo" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/MedService/Create">
      	<msh:section title="Услуги" createUrl="entityParentPrepareCreate-smo_medService.do?id=${param.id}" createRoles="/Policy/Mis/MedCase/MedService/Create">
      		<ecom:webQuery name="services" nativeSql="select mc.id,ms.name,mc.medServiceAmount
      		from MedCase mc 
      		left join MedService ms on mc.medService_id=ms.id
      		where mc.parent_id='${param.id}' and mc.dtype='ServiceMedCase'
      		"/>
      		<msh:table name="services" action="entityParentView-smo_medService.do" 
      	 	 viewUrl="entityShortView-smo_medService.do" idField="1" >
      			<msh:tableColumn columnName="Название услуги" property="2"/>
      			<msh:tableColumn columnName="Кол-во" property="3"/>
      		</msh:table>
      	</msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Document/Internal/View">
      	<msh:section title="Документы">
      		<ecom:webQuery name="docum" nativeSql="select d.id as did,d.history
      		, case when d.dtype='DirectionDocument' then 'Направление' 
      		when d.dtype='DischargeDocument' then 'Выписка'
      		else '-' end,d.diagnosis
      		from Document d where d.medCase_id='${param.id}'
      		"/>
      		<msh:table name="docum" action="entitySubclassView-doc_document.do" 
      	 	 viewUrl="entitySubclassShortView-doc_document.do" idField="1" hideTitle="true">
      			<msh:tableColumn property="3"/>
      		</msh:table>
      	</msh:section>
      </msh:ifInRole>
	</msh:ifFormTypeIsView>    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="smo_visitForm" />
  </tiles:put>  
</tiles:insert>

