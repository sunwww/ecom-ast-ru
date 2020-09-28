<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags/diet" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title title="Список шаблонов меню раскладок" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:templateDietNewTemplate name="new" />
    <msh:sideMenu title="Добавить">
      <msh:sideLink key="ALT+N" action="/entityPrepareCreate-diet_mealMenuTemplate" name="Новый шаблон меню-раскладки" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/Create" />
      <msh:sideLink key="ALT+N" action="/javascript:shownewTemplateDiet('.do')" name="Из шаблона" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/Create" />
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно">
      <msh:sideLink action="/entityList-diet_diet" name="⇧Список всех диет" />
      <msh:sideLink roles="/Policy/Mis/Dish/View" action="/entityList-diet_dish" name="Справочник блюд" />
      <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuff/View" action="/diet_foodStuffs" name="Справочник продуктов питания" title="Показать справочник продуктов питания" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-diet_mealMenuTemplate.do" idField="id">
      <msh:tableColumn columnName="ИД" property="id" />
      <msh:tableColumn columnName="День недели" identificator="false" property="weekDayName" />
	  <msh:tableColumn columnName="Диета"  property="dietName" />
      <msh:tableColumn columnName="Время приема пищи"  property="mealTimeName" />
      <msh:tableColumn columnName="Дата начала действия" identificator="false" property="dateFrom" />
      <msh:tableColumn columnName="Дата окончания действия" property="dateTo" />
    </msh:table>
  </tiles:put>
</tiles:insert>

