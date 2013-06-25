<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="/entitySaveGoView-smo_visit.do" defaultField="dateStart">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="parent" />
      <msh:hidden property="isDiaryCreate"/>
      <msh:hidden property="isPrint"/>
      <msh:hidden property="isDiagnosisCreate"/>
      <msh:hidden property="isCloseSpo"/>
      <msh:hidden property="orderWorkFunction"/>
      <msh:hidden property="username"/>
      <msh:hidden property="createDate"/>
      <msh:hidden property="infoByPolicy" />
      <msh:hidden property="isPreRecord" />
      <msh:hidden property="patient" guid="ef57d35d-e9a0-48ba-a00c-b77676505ab2" />
      <msh:panel guid="panel">
                  	<msh:row>
      		<td colspan="4"><div id='medPolicyInformation' style="display: none;" class="errorMessage"/></td>
      	</msh:row>
        <msh:separator colSpan="4" label="Направлен" guid="86dbd4c5-cfa1-4af1-b250-fabe97b77971" />
        <msh:row guid="fa7ff4e9-4b3d-4402-b046-86283cf7938e">
          <msh:autoComplete viewAction="entityParentView-mis_lpu.do" vocName="mainLpu" property="orderLpu" label="Внешний направитель" guid="cbab0829-c896-4b74-9a68-c9f95676cc3b" horizontalFill="true" fieldColSpan="3" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunctionByDirect" property="workFunctionPlan" label="Куда" guid="377c0e96-6812-477d-b44f-d640d659b7b6" fieldColSpan="3" size="50" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="row1">
          <msh:autoComplete vocName="vocWorkCalendarDayByWorkFunction" property="datePlan" label="Направлен на дату" guid="d7f4bef5-0f84-4d3c-b7d9-b7c7c5d51907" horizontalFill="true" parentAutocomplete="workFunctionPlan" viewOnlyField="true" />
          <msh:autoComplete vocName="vocWorkCalendarTimeWorkCalendarDay" property="timePlan" label="Время" guid="1d6b9712-62cc-4c67-a2d8-77bfef298ff3" parentAutocomplete="datePlan" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="1f82-f4e4-4051-ae36-9bfded6">
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="orderWorkFunction" label="Функция направителя" guid="01d5-ba7e-45da-a82a-9cdcd2" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" size="50" />
        </msh:row>
        <msh:row guid="213e017e-0fd0-4027-a84b-96d11818c390">
          <msh:autoComplete viewAction="entityParentView-smo_spo.do" property="parent" label="СПО" guid="1daf90d8-052b-4776-809c-aedeeac116be" fieldColSpan="3" horizontalFill="true" vocName="vocOpenedSpoByPatient" parentId="smo_visitForm.patient" />
        </msh:row>
        <msh:row guid="8171a822-87ac-4a70-9c65-dd2234890dad">
        	<msh:ifInRole roles="/Policy/Mis/MedCase/Visit/DontEditServiceStream">
          <msh:autoComplete size="20" showId="false" vocName="vocServiceStream" property="serviceStream" label="Поток" guid="d23cec11-2213-41d7-8877-73235062a656" horizontalFill="true"  viewAction="true"/>
          </msh:ifInRole>
          <msh:ifNotInRole roles="/Policy/Mis/MedCase/Visit/DontEditServiceStream">
          <msh:autoComplete size="20" showId="false" vocName="vocServiceStream" property="serviceStream" label="Поток" guid="d23cec11-2213-41d7-8877-73235062a656" horizontalFill="true"/>
          </msh:ifNotInRole>
          <msh:autoComplete size="20" showId="false" vocName="vocWorkPlaceType" property="workPlaceType" label="Место" guid="7f2d4e25-c863-439c-99eb-4f82a6159067" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:checkBox label="Неотложная помощь" property="emergency" fieldColSpan="3"/>
        </msh:row>
        <msh:separator colSpan="4" label="Прием" guid="86dbd4c5-cfa1-4af1-b250-fabe97b77971y" />
        <msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель" viewAction="entityParentView-mis_kinsman.do" 
        	parentId="smo_visitForm.patient" vocName="kinsmanBySMO" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
          <msh:textField property="dateStart" label="Дата приема" guid="b2d29f22-2b89-4b43-a6af-ef7c8b8c5fb3" />
          <msh:textField property="timeExecute" label="Время" guid="8d583c3f-dda1-43a9-8417-5a2d43a6cd40" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocReason" property="visitReason" viewOnlyField="false" label="Цель визита" fieldColSpan="1" horizontalFill="true" />
          <msh:autoComplete vocName="vocDispanseryRegistration" property="dispRegistration" label="Диспансерный учет" fieldColSpan="1" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" vocName="vocVisitResult" property="visitResult" viewOnlyField="false" label="Результат визита" guid="77a0dc57-91e5-45a8-b12e-0cdebc6475bb" fieldColSpan="1" horizontalFill="true" />
	        <msh:textField property="uet" label="Усл.един.трудоем."/>
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocHospitalization" labelColSpan="3" property="hospitalization" label="Посещ. по дан. заболеванию в текущем году" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocMedCaseDefect" labelColSpan="3" property="medCaseDefect" label="Дефекты направления" horizontalFill="true" />
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MisLpu/Ambulance">
	       	<msh:row>
	       		<msh:separator label="Скорая помощь" colSpan="4"/>
	       	</msh:row>
	        <msh:row>
	        	<msh:autoComplete vocName="vocAmbulance" property="ambulance" label="Бригада СП" horizontalFill="true" />
	        	<msh:autoComplete vocName="vocVisitOutcome" property="visitOutcome" label="Исход СП" horizontalFill="true" />
	        </msh:row>
        </msh:ifInRole>
       	<msh:row>
       		<msh:separator label="Госпитализация" colSpan="4"/>
       	</msh:row>
        <msh:row>
        	<msh:textField property="orderDate" label="Планируемая дата госпитализации" labelColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" label="Отделение" fieldColSpan="3" horizontalFill="true" vocName="lpu"/>
        </msh:row>

        <msh:row>
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="workFunctionExecute" label="Функция" guid="010e3a75-ba7e-45da-a82a-9c618a0ffcd2" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительно" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isPrintInfo" label="Распечатан?" viewOnlyField="true"/>
			<msh:checkBox property="noActuality" viewOnlyField="false" label="Недействительность визита" guid="6573be39-9a16-4a7c-bdef-5ca915d669c2" horizontalFill="false" fieldColSpan="1" labelColSpan="1" />        
		</msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
          <msh:label property="username" label="Пользователь" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
          	<msh:label property="editUsername" label="Пользователь" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="3" labelSave="Принять пациента" labelSaving="Принять пациента..." />
        <msh:ifFormTypeIsNotView formName="smo_visitForm">
        <msh:row>
        	<td colspan="3" align="right"><input type="button" onclick="preRecord()" value="Предварительный прием"></td>
        </msh:row>
        </msh:ifFormTypeIsNotView>
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView1212" formName="smo_visitForm">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Diagnosis/View" guid="984f4783-f982-4aea-9652-0f5567c932d6">
        <msh:section guid="sectionChilds" title="Диагнозы">
          <ecom:parentEntityListAll guid="parentEntityListChilds" formName="smo_diagnosisForm" attribute="childs" />
          <msh:table guid="tableChilds" name="childs" action="entityParentView-smo_diagnosis.do" idField="id">
            <msh:tableColumn columnName="Дата установления" property="establishDate" guid="c975f4df-68b1-42b9-8ba7-44e4b82b0144" />
            <msh:tableColumn columnName="Наименование" property="name" guid="a744754f-5212-4807-910f-e4b252aec108" />
            <msh:tableColumn columnName="Приоритет" property="priorityDiagnosisText" guid="bf4cb2b2-eb35-4e8f-b8cb-4ccccb06d5ac" />
            <msh:tableColumn columnName="Код МКБ" property="idc10Text" guid="680ae408-52a8-4743-bb60-a129db69a544" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/View" guid="141a017a-569a-4fa2-9a0e-37093a8f69da">
        <msh:section title="Заключение" guid="1fbe1294-8ea0-4b66-a0f3-6c99bcea13c1">
          <ecom:parentEntityListAll formName="smo_visitProtocolForm" attribute="protocols" guid="307a660c-7369-4ec7-a67c-888f8c6aabcf" />
          <msh:table hideTitle="false" disableKeySupport="false" idField="id" name="protocols" action="entityParentView-smo_visitProtocol.do" disabledGoFirst="false" disabledGoLast="false" guid="d0e60067-9aec-4ee0-b20a-4f4b5aea9b37">
            <msh:tableColumn columnName="Дата" property="dateRegistration" guid="c16dd9e1-4534-44db-8b0a-972e2c67dd87" />
            <msh:tableColumn columnName="Запись" identificator="false" property="record" guid="bd9f7fe4-b1cb-4320-aa85-03952b4abd26" cssClass="preCell" />
            <msh:tableColumn columnName="Специалист" property="specialistInfo" guid="96c6570b-360d-46a5-9fc5-2f9c63ad1912" />
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
      		when d.dtype='BaseMedicalExamination' then 'Паспорт здоровья'
      		when d.dtype='DirectionToMicrobiologAnalysis' then 'Направление на микробиологическое исследование'
      		else '-' end,d.diagnosis
      		from Document d where d.medCase_id='${param.id}'
      		"/>
      		<msh:table name="docum" action="entitySubclassView-doc_document.do" 
      	 	 viewUrl="entitySubclassShortView-doc_document.do" idField="1" hideTitle="true">
      			<msh:tableColumn property="3"/>
      		</msh:table>
      	</msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/View">
      	<msh:section title="Предварительная госпитализация">
      		<ecom:webQuery name="WorkCalendarHospitalBed" nativeSql="select d.id as di
      		,d.dateFrom
      		,dep.name
      		,d.diagnosis
      		from WorkCalendarHospitalBed d
      		left join MisLpu dep on dep.id=d.department_id 
      		where d.visit_id='${param.id}'
      		"/>
      		<msh:table name="WorkCalendarHospitalBed" action="entityView-smo_planHospitalByVisit.do" 
      	 	 viewUrl="entityShortView-smo_planHospitalByVisit.do" idField="1" hideTitle="true">
      			<msh:tableColumn property="2" columnName="Пред. дата госпитализации"/>
      			<msh:tableColumn property="3" columnName="Отделение"/>
      		</msh:table>
      	</msh:section>
      </msh:ifInRole>
      <%--
      <msh:ifInRole roles="/Policy/Mis/MedCase/PrescriptionBlank/View" guid="5a2a2f8d-9bd8-4443-962c-1bbd5aa6caa9">
        <msh:section title="Рецептурные бланки" guid="4d7584a1-8f31-468c-9185-b3cb98e8368e">
          <ecom:parentEntityListAll formName="smo_visitPrescriptionBlankForm" attribute="prescriptionBlanks" guid="c9756299-7963-4979-bdc3-a3f1290e279c" />
          <msh:table name="prescriptionBlanks" action="entityParentView-smo_visitPrescriptionBlank.do" idField="id" guid="e52580a8-d07a-47b8-b2a4-b875333e687f">
            <msh:tableColumn columnName="Дата выдачи" property="writingOutDate" guid="5a18547c-ed9d-48df-b448-253d004c7562" />
            <msh:tableColumn columnName="Серия" property="series" guid="56eb20e4-e7cc-4832-8fce-a69798817ba8" />
            <msh:tableColumn columnName="Номер" property="number" guid="f3a255cd-dda5-4a44-a782-cf4480680a4a" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
       --%>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="smo_visitForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
  	<msh:ifFormTypeIsNotView formName="smo_visitForm">
  		<msh:sideMenu title="Визит">
	        <msh:sideLink confirm="Вы точно хотите оформить не явку на прием" key="ALT+3" params="id" action="/js-smo_visit-noPatient" name="Оформить не явку на прием" title="Оформить не явку на прием" roles="/Policy/Mis/MedCase/Visit/Edit"/>
	        
  		</msh:sideMenu>
  		<msh:sideMenu title="Показать">
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherVisitsByPatient('.do')" name='ВИЗИТЫ' title="Просмотр визитов по пациенту" key="ALT+4" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Visit/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherDiagnosisByPatient('.do')" name='ДИАГНОЗЫ' title="Просмотр диагнозов по пациенту" key="ALT+5" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Visit/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherHospitalMedCase('.do')" name='Госпитализации' title="Просмотр госпитазиций по пациенту" key="ALT+6" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Stac/Ssl/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherExtMedserviceByPatient('.do')" name='Внешние лаб. исследования' title="Просмотр внешних лабораторных данных по пациенту" key="ALT+5" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Document/External/Medservice/View" />
  		</msh:sideMenu>
  	</msh:ifFormTypeIsNotView>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="smo_visitForm">
      <msh:sideMenu guid="sideMenu-123" title="Визит">
        <msh:sideLink key="ALT+0" action="/js-smo_visit-findPolyAdmissions" name="Рабочий календарь"
        	roles="/Policy/Mis/MedCase/Visit/View" styleId="selected_menu"
        />
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-smo_visit" name="Принять пациента" roles="/Policy/Mis/MedCase/Visit/Edit" />
        <msh:sideLink confirm="Вы точно хотите оформить не явку на прием" key="ALT+3" params="id" action="/js-smo_visit-noPatient" name="Оформить не явку на прием" title="Оформить не явку на прием" roles="/Policy/Mis/MedCase/Visit/Edit"/>
        <msh:sideLink params="id" action="/js-smo_visit-closeSpo" name="Закрыть СПО" title="Закрыть СПО" confirm="Закрыть СПО?" guid="d84659f7-7ea9-4400-a11c-c83e7d5c578d" key="ALT+4" roles="/Policy/Mis/MedCase/Spo/Close" />
        
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-smo_visit" name="Удалить" roles="/Policy/Mis/MedCase/Visit/Delete" />
        
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="d2c3339b-5d34-41a4-8e29-c91aafc9f483">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-smo_diagnosis" name="Диагноз" title="Добавить диагноз" guid="a54c4c9e-7248-467f-8095-de4edfec868d" roles="/Policy/Mis/MedCase/Diagnosis/Create" key="ALT+5" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-vac_vaccination" name="Вакцинацию" title="Добавить вакцинацию" guid="f417958a-1816-4fe0-8592-dfe35ac19dc8" roles="/Policy/Mis/Vaccination/Create" key="ALT+6" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-smo_visitProtocol" name="Заключение" title="Добавить протокол" guid="2209b5f9-4b4f-4ed5-b825-b66f2ac57e87" roles="/Policy/Mis/MedCase/Protocol/Create" key="ALT+7" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-smo_planHospitalByVisit" name="Предварительную госпитализацию" title="Добавить протокол" guid="2209b5f9-4b4f-4ed5-b825-b66f2ac57e87" roles="/Policy/Mis/MedCase/Protocol/Create" key="ALT+7" />
         <msh:sideLink roles="/Policy/Mis/MedCase/MedService/Create" key="ALT+8" name="Услугу" params="id" 
         	action="/entityParentPrepareCreate-smo_medService" title="Добавить услугу" guid="df23d" />
