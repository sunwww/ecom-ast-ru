<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_apgarEstimationForm" guid="a69f4539-419c-4bf9-9d53-deb5ea772d19">
      <msh:sideMenu title="Осмотр" guid="176999d1-9386-4534-adb7-46e38aa8346b">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_apgarEstimation" name="Изменить" roles="/Policy/Mis/Inspection/ApgarEstimation/Edit" guid="0b26b892-3a61-46d4-b6fd-6c13828399b8" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_apgarEstimation" name="Удалить" roles="/Policy/Mis/Inspection/ApgarEstimation/Delete" guid="f5f11111-3374-4912-9799-582342b4adcb" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Оценка состояния новорожденного по шкале Апгар
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_apgarEstimation.do" defaultField="inspectionDate" guid="18721698-7743-4c4e-a3f7-fc86aec480a1">
      <msh:hidden property="id" guid="8d3e883e-8b6a-43f3-8883-8cfb78a1692f" />
      <msh:hidden property="medCase" guid="c81a5f0b-bc59-4d2a-8d35-b4f8138e5d29" />
      <msh:hidden property="saveType" guid="289c839b-e054-44f2-b577-c1715ce9a365" />
      <msh:panel guid="3ea02431-d642-4b20-9923-37a6df2e98fb" colsWidth="5%,5%,5%,30%">
        <msh:row guid="554ce28d-0b91-40e2-9ffe-cebcad92573f">
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row guid="c2e6f524-9df7-46bc-9159-dcfd41fbd474">
          <msh:textField property="inspectionDate" label="Дата осмотра" guid="aca4d2fa-6ee0-46b5-99e2-4be88d8c2a6a" />
          <msh:textField property="inspectionTime" label="Время" guid="e989596c-3b2a-4881-945f-c9e87cf3e5b9" />
        </msh:row>
        <msh:row guid="494f309a-dfd3-48c0-b976-51fb70d4b069">
          <msh:textField property="postNatalTime" label="Время после рождения (мин)" guid="9c9397e7-6f2b-4c70-94e7-74003d5bb46a" />
        </msh:row>
        <msh:row guid="1c338b89-7f09-4c48-89b1-d251b56169c1">
          <msh:autoComplete property="palpitation" vocName="vocApgarPalpitation" guid="4382078a-00ef-4a30-bd9c-6521731d3aff" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="8f8f799d-7e18-4776-8d37-6b2b8626b3ca">
          <msh:autoComplete property="respiration" vocName="vocApgarRespiration" guid="f2717dcf-119e-4c5d-866b-ffddc595f99e" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="6b29780e-6656-42c5-8be6-b3cd11378a33">
          <msh:autoComplete property="skinColor" vocName="vocApgarSkinColor" guid="44053847-95df-4633-8750-7512decaeef2" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="84c366c4-a908-44f2-a04a-9688b05003ea">
          <msh:autoComplete property="muscleTone" vocName="vocApgarMuscleTone" horizontalFill="true" guid="a2d593bf-1ff0-40d0-88c8-e934c6c25a58" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="e27afb42-a223-4bd5-8600-26bc772ee900">
          <msh:autoComplete property="reflexes" vocName="vocApgarReflexes" horizontalFill="true" guid="98dc4719-4b8b-4478-841f-c2c6aad1ea6f" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="2f1c1a9e-e12d-4875-b66c-ec8812257e19">
          <msh:textField property="commonMark" viewOnlyField="true" label="Общая оценка (балл)" guid="d49c8879-8abb-43ad-b1ad-2c488bd2ee6b" />
        </msh:row>
        <msh:row guid="c727920e-0d60-4118-9d20-da32d07f7dcc">
          <msh:autoComplete property="workFunction" label="Специалист" horizontalFill="true" fieldColSpan="3" guid="8aae4afd-510c-4036-b4c3-010f1ff69978" vocName="workFunction" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" guid="1848f119-69b2-48a9-9866-c99dae2d14c4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_apgarEstimationForm" guid="290fb173-3f15-48bd-8d02-e0550d774ce2" />
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

