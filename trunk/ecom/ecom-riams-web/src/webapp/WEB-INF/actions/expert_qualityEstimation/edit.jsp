<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="ru.ecom.mis.web.dwr.medcase.QualityEstimationServiceJs"%>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Экспертная карта
    	  -->
    <msh:form  action="/entityParentSaveGoParentView-expert_qualityEstimation.do" defaultField="criterion1Name">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenSavefgdfs" property="expertType" />
      <msh:hidden guid="hiddenSavegfdsgfd" property="card" />
      <msh:hidden guid="hiddenSavefd" property="criterions" />
      <msh:panel guid="panel" colsWidth="5%,5%,10%">
        <msh:row>
	        <td colspan="6">
	        	<div id='loadCriterion'>
	        	</div>
	        </td>
        </msh:row>
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/EditExpert">
	        <msh:row>
	        	<msh:autoComplete  property="expert" label="Специалист" vocName="workFunction" fieldColSpan="3" horizontalFill="true" viewOnlyField="true"/>
	        </msh:row>
        </msh:ifNotInRole>
        <msh:ifInRole roles="/Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/EditExpert">
	        <msh:row>
	        	<msh:autoComplete  property="expert" label="Специалист" vocName="workFunction" fieldColSpan="3" horizontalFill="true"/>
	        </msh:row>
        </msh:ifInRole>
        <msh:row>
        	<msh:textField property="createUsername" viewOnlyField="true" label="Пользователь"/>
        	<msh:textField property="createDate" viewOnlyField="true" label="Дата создания"/>
        </msh:row>
        <msh:row>
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="3" />
      </msh:panel>
    </msh:form>
    
 
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Expert" beginForm="expert_qualityEstimationForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Вид оценки качества">
      <msh:sideLink roles="/Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/Edit" key="ALT+2" params="id" action="/entityParentEdit-expert_qualityEstimation" name="Изменить" title="Изменить данные" />
      <msh:sideLink roles="/Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/Delete" confirm="Удалить?" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-expert_qualityEstimation" name="Удалить" title="Удалить данные" />
    </msh:sideMenu>
    
    <tags:expert_menu currentAction="expert_card"/>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <div id='loadJavascriptCriterion'></div>
  <script type="text/javascript" src="./dwr/interface/QualityEstimationService.js" >
  /*   */
  </script>
  <msh:ifFormTypeIsCreate formName="expert_qualityEstimationForm">
  		<script type="text/javascript">
  			if ($('expertType').value=="") $('expertType').value='${param.type}' ;
  		</script>
  </msh:ifFormTypeIsCreate>
  		<script type="text/javascript">
  		var crits ;
  			function loadDataCriterion() {
  				//alert($('criterions').value) ;
	  			QualityEstimationService.getRowEdit($('criterions').value,$('card').value,$('expertType').value,false
	  				,{
						 callback: function(aRow) {
						     	//alert(aRow) ;
						     	if (aRow!=null) {
						     		$('loadCriterion').innerHTML = aRow ;
						     		QualityEstimationService.getCountRow($('card').value,
						     		{
						     			callback: function(aCount) {
						     					 crits = aCount.split('#') ;
						     					updateVoc(crits);
						     					updateCriterions() ;
						     				
						     			}
						     		}
						     		)
						     		
						     	}
						  	}
					}
	  				
	  			);}
	  			loadDataCriterion() ;
	  			function updateVoc(aStr) {
	  				for (var i=0;i<aStr[0];i++) {
						//		 class ru.nuzmsh.web.tags.AutoCompleteTag
						var ii=i+1;
						//if (ii==1) {
						//	eventutil.addEnterSupport('workFunctionName','criterion'+ii+'Name');
						//}
						if (ii==+aStr[0]) {
							eventutil.addEnterSupport('criterion'+ii+'Name', 'expertName');
						} else {
							eventutil.addEnterSupport('criterion'+ii+'Name', 'criterion'+(ii+1)+'Name');
						}
						//		 class ru.nuzmsh.web.tags.AutoCompleteTag
						eval("var criterion"+ii+"Autocomplete = new msh_autocomplete.Autocomplete()") ;
						eval("criterion"+ii+"Autocomplete.setUrl('simpleVocAutocomplete/vocQualityEstimationMark') ");
						eval("criterion"+ii+"Autocomplete.setIdFieldId('criterion"+ii+"') ");
						eval("criterion"+ii+"Autocomplete.setNameFieldId('criterion"+ii+"Name') ");
						eval("criterion"+ii+"Autocomplete.setDivId('criterion"+ii+"Div') ");
						eval("criterion"+ii+"Autocomplete.setVocKey('vocQualityEstimationMark') ");
						eval("criterion"+ii+"Autocomplete.setVocTitle('Оценка зав.отд.')") ;
						eval("criterion"+ii+"Autocomplete.build() ");
						eval("criterion"+ii+"Autocomplete.setParentId('"+aStr[ii]+"')") ;
						eval("criterion"+ii+"Autocomplete.addOnChangeCallback(function() {updateCriterions()  })") ;
					}
					if (+aStr[0]>0) {
						
						$('criterion1Name').select() ;
						$('criterion1Name').focus() ;
						
					}
					
	  			}
	  			function updateCriterions() {
	  				var rez ="" ;
	  				for (var i=0;i<crits[0]; i++) {
	  					if ($("criterion"+(i+1)).value!="") rez=rez+"#"+crits[i+1]+":"+$("criterion"+(i+1)).value ;
	  				}
	  				$('criterions').value=rez.length>0?rez.substring(1) :"";
	  			}
  		</script>

  		
  </tiles:put>
</tiles:insert>