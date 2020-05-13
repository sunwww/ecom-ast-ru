<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
      	<msh:separator label="Печать" colSpan="4"/>
      	<msh:link action="print-vis_ticket.do?s=VisitPrintService&amp;m=printTalon1&id=${param.id}" >талона</msh:link>
    	<msh:link action="print-vis_ticket1.do?s=VisitPrintService&amp;m=printTalon1&id=${param.id}">1 часть талона</msh:link>
    	<msh:link action="print-vis_ticket2.do?s=VisitPrintService&amp;m=printTalon1&id=${param.id}">2 часть талона</msh:link>
  
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entitySaveGoView-smo_direction" defaultField="orderLpuName"
    title="<a href='entityView-smo_direction.do?id=${param.id}'>Направление</a>">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="patient" />
      <msh:panel colsWidth="10%, 10%, 10%">
        <msh:row>
          <msh:autoComplete viewAction="entityParentView-mis_lpu.do" vocName="mainLpu" property="orderLpu" label="Внешний направитель" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <tr>
          <td class="separator" colspan="4">
            <div class="h3">
              <h3>
                К специалисту:
                <msh:ifFormTypeIsNotView formName="smo_directionForm">
                  <a href=" javascript:showTimeWorkCalendar('.do') ">расписание специалистов</a>
                </msh:ifFormTypeIsNotView>
              </h3>
            </div>
          </td>
        </tr>
        <msh:row>
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunctionByDirect" property="workFunctionPlan" label="Куда" fieldColSpan="3" horizontalFill="true"  />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocWorkCalendarDayByWorkFunction" property="datePlan" label="Направлен на дату" horizontalFill="true" parentAutocomplete="workFunctionPlan" />
          <msh:autoComplete vocName="vocWorkCalendarTimeWorkCalendarDay" property="timePlan" label="Время" parentAutocomplete="datePlan" />
        </msh:row>
        <msh:row>
          <msh:separator label="Дополнительные параметры" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" vocName="vocServiceStream" property="serviceStream" viewOnlyField="false" label="Поток обслуживания" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" vocName="vocWorkPlaceType" property="workPlaceType" viewOnlyField="false" label="Рабочее место" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель" 
        	parentId="smo_directionForm.patient" viewAction="entityParentView-mis_kinsman.do" vocName="kinsmanBySMO" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete parentId="smo_directionForm.patient" viewAction="entitySubclassView-mis_medCase.do" vocName="vocOpenedSpoByPatient" property="parent"  label="СПО" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" vocName="vocReason" property="visitReason" viewOnlyField="false" label="Цель визита" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
        	<msh:checkBox label="Неотложная помощь" property="emergency" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="username" viewOnlyField="true" label="Пользователь"/>
        	<msh:textField property="createDate" viewOnlyField="true" label="Дата создания"/>
        </msh:row>
        
        <msh:submitCancelButtonsRow colSpan="3" />
      </msh:panel>
    </msh:form>
    <tags:smo_direction_time name="Time" workFuncId="workFunctionPlan" calenDayId="datePlan" calenTimeId="timePlan" />
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="smo_directionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_directionForm">
      <msh:sideMenu title="Направление">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-smo_direction" name="Изменить" roles="/Policy/Mis/MedCase/Direction/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-smo_direction" name="Удалить" roles="/Policy/Mis/MedCase/Direction/Delete" />
        
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
      	<msh:sideLink params="id" action="/print-vis_ticket.do?s=VisitPrintService&amp;m=printTalon1" name="Талона" title="Печать талона"  roles="/Policy/Mis/MedCase/Direction/Print" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <msh:ifFormTypeIsNotView formName="smo_directionForm">
      <script type="text/javascript">
      //new dateutil.DateField($('datePlanName'));
      //new timeutil.TimeField($('timePlanName'));
	  	eventutil.addEventListener($('datePlanName'), eventutil.EVENT_KEY_UP, 
	  		  	function() {
		  		if ($('datePlanName').value.length==2) $('datePlanName').value=$('datePlanName').value+"." ; 
		  		if ($('datePlanName').value.length==5) $('datePlanName').value=$('datePlanName').value+"." ; 
	  		  	}
	  	) ;
	  	eventutil.addEventListener($('timePlanName'), eventutil.EVENT_KEY_UP, 
	  		  	function() {
		  		if ($('timePlanName').value.length==2) $('timePlanName').value=$('timePlanName').value+":" ; 
	  		  	}
	  	) ;
      
      workFunctionPlanAutocomplete.addOnChangeCallback(function(){
  			updateDefaultDate() ;
  		}) ;
  		function updateDefaultDate() {
  			WorkCalendarService.getDefaultDate($('workFunctionPlan').value,
  			{
  				callback:function(aDateDefault) {
  					if (aDateDefault!=null) {
  						//alert(aDateDefault) ;
	  					var calDayId, calDayInfo,ind1 ;
	  					ind1 = aDateDefault.indexOf("#") ;
  						calDayInfo = aDateDefault.substring(0,ind1) ;
  						calDayId = aDateDefault.substring(ind1+1) ;
  						
	  					$('datePlan').value=calDayId ;
				        $('datePlanName').value = calDayInfo;
  					}
	  				else {
	  					$('datePlan').value=0 ;
				        $('datePlanName').value = "";
	  				}
	  			}
  			}
  			) ;
  			$('timePlan').value="0" ;
			$('timePlanName').value = "";
  			
  		}
  		</script>
    </msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

