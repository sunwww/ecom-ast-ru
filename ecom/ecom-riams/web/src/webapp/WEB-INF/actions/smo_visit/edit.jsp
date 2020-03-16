<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name="style" type="string">
        <style type="text/css">
            .centerModalDiv {
                top: 10%;
                left: 20%;
                line-height: 1.7;
            }
        </style>
    </tiles:put>

  <tiles:put name="body" type="string">
      <div class="centerModalDiv" id="simpleServiceDiv" title="Простые услуги">
          <div id="simpleServiceDataDiv"></div>
      </div>
    <msh:form action="/entitySaveGoView-smo_visit.do" defaultField="dateStart">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parent" />
      <msh:hidden property="isDiaryCreate"/>
      <msh:hidden property="isPrint"/>
      <msh:hidden property="isDiagnosisCreate"/>
      <msh:hidden property="isCloseSpo"/>
      <msh:hidden property="orderWorkFunction"/>
      <msh:hidden property="username"/>
      <msh:hidden property="createDate"/>
      <msh:hidden property="infoByPolicy" />
      <msh:hidden property="medserviceAmounts" />
      <msh:hidden property="isPreRecord" />
      <msh:hidden property="guarantee" />
      <msh:hidden property="isPaid" />
      <msh:hidden property="patient" />
      <msh:panel>
                  	<msh:row>
      		<td colspan="4"><div id='medPolicyInformation' style="display: none;" class="errorMessage"></div></td>
      	</msh:row>
<msh:ifInRole roles="/Policy/TEST">
<msh:row><td colspan="4">

    <ecom:webQuery name="documentsByVisitList" nativeSql="select d.id,vedt.name as vedtname,to_char(d.createDate,'dd.mm.yyyy')||coalesce(' '||cast(d.createTime as varchar(5)),'')
              ,d.referenceTo from Document d
      	left join VocExternalDocumentType vedt on vedt.id=d.type_id
      	where d.medcase_id='${param.id}' AND UPPER(d.dtype)='EXTERNALDOCUMENT'"/>
    <msh:tableNotEmpty name="documentsByVisitList">
        <msh:table name="documentsByVisitList"
                   action="javascript:showDocument()" idField="4">
            <msh:tableColumn  columnName="#" property="sn"/>
            <msh:tableButton  property="4" buttonFunction="showExternalDocument" buttonName="Просмотреть документ" buttonShortName="Просмотреть документ"/>
        </msh:table>
    </msh:tableNotEmpty>
</td>

</msh:row>
          <msh:row>
              <msh:textArea property="patientComment" fieldColSpan="4" label="Сообщение пациента при записи" viewOnlyField="true"/>
          </msh:row>
</msh:ifInRole>
        <msh:separator colSpan="4" label="Направлен" />
        <msh:row>
          <msh:autoComplete vocName="mainLpu" property="orderLpu" label="Внешний направитель" horizontalFill="true" fieldColSpan="3" viewOnlyField="true" />
        </msh:row>
          <msh:row>
              <msh:textField property="orderDate" label="Дата направления" viewOnlyField="true" labelColSpan="3"/>
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
 <msh:ifInRole roles="/Policy/Mis/MedCase/Visit/CanAttachToCloseSPO"> 
