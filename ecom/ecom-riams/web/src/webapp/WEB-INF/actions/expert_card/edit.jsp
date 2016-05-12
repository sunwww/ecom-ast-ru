<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Экспертная карта
    	  -->
    <msh:form  action="/entityParentSaveGoView-expert_card.do" defaultField="kindName">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="medcase" property="medcase" />
      <msh:panel guid="panel" colsWidth="10%,20%,10%,50%">
        <msh:row>
        	<msh:autoComplete viewAction="entityView-exp_vocKind.do" property="kind" label="Вид оценки кач-ва" vocName="vocQualityEstimationKind" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <%-- 
        <msh:row>
        	<msh:autoComplete viewAction="entitySubclassView-mis_medCase.do" property="medcase" label="Случ.мед.обслуж." vocName="allSMO" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        --%>
        <msh:row>
        	<msh:autoComplete viewOnlyField="true" viewAction="entityView-mis_patient.do" viewOnlyField="true" property="patient" label="Пациент" horizontalFill="true" vocName="patient" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:textField viewOnlyField="true" property="cardNumber"  label="Номер карты"/>
        </msh:row>
        <%--
        <msh:ifFormTypeIsCreate formName="expert_cardForm">
	        <msh:row>
	        	<msh:autoComplete property="slo" vocName="sloBySLS" parentAutocomplete="medcase" horizontalFill="true" fieldColSpan="3"/>
	        </msh:row>
        </msh:ifFormTypeIsCreate>
         --%>
        <msh:row>
        	<msh:autoComplete viewOnlyField="true" property="department" vocName="departmentBySMO" label="Отделение" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete viewOnlyField="true" property="idc10" vocName="vocIdc10" label="Код МКБ" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField viewOnlyField="true" property="diagnosis"  horizontalFill="true" label="Диагноз" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete viewOnlyField="true" property="doctorCase" vocName="workFunction" fieldColSpan="3" label="Лечащий врач" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="createUsername" label="Пользователь" viewOnlyField="true"/>
        	<msh:textField property="createDate" label="Дата создания" viewOnlyField="true"/>
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="2" />
      </msh:panel>
    </msh:form>
    
    <msh:ifFormTypeIsView formName="expert_cardForm">
    	<msh:ifInRole roles="/Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/View">
    		<div id='loadCriterion'>
        	</div>
    	</msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Expert" beginForm="expert_cardForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Вид оценки качества">
      <msh:sideLink roles="/Policy/Mis/MedCase/QualityEstimationCard/Edit" key="ALT+2" params="id" action="/entityEdit-expert_card" name="Изменить" title="Изменить данные" />
      <msh:sideLink roles="/Policy/Mis/MedCase/QualityEstimationCard/Delete" confirm="Удалить?" key="ALT+DEL" params="id" action="/entityParentDelete-expert_card" name="Удалить" title="Удалить данные" />
    	<msh:sideLink roles="/Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/Edit,/Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/BranchManager" 
    		key="ALT+4" action="/javascript:goExpertEstimation('.do','BranchManager')" 
    		name="Баллы зав.отделения" title="Добавить оценочные баллы зав.отделением"/>
    	<msh:sideLink roles="/Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/Edit,/Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/Expert" 
    		key="ALT+5" action="/javascript:goExpertEstimation('.do','Expert')" 
    		name="Баллы эксперта" title="Добавить оценочные баллы эксперта"/>
    	<msh:sideLink roles="/Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/Edit,/Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/Coeur" 
    		key="ALT+6" action="/javascript:goExpertEstimation('.do','Coeur')" 
    		name="Баллы КЭР" title="Добавить оценочные баллы КЭР"/>
    </msh:sideMenu>
    <msh:sideMenu>
    	<msh:sideLink  action="/entityParentListRedirect-expert_card.do" params="id" name="Список экспертных карт по СМО" roles="/Policy/Mis/MedCase/QualityEstimationCard/View" title="Список экспертных карт" styleId="selected"/>
    </msh:sideMenu>

    <tags:expert_menu currentAction="expert_card_smo"/>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <script type="text/javascript" src="./dwr/interface/QualityEstimationService.js" >
  /*   */
  </script>
  		<script type="text/javascript">
    function goExpertEstimation(aQ,aType) {
 		QualityEstimationService.getIdQualityEstimationByType(
			     		aType, '${param.id}' , {
			 callback: function(aId) {
			     if (aId!=null) {
			          window.location.href = "entityParentEdit-expert_qualityEstimation.do?id="+aId+"&type="+aType ;
			     } else {
			     
			    QualityEstimationService.ableCreate (aType,{
			    	callback: function(aBool) {
			    		createUrl(aBool
				    		,"entityParentPrepareCreate-expert_qualityEstimation.do?id=${param.id}&type="+aType
			    		) } } );
			     }
			  }
			}) ;
  }  		
    function createUrl(aBool,aUrlCreate) {
   		if (bool(aBool)) {
			window.location.href = aUrlCreate  ;
		} 
  }
  function bool(aBool) {
  	if (aBool!=null) if (aBool==true) return true ;
  	return false ;
  }
  function loadDataCriterion() {
  				//alert($('card').value+"#"+$('expertType').value);
	  			QualityEstimationService.getRow($('id').value,'',true
	  				,{
						 callback: function(aRow) {
						     	//alert(aRow) ;
						     	if (aRow!=null) {
						     		$('loadCriterion').innerHTML = aRow ;
						     		
						     		
						     	}
						  	}
					}
	  				
	  			);}
  		</script>
  		<msh:ifFormTypeIsView formName="expert_cardForm">
  		  		<script type="text/javascript">loadDataCriterion();</script>
  		</msh:ifFormTypeIsView>
  		<msh:ifFormTypeIsCreate formName="expert_cardForm">
  		<script type="text/javascript">
  			infoByPatient() ;
  			function infoByPatient() {
  				QualityEstimationService.getInfoBySmo($('medcase').value
		  				,{
							 callback: function(aRow) {
							     	//alert(aRow) ;
							     	if (aRow!=null) {
							     		  var ind1,ind2,ind3,ind4 ;
							     		  ind1=aRow.indexOf('#') ;
							     		  ind2=aRow.indexOf('#',ind1+1) ;
							     		  $('patient').value = aRow.substring(0,ind1) ;
							     		  $('patientReadOnly').value = aRow.substring(ind1+1,ind2) ;
							     		  $('cardNumber').value = aRow.substring(ind2+1) ;
							     		  $('cardNumberReadOnly').value = aRow.substring(ind2+1) ;
							     		  
							     	} else {
							     		  $('patient').value = "" ;
							     		  $('patientReadOnly').value = "" ;
							     		  $('cardNumber').value = "" ;
							     		  $('cardNumberReadOnly').value = "" ;
							     	}
							     	infoBySlo() ;
							  	}
						}
	  				
		  			);
  			}
		  	function infoBySlo() {
		  		QualityEstimationService.getInfoBySlo($('medcase').value,$('medcase').value
		  				,{
							 callback: function(aRow) {
							     	//alert(aRow) ;
							     	if (aRow!=null) {
							     		  var ind1,ind2,ind3,ind4,ind5,ind6 ;
							     		  ind1=aRow.indexOf('#') ;
							     		  ind2=aRow.indexOf('#',ind1+1) ;
							     		  ind3=aRow.indexOf('#',ind2+1) ;
							     		  ind4=aRow.indexOf('#',ind3+1) ;
							     		  ind5=aRow.indexOf('#',ind4+1) ;
							     		  ind6=aRow.indexOf('#',ind5+1) ;
							     		  $('doctorCase').value = aRow.substring(0,ind1) ;
							     		  $('doctorCaseReadOnly').value = aRow.substring(ind1+1,ind2) ;
							     		  $('idc10').value = aRow.substring(ind2+1,ind3) ;
							     		  $('idc10ReadOnly').value = aRow.substring(ind3+1,ind4) ;
							     		  $('diagnosis').value = aRow.substring(ind4+1,ind5) ;$('diagnosisReadOnly').value = aRow.substring(ind4+1,ind5) ;
							     		  $('department').value = aRow.substring(ind5+1,ind6) ;
							     		  $('departmentReadOnly').value = aRow.substring(ind6+1) ;
							     		  
							     	} else {
							     		  $('doctorCase').value = "" ;$('doctorCaseReadOnly').value = "" ;
							     		  $('idc10').value = "" ;$('idc10ReadOnly').value = "" ;
							     		  $('diagnosis').value = "" ;
							     		  $('department').value = "" ;$('departmentReadOnly').value = "" ;
							     	}

							  	}
						}
	  				
		  			);
		  	}
  		</script>
  		</msh:ifFormTypeIsCreate>
