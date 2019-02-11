<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoView-smo_ticket.do" defaultField="dateStart" guid="77bf3d00-cfc6-49eb-9751-76e82d38751c">
      <msh:hidden property="id" guid="e862851f-7390-4fe6-9a37-3b22306138b4" />
      <msh:hidden property="saveType" guid="3e3fb7b5-258e-4194-9dbe-5093382cf627" />
      <msh:hidden property="isTalk" guid="34911b70-6c2c-43f2-bbf4-c58fed9a296e" />
      <msh:hidden property="medcard" guid="34911b70-6c2c-43f2-bbf4-c58fed9a296e" />
      <msh:hidden property="dateFinish"/>
      <msh:hidden property="patient" guid="34911b70-6c2c-43f2-bbf4-c58fed9a296e" />

      <msh:panel colsWidth="1%,1%,1%,97%">
        <msh:row guid="fa7ff4e9-4b3d-4402-b046-86283cf7938e">
          <msh:autoComplete viewAction="entityParentView-mis_lpu.do" vocName="mainLpu" property="orderLpu" label="Внешний направитель" guid="cbab0829-c896-4b74-9a68-c9f95676cc3b" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
     
        <msh:row guid="fa7ff4e9-4b3d-4402-b046-86283cf7938e">
        	<msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        	<msh:textField property="externalId" fieldColSpan="1" label="№ бланка МЗ" />
        	</msh:ifInRole>
        	<msh:ifNotInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        	<msh:textField property="externalId" fieldColSpan="1" label="№ направления" />
        	</msh:ifNotInRole> 
           <msh:autoComplete property="categoryChild" fieldColSpan="1" label="Кат. ребенка" horizontalFill="true" vocName="vocCategoryChild" />
        </msh:row>
     
        <msh:row guid="59560d9f-0765-4df0-bfb7-9a90b5eed824">
          <msh:textField label="Дата приема" property="dateStart" fieldColSpan="1" guid="9e3a8e0d-cd82-4158-b764-e15cb16b4fca" />
          <msh:textField label="Время" property="timeExecute" fieldColSpan="1" guid="ed78c5e3-5e2c-4d8c-b64e-75767dcf0775" />
        </msh:row>
        <msh:row>
        	<msh:checkBox label="Неотложная помощь" property="emergency" />
        <msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        	<msh:ifFormTypeIsView formName="smo_ticketForm">
	        	<msh:checkBox label="Беседа с родств." property="isTalk"/>
	        </msh:ifFormTypeIsView>
        </msh:ifInRole>
        </msh:row>
        <msh:row>
        <msh:row>  <msh:checkBox property="isDiagnosticSpo" label="Диагностическое СПО"/></msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель" viewAction="entityParentView-mis_kinsman.do"
        	parentId="smo_ticketForm.medcard" vocName="kinsmanByTicket" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
	        <msh:ifNotInRole roles="/Policy/Poly/Ticket/IsDoctorEdit">
		        <msh:row guid="47073a0b-da87-49e0-9ff0-711dc597ce07">
		          <msh:autoComplete parentId="smo_ticketForm.medcard" vocName="workFunctionByTicket" property="workFunctionExecute" label="Специалист" fieldColSpan="3"  horizontalFill="true" guid="a8404201-1bae-467e-b3e9-5cef63411d01" />
		        </msh:row>
	        </msh:ifNotInRole>
	        <msh:ifInRole roles="/Policy/Poly/Ticket/IsDoctorEdit">
			        <msh:row guid="47073a0b-da87-49e0-9ff0-711dc597ce07">
			          <msh:autoComplete vocName="workFunctionByDoctor" property="workFunctionExecute" label="Специалист" fieldColSpan="3"  horizontalFill="true" guid="a8404201-1bae-467e-b3e9-5cef63411d01" />
			        </msh:row>
	        </msh:ifInRole>
        <msh:row>
          <msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Вид оплаты" horizontalFill="true" guid="e5ac1267-bc69-44b2-8aba-b7455ac064c4" />
          <msh:autoComplete vocName="vocWorkPlaceType" property="workPlaceType" label="Место обслуживания" horizontalFill="true" guid="18063077-15e8-4e4a-8679-ff79de589a72" />
        </msh:row>
        <msh:row>
          <msh:autoComplete viewAction="entityParentView-smo_spo.do" property="parent" 
          	label="СПО" fieldColSpan="3" 
          	horizontalFill="true" vocName="vocOpenedSpoByPatient" parentId="smo_ticketForm.patient" />
        </msh:row>
        
        <msh:row guid="6d8642e8-756a-482f-a561-a9b474ef6c50">
          <msh:autoComplete vocName="vocReason" property="visitReason" label="Цель посещения" horizontalFill="true" guid="3632a2ed-6ecb-446f-8ae3-fe047f091076" />
          <msh:autoComplete vocName="vocVisitResult" property="visitResult" label="Результат обращения" horizontalFill="true" guid="4346bd08-5fe2-4f01-9065-93a66cdfc1dd" />
        </msh:row>
        <msh:row guid="16f1e99-4017-4385-87c1-bf5895e2">
          <msh:autoComplete labelColSpan="3" property="hospitalization" label="Посещение в данном году по данному заболевания" guid="ddc10e76-8ee913984f" vocName="vocHospitalization" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
        	<msh:ifFormTypeIsCreate formName="smo_ticketForm">
	        <msh:row>
	        	<msh:textField property="mkb" label="Код МКБ" />
	        </msh:row>
	        </msh:ifFormTypeIsCreate>
        </msh:ifInRole>
        <msh:row guid="0489132a-531c-47bc-abfc-1528e774bbfe">
          <msh:autoComplete vocName="vocIdc10" property="concludingMkb" label="Код МКБ" fieldColSpan="3" horizontalFill="true" guid="9818fb43-33d1-4fe9-a0b4-2b04a9eee955" />
        </msh:row>
        <msh:row guid="0489132a-531c-47bc-abfc-1528e774bbfe">
          <msh:textField property="concludingDiagnos" label="Диагноз" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocIllnesPrimary" property="concludingActuity" label="Характер заболевания" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row guid="4bd126b5-2316-42c4-bcb7-ccf5108b2c27">
          <msh:autoComplete vocName="vocDispanseryRegistration" property="dispRegistration" label="Диспансерный учет" horizontalFill="true" guid="bf850705-5557-438e-b56e-33d59b1618e4" />
          <msh:autoComplete vocName="vocTraumaType" property="concludingTrauma" label="Травма" horizontalFill="true" guid="eedb1042-1861-426e-a0ec-6151c3933dd1" />
        </msh:row>
          <msh:row>
        <msh:textField property="nextVisitDate" label="Дата следующего визита"/>
          </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MisLpu/Ambulance">
	        <msh:row>
	        	<msh:textField property="ambulanceCard" fieldColSpan="3" label="№карты СП" horizontalFill="true"/>
	        </msh:row>
	        <msh:row>
	        	<msh:autoComplete vocName="vocAmbulance" property="ambulance" label="Бригада СП" horizontalFill="true" />
	        	<msh:autoComplete vocName="vocVisitOutcome" property="visitOutcome" label="Исход СП" horizontalFill="true" />
	        </msh:row>
	        <msh:row>
	        	<msh:textField property="callReceiveTime" label="Время получения вызова СМП"/>
	        	<msh:textField property="arrivalTime" label="Время прибытия бригады до места назначения"/>
	        </msh:row>
        </msh:ifInRole>
        <msh:row>
        <msh:ifFormTypeIsNotView formName="smo_ticketForm">
        	<msh:checkBox property="isCloseSpo" label="Закрыть СПО" />
        </msh:ifFormTypeIsNotView>
        	<msh:checkBox fieldColSpan="2" property="isDirectHospital" label="Направлен на стационарное лечение" horizontalFill="true"/>
        </msh:row>
        <msh:row guid="7dfb3b2c-407d-48f1-9e70-76cb3328f5f5">
        	<msh:autoComplete property="mkbAdc" vocName="vocMkbAdc" parentAutocomplete="concludingMkb" label="Доп.код"/>
        </msh:row>
        <msh:row guid="1283d16a-e417-4add-acf5-5185dbb7737d">
          <ecom:oneToManyOneAutocomplete vocName="vocIdc10" label="Соп. заболевания" property="concomitantDiseases" colSpan="6" guid="1204d6c4-a3ff-44aa-a698-b99816d10337" />
        </msh:row>
        </msh:panel><msh:panel>
       <msh:ifFormTypeIsNotView formName="smo_ticketForm">
       
       <msh:row>
       <msh:separator label="Мед.услуги" colSpan="7"/>
       </msh:row>
        <msh:row>
        <msh:hidden property="medServices"/>
	   	<msh:autoComplete viewAction="entityView-mis_medService.do" label="Мед. услуги" property="medService" vocName="medServiceForSpec" size="45"/>
        	<msh:textField property="uetT" label="УЕТ" size="4"/>
        	<msh:textField property="orderNumber" label="№зуба" size="1"/>
            <msh:textField property="medServiceAmount" label="Кол-во" size="2" />
        
        	<td><input type="button" value="+ услугу" onclick="addService()"/></td>
        </msh:row>
        <msh:row>
        <table id="otherMedServices" border="1px solid">
        	
        </table>
        </msh:row>
       
       </msh:ifFormTypeIsNotView>
        <msh:ifFormTypeAreViewOrEdit formName="smo_ticketForm">
        <msh:separator label="Выдан талон" colSpan="4" guid="d9a7ec35-7893-48b3-aa08-f2e04d9a9400" />
        <msh:row>
        	<msh:textField label="Дата" property="createDate" fieldColSpan="1" viewOnlyField="true"/>
        	<msh:textField label="Время" property="createTime" fieldColSpan="1" viewOnlyField="true"/>
        </msh:row>
        
        <msh:separator label="Редакция талона" colSpan="4" guid="d9a7ec35-7893-48b3-aa08-f2e04d9a9400" />
        <msh:row>
        	<msh:textField label="Пользователь" property="editUsername" fieldColSpan="1" viewOnlyField="true"/>
        	<msh:textField label="Дата" property="editDate" fieldColSpan="1" viewOnlyField="true"/>
        </msh:row>
        
        </msh:ifFormTypeAreViewOrEdit>
	    <msh:row>
        	<msh:textField label="Пользователь" property="username" viewOnlyField="true" />
        	<msh:checkBox label="Недействующий талон" property="noActuality"/>
        </msh:row>
	    <msh:submitCancelButtonsRow colSpan="3" guid="13aa4bce-1133-48d6-896b-eb588a046d59" />


	    
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="smo_ticketForm">
          	<msh:section title="Услуги">
      		<ecom:webQuery name="services" nativeSql="select mc.id,ms.name
      		,mc.uet,mc.orderNumber
      		,mc.medServiceAmount
      		from MedCase mc 
      		left join MedService ms on mc.medService_id=ms.id
      		where mc.parent_id='${param.id}' and mc.dtype='ServiceMedCase'
      		"/>
      		<msh:table name="services" action="javascript:void(0)" 
      	 	  idField="1" >
      			<msh:tableColumn columnName="Название услуги" property="2"/>
      			<msh:tableColumn columnName="Ует" property="3"/>
      			<msh:tableColumn columnName="№зуба" property="4"/>
      			<msh:tableColumn columnName="Кол-во" property="5"/>
      		</msh:table>
      	</msh:section>
    
    <msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
    	<msh:section>
    		<msh:sectionTitle>Талоны беседы с родственниками <msh:link action="/js-smo_ticket-addTalk.do?id=${param.id}" roles="/Policy/Poly/Ticket/CreateTalk">добавить</msh:link> </msh:sectionTitle>
    		<msh:sectionContent>
    			<ecom:webQuery name="ticketTalk" nativeSql="select t1.id,t1.isTalk 
    			from MedCase t1 
    			left join MedCase t2 on t1.medcard_id=t2.medcard_id
    			 and t1.dateStart=t2.dateStart 
    			where t1.dtype='ShortMedCase' and t2.dtype='ShortMedCase' and t2.id='${param.id}'
    			 and t1.workFunctionExecute_id=t2.workFunctionExecute_id
    			   and t1.isTalk='1'"/>
    			<msh:table deleteUrl="entityParentDeleteGoParentView-smo_ticket.do" viewUrl="entityParentView-smo_ticket.do?short=Short" name="ticketTalk" action="entityParentView-smo_ticket.do" idField="1">
    				<msh:tableColumn property="sn" columnName="#"/>
    				<msh:tableColumn property="1" columnName="ИД талона"/>
    				<msh:tableColumn property="2" columnName="talk"/>
    			</msh:table>
    		</msh:sectionContent>
    	</msh:section>
    	<msh:ifInRole roles="/Policy/Mis/Psychiatry/CareCard/View">
    	<msh:section>
    		<msh:sectionTitle>Карта обратившегося за психиатрической помощью</msh:sectionTitle>
    		<msh:sectionContent>
	    		<ecom:webQuery name="careCard" nativeSql="select cc.id,cc.cardNumber from PsychiatricCareCard cc left join Ticket t on t.id=${param.id} left join Medcard m on m.id=t.medcard_id where cc.patient_id=m.person_id"/>
	    		<msh:table name="careCard" action="entityParentView-psych_careCard.do" idField="1">
	    			<msh:tableColumn property="sn" columnName="#"/>
	    			<msh:tableColumn property="2" columnName="№"/>
	    		</msh:table>
    		</msh:sectionContent>
    	</msh:section>
    	</msh:ifInRole>
    </msh:ifInRole>
    </msh:ifFormTypeIsView>
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
    <msh:ifInRole roles="/Policy/Poly/PrescriptionBlank/View">
    <msh:ifFormTypeIsView formName="smo_ticketForm" guid="e2bc3fba-21d3-49fd-95ba-4b65d5dc2eaf">
      <msh:section guid="f58220d8-bf90-4099-a5c8-cb51ad9937fb">
        <msh:sectionTitle guid="cd08cdbe-22e2-4ae5-9734-0d1dea83306a">Рецептурные бланки</msh:sectionTitle>
        <msh:sectionContent guid="6d850709-2323-43da-8cb9-56b6cba18cc8">
          <ecom:parentEntityListAll formName="poly_prescriptionBlankForm" attribute="blanks" guid="3114a75b-0027-4e7d-845d-710be7ef97e2" />
          <msh:table name="blanks" action="entityParentView-poly_prescriptionBlank.do" idField="id" guid="cfb57244-ac75-4062-888a-346a3aa301c7">
            <msh:tableColumn columnName="Дата выдачи" property="writingOutDate" guid="f4c3707a-c480-4b80-9556-e690a2644fbe" />
            <msh:tableColumn columnName="Серия" property="series" guid="1556c5dd-bd80-43d3-90ac-d6cbdffbbbc7" />
            <msh:tableColumn columnName="Номер" property="number" guid="7e292520-fb32-40c2-ad1d-62388e440bc8" />
          </msh:table>
        </msh:sectionContent>
      </msh:section>
    </msh:ifFormTypeIsView>
    </msh:ifInRole>
    <msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/View">
    <msh:ifFormTypeIsView formName="smo_ticketForm" guid="35116e3f-c2be-453e-82a9-188919feffeb">
      <msh:section guid="d1ffa344-d0a3-44f5-bc17-9df25e81577a">
        <msh:sectionTitle guid="fe1105d3-8dc3-4adc-a89f-1af1b8f184b3">Протоколы</msh:sectionTitle>
        <msh:sectionContent guid="f3a17575-c8c3-4322-99be-8b73ac86ce6e">
          <ecom:webQuery name="protocols" 
          nativeSql="select d.id,to_char(d.dateRegistration,'dd.mm.yyyy')||' '||cast(d.timeRegistration as varchar(5)) || ' '||vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
          ,d.record as drecord
           from diary d
           left join WorkFunction wf on wf.id=d.specialist_id
           left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
           left join Worker w on w.id=wf.worker_id
           left join Patient wp on wp.id=w.person_id
            where d.medcase_id='${param.id}' and d.dtype='Protocol'
            order by d.dateRegistration, d.timeRegistration
            " 
            />
          <msh:table name="protocols" action="entityParentView-smo_visitProtocol.do" idField="1">
            <msh:tableColumn columnName="Текст" property="3" guid="d10f2929-fb-9ea4-636bcec18f1e" />
            <msh:tableColumn columnName="Специалист" property="2" guid="d10a4-636bcec18f1e" />
          </msh:table>
        </msh:sectionContent>
      </msh:section>
    </msh:ifFormTypeIsView>
    </msh:ifInRole>
          <msh:ifInRole roles="/Policy/Mis/MedCase/Document/Internal/View">
      	<msh:section title="Документы">
      		<ecom:webQuery name="docum" nativeSql="select d.id as did,d.history
      		, case when d.dtype='DirectionDocument' then 'Направление' 
      		when d.dtype='DischargeDocument' then 'Выписка'
      		when d.dtype='DischargeDiagnostDocument' then 'Выписка диагностическая'
      		when d.dtype='BaseMedicalExamination' then 'Паспорт здоровья'
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
    
    <tags:mis_double name='Ticket' title='Существующие талоны в базе:' cmdAdd="document.forms[0].submitButton.disabled = false"/>
  </tiles:put>
  <tiles:put name="side" type="string">
  		<msh:sideMenu title="Дополнительно">
	        <msh:sideLink action="/javascript:viewProtocolByMedcard(1,'.do')" name='Заключения диаг. служб<img src="/skin/images/main/view1.png" alt="Просмотр записи" title="Просмотр записи" height="16" width="16">' title="Просмотр визитов по пациенту" key="ALT+4" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Protocol/Create" />
	        <msh:sideLink action="/javascript:viewProtocolByMedcard(0,'.do')" name='Заключения<img src="/skin/images/main/view1.png" alt="Просмотр записи" title="Просмотр записи" height="16" width="16">' title="Просмотр визитов по пациенту" key="ALT+4" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Protocol/Create" />
	        <msh:sideLink action="/javascript:infoDiagByMedcard('.do')" name='Диагнозы<img src="/skin/images/main/view1.png" alt="Просмотр записи" title="Просмотр записи" height="16" width="16">' title="Просмотр визитов по пациенту" key="ALT+4" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Protocol/Create" />
  		</msh:sideMenu>
    <msh:ifFormTypeIsView formName="smo_ticketForm" guid="8f-4d80-856b-ce3095ca1d">
      <msh:sideMenu guid="e6c81315-888f-4d80-856b-ce3095ca1d55" title="Талон" >
        <msh:sideLink roles="/Policy/Poly/Ticket/Edit" key="ALT+2" params="id" action="/entityEdit-smo_ticket" name="Изменить" guid="89585df8-aadb-4d59-abd9-c0d16a6170a9" title="Изменить талон" />
		<msh:sideLink params="id" action="/js-smo_visit-closeSpo" 
		name="Закрыть СПО" title="Закрыть СПО" confirm="Закрыть СПО?" 
			key="ALT+4" roles="/Policy/Poly/Ticket/Edit" />        
			<msh:sideLink roles="/Policy/Poly/Ticket/CreateTalk" key="ALT+4" params="id" action="/js-smo_ticket-addTalk" name="Беседа с родственниками" guid="661fe852-e096-410a-9fab-86d8e75db177" title="Беседа с родственниками" />
        <msh:sideLink roles="/Policy/Poly/Ticket/Edit,/Policy/Poly/Ticket/TalkDelete,/Policy/Mis/MisLpu/Psychiatry" key="ALT+4" params="id" action="/js-smo_ticket-doNotAddTalk" name="Сделать обычным посещением" guid="661fe852-e096-410a-9fab-86d8e75db177" title="Беседа с родственниками" />
        
        <msh:ifFormTypeAreViewOrEdit formName="smo_ticketForm" guid="7f581b0a-a8b3-4d57-9cff-6dc6db1c85e3">
          <msh:sideLink roles="/Policy/Poly/Ticket/Delete" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-smo_ticket" name="Удалить" confirm="Вы действительно хотите удалить талон?" guid="8b9de89f-3b99-414e-b4af-778ccbb70edf" title="Удалить талон" />
        </msh:ifFormTypeAreViewOrEdit>
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="3d94cf79-f341-469e-863e-5e28bd16aabe">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-smo_planHospitalByVisit" name="Предварительную госпитализацию" title="Добавить планирование госпитализации" guid="2209b5f9-4b4f-4ed5-b825-b66f2ac57e87" roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Create" key="ALT+7" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-doc_discharge" name="Выписку" title="Добавить выписку" roles="/Policy/Mis/MedCase/Document/Internal/Discharge/Create" />
        <msh:sideLink roles="/Policy/Poly/PrescriptionBlank/Create" key="CTRL+2" params="id" action="/entityParentPrepareCreate-poly_prescriptionBlank" name="Рецептурный бланк" guid="09e47fdd-298c-4230-9916-2b9a15abee56" title="Добавить рецептурный бланк" />
        <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Create" key="CTRL+3" params="id" action="/entityParentPrepareCreate-smo_visitProtocol" name="Заключение" guid="b5ae64d7-16da-4307-998b-9214fa4a600f" title="Добавить протокол" />
        <msh:sideLink roles="/Policy/Poly/Ticket/Create" key="CTRL+4" 
         action="/javascript:window.location='entityParentPrepareCreate-smo_ticket.do?id='+$('medcard').value" name="Талон на пациента"
         title="Добавить талон" />
        <msh:sideLink roles="/Policy/Poly/Ticket/Create"
         action="/javascript:window.location='entityParentPrepareCreate-smo_ticket.do?id='+$('medcard').value+'&prevTicket='+$('id').value" name="Талон на основе текущего"
         title="Добавить талон пациента на основе текущего" />
       <msh:sideLink styleId="viewShort"  action="/javascript:getDefinition('entityParentList-expert_ker.do?short=Short&id=${param.id}',null)" name='Врачеб. комиссии' title="Просмотр врачебных комиссий" guid="2156670f-b32c-4634-942b-2f8a4467567c" roles="/Policy/Mis/MedCase/ClinicExpertCard/View" />
      </msh:sideMenu>
      <msh:sideMenu title="Администрирование">
	   	<tags:mis_changeServiceStream service="TicketService" name="CSS" title="Изменить поток обслуживания" roles="/Policy/Poly/Ticket/ChangeServiceStream" />
      	<tags:mis_choiceSpo method="moveVisitOtherSpo" methodGetPatientByPatient="getOpenSpoBySmo" hiddenNewSpo="0" service="TicketService" name="moveVisit"  roles="/Policy/Poly/Ticket/MoveVisitOtherSpo" title="Перевести визит в другой СПО" />
      </msh:sideMenu>      
      <msh:sideMenu title="Печать" guid="62fd4ce0-85b5-4661-87b2-fea2d4fb7339">
        <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/View" key="SHIFT+8" params="id" 
	        action="/print-visit.do?s=VisitPrintService&amp;m=printVisit" name="Талона с заключением" guid="97e65138-f936-45d0-ac70-05e1ec87866c" title="Печатать талона с заключением" />
	                <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/View" 
    	name="Печать справки" 
    	action='.javascript:printReference(".do")' title='Печать справки'
    	/>
        <msh:sideLink roles="/Policy/Poly/Ticket/View" key="SHIFT+8" params="id" action="/print-ticket.do?s=PrintTicketService&amp;m=printInfo" name="Талона" guid="97e65138-f936-45d0-ac70-05e1ec87866c" title="Печатать талона" />

        <msh:sideLink roles="/Policy/Poly/Ticket/BakExp" params="id" action="/print-BakExp.do?s=PrintTicketService&amp;m=printBakExp" name="Направления на бак.исследование" guid="5138-f936-45d0-ac70-066c" key="SHIFT+9" title="Печатать направления на бак.исследование" />
        
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
        <tags:ticket_finds currentAction="ticket"/>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Medcard" beginForm="smo_ticketForm" guid="5c4f3682-e66b-4e0d-b448-4e6a2961a943" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript" src="./dwr/interface/TicketService.js"></script>
    
      	<msh:ifFormTypeIsNotView formName="smo_ticketForm">
      	<script type="text/javascript"> 
      	onload=function(){
      		if (+$('medServiceAmount').value<1) $('medServiceAmount').value="1";
         	if ($('medServices').value!=null&&$('medServices').value!='') {
        		var arr = $('medServices').value.split("##");
        		for (var i=0;i<arr.length;i++) {
        			var ar=arr[i].split("@") ;
                    addRow ("<b>"+ar[4]+"</b> ует: <input type='text' value='"+(ar[1]!='null'?ar[1]:"")+"' size='4'> №зуба: <input type='text' value='"+ar[2]+"' size='2'> кол-во: </i><input type='text' value='"+ar[3]+"' size='1'>",ar[0]);
        		}
        	}
        }

        
   
        function addService(check) {
            var service = $('medService').value;
            var serviceName = $('medServiceName').value;
            var medServiceAmount=$('medServiceAmount').value;
	  		var uetT=$('uetT').value;
  		    var orderNumber=$('orderNumber').value ;
            if (+service>0) {
            	var servs = document.getElementById('otherMedServices').childNodes;
                  var l = servs.length;
                  for (var i=1; i<l;i++) {
                	 if (servs[i].childNodes[0].childNodes[0].value==service && 
                			 servs[i].childNodes[0].childNodes[5].value==orderNumber) {
                 		 if (+check!=1) {
                 			 if (confirm("Такая услуга уже есть в талоне. Вы хотите ее заменить?")) {
                 			var node=servs[i];node.parentNode.removeChild(node);
                 		 } else {
                  			return;
                  		 } 
                 		} else {return;}
                    }                 
                 }
                 addRow ("<b>"+serviceName+"</b> ует: <input type='text' value='"+uetT+"' size='4'> №зуба: <input type='text' value='"+orderNumber+"' size='2'> кол-во: </i><input type='text' value='"+medServiceAmount+"' size='1'>",service);
                 $('medService').value="";
                 $('medServiceName').value="";
                 $('medServiceAmount').value="1";
     	  		 $('uetT').value="";
       		   //  $('orderNumber').value="" ;
            }
         }
        
      	function createOtherMedServices() {
      		var servs = document.getElementById('otherMedServices').childNodes;
      		var str = ""; $('medServices').value='';
      		for (var i=1;i<servs.length;i++) {
      			str+=servs[i].childNodes[0].childNodes[0].value
      			+"@"+servs[i].childNodes[0].childNodes[3].value
      			+"@"+servs[i].childNodes[0].childNodes[5].value
      			+"@"+servs[i].childNodes[0].childNodes[7].value
      			+"##";
      		}
      		str=str.length>0?str.trim().substring(0,str.length-2):"";
      		$('medServices').value=str;
      	}
      	function addRow (aValue,aFld) {
      		var table = document.getElementById('otherMedServices');
      		var row = document.createElement('TR');
      		var td = document.createElement('TD');
      		var tdDel = document.createElement('TD');
      		table.appendChild(row);
      		row.appendChild(td);
      		td.innerHTML="<input type='hidden' value='"+aFld+"'>"+aValue;
      		row.appendChild(tdDel);
      		tdDel.innerHTML = "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;node.parentNode.removeChild(node);createOtherMedServices()' value='- услугу' />";
      		createOtherMedServices();
      	}
    	
      	function checkCrossSPO () {
    		TicketService.getCrossSPO($('dateStart').value,$('patient').value,$('workFunctionExecute').value,{
    			callback: function(aResult) {
					if (aResult!=null&&aResult!='') {
						var arr = aResult.split(':');
						if ($('parent')!=null&&$('parent').value!=arr[0]) {
							if (confirm('Случай пересекается с закрытым СПО (Период с '+arr[1]+' по '+arr[2]+'). Продолжить?')) {
								checkIsHoliday();
							} else {
	  							document.getElementById('submitButton').disabled=false;
	  							document.getElementById('submitButton').value='Создать';
							}
						} else {
							checkIsHoliday();
						}
					} else {
						checkIsHoliday();
					}
					
    			}
    		});
    	}
      	
