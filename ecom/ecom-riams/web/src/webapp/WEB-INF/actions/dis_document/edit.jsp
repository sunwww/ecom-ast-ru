<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Документ нетрудоспособности
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoView-dis_document.do" defaultField="documentTypeName">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="disabilityCase" />
      <msh:panel>
        <msh:row>
          <msh:autoComplete vocName="mainLpu" property="anotherLpu" label="Другое лечебное учреждение" guid="c431085f-265a-4c8b5babeff" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocDisabilityDocumentType" property="documentType" label="Документ" guid="c431085f-265a-40ab-958a1c8b5babeff" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="status" fieldColSpan="3" horizontalFill="true" label="Статус документа" vocName="vocDisabilityStatus" />
        </msh:row>
        <msh:row>
          <msh:autoComplete viewAction="entityView-dis_document.do" 
          	shortViewAction="entityShortView-dis_document.do" viewOnlyField="true" 
          	vocName="disabilityDocumentByCase" property="duplicate" 
          	label="Заменен на документ" fieldColSpan="3" horizontalFill="true" />
        </msh:row>        
        <msh:row>
          <msh:textField passwordEnabled="false" hideLabel="false" property="issueDate" viewOnlyField="false" guid="7a444864-9b79-4e21-b218-11989c5d4c98" horizontalFill="false" />
          <msh:autoComplete vocName="vocDisabilityDocumentPrimarity" property="primarity" label="Первичность" guid="2e7aa7a4-336c-4831-b3d9-97d6f64d2ef1" horizontalFill="true" size="20" />
        </msh:row>
        <msh:row>
          <msh:textField property="series" label="Серия" guid="b9d0f37f-bd93-4e91-be9c-703c363ca9a8" />
          <msh:textField property="number" label="Номер"  fieldColSpan="30" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="disabilityDocumentByCase" property="prevDocument" label="Предыдущий документ" guid="c431085f-265a-40ab-9581-a1c8b5babeff" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:textField property="job" fieldColSpan="3" label="Место работы" horizontalFill="true"/>
        </msh:row>
        <msh:ifFormTypeIsNotView formName="dis_documentForm">
        	<msh:row>
        		<msh:textField property="otherNumber" fieldColSpan="3" horizontalFill="true"
        			label="Место рабо (29 сим.)" viewOnlyField="true"/>
        		
        	</msh:row>
	        <msh:row>
	        	<msh:checkBox property="isUpdateWork" fieldColSpan="3" horizontalFill="Обновить сокращенное название организации"/>
	        </msh:row>
        </msh:ifFormTypeIsNotView>
        <msh:ifFormTypeIsCreate formName="dis_documentForm">
	        <msh:row guid="3972e779-80b6-45cb-8004-df6bcb22da38">
	          <msh:separator label="Период нетрудоспособности" colSpan="4" guid="819b1c93-689a-404c-bd28-c18025b03fe4" />
	        </msh:row>
	        <msh:row>
	          <msh:textField property="dateFrom" label="Дата начала" guid="71bb6108-4449-460b-aaca-0c7419683133" />
	          <msh:textField property="dateTo" label="Дата окончания" guid="31e70e41-3526-4a9e-b746-263d6e81e657" />
	        </msh:row>
        <msh:ifFormTypeIsNotView formName="dis_documentForm">
        	<msh:row>
        		<msh:textField property="info" labelColSpan="2" fieldColSpan="2" horizontalFill="true"
        			label="Количество дней нетрудоспособности" viewOnlyField="true"/>
        		
        	</msh:row>
        </msh:ifFormTypeIsNotView>        
	        
	        <msh:row>
	          <msh:autoComplete vocName="vocDisabilityRegime" property="regime" label="Режим" guid="a0252f86-792b-4992-a278-5cb0d1a1bc27" fieldColSpan="3" horizontalFill="true" />
	        </msh:row>
	        <msh:row>
	          <msh:autoComplete showId="false" vocName="workFunction" hideLabel="false" property="workFunction" viewOnlyField="false" label="Леч.врач" fieldColSpan="3" horizontalFill="true" />
	        </msh:row>
	        <msh:row>
	          <msh:autoComplete showId="false" vocName="workFunction" hideLabel="false" property="workFunctionAdd" viewOnlyField="false" label="Председ. ВК" fieldColSpan="3" horizontalFill="true" />
	        </msh:row>
        </msh:ifFormTypeIsCreate>



        <msh:row>
          <msh:separator label="Совместительство" colSpan="4" guid="3ff9bb0c-9272-467c-9623-a30b175721fd" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocCombo" property="workComboType" label="Тип совместительства" guid="227a4-336c-4831-b3d9-9f12ef1" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField guid="textFieldHello" property="mainWorkDocumentSeries" label="Серия по основному месту работы" />
          <msh:textField property="mainWorkDocumentNumber" label="номер" guid="0cdf1a41-0d4d-40d6-81a4-4e61b1dd3095" />
        </msh:row>
        <msh:row>
          <msh:separator label="Причина нетрудоспособности" colSpan="4" guid="7079359c-4652-4f5b-8e43-bbd120ce2270" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocDisabilityReason" property="disabilityReason" label="Причина нетруд." guid="c431085f-265a-40ab-9581-a1c8b5babeff" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocDisabilityReason2" property="disabilityReason2" label="Доп. причина нетруд." fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocDisabilityReason" property="disabilityReasonChange" label="Изм. причины нетруд." fieldColSpan="3" horizontalFill="true"/>
        </msh:row>        
        <msh:row>
          <msh:autoComplete vocName="vocIdc10" property="idc10" label="Диагноз первич." fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocIdc10" property="idc10Final" label="Диагноз заключ." fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Госпитацизация" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="hospitalizedFrom" label="Дата начала госпит."/>
        	<msh:textField property="hospitalizedTo" label="Дата окончания"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="hospitalizedNumber" label="№ истории болезни" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:separator label="Санаторное лечение" colSpan="4" guid="df2feaa7-d518-475a-afc5-fc424b3441e4" />
        </msh:row>
        <msh:row>
          <msh:textField property="sanatoriumDateFrom" label="Дата начала (пред.родов)" guid="c78d8c08-23f6-4825-9910-050d0d4c41bb" />
          <msh:textField property="sanatoriumDateTo" label="Дата окончания" guid="daa5ef7d-f6fa-474b-8d67-f56e7922c417" />
        </msh:row>
        <msh:row>
          <msh:textField property="sanatoriumTicketNumber" label="Номер путевки" guid="719c215c-d614-4c67-ba5d-8d1af83257ec" />
        </msh:row>
        <msh:row>
          <msh:textField property="sanatoriumPlace" label="Место нахождения санатория" guid="1934315c-53e9-4b96-9ce2-6e7bc7a59a2e" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:textField property="sanatoriumOgrn" label="ОГРН санатория или клиники НИИ" fieldColSpan="2" horizontalFill="true"/>
        </msh:row>

        <msh:row guid="ef70bf08-f8aa-4283-ae1d-fcc5fa8692de">
          <msh:separator label="Закрытие" colSpan="4" guid="c03fc1ea-c2ab-472e-8d52-3f70b7efbd08" />
        </msh:row>
        <msh:row guid="e46a3-e51c-46ef-9a21-6bvb60831">
    	      <msh:autoComplete vocName="vocDisabilityDocumentCloseReason" property="closeReason" label="Причина закрытия" guid="c425f-265a-40ab-9581-a8ff" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
       <msh:row>
        	<msh:textField property="otherCloseDate" label="Иная дата закрытия для причин 32, 33, 34, 36" labelColSpan="3"/>
        </msh:row>
        <msh:row>
          <msh:checkBox hideLabel="false" property="noActuality" viewOnlyField="true" guid="6deca67a-3fcb-472f-aadd-3e6cf3139c83" horizontalFill="false" label="Испорчен" />

         	<msh:ifInRole roles="/Policy/Mis/Disability/Case/Document/Reopen"> 
        		<msh:ifFormTypeIsNotView formName="dis_documentForm">
        			<msh:checkBox property="isClose" label="Документ закрыт" guid="c425f-265a-40ab-9581-a8ff"  />
        		</msh:ifFormTypeIsNotView>
         	</msh:ifInRole> 
        	<msh:ifFormTypeIsView formName="dis_documentForm">
        		<msh:checkBox property="isClose" label="Документ закрыт"/>
        	</msh:ifFormTypeIsView>
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительная информация" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="createDate" label="Дата создания" viewOnlyField="true"/>
        	<msh:textField property="createUsername" label="Пользователь" viewOnlyField="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="editDate" label="Дата редактирования" viewOnlyField="true"/>
        	<msh:textField property="editUsername" label="Пользователь" viewOnlyField="true"/>
        </msh:row>
        <msh:row guid="685ad8f8-f93c-4bd6-98b5-a1618944cb07">
          <msh:submitCancelButtonsRow guid="submitCancel" colSpan="3" />
        </msh:row>
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="dis_documentForm">
    	<ecom:webQuery name="duplicateDocument" nativeSql="select dd.id,dd.series|| ' '||dd.number from disabilitydocument dd where dd.duplicate_id=${param.id }"/>
    	<msh:section title="Данный документ заменил документ">
    		<msh:table name="duplicateDocument" 
    			action="entityView-dis_document.do"
    			viewUrl="entityShortView-dis_document.do" 
    			hideTitle="true" idField="1">
    			<msh:tableColumn property="2" columnName="номер"/>

    		</msh:table>
    	</msh:section>
      <msh:ifInRole roles="/Policy/Mis/Disability/Case/Document/Record/View" guid="7c589f25-6e30-4d30-bb2f-d86a68f4f0cd">
        <msh:section guid="sectionChilds" title="Продление" 
        	createRoles="/Policy/Mis/Disability/Case/Document/Record/Create" createUrl="entityParentPrepareCreate-dis_record.do?id=${param.id}">
          <ecom:parentEntityListAll guid="parentEntityListChilds" formName="dis_recordForm" attribute="disabilityRecord" />
          <msh:table editUrl="entityParentEdit-dis_record.do" guid="tableChilds" viewUrl="entityShortView-dis_record.do" name="disabilityRecord" action="entityParentView-dis_record.do" idField="id">
            <msh:tableColumn columnName="Дата начала" property="dateFrom" guid="23eed88f-9ea7-4b8f-a955-20ecf89ca86c" />
            <msh:tableColumn columnName="Дата окончания" property="dateTo" guid="a744754f-5212-4807-910f-e4b252aec108" />
            <msh:tableColumn columnName="Леч.врач" identificator="false" property="workFunctionInfo"  />
            <msh:tableColumn columnName="Председ. ВК" identificator="false" property="workFunctionAddInfo"  />
            <msh:tableColumn columnName="Режим" identificator="false" property="regimeText" guid="14e8c4f9-f430-496c-ae75-ae2a2240937d" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/Disability/Case/Document/RegimeViolationRecord/View" guid="1e5e59a5-8acd-4c54-a10a-fafd5ddcc685">
        <msh:section createRoles="/Policy/Mis/Disability/Case/Document/RegimeViolationRecord/Create" 
        createUrl="entityParentPrepareCreate-dis_regimeViolation.do?id=${param.id}"
        shortList="entityParentShortList-dis_regimeViolation.do?id=${param.id}"
         title="Нарушение режима" guid="11e46cd9-93cd-44c0-a16d-2470243a0a65">
          <ecom:parentEntityListAll formName="dis_regimeViolationForm" attribute="violation" guid="16363824-17a0-4ba7-9022-720dcb016bad" />
          <msh:table viewUrl="entityShortView-dis_regimeViolation.do" idField="id" name="violation" action="entityParentView-dis_regimeViolation.do" guid="cac74c69-de47-4874-aa5f-a0466d479750">
            <msh:tableColumn columnName="Дата начала" property="dateFrom" guid="cc1f4517-871f-44b9-b614-3dee5bddd607" />
            <msh:tableColumn columnName="Дата окончания" property="dateTo" guid="7a12d9ed-fa3b-49c6-83be-a46de701aded" />
            <msh:tableColumn columnName="Комментарий" property="comment" guid="82b358c5-9acf-4936-90ff-1823d8c2046e" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/Disability/Case/Document/MedSocCommission/View" guid="08b29fe0-78b3-4ade-8a29-5962b37102d2">
        <msh:section title="Медико-социальная экспертная комиссия" guid="15cd2fda-ef53-4af1-b71d-bb9d2b767158">
          <ecom:parentEntityListAll formName="dis_medSocCommissionForm" attribute="medSoc" guid="56302751-44dd-4b75-8d7f-8f66bf0fe577" />
          <msh:table viewUrl="entityShortView-dis_medSocCommission.do" idField="id" name="medSoc" action="entityParentView-dis_medSocCommission.do" guid="99c062d6-25c4-4609-9181-bfa155a7d704">
            <msh:tableColumn columnName="Дата направления" property="assignmentDate" guid="e956f01b-fd81-41f7-ac94-5bc1cc81443a" />
            <msh:tableColumn columnName="Дата регистрации" property="registrationDate" guid="88739c92-a1e1-4c74-b5ef-81f137685a4f" />
            <msh:tableColumn columnName="Дата освидетельствования" property="examinationDate" guid="fb4d52ce-97b4-4b7a-92cd-92d2aa7efee1" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Disability" beginForm="dis_documentForm" guid="116eb2b5-9e8e-45d6-91a4-328b6922bee6" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
	<script type='text/javascript' src='./dwr/interface/DisabilityService.js'></script>
	<script type="text/javascript">
	function printDoc(aTemplate) {
  		DisabilityService.getPrefixForLN({
    		callback: function(aResult) {
    			document.location.href = "print-disability_"+aTemplate+aResult+".do?s=DisabilityService&m=printDocument&id=${param.id}";
    		}
    	}) ;
  		
  	}
	</script>
  <msh:ifFormTypeIsView formName="dis_documentForm">
    <script type="text/javascript">
     if (+$('isClose').checked==true) {
     	$('ALT_3').style.display = 'none' ;
     	$('ALT_7').style.display = 'none' ;
     } else {
    	 $('ALT_6').style.display = 'none' ;
     }
     </script>
     </msh:ifFormTypeIsView>
     <msh:ifFormTypeIsNotView formName="dis_documentForm">
    <script type="text/javascript">
		prevDocumentAutocomplete.setParentId($('disabilityCase').value) ;
	    closeReasonAutocomplete.addOnChangeCallback(function() {
	    	DisabilityService.getCodeByReasonClose($('closeReason').value,{
	    		callback: function(aString) {
	    			if (aString!=null&&aString!=""&&(aString=="32" || aString=="33"||aString=="34"||aString=="36")) {
	    				DisabilityService.getMaxDateToByDisDocument($('id').value,{
	    		    		callback: function(aString1) {
	    		    			if (aString1!=null&&aString1!=""&&aString1!="null") {
	    		   					$('otherCloseDate').value=aString1 ;
	    		    			} else {
	    		    				$('otherCloseDate').value=$('hospitalizedTo').value ;;
	    		    			}
	    		    		}
	    		    	})
	   					$('otherCloseDate').className="required";
	    			} else {
	    				$('otherCloseDate').className="";
	    				$('otherCloseDate').value="";
	    			}
	    		}
	    	})
	    });
	  	idc10Autocomplete.addOnChangeCallback(function() {
	 	 	 if ($('idc10Final').value==""){
	 	 		$('idc10Final').value = $('idc10').value ; 
	 	 		$('idc10FinalName').value = $('idc10Name').value ; 
	 	 	 }
		 });
	  	eventutil.addEventListener($('job'), eventutil.EVENT_KEY_DOWN, 
	  		  	function() {
		  		setJob() ;
	  		  	}) ;
	  	eventutil.addEventListener($('job'), eventutil.EVENT_KEY_UP, 
	  		  	function() {
		  		setJob() ;
	  		  	}) ;
	  	eventutil.addEventListener($('job'), "change", 
	  		  	function() {
	  		  		setJob() ;
	  		  	}) ;
	  	
	  	function setJob() {
	  		$('otherNumberReadOnly').value=$('job').value.substring(0,29).toUpperCase() ;	
	  	}
	  	setJob() ;
	  	

	  	eventutil.addEventListener($('dateFrom'), eventutil.EVENT_KEY_DOWN, 
	  		  	function() {
		  			setPeriod() ;
	  		  	}) ;
	  	eventutil.addEventListener($('dateFrom'), eventutil.EVENT_KEY_UP, 
	  		  	function() {
	  				setPeriod() ;
	  		  	}) ;
	  	eventutil.addEventListener($('dateFrom'), "change", 
	  		  	function() {
	  				setPeriod() ;
	  		  	}) ;
	  	eventutil.addEventListener($('dateFrom'), "blur", 
	  		  	function() {
	  				setPeriod() ;
	  		  	}) ;
	  	eventutil.addEventListener($('dateTo'), eventutil.EVENT_KEY_DOWN, 
	  		  	function() {
		  			setPeriod() ;
	  		  	}) ;
	  	eventutil.addEventListener($('dateTo'), eventutil.EVENT_KEY_UP, 
	  		  	function() {
	  				setPeriod() ;
	  		  	}) ;
	  	eventutil.addEventListener($('dateTo'), "change", 
	  		  	function() {
	  				setPeriod() ;
	  		  	}) ;
	  	eventutil.addEventListener($('dateTo'), "blur", 
	  		  	function() {
	  				setPeriod() ;
	  		  	}) ;
	  	function setPeriod() {
	  		try {
	  			if ($('dateFrom').value.length==10 &&  $('dateTo').value.length==10) {
		  			var dateTo = new Date($('dateTo').value.replace(/(\d+).(\d+).(\d+)/, '$3/$2/$1')+" 12:12:12 GMT +0300") ;
		  			var dateFrom = new Date($('dateFrom').value.replace(/(\d+).(\d+).(\d+)/, '$3/$2/$1')+" 12:12:12 GMT +0300") ;
		  			var one_day=1000*60*60*24 ;
		  			$('infoReadOnly').value=1+((dateTo.getTime()-dateFrom.getTime())/one_day) ;
	  			}
	  		} catch(e) {
	  			//alert('222') ;
	  		}
	  	}
	  	setPeriod() ;
	  	
	 </script>
     </msh:ifFormTypeIsNotView>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="dis_documentForm" guid="70347895-a57d-49ea-a6d5-e634d280f5e7">
      <msh:sideMenu title="Документ нетрудоспобности" guid="c21230e7-e6fa-462b-b0cd-b1305ecd0ade">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-dis_document" name="Изменить" roles="/Policy/Mis/Disability/Case/Document/Edit" guid="d8ee3597-d55e-4f08-a868-c58d8dfc57c4" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-dis_document" name="Удалить" roles="/Policy/Mis/Disability/Case/Document/Delete" guid="4565603e-337e-48eb-82eb-79bd40cd5108" />
        <tags:closeDisDocument reason="closeReason" 
	        roles="/Policy/Mis/Disability/Case/Document/Edit" key="ALT+3" 
	        name="doc" title="Закрыть" otherCloseDate="otherCloseDate"
	        confirm="Вы действительно хотите закрыть текущий документ нетрудоспособности?" 
	        seria='series' number='number' />
        <tags:dis_duplicateDocument roles="/Policy/Mis/Disability/Case/Document/Create" key="ALT+4" 
        	name="duplicate" title="Дубликат (испорчен)" confirm="Вы действительно хотите создать дубликат текущего документа нетрудоспособности?" />
        <tags:dis_workComboDocument roles="/Policy/Mis/Disability/Case/Document/Create" key="ALT+5" 
        	name="workCombo" title="Бланк по совместительству" confirm="Вы действительно хотите создать документ по совместительству на основе текущего документа нетрудоспособности?" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
      	<msh:sideLink  name="шаблон 1" key="ALT+6" action="/javascript:printDoc(1,'.do')"/>
      	<msh:sideLink  name="НЕЗАКРЫТЫЙ шаблон 1" key="ALT+7" action="/javascript:printDoc(1,'.do')"/>
      	<msh:sideLink  name="шаблон 2" key="ALT+8"  action="/javascript:printDoc(2,'.do')"/>
      	<msh:sideLink  name="шаблон 3" action="/javascript:printDoc(3,'.do')"/>
      	<msh:sideLink  name="шаблон 4" action="/javascript:printDoc(4,'.do')"/>
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="c79769a2-8a1c-4c21-ab9c-b7ed71ceb99d">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_record" roles="/Policy/Mis/Disability/Case/Document/Record/Create" name="Продление" guid="0634b894-60e2-4b73-acee-7bf7316a77fc" title="Продлить листок нетрудоспособности" key="CTRL+1" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_regimeViolation" roles="/Policy/Mis/Disability/Case/Document/RegimeViolationRecord/Create" name="Нарушение режима" guid="d9a0ba4a-a68a-4f48-8492-767e911bce80" title="Добавить запись о нарушении режима" key="CTRL+2" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_medSocCommission" roles="/Policy/Mis/Disability/Case/Document/MedSocCommission/Create" name="МСЭК" title="Добавить решение медико-социальной экспертной комиссии" guid="4e09fb92-851a-4547-a12d-c384f63e31cd" key="CTRL+3" />
        
        
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

