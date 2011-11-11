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
  <msh:ifFormTypeIsView formName="dis_caseShortByPatientForm">
  <script type="text/javascript">
  	window.location.href='entityParentView-dis_document.do?id='+${param.id} ;
  </script>
  </msh:ifFormTypeIsView>
    <msh:form guid="formHello" action="/entityParentSaveGoView-dis_caseShortByPatient.do" defaultField="documentTypeName">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="patient" />
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
          <msh:textField passwordEnabled="false" hideLabel="false" property="issueDate" viewOnlyField="false" guid="7a444864-9b79-4e21-b218-11989c5d4c98" horizontalFill="false" />
          <msh:autoComplete vocName="vocDisabilityDocumentPrimarity" property="primarity" label="Первичность" guid="2e7aa7a4-336c-4831-b3d9-97d6f64d2ef1" horizontalFill="true" size="20" />
        </msh:row>
        <msh:row>
          <msh:textField property="number" label="Номер" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox fieldColSpan="3" property="placementService" label="Состоит на учете в службе занятости"/>
        </msh:row>

        <msh:row guid="78e547a9-eb93-4f32-b406-c90227deb286">
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
        <msh:row guid="3972e779-80b6-45cb-8004-df6bcb22da38">
          <msh:separator label="Период нетрудоспособности" colSpan="4" guid="819b1c93-689a-404c-bd28-c18025b03fe4" />
        </msh:row>
        <msh:row>
          <msh:textField property="dateFrom" label="Дата начала" guid="71bb6108-4449-460b-aaca-0c7419683133" />
          <msh:textField property="dateTo" label="Дата окончания" guid="31e70e41-3526-4a9e-b746-263d6e81e657" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocDisabilityRegime" property="regime" label="Режим" guid="a0252f86-792b-4992-a278-5cb0d1a1bc27" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" vocName="workFunction" hideLabel="false" property="workFunction" viewOnlyField="false" label="Леч.врач" guid="baeb2ab7-b6c6-4b61-b05b-a939fa32af9a" fieldColSpan="3" horizontalFill="true" />
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
        <msh:row guid="ef70bf08-f8aa-4283-ae1d-fcc5fa8692de">
          <msh:separator label="Закрытие" colSpan="4" guid="c03fc1ea-c2ab-472e-8d52-3f70b7efbd08" />
        </msh:row>      
        <msh:row guid="e46a3-e51c-46ef-9a21-6bvb60831">
          <msh:autoComplete vocName="vocDisabilityDocumentCloseReason" property="closeReason" label="Причина закрытия" guid="c425f-265a-40ab-9581-a8ff" horizontalFill="true" size="40" />
        </msh:row> 
        <msh:row guid="685ad8f8-f93c-4bd6-98b5-a1618944cb07">
          <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
        </msh:row>
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Disability" beginForm="dis_caseShortByPatientForm" guid="116eb2b5-9e8e-45d6-91a4-328b6922bee6" />
  </tiles:put>
  <tiles:put name="javascript" type="string" >
    <msh:ifFormTypeIsNotView formName="dis_caseShortByPatientForm">
    <script type="text/javascript">
  	idc10Autocomplete.addOnChangeCallback(function() {
 	 	 if ($('idc10Final').value==""){
 	 		$('idc10Final').value = $('idc10').value ; 
 	 		$('idc10FinalName').value = $('idc10Name').value ; 
 	 	 }
	 });
  	regimeAutocomplete.addOnChangeCallback(function() {
	 	 if ($('regimeName').value.indexOf('САНАТОР')!=-1) {
	 		 //$('sanatoriumDateFrom').value = $('dateFrom').value;
	 		 //$('sanatoriumDateTo').value = $('dateTo').value;
	 		 $('hospitalizedFrom').value = "";
	 		 $('hospitalizedTo').value = "";
	 		 
	 	 }
	 	 if ($('regimeName').value.indexOf('СТАЦИОНАР')!=-1) {
	 		 $('hospitalizedFrom').value = $('dateFrom').value;
	 		 $('hospitalizedTo').value = $('dateTo').value;
	 		 //$('sanatoriumDateFrom').value = "";
	 		 //$('sanatoriumDateTo').value = "";
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
  		//$('otherNumberReadOnly').value=$('job').value.substring(0,29).toUpperCase() ;	
  	}
  	setJob() ;
  	</script>
     </msh:ifFormTypeIsNotView>
  </tiles:put>
  <tiles:put name="side" type="string" />
</tiles:insert>