</tiles:put>  
  <%--
  <tiles:put name="javascript" type="string">
  <script type="text/javascript" src="./dwr/interface/QualityEstimationService.js" >
  /*   */
  </script>
  		<script type="text/javascript">
    function goExpertEstimation(aQ,aType) {
 		QualityEstimationService.getIdQualityEstimationByType(
			     		aType, '${param.id}' , {
			 callback: function(aId) {
			     if (aId!=null) {
			          window.location.href = "entityParentEdit-expert_qualityEstimation.do?id="+aId+"&type="+aType ;
			     } else {
			     
			    QualityEstimationService.ableCreate (aType,{
			    	callback: function(aBool) {
			    		createUrl(aBool
				    		,"entityParentPrepareCreate-expert_qualityEstimation.do?id=${param.id}&type="+aType
			    		) } } );
			     }
			  }
			}) ;
  }  		
    function createUrl(aBool,aUrlCreate) {
   		if (bool(aBool)) {
			window.location.href = aUrlCreate  ;
		} 
  }
  function bool(aBool) {
  	if (aBool!=null) if (aBool==true) return true ;
  	return false ;
  }
  function loadDataCriterion() {
  				//alert($('card').value+"#"+$('expertType').value);
	  			QualityEstimationService.getRow($('id').value,'',true
	  				,{
						 callback: function(aRow) {
						     	//alert(aRow) ;
						     	if (aRow!=null) {
						     		$('loadCriterion').innerHTML = aRow ;
						     		
						     		
						     	}
						  	}
					}
	  				
	  			);}
  		</script>
  		  		<msh:ifFormTypeIsView formName="expert_cardForm">
  		  		<script type="text/javascript">loadDataCriterion();</script>
  		</msh:ifFormTypeIsView>
  		<msh:ifFormTypeIsCreate formName="expert_cardForm">