</script>

      	<msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
  			<script type="text/javascript">
  			
  			
  			eventutil.addEventListener($('mkb'), 'change', function(){
  				if ($('mkb'.value!='')) {
  				TicketService.findMkbByCode($('mkb').value,{
	      	 		callback: function(aResult) {
	      	 			var ind = aResult.indexOf('#') ;
	      	 			if (ind!=-1) {
	      	 				$('concludingMkb').value=aResult.substring(0,ind) ;
	      	 				$('concludingMkbName').value=aResult.substring(ind+1) ;
	      	 			} else {
	      	 				$('concludingMkb').value="" ;
	      	 				$('concludingMkbName').value=$('mkb').value ;
	      	 			}
	      	 			setDiagnosisText('concludingMkb','concludingDiagnos') ;
	      	    		if (($('concludingMkbName').value!='') &&($('concludingMkbName').value.substring(0,1)=='Z')) {
	      		      	 	TicketService.findProvReason($('visitReason').value,{
	      		      	 		callback: function(aResult) {
	      		      	 			var ind = aResult.indexOf('#') ;
	      		      	 			if (ind!=-1) {
	      		      	 				$('visitReason').value=aResult.substring(0,ind) ;
	      		      	 				$('visitReasonName').value=aResult.substring(ind+1) ;
	      		      	 			}
	      		      	 		}
	      		      	 	}) ;
	      		      	 }
	      	 		}
	      	 	}) ;
  				
  				} else {
  	 				
  				}
  			}) ;
	      	 	
  			</script>
      	
      	</msh:ifInRole>
  		<msh:ifFormTypeAreViewOrEdit formName="smo_ticketForm">
  			<script type="text/javascript">
  				if ($('dateStart').value=="") $('dateStart').value=$('dateFinish').value 
  			</script>
  		</msh:ifFormTypeAreViewOrEdit>
  	</msh:ifFormTypeIsNotView>
    <script type="text/javascript">
    function viewProtocolByMedcard(d) {
    	var m = document.forms[0].medcard ;
  	  getDefinition("js-smo_visitProtocol-infoByMedcardShort.do?diag="+d+"&id="+m.value, null); 
    }    
    function infoDiagByMedcard(d) {
    	var m = document.forms[0].medcard ;
  	  getDefinition("js-smo_ticket-infoDiagByMedcard.do?id="+m.value, null); 
    }    
    </script>
  	<msh:ifInRole roles="/Policy/Poly/Ticket/IsDoctorEdit">
  <msh:ifFormTypeIsCreate formName="smo_ticketForm">
  <script type="text/javascript">
  
  TicketService.getWorkFunction(
    		 {
                   callback: function(aResult) {
                      //$('workFunction').value=aResult ;
                      workFunctionExecuteAutocomplete.setParentId(aResult) ;
                      //workFunctionExecuteAutocomplete.setVocId(aResult) ;                   }
	        	}}
	        	);
  </script>
  </msh:ifFormTypeIsCreate>
  <msh:ifFormTypeIsNotView formName="smo_ticketForm">
  <msh:ifInRole roles="/Policy/Mis/MisLpu/Ambulance">
  <script type="text/javascript">
  	try{
  		ambulanceAutocomplete.addOnChangeCallback(function() {
  			if (+$('ambulance').value>0) $('emergency').checked=true;
  		});
  	} catch(e) {
  		
  	}
  </script>
  </msh:ifInRole>
  <script type="text/javascript">
   TicketService.getWorkFunction(
    		 {
                   callback: function(aResult) {
                      workFunctionExecuteAutocomplete.setParentId(aResult) ;
                      //workFunctionAutocomplete.setVocId(aResult) ;
//                      $('workFunction').value=aResult ;
                   }
	        	}
	        	);
  </script>
  </msh:ifFormTypeIsNotView>
  </msh:ifInRole>
  <msh:ifFormTypeAreViewOrEdit formName="smo_ticketForm">
  <script type="text/javascript">
  function printReference() {
		TicketService.getDataByReference(
			'${param.id}','SPO',{
				callback: function(aResult) {
					if (aResult!=null) {
						window.location.href = "print-doc_reference.do?medCase=${param.id}&m=refenceSMO&s=VisitPrintService"+aResult;
						
					}
				}, errorHandler: function(aMessage) {
					if (aMessage!=null) {
						alert(aMessage);
					} else {
				    	alert("СПРАВКА РАСПЕЧАТЫВАЕТСЯ ТОЛЬКО ПО ВЫПИСАННЫМ ОМС БОЛЬНЫМ!!!") ;
					}
				}
			
			}
		);
		//print-discharge_reference.do?m=printReference&s=HospitalPrintService
	}
  </script>
  	<msh:ifFormTypeIsNotView formName="smo_ticketForm">
  	 <script type="text/javascript">
  	 
  	
  		

		TicketService.isEditCheck($('id').value, $('workFunctionExecute').value,
			{
				callback: function(aResult) {
					if (+aResult.substring(0,1)==0) {
						alert(aResult.substring(2));
						window.location.href= "entityParentView-smo_ticket.do?id=${param.id}"; 
					}
				}
			}
		);
	    
	 </script>
  	</msh:ifFormTypeIsNotView>
  </msh:ifFormTypeAreViewOrEdit>  
  <msh:ifFormTypeIsNotView formName="smo_ticketForm">
    <script type="text/javascript">
    	
    	var oldaction = document.forms[0].action ;
    	var oldValue = $('dateStart').value ;
    	document.forms[0].action = 'javascript:checkCrossSPO()';
    	concludingMkbAutocomplete.addOnChangeCallback(function() {
    		setDiagnosisText('concludingMkb','concludingDiagnos') ;
    		if (($('concludingMkbName').value!='') &&($('concludingMkbName').value.substring(0,1)=='Z')) {
	      	 	TicketService.findProvReason($('visitReason').value,{
	      	 		callback: function(aResult) {
	      	 			var ind = aResult.indexOf('#') ;
	      	 			if (ind!=-1) {
	      	 				$('visitReason').value=aResult.substring(0,ind) ;
	      	 				$('visitReasonName').value=aResult.substring(ind+1) ;
	      	 			}
	      	 		}
	      	 	}) ;
	      	 }
	    });
      	medServiceAutocomplete.addOnChangeCallback(function() {
    		if (+$('medService').value>0) {
	      	 	TicketService.getUetByService($('medService').value,{
	      	 		callback: function(aResult) {
	      	 			$('uetT').value=aResult ;
	      	 		}
	      	 	}) ;
	      	 }
	    });
      	function setAdditionParam() {
      		var wf = +$("workFunctionExecute").value;
    		medServiceAutocomplete.setParentId(wf+"#"+$("dateStart").value) ;
    		
     		if (wf>0) {
        		TicketService.getOpenSpoByPatient(wf,$('patient').value,{
        			callback: function(aResult) {
        				if (aResult!="") {
            				var val = aResult.split("@") ;
            				$('parent').value = val[0];
            				$('parentName').value= val[1];
        				} else {
            				$('parent').value = '';
            				$('parentName').value= '';
        				}
        				
        			}}) ;
        		} else {
        			$('parent').value = '';$('parentName').value = '';
        		}		
      	}
  		function setDiagnosisText(aFieldMkb,aFieldText) {
  			var val = $(aFieldMkb+'Name').value ;
  			var ind = val.indexOf(' ') ;
  			//alert(ind+' '+val)
  			if (ind!=-1) {
  				$(aFieldText).value=val.substring(ind+1) ;
  			}
  		}
    	if ($('workFunctionExecuteName')) workFunctionExecuteAutocomplete.addOnChangeCallback(function() {
    		setAdditionParam() ;
    		
    	});
	    
	      	eventutil.addEventListener($('dateStart'),'blur',function(){
		  		if (oldValue!=$('dateStart').value) {
		  			var wf = +$("workFunctionExecute").value;
		    		if (wf=='') {wf=0;}
		  			 medServiceAutocomplete.setParentId(wf+"#"+$("dateStart").value) ;
		  		}
		  	}) ;
		function changeParentMedService() {
		}
  		function checkIsHoliday() {
  			var v =$('emergency').checked;  			
  			if (v) {
  				isExistTicket();
  			} else {
	  			TicketService.isHoliday($('dateStart').value,{
	  				callback: function(aResult) {
	  					if (aResult=='1') {
	  						if (confirm("Прием приходится на воскресенье, точно создать талон?")) {
	  							isExistTicket();
		  						} else {
	  							document.getElementById('submitButton').disabled=false;
	  							document.getElementById('submitButton').value='Создать';
	  							return;
	  						}
	  					}  else {
	  						isExistTicket();
	  					}
	  				}
	  			});
  			}
  			
  		}
    	function isExistTicket() {
    		 if ($('dateStart').value!="") {
    		TicketService.findDoubleBySpecAndDate($('id').value,document.forms[0].medcard.value,$('workFunctionExecute').value, $('dateStart').value
    		, {
                   callback: function(aResult) {
                   
                      if (aResult) {
				    	 TicketService.checkCreateDoubleBySpecAndDate({
                    		  callback: function(aRes) {
	                    		  if (aRes) {
	                    			  alert('Уже заведен талон посещения на '+$('dateStart').value+' к данному специалисту  по медкарте №'+document.forms[0].medcard.value) ;
					    			  document.forms[0].submitButton.disabled = false ;
	                    		 } else {
	      				    		  showTicketDouble(aResult) ;
	                    		 }
	                    	   }
                    	  }) ;
                       } else {
                     		TicketService.checkHospitalByMedcard($('dateStart').value,document.forms[0].medcard.value,$('serviceStream').value
                     		  		,{callback: function(aString) {
                     		        	//alert(aString) ;
                     		            if (aString!=null) {
                     		            	alert("Человек находился в больнице "+aString+" по ОМС его оформить за этот период нельзя!!!") ;
                     		            	document.forms[0].submitButton.disabled = false ;
                     		            } else {
                                    	   	TicketService.saveSession($('dateStart').value,$('workFunctionExecute').value
                                    	   			,$('workFunctionExecuteName').value,$('medServices').value,$('emergency').checked, {
                                    	   		callback: function(aResult) {
                                    	    		addService(1) ;
                                    	    		createOtherMedServices() ;
                                    	   			document.forms[0].action = oldaction ;
                        				    		document.forms[0].submit() ;
                                    	   		}
                                    	   	});
                     		             }
                     		         }}) ;
                       		
                       }
                   }
	        	}
	        	); 
    			}else {
	        		
	        	}
    		}
    		function getValue(aFld) {
    			if (aFld) {
    				return aFld.value ;
    			} else {
    				return "none" ;
    			}
    		}
    		 medServiceAutocomplete.setParentId((+$("workFunctionExecute").value)+"#"+$("dateStart").value) ;

    	</script>
    	
  </msh:ifFormTypeIsNotView>
  <msh:ifFormTypeIsCreate formName="smo_ticketForm">
  <msh:ifInRole roles="/Policy/Poly/Ticket/IsDoctorEdit">
  	<script type="text/javascript">
  	if (+$('workFunctionExecute').value<1) {
	  	if (+'${param.prevTicket}'>0) {
	  		TicketService.getInfoByTicket('${param.prevTicket}',{callback:function(aResult){
	  			if (aResult!=null&&aResult!="") {
	  				var val = aResult.split("@") ;
	   	   			if (val[0]!="") $('serviceStream').value = val[0] ;if (val[1]!="") $('serviceStreamName').value = val[1] ;
	   	   			if (val[2]!="") $('workPlaceType').value = val[2] ;if (val[3]!="") $('workPlaceTypeName').value = val[3] ;
	   	   			if (val[4]!="") $('visitReason').value = val[4] ;if (val[5]!="") $('visitReasonName').value = val[5] ;
	   	   			if (val[6]!="") $('visitResult').value = val[6] ;if (val[7]!="") $('visitResultName').value = val[7] ;
	   	   			if (val[8]!="") $('hospitalization').value = val[8] ;if (val[9]!="") $('hospitalizationName').value = val[9] ;
	   	   			if (val[10]!="") $('dispRegistration').value = val[10] ;if (val[11]!="") $('dispRegistrationName').value = val[11] ;
	   	   			//if (val[12]!="") $('serviceStream').value = val[12] ;if (val[13]!="") $('serviceStreamName').value = val[13] ;
	   	   			if (val[12]!=""&&(+val[12]>0)) $('emergency').checked=true;
	   	   			if (val[13]!="") {
	   	   				var diag = val[13].split("##") ;
	   	   				if (diag[0]!="") $('concludingMkb').value = diag[0] ;if (diag[1]!="") $('concludingMkbName').value = diag[1] ;
	   	   				if (diag[2]!="") $('concludingDiagnos').value = diag[2] ;if (diag[3]!="") $('concludingActuity').value = diag[3] ;
	   	   				if (diag[4]!="") $('concludingActuityName').value = diag[4] ;
	   	   			}
	   	   			//if (val[14]!="") $('workFunctionExecute').value = val[14] ;if (val[15]!="") $('workFunctionExecuteName').value = val[15] ;
	   	   			setAdditionParam();
	  			}
	  		}})
	  	}}
