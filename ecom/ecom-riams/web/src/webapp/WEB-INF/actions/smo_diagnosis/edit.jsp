<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entityParentSaveGoSubclassView-smo_diagnosis.do" defaultField="idc10Name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="medCase" />
      <msh:panel colsWidth="20% 20% 15%">
        <msh:row>
          <msh:textField property="establishDate" label="Дата установления диагноза" />
          <msh:textField property="accurationDate" label="Дата уточнения диагноза" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocIdc10" property="idc10" label="Код МКБ-10" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textArea horizontalFill="true" property="name" label="Наименование" fieldColSpan="3" rows="2" />
        </msh:row>
        <msh:ifFormTypeIsView formName="smo_diagnosisForm">
	        <msh:row>
	          <msh:autoComplete vocName="vocAcuityDiagnosis" property="acuity" label="Острота" horizontalFill="true" />
          <msh:autoComplete vocName="vocPrimaryDiagnosis" property="primary" label="Первичность" horizontalFill="true" />
	        </msh:row> 
        </msh:ifFormTypeIsView>
        <msh:row>
          <msh:autoComplete vocName="vocIllnesPrimary" property="illnesPrimary" label="Характер заболевания" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
	          <msh:autoComplete vocName="vocPriorityDiagnosis" property="priority" label="Приоритет" horizontalFill="true" />
          <msh:checkBox property="prophylacticExamination" label="Выявлен при профосмотре" />
          </msh:row>
          <msh:row><td></td><td></td>
          <msh:checkBox property="isFoundAtherosclerosis" label="Выявлен атеросклероз БЦА" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="diagnosisByDiagnosisSpo" property="diagnosisPrior" label="Предыдущий диагноз" horizontalFill="true" fieldColSpan="3" viewOnlyField="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="diagnosisByDiagnosisSpo" property="diagnosisNext" label="Заменен диагнозом" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" />
        </msh:row>
        <msh:row>
        <msh:autoComplete vocName="omcRoadTrafficInjury" property="roadTrafficInjury" label="Признак ДТП" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:autoComplete vocName="vocTraumaType" property="traumaType" label="Тип травмы" fieldColSpan="3" horizontalFill="true" />
        <msh:row>
          <msh:autoComplete vocName="vocIdc10" property="idc10Reason" label="МКБ-10 причины травмы" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительно" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
          <msh:label property="createUsername" label="Пользователь" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
          	<msh:label property="editUsername" label="Пользователь" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" />
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
    <ecom:titleTrail mainMenu="Patient" beginForm="smo_diagnosisForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_diagnosisForm" insideJavascript="false">
      <msh:sideMenu>
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-smo_diagnosis" name="Изменить" roles="/Policy/Mis/MedCase/Diagnosis/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-smo_diagnosis" name="Удалить" roles="/Policy/Mis/MedCase/Diagnosis/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

