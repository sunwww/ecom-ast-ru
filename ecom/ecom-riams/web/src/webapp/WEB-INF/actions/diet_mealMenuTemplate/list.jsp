<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags/diet" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title title="Список шаблонов меню раскладок" guid="2b730234-40d8-4a7c-b415-a5a66b682690" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:templateDietNewTemplate name="new" />
    <msh:sideMenu title="Добавить" guid="helloSideMenu-123">
      <msh:sideLink guid="helloSideLinkNew" key="ALT+N" action="/entityPrepareCreate-diet_mealMenuTemplate" name="Новый шаблон меню-раскладки" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/Create" />
      <msh:sideLink guid="helloSideLinkNew1" key="ALT+N" action="/javascript:shownewTemplateDiet('.do')" name="Из шаблона" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/Create" />
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно" guid="0ecbb4d3-2ad1-4400-9736-a3ed7c5ca571">
      <msh:sideLink action="/entityList-diet_diet" name="⇧Список всех диет" guid="13a3d527-25c9-4ee7-92b2-5f445ddbb27f" />
      <msh:sideLink roles="/Policy/Mis/Dish/View" action="/entityList-diet_dish" name="Справочник блюд" guid="c2b93e08-54ba-45b3-ad9c-59ee145c7922" />
      <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuff/View" action="/diet_foodStuffs" name="Справочник продуктов питания" title="Показать справочник продуктов питания" guid="3597-f85c-4e87-b447-4124027e2e4e" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-diet_mealMenuTemplate.do" idField="id" guid="21e361-1e0b-4ebd-9f58-b7d919b4">
      <msh:tableColumn columnName="ИД" property="id" guid="94f6a7-ed40-4ebf-a274-1efd69" />
      <msh:tableColumn columnName="День недели" identificator="false" property="weekDayName" guid="e31450e7-065a-4a0a-8468-fe94c856c935" />
	  <msh:tableColumn columnName="Диета"  property="dietName" guid="4bfef0-4089-9385-93fc8ab" />
      <msh:tableColumn columnName="Время приема пищи"  property="mealTimeName" guid="4bdfef0-4089-9385-9d3fc8ab" />
      <msh:tableColumn columnName="Дата начала действия" identificator="false" property="dateFrom" guid="4b71d292-9ef0-4089-9385-93907729c8ab" />
      <msh:tableColumn columnName="Дата окончания действия" property="dateTo" guid="82eeef-105f-43a0-be61-30a86" />
    </msh:table>
  </tiles:put>
</tiles:insert>