</script>
</msh:ifInRole>  
  <msh:ifNotInRole roles="/Policy/Poly/Ticket/IsDoctorEdit">
  	<script type="text/javascript">
  	if (+$('workFunctionExecute').value<1) {
	  	if (+'${param.prevTicket}'>0) {
	  		TicketService.getInfoByTicket('${param.prevTicket}',{callback:function(aResult){
	  			if (aResult!=null&&aResult!="") {
	  				var val = aResult.split("@") ;
	   	   			if (val[0]!="") $('serviceStream').value = val[0] ;if (val[1]!="") $('serviceStreamName').value = val[1] ;
	   	   			if (val[2]!="") $('workPlaceType').value = val[2] ;if (val[3]!="") $('workPlaceTypeName').value = val[3] ;
	   	   			if (val[4]!="") $('visitReason').value = val[4] ;if (val[5]!="") $('visitReasonName').value = val[5] ;
	   	   			if (val[6]!="") $('visitResult').value = val[6] ;if (val[7]!="") $('visitResultName').value = val[7] ;
	   	   			if (val[8]!="") $('hospitalization').value = val[8] ;if (val[9]!="") $('hospitalizationName').value = val[9] ;
	   	   			if (val[10]!="") $('dispRegistration').value = val[10] ;if (val[11]!="") $('dispRegistrationName').value = val[11] ;
	   	   			//if (val[12]!="") $('serviceStream').value = val[12] ;if (val[13]!="") $('serviceStreamName').value = val[13] ;
	   	   			if (val[12]!=""&&(+val[12]>0)) $('emergency').checked=true;
	   	   			if (val[13]!="") {
	   	   				var diag = val[13].split("##") ;
	   	   				if (diag[0]!="") $('concludingMkb').value = diag[0] ;if (diag[1]!="") $('concludingMkbName').value = diag[1] ;
	   	   				if (diag[2]!="") $('concludingDiagnos').value = diag[2] ;if (diag[3]!="") $('concludingActuity').value = diag[3] ;
	   	   				if (diag[4]!="") $('concludingActuityName').value = diag[4] ;
	   	   			}
	   	   			if (val[14]!="") $('workFunctionExecute').value = val[14] ;if (val[15]!="") $('workFunctionExecuteName').value = val[15] ;
	   	   			setAdditionParam();
	  			}
	  		}})
	  	} else {
		   	TicketService.getSessionData( {
		   		callback: function(aResult) {
		   			//alert(aResult) ;
		   			if (aResult!=null&&aResult!="") {
		   				var val = aResult.split("@") ;
		   	   			if (val[0]!="") $('dateStart').value = val[0] ;
		   	   			if (val[1]!="") $('workFunctionExecute').value=val[1] ;
		   	   			if (val[2]!="") $('workFunctionExecuteName').value=val[2];
		   	   			if (val[4]!=""&&(+val[4]>0)) $('emergency').checked=true;
		   	   			setAdditionParam();
		   	   		}
	   			}
	   		});
	  	}
  	}
  	</script>
  	</msh:ifNotInRole>
  </msh:ifFormTypeIsCreate>
</tiles:put>
</tiles:insert>