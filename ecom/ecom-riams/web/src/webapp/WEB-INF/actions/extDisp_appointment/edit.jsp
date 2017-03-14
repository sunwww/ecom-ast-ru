<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="extDisp_appointmentForm" />
	</tiles:put>
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoParentView-extDisp_appointment.do" defaultField="lpuName">
			<msh:hidden property="id" /> 
			<msh:hidden property="saveType" />
			<msh:hidden property="dispCard" />
			
			<msh:panel colsWidth="1%,1%,1%,97%">
			
			<msh:row>
			<msh:autoComplete property="appointment" label="Назначение" vocName="vocExtDispAppointment"  size="100" fieldColSpan="3"/>
			</msh:row>
			
			<msh:row>
			<msh:autoComplete property="kindSurvey" label="Вид обследования" vocName="vocKindSurvey" size="100" fieldColSpan="3"  />
			</msh:row>

			<msh:row>
			<msh:autoComplete property="profile" label="Профиль мед.помощи" vocName="vocOmcDepType" size="100" fieldColSpan="3" />
			</msh:row>

		  </msh:panel>
			<msh:submitCancelButtonsRow colSpan="1000"  />
		</msh:form>
		
		<msh:ifFormTypeIsView formName="extDisp_appointmentForm">
	
		</msh:ifFormTypeIsView>
		
		</tiles:put>
		
		<tiles:put name="side" type="string">
			<msh:ifFormTypeAreViewOrEdit formName="extDisp_appointmentForm">
				<msh:sideMenu>
					<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-extDisp_appointment" name="Изменить" title="Изменить" roles="/Policy/Mis/ExtDisp/Card/Edit"/>
					<msh:sideLink confirm="Удалить назначение?" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-extDisp_appointment" name="Удалить" title="Удалить" roles="/Policy/Mis/ExtDisp/Card/Delete"/>
				</msh:sideMenu>
				<msh:sideMenu title="Добавить" >
					<msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-extDisp_appointment.do" name="Назначение" title="Назначение" roles="/Policy/Mis/ExtDisp/Card/Edit"/>
				</msh:sideMenu>
			</msh:ifFormTypeAreViewOrEdit>
		</tiles:put>
		
		<tiles:put name="javascript" type="string">
		<script type="text/javascript" src="./dwr/interface/AppointmentService.js"></script>
		<script type="text/javascript"> 

		//disableAutoComplites();
		function updateAppointment() {
			AppointmentService.getCodeForVocExtDispAppointment($('appointment').value, {
			callback: function(aResult) {
				//	alert(aResult);//$('ageReadOnly').value=aResult ;
				/*	disableAutoComplites()
					disableCase(aResult);*/
			}});
		}
	
	/*function disableAutoComplites(){

		$('workFunctionName').disabled=true;
		$('workFunctionName').value="";
		$('workFunctionName').className="dark";
		$('kindSurveyName').disabled=true;
		$('kindSurveyName').value="";
		$('kindSurveyName').className="dark";
		$('kindMedHelpName').disabled=true;
		$('kindMedHelpName').value="";
		$('kindMedHelpName').className="dark";
		$('bedTypeName').disabled=true;
		$('bedTypeName').value="";
		$('bedTypeName').className="dark";
	}
	
	function disableCase(result)
	{
		var result1 =parseInt(result);
		switch (result1) {
		  case 1:
			  $('workFunctionName').disabled=false;
			  $('workFunctionName').className="required";
		    break;
		  case 2:
			  $('workFunctionName').disabled=false;
			  $('workFunctionName').className="required";
		    break;
		  case 3:
			  $('kindSurveyName').disabled=false;
			  $('kindSurveyName').className="required";
		    break;
		  case 4:
			  $('kindMedHelpName').disabled=false;
			  $('kindMedHelpName').className="required";
			    break;
		  case 5:
			  $('kindMedHelpName').disabled=false;
			  $('kindMedHelpName').className="required";
			    break;
		  case 6:
			  $('bedTypeName').disabled=false;
			  $('bedTypeName').className="required";
			    break;
		}
	}*/
	
		appointmentAutocomplete.addOnChangeCallback(function(){updateAppointment();}); 
		</script>
		</tiles:put>
	
	
</tiles:insert>