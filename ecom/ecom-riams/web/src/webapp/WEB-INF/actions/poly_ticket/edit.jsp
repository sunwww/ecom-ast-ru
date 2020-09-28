<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoView-poly_ticket.do" defaultField="date">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="talk" />
      <msh:hidden property="medcard" />
      <msh:panel colsWidth="15%,15%,15%,55%">
        <msh:row>
          <msh:textField label="Запись на прием: Дата" property="date" fieldColSpan="1" />
          <msh:textField label="Время" property="time" fieldColSpan="1" />
        </msh:row>
        <msh:row>
        	<msh:checkBox label="Неотложная помощь" property="emergency" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель" viewAction="entityParentView-mis_kinsman.do" 
        	parentId="poly_ticketForm.medcard" vocName="kinsmanByTicket" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
	        <msh:ifNotInRole roles="/Policy/Poly/Ticket/IsDoctorEdit">
		        <msh:row>
		          <msh:autoComplete parentId="poly_ticketForm.medcard" vocName="workFunctionByTicket" property="workFunction" label="Специалист" fieldColSpan="3"  horizontalFill="true" />
		        </msh:row>
	        </msh:ifNotInRole>
	        <msh:ifInRole roles="/Policy/Poly/Ticket/IsDoctorEdit">
			        <msh:row>
			          <msh:autoComplete vocName="workFunctionByDoctor" property="workFunction" label="Специалист" fieldColSpan="3"  horizontalFill="true" />
			        </msh:row>
	        </msh:ifInRole>
        <msh:row>
          <msh:autoComplete vocName="vocServiceStream" property="vocPaymentType" label="Вид оплаты" horizontalFill="true" />
          <msh:autoComplete vocName="vocWorkPlaceType" property="vocServicePlace" label="Место обслуживания" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocReason" property="vocReason" label="Цель посещения" horizontalFill="true" />
          <msh:autoComplete vocName="vocVisitResult" property="vocVisitResult" label="Результат обращения" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete labelColSpan="3" property="hospitalization" label="Посещение в данном году по данному заболевания" vocName="vocHospitalization" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocIdc10" property="idc10" label="Диагноз код МКБ" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <%-- 
        <msh:row>
        	<msh:autoComplete property="illnesType" vocName="vocIllnesType" fieldColSpan="3" label="Характер заболевания"/>
        </msh:row>
        --%>
        <msh:ifFormTypeIsView formName="poly_ticketForm">
	        <msh:row>
	          <msh:autoComplete vocName="vocAcuityDiagnosis" property="vocIllnesType" label="Острота" horizontalFill="true" />
	          <msh:autoComplete vocName="vocPrimaryDiagnosis" property="primary" label="Первичность" horizontalFill="true" />
	        </msh:row>
        </msh:ifFormTypeIsView>
        <msh:row>
          <msh:autoComplete vocName="vocIllnesPrimary" property="illnesPrimary" label="Характер заболевания" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocDispanseryRegistration" property="dispRegistration" label="Диспансерный учет" horizontalFill="true" />
          <msh:autoComplete vocName="vocTrauma" property="vocTrauma" label="Травма" horizontalFill="true" />
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
        <msh:row>
          <ecom:oneToManyOneAutocomplete vocName="vocIdc10" label="Сопутствующие заболевания" property="concomitantDiseases" colSpan="4" />
        </msh:row>
        <msh:row>
        	<msh:textField property="uet" label="Усл.един.трудоем."/>
          <msh:autoComplete vocName="vocSpecLabel" property="specialLabel" label="Пометка" horizontalFill="true" />
