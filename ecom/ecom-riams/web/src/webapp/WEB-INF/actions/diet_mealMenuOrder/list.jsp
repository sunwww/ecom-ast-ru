<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib tagdir="/WEB-INF/tags/diet" prefix="diet" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="diet_dietForm" mainMenu="Diet" title="Меню-заказ" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <diet:templateDietNew record="2" parentId="" name="new" />
    <msh:sideMenu title="Меню-заказ">
      <msh:sideLink roles="/Policy/Mis/InvalidFood/MealMenuOrder/Create" key="ALT+N" action="/entityParentPrepareCreate-diet_mealMenuOrder" name="Добавить" params="id" title="Сформировать новое меню-заказ" />
      <msh:sideLink key="ALT+N" action="/javascript:shownewTemplateDiet('.do')" name="Из шаблона" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/Create" />
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно">
      <msh:sideLink action="/entityList-diet_diet" name="⇧Список всех диет" />
      <msh:sideLink roles="/Policy/Mis/Dish/View" action="/entityList-diet_dish" name="Справочник блюд" />
      <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuff/View" action="/diet_foodStuffs" name="Справочник продуктов питания" title="Показать справочник продуктов питания" />
      <msh:sideLink params="id" action="/entityList-diet_mealMenuTemplate" name="Шаблоны-меню" title="Просмотр списка шаблонов меню" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-diet_mealMenuOrder.do" idField="id">
      <msh:tableColumn columnName="ИД" property="id" />
      <msh:tableColumn columnName="Дата" property="dateFrom" />
      <msh:tableColumn columnName="Диета" property="dietName" />
      <msh:tableColumn columnName="Количество порций" property="portionAmount" />
      <msh:tableColumn identificator="false" property="listDishes" columnName="Блюда" />
    </msh:table>
  </tiles:put>
</tiles:insert>

