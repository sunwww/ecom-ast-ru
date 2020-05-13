<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!--
    	  - Документ нетрудоспособности
    	  -->
  <msh:ifFormTypeIsView formName="dis_documentByPatientForm">
  <script type="text/javascript">
  	window.location.href='entityParentView-dis_document.do?id='+${param.id} ;
  </script>
  </msh:ifFormTypeIsView>
    <msh:form action="/entityParentSaveGoView-dis_documentByPatient.do" defaultField="documentTypeName">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="patient" />
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
          <msh:textField passwordEnabled="false" hideLabel="false" property="issueDate" viewOnlyField="false" horizontalFill="false" />
          <msh:autoComplete vocName="vocDisabilityDocumentPrimarity" property="primarity" label="Первичность" horizontalFill="true" size="20" />
        </msh:row>
        <msh:row>
          <msh:textField property="series" label="Серия"/>
          <msh:textField property="number" label="Номер" size="30" />
          <msh:ifFormTypeIsCreate formName="dis_documentByPatientForm">
            <td><input id="getFreeNumberButton" type="button" onclick="getFreeNumber(this)" value="Получить номер"></td>
            <msh:checkBox property="ELN" label="Электронный"/>
          </msh:ifFormTypeIsCreate>
        </msh:row>

        <msh:row>
          <msh:autoComplete parentId="dis_documentByPatientForm.disabilityCase" vocName="disabilityDocumentByCase" property="prevDocument" label="Предыдущий документ" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:checkBox fieldColSpan="3" property="placementService" label="Состоит на учете в службе занятости"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="job" fieldColSpan="3" label="Место работы" horizontalFill="true"/>
        </msh:row>

        <msh:ifFormTypeIsNotView formName="dis_documentByPatientForm">
        	<msh:row>
        		<msh:textField property="otherNumber" fieldColSpan="3" horizontalFill="true"
        			label="Место рабо (29 сим.)" viewOnlyField="true"/>

        	</msh:row>
	        <msh:row>
	        	<msh:checkBox property="isUpdateWork" fieldColSpan="3" horizontalFill="Обновить сокращенное название организации"/>
	        </msh:row>
        </msh:ifFormTypeIsNotView>

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
        	<msh:separator label="Больной по уходу" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete horizontalFill="true" parentId="dis_documentByPatientForm.patient" vocName="kinsmanByDis"
        		property="nursingPerson1" label="Лицо по уходу 1" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete horizontalFill="true" parentId="dis_documentByPatientForm.patient" vocName="kinsmanByDis"
        		property="nursingPerson2" label="Лицо по уходу 2" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
          <msh:separator label="Период нетрудоспособности" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textField property="dateFrom" label="Дата начала" />
          <msh:textField property="dateTo" label="Дата окончания" />
        </msh:row>
        <msh:ifFormTypeIsNotView formName="dis_documentByPatientForm">
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
        	<msh:textField property="sanatoriumOgrn" label="ОГРН санатория или клиники НИИ" labelColSpan="2" fieldColSpan="2" horizontalFill="true"/>
        </msh:row>
        <msh:row>
          <msh:separator label="Беременность" colSpan="4" />
        </msh:row>
        <msh:row>
        	<msh:checkBox property="earlyPregnancyRegistration"  label="Поставлена на учет в ранние сроки беременности (до 12 недель)" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
          <msh:separator label="Закрытие" colSpan="4" />
        </msh:row>

        <msh:row>
          <msh:autoComplete vocName="vocDisabilityDocumentCloseReason" property="closeReason" label="Причина закрытия" horizontalFill="true" size="40" />
        </msh:row>
        <msh:row>
        	<msh:textField property="otherCloseDate" label="Иная дата закрытия для причин 32, 33, 34, 36" labelColSpan="3"/>
        </msh:row>
        <msh:row>
          <msh:submitCancelButtonsRow colSpan="3" />
        </msh:row>
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Disability" beginForm="dis_documentByPatientForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string" >
	<script type='text/javascript' src='./dwr/interface/DisabilityService.js'></script>
    <msh:ifFormTypeIsNotView formName="dis_documentByPatientForm">

    <script type="text/javascript">
        function getFreeNumber (aButton){
            DisabilityService.getSnils(${param.id},{
                callback: function (snils) {
                    if(snils==null || snils=='1' || snils==''){ alert("У пациента отсутствует СНИЛС!\nДобавьте СНИЛС и попробуйте ещё раз!");
                    }else{
                        aButton.value="Подождите...";
                        aButton.disabled=true;
                        if ($('number').value!="") {
                            alert ("Поле \"Номер\" уже заполнено");
                            return;
                        }
                        DisabilityService.getFreeNumberForDisabilityDocument({
                            callback: function (num) {
                                if (num!=null&&num!="") {
                                    $('number').value=num;
                                    $('number').className="viewOnly";
                                    aButton.style.display="none";
                                    var a = document.getElementById('ELN');
                                    a.checked=true;
                                } else {
                                    alert ("Не удалось получить номер больничного листа");
                                }

                            }
                        });}
                }
            });
        }

    closeReasonAutocomplete.addOnChangeCallback(function() {
    	DisabilityService.getCodeByReasonClose($('closeReason').value,{
    		callback: function(aString) {
    			if (aString!=null&&aString!=""&&(aString=="32" || aString=="33"||aString=="34"||aString=="36")) {
    				$('otherCloseDate').value=$('dateTo').value ;
    		    	$('otherCloseDate').className="required";
    			} else {
    				$('otherCloseDate').className="";
    				$('otherCloseDate').value="" ;
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
  	regimeAutocomplete.addOnChangeCallback(function() {
	 	 if ($('regimeName').value.indexOf('САНАТОР')!=-1) {
	 		 $('sanatoriumDateFrom').value = $('dateFrom').value;
	 		 $('sanatoriumDateTo').value = $('dateTo').value;
	 		 $('hospitalizedFrom').value = "";
	 		 $('hospitalizedTo').value = "";

	 	 }
	 	 if ($('regimeName').value.indexOf('СТАЦИОНАР')!=-1) {
	 		 $('hospitalizedFrom').value = $('dateFrom').value;
	 		 $('hospitalizedTo').value = $('dateTo').value;
	 		 $('sanatoriumDateFrom').value = "";
	 		 $('sanatoriumDateTo').value = "";
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
  	function setJob() {
  		$('otherNumberReadOnly').value=$('job').value.substring(0,29).toUpperCase() ;
  	}

  	setJob() ;
  	setPeriod() ;

  	</script>
     </msh:ifFormTypeIsNotView>
  </tiles:put>
  <tiles:put name="side" type="string" />
</tiles:insert>

