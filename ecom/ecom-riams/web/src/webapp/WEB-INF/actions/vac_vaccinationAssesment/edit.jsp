<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entitySaveGoView-vac_vaccinationAssesment.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="vaccination" />
      <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:textField guid="textFieldHello" property="assesmentDate" label="Дата" />
          <msh:textField property="assesmentTime" label="Время" guid="e71fa83a-c6c2-4221-bb72-77067f879971" />
        </msh:row>
        <msh:row guid="5cf2043b-b2f2-4b8d-9473-bdda8aca240b" />
        <msh:row guid="7b2b7429-6a7c-44e2-a3b1-0f022c28b958">
          <msh:textArea rows="2" property="commonReactionComment" label="Общие реакции" guid="966e6158-6d8c-4dc0-9602-843b16e5801f" fieldColSpan="3" horizontalFill="on" />
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:textArea rows="2" property="localReactionComment" label="Локальные реакции" guid="6a8360f5-3f3f-43b8-8b43-6d834a9caee2" fieldColSpan="3" horizontalFill="on" />
        </msh:row>
        <msh:autoComplete vocName="workFunction" property="worker" label="Кто оценивал" guid="27830ebd-44e9-48ba-a79d-4c2987a756bf" fieldColSpan="3" horizontalFill="true" />
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="vac_vaccinationAssesmentForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
  	<msh:ifFormTypeIsView formName="vac_vaccinationAssesmentForm">
    <msh:sideMenu guid="sideMenu-123">
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityParentEdit-vac_vaccinationAssesment" name="Изменить" roles="/Policy/Mis/Vaccination/VaccinationAssesment/Edit" />
      <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-vac_vaccinationAssesment" name="Удалить" roles="/Policy/Mis/Vaccination/VaccinationAssesment/Delete" />
    </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

