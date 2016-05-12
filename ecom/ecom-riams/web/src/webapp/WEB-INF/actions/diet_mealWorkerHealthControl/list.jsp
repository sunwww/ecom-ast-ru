<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Diet">Контроль здоровья работающих с пищей</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink action="/entityList-diet_diet.do?id=-1" name="Список диет" guid="87rt3bnt99a7-45cb-4d46-bf19-0f681929b4c2" />
      <msh:sideLink guid="helloSideLinkNew" roles="/Policy/Mis/InvalidFood/MealWorkerHealthControl/Create" key="ALT+N" action="/entityPrepareCreate-diet_mealWorkerHealthControl" name="Создать новую запись" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entitySaveGoView-diet_mealWorkerHealthControl.do" idField="id" guid="21egff58f361-1e0b-4ebd-9f58-b7d919b4">
      <msh:tableColumn columnName="Дата" property="controlDate" guid="945vcf6a7-ed40-4ebf-a274-1efd69" />
      <msh:tableColumn columnName="Сотрудник" identificator="false" property="controllingWorkerInfo" guid="e31tg4gfe50e7-065a-4a0a-8468-fe94c856c935" />
      <msh:tableColumn columnName="Отсутствие острых кишечных заболеваний" property="acuteIntestinalDiseaseAbsence" guid="82e6ydteef-105f-43a0-be61-30a86" />
      <msh:tableColumn columnName="Отсутствие гнойных заболеваний" property="purulentDiseaseAbsence" guid="82er56yte2546ef-105f-43a0-be61-30a86" />
      <msh:tableColumn columnName="Диагноз нетрудоспособности по уходу" property="nursingDisabilityDiagnosis" guid="82de4e6y014fteef-105f-43a0-be61-30a86" />
      <msh:tableColumn columnName="Диетолог" property="dietitian" guid="82e6yt4de4grfeef-105f-43a0-be61-30a86" />
      <msh:tableColumn columnName="Разрешил" property="dietitianInfo" guid="82e6n1h6sfyteef-105f-43a0-be61-30a86" />
    </msh:table>
  </tiles:put>
</tiles:insert>

