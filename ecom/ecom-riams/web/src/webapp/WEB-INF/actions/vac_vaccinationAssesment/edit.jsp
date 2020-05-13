<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entitySaveGoView-vac_vaccinationAssesment.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="vaccination" />
      <msh:panel>
        <msh:row>
          <msh:textField property="assesmentDate" label="Дата" />
          <msh:textField property="assesmentTime" label="Время" />
        </msh:row>
        <msh:row />
        <msh:row>
          <msh:textArea rows="2" property="commonReactionComment" label="Общие реакции" fieldColSpan="3" horizontalFill="on" />
        </msh:row>
        <msh:row>
          <msh:textArea rows="2" property="localReactionComment" label="Локальные реакции" fieldColSpan="3" horizontalFill="on" />
        </msh:row>
        <msh:autoComplete vocName="workFunction" property="worker" label="Кто оценивал" fieldColSpan="3" horizontalFill="true" />
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="vac_vaccinationAssesmentForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
  	<msh:ifFormTypeIsView formName="vac_vaccinationAssesmentForm">
    <msh:sideMenu>
      <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-vac_vaccinationAssesment" name="Изменить" roles="/Policy/Mis/Vaccination/VaccinationAssesment/Edit" />
      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-vac_vaccinationAssesment" name="Удалить" roles="/Policy/Mis/Vaccination/VaccinationAssesment/Delete" />
    </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