<script type="text/javascript">
  		  		if (+$('medcase').value>0) {
  		  			//alert("Изменился СМО");
  		  			QualityEstimationService.getInfoBySmo($('medcase').value
		  				,{
							 callback: function(aRow) {
							     	//alert(aRow) ;
							     	if (aRow!=null) {
							     		  var ind1,ind2,ind3,ind4 ;
							     		  ind1=aRow.indexOf('#') ;
							     		  ind2=aRow.indexOf('#',ind1+1) ;
							     		  $('patient').value = aRow.substring(0,ind1) ;
							     		  $('patientReadOnly').value = aRow.substring(ind1+1,ind2) ;
							     		  $('cardNumber').value = aRow.substring(ind2+1) ;
							     		  $('cardNumberReadOnly').value = aRow.substring(ind2+1) ;
							     		  
							     	} else {
							     		  $('patient').value = "" ;
							     		  $('patientName').value = "" ;
							     		  $('cardNumber').value = "" ;
							     		  $('cardNumberReadOnly').value = "" ;
							     	}
							  	}
						}
	  				
		  			);}
  		  		
  		  		</script>  		
  		</msh:ifFormTypeIsCreate>
  		<msh:ifFormTypeIsNotView formName="expert_cardForm">
 <script type="text/javascript">
  		  		medcaseAutocomplete.addOnChangeCallback(function() {
  		  			//alert("Изменился СМО");
  		  			QualityEstimationService.getInfoBySmo($('medcase').value
		  				,{
							 callback: function(aRow) {
							     	//alert(aRow) ;
							     	if (aRow!=null) {
							     		  var ind1,ind2,ind3,ind4 ;
							     		  ind1=aRow.indexOf('#') ;
							     		  ind2=aRow.indexOf('#',ind1+1) ;
							     		  $('patient').value = aRow.substring(0,ind1) ;
							     		  $('patientReadOnly').value = aRow.substring(ind1+1,ind2) ;
							     		  $('cardNumber').value = aRow.substring(ind2+1) ;
							     		  $('cardNumberReadOnly').value = aRow.substring(ind2+1) ;
							     		  
							     	} else {
							     		  $('patient').value = "" ;
							     		  $('patientName').value = "" ;
							     		  $('cardNumber').value = "" ;
							     		  $('cardNumberReadOnly').value = "" ;
							     	}
							  	}
						}
	  				
		  			);}
  		  		) ;
  		  		</script>
  		  		 <script type="text/javascript">
  		  		departmentAutocomplete.addOnChangeCallback(function() {
  		  			//alert("Изменилось отделение");
  		  			if ($('slo')) 	QualityEstimationService.getInfoByDep($('medcase').value,$('department').value
		  				,{
							 callback: function(aRow) {
							     	//alert(aRow) ;
							     	if (aRow!=null) {
							     		  var ind1,ind2 ;
							     		  ind1=aRow.indexOf('#') ;
							     		  ind2=aRow.indexOf('#',ind1+1) ;
							     		  ind3=aRow.indexOf('#',ind2+1) ;
							     		  ind4=aRow.indexOf('#',ind3+1) ;
							     		  $('doctorCase').value = aRow.substring(0,ind1) ;
							     		  $('doctorCaseName').value = aRow.substring(ind1+1,ind2) ;
							     		  $('idc10').value = aRow.substring(ind2+1,ind3) ;
							     		  $('idc10Name').value = aRow.substring(ind3+1,ind4) ;
							     		  $('diagnosis').value = aRow.substring(ind4+1) ;
							     	} else {
							     		  $('doctorCase').value = "" ;
							     		  $('doctorCaseName').value = "" ;
							     		  $('idc10').value = "" ;
							     		  $('idc10Name').value = "" ;
							     		  $('diagnosis').value = "" ;
							     	}
							  	}
						}
	  				
		  			);}
  		  		) ;
  		  		
  		  		</script>  		
  		</msh:ifFormTypeIsNotView>
  		<msh:ifFormTypeIsCreate formName="expert_cardForm">
  			<script type="text/javascript">
