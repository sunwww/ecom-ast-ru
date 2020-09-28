<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<style>
    h2 {display:none;}
    @media print {
        h2 {display:inline;}
        img,div#copyright,h1,ul#ideModeMainMenu, div#ideModeMainMenuClose {display:none;}
        div#beginDate{display:inline;}
        span#kindLabel{display:none;}
        span#createUsernameLabel{display:none;}
        span#createDateLabel{display:none;}
        input#kindReadOnly{display:none;}
        input#createUsernameReadOnly{display:none;}
        input#createDateReadOnly{display:none;}
        .formInfoMessage{display:none;}
       .titleTrail{display:none;}
        div#header{display:none;}
        div#gotoUpDown{display:none;}
    }
</style>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Экспертная карта
    	  -->
    <msh:form  action="/entityParentSaveGoView-expert_card.do" defaultField="kindName">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="medcase" />
      <msh:panel colsWidth="10%,20%,10%,50%">
          <msh:ifFormTypeAreViewOrEdit formName="expert_cardForm">
              <msh:ifFormTypeIsNotView formName="expert_cardForm">
                  <msh:row>
                      <msh:autoComplete viewAction="entityView-exp_vocKind.do" property="kind" viewOnlyField="true" label="Вид оценки кач-ва" vocName="vocQualityEstimationKind" fieldColSpan="3" horizontalFill="true"/>
                  </msh:row>
              </msh:ifFormTypeIsNotView>
              <msh:ifFormTypeIsView formName="expert_cardForm">
                  <msh:row>
                      <msh:autoComplete viewAction="entityView-exp_vocKind.do" property="kind" label="Вид оценки кач-ва" vocName="vocQualityEstimationKind" fieldColSpan="3" horizontalFill="true"/>
                  </msh:row>
              </msh:ifFormTypeIsView>
          </msh:ifFormTypeAreViewOrEdit>
          <msh:ifFormTypeIsCreate formName="expert_cardForm">
              <msh:row>
                  <msh:autoComplete viewAction="entityView-exp_vocKind.do" property="kind" label="Вид оценки кач-ва" vocName="vocQualityEstimationKind" fieldColSpan="3" horizontalFill="true"/>
              </msh:row>
          </msh:ifFormTypeIsCreate>
        <%-- 
        <msh:row>
        	<msh:autoComplete viewAction="entitySubclassView-mis_medCase.do" property="medcase" label="Случ.мед.обслуж." vocName="allSMO" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        --%>
        <msh:row>
        	<msh:autoComplete viewAction="entityView-mis_patient.do" viewOnlyField="true" property="patient" label="Пациент" horizontalFill="true" vocName="patient" fieldColSpan="3"/>
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
        	<msh:autoComplete property="idc10" vocName="vocIdc10" label="Код МКБ" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="diagnosis"  horizontalFill="true" label="Диагноз" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="doctorCase" vocName="workFunction" fieldColSpan="3" label="Лечащий врач" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="createUsername" label="Пользователь" viewOnlyField="true"/>
        	<msh:textField property="createDate" label="Дата создания" viewOnlyField="true"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
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
    <ecom:titleTrail mainMenu="Expert" beginForm="expert_cardForm" />
  </tiles:put>
    <msh:ifFormTypeAreViewOrEdit formName="expert_cardForm">
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
</msh:ifFormTypeAreViewOrEdit>
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
                             if (aRow!=null) {
						     		$('loadCriterion').innerHTML = aRow ;
						     		
						     		
						     	}
						  	}
					}
	  				
	  			);}
    var closure = function() {
        return function() {
            var idc10Field=document.getElementById('idc10Name');
            var textField=document.getElementById('diagnosis');
            if (textField!=null && idc10Field!=null) {
                textField.value=idc10Field.value;
            }
        };
    };
    if (typeof idc10Autocomplete !=='undefined') idc10Autocomplete.addOnChangeCallback(closure());
  		</script>
  		<msh:ifFormTypeIsView formName="expert_cardForm">
  		  		<script type="text/javascript">
                    loadDataCriterion();
                    //#150
                    QualityEstimationService.getIfCanCreateNow($('medcase').value,$('kind').value, {
                        callback: function (res2) {
                            if (!res2) { //Выписан ранее дней в настройке
                                showToastMessage("Истёк срок с момента выписки пациента, во время которого можно создавать и редактировать экспертные карты (и черновики) по 203 приказу!",null,true);
                                jQuery('#'+document.getElementsByClassName('firstMenu')[0].id).fadeTo('slow',.6);
                                jQuery('#'+document.getElementsByClassName('firstMenu')[0].id).append('<div style="position: absolute;bottom:0;left:0;width: 100%;height:98%;z-index:2;opacity:0.4;filter: alpha(opacity = 50)"></div>');
                            }
                        }
                    },$('kind').value);
                </script>
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
							     		  var rows = aRow.split("#") ;
							     		  if (+rows[4]>0) {
							     			  document.location.href="entityParentPrepareCreate-expert_card.do?id="+rows[4] ;
							     		  }
							     		  if ($('patient')!=null) $('patient').value = rows[0] ;
                                          if ($('patientReadOnly')!=null) $('patientReadOnly').value = rows[1] ;
                                          if ($('cardNumber')!=null) $('cardNumber').value = rows[2] ;
                                          if ($('cardNumberReadOnly')!=null) $('cardNumberReadOnly').value = rows[2] ;
							     		  
							     	} else {
                                        if ($('patient')!=null) $('patient').value = "" ;
                                        if ($('patientReadOnly')!=null) $('patientReadOnly').value = "" ;
                                        if ($('cardNumber')!=null) $('cardNumber').value = "" ;
                                        if ($('cardNumberReadOnly')!=null) $('cardNumberReadOnly').value = "" ;
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
                                 if (aRow != null) {
                                     var res = aRow.split("#");
                                     //var ind1,ind2,ind3,ind4,ind5,ind6 ;
                                     //ind1=aRow.indexOf('#') ;
                                     //ind2=aRow.indexOf('#',ind1+1) ;
                                     //ind3=aRow.indexOf('#',ind2+1) ;
                                     //ind4=aRow.indexOf('#',ind3+1) ;
                                     //ind5=aRow.indexOf('#',ind4+1) ;
                                     //ind6=aRow.indexOf('#',ind5+1) ;
                                     $('doctorCase').value = res[0];
                                     if ($('doctorCase').value != "") $('doctorCaseName').value = res[1];
                                     $('idc10').value = res[2];
                                     $('idc10Name').value = res[3];
                                     $('diagnosis').value = res[4]; //($('diagnosisReadOnly').value =res[4];
                                     $('department').value = res[5];
                                     $('departmentReadOnly').value = res[6];
                                     if (res.length > 7) {
                                         $('kind').value = res[7];
                                         $('kindName').value = res[8];
                                     }

                                 } else {
                                     $('doctorCase').value = "";
                                     $('doctorCaseName').value = "";
                                     $('idc10').value = "";
                                     $('idc10ReadOnly').value = "";
                                     $('diagnosis').value = "";
                                     $('department').value = "";
                                     $('departmentReadOnly').value = "";
                                 }
                                 //Milamesher 05.07.2018 #105 значения в случае undefined
                                 //Отделение, за которым закрепили
                                 if ($('department').value == "undefined") {
                                     QualityEstimationService.getFixedDepartmentFromMedcase($('medcase').value, {
                                         callback: function (aRow) {
                                             if (aRow != "##") {
                                                 var res = aRow.split("#");
                                                 $('department').value = res[0];
                                                 $('departmentReadOnly').value = res[1];
                                             }
                                         }
                                     });
                                 }
                                 //Диагноз, если проставлен, сделать недоступным
                                 if ($('idc10Name').value == "undefined") {
                                     $('idc10Name').value = "";
                                     $('idc10').value = "";
                                 }
                                 else { //если диагноз есть
                                     $('idc10Name').setAttribute("class", "viewOnly horizontalFill");
                                     $('diagnosis').setAttribute("class", "viewOnly horizontalFill");
                                     $('idc10Name').setAttribute("disabled", "true");
                                     $('diagnosis').setAttribute("readonly", "true");
                                 }
                                 if ($('diagnosis').value == "undefined") $('diagnosis').value = "";
                                 //Лечащий врач, если проставлен, сделать недоступным
                                 if ($('doctorCase').value == "undefined") $('doctorCase').value = "";
                                 if ($('doctorCase').value != "") {
                                     $('doctorCase').setAttribute("class", "viewOnly horizontalFill");
                                     $('doctorCase').setAttribute("readonly", "true");
                                     $('doctorCaseName').setAttribute("class", "viewOnly horizontalFill");
                                     $('doctorCaseName').setAttribute("disabled", "true");
                                 }
                             }
						}
	  				
		  			);
		  	}
  		</script>
  		</msh:ifFormTypeIsCreate>
</tiles:put>  
 
</tiles:insert>