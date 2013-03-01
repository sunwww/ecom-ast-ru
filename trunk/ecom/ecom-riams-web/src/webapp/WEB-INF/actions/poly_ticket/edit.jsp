<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoView-poly_ticket.do" defaultField="date" guid="77bf3d00-cfc6-49eb-9751-76e82d38751c">
      <msh:hidden property="id" guid="e862851f-7390-4fe6-9a37-3b22306138b4" />
      <msh:hidden property="saveType" guid="3e3fb7b5-258e-4194-9dbe-5093382cf627" />
      <msh:hidden property="talk" guid="34911b70-6c2c-43f2-bbf4-c58fed9a296e" />
      <msh:hidden property="medcard" guid="34911b70-6c2c-43f2-bbf4-c58fed9a296e" />
      <msh:panel colsWidth="15%,15%,15%,55%" guid="fecf8cd8-e883-4375-b47a-2954067ec3a7">
        <msh:row guid="59560d9f-0765-4df0-bfb7-9a90b5eed824">
          <msh:textField label="Запись на прием: Дата" property="date" fieldColSpan="1" guid="9e3a8e0d-cd82-4158-b764-e15cb16b4fca" />
          <msh:textField label="Время" property="time" fieldColSpan="1" guid="ed78c5e3-5e2c-4d8c-b64e-75767dcf0775" />
        </msh:row>
        <msh:row>
        	<msh:checkBox label="Неотложная помощь" property="emergency" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель" viewAction="entityParentView-mis_kinsman.do" 
        	parentId="poly_ticketForm.medcard" vocName="kinsmanByTicket" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
	        <msh:ifNotInRole roles="/Policy/Poly/Ticket/IsDoctorEdit">
		        <msh:row guid="47073a0b-da87-49e0-9ff0-711dc597ce07">
		          <msh:autoComplete parentId="poly_ticketForm.medcard" vocName="workFunctionByTicket" property="workFunction" label="Специалист" fieldColSpan="3"  horizontalFill="true" guid="a8404201-1bae-467e-b3e9-5cef63411d01" />
		        </msh:row>
	        </msh:ifNotInRole>
	        <msh:ifInRole roles="/Policy/Poly/Ticket/IsDoctorEdit">
			        <msh:row guid="47073a0b-da87-49e0-9ff0-711dc597ce07">
			          <msh:autoComplete vocName="workFunctionByDoctor" property="workFunction" label="Специалист" fieldColSpan="3"  horizontalFill="true" guid="a8404201-1bae-467e-b3e9-5cef63411d01" />
			        </msh:row>
	        </msh:ifInRole>
        <msh:row guid="a4309021-f766-4a2b-aad0-1ddbf0b3c9d9">
          <msh:autoComplete vocName="vocServiceStream" property="vocPaymentType" label="Вид оплаты" horizontalFill="true" guid="e5ac1267-bc69-44b2-8aba-b7455ac064c4" />
          <msh:autoComplete vocName="vocWorkPlaceType" property="vocServicePlace" label="Место обслуживания" horizontalFill="true" guid="18063077-15e8-4e4a-8679-ff79de589a72" />
        </msh:row>
        <msh:row guid="6d8642e8-756a-482f-a561-a9b474ef6c50">
          <msh:autoComplete vocName="vocReason" property="vocReason" label="Цель посещения" horizontalFill="true" guid="3632a2ed-6ecb-446f-8ae3-fe047f091076" />
          <msh:autoComplete vocName="vocVisitResult" property="vocVisitResult" label="Результат обращения" horizontalFill="true" guid="4346bd08-5fe2-4f01-9065-93a66cdfc1dd" />
        </msh:row>
        <msh:row guid="16f1e99-4017-4385-87c1-bf5895e2">
          <msh:autoComplete labelColSpan="3" property="hospitalization" label="Посещение в данном году по данному заболевания" guid="ddc10e76-8ee913984f" vocName="vocHospitalization" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        <msh:row guid="0489132a-531c-47bc-abfc-1528e774bbfe">
          <msh:autoComplete vocName="vocIdc10ticket" property="idc10" label="Диагноз код МКБ" fieldColSpan="3" horizontalFill="true" guid="9818fb43-33d1-4fe9-a0b4-2b04a9eee955" />
        </msh:row>
        <%-- 
        <msh:row>
        	<msh:autoComplete property="illnesType" vocName="vocIllnesType" fieldColSpan="3" label="Характер заболевания"/>
        </msh:row>
        --%>
        <msh:ifFormTypeIsView formName="poly_ticketForm">
	        <msh:row>
	          <msh:autoComplete vocName="vocAcuityDiagnosis" property="vocIllnesType" label="Острота" horizontalFill="true" guid="da756e0d-c6c0-4870-85c6-65973d6183de" />
	          <msh:autoComplete vocName="vocPrimaryDiagnosis" property="primary" label="Первичность" horizontalFill="true" guid="dade0d-c6c0-4870-85c6-6d3de" />
	        </msh:row>
        </msh:ifFormTypeIsView>
        <msh:row>
          <msh:autoComplete vocName="vocIllnesPrimary" property="illnesPrimary" label="Характер заболевания" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row guid="4bd126b5-2316-42c4-bcb7-ccf5108b2c27">
          <msh:autoComplete vocName="vocDispanseryRegistration" property="dispRegistration" label="Диспансерный учет" horizontalFill="true" guid="bf850705-5557-438e-b56e-33d59b1618e4" />
          <msh:autoComplete vocName="vocTrauma" property="vocTrauma" label="Травма" horizontalFill="true" guid="eedb1042-1861-426e-a0ec-6151c3933dd1" />
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MisLpu/Ambulance">
	        <msh:row>
	        	<msh:autoComplete vocName="vocAmbulance" property="ambulance" label="Бригада СП" horizontalFill="true" />
	        	<msh:autoComplete vocName="vocVisitOutcome" property="visitOutcome" label="Исход СП" horizontalFill="true" />
	        </msh:row>
        </msh:ifInRole>
        <msh:row>
        	<msh:checkBox property="directHospital" label="Направлен на стационарное лечение" fieldColSpan="3"/>
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        	<msh:ifFormTypeIsView formName="poly_ticketForm">
	        <msh:row>
	        	<msh:checkBox label="Беседа с родств." property="talk"/>
	        </msh:row>
	        </msh:ifFormTypeIsView>
        </msh:ifInRole>
        <msh:row>
	        	<ecom:oneToManyOneAutocomplete viewAction="entityView-mis_medService.do" label="Мед. услуги" property="medServices" vocName="medServiceForSpec" colSpan="3"/>
	    </msh:row>
        <msh:row guid="1283d16a-e417-4add-acf5-5185dbb7737d">
          <ecom:oneToManyOneAutocomplete vocName="vocIdc10" label="Сопутствующие заболевания" property="concomitantDiseases" colSpan="4" guid="1204d6c4-a3ff-44aa-a698-b99816d10337" />
        </msh:row>
        <msh:row guid="7dfb3b2c-407d-48f1-9e70-76cb3328f5f5">
        	<msh:textField property="uet" label="Усл.един.трудоем."/>
          <msh:autoComplete vocName="vocSpecLabel" property="specialLabel" label="Пометка" horizontalFill="true" guid="31f7b8de-d9e3-4f53-868a-1b76cf0930da" />
