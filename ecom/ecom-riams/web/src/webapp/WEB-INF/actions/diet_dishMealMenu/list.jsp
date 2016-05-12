<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Diet">Блюдо-меню</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink guid="helloSideLinkNew" roles="/Policy/Mis/InvalidFood/DishMealMenu/Create" key="ALT+N" action="/entityPrepareCreate-diet_dishMealMenu" name="Создать новое" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entitySaveGoView-diet_dishMealMenu.do" idField="id" guid="e548361-1e0b-4ebd-9f58-b7d919b4">
      <msh:tableColumn columnName="ИД" property="id" guid="94f48796a7-jh40-4ebf-a274-1efd69" />
      <msh:tableColumn columnName="Блюдо" property="dish" guid="8e582ef-105f-43a0-be61-30a86" />
    </msh:table>
  </tiles:put>
</tiles:insert>

