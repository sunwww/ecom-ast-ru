<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Суицид
    	  -->
    <msh:form action="/entityParentSaveGoParentView-psych_suicide.do" defaultField="fulfilmentDate">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="careCard" />
      
      <msh:panel>
        <msh:row>
        	<msh:textField property="fulfilmentDate" label="Дата совершения"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete size="100" property="nature" label="Характер суицида" vocName="vocPsychSuicideNature" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isFinished" label="Завершен"/>
        </msh:row>
        <msh:row>
        	<msh:textArea property="notes" label="Описание"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="psych_suicideForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Суицид">
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityParentEdit-psych_suicide" name="Изменить" roles="/Policy/Mis/Psychiatry/CareCard/Suicide/Edit" />
      <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" params="id" action="/entityParentDelete-psych_suicide" name="Удалить" confirm="Вы точно хотите удалить?"  roles="/Policy/Mis/Psychiatry/CareCard/Suicide/Delete"  />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>