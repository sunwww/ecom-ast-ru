<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Diet" title="Шаблон блюда меню">Блюдо-меню</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink params="id" action="/entityList-diet_mealMenuTemplate" name="Список меню-шаблонов" guid="e221db75-5ffc-498e-99e2-81904c1fbb5a" />
      <msh:sideLink guid="helloSideLinkNew" roles="/Policy/Mis/InvalidFood/DishMealMenuTemplate/Create" key="ALT+N" action="/entityPrepareCreate-diet_dishMealMenuTemplate" name="Создать новое блюдо" title="Создать новое блюдо" />
    </msh:sideMenu>
      <msh:sideMenu title="Дополнительно" guid="0ecbb4d3-2ad1-4400-9736-a3ed7c5ca571">
        <msh:sideLink action="/entityList-diet_diet" name="Список всех диет" guid="13a3d527-25c9-4ee7-92b2-5f445ddbb27f" />
        <msh:sideLink roles="/Policy/Mis/Dish/View" action="/entityList-diet_dish" name="Справочник блюд" guid="c2b93e08-54ba-45b3-ad9c-59ee145c7922" />
        <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuff/View" action="/diet_foodStuffs" name="Справочник продуктов питания" title="Показать справочник продуктов питания" guid="3597-f85c-4e87-b447-4124027e2e4e" />
        <msh:sideLink params="" action="/entityList-diet_mealMenuTemplate" name="Шаблоны-меню" title="Просмотр списка шаблонов меню" guid="87e557d5-a9c3-427e-9afc-b42214cb77e5" />
      </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-diet_dishMealMenuTemplate.do" idField="id" guid="e548dvf361-1e0b-4ebd-9f58-b7d919b4">
      <msh:tableColumn columnName="Меню" property="menu" guid="94f4v8v79c6a7-jh40-4ebf-a274-1efd69" />
      <msh:tableColumn columnName="Блюдо" property="dishName" guid="8ce5v8v2ef-105f-43a0-be61-30a86" />
    </msh:table>
  </tiles:put>
</tiles:insert>

