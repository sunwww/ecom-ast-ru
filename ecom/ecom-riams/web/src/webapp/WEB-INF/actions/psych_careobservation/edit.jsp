<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Психиатрическое наблюдение
    	  -->
    <msh:form action="/entityParentSaveGoParentView-psych_careobservation.do" defaultField="startDate">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="careCard" />
      <msh:hidden property="lpuAreaPsychCareCard" />
      <msh:panel>
        <msh:row>
        	<msh:textField property="startDate" label="Дата начала"/>
        	<msh:textField property="finishDate" label="Дата окончания"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete size="100" property="criminalCodeArticle" label="Статья уг.кодекса" vocName="vocCriminalCodeArticle" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete size="100" property="ambulatoryCare" label="Вид наблюдения" vocName="vocPsychAmbulatoryCare" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete size="100" parentAutocomplete="ambulatoryCare" property="dispensaryGroup" label="Группа" vocName="vocPsychDispensaryGroup" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete size="100" property="strikeOffReason" label="Причина снятия" vocName="vocPsychStrikeOffReasonAdn" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    <script type="text/javascript">
    	if (+$('lpuAreaPsychCareCard')>0) window.location = "entityParentView-psych_observation.do?id=${param.id}" ;
    </script>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="psych_careobservationForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Психиатрическое наблюдение">
      <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-psych_careobservation" name="Изменить" roles="/Policy/Mis/Psychiatry/CareCard/PsychiaticObservation/Edit" />
      <msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-psych_careobservation" name="Удалить" confirm="Вы точно хотите удалить?"  roles="/Policy/Mis/Psychiatry/CareCard/PsychiaticObservation/Delete"  />
    </msh:sideMenu>

  </tiles:put>

</tiles:insert>