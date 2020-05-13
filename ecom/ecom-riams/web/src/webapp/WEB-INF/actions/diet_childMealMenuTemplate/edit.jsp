<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib tagdir="/WEB-INF/tags/diet" prefix="diet" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="entitySaveGoView-diet_childMealMenuTemplate.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parentMenu" />
      <msh:panel>
        <msh:row>
          <msh:autoComplete vocName="vocMealTime" property="mealTime" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="6" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="diet_childMealMenuTemplateForm">
      <msh:section title="Список блюд ">
        <ecom:parentEntityListAll formName="diet_dishMealMenuTemplateForm" attribute="dishes" />
        <msh:table hideTitle="false" idField="id" name="dishes" action="entityParentView-diet_dishMealMenuTemplate.do">
          <msh:tableColumn identificator="false" property="dishName" columnName="Название блюда" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Diet" beginForm="diet_childMealMenuTemplateForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="diet_childMealMenuTemplateForm">
		<diet:templateDietDish record="1" parentId="${param.id}" name="add" />
		<msh:sideMenu title="Меню-шаблон">
        <msh:sideLink action="/entityParentListRedirect-diet_mealMenuTemplate" name="⇧Список меню-шаблонов текущего ЛПУ" params="id" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/View" />
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-diet_mealMenuTemplate" name="Изменить" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-diet_mealMenuTemplate" name="Удалить" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-diet_dishMealMenuTemplate" name="Блюдо в меню" title="Добавить блюдо в меню" roles="/Policy/Mis/InvalidFood/DishMealMenuTemplate/Create" />
        <msh:sideLink action="/javascript:showaddTemplateDiet('.do')" name="Блюда в меню из шаблона" title="Добавить блюда в меню-заказ из существующего шаблона приема пищи" roles="/Policy/Mis/InvalidFood/DishMealMenuOrder/Create" />
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно">
        <msh:sideLink action="/entityList-diet_diet" name="⇧Список всех диет" />
        <msh:sideLink roles="/Policy/Mis/Dish/View" action="/entityList-diet_dish" name="Справочник блюд" />
        <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuff/View" action="/diet_foodStuffs" name="Справочник продуктов питания" title="Показать справочник продуктов питания" />
        <msh:sideLink action="/entityList-diet_mealMenuTemplate" name="Шаблоны меню-раскладок" title="Просмотр списка шаблонов меню" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

