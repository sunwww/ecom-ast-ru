<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Документ нетрудоспособности
    	  -->
    <msh:form action="/entityParentSaveGoView-dis_document.do" defaultField="documentTypeName">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="disabilityCase" />
      <msh:panel>
        <msh:row>
          <msh:autoComplete vocName="mainLpu" property="anotherLpu" label="Другое лечебное учреждение" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocDisabilityDocumentType" property="documentType" label="Документ" fieldColSpan="3" horizontalFill="true" />
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
          <msh:textField passwordEnabled="false" hideLabel="false" property="issueDate" viewOnlyField="false" horizontalFill="false" />
          <msh:autoComplete vocName="vocDisabilityDocumentPrimarity" property="primarity" label="Первичность" horizontalFill="true" size="20" />
        </msh:row>
        <msh:row>
          <msh:textField property="series" label="Серия" />
          <msh:textField property="number" label="Номер"  fieldColSpan="50" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="disabilityDocumentByCase" property="prevDocument" label="Предыдущий документ" fieldColSpan="3" horizontalFill="true" />
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
	        <msh:row>
	          <msh:separator label="Период нетрудоспособности" colSpan="4" />
	        </msh:row>
	        <msh:row>
	          <msh:textField property="dateFrom" label="Дата начала" />
	          <msh:textField property="dateTo" label="Дата окончания" />
	        </msh:row>
        <msh:ifFormTypeIsNotView formName="dis_documentForm">
        	<msh:row>
        		<msh:textField property="info" labelColSpan="2" fieldColSpan="2" horizontalFill="true"
        			label="Количество дней нетрудоспособности" viewOnlyField="true"/>
        		
        	</msh:row>
        </msh:ifFormTypeIsNotView>        
	        
	        <msh:row>
	          <msh:autoComplete vocName="vocDisabilityRegime" property="regime" label="Режим" fieldColSpan="3" horizontalFill="true" />
	        </msh:row>
	        <msh:row>
	          <msh:autoComplete showId="false" vocName="workFunction" hideLabel="false" property="workFunction" viewOnlyField="false" label="Леч.врач" fieldColSpan="3" horizontalFill="true" />
	        </msh:row>
	        <msh:row>
	          <msh:autoComplete showId="false" vocName="workFunction" hideLabel="false" property="workFunctionAdd" viewOnlyField="false" label="Председ. ВК" fieldColSpan="3" horizontalFill="true" />
	        </msh:row>
        </msh:ifFormTypeIsCreate>



        <msh:row>
          <msh:separator label="Совместительство" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocCombo" property="workComboType" label="Тип совместительства" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="mainWorkDocumentSeries" label="Серия по основному месту работы" />
          <msh:textField property="mainWorkDocumentNumber" label="номер" />
        </msh:row>
        <msh:row>
          <msh:separator label="Причина нетрудоспособности" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocDisabilityReason" property="disabilityReason" label="Причина нетруд." fieldColSpan="3" horizontalFill="true" />
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
          <msh:separator label="Санаторное лечение" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textField property="sanatoriumDateFrom" label="Дата начала (пред.родов)" />
          <msh:textField property="sanatoriumDateTo" label="Дата окончания" />
        </msh:row>
        <msh:row>
          <msh:textField property="sanatoriumTicketNumber" label="Номер путевки" />
        </msh:row>
        <msh:row>
          <msh:textField property="sanatoriumPlace" label="Место нахождения санатория" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:textField property="sanatoriumOgrn" label="ОГРН санатория или клиники НИИ" fieldColSpan="2" horizontalFill="true"/>
        </msh:row>

        <msh:row>
          <msh:separator label="Закрытие" colSpan="4" />
        </msh:row>
        <msh:row>
    	      <msh:autoComplete vocName="vocDisabilityDocumentCloseReason" property="closeReason" label="Причина закрытия" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
       <msh:row>
        	<msh:textField property="otherCloseDate" label="Иная дата закрытия для причин 32, 33, 34, 36" labelColSpan="3"/>
        </msh:row>
        <msh:row>
          <msh:checkBox hideLabel="false" property="noActuality" viewOnlyField="true" horizontalFill="false" label="Испорчен" />

         	<msh:ifInRole roles="/Policy/Mis/Disability/Case/Document/Reopen"> 
        		<msh:ifFormTypeIsNotView formName="dis_documentForm">
        			<msh:checkBox property="isClose" label="Документ закрыт"  />
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
        <msh:row>
          <msh:submitCancelButtonsRow colSpan="4" />
        </msh:row>
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="dis_documentForm">
    	<ecom:webQuery name="duplicateDocument" nativeSql="select dd.id,dd.series|| ' '||dd.number from disabilitydocument dd where dd.duplicate_id=${param.id }"/>
    	<msh:section title="Данный документ заменил документ">
    		<msh:table name="duplicateDocument" 
    			action="entityView-dis_document.do"
    			viewUrl="entityShortView-dis_document.do" 
    			hideTitle="true" idField="1">
    			<msh:tableColumn property="2" columnName="номер"/>

    		</msh:table>
    	</msh:section>
      <msh:ifInRole roles="/Policy/Mis/Disability/Case/Document/Record/View">
        <msh:section title="Продление"
        	createRoles="/Policy/Mis/Disability/Case/Document/Record/Create" createUrl="entityParentPrepareCreate-dis_record.do?id=${param.id}">
          <ecom:parentEntityListAll formName="dis_recordForm" attribute="disabilityRecord" />
          <msh:table editUrl="entityParentEdit-dis_record.do" viewUrl="entityShortView-dis_record.do" name="disabilityRecord" action="entityParentView-dis_record.do" idField="id">
            <msh:tableColumn columnName="Дата начала" property="dateFrom" />
            <msh:tableColumn columnName="Дата окончания" property="dateTo" />
            <msh:tableColumn columnName="Леч.врач" identificator="false" property="workFunctionInfo"  />
            <msh:tableColumn columnName="Председ. ВК" identificator="false" property="workFunctionAddInfo"  />
            <msh:tableColumn columnName="Режим" identificator="false" property="regimeText" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/Disability/Case/Document/RegimeViolationRecord/View">
        <msh:section createRoles="/Policy/Mis/Disability/Case/Document/RegimeViolationRecord/Create" 
        createUrl="entityParentPrepareCreate-dis_regimeViolation.do?id=${param.id}"
        shortList="entityParentShortList-dis_regimeViolation.do?id=${param.id}"
         title="Нарушение режима">
          <ecom:parentEntityListAll formName="dis_regimeViolationForm" attribute="violation" />
          <msh:table viewUrl="entityShortView-dis_regimeViolation.do" idField="id" name="violation" action="entityParentView-dis_regimeViolation.do">
            <msh:tableColumn columnName="Дата начала" property="dateFrom" />
            <msh:tableColumn columnName="Дата окончания" property="dateTo" />
            <msh:tableColumn columnName="Комментарий" property="comment" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/Disability/Case/Document/MedSocCommission/View">
        <msh:section title="Медико-социальная экспертная комиссия">
          <ecom:parentEntityListAll formName="dis_medSocCommissionForm" attribute="medSoc" />
          <msh:table viewUrl="entityShortView-dis_medSocCommission.do" idField="id" name="medSoc" action="entityParentView-dis_medSocCommission.do">
            <msh:tableColumn columnName="Дата направления" property="assignmentDate" />
            <msh:tableColumn columnName="Дата регистрации" property="registrationDate" />
            <msh:tableColumn columnName="Дата освидетельствования" property="examinationDate" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Disability" beginForm="dis_documentForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
	<script type='text/javascript' src='./dwr/interface/DisabilityService.js'></script>
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
    <msh:ifFormTypeIsView formName="dis_documentForm">
      <msh:sideMenu title="Документ нетрудоспобности">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-dis_document" name="Изменить" roles="/Policy/Mis/Disability/Case/Document/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-dis_document" name="Удалить" roles="/Policy/Mis/Disability/Case/Document/Delete" />
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
      	<msh:sideLink params="id" 
      	name="шаблон 1" key="ALT+6" 
      	action="/print-disability_1.do?s=DisabilityService&amp;m=printDocument"/>
      	<msh:sideLink params="id" 
      	name="НЕЗАКРЫТЫЙ шаблон 1" key="ALT+7" 
      	action="/print-disability_1.do?s=DisabilityService&amp;m=printDocument"/>
      	<msh:sideLink params="id" 
      	name="шаблон 2" key="ALT+8" 
      	action="/print-disability_2.do?s=DisabilityService&amp;m=printDocument"/>
      	<msh:sideLink params="id" 
      	name="шаблон 3"  
      	action="/print-disability_3.do?s=DisabilityService&amp;m=printDocument"/>
      	<msh:sideLink params="id" 
      	name="шаблон 4"  
      	action="/print-disability_4.do?s=DisabilityService&amp;m=printDocument"/>
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_record" roles="/Policy/Mis/Disability/Case/Document/Record/Create" name="Продление" title="Продлить листок нетрудоспособности" key="CTRL+1" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_regimeViolation" roles="/Policy/Mis/Disability/Case/Document/RegimeViolationRecord/Create" name="Нарушение режима" title="Добавить запись о нарушении режима" key="CTRL+2" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_medSocCommission" roles="/Policy/Mis/Disability/Case/Document/MedSocCommission/Create" name="МСЭК" title="Добавить решение медико-социальной экспертной комиссии" key="CTRL+3" />
        
        
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

