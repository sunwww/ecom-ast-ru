<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Diet">Журнал контроля качества пищи</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink action="/entityList-diet_diet.do?id=-1" name="Список диет" guid="87rt3b99a7-45cb-4d46-bf19-0f681929b4c2" />
      <msh:sideLink guid="helloSideLinkNew" roles="/Policy/Mis/InvalidFood/MealQualityControl" key="ALT+N" action="/entityPrepareCreate-diet_mealQualityControl" name="Создать новую запись" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entitySaveGoView-diet_mealQualityControl.do" idField="id" guid="21egf361-1e0b-4ebd-9f58-b7d919b4">
      <msh:tableColumn columnName="Дата" property="controlDate" guid="9456f6a7-ed40-4ebf-a274-1efd69" />
      <msh:tableColumn columnName="Прием пищи" identificator="false" property="mealTime" guid="e314gfe50e7-065a-4a0a-8468-fe94c856c935" />
      <msh:tableColumn columnName="Оценка меню" identificator="false" property="estimateMenu" guid="4b71rewd292-9ef0-4089-9385-93907729c8ab" />
      <msh:tableColumn columnName="Оценка блюд" property="estimateDish" guid="82e6yteef-105f-43a0-be61-30a86" />
      <msh:tableColumn columnName="Оценка приготовления" property="estimatePreparation" guid="82e6yte2546ef-105f-43a0-be61-30a86" />
      <msh:tableColumn columnName="Оценка веса" property="estimateWeight" guid="82e6y014fteef-105f-43a0-be61-30a86" />
      <msh:tableColumn columnName="Оценка санитарии" property="estimateSanitation" guid="82e6yt4rfeef-105f-43a0-be61-30a86" />
      <msh:tableColumn columnName="Разрешил" property="dutyDoctorInfo" guid="82e6n1h6yteef-105f-43a0-be61-30a86" />
    </msh:table>
  </tiles:put>
</tiles:insert>