sloAutocomplete.addOnChangeCallback(function() {
  		  			//alert("Изменилось slo");
  		  			QualityEstimationService.getInfoBySlo($('medcase').value,$('slo').value
		  				,{
							 callback: function(aRow) {
							     	//alert(aRow) ;
							     	if (aRow!=null) {
							     		  var ind1,ind2,ind3,ind4,ind5,ind6 ;
							     		  ind1=aRow.indexOf('#') ;
							     		  ind2=aRow.indexOf('#',ind1+1) ;
							     		  ind3=aRow.indexOf('#',ind2+1) ;
							     		  ind4=aRow.indexOf('#',ind3+1) ;
							     		  ind5=aRow.indexOf('#',ind4+1) ;
							     		  ind6=aRow.indexOf('#',ind5+1) ;
							     		  $('doctorCase').value = aRow.substring(0,ind1) ;
							     		  $('doctorCaseName').value = aRow.substring(ind1+1,ind2) ;
							     		  $('idc10').value = aRow.substring(ind2+1,ind3) ;
							     		  $('idc10Name').value = aRow.substring(ind3+1,ind4) ;
							     		  $('diagnosis').value = aRow.substring(ind4+1,ind5) ;
							     		  $('department').value = aRow.substring(ind5+1,ind6) ;
							     		  $('departmentName').value = aRow.substring(ind6+1) ;
							     		  
							     	} else {
							     		  $('doctorCase').value = "" ;
							     		  $('doctorCaseName').value = "" ;
							     		  $('idc10').value = "" ;
							     		  $('idc10Name').value = "" ;
							     		  $('diagnosis').value = "" ;
							     		  $('department').value = "" ;
							     		  $('departmentName').value = "" ;
							     	}
							  	}
						}
	  				
		  			);}
  		  		) ;
  		  		  			</script>
  		</msh:ifFormTypeIsCreate>
  </tiles:put>--%>
</tiles:insert>