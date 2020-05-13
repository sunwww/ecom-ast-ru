<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags/diet" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="entitySaveGoView-diet_mealMenuTemplate.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="diet" />
      <msh:panel>
        <msh:row>
          <msh:autoComplete showId="false" hideLabel="false" property="diet" viewOnlyField="false" horizontalFill="true" fieldColSpan="3" vocName="Diet" />
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" vocName="vocServiceStream" hideLabel="false" property="serviceStream" viewOnlyField="false" label="Поток обслуживания" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocWeekDay" property="weekDay" label="День недели" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="dateFrom" label="Дата начала действия" />
          <msh:textField property="dateTo" label="Дата окончания действия" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="6" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="diet_mealMenuTemplateForm">
      <msh:section title="Список шаблонов меню">
        <ecom:parentEntityListAll formName="diet_childMealMenuTemplateForm" attribute="childMenus" />
        <msh:table hideTitle="false" idField="id" name="childMenus" action="entityParentView-diet_childMealMenuTemplate.do">
          <msh:tableColumn identificator="false" property="description" columnName="Название приема пищи" />
          <msh:tableColumn identificator="false" property="listDishes" columnName="Список блюд" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Diet" beginForm="diet_mealMenuTemplateForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="diet_mealMenuTemplateForm">
      <tags:templateDietMeal parentId="${param.id}" name="add" />
      <msh:sideMenu title="Меню-шаблон">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-diet_mealMenuTemplate" name="Изменить" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-diet_mealMenuTemplate" name="Удалить" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-diet_childMealMenuTemplate" name="Прием пищи" title="Добавить прием пищи в меню-раскладку" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/Create" />
        <msh:sideLink action="/javascript:showaddTemplateDiet('.do')" name="прием пищи из шаблона" title="Добавить приемы пищи из шаблона меню-раскладки" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/Create" />

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

