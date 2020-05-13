<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_newBornInfRiskEstimationForm">
      <msh:sideMenu title="Осмотр">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_newBornInfRiskEstimation" name="Изменить" roles="/Policy/Mis/Inspection/NewBornInfRiskEstimation/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_newBornInfRiskEstimation" name="Удалить" roles="/Policy/Mis/Inspection/NewBornInfRiskEstimation/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Оценка риска бактериальной инфекции у новорожденных
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_newBornInfRiskEstimation.do" defaultField="inspectionDate">
      <msh:hidden property="id" />
      <msh:hidden property="medCase" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row>
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row>
          <msh:textField property="inspectionDate" label="Дата осмотра" />
          <msh:textField passwordEnabled="false" hideLabel="false" property="inspectionTime" viewOnlyField="false" label="Время" horizontalFill="false" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="waterlessDuration" vocName="vocInfRiskWaterless" fieldColSpan="3" horizontalFill="true" size="50" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="motherTemperature" vocName="vocInfRiskMotherTemperature" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="waterNature" vocName="vocInfRiskWaterNature" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="apgar" vocName="vocInfRiskApgar" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="newBornWeight" vocName="vocInfRiskNewBornWeight" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="motherInfectiousDiseases" vocName="vocInfRiskMotherDiseases" label="Хрон.очаги инфекции матери" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="commonMark" label="Общая оценка" viewOnlyField="true" horizontalFill="true" fieldColSpan="3" vocName="vocDownesCommonMark"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete property="workFunction" label="Специалист" horizontalFill="true" fieldColSpan="3" vocName="workFunction" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeIsNotView formName="preg_newBornInfRiskEstimationForm">
  	<script type="text/javascript" src="./dwr/interface/PregnancyService.js"></script>
  	<script type="text/javascript">
  	
	
  	theFields = new Array(6) ;
    theFields[0] = waterlessDurationAutocomplete ;
    theFields[1] = motherTemperatureAutocomplete ;
    theFields[2] = waterNatureAutocomplete ;
    theFields[3] = apgarAutocomplete ;
    theFields[4] = newBornWeightAutocomplete ;
    theFields[5] = motherInfectiousDiseasesAutocomplete ;

    for(var i=0; i<theFields.length; i++) {
        theFields[i].addOnChangeCallback(function() {
      	 	changeCommonMark() ;
      	 });
    }


  	  	

  	function changeCommonMark() {
  		PregnancyService.calcInfRiskEstimation(
  			$('waterlessDuration').value, $('motherTemperature').value
  			, $('waterNature').value, $('apgar').value
  			, $('newBornWeight').value, $('motherInfectiousDiseases').value, {
  				callback: function(aResult) {
  					var del1 = aResult.indexOf('#') ;
  					var del2 = aResult.indexOf('#',del1+1) ;
  					var del3 = aResult.indexOf('#',del2+1) ;
  					var ball = aResult.substring(0,del1) ;
  					var idmes = aResult.substring(del1+1,del2) ;
  					var mes = aResult.substring(del2+1,del3) ;
  					var per = aResult.substring(del3+1) ;
  					$('commonMark').value = idmes ;
  					$('commonMarkReadOnly').value = per+" "+mes ;
  				},
  				errorHandler: function(aMessage){
  					alert("Error: "+aMessage);
  				}
  			}
  		) ;
  	}
  	changeCommonMark() ;
  	
  	</script>
  	  	</msh:ifFormTypeIsNotView>
  </tiles:put>
  
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_newBornInfRiskEstimationForm" />
  </tiles:put>
</tiles:insert>