<td id="btnClosedSPO" style="display:none;">
<input type='button' onclick='showListClosedSpo()' value="Прикрепить к закрытому СПО" >
</td>
 </msh:ifInRole>
        </msh:row>
        <msh:row>
        	<msh:ifInRole roles="/Policy/Mis/MedCase/Visit/DontEditServiceStream">
          <msh:autoComplete size="20" showId="false" vocName="vocServiceStream" property="serviceStream" label="Поток" horizontalFill="true"  viewAction="true"/>
          </msh:ifInRole>
          <msh:ifNotInRole roles="/Policy/Mis/MedCase/Visit/DontEditServiceStream">
          <msh:autoComplete size="20" showId="false" vocName="vocServiceStream" property="serviceStream" label="Поток" horizontalFill="true"/>
          </msh:ifNotInRole>
          <msh:autoComplete size="20" showId="false" vocName="vocWorkPlaceType" property="workPlaceType" label="Место" horizontalFill="true" />
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
          <msh:autoComplete vocName="vocReason" property="visitReason" viewOnlyField="false" label="Цель визита" fieldColSpan="1" horizontalFill="true" />
          <msh:autoComplete vocName="vocDispanseryRegistration" property="dispRegistration" label="Диспансерный учет" fieldColSpan="1" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" vocName="vocVisitResult" property="visitResult" viewOnlyField="false" label="Результат визита" fieldColSpan="1" horizontalFill="true"/>
	        <msh:textField property="uet" label="Усл.един.трудоем."/>
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocHospitalization" labelColSpan="3" property="hospitalization" label="Посещ. по дан. заболеванию в текущем году" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocMedCaseDefect" labelColSpan="3" property="medCaseDefect" label="Дефекты направления" horizontalFill="true" />
        </msh:row>
        <msh:row>
	   		<ecom:oneToManyOneAutocomplete viewAction="entityView-mis_medService.do" label="Мед. услуги" property="medServices" vocName="medServiceForSpec" colSpan="5"/>
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
        	<msh:autoComplete property="department" label="Отделение" fieldColSpan="3" horizontalFill="true" vocName="lpu"/>
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
        	<msh:separator label="Дополнительно" colSpan="4"/>
        </msh:row>
        </msh:panel>
        <msh:panel>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
            <msh:label property="username" label="пользователь" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
        	<msh:label property="editTime" label="время"/>
          	<msh:label property="editUsername" label="пользователь" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" labelSave="Принять пациента" labelSaving="Принять пациента..." />
        <msh:ifFormTypeIsNotView formName="smo_visitForm">
        <msh:row>
        	<td colspan="3" align="right"><input type="button" onclick="preRecord()" value="Предварительный прием"></td>
        </msh:row>
        </msh:ifFormTypeIsNotView>
      </msh:panel>
    </msh:form>
    
    <msh:ifFormTypeIsView formName="smo_visitForm">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Diagnosis/View">
        <msh:section title="Диагнозы">
        <ecom:webQuery name="info_print_list" nativeSql="select vwf.id,vwf.name
      		from WorkFunction wf
      		left join VocWorkFunction vwf on vwf.id=wf.workFunction_id 
      		left join MedCase mc on mc.workFunctionExecute_id=wf.id
      		left join Diagnosis diag on diag.medcase_id=mc.id
      		where mc.id='${param.id}'
      		group by vwf.id,vwf.name
      		having count(diag.id)>0 or count(case when vwf.isNoDiagnosis='1' then vwf.id else null end)>0" maxResult="1"/>
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
      <msh:ifInRole roles="/Policy/Mis/MedCase/MedService/View">
      	<msh:section title="Услуги" createUrl="entityParentPrepareCreate-smo_medService.do?id=${param.id}" createRoles="/Policy/Mis/MedCase/MedService/Create">
      		<ecom:webQuery name="services" nativeSql="select mc.id,ms.name,mc.medServiceAmount,mc.serviceComment
      		from MedCase mc 
      		left join MedService ms on mc.medService_id=ms.id
      		where mc.parent_id='${param.id}' and mc.dtype='ServiceMedCase'
      		"/>
      		<msh:table name="services" action="entityParentView-smo_medService.do" 
      	 	 viewUrl="entityShortView-smo_medService.do" idField="1" >
      			<msh:tableColumn columnName="Название услуги" property="2"/>
      			<msh:tableColumn columnName="Кол-во" property="3"/>
                <msh:tableColumn columnName="Примечание" property="4"/>
      		</msh:table>
      	</msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Document/Internal/View">
      	<msh:section title="Документы">
      		<ecom:webQuery name="docum" nativeSql="select d.id as did,d.history
      		, case when d.dtype='DirectionDocument' then 'Направление' 
      		when d.dtype='DischargeDocument' then 'Выписка'
      		when d.dtype='DischargeDiagnostDocument' then 'Выписка диагностическая'
      		when d.dtype='BaseMedicalExamination' then 'Паспорт здоровья/Медосмотр'
      		when d.dtype='DirectionToMicrobiologAnalysis' then 'Направление на микробиологическое исследование'
      		when d.dtype='RequitDirectionDocument' then 'Акт в военкомат'
      		when d.dtype='ExternalDocument' then coalesce(vedt.name,'Внешний документ')
      		else '-' end,d.diagnosis
      		from Document d 
      		left join vocexternaldocumenttype vedt on vedt.id=d.type_id
      		where d.medCase_id='${param.id}'
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
      		where d.visit_id='${param.id}' and d.dtype='WorkCalendarHospitalBed'
      		"/>
      		<msh:table name="WorkCalendarHospitalBed" action="entityView-smo_planHospitalByVisit.do" 
      	 	 viewUrl="entityShortView-smo_planHospitalByVisit.do" idField="1" hideTitle="true">
      			<msh:tableColumn property="2" columnName="Пред. дата госпитализации"/>
      			<msh:tableColumn property="3" columnName="Отделение"/>
      		</msh:table>
      	</msh:section>
      </msh:ifInRole>

        <msh:ifInRole roles="/Policy/Mis/Pharmacy/CreateOutcome">
            <msh:section title="Списания" createUrl="pharm_outcome.do?id=${param.id}"
                         createRoles="/Policy/Mis/Pharmacy/CreateOutcome">
                <ecom:webQuery name="services" nativeSql="select
               doc.id
               ,p.lastname||' '||p.firstname||' '||p.middlename||' ('||vwf.name||')' as who
                ,to_char(doc.dateoperation,'dd.MM.yyyy')||' '||to_char(doc.timeoperation,'HH24:MI:SS') as when
                ,vg.drug||'('||vg.form||')' as what
                ,cast(doc.amount as numeric)
                from internaldocument doc
                left join workfunction wf on wf.id = doc.workfunctionid
                left join vocworkfunction vwf on vwf.id = wf.workfunction_id
                left join worker w on w.id =wf.worker_id
                left join patient p on p.id = w.person_id
                left join vocgoods vg on vg.regid = doc.goodid
                where doc.medcaseid=${param.id}
      		"/>
                <msh:table name="services" action="entityParentView-smo_medService.do"
                           viewUrl="entityShortView-smo_medService.do" idField="1" >
                    <msh:tableColumn columnName="Кто списал" property="2"/>
                    <msh:tableColumn columnName="Когда" property="3"/>
                    <msh:tableColumn columnName="Наименование" property="4"/>
                    <msh:tableColumn columnName="Кол-во" property="5"/>
                </msh:table>
            </msh:section>
        </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="smo_visitForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
  	<msh:ifFormTypeIsNotView formName="smo_visitForm">
  		<msh:sideMenu title="Визит">
	        <msh:sideLink confirm="Вы точно хотите оформить не явку на прием" key="ALT+3" params="id" action="/js-smo_visit-noPatient" name="Оформить не явку на прием" title="Оформить не явку на прием" roles="/Policy/Mis/MedCase/Visit/Edit"/>
	        <msh:sideLink action="/javascript:showSimpleService()" name="Добавить простую услугу" title="Добавить простую услугу" roles="/Policy/Mis/MedCase/Visit/Edit"/>

  		</msh:sideMenu>
  		<msh:sideMenu title="Показать">
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherVisitsByPatient('.do')" name='ВИЗИТЫ' title="Просмотр визитов по пациенту" key="ALT+4" params="" roles="/Policy/Mis/MedCase/Visit/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherDiagnosisByPatient('.do')" name='ДИАГНОЗЫ' title="Просмотр диагнозов по пациенту" key="ALT+5" params="" roles="/Policy/Mis/MedCase/Visit/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherHospitalMedCase('.do')" name='Госпитализации' title="Просмотр госпитазиций по пациенту" key="ALT+6" params="" roles="/Policy/Mis/MedCase/Stac/Ssl/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherExtMedserviceByPatient('.do')" name='Внешние лаб. исследования' title="Просмотр внешних лабораторных данных по пациенту" key="ALT+5" params="" roles="/Policy/Mis/MedCase/Document/External/Medservice/View" />
       
