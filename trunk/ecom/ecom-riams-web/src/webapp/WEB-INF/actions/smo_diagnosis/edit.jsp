<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoSubclassView-smo_diagnosis.do" defaultField="idc10Name">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="medCase" />
      <msh:panel guid="panel" colsWidth="20% 20% 15%">
        <msh:row guid="row1">
          <msh:textField guid="textFieldHello" property="establishDate" label="Дата установления диагноза" />
          <msh:textField property="accurationDate" label="Дата уточнения диагноза" guid="586db881-d591-45af-a522-d5f4146bde73" />
        </msh:row>
        <msh:row guid="cfba9b91-b2af-4867-aab3-29a1f39833fd">
          <msh:autoComplete vocName="vocIdc10" property="idc10" label="Код МКБ-10" guid="e36df3bf-fe77-4096-a082-51016fc2baad" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="fb31a065-5f7f-4b11-b1b5-0f336254b9fd">
          <msh:textArea horizontalFill="true" property="name" label="Наименование" guid="c0a86a5e-34ff-46f3-984b-5ecbd2749760" fieldColSpan="3" rows="2" />
        </msh:row>
        <msh:row guid="07fea0ed-ebcd-4cd7-85a7-23c229125b85">
          <msh:autoComplete vocName="vocAcuityDiagnosis" property="acuity" label="Острота" guid="21cf4876-83b6-457c-be68-f186d986c130" horizontalFill="true" />
          <msh:autoComplete vocName="vocPriorityDiagnosis" property="priority" label="Приоритет" guid="e28f35fc-fe25-4968-bf2f-d1fe4661349e" horizontalFill="true" />
        </msh:row>
        <msh:row guid="313f2ba2-1321-4ea9-85d8-c5b17b37f6fc">
          <msh:autoComplete vocName="vocPrimaryDiagnosis" property="primary" label="Первичность" guid="8efc75d0-c5c7-4a5d-856f-d043968e1429" horizontalFill="true" />
          <msh:checkBox property="prophylacticExamination" label="Выявлен при профосмотре" guid="7db11bbf-480e-4be0-9e96-5d8bbcd4fcd8" />
        </msh:row>
        <msh:row guid="61966961-9aee-416e-86d4-40be76f573d5">
          <msh:autoComplete vocName="diagnosisByDiagnosisSpo" property="diagnosisPrior" label="Предыдущий диагноз" guid="7205fe08-a2bb-42d1-8323-f6e757ba0e13" horizontalFill="true" fieldColSpan="3" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="221d9e3f-6b24-4293-a943-1db2ed538d77">
          <msh:autoComplete vocName="diagnosisByDiagnosisSpo" property="diagnosisNext" label="Заменен диагнозом" guid="085e666c-098f-473e-adf4-7a88de13d383" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="2ve3f-6b24-4293-a943-1dsd77">
        <msh:autoComplete vocName="omcRoadTrafficInjury" property="roadTrafficInjury" label="Признак ДТП" guid="2bzef-acc0-4465-8zd9-7z6b5cbd3e" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:autoComplete vocName="vocTraumaType" property="traumaType" label="Тип травмы" guid="2bc6b8ef-acc0-4465-88d9-7ee26b5cbd3e" fieldColSpan="3" horizontalFill="true" />
        <msh:row guid="921d61b3-ef1e-40b7-8275-c5c9c755ce89">
          <msh:autoComplete vocName="vocIdc10" property="idc10Reason" label="МКБ-10 причины травмы" guid="459b7b01-929f-440a-8cb8-e6e9340fd958" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="26f25ba9-b90a-4825-b37f-a24f1ecf67ff" />
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
	<tiles:put name="javascript" type="string">
	  	<script type="text/javascript">
	  	idc10Autocomplete.addOnChangeCallback(function() {
		      	 	setDiagnosisText('idc10','name');
		    });
	  		function setDiagnosisText(aFieldMkb,aFieldText) {
	  			var val = $(aFieldMkb+'Name').value ;
	  			var ind = val.indexOf(' ') ;
	  			//alert(ind+' '+val)
	  			if (ind!=-1) {
	  				if ($(aFieldText).value=="") $(aFieldText).value=val.substring(ind+1) ;
	  			}
	  		}
	  	</script>
	  </tiles:put>  
	  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="smo_diagnosisForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_diagnosisForm" insideJavascript="false" guid="67f5ad3b-91bf-4752-85bf-722efba2b3dc">
      <msh:sideMenu guid="sideMenu-123">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-smo_diagnosis" name="Изменить" roles="/Policy/Mis/MedCase/Diagnosis/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-smo_diagnosis" name="Удалить" roles="/Policy/Mis/MedCase/Diagnosis/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