<%--           <msh:autoComplete vocName="omcRoadTrafficInjury" property="roadTrafficInjury" label="Справочник ДТП" horizontalFill="true" /> --%>
        </msh:row>
        <msh:row>
          <msh:ifFormTypeIsNotView formName="poly_ticketForm">
            <msh:checkBox property="isTicketClosed" label="Закрыть талон?" />
          </msh:ifFormTypeIsNotView>
          <msh:ifFormTypeAreViewOrEdit formName="poly_ticketForm">
            <msh:label property="statusName" label="Статус талона" />
          </msh:ifFormTypeAreViewOrEdit>
        </msh:row>
        <msh:ifFormTypeIsNotView formName="poly_ticketForm">
          <script type="text/javascript">$('isTicketClosed').checked=true ;</script>
        </msh:ifFormTypeIsNotView>
        <%--
        <msh:separator label="Ранее зарегистрированный диагноз" colSpan="4" />
        <msh:row>
          <msh:autoComplete vocName="vocIdc10" property="prevIdc10" label="Диагноз" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField label="Дата" property="prevIdc10Date" size="10" />
        </msh:row>
        <msh:separator label="Нетрудоспособность" colSpan="4" />
        <msh:row>
          <msh:autoComplete showId="false" vocName="vocDisabilityDocumentStatus" property="disabilityDocumentStatus" label="Статус документа нетруд." fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" vocName="vocDisabilityReason" property="disabilityReason" label="Причина нетрудоспос." fieldColSpan="3" horizontalFill="true" />
        </msh:row>
         --%>
        <msh:ifFormTypeAreViewOrEdit formName="poly_ticketForm">
        <msh:separator label="Выдан талон" colSpan="4" />
        <msh:row>
        	<msh:textField label="Дата" property="dateCreate" fieldColSpan="1" viewOnlyField="true"/>
        	<msh:textField label="Время" property="timeCreate" fieldColSpan="1" viewOnlyField="true"/>
        </msh:row>
        

	    <msh:row>
        	<msh:textField label="Пользователь" property="usernameCreate" viewOnlyField="true" />
        </msh:row>
        </msh:ifFormTypeAreViewOrEdit>
	    <msh:submitCancelButtonsRow colSpan="4" />
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
    <msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/View">
    <msh:ifFormTypeIsView formName="poly_ticketForm">
      <msh:section>
        <msh:sectionTitle>Протоколы</msh:sectionTitle>
        <msh:sectionContent>
          <ecom:parentEntityListAll formName="poly_protocolForm" attribute="protocols" />
          <msh:table name="protocols" action="entityParentView-poly_protocol.do" idField="id">
            <msh:tableColumn columnName="Информация о протоколе" property="info" />
            <msh:tableColumn columnName="Текст" property="record" />
            <msh:tableColumn columnName="Специалист" property="specialistInfo" />
          </msh:table>
        </msh:sectionContent>
      </msh:section>
    </msh:ifFormTypeIsView>
    </msh:ifInRole>
    <tags:mis_double name='Ticket' title='Существующие талоны в базе:' cmdAdd="document.forms[0].submitButton.disabled = false "/>
  </tiles:put>
  <tiles:put name="side" type="string">
  		<msh:sideMenu title="Дополнительно">
	        <msh:sideLink action="/javascript:viewProtocolByMedcard('.do')" name='Заключения<img src="/skin/images/main/view1.png" alt="Просмотр записи" title="Просмотр записи" height="16" width="16">' title="Просмотр визитов по пациенту" key="ALT+4" params="" roles="/Policy/Mis/MedCase/Protocol/Create" />
	        <msh:sideLink action="/javascript:infoDiagByMedcard('.do')" name='Диагнозы<img src="/skin/images/main/view1.png" alt="Просмотр записи" title="Просмотр записи" height="16" width="16">' title="Просмотр визитов по пациенту" key="ALT+4" params="" roles="/Policy/Mis/MedCase/Protocol/Create" />
  		</msh:sideMenu>
    <msh:ifFormTypeIsView formName="poly_ticketForm">
      <msh:sideMenu title="Талон" >
        <msh:sideLink roles="/Policy/Poly/Ticket/Edit" key="ALT+2" params="id" action="/entityEdit-poly_ticket" name="Изменить" title="Изменить талон" />
        <msh:sideLink roles="/Policy/Poly/Ticket/Edit" key="ALT+3" params="id" action="/poly_closeTicket" name="Закрыть" title="Закрыть талон" />
        <msh:sideLink roles="/Policy/Poly/Ticket/Edit,/Policy/Mis/MisLpu/Psychiatry" key="ALT+4" params="id" action="/js-poly_ticket-addTalk" name="Беседа с родственниками" title="Беседа с родственниками" />
        <msh:sideLink roles="/Policy/Poly/Ticket/Edit,/Policy/Poly/Ticket/TalkDelete,/Policy/Mis/MisLpu/Psychiatry" key="ALT+4" params="id" action="/js-poly_ticket-doNotAddTalk" name="Сделать обычным посещением" title="Беседа с родственниками" />
        
        <msh:ifFormTypeAreViewOrEdit formName="poly_ticketForm">
          <msh:sideLink roles="/Policy/Poly/Ticket/Delete" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-poly_ticket" name="Удалить" confirm="Вы действительно хотите удалить талон?" title="Удалить талон" />
        </msh:ifFormTypeAreViewOrEdit>
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Create" key="CTRL+3" params="id" action="/entityParentPrepareCreate-poly_protocol" name="Заключение" title="Добавить протокол" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
        <msh:sideLink roles="/Policy/Poly/Ticket/View" key="SHIFT+8" params="id" action="/print-ticket.do?s=PrintTicketService&amp;m=printInfo" name="Талона" title="Печатать талона" />
        <msh:sideLink roles="/Policy/Poly/Ticket/BakExp" params="id" action="/print-BakExp.do?s=PrintTicketService&amp;m=printBakExp" name="Направления на бак.исследование" key="SHIFT+9" title="Печатать направления на бак.исследование" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
        <tags:ticket_finds currentAction="ticket"/>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Medcard" beginForm="poly_ticketForm" />
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

