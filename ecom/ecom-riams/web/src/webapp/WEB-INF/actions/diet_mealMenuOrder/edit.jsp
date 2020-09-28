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
    <msh:form action="/entityParentSaveGoView-diet_mealMenuOrder.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="diet" />
      <msh:panel>
        <msh:row>
          <msh:autoComplete showId="false" hideLabel="false" property="diet" viewOnlyField="false" horizontalFill="true" vocName="Diet" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="lpu" property="lpu" label="Отделение" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="dateFrom" label="Дата" />
          <msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Поток обслуживания" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" hideLabel="false" property="mealTime" viewOnlyField="false" horizontalFill="false" vocName="vocMealTime" />
          <msh:textField passwordEnabled="false" hideLabel="false" property="portionAmount" viewOnlyField="false" horizontalFill="false" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="6" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="diet_mealMenuOrderForm">
      <msh:ifInRole roles="/Policy/Mis/InvalidFood/DishMealMenu/View">
        <msh:section title="Список блюд меню-заказа">
          <ecom:parentEntityListAll formName="diet_dishMealMenuOrderForm" attribute="dishes" />
          <msh:table hideTitle="false" idField="id" name="dishes" action="entityParentView-diet_dishMealMenuOrder.do">
            <msh:tableColumn identificator="false" property="dishName" columnName="Название блюда" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Diet" beginForm="diet_mealMenuOrderForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="diet_mealMenuOrderForm">
      <tags:templateDietDish record="1" parentId="${param.id}" name="add" />
      <msh:sideMenu title="Меню-заказ">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-diet_mealMenuOrder" name="Изменить" roles="/Policy/Mis/InvalidFood/MealMenuOrder/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-diet_mealMenuOrder" name="Удалить" roles="/Policy/Mis/InvalidFood/MealMenuOrder/Delete" />
        <msh:sideLink action="/entityParentListRedirect-diet_mealMenuOrder" name="Список меню-заказов" params="id" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-diet_dishMealMenuOrder" name="Блюдо в меню" title="Добавить блюдо в меню-заказ" roles="/Policy/Mis/InvalidFood/DishMealMenuOrder/Create" />
        <msh:sideLink action="/javascript:showaddTemplateDiet('.do')" name="Блюда в меню из шаблона" title="Добавить блюда в меню-заказ из существующего шаблона приема пищи" roles="/Policy/Mis/InvalidFood/DishMealMenuOrder/Create" />
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно">
        <msh:sideLink action="/entityList-diet_diet" name="⇧Список всех диет" />
        <msh:sideLink roles="/Policy/Mis/Dish/View" action="/entityList-diet_dish" name="Справочник блюд" />
        <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuff/View" action="/diet_foodStuffs" name="Справочник продуктов питания" title="Показать справочник продуктов питания" />
        <msh:sideLink params="id" action="/entityList-diet_mealMenuTemplate" name="Шаблоны-меню" title="Просмотр списка шаблонов меню" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

