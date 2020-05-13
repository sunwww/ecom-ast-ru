<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Diet" title="Шаблон блюда меню">Блюдо-меню</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink params="id" action="/entityList-diet_mealMenuTemplate" name="Список меню-шаблонов" />
      <msh:sideLink roles="/Policy/Mis/InvalidFood/DishMealMenuTemplate/Create" key="ALT+N" action="/entityPrepareCreate-diet_dishMealMenuTemplate" name="Создать новое блюдо" title="Создать новое блюдо" />
    </msh:sideMenu>
      <msh:sideMenu title="Дополнительно">
        <msh:sideLink action="/entityList-diet_diet" name="Список всех диет" />
        <msh:sideLink roles="/Policy/Mis/Dish/View" action="/entityList-diet_dish" name="Справочник блюд" />
        <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuff/View" action="/diet_foodStuffs" name="Справочник продуктов питания" title="Показать справочник продуктов питания" />
        <msh:sideLink params="" action="/entityList-diet_mealMenuTemplate" name="Шаблоны-меню" title="Просмотр списка шаблонов меню" />
      </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-diet_dishMealMenuTemplate.do" idField="id">
      <msh:tableColumn columnName="Меню" property="menu" />
      <msh:tableColumn columnName="Блюдо" property="dishName" />
    </msh:table>
  </tiles:put>
</tiles:insert>

