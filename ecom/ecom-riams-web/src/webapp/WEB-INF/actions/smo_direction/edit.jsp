<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entitySaveGoView-smo_direction" defaultField="workFunctionPlanName">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="patient" />
      <msh:panel guid="panel" colsWidth="10%, 10%, 10%">
        <msh:row guid="fa7ff4e9-4b3d-4402-b046-86283cf7938e">
          <msh:autoComplete viewAction="entityParentView-mis_lpu.do" vocName="mainLpu" property="orderLpu" label="Внешний направитель" guid="cbab0829-c896-4b74-9a68-c9f95676cc3b" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <tr>
          <td class="separator" colspan="4">
            <div class="h3">
              <h3>
                К специалисту:
                <msh:ifFormTypeIsNotView formName="smo_directionForm" guid="71ddfd0b-09a1-4cfe-bd83-3dc3738cb9d2">
                  <a href=" javascript:showTimeWorkCalendar('.do') ">расписание специалистов</a>
                </msh:ifFormTypeIsNotView>
              </h3>
            </div>
          </td>
        </tr>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunctionByDirect" property="workFunctionPlan" label="Куда" guid="377c0e96-6812-477d-b44f-d640d659b7b6" fieldColSpan="3" horizontalFill="true" size="50" />
        </msh:row>
        <msh:row guid="row1">
          <msh:autoComplete vocName="vocWorkCalendarDayByWorkFunction" property="datePlan" label="Направлен на дату" guid="d7f4bef5-0f84-4d3c-b7d9-b7c7c5d51907" horizontalFill="true" parentAutocomplete="workFunctionPlan" />
          <msh:autoComplete vocName="vocWorkCalendarTimeWorkCalendarDay" property="timePlan" label="Время" guid="1d6b9712-62cc-4c67-a2d8-77bfef298ff3" parentAutocomplete="datePlan" />
        </msh:row>
        <msh:row guid="6898ae03-16fe-46dd-9b8f-8cc25e19913b">
          <msh:separator label="Дополнительные параметры" colSpan="4" guid="314f5445-a630-411f-88cb-16813fefa0d9" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель" 
        	parentId="smo_directionForm.patient" viewAction="entityParentView-mis_kinsman.do" vocName="kinsmanBySMO" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row guid="69487e8f-d832-47f8-b486-dd2cd97d11ca">
          <msh:autoComplete parentId="smo_directionForm.patient" viewAction="entitySubclassView-mis_medCase.do" vocName="vocOpenedSpoByPatient" property="parent" size="50" label="СПО" guid="5c46703a-e901-4c07-9426-10bc2ca3f5df" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="16db3ed2-4536-4a64-9cac-b73390bf65d6">
          <msh:autoComplete showId="false" vocName="vocReason" property="visitReason" viewOnlyField="false" label="Цель визита" guid="5f53c276-d7de-423a-86f5-b523ed37e75d" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
        	<msh:checkBox label="Неотложная помощь" property="emergency" fieldColSpan="3"/>
        </msh:row>
        <msh:row guid="a970fa32-6038-4e0b-a0a8-c57b5ebd3a04">
          <msh:autoComplete showId="false" vocName="vocWorkPlaceType" property="workPlaceType" viewOnlyField="false" label="Рабочее место" guid="c61023b1-bf59-432f-8764-8a571b1eddf8" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="de2d6415-7834-4d4a-934b-c4740cb28b6c">
          <msh:autoComplete showId="false" vocName="vocServiceStream" property="serviceStream" viewOnlyField="false" label="Поток обслуживания" guid="58d43ea6-3555-4eaf-978e-f259920d179c" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="6a5e584e-2322-4c12-a0d0-285bf544e7ef" />
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <tags:smo_direction_time name="Time" workFuncId="workFunctionPlan" calenDayId="datePlan" calenTimeId="timePlan" />
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="smo_directionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_directionForm" guid="22796a9c-c0f8-46e4-b0de-02e2f7d3472b">
      <msh:sideMenu guid="sideMenu-123" title="Направление">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityParentEdit-smo_direction" name="Изменить" roles="/Policy/Mis/MedCase/Direction/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-smo_direction" name="Удалить" roles="/Policy/Mis/MedCase/Direction/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <msh:ifFormTypeIsNotView formName="smo_directionForm" guid="0cfa71af-92f6-432b-b592-483a2c92429d">
      <script type="text/javascript">workFunctionPlanAutocomplete.addOnChangeCallback(function(){
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
  			
  		}</script>
    </msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