<tags:visit_listClosedSpo name="List" title="Изменить ЛПУ направителя" />
  		</msh:sideMenu>
  	</msh:ifFormTypeIsNotView>
    <msh:ifFormTypeIsView formName="smo_visitForm">
      <msh:sideMenu title="Визит">
        <msh:sideLink key="ALT+0" action="/js-smo_visit-findPolyAdmissions" name="Рабочий календарь"
        	roles="/Policy/Mis/MedCase/Visit/View" styleId="selected_menu"
        />
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-smo_visit" name="Принять пациента" roles="/Policy/Mis/MedCase/Visit/Edit" />
        <msh:sideLink params="id" action="/entityEdit-smo_direction" name="Редактировать направление" roles="/Policy/Mis/MedCase/Direction/Edit" />
        <tags:mis_change_lpu service="TicketService" name="CSS" title="Изменить ЛПУ направителя" roles="/Policy/Mis/MedCase/Direction/Edit" />
        <msh:sideLink confirm="Вы точно хотите оформить не явку на прием" key="ALT+3" params="id" action="/js-smo_visit-noPatient" name="Оформить не явку на прием" title="Оформить не явку на прием" roles="/Policy/Mis/MedCase/Visit/Edit"/>
        <msh:sideLink params="id" action="/js-smo_visit-closeSpo" name="Закрыть СПО" title="Закрыть СПО" confirm="Закрыть СПО?" key="ALT+4" roles="/Policy/Mis/MedCase/Spo/Close" />
        
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-smo_visit" name="Удалить" roles="/Policy/Mis/MedCase/Visit/Delete" />
        <msh:sideLink styleId="viewShort" action="/javascript:getDefinition('js-smo_visit-cost_case.do?short=Short&id=${param.id}','.do')" name='Цена' title="Просмотр стоимости услуг" 
        	roles="/Policy/Mis/Contract/Journals/AnalisisMedServices" />
        <msh:sideLink styleId="viewShort" action="/javascript:getDefinition('js-contract_juridicalContract-account_view_by_patient.do?short=Short&id=${param.id}','.do')" name='Услуги по счету' title="Просмотр услуг по счету" 
        	roles="/Policy/Mis/Contract/Journals/AnalisisMedServices" />        
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-smo_diagnosis" name="Диагноз" title="Добавить диагноз" roles="/Policy/Mis/MedCase/Diagnosis/Create" key="ALT+5" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-vac_vaccination" name="Вакцинацию" title="Добавить вакцинацию" roles="/Policy/Mis/Vaccination/Create" key="ALT+6" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-smo_visitProtocol" name="Заключение" title="Добавить протокол" roles="/Policy/Mis/MedCase/Protocol/Create" key="ALT+7" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-smo_planHospitalByVisit" name="Предварительную госпитализацию" title="Добавить предварительную госпитализацию" roles="/Policy/Mis/MedCase/Protocol/Create" key="ALT+10" />
          <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Opht/Create" params="" action="/javascript:createPlanOphtHospital()" name="Планирование введения ангиогенеза" title="Планирование введения ангиогенеза"/>
          <msh:sideLink params="id" action="/pharm_outcome" name="Списание" title="Добавить Списание" roles="/Policy/Mis/Pharmacy/CreateOutcome"/>
         <msh:sideLink roles="/Policy/Mis/MedCase/MedService/Create" key="ALT+8" name="Услугу" params="id"
         	action="/entityParentPrepareCreate-smo_medService" title="Добавить услугу" />
            <msh:sideLink roles="/Policy/Mis/MedCase/Direction/Create"  key="CTRL+1"
    	name="Направление к себе &larr;"   action="/javascript:goNewDirectionMine('.do')"
    	 title='Направление к план. раб. функции'  />
    	         
            <msh:sideLink roles="/Policy/Mis/MedCase/Direction/Create"  key="CTRL+1"
    	name="Направление к другому специалисту &larr;"   action="/javascript:goNewDirectionOther('.do')"  
    	 title='Направление к другому специалисту'  />
        <msh:sideLink styleId="viewShort"  action="/javascript:getDefinition('entityParentList-expert_ker.do?short=Short&id=${param.id}',null)" name='Врачеб. комиссии' title="Просмотр врачебных комиссий" roles="/Policy/Mis/MedCase/ClinicExpertCard/View" />
    	<msh:sideLink roles="/Policy/Mis/Prescription/Prescript/Create" name="Лист назначений" action="/javascript: showCreatePrescriptList('${param.id}','.do')" title="Лист назначений" />         
    	<msh:sideLink roles="/Policy/Mis/AssessmentCard/Create" name="Карту оценки" action="/javascript:goCreateAssessmentCard()" title="Карту оценки" />

      </msh:sideMenu>
      <msh:sideMenu title="Администрирование">

          <msh:sideLink name="Ориентировочная цена по ОМС" action=".javascript:getMedcaseCost()" roles="/Policy/E2/Admin"/>
	   	<tags:mis_changeServiceStream name="CSS" title="Изменить поток обслуживания" roles="/Policy/Mis/MedCase/Visit/ChangeServiceStream" />
      	<tags:mis_choiceSpo method="moveVisitOtherSpo" methodGetPatientByPatient="getOpenSpoBySmo" hiddenNewSpo="0" service="TicketService" name="moveVisit"  roles="/Policy/Mis/MedCase/Visit/MoveVisitOtherSpo" title="Перевести визит в другой СПО" />
      <tags:pres_newPrescriptList name="Create" parentID="${param.id}" />
      </msh:sideMenu>
      <msh:sideMenu title="Показать">
        <msh:sideLink params="id" action="/entityParentList-vac_vaccination" name="Вакцинации" title="Показать все вакцинации" roles="/Policy/Mis/Vaccination/View" />
         <msh:sideLink roles="/Policy/Mis/MedCase/MedService/View" name="Услуги" params="id" action="/entityParentList-smo_medService" title="Показать все услуги" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewExtMedDocumentByPatient('.do')" name='Внеш. мед. документы' title="Просмотр внеш. мед. документов по пациенту" params="" roles="/Policy/Mis/MedCase/Document/External" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherVisitsByPatient('.do')" name='ВИЗИТЫ' title="Просмотр визитов по пациенту" key="ALT+4" params="" roles="/Policy/Mis/MedCase/Visit/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherDiagnosisByPatient('.do')" name='ДИАГНОЗЫ' title="Просмотр диагнозов по пациенту" key="ALT+5" params="" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherHospitalMedCase('.do')" name='Госпитализации' title="Просмотр госпитазиций по пациенту" key="ALT+6" params="" roles="/Policy/Mis/MedCase/Stac/Ssl/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherPreHospByPatient('.do')" name='Пред госпитализации' title="Просмотр предварительных госпитазиций по пациенту" params="" roles="/Policy/Mis/MedCase/Protocol/Create" />
          <msh:sideLink styleId="viewShort" action="/javascript:viewOtherPreHospOphtByPatient('.do')" name='Введение ингибиторов ангиогенеза' title="Просмотр направлений на введение ингибиторов ангиогенеза" params="" roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Opht/View" />
          <msh:sideLink styleId="viewShort" action="/javascript:viewOtherExtMedserviceByPatient('.do')" name='Внешние лаб. исследования' title="Просмотр внешних лабораторных данных по пациенту" params="" roles="/Policy/Mis/MedCase/Document/External/Medservice/View" />
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/ShowSls,/Policy/Mis/MedCase/Stac/Ssl/SurOper/View" name="Операции"  
    	params="id"  action='/javascript:getDefinition("entityParentList-stac_surOperation.do?short=Short&id=${param.id}", null);'  title='Операции'
    	styleId="viewShort"
    	/>
    	<msh:sideLink styleId="viewShort" action="/javascript:viewAssessmentCardsByVisit('.do')" name="Карты оценки"  title="Показать все карты оценки" roles="/Policy/Mis/AssessmentCard/View"/>
       <msh:sideLink styleId="viewShort" roles="/Policy/Mis/MedCase/QualityEstimationCard/View" name="Экспертные карты" params="id" action="/javascript:getDefinition('entityParentList-expert_card.do?short=Short&id=${param.id}',null)"/>
          <msh:sideLink roles="/Policy/Mis/MedCase/ActRVK" name="Акт РВК" params="" action="/javascript:showOrCreateAktRvk();" title="Акт РВК"
          />
      </msh:sideMenu>
      <msh:tableNotEmpty name="info_print_list">
      <msh:sideMenu title="Печать">
        <msh:sideLink action="/javascript:printVisit('.do')" name="Визита" title="Печать визита" key="ALT+8" params="" roles="/Policy/Mis/MedCase/Visit/PrintVisit" />
        <msh:sideLink params="id" action="/print-talon_amb.do?s=VisitPrintService&amp;m=printTalon" key="ALT+9" name="Талона" title="Печать талона" roles="/Policy/Mis/MedCase/Visit/PrintTalon" />
        <msh:sideLink params="id" action="/print-talon_amb_short.do?s=VisitPrintService&amp;m=printTalon"  name="Укороченного талона" title="Печать короткой версии талона" roles="/Policy/Mis/MedCase/Visit/PrintShortTalon" />
        <msh:sideLink roles="/Policy/Mis/MedCase/Visit/PrintBakExp" params="id" action="/print-BakExp.do?s=VisitPrintService&amp;m=printBakExp" name="Направления на бак.исследование" />
        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/View"  action="/javascript:printAgree();" key="SHIFT+9" name="Информ. согласие на мед. вмешательство" title="Печать информационного согласия"/>
        <tags:diary_parameterCreate document="документа" roles="/Policy/Mis/MedCase/Document/Internal/Create" action="doc_create_type.do?id=${param.id}" name="type" title="Документа" vocName="documentType" />
      </msh:sideMenu>
      </msh:tableNotEmpty>
    <tags:contract_getAccount name="VISIT"/>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <script type="text/javascript" src="./dwr/interface/TicketService.js"></script>
  <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
  <script type="text/javascript">

  function printAgree() {
  	window.location = "print-agreement.do?s=PatientPrintService&m=printAgreement&id="+$('patient').value;
  }
  /* Формируем блок с информаций об оплаченных услугах*/
  function showSimpleService() {
      TicketService.showSimpleServiceBySpecialist($('workFunctionExecute').value, {
         callback: function (serviceList) {
             serviceList = JSON.parse(serviceList);
             if (serviceList.length>0) {
                 var d = jQuery('#simpleServiceDiv');
                 var dd = jQuery('#simpleServiceDataDiv');
                 var txt = "";
                 for (var i = 0; i < serviceList.length; i++) {
                     var s = serviceList[i];
                     txt += "<label style='"+(s.isdefault=="true" ? "font-weight: bold;" : "")+"'><input onclick='this.parentNode.style.fontWeight= (this.checked? \"bold\": \"\")' type='checkbox'" +(s.isdefault=="true" ? "checked" : "") + " id='" + s.serviceid + "' name='chkSimpleService'"
                         + "/>" + s.servicename + "</label><br>";
                 }
                 dd.html(txt);
                 d.dialog({
                     modal: true,
                     position: ['80','40'],
                     width: '50%',
                     top: '10%',
                     left: '20%',
                     closeOnEscape: true,
                     buttons: [
                         {
                             text: "Назначить",
                             click: function () {
                                 jQuery('[name="chkSimpleService"]').each(function (ind, el) {
                                     if (el.checked) {
                                         theOtmoa_medServices.addRow(el.id, jQuery(el).parent().text());
                                     }
                                 });
                                 //Сохраняем все
                                 jQuery(this).dialog("close");
                             }
                         },
                         {
                             text: "Отмена",
                             click: function () {
                                 jQuery(this).dialog("close");
                             }
                         }
                     ]
                 });
                 d.addClass("centerModalDiv");
             } else {
                 alert('Простые услуги по специальности не настроены');
             }
         }
      });

  }
  //создание направления на введение ингибитора ангиогенеза
  // (фактически, создание предв. госпитализации в офтальмологию с доп параметрами)
  function createPlanOphtHospital() {
      window.document.location='entityParentPrepareCreate-stac_planOphtHospitalByVisit.do?id='+$('id').value;
  }
  </script>
  <msh:ifFormTypeIsNotView formName="smo_visitForm">
  
  	<script type="text/javascript">
        function showExternalDocument(aPath) {

            window.open("/docmis"+aPath,null,null);

        }
  	if ($('parent')&&($('parent').value==null||$('parent').value==''||+$('parent').value==0)&&$('btnClosedSPO')){
  		$('btnClosedSPO').style.display="block";
  	}
  	var oldValue=$('dateStart').value;
  	
  	updateService() ;
  	serviceStreamAutocomplete.addOnChangeCallback(function() {updateService();});
  	eventutil.addEventListener($('dateStart'),'blur',function(){
  		if (oldValue!=$('dateStart').value) {
            updateService();
        }
  	}) ;
  	function updateService() { //обновляем родителя для справочника услуг
		var wf = +$("workFunctionExecute").value;
   		if (wf==0) {wf=+$("workFunctionPlan").value;}
   		if (wf=='') {wf=0;}
  		if (theOtmoa_medServices) theOtmoa_medServices.setParentId(wf+"#"+$("dateStart").value + "#" + $('serviceStream').value) ;
  	}
  	var frm = document.forms[0] ;
  	frm.action='javascript:checkVisit()' ;
  	//alert($("workFunctionExecute").value) ;
  	if (+$('parent').value=='0') {
		TicketService.getOpenSpoByPatient((+$("workFunctionPlan").value),$('patient').value,{
			callback: function(aResult) {
				try {
					if (aResult!="") {
	    				var val = aResult.split("@") ;
	    				$('parent').value = val[0];
	    				$('parentName').value= val[1];
					} else {
	    				$('parent').value = '';
	    				$('parentName').value= '';
					}
				} catch(e) {}
			}
		}) ;
  	}
  	
  	
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
          function goCreateAssessmentCard() {
  	  		window.location.href = "entityParentPrepareCreate-mis_assessmentCard.do?id="+$('patient').value+"&visit="+${param.id};
  			$('isPrintInfo').checked='checked' ;
      }
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
  function viewOtherPreHospByPatient(d) {
      getDefinition("js-smo_planHospitalByVisit-allByPatient.do?short=Short&patient="+$('patient').value, null);
  }
  function viewOtherPreHospOphtByPatient(d) {
      getDefinition("js-smo_planHospitalByVisit-allByPatientOphtVis.do?short=Short&vis="+$('id').value, null);
  }
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
  function viewAssessmentCardsByVisit(d) {
	  getDefinition("js-mis_assessmentCard-listByVisit.do?short=Short&patient="+$('patient').value+"&id="+${param.id}, null);
  }
  </script>

  <msh:ifFormTypeIsView formName="smo_visitForm">
  	<msh:ifNotInRole roles="/Policy/Mis/MedCase/Visit/DisableCheck">
  	<script type="text/javascript">
  		var isDiag = 0 ,isDiary = 0, isPrint = 0 ; 
  	</script>
    <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
        <msh:ifInRole roles="/Policy/Mis/MedCase/ActRVK">
            <script type="text/javascript">
                function showOrCreateAktRvk() {
                    PatientService.getIfRVKAlreadyExists(${param.id}, {
                        callback: function (aResult) {
                            if (aResult != '{}') {
                                if (confirm('У пациента уже есть акт РВК в этом СПО. Перейти к нему?')) {
                                    var res = JSON.parse(aResult);
                                    if (res.dtype=='Visit')
                                        window.location.href = 'entityView-rvk_aktVisit.do?id=' +res.id;
                                    else if (res.dtype=='DepartmentMedCase')
                                        window.location.href = 'entityView-rvk_aktSlo.do?id=' +res.id;
                                    else
                                        showToastMessage('Тип medcase в акте РВК некорректный! Обратитесь к администратору',null,true,true);
                                }
                            }
                            else
                                window.location.href = 'entityParentPrepareCreate-rvk_aktVisit.do?id=' + ${param.id};
                        }
                    });
                }
            </script>
        </msh:ifInRole>
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
        <msh:ifInRole roles="/Policy/Mis/MedCase/ActRVK">
            function goToRVK(id) {
            window.location.href='entityParentPrepareCreate-rvk_aktVisit.do?id='+id;
            }
        </msh:ifInRole>
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
  									}
  								}
  							);
  					} else {
  						PatientService.setAddParamByMedCase(url[3],'${param.id}',1,
  								{callback: function(aReturn) {
  									goPriem(index) ;
  								}}
  							);	  						
  					}
  				}
  			}
	  		if (next) goPriem(index) ;
	  	}
	  	goPriem(0) ;
	  </script>
  	</msh:ifNotInRole>
  </msh:ifFormTypeIsView>
      <script type="text/javascript">
          //вывести восклицательные знаки, показывающие, что к услуге есть примечание
          function otmoaOnCnahge() {
              for (var i=0; i<100; i++) { //id при удалении будут идти не по порядку, но вряд ли больше 100
                  if(document.getElementById('otma_input_'+(i+1))) {
                      var tableRow=document.getElementById('otma_input_'+(i+1)).parentNode.parentNode;
                      if (tableRow.childNodes.length<4) {
                          var td = document.createElement('td');
                          td.innerHTML="<img src='/skin/images/main/znak.png' alt='Примечание' hidden height='20' width='20' id='otma_input_cmnt"+(i+1)+"' onclick='showTitle(this);'>";
                          tableRow.insertBefore(td,tableRow.childNodes[0]);
                      }
                  }
              }
          }
          //показать комментарий по клику на знак
          function showTitle(label) {
              showToastMessage(label.title,false,null);
          }
          //загрузка комментариев
          function loadComments() {
              TicketService.getServiceComments(
                  '${param.id}', {
                      callback: function (res) {
                          if (res!=null && res!='[]') {
                              <msh:ifFormTypeIsNotView formName="smo_visitForm">
                              var otmoaMas = document.getElementsByClassName('autocomplete maxHorizontalSize');
                              </msh:ifFormTypeIsNotView>
                              <msh:ifFormTypeIsView formName="smo_visitForm">
                              var otmoaMas = document.getElementsByTagName('a');
                              </msh:ifFormTypeIsView>

                              var aResult = JSON.parse(res);
                              for (var j=0; j<otmoaMas.length; j++) {
                                  var flag=true;
                                  <msh:ifFormTypeIsView formName="smo_visitForm">
                                  flag=otmoaMas[j].href.indexOf('entityView-mis_medService.do')!=-1;
                                  </msh:ifFormTypeIsView>
                                  if (flag) {
                                      for (var i = 0; i < aResult.length; i++) {
                                          if (typeof aResult[i].cmnt!=='undefined') {
                                              <msh:ifFormTypeIsNotView formName="smo_visitForm">
                                              var val = otmoaMas[j].value;
                                              </msh:ifFormTypeIsNotView>
                                              <msh:ifFormTypeIsView formName="smo_visitForm">
                                              var val = otmoaMas[j].text;
                                              </msh:ifFormTypeIsView>
                                              if (otmoaMas[j].id.indexOf('otma_input_') != -1 && val == aResult[i].name) {
                                                  document.getElementById('otma_input_cmnt' + otmoaMas[j].id.replace('otma_input_', '')).title = aResult[i].cmnt;
                                                  document.getElementById('otma_input_cmnt' + otmoaMas[j].id.replace('otma_input_', '')).removeAttribute('hidden');
                                              }
                                          }
                                      }
                                  }
                              }
                          }
                      }
                  }
              );
          }

          function getMedcaseCost() {
              Expert2Service.getMedcaseCost(${param.id},{
                  callback:function(js) {
                      js=JSON.parse(js);
                      if (js.status=='ok') {
                          showToastMessage("Расчетная стоимость случая:<br>Тип расчета:"+js.calcType
                              +"<br>aКСГ="+(js.ksg?js.ksg:'-')+"<br>Цена="+js.price+"<br>Формула расчета: "+(js.formulaCost?js.formulaCost:''));
                      } else {
                          showToastMessage("Ошибка расчета цены:"+js.errorName ? js.errorName :'--');
                      }
                  }
              })
          }

          otmoaOnCnahge();
          loadComments()
      </script>
  </tiles:put>
</tiles:insert>

