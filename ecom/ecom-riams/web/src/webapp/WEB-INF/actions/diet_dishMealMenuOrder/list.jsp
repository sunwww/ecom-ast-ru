<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Diet" title="Заказ блюда меню">Блюдо-меню</msh:title>
    <ecom:titleTrail beginForm="diet_mealMenuForm" mainMenu="Diet" title="Блюда меню" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Блюдо меню">
      <msh:sideLink roles="/Policy/Mis/InvalidFood/DishMealMenuOrder/Create" key="ALT+N" action="/entityParentPrepareCreate-diet_dishMealMenuOrder" name="Создать новое" title="Добавить в меню еще одно блюдо" />
    </msh:sideMenu>
  </tiles:put>
      <msh:sideMenu title="Дополнительно">
        <msh:sideLink action="/entityList-diet_diet" name="Список всех диет" />
        <msh:sideLink roles="/Policy/Mis/Dish/View" action="/entityList-diet_dish" name="Справочник блюд" />
        <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuff/View" action="/diet_foodStuffs" name="Справочник продуктов питания" title="Показать справочник продуктов питания" />
        <msh:sideLink params="" action="/entityList-diet_mealMenuTemplate" name="Шаблоны-меню" title="Просмотр списка шаблонов меню" />
      </msh:sideMenu>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-diet_dishMealMenuOrder.do" idField="id">
      <msh:tableColumn columnName="ИД" property="id" />
      <msh:tableColumn columnName="Блюдо" property="dishName" />
    </msh:table>
  </tiles:put>
</tiles:insert>

