<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_apgarEstimationForm">
      <msh:sideMenu title="Осмотр">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_apgarEstimation" name="Изменить" roles="/Policy/Mis/Inspection/ApgarEstimation/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_apgarEstimation" name="Удалить" roles="/Policy/Mis/Inspection/ApgarEstimation/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Оценка состояния новорожденного по шкале Апгар
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_apgarEstimation.do" defaultField="inspectionDate">
      <msh:hidden property="id" />
      <msh:hidden property="medCase" />
      <msh:hidden property="saveType" />
      <msh:panel colsWidth="5%,5%,5%,30%">
        <msh:row>
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row>
          <msh:textField property="inspectionDate" label="Дата осмотра" />
          <msh:textField property="inspectionTime" label="Время" />
        </msh:row>
        <msh:row>
          <msh:textField property="postNatalTime" label="Время после рождения (мин)" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="palpitation" vocName="vocApgarPalpitation" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="respiration" vocName="vocApgarRespiration" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="skinColor" vocName="vocApgarSkinColor" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="muscleTone" vocName="vocApgarMuscleTone" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="reflexes" vocName="vocApgarReflexes" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="commonMark" viewOnlyField="true" label="Общая оценка (балл)" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="workFunction" label="Специалист" horizontalFill="true" fieldColSpan="3" vocName="workFunction" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_apgarEstimationForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeIsNotView formName="preg_apgarEstimationForm">
  	<script type="text/javascript" src="./dwr/interface/PregnancyService.js"></script>
  	<script type="text/javascript">
	changeCommonMark() ;
  	
  	theFields = new Array(5) ;
    theFields[0] = muscleToneAutocomplete ;
    theFields[1] = palpitationAutocomplete ;
    theFields[2] = reflexesAutocomplete ;
    theFields[3] = respirationAutocomplete ;
    theFields[4] = skinColorAutocomplete ;

    for(var i=0; i<theFields.length; i++) {
        theFields[i].addOnChangeCallback(function() {
      	 	changeCommonMark() ;
      	 });
    }
  	
  	function changeCommonMark() {
  		PregnancyService.calcApgarEstimation(
  			$('muscleTone').value, $('palpitation').value
  			, $('reflexes').value, $('respiration').value
  			, $('skinColor').value, {
  				callback: function(aResult) {
  					$('commonMark').value = aResult ;
  					$('commonMarkReadOnly').value = aResult ;
  				}
  			}
  		) ;
  	}
  	</script>
  	</msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

