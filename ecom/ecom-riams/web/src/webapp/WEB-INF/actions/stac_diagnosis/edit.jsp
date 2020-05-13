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
    <msh:form action="/entityParentSaveGoSubclassView-stac_diagnosis.do" defaultField="establishDate">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="patient" />
      <msh:hidden property="medCase" />
      <msh:panel colsWidth="2% 15% 2% 15% 2% 15% 2%">
        <msh:row>
          <msh:textField property="establishDate" label="Дата установления" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="registrationType" label="Тип регистрации" horizontalFill="true" fieldColSpan="1" vocName="vocDiagnosisRegistrationType" />
          <msh:autoComplete vocName="vocPriorityDiagnosis" property="priority" label="Приоритет" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocIdc10" property="idc10" label="Код МКБ-10" fieldColSpan="3" horizontalFill="true" />
          </msh:row>
        <msh:row>
          <msh:textArea property="name" label="Наименование" fieldColSpan="5" rows="2" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocIdc10" property="backgroundDisease" label="Фоновое заболевание" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:ifFormTypeIsView formName="stac_diagnosisForm">
	        <msh:row>
	          <msh:autoComplete vocName="vocAcuityDiagnosis" property="acuity" label="Острота" horizontalFill="true" />
          	  <msh:autoComplete vocName="vocPrimaryDiagnosis" property="primary" label="Первичность" horizontalFill="true" />
	        </msh:row> 
        </msh:ifFormTypeIsView>
        <msh:row>
          <msh:autoComplete vocName="vocIllnesPrimary" property="illnesPrimary" label="Характер заболевания" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocTraumaType" property="traumaType" label="Тип травмы" fieldColSpan="1" horizontalFill="true" />
          <msh:autoComplete vocName="vocIdc10Travm" property="idc10Reason" label="МКБ-10 причины травмы" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="workFunction" property="medicalWorker" label="Специалист" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="username" label="Пользователь" viewOnlyField="true" />
        </msh:row>
        <msh:row />
        <msh:submitCancelButtonsRow colSpan="4" functionSubmit="showCriteria();"/>
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_diagnosisForm" />
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
    <msh:ifFormTypeIsView formName="stac_diagnosisForm" insideJavascript="false">
      <msh:sideMenu title="Диагноз">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-stac_diagnosis" name="Изменить" roles="/Policy/Mis/MedCase/Diagnosis/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-stac_diagnosis" name="Удалить" roles="/Policy/Mis/MedCase/Diagnosis/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

