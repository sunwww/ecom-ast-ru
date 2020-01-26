<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoSubclassView-stac_diagnosis.do" defaultField="establishDate">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="patient" />
      <msh:hidden guid="hiddenParent" property="medCase" />
      <msh:panel guid="panel" colsWidth="2% 15% 2% 15% 2% 15% 2%">
        <msh:row guid="row1">
          <msh:textField guid="textFieldHello" property="establishDate" label="Дата установления" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="registrationType" label="Тип регистрации" horizontalFill="true" fieldColSpan="1" vocName="vocDiagnosisRegistrationType" guid="1ecf26b7-d071-4abc-93ae-c52af4ae368b" />
          <msh:autoComplete vocName="vocPriorityDiagnosis" property="priority" label="Приоритет" guid="e28f35fc-fe25-4968-bf2f-d1fe4661349e" horizontalFill="true" />
        </msh:row>
        <msh:row guid="cfba9b91-b2af-4867-aab3-29a1f39833fd">
          <msh:autoComplete vocName="vocIdc10" property="idc10" label="Код МКБ-10" guid="e36df3bf-fe77-4096-a082-51016fc2baad" fieldColSpan="3" horizontalFill="true" />
          </msh:row>
        <msh:row guid="fb31a065-5f7f-4b11-b1b5-0f336254b9fd">
          <msh:textArea property="name" label="Наименование" guid="c0a86a5e-34ff-46f3-984b-5ecbd2749760" fieldColSpan="5" rows="2" horizontalFill="true" />
        </msh:row>
        <msh:row guid="fb31a065-5f7f-4b11-b1b5-0f336254b9fd">
        	<msh:autoComplete vocName="vocIdc10" property="backgroundDisease" label="Фоновое заболевание" guid="e36df3bf-fe77-4096-a082-51016fc2baad" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:ifFormTypeIsView formName="stac_diagnosisForm">
	        <msh:row>
	          <msh:autoComplete vocName="vocAcuityDiagnosis" property="acuity" label="Острота" guid="21cf4876-83b6-457c-be68-f186d986c130" horizontalFill="true" />
          	  <msh:autoComplete vocName="vocPrimaryDiagnosis" property="primary" label="Первичность" guid="8efc75d0-c5c7-4a5d-856f-d043968e1429" horizontalFill="true" />
	        </msh:row> 
        </msh:ifFormTypeIsView>
        <msh:row>
          <msh:autoComplete vocName="vocIllnesPrimary" property="illnesPrimary" label="Характер заболевания" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row guid="313f2ba2-1321-4ea9-85d8-c5b17b37f6fc">
        </msh:row>
        <msh:row guid="921d61b3-ef1e-40b7-8275-c5c9c755ce89">
          <msh:autoComplete vocName="vocTraumaType" property="traumaType" label="Тип травмы" guid="2bc6b8ef-acc0-4465-88d9-7ee26b5cbd3e" fieldColSpan="1" horizontalFill="true" />
          <msh:autoComplete vocName="vocIdc10Travm" property="idc10Reason" label="МКБ-10 причины травмы" guid="459b7b01-929f-440a-8cb8-e6e9340fd958" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="921d61b-1e-40b7-8275-c5c9c755ce89">
          <msh:autoComplete vocName="workFunction" property="medicalWorker" label="Специалист" guid="459b7b01-929f-440a-8cb8-e6e9340fd958" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="921d61b-1e-40b7-8275-c5c9c755ce89">
          <msh:textField property="username" label="Пользователь" viewOnlyField="true" guid="962a2bab-81e2-43f8-871f-12cf8921be43" />
        </msh:row>
        <msh:row guid="26f25ba9-b90a-4825-b37f-a24f1ecf67ff" />
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" functionSubmit="showCriteria();"/>
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="stac_diagnosisForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
      <tags:CreateDiagnoseCriteria name="CreateDiagnoseCriteria" />
  	<msh:ifFormTypeIsNotView formName="stac_diagnosisForm">
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
        <msh:ifInRole roles="/Policy/Mis/Order203">
            <script type="text/javascript">
            function showCriteria() {
            showCreateDiagnoseCriteriaCloseDocument($(idc10).value,$('registrationType').value,$('priority').value, document.forms[0],$(medCase).value,true);
            //document.forms[0].submit() ;
            }
            </script>
        </msh:ifInRole>
        <msh:ifNotInRole roles="/Policy/Mis/Order203">
            <script type="text/javascript">
                function showCriteria() {
                    //showCreateDiagnoseCriteriaCloseDocument($(idc10).value,$('registrationType').value,$('priority').value, document.forms[0]);
                    document.forms[0].submit() ;
                }
            </script>
        </msh:ifNotInRole>
  	</msh:ifFormTypeIsNotView>
  	<msh:ifFormTypeIsCreate formName="stac_diagnosisForm">
  		<msh:ifInRole roles="/Policy/Mis/MedCase/Diagnosis/Accoucheur">
		    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
  			<script type="text/javascript">
  				
  				HospitalMedCaseService.getTypeDiagByAccoucheur( {
	                    callback: function(aResult) {
	                    	
	                        var ind1 = aResult.indexOf('#') ;
	                        var ind2 = aResult.indexOf('#',ind1+1) ;
	                        var ind3 = aResult.indexOf('#',ind2+1) ;
	                        var ind4 = aResult.indexOf('#',ind3+1) ;
	                        var ind5 = aResult.indexOf('#',ind4+1) ;
	                        
	                        $('primary').value = aResult.substring(0,ind1) ;
	                        $('primaryName').value = aResult.substring(ind1+1,ind2) ;
	                        $('acuity').value = aResult.substring(ind2+1,ind3) ;
	                        $('acuityName').value = aResult.substring(ind3+1,ind4) ;
	                        $('registrationType').value = aResult.substring(ind4+1,ind5) ;
	                        $('registrationTypeName').value = aResult.substring(ind5+1) ;
	                        
	                        
	                    }
	                });
  			</script>
  		</msh:ifInRole>
  	</msh:ifFormTypeIsCreate>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_diagnosisForm" insideJavascript="false" guid="67f5ad3b-91bf-4752-85bf-722efba2b3dc">
      <msh:sideMenu guid="sideMenu-123" title="Диагноз">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-stac_diagnosis" name="Изменить" roles="/Policy/Mis/MedCase/Diagnosis/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-stac_diagnosis" name="Удалить" roles="/Policy/Mis/MedCase/Diagnosis/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