<%--           <msh:autoComplete vocName="omcRoadTrafficInjury" property="roadTrafficInjury" label="Справочник ДТП" horizontalFill="true" guid="317de-d9e3-4f53-868a-1b40da" /> --%>
        </msh:row>
        <msh:row guid="4392dbed-1d23-4f6f-8f78-7c9dc1a22238">
          <msh:ifFormTypeIsNotView formName="poly_ticketForm" guid="e7b243ab-fe9b-4b63-93f2-6fd52c362132">
            <msh:checkBox property="isTicketClosed" label="Закрыть талон?" guid="1a755b75-8611-4fa3-8cf7-b73f39635911" />
          </msh:ifFormTypeIsNotView>
          <msh:ifFormTypeAreViewOrEdit formName="poly_ticketForm" guid="e3e0c68a-63b6-47ee-8d1b-cc6eceb2dc23">
            <msh:label property="statusName" label="Статус талона" guid="388bd2d8-3a20-4072-bd11-fa5afadcc7c2" />
          </msh:ifFormTypeAreViewOrEdit>
        </msh:row>
        <msh:ifFormTypeIsNotView formName="poly_ticketForm" guid="540eb2a2-2113-4cad-97a0-f54f680a2a44">
          <script type="text/javascript">$('isTicketClosed').checked=true ;</script>
        </msh:ifFormTypeIsNotView>
        <%--
        <msh:separator label="Ранее зарегистрированный диагноз" colSpan="4" guid="88c6ecbf-55f4-4d7a-8db7-f41a75b8f86d" />
        <msh:row guid="f969e28a-aa6a-4457-bf2b-e228b53544e5">
          <msh:autoComplete vocName="vocIdc10ticket" property="prevIdc10" label="Диагноз" fieldColSpan="3" horizontalFill="true" guid="dd2d60e5-7f9b-45c2-8df1-74b553d52ae8" />
        </msh:row>
        <msh:row guid="11657459-a8bc-400d-a2cc-df9b05de500b">
          <msh:textField label="Дата" property="prevIdc10Date" size="10" guid="46035755-65f7-4643-82bf-a85be832fea1" />
        </msh:row>
        <msh:separator label="Нетрудоспособность" colSpan="4" guid="d9a7ec35-7893-48b3-aa08-f2e04d9a9400" />
        <msh:row guid="6a1af7d7-1664-4967-a995-3697382934b5">
          <msh:autoComplete showId="false" vocName="vocDisabilityDocumentStatus" property="disabilityDocumentStatus" label="Статус документа нетруд." fieldColSpan="3" horizontalFill="true" guid="e4283290-247e-4e36-a063-e43040e55801" />
        </msh:row>
        <msh:row guid="f9adcbb3-0c79-486e-a402-1e48bd857afb">
          <msh:autoComplete showId="false" vocName="vocDisabilityReason" property="disabilityReason" label="Причина нетрудоспос." fieldColSpan="3" horizontalFill="true" guid="8cb2cdff-ff63-4a1c-84c1-ab1871fd7e8a" />
        </msh:row>
         --%>
        <msh:ifFormTypeAreViewOrEdit formName="poly_ticketForm">
        <msh:separator label="Выдан талон" colSpan="4" guid="d9a7ec35-7893-48b3-aa08-f2e04d9a9400" />
        <msh:row>
        	<msh:textField label="Дата" property="dateCreate" fieldColSpan="1" viewOnlyField="true"/>
        	<msh:textField label="Время" property="timeCreate" fieldColSpan="1" viewOnlyField="true"/>
        </msh:row>
        

	    <msh:row>
        	<msh:textField label="Пользователь" property="usernameCreate" viewOnlyField="true" />
        </msh:row>
        </msh:ifFormTypeAreViewOrEdit>
	    <msh:submitCancelButtonsRow colSpan="4" guid="13aa4bce-1133-48d6-896b-eb588a046d59" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="poly_ticketForm">
    <msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
    	<msh:section>
    		<msh:sectionTitle>Талоны беседы с родственниками <msh:link action="/js-poly_ticket-addTalk.do?id=${param.id}" roles="/Policy/Poly/Ticket/Create,/Policy/Mis/MisLpu/Psychiatry">добавить</msh:link> </msh:sectionTitle>
    		<msh:sectionContent>
    			<ecom:webQuery name="ticketTalk" nativeSql="select t1.id, t1.status,t1.talk 
    			from Ticket t1 
    			left join Ticket t2 on t1.medcard_id=t2.medcard_id and t1.date=t2.date 
    			where t2.id=${param.id} and t1.workFunction_id=t2.workFunction_id  and t1.talk=1"/>
    			<msh:table deleteUrl="entityParentDeleteGoParentView-poly_ticket.do" viewUrl="entityShortView-poly_ticket.do" name="ticketTalk" action="entityParentView-poly_ticket.do" idField="1">
    				<msh:tableColumn property="sn" columnName="#"/>
    				<msh:tableColumn property="1" columnName="ИД талона"/>
    				<msh:tableColumn property="2" columnName="status"/>
    				<msh:tableColumn property="3" columnName="talk"/>
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
    <msh:ifInRole roles="/Policy/Poly/PrescriptionBlank/View">
    <msh:ifFormTypeIsView formName="poly_ticketForm" guid="e2bc3fba-21d3-49fd-95ba-4b65d5dc2eaf">
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
    <msh:ifFormTypeIsView formName="poly_ticketForm" guid="35116e3f-c2be-453e-82a9-188919feffeb">
      <msh:section guid="d1ffa344-d0a3-44f5-bc17-9df25e81577a">
        <msh:sectionTitle guid="fe1105d3-8dc3-4adc-a89f-1af1b8f184b3">Протоколы</msh:sectionTitle>
        <msh:sectionContent guid="f3a17575-c8c3-4322-99be-8b73ac86ce6e">
          <ecom:parentEntityListAll formName="poly_protocolForm" attribute="protocols" guid="b50bcecc-8db1-4722-a9c9-1c133bd45db9" />
          <msh:table name="protocols" action="entityParentView-poly_protocol.do" idField="id" guid="6372a920-f150-4aaa-ba47-0e8017849f42">
            <msh:tableColumn columnName="Информация о протоколе" property="info" guid="d10f2929-52ff-44fb-9ea4-636bcec18f1e" />
            <msh:tableColumn columnName="Текст" property="record" guid="d10f2929-fb-9ea4-636bcec18f1e" />
            <msh:tableColumn columnName="Специалист" property="specialistInfo" guid="d10a4-636bcec18f1e" />
          </msh:table>
        </msh:sectionContent>
      </msh:section>
    </msh:ifFormTypeIsView>
    </msh:ifInRole>
    <tags:mis_double name='Ticket' title='Существующие талоны в базе:'/>
  </tiles:put>
  <tiles:put name="side" type="string">
  		<msh:sideMenu title="Дополнительно">
	        <msh:sideLink action="/javascript:viewProtocolByMedcard('.do')" name='Заключения<img src="/skin/images/main/view1.png" alt="Просмотр записи" title="Просмотр записи" height="16" width="16">' title="Просмотр визитов по пациенту" key="ALT+4" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Protocol/Create" />
	        <msh:sideLink action="/javascript:infoDiagByMedcard('.do')" name='Диагнозы<img src="/skin/images/main/view1.png" alt="Просмотр записи" title="Просмотр записи" height="16" width="16">' title="Просмотр визитов по пациенту" key="ALT+4" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Protocol/Create" />
  		</msh:sideMenu>
    <msh:ifFormTypeIsView formName="poly_ticketForm" guid="8f-4d80-856b-ce3095ca1d">
      <msh:sideMenu guid="e6c81315-888f-4d80-856b-ce3095ca1d55" title="Талон" >
        <msh:sideLink roles="/Policy/Poly/Ticket/Edit" key="ALT+2" params="id" action="/entityEdit-poly_ticket" name="Изменить" guid="89585df8-aadb-4d59-abd9-c0d16a6170a9" title="Изменить талон" />
        <msh:sideLink roles="/Policy/Poly/Ticket/Edit" key="ALT+3" params="id" action="/poly_closeTicket" name="Закрыть" guid="661fe852-e096-410a-9fab-86d8e75db177" title="Закрыть талон" />
        <msh:sideLink roles="/Policy/Poly/Ticket/Edit,/Policy/Mis/MisLpu/Psychiatry" key="ALT+4" params="id" action="/js-poly_ticket-addTalk" name="Беседа с родственниками" guid="661fe852-e096-410a-9fab-86d8e75db177" title="Беседа с родственниками" />
        <msh:sideLink roles="/Policy/Poly/Ticket/Edit,/Policy/Poly/Ticket/TalkDelete,/Policy/Mis/MisLpu/Psychiatry" key="ALT+4" params="id" action="/js-poly_ticket-doNotAddTalk" name="Сделать обычным посещением" guid="661fe852-e096-410a-9fab-86d8e75db177" title="Беседа с родственниками" />
        
        <msh:ifFormTypeAreViewOrEdit formName="poly_ticketForm" guid="7f581b0a-a8b3-4d57-9cff-6dc6db1c85e3">
          <msh:sideLink roles="/Policy/Poly/Ticket/Delete" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-poly_ticket" name="Удалить" confirm="Вы действительно хотите удалить талон?" guid="8b9de89f-3b99-414e-b4af-778ccbb70edf" title="Удалить талон" />
        </msh:ifFormTypeAreViewOrEdit>
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="3d94cf79-f341-469e-863e-5e28bd16aabe">
        <msh:sideLink roles="/Policy/Poly/PrescriptionBlank/Create" key="CTRL+2" params="id" action="/entityParentPrepareCreate-poly_prescriptionBlank" name="Рецептурный бланк" guid="09e47fdd-298c-4230-9916-2b9a15abee56" title="Добавить рецептурный бланк" />
        <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Create" key="CTRL+3" params="id" action="/entityParentPrepareCreate-poly_protocol" name="Заключение" guid="b5ae64d7-16da-4307-998b-9214fa4a600f" title="Добавить протокол" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать" guid="62fd4ce0-85b5-4661-87b2-fea2d4fb7339">
        <msh:sideLink roles="/Policy/Poly/Ticket/View" key="SHIFT+8" params="id" action="/print-ticket.do?s=PrintTicketService&amp;m=printInfo" name="Талона" guid="97e65138-f936-45d0-ac70-05e1ec87866c" title="Печатать талона" />
        <msh:sideLink roles="/Policy/Poly/Ticket/BakExp" params="id" action="/print-BakExp.do?s=PrintTicketService&amp;m=printBakExp" name="Направления на бак.исследование" guid="5138-f936-45d0-ac70-066c" key="SHIFT+9" title="Печатать направления на бак.исследование" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
        <tags:ticket_finds currentAction="ticket"/>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Medcard" beginForm="poly_ticketForm" guid="5c4f3682-e66b-4e0d-b448-4e6a2961a943" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript" src="./dwr/interface/TicketService.js"></script>
    <script type="text/javascript">
    function viewProtocolByMedcard(d) {
    	var m = document.forms[0].medcard ;
  	  getDefinition("js-poly_protocol-infoByMedcardShort.do?id="+m.value, null); 
    }    
    function infoDiagByMedcard(d) {
    	var m = document.forms[0].medcard ;
  	  getDefinition("js-poly_ticket-infoDiagByMedcard.do?id="+m.value, null); 
    }    
    </script>
  	<msh:ifInRole roles="/Policy/Poly/Ticket/IsDoctorEdit">
  <msh:ifFormTypeIsCreate formName="poly_ticketForm">
  <script type="text/javascript">
  TicketService.getWorkFunction(
    		 {
                   callback: function(aResult) {
                      //$('workFunction').value=aResult ;
                      workFunctionAutocomplete.setParentId(aResult) ;
                      workFunctionAutocomplete.setVocId(aResult) ;                   }
	        	}
	        	);
  </script>
  </msh:ifFormTypeIsCreate>
  <msh:ifFormTypeIsNotView formName="poly_ticketForm">
  <script type="text/javascript">
  TicketService.getWorkFunction(
    		 {
                   callback: function(aResult) {
                      workFunctionAutocomplete.setParentId(aResult) ;
                      //workFunctionAutocomplete.setVocId(aResult) ;
//                      $('workFunction').value=aResult ;
                   }
	        	}
	        	);
  </script>
  </msh:ifFormTypeIsNotView>
  </msh:ifInRole>
  <msh:ifFormTypeAreViewOrEdit formName="poly_ticketForm">
  	<msh:ifFormTypeIsNotView formName="poly_ticketForm">
  	 <script type="text/javascript">
		TicketService.isEditCheck($('id').value, $('workFunction').value,
			{
				callback: function(aResult) {
					if (+aResult.substring(0,1)==0) {
						alert(aResult.substring(2));
						window.location.href= "entityParentView-poly_ticket.do?id=${param.id}"; 
					}
				}
			}
		);
	    
	 </script>
  	</msh:ifFormTypeIsNotView>
  </msh:ifFormTypeAreViewOrEdit>  
  <msh:ifFormTypeIsNotView formName="poly_ticketForm">
    <script type="text/javascript">// <![CDATA[//
    	
    	var oldaction = document.forms[0].action ;
    	var oldValue = $('date').value ;
    	document.forms[0].action = 'javascript:isExistTicket()';
    	idc10Autocomplete.addOnChangeCallback(function() {
    		if (($('idc10Name').value!='') &&($('idc10Name').value.substring(0,1)=='Z')) {
	      	 	TicketService.findProvReason({
	      	 		callback: function(aResult) {
	      	 			var ind = aResult.indexOf('#') ;
	      	 			if (ind!=-1) {
	      	 				$('vocReason').value=aResult.substring(0,ind) ;
	      	 				$('vocReasonName').value=aResult.substring(ind+1) ;
	      	 			}
	      	 		}
	      	 	}) ;
	      	 }
	    });
    	if ($('workFunctionName')) workFunctionAutocomplete.addOnChangeCallback(function() {
    		var wf = +$("workFunction").value;
    		if (wf=='') {wf=0;}
    		 if (theOtmoa_medServices) theOtmoa_medServices.setParentId(wf+"#"+$("date").value) ;
    		 if (theOtmoa_medServices) theOtmoa_medServices.clearData() ;
    		 TicketService.getMedServiceBySpec(wf,$('date').value,{
	      	 		callback: function(aResult) {
	      	 			if (theOtmoa_medServices) theOtmoa_medServices.setIds(aResult) ;
	      	 		}
	      	 	}) ;
	    });
	      	eventutil.addEventListener($('date'),'blur',function(){
		  		if (oldValue!=$('date').value) {
		  			var wf = +$("workFunction").value;
		    		if (wf=='') {wf=0;}
		  			 if (theOtmoa_medServices) theOtmoa_medServices.setParentId(wf+"#"+$("date").value) ;
		    		 if (theOtmoa_medServices) theOtmoa_medServices.clearData() ;
		    		 TicketService.getMedServiceBySpec(wf,$('date').value,{
			      	 		callback: function(aResult) {
			      	 			if (theOtmoa_medServices) theOtmoa_medServices.setIds(aResult) ;
			      	 		}
			      	 	}) ;
		  		}
		  	}) ;
		function changeParentMedService() {
		}
    	function isExistTicket() {
    		 
    		TicketService.findDoubleBySpecAndDate($('id').value,document.forms[0].medcard.value,$('workFunction').value, $('date').value
    		, {
                   callback: function(aResult) {
                   
                      if (aResult) {
				    		//showTicketDouble(aResult) ;
				    		alert('Уже заведен талон посещения на '+$('date').value+' к специалисту данному по медкарте №'+document.forms[0].medcard.value) ;
				    			 ;
                       } else {
                     		TicketService.checkHospitalByMedcard($('date').value,document.forms[0].medcard.value,$('vocPaymentType').value
                     		  		,{callback: function(aString) {
                     		        	//alert(aString) ;
                     		            if (aString!=null) {
                     		            	alert("Человек находился в больнице "+aString+" по ОМС его оформить за этот период нельзя!!!") ;
                     		            } else {
                                    	   	TicketService.saveSession($('date').value,$('workFunction').value
                                    	   			,$('workFunctionName').value,$('medServices').value,$('emergency').checked, {
                                    	   		callback: function(aResult) {
                                    	   			document.forms[0].action = oldaction ;
                        				    		document.forms[0].submit() ;
                                    	   		}
                                    	   	});
                     		             }
                     		         }}) ;
                       		
                       }
                   }
	        	}
	        	)
    		}
    		function getValue(aFld) {
    			if (aFld) {
    				return aFld.value ;
    			} else {
    				return "none" ;
    			}
    		}
    		 if (theOtmoa_medServices) theOtmoa_medServices.setParentId((+$("workFunction").value)+"#"+$("date").value) ;
    	//]]>
    	</script>
    	
  </msh:ifFormTypeIsNotView>
  <msh:ifFormTypeIsCreate formName="poly_ticketForm">
  <msh:ifNotInRole roles="/Policy/Poly/Ticket/IsDoctorEdit">
  	<script type="text/javascript">
   	TicketService.getSessionData( {
   		
   		callback: function(aResult) {
   			//alert(aResult) ;
   			if (aResult!=null&&aResult!=""&&(+$('workFunction').value<1)) {
   				var val = aResult.split("@") ;
   	   			if (val[0]!="") $('date').value = val[0] ;
   	   			if (val[1]!="") $('workFunction').value=val[1] ;
   	   			if (val[2]!="") $('workFunctionName').value=val[2];
   	   			if (val[4]!=""&&(+val[4]>0)) $('emergency').checked=true;
   	   			
   	   		var wf = +$("workFunction").value;
    		if (wf=='') {wf=0;}
    		 if (theOtmoa_medServices) theOtmoa_medServices.setParentId(wf+"#"+$("date").value) ;
    		 if (theOtmoa_medServices) theOtmoa_medServices.clearData() ;
    		 TicketService.getMedServiceBySpec(wf,$('date').value,{
	      	 		callback: function(aResult) {
	      	 			if (theOtmoa_medServices) theOtmoa_medServices.setIds(aResult) ;
	      	 		}
	      	 	}) ;
   	    		//if (theOtmoa_medServices && val[3]!="") theOtmoa_medServices.setIds(val[3]) ;
   			}
   		}
   	});
  	
  	</script>
  	</msh:ifNotInRole>
  </msh:ifFormTypeIsCreate>
</tiles:put>
</tiles:insert>

