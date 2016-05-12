<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="entityParentSaveGoView-diet_mealWorkerHealthControl.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="diet" />
      <msh:panel guid="panel" colsWidth="10%,20%,10%,60%">
        <msh:row guid="row1" />
        <msh:textField property="controlDate" label="Дата" guid="5n45ty64gj653a-c6c2-4221-bb72-7706" />
        <msh:row guid="b5fgh4f45ereb-b971-441e-9a90-5194a" />
        <msh:autoComplete vocName="vocWorker" property="controllingWorker" label="Cотрудник" guid="84ate5hcg21e7-0a81-4762-9264-fbffc6" horizontalFill="true" size="50" fieldColSpan="3" />
        <msh:row guid="bt5fgh4f45h7syt6r6eb-b971-441e-9a90-519i4a" />
        <msh:checkBox property="acuteIntestinalDiseaseAbsence" label="Отсутствие острых кишечных заболеваний" guid="k123jht52iu7-0a81-4762-9864-fbffc6" horizontalFill="true" />
        <msh:checkBox property="purulentDiseaseAbsence" label="Отсутствие гнойных заболеваний" guid="kj2hy6t52iu7-0a81-4762-9864-fbf25fc6" />
        <msh:row guid="bt5fgh23nb645s6eb-b971-441e-9a90-519i10a" />
        <msh:textField property="nursingDisabilityDiagnosis" label="Диагноз нетрудоспособности по уходу" guid="kj76214ng2iu7-0a81-4762-9864-fbffc6" horizontalFill="true" fieldColSpan="3" size="50" />
        <msh:row guid="btmn75ht2fgh645v26eb-b971-441e-9a90-5174125mj0a" />
        <msh:autoComplete vocName="vocWorker" property="dietitian" label="Диетолог" guid="kj76ds32ft756u7-0a81-4762-9864-fb2ffc6" horizontalFill="true" fieldColSpan="3" />
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="6" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Diet" beginForm="diet_mealWorkerHealthControlForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu-123">
      <msh:sideLink action="/entityList-diet_mealWorkerHealthControl" name="Список" guid="cfccnc3s8df5-acdc-407d-ab94-f40ac55be477" params="id" />
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-diet_mealWorkerHealthControl" name="Изменить" roles="/Policy/Mis/InvalidFood/MealWorkerHealthControl/Edit" />
      <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-diet_mealWorkerHealthControl" name="Удалить" roles="/Policy/Mis/InvalidFood/MealWorkerHealthControl/Delete" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

