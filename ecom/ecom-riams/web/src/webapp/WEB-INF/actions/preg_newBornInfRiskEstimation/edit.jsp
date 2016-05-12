<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_newBornInfRiskEstimationForm" guid="80144fb0-0f76-44d9-9904-7c2e76a32ef4">
      <msh:sideMenu title="Осмотр" guid="f8f7bdcb-3f59-4e48-8430-df5ce4db0bfb">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_newBornInfRiskEstimation" name="Изменить" roles="/Policy/Mis/Inspection/NewBornInfRiskEstimation/Edit" guid="7362c617-23fc-4f59-93b4-3d16601c94dd" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_newBornInfRiskEstimation" name="Удалить" roles="/Policy/Mis/Inspection/NewBornInfRiskEstimation/Delete" guid="a5df7297-5096-4a9c-877e-1a8f9dcc7ee5" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Оценка риска бактериальной инфекции у новорожденных
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_newBornInfRiskEstimation.do" defaultField="inspectionDate" guid="7f923ac7-f45d-4180-9e7c-c5ec711c5237">
      <msh:hidden property="id" guid="1408303a-8f85-4762-bd2a-2686c3d6c78f" />
      <msh:hidden property="medCase" guid="452b96a1-e75f-4255-8c5b-0e1e09df4bf4" />
      <msh:hidden property="saveType" guid="ae2ce1be-7ef0-46e7-8937-90ff552d6642" />
      <msh:panel guid="37e42e5b-a632-4839-83aa-fcbc4e4538c5">
        <msh:row guid="6116424e-84c6-4cae-a789-00e69fdb4a2b">
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row guid="3663e2a8-bfcc-44b8-8d3b-ab31cfe8f41a">
          <msh:textField property="inspectionDate" label="Дата осмотра" guid="7b1580ff-0bf3-4835-b5cd-3d1aa35921ef" />
          <msh:textField passwordEnabled="false" hideLabel="false" property="inspectionTime" viewOnlyField="false" label="Время" guid="0d81c1f9-b233-4f13-ba61-86478898d3b5" horizontalFill="false" />
        </msh:row>
        <msh:row guid="629f1393-b445-48f8-b908-ca93e60af4a3">
          <msh:autoComplete property="waterlessDuration" vocName="vocInfRiskWaterless" fieldColSpan="3" horizontalFill="true" guid="7ea7cbda-20f5-4af9-b50d-ddbfde5201d0" size="50" />
        </msh:row>
        <msh:row guid="609b8d08-508f-4988-98ea-0f33f473c534">
          <msh:autoComplete property="motherTemperature" vocName="vocInfRiskMotherTemperature" fieldColSpan="3" horizontalFill="true" guid="9f7f7b70-b977-4c00-9d11-1b42f286c4c3" />
        </msh:row>
        <msh:row guid="11b6b66b-e825-42b9-b780-4f40d0f5cb37">
          <msh:autoComplete property="waterNature" vocName="vocInfRiskWaterNature" fieldColSpan="3" horizontalFill="true" guid="1efdcc3d-b4fa-400f-9da1-a29d837d6fc2" />
        </msh:row>
        <msh:row guid="0645928b-17b6-4074-95f4-50266eba458f">
          <msh:autoComplete property="apgar" vocName="vocInfRiskApgar" horizontalFill="true" fieldColSpan="3" guid="17130b6b-b666-4ac2-8d0f-5357daf09028" />
        </msh:row>
        <msh:row guid="26edf751-634f-4486-880e-dc26e57b89ad">
          <msh:autoComplete property="newBornWeight" vocName="vocInfRiskNewBornWeight" horizontalFill="true" fieldColSpan="3" guid="99ede48d-ca20-4dd1-aafc-28297530b565" />
        </msh:row>
        <msh:row guid="079b5e90-53e6-4057-bf9c-6b22fe019f4f">
          <msh:autoComplete property="motherInfectiousDiseases" vocName="vocInfRiskMotherDiseases" label="Хрон.очаги инфекции матери" horizontalFill="true" fieldColSpan="3" guid="ac3d4f01-cea2-465d-940a-ab6b3d65d656" />
        </msh:row>
        <msh:row guid="40d68a00-f5f1-4ced-a1d2-1c541cde475a">
          <msh:autoComplete property="commonMark" label="Общая оценка" viewOnlyField="true" horizontalFill="true" fieldColSpan="3" vocName="vocDownesCommonMark"/>
        </msh:row>
        <msh:row guid="c727920e-0d60-4118-9d20-da32d07f7dcc">
          <msh:autoComplete property="workFunction" label="Специалист" horizontalFill="true" fieldColSpan="3" guid="8aae4afd-510c-4036-b4c3-010f1ff69978" vocName="workFunction" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" guid="e93a17c2-2b7c-485b-9400-5fb5ac7cc3aa" />
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
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_newBornInfRiskEstimationForm" guid="168e9cea-ced4-4577-b6d3-003362ab57ed" />
  </tiles:put>
</tiles:insert>

