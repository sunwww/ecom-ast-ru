<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib tagdir="/WEB-INF/tags/diet" prefix="diet" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="diet_dietForm" mainMenu="Diet" guid="8632037e-a6c6-4b1f-825a-50a47fd89bd7" title="Меню-заказ" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <diet:templateDietNew record="2" parentId="" name="new" />
    <msh:sideMenu title="Меню-заказ" guid="helloSideMenu-123">
      <msh:sideLink guid="helloSideLinkNew" roles="/Policy/Mis/InvalidFood/MealMenuOrder/Create" key="ALT+N" action="/entityParentPrepareCreate-diet_mealMenuOrder" name="Добавить" params="id" title="Сформировать новое меню-заказ" />
      <msh:sideLink guid="helloSideLinkNew1" key="ALT+N" action="/javascript:shownewTemplateDiet('.do')" name="Из шаблона" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/Create" />
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно" guid="0ecbb4d3-2ad1-4400-9736-a3ed7c5ca571">
      <msh:sideLink action="/entityList-diet_diet" name="⇧Список всех диет" guid="13a3d527-25c9-4ee7-92b2-5f445ddbb27f" />
      <msh:sideLink roles="/Policy/Mis/Dish/View" action="/entityList-diet_dish" name="Справочник блюд" guid="c2b93e08-54ba-45b3-ad9c-59ee145c7922" />
      <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuff/View" action="/diet_foodStuffs" name="Справочник продуктов питания" title="Показать справочник продуктов питания" guid="3597-f85c-4e87-b447-4124027e2e4e" />
      <msh:sideLink params="id" action="/entityList-diet_mealMenuTemplate" name="Шаблоны-меню" title="Просмотр списка шаблонов меню" guid="87e557d5-a9c3-427e-9afc-b42214cb77e5" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-diet_mealMenuOrder.do" idField="id" guid="e361-1e0b-4ebd-9f58-b7d919b4">
      <msh:tableColumn columnName="ИД" property="id" guid="94f6a7-jh40-4ebf-a274-1efd69" />
      <msh:tableColumn columnName="Дата" property="dateFrom" guid="8eef-105f-43a0-be61-30a86" />
      <msh:tableColumn columnName="Диета" property="dietName" guid="f34e1b12-3392-4978-b31f" />
      <msh:tableColumn columnName="Количество порций" property="portionAmount" guid="34e1b12-3392-4978-b31f" />
      <msh:tableColumn identificator="false" property="listDishes" guid="d86b6177-5029-43ac-a9f2-a45c516b2405" columnName="Блюда" />
    </msh:table>
  </tiles:put>
</tiles:insert>