<%--         <msh:sideLink params="id" action="/js-smo_visit-addDisabilityByRedirectFromVisit" name="Нетрудоспособность" title="Добавить нетрудоспособность" guid="784c86f1-44e5-4642-ae35-b68c2abd0604" roles="/Policy/Mis/Disability/DisabilityCase/Create" key="ALT+7" /> --%>
        <msh:sideLink params="id" action="/entityParentPrepareCreate-smo_visitPrescriptionBlank" name="Рецептурный бланк" title="Добавить рецептурный бланк" guid="78d-4642-ae35-b6d04" roles="/Policy/Mis/MedCase/PrescriptionBlank/Create" />
            <msh:sideLink roles="/Policy/Mis/MedCase/Direction/Create"  key="CTRL+1"
    	name="Направление к план. раб. функции &larr;"   action="/javascript:goNewDirectionMine('.do')"  
    	 title='Направление к план. раб. функции'  />
    	         
            <msh:sideLink roles="/Policy/Mis/MedCase/Direction/Create"  key="CTRL+1"
    	name="Направление к другому специалисту &larr;"   action="/javascript:goNewDirectionOther('.do')"  
    	 title='Направление к другому специалисту'  />
    	         
    	 
      </msh:sideMenu>
      <msh:sideMenu title="Администрирование">
	   	<tags:mis_changeServiceStream service="TicketService" name="CSS" title="Изменить поток обслуживания" roles="/Policy/Mis/MedCase/Visit/ChangeServiceStream" />
      	<tags:mis_choiceSpo method="moveVisitOtherSpo" methodGetPatientByPatient="getOpenSpoBySmo" hiddenNewSpo="0" service="TicketService" name="moveVisit"  roles="/Policy/Mis/MedCase/Visit/MoveVisitOtherSpo" title="Перевести визит в другой СПО" />
      </msh:sideMenu>
      <msh:sideMenu title="Показать" guid="1e56f157-fd4c-4c13-9275-cfe7868b4ceb">
        <msh:sideLink params="id" action="/entityParentList-vac_vaccination" name="Вакцинации" title="Показать все вакцинации" roles="/Policy/Mis/Vaccination/View" guid="0b4406ba-1860-4063-a26a-ea11f6f9fb23" />
        <msh:sideLink roles="/Policy/Mis/Prescription/Prescript/View" name="Листы назначений" params="id" action="/entityParentList-pres_prescriptList" title="Показать все листы назначений" guid="7hb0b69ae-3b9c-47d9-ab3c-5fbe6fa9f" />
         <msh:sideLink roles="/Policy/Mis/MedCase/MedService/View" name="Услуги" params="id" action="/entityParentList-smo_medService" title="Показать все услуги" guid="df23-45a26d-5hfd" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewExtMedDocumentByPatient('.do')" name='Внеш. мед. документы' title="Просмотр внеш. мед. документов по пациенту" params="" roles="/Policy/Mis/MedCase/Document/External" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherVisitsByPatient('.do')" name='ВИЗИТЫ' title="Просмотр визитов по пациенту" key="ALT+4" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Visit/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherDiagnosisByPatient('.do')" name='ДИАГНОЗЫ' title="Просмотр диагнозов по пациенту" key="ALT+5" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherHospitalMedCase('.do')" name='Госпитализации' title="Просмотр госпитазиций по пациенту" key="ALT+6" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Stac/Ssl/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherExtMedserviceByPatient('.do')" name='Внешние лаб. исследования' title="Просмотр внешних лабораторных данных по пациенту" key="ALT+5" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Document/External/Medservice/View" />
      </msh:sideMenu>
      <msh:tableNotEmpty name="childs">
      <msh:sideMenu title="Печать" guid="cdf02c63-67bc-4542-a68d-38398f5059bd">
        <msh:sideLink action="/javascript:printVisit('.do')" name="Визита" title="Печать визита" key="ALT+8" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Visit/PrintVisit" />
        <msh:sideLink params="id" action="/print-talon_amb.do?s=VisitPrintService&amp;m=printTalon" key="ALT+9" name="Талона" title="Печать талона" guid="12dbaf61-0108-4845-86c3-cee528329b33" roles="/Policy/Mis/MedCase/Visit/PrintTalon" />
        <msh:sideLink params="id" action="/print-talon_amb_short.do?s=VisitPrintService&amp;m=printTalon"  name="Укороченного талона" title="Печать короткой версии талона" guid="1daf61-0108-4845-86c3-cs3" roles="/Policy/Mis/MedCase/Visit/PrintShortTalon" />
        <msh:sideLink roles="/Policy/Mis/MedCase/Visit/PrintBakExp" params="id" action="/print-BakExp.do?s=VisitPrintService&amp;m=printBakExp" name="Направления на бак.исследование" guid="51g38-f936-45d0-ac70-0g66c" />
        <tags:diary_parameterCreate document="документа" roles="/Policy/Mis/MedCase/Document/Internal/Create" action="doc_create_type.do?id=${param.id}" name="type" title="Документа" vocName="documentType" />
      </msh:sideMenu>
      </msh:tableNotEmpty>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <script type="text/javascript" src="./dwr/interface/TicketService.js"></script>
  <msh:ifFormTypeIsNotView formName="smo_visitForm">
  
  	<script type="text/javascript">
  	var frm = document.forms[0] ;
  	frm.action='javascript:checkVisit()' ;
  	alert($("workFunctionExecute").value) ;
		TicketService.getOpenSpoByPatient((+$("workFunctionPlan").value),$('patient').value,{
			callback: function(aResult) {
				if (aResult!="") {
    				var val = aResult.split("@") ;
    				$('parent').value = val[0];
    				$('parentName').value= val[1];
				} else {
    				$('parent').value = '';
    				$('parentName').value= '';
				}
			}
		}) ;
	
  	
  	
  	function checkVisit() {
  		TicketService.checkHospital($('dateStart').value,$('patient').value,$('serviceStream').value
  		,{callback: function(aString) {
        	//alert(aString) ;
            if (aString!=null) {
            	alert("Человек находился в больнице "+aString+" по ОМС его оформить за этот период нельзя!!!") ;
            } else {
             	frm.action= "entitySaveGoView-smo_visit.do";
             	frm.submit() ;
             }
         }}) ;
  	}
  	function preRecord() {
  		$('isPreRecord').value='1' ;
  		document.forms[0].submit() ;
  	}
  	</script>
  </msh:ifFormTypeIsNotView>
  <%--
  <msh:ifInRole roles="/Policy/Mis/MedCase/MedService/Create">
  <msh:ifFormTypeIsNotView formName="smo_visitForm">
  
  <script type="text/javascript">
  var oldValue = $('dateStart').value ;
  if (theOtmoa_medServices) theOtmoa_medServices.setParentId((+$("workFunctionExecute").value)+"#"+$("dateStart").value) ;
  eventutil.addEventListener($('dateStart'),'blur',function(){
		if (oldValue!=$('dateStart').value) {
			var wf = +$("workFunctionExecute").value;
  		if (wf=='') {wf=0;}
			 if (theOtmoa_medServices) theOtmoa_medServices.setParentId(wf+"#"+$("dateStart").value) ;
  		 if (theOtmoa_medServices) theOtmoa_medServices.clearData() ;
  		 TicketService.getMedServiceBySpec(wf,$('dateStart').value,{
	      	 		callback: function(aResult) {
	      	 			if (theOtmoa_medServices) theOtmoa_medServices.setIds(aResult) ;
	      	 		}
	      	 	}) ;
		}
	}) ;
  </script>
  </msh:ifFormTypeIsNotView>
  </msh:ifInRole>
   --%>
  <script type="text/javascript">
	  function printDischarge() {
		  if (confirm('Вы хотите распечатать выписку по заключению без изменений?')) {
			  window.location.href = "print-discharge.do?s=VisitPrintService&m=printVisit&id=${param.id}" ;
		  } else {
			  if (confirm('Вы будете использовать использовать только заключение данного посещения?')) {
				  
			  } else {
				  
			  }
			  
		  }
	  }
  </script>
  <script type='text/javascript' src='./dwr/interface/TemplateProtocolService.js'></script>
          <script type="text/javascript">//var theBedFund = $('bedFund').value;
      	function goNewDirectionMine() {
      		if (+$('parent').value>0) {
      			window.location = 'entityParentPrepareCreate-smo_direction.do?id='+$('patient').value+"&spo="+$('parent').value+"&workFunction="+$('workFunctionPlan').value+"&serviceStream="+$('serviceStream').value+"&visitReason="+$('visitReason').value+"&tmp="+Math.random() ;
      		} else {
      			alert('Направить к себе можно только после приема пациента!!!') ;
      		}
      	}
      	function goNewDirectionOther() {
    		window.location = 'entityParentPrepareCreate-smo_direction.do?id='+$('patient').value+"&tmp="+Math.random() ;
      	}
      	</script>
      <script type="text/javascript">//var theBedFund = $('bedFund').value;
		if ($('infoByPolicy').value.length>0) {
			$('medPolicyInformation').innerHTML = $('infoByPolicy').value ;
			$('medPolicyInformation').style.display = 'block' ;
		} else {
			$('medPolicyInformation').style.display = 'none' ;
		}
      	</script>
  <msh:ifInRole roles="/Policy/Mis/MedCase/Visit/PrintNotView">
  <script type="text/javascript">
    function printVisit() {
  	TemplateProtocolService.getCountSymbolsInProtocol ('${param.id}'  , {
  	 callback: function(aCount) {
		 window.location.href = "print-visit.do?next=entityParentView-smo_visit.do__id=${param.id}&noView=1&s=VisitPrintService&m=printVisit&id=${param.id}" ;
	 }}) ;
	 $('isPrintInfo').checked='checked' ;
  }
    </script>
  </msh:ifInRole>
  <msh:ifNotInRole roles="/Policy/Mis/MedCase/Visit/PrintNotView">
  	<script type="text/javascript">
  	function printVisit() {
	  		window.location.href = "print-visit.do?s=VisitPrintService&m=printVisit&id=${param.id}" ;
			$('isPrintInfo').checked='checked' ;
    }
  	</script>
  </msh:ifNotInRole>
  <script type="text/javascript">
  var isPrint=0, isDiary=0, isDiag=0, isCloseSpo=0 ;
  function viewOtherExtMedserviceByPatient(d) {
  	  getDefinition("js-doc_externalMedservice-list.do?short=Short&id=${param.id}", null);
    }
  function viewOtherVisitsByPatient(d) {
	  //alert("js-smo_visit-infoShortByPatient.do?id="+$('patient').value) ;
	  
	  getDefinition("js-smo_visit-infoShortByPatient.do?id="+$('patient').value, null); 
  }
  function viewOtherDiagnosisByPatient(d) {
	  getDefinition("js-smo_diagnosis-infoShortByPatient.do?id="+$('patient').value, null);
  }
  function viewOtherHospitalMedCase(d) {
	  getDefinition("js-stac_ssl-infoShortByPatient.do?id="+$('patient').value, null);
  }
  function viewExtMedDocumentByPatient(d) {
	  getDefinition("js-doc_externalDocument-infoMedShortByPatient.do?short=Short&id="+$('patient').value, null);
  }
  </script>

  <msh:ifFormTypeIsView formName="smo_visitForm">
  	<msh:ifNotInRole roles="/Policy/Mis/MedCase/Visit/DisableCheck">
  	<script type="text/javascript">
  		var isDiag = 0 ,isDiary = 0, isPrint = 0 ; 
  	</script>
    <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
  		<msh:ifInRole roles="/Policy/Mis/MedCase/Diagnosis/Create">
	  		<script type="text/javascript">isDiag=1 ;</script>
	  	</msh:ifInRole>
  		<msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/Create">
	  		<script type="text/javascript">isDiary=1 ;</script>
	  	</msh:ifInRole>
	  	<msh:ifInRole roles="/Policy/Mis/MedCase/Visit/PrintVisit">
	  		<script type="text/javascript">isPrint=1 ;</script>
	  	</msh:ifInRole>
	  	<script type="text/javascript">
	  	var url_next = new Array(
	  			new Array(0,"entityParentPrepareCreate-smo_visitProtocol.do?id=${param.id}"
	  				,"Вы хотите создать заключение по визиту?","isDiaryCreate",isDiary)
	  			,new Array(0,"entityParentPrepareCreate-smo_diagnosis.do?id=${param.id}"
	  				,"Вы хотите создать диагноз по визиту?","isDiagnosisCreate",isDiag)
	  			,new Array(1,"printVisit()","Вы хотите распечатать визит?","isPrint",isPrint)
	  			//,new Array(0,"js-smo_visit-closeSpo.do?id=${param.id}","Закрыть СПО?"
	  			//	,"isCloseSpo",1)
	  	) ;
	  	function goPriem(index) {
	  		if ($('dateStart').value=="") return ;
	   		if (index>(url_next.length-1)) return ;
	  		var url = url_next[index] ;
	  		var next = true ;
	  		index++ ;
	  		if (url[4]>0) {
  				if (+$(url[3]).value<1) {
  					if (+url[0]==0) {
  						next = false ;
  					} 
  					
  					if (confirm(url[2])) {
  						PatientService.setAddParamByMedCase(url[3],'${param.id}',2,
  								{
  									callback: function(aReturn) {
  										if (url[0]==0) {
  											window.location = url[1] ;
  										} else {eval(url[1]) ;}
  										return ;
  									}
  								}
  							);
  					} else {
  						PatientService.setAddParamByMedCase(url[3],'${param.id}',1,
  								{callback: function(aReturn) {
  									goPriem(index) ;
  									return ;
  								}}
  							);	  						
  					}
  				}
  			}
	  		if (next) goPriem(index) ;
	  		return ;
	  	}
	  	goPriem(0) ;
	  </script>

  	</msh:ifNotInRole>
  </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

