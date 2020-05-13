<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Состояние здоровья
    	  -->
    <msh:form action="/entityParentSaveGoParentView-psych_healthState.do" defaultField="startDate">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="careCard" />
      <msh:panel>
        <msh:row>
        	<msh:textField property="startDate" label="Дата начала"/>
        	<msh:textField property="finishDate" label="Дата окончания"/>
        </msh:row>

        <msh:row>
        	<msh:autoComplete fieldColSpan="3" horizontalFill="true" property="kind" label="Вид состояния психического здоровья" vocName="vocPsyhicHealthState"/>
        </msh:row>
        <msh:row>
        	<msh:textArea property="notes" label="Описание" fieldColSpan="3"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="psych_healthStateForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Принудительное лечение">
      <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-psych_healthState" name="Изменить" roles="/Policy/Mis/Psychiatry/CareCard/HealthState/Edit" />
      <msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-psych_healthState" name="Удалить" confirm="Вы точно хотите удалить?"  roles="/Policy/Mis/Psychiatry/CareCard/HealthState/Delete"  />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>