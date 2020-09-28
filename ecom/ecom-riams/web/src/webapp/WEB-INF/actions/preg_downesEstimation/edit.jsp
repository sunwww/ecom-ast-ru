<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_downesEstimationForm" >
      <msh:sideMenu title="Осмотр" >
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_downesEstimation" name="Изменить" roles="/Policy/Mis/Inspection/DownesEstimation/Edit"  />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_downesEstimation" name="Удалить" roles="/Policy/Mis/Inspection/DownesEstimation/Delete"  />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Оценка респираторного дистресса новорожденного по Downes
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_downesEstimation.do" defaultField="inspectionDate" >
      <msh:hidden property="id" />
      <msh:hidden property="medCase" />
      <msh:hidden property="saveType"  />
      <msh:panel colsWidth="5%,5%,5%,30%">
        <msh:row >
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row >
          <msh:textField property="inspectionDate" label="Дата осмотра" />
          <msh:textField property="inspectionTime" label="Время" />
        </msh:row>
        <msh:row >
          <msh:autoComplete property="respirationRate" vocName="vocDownesRespirationRate"  fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row >
          <msh:autoComplete property="cyanosis" vocName="vocDownesCyanosis"  fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row >
          <msh:autoComplete property="intercostalRetraction" vocName="vocDownesIntercostalRet"  fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row >
          <msh:autoComplete property="difficultExhalation" vocName="vocDownesDifExhalation" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row >
          <msh:autoComplete property="auscultation" vocName="vocDownesAuscultation" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row >
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
  <msh:ifFormTypeIsNotView formName="preg_downesEstimationForm">
  	<script type="text/javascript" src="./dwr/interface/PregnancyService.js"></script>
  	<script type="text/javascript">
  	
	
  	theFields = new Array(5) ;
    theFields[0] = respirationRateAutocomplete ;
    theFields[1] = cyanosisAutocomplete ;
    theFields[2] = intercostalRetractionAutocomplete ;
    theFields[3] = difficultExhalationAutocomplete ;
    theFields[4] = auscultationAutocomplete ;

    for(var i=0; i<theFields.length; i++) {
        theFields[i].addOnChangeCallback(function() {
      	 	changeCommonMark() ;
      	 });
    }


  	  	

  	function changeCommonMark() {
  		PregnancyService.calcDownesEstimation(
  			$('respirationRate').value, $('cyanosis').value
  			, $('intercostalRetraction').value, $('difficultExhalation').value
  			, $('auscultation').value, {
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
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_downesEstimationForm" />
  </tiles:put>
</tiles:insert>